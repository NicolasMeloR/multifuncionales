// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.ws.cuadrecifrasmulti.servicio;

import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustesMulti;
import javax.xml.ws.WebServiceException;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
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
import com.davivienda.multifuncional.tablas.historicoajustesmulti.session.HistoricoAjustesMultiLocal;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.utilidades.ws.gestor.cliente.InvocacionServicios;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ CuadreCifrasMultiWsSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class CuadreCifrasMultiWsSessionBean implements CuadreCifrasMultiWsSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private OccaServicio occaServicio;
    private InvocacionServicios invocacionServicios;
    String servidor;
    String puerto;
    private static SaraConfig configApp;
    @EJB
    private HistoricoAjustesMultiLocal administradorProcesosHistoricoAjustesSessionLocal;
    
    public CuadreCifrasMultiWsSessionBean() {
        this.servidor = "";
        this.puerto = "";
    }
    
    @PostConstruct
    public void postConstructor() {
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
        if (CuadreCifrasMultiWsSessionBean.configApp == null) {
            try {
                CuadreCifrasMultiWsSessionBean.configApp = SaraConfig.obtenerInstancia();
            }
            catch (IllegalAccessException ex) {
                Logger.getLogger(CuadreCifrasMultiWsSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            catch (Exception ex2) {
                Logger.getLogger(CuadreCifrasMultiWsSessionBean.class.getName()).log(Level.SEVERE, ex2.getMessage(), ex2);
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
    
    private void guardarHistoricoAjustes(final String usuario, final Integer codigoCajero, final Integer codigoOficina, final String tipoAjuste, final Date fecha, final BigDecimal valor, final String talon, final String error, final String descripcionError) {
        try {
            this.administradorProcesosHistoricoAjustesSessionLocal.guardarHistoricoAjustes(usuario, codigoCajero, codigoOficina, tipoAjuste, fecha, valor.longValue(), talon, error, descripcionError);
        }
        catch (EntityServicioExcepcion ex) {
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, " Error al guardar datos en la tabla HistoricoAjustes:" + ex.getMessage());
        }
        catch (SecurityException ex2) {
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, " Error al guardar datos en la tabla HistoricoAjustes:" + ex2.getMessage());
        }
    }
    
    @Override
    public String realizarAjustePorSobrante(final String usuario, final Integer codigoCajero, final Integer codigoOficina, final BigDecimal valorAjuste) {
        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, codigoCajero);
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalon(codigoCajero);
            respuesta = this.invocacionServicios.ajustarIngresoESB(codigoCajero, codigoOficina, valorAjuste, fechaActual, talon, "26", (short)new Short("215"), "0");
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.FINE, "DATA EN AjustePorSobrante para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
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
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobrante Error al ejecutar el requerimiento:" + ex.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorSobrante Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobrante Error al llamar al WS:" + ex2.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorSobrante Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (Exception ex3) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobrante Error: " + ex3.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorSobrante Error: " + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOficina, "Ajuste por Sobrante", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public String realizarAjustePorFaltante(final String usuario, final Integer codigoCajero, final Integer codigoOficina, final BigDecimal valorAjuste) {
        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, codigoCajero);
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalon(codigoCajero);
            respuesta = this.invocacionServicios.ajustarEgresoESB(codigoCajero, codigoOficina, valorAjuste, fechaActual, talon, "26", (short)new Short("201"), "0");
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.FINE, "EN AjustePorFaltante para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
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
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltante Error al ejecutar el requerimiento:" + ex.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorFaltante Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltante Error al llamar al WS:" + ex2.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorFaltante Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (Exception ex3) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltante Error: " + ex3.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorFaltante Error: " + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOficina, "Ajuste por Faltante", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public String realizarAjustePorSobranteArqueo(final String usuario, final Integer codigoCajero, final Integer codigoOficina, final BigDecimal valorAjuste, final String nitTransportadora) {
        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, codigoCajero);
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalon(codigoCajero);
            respuesta = this.invocacionServicios.ajustarIngresoESB(codigoCajero, codigoOficina, valorAjuste, fechaActual, talon, "26", (short)new Short("218"), nitTransportadora);
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.FINE, "EN AjustePorSobranteArqueo para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "No se pudo realizar el ajuste por Sobrante Arqueo";
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion += "Ajuste Realizado con Exito");
                }
            }
        }
        catch (SecurityException ex) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobranteArqueo Error al ejecutar el requerimiento:" + ex.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorSobranteArqueo Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobranteArqueo Error al llamar al WS:" + ex2.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorSobranteArqueo Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (Exception ex3) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorSobranteArqueo Error: " + ex3.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorSobranteArqueo Error: " + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOficina, "Ajuste por Sobrante Arqueo", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public String realizarAjustePorFaltanteArqueo(final String usuario, final Integer codigoCajero, final Integer codigoOficina, final BigDecimal valorAjuste, final String nitTransportadora) {
        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Date fecha = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, codigoCajero);
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalon(codigoCajero);
            respuesta = this.invocacionServicios.ajustarEgresoESB(codigoCajero, codigoOficina, valorAjuste, fechaActual, talon, "26", (short)new Short("202"), nitTransportadora);
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.FINE, "EN AjustePorFaltanteArqueo para codigoCajero : " + codigoCajero.toString() + " por : " + valorAjuste.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            respuestaError = respuesta;
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "No se pudo realizar el ajuste por Faltante Arqueo";
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion += "Ajuste Realizado con Exito");
                }
            }
        }
        catch (SecurityException ex) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltanteArqueo Error al ejecutar el requerimiento:" + ex.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorFaltante Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltanteArqueo Error al llamar al WS:" + ex2.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorFaltante Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (Exception ex3) {
            respuestaError = "F";
            respuestaDescripcion = "EN AjustePorFaltanteArqueo Error: " + ex3.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN AjustePorFaltante Error: " + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOficina, "Ajuste por Faltante Arqueo", fecha, valorAjuste, talon, respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public String realizarDisminucionDeposito(final String usuario, final long codigoTransportadora, final Integer codigoCajero, final BigDecimal valorEfectivo, final Integer codigoOficina) {
        String respuesta = "";
        String respuestaDescripcion = "";
        String respuestaError = "";
        Integer talon = 0;
        Date fecha = null;
        fecha = Fecha.getCalendarHoy().getTime();
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, codigoCajero);
            talon = new Integer(this.armarTalon(codigoCajero));
            respuesta = (respuestaError = (respuestaDescripcion = this.invocacionServicios.realizarEnvioTransportadora(codigoTransportadora, valorEfectivo, talon)));
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "NO se pudo Realizar DisminucionDeposito";
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion += "DisminucionDeposito Realizado con Exito");
                }
            }
        }
        catch (SecurityException ex) {
            respuestaError = "F";
            respuestaDescripcion = "EN DisminucionDeposito Error al ejecutar el requerimiento:" + ex.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN DisminucionDeposito Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaError = "F";
            respuestaDescripcion = "EN DisminucionDeposito Error al llamar al WS:" + ex2.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN DisminucionDeposito Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (Exception ex3) {
            respuestaError = "F";
            respuestaDescripcion = "EN DisminucionDeposito Error: " + ex3.getMessage();
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN DisminucionDeposito Error: " + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        finally {
            this.guardarHistoricoAjustes(usuario, codigoCajero, codigoOficina, "Ajuste Disminucion Provision", fecha, valorEfectivo, "000000", respuestaError, respuestaDescripcion);
        }
        return respuesta;
    }
    
    @Override
    public ResumenAjustesMulti[] consultarInformeTotalesMultifuncional(final Integer codigoCajero, final Integer codigoOficina, final Integer tipoConsulta, final Integer indicadorTotales) {
        ResumenAjustesMulti[] resumen = null;
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOficina));
            resumen = this.invocacionServicios.consultarInformeTotalesATM(codigoCajero, tipoConsulta, indicadorTotales);
        }
        catch (SecurityException ex) {
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el requerimiento:" + ex.getMessage());
        }
        catch (WebServiceException ex2) {
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error al llamar al WS:" + ex2.getMessage());
        }
        catch (Exception ex3) {
            CuadreCifrasMultiWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "Error: " + ex3.getMessage());
        }
        return resumen;
    }
}
