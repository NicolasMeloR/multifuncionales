/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.diarioelectronico;

import com.davivienda.multifuncional.constantes.EstadosDiarioMultifuncional;
import com.davivienda.multifuncional.constantes.TipoCuentaMultifuncional;
import com.davivienda.multifuncional.diarioelectronico.formato.TituloDiarioMultifuncionalGeneral;
import com.davivienda.multifuncional.tablas.edccargue.session.EDCCargueMultifuncionalLocal;
import com.davivienda.multifuncional.tablas.logcajeromulti.session.LogCajeroMultiSessionLocal;
import com.davivienda.multifuncional.tablas.txcontrolmulticheque.session.TxControlMultiChequeSessionLocal;
import com.davivienda.multifuncional.tablas.txmultifuncionalcheque.session.TxMultifuncionalChequeSessionLocal;
import com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.session.TxMultifuncionalEfectivoSessionLocal;
import com.davivienda.sara.dto.DiarioElectronicoTransaccionDTO;
import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.dto.CajeroDTO;
import com.davivienda.sara.dto.CajerosSinTransmisionDTO;
import com.davivienda.sara.dto.HistoricoCargueDTO;
import com.davivienda.sara.dto.NumeroTransaccionesDTO;
import com.davivienda.sara.dto.TransaccionDTO;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.entitys.TransaccionTemp;
import com.davivienda.sara.entitys.transaccion.CantidadTransaccionesBean;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.estadisticas.EstadisticasGeneralesSessionLocal;
import com.davivienda.sara.tablas.diarioelectronico.session.DiarioElectronicoSessionLocal;
import com.davivienda.sara.tablas.edccargue.session.EdcCargueSessionLocal;
import com.davivienda.sara.tablas.transaccion.session.TransaccionSessionLocal;
import com.davivienda.sara.tablas.transacciontemp.session.TransaccionTempSessionLocal;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.edc.Edc;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.TipoDiario;
import com.davivienda.sara.diarioelectronico.general.formato.TituloDiarioElectronicoGeneral;
import com.davivienda.sara.dto.TxMultifuncionalChequeDTO;
import com.davivienda.sara.dto.TxMultifuncionalEfectivoDTO;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.Edcarguemultifuncional;
import com.davivienda.sara.entitys.Logcajeromulti;
import com.davivienda.sara.entitys.Txcontrolmulticheque;
import com.davivienda.sara.entitys.Txmultifuncionalcheque;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import com.davivienda.utilidades.conversion.JSon;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;

/**
 *
 * @author jmcastel
 */
@ManagedBean(name = "transaccionMFBean")
@ViewScoped
public class TransaccionMFBean extends BaseBean implements Serializable {

    @EJB
    TxMultifuncionalEfectivoSessionLocal txMultifuncionalEfectivoSessionLocal;

    @EJB
    TxMultifuncionalChequeSessionLocal txMultifuncionalChequeSessionLocal;

    @EJB
    DiarioElectronicoSessionLocal sessionDiarioElectronico;

    @EJB
    TxControlMultiChequeSessionLocal txControlMultiChequeSessionLocal;

    @EJB
    TransaccionSessionLocal sessionTransaccion;

    @EJB
    TransaccionTempSessionLocal transaccionTempSession;

    @EJB
    EdcCargueSessionLocal sessionHistoricoCargue;

    @EJB
    EDCCargueMultifuncionalLocal sessionHistoricoCargueMultifuncional;

    @EJB
    EstadisticasGeneralesSessionLocal sessionNumTransacciones;

    @EJB
    CajeroSessionLocal cajeroSession;

    @EJB
    LogCajeroMultiSessionLocal logSession;

    private String respuestaJSon;

    public ComponenteAjaxObjectContextWeb objectContext;
    private String cajeroSeleccionado;
    private Date fechaInicial;
    private Date fechaFinal;
    private String horaInicial;
    private String horaFinal;
    private String talon;
    private String referencia;
    private String cuenta;
    private String tarjeta;
    private String tipoDiario;
    public Date fechaInicialReporte = null;

    private boolean mostrarPanelGeneral;
    private boolean mostrarDiarioElectronicoEfectivo;
    private boolean mostrarDiarioElectronicoCheque;
    private boolean mostrarHistorial;
    private boolean mostrarCajSinTransmision;

    private List<TransaccionDTO> transacciones;
    private List<HistoricoCargueDTO> historicosCargue;
    private List<NumeroTransaccionesDTO> numTransacciones;
    private List<CajerosSinTransmisionDTO> cajerosSinTransamision;
    private List<TxMultifuncionalEfectivoDTO> multifuncionalEfectivo;
    private List<TxMultifuncionalChequeDTO> multifuncionalcheque;
    private Collection<TransaccionTemp> itemsDTemp = null;

    private Collection<DiarioElectronicoTransaccionDTO> itemsFormateados = null;

    private List<SelectItem> listaHora;
    private String fechaDesde;
    private String horaDesde;
    private String fechaHasta;
    private String horaHasta;

    private List<CajeroDTO> listaCajeros;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    public void TransaccionMFBean() {
        objectContext = cargarComponenteAjaxObjectContext();
        listaHora = new ArrayList<>();
        if (objectContext != null) {
            dataInicial();
        }
    }

    public void dataInicial() {
        cajerosSinTransamision = new ArrayList<>();
        historicosCargue = new ArrayList<>();
        multifuncionalEfectivo = new ArrayList<>();
        multifuncionalcheque = new ArrayList<>();
        this.fechaDesde = "";
        this.horaDesde = "00:00:00";
        this.fechaHasta = "";
        this.horaHasta = "23:59:59";
        this.listaHora = cargarListaHora();
        cargarListaCajeros();
        this.setMostrarPanelGeneral(true);
        this.setMostrarDiarioElectronicoCheque(false);
        this.setMostrarDiarioElectronicoEfectivo(false);
        this.setMostrarHistorial(false);
        this.setMostrarCajSinTransmision(false);
    }

