package com.davivienda.sara.procesos.ajustes.session;

import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Occa;
import javax.ejb.Local;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.ws.WebServiceException;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.procesos.historicoajustes.session.AdministradorProcesosHistoricoAjustesSessionLocal;
import com.davivienda.utilidades.ws.gestor.cliente.InvocacionServicios;
import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustes;
import javax.ejb.EJB;
import java.util.Date;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */

@Stateless
@Local(value = AjustesProcesosSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AjustesProcesosSessionBean implements AjustesProcesosSessionLocal {

    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private CajeroServicio cajeroServicio;
    private OccaServicio occaServicio;
    private InvocacionServicios invocacionServicios;
    String servidor = "";
    String puerto = "";
    private static SaraConfig configApp;
    @EJB
    private AdministradorProcesosHistoricoAjustesSessionLocal administradorProcesosHistoricoAjustesSessionLocal;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {

        cajeroServicio = new CajeroServicio(em);
        occaServicio = new OccaServicio(em);
        servidor = em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.SERVIDOR_WS")).getValor().trim();
        puerto = em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.PUERTO_SERVIDOR_WS")).getValor().trim();
        iniciarLog();

    }

    /**
     * Método para obtener el Codigo de terminal caja
     *
     * @param codigoOcca
     * @return Integer CodigoTerminalCaja
     *
     */
    private Integer obtenerCodigoTerminal(Integer codigoOcca) {
        Integer codTerminal = 0;
        try {
            Occa occa = occaServicio.buscar(codigoOcca);
            if (occa != null) {
                codTerminal = occa.getCodigoTerminal();
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("No se pudo obtener el CodigoTerminal de la occa" + ex.getMessage());
        }
        return codTerminal;
    }

    private void iniciarLog() {
        if (configApp == null) {
            try {
                configApp = SaraConfig.obtenerInstancia();
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AjustesProcesosSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (Exception ex) {
                Logger.getLogger(AjustesProcesosSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    /**
     * Método para obtener el talon codigoCajero + consecutivo debe ser de 6
     * posiciones
     *
     * @param codigoCajero
     * @return String Talon
     */
    private String armarTalon(Integer codigoCajero) {
        String strCodigoCajero = "0";
        String talon = "";
        strCodigoCajero = codigoCajero.toString();
        Integer milisegundos = com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().get(Calendar.MILLISECOND);
        if (codigoCajero < 10) {
            strCodigoCajero = "000" + strCodigoCajero;
        } else if (codigoCajero < 100) {
            strCodigoCajero = "00" + strCodigoCajero;
        } else if (codigoCajero < 1000) {
            strCodigoCajero = "0" + strCodigoCajero;

        }
        talon = strCodigoCajero + milisegundos.toString().substring(1);
        //EL TALON DEBE SER DE 6 CARACTERES
        if (talon.length() == 4) {
            talon = talon + "0";
        }
        if (talon.length() == 5) {
            talon = talon + "0";
        }
        return talon;
    }

    private void guardarHistoricoAjustes(String usuario, Integer codigoCajero, Integer codigoOcca, String tipoAjuste, Date fecha, BigDecimal valor, String talon, String error, String descripcionError) {
        try {
            administradorProcesosHistoricoAjustesSessionLocal.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, tipoAjuste, fecha, valor, talon, error, descripcionError);
        } catch (SecurityException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, " Error al guardar datos en la tabla HistoricoAjustes:" + ex.getMessage());
        }
    }

    /**
     * Método para realizar el ajuste sobrante de efectivo del cajero automático
     *
     * @param codigoCajero
     * @param codigoOcca
     * @param valorAjuste
     * @param fechaAjuste formato AAAAMMDD
     * @param talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @return
     * @throws
     * com.davivienda.procesadortransacciones.impl.servicios.ServicioException_Exception
     */
    public String realizarAjustePorSobrante(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            invocacionServicios = new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            fecha = com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
            fechaActual = com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
            talon = armarTalon(codigoCajero);
            respuesta = invocacionServicios.realizarAjustePorSobrante(codigoCajero, codigoOcca, valorAjuste, fechaActual, talon);
            configApp.loggerApp.log(java.util.logging.Level.FINE, "EN AjustePorSobrante para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("M")) {
                        respuestaDescripcion = "No se pudo realizar el ajuste por Sobrante";
                    } else if (respuesta.substring(0, 1).equals("B")) {
                        respuestaDescripcion = respuestaDescripcion += "-Ajuste Realizado con Exito";
                    }
                }
            }
        } catch (SecurityException ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobrante Error al ejecutar el requerimiento:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN AjustePorSobrante Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } catch (WebServiceException ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobrante Error al llamar al WS:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN AjustePorSobrante Error al llamar al WS:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } catch (Exception ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobrante Error: " + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN AjustePorSobrante Error: " + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } finally {
            guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste por Sobrante", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }

    public String realizarAjustePorFaltante(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            invocacionServicios = new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            fecha = com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
            fechaActual = com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
            talon = armarTalon(codigoCajero);
            respuesta = invocacionServicios.realizarAjustePorFaltante(codigoCajero, codigoOcca, valorAjuste, fechaActual, talon);
            configApp.loggerApp.log(java.util.logging.Level.FINE, "EN AjustePorFaltante para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("M")) {
                        respuestaDescripcion = "No se pudo realizar el ajuste por Faltante";
                    } else if (respuesta.substring(0, 1).equals("B")) {
                        respuestaDescripcion = respuestaDescripcion += "-Ajuste Realizado con Exito";
                    }
                }

            }
        } catch (SecurityException ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltante Error al ejecutar el requerimiento:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN AjustePorFaltante Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F-" + respuestaDescripcion;
        } catch (WebServiceException ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltante Error al llamar al WS:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN AjustePorFaltante Error al llamar al WS:" + ex.getMessage());
            respuesta = "F-" + respuestaDescripcion;
        } catch (Exception ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F-";
            respuestaDescripcion = "EN AjustePorFaltante Error: " + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN AjustePorFaltante Error: " + ex.getMessage());
            respuesta = "F-" + respuestaDescripcion;
        } finally {
            guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste por Faltante", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);

        }

        return respuesta;
    }

    public String realizarAjusteAumentoProvision(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            fecha = com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
            invocacionServicios = new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            respuesta = invocacionServicios.realizarAjusteAumentoProvision(codigoCajero, valorAjuste);
            configApp.loggerApp.log(java.util.logging.Level.FINE, "EN AjusteAumentoProvision para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("M")) {
                        respuestaDescripcion = "NO se pudo Realizar el Ajuste";
                    } else if (respuesta.substring(0, 1).equals("B")) {
                        respuestaDescripcion = respuestaDescripcion += "-Ajuste Realizado con Exito";
                    }
                }
            }
        } catch (SecurityException ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN AjusteAumentoProvision Error al ejecutar el requerimiento:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN AjusteAumentoProvision Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = respuestaDescripcion;
        } catch (WebServiceException ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN AjusteAumentoProvision Error al llamar al WS:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN AjusteAumentoProvision Error al llamar al WS:" + ex.getMessage());
            respuesta = respuestaDescripcion;
        } catch (Exception ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "AjusteAumentoProvision Error: " + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN AjusteAumentoProvision Error: " + ex.getMessage());
            respuesta = respuestaDescripcion;
        } finally {
            guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste Aumento Provision", fecha, valorAjuste, "000000", respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }

    public String realizarAjusteDisminucionProvision(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            fecha = com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
            invocacionServicios = new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            respuesta = invocacionServicios.realizarAjusteDisminucionProvision(codigoCajero, valorAjuste);
            configApp.loggerApp.log(java.util.logging.Level.FINE, "EN DisminucionProvision para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("M")) {
                        respuestaDescripcion = "NO se pudo Realizar el Ajuste";
                    } else if (respuesta.substring(0, 1).equals("B")) {
                        respuestaDescripcion = respuestaDescripcion += "-Ajuste Realizado con Exito";
                    }
                }
            }
        } catch (SecurityException ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN DisminucionProvision Error al ejecutar el requerimiento:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN DisminucionProvision Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } catch (WebServiceException ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN DisminucionProvision Error al llamar al WS:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN DisminucionProvision Error al llamar al WS:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } catch (Exception ex) {
            configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN DisminucionProvision Error: " + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN DisminucionProvision Error: " + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } finally {
            guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste Disminucion Provision", fecha, valorAjuste, "000000", respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }

    public String ajustarEgreso(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste, short concepto) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            invocacionServicios = new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            //invocacionServicios=new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            fecha = com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
            fechaActual = com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
            talon = armarTalon(codigoCajero);
            respuesta = invocacionServicios.ajustarEgresoESB(codigoCajero, codigoOcca, valorAjuste, fechaActual, talon, "0", concepto, "0"); // "0"-> canal "0"->nitTransportadora
            configApp.loggerApp.log(java.util.logging.Level.FINE, "EN ajustarEgreso para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("M")) {
                        respuestaDescripcion = "NO se pudo Realizar el Ajuste";
                    } else if (respuesta.substring(0, 1).equals("B")) {
                        respuestaDescripcion = respuestaDescripcion += "Ajuste Realizado con Exito";
                    }
                }
            }
        } catch (SecurityException ex) {

            respuestaError = "F";
            respuestaDescripcion = "EN Ajustar Egreso Error al ejecutar el requerimiento:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN Ajustar Egreso Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } catch (WebServiceException ex) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltante Error al llamar al WS:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN Ajustar Egreso Error al llamar al WS:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;

        } catch (Exception ex) {

            respuestaError = "F";
            respuestaDescripcion = "EN Ajustar Egreso Error: " + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN Ajustar Egreso Error: " + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;

        } finally {

            guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste Egreso", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);

        }

        return respuesta;

    }

    public String ajustarIngreso(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste, short concepto) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            invocacionServicios = new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            //invocacionServicios=new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            fecha = com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
            fechaActual = com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
            talon = armarTalon(codigoCajero);
            respuesta = invocacionServicios.ajustarIngresoESB(codigoCajero, codigoOcca, valorAjuste, fechaActual, talon, "0", concepto, "");
            configApp.loggerApp.log(java.util.logging.Level.FINE, "EN ajustarIngreso para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("M")) {
                        respuestaDescripcion = "NO se pudo Realizar el Ajuste";
                    } else if (respuesta.substring(0, 1).equals("B")) {
                        respuestaDescripcion = respuestaDescripcion += "Ajuste Realizado con Exito";
                    }
                }
            }
        } catch (SecurityException ex) {
            respuestaError = "F";
            respuestaDescripcion = "EN Ajustar Ingreso Error al ejecutar el requerimiento:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN Ajustar Ingreso Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } catch (WebServiceException ex) {
            respuestaError = "F";
            respuestaDescripcion = "EN Ajustar Ingreso Error al llamar al WS:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN Ajustar Ingreso Error al llamar al WS:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } catch (Exception ex) {
            respuestaError = "F";
            respuestaDescripcion = "EN Ajustar Ingreso Error: " + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN Ajustar Ingreso Error: " + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } finally {
            guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste Ingreso", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }

    /**
     * Método para consultar el informe de totales de un cajero
     *
     * @param codigoCajero Código del cajero a consultar
     * @return RespuestaInformeTotalesATMDto En el Arraylist registrosRepetitivo
     * se tienen uno a uno los registros que se deben presentar
     * @throws
     * com.davivienda.procesadortransacciones.impl.servicios.informetotalesatm.ServicioException_Exception
     */
    public ResumenAjustes[] consultarInformeTotalesATM(Integer codigoCajero, Integer codigoOcca) {
        ResumenAjustes[] resumen = null;
        try {
            invocacionServicios = new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            resumen = invocacionServicios.consultarInformeTotalesATM(codigoCajero);

        } catch (SecurityException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el requerimiento:" + ex.getMessage());
        } catch (WebServiceException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al llamar al WS:" + ex.getMessage());
        } catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error: " + ex.getMessage());
        }
        return resumen;
    }

    /**
     * Método para la consulta de resumen IDO de la Terminal de Caja
     *
     * @return RespuestaResumenIDOATMDto
     * @throws
     * com.davivienda.procesadortransacciones.impl.servicios.resumenidoatm.ServicioException_Exception
     */
    public ResumenAjustes[] consultarResumenIDOTerminal(Integer codigoOcca) {
        ResumenAjustes[] resumen = null;
        try {
            invocacionServicios = new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            resumen = invocacionServicios.consultarResumenIDOTerminal();
        } catch (SecurityException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el requerimiento:" + ex.getMessage());
        } catch (WebServiceException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al llamar al WS:" + ex.getMessage());
        } catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error: " + ex.getMessage());
        }
        return resumen;
    }

    /**
     * Método para la consulta de resumen IDO de la oficina OCCA
     *
     * @return RespuestaResumenIDOATMDto
     * @throws
     * com.davivienda.procesadortransacciones.impl.servicios.resumenidoatm.ServicioException_Exception
     */
    public ResumenAjustes[] consultarResumenIDOOficina(Integer codigoOcca) {
        ResumenAjustes[] resumen = null;
        try {
            invocacionServicios = new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            resumen = invocacionServicios.consultarResumenIDOOficina();
        } catch (SecurityException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el requerimiento:" + ex.getMessage());
        } catch (WebServiceException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al llamar al WS:" + ex.getMessage());
        } catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error: " + ex.getMessage());
        }
        return resumen;
    }

}
