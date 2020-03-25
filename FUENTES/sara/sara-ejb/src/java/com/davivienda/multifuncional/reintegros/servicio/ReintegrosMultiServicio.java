// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.reintegros.servicio;

import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.entitys.ReintegrosmultiefectivoPK;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.dto.TxMultifuncionalEfeDTO;
import com.davivienda.sara.entitys.Hostmultifuncional;
import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.utilidades.conversion.Cadena;
import java.math.BigDecimal;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.entitys.TxmultifuncionalefectivoHisto;
import java.util.logging.Level;
import java.util.Iterator;
import java.util.Collection;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.util.logging.Logger;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.transaccion.SumatoriaTransaccionesHostBean;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import java.util.List;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.servicio.ReintegrosMultiEfectivoServicio;
import com.davivienda.multifuncional.tablas.hostmultifuncional.servicio.HostMultifuncionalServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
import com.davivienda.sara.base.BaseEstadisticaServicio;

public class ReintegrosMultiServicio extends BaseEstadisticaServicio
{
    TransaccionServicio transaccionServicio;
    private CajeroServicio cajeroServicio;
    HostMultifuncionalServicio hostAtmServicio;
    ReintegrosMultiEfectivoServicio reintegrosServicio;
    private long valorMinimoReintegros;
    private long valorMinimoReintegrosNegativo;
    List<String> binesBanagrario;
    
    public ReintegrosMultiServicio(final EntityManager em) {
        super(em);
        this.valorMinimoReintegros = 9000L;
        this.valorMinimoReintegrosNegativo = -9000L;
        this.binesBanagrario = new ArrayList<String>();
        this.transaccionServicio = new TransaccionServicio(em);
        this.cajeroServicio = new CajeroServicio(em);
        this.hostAtmServicio = new HostMultifuncionalServicio(em);
        this.reintegrosServicio = new ReintegrosMultiEfectivoServicio(em);
    }
    
