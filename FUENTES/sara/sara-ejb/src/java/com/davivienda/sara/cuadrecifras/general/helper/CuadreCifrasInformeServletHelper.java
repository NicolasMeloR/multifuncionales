// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.general.helper;

import com.davivienda.sara.cuadrecifras.general.bean.InformeAyerBean;
import com.davivienda.sara.entitys.ProvisionHost;
import com.davivienda.sara.entitys.Reintegros;
import java.util.Date;
import com.davivienda.sara.entitys.InformeDiferenciasPK;
import com.davivienda.sara.entitys.InformeDiferencias;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import com.davivienda.utilidades.conversion.Numero;
import com.davivienda.sara.cuadrecifras.general.bean.DatosMovimientoCajeroBean;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.math.BigInteger;
import com.davivienda.sara.constantes.TipoMovimientoCuadre;
import java.util.HashMap;
import java.util.ArrayList;
import com.davivienda.sara.entitys.MovimientoCuadre;
import javax.ejb.EJBException;
import com.davivienda.utilidades.conversion.JSon;
import com.davivienda.sara.constantes.CodigoError;
import java.util.logging.Level;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.cuadrecifras.general.bean.MovimientoCuadreCifrasBean;
import java.util.Collection;
import java.util.Calendar;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.tablas.provisionhost.session.ProvisionHostLocal;
import java.util.logging.Logger;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.cuadrecifras.session.InformeDiferenciasSessionLocal;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasSessionLocal;

public class CuadreCifrasInformeServletHelper implements CuadreCifrasHelperInterface
{
    private long limiteDiferencia;
    private long limiteDiferenciaMax;
    private CuadreCifrasSessionLocal session;
    private InformeDiferenciasSessionLocal informeSession;
    private String respuestaJSon;
    private ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSession;
    private Logger loggerApp;
    private ProvisionHostLocal provisionHostsession;
    private ReintegrosSessionLocal reintegrosSession;
    
    public CuadreCifrasInformeServletHelper(final CuadreCifrasSessionLocal session, final ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSession) {
        this.limiteDiferencia = -1000L;
        this.limiteDiferenciaMax = 1000L;
        this.session = session;
        this.procesoCuadreCifrasSession = procesoCuadreCifrasSession;
    }
    
    public CuadreCifrasInformeServletHelper(final CuadreCifrasSessionLocal session, final ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSession, final InformeDiferenciasSessionLocal informeSession, final ProvisionHostLocal provisionHostsession, final ReintegrosSessionLocal reintegrosSession) {
        this.limiteDiferencia = -1000L;
        this.limiteDiferenciaMax = 1000L;
        this.session = session;
        this.informeSession = informeSession;
        this.procesoCuadreCifrasSession = procesoCuadreCifrasSession;
        this.provisionHostsession = provisionHostsession;
        this.reintegrosSession = reintegrosSession;
    }
    
