package com.davivienda.sara.cuadrecifras.general.helper;

import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.cuadrecifras.general.bean.DatosMovimientoCajeroBean;
import com.davivienda.sara.cuadrecifras.general.bean.InformeAyerBean;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasSessionLocal;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.JSon;
import com.davivienda.sara.constantes.TipoMovimientoCuadre;
import com.davivienda.sara.cuadrecifras.general.bean.MovimientoCuadreCifrasBean;
import com.davivienda.sara.cuadrecifras.session.InformeDiferenciasSessionLocal;
import com.davivienda.sara.entitys.InformeDiferencias;
import com.davivienda.sara.entitys.InformeDiferenciasPK;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.tablas.provisionhost.session.ProvisionHostLocal;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.Numero;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import java.util.logging.Level;
import javax.ejb.EJBException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * DiarioElectronicoGeneralDiarioElectronicoServletHelper - 27/08/2008
 * Descripci√≥n : Versi√≥n : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class CuadreCifrasInformeServletHelper implements CuadreCifrasHelperInterface {

    private long limiteDiferencia = -1000;
    private long limiteDiferenciaMax = 1000;
    private CuadreCifrasSessionLocal session;
    private InformeDiferenciasSessionLocal informeSession;
    private String respuestaJSon;
    private ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSession;
    private Logger loggerApp;
    private ProvisionHostLocal provisionHostsession;
    private ReintegrosSessionLocal reintegrosSession;

    public CuadreCifrasInformeServletHelper(CuadreCifrasSessionLocal session, ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSession) {
        this.session = session;
        this.procesoCuadreCifrasSession = procesoCuadreCifrasSession;
    }

    public CuadreCifrasInformeServletHelper(CuadreCifrasSessionLocal session, ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSession, InformeDiferenciasSessionLocal informeSession, ProvisionHostLocal provisionHostsession, ReintegrosSessionLocal reintegrosSession) {
        this.session = session;
        this.informeSession = informeSession;
        this.procesoCuadreCifrasSession = procesoCuadreCifrasSession;
        this.provisionHostsession = provisionHostsession;
        this.reintegrosSession = reintegrosSession;
    }

    public String obtenerDatos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<MovimientoCuadreCifrasBean> obtenerDatosCollectionCC(Calendar fechaInicial, Integer codigoOcca) throws IllegalAccessException {
        loggerApp = SaraConfig.obtenerInstancia().loggerApp;
        loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC Inicio");
        respuestaJSon = "";
        String respuesta = "";
        Collection<MovimientoCuadre> items = null;
        Collection<MovimientoCuadreCifrasBean> itemsFormateados = null;
        ArrayList cajerosDiferencia = null;
        try {

            Calendar fechaFinal = null;
            Integer codigoCajero = null;
            try {
                //OJO SOLO PARA PRUEBAS
                fechaFinal = fechaInicial;
                // fechaFinal = objectContext.getAtributoFechaHoraFinal();
            } catch (IllegalArgumentException ex) {
                fechaFinal = fechaInicial;
            }

            loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC codigoOcca: " + codigoOcca);
            if (codigoOcca == null) {
                codigoOcca = -1;
            } else if (codigoOcca == 0) {
                codigoOcca = -1;
            }

            try {
                codigoCajero = 9999;
                loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC codigoCajero: " + codigoCajero);
                loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC fechaInicial: " + fechaInicial.getTime());
                java.util.Collection<com.davivienda.sara.entitys.MovimientoCuadre> items2 = session.getMovimientoCuadreCifrasOccaMostrar(codigoCajero, codigoOcca, fechaInicial, fechaInicial);
                loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC movimientos encontrados: " + items2.size());
                itemsFormateados = getBeansMovimientoCuadreCifras(items2);
                loggerApp.log(Level.INFO, "obtenerDatosCollectionCC itemsFormateados size" + itemsFormateados.size());
            } catch (EJBException ex) {
                loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros para que cumplas los criterios de la consulta");
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
                }

            } catch (Exception ex) {
                loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }

        } catch (IllegalArgumentException ex) {
            loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
        }
        if (items != null && this.respuestaJSon.length() <= 1) {
            // itemsFormateados  = getBeansMovimientoCuadreCifras(items,cajerosDiferencia);  // paso los items a JSON

        } else {
            respuesta = this.respuestaJSon;
        }

        loggerApp.log(Level.INFO, "obtenerDatosCollectionCC respuesta " + respuesta);
        loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper obtenerDatosCollectionCC fin");
        return itemsFormateados;
    }

    private Collection<MovimientoCuadreCifrasBean> getBeansMovimientoCuadreCifras(Collection<MovimientoCuadre> regs) {
        Collection<MovimientoCuadreCifrasBean> beans = new ArrayList<MovimientoCuadreCifrasBean>();
        Map<Integer, MovimientoCuadreCifrasBean> mapFechas = new HashMap<Integer, MovimientoCuadreCifrasBean>();
        long i = 1;

        for (MovimientoCuadre movimientoCuadre : regs) {

            if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(com.davivienda.sara.constantes.TipoMovimientoCuadre.DIFERENCIAS.codigo)
                    && movimientoCuadre.getValorMovimiento() != 0) {
                if (!mapFechas.containsKey(movimientoCuadre.getCajero().getCodigoCajero())) {
                    MovimientoCuadreCifrasBean b1 = new MovimientoCuadreCifrasBean();
                    mapFechas.put(movimientoCuadre.getCajero().getCodigoCajero(), b1);
                    beans.add(b1);
                    b1.id = BigInteger.valueOf(i);
                    i++;

                }
            }

        }

        for (MovimientoCuadre movimientoCuadre : regs) {
            //guarda DatosMovimientoCajeroBean

            if (mapFechas.get(movimientoCuadre.getCajero().getCodigoCajero()) != null) {

                MovimientoCuadreCifrasBean b1 = mapFechas.get(movimientoCuadre.getCajero().getCodigoCajero());

                b1.codigoCajero = movimientoCuadre.getCajero().getCodigoCajero();
                b1.nombreCajero = movimientoCuadre.getCajero().getNombre();
                b1.fecha = Fecha.aCadena(movimientoCuadre.getFecha(), FormatoFecha.DEFECTO_SEPARADOR_GUION.formato);
                b1.codigoOcca = movimientoCuadre.getCajero().getOcca().getCodigoOcca();
                DatosMovimientoCajeroBean dc1 = new DatosMovimientoCajeroBean();
                dc1.descripcion = movimientoCuadre.getTipoMovimientoCuadre().getDescripcion();
                dc1.valor = Numero.aMoneda(movimientoCuadre.getValorMovimiento());
                dc1.observacion = movimientoCuadre.getObservacion();
                dc1.idRegistro = movimientoCuadre.getIdMovimientoCuadre();
                dc1.idUsuarioObservacion = movimientoCuadre.getIdUsuarioObservacion();
                dc1.fecha = Fecha.aCadena(movimientoCuadre.getFecha(), FormatoFecha.AAAAMMDD);
                if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre() <= 199) {

                    StringBuffer sb = new StringBuffer();
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
                } else {

                    if (movimientoCuadre.getConceptoMovimientoCuadre() != null && movimientoCuadre.getConceptoMovimientoCuadre().getCodigoConcepto() != 0) {
                        dc1.concepto = movimientoCuadre.getConceptoMovimientoCuadre().getCodigoConcepto();
                        dc1.descripcion = dc1.descripcion + " - " + movimientoCuadre.getConceptoMovimientoCuadre().getDescripcion();
                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre() != 231) {
                        b1.datosLinea.add(dc1);
                    }

                }
            }
        }

        return beans;
    }

    public void procesoInformeDescuadreOld(Calendar fechaInicial, Integer codigoOcca) throws IllegalAccessException {
        loggerApp = SaraConfig.obtenerInstancia().loggerApp;
        loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper procesoInformeDescruadre Inicio");

        try {
            Collection<MovimientoCuadreCifrasBean> itemsFormateados = this.obtenerDatosCollectionCC(fechaInicial, codigoOcca);

            int itemsDescuadrados = 0;

            if (itemsFormateados.size() > 0) {
                loggerApp.info("Susceptibles de reintegro: " + itemsFormateados.size());

                for (MovimientoCuadreCifrasBean mov : itemsFormateados) {

                    Collection<DatosMovimientoCajeroBean> datosCajero = mov.getDatosCajero();
                    Collection<DatosMovimientoCajeroBean> datosLinea = mov.getDatosLinea();

                    boolean provDiaSgteMaquina = false;
                    boolean provDiaSgteLinea = false;
                    boolean diferencias = false;
                    BigDecimal maquina = new BigDecimal(0);
                    BigDecimal linea = new BigDecimal(0);
                    BigDecimal dif = new BigDecimal(0);

                    for (DatosMovimientoCajeroBean cajeroBean : datosCajero) {
                        if (cajeroBean.getDescripcion().equals("ProvisiÛn DÌa Siguiente")) {
                            maquina = Numero.aBigDecimal(cajeroBean.getValor(), Constantes.FORMATO_MONEDA_INFO_REINTEGROS);
                            if (maquina.compareTo(BigDecimal.ZERO) > 0) {
                                provDiaSgteMaquina = true;
                                break;
                            }
                        }
                    }

                    for (DatosMovimientoCajeroBean lineaBean : datosLinea) {
                        if (lineaBean.getDescripcion().equals("ProvisiÛn Dia Siguiente IDO")) {
                            linea = Numero.aBigDecimal(lineaBean.getValor(), Constantes.FORMATO_MONEDA_INFO_REINTEGROS);
                            if (linea.compareTo(BigDecimal.ZERO) > 0) {
                                provDiaSgteLinea = true;
                            }
                        } else if (lineaBean.getDescripcion().equals("Diferencias")) {
                            dif = Numero.aBigDecimal(lineaBean.getValor(), Constantes.FORMATO_MONEDA_INFO_REINTEGROS);
                            if (dif.compareTo(BigDecimal.ZERO) != 0) {
                                diferencias = true;
                            }
                        }

                        if (provDiaSgteLinea && diferencias) {
                            break;
                        }
                    }

                    if (provDiaSgteMaquina && provDiaSgteLinea && diferencias) {
                        itemsDescuadrados++;

                        Date d = Fecha.aDate(mov.getFecha(), FormatoFecha.DEFECTO_SEPARADOR_GUION.formato);

                        if (null != d) {

                            InformeDiferencias pEntity = new InformeDiferencias();
                            InformeDiferenciasPK diferenciasPK = new InformeDiferenciasPK(mov.getCodigoCajero(), d);

                            pEntity.setInformeDiferenciasPK(diferenciasPK);
                            pEntity.setCodigoOcca(mov.getCodigoOcca());
                            pEntity.setValorMaquina(maquina.longValue());
                            pEntity.setValorLinea(linea.longValue());
                            pEntity.setValorDiferencias(dif.longValue());
                            pEntity.setFechaSistema(new Date());
                            pEntity.setAplicado(Constantes.PARAM_INFORME_DIFERENCIAS_NO);
                            pEntity.setAumento(0L);
                            pEntity.setDisminucion(0L);
                            pEntity.setFaltante(0L);
                            pEntity.setSobrante(0L);
                            informeSession.adicionar(pEntity);
                        }
                    }
                }

                loggerApp.info("Aceptados con descuadre: " + itemsDescuadrados);

            } else {
                loggerApp.info("No se encontraron reintegros: " + itemsFormateados.size());
            }

        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, "CuadreCifrasInformeServletHelper-Registros susceptibles de reintegro Error ", ex);
        }
        loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper procesoInformeDescruadre Fin");
    }

    public void procesoInformeDescuadre(Calendar fechaInicial, Integer codigoOcca) throws IllegalAccessException {
        loggerApp = SaraConfig.obtenerInstancia().loggerApp;
        loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper procesoInformeDescruadre Inicio");

        try {
            Date fechaHisto = SaraConfig.obtenerInstancia().FECHA_HISTORICAS_CONSULTA;
            java.util.Collection<com.davivienda.sara.entitys.MovimientoCuadre> items = session.getMovimientoCuadreCifras(9999, fechaInicial, fechaInicial);
            java.util.Collection<com.davivienda.sara.entitys.ProvisionHost> itemsProvision = provisionHostsession.getProvisionHostRangoFecha(fechaInicial, com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial));
            Collection<InformeAyerBean> itemsProvisionAyer = getCollectionInformeCuadreAyer(items, itemsProvision, com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, FormatoFecha.AAAAMMDD), -1);
            Collection<InformeDiferencias> reporte = generarResumenCuadreDiario(itemsProvisionAyer, items, -1);

            loggerApp.info("Susceptibles de reintegro: " + reporte.size());

            for (InformeDiferencias pEntity : reporte) {
                informeSession.adicionar(pEntity);
                Collection<Reintegros> reintegros = reintegrosSession.getReintegros(fechaInicial.getTime(), pEntity.getInformeDiferenciasPK().getCodigoCajero(), fechaHisto);
                if (reintegros != null && !reintegros.isEmpty()) {
                    for (Reintegros reintegro : reintegros) {
                        reintegro.setDifeDescuadre("Y");
                        reintegrosSession.actualizar(reintegro);
                    }
                }
            }
            reintegrosSession.actualizarPersistencia();

        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, "CuadreCifrasInformeServletHelper-Registros susceptibles de reintegro Error ", ex);
        }
        loggerApp.log(Level.INFO, "CuadreCifrasInformeServletHelper procesoInformeDescruadre Fin");
    }

    private Collection<InformeAyerBean> getCollectionInformeCuadreAyer(Collection<MovimientoCuadre> regs, java.util.Collection<com.davivienda.sara.entitys.ProvisionHost> itemsProvision, String fechaInicial, Integer codigoOcca) {
        Collection<InformeAyerBean> informes = new ArrayList<InformeAyerBean>();
        Collection<InformeAyerBean> informesR = new ArrayList<InformeAyerBean>();
        Map<Integer, Long> mapCajeroProvision = new HashMap<Integer, Long>();
        Map<Integer, Long> mapCajeroDiaSgteIdo = new HashMap<Integer, Long>();

        Integer codigoCajero = 0;
        Integer codigoOccaRegistro = 0;
        Integer codigoOccaCompara = 0;
        String nombreOcca = "";
        boolean primerCodigo = false;
        long pagado = 0;
        long efectivoAtm = 0;
        long provisionhost = 0;
        long aumentoProvLinea = 0;
        long aumentoProvIDO = 0;
        long disminucionProvLinea = 0;
        long disminucionProvIDO = 0;
        long provDiaSgteIDO = 0;
        long provisionAyer = -1;

        long faltanteIDO = 0;
        long provisionAnteriorAux = -1;
        long provisionDiaSgteAux = -1;
        //variable para saber si se movio el cajero distinto apagado ido y encontrar diferencia en pagadoido
        long sumaSinPagadoIdo = 0;

        if (regs != null) {
            // creo cada uno de los informes asociados al cajero

            for (MovimientoCuadre movimientoCuadre : regs) {
                //System.out.println("CodigoCajero "+ movimientoCuadre.getCajero().getCodigoCajero());
                codigoOccaCompara = movimientoCuadre.getCajero().getOcca().getCodigoOcca();
                if ((codigoOccaCompara.compareTo(codigoOcca) == 0) || codigoOcca == -1) {
                    if (primerCodigo == false) {
                        codigoCajero = movimientoCuadre.getCajero().getCodigoCajero();
                        primerCodigo = true;
                    }

                    if (codigoCajero.compareTo(movimientoCuadre.getCajero().getCodigoCajero()) == 0) {
                    } else {
                        InformeAyerBean informe = new InformeAyerBean();
                        informe.setCodigoCajero(codigoCajero);
                        informe.setCodigoOcca(codigoOccaRegistro);
                        informe.setNombreOcca(nombreOcca);
                        informe.setpagado(pagado);
                        informe.setefectivoAtm(efectivoAtm);
                        //
                        informe.setProvisionDiaAnterior(provisionAnteriorAux);
                        informe.setProvisionDiaSig(provisionDiaSgteAux);
                        informe.setSumaSinPagadoIdo(sumaSinPagadoIdo);
                        informe.setProvisionAyer(provisionAyer);

                        informes.add(informe);
                        pagado = 0;
                        efectivoAtm = 0;
                        provisionAnteriorAux = -1;
                        provisionDiaSgteAux = -1;
                        sumaSinPagadoIdo = 0;
                        provisionAyer = -1;

                    }

                    codigoCajero = movimientoCuadre.getCajero().getCodigoCajero();
                    codigoOccaRegistro = movimientoCuadre.getCajero().getOcca().getCodigoOcca();
                    nombreOcca = movimientoCuadre.getCajero().getOcca().getNombre();

                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(com.davivienda.sara.constantes.TipoMovimientoCuadre.PAGADO_IDO.codigo)) {
                        pagado = pagado + movimientoCuadre.getValorMovimiento();
                        sumaSinPagadoIdo = sumaSinPagadoIdo - movimientoCuadre.getValorMovimiento();
                        // pagado=pagado+movimientoCuadre.getValorMovimiento()/10000;
                        //pagado=pagado*10000;
                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(com.davivienda.sara.constantes.TipoMovimientoCuadre.DONACIONES.codigo)) {

                        pagado = pagado - movimientoCuadre.getValorMovimiento();
                        //pagado=pagado-movimientoCuadre.getValorMovimiento()/10000;
                        //pagado=pagado*10000;
                    }

                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(com.davivienda.sara.constantes.TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE_IDO.codigo)) {
                        efectivoAtm = movimientoCuadre.getValorMovimiento();

                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(com.davivienda.sara.constantes.TipoMovimientoCuadre.PROVISION_DIA_ANTERIOR.codigo)) {
                        provisionAnteriorAux = movimientoCuadre.getValorMovimiento();

                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(com.davivienda.sara.constantes.TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE.codigo)) {
                        provisionDiaSgteAux = movimientoCuadre.getValorMovimiento();

                    }
                    if (movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(com.davivienda.sara.constantes.TipoMovimientoCuadre.PROVISION_AYER.codigo)) {
                        provisionAyer = movimientoCuadre.getValorMovimiento();

                    }

                    //tener en cuenta si disminuyen provisiones
                    sumaSinPagadoIdo = sumaSinPagadoIdo + movimientoCuadre.getValorMovimiento();

                }
            }
            //LLENO EL ULTIMO REGISTRO 

            InformeAyerBean informe = new InformeAyerBean();
            informe.setCodigoCajero(codigoCajero);
            informe.setCodigoOcca(codigoOccaRegistro);
            informe.setNombreOcca(nombreOcca);
            informe.setpagado(pagado);
            informe.setefectivoAtm(efectivoAtm);
            //
            informe.setProvisionDiaAnterior(provisionAnteriorAux);
            informe.setProvisionDiaSig(provisionDiaSgteAux);
            informe.setSumaSinPagadoIdo(sumaSinPagadoIdo);
            informe.setProvisionAyer(provisionAyer);
            informes.add(informe);

        }

        primerCodigo = false;

        if (itemsProvision != null) {
            // creo cada uno de los informes asociados al cajero
            for (com.davivienda.sara.entitys.ProvisionHost provisionHost : itemsProvision) {
                codigoOccaCompara = provisionHost.getCajero().getOcca().getCodigoOcca();
                if ((codigoOccaCompara.compareTo(codigoOcca) == 0) || codigoOcca == -1) {
                    if (primerCodigo == false) {
                        codigoCajero = provisionHost.getCajero().getCodigoCajero();
                        primerCodigo = true;
                    }

                    if (codigoCajero.compareTo(provisionHost.getCajero().getCodigoCajero()) == 0) {
                    } else {
                        if (disminucionProvLinea != 0) {
                            disminucionProvIDO = disminucionProvLinea;

                        }

                        if (aumentoProvLinea != 0) {
                            aumentoProvIDO = aumentoProvLinea;

                        }

                        provisionhost = provisionhost + aumentoProvIDO;
                        provisionhost = provisionhost - disminucionProvIDO;
                        //si provDiaSgteIDO==FALTANTE_IDO REVISAR CUANDO HALLA DOS FALTANTESIDO
                        if (provDiaSgteIDO == faltanteIDO) {
                            provisionhost = provisionhost - provDiaSgteIDO;
                        }

                        if (!mapCajeroProvision.containsKey((Integer) codigoCajero)) {
                            mapCajeroProvision.put(codigoCajero, provisionhost);
                            mapCajeroDiaSgteIdo.put(codigoCajero, provDiaSgteIDO);
                        }
                        provisionhost = 0;
                        aumentoProvLinea = 0;
                        aumentoProvIDO = 0;
                        disminucionProvLinea = 0;
                        disminucionProvIDO = 0;
                        //si provDiaSgteIDO==FALTANTE_IDO
                        provDiaSgteIDO = 0;
                        faltanteIDO = 0;

                    }
                    codigoCajero = provisionHost.getCajero().getCodigoCajero();
                    if (provisionHost.getProvisionHostPK().getMotivo() == 75 && provisionHost.getProvisionHostPK().getTipo() == 99) {
                        provDiaSgteIDO = provisionHost.getValor();

                    }
                    if (provisionHost.getProvisionHostPK().getMotivo() == 35 && provisionHost.getProvisionHostPK().getTipo() == 99) {
                        faltanteIDO = provisionHost.getValor();
                    }

                    if (provisionHost.getProvisionHostPK().getMotivo() == ((short) 112) || provisionHost.getProvisionHostPK().getMotivo() == ((short) 70)) {
                        if (provisionHost.getProvisionHostPK().getMotivo() == ((short) 112)) {

                            if (provisionHost.getProvisionHostPK().getTipo() == ((short) 99)) {
                                disminucionProvIDO = disminucionProvIDO + provisionHost.getValor();
                            } else {
                                aumentoProvIDO = aumentoProvIDO + provisionHost.getValor();
                            }

                        } else if (provisionHost.getProvisionHostPK().getTipo() == ((short) 99)) {
                            disminucionProvLinea = disminucionProvLinea + provisionHost.getValor();
                        } else {
                            aumentoProvLinea = aumentoProvLinea + provisionHost.getValor();
                        }
                    } else if ((provisionHost.getProvisionHostPK().getMotivo() == ((short) 35)) && (provisionHost.getProvisionHostPK().getTipo() == ((short) 99))) {

                        provisionhost = provisionhost - provisionHost.getValor();
                    } else {
                        provisionhost = provisionhost + provisionHost.getValor();
                    }
                }
            }
        }
        long auxnumero = 0;

        //Se controla que exista valor de provision para el cajero
        boolean tieneProvision = false;

        for (InformeAyerBean informesRecorre : informes) {
            InformeAyerBean informe = new InformeAyerBean();
            informe = informesRecorre;
            tieneProvision = false;
            if (mapCajeroProvision.get(informesRecorre.getCodigoCajero()) != null) {
                tieneProvision = true;

                //informe.setProvisionEfectivo(mapCajeroProvision.get(informesRecorre.getCodigoCajero()));
                if (mapCajeroProvision.get(informesRecorre.getCodigoCajero()) == 0) {
                    informe.setefectivoAtm(informe.getNumericopagado());
                } else if (mapCajeroProvision.get(informesRecorre.getCodigoCajero()) < 0) {
                    auxnumero = mapCajeroProvision.get(informesRecorre.getCodigoCajero()) * (-1);
                    informe.setProvisionEfectivo(auxnumero);
                    auxnumero = auxnumero - informe.getNumericopagado();
                    if (auxnumero < 0) {
                        auxnumero = auxnumero * (-1);
                    }
                    informe.setefectivoAtm(auxnumero);
                    auxnumero = 0;

                } else if (informe.getNumericoefectivoAtm() == 0) {
                    informe.setProvisionEfectivo(informe.getNumericopagado());
                } else {
                    informe.setProvisionEfectivo(mapCajeroProvision.get(informesRecorre.getCodigoCajero()));
                }

                if ((informe.getNumericoefectivoAtm() - (mapCajeroProvision.get(informesRecorre.getCodigoCajero()) - informe.getNumericopagado())) < 0) {
                    informe.setProvisionEfectivo(mapCajeroProvision.get(informesRecorre.getCodigoCajero()) - mapCajeroDiaSgteIdo.get(informesRecorre.getCodigoCajero()));
                }

            } else /**
             * si no existen datos de provision y provision esta igual a pagado
             * se pone en valor provision cero*
             */
            {
                if (informe.getNumericoefectivoAtm().equals(new Long(0))) {
                    informe.setProvisionEfectivo(new Long(0));
                    informe.setefectivoAtm(informe.getNumericopagado());

                } else {
                    informe.setProvisionEfectivo(informe.getNumericopagado() + informe.getNumericoefectivoAtm());
                }
            }
            //NUEVA
            //validacion para provisionDiaIdoSgteAux==-1 && provisionDiaSgteAux==diferenciadiasgte && provisionAnteriorAux!= provisionDiaSgteAux 
            //diferencia en pagado
//                    if(informe.getCodigoCajero()==1109)
//                     {
            if (informe.getNumericopagado().equals(informe.getNumericoProvisionEfectivo()) && informe.getProvisionDiaSigIdo().equals(new Long(0))) {
                if ((informe.getProvisionDiaAnterior().equals(informe.getProvisionDiaSig()) == false) || informe.getSumaSinPagadoIdo().equals(new Long(0))) {
                    //si es necesario se valida una variable auxiliar para ProvisionDiaSigIdo con valor -1

                    /**
                     * si existen datos de provision y provision esta en cero se
                     * pone en valor provision elvalordel pagado*
                     */
                    //si NO tiene provision 
                    if (!tieneProvision) {
                        informe.setProvisionEfectivo(new Long(0));
                        informe.setefectivoAtm(informe.getNumericopagado());
                    }
                }

                //diferencia en pagado solamente existe datos de pagadoido
            }
//                    }
            informesR.add(informe);
        }

        return informesR;
    }

    private Collection<InformeDiferencias> generarResumenCuadreDiario(Collection<InformeAyerBean> itemsProvisionAyer, java.util.Collection<com.davivienda.sara.entitys.MovimientoCuadre> regs, Integer codigoOcca) {
        String respuesta = "";
        Integer tipoMovimientoCuadre = 0;
        Integer codigoCajero = 0;
        Integer codigoCajeroOcca = 0;
        Date fecha = null;
        boolean primerCodigo = false;
        long provisionAnterior = 0;
        long provisionInicialReal = 0;
        long diferenciaSobrante = 0;
        long diferenciaTransportadora = 0;
        long provisionReal = 0;
        long provisionDiaSgteIdo = 0;
        long dispensado = 0;
        long pagadoIdo = 0;
        long donaciones = 0;
        long provisionDiaSgte = 0;
        long diferenciadiasgte = 0;
        long sumaRevProvision = 0;
        long dispensadoContadores = 0;

        long provisionDiaSgteAux = -1;
        long provisionDiaIdoSgteAux = -1;
        long dispensadoAux = -1;
        long provisionAnteriorAux = -1;
        Integer codigoOccaRegistro = 0;
        String observacion = "";
        Map<Integer, Long> mapCajeroEfectivoAtm = new HashMap<Integer, Long>();
        Collection<InformeDiferencias> reporte = new ArrayList<InformeDiferencias>();

        if (itemsProvisionAyer != null) {
            for (InformeAyerBean item : itemsProvisionAyer) {

                if (!mapCajeroEfectivoAtm.containsKey((Integer) item.getCodigoCajero())) {
                    mapCajeroEfectivoAtm.put(item.getCodigoCajero(), item.getNumericoefectivoAtm());

                }
            }
        }

        // 
        if (regs != null) {
            boolean imprime = false;

            try {
                for (MovimientoCuadre item : regs) {
                    codigoOccaRegistro = item.getCajero().getOcca().getCodigoOcca();

                    if ((codigoOccaRegistro.compareTo(codigoOcca) == 0) || codigoOcca == -1) {
                        if (primerCodigo == false) {
                            fecha = item.getFecha();
                            codigoCajero = item.getCajero().getCodigoCajero();
                            codigoCajeroOcca = item.getCajero().getOcca().getCodigoOcca();
                            primerCodigo = true;
                        }
                        if (codigoCajero.compareTo(item.getCajero().getCodigoCajero()) == 0) {

                        } else {
                            diferenciaTransportadora = provisionReal - provisionDiaSgteIdo;
                            diferenciaSobrante = dispensado - (pagadoIdo - donaciones);
                            //VALIDACION ESPECIAL PARA CAJEROS QUE TRANSMITEN pagadoIdo
                            if (provisionAnteriorAux == 0 && provisionDiaSgteAux == 0 && dispensadoAux == 0 && provisionDiaIdoSgteAux == -1) {
                                provisionDiaSgteIdo = pagadoIdo;
                            }

                            //validacion para revisar si no corto el cajero
                            if ((provisionAnteriorAux == provisionDiaSgteAux) && (dispensadoAux == 0)) {
                                sumaRevProvision = provisionAnteriorAux + provisionDiaSgteAux;
                                provisionDiaSgteAux = -1;
                                dispensadoAux = -1;
                                provisionAnteriorAux = -1;
                            }

                            if (provisionDiaSgte == 0) {
                                observacion = "Cajero No Corto " + observacion;

                            }
                            //aqui se hace el CAMBIO CON EL AYER
                            if (mapCajeroEfectivoAtm.get(codigoCajero) != null) {
                                provisionDiaSgteIdo = mapCajeroEfectivoAtm.get(codigoCajero);
                            }

                            diferenciadiasgte = provisionDiaSgte - provisionDiaSgteIdo;

                            if (diferenciadiasgte != 0) {

                                if (provisionDiaSgte == provisionDiaSgteIdo) {
                                    provisionDiaSgte = provisionDiaSgteIdo - diferenciadiasgte;

                                }
                                InformeDiferencias pEntity = new InformeDiferencias();
                                InformeDiferenciasPK diferenciasPK = new InformeDiferenciasPK(codigoCajero, item.getFecha());

                                pEntity.setInformeDiferenciasPK(diferenciasPK);
                                pEntity.setCodigoOcca(codigoCajeroOcca);
                                pEntity.setValorMaquina(provisionDiaSgte);
                                pEntity.setValorLinea(provisionDiaSgteIdo);
                                pEntity.setValorDiferencias(diferenciadiasgte);
                                pEntity.setFechaSistema(new Date());
                                pEntity.setAplicado(Constantes.PARAM_INFORME_DIFERENCIAS_NO);
                                pEntity.setAumento(0L);
                                pEntity.setDisminucion(0L);
                                pEntity.setFaltante(0L);
                                pEntity.setSobrante(0L);

                                if (diferenciadiasgte < limiteDiferencia) {

                                    imprime = true;
                                } else {
                                    //diferenciadiasgte!=0 
                                    if (diferenciadiasgte > 0) {

                                        imprime = true;
                                    }
                                    if (sumaRevProvision > 0) {

                                        sumaRevProvision = 0;
                                        imprime = true;
                                    }
                                    //valido mayores a
                                    if (diferenciadiasgte < limiteDiferenciaMax) {

                                        imprime = false;
                                    }
                                }
                                if (imprime) {
                                    reporte.add(pEntity);
                                    imprime = false;
                                }

                            }
                            //OJO VALIDACION NUEVA 

                            provisionAnterior = 0;
                            provisionInicialReal = 0;
                            diferenciaSobrante = 0;
                            diferenciaTransportadora = 0;
                            provisionReal = 0;
                            provisionDiaSgteIdo = 0;
                            dispensado = 0;
                            pagadoIdo = 0;
                            donaciones = 0;
                            provisionDiaSgte = 0;
                            diferenciadiasgte = 0;
                            provisionDiaSgteAux = -1;
                            provisionDiaIdoSgteAux = -1;
                            dispensadoAux = -1;
                            provisionAnteriorAux = -1;

                            observacion = "";
                            dispensadoContadores = 0;
                        }
                        tipoMovimientoCuadre = item.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre();
                        codigoCajero = item.getCajero().getCodigoCajero();
                        codigoCajeroOcca = item.getCajero().getOcca().getCodigoOcca();
                        TipoMovimientoCuadre consultaAjuste = TipoMovimientoCuadre.AJUSTE_AUMENTO_PROVISION;
                        consultaAjuste = TipoMovimientoCuadre.getTipoMovimientoCuadre(tipoMovimientoCuadre);

                        switch (consultaAjuste) {
                            case PROVISION_DIA_ANTERIOR:
                                provisionAnteriorAux = provisionAnterior = item.getValorMovimiento();
                                break;
                            case PROVISION_INICIAL_REAL:
                                provisionInicialReal = item.getValorMovimiento();
                                break;
                            case PROVISION_DIA_SIGUIENTE_REAL:
                                provisionReal = item.getValorMovimiento();
                                if (item.getObservacion() != null) {
                                    if (item.getObservacion().equals("null") == false) {
                                        observacion = observacion + " " + item.getObservacion();
                                    }
                                }
                                break;
                            case PROVISION_DIA_SIGUIENTE_IDO:
                                provisionDiaIdoSgteAux = provisionDiaSgteIdo = item.getValorMovimiento();
                                if (item.getObservacion() != null) {
                                    if (item.getObservacion().equals("null") == false) {
                                        observacion = observacion + " " + item.getObservacion();
                                    }
                                }
                                break;
                            case DISPENSADO:
                                dispensadoAux = dispensado = item.getValorMovimiento();

//                                dispensadoContadores = 
//                                (item.getDenominacion1() * item.getBilletes1()  * 1000) +
//                                (item.getDenominacion2() * item.getBilletes2()  * 1000)+
//                                (item.getDenominacion3() * item.getBilletes3()  * 1000) +
//                                (item.getDenominacion4() * item.getBilletes4() * 1000);
                                break;
                            case PAGADO_IDO:
                                pagadoIdo = item.getValorMovimiento();
                                if (item.getObservacion() != null) {
                                    if (item.getObservacion().equals("null") == false) {
                                        observacion = observacion + " " + item.getObservacion();
                                    }
                                }
                                break;
                            case DONACIONES:
                                donaciones = item.getValorMovimiento();

                                break;
                            case PROVISION_DIA_SIGUIENTE:
                                provisionDiaSgteAux = provisionDiaSgte = item.getValorMovimiento();
                                break;
                            case AJUSTE_AUMENTO_PROVISION:
                                if (item.getObservacion() != null) {
                                    if (item.getObservacion().equals("null") == false) {
                                        observacion = observacion + " " + item.getObservacion();
                                    }
                                }
                                break;

                            case AJUSTE_DISMINUCION_PROVISION:
                                if (item.getObservacion() != null) {
                                    if (item.getObservacion().equals("null") == false) {
                                        observacion = observacion + " " + item.getObservacion();
                                    }
                                }
                                break;
                            case AJUSTE_SOBRANTE:
                                if (item.getObservacion() != null) {
                                    if (item.getObservacion().equals("null") == false) {
                                        observacion = observacion + " " + item.getObservacion();
                                    }
                                }
                                break;
                            case AJUSTE_FALTANTE:
                                if (item.getObservacion() != null) {
                                    if (item.getObservacion().equals("null") == false) {
                                        observacion = observacion + " " + item.getObservacion();
                                    }
                                }
                                break;
                            case DIFERENCIAS:
                                if (item.getObservacion() != null) {
                                    if (item.getObservacion().equals("null") == false) {
                                        observacion = observacion + " " + item.getObservacion();
                                    }
                                }
                                break;

                            default:

                                break;
                        }
                    }
                }
                //LLENO EL ULTIMO REGISTRO
                diferenciaTransportadora = provisionReal - provisionDiaSgteIdo;
                diferenciaSobrante = dispensado - (pagadoIdo - donaciones);
                //VALIDACION ESPECIAL PARA CAJEROS QUE TRANSMITEN pagadoIdo
                if (provisionAnteriorAux == 0 && provisionDiaSgteAux == 0 && dispensadoAux == 0 && provisionDiaIdoSgteAux == -1) {
                    provisionDiaSgteIdo = pagadoIdo;
                }

                //validacion para revisar si no corto el cajero
                if ((provisionAnteriorAux == provisionDiaSgteAux) && (dispensadoAux == 0)) {
                    sumaRevProvision = provisionAnteriorAux + provisionDiaSgteAux;
                    provisionDiaSgteAux = -1;
                    dispensadoAux = -1;
                    provisionAnteriorAux = -1;
                }

                if (provisionDiaSgte == 0) {
                    observacion = "Cajero No Corto " + observacion;

                }
                //aqui se hace el CAMBIO CON EL AYER
                if (mapCajeroEfectivoAtm.get(codigoCajero) != null) {
                    provisionDiaSgteIdo = mapCajeroEfectivoAtm.get(codigoCajero);
                }

                diferenciadiasgte = provisionDiaSgte - provisionDiaSgteIdo;

                if (diferenciadiasgte != 0) {

                    if (provisionDiaSgte == provisionDiaSgteIdo) {
                        provisionDiaSgte = provisionDiaSgteIdo - diferenciadiasgte;

                    }
                    InformeDiferencias pEntity = new InformeDiferencias();
                    InformeDiferenciasPK diferenciasPK = new InformeDiferenciasPK(codigoCajero, fecha);

                    pEntity.setInformeDiferenciasPK(diferenciasPK);
                    pEntity.setCodigoOcca(codigoCajeroOcca);
                    pEntity.setValorMaquina(provisionDiaSgte);
                    pEntity.setValorLinea(provisionDiaSgteIdo);
                    pEntity.setValorDiferencias(diferenciadiasgte);
                    pEntity.setFechaSistema(new Date());
                    pEntity.setAplicado(Constantes.PARAM_INFORME_DIFERENCIAS_NO);
                    pEntity.setAumento(0L);
                    pEntity.setDisminucion(0L);
                    pEntity.setFaltante(0L);
                    pEntity.setSobrante(0L);

                    if (diferenciadiasgte < limiteDiferencia) {

                        imprime = true;
                    } else {
                        //diferenciadiasgte!=0 
                        if (diferenciadiasgte > 0) {

                            imprime = true;
                        }
                        if (sumaRevProvision > 0) {

                            sumaRevProvision = 0;
                            imprime = true;
                        }
                        //valido mayores a
                        if (diferenciadiasgte < limiteDiferenciaMax) {

                            imprime = false;
                        }
                    }
                    if (imprime) {
                        reporte.add(pEntity);
                        imprime = false;
                    }

                }
                //TERMINA LLENO EL ULTIMO REGISTRO

            } catch (Exception ex) {
                loggerApp.severe("Error al generar reporte de diferencias: " + ex.getMessage());
            }

        }

        return reporte;
    }

}
