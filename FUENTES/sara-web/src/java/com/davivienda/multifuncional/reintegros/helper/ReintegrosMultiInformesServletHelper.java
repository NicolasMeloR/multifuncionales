/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.multifuncional.reintegros.helper;

import com.davivienda.multifuncional.constantes.EstadosDiarioMultifuncional;
import com.davivienda.multifuncional.constantes.TipoCuentaMultifuncional;
import com.davivienda.multifuncional.diarioelectronico.formato.TituloDiarioMultifuncionalGeneral;
import com.davivienda.multifuncional.reintegros.formato.TituloNotasDebitoMultiGeneral;
import com.davivienda.multifuncional.reintegros.formato.TituloReintegroMultiOtroInforme;
import com.davivienda.multifuncional.reintegros.formato.TituloReintegrosMultiGeneral;
import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalGeneralObjectContext;
import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalHelperInterface;
import com.davivienda.multifuncional.tablas.notasdebitomulti.session.NotasDebitoMultiSessionLocal;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session.ReintegrosMultiEfectivoSessionLocal;
import com.davivienda.multifuncional.tablas.reintegrosmultifuncional.session.ReintegrosMultifuncionalSessionLocal;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.constantes.TipoCuentaReintegro;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.entitys.ReintegrosMultifuncional;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.JSon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;

/**
 *
 * @author P-CCHAPA
 */
public class ReintegrosMultiInformesServletHelper implements ReintegrosMultifuncionalHelperInterface {

    private ReintegrosMultiEfectivoSessionLocal session = null;
    private ReintegrosMultifuncionalSessionLocal sessionMultifuncional = null;
    private ReintegrosMultifuncionalGeneralObjectContext objectContext = null;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    private NotasDebitoMultiSessionLocal notasDebitoMultiSessionLocal;
    private String respuestaJSon;
    private String strDirLogoDavivienda;
    private long longValorComision;