    public String obtenerDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Collection<MovimientoCuadreCifrasBean> obtenerDatosCollectionCC(final Calendar fechaInicial, Integer codigoOcca) throws IllegalAccessException {
        (this.loggerApp = SaraConfig.obtenerInstancia().loggerApp).log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC Inicio");
        this.respuestaJSon = "";
        String respuesta = "";
        final Collection<MovimientoCuadre> items = null;
        Collection<MovimientoCuadreCifrasBean> itemsFormateados = null;
        final ArrayList cajerosDiferencia = null;
        try {
            Calendar fechaFinal = null;
            Integer codigoCajero = null;
            try {
                fechaFinal = fechaInicial;
            }
            catch (IllegalArgumentException ex4) {
                fechaFinal = fechaInicial;
            }
            this.loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC codigoOcca: " + codigoOcca);
            if (codigoOcca == null) {
                codigoOcca = -1;
            }
            else if (codigoOcca == 0) {
                codigoOcca = -1;
            }
            try {
                codigoCajero = 9999;
                this.loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC codigoCajero: " + codigoCajero);
                this.loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC fechaInicial: " + fechaInicial.getTime());
                final Collection<MovimientoCuadre> items2 = this.session.getMovimientoCuadreCifrasOccaMostrar(codigoCajero, codigoOcca, fechaInicial, fechaInicial);
                this.loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC movimientos encontrados: " + items2.size());
                itemsFormateados = this.getBeansMovimientoCuadreCifras(items2);
                this.loggerApp.log(Level.INFO, "obtenerDatosCollectionCC itemsFormateados size" + itemsFormateados.size());
            }
            catch (EJBException ex) {
                this.loggerApp.log(Level.SEVERE, ex.getMessage(), (Throwable)ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    this.respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    this.respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    this.respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "No hay registros que cumplan con los criterios de la consulta");
                }
            }
            catch (Exception ex2) {
                this.loggerApp.log(Level.SEVERE, ex2.getMessage(), ex2);
                this.respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
            }
        }
        catch (IllegalArgumentException ex3) {
            this.loggerApp.log(Level.SEVERE, ex3.getMessage(), ex3);
            this.respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
        }
        if (items == null || this.respuestaJSon.length() > 1) {
            respuesta = this.respuestaJSon;
        }
        this.loggerApp.log(Level.INFO, "obtenerDatosCollectionCC respuesta " + respuesta);
        this.loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC fin");
        return itemsFormateados;
    }
    
    private Collection<MovimientoCuadreCifrasBean> getBeansMovimientoCuadreCifras(final Collection<MovimientoCuadre> regs) {
        final Collection<MovimientoCuadreCifrasBean> beans = new ArrayList<MovimientoCuadreCifrasBean>();
        final Map<Integer, MovimientoCuadreCifrasBean> mapFechas = new HashMap<Integer, MovimientoCuadreCifrasBean>();
        long i = 1L;
        for (final MovimientoCuadre movimientoCuadre : regs) {
            if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.DIFERENCIAS.codigo) && movimientoCuadre.getValorMovimiento() != 0L && !mapFechas.containsKey(movimientoCuadre.getCajero().getCodigoCajero())) {
                final MovimientoCuadreCifrasBean b1 = new MovimientoCuadreCifrasBean();
                mapFechas.put(movimientoCuadre.getCajero().getCodigoCajero(), b1);
                beans.add(b1);
                b1.id = BigInteger.valueOf(i);
                ++i;
            }
        }
        for (final MovimientoCuadre movimientoCuadre : regs) {
            if (mapFechas.get(movimientoCuadre.getCajero().getCodigoCajero()) != null) {
                final MovimientoCuadreCifrasBean b1 = mapFechas.get(movimientoCuadre.getCajero().getCodigoCajero());
                b1.codigoCajero = movimientoCuadre.getCajero().getCodigoCajero();
                b1.nombreCajero = movimientoCuadre.getCajero().getNombre();
                b1.fecha = Fecha.aCadena(movimientoCuadre.getFecha(), FormatoFecha.DEFECTO_SEPARADOR_GUION.formato);
                b1.codigoOcca = movimientoCuadre.getCajero().getOcca().getCodigoOcca();
                final DatosMovimientoCajeroBean dc1 = new DatosMovimientoCajeroBean();
                dc1.descripcion = movimientoCuadre.getTipoMovimientoCuadre().getDescripcion();
                dc1.valor = Numero.aMoneda(movimientoCuadre.getValorMovimiento());
                dc1.observacion = movimientoCuadre.getObservacion();
                dc1.idRegistro = movimientoCuadre.getIdMovimientoCuadre();
                dc1.idUsuarioObservacion = movimientoCuadre.getIdUsuarioObservacion();
                dc1.fecha = Fecha.aCadena(movimientoCuadre.getFecha(), FormatoFecha.AAAAMMDD);
                if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre() <= 199) {
                    final StringBuffer sb = new StringBuffer();
                    sb.append(movimientoCuadre.getDenominacion1()).append(':');
                    sb.append(movimientoCuadre.getBilletes1()).append(" - ");
                    sb.append(movimientoCuadre.getDenominacion2()).append(':');
                    sb.append(movimientoCuadre.getBilletes2()).append(" - ");
                    sb.append(movimientoCuadre.getDenominacion3()).append(':');
                    sb.append(movimientoCuadre.getBilletes3()).append(" - ");
                    sb.append(movimientoCuadre.getDenominacion4()).append(':');
                    sb.append(movimientoCuadre.getBilletes4());
                    dc1.contadores = sb.toString();
                    b1.datosCajero.add(dc1);
                }
                else {
                    if (movimientoCuadre.getConceptoMovimientoCuadre() != null && movimientoCuadre.getConceptoMovimientoCuadre().getCodigoConcepto() != 0) {
                        dc1.concepto = movimientoCuadre.getConceptoMovimientoCuadre().getCodigoConcepto();
                        dc1.descripcion = dc1.descripcion + " - " + movimientoCuadre.getConceptoMovimientoCuadre().getDescripcion();
                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre() == 231) {
                        continue;
                    }
                    b1.datosLinea.add(dc1);
                }
            }
        }
        return beans;
    }
    
    public void procesoInformeDescuadreOld(final Calendar fechaInicial, final Integer codigoOcca) throws IllegalAccessException {
        (this.loggerApp = SaraConfig.obtenerInstancia().loggerApp).log(Level.INFO, "CuadreCifrasInformeServletHelper procesoInformeDescruadre Inicio");
        try {
            final Collection<MovimientoCuadreCifrasBean> itemsFormateados = this.obtenerDatosCollectionCC(fechaInicial, codigoOcca);
            int itemsDescuadrados = 0;
            if (itemsFormateados.size() > 0) {
                this.loggerApp.info("Susceptibles de reintegro: " + itemsFormateados.size());
                for (final MovimientoCuadreCifrasBean mov : itemsFormateados) {
                    final Collection<DatosMovimientoCajeroBean> datosCajero = mov.getDatosCajero();
                    final Collection<DatosMovimientoCajeroBean> datosLinea = mov.getDatosLinea();
                    boolean provDiaSgteMaquina = false;
                    boolean provDiaSgteLinea = false;
                    boolean diferencias = false;
                    BigDecimal maquina = new BigDecimal(0);
                    BigDecimal linea = new BigDecimal(0);
                    BigDecimal dif = new BigDecimal(0);
                    for (final DatosMovimientoCajeroBean cajeroBean : datosCajero) {
                        if (cajeroBean.getDescripcion().equals("Provisi\u00f3n D\u00eda Siguiente")) {
                            maquina = Numero.aBigDecimal(cajeroBean.getValor(), "$###,###,###.##");
                            if (maquina.compareTo(BigDecimal.ZERO) > 0) {
                                provDiaSgteMaquina = true;
                                break;
                            }
                            continue;
                        }
                    }
                    for (final DatosMovimientoCajeroBean lineaBean : datosLinea) {
                        if (lineaBean.getDescripcion().equals("Provisi\u00f3n Dia Siguiente IDO")) {
                            linea = Numero.aBigDecimal(lineaBean.getValor(), "$###,###,###.##");
                            if (linea.compareTo(BigDecimal.ZERO) > 0) {
                                provDiaSgteLinea = true;
                            }
                        }
                        else if (lineaBean.getDescripcion().equals("Diferencias")) {
                            dif = Numero.aBigDecimal(lineaBean.getValor(), "$###,###,###.##");
                            if (dif.compareTo(BigDecimal.ZERO) != 0) {
                                diferencias = true;
                            }
                        }
                        if (provDiaSgteLinea && diferencias) {
                            break;
                        }
                    }
                    if (provDiaSgteMaquina && provDiaSgteLinea && diferencias) {
                        ++itemsDescuadrados;
                        final Date d = Fecha.aDate(mov.getFecha(), FormatoFecha.DEFECTO_SEPARADOR_GUION.formato);
                        if (null == d) {
                            continue;
                        }
                        final InformeDiferencias pEntity = new InformeDiferencias();
                        final InformeDiferenciasPK diferenciasPK = new InformeDiferenciasPK(mov.getCodigoCajero(), d);
                        pEntity.setInformeDiferenciasPK(diferenciasPK);
                        pEntity.setCodigoOcca(mov.getCodigoOcca());
                        pEntity.setValorMaquina(Long.valueOf(maquina.longValue()));
                        pEntity.setValorLinea(Long.valueOf(linea.longValue()));
                        pEntity.setValorDiferencias(Long.valueOf(dif.longValue()));
                        pEntity.setFechaSistema(new Date());
                        pEntity.setAplicado("NO");
                        pEntity.setAumento(Long.valueOf(0L));
                        pEntity.setDisminucion(Long.valueOf(0L));
                        pEntity.setFaltante(Long.valueOf(0L));
                        pEntity.setSobrante(Long.valueOf(0L));
                        this.informeSession.adicionar(pEntity);
                    }
                }
                this.loggerApp.info("Aceptados con descuadre: " + itemsDescuadrados);
            }
            else {
                this.loggerApp.info("No se encontraron reintegros: " + itemsFormateados.size());
            }
        }
        catch (Exception ex) {
            this.loggerApp.log(Level.SEVERE, "CuadreCifrasInformeServletHelper-Registros susceptibles de reintegro Error ", ex);
        }
        this.loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper procesoInformeDescruadre Fin");
    }
    
    @Override
    public void procesoInformeDescuadre(final Calendar fechaInicial, final Integer codigoOcca) throws IllegalAccessException {
        (this.loggerApp = SaraConfig.obtenerInstancia().loggerApp).log(Level.INFO, "CuadreCifrasInformeServletHelper procesoInformeDescruadre Inicio");
        try {
            final Date fechaHisto = SaraConfig.obtenerInstancia().FECHA_HISTORICAS_CONSULTA;
            final Collection<MovimientoCuadre> items = this.session.getMovimientoCuadreCifras(9999, fechaInicial, fechaInicial);
            final Collection<ProvisionHost> itemsProvision = this.provisionHostsession.getProvisionHostRangoFecha(fechaInicial, Fecha.getFechaFinDia(fechaInicial));
            final Collection<InformeAyerBean> itemsProvisionAyer = this.getCollectionInformeCuadreAyer(items, itemsProvision, Fecha.aCadena(fechaInicial, FormatoFecha.AAAAMMDD), -1);
            final Collection<InformeDiferencias> reporte = this.generarResumenCuadreDiario(itemsProvisionAyer, items, -1);
            this.loggerApp.info("Susceptibles de reintegro: " + reporte.size());
            for (final InformeDiferencias pEntity : reporte) {
                this.informeSession.adicionar(pEntity);
                final Collection<Reintegros> reintegros = this.reintegrosSession.getReintegros(fechaInicial.getTime(), pEntity.getInformeDiferenciasPK().getCodigoCajero(), fechaHisto);
                if (reintegros != null && !reintegros.isEmpty()) {
                    for (final Reintegros reintegro : reintegros) {
                        reintegro.setDifeDescuadre("Y");
                        this.reintegrosSession.actualizar(reintegro);
                    }
                }
            }
            this.reintegrosSession.actualizarPersistencia();
        }
        catch (Exception ex) {
            this.loggerApp.log(Level.SEVERE, "CuadreCifrasInformeServletHelper-Registros susceptibles de reintegro Error ", ex);
        }
        this.loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper procesoInformeDescruadre Fin");
    }
    
    private Collection<InformeAyerBean> getCollectionInformeCuadreAyer(final Collection<MovimientoCuadre> regs, final Collection<ProvisionHost> itemsProvision, final String fechaInicial, final Integer codigoOcca) {
        final Collection<InformeAyerBean> informes = new ArrayList<InformeAyerBean>();
        final Collection<InformeAyerBean> informesR = new ArrayList<InformeAyerBean>();
        final Map<Integer, Long> mapCajeroProvision = new HashMap<Integer, Long>();
        final Map<Integer, Long> mapCajeroDiaSgteIdo = new HashMap<Integer, Long>();
        Integer codigoCajero = 0;
        Integer codigoOccaRegistro = 0;
        Integer codigoOccaCompara = 0;
        String nombreOcca = "";
        boolean primerCodigo = false;
        long pagado = 0L;
        long efectivoAtm = 0L;
        long provisionhost = 0L;
        long aumentoProvLinea = 0L;
        long aumentoProvIDO = 0L;
        long disminucionProvLinea = 0L;
        long disminucionProvIDO = 0L;
        long provDiaSgteIDO = 0L;
        long provisionAyer = -1L;
        long faltanteIDO = 0L;
        long provisionAnteriorAux = -1L;
        long provisionDiaSgteAux = -1L;
        long sumaSinPagadoIdo = 0L;
        if (regs != null) {
            for (final MovimientoCuadre movimientoCuadre : regs) {
                codigoOccaCompara = movimientoCuadre.getCajero().getOcca().getCodigoOcca();
                if (codigoOccaCompara.compareTo(codigoOcca) == 0 || codigoOcca == -1) {
                    if (!primerCodigo) {
                        codigoCajero = movimientoCuadre.getCajero().getCodigoCajero();
                        primerCodigo = true;
                    }
                    if (codigoCajero.compareTo(movimientoCuadre.getCajero().getCodigoCajero()) != 0) {
                        final InformeAyerBean informe = new InformeAyerBean();
                        informe.setCodigoCajero(codigoCajero);
                        informe.setCodigoOcca(codigoOccaRegistro);
                        informe.setNombreOcca(nombreOcca);
                        informe.setpagado(Long.valueOf(pagado));
                        informe.setefectivoAtm(Long.valueOf(efectivoAtm));
                        informe.setProvisionDiaAnterior(Long.valueOf(provisionAnteriorAux));
                        informe.setProvisionDiaSig(Long.valueOf(provisionDiaSgteAux));
                        informe.setSumaSinPagadoIdo(Long.valueOf(sumaSinPagadoIdo));
                        informe.setProvisionAyer(Long.valueOf(provisionAyer));
                        informes.add(informe);
                        pagado = 0L;
                        efectivoAtm = 0L;
                        provisionAnteriorAux = -1L;
                        provisionDiaSgteAux = -1L;
                        sumaSinPagadoIdo = 0L;
                        provisionAyer = -1L;
                    }
                    codigoCajero = movimientoCuadre.getCajero().getCodigoCajero();
                    codigoOccaRegistro = movimientoCuadre.getCajero().getOcca().getCodigoOcca();
                    nombreOcca = movimientoCuadre.getCajero().getOcca().getNombre();
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.PAGADO_IDO.codigo)) {
                        pagado += movimientoCuadre.getValorMovimiento();
                        sumaSinPagadoIdo -= movimientoCuadre.getValorMovimiento();
                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.DONACIONES.codigo)) {
                        pagado -= movimientoCuadre.getValorMovimiento();
                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE_IDO.codigo)) {
                        efectivoAtm = movimientoCuadre.getValorMovimiento();
                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.PROVISION_DIA_ANTERIOR.codigo)) {
                        provisionAnteriorAux = movimientoCuadre.getValorMovimiento();
                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE.codigo)) {
                        provisionDiaSgteAux = movimientoCuadre.getValorMovimiento();
                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.PROVISION_AYER.codigo)) {
                        provisionAyer = movimientoCuadre.getValorMovimiento();
                    }
                    sumaSinPagadoIdo += movimientoCuadre.getValorMovimiento();
                }
            }
            final InformeAyerBean informe2 = new InformeAyerBean();
            informe2.setCodigoCajero(codigoCajero);
            informe2.setCodigoOcca(codigoOccaRegistro);
            informe2.setNombreOcca(nombreOcca);
            informe2.setpagado(Long.valueOf(pagado));
            informe2.setefectivoAtm(Long.valueOf(efectivoAtm));
            informe2.setProvisionDiaAnterior(Long.valueOf(provisionAnteriorAux));
            informe2.setProvisionDiaSig(Long.valueOf(provisionDiaSgteAux));
            informe2.setSumaSinPagadoIdo(Long.valueOf(sumaSinPagadoIdo));
            informe2.setProvisionAyer(Long.valueOf(provisionAyer));
            informes.add(informe2);
        }
        primerCodigo = false;
        if (itemsProvision != null) {
            for (final ProvisionHost provisionHost : itemsProvision) {
                codigoOccaCompara = provisionHost.getCajero().getOcca().getCodigoOcca();
                if (codigoOccaCompara.compareTo(codigoOcca) == 0 || codigoOcca == -1) {
                    if (!primerCodigo) {
                        codigoCajero = provisionHost.getCajero().getCodigoCajero();
                        primerCodigo = true;
                    }
                    if (codigoCajero.compareTo(provisionHost.getCajero().getCodigoCajero()) != 0) {
                        if (disminucionProvLinea != 0L) {
                            disminucionProvIDO = disminucionProvLinea;
                        }
                        if (aumentoProvLinea != 0L) {
                            aumentoProvIDO = aumentoProvLinea;
                        }
                        provisionhost += aumentoProvIDO;
                        provisionhost -= disminucionProvIDO;
                        if (provDiaSgteIDO == faltanteIDO) {
                            provisionhost -= provDiaSgteIDO;
                        }
                        if (!mapCajeroProvision.containsKey(codigoCajero)) {
                            mapCajeroProvision.put(codigoCajero, provisionhost);
                            mapCajeroDiaSgteIdo.put(codigoCajero, provDiaSgteIDO);
                        }
                        provisionhost = 0L;
                        aumentoProvLinea = 0L;
                        aumentoProvIDO = 0L;
                        disminucionProvLinea = 0L;
                        disminucionProvIDO = 0L;
                        provDiaSgteIDO = 0L;
                        faltanteIDO = 0L;
                    }
                    codigoCajero = provisionHost.getCajero().getCodigoCajero();
                    if (provisionHost.getProvisionHostPK().getMotivo() == 75 && provisionHost.getProvisionHostPK().getTipo() == 99) {
                        provDiaSgteIDO = provisionHost.getValor();
                    }
                    if (provisionHost.getProvisionHostPK().getMotivo() == 35 && provisionHost.getProvisionHostPK().getTipo() == 99) {
                        faltanteIDO = provisionHost.getValor();
                    }
                    if (provisionHost.getProvisionHostPK().getMotivo() == 112 || provisionHost.getProvisionHostPK().getMotivo() == 70) {
                        if (provisionHost.getProvisionHostPK().getMotivo() == 112) {
                            if (provisionHost.getProvisionHostPK().getTipo() == 99) {
                                disminucionProvIDO += provisionHost.getValor();
                            }
                            else {
                                aumentoProvIDO += provisionHost.getValor();
                            }
                        }
                        else if (provisionHost.getProvisionHostPK().getTipo() == 99) {
                            disminucionProvLinea += provisionHost.getValor();
                        }
                        else {
                            aumentoProvLinea += provisionHost.getValor();
                        }
                    }
                    else if (provisionHost.getProvisionHostPK().getMotivo() == 35 && provisionHost.getProvisionHostPK().getTipo() == 99) {
                        provisionhost -= provisionHost.getValor();
                    }
                    else {
                        provisionhost += provisionHost.getValor();
                    }
                }
            }
        }
        long auxnumero = 0L;
        boolean tieneProvision = false;
        for (final InformeAyerBean informesRecorre : informes) {
            InformeAyerBean informe3 = new InformeAyerBean();
            informe3 = informesRecorre;
            tieneProvision = false;
            if (mapCajeroProvision.get(informesRecorre.getCodigoCajero()) != null) {
                tieneProvision = true;
                if (mapCajeroProvision.get(informesRecorre.getCodigoCajero()) == 0L) {
                    informe3.setefectivoAtm(informe3.getNumericopagado());
                }
                else if (mapCajeroProvision.get(informesRecorre.getCodigoCajero()) < 0L) {
                    auxnumero = mapCajeroProvision.get(informesRecorre.getCodigoCajero()) * -1L;
                    informe3.setProvisionEfectivo(Long.valueOf(auxnumero));
                    auxnumero -= informe3.getNumericopagado();
                    if (auxnumero < 0L) {
                        auxnumero *= -1L;
                    }
                    informe3.setefectivoAtm(Long.valueOf(auxnumero));
                    auxnumero = 0L;
                }
                else if (informe3.getNumericoefectivoAtm() == 0L) {
                    informe3.setProvisionEfectivo(informe3.getNumericopagado());
                }
                else {
                    informe3.setProvisionEfectivo(Long.valueOf(mapCajeroProvision.get(informesRecorre.getCodigoCajero())));
                }
                if (informe3.getNumericoefectivoAtm() - (mapCajeroProvision.get(informesRecorre.getCodigoCajero()) - informe3.getNumericopagado()) < 0L) {
                    informe3.setProvisionEfectivo(Long.valueOf(mapCajeroProvision.get(informesRecorre.getCodigoCajero()) - mapCajeroDiaSgteIdo.get(informesRecorre.getCodigoCajero())));
                }
            }
            else if (informe3.getNumericoefectivoAtm().equals(new Long(0L))) {
                informe3.setProvisionEfectivo(new Long(0L));
                informe3.setefectivoAtm(informe3.getNumericopagado());
            }
            else {
                informe3.setProvisionEfectivo(Long.valueOf(informe3.getNumericopagado() + informe3.getNumericoefectivoAtm()));
            }
            if (informe3.getNumericopagado().equals(informe3.getNumericoProvisionEfectivo()) && informe3.getProvisionDiaSigIdo().equals(new Long(0L)) && (!informe3.getProvisionDiaAnterior().equals(informe3.getProvisionDiaSig()) || informe3.getSumaSinPagadoIdo().equals(new Long(0L))) && !tieneProvision) {
                informe3.setProvisionEfectivo(new Long(0L));
                informe3.setefectivoAtm(informe3.getNumericopagado());
            }
            informesR.add(informe3);
        }
        return informesR;
    }
    
    private Collection<InformeDiferencias> generarResumenCuadreDiario(final Collection<InformeAyerBean> itemsProvisionAyer, final Collection<MovimientoCuadre> regs, final Integer codigoOcca) {
        final String respuesta = "";
        Integer tipoMovimientoCuadre = 0;
        Integer codigoCajero = 0;
        Integer codigoCajeroOcca = 0;
        Date fecha = null;
        boolean primerCodigo = false;
        long provisionAnterior = 0L;
        long provisionInicialReal = 0L;
        long diferenciaSobrante = 0L;
        long diferenciaTransportadora = 0L;
        long provisionReal = 0L;
        long provisionDiaSgteIdo = 0L;
        long dispensado = 0L;
        long pagadoIdo = 0L;
        long donaciones = 0L;
        long provisionDiaSgte = 0L;
        long diferenciadiasgte = 0L;
        long sumaRevProvision = 0L;
        long dispensadoContadores = 0L;
        long provisionDiaSgteAux = -1L;
        long provisionDiaIdoSgteAux = -1L;
        long dispensadoAux = -1L;
        long provisionAnteriorAux = -1L;
        Integer codigoOccaRegistro = 0;
        String observacion = "";
        final Map<Integer, Long> mapCajeroEfectivoAtm = new HashMap<Integer, Long>();
        final Collection<InformeDiferencias> reporte = new ArrayList<InformeDiferencias>();
        if (itemsProvisionAyer != null) {
            for (final InformeAyerBean item : itemsProvisionAyer) {
                if (!mapCajeroEfectivoAtm.containsKey(item.getCodigoCajero())) {
                    mapCajeroEfectivoAtm.put(item.getCodigoCajero(), item.getNumericoefectivoAtm());
                }
            }
        }
        if (regs != null) {
            boolean imprime = false;
            try {
                for (final MovimientoCuadre item2 : regs) {
                    codigoOccaRegistro = item2.getCajero().getOcca().getCodigoOcca();
                    if (codigoOccaRegistro.compareTo(codigoOcca) == 0 || codigoOcca == -1) {
                        if (!primerCodigo) {
                            fecha = item2.getFecha();
                            codigoCajero = item2.getCajero().getCodigoCajero();
                            codigoCajeroOcca = item2.getCajero().getOcca().getCodigoOcca();
                            primerCodigo = true;
                        }
                        if (codigoCajero.compareTo(item2.getCajero().getCodigoCajero()) != 0) {
                            diferenciaTransportadora = provisionReal - provisionDiaSgteIdo;
                            diferenciaSobrante = dispensado - (pagadoIdo - donaciones);
                            if (provisionAnteriorAux == 0L && provisionDiaSgteAux == 0L && dispensadoAux == 0L && provisionDiaIdoSgteAux == -1L) {
                                provisionDiaSgteIdo = pagadoIdo;
                            }
                            if (provisionAnteriorAux == provisionDiaSgteAux && dispensadoAux == 0L) {
                                sumaRevProvision = provisionAnteriorAux + provisionDiaSgteAux;
                                provisionDiaSgteAux = -1L;
                                dispensadoAux = -1L;
                                provisionAnteriorAux = -1L;
                            }
                            if (provisionDiaSgte == 0L) {
                                observacion = "Cajero No Corto " + observacion;
                            }
                            if (mapCajeroEfectivoAtm.get(codigoCajero) != null) {
                                provisionDiaSgteIdo = mapCajeroEfectivoAtm.get(codigoCajero);
                            }
                            diferenciadiasgte = provisionDiaSgte - provisionDiaSgteIdo;
                            if (diferenciadiasgte != 0L) {
                                if (provisionDiaSgte == provisionDiaSgteIdo) {
                                    provisionDiaSgte = provisionDiaSgteIdo - diferenciadiasgte;
                                }
                                final InformeDiferencias pEntity = new InformeDiferencias();
                                final InformeDiferenciasPK diferenciasPK = new InformeDiferenciasPK(codigoCajero, item2.getFecha());
                                pEntity.setInformeDiferenciasPK(diferenciasPK);
                                pEntity.setCodigoOcca(codigoCajeroOcca);
                                pEntity.setValorMaquina(Long.valueOf(provisionDiaSgte));
                                pEntity.setValorLinea(Long.valueOf(provisionDiaSgteIdo));
                                pEntity.setValorDiferencias(Long.valueOf(diferenciadiasgte));
                                pEntity.setFechaSistema(new Date());
                                pEntity.setAplicado("NO");
                                pEntity.setAumento(Long.valueOf(0L));
                                pEntity.setDisminucion(Long.valueOf(0L));
                                pEntity.setFaltante(Long.valueOf(0L));
                                pEntity.setSobrante(Long.valueOf(0L));
                                if (diferenciadiasgte < this.limiteDiferencia) {
                                    imprime = true;
                                }
                                else {
                                    if (diferenciadiasgte > 0L) {
                                        imprime = true;
                                    }
                                    if (sumaRevProvision > 0L) {
                                        sumaRevProvision = 0L;
                                        imprime = true;
                                    }
                                    if (diferenciadiasgte < this.limiteDiferenciaMax) {
                                        imprime = false;
                                    }
                                }
                                if (imprime) {
                                    reporte.add(pEntity);
                                    imprime = false;
                                }
                            }
                            provisionAnterior = 0L;
                            provisionInicialReal = 0L;
                            diferenciaSobrante = 0L;
                            diferenciaTransportadora = 0L;
                            provisionReal = 0L;
                            provisionDiaSgteIdo = 0L;
                            dispensado = 0L;
                            pagadoIdo = 0L;
                            donaciones = 0L;
                            provisionDiaSgte = 0L;
                            diferenciadiasgte = 0L;
                            provisionDiaSgteAux = -1L;
                            provisionDiaIdoSgteAux = -1L;
                            dispensadoAux = -1L;
                            provisionAnteriorAux = -1L;
                            observacion = "";
                            dispensadoContadores = 0L;
                        }
                        tipoMovimientoCuadre = item2.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre();
                        codigoCajero = item2.getCajero().getCodigoCajero();
                        codigoCajeroOcca = item2.getCajero().getOcca().getCodigoOcca();
                        TipoMovimientoCuadre consultaAjuste = TipoMovimientoCuadre.AJUSTE_AUMENTO_PROVISION;
                        consultaAjuste = TipoMovimientoCuadre.getTipoMovimientoCuadre((int)tipoMovimientoCuadre);
                        switch (consultaAjuste) {
                            case PROVISION_DIA_ANTERIOR: {
                                provisionAnterior = (provisionAnteriorAux = item2.getValorMovimiento());
                                continue;
                            }
                            case PROVISION_INICIAL_REAL: {
                                provisionInicialReal = item2.getValorMovimiento();
                                continue;
                            }
                            case PROVISION_DIA_SIGUIENTE_REAL: {
                                provisionReal = item2.getValorMovimiento();
                                if (item2.getObservacion() != null && !item2.getObservacion().equals("null")) {
                                    observacion = observacion + " " + item2.getObservacion();
                                    continue;
                                }
                                continue;
                            }
                            case PROVISION_DIA_SIGUIENTE_IDO: {
                                provisionDiaSgteIdo = (provisionDiaIdoSgteAux = item2.getValorMovimiento());
                                if (item2.getObservacion() != null && !item2.getObservacion().equals("null")) {
                                    observacion = observacion + " " + item2.getObservacion();
                                    continue;
                                }
                                continue;
                            }
                            case DISPENSADO: {
                                dispensado = (dispensadoAux = item2.getValorMovimiento());
                                continue;
                            }
                            case PAGADO_IDO: {
                                pagadoIdo = item2.getValorMovimiento();
                                if (item2.getObservacion() != null && !item2.getObservacion().equals("null")) {
                                    observacion = observacion + " " + item2.getObservacion();
                                    continue;
                                }
                                continue;
                            }
                            case DONACIONES: {
                                donaciones = item2.getValorMovimiento();
                                continue;
                            }
                            case PROVISION_DIA_SIGUIENTE: {
                                provisionDiaSgte = (provisionDiaSgteAux = item2.getValorMovimiento());
                                continue;
                            }
                            case AJUSTE_AUMENTO_PROVISION: {
                                if (item2.getObservacion() != null && !item2.getObservacion().equals("null")) {
                                    observacion = observacion + " " + item2.getObservacion();
                                    continue;
                                }
                                continue;
                            }
                            case AJUSTE_DISMINUCION_PROVISION: {
                                if (item2.getObservacion() != null && !item2.getObservacion().equals("null")) {
                                    observacion = observacion + " " + item2.getObservacion();
                                    continue;
                                }
                                continue;
                            }
                            case AJUSTE_SOBRANTE: {
                                if (item2.getObservacion() != null && !item2.getObservacion().equals("null")) {
                                    observacion = observacion + " " + item2.getObservacion();
                                    continue;
                                }
                                continue;
                            }
                            case AJUSTE_FALTANTE: {
                                if (item2.getObservacion() != null && !item2.getObservacion().equals("null")) {
                                    observacion = observacion + " " + item2.getObservacion();
                                    continue;
                                }
                                continue;
                            }
                            case DIFERENCIAS: {
                                if (item2.getObservacion() != null && !item2.getObservacion().equals("null")) {
                                    observacion = observacion + " " + item2.getObservacion();
                                    continue;
                                }
                                continue;
                            }
                        }
                    }
                }
                diferenciaTransportadora = provisionReal - provisionDiaSgteIdo;
                diferenciaSobrante = dispensado - (pagadoIdo - donaciones);
                if (provisionAnteriorAux == 0L && provisionDiaSgteAux == 0L && dispensadoAux == 0L && provisionDiaIdoSgteAux == -1L) {
                    provisionDiaSgteIdo = pagadoIdo;
                }
                if (provisionAnteriorAux == provisionDiaSgteAux && dispensadoAux == 0L) {
                    sumaRevProvision = provisionAnteriorAux + provisionDiaSgteAux;
                    provisionDiaSgteAux = -1L;
                    dispensadoAux = -1L;
                    provisionAnteriorAux = -1L;
                }
                if (provisionDiaSgte == 0L) {
                    observacion = "Cajero No Corto " + observacion;
                }
                if (mapCajeroEfectivoAtm.get(codigoCajero) != null) {
                    provisionDiaSgteIdo = mapCajeroEfectivoAtm.get(codigoCajero);
                }
                diferenciadiasgte = provisionDiaSgte - provisionDiaSgteIdo;
                if (diferenciadiasgte != 0L) {
                    if (provisionDiaSgte == provisionDiaSgteIdo) {
                        provisionDiaSgte = provisionDiaSgteIdo - diferenciadiasgte;
                    }
                    final InformeDiferencias pEntity2 = new InformeDiferencias();
                    final InformeDiferenciasPK diferenciasPK2 = new InformeDiferenciasPK(codigoCajero, fecha);
                    pEntity2.setInformeDiferenciasPK(diferenciasPK2);
                    pEntity2.setCodigoOcca(codigoCajeroOcca);
                    pEntity2.setValorMaquina(Long.valueOf(provisionDiaSgte));
                    pEntity2.setValorLinea(Long.valueOf(provisionDiaSgteIdo));
                    pEntity2.setValorDiferencias(Long.valueOf(diferenciadiasgte));
                    pEntity2.setFechaSistema(new Date());
                    pEntity2.setAplicado("NO");
                    pEntity2.setAumento(Long.valueOf(0L));
                    pEntity2.setDisminucion(Long.valueOf(0L));
                    pEntity2.setFaltante(Long.valueOf(0L));
                    pEntity2.setSobrante(Long.valueOf(0L));
                    if (diferenciadiasgte < this.limiteDiferencia) {
                        imprime = true;
                    }
                    else {
                        if (diferenciadiasgte > 0L) {
                            imprime = true;
                        }
                        if (sumaRevProvision > 0L) {
                            sumaRevProvision = 0L;
                            imprime = true;
                        }
                        if (diferenciadiasgte < this.limiteDiferenciaMax) {
                            imprime = false;
                        }
                    }
                    if (imprime) {
                        reporte.add(pEntity2);
                        imprime = false;
                    }
                }
            }
            catch (Exception ex) {
                this.loggerApp.severe("Error al generar reporte de diferencias: " + ex.getMessage());
            }
        }
        return reporte;
    }
}