    private void cargarListaCajeros() {
        Collection<Cajero> items = null;
        try {
            items = cajeroSession.getTodosActivosMulti(0, 5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        listaCajeros = cargarListaCajeros(objectContext.getCajeroCB(items));
    }

    private List<CajeroDTO> cargarListaCajeros(List<CajeroDTO> cajeroDTO) {
        List<CajeroDTO> lista = new ArrayList<>();
        int cont = 1;
        for (CajeroDTO dto : cajeroDTO) {
            dto.setIdCajero(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    public String consultarDiarioElctronico() {
        String respuesta = "";
        try {

            objectContext.getConfigApp().loggerApp.info("TransaccionMFBean consultarDiarioElctronico - fechaInicial " + fechaDesde +" "+ horaDesde + " fechaFinal " + fechaHasta +" "+ horaHasta);
            this.respuestaJSon = "";
            Collection<Txmultifuncionalefectivo> itemsEfectivo = null;
            Collection<Txmultifuncionalcheque> itemsCheque = null;

            try {
                fechaInicial = formatter.parse(this.fechaDesde);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }
            if (tipoDiario == null || tipoDiario.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_DIARIO, null);
                return "";
            }

            if (tipoDiario.length() > 0) {

                Integer tipointDiario = com.davivienda.utilidades.conversion.Cadena.aInteger(tipoDiario);
                TipoDiario tipoDiarioLocal = TipoDiario.Efectivo;
                tipoDiarioLocal = TipoDiario.getTipoDiario(tipointDiario);

                switch (tipoDiarioLocal) {
                    case Efectivo:
                        itemsEfectivo = consultarTransaccionesEfectivo();

                        if (itemsEfectivo != null && respuestaJSon.length() <= 1) {
                            this.getMultifuncionalEfectivo().clear();
                            this.getMultifuncionalEfectivo().addAll(convertItemsDiarioEMultifuncionalEfectivo(itemsEfectivo));
                            this.setMostrarPanelGeneral(false);
                            this.setMostrarDiarioElectronicoCheque(false);
                            this.setMostrarDiarioElectronicoEfectivo(true);
                            this.setMostrarHistorial(false);
                            this.setMostrarCajSinTransmision(false);
                        } else if (respuestaJSon != null) {
                            if (!respuestaJSon.isEmpty()) {
                                abrirModal("SARA", respuestaJSon, null);
                            } else {
                                abrirModal("SARA", "No se encontraron registros.", null);
                            }
                        } else if (itemsEfectivo == null) {
                            abrirModal("SARA", "No se encontraron registros.", null);
                        }
                        break;
                    case Cheque:
                        itemsCheque = consultarTransaccionesCheque();

                        if (itemsCheque != null && respuestaJSon.length() <= 1) {
                            this.getMultifuncionalcheque().clear();
                            this.getMultifuncionalcheque().addAll(convertItemsDiarioEMultifuncionalECheque(itemsCheque));
                            this.setMostrarPanelGeneral(false);
                            this.setMostrarDiarioElectronicoCheque(true);
                            this.setMostrarDiarioElectronicoEfectivo(false);
                            this.setMostrarHistorial(false);
                            this.setMostrarCajSinTransmision(false);
                        } else if (respuestaJSon != null) {
                            if (!respuestaJSon.isEmpty()) {
                                abrirModal("SARA", respuestaJSon, null);
                            } else {
                                abrirModal("SARA", "No se encontraron registros.", null);
                            }
                        } else if (itemsCheque == null) {
                            abrirModal("SARA", "No se encontraron registros.", null);
                        }
                        break;

                }
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
            return null;
        }
        return respuesta;

    }

    private List<TxMultifuncionalChequeDTO> convertItemsDiarioEMultifuncionalECheque(Collection<Txmultifuncionalcheque> itemscheque) {
        int idRegistro = 0;
        List<TxMultifuncionalChequeDTO> listamfc = new ArrayList<>();
        Txcontrolmulticheque itemsControl = null;
        BigDecimal newValDecCheque;
        Long newValLongCheque;

        for (Txmultifuncionalcheque item : itemscheque) {
            TxMultifuncionalChequeDTO elem = new TxMultifuncionalChequeDTO();
            elem.setIdRegistro(++idRegistro);
            elem.setCodigoTransaccion(item.getTxmultifuncionalchequePK().getCodigotransaccion());
            elem.setEstado(com.davivienda.utilidades.conversion.Cadena.aCerosIzquierda(item.getEstado(), 2) + " - " + EstadosDiarioMultifuncional.getEstadoDiarioMultifuncional(item.getEstado()).nombre);
            elem.setCodigoCajero(item.getTxmultifuncionalchequePK().getCodigocajero());
            elem.setFechaCajero(item.getTxmultifuncionalchequePK().getFechacajero());

            elem.setNumerocorte(item.getNumerocorte());
            elem.setCodigoTransaccion(item.getConsecutivotransaccion());
            elem.setFechacierre(item.getFechacierre());

            elem.setConsecutivochequeconsignacion(item.getConsecutivochequeconsignacion());
            elem.setTipocuenta(TipoCuentaMultifuncional.getTipoCuentaMultifuncional(com.davivienda.utilidades.conversion.Cadena.aInteger(item.getTipocuenta())).nombre);
            elem.setNumerocuentaconsignar(item.getNumerocuentaconsignar());
            elem.setNumerocuentahomologa(item.getNumerocuentahomologa());
            elem.setChequepropio(item.getChequepropio());
            elem.setRuta(item.getRuta());
            elem.setTransito(item.getTransito());
            elem.setCuenta(item.getCuenta());
            elem.setSerial(item.getSerial());
            elem.setOficina(item.getOficina());
            newValLongCheque = item.getValorchequeusuario() / 100;
            try {
                newValDecCheque = new BigDecimal(newValLongCheque.toString() + "." + item.getValorchequeusuario().toString().substring(newValLongCheque.toString().length()));
                elem.setValorchequeusuario(com.davivienda.utilidades.conversion.Numero.aMonedaDecimal(newValDecCheque));
            } catch (Exception ex) {
                elem.setValorchequeusuario(com.davivienda.utilidades.conversion.Numero.aMoneda(newValLongCheque));
            }

            itemsControl = ObtenerDatosControlMultiCheque(item.getIdregistrocontrol().getIdregistro());

            elem.setPlazaControl(itemsControl.getPlaza());
            elem.setFechaarchivoControl(itemsControl.getFechaarchivo());
            newValLongCheque = itemsControl.getMontoarchivo() / 100;
            try {
                newValDecCheque = new BigDecimal(newValLongCheque.toString() + "." + itemsControl.getMontoarchivo().toString().substring(newValLongCheque.toString().length()));
                elem.setMontoarchivoControl(com.davivienda.utilidades.conversion.Numero.aMonedaDecimal(newValDecCheque));
            } catch (Exception ex) {
                elem.setMontoarchivoControl(com.davivienda.utilidades.conversion.Numero.aMoneda(newValLongCheque));
            }

            elem.setCantidadchequesControl(itemsControl.getCantidadcheques());

            listamfc.add(elem);
        }
        return listamfc;
    }

    private Txcontrolmulticheque ObtenerDatosControlMultiCheque(Long idregistro) {
        Txcontrolmulticheque items = null;

        try {
            items = txControlMultiChequeSessionLocal.getRegistroControlCheque(idregistro);

        } catch (EJBException ex) {
            if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            }
            if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            }
            if (ex.getLocalizedMessage().contains("NoResultException")) {
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
            }
        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
        }

        return items;
    }

    private List<TxMultifuncionalEfectivoDTO> convertItemsDiarioEMultifuncionalEfectivo(Collection<Txmultifuncionalefectivo> itemsEfectivo) {
        int idRegistro = 0;
        List<TxMultifuncionalEfectivoDTO> listamne = new ArrayList<>();

        for (Txmultifuncionalefectivo item : itemsEfectivo) {
            TxMultifuncionalEfectivoDTO elem = new TxMultifuncionalEfectivoDTO();

            elem.setIdRegistro(++idRegistro);
            elem.setCodigoTransaccion(item.getTxmultifuncionalefectivoPK().getCodigotx());
            elem.setEstado(com.davivienda.utilidades.conversion.Cadena.aCerosIzquierda(item.getEstado(), 2) + " - " + EstadosDiarioMultifuncional.getEstadoDiarioMultifuncional(item.getEstado()).nombre);
            elem.setTransaccionconsecutivo(item.getTransaccionconsecutivo());
            elem.setCodigoCajero(item.getTxmultifuncionalefectivoPK().getCodigocajero());
            elem.setNumerocorte(item.getNumerocorte());
            elem.setFechacierre(com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFechacierre(), FormatoFecha.FECHA_HORA));
            elem.setFechaCajero(com.davivienda.utilidades.conversion.Fecha.aCadena(item.getTxmultifuncionalefectivoPK().getFechacajero(), FormatoFecha.FECHA_HORA));
            elem.setTipocuenta(TipoCuentaMultifuncional.getTipoCuentaMultifuncional((Integer) item.getTipocuenta().intValue()).nombre);
            elem.setNumerocuentaconsignar(item.getNumerocuentaconsignar());
            elem.setNumerocuentahomologa(item.getNumerocuentahomologa());
            elem.setValorconsignacion(item.getValorconsignacion());
            elem.setNobilletesnd(item.getNobilletesnd());
            elem.setNobilletesdenominacionf(item.getNobilletesdenominacionf());
            elem.setNobilletesdenominacione(item.getNobilletesdenominacione());
            elem.setNobilletesdenominaciond(item.getNobilletesdenominaciond());
            elem.setNobilletesdenominacionc(item.getNobilletesdenominacionc());
            elem.setNobilletesdenominacionb(item.getNobilletesdenominacionb());
            elem.setNobilletesdenominaciona(item.getNobilletesdenominaciona());
            elem.setTotalbilletesconsignados(item.getTotalbilletesconsignados());

            listamne.add(elem);
        }
        return listamne;
    }

    public String consultarHistoricoCargue() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Edcarguemultifuncional> items = null;
        int codigoCiclo;
        try {
            Date fechaIni = null;
            Date fechaFin = null;
            try {
                fechaIni = getFechaTransformada(fechaDesde, horaDesde);
            } catch (Exception ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }
            
            if(null == this.fechaHasta || this.fechaHasta.isEmpty()){
                fechaFin = getFechaTransformada(fechaDesde, horaHasta);
            }else{
                try {
                    fechaFin = getFechaTransformada(fechaHasta, horaHasta);
                } catch (Exception ex) {
                    fechaFin = fechaIni;
                }
                fechaFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaIni);
            }

            try {
                // Consulta los registros según los parámetros tomados del request
                items = sessionHistoricoCargueMultifuncional.getEDCCargueXFecha(fechaIni, fechaFin);
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
        }
        if (items == null || items.isEmpty()) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
        }

        if (items != null && respuestaJSon.length() <= 1) {
            this.getHistoricosCargue().clear();
            this.getHistoricosCargue().addAll(convertItemsHistoricoMultifuncional(items));
            this.setMostrarPanelGeneral(false);
            this.setMostrarDiarioElectronicoCheque(false);
            this.setMostrarDiarioElectronicoEfectivo(false);
            this.setMostrarHistorial(true);
            this.setMostrarCajSinTransmision(false);
        } else if (respuestaJSon != null) {
            if (!respuestaJSon.isEmpty()) {
                abrirModal("SARA", respuestaJSon, null);
            } else {
                abrirModal("SARA", "No se encontraron registros.", null);
            }
        } else if (items == null) {
            abrirModal("SARA", "No se encontraron registros.", null);
        }
        return respuesta;
    }

