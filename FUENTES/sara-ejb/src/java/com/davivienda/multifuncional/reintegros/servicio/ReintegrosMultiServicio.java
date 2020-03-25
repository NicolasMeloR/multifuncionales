package com.davivienda.multifuncional.reintegros.servicio;

import com.davivienda.multifuncional.tablas.hostmultifuncional.servicio.HostMultifuncionalServicio;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.servicio.ReintegrosMultiEfectivoServicio;
import com.davivienda.sara.base.BaseEstadisticaServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.transaccion.SumatoriaTransaccionesHostBean;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.Cadena;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.dto.TxMultifuncionalEfeDTO;
import com.davivienda.sara.entitys.Hostmultifuncional;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.entitys.ReintegrosmultiefectivoPK;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.entitys.TxmultifuncionalefectivoHisto;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * TransaccionEstadisticaServicio - 1/09/2008 Descripción : Estadísticas de las
 * transacciones generadas por los cajeros Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReintegrosMultiServicio extends BaseEstadisticaServicio {

    TransaccionServicio transaccionServicio;
    private CajeroServicio cajeroServicio;
    HostMultifuncionalServicio hostAtmServicio;
    ReintegrosMultiEfectivoServicio reintegrosServicio;
    //variable que maneja el rango de diferencia minima para reinetgos
    //OJO VALIDAR SI ESTE RANGO SE GUARDA EN BASE  DE DATOS
    private long valorMinimoReintegros = 9000;
    private long valorMinimoReintegrosNegativo = -9000;
    List<String> binesBanagrario = new ArrayList<String>();

    public ReintegrosMultiServicio(EntityManager em) {
        super(em);
        transaccionServicio = new TransaccionServicio(em);
        cajeroServicio = new CajeroServicio(em);
        hostAtmServicio = new HostMultifuncionalServicio(em);
        reintegrosServicio = new ReintegrosMultiEfectivoServicio(em);

    }

    public Integer calcularReintegros(Calendar fecha, Date fechaHisto) {

        Integer regsProceso = 0;

        Collection<SumatoriaTransaccionesHostBean> regs = new ArrayList<SumatoriaTransaccionesHostBean>();
        try {

            Date fInicial = com.davivienda.utilidades.conversion.Fecha.getFechaInicioDia(fecha).getTime();
            Date fFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fecha.getTime());
            Collection<Cajero> cajeros = cajeroServicio.getCajerosActivosMulti();

            java.util.logging.Logger.getLogger("globalApp").info("Se inicia el proceso de Calcular reintegros " + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD));

            for (Cajero cajero : cajeros) {

                java.util.logging.Logger.getLogger("globalApp").info("Inicia Sumatoria Cajero :" + cajero.getCodigoCajero().toString());
                SumatoriaTransaccionesHostBean reg = new SumatoriaTransaccionesHostBean();
                reg = (getSumatoria(cajero.getCodigoCajero(), fInicial, fFin, fechaHisto));
                regsProceso = regsProceso + buscarReintegrosHost(reg.getCodigoCajero(), fInicial, fFin, fechaHisto);

            }

        } catch (Exception ex) {

            java.util.logging.Logger.getLogger("globalApp").info("Error Calculando reintegros " + ex.getMessage());
            regsProceso = -1;
        }

        java.util.logging.Logger.getLogger("globalApp").info("Fin del proceso de  Calculo de reintegros   para el dia " + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD) + "  reintegros encontrados : " + regsProceso);

        return regsProceso;
    }

    private SumatoriaTransaccionesHostBean getSumatoria(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion, IllegalArgumentException {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - getSumatoria fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal
                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla historico: " + TxmultifuncionalefectivoHisto.class.getSimpleName());
            nombreTabla = "TxmultifuncionalefectivoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla : " + Txmultifuncionalefectivo.class.getSimpleName());
            nombreTabla = "Txmultifuncionalefectivo";
        }

        //   Collection vectorRegs = null;
        List result = new ArrayList<Integer>();
        SumatoriaTransaccionesHostBean reg = new SumatoriaTransaccionesHostBean();
        Query query = null;
        String strQuery
                = "(select   sum(obj.valorconsignacion) as sumTx from " + nombreTabla + " obj "
                + " where obj.codigocajero= ? and  "
                + " obj.fechacajero (+) between ? and ?"
                + " and  obj.estado=0 ) "
                + " UNION ALL "
                + " (select   sum(obj.valor/100) as sumTx from Hostmultifuncional obj "
                + " where obj.codigocajero= ? and   obj.fecha (+) between ? and ?)";

        BigDecimal sumTransaccion = new BigDecimal(0);
        BigDecimal sumHost = new BigDecimal(0);

        if (codigoCajero == null || codigoCajero == 0) {
            throw new IllegalArgumentException("Se debe seleccionar un cajero");
        }

        if (fechaInicial == null) {
            fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser despues de la fecha Inicial");
        }
        try {
            query = em.createNativeQuery(strQuery);

            query.setParameter(1, codigoCajero);
            query.setParameter(2, fechaInicial).setParameter(3, fechaFinal);
            query.setParameter(4, codigoCajero);
            query.setParameter(5, fechaInicial).setParameter(6, fechaFinal);
            result = query.getResultList();

            if (result != null) {
                if (result.get(0).toString().contains("[null]") == false) {
                    sumTransaccion = BigDecimal.valueOf(Cadena.aLong(result.get(0).toString().replace("[", "").replace("]", "")));
                }
                if (result.get(1).toString().contains("[null]") == false) {
                    sumHost = BigDecimal.valueOf(Cadena.aLong(result.get(1).toString().replace("[", "").replace("]", "")));
                }

                reg.setSumTransaccion(sumTransaccion);
                reg.setSumHost(sumHost);
                reg.setDiferencia(sumHost.subtract(sumTransaccion));
                reg.setCodigoCajero(codigoCajero);
                reg.setFechaInicial(com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, FormatoFecha.AAAAMMDD));
                reg.setFechaFinal(com.davivienda.utilidades.conversion.Fecha.aCadena(fechaFinal, FormatoFecha.AAAAMMDD));
            }

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

        return reg;
    }

    private Integer buscarReintegrosHost(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {
        Txmultifuncionalefectivo regTxReintegro = null;
        Collection<Object[]> regsObject = null;
        Hostmultifuncional regHostAtm = new Hostmultifuncional();
        Integer regsProceso = 0;
        Integer talon;
        try {

            regsObject = getTransaccionTalonUnico(codigoCajero, fechaInicial, fechaFinal, fechaHisto);

            if (regsObject != null) {
                for (Object[] item : regsObject) {
                    talon = (Integer) item[1];
                    regHostAtm = hostAtmServicio.getReintegrosHost(codigoCajero, fechaInicial, fechaFinal, talon);

                    if (regHostAtm != null) {
                        if (!regHostAtm.getValor().equals(new Long(0))) {
                            regTxReintegro = getTransaccionReintegrosXTalon(codigoCajero, talon, fechaInicial, fechaFinal, fechaHisto);
                        }
                        if (regTxReintegro != null) {
                            guardarReintegro(regHostAtm, regTxReintegro);
                            regsProceso++;
                        }

                    }

                    //con el talon obtengo el registro en diario efectivo y luego lo busco en host                                                                                                            
                }
            }

        } catch (Exception ex) {

            java.util.logging.Logger.getLogger("globalApp").info("Error buscando reintegros  " + ex.getMessage());

        }

        return regsProceso;

    }

    public Collection<Object[]> getTransaccionTalonUnico(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - getTransaccionTalonUnico fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal
                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla historico: " + TxmultifuncionalefectivoHisto.class.getSimpleName());
            nombreTabla = "TxmultifuncionalefectivoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla : " + Txmultifuncionalefectivo.class.getSimpleName());
            nombreTabla = "Txmultifuncionalefectivo";
        }

        Object cadenaArray[] = new Object[2];
        Collection<Object[]> reg = null;
        try {
            Date fInicial = (fechaInicial != null) ? fechaInicial : com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            Date fFin = fechaFinal;
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla + ".TransaccionesTalonUnico");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);

                reg = query.getResultList();
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }

    public Txmultifuncionalefectivo getTransaccionReintegrosXTalon(Integer codigoCajero, Integer talon, Date fechaInicial, Date fechaFinal ,Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - getTransaccionTalonUnico fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal
                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla historico: " + TxmultifuncionalefectivoHisto.class.getSimpleName());
            nombreTabla = "TxmultifuncionalefectivoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiServicio - Consultando tabla : " + Txmultifuncionalefectivo.class.getSimpleName());
            nombreTabla = "Txmultifuncionalefectivo";
        }

        Txmultifuncionalefectivo reg = null;
        try {
            Date fInicial = (fechaInicial != null) ? fechaInicial : com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            Date fFin = fechaFinal;
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla + ".TransaccionReintegrosXTalon");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("talon", talon);

                if (tablaHisto) {
                    reg = new TxMultifuncionalEfeDTO(configApp.loggerApp).txMultiEfeHistoATxMultiEfe((TxmultifuncionalefectivoHisto) query.getSingleResult());
                } else {
                    reg = (Txmultifuncionalefectivo) query.getSingleResult();

                }

            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error no conocido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }

    public void guardarReintegro(Hostmultifuncional regHostAtm, Txmultifuncionalefectivo regTxReintegro) throws EntityServicioExcepcion {

        Reintegrosmultiefectivo newReintegro = new Reintegrosmultiefectivo();
        ReintegrosmultiefectivoPK newReintegroPK = new ReintegrosmultiefectivoPK();
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
            newReintegro.setHValor(regHostAtm.getValor() / 100);
            newReintegro.setTCodigocajero(regTxReintegro.getTxmultifuncionalefectivoPK().getCodigocajero());
            newReintegro.setTFechacajero(regTxReintegro.getTxmultifuncionalefectivoPK().getFechacajero());
            newReintegro.setTTransaccionconsecutivo(regTxReintegro.getTransaccionconsecutivo());
            newReintegro.setEstadoreintegro(regTxReintegro.getEstado());

            newReintegro.setTCodigotransaccion(regTxReintegro.getTxmultifuncionalefectivoPK().getCodigotx());
            newReintegro.setTSecuencia(regTxReintegro.getSecuencia());

            newReintegro.setTNumerodecuentaconsignar(regTxReintegro.getNumerocuentaconsignar());
            newReintegro.setTTipocuenta(new Integer(regTxReintegro.getTipocuenta().toString()));

            newReintegro.setTValorconsignacion(regTxReintegro.getValorconsignacion());

            newReintegro.setEstadoreintegro(EstadoReintegro.INICIADO.getEstado());
            newReintegro.setValorajustado(regTxReintegro.getValorconsignacion());
            newReintegro.setTipocuentareintegro(new Integer(regTxReintegro.getTipocuenta().toString()));

            reintegrosServicio.adicionar(newReintegro);

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }
    /*
   
    public Collection<Txmultifuncionalefectivo> getTransaccionReintegros(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer estado, Date fechaHisto) throws EntityServicioExcepcion {
        Collection<Txmultifuncionalefectivo> reg = null;
        try {
            Date fInicial = (fechaInicial != null) ? fechaInicial : com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            Date fFin = fechaFinal;
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("Txmultifuncionalefectivo.TransaccionBuscarReintegros");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("estado", estado);

                reg = query.getResultList();
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }

    private Integer buscarReintegros(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {
        Collection<Txmultifuncionalefectivo> regsTxReintegros = null;
        Collection<Hostmultifuncional> regsHostAtm = null;
        Hostmultifuncional regHostAtm = new Hostmultifuncional();
        Integer regsProceso = 0;
        try {
            //estado 66 retract
            regsTxReintegros = getTransaccionReintegros(codigoCajero, fechaInicial, fechaFinal, 66, fechaHisto);
            for (Txmultifuncionalefectivo regTxReintegro : regsTxReintegros) {
                regHostAtm = hostAtmServicio.getReintegrosHost(regTxReintegro.getTxmultifuncionalefectivoPK().getCodigocajero(), fechaInicial, fechaFinal, regTxReintegro.getTransaccionconsecutivo());

                //OJO VALIDAR CANTIDAD SOLICITADA TRANSACCION  regTxReintegro.getValorSolicitado() CONTRA VALOR HOST ANALIZAR CRITERIO
                if (regHostAtm != null) {
                    guardarReintegro(regHostAtm, regTxReintegro);
                    regsProceso++;
                }
            }
        } catch (Exception ex) {

            java.util.logging.Logger.getLogger("globalApp").info("Error buscando reintegros  " + ex.getMessage());

        }

        return regsProceso;

    }

    

    

    private Integer buscarReintegrosTimeOut(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer codigoOficina, Date fechaHisto) throws EntityServicioExcepcion {
        Collection<Txmultifuncionalefectivo> regsTxReintegros = null;
        Collection<Hostmultifuncional> regsHostAtm = null;
        Hostmultifuncional regHostAtm = new Hostmultifuncional();
        Integer regsProceso = 0;
        try {
            //estado 1 time Out
            regsTxReintegros = getTransaccionReintegros(codigoCajero, fechaInicial, fechaFinal, 1, fechaHisto);
            for (Txmultifuncionalefectivo regTxReintegro : regsTxReintegros) {
                regHostAtm = hostAtmServicio.getReintegrosHost(regTxReintegro.getTxmultifuncionalefectivoPK().getCodigocajero(), fechaInicial, fechaFinal, regTxReintegro.getTransaccionconsecutivo());

                //OJO VALIDAR CANTIDAD SOLICITADA TRANSACCION  regTxReintegro.getValorSolicitado() CONTRA VALOR HOST ANALIZAR CRITERIO
                if (regHostAtm == null) {
                    guardarReintegroTimeOut(regTxReintegro, codigoOficina);
                    regsProceso++;
                }
            }
        } catch (Exception ex) {

            java.util.logging.Logger.getLogger("globalApp").info("Error buscando reintegros  " + ex.getMessage());

        }

        return regsProceso;

    }

    public Collection<Txmultifuncionalefectivo> getTransaccionReintegrosTimeOut(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Date fechaHisto) throws EntityServicioExcepcion {
        Collection<Txmultifuncionalefectivo> reg = null;
        try {
            Date fInicial = (fechaInicial != null) ? fechaInicial : com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            Date fFin = fechaFinal;
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("Txmultifuncionalefectivo.TransaccionBuscReintegrosTimeO");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                query.setParameter("codigoCajero", cCajero);

                reg = query.getResultList();
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }

   

    public void guardarReintegroTimeOut(Txmultifuncionalefectivo regTxReintegro, Integer codigoOficina) throws EntityServicioExcepcion {

        Reintegrosmultiefectivo newReintegro = new Reintegrosmultiefectivo();
        ReintegrosmultiefectivoPK newReintegroPK = new ReintegrosmultiefectivoPK();
        try {
            newReintegroPK.setHCodigocajero(regTxReintegro.getTxmultifuncionalefectivoPK().getCodigocajero());
            newReintegroPK.setHFechasistema(regTxReintegro.getTxmultifuncionalefectivoPK().getFechacajero());
            newReintegroPK.setHTalon(regTxReintegro.getTransaccionconsecutivo());
            newReintegro.setReintegrosmultiefectivoPK(newReintegroPK);

            newReintegro.setHCodigooficina(codigoOficina);
            newReintegro.setHDatostarjeta(regTxReintegro.getNumerocuentaconsignar());
            newReintegro.setHFecha(regTxReintegro.getTxmultifuncionalefectivoPK().getFechacajero());
            newReintegro.setHFiller("0");
            newReintegro.setHIndices("0");
            newReintegro.setHNumerocuenta(regTxReintegro.getNumerocuentaconsignar().substring(3));
            newReintegro.setHTipotarjeta("0");
            newReintegro.setHTipotransaccion(new Integer(regTxReintegro.getTipocuenta().toString()));
            newReintegro.setHValor(regTxReintegro.getValorconsignacion());
            newReintegro.setTCodigocajero(regTxReintegro.getTxmultifuncionalefectivoPK().getCodigocajero());
            newReintegro.setTFechacajero(regTxReintegro.getTxmultifuncionalefectivoPK().getFechacajero());
            newReintegro.setTTransaccionconsecutivo(regTxReintegro.getTransaccionconsecutivo());
            newReintegro.setEstadoreintegro(regTxReintegro.getEstado());

            newReintegro.setTCodigotransaccion(regTxReintegro.getTxmultifuncionalefectivoPK().getCodigotx());
            newReintegro.setTSecuencia(regTxReintegro.getSecuencia());

            newReintegro.setTNumerodecuentaconsignar(regTxReintegro.getNumerocuentaconsignar().substring(3));
            newReintegro.setTTipocuenta(new Integer(regTxReintegro.getTipocuenta().toString()));

            newReintegro.setTValorconsignacion(regTxReintegro.getValorconsignacion());

            newReintegro.setEstadoreintegro(EstadoReintegro.INICIADO.getEstado());
            newReintegro.setValorajustado(regTxReintegro.getValorconsignacion());
            newReintegro.setTipocuentareintegro(new Integer(regTxReintegro.getTipocuenta().toString()));

            reintegrosServicio.adicionar(newReintegro);

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

    }

     */
}
