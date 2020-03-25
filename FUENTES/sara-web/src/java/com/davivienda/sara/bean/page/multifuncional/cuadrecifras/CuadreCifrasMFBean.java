/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.cuadrecifras;

import com.davivienda.multifuncional.constantes.TipoMovimientoCuadreMultifuncional;
import com.davivienda.multifuncional.cuadrecifrasmulti.session.CuadreCifrasMultiSessionLocal;
import com.davivienda.multifuncional.cuadremulticifras.bean.DetalleEfectivoBean;
import com.davivienda.multifuncional.cuadremulticifras.bean.InformeAyerMulti;
import com.davivienda.multifuncional.cuadremulticifras.bean.InformeDali40LSBean;
import com.davivienda.multifuncional.cuadremulticifras.bean.InformeDiferenciasMulti;
import com.davivienda.multifuncional.cuadremulticifras.formato.TituloDali40LSGeneral;
import com.davivienda.multifuncional.cuadremulticifras.formato.TituloDali50LSGeneral;
import com.davivienda.multifuncional.cuadremulticifras.formato.TituloInformeAyerMulti;
import com.davivienda.multifuncional.cuadremulticifras.formato.TituloInformeDiferencias;
import com.davivienda.multifuncional.cuadremulticifras.formato.TituloResumenDali;
import com.davivienda.multifuncional.tablas.biletajemulti.session.BilletajeMultiSessionLocal;
import com.davivienda.multifuncional.tablas.provisionhostmultifuncional.session.ProvisionHostMultifuncionalLocal;
import com.davivienda.multifuncional.tablas.totalesestacionmultifuncional.session.TotalesEstacionMultiSessionLocal;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.entitys.Billetajemulti;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.Movimientocuadremulti;
import com.davivienda.sara.entitys.Provisionhostmulti;
import com.davivienda.sara.entitys.Totalesestacionmultifuncional;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.ArchivoXLSDali40;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.JSon;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author calvarez
 */
@ManagedBean(name = "cuadreCifrasMFBean")
@ViewScoped
public class CuadreCifrasMFBean extends BaseBean implements Serializable {

    private String fecha;
    public ComponenteAjaxObjectContextWeb objectContext;
    private String respuestaJSon;

    //EJB INYECTIONS
    @EJB
    ProvisionHostMultifuncionalLocal sessionphml;

    @EJB
    BilletajeMultiSessionLocal sessionBMSL;

    @EJB
    TotalesEstacionMultiSessionLocal totalesEstacionMultiSession;

    @EJB
    CuadreCifrasMultiSessionLocal cuadreCifrasMultiSession;

    @EJB
    CajeroSessionLocal cajeroSession;

    private InformeAyerMulti informeAyerMulti = null;

    @PostConstruct
    public void CuadreCifrasMFBean() {
        objectContext = cargarComponenteAjaxObjectContext();
    }