    public List<HistoricoCargueDTO> convertItemsHistoricoMultifuncional(Collection<Edcarguemultifuncional> items) {
        int idRegistro = 0;
        List<HistoricoCargueDTO> historicos = new ArrayList<>();

        for (Edcarguemultifuncional item : items) {
            HistoricoCargueDTO historico = new HistoricoCargueDTO();
            historico.setIdRegistro(++idRegistro);
            historico.setCodigoCajero(String.valueOf(item.getCodigocajero()));
            historico.setNombreArchivo(item.getNombrearchivo());
            historico.setCiclo(String.valueOf(item.getCiclo()));
            historico.setFecha(String.valueOf(item.getFechaedccargue()));
            historico.setDescripcionError(com.davivienda.sara.constantes.CodigoError.getCodigoError(item.getError()));
            historicos.add(historico);
        }

        return historicos;
    }

    public String consultarSinTrasmision() {

        String respuesta = "";

        try {
            this.respuestaJSon = "";
            Collection<Cajero> items = null;

            try {
                Date fechaIni = getFechaTransformada(fechaDesde, horaDesde);
            } catch (Exception ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }

            if (null == cajeroSeleccionado || cajeroSeleccionado.equals("") || cajeroSeleccionado.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return "";
            }

            try {
                items = consultarRegitrosSinTrasmision();
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
                return "";
            }

            if (items != null && respuestaJSon.length() <= 1) {
                this.getCajerosSinTransamision().clear();
                this.getCajerosSinTransamision().addAll(convertirListaCajerosSinTrasmision(items));
                this.setMostrarPanelGeneral(false);
                this.setMostrarDiarioElectronicoCheque(false);
                this.setMostrarDiarioElectronicoEfectivo(false);
                this.setMostrarHistorial(false);
                this.setMostrarCajSinTransmision(true);
            } else if (respuestaJSon != null) {
                if (!respuestaJSon.isEmpty()) {
                    abrirModal("SARA", respuestaJSon, null);
                } else {
                    abrirModal("SARA", "No se encontraron registros.", null);
                }
            } else if (items == null) {
                abrirModal("SARA", "No se encontraron registros.", null);
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
        return respuesta;
    }

    private Collection<Cajero> consultarRegitrosSinTrasmision() {

        respuestaJSon = "";
        String respuesta = "";
        Collection<Cajero> items = null;

        Date fechaIni = null;
        Date fechaFin = null;
        int codigoCajero = 0;
        Calendar clrfechaInicial = null;
        Integer codigoCiclo;
        String strCiclo = "";
        String strYear = "";
        String strMes = "";
        String strDia = "";

        try {
            codigoCajero = Integer.parseInt(cajeroSeleccionado);
        } catch (Exception ex) {
            codigoCajero = 0;
        }
        if (codigoCajero == 0) {
            throw new IllegalArgumentException("Debe seleccionar un cajero");
        }

        
           
        try {
            fechaIni = getFechaTransformada(fechaDesde, horaDesde);
            clrfechaInicial = getFechaTransformadaCalendar(fechaDesde, horaDesde);

        } catch (Exception ex) {
            fechaIni = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
            clrfechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();
        }

        fechaInicialReporte = fechaIni;
        fechaFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaIni);

        strYear = String.valueOf(clrfechaInicial.get(Calendar.YEAR)).substring(2);
        if (clrfechaInicial.get(Calendar.MONTH) < 10) {
            strMes = "0" + String.valueOf(clrfechaInicial.get(Calendar.MONTH) + 1);
        } else {
            strMes = String.valueOf(clrfechaInicial.get(Calendar.MONTH));
        }
        if (clrfechaInicial.get(Calendar.DAY_OF_MONTH) < 10) {
            strDia = "0" + String.valueOf(clrfechaInicial.get(Calendar.DAY_OF_MONTH));
        } else {
            strDia = String.valueOf(clrfechaInicial.get(Calendar.DAY_OF_MONTH));
        }

        strCiclo = strYear + strMes + strDia;
        codigoCiclo = Integer.parseInt(strCiclo);

        try {
            // Consulta los registros según los parámetros tomados del request
            items = cajeroSession.getCajerosMultiSnTransmitir(codigoCiclo);

        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }
            if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }
            if (ex.getLocalizedMessage().contains("NoResultException")) {
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
        }

        if (items == null || items.isEmpty()) {
            items = null;
        }

        return items;
    }

    private List<CajerosSinTransmisionDTO> convertirListaCajerosSinTrasmision(Collection<Cajero> items) {
        List<CajerosSinTransmisionDTO> lista = new ArrayList<>();
        int i = 1;
        for (Cajero cajero : items) {
            CajerosSinTransmisionDTO cajLista = new CajerosSinTransmisionDTO();
            cajLista.setCodigoCajero(cajero.getCodigoCajero().toString());
            cajLista.setIdRegistro(i);
            cajLista.setNombreCajero(cajero.getNombre());
            lista.add(cajLista);
            i++;
        }
        return lista;
    }

