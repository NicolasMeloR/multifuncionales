/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.cuadreCajeros;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.constantes.TipoMovimientoCuadre;
import com.davivienda.sara.cuadrecifras.general.helper.CuadreCifrasHelperInterface;
import com.davivienda.sara.cuadrecifras.general.bean.DatosMovimientoCajeroBean;
import com.davivienda.sara.cuadrecifras.general.bean.InformeAyerBean;
import com.davivienda.sara.cuadrecifras.general.bean.InformeDiferenciasBean;
import com.davivienda.sara.cuadrecifras.general.bean.MovimientoCuadreCifrasBean;
import com.davivienda.sara.cuadrecifras.general.helper.CuadreCifrasInformeServletHelper;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasSessionLocal;
import com.davivienda.sara.cuadrecifras.session.InformeDiferenciasSessionLocal;
import com.davivienda.sara.dto.CajeroDTO;
import com.davivienda.sara.dto.ConsultarAjustesDTO;
import com.davivienda.sara.dto.OccaDTO;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.InformeDiferencias;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.procesos.ajustes.session.AjustesProcesosSessionLocal;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.occa.session.OccaSessionLocal;
import com.davivienda.sara.tablas.provisionhost.session.ProvisionHostLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.Numero;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author jmcastel
 */
@ManagedBean(name = "cuadreCifrasBean")
@ViewScoped
public class CuadreCifrasBean extends BaseBean implements Serializable {

    @EJB
    CajeroSessionLocal cajeroSession;

    @EJB
    OccaSessionLocal occaSession;

    @EJB
    CuadreCifrasSessionLocal sessionCuadreCifras;

    @EJB
    CuadreCifrasSessionLocal session;

    @EJB
    ProvisionHostLocal provisionHostsession;

    @EJB
    ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSessionLocal;

    @EJB
    InformeDiferenciasSessionLocal diferenciasSessionLocal;

    @EJB
    AjustesProcesosSessionLocal sessionAjustes;

    private long limiteDiferencia = -1000;
    private long limiteDiferenciaMax = 1000;
    private List<SelectItem> cajeros = new ArrayList<SelectItem>();
    private List<ConsultarAjustesDTO> ajustes = new ArrayList<ConsultarAjustesDTO>();
    private List<SelectItem> listaOcca;
    private String cajeroSeleccionado;
    private String occaSeleccionado;
    private String fecha;
    private boolean mostrarPanelConsulta;
    private boolean mostrarInformeCuadre;
    private Collection<MovimientoCuadreCifrasBean> itemsFormateados = null;
    private Collection<InformeDiferenciasBean> itemsInformeDiferenciasBean = null;
    private Map<Integer, InformeDiferencias> mapaItemsInfoDiferencias = new HashMap<Integer, InformeDiferencias>(0);
    private boolean mostrarInformeTimbres;

    public ComponenteAjaxObjectContextWeb objectContext;

    @PostConstruct
    public void CuadreCifrasBean() {
        try {
            List<CajeroDTO> cajerosItems = new ArrayList<CajeroDTO>();
            objectContext = cargarComponenteAjaxObjectContext();
            this.setMostrarInformeCuadre(false);
            this.setMostrarPanelConsulta(true);
            this.setMostrarInformeTimbres(false);

            if (objectContext != null) {
                Collection<Cajero> itemsCaj;
                itemsCaj = cajeroSession.getTodosActivos(0, 5000);
                cajerosItems = objectContext.getCajeroCB(itemsCaj);
                this.setCajeros(cargarListaCajeros(cajerosItems));
                Collection<Occa> itemsOcca = occaSession.getTodos();
                this.setListaOcca(cargarListaOcca(objectContext.getOccaCB(itemsOcca)));
            }
            dataInicial();
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void dataInicial() {
        this.fecha = "";
        this.cajeroSeleccionado = "";
        this.occaSeleccionado = "";
        this.mostrarInformeCuadre = false;
        this.mostrarPanelConsulta = true;
        this.setMostrarInformeCuadre(false);
        this.setMostrarPanelConsulta(true);
        this.setMostrarInformeTimbres(false);
        itemsInformeDiferenciasBean = null;
        itemsFormateados = null;

    }

    public Collection<MovimientoCuadreCifrasBean> cuadrarCajero() {

        Collection<MovimientoCuadre> items = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Calendar fechaInicial = null;
            Calendar fechaFinal = null;
            Integer codigoCajero = 0;
            try {
                Date fechaI = formatter.parse(this.fecha);
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);
            } catch (Exception ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Cadena.aDate(this.fecha, FormatoFecha.AAAAMMDD));
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return null;
            }
            fechaFinal = fechaInicial;

            if (codigoCajero == 0) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return null;
            }