    public Integer calcularReintegros(final Calendar fecha, final Date fechaHisto) {
        Integer regsProceso = 0;
        final Collection<SumatoriaTransaccionesHostBean> regs = new ArrayList<SumatoriaTransaccionesHostBean>();
        try {
            final Date fInicial = Fecha.getFechaInicioDia(fecha).getTime();
            final Date fFin = Fecha.getFechaFinDia(fecha.getTime());
            final Collection<Cajero> cajeros = this.cajeroServicio.getCajerosActivosMulti();
            Logger.getLogger("globalApp").info("Se inicia el proceso de Calcular reintegros " + Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD));
            for (final Cajero cajero : cajeros) {
                Logger.getLogger("globalApp").info("Inicia Sumatoria Cajero :" + cajero.getCodigoCajero().toString());
                SumatoriaTransaccionesHostBean reg = new SumatoriaTransaccionesHostBean();
                reg = this.getSumatoria(cajero.getCodigoCajero(), fInicial, fFin, fechaHisto);
                regsProceso += this.buscarReintegrosHost(reg.getCodigoCajero(), fInicial, fFin, fechaHisto);
            }
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error Calculando reintegros " + ex.getMessage());
            regsProceso = -1;
        }
        Logger.getLogger("globalApp").info("Fin del proceso de  Calculo de reintegros   para el dia " + Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD) + "  reintegros encontrados : " + regsProceso);
        return regsProceso;
    }
    
    private SumatoriaTransaccionesHostBean getSumatoria(final Integer codigoCajero, Date fechaInicial, Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion, IllegalArgumentException {
        String nombreTabla = "";
        ReintegrosMultiServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - getSumatoria fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla historico: " + TxmultifuncionalefectivoHisto.class.getSimpleName());
            nombreTabla = "TxmultifuncionalefectivoHisto";
            tablaHisto = true;
        }
        else {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla : " + Txmultifuncionalefectivo.class.getSimpleName());
            nombreTabla = "Txmultifuncionalefectivo";
        }
        List result = new ArrayList();
        final SumatoriaTransaccionesHostBean reg = new SumatoriaTransaccionesHostBean();
        Query query = null;
        final String strQuery = "(select   sum(obj.valorconsignacion) as sumTx from " + nombreTabla + " obj " + " where obj.codigocajero= ? and  " + " obj.fechacajero (+) between ? and ?" + " and  obj.estado=0 ) " + " UNION ALL " + " (select   sum(obj.valor/100) as sumTx from Hostmultifuncional obj " + " where obj.codigocajero= ? and   obj.fecha (+) between ? and ?)";
        BigDecimal sumTransaccion = new BigDecimal(0);
        BigDecimal sumHost = new BigDecimal(0);
        if (codigoCajero == null || codigoCajero == 0) {
            throw new IllegalArgumentException("Se debe seleccionar un cajero");
        }
        if (fechaInicial == null) {
            fechaInicial = Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser despues de la fecha Inicial");
        }
        try {
            query = this.em.createNativeQuery(strQuery);
            query.setParameter(1, (Object)codigoCajero);
            query.setParameter(2, (Object)fechaInicial).setParameter(3, (Object)fechaFinal);
            query.setParameter(4, (Object)codigoCajero);
            query.setParameter(5, (Object)fechaInicial).setParameter(6, (Object)fechaFinal);
            result = query.getResultList();
            if (result != null) {
                if (!result.get(0).toString().contains("[null]")) {
                    sumTransaccion = BigDecimal.valueOf(Cadena.aLong(result.get(0).toString().replace("[", "").replace("]", "")));
                }
                if (!result.get(1).toString().contains("[null]")) {
                    sumHost = BigDecimal.valueOf(Cadena.aLong(result.get(1).toString().replace("[", "").replace("]", "")));
                }
                reg.setSumTransaccion(sumTransaccion);
                reg.setSumHost(sumHost);
                reg.setDiferencia(sumHost.subtract(sumTransaccion));
                reg.setCodigoCajero(codigoCajero);
                reg.setFechaInicial(Fecha.aCadena(fechaInicial, FormatoFecha.AAAAMMDD));
                reg.setFechaFinal(Fecha.aCadena(fechaFinal, FormatoFecha.AAAAMMDD));
            }
        }
        catch (IllegalStateException ex) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return reg;
    }
    
    private Integer buscarReintegrosHost(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        Txmultifuncionalefectivo regTxReintegro = null;
        Collection<Object[]> regsObject = null;
        Hostmultifuncional regHostAtm = new Hostmultifuncional();
        Integer regsProceso = 0;
        try {
            regsObject = this.getTransaccionTalonUnico(codigoCajero, fechaInicial, fechaFinal, fechaHisto);
            if (regsObject != null) {
                for (final Object[] item : regsObject) {
                    final Integer talon = (Integer)item[1];
                    regHostAtm = this.hostAtmServicio.getReintegrosHost(codigoCajero, fechaInicial, fechaFinal, talon);
                    if (regHostAtm != null) {
                        if (!regHostAtm.getValor().equals(new Long(0L))) {
                            regTxReintegro = this.getTransaccionReintegrosXTalon(codigoCajero, talon, fechaInicial, fechaFinal, fechaHisto);
                        }
                        if (regTxReintegro == null) {
                            continue;
                        }
                        this.guardarReintegro(regHostAtm, regTxReintegro);
                        ++regsProceso;
                    }
                }
            }
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error buscando reintegros  " + ex.getMessage());
        }
        return regsProceso;
    }
    
    public Collection<Object[]> getTransaccionTalonUnico(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        ReintegrosMultiServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - getTransaccionTalonUnico fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla historico: " + TxmultifuncionalefectivoHisto.class.getSimpleName());
            nombreTabla = "TxmultifuncionalefectivoHisto";
            tablaHisto = true;
        }
        else {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla : " + Txmultifuncionalefectivo.class.getSimpleName());
            nombreTabla = "Txmultifuncionalefectivo";
        }
        final Object[] cadenaArray = new Object[2];
        Collection<Object[]> reg = null;
        try {
            final Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            final Date fFin = fechaFinal;
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery(nombreTabla + ".TransaccionesTalonUnico");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
                reg = (Collection<Object[]>)query.getResultList();
            }
        }
        catch (IllegalStateException ex) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return reg;
    }
    
    public Txmultifuncionalefectivo getTransaccionReintegrosXTalon(final Integer codigoCajero, final Integer talon, final Date fechaInicial, final Date fechaFinal, final Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        ReintegrosMultiServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - getTransaccionTalonUnico fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla historico: " + TxmultifuncionalefectivoHisto.class.getSimpleName());
            nombreTabla = "TxmultifuncionalefectivoHisto";
            tablaHisto = true;
        }
        else {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla : " + Txmultifuncionalefectivo.class.getSimpleName());
            nombreTabla = "Txmultifuncionalefectivo";
        }
        Txmultifuncionalefectivo reg = null;
        try {
            final Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            final Date fFin = fechaFinal;
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery(nombreTabla + ".TransaccionReintegrosXTalon");
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
                query.setParameter("codigoCajero", (Object)cCajero);
                query.setParameter("talon", (Object)talon);
                if (tablaHisto) {
                    reg = new TxMultifuncionalEfeDTO(ReintegrosMultiServicio.configApp.loggerApp).txMultiEfeHistoATxMultiEfe((TxmultifuncionalefectivoHisto)query.getSingleResult());
                }
                else {
                    reg = (Txmultifuncionalefectivo)query.getSingleResult();
                }
            }
        }
        catch (IllegalStateException ex) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        catch (Exception ex3) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "Error no conocido ", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
        return reg;
    }
    
    public void guardarReintegro(final Hostmultifuncional regHostAtm, final Txmultifuncionalefectivo regTxReintegro) throws EntityServicioExcepcion {
        final Reintegrosmultiefectivo newReintegro = new Reintegrosmultiefectivo();
        final ReintegrosmultiefectivoPK newReintegroPK = new ReintegrosmultiefectivoPK();
        try {
            newReintegroPK.setHCodigocajero(regHostAtm.getHostmultifuncionalPK().getCodigocajero());
            newReintegroPK.setHFechasistema(regHostAtm.getHostmultifuncionalPK().getFechasistema());
            newReintegroPK.setHTalon(regHostAtm.getHostmultifuncionalPK().getTalon());
            newReintegro.setReintegrosmultiefectivoPK(newReintegroPK);
            newReintegro.setHCodigooficina(regHostAtm.getCodigooficina());
            newReintegro.setHDatostarjeta(regHostAtm.getDatostarjeta());
            newReintegro.setHFecha(regHostAtm.getFecha());
            newReintegro.setHFiller(regHostAtm.getFiller());
            newReintegro.setHIndices(regHostAtm.getIndices());
            newReintegro.setHNumerocuenta(regHostAtm.getNumerocuenta());
            newReintegro.setHTipotarjeta(regHostAtm.getTipotarjeta());
            newReintegro.setHTipotransaccion(regHostAtm.getTipotransaccion());
            newReintegro.setHValor(Long.valueOf(regHostAtm.getValor() / 100L));
            newReintegro.setTCodigocajero(Integer.valueOf(regTxReintegro.getTxmultifuncionalefectivoPK().getCodigocajero()));
            newReintegro.setTFechacajero(regTxReintegro.getTxmultifuncionalefectivoPK().getFechacajero());
            newReintegro.setTTransaccionconsecutivo(regTxReintegro.getTransaccionconsecutivo());
            newReintegro.setEstadoreintegro(regTxReintegro.getEstado());
            newReintegro.setTCodigotransaccion(Integer.valueOf(regTxReintegro.getTxmultifuncionalefectivoPK().getCodigotx()));
            newReintegro.setTSecuencia(regTxReintegro.getSecuencia());
            newReintegro.setTNumerodecuentaconsignar(regTxReintegro.getNumerocuentaconsignar());
            newReintegro.setTTipocuenta(new Integer(regTxReintegro.getTipocuenta().toString()));
            newReintegro.setTValorconsignacion(regTxReintegro.getValorconsignacion());
            newReintegro.setEstadoreintegro(EstadoReintegro.INICIADO.getEstado());
            newReintegro.setValorajustado(regTxReintegro.getValorconsignacion());
            newReintegro.setTipocuentareintegro(new Integer(regTxReintegro.getTipocuenta().toString()));
            this.reintegrosServicio.adicionar(newReintegro);
        }
        catch (IllegalStateException ex) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ReintegrosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
}