    public void dataInicial() {
        this.fecha = "";
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void generarReporteDALI50LS() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Provisionhostmulti> items = null;
        Calendar fechaInicial = null;
        Calendar fechaFinal = null;
        Calendar fechaInicialAyer = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            try {
                Date fechaI = formatter.parse(this.fecha);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }

            try {
                Date fechaI = formatter.parse(this.fecha);
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);
                fechaInicialAyer = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial, -1);
            } catch (Exception ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();
                fechaInicialAyer = com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Fecha.getCalendarAyer(), -1);

            }
            try {
                fechaFinal = fechaInicial;
            } catch (Exception ex) {
                fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
            }

            try {
                items = sessionphml.getProvisionHostRangoFecha(fechaInicial, fechaFinal);
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
                respuesta = this.respuestaJSon;
                abrirModal("SARA", respuesta, null);
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                respuesta = this.respuestaJSon;
                abrirModal("SARA", respuesta, null);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            respuesta = this.respuestaJSon;
            abrirModal("SARA", respuesta, null);
        }
        if (items == null || items.isEmpty()) {
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
            respuesta = this.respuestaJSon;
            // abrirModal("SARA", respuesta, null);
        }

        if (items != null && this.respuestaJSon.length() <= 1) {
            respuesta = generarArchivoXLSReporteDALI50LS(items, fechaInicial);  // paso los items a JSON
            abrirModal("SARA", respuesta, null);
        } else {
            respuesta = this.respuestaJSon;
            abrirModal("SARA", respuesta, null);
        }
    }

    private String generarArchivoXLSReporteDALI50LS(Collection<Provisionhostmulti> items, Calendar fechaInicial) {
        String respuesta = "";
        if (items != null) {
            //Creo la hoja de cálculo
            String[] titulosHoja = TituloDali50LSGeneral.tituloHoja;
            String[] titulosColumna = TituloDali50LSGeneral.tituloColumnas;
            titulosHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);

            Collection<Registro> lineas = new ArrayList<>();
            Short numColumna;

            try {
                for (Provisionhostmulti item : items) {

                    Cajero cajero = new Cajero(item.getProvisionhostmultiPK().getCodigocajero());
                    Registro reg = new Registro();
                    numColumna = 0;

                    reg.addCelda(new Celda(numColumna++, item.getOfiMulti(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getProvisionhostmultiPK().getCodigocajero(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getNumCuenta(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getProvisionhostmultiPK().getFecha(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getProvisionhostmultiPK().getNumtalon(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getProvisionhostmultiPK().getTipo(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getProvisionhostmultiPK().getMotivo(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValor() / 100), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, item.getReferencia1(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getReferencia2(), TipoDatoCelda.NORMAL));
                    if (item.getProvisionhostmultiPK().getMotivo() != 45) {
                        lineas.add(reg);
                    }
                }
                ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("InformeDali50LS", titulosHoja, titulosColumna, lineas);
                objectContext.enviarArchivoXLS(archivo);
            } catch (IllegalArgumentException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), Constantes.MSJ_ERROR_CREAR_ARCHIVO);
                respuesta = this.respuestaJSon;
            } catch (IOException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_DESCARGAR_ARCHIVO);
                respuesta = this.respuestaJSon;
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                respuesta = this.respuestaJSon;
            }

        } else {
            respuesta = this.respuestaJSon;
        }
        return respuesta;
    }

    public void generarReporteDALI40LS() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Billetajemulti> items = null;
        Collection<Totalesestacionmultifuncional> itemsTotalesestacion = null;
        Collection<Billetajemulti> itemsdiaanterior = null;
        Collection<InformeDali40LSBean> informeDali40 = null;
        Collection<Movimientocuadremulti> itemsFaltanteSobrantes;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar fechaInicial = null;
        Calendar fechaFinal = null;
        Calendar fechaInicialAyer = null;

        try {

            try {
                Date fechaI = formatter.parse(this.fecha);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }

            try {

                Date fechaI = formatter.parse(this.fecha);
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);
                fechaInicialAyer = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial, -1);
            } catch (Exception ex) {

                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();
                fechaInicialAyer = com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Fecha.getCalendarAyer(), -1);
            }
            try {
                fechaFinal = fechaInicial;
            } catch (Exception ex) {
                fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);

            }
            try {

                items = sessionBMSL.getBilletajeMultiRangoFecha(fechaInicial, fechaFinal);
                itemsdiaanterior = sessionBMSL.getBilletajeMultiRangoFecha(fechaInicialAyer, fechaInicialAyer);
                itemsFaltanteSobrantes = cuadreCifrasMultiSession.getUsuarioFecha("Dali40Ajuste", fechaInicial.getTime());
                itemsTotalesestacion = totalesEstacionMultiSession.getTotalesEstacionMultiRangoFecha(fechaInicial, fechaFinal);
                informeDali40 = getCollectionInformeCDali40LS(items, itemsTotalesestacion, itemsdiaanterior, itemsFaltanteSobrantes);

                if (informeDali40 == null || informeDali40.isEmpty()) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
                    respuesta = this.respuestaJSon;
                    abrirModal("SARA", respuesta, null);
                } else {
                    respuestaJSon = generarArchivoXLSDali40LS(informeDali40, fechaInicial);
                }
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
                respuesta = this.respuestaJSon;
                abrirModal("SARA", respuesta, null);
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                respuesta = this.respuestaJSon;
                abrirModal("SARA", respuesta, null);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            respuesta = this.respuestaJSon;
            abrirModal("SARA", respuesta, null);
        }
        if (items == null || items.isEmpty()) {
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
            respuesta = this.respuestaJSon;
            //abrirModal("SARA", respuesta, null);
        } else if (items == null && itemsTotalesestacion == null && this.respuestaJSon.length() >= 1) {
            respuesta = this.respuestaJSon;
            abrirModal("SARA", respuesta, null);
        }
    }

    public Collection<InformeDali40LSBean> getCollectionInformeCDali40LS(Collection<Billetajemulti> regs, Collection<Totalesestacionmultifuncional> regsTotalesestacion, Collection<Billetajemulti> regsDiaanterior, Collection<Movimientocuadremulti> itemsFaltanteSobrantes) {

        Collection<InformeDali40LSBean> informes = new ArrayList<>();
        Map<Integer, DetalleEfectivoBean> mapRegDiaAnterior = new HashMap<>();
        Map<Integer, Long> mapFaltantesSobrantes = new HashMap<>();
        Map<Integer, Long> mapStratusDepEefect = new HashMap<>();
        Map<Integer, Long> mapStratusPagoEfectFM = new HashMap<>();
        Map<Integer, Long> mapStratusPagoEfectTC = new HashMap<>();
        Map<Integer, Long> mapStratusRecaudoEfect = new HashMap<>();

        Map<Integer, Long> mapStratusDepCheque = new HashMap<>();
        Map<Integer, Long> mapStratusPagoChequeFM = new HashMap<>();
        Map<Integer, Long> mapStratusPagoChequeTC = new HashMap<>();
        Map<Integer, Long> mapStratusRecaudoCheque = new HashMap<>();

        Long depEefect = (long) 0;
        Long pagoEfectFM = (long) 0;
        Long pagoEfectT_Credito = (long) 0;
        Long recaudoEfect = (long) 0;

        Long depCheque = (long) 0;
        Long pagoChequeFM = (long) 0;
        Long pagoChequeTT_Credito = (long) 0;
        Long recaudoCheque = (long) 0;

        Integer codCajero = 0;

        if (itemsFaltanteSobrantes != null) {
            for (Movimientocuadremulti movimientocuadremulti : itemsFaltanteSobrantes) {
                if (movimientocuadremulti.getCodtipomovimientocuadremuti() == (long) 201) {
                    mapFaltantesSobrantes.put(movimientocuadremulti.getCajero().getCodigoCajero(), (movimientocuadremulti.getValormovimiento() * -1));
                } else if (movimientocuadremulti.getCodtipomovimientocuadremuti() == (long) 215) {
                    mapFaltantesSobrantes.put(movimientocuadremulti.getCajero().getCodigoCajero(), movimientocuadremulti.getValormovimiento());
                }
            }
        }

        if (regsTotalesestacion != null) {

            for (Totalesestacionmultifuncional totalesestacionmulti : regsTotalesestacion) {
                if (codCajero.compareTo(0) == 0) {
                    codCajero = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero();
                }
                if (codCajero.compareTo(totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero()) != 0) {

                    mapStratusDepEefect.put(codCajero, depEefect / 100);
                    mapStratusPagoEfectFM.put(codCajero, pagoEfectFM / 100);
                    mapStratusPagoEfectTC.put(codCajero, pagoEfectT_Credito / 100);
                    mapStratusRecaudoEfect.put(codCajero, recaudoEfect / 100);
                    mapStratusDepCheque.put(codCajero, depCheque / 100);
                    mapStratusPagoChequeFM.put(codCajero, pagoChequeFM / 100);
                    mapStratusPagoChequeTC.put(codCajero, pagoChequeTT_Credito / 100);
                    mapStratusRecaudoCheque.put(codCajero, recaudoCheque / 100);
                    depEefect = new Long(0);
                    pagoEfectFM = new Long(0);
                    pagoEfectT_Credito = new Long(0);
                    recaudoEfect = new Long(0);
                    depCheque = new Long(0);
                    pagoChequeFM = new Long(0);
                    pagoChequeTT_Credito = new Long(0);
                    recaudoCheque = new Long(0);
                    codCajero = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero();
                }

                if (totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(475)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(476)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(477)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(483)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(481)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(95)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(361)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(485)) {
                    depEefect = depEefect + totalesestacionmulti.getValorevento();
                }

                if (totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(478)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(479)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(480)) {
                    depCheque = depCheque + totalesestacionmulti.getValorevento();
                }
                if (totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(484)) {
                    pagoChequeFM = pagoChequeFM + totalesestacionmulti.getValorevento();

                }
                if (totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(482)) {
                    pagoChequeTT_Credito = pagoChequeTT_Credito + totalesestacionmulti.getValorevento();

                }
                if (totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(486)) {
                    recaudoCheque = recaudoCheque + totalesestacionmulti.getValorevento();

                }
            }

            mapStratusDepEefect.put(codCajero, depEefect / 100);
            mapStratusPagoEfectFM.put(codCajero, pagoEfectFM / 100);
            mapStratusPagoEfectTC.put(codCajero, pagoEfectT_Credito / 100);
            mapStratusRecaudoEfect.put(codCajero, recaudoEfect / 100);
            mapStratusDepCheque.put(codCajero, depCheque / 100);
            mapStratusPagoChequeFM.put(codCajero, pagoChequeFM / 100);
            mapStratusPagoChequeTC.put(codCajero, pagoChequeTT_Credito / 100);
            mapStratusRecaudoCheque.put(codCajero, recaudoCheque / 100);
        }

        if (regs != null) {

            for (Billetajemulti billetajemulti : regs) {

                InformeDali40LSBean informe = new InformeDali40LSBean();
                informe.setCodigoCajero(billetajemulti.getBilletajemultiPK().getCodigocajero());
                informe.setDepositoEfectivoATM((billetajemulti.getValordepefectivoctaaho() + billetajemulti.getValordepefectivoctacte()));
                informe.setPagosFMEfectivoATM(billetajemulti.getValorpagoefectivofm());
                informe.setPagosTCEfectivoATM(billetajemulti.getValorpagoefectivotc());
                informe.setRecaudosEfectivoATM(billetajemulti.getValorrecaudoefectivo());
                informe.setDepositoChequeATM((billetajemulti.getValordepchequectacte() + billetajemulti.getValordepchequectacte()) / 100);
                informe.setPagosFMChequeATM(billetajemulti.getValorpagochequefm() / 100);
                informe.setPagosTCChequeATM(billetajemulti.getValorpagochequetc() / 100);
                informe.setRecaudosChequeATM(billetajemulti.getValorrecaudocheque() / 100);
                informe.setTotalRetractEfectivoCant(billetajemulti.getCantidadbilletesretract().longValue());
                informe.setTotalRetractEfectivoValor(billetajemulti.getValorefectivoretract());
                informe.setTotalRetractChequeCant(billetajemulti.getCantidadchequesretract().longValue());
                informe.setTotalRetractChequeValor(billetajemulti.getValorchequesretract() / 100);
                DetalleEfectivoBean detalle = new DetalleEfectivoBean();
                detalle.setDen1000Cantidad(billetajemulti.getCantidadbilletesdena().longValue());
                detalle.setDen1000Valor(billetajemulti.getValortotalbillesdena());
                detalle.setDen2000Cantidad(billetajemulti.getCantidadbilletesdenb().longValue());
                detalle.setDen2000Valor(billetajemulti.getValortotalbillesdenb());
                detalle.setDen5000Cantidad(billetajemulti.getCantidadbilletesdenc().longValue());
                detalle.setDen5000Valor(billetajemulti.getValortotalbillesdenc());
                detalle.setDen10000Cantidad(billetajemulti.getCantidadbilletesdend().longValue());
                detalle.setDen10000Valor(billetajemulti.getValortotalbillesdend());
                detalle.setDen20000Cantidad(billetajemulti.getCantidadbilletesdene().longValue());
                detalle.setDen20000Valor(billetajemulti.getValortotalbillesdene());
                detalle.setDen50000Cantidad(billetajemulti.getCantidadbilletesdenf().longValue());
                detalle.setDen50000Valor(billetajemulti.getValortotalbillesdenf());
                detalle.setTotalesEfectivoCantidad(billetajemulti.getCanbilletesrecibidos().longValue());
                detalle.setTotalesEfectivoValor(billetajemulti.getValortotalefectivo());
                detalle.setTotalChequeCantidad(billetajemulti.getCantidadnumerocheques().longValue());
                detalle.setTotalChequeValor(billetajemulti.getValorcheques() / 100);
                informe.setDetEfectivoRecibido(detalle);
                informe.setRecaudosEfectivoATM(billetajemulti.getValorrecaudoefectivo());
                informe.setDepositosTotalATM(billetajemulti.getValortotalefectivo());
                informes.add(informe);

            }
        }

        if (regsDiaanterior != null) {
            for (Billetajemulti billetajemulti : regsDiaanterior) {
                DetalleEfectivoBean detalle = new DetalleEfectivoBean();
                detalle.setDen1000Cantidad(billetajemulti.getCantidadbilletesdena().longValue());
                detalle.setDen1000Valor(billetajemulti.getValortotalbillesdena());
                detalle.setDen2000Cantidad(billetajemulti.getCantidadbilletesdenb().longValue());
                detalle.setDen2000Valor(billetajemulti.getValortotalbillesdenb());
                detalle.setDen5000Cantidad(billetajemulti.getCantidadbilletesdenc().longValue());
                detalle.setDen5000Valor(billetajemulti.getValortotalbillesdenc());
                detalle.setDen10000Cantidad(billetajemulti.getCantidadbilletesdend().longValue());
                detalle.setDen10000Valor(billetajemulti.getValortotalbillesdend());
                detalle.setDen20000Cantidad(billetajemulti.getCantidadbilletesdene().longValue());
                detalle.setDen20000Valor(billetajemulti.getValortotalbillesdene());
                detalle.setDen50000Cantidad(billetajemulti.getCantidadbilletesdenf().longValue());
                detalle.setDen50000Valor(billetajemulti.getValortotalbillesdenf());
                detalle.setTotalesEfectivoCantidad(billetajemulti.getCanbilletesrecibidos().longValue());
                detalle.setTotalesEfectivoValor(billetajemulti.getValortotalefectivo());
                detalle.setTotalChequeCantidad(billetajemulti.getCantidadnumerocheques().longValue());
                detalle.setTotalChequeValor(billetajemulti.getValorcheques() / 100);
                mapRegDiaAnterior.put(billetajemulti.getBilletajemultiPK().getCodigocajero(), detalle);

            }
        }

        for (InformeDali40LSBean informeDali40LSBean : informes) {
            DetalleEfectivoBean detalle = new DetalleEfectivoBean();
            if (!mapRegDiaAnterior.isEmpty()) {
                if (mapRegDiaAnterior.containsKey(informeDali40LSBean.getCodigoCajero())) {
                    detalle = mapRegDiaAnterior.get(informeDali40LSBean.getCodigoCajero());
                }
                informeDali40LSBean.setDetEfectivoIniJor(detalle);
            } else {
                informeDali40LSBean.setDetEfectivoIniJor(detalle);
            }

            if (!mapStratusDepEefect.isEmpty()) {
                if (mapStratusDepEefect.containsKey(informeDali40LSBean.getCodigoCajero())) {
                    informeDali40LSBean.setDepositoEfectivoStratus(mapStratusDepEefect.get(informeDali40LSBean.getCodigoCajero()));
                }
            }

            if (!mapStratusPagoEfectFM.isEmpty()) {
                if (mapStratusPagoEfectFM.containsKey(informeDali40LSBean.getCodigoCajero())) {
                    informeDali40LSBean.setPagosFMEfectivoStratus(mapStratusPagoEfectFM.get(informeDali40LSBean.getCodigoCajero()));
                }
            }
            if (!mapStratusPagoEfectTC.isEmpty()) {
                if (mapStratusPagoEfectTC.containsKey(informeDali40LSBean.getCodigoCajero())) {
                    informeDali40LSBean.setPagosTCEfectivoStratus(mapStratusPagoEfectTC.get(informeDali40LSBean.getCodigoCajero()));
                }
            }
            if (!mapStratusRecaudoEfect.isEmpty()) {
                if (mapStratusRecaudoEfect.containsKey(informeDali40LSBean.getCodigoCajero())) {
                    informeDali40LSBean.setRecaudosEfectivoStratus(mapStratusRecaudoEfect.get(informeDali40LSBean.getCodigoCajero()));
                }
            }
            if (!mapStratusDepCheque.isEmpty()) {
                if (mapStratusDepCheque.containsKey(informeDali40LSBean.getCodigoCajero())) {
                    informeDali40LSBean.setDepositoChequeStratus(mapStratusDepCheque.get(informeDali40LSBean.getCodigoCajero()));
                }
            }
            if (!mapStratusPagoChequeFM.isEmpty()) {
                if (mapStratusPagoChequeFM.containsKey(informeDali40LSBean.getCodigoCajero())) {
                    informeDali40LSBean.setPagosFMChequeStratus(mapStratusPagoChequeFM.get(informeDali40LSBean.getCodigoCajero()));
                }
            }
            if (!mapStratusPagoChequeTC.isEmpty()) {
                if (mapStratusPagoChequeTC.containsKey(informeDali40LSBean.getCodigoCajero())) {
                    informeDali40LSBean.setPagosTCChequeStratus(mapStratusPagoChequeTC.get(informeDali40LSBean.getCodigoCajero()));
                }
            }
            if (!mapStratusRecaudoCheque.isEmpty()) {
                if (mapStratusRecaudoCheque.containsKey(informeDali40LSBean.getCodigoCajero())) {
                    informeDali40LSBean.setRecaudosChequeStratus(mapStratusRecaudoCheque.get(informeDali40LSBean.getCodigoCajero()));
                }
            }
            if (!mapFaltantesSobrantes.isEmpty()) {
                if (mapFaltantesSobrantes.containsKey(informeDali40LSBean.getCodigoCajero())) {
                    if (mapFaltantesSobrantes.get(informeDali40LSBean.getCodigoCajero()) > new Long(0)) {
                        informeDali40LSBean.setSobrantesEfectivo(mapFaltantesSobrantes.get(informeDali40LSBean.getCodigoCajero()));
                    } else {
                        informeDali40LSBean.setFaltantesEfectivo((mapFaltantesSobrantes.get(informeDali40LSBean.getCodigoCajero())) * -1);

                    }
                }
            }
        }
        return informes;
    }

    public String generarArchivoXLSDali40LS(Collection<InformeDali40LSBean> regs, Calendar fechaInicial) {

        String respuesta = "";

        String[] tituloDali40 = TituloDali40LSGeneral.tituloDali40;
        String[] tituloColumnas = TituloDali40LSGeneral.tituloColumnas;
        String[] tituloFilas = TituloDali40LSGeneral.tituloFilas;
        String nombreArchivo = "EstadísticaDali40_" + com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
        Collection<Registro> lineasDali40 = new ArrayList<>();

        if (regs != null) {

            try {

                for (InformeDali40LSBean item : regs) {

                    Registro regDali40 = new Registro();

                    Celda celdao = new Celda((short) 1, item.getCodigoCajero(), TipoDatoCelda.NUMERICO);
                    regDali40.addCelda(celdao);

                    Celda celda1 = new Celda((short) 2, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getDepositoEfectivoATM()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda1);

                    Celda celda2 = new Celda((short) 3, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getDepositoEfectivoStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda2);

                    Celda celda3 = new Celda((short) 4, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getDepositoEfectivoATM() - item.getDepositoEfectivoStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda3);

                    Celda celda4 = new Celda((short) 2, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getDepositoChequeATM()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda4);

                    Celda celda5 = new Celda((short) 3, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getDepositoChequeStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda5);
                    Celda celda6 = new Celda((short) 4, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getDepositoChequeATM() - item.getDepositoChequeStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda6);

                    Celda celda7 = new Celda((short) 2, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosTCEfectivoATM()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda7);

                    Celda celda8 = new Celda((short) 3, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosTCEfectivoStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda8);

                    Celda celda9 = new Celda((short) 4, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosTCEfectivoATM() - item.getPagosTCEfectivoStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda9);

                    Celda celda10 = new Celda((short) 2, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosTCChequeATM()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda10);

                    Celda celda11 = new Celda((short) 3, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosTCChequeStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda11);
                    Celda celda12 = new Celda((short) 4, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosTCChequeATM() - item.getPagosTCChequeStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda12);

                    Celda celda13 = new Celda((short) 2, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosFMEfectivoATM()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda13);

                    Celda celda14 = new Celda((short) 3, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosFMEfectivoStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda14);

                    Celda celda15 = new Celda((short) 4, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosFMEfectivoATM() - item.getPagosFMEfectivoStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda15);

                    Celda celda16 = new Celda((short) 2, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosFMChequeATM()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda16);
                    Celda celda17 = new Celda((short) 3, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosFMChequeStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda17);

                    Celda celda18 = new Celda((short) 4, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getPagosFMChequeATM() - item.getPagosFMChequeStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda18);

                    Celda celda19 = new Celda((short) 2, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getRecaudosEfectivoATM()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda19);

                    Celda celda20 = new Celda((short) 3, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getRecaudosEfectivoStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda20);

                    Celda celda21 = new Celda((short) 4, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getRecaudosEfectivoATM() - item.getRecaudosEfectivoStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda21);

                    Celda celda22 = new Celda((short) 2, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getRecaudosChequeATM()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda22);

                    Celda celda23 = new Celda((short) 3, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getRecaudosChequeStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda23);

                    Celda celda24 = new Celda((short) 4, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getRecaudosChequeATM() - item.getRecaudosChequeStratus()), TipoDatoCelda.MONEDA);
                    regDali40.addCelda(celda24);

                    Celda celda25 = new Celda((short) 1, "" + item.getDetEfectivoIniJor().getDen1000Cantidad().toString() + "/" + item.getDetEfectivoIniJor().getDen1000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda25);
                    Celda celda26 = new Celda((short) 2, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda26);
                    Celda celda27 = new Celda((short) 3, "" + item.getDetEfectivoRecibido().getDen1000Cantidad().toString() + "/" + item.getDetEfectivoRecibido().getDen1000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda27);
                    Celda celda28 = new Celda((short) 4, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda28);
                    Celda celda29 = new Celda((short) 1, "" + item.getDetEfectivoIniJor().getDen2000Cantidad().toString() + "/" + item.getDetEfectivoIniJor().getDen2000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda29);
                    Celda celda30 = new Celda((short) 2, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda30);
                    Celda celda31 = new Celda((short) 3, "" + item.getDetEfectivoRecibido().getDen2000Cantidad().toString() + "/" + item.getDetEfectivoRecibido().getDen2000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda31);
                    Celda celda32 = new Celda((short) 4, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda32);
                    Celda celda33 = new Celda((short) 1, "" + item.getDetEfectivoIniJor().getDen5000Cantidad().toString() + "/" + item.getDetEfectivoIniJor().getDen5000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda33);
                    Celda celda34 = new Celda((short) 2, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda34);
                    Celda celda35 = new Celda((short) 3, "" + item.getDetEfectivoRecibido().getDen5000Cantidad().toString() + "/" + item.getDetEfectivoRecibido().getDen5000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda35);
                    Celda celda36 = new Celda((short) 4, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda36);
                    Celda celda37 = new Celda((short) 1, "" + item.getDetEfectivoIniJor().getDen10000Cantidad().toString() + "/" + item.getDetEfectivoIniJor().getDen10000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda37);
                    Celda celda38 = new Celda((short) 2, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda38);
                    Celda celda39 = new Celda((short) 3, "" + item.getDetEfectivoRecibido().getDen10000Cantidad().toString() + "/" + item.getDetEfectivoRecibido().getDen10000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda39);
                    Celda celda40 = new Celda((short) 4, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda40);
                    Celda celda41 = new Celda((short) 1, "" + item.getDetEfectivoIniJor().getDen20000Cantidad().toString() + "/" + item.getDetEfectivoIniJor().getDen20000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda41);
                    Celda celda42 = new Celda((short) 2, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda42);
                    Celda celda43 = new Celda((short) 3, "" + item.getDetEfectivoRecibido().getDen20000Cantidad().toString() + "/" + item.getDetEfectivoRecibido().getDen20000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda43);
                    Celda celda44 = new Celda((short) 4, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda44);
                    Celda celda45 = new Celda((short) 1, "" + item.getDetEfectivoIniJor().getDen50000Cantidad().toString() + "/" + item.getDetEfectivoIniJor().getDen50000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda45);
                    Celda celda46 = new Celda((short) 2, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda46);
                    Celda celda47 = new Celda((short) 3, "" + item.getDetEfectivoRecibido().getDen50000Cantidad().toString() + "/" + item.getDetEfectivoRecibido().getDen50000Valor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda47);
                    Celda celda48 = new Celda((short) 4, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda48);

                    Celda celda49 = new Celda((short) 1, "" + item.getDetEfectivoIniJor().getTotalesEfectivoCantidad().toString() + "/" + item.getDetEfectivoIniJor().getTotalesEfectivoValor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda49);
                    Celda celda50 = new Celda((short) 2, "" + 0 + "/" + 0, TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda50);

                    Celda celda51 = new Celda((short) 3, "" + item.getDetEfectivoRecibido().getTotalesEfectivoCantidad().toString() + "/" + item.getDetEfectivoRecibido().getTotalesEfectivoValor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda51);
                    Celda celda52 = new Celda((short) 4, "" + item.getTotalRetractEfectivoCant().toString() + "/" + item.getTotalRetractEfectivoValor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda52);

                    Celda celda53 = new Celda((short) 1, item.getDetEfectivoIniJor().getTotalChequeCantidad().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda53);
                    Celda celda54 = new Celda((short) 2, 0, TipoDatoCelda.NUMERICO);
                    regDali40.addCelda(celda54);

                    Celda celda55 = new Celda((short) 3, item.getDetEfectivoRecibido().getTotalChequeCantidad().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda55);

                    Celda celda56 = new Celda((short) 4, item.getTotalRetractChequeCant().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda56);

                    Celda celda57 = new Celda((short) 1, item.getDetEfectivoIniJor().getTotalChequeValor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda57);
                    Celda celda58 = new Celda((short) 2, 0, TipoDatoCelda.NUMERICO);
                    regDali40.addCelda(celda58);

                    Celda celda59 = new Celda((short) 3, item.getDetEfectivoRecibido().getTotalChequeValor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda59);

                    Celda celda60 = new Celda((short) 4, item.getTotalRetractChequeValor().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda60);

                    Celda celda61 = new Celda((short) 1, item.getFaltantesEfectivo().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda61);

                    Celda celda62 = new Celda((short) 2, 0, TipoDatoCelda.NUMERICO);
                    regDali40.addCelda(celda62);

                    Celda celda63 = new Celda((short) 1, item.getSobrantesEfectivo().toString(), TipoDatoCelda.NORMAL);
                    regDali40.addCelda(celda63);
                    Celda celda64 = new Celda((short) 2, 0, TipoDatoCelda.NUMERICO);
                    regDali40.addCelda(celda64);

                    lineasDali40.add(regDali40);

                }

                ArchivoXLSDali40 archivo = ProcesadorArchivoXLS.crearArchivoXLSDali40(nombreArchivo, "", "EstadísticaDali40", tituloDali40, tituloColumnas, tituloFilas, lineasDali40);

                objectContext.enviarArchivoXLS(archivo);
            } catch (IllegalArgumentException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), Constantes.MSJ_ERROR_CREAR_ARCHIVO);
                respuesta = this.respuestaJSon;
            } catch (IOException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_DESCARGAR_ARCHIVO);
                respuesta = this.respuestaJSon;
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                respuesta = this.respuestaJSon;
            }

        } else {
            respuesta = this.respuestaJSon;
        }
        return respuesta;

    }

    public String generarReporteResumenDALI40() {
        String respuesta = "";

        Collection<Movimientocuadremulti> itemsFaltanteSobrantes = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strExepcion = "";

        try {

            try {
                Date fechaI = formatter.parse(this.fecha);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }

            Calendar fechaInicial = null;
            try {
                Date fechaI = formatter.parse(this.fecha);
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);
            } catch (Exception ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();
            }

            try {

                itemsFaltanteSobrantes = cuadreCifrasMultiSession.getUsuarioFecha("Dali40Ajuste", fechaInicial.getTime());
                if (itemsFaltanteSobrantes != null && !itemsFaltanteSobrantes.isEmpty()) {
                    respuesta = generarArchivoXLSReporteResumenDALI40(itemsFaltanteSobrantes, fechaInicial);
                    abrirModal("SARA", respuesta, null);
                } else {
                    //respuesta = "No se encontraron registros para la consulta";
                    respuesta = Constantes.MSJ_QUERY_SIN_DATA;
                    // abrirModal("SARA", respuesta, null);
                }

            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
//                if (ex.getMessage() == null) {
//                    strExepcion = ex.getCause().getMessage();
//                } else {
//                    strExepcion = ex.getMessage();
//                }
//                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, strExepcion);
//                objectContext.setError(strExepcion, CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
//                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                abrirModal("SARA", respuesta, null);
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                abrirModal("SARA", respuesta, null);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
            abrirModal("SARA", respuesta, null);
        }

        return respuesta;
    }

    public String generarArchivoXLSReporteResumenDALI40(Collection<Movimientocuadremulti> regs, Calendar fechaInicial) {
        String respuesta = "";

        if (regs != null) {

            String[] titulosHoja = TituloResumenDali.tituloHoja;
            String[] titulosColumna = TituloResumenDali.tituloColumnas;
            titulosHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);

            Collection<Registro> lineas = new ArrayList<>();
            Short numColumna;

            try {
                for (Movimientocuadremulti item : regs) {

                    Registro reg = new Registro();
                    numColumna = 0;
                    reg.addCelda(new Celda(numColumna++, item.getCajero().getCodigoCajero(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getCajero().getOficinaMulti().getCodigooficinamulti(), TipoDatoCelda.NUMERICO));
                    if (TipoMovimientoCuadreMultifuncional.Sobrante.codigo == item.getCodtipomovimientocuadremuti()) {
                        reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValormovimiento()), TipoDatoCelda.MONEDA));
                        reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(0), TipoDatoCelda.MONEDA));
                    } else {
                        reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(0), TipoDatoCelda.MONEDA));
                        reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValormovimiento()), TipoDatoCelda.MONEDA));

                    }
                    if (item.getValormovimiento() != 0) {
                        lineas.add(reg);
                    }

                }
                ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("ResumenDali", titulosHoja, titulosColumna, lineas);
                objectContext.enviarArchivoXLS(archivo);
            } catch (IllegalArgumentException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), Constantes.MSJ_ERROR_CREAR_ARCHIVO);
                respuesta = this.respuestaJSon;
            } catch (IOException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_DESCARGAR_ARCHIVO);
                respuesta = this.respuestaJSon;
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                respuesta = this.respuestaJSon;
            }

        }
        return respuesta;
    }

    public String generarReporteInformeGeneral() {
        String respuesta = "";

        Collection<Billetajemulti> items = null;
        Collection<Totalesestacionmultifuncional> itemsTotalesestacion = null;
        Collection<InformeDiferenciasMulti> informeDiferencias = null;
        String strExepcion = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {

            try {
                Date fechaI = formatter.parse(this.fecha);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }

            Calendar fechaInicial = null;

            try {
                Date fechaI = formatter.parse(this.fecha);
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);
            } catch (Exception ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();
            }

            try {

                items = sessionBMSL.getBilletajeMultiRangoFecha(fechaInicial, fechaInicial);
                itemsTotalesestacion = totalesEstacionMultiSession.getTotalesEstacionMultiRangoFecha(fechaInicial, fechaInicial);
                informeDiferencias = getDiferenciasMulti(items, itemsTotalesestacion);
                if (informeDiferencias != null && !informeDiferencias.isEmpty()) {
                    respuesta = generarArchivoXLSReporteInformeGeneral(informeDiferencias, fechaInicial);
                } else {
                    //respuesta = "No se encontraron registros para la consulta";
                    respuesta = Constantes.MSJ_QUERY_SIN_DATA;
                }
                abrirModal("SARA", respuesta, null);
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
//                if (ex.getMessage() == null) {
//                    strExepcion = ex.getCause().getMessage();
//                } else {
//                    strExepcion = ex.getMessage();
//                }
//                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, strExepcion);
//                objectContext.setError(strExepcion, CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
//                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                abrirModal("SARA", respuesta, null);
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                abrirModal("SARA", respuesta, null);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
            abrirModal("SARA", respuesta, null);
        }

        return respuesta;
    }

    public Collection<InformeDiferenciasMulti> getDiferenciasMulti(Collection<Billetajemulti> regs, Collection<Totalesestacionmultifuncional> regsTotalesestacion) throws EntityServicioExcepcion {

        Collection<InformeDiferenciasMulti> informesDiferencias = new ArrayList<>();

        Map<Integer, Long> mapTotalesEstacion = new HashMap<>();
        Map<Integer, Long> mapBilletajeCajero = new HashMap<>();
        Collection<Cajero> cajeros = null;
        try {
            cajeros = cajeroSession.getTodosActivosMulti(0, 5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Long recTotalesEstacion = (long) 0;
        Long recTotalesAtm = (long) 0;

        Integer codCajero = 0;

        if (regsTotalesestacion != null) {

            for (Totalesestacionmultifuncional totalesestacionmulti : regsTotalesestacion) {
                if (codCajero.compareTo(0) == 0) {
                    codCajero = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero();
                }
                if (codCajero.compareTo(totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero()) != 0) {

                    mapTotalesEstacion.put(codCajero, recTotalesEstacion / 100);
                    recTotalesEstacion = new Long(0);
                    codCajero = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero();
                }
                if (totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(475)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(476)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(361)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(95)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(477)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(481)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(483)
                        || totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal().equals(485)) {
                    recTotalesEstacion = recTotalesEstacion + totalesestacionmulti.getValorevento();
                }
            }

            mapTotalesEstacion.put(codCajero, recTotalesEstacion / 100);

        }

        if (regs != null) {

            for (Billetajemulti billetajemulti : regs) {

                recTotalesAtm = billetajemulti.getValordepefectivoctaaho() + billetajemulti.getValordepefectivoctacte()
                        + billetajemulti.getValorpagoefectivofm() + billetajemulti.getValorpagoefectivotc();
                mapBilletajeCajero.put(billetajemulti.getBilletajemultiPK().getCodigocajero(), recTotalesAtm);

            }

        }

        if (cajeros != null) {
            for (Cajero regCajero : cajeros) {
                InformeDiferenciasMulti informeDiferenciasMulti = new InformeDiferenciasMulti();
                informeDiferenciasMulti.setCodigoCajero(regCajero.getCodigoCajero());

                if (!mapTotalesEstacion.isEmpty()) {

                    if (mapTotalesEstacion.containsKey(regCajero.getCodigoCajero())) {
                        informeDiferenciasMulti.setRecibidoEfectivoLinea(mapTotalesEstacion.get(regCajero.getCodigoCajero()));

                    }
                }
                if (!mapBilletajeCajero.isEmpty()) {

                    if (mapBilletajeCajero.containsKey(regCajero.getCodigoCajero())) {
                        informeDiferenciasMulti.setRecibidoEfectivoAtm(mapBilletajeCajero.get(regCajero.getCodigoCajero()));

                    }
                }
                informesDiferencias.add(informeDiferenciasMulti);

            }

        }

        return informesDiferencias;

    }

    public String generarArchivoXLSReporteInformeGeneral(Collection<InformeDiferenciasMulti> regs, Calendar fechaInicial) {
        String respuesta = "";

        if (regs != null) {

            //Creo la hoja de cálculo
            String[] titulosHoja = TituloInformeDiferencias.tituloHoja;
            String[] titulosColumna = TituloInformeDiferencias.tituloColumnas;
            titulosHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);

            Collection<Registro> lineas = new ArrayList<>();
            Short numColumna;

            try {
                for (InformeDiferenciasMulti item : regs) {

                    Registro reg = new Registro();
                    numColumna = 0;

                    reg.addCelda(new Celda(numColumna++, item.getCodigoCajero(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getRecibidoEfectivoLinea()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getRecibidoEfectivoAtm()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getRecibidoEfectivoLinea() - item.getRecibidoEfectivoAtm()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, item.getObservaciones(), TipoDatoCelda.NORMAL));

                    if ((item.getRecibidoEfectivoLinea() - item.getRecibidoEfectivoAtm()) != 0) {
                        lineas.add(reg);
                    }

                }
                ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("InformeDiferencias", titulosHoja, titulosColumna, lineas);
                objectContext.enviarArchivoXLS(archivo);
            } catch (IllegalArgumentException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), Constantes.MSJ_ERROR_CREAR_ARCHIVO);
                respuesta = this.respuestaJSon;
            } catch (IOException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_DESCARGAR_ARCHIVO);
                respuesta = this.respuestaJSon;
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                respuesta = this.respuestaJSon;
            }

        }
        return respuesta;
    }

    public String generarReporteInformeGeneralAyer() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Totalesestacionmultifuncional> items = null;
        Calendar fechaInicial = null;
        Calendar fechaFinal = null;
        Collection<InformeAyerMulti> informeAyerMultiLocal = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {

            try {
                Date fechaI = formatter.parse(this.fecha);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }

            try {
                Date fechaI = formatter.parse(this.fecha);
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);
            } catch (Exception ex) {

                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();

            }
            try {

                fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);

            } catch (Exception ex) {
                fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);

            }

            try {
                items = totalesEstacionMultiSession.getTotalesEstacionMultiRangoFecha(fechaInicial, fechaFinal);
                informeAyerMultiLocal = getCollectionInformeAyer(items);

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
                respuesta = this.respuestaJSon;
                abrirModal("SARA", respuesta, null);
            } catch (Exception ex) {
                 objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                respuesta = this.respuestaJSon;
                abrirModal("SARA", respuesta, null);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
            respuesta = this.respuestaJSon;
            abrirModal("SARA", respuesta, null);
        }
        if (items == null || items.isEmpty()) {
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
            respuesta = this.respuestaJSon;
            abrirModal("SARA", respuesta, null);
        }

        if (items != null && this.respuestaJSon.length() <= 1) {
            respuesta = generarArchivoXLS(informeAyerMultiLocal, fechaInicial);  // paso los items a JSON
            abrirModal("SARA", respuesta, null);
        } else {
            respuesta = this.respuestaJSon;
            abrirModal("SARA", respuesta, null);
        }

        return respuesta;
    }

    public Collection<InformeAyerMulti> getCollectionInformeAyer(Collection<Totalesestacionmultifuncional> regsTotalesestacion) {

        Collection<InformeAyerMulti> informes = new ArrayList<>();

        Long valorTotal = (long) 0;
        Integer codigoTotal = 0;
        Integer codCajero = 0;

        if (regsTotalesestacion != null) {

            for (Totalesestacionmultifuncional totalesestacionmulti : regsTotalesestacion) {
                if (codCajero.compareTo(0) == 0) {
                    codCajero = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero();
                    iniciarInformeAyerMulti();

                }

                if (codCajero.compareTo(totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero()) != 0) {
                    informeAyerMulti.setCodigoCajero(codCajero);
                    codCajero = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigocajero();
                    informes.add(informeAyerMulti);
                    iniciarInformeAyerMulti();

                }
                codigoTotal = totalesestacionmulti.getTotalesestacionmultifuncionalPK().getCodigototal();
                valorTotal = totalesestacionmulti.getValorevento();
                setInformeAyerMulti(codigoTotal, valorTotal);

            }

            informeAyerMulti.setCodigoCajero(codCajero);
            informes.add(informeAyerMulti);

        }
        return informes;

    }

    private String generarArchivoXLS(Collection<InformeAyerMulti> items, Calendar fechaInicial) {
        String respuesta = "";
        Cajero cajero;

        if (items != null) {

            String[] titulosHoja = TituloInformeAyerMulti.tituloHoja;
            String[] titulosColumna = TituloInformeAyerMulti.tituloColumnas;
            titulosHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);

            Collection<Registro> lineas = new ArrayList<Registro>();
            Short numColumna;

            try {
                for (InformeAyerMulti item : items) {

                    cajero = cajeroSession.buscar(item.getCodigoCajero());
                    Registro reg = new Registro();
                    numColumna = 0;
                    reg.addCelda(new Celda(numColumna++, item.getCodigoCajero(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, cajero.getOficinaMulti().getCodigooficinamulti(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getProvCaja()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getDisminProv()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getEfectRecibido()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getEfectPagado()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getEfectCaja()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getEfectRecHorAnt()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getEfectRecHorSig()), TipoDatoCelda.MONEDA));

                    lineas.add(reg);
                }

                ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("InformeAyerMulti", titulosHoja, titulosColumna, lineas);
                objectContext.enviarArchivoXLS(archivo);
            } catch (IllegalArgumentException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), Constantes.MSJ_ERROR_CREAR_ARCHIVO);
                respuesta = this.respuestaJSon;
            } catch (IOException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_DESCARGAR_ARCHIVO);
                respuesta = this.respuestaJSon;
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
            }

        } else {
            respuesta = this.respuestaJSon;
        }
        return respuesta;
    }

    private void iniciarInformeAyerMulti() {
        informeAyerMulti = null;
        informeAyerMulti = new InformeAyerMulti();
    }

    private void setInformeAyerMulti(Integer codTotal, Long valor) {

        switch (codTotal) {
            case 3:
                informeAyerMulti.setProvCaja(valor / 100);
                break;
            case 38:
                informeAyerMulti.setDisminProv(valor / 100);
                break;
            case 1:
                informeAyerMulti.setEfectRecibido(valor / 100);
                break;
            case 36:
                informeAyerMulti.setEfectPagado(valor / 100);
                break;
            case 35:
                informeAyerMulti.setEfectCaja(valor / 100);
                break;
            case 62:
                informeAyerMulti.setEfectCaja(informeAyerMulti.getEfectCaja() - (valor / 100));
                break;
            case 180:
                informeAyerMulti.setEfectRecHorSig(valor / 100);
                break;
            case 181:
                informeAyerMulti.setEfectRecHorAnt(valor / 100);
                break;

            default:

                break;

        }
    }

    private Calendar getFechaTransformadaCalendar(String fecha) throws IllegalArgumentException {
        String fechaStr = fecha;
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (Exception ex) {
            throw ex;
        }
        return calendar;
    }
}