    public ReintegrosMultiInformesServletHelper(ReintegrosMultiEfectivoSessionLocal session, ReintegrosMultifuncionalGeneralObjectContext objectContext,
            ConfModulosAplicacionLocal confModulosAplicacionSession, NotasDebitoMultiSessionLocal notasDebitoSession,
            ReintegrosMultifuncionalSessionLocal sessionMultifuncionalLocal) {
        this.session = session;
        this.objectContext = objectContext;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
        this.notasDebitoMultiSessionLocal = notasDebitoSession;
        this.sessionMultifuncional = sessionMultifuncionalLocal;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Reintegrosmultiefectivo> items = null;
        Collection<Notasdebitomultifuncional> itemsNd = null;
        Collection<ReintegrosMultifuncional> itemsRm = null;
        Date fechaInicial = null;
        try {
            Date fechaFinal = null;
            Integer codigoCajero = 0;
            try {
                fechaInicial = objectContext.getAtributoFechaInicial().getTime();
            } catch (IllegalArgumentException ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
            }
            try {
                fechaFinal = objectContext.getAtributoFechaHoraFinal().getTime();
            } catch (IllegalArgumentException ex) {
                fechaFinal = fechaInicial;
                if (com.davivienda.utilidades.conversion.Cadena.aInteger(objectContext.getAtributoString("tipoReporte")).equals(TipoCuentaMultifuncional.PorRevisar.codigo)) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(fechaFinal);
                    c.set(Calendar.HOUR_OF_DAY, 23);
                    c.set(Calendar.MINUTE, 59);
                    c.set(Calendar.SECOND, 59);
                    fechaFinal = c.getTime();
                }
            }

            try {
                codigoCajero = objectContext.getCodigoCajero();
            } catch (IllegalArgumentException ex) {
                codigoCajero = 0;
            }
            if (codigoCajero == null) {
                codigoCajero = 0;
            }
            try {
                strDirLogoDavivienda = getParametro("SARA.DIR_LOGO_DAVIVIENDA", "/opt1/appsvr/appsvr/sara/tiras/Davivienda61.JPG");
                longValorComision = com.davivienda.utilidades.conversion.Cadena.aLong(getParametro("SARA.VALOR_COMISION_BANAGRARIO", "0"));

                  objectContext.getConfigApp().loggerApp.info("ReintegrosMultiInformesServletHelper fechaInicial: " +fechaInicial);
                   objectContext.getConfigApp().loggerApp.info("ReintegrosMultiInformesServletHelper fechaFinal: " +fechaFinal);
                   objectContext.getConfigApp().loggerApp.info("ReintegrosMultiInformesServletHelper codigoCajero: " +codigoCajero);
                // Consulta los registros según los parámetros tomados del request
                Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
                if (com.davivienda.utilidades.conversion.Cadena.aInteger(objectContext.getAtributoString("tipoReporte")).equals(TipoCuentaMultifuncional.NotasDebito.codigo)) {
                    itemsNd = notasDebitoMultiSessionLocal.getNotasDebito(fechaInicial, fechaFinal, codigoCajero, fechaHisto);
                }
                if (com.davivienda.utilidades.conversion.Cadena.aInteger(objectContext.getAtributoString("tipoReporte")).equals(TipoCuentaMultifuncional.PorRevisar.codigo)) {
                    itemsRm = sessionMultifuncional.getReintegrosMultifuncional(fechaInicial, fechaFinal, codigoCajero);
                } else {                    
                    items = session.getReintegros(fechaInicial, fechaFinal, codigoCajero, fechaHisto);
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
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, "IllegalArgumentException: " + ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
        }

        if (items != null && this.respuestaJSon.length() <= 1) {
            clasificarReporteSeleccion(items, fechaInicial);
        } else {
            if (itemsNd != null && this.respuestaJSon.length() <= 1) {
                informeNotasDebitoXLS(itemsNd);
            }else  if (itemsRm != null && this.respuestaJSon.length() <= 1) {
                objectContext.getConfigApp().loggerApp.info(" tamano itemsRm: " +itemsRm.size());
                informeReintegrosMultifuncionalXLS(itemsRm);
            } else {
                respuesta = this.respuestaJSon;
            }
        }

        return respuesta;
    }

    private void clasificarReporteSeleccion(Collection<Reintegrosmultiefectivo> items, Date fechaInicial) {

        if (objectContext.getAtributoString("tipoReporte").length() > 0) {

            Integer tipoCuentaMulti = com.davivienda.utilidades.conversion.Cadena.aInteger(objectContext.getAtributoString("tipoReporte"));
            TipoCuentaMultifuncional tipoReporteMulti = TipoCuentaMultifuncional.Todas;
            tipoReporteMulti = TipoCuentaMultifuncional.getTipoCuentaMultifuncional(tipoCuentaMulti);
            String nombreArchivo = "";

            switch (tipoReporteMulti) {
                case Todas:
                    informeGeneralXLS(items, fechaInicial);
                    break;
                case CreditoFM:
                    nombreArchivo = "CREDITOSFM_" + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), "MMdd");
                    OtroInformeXLS(items, nombreArchivo, tipoReporteMulti, true);
                    break;
                case TarjetaCredito:
                    nombreArchivo = "TARJETADECREDITO_" + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), "MMdd");
                    OtroInformeXLS(items, nombreArchivo, tipoReporteMulti, true);
                    break;
                case DaviviendaTodas:
                    //PENDIENTE FILTRAR
                    informeGeneralXLS(items, fechaInicial);
                    break;
            }
        } else {
            informeGeneralXLS(items, fechaInicial);
        }
    }

    private String OtroInformeXLS(Collection<Reintegrosmultiefectivo> items, String nombreArchivo, TipoCuentaMultifuncional tipoReporteMulti, Boolean observaciones) {
        String respuesta = "";
        String[] titulosHoja = TituloReintegroMultiOtroInforme.tituloHoja;
        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;
        String estadoObservacion = "";
        String[] titulosColumna;

        if (observaciones) {
            titulosColumna = TituloReintegroMultiOtroInforme.tituloColumnas;
            estadoObservacion = "";
        } else if (tipoReporteMulti.codigo.equals(TipoCuentaReintegro.OtraRed.codigo)) {
            titulosColumna = TituloReintegroMultiOtroInforme.tituloColumnasORed;
        } else {
            titulosColumna = TituloReintegroMultiOtroInforme.tituloColumnas;
            estadoObservacion = "TRANSACCION CON PROBLEMAS";
        }

        try {
            for (Reintegrosmultiefectivo item : items) {
                Registro reg = new Registro();
                numColumna = 0;
                //{"FECHA MOV",  "HORA","TARJETA", "ATM","TALON","VALOR A ABONAR","ESTADO DE LA TRANSACCION","RED"};
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosmultiefectivoPK().getHFechasistema(), FormatoFecha.DEFECTO), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosmultiefectivoPK().getHFechasistema(), FormatoFecha.HOUR_REINTEGROS), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getHTipotarjeta(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getReintegrosmultiefectivoPK().getHCodigocajero(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getReintegrosmultiefectivoPK().getHTalon(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getHValor()), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, estadoObservacion, TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, "SOBRANTE DALI 20 " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), "MMdd"), TipoDatoCelda.NORMAL));
                if (tipoReporteMulti.codigo.equals(TipoCuentaReintegro.OtraRed.codigo)) {
                    reg.addCelda(new Celda(numColumna++, item.getHNumerocuenta(), TipoDatoCelda.NORMAL));
                }
                if (item.getTipocuentareintegro().equals(tipoReporteMulti.codigo) && (item.getEstadoreintegro().equals(EstadoReintegro.INICIADO.codigo))) {
                    lineas.add(reg);
                }
            }

            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibroReintegrosDavivienda(strDirLogoDavivienda, nombreArchivo, titulosHoja, titulosColumna, lineas);
            objectContext.enviarArchivoXLS(archivo);
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
            respuesta = this.respuestaJSon;
        } catch (IOException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
            respuesta = this.respuestaJSon;
        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
        }
        return respuesta;
    }

    public String informeNotasDebitoXLS(Collection<Notasdebitomultifuncional> items) {

        String respuesta = "";
        String[] titulosHoja = TituloNotasDebitoMultiGeneral.tituloHoja;
        String[] titulosColumna = TituloNotasDebitoMultiGeneral.tituloColumnas;

        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;
        try {
            for (Notasdebitomultifuncional item : items) {
                Registro reg = new Registro();
                numColumna = 0;
                //tituloColumnas = {"CAJERO",  "OCCA","CUENTA","FECHA","VALOR","VALOR AJUSTAR","USUARIOCREANOTA","USUARIOAUTORIZA","FECHAAPLICA","ERROR","ESTADO","CLASIFICACION"};
                reg.addCelda(new Celda(numColumna++, item.getNotasdebitomultifuncionalPK().getCodigocajero(), TipoDatoCelda.NUMERICO));

                reg.addCelda(new Celda(numColumna++, item.getNumerocuenta(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getNotasdebitomultifuncionalPK().getFecha(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValor()), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValorajustado()), TipoDatoCelda.MONEDA));

                if (item.getUsuariocrea() != null) {
                    reg.addCelda(new Celda(numColumna++, item.getUsuariocrea(), TipoDatoCelda.NORMAL));
                } else {
                    reg.addCelda(new Celda(numColumna++, "", TipoDatoCelda.NORMAL));
                }

                if (item.getUsuarioautoriza() != null) {
                    reg.addCelda(new Celda(numColumna++, item.getUsuarioautoriza(), TipoDatoCelda.NORMAL));
                } else {
                    reg.addCelda(new Celda(numColumna++, "", TipoDatoCelda.NORMAL));
                }

                if (item.getFechaaplica() != null) {
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFechaaplica(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                } else {
                    reg.addCelda(new Celda(numColumna++, "", TipoDatoCelda.NORMAL));
                }
                // reg.addCelda(new Celda(numColumna++, TipoCuentaReintegro.getTipoCuentaReintegro(item.getTipoCuentaReintegro()).nombre, TipoDatoCelda.NORMAL));
                if (item.getError() != null) {
                    reg.addCelda(new Celda(numColumna++, item.getError(), TipoDatoCelda.NORMAL));
                } else {
                    reg.addCelda(new Celda(numColumna++, "", TipoDatoCelda.NORMAL));
                }
                reg.addCelda(new Celda(numColumna++, EstadoReintegro.getEstadoReintegro(item.getEstado()).nombre, TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, TipoCuentaReintegro.getTipoCuentaReintegro(item.getTipocuenta()).nombre, TipoDatoCelda.NORMAL));

                lineas.add(reg);
            }

            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("NotasDebitoMultifuncional", titulosHoja, titulosColumna, lineas);
            objectContext.enviarArchivoXLS(archivo);
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
            respuesta = this.respuestaJSon;
        } catch (IOException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
            respuesta = this.respuestaJSon;
        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
        }
        return respuesta;
    }

    public String informeGeneralXLS(Collection<Reintegrosmultiefectivo> items, Date fechaInicial) {

        String respuesta = "";
        String[] titulosHoja = TituloReintegrosMultiGeneral.tituloHoja;
        //titulosHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);

        String[] titulosColumna = TituloReintegrosMultiGeneral.tituloColumnas;

        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;
        try {
            for (Reintegrosmultiefectivo item : items) {
                Registro reg = new Registro();
                numColumna = 0;
                //public static String[] tituloColumnas = {"CAJERO",  "TRANSACCION", "OCCA","CUENTA","TARJETA","FECHA","VALOR","VALOR AJUSTAR","VALOR AJUSTADO,"STATUS TX","VALOR AJUSTADO","STATUS TX","RED ENRUTO","USUARIOREVISA","USUARIOAUTORIZA","FECHAREINTEGRO","ERROR","ESTADO","CLASIFICACION"};
                reg.addCelda(new Celda(numColumna++, item.getReintegrosmultiefectivoPK().getHCodigocajero(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getReintegrosmultiefectivoPK().getHTalon(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getTFechacajero(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getHNumerocuenta(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, TipoCuentaMultifuncional.getTipoCuentaMultifuncional(item.getTTipocuenta()).toString(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosmultiefectivoPK().getHFechasistema(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getHValor()), TipoDatoCelda.MONEDA));
                if (item.getTTotalbilletesconsig() != null) {
                    reg.addCelda(new Celda(numColumna++, item.getTTotalbilletesconsig(), TipoDatoCelda.NUMERICO));
                } else {
                    reg.addCelda(new Celda(numColumna++, "", TipoDatoCelda.NORMAL));
                }

                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getTValorconsignacion()), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValorajustado()), TipoDatoCelda.MONEDA));

                if (item.getUsuariosrevisa() != null) {
                    reg.addCelda(new Celda(numColumna++, item.getUsuariosrevisa(), TipoDatoCelda.NORMAL));
                } else {
                    reg.addCelda(new Celda(numColumna++, "", TipoDatoCelda.NORMAL));
                }

                if (item.getUsuarioautoriza() != null) {
                    reg.addCelda(new Celda(numColumna++, item.getUsuarioautoriza(), TipoDatoCelda.NORMAL));
                } else {
                    reg.addCelda(new Celda(numColumna++, "", TipoDatoCelda.NORMAL));
                }

                if (item.getFechareintegro() != null) {
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFechareintegro(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                } else {
                    reg.addCelda(new Celda(numColumna++, "", TipoDatoCelda.NORMAL));
                }

                if (item.getErrorreintegro() != null) {
                    reg.addCelda(new Celda(numColumna++, item.getErrorreintegro(), TipoDatoCelda.NORMAL));
                } else {
                    reg.addCelda(new Celda(numColumna++, "", TipoDatoCelda.NORMAL));
                }

                //reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFechareintegro(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, EstadoReintegro.getEstadoReintegro(item.getEstadoreintegro()).nombre, TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, TipoCuentaMultifuncional.getTipoCuentaMultifuncional(item.getTTipocuenta()).toString(), TipoDatoCelda.NORMAL));

                lineas.add(reg);
            }

            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("MultifuncionalInforme", titulosHoja, titulosColumna, lineas);
            objectContext.enviarArchivoXLS(archivo);
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
            respuesta = this.respuestaJSon;
        } catch (IOException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
            respuesta = this.respuestaJSon;
        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());

        }
        return respuesta;
    }

    private String getParametro(String strParametro, String strValorDefault) {
        String strValParametro = "";

        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", strParametro);
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            strValParametro = registroEntityConsulta.getValor();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error obteniendo el valor del parametro: " + strParametro + "  " + ex.getMessage());
            strValParametro = strValorDefault;
        }
        return strValParametro;

    }

    public String generarDiarioElectronicoXML() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String informeReintegrosMultifuncionalXLS(Collection<ReintegrosMultifuncional> items) {

        String respuesta = "";
        String[] titulosHoja = TituloDiarioMultifuncionalGeneral.tituloHojaReintegrosMultifuncional;
        String[] titulosColumna = TituloDiarioMultifuncionalGeneral.tituloColumnasReintegrosMultifuncional;

        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;
        try {
            for (ReintegrosMultifuncional item : items) {
                Registro reg = new Registro();
                numColumna = 0;
                reg.addCelda(new Celda(numColumna++, item.getFechaHoraDispensador(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getBilletajeDispensador(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getTotalDispensador(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getCodigoTx(), TipoDatoCelda.NUMERICO));
                //revisa estado EstadosDiarioMultifuncional
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Cadena.aCerosIzquierda(item.getEstado(), 2) + " - " + EstadosDiarioMultifuncional.getEstadoDiarioMultifuncional(item.getEstado()).nombre, TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getCodigoCajero(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getNumerocorte(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getTransaccionconsecutivo(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFechacierre(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFechacajero(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, TipoCuentaMultifuncional.getTipoCuentaMultifuncional((Integer) item.getTipocuenta().intValue()).nombre, TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getNumerocuentaconsignar(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getNumerocuentahomologa(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValorconsignacion()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getNobilletesnd(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getNobilletesdenominacionf(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getNobilletesdenominacione(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getNobilletesdenominaciond(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getNobilletesdenominacionc(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getNobilletesdenominacionb(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getNobilletesdenominaciona(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getTotalbilletesconsignados(), TipoDatoCelda.NUMERICO));
                lineas.add(reg);
            }

            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("ReintegrosMultifuncional", titulosHoja, titulosColumna, lineas);
            objectContext.enviarArchivoXLS(archivo);
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
            respuesta = this.respuestaJSon;
        } catch (IOException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
            respuesta = this.respuestaJSon;
        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
        }
        return respuesta;
    }
}
