/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.diarioelectronico;

import com.davivienda.sara.dto.DiarioElectronicoTransaccionDTO;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.dto.CajerosSinTransmisionDTO;
import com.davivienda.sara.dto.HistoricoCargueDTO;
import com.davivienda.sara.dto.NumeroTransaccionesDTO;
import com.davivienda.sara.dto.TransaccionDTO;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.entitys.TransaccionTemp;
import com.davivienda.sara.entitys.transaccion.CantidadTransaccionesBean;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.estadisticas.EstadisticasGeneralesSessionLocal;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.diarioelectronico.session.DiarioElectronicoSessionLocal;
import com.davivienda.sara.tablas.edccargue.session.EdcCargueSessionLocal;
import com.davivienda.sara.tablas.transaccion.session.TransaccionSessionLocal;
import com.davivienda.sara.tablas.transacciontemp.session.TransaccionTempSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.edc.Edc;
import java.io.IOException;
import java.io.Serializable;
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
@ManagedBean(name = "transaccionBean")
@ViewScoped
public class TransaccionBean extends BaseBean implements Serializable {

    @EJB
    DiarioElectronicoSessionLocal sessionDiarioElectronico;

    @EJB
    TransaccionSessionLocal sessionTransaccion;

    @EJB
    TransaccionTempSessionLocal transaccionTempSession;

    @EJB
    EdcCargueSessionLocal sessionHistoricoCargue;

    @EJB
    EstadisticasGeneralesSessionLocal sessionNumTransacciones;

    @EJB
    CajeroSessionLocal sessionSinTransmitir;

    public ComponenteAjaxObjectContextWeb objectContext;
    private String codigoCajero;
    private String fechaInicial;
    private String fechaFinal;
    private String horaInicial;
    private String horaFinal;
    private String talon;
    private String referencia;
    private String cuenta;
    private String tarjeta;
    public Date fechaInicialReporte = null;

    private boolean mostrarPanelGeneral;
    private boolean mostrarDiarioElect;
    private boolean mostrarTransacciones;
    private boolean mostrarHistorial;
    private boolean mostrarNumTrasaccioes;
    private boolean mostrarCajSinTransmision;

    private List<SelectItem> listaHora;
    private List<TransaccionDTO> transacciones;
    private List<HistoricoCargueDTO> historicosCargue;
    private List<NumeroTransaccionesDTO> numTransacciones;
    private List<CajerosSinTransmisionDTO> cajerosSinTransamision;
    private Collection<TransaccionTemp> itemsDTemp = null;
    private Logger loggerApp;

    private Collection<DiarioElectronicoTransaccionDTO> itemsFormateados = null;

    @PostConstruct
    public void TransaccionBean() {
        objectContext = cargarComponenteAjaxObjectContext();
        listaHora = new ArrayList<SelectItem>();
        this.transacciones = new ArrayList<TransaccionDTO>();
        this.historicosCargue = new ArrayList<HistoricoCargueDTO>();
        this.numTransacciones = new ArrayList<NumeroTransaccionesDTO>();
        this.cajerosSinTransamision = new ArrayList<CajerosSinTransmisionDTO>();
        dataInicial();
        loggerApp = objectContext.getConfigApp().loggerApp;
    }

    public void dataInicial() {
        this.fechaInicial = "";
        this.fechaFinal = "";
        this.horaInicial = "00:00:00";
        this.horaFinal = "23:59:59";
        this.codigoCajero = "0";
        this.cuenta = "";
        this.tarjeta = "";
        this.talon = "";
        this.referencia = "";
        this.listaHora = cargarListaHora();
        this.setMostrarPanelGeneral(true);
        this.setMostrarDiarioElect(false);
        this.setMostrarTransacciones(false);
        this.setMostrarHistorial(false);
        this.setMostrarNumTrasaccioes(false);
        this.setMostrarCajSinTransmision(false);
    }

    public String generarDiarioElectronico() {

        loggerApp.info("TransaccionBean-generarDiarioElectronico");

        String nombreArchivo = "";
        Calendar clFechaInicial;
        boolean registrosExiste = false;
        Collection<DiarioElectronico> items = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechaInicial = null;
            Integer codigoCajero = 0;
            try {
                fechaInicial = formatter.parse(this.fechaInicial);
            } catch (IllegalArgumentException | ParseException ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return null;
            }

            clFechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial);
            codigoCajero = Integer.parseInt(this.codigoCajero);