    public String generarReporteSinTrasmision() {
        String respuesta = "";
        try {

            try {
                fechaInicial = formatter.parse(this.fechaDesde);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }

            if (null == cajeroSeleccionado || cajeroSeleccionado.equals("") || cajeroSeleccionado.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return "";
            }

            Collection<Cajero> regs = generarDiarioElectronicoSinTrasmision();
            if (regs != null && this.respuestaJSon.length() <= 1) {
                //Creo la hoja de cálculo
                String[] tituloHoja;
                tituloHoja = new String[2];
                tituloHoja[0] = "Cajeros sin Diario Electronico  ";
                tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicialReporte, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
                String[] titulosHoja = tituloHoja;
                String[] titulosColumna = TituloDiarioElectronicoGeneral.tituloColumnasCajero;

                Collection<Registro> lineas = new ArrayList<>();
                Short numColumna;
                try {
                    for (Cajero item : regs) {
                        Registro reg = new Registro();
                        numColumna = 0;
                        reg.addCelda(new Celda(numColumna++, item.getCodigoCajero(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getNombre(), TipoDatoCelda.NORMAL));
                        lineas.add(reg);
                    }

                    ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("CajerosSinTransmitir", titulosHoja, titulosColumna, lineas);
                    objectContext.enviarArchivoXLS(archivo);
                    respuesta = "Se ha enviado la solicitud puede cerrar esta ventana y continuar con las consultas ...";
                } catch (IllegalArgumentException ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                    abrirModal("SARA", respuestaJSon, ex);
                } catch (IOException ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_CREAR_ARCHIVO);
                    abrirModal("SARA", respuestaJSon, ex);
                } catch (Exception ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                    abrirModal("SARA", respuestaJSon, ex);
                }

            } else {
                respuesta = this.respuestaJSon;
                abrirModal("SARA", respuesta, null);
            }
            //FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelPaginas");        

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
        return respuesta;
    }

    private Collection<Cajero> generarDiarioElectronicoSinTrasmision() {

        respuestaJSon = "";
        String respuesta = "";
        Collection<Cajero> items = null;
        try {

            Date fechaIni = null;
            Date fechaFin = null;
            int codigoCajero = 0;
            Calendar clrfechaInicial = null;
            Integer codigoCiclo;
            String strCiclo = "";
            String strYear = "";
            String strMes = "";
            String strDia = "";
            if (cajeroSeleccionado == null) {
                throw new IllegalArgumentException("Debe seleccionar un cajero");
            }
            if (fechaDesde == null || fechaDesde.isEmpty()) {
                throw new IllegalArgumentException("Debe seleccionar una fecha inicial");
            }
            if (horaDesde == null) {
                throw new IllegalArgumentException("Debe seleccionar una hora inicial");
            }

            try {
                fechaIni = getFechaTransformada(fechaDesde, horaDesde);
                clrfechaInicial = getFechaTransformadaCalendar(fechaDesde, horaDesde);

            } catch (Exception ex) {
                fechaIni = com.davivienda.utilidades.conversion.Fecha.getDateAyer();

            }

            if (null == clrfechaInicial) {
                clrfechaInicial = Fecha.getCalendarAyer();
            }
            fechaInicialReporte = fechaIni;
            fechaFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaIni);
            //String strCiclo = String.valueOf(clrfechaInicial.get(Calendar.DAY_OF_MONTH)) + String.valueOf(clrfechaInicial.get(Calendar.YEAR)).substring(2);
            strYear = String.valueOf(clrfechaInicial.get(Calendar.YEAR)).substring(2);
            if (clrfechaInicial.get(Calendar.MONTH) < 10) {
                strMes = "0" + String.valueOf(clrfechaInicial.get(Calendar.MONTH) + 1);
            } else {
                strMes = String.valueOf(clrfechaInicial.get(Calendar.MONTH));
            }
            if (clrfechaInicial.get(Calendar.DAY_OF_MONTH) < 10) {
                strDia = "0" + String.valueOf(clrfechaInicial.get(Calendar.DAY_OF_MONTH));
            } else {
                strDia = String.valueOf(clrfechaInicial.get(Calendar.DAY_OF_MONTH));
            }

            strCiclo = strYear + strMes + strDia;
            codigoCiclo = Integer.parseInt(strCiclo);

            try {
                // Consulta los registros según los parámetros tomados del request
                items = cajeroSession.getCajerosMultiSnTransmitir(codigoCiclo);

            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
        }
        if (items == null || items.isEmpty()) {
            items = null;
        }

        return items;
    }

    public String generarReporteDiarioElectronico() {

        String respuesta = "";
        try {

            try {
                Date fechaInicialT = formatter.parse(this.fechaDesde);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }

            BigDecimal newValDecCheque;
            Long newValLongCheque;

            if (tipoDiario == null || tipoDiario.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_DIARIO, null);
                return "";
            }

            if (tipoDiario.length() > 0) {

                Integer tipointDiario = com.davivienda.utilidades.conversion.Cadena.aInteger(tipoDiario);
                TipoDiario tipoDiarioLocal = TipoDiario.Efectivo;
                tipoDiarioLocal = TipoDiario.getTipoDiario(tipointDiario);

                switch (tipoDiarioLocal) {
                    case Efectivo:
                        Collection<Txmultifuncionalefectivo> regsEfectivo = consultarTransaccionesEfectivo();

                        if (this.respuestaJSon.length() <= 1) {
                            //Creo la hoja de cálculo
                            String[] titulosHojaEfectivo = TituloDiarioMultifuncionalGeneral.tituloHojaEfectivo;
                            String[] titulosColumna = TituloDiarioMultifuncionalGeneral.tituloColumnasEfectivo;
                            Collection<Registro> lineas = new ArrayList<>();
                            Short numColumna;
                            try {
                                if (regsEfectivo != null) {
                                    for (Txmultifuncionalefectivo item : regsEfectivo) {
                                        Registro reg = new Registro();
                                        numColumna = 0;

                                        int codigoTx = 0;
                                        int codigoCajero = 0;
                                        if (item.getTxmultifuncionalefectivoPK() != null) {
                                            codigoTx = item.getTxmultifuncionalefectivoPK().getCodigotx();
                                            codigoCajero = item.getTxmultifuncionalefectivoPK().getCodigocajero();
                                        }
                                        reg.addCelda(new Celda(numColumna++, codigoTx, TipoDatoCelda.NUMERICO));
                                        //revisa estado EstadosDiarioMultifuncional

                                        String estado = "";
                                        if (item.getEstado() != null) {
                                            estado = com.davivienda.utilidades.conversion.Cadena.aCerosIzquierda(item.getEstado(), 2) + " - " + EstadosDiarioMultifuncional.getEstadoDiarioMultifuncional(item.getEstado()).nombre;
                                        }

                                        reg.addCelda(new Celda(numColumna++, estado, TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, codigoCajero, TipoDatoCelda.NUMERICO));

                                        int numeroCorte = 0;
                                        if (item.getNumerocorte() != null) {
                                            numeroCorte = item.getNumerocorte();
                                        }

                                        reg.addCelda(new Celda(numColumna++, numeroCorte, TipoDatoCelda.NUMERICO));

                                        int transaccionConsecutivo = 0;
                                        if (item.getTransaccionconsecutivo() != null) {
                                            transaccionConsecutivo = item.getTransaccionconsecutivo();
                                        }

                                        reg.addCelda(new Celda(numColumna++, transaccionConsecutivo, TipoDatoCelda.NUMERICO));

                                        String fechaCierre = "";
                                        if (item.getFechacierre() != null) {
                                            fechaCierre = com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFechacierre(), FormatoFecha.FECHA_HORA);
                                        }

                                        reg.addCelda(new Celda(numColumna++, fechaCierre, TipoDatoCelda.NORMAL));

                                        String fechaCajero = "";
                                        if (item.getTxmultifuncionalefectivoPK() != null && item.getTxmultifuncionalefectivoPK().getFechacajero() != null) {
                                            fechaCajero = com.davivienda.utilidades.conversion.Fecha.aCadena(item.getTxmultifuncionalefectivoPK().getFechacajero(), FormatoFecha.FECHA_HORA);
                                        }

                                        reg.addCelda(new Celda(numColumna++, fechaCajero, TipoDatoCelda.NORMAL));

                                        String tipoCuenta = "";
                                        if (item.getTipocuenta() != null) {
                                            tipoCuenta = TipoCuentaMultifuncional.getTipoCuentaMultifuncional((Integer) item.getTipocuenta().intValue()).nombre;
                                        }

                                        reg.addCelda(new Celda(numColumna++, tipoCuenta, TipoDatoCelda.NORMAL));

                                        String numerocuentaconsignar = "";
                                        if (item.getNumerocuentaconsignar() != null) {
                                            numerocuentaconsignar = item.getNumerocuentaconsignar();
                                        }

                                        reg.addCelda(new Celda(numColumna++, numerocuentaconsignar, TipoDatoCelda.NORMAL));

                                        String numerocuentahomologar = "";
                                        if (item.getNumerocuentahomologa() != null) {
                                            numerocuentahomologar = item.getNumerocuentahomologa();
                                        }

                                        reg.addCelda(new Celda(numColumna++, numerocuentahomologar, TipoDatoCelda.NORMAL));

                                        String valorConsignacion = "";
                                        if (item.getValorconsignacion() != null) {
                                            valorConsignacion = com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValorconsignacion());
                                        }

                                        reg.addCelda(new Celda(numColumna++, valorConsignacion, TipoDatoCelda.NORMAL));

                                        int nobilletesnd = 0;
                                        if (item.getNobilletesnd() != null) {
                                            nobilletesnd = item.getNobilletesnd();
                                        }

                                        reg.addCelda(new Celda(numColumna++, nobilletesnd, TipoDatoCelda.NUMERICO));

                                        int nobilletesdenominacionf = 0;
                                        if (item.getNobilletesdenominacionf() != null) {
                                            nobilletesdenominacionf = item.getNobilletesdenominacionf();
                                        }

                                        reg.addCelda(new Celda(numColumna++, nobilletesdenominacionf, TipoDatoCelda.NUMERICO));

                                        int nobilletesdenominacione = 0;
                                        if (item.getNobilletesdenominacione() != null) {
                                            nobilletesdenominacione = item.getNobilletesdenominacione();
                                        }

                                        reg.addCelda(new Celda(numColumna++, nobilletesdenominacione, TipoDatoCelda.NUMERICO));
                                        
                                        int nobilletesdenominaciond = 0;
                                        if (item.getNobilletesdenominaciond() != null) {
                                            nobilletesdenominaciond = item.getNobilletesdenominaciond();
                                        }
                                                                                
                                        reg.addCelda(new Celda(numColumna++, nobilletesdenominaciond, TipoDatoCelda.NUMERICO));
                                        
                                        int nobilletesdenominacionc = 0;
                                        if (item.getNobilletesdenominacionc() != null) {
                                            nobilletesdenominacionc = item.getNobilletesdenominacionc();
                                        }
                                                                                
                                        reg.addCelda(new Celda(numColumna++, nobilletesdenominacionc, TipoDatoCelda.NUMERICO));
                                        
                                        int nobilletesdenominacionb = 0;
                                        if (item.getNobilletesdenominacionb() != null) {
                                            nobilletesdenominacionb = item.getNobilletesdenominacionb();
                                        }
                                        
                                        reg.addCelda(new Celda(numColumna++, nobilletesdenominacionb, TipoDatoCelda.NUMERICO));
                                        
                                        int nobilletesdenominaciona = 0;
                                        if (item.getNobilletesdenominaciona() != null) {
                                            nobilletesdenominaciona = item.getNobilletesdenominaciona();
                                        }
                                        
                                        
                                        reg.addCelda(new Celda(numColumna++, nobilletesdenominaciona, TipoDatoCelda.NUMERICO));
                                        
                                        int totalBilletesConsignados = 0;
                                        if (item.getTotalbilletesconsignados() != null) {
                                            totalBilletesConsignados = item.getTotalbilletesconsignados();
                                        }
                                        
                                        reg.addCelda(new Celda(numColumna++, totalBilletesConsignados, TipoDatoCelda.NUMERICO));
                                        lineas.add(reg);
                                    }
                                }
                                ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("DiarioMultifuncionalEfectivo", titulosHojaEfectivo, titulosColumna, lineas);
                                objectContext.enviarArchivoXLS(archivo);
                                respuesta = "Se ha enviado la solicitud puede cerrar esta ventana y continuar con las consultas ...";

                            } catch (IllegalArgumentException ex) {
                                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
                                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                                respuesta = this.respuestaJSon;
                                abrirModal("SARA", respuestaJSon, ex);
                            } catch (IOException ex) {
                                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
                                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_CREAR_ARCHIVO);
                                respuesta = this.respuestaJSon;
                                abrirModal("SARA", respuestaJSon, ex);
                            } catch (Exception ex) {
                                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
                                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                                respuesta = this.respuestaJSon;
                                abrirModal("SARA", respuestaJSon, ex);
                            }

                        } else {
                            respuesta = this.respuestaJSon;
                        }
                        break;

                    case Cheque:
                        Collection<Txmultifuncionalcheque> regsCheque = consultarTransaccionesCheque();
                        Txcontrolmulticheque itemsControl = null;
                        if (this.respuestaJSon.length() <= 1) {
                            //Creo la hoja de cálculo
                            String[] titulosHojaCheque = TituloDiarioMultifuncionalGeneral.tituloHojaCheque;
                            String[] titulosColumna = TituloDiarioMultifuncionalGeneral.tituloColumnasCheque;
                            Collection<Registro> lineas = new ArrayList<>();
                            Short numColumna;
                            try {
                                if (regsCheque != null) {
                                    for (Txmultifuncionalcheque item : regsCheque) {
                                        Registro reg = new Registro();
                                        numColumna = 0;
                                        reg.addCelda(new Celda(numColumna++, item.getTxmultifuncionalchequePK().getCodigotransaccion(), TipoDatoCelda.NUMERICO));
                                        reg.addCelda(new Celda(numColumna++, item.getTxmultifuncionalchequePK().getCodigocajero(), TipoDatoCelda.NUMERICO));
                                        reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getTxmultifuncionalchequePK().getFechacajero(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, item.getNumerocorte(), TipoDatoCelda.NUMERICO));
                                        reg.addCelda(new Celda(numColumna++, item.getConsecutivotransaccion(), TipoDatoCelda.NUMERICO));
                                        reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFechacierre(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Cadena.aCerosIzquierda(item.getEstado(), 2) + " - " + EstadosDiarioMultifuncional.getEstadoDiarioMultifuncional(item.getEstado()).nombre, TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, item.getConsecutivochequeconsignacion(), TipoDatoCelda.NUMERICO));
                                        reg.addCelda(new Celda(numColumna++, TipoCuentaMultifuncional.getTipoCuentaMultifuncional(com.davivienda.utilidades.conversion.Cadena.aInteger(item.getTipocuenta())).nombre, TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, item.getNumerocuentaconsignar(), TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, item.getNumerocuentahomologa(), TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, item.getChequepropio(), TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, item.getRuta(), TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, item.getTransito(), TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, item.getCuenta(), TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, item.getSerial().toString(), TipoDatoCelda.NORMAL));
                                        reg.addCelda(new Celda(numColumna++, item.getOficina(), TipoDatoCelda.NUMERICO));
                                        newValLongCheque = item.getValorchequeusuario() / 100;
                                        try {

                                            newValDecCheque = new BigDecimal(newValLongCheque.toString() + "." + item.getValorchequeusuario().toString().substring(newValLongCheque.toString().length()));
                                            reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMonedaDecimal(newValDecCheque), TipoDatoCelda.MONEDA));

                                        } catch (Exception ex) {

                                            reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(newValLongCheque), TipoDatoCelda.MONEDA));
                                        }

                                        itemsControl = ObtenerDatosControlMultiCheque(item.getIdregistrocontrol().getIdregistro());

                                        reg.addCelda(new Celda(numColumna++, itemsControl.getPlaza(), TipoDatoCelda.NUMERICO));

                                        reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(itemsControl.getFechaarchivo(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                                        newValLongCheque = itemsControl.getMontoarchivo() / 100;
                                        try {

                                            newValDecCheque = new BigDecimal(newValLongCheque.toString() + "." + itemsControl.getMontoarchivo().toString().substring(newValLongCheque.toString().length()));
                                            reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMonedaDecimal(newValDecCheque), TipoDatoCelda.MONEDA));

                                        } catch (Exception ex) {

                                            reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(newValLongCheque), TipoDatoCelda.MONEDA));
                                        }

                                        reg.addCelda(new Celda(numColumna++, itemsControl.getCantidadcheques(), TipoDatoCelda.NUMERICO));

                                        lineas.add(reg);
                                    }
                                }

                                ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("DiarioMultifuncionalCheque", titulosHojaCheque, titulosColumna, lineas);
                                objectContext.enviarArchivoXLS(archivo);
                                respuesta = "Se ha enviado la solicitud puede cerrar esta ventana y continuar con las consultas ...";

                            } catch (IllegalArgumentException ex) {
                                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
                                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                                respuesta = this.respuestaJSon;
                                abrirModal("SARA", respuestaJSon, ex);
                            } catch (IOException ex) {
                                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
                                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_CREAR_ARCHIVO);
                                respuesta = this.respuestaJSon;
                                abrirModal("SARA", respuestaJSon, ex);
                            } catch (Exception ex) {
                                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
                                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                                respuesta = this.respuestaJSon;
                                abrirModal("SARA", respuestaJSon, ex);
                            }

                        } else {
                            respuesta = this.respuestaJSon;
                            abrirModal("SARA", respuesta, null);
                        }
                        break;
                }
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
        return respuesta;
    }

    private Collection<Txmultifuncionalefectivo> consultarTransaccionesEfectivo() {

        respuestaJSon = "";
        Collection<Txmultifuncionalefectivo> items = null;

        try {
            Date fechaIni = null;
            Date fechaFin = null;
            Integer codigoCajero = 0;
            Integer numeroTransaccion = 0;
            Calendar clFechaInicial;
            String cuentaC = "";
            try {
                codigoCajero = Integer.parseInt(cajeroSeleccionado);
            } catch (Exception ex) {
                codigoCajero = 0;
            }
            if (fechaDesde == null || fechaDesde.isEmpty()) {
                throw new IllegalArgumentException("Debe seleccionar una fecha inicial");
            }
            if (horaDesde == null) {
                throw new IllegalArgumentException("Debe seleccionar una hora inicial");
            }
            try {
                fechaIni = getFechaTransformada(fechaDesde, horaDesde);
                clFechaInicial = getFechaTransformadaCalendar(fechaDesde, horaDesde);

            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Debe seleccionar una fecha Inicial");
                //  fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
            }
            if(null == this.fechaHasta || this.fechaHasta.isEmpty()){
                fechaFin = getFechaTransformada(fechaDesde, horaHasta);
            }else{
                try {
                    fechaFin = getFechaTransformada(fechaHasta, horaHasta);
                } catch (Exception ex) {
                    fechaFin = fechaIni;
                }
            }

            try {
                numeroTransaccion = Integer.parseInt(talon);
            } catch (Exception ex) {
                numeroTransaccion = null;
            }

            try {
                cuentaC = cuenta;
            } catch (Exception ex) {
                cuentaC = null;
            }

            try {
                Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
                items = txMultifuncionalEfectivoSessionLocal.getColeccionTxEfectivo(codigoCajero, fechaIni, fechaFin, numeroTransaccion, cuentaC, fechaHisto);
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
        }
        if (items == null || items.isEmpty()) {
            items = null;
        }

        return items;
    }

    private Collection<Txmultifuncionalcheque> consultarTransaccionesCheque() {
        respuestaJSon = "";
        Collection<Txmultifuncionalcheque> items = null;

        try {

            Date fechaIni = null;
            Date fechaFin = null;
            Integer codigoCajero = 0;
            Integer numeroTransaccion = 0;
            Calendar clFechaInicial;
            String cuentaC = "";

            try {
                codigoCajero = Integer.parseInt(cajeroSeleccionado);
            } catch (Exception ex) {
                codigoCajero = 0;
            }
            if (fechaDesde == null || fechaDesde.isEmpty()) {
                throw new IllegalArgumentException("Debe seleccionar una fecha inicial");
            }
            if (horaDesde == null) {
                throw new IllegalArgumentException("Debe seleccionar una hora inicial");
            }
            try {
                fechaIni = getFechaTransformada(fechaDesde, horaDesde);
                clFechaInicial = getFechaTransformadaCalendar(fechaDesde, horaDesde);

            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Debe seleccionar una fecha Inicial");
            }
            try {
                fechaFin = getFechaTransformada(fechaHasta, horaHasta);
            } catch (IllegalArgumentException ex) {
                fechaFin = fechaIni;
            }

            try {
                numeroTransaccion = Integer.parseInt(talon);
            } catch (IllegalArgumentException ex) {
                numeroTransaccion = null;
            }

            try {
                cuentaC = cuenta;
            } catch (IllegalArgumentException ex) {
                cuentaC = null;
            }

            try {

                items = txMultifuncionalChequeSessionLocal.getColeccionTxCheque(codigoCajero, fechaIni, fechaFin, numeroTransaccion, cuentaC);

            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
        }
        if (items == null || items.isEmpty()) {
            items = null;
        }

        return items;
    }

    public String generarReporteLogEventos() {
        String respuesta = "";
        try {
            Date fechaInial = null;
            try {
                fechaInial = formatter.parse(this.fechaDesde);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }

            if (null == cajeroSeleccionado || cajeroSeleccionado.equals("") || cajeroSeleccionado.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return "";
            }

            Collection<Logcajeromulti> regs = generarLogEventos();
            if (regs != null && this.respuestaJSon.length() <= 1) {
                Date fechaIni = null;
                try {
                    fechaIni = getFechaTransformada(fechaDesde, horaDesde);

                } catch (Exception ex) {
                    fechaIni = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
                }

                fechaInicialReporte = fechaIni;

                String[] tituloHoja = new String[2];
                tituloHoja[0] = "LOG EVENTOS CAJERO  ";
                tituloHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicialReporte, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
                String[] titulosColumna = TituloDiarioMultifuncionalGeneral.tituloColumnasLogCajero;

                Collection<Registro> lineas = new ArrayList<>();
                Short numColumna;
                try {
                    for (Logcajeromulti item : regs) {
                        Registro reg = new Registro();
                        numColumna = 0;
                        reg.addCelda(new Celda(numColumna++, item.getLogcajeromultiPK().getCodigocajero(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getLogcajeromultiPK().getFechacargue(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getNombrearchivo(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getLogcajeromultiPK().getSecuencia(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getDatos(), TipoDatoCelda.NORMAL));

                        lineas.add(reg);
                    }

                    ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("LOG_Cajero", tituloHoja, titulosColumna, lineas);
                    objectContext.enviarArchivoXLS(archivo);
                    respuesta = "Se ha enviado la solicitud puede cerrar esta ventana y continuar con las consultas ...";
                } catch (IllegalArgumentException ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                    respuesta = this.respuestaJSon;
                    abrirModal("SARA", respuestaJSon, ex);
                } catch (IOException ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_CREAR_ARCHIVO);
                    respuesta = this.respuestaJSon;
                    abrirModal("SARA", respuestaJSon, ex);
                } catch (Exception ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                    respuesta = this.respuestaJSon;
                    abrirModal("SARA", respuestaJSon, ex);
                }

            } else {
                respuesta = this.respuestaJSon;
                if (respuesta == null) {
                    if (regs == null) {
                        abrirModal("SARA", "No se encontraron registros", null);
                    }
                } else if (!respuesta.isEmpty()) {
                    abrirModal("SARA", respuesta, null);
                } else if (regs == null) {
                    abrirModal("SARA", "No se encontraron registros", null);
                }
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
            return null;
        }
        return respuesta;
    }

    private Collection<Logcajeromulti> generarLogEventos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Logcajeromulti> items = null;

        Date fechaIni = null;
        Date fechaFin = null;
        int codigoCajero = 0;
        Calendar clrfechaInicial = null;
        Integer codigoCiclo;

        try {
            codigoCajero = Integer.parseInt(cajeroSeleccionado);
        } catch (Exception ex) {
            codigoCajero = 0;
        }

        if (codigoCajero == 0) {
            respuestaJSon = "Debe seleccionar un cajero";
            return null;
        }

        try {
            fechaIni = getFechaTransformada(fechaDesde, horaDesde);
            clrfechaInicial = getFechaTransformadaCalendar(fechaDesde, horaDesde);

        } catch (Exception ex) {
            fechaIni = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
            clrfechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();
        }

        fechaInicialReporte = fechaIni;
        fechaFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaIni);
        String strCiclo = String.valueOf(clrfechaInicial.get(Calendar.DAY_OF_MONTH)) + String.valueOf(clrfechaInicial.get(Calendar.YEAR)).substring(2);
        codigoCiclo = Integer.parseInt(strCiclo);
        codigoCiclo = ((clrfechaInicial.get(Calendar.MONTH) + 1) * 10000) + codigoCiclo;

        try {
            // Consulta los registros según los parámetros tomados del request
            items = logSession.getColeccionLogCajeroMulti(codigoCajero, fechaIni);

        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }
            if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }
            if (ex.getLocalizedMessage().contains("NoResultException")) {
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
        }

        if (items == null || items.isEmpty()) {
            items = null;
        }

        return items;
    }

    private List<SelectItem> cargarListaHora() {
        List<SelectItem> lista = new ArrayList<>();
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

    public void generarTransacciones() {
        String error = "";
        Collection<Transaccion> itemsTransaccion = null;
        try {

            Date fechaIni = null;
            Date fechaFin = null;
            Integer codigoCajer = 0;
            Integer numeroTransaccion = 0;
            Calendar clFechaInicial;
            Map<String, Object> criterios = new HashMap<>();
            String strTmp = null;

            try {
                codigoCajer = 9975;//Integer.parseInt(this.codigoCajero);
            } catch (IllegalArgumentException ex) {
                codigoCajer = 0;
            }
            try {
                fechaIni = com.davivienda.utilidades.conversion.Fecha.getDate(-3);//this.fechaInicial;
                clFechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaIni);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Debe seleccionar una fecha Inicial");

            }
            try {
                fechaFin = com.davivienda.utilidades.conversion.Fecha.getDate(-3);
            } catch (IllegalArgumentException ex) {
                fechaFin = fechaIni;
            }

            try {
                numeroTransaccion = null;//Integer.parseInt(this.getTalon());
            } catch (IllegalArgumentException ex) {
                numeroTransaccion = null;
            }

            try {
                strTmp = "";//this.referencia;
                criterios.put("referencia", strTmp);
            } catch (IllegalArgumentException ex) {
                strTmp = "";
            }

            try {
                strTmp = "";//this.cuenta;
                criterios.put("productoOrigen", strTmp);
            } catch (IllegalArgumentException ex) {
                strTmp = "";
            }

            try {
                strTmp = "";//this.tarjeta;
                criterios.put("tarjeta", strTmp);
            } catch (IllegalArgumentException ex) {
                strTmp = "";
            }

            try {
                itemsTransaccion = sessionTransaccion.getColeccionTransaccion(codigoCajer, fechaIni, fechaFin, numeroTransaccion, criterios);
                if (itemsTransaccion.isEmpty() && (Fecha.getFechaInicioDia(clFechaInicial).equals(Fecha.getFechaInicioDia(Fecha.getCalendarAyer())))) {
                    setItemsDTemp(transaccionTempSession.getColeccionTransaccionTemp(codigoCajer, fechaIni, fechaFin, numeroTransaccion, criterios));
                }

            } catch (EJBException ex) {
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    error = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                }
            } catch (Exception ex) {
                error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
            }

        } catch (IllegalArgumentException ex) {
            error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
        }
        if (itemsTransaccion == null || itemsTransaccion.isEmpty()) {
            itemsTransaccion = null;
        } else {
            this.getTransacciones().clear();
            this.getTransacciones().addAll(convertItemsTransaccion(itemsTransaccion));
            this.setMostrarPanelGeneral(false);
            this.setMostrarDiarioElectronicoCheque(false);
            this.setMostrarDiarioElectronicoEfectivo(false);
            this.setMostrarHistorial(false);
            this.setMostrarCajSinTransmision(false);
        }
    }

    public void generarHistoricoCargue() {
        String error = "";
        String respuesta = "";
        Collection<EdcCargue> items = null;
        int codigoCiclo;
        try {

            Date fechaIni = null;
            Date fechaFin = null;

            try {
                fechaIni = objectContext.getAtributoFechaHoraInicial().getTime();

            } catch (IllegalArgumentException ex) {
                fechaIni = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
            }
            try {
                fechaFin = objectContext.getAtributoFechaHoraFinal().getTime();
            } catch (IllegalArgumentException ex) {
                fechaFin = fechaIni;
            }
            fechaFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaIni);

            try {
                // Consulta los registros según los parámetros tomados del request
                items = sessionHistoricoCargue.getEDCCarguePorFecha(fechaIni, fechaFin);
            } catch (EJBException ex) {
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    error = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                }
            } catch (Exception ex) {
                error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
            }

        } catch (IllegalArgumentException ex) {
            error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
        }
        if (items == null || items.isEmpty()) {
            error = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
        } else {
            this.getHistoricosCargue().clear();
            this.getHistoricosCargue().addAll(convertItemsHistorico(items));
            this.setMostrarPanelGeneral(false);
            this.setMostrarDiarioElectronicoCheque(false);
            this.setMostrarDiarioElectronicoEfectivo(false);
            this.setMostrarHistorial(true);
            this.setMostrarCajSinTransmision(false);
        }

    }

    public void generarNumTrasacciones() {
        String error = "";
        String respuesta = "";
        Collection<CantidadTransaccionesBean> itemsNumeroTransacciones = null;
        try {

            Date fechaIni = null;
            Date fechaFin = null;
            Integer codigoCajer = 0;

            //codigoCajero = objectContext.getCodigoCajero();
            codigoCajer = objectContext.getCodigo_Cajero();
            if (codigoCajer == 0) {
                throw new IllegalArgumentException("Debe seleccionar un cajero");
            }

            try {
                fechaIni = objectContext.getAtributoFechaHoraInicial().getTime();
            } catch (IllegalArgumentException ex) {
                fechaIni = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
            }
            try {
                fechaFin = objectContext.getAtributoFechaHoraFinal().getTime();
            } catch (IllegalArgumentException ex) {
                fechaFin = fechaIni;
            }
            fechaFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaIni);

            try {
                // Consulta los registros según los parámetros tomados del request
                itemsNumeroTransacciones = sessionNumTransacciones.getTransaccionesRealizadasPorCajero(null, null, codigoCajer, 9999, fechaInicial, fechaFinal);
                this.getNumTransacciones().clear();
                this.getNumTransacciones().addAll(convertItemsNumTransacciones(itemsNumeroTransacciones));
                this.setMostrarPanelGeneral(false);
                this.setMostrarDiarioElectronicoCheque(false);
                this.setMostrarDiarioElectronicoEfectivo(false);
                this.setMostrarHistorial(false);
                this.setMostrarCajSinTransmision(false);
            } catch (EJBException ex) {
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    error = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                }
            } catch (EntityServicioExcepcion | IllegalArgumentException ex) {
                error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
            }

        } catch (IllegalArgumentException ex) {
            error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
        }

    }

    public void cajerosSinTransmision() {

        String error = "";
        String respuesta = "";
        Collection<Cajero> itemsSinTransmision = null;

        try {

            Date fechaIn = null;
            Date fechaFin = null;
            Integer codigoCajer = 0;
            Calendar clrfechaInicial = null;
            Integer codigoCiclo;

            //codigoCajero = objectContext.getCodigoCajero();
            codigoCajer = objectContext.getCodigo_Cajero();
            if (codigoCajer == 0) {
                throw new IllegalArgumentException("Debe seleccionar un cajero");
            }

            try {
                fechaIn = objectContext.getAtributoFechaHoraInicial().getTime();
                clrfechaInicial = objectContext.getAtributoFechaHoraInicial();

            } catch (IllegalArgumentException ex) {
                fechaIn = com.davivienda.utilidades.conversion.Fecha.getDateAyer();

            }
            if (null == clrfechaInicial) {
                clrfechaInicial = Fecha.getCalendarAyer();
            }

            fechaInicialReporte = fechaIn;
            fechaFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaIn);
            String strCiclo = String.valueOf(clrfechaInicial.get(Calendar.DAY_OF_MONTH)) + String.valueOf(clrfechaInicial.get(Calendar.YEAR)).substring(2);
            codigoCiclo = Integer.parseInt(strCiclo);
            codigoCiclo = ((clrfechaInicial.get(Calendar.MONTH) + 1) * 10000) + codigoCiclo;

            try {
                // Consulta los registros según los parámetros tomados del request
                itemsSinTransmision = cajeroSession.getCajerosSnTransmitir(codigoCiclo);
                this.cajerosSinTransamision.clear();
                this.cajerosSinTransamision.addAll(convertItemsCajerosSinTransmision(itemsSinTransmision));
                this.setMostrarPanelGeneral(false);
                this.setMostrarDiarioElectronicoCheque(false);
                this.setMostrarDiarioElectronicoEfectivo(false);
                this.setMostrarHistorial(false);
                this.setMostrarCajSinTransmision(false);
            } catch (EJBException ex) {
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    error = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                }
            } catch (Exception ex) {
                error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
            }

        } catch (IllegalArgumentException ex) {
            error = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
        }
    }

    public Collection<DiarioElectronicoTransaccionDTO> setColeccionRegistroDiarioElectronico(Collection<DiarioElectronico> registros) {
        Collection<DiarioElectronicoTransaccionDTO> coleccionDiarioElectronicoBean = new ArrayList<>();
        for (DiarioElectronico edcRegistro : registros) {
            DiarioElectronicoTransaccionDTO bean = convertDiarioDTO(edcRegistro);
            coleccionDiarioElectronicoBean.add(bean);
        }
        return coleccionDiarioElectronicoBean;
    }

    private DiarioElectronicoTransaccionDTO convertDiarioDTO(DiarioElectronico edcRegistro) {
        DiarioElectronicoTransaccionDTO bean = new DiarioElectronicoTransaccionDTO();
        bean.tipoRegistro = String.valueOf(edcRegistro.getTipoRegistro());
        bean.secuencia = edcRegistro.getDiarioElectronicoPK().getSecuencia();
        bean.datos = com.davivienda.utilidades.edc.Edc.aCadena(edcRegistro.getTipoRegistro(), edcRegistro.getInformacion());

        return bean;
    }

    public List<TransaccionDTO> convertItemsTransaccion(Collection<Transaccion> items) {
        Integer idRegistro = 0;
        List<TransaccionDTO> transaccionesLocal = new ArrayList<>();
        for (Transaccion tra : items) {
            TransaccionDTO transaccion = TransaccionDTO();
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

            transaccionesLocal.add(transaccion);

        }

        return transaccionesLocal;
    }

    public List<HistoricoCargueDTO> convertItemsHistorico(Collection<EdcCargue> items) {
        Integer idRegistro = 0;
        List<HistoricoCargueDTO> historicos = new ArrayList<>();

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

        return historicos;
    }

    public List<NumeroTransaccionesDTO> convertItemsNumTransacciones(Collection<CantidadTransaccionesBean> items) {
        Integer idRegistro = 0;
        List<NumeroTransaccionesDTO> numeroTransacciones = new ArrayList<>();
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
        List<CajerosSinTransmisionDTO> cajerosSinTransaccion = new ArrayList<>();
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
        String fechaStr = this.fechaInicial.toString();
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

    public Date getFechaTransformada(String fecha, String hora) throws IllegalArgumentException {
        String fechaStr = fecha;
        String horaStr = hora;
        fechaStr = fechaStr.concat(horaStr);
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        return calendar.getTime();
    }

    public Calendar getFechaTransformadaCalendar(String fecha, String hora) throws IllegalArgumentException {
        String fechaStr = fecha;
        String horaStr = hora;
        fechaStr = fechaStr.concat(horaStr);
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        return calendar;
    }

    public String getCajeroSeleccionado() {
        return cajeroSeleccionado;
    }

    public void setCajeroSeleccionado(String cajeroSeleccionado) {
        this.cajeroSeleccionado = cajeroSeleccionado;
    }

    /**
     * @return the fechaInicial
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param fechaInicial the fechaInicial to set
     */
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Date fechaFinal) {
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

    private TransaccionDTO TransaccionDTO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(String horaDesde) {
        this.horaDesde = horaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(String horaHasta) {
        this.horaHasta = horaHasta;
    }

    public List<CajeroDTO> getListaCajeros() {
        return listaCajeros;
    }

    public void setListaCajeros(List<CajeroDTO> listaCajeros) {
        this.listaCajeros = listaCajeros;
    }

    public String getTipoDiario() {
        return tipoDiario;
    }

    public void setTipoDiario(String tipoDiario) {
        this.tipoDiario = tipoDiario;
    }

    public boolean isMostrarDiarioElectronicoEfectivo() {
        return mostrarDiarioElectronicoEfectivo;
    }

    public void setMostrarDiarioElectronicoEfectivo(boolean mostrarDiarioElectronicoEfectivo) {
        this.mostrarDiarioElectronicoEfectivo = mostrarDiarioElectronicoEfectivo;
    }

    public boolean isMostrarDiarioElectronicoCheque() {
        return mostrarDiarioElectronicoCheque;
    }

    public void setMostrarDiarioElectronicoCheque(boolean mostrarDiarioElectronicoCheque) {
        this.mostrarDiarioElectronicoCheque = mostrarDiarioElectronicoCheque;
    }

    public List<TxMultifuncionalEfectivoDTO> getMultifuncionalEfectivo() {
        return multifuncionalEfectivo;
    }

    public void setMultifuncionalEfectivo(List<TxMultifuncionalEfectivoDTO> multifuncionalEfectivo) {
        this.multifuncionalEfectivo = multifuncionalEfectivo;
    }

    public List<TxMultifuncionalChequeDTO> getMultifuncionalcheque() {
        return multifuncionalcheque;
    }

    public void setMultifuncionalcheque(List<TxMultifuncionalChequeDTO> multifuncionalcheque) {
        this.multifuncionalcheque = multifuncionalcheque;
    }

}
