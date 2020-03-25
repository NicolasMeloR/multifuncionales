// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.ajustes.session;

import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustes;
import javax.xml.ws.WebServiceException;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.math.BigDecimal;
import java.util.Date;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.davivienda.sara.entitys.Occa;
import javax.annotation.PostConstruct;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import javax.ejb.EJB;
import com.davivienda.sara.procesos.historicoajustes.session.AdministradorProcesosHistoricoAjustesSessionLocal;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.utilidades.ws.gestor.cliente.InvocacionServicios;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ AjustesProcesosSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class AjustesProcesosSessionBean implements AjustesProcesosSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private CajeroServicio cajeroServicio;
    private OccaServicio occaServicio;
    private InvocacionServicios invocacionServicios;
    String servidor;
    String puerto;
    private static SaraConfig configApp;
    @EJB
    private AdministradorProcesosHistoricoAjustesSessionLocal administradorProcesosHistoricoAjustesSessionLocal;
    
    public AjustesProcesosSessionBean() {
        this.servidor = "";
        this.puerto = "";
    }
    
    @PostConstruct
    public void postConstructor() {
        this.cajeroServicio = new CajeroServicio(this.em);
        this.occaServicio = new OccaServicio(this.em);
        this.servidor = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.SERVIDOR_WS"))).getValor().trim();
        this.puerto = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.PUERTO_SERVIDOR_WS"))).getValor().trim();
        this.iniciarLog();
    }
    
    private Integer obtenerCodigoTerminal(final Integer codigoOcca) {
        Integer codTerminal = 0;
        try {
            final Occa occa = this.occaServicio.buscar(codigoOcca);
            if (occa != null) {
                codTerminal = occa.getCodigoTerminal();
            }
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("No se pudo obtener el CodigoTerminal de la occa" + ex.getMessage());
        }
        return codTerminal;
    }
    
    private void iniciarLog() {
        if (AjustesProcesosSessionBean.configApp == null) {
            try {
                AjustesProcesosSessionBean.configApp = SaraConfig.obtenerInstancia();
            }
            catch (IllegalAccessException ex) {
                Logger.getLogger(AjustesProcesosSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            catch (Exception ex2) {
                Logger.getLogger(AjustesProcesosSessionBean.class.getName()).log(Level.SEVERE, ex2.getMessage(), ex2);
            }
        }
    }
    
    private String armarTalon(final Integer codigoCajero) {
        String strCodigoCajero = "0";
        String talon = "";
        strCodigoCajero = codigoCajero.toString();
        final Integer milisegundos = Fecha.getCalendarHoy().get(14);
        if (codigoCajero < 10) {
            strCodigoCajero = "000" + strCodigoCajero;
        }
        else if (codigoCajero < 100) {
            strCodigoCajero = "00" + strCodigoCajero;
        }
        else if (codigoCajero < 1000) {
            strCodigoCajero = "0" + strCodigoCajero;
        }
        talon = strCodigoCajero + milisegundos.toString().substring(1);
        if (talon.length() == 4) {
            talon += "0";
        }
        if (talon.length() == 5) {
            talon += "0";
        }
        return talon;
    }
    
    private void guardarHistoricoAjustes(final String usuario, final Integer codigoCajero, final Integer codigoOcca, final String tipoAjuste, final Date fecha, final BigDecimal valor, final String talon, final String error, final String descripcionError) {
        try {
            this.administradorProcesosHistoricoAjustesSessionLocal.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, tipoAjuste, fecha, valor, talon, error, descripcionError);
        }
        catch (SecurityException ex) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, " Error al guardar datos en la tabla HistoricoAjustes:" + ex.getMessage());
        }
    }
    
    @Override
    public String realizarAjustePorSobrante(final String usuario, final Integer codigoCajero, final Integer codigoOcca, final BigDecimal valorAjuste) {
        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalon(codigoCajero);
            respuesta = this.invocacionServicios.realizarAjustePorSobrante(codigoCajero, codigoOcca, valorAjuste, fechaActual, talon);
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.FINE, "EN AjustePorSobrante para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "No se pudo realizar el ajuste por Sobrante";
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion += "Ajuste Realizado con Exito");
                }
            }
        }
        catch (SecurityException ex) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobrante Error al ejecutar el requerimiento:" + ex.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorSobrante Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex2.getMessage(), (Throwable)ex2);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobrante Error al llamar al WS:" + ex2.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorSobrante Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (Exception ex3) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex3.getMessage(), ex3);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobrante Error: " + ex3.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorSobrante Error: " + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste por Sobrante", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public String realizarAjustePorFaltante(final String usuario, final Integer codigoCajero, final Integer codigoOcca, final BigDecimal valorAjuste) {
        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalon(codigoCajero);
            respuesta = this.invocacionServicios.realizarAjustePorFaltante(codigoCajero, codigoOcca, valorAjuste, fechaActual, talon);
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.FINE, "EN AjustePorFaltante para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "No se pudo realizar el ajuste por Faltante";
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion += "Ajuste Realizado con Exito");
                }
            }
        }
        catch (SecurityException ex) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltante Error al ejecutar el requerimiento:" + ex.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorFaltante Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex2.getMessage(), (Throwable)ex2);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltante Error al llamar al WS:" + ex2.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorFaltante Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (Exception ex3) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex3.getMessage(), ex3);
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltante Error: " + ex3.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorFaltante Error: " + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste por Faltante", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public String realizarAjusteAumentoProvision(final String usuario, final Integer codigoCajero, final Integer codigoOcca, final BigDecimal valorAjuste) {
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            fecha = Fecha.getCalendarHoy().getTime();
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            respuesta = this.invocacionServicios.realizarAjusteAumentoProvision(codigoCajero, valorAjuste);
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.FINE, "EN AjusteAumentoProvision para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "NO se pudo Realizar el Ajuste";
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion += "Ajuste Realizado con Exito");
                }
            }
        }
        catch (SecurityException ex) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN AjusteAumentoProvision Error al ejecutar el requerimiento:" + ex.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjusteAumentoProvision Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex2.getMessage(), (Throwable)ex2);
            respuestaError = "F";
            respuestaDescripcion = "EN AjusteAumentoProvision Error al llamar al WS:" + ex2.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjusteAumentoProvision Error al llamar al WS:" + ex2.getMessage());
            respuesta = respuestaDescripcion;
        }
        catch (Exception ex3) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex3.getMessage(), ex3);
            respuestaError = "F";
            respuestaDescripcion = "AjusteAumentoProvision Error: " + ex3.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjusteAumentoProvision Error: " + ex3.getMessage());
            respuesta = respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste Aumento Provision", fecha, valorAjuste, "000000", respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public String realizarAjusteDisminucionProvision(final String usuario, final Integer codigoCajero, final Integer codigoOcca, final BigDecimal valorAjuste) {
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            fecha = Fecha.getCalendarHoy().getTime();
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            respuesta = this.invocacionServicios.realizarAjusteDisminucionProvision(codigoCajero, valorAjuste);
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.FINE, "EN DisminucionProvision para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "NO se pudo Realizar el Ajuste";
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion += "Ajuste Realizado con Exito");
                }
            }
        }
        catch (SecurityException ex) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            respuestaError = "F";
            respuestaDescripcion = "EN DisminucionProvision Error al ejecutar el requerimiento:" + ex.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN DisminucionProvision Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex2.getMessage(), (Throwable)ex2);
            respuestaError = "F";
            respuestaDescripcion = "EN DisminucionProvision Error al llamar al WS:" + ex2.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN DisminucionProvision Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (Exception ex3) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, ex3.getMessage(), ex3);
            respuestaError = "F";
            respuestaDescripcion = "EN DisminucionProvision Error: " + ex3.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN DisminucionProvision Error: " + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste Disminucion Provision", fecha, valorAjuste, "000000", respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public String ajustarEgreso(final String usuario, final Integer codigoCajero, final Integer codigoOcca, final BigDecimal valorAjuste, final short concepto) {
        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalon(codigoCajero);
            respuesta = this.invocacionServicios.ajustarEgresoESB(codigoCajero, codigoOcca, valorAjuste, fechaActual, talon, "0", concepto, "0");
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.FINE, "EN ajustarEgreso para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "NO se pudo Realizar el Ajuste";
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion += "Ajuste Realizado con Exito");
                }
            }
        }
        catch (SecurityException ex) {
            respuestaError = "F";
            respuestaDescripcion = "EN Ajustar Egreso Error al ejecutar el requerimiento:" + ex.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN Ajustar Egreso Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltante Error al llamar al WS:" + ex2.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN Ajustar Egreso Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (Exception ex3) {
            respuestaError = "F";
            respuestaDescripcion = "EN Ajustar Egreso Error: " + ex3.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN Ajustar Egreso Error: " + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste Egreso", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public String ajustarIngreso(final String usuario, final Integer codigoCajero, final Integer codigoOcca, final BigDecimal valorAjuste, final short concepto) {
        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalon(codigoCajero);
            respuesta = this.invocacionServicios.ajustarIngresoESB(codigoCajero, codigoOcca, valorAjuste, fechaActual, talon, "0", concepto, "");
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.FINE, "EN ajustarIngreso para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "NO se pudo Realizar el Ajuste";
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion += "Ajuste Realizado con Exito");
                }
            }
        }
        catch (SecurityException ex) {
            respuestaError = "F";
            respuestaDescripcion = "EN Ajustar Ingreso Error al ejecutar el requerimiento:" + ex.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN Ajustar Ingreso Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaError = "F";
            respuestaDescripcion = "EN Ajustar Ingreso Error al llamar al WS:" + ex2.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN Ajustar Ingreso Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (Exception ex3) {
            respuestaError = "F";
            respuestaDescripcion = "EN Ajustar Ingreso Error: " + ex3.getMessage();
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN Ajustar Ingreso Error: " + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, "Ajuste Ingreso", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public ResumenAjustes[] consultarInformeTotalesATM(final Integer codigoCajero, final Integer codigoOcca) {
        ResumenAjustes[] resumen = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            resumen = this.invocacionServicios.consultarInformeTotalesATM(codigoCajero);
        }
        catch (SecurityException ex) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el requerimiento:" + ex.getMessage());
        }
        catch (WebServiceException ex2) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al llamar al WS:" + ex2.getMessage());
        }
        catch (Exception ex3) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error: " + ex3.getMessage());
        }
        return resumen;
    }
    
    @Override
    public ResumenAjustes[] consultarResumenIDOTerminal(final Integer codigoOcca) {
        ResumenAjustes[] resumen = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            resumen = this.invocacionServicios.consultarResumenIDOTerminal();
        }
        catch (SecurityException ex) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el requerimiento:" + ex.getMessage());
        }
        catch (WebServiceException ex2) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al llamar al WS:" + ex2.getMessage());
        }
        catch (Exception ex3) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error: " + ex3.getMessage());
        }
        return resumen;
    }
    
    @Override
    public ResumenAjustes[] consultarResumenIDOOficina(final Integer codigoOcca) {
        ResumenAjustes[] resumen = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            resumen = this.invocacionServicios.consultarResumenIDOOficina();
        }
        catch (SecurityException ex) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el requerimiento:" + ex.getMessage());
        }
        catch (WebServiceException ex2) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al llamar al WS:" + ex2.getMessage());
        }
        catch (Exception ex3) {
            AjustesProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error: " + ex3.getMessage());
        }
        return resumen;
    }
}
