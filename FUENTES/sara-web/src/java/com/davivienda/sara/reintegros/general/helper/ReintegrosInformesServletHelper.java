package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.reintegros.general.formato.TituloReintegrosGeneral;
import com.davivienda.sara.reintegros.general.formato.TituloReintegrosOtraRed;
import com.davivienda.sara.reintegros.general.formato.TituloReintegrosBanagrario;
import com.davivienda.sara.reintegros.general.formato.TituloNotasDebitoGeneral;
import com.davivienda.sara.constantes.TipoCuentaReintegro;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.constantes.OfiRadicacion;
import com.davivienda.utilidades.conversion.JSon;
import java.util.Collection;
import java.util.Date;

import java.util.logging.Level;
import javax.ejb.EJBException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.constantes.RedCajero;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import java.io.IOException;
import java.util.ArrayList;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.tablas.notasdebito.session.NotasDebitoSessionLocal;
import com.davivienda.utilidades.Constantes;

/**
 * DiarioElectronicoGeneralTransaccionServletHelper - 27/08/2008 Descripción :
 * Helper para el manejo de los requerimientos de Transaccion Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReintegrosInformesServletHelper implements ReintegrosHelperInterface {

    private ReintegrosSessionLocal session = null;
    private ReintegrosGeneralObjectContext objectContext = null;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    private NotasDebitoSessionLocal notasDebitoSession;
    private String respuestaJSon;
    private String strDirLogoDavivienda;
    private long longValorComision;

    public ReintegrosInformesServletHelper(ReintegrosSessionLocal session, ReintegrosGeneralObjectContext objectContext, ConfModulosAplicacionLocal confModulosAplicacionSession, NotasDebitoSessionLocal notasDebitoSession) {
        this.session = session;
        this.objectContext = objectContext;
        this.confModulosAplicacionSession = confModulosAplicacionSession;
        this.notasDebitoSession = notasDebitoSession;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Reintegros> items = null;
        Collection<Notasdebito> itemsNd = null;
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
                Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
                // Consulta los registros según los parámetros tomados del request
                if (com.davivienda.utilidades.conversion.Cadena.aInteger(objectContext.getAtributoString("tipoReporte")).equals(TipoCuentaReintegro.NotasDebito.codigo)) {
                    itemsNd = notasDebitoSession.getNotasDebito(fechaInicial, fechaFinal, codigoCajero,fechaHisto);
                } else {
                    items = session.getReintegros(fechaInicial, fechaFinal, codigoCajero,fechaHisto);
                }
            } catch (EJBException ex) {
                 objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_SIN_DATA);
                }
            } catch (Exception ex) {
                 objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
            }

        } catch (IllegalArgumentException ex) {
             objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(),Constantes.MSJ_QUERY_ERROR);
        }

        if (items != null && this.respuestaJSon.length() <= 1) {
            clasificarReporteSeleccion(items, fechaInicial);
            //respuesta = aJSon(items);  // paso los items a JSON
        } else if (itemsNd != null && this.respuestaJSon.length() <= 1) {
            informeNotasDebitoXLS(itemsNd);
            //respuesta = aJSon(items);  // paso los items a JSON
        } else {

            respuesta = this.respuestaJSon;
        }

        return respuesta;
    }

    private String aJSon(Collection<Reintegros> items) {
        String cadenaJSon = "";
        try {
            Integer idRegistro = 0;
            JSONObject resp = new JSONObject();
            JSONArray respItems = new JSONArray();
            for (Reintegros item : items) {
                JSONObject itemJSon = new JSONObject();
                itemJSon.put("idRegistro", ++idRegistro);
                itemJSon.put("codigoCajero", item.getReintegrosPK().getHCodigocajero().toString());
                itemJSon.put("codigoOcca", item.getHCodigoocca().toString());
                itemJSon.put("numeroTransaccion", item.getReintegrosPK().getHTalon().toString());
                itemJSon.put("numeroCuenta", item.getHNumerocuenta());
                itemJSon.put("numeroTarjeta", item.getTTarjeta());
                itemJSon.put("fecha", com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosPK().getHFechasistema(), FormatoFecha.FECHA_HORA));
                itemJSon.put("valor", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getHValor()));
                itemJSon.put("valorAjustar", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getValorajustado()));
                itemJSon.put("statusTransaccion", item.getTCodigoterminaciontransaccion());
                itemJSon.put("valorAjustado", com.davivienda.utilidades.conversion.Numero.aFormatoDecimal(item.getTValorentregado()));
                itemJSon.put("redEnruta", RedCajero.getRedCajero(Cadena.aInteger(item.getHNumerocuenta().substring(0, 4))).toString());
                //  clasificarSobrante(item.getHNumerocuenta(),item.getTTarjeta()) ;
                respItems.put(itemJSon);
            }
            resp.put("identifier", "idRegistro");
            resp.put("label", "codigoCajero");
            resp.put("items", respItems);
            cadenaJSon = resp.toString();

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.severe("No se puede pasar a JSON \n " + ex.getMessage());
            cadenaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_PARSEAR_REGISTRO.getCodigo(), ex.getMessage());
        }
        return cadenaJSon;
    }

    public String informeGeneralXLS(Collection<Reintegros> items, boolean blnTodos, Date fechaInicial) {

        String respuesta = "";
        String[] titulosHoja = TituloReintegrosGeneral.tituloHoja;
        titulosHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
        String[] titulosColumna = TituloReintegrosGeneral.tituloColumnas;

        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;
        try {
            for (Reintegros item : items) {
                if (null == item.getDifeDescuadre() || !item.getDifeDescuadre().equals(Constantes.PARAM_REINTEGROS_DESCUADRE_N)) {
                    Registro reg = new Registro();
                    numColumna = 0;
                    //public static String[] tituloColumnas = {"CAJERO",  "TRANSACCION", "OCCA","CUENTA","TARJETA","FECHA","VALOR","VALOR AJUSTAR","VALOR AJUSTADO,"STATUS TX","VALOR AJUSTADO","STATUS TX","RED ENRUTO","USUARIOREVISA","USUARIOAUTORIZA","FECHAREINTEGRO","ERROR","ESTADO","CLASIFICACION"};
                    reg.addCelda(new Celda(numColumna++, item.getReintegrosPK().getHCodigocajero(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getReintegrosPK().getHTalon(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getHCodigoocca(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getHNumerocuenta(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getTTarjeta(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosPK().getHFechasistema(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getTValorsolicitado()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getTValorentregado()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getHValor()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValorajustado()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, item.getTCodigoterminaciontransaccion(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getHNumerocuenta().substring(0, 4).toString(), TipoDatoCelda.NORMAL));
                    if (item.getUsuariorevisa() != null) {
                        reg.addCelda(new Celda(numColumna++, item.getUsuariorevisa(), TipoDatoCelda.NORMAL));
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
                    reg.addCelda(new Celda(numColumna++, EstadoReintegro.getEstadoReintegro(item.getEstadoreintegro()).nombre, TipoDatoCelda.NORMAL));
                    //CIBC 2 mayo
                    if (item.getTipoCuentaReintegro().equals(TipoCuentaReintegro.OtraRed.codigo)) {
                        if (item.getHOfiRadicacion() != null) {
                            reg.addCelda(new Celda(numColumna++, OfiRadicacion.getOfiRadicacion(item.getHOfiRadicacion()).nombre, TipoDatoCelda.NORMAL));
                        } else {
                            reg.addCelda(new Celda(numColumna++, TipoCuentaReintegro.getTipoCuentaReintegro(item.getTipoCuentaReintegro()).nombre, TipoDatoCelda.NORMAL));
                        }
                    } else {
                        reg.addCelda(new Celda(numColumna++, TipoCuentaReintegro.getTipoCuentaReintegro(item.getTipoCuentaReintegro()).nombre, TipoDatoCelda.NORMAL));
                    }
                    reg.addCelda(new Celda(numColumna++, item.getConcepto() != null ? item.getConcepto() : "", TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getComision()!= null ? item.getComision(): "", TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getFechaReversado()!= null ? com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFechaReversado(), FormatoFecha.FECHA_HORA): "", TipoDatoCelda.NORMAL));
                    if (blnTodos) {
                        reg.addCelda(new Celda(numColumna++, null != item.getDispensed() ? item.getDispensed() : "", TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, null != item.getRemaining() ? item.getRemaining() : "", TipoDatoCelda.NORMAL));
                        titulosColumna = TituloReintegrosGeneral.tituloColumnasTodos;
                        lineas.add(reg);
                    } else if (((item.getTipoCuentaReintegro().equals(TipoCuentaReintegro.CuentaAhorros.codigo)) || (item.getTipoCuentaReintegro().equals(TipoCuentaReintegro.CuentaCorriente.codigo)))) {
                        lineas.add(reg);
                    }

                }

            }

            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("Reintegros", titulosHoja, titulosColumna, lineas);
            objectContext.enviarArchivoXLS(archivo);
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
            ex.printStackTrace();
            respuesta = this.respuestaJSon;
        } catch (IOException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
            ex.printStackTrace();
            respuesta = this.respuestaJSon;
        } catch (Exception ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
            ex.printStackTrace();
        }

        return respuesta;

    }

    public String informeOtraRedXLS(Collection<Reintegros> items, String nombreArchivo, TipoCuentaReintegro tipoReporte, Boolean observaciones) {

        String respuesta = "";
        String[] titulosHoja = TituloReintegrosOtraRed.tituloHoja;
        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;
        String estadoObservacion = "";
        String[] titulosColumna;

        if (observaciones) {
            titulosColumna = TituloReintegrosOtraRed.tituloColumnasObservacion;
            estadoObservacion = "";
        } else if (tipoReporte.codigo.equals(TipoCuentaReintegro.OtraRed.codigo)) {
            titulosColumna = TituloReintegrosOtraRed.tituloColumnasORed;
        } else {
            titulosColumna = TituloReintegrosOtraRed.tituloColumnas;
            estadoObservacion = "TRANSACCION CON PROBLEMAS";
        }

        try {
            for (Reintegros item : items) {
                Registro reg = new Registro();
                numColumna = 0;
                //{"FECHA MOV",  "HORA","TARJETA", "ATM","TALON","VALOR A ABONAR","ESTADO DE LA TRANSACCION","RED"};
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosPK().getHFechasistema(), FormatoFecha.DEFECTO), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosPK().getHFechasistema(), FormatoFecha.HOUR_REINTEGROS), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getTTarjeta(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getReintegrosPK().getHCodigocajero(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getReintegrosPK().getHTalon(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getHValor()), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, estadoObservacion, TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, "SOBRANTE DALI 20 " + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), "MMdd"), TipoDatoCelda.NORMAL));
                if (tipoReporte.codigo.equals(TipoCuentaReintegro.OtraRed.codigo)) {

                    reg.addCelda(new Celda(numColumna++, item.getHNumerocuenta(), TipoDatoCelda.NORMAL));

                }

                if (item.getTipoCuentaReintegro().equals(tipoReporte.codigo) && (item.getEstadoreintegro().equals(EstadoReintegro.INICIADO.codigo))) {

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

    public String informeBanAgrarioXLS(Collection<Reintegros> items, TipoCuentaReintegro tipoReporte) {

        long SumTransacciones = 0;
        long SumComision = 0;
        long SumTotal = 0;
        long comision = longValorComision;
        String[] subtituloTotalesHoja = null;
        String respuesta = "";
        String[] titulosHoja = TituloReintegrosBanagrario.tituloHoja;
        String[] titulosColumna = TituloReintegrosBanagrario.tituloColumnas;
        String subtituloHoja = TituloReintegrosBanagrario.subtituloHoja;
        String[] subtituloInferiorHoja = TituloReintegrosBanagrario.subtituloInferiorHoja;
        subtituloTotalesHoja = new String[3];
        String[] firmaHoja = TituloReintegrosBanagrario.firmaHoja;

        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;

        try {
            for (Reintegros item : items) {
                Registro reg = new Registro();
                numColumna = 0;
                //{"TRANS",  "CAUSAL","FECHA", "HORA","VALOR","COMISION","TARJETA","CAJERO","OFICINA"};
                reg.addCelda(new Celda(numColumna++, item.getReintegrosPK().getHTalon(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, "6", TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosPK().getHFechasistema(), FormatoFecha.DEFECTO), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getReintegrosPK().getHFechasistema(), FormatoFecha.HOUR_REINTEGROS), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getHValor()), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(comision), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, item.getTTarjeta(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getReintegrosPK().getHCodigocajero(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getHCodigoocca(), TipoDatoCelda.NORMAL));

                if (item.getTipoCuentaReintegro().equals(tipoReporte.codigo) && (item.getEstadoreintegro().equals(EstadoReintegro.INICIADO.codigo))) {
                    SumComision = SumComision + comision;
                    SumTransacciones = SumTransacciones + item.getHValor();
                    SumTotal = SumComision + SumTransacciones;

                    lineas.add(reg);
                }

            }
            subtituloTotalesHoja[0] = "TOTAL TRANSACCIONES   " + com.davivienda.utilidades.conversion.Numero.aMoneda(SumTransacciones);
            subtituloTotalesHoja[1] = "TOTAL COMISIONES            " + com.davivienda.utilidades.conversion.Numero.aMoneda(SumComision);
            subtituloTotalesHoja[2] = "TOTAL                                     " + com.davivienda.utilidades.conversion.Numero.aMoneda(SumTotal);

            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibroBanAgrario("AbonoBanAgrario", titulosHoja, subtituloHoja, titulosColumna, lineas, subtituloTotalesHoja, subtituloInferiorHoja, firmaHoja);
            objectContext.enviarArchivoXLS(archivo);
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
            ex.printStackTrace();
            respuesta = this.respuestaJSon;
        } catch (IOException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
            respuesta = this.respuestaJSon;
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
        }

        return respuesta;

    }

    public String informeNotasDebitoXLS(Collection<Notasdebito> items) {

        String respuesta = "";
        String[] titulosHoja = TituloNotasDebitoGeneral.tituloHoja;
        String[] titulosColumna = TituloNotasDebitoGeneral.tituloColumnas;

        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;
        try {
            for (Notasdebito item : items) {
                Registro reg = new Registro();
                numColumna = 0;
                //tituloColumnas = {"CAJERO",  "OCCA","CUENTA","FECHA","VALOR","VALOR AJUSTAR","USUARIOCREANOTA","USUARIOAUTORIZA","FECHAAPLICA","ERROR","ESTADO","CLASIFICACION"};
                reg.addCelda(new Celda(numColumna++, item.getNotasdebitoPK().getCodigocajero(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getCodigoocca(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getNumerocuenta(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getNotasdebitoPK().getFecha(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
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
                reg.addCelda(new Celda(numColumna++, item.getConcepto()!=null ? item.getConcepto() : "" , TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getComision() != null ? item.getComision() : "", TipoDatoCelda.NORMAL));
                
                lineas.add(reg);

            }

            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("NotasDebito", titulosHoja, titulosColumna, lineas);
            objectContext.enviarArchivoXLS(archivo);
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
            ex.printStackTrace();
            respuesta = this.respuestaJSon;
        } catch (IOException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
            ex.printStackTrace();
            respuesta = this.respuestaJSon;
        } catch (Exception ex) {
            ex.printStackTrace();
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

    private void clasificarReporteSeleccion(Collection<Reintegros> items, Date fechaInicial) {

        if (objectContext.getAtributoString("tipoReporte").length() > 0) {

            Integer tipoCuentaReintegro = com.davivienda.utilidades.conversion.Cadena.aInteger(objectContext.getAtributoString("tipoReporte"));
            TipoCuentaReintegro tipoReporte = TipoCuentaReintegro.Todas;
            tipoReporte = TipoCuentaReintegro.getTipoCuentaReintegro(tipoCuentaReintegro);
            String nombreArchivo = "";

            switch (tipoReporte) {
                case Todas:
                    informeGeneralXLS(items, true, fechaInicial);
                    break;
                case OtraRed:
                    nombreArchivo = "OTRASREDES_" + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), "MMdd");
                    informeOtraRedXLS(items, nombreArchivo, tipoReporte, false);
                    break;
                case VisaPagos:
                    nombreArchivo = "VISAPAGOS_" + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), "MMdd");
                    informeOtraRedXLS(items, nombreArchivo, tipoReporte, false);
                    break;
                case TarjetaCredito:
                    nombreArchivo = "DAVIVIENDATARJETADECREDITO_" + com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), "MMdd");
                    informeOtraRedXLS(items, nombreArchivo, tipoReporte, true);
                    break;
                case Banagrario:
                    informeBanAgrarioXLS(items, tipoReporte);
                    break;
                case DaviviendaTodas:
                    informeGeneralXLS(items, false, fechaInicial);
                    break;
            }
        } else {
            informeGeneralXLS(items, true, fechaInicial);
        }
    }
}