            try {
                String[] splitCaj = buscarCodCaj(this.cajeroSeleccionado).split("-");
                codigoCajero = Integer.parseInt(splitCaj[0].trim());
            } catch (Exception ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return null;
            }

            try {
                // Consulta los registros según los parámetros tomados del request
                items = sessionCuadreCifras.getMovimientoCuadreCifrasMostrar(codigoCajero, fechaInicial, fechaFinal);
                itemsFormateados = getBeansMovimientoCuadreCifras(items);

            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //abrirModal("SARA", "Error Consultando Entitys", ex);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return null;
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //abrirModal("SARA", "Error consultando Entitys", null);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return null;
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                    return null;
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                return null;
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            return null;
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
            return null;
        }

        this.mostrarInformeCuadre = true;
        this.mostrarPanelConsulta = false;
        return itemsFormateados;
    }

    public Collection<MovimientoCuadreCifrasBean> informeCuadre() {

        try {
            Integer codigoOcca = 0;
            Calendar fechaInicial = null;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date fechaI = formatter.parse(this.fecha);
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);
            } catch (Exception ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Cadena.aDate(this.fecha, FormatoFecha.AAAAMMDD));
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return null;
            }

            if (codigoOcca == null || codigoOcca == 0) {
                codigoOcca = -1;
            } else {
                String[] splitOcca = buscarCodOcca(this.occaSeleccionado).split("-");
                codigoOcca = Integer.parseInt(splitOcca[0].trim());
            }

            CuadreCifrasHelperInterface cuadreCifrasHelper = null;

            objectContext.getConfigApp().loggerApp.log(Level.INFO, "informeCuadre fecha:" + fecha);
            objectContext.getConfigApp().loggerApp.log(Level.INFO, "informeCuadre codigoCajero:" + 9999);
            objectContext.getConfigApp().loggerApp.log(Level.INFO, "informeCuadre codigoOcca:" + codigoOcca);

            cuadreCifrasHelper = new CuadreCifrasInformeServletHelper(sessionCuadreCifras, procesoCuadreCifrasSessionLocal);

            objectContext.getConfigApp().loggerApp.log(Level.INFO, "informeCuadre cuadreCifrasHelper:" + cuadreCifrasHelper);

            if (cuadreCifrasHelper != null) {
                itemsFormateados = cuadreCifrasHelper.obtenerDatosCollectionCC(fechaInicial, codigoOcca);

                if (null != itemsFormateados && !itemsFormateados.isEmpty()) {
                    this.mostrarInformeCuadre = true;
                    this.mostrarPanelConsulta = false;
                } else {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                    return null;
                }

            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
            return null;
        }

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

    public void generarInforme() {
        try {
            Calendar fechaInicial = null;
            Calendar fechaFinal = null;
            Integer codigoCajero = null;
            Integer codigoOcca = 0;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date fechaI = formatter.parse(this.fecha);
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);

            } catch (Exception ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }

            codigoOcca = Integer.parseInt(this.occaSeleccionado);
            if (codigoOcca == 0) {
                codigoOcca = -1;
            }

            //este codigo es para consultar todos lo cajeros      
            codigoCajero = 9999;

            Collection<InformeAyerBean> itemsProvisionAyer = null;

            java.util.Collection<com.davivienda.sara.entitys.MovimientoCuadre> items = session.getMovimientoCuadreCifras(codigoCajero, fechaInicial, fechaInicial);
            java.util.Collection<com.davivienda.sara.entitys.ProvisionHost> itemsProvision = provisionHostsession.getProvisionHostRangoFecha(fechaInicial, com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial));
            itemsProvisionAyer = getCollectionInformeCuadreAyer(items, itemsProvision, com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, FormatoFecha.AAAAMMDD), codigoOcca);

            if (null != itemsProvisionAyer && !itemsProvisionAyer.isEmpty() && itemsProvisionAyer.size() > 0) {
                generarArchivoXLS(itemsProvisionAyer, items, codigoOcca);
            } else {
                abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
            }
        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                //abrirModal("SARA", "Error Consultando Entitys", ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                return;
            }
            if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                //abrirModal("SARA", "Error consultando Entitys", null);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                return;
            }
            if (ex.getLocalizedMessage().contains("NoResultException")) {
                abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                return;
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
            return;
        }
    }

    public void generarArchivoXLS(Collection<InformeAyerBean> itemsProvisionAyer, java.util.Collection<com.davivienda.sara.entitys.MovimientoCuadre> regs, Integer codigoOcca) {
        String respuesta = "";
        Integer tipoMovimientoCuadre = 0;
        Integer codigoCajero = 0;
        Integer codigoCajeroOcca = 0;
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

        if (itemsProvisionAyer != null) {
            for (InformeAyerBean item : itemsProvisionAyer) {

                if (!mapCajeroEfectivoAtm.containsKey((Integer) item.getCodigoCajero())) {
                    mapCajeroEfectivoAtm.put(item.getCodigoCajero(), item.getNumericoefectivoAtm());

                }
            }
        }

        // 
        if (regs != null) {

            //Creo la hoja de cálculo
            String[] titulosHoja = TituloCuadreCifrasGeneral.tituloHoja;
            String[] titulosColumna = TituloCuadreCifrasGeneral.tituloColumnas;
            //CAJERO", "NOMBRE", "TRANSACCION", "FECHA", "TALON", "CUENTA", "TARJETA", "VALOR ENTREGADO","REFERENCIA","CODIGO ERROR","CODIGO TERMINACION"
            Collection<Registro> lineas = new ArrayList<Registro>();
            Short numColumna;
            boolean imprime = false;

            try {
                for (MovimientoCuadre item : regs) {
                    codigoOccaRegistro = item.getCajero().getOcca().getCodigoOcca();

                    if ((codigoOccaRegistro.compareTo(codigoOcca) == 0) || codigoOcca == -1) {
                        if (primerCodigo == false) {
                            titulosHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFecha(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
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
                                Registro reg = new Registro();
                                numColumna = 0;
                                reg.addCelda(new Celda(numColumna++, codigoCajero, TipoDatoCelda.NUMERICO));
                                reg.addCelda(new Celda(numColumna++, codigoCajeroOcca, TipoDatoCelda.NUMERICO));
                                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(provisionDiaSgte), TipoDatoCelda.MONEDA));
                                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(provisionDiaSgteIdo), TipoDatoCelda.MONEDA));
                                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(diferenciadiasgte), TipoDatoCelda.MONEDA));
                                reg.addCelda(new Celda(numColumna++, observacion, TipoDatoCelda.MONEDA));

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
                                    lineas.add(reg);
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
                    Registro reg = new Registro();
                    numColumna = 0;
                    reg.addCelda(new Celda(numColumna++, codigoCajero, TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, codigoCajeroOcca, TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(provisionDiaSgte), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(provisionDiaSgteIdo), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(diferenciadiasgte), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, observacion, TipoDatoCelda.MONEDA));

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
                        lineas.add(reg);
                        imprime = false;
                    }

                }
                //TERMINA LLENO EL ULTIMO REGISTRO
                ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("CuadreDiario", titulosHoja, titulosColumna, lineas);
                objectContext.enviarArchivoXLS(archivo);
            } catch (IllegalArgumentException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", "Parametro Invalido", ex);

            } catch (IOException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", "Archivo No existe", ex);
            } catch (Exception ex) {
                abrirModal("SARA", "Error no defeinido", ex);
            }

        }
    }

    public Collection<InformeAyerBean> getCollectionInformeCuadreAyer(Collection<MovimientoCuadre> regs, java.util.Collection<com.davivienda.sara.entitys.ProvisionHost> itemsProvision, String fechaInicial, Integer codigoOcca) {
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
             if (informe.getNumericoefectivoAtm().equals(new Long(0))) {
                    informe.setProvisionEfectivo(new Long(0));
                    informe.setefectivoAtm(informe.getNumericopagado());

                } else {
                    informe.setProvisionEfectivo(informe.getNumericopagado() + informe.getNumericoefectivoAtm());
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

    public void informeAyer() {
        try {
            Calendar fechaInicial = null;
            Integer codigoCajero = null;
            Integer codigoOcca = 0;
            Collection<InformeAyerBean> itemsFormateados = null;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date fechaI = formatter.parse(this.fecha);
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);
            } catch (Exception ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }

            codigoOcca = Integer.parseInt(this.occaSeleccionado);
            if (codigoOcca == 0) {
                codigoOcca = -1;
            }

            codigoCajero = 9999;

            java.util.Collection<com.davivienda.sara.entitys.MovimientoCuadre> items = session.getMovimientoCuadreCifras(codigoCajero, fechaInicial, fechaInicial);
            java.util.Collection<com.davivienda.sara.entitys.ProvisionHost> itemsProvision = provisionHostsession.getProvisionHostRangoFecha(fechaInicial, com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial));
            itemsFormateados = getCollectionInformeCuadreAyer(items, itemsProvision, com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, FormatoFecha.AAAAMMDD), codigoOcca);

            if (null != itemsFormateados && !itemsFormateados.isEmpty() && itemsFormateados.size() > 0) {
                generarArchivoXLSAyer(itemsFormateados, fechaInicial);
            } else {
                abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
            }
        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                //abrirModal("SARA", "Error Consultando Entitys", ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                return;
            }
            if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                //abrirModal("SARA", "Error consultando Entitys", null);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                return;
            }
            if (ex.getLocalizedMessage().contains("NoResultException")) {
                abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void generarArchivoXLSAyer(Collection<InformeAyerBean> regs, Calendar fechaInicial) {
        String respuesta = "";
        if (regs != null) {

            //Creo la hoja de cálculo
            String[] titulosHoja = TituloInformeAyerGeneral.tituloHoja;
            String[] titulosColumna = TituloInformeAyerGeneral.tituloColumnas;
            titulosHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);

            Collection<Registro> lineas = new ArrayList<Registro>();
            Short numColumna;
            Long provisionEfectivo;

            try {
                for (InformeAyerBean item : regs) {

                    Registro reg = new Registro();
                    numColumna = 0;

                    if (!item.getNumericoProvisionAyer().equals(new Long(-1))) {
                        provisionEfectivo = item.getNumericoProvisionAyer();
                    } else {
                        provisionEfectivo = item.getNumericoProvisionEfectivo();
                    }
                    reg.addCelda(new Celda(numColumna++, item.getCodigoOcca(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getNombreOcca(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getCodigoCajero(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getNumericopagado()), TipoDatoCelda.MONEDA));
                    //CAMBIA
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(provisionEfectivo), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getNumericoefectivoAtm()), TipoDatoCelda.MONEDA));
                    //CAMBIA
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(provisionEfectivo - item.getNumericopagado()), TipoDatoCelda.MONEDA));
                    lineas.add(reg);

                }

                ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("InformeAyer", titulosHoja, titulosColumna, lineas);
                objectContext.enviarArchivoXLS(archivo);
            } catch (IllegalArgumentException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", "Parametro Valido", ex);
            } catch (IOException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", "Archivo no Existe", ex);
            } catch (Exception ex) {
                abrirModal("SARA", "Error no definido", ex);
            }

        }
    }

    public void visualizarInforme() {
        objectContext.getConfigApp().loggerApp.log(Level.INFO, "CuadreCifrasBean visualizarInforme");

        mapaItemsInfoDiferencias.clear();
        try {

            try {

                // Consulta la tabla Informe Diferencias
                consultarItemsInformeDiferenciasBean();

            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //abrirModal("SARA", "Error Consultando Entitys", ex);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return;
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //abrirModal("SARA", "Error consultando Entitys", null);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return;
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    public void guardarInformeDiferencias() {
        objectContext.getConfigApp().loggerApp.log(Level.INFO, "CuadreCifrasBean guardarInformeDiferencias");
        try {

            if (validarCamposInformeDiferencias()) {
                for (InformeDiferenciasBean item : itemsInformeDiferenciasBean) {
                    actualizarInformeDiferencias(item, false);
                }
                // Consulta la tabla Informe Diferencias
                consultarItemsInformeDiferenciasBean();
                abrirModal("SARA", "Se ha guardado exitosamente", null);
            } else {
                abrirModal("SARA", "No se permiten campos vacios", null);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Error al guardar", ex);
        }
    }

    public void actualizarInformeDiferencias(InformeDiferenciasBean item, boolean esStratus) throws EntityServicioExcepcion {
        InformeDiferencias obj = (InformeDiferencias) mapaItemsInfoDiferencias.get(item.id.intValue());
        if (null != obj) {

            if (item.getAumentoLong() >= 0) {
                obj.setAumento(item.getAumentoLong());
            }
            if (item.getDisminucionLong() >= 0) {
                obj.setDisminucion(item.getDisminucionLong());
            }
            if (item.getFaltanteLong() >= 0) {
                obj.setFaltante(item.getFaltanteLong());
            }
            if (item.getSobranteLong() >= 0) {
                obj.setSobrante(item.getSobranteLong());
            }

            if (esStratus) {
                obj.setAplicado(obj.getAplicado());
            } else {
                obj.setAplicado(Constantes.PARAM_INFORME_DIFERENCIAS_NO);
            }

            diferenciasSessionLocal.actualizar(obj);
        }
    }

    public boolean validarCamposInformeDiferencias() {
        boolean valido = true;

        for (InformeDiferenciasBean item : itemsInformeDiferenciasBean) {
            if (null == item.getAumento() || item.getAumento().isEmpty() || item.getAumento().trim().length() == 0) {
                valido = false;
                break;
            }
            if (null == item.getDisminucion() || item.getDisminucion().isEmpty() || item.getDisminucion().trim().length() == 0) {
                valido = false;
                break;
            }
            if (null == item.getFaltante() || item.getFaltante().isEmpty() || item.getFaltante().trim().length() == 0) {
                valido = false;
                break;
            }
            if (null == item.getSobrante() || item.getSobrante().isEmpty() || item.getSobrante().trim().length() == 0) {
                valido = false;
                break;
            }
        }

        return valido;
    }

    public void stratusInformeDiferencias() {
        try {
            if (validarCamposInformeDiferencias()) {
                Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
                InitBean initBean = (InitBean) viewMap.get("initBean");
                String usuario = initBean.getObjectContext().getUsuarioEnSesion().getUsuario();
                String respuesta = "";
                BigDecimal smallBigDecimal = new BigDecimal(0);
                BigDecimal bigBigDecimal = new BigDecimal(999999999);
                BigDecimal valorAjuste = new BigDecimal(0);
                int cantidadAumentos = 0;
                int cantidadAumentosExitosos = 0;

                int cantidadDisminuciones = 0;
                int cantidadDisminucionesExitosos = 0;

                int cantidadPorFaltantes = 0;
                int cantidadPorFaltantesExitosos = 0;

                int cantidadPorSobrantes = 0;
                int cantidadPorSobrantesExitosos = 0;

                
                for (InformeDiferenciasBean item : itemsInformeDiferenciasBean) {
                    boolean stratusAumentoExitoso = false;
                    boolean stratusDisminucionExitoso = false;
                    boolean stratusFaltanteExitoso = false;
                    boolean stratusSobranteExitoso = false;

                    if (item.getAplicado().equals(Constantes.PARAM_INFORME_DIFERENCIAS_NO)) {

                        if (item.getAumentoLong() > 0) {
                            
                            cantidadAumentos++;
                            valorAjuste = BigDecimal.valueOf(item.getAumentoLong());
                            if (smallBigDecimal.compareTo(valorAjuste) <= 0 && valorAjuste.compareTo(bigBigDecimal) <= 0) {
                                respuesta = sessionAjustes.realizarAjusteAumentoProvision(usuario, item.getCodigoCajero(), item.getCodigoOcca(), valorAjuste);

                                if (respuesta.substring(0, 1).equals("B")) {
                                    stratusAumentoExitoso = true;
                                    cantidadAumentosExitosos++;
                                }

                            }
                        }
                        if (item.getDisminucionLong() > 0) {
                            
                            cantidadDisminuciones++;
                            valorAjuste = BigDecimal.valueOf(item.getDisminucionLong());
                            if (smallBigDecimal.compareTo(valorAjuste) <= 0 && valorAjuste.compareTo(bigBigDecimal) <= 0) {
                                respuesta = sessionAjustes.realizarAjusteDisminucionProvision(usuario, item.getCodigoCajero(), item.getCodigoOcca(), valorAjuste);

                                if (respuesta.substring(0, 1).equals("B")) {
                                    stratusDisminucionExitoso = true;
                                    cantidadDisminucionesExitosos++;
                                }
                            }
                        }
                        if (item.getFaltanteLong() > 0) {
                            
                            cantidadPorFaltantes++;
                            valorAjuste = BigDecimal.valueOf(item.getFaltanteLong());
                            if (smallBigDecimal.compareTo(valorAjuste) <= 0 && valorAjuste.compareTo(bigBigDecimal) <= 0) {
                                respuesta = sessionAjustes.realizarAjustePorFaltante(usuario, item.getCodigoCajero(), item.getCodigoOcca(), valorAjuste);

                                if (respuesta.substring(0, 1).equals("B")) {
                                    stratusFaltanteExitoso = true;
                                    cantidadPorFaltantesExitosos++;
                                }
                            }
                        }
                        if (item.getSobranteLong() > 0) {
                            
                            cantidadPorSobrantes++;
                            valorAjuste = BigDecimal.valueOf(item.getSobranteLong());
                            if (smallBigDecimal.compareTo(valorAjuste) <= 0 && valorAjuste.compareTo(bigBigDecimal) <= 0) {
                                respuesta = sessionAjustes.realizarAjustePorSobrante(usuario, item.getCodigoCajero(), item.getCodigoOcca(), valorAjuste);

                                if (respuesta.substring(0, 1).equals("B")) {
                                    stratusSobranteExitoso = true;
                                    cantidadPorSobrantesExitosos++;
                                    
                                }
                            }
                        }

                        if (stratusAumentoExitoso && stratusDisminucionExitoso && stratusFaltanteExitoso && stratusSobranteExitoso) {
                            item.setAplicado(Constantes.PARAM_INFORME_DIFERENCIAS_SI);
                        }
                    }
                    actualizarInformeDiferencias(item, true);
                }
                consultarItemsInformeDiferenciasBean();
                
                String stratusProceso = "";
                if(cantidadAumentos > 0){
                    stratusProceso += "\nTotal Aumentos: " + cantidadAumentos;
                    stratusProceso += "\nExitosas: " + cantidadAumentosExitosos;
                    stratusProceso += "\nFallidas: " + (cantidadAumentos-cantidadAumentosExitosos);
                    stratusProceso += "\n";
                }
                
                if(cantidadDisminuciones > 0){
                    stratusProceso += "\nTotal Disminuciones: " + cantidadDisminuciones;
                    stratusProceso += "\nExitosas: " + cantidadDisminucionesExitosos;
                    stratusProceso += "\nFallidas: " + (cantidadDisminuciones-cantidadDisminucionesExitosos);
                    stratusProceso += "\n";
                }
                
                if(cantidadPorFaltantes > 0){
                    stratusProceso += "\nTotal Por Faltantes: " + cantidadPorFaltantes;
                    stratusProceso += "\nExitosas: " + cantidadPorFaltantesExitosos;
                    stratusProceso += "\nFallidas: " + (cantidadPorFaltantes-cantidadPorFaltantesExitosos);
                    stratusProceso += "\n";
                }
                
                if(cantidadPorSobrantes > 0){
                    stratusProceso += "\nTotal Por Sobrantes: " + cantidadPorSobrantes;
                    stratusProceso += "\nExitosas: " + cantidadPorSobrantesExitosos;
                    stratusProceso += "\nFallidas: " + (cantidadPorSobrantes-cantidadPorSobrantesExitosos);
                    stratusProceso += "\n";
                }
                
                abrirModal("SARA", stratusProceso, null);
            } else {
                abrirModal("SARA", "No se permiten campos vacios", null);
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Error al aplicar Stratus", ex);
        }
    }

    public void imprimirInformeDiferencias() {
        try {
            String[] titulosHoja = TituloCuadreCifrasGeneral.tituloHoja;
            titulosHoja[0] = TituloCuadreCifrasGeneral.tituloHojaDiferencias;

            String[] titulosColumna = TituloCuadreCifrasGeneral.tituloColumnasDiferencias;
            Collection<Registro> lineas = new ArrayList<Registro>();
            Short numColumna;
            for (InformeDiferenciasBean item : this.itemsInformeDiferenciasBean) {
                Registro reg = new Registro();
                numColumna = 0;
                reg.addCelda(new Celda(numColumna++, item.getCodigoCajero(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getCodigoOcca(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getValorMaquina(), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, item.getValorLinea(), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, item.getValorDiferencias(), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getAumentoLong()), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getDisminucionLong()), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getFaltanteLong()), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getSobranteLong()), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, item.getAplicado(), TipoDatoCelda.NORMAL));

                lineas.add(reg);
            }

            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("InformeDiferencias", titulosHoja, titulosColumna, lineas);
            objectContext.enviarArchivoXLS(archivo);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Error al generar archivo ", ex);
        }
    }

    public Collection<InformeDiferenciasBean> getCollectionInformeDiferenciasBean(Collection<InformeDiferencias> items) {
        Collection<InformeDiferenciasBean> resp = new ArrayList<InformeDiferenciasBean>(0);
        mapaItemsInfoDiferencias = new HashMap<Integer, InformeDiferencias>(0);
        int cont = 1;
        for (InformeDiferencias info : items) {
            InformeDiferenciasBean idb = new InformeDiferenciasBean();

            idb.setCodigoCajero(info.getInformeDiferenciasPK().getCodigoCajero());
            idb.setCodigoOcca(info.getCodigoOcca());
            idb.setValorMaquina(Numero.aMoneda(info.getValorMaquina()));
            idb.setValorLinea(Numero.aMoneda(info.getValorLinea()));
            idb.setValorDiferencias(Numero.aMoneda(info.getValorDiferencias()));
            idb.setAplicado(info.getAplicado());

            idb.setAumento(Numero.aFormatoMiles(info.getAumento()));
            idb.setDisminucion(Numero.aFormatoMiles(info.getDisminucion()));
            idb.setFaltante(Numero.aFormatoMiles(info.getFaltante()));
            idb.setSobrante(Numero.aFormatoMiles(info.getSobrante()));

            idb.id = BigInteger.valueOf(cont);
            resp.add(idb);
            mapaItemsInfoDiferencias.put(cont, info);
            cont++;
        }

        return resp;

    }

    private List<SelectItem> cargarListaOcca(List<OccaDTO> occaCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem(0, "Seleccione una");
        lista.add(item);
        int cont = 1;
        for (OccaDTO dto : occaCB) {
            item = new SelectItem(cont, dto.getCodigoOcca() + " - " + dto.getNombre());
            lista.add(item);
            cont++;
        }
        return lista;
    }

    private List<SelectItem> cargarListaCajeros(List<CajeroDTO> cajerosCB) {
        int cont = 1;
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem item1 = new SelectItem(0, "Seleccione uno ...");
        lista.add(item1);

        for (CajeroDTO dto : cajerosCB) {
            SelectItem item = new SelectItem(cont, String.valueOf(dto.getCodigoCajero()) + "-" + dto.getNombre());
            lista.add(item);
            cont++;
        }
        return lista;
    }

    public String buscarCodOcca(String cod) {
        String nomOcca = "";
        for (SelectItem usr : listaOcca) {
            if (cod.equalsIgnoreCase(String.valueOf(usr.getValue()))) {
                nomOcca = String.valueOf(usr.getLabel());
                break;
            }
        }
        return nomOcca;
    }

    public String buscarCodCaj(String cod) {
        String codCaj = "";
        for (SelectItem usr : cajeros) {
            if (cod.equalsIgnoreCase(String.valueOf(usr.getValue()))) {
                codCaj = String.valueOf(usr.getLabel());
                break;
            }
        }
        return codCaj;
    }

    /**
     * @return the cajeros
     */
    public List<SelectItem> getCajeros() {
        return cajeros;
    }

    /**
     * @param cajeros the cajeros to set
     */
    public void setCajeros(List<SelectItem> cajeros) {
        this.cajeros = cajeros;
    }

    /**
     * @return the ajustes
     */
    public List<ConsultarAjustesDTO> getAjustes() {
        return ajustes;
    }

    /**
     * @param ajustes the ajustes to set
     */
    public void setAjustes(List<ConsultarAjustesDTO> ajustes) {
        this.ajustes = ajustes;
    }

    /**
     * @return the listaOcca
     */
    public List<SelectItem> getListaOcca() {
        return listaOcca;
    }

    /**
     * @param listaOcca the listaOcca to set
     */
    public void setListaOcca(List<SelectItem> listaOcca) {
        this.listaOcca = listaOcca;
    }

    /**
     * @return the cajeroSeleccionado
     */
    public String getCajeroSeleccionado() {
        return cajeroSeleccionado;
    }

    /**
     * @param cajeroSeleccionado the cajeroSeleccionado to set
     */
    public void setCajeroSeleccionado(String cajeroSeleccionado) {
        this.cajeroSeleccionado = cajeroSeleccionado;
    }

    /**
     * @return the occaSeleccionado
     */
    public String getOccaSeleccionado() {
        return occaSeleccionado;
    }

    /**
     * @param occaSeleccionado the occaSeleccionado to set
     */
    public void setOccaSeleccionado(String occaSeleccionado) {
        this.occaSeleccionado = occaSeleccionado;
    }

    /**
     * @return the itemsFormateados
     */
    public Collection<MovimientoCuadreCifrasBean> getItemsFormateados() {
        return itemsFormateados;
    }

    /**
     * @param itemsFormateados the itemsFormateados to set
     */
    public void setItemsFormateados(Collection<MovimientoCuadreCifrasBean> itemsFormateados) {
        this.itemsFormateados = itemsFormateados;
    }

    /**
     * @return the mostrarPanelConsulta
     */
    public boolean isMostrarPanelConsulta() {
        return mostrarPanelConsulta;
    }

    /**
     * @param mostrarPanelConsulta the mostrarPanelConsulta to set
     */
    public void setMostrarPanelConsulta(boolean mostrarPanelConsulta) {
        this.mostrarPanelConsulta = mostrarPanelConsulta;
    }

    /**
     * @return the mostrarInformeCuadre
     */
    public boolean isMostrarInformeCuadre() {
        return mostrarInformeCuadre;
    }

    /**
     * @param mostrarInformeCuadre the mostrarInformeCuadre to set
     */
    public void setMostrarInformeCuadre(boolean mostrarInformeCuadre) {
        this.mostrarInformeCuadre = mostrarInformeCuadre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isMostrarInformeTimbres() {
        return mostrarInformeTimbres;
    }

    public void setMostrarInformeTimbres(boolean mostrarInformeTimbres) {
        this.mostrarInformeTimbres = mostrarInformeTimbres;
    }

    public Collection<InformeDiferenciasBean> getItemsInformeDiferenciasBean() {
        return itemsInformeDiferenciasBean;
    }

    public void setItemsInformeDiferenciasBean(Collection<InformeDiferenciasBean> itemsInformeDiferenciasBean) {
        this.itemsInformeDiferenciasBean = itemsInformeDiferenciasBean;
    }

    private void consultarItemsInformeDiferenciasBean() throws ParseException, EntityServicioExcepcion {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar fechaInicial = null;
        Calendar fechaFinal = null;
        Integer codigoCajero = 0;
        itemsInformeDiferenciasBean = null;
        mapaItemsInfoDiferencias.clear();
        try {
            Date fechaI = formatter.parse(this.fecha);
            fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);
        } catch (Exception ex) {
            //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();
            abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
            return;
        }
        fechaFinal = fechaInicial;

        try {
            String[] splitCaj = buscarCodCaj(this.cajeroSeleccionado).split("-");
            codigoCajero = Integer.parseInt(splitCaj[0].trim());

        } catch (Exception ex) {
            codigoCajero = 9999;
        }
        objectContext.getConfigApp().loggerApp.log(Level.INFO, "InformeDiferencias codigoCajero: " + codigoCajero);
        objectContext.getConfigApp().loggerApp.log(Level.INFO, "InformeDiferencias fechaInicial: " + fechaInicial.getTime());
        objectContext.getConfigApp().loggerApp.log(Level.INFO, "InformeDiferencias fechaFinal: " + fechaFinal.getTime());

        Collection<InformeDiferencias> items = diferenciasSessionLocal.getInformeDiferenciaXFecha(codigoCajero, fechaInicial, fechaFinal);
        objectContext.getConfigApp().loggerApp.log(Level.INFO, "InformeDiferencias items: " + items.size());
        if (null == items || items.size() == 0) {
            abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
        } else {
            this.itemsInformeDiferenciasBean = getCollectionInformeDiferenciasBean(items);
            this.mostrarInformeTimbres = true;
            this.mostrarPanelConsulta = false;
        }
    }

}