            if (codigoCajero == 0) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return null;
            }
            try {

                loggerApp.info("TransaccionBean-fechaInicial " + fechaInicial + " codigoCajero: " + codigoCajero);
                if ((Fecha.getFechaInicioDia(clFechaInicial).equals(
                        Fecha.getFechaInicioDia(Fecha.getCalendarAyer())))) {
                    nombreArchivo = Edc.obtenerNombreArchivo(codigoCajero, Fecha.getCalendarHoy());
                    items = sessionDiarioElectronico.getDiarioElectronicoDia(codigoCajero, Fecha.getCalendarHoy(), nombreArchivo);
                } else {
                    items = sessionDiarioElectronico.getDiarioElectronico(codigoCajero, fechaInicial);
                }
                if (null != items && !items.isEmpty()) {
                    registrosExiste = true;
                }
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
            //abrirModal("SARA", "Error consultando Entitys", ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            return null;
        }

        if (registrosExiste) {
            this.setItemsFormateados(setColeccionRegistroDiarioElectronico(items));
            this.setMostrarPanelGeneral(false);
            this.setMostrarDiarioElect(true);
            this.setMostrarTransacciones(false);
            this.setMostrarHistorial(false);
            this.setMostrarNumTrasaccioes(false);
            this.setMostrarCajSinTransmision(false);
        } else {
            abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
            return null;
        }
        return "";
    }

    public void generarTransacciones() {
        loggerApp.info("TransaccionBean-generarTransacciones");
        Collection<Transaccion> items = null;
        this.transacciones = new ArrayList<TransaccionDTO>(0);
        items = generarTransaccionesData();
        if (null != items && !items.isEmpty()) {
            loggerApp.info("TransaccionBean generarTransacciones items: " + items.size());
            this.getTransacciones().addAll(convertItemsTransaccion(items));
            this.setMostrarPanelGeneral(false);
            this.setMostrarDiarioElect(false);
            this.setMostrarTransacciones(true);
            this.setMostrarHistorial(false);
            this.setMostrarNumTrasaccioes(false);
            this.setMostrarCajSinTransmision(false);

        } else if (itemsDTemp != null) {
            loggerApp.info("TransaccionBean generarTransacciones itemsDTemp: " + this.itemsDTemp.size());
            this.getTransacciones().addAll(convertItemsTransaccionTemp(itemsDTemp));
            this.setMostrarPanelGeneral(false);
            this.setMostrarDiarioElect(false);
            this.setMostrarTransacciones(true);
            this.setMostrarHistorial(false);
            this.setMostrarNumTrasaccioes(false);
            this.setMostrarCajSinTransmision(false);
        } else {
            //abrirModal("SARA", "No hay registros para que cumplan con los criterios de la consulta",null);
        }
    }

    public Collection<Transaccion> generarTransaccionesData() {
        String error = "";
        Collection<Transaccion> itemsTransaccion = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date fechaInicial = null;
            Date fechaFinal = null;
            Integer codigoCajero = 0;
            Integer numeroTransaccion = 0;
            Calendar clFechaInicial = null;
            Map<String, Object> criterios = new HashMap<String, Object>();
            String strTmp = null;

            try {
                codigoCajero = Integer.parseInt(this.codigoCajero);
            } catch (IllegalArgumentException ex) {
                codigoCajero = 0;
            }
            try {
                fechaInicial = formatter.parse(this.fechaInicial);
                clFechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial);
            } catch (IllegalArgumentException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return null;
            } catch (ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                Logger.getLogger(TransaccionBean.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            try {
                fechaFinal = formatter.parse(this.fechaFinal);
            } catch (IllegalArgumentException ex) {
                fechaFinal = fechaInicial;
            } catch (ParseException ex) {
                fechaFinal = fechaInicial;
            }

            loggerApp.info("TransaccionBean generarTransaccionesData - fechaInicial " + fechaInicial + " fechaFinal " + fechaFinal + " codigoCajero: " + codigoCajero);
            try {
                numeroTransaccion = Integer.parseInt(this.getTalon());
            } catch (IllegalArgumentException ex) {
                numeroTransaccion = null;
            }

            try {
                strTmp = this.referencia;
                criterios.put("referencia", strTmp);
            } catch (IllegalArgumentException ex) {
                strTmp = "";
            }

            try {
                strTmp = this.cuenta;
                criterios.put("productoOrigen", strTmp);
            } catch (IllegalArgumentException ex) {
                strTmp = "";
            }

            try {
                strTmp = this.tarjeta;
                criterios.put("tarjeta", strTmp);
            } catch (IllegalArgumentException ex) {
                strTmp = "";
            }

            try {
                itemsTransaccion = sessionTransaccion.getColeccionTransaccion(codigoCajero, fechaInicial, fechaFinal, numeroTransaccion, criterios);
                if (itemsTransaccion.isEmpty() && (Fecha.getFechaInicioDia(clFechaInicial).equals(Fecha.getFechaInicioDia(Fecha.getCalendarAyer())))) {
                    setItemsDTemp(transaccionTempSession.getColeccionTransaccionTemp(codigoCajero, fechaInicial, fechaFinal, numeroTransaccion, criterios));
                }

                if (itemsTransaccion.isEmpty() && null == itemsDTemp) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                    return null;
                }
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //abrirModal("SARA", "Error consultando Entitys", null);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return null;
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //abrirModal("SARA", "Error Consultando Entitys", ex);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return null;
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    //abrirModal("SARA", "Registro no existe", ex);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return null;
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //abrirModal("SARA", "Error Consultando Entitys", ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                return null;
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //abrirModal("SARA", "Error Consultando Entitys", ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            return null;
        }
        return itemsTransaccion;
    }

    public void generarHistoricoCargue() {
        
        loggerApp.info("TransaccionBean-generarHistoricoCargue");
        Collection<EdcCargue> items = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.historicosCargue = new ArrayList<HistoricoCargueDTO>(0);
        try {
            Date fechaInicial = null;
            Date fechaFinal = null;

            try {
                fechaInicial = formatter.parse(this.fechaInicial);

            } catch (IllegalArgumentException ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            } catch (ParseException ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }
            try {
                fechaFinal = formatter.parse(this.fechaFinal);
            } catch (IllegalArgumentException ex) {
                fechaFinal = fechaInicial;
            } catch (ParseException ex) {
                fechaFinal = fechaInicial;
            }
            fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);

            try {
                // Consulta los registros según los parámetros tomados del request
                items = sessionHistoricoCargue.getEDCCarguePorFecha(fechaInicial, fechaFinal);
                loggerApp.info("generarHistoricoCargue items " + items.size());
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return;
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return;
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return;
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                return;
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            return;
        }
        if (items == null || items.isEmpty()) {
            abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
            return;
        } else {
            this.getHistoricosCargue().addAll(convertItemsHistorico(items));
            this.setMostrarPanelGeneral(false);
            this.setMostrarDiarioElect(false);
            this.setMostrarTransacciones(false);
            this.setMostrarHistorial(true);
            this.setMostrarNumTrasaccioes(false);
            this.setMostrarCajSinTransmision(false);
        }

        loggerApp.info("generarHistoricoCargue Fin");

    }

    public void generarNumTrasacciones() {
        loggerApp.info("TransaccionBean-generarNumTrasacciones");
        String error = "";
        String respuesta = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Collection<CantidadTransaccionesBean> itemsNumeroTransacciones = null;
        try {

            Date fechaInicial = null;
            Date fechaFinal = null;
            Integer codigoCajero = 0;

            codigoCajero = Integer.parseInt(this.codigoCajero);
            if (codigoCajero == 0) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return;
            }

            try {
                fechaInicial = this.getAtributoFechaHoraInicial().getTime();
            } catch (IllegalArgumentException ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }
            
            if(null == this.fechaFinal || this.fechaFinal.isEmpty()){
                fechaFinal = this.getAtributoFechaInicialHoraFinal().getTime();
            }else{
                try {
                    fechaFinal = this.getAtributoFechaHoraFinal().getTime();
                } catch (IllegalArgumentException ex) {
                    fechaFinal = this.getAtributoFechaHoraFinal().getTime();
                }
                fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
            }
            try {
                // Consulta los registros según los parámetros tomados del request
                loggerApp.info("TransaccionBean generarNumTrasacciones - fechaInicial " + fechaInicial + " fechaFinal " + fechaFinal + " codigoCajero: " + codigoCajero);
           
                itemsNumeroTransacciones = sessionNumTransacciones.getTransaccionesRealizadasPorCajero(null, null, codigoCajero, 9999, fechaInicial, fechaFinal);
                this.getNumTransacciones().addAll(convertItemsNumTransacciones(itemsNumeroTransacciones));
                this.setMostrarPanelGeneral(false);
                this.setMostrarDiarioElect(false);
                this.setMostrarTransacciones(false);
                this.setMostrarHistorial(false);
                this.setMostrarNumTrasaccioes(true);
                this.setMostrarCajSinTransmision(false);
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return;
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return;
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, ex);
                    return;
                }
            } catch (Exception ex) {
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, ex);
                return;
            }

        } catch (IllegalArgumentException ex) {
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            return;
        }

    }

    public Collection<Cajero> cajerosSinTransmision() {
        loggerApp.info("TransaccionBean-cajerosSinTransmision");
        String error = "";
        String respuesta = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Collection<Cajero> itemsSinTransmision = null;

        try {

            Date fechaInicial = null;
            Date fechaFinal = null;
            Integer codigoCajero = 0;
            Calendar clrfechaInicial = null;
            Integer codigoCiclo;

            codigoCajero = Integer.parseInt(this.codigoCajero);
            if (codigoCajero == 0) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return null;
            }

            try {
                fechaInicial = getAtributoFechaHoraInicial().getTime();
                clrfechaInicial = getAtributoFechaHoraInicial();

            } catch (IllegalArgumentException ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return null;

            }

            if (null == clrfechaInicial) {
                clrfechaInicial = Fecha.getCalendarAyer();
            }

            fechaInicialReporte = fechaInicial;
            fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
            String strCiclo = String.valueOf(clrfechaInicial.get(Calendar.DAY_OF_MONTH)) + String.valueOf(clrfechaInicial.get(Calendar.YEAR)).substring(2);
            codigoCiclo = Integer.parseInt(strCiclo);
            codigoCiclo = ((clrfechaInicial.get(Calendar.MONTH) + 1) * 10000) + codigoCiclo;

            try {
                // Consulta los registros según los parámetros tomados del request
                itemsSinTransmision = sessionSinTransmitir.getCajerosSnTransmitir(codigoCiclo);
                this.cajerosSinTransamision.clear();
                this.cajerosSinTransamision.addAll(convertItemsCajerosSinTransmision(itemsSinTransmision));
                this.setMostrarPanelGeneral(false);
                this.setMostrarDiarioElect(false);
                this.setMostrarTransacciones(false);
                this.setMostrarHistorial(false);
                this.setMostrarNumTrasaccioes(false);
                this.setMostrarCajSinTransmision(true);
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return null;
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return null;
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, ex);
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
        }

        return itemsSinTransmision;
    }

    public void generarXLSTransaccion() {
        loggerApp.info("TransaccionBean-generarXLSTransaccion");
        String respuesta = "";
        Collection<Transaccion> regs = this.generarTransaccionesData();
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        InitBean initBean = (InitBean) viewMap.get("initBean");
        //Creo la hoja de cálculo
        String[] titulosHoja = TituloDiarioElectronicoGeneral.tituloHoja;
        String[] titulosColumna = TituloDiarioElectronicoGeneral.tituloColumnas;
        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;
        try {
            if (null != regs && !regs.isEmpty()) {
                for (Transaccion item : regs) {
                    Registro reg = new Registro();
                    numColumna = 0;
                    reg.addCelda(new Celda(numColumna++, item.getCajero().getCodigoCajero(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getCajero().getNombre(), TipoDatoCelda.NORMAL));
                    if (item.getCajero().getTipoLecturaEDC() == 0) {
                        reg.addCelda(new Celda(numColumna++, item.getTipoTransaccion() + "" + item.getCodigoTransaccion() + " " + com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.TipoTransaccion.getTipoTransaccion(item.getCodigoTransaccion()).nombre, TipoDatoCelda.NORMAL));
                    } else {
                        reg.addCelda(new Celda(numColumna++, "", TipoDatoCelda.NORMAL));
                    }
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getTransaccionPK().getFechaTransaccion(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getTransaccionPK().getNumeroTransaccion(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getCuenta(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getTarjeta(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValorEntregado()), TipoDatoCelda.MONEDA));
                    if (item.getReferencia() != null) {
                        reg.addCelda(new Celda(numColumna++, item.getReferencia(), TipoDatoCelda.NORMAL));
                    } else {
                        reg.addCelda(new Celda(numColumna++, "0", TipoDatoCelda.NORMAL));
                    }

                    reg.addCelda(new Celda(numColumna++, item.getErrorTransaccion(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getCodigoTerminacionTransaccion(), TipoDatoCelda.NORMAL));
                    lineas.add(reg);
                }
            } else if (itemsDTemp != null) {
                for (TransaccionTemp item : itemsDTemp) {
                    Registro reg = new Registro();
                    numColumna = 0;
                    reg.addCelda(new Celda(numColumna++, item.getTransaccionTempPK().getCodigocajero(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getTransaccionTempPK().getCodigocajero(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getTipotransaccion() + "" + item.getCodigotransaccion() + " " + com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.TipoTransaccion.getTipoTransaccion(item.getCodigotransaccion()).nombre, TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getTransaccionTempPK().getFechatransaccion(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getTransaccionTempPK().getNumerotransaccion(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getCuenta(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getTarjeta(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValorentregado()), TipoDatoCelda.MONEDA));
                    if (item.getReferencia() != null) {
                        reg.addCelda(new Celda(numColumna++, item.getReferencia(), TipoDatoCelda.NORMAL));
                    } else {
                        reg.addCelda(new Celda(numColumna++, "0", TipoDatoCelda.NORMAL));
                    }
                    reg.addCelda(new Celda(numColumna++, item.getErrortransaccion(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getCodigoterminaciontransaccion(), TipoDatoCelda.NORMAL));
                    lineas.add(reg);
                }
            }
            if (!lineas.isEmpty()) {
                ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("DiarioElectronicoGeneral", titulosHoja, titulosColumna, lineas);
                objectContext.enviarArchivoXLS(archivo);
                this.mostrarPanelGeneral = true;
                this.mostrarDiarioElect = false;
                this.mostrarTransacciones = false;
                this.mostrarHistorial = false;
                this.mostrarNumTrasaccioes = false;
                this.mostrarCajSinTransmision = false;
            }
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_CREAR_ARCHIVO, null);
        } catch (IOException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_DESCARGAR_ARCHIVO, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void generarXLSSinTransmision() {
        loggerApp.info("TransaccionBean-generarXLSSinTransmision");
        String respuesta = "";
        Collection<Cajero> regs = this.cajerosSinTransmision();

        //Creo la hoja de cálculo
        String[] tituloHoja;
        tituloHoja = new String[2];
        tituloHoja[0] = "Cajeros sin Diario Electronico  ";
        tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicialReporte, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
        String[] titulosHoja = tituloHoja; //TituloDiarioElectronicoGeneral.tituloHoja;
        String[] titulosColumna = TituloDiarioElectronicoGeneral.tituloColumnasCajero;

        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;
        try {
            for (Cajero item : regs) {
                Registro reg = new Registro();
                numColumna = 0;
                reg.addCelda(new Celda(numColumna++, item.getCodigoCajero(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getNombre(), TipoDatoCelda.NORMAL));
                lineas.add(reg);
            }

            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("Cajeros Sin Transmitir", titulosHoja, titulosColumna, lineas);
            objectContext.enviarArchivoXLS(archivo);
            this.mostrarPanelGeneral = true;
            this.mostrarDiarioElect = false;
            this.mostrarTransacciones = false;
            this.mostrarHistorial = false;
            this.mostrarNumTrasaccioes = false;
            this.mostrarCajSinTransmision = false;
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_CREAR_ARCHIVO, null);
        } catch (IOException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_DESCARGAR_ARCHIVO,  null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO,  null);
        }
    }

    public Collection<DiarioElectronicoTransaccionDTO> setColeccionRegistroDiarioElectronico(Collection<DiarioElectronico> registros) {
        Collection<DiarioElectronicoTransaccionDTO> coleccionDiarioElectronicoBean = new ArrayList<DiarioElectronicoTransaccionDTO>();
        for (DiarioElectronico edcRegistro : registros) {
            DiarioElectronicoTransaccionDTO bean = convertDiarioDTO(edcRegistro);
            coleccionDiarioElectronicoBean.add(bean);
        }
        return coleccionDiarioElectronicoBean;
    }

    private DiarioElectronicoTransaccionDTO convertDiarioDTO(DiarioElectronico edcRegistro) {
        DiarioElectronicoTransaccionDTO bean = new DiarioElectronicoTransaccionDTO();
        bean.tipoRegistro = String.valueOf(edcRegistro.getTipoRegistro());
        bean.secuencia = new Integer(edcRegistro.getDiarioElectronicoPK().getSecuencia());
        bean.datos = com.davivienda.utilidades.edc.Edc.aCadena(edcRegistro.getTipoRegistro(), edcRegistro.getInformacion());

        return bean;
    }

    public List<TransaccionDTO> convertItemsTransaccion(Collection<Transaccion> items) {
        Integer idRegistro = 0;
        List<TransaccionDTO> transacciones = new ArrayList<TransaccionDTO>();
        for (Transaccion tra : items) {
            TransaccionDTO transaccion = new TransaccionDTO();
            transaccion.setIdRegistro(++idRegistro);
            transaccion.setNombreCajero(tra.getCajero().getNombre());
            transaccion.setCodigoCajero(String.valueOf(tra.getTransaccionPK().getCodigoCajero()));
            transaccion.setNumeroSerial(String.valueOf(tra.getTransaccionPK().getNumeroTransaccion()));
            transaccion.setFecha(com.davivienda.utilidades.conversion.Fecha.aCadena(tra.getTransaccionPK().getFechaTransaccion(), FormatoFecha.FECHA_HORA));
            transaccion.setTipoTransaccion(String.valueOf(tra.getTipoTransaccion()));
            transaccion.setCodigoTransaccion(String.valueOf(tra.getCodigoTransaccion()));
            if (tra.getCajero().getTipoLecturaEDC() == 0) {
                transaccion.setDescripcionTransaccion(tra.getTipoTransaccion() + "" + tra.getCodigoTransaccion() + " " + com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.TipoTransaccion.getTipoTransaccion(tra.getCodigoTransaccion()).nombre);
            }
            transaccion.setCodigoTerminacion(tra.getCodigoTerminacionTransaccion());
            transaccion.setCodigoError(tra.getErrorTransaccion());
            transaccion.setCuenta(tra.getCuenta());
            transaccion.setReferencia(tra.getReferencia());
            transaccion.setTarjeta(tra.getTarjeta());
            transaccion.setValorEntregado(com.davivienda.utilidades.conversion.Numero.aMoneda(tra.getValorEntregado()));
            transaccion.setValorSolicitado(com.davivienda.utilidades.conversion.Numero.aMoneda(tra.getValorSolicitado()));

            transacciones.add(transaccion);

        }

        return transacciones;
    }

    public List<TransaccionDTO> convertItemsTransaccionTemp(Collection<TransaccionTemp> items) {
        Integer idRegistro = 0;
        List<TransaccionDTO> transacciones = new ArrayList<TransaccionDTO>();
        for (TransaccionTemp tra : items) {
            TransaccionDTO transaccion = new TransaccionDTO();
            transaccion.setIdRegistro(++idRegistro);
            transaccion.setNombreCajero(String.valueOf(tra.getTransaccionTempPK().getCodigocajero()));
            transaccion.setCodigoCajero(String.valueOf(tra.getTransaccionTempPK().getCodigocajero()));
            transaccion.setNumeroSerial(String.valueOf(tra.getTransaccionTempPK().getNumerotransaccion()));
            transaccion.setFecha(com.davivienda.utilidades.conversion.Fecha.aCadena(tra.getTransaccionTempPK().getFechatransaccion(), FormatoFecha.FECHA_HORA));
            transaccion.setTipoTransaccion(String.valueOf(tra.getTipotransaccion()));
            transaccion.setCodigoTransaccion(String.valueOf(tra.getCodigotransaccion()));
            transaccion.setDescripcionTransaccion(tra.getTipotransaccion() + "" + tra.getCodigotransaccion() + " " + com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.TipoTransaccion.getTipoTransaccion(tra.getCodigotransaccion()).nombre);
            transaccion.setCodigoTerminacion(tra.getCodigoterminaciontransaccion());
            transaccion.setCodigoError(tra.getErrortransaccion());
            transaccion.setCuenta(tra.getCuenta());
            transaccion.setReferencia(tra.getReferencia());
            transaccion.setTarjeta(tra.getTarjeta());
            transaccion.setValorEntregado(com.davivienda.utilidades.conversion.Numero.aMoneda(tra.getValorentregado()));
            transaccion.setValorSolicitado(com.davivienda.utilidades.conversion.Numero.aMoneda(tra.getValorsolicitado()));

            transacciones.add(transaccion);

        }

        return transacciones;
    }

    public List<HistoricoCargueDTO> convertItemsHistorico(Collection<EdcCargue> items) {
        Integer idRegistro = 0;
        List<HistoricoCargueDTO> historicos = new ArrayList<HistoricoCargueDTO>();

        for (EdcCargue item : items) {
            HistoricoCargueDTO historico = new HistoricoCargueDTO();
            historico.setIdRegistro(++idRegistro);
            historico.setCodigoCajero(String.valueOf(item.getCodigoCajero()));
            historico.setNombreArchivo(item.getNombrearchivo());
            historico.setCiclo(String.valueOf(item.getCiclo()));
            historico.setFecha(String.valueOf(item.getFechaEdcCargue()));
            historico.setEstadoProceso(com.davivienda.sara.constantes.EstadoProceso.getEstadoProceso(item.getEstadoproceso()));
            historico.setError(String.valueOf(item.getError()));
            historico.setDescripcionError(com.davivienda.sara.constantes.CodigoError.getCodigoError(item.getError()));
            historico.setTamanoBytes(String.valueOf(item.getTamano()));
            historico.setPrueba(true);

            historicos.add(historico);
        }
        loggerApp.info("generarHistoricoCargue historicos " + historicos.size());
        return historicos;
    }

    public List<NumeroTransaccionesDTO> convertItemsNumTransacciones(Collection<CantidadTransaccionesBean> items) {
        Integer idRegistro = 0;
        List<NumeroTransaccionesDTO> numeroTransacciones = new ArrayList<NumeroTransaccionesDTO>();
        for (CantidadTransaccionesBean item : items) {

            NumeroTransaccionesDTO numTransaccion = new NumeroTransaccionesDTO();
            numTransaccion.setIdRegistro(++idRegistro);
            numTransaccion.setCodigoCajero(String.valueOf(item.getCodigoCajero()));
            numTransaccion.setNombreCajero(item.getNombreCajero());
            numTransaccion.setFecha(item.getFecha());
            numTransaccion.setDescripcionTransaccion(com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.TipoTransaccion.getTipoTransaccion(item.getTipoTransacion()).nombre);
            numTransaccion.setCantidad(com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getCantidad()));
            numeroTransacciones.add(numTransaccion);
        }
        return numeroTransacciones;
    }

    public List<CajerosSinTransmisionDTO> convertItemsCajerosSinTransmision(Collection<Cajero> items) {
        Integer idRegistro = 0;
        List<CajerosSinTransmisionDTO> cajerosSinTransaccion = new ArrayList<CajerosSinTransmisionDTO>();
        for (Cajero item : items) {

            CajerosSinTransmisionDTO cajeroSinTransmision = new CajerosSinTransmisionDTO();
            cajeroSinTransmision.setIdRegistro(++idRegistro);
            cajeroSinTransmision.setCodigoCajero(String.valueOf(item.getCodigoCajero()));
            cajeroSinTransmision.setNombreCajero(item.getNombre());

            cajerosSinTransaccion.add(cajeroSinTransmision);
        }
        return cajerosSinTransaccion;
    }

    public Calendar getAtributoFechaHoraInicial() throws IllegalArgumentException {
        String fechaStr = this.fechaInicial;
        String horaStr = this.horaInicial;
        fechaStr = fechaStr.concat(horaStr);
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        return calendar;
    }

    public Calendar getAtributoFechaHoraFinal() throws IllegalArgumentException {
        String fechaStr = this.fechaFinal;
        String horaStr = this.horaFinal;
        fechaStr = fechaStr.concat(horaStr);
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        return calendar;
    }
    
    public Calendar getAtributoFechaInicialHoraFinal() throws IllegalArgumentException {
        String fechaStr = this.fechaInicial;
        String horaStr = this.horaFinal;
        fechaStr = fechaStr.concat(horaStr);
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        return calendar;
    }

    private List<SelectItem> cargarListaHora() {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        boolean iniciar = true;
        int mn = 0;
        int hr = 0;
        while (iniciar) {
            SelectItem item = null;
            if (hr < 24) {
                if (mn == 0) {
                    if (hr < 10) {
                        item = new SelectItem("0" + hr + ":00:00", "0" + hr + ":00:00");
                    } else {
                        item = new SelectItem(+hr + ":00:00", +hr + ":00:00");
                    }
                    mn += 15;
                } else if (mn < 60) {
                    if (hr < 10) {
                        item = new SelectItem("0" + hr + ":" + mn + ":00", "0" + hr + ":" + mn + ":00");
                    } else {
                        item = new SelectItem(+hr + ":" + mn + ":00", +hr + ":" + mn + ":00");
                    }
                    mn += 15;
                } else {
                    hr++;
                    mn = 0;
                    if (hr < 24) {
                        if (hr < 10) {
                            item = new SelectItem("0" + hr + ":00:00", "0" + hr + ":00:00");
                        } else {
                            item = new SelectItem(+hr + ":00:00", +hr + ":00:00");
                        }
                    }
                    mn += 15;

                }
                if (item != null) {
                    lista.add(item);
                }

            } else {
                iniciar = false;
            }
        }
        return lista;
    }

    /**
     * @return the codigoCajero
     */
    public String getCodigoCajero() {
        return codigoCajero;
    }

    /**
     * @param codigoCajero the codigoCajero to set
     */
    public void setCodigoCajero(String codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the horaInicial
     */
    public String getHoraInicial() {
        return horaInicial;
    }

    /**
     * @param horaInicial the horaInicial to set
     */
    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    /**
     * @return the horaFinal
     */
    public String getHoraFinal() {
        return horaFinal;
    }

    /**
     * @param horaFinal the horaFinal to set
     */
    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    /**
     * @return the mostrarDiarioElect
     */
    public boolean isMostrarDiarioElect() {
        return mostrarDiarioElect;
    }

    /**
     * @param mostrarDiarioElect the mostrarDiarioElect to set
     */
    public void setMostrarDiarioElect(boolean mostrarDiarioElect) {
        this.mostrarDiarioElect = mostrarDiarioElect;
    }

    /**
     * @return the itemsFormateados
     */
    public Collection<DiarioElectronicoTransaccionDTO> getItemsFormateados() {
        return itemsFormateados;
    }

    /**
     * @param itemsFormateados the itemsFormateados to set
     */
    public void setItemsFormateados(Collection<DiarioElectronicoTransaccionDTO> itemsFormateados) {
        this.itemsFormateados = itemsFormateados;
    }

    /**
     * @return the mostrarTransacciones
     */
    public boolean isMostrarTransacciones() {
        return mostrarTransacciones;
    }

    /**
     * @param mostrarTransacciones the mostrarTransacciones to set
     */
    public void setMostrarTransacciones(boolean mostrarTransacciones) {
        this.mostrarTransacciones = mostrarTransacciones;
    }

    /**
     * @return the itemsDTemp
     */
    public Collection<TransaccionTemp> getItemsDTemp() {
        return itemsDTemp;
    }

    /**
     * @param itemsDTemp the itemsDTemp to set
     */
    public void setItemsDTemp(Collection<TransaccionTemp> itemsDTemp) {
        this.itemsDTemp = itemsDTemp;
    }

    /**
     * @return the talon
     */
    public String getTalon() {
        return talon;
    }

    /**
     * @param talon the talon to set
     */
    public void setTalon(String talon) {
        this.talon = talon;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the tarjeta
     */
    public String getTarjeta() {
        return tarjeta;
    }

    /**
     * @param tarjeta the tarjeta to set
     */
    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    /**
     * @return the mostrarPanelGeneral
     */
    public boolean isMostrarPanelGeneral() {
        return mostrarPanelGeneral;
    }

    /**
     * @param mostrarPanelGeneral the mostrarPanelGeneral to set
     */
    public void setMostrarPanelGeneral(boolean mostrarPanelGeneral) {
        this.mostrarPanelGeneral = mostrarPanelGeneral;
    }

    /**
     * @return the transacciones
     */
    public List<TransaccionDTO> getTransacciones() {
        return transacciones;
    }

    /**
     * @param transacciones the transacciones to set
     */
    public void setTransacciones(List<TransaccionDTO> transacciones) {
        this.transacciones = transacciones;
    }

    /**
     * @return the mostrarHistorial
     */
    public boolean isMostrarHistorial() {
        return mostrarHistorial;
    }

    /**
     * @param mostrarHistorial the mostrarHistorial to set
     */
    public void setMostrarHistorial(boolean mostrarHistorial) {
        this.mostrarHistorial = mostrarHistorial;
    }

    /**
     * @return the historicosCargue
     */
    public List<HistoricoCargueDTO> getHistoricosCargue() {
        return historicosCargue;
    }

    /**
     * @param historicosCargue the historicosCargue to set
     */
    public void setHistoricosCargue(List<HistoricoCargueDTO> historicosCargue) {
        this.historicosCargue = historicosCargue;
    }

    /**
     * @return the numTransacciones
     */
    public List<NumeroTransaccionesDTO> getNumTransacciones() {
        return numTransacciones;
    }

    /**
     * @param numTransacciones the numTransacciones to set
     */
    public void setNumTransacciones(List<NumeroTransaccionesDTO> numTransacciones) {
        this.numTransacciones = numTransacciones;
    }

    /**
     * @return the cajerosSinTransamision
     */
    public List<CajerosSinTransmisionDTO> getCajerosSinTransamision() {
        return cajerosSinTransamision;
    }

    /**
     * @param cajerosSinTransamision the cajerosSinTransamision to set
     */
    public void setCajerosSinTransamision(List<CajerosSinTransmisionDTO> cajerosSinTransamision) {
        this.cajerosSinTransamision = cajerosSinTransamision;
    }

    /**
     * @return the mostrarNumTrasaccioes
     */
    public boolean isMostrarNumTrasaccioes() {
        return mostrarNumTrasaccioes;
    }

    /**
     * @param mostrarNumTrasaccioes the mostrarNumTrasaccioes to set
     */
    public void setMostrarNumTrasaccioes(boolean mostrarNumTrasaccioes) {
        this.mostrarNumTrasaccioes = mostrarNumTrasaccioes;
    }

    /**
     * @return the mostrarCajSinTransmision
     */
    public boolean isMostrarCajSinTransmision() {
        return mostrarCajSinTransmision;
    }

    /**
     * @param mostrarCajSinTransmision the mostrarCajSinTransmision to set
     */
    public void setMostrarCajSinTransmision(boolean mostrarCajSinTransmision) {
        this.mostrarCajSinTransmision = mostrarCajSinTransmision;
    }

    public List<SelectItem> getListaHora() {
        return listaHora;
    }

    public void setListaHora(List<SelectItem> listaHora) {
        this.listaHora = listaHora;
    }

}
