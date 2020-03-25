// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.reintegros.notas.session;

import javax.xml.ws.WebServiceException;
import java.util.Date;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.constantes.TipoCuenta;
import java.math.BigDecimal;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;
import com.davivienda.sara.entitys.Cajero;
import java.util.logging.Logger;
import com.davivienda.sara.entitys.Occa;
import javax.annotation.PostConstruct;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
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
@Local({ ReintegrosNotasProcesosSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class ReintegrosNotasProcesosSessionBean implements ReintegrosNotasProcesosSessionLocal
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
    String concepto;
    String canal;
    private static SaraConfig configApp;
    
    public ReintegrosNotasProcesosSessionBean() {
        this.servidor = "";
        this.puerto = "";
        this.concepto = "";
        this.canal = "";
    }
    
    @PostConstruct
    public void postConstructor() {
        this.cajeroServicio = new CajeroServicio(this.em);
        this.occaServicio = new OccaServicio(this.em);
        this.servidor = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.SERVIDOR_WS"))).getValor().trim();
        this.puerto = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.PUERTO_SERVIDOR_WS"))).getValor().trim();
        this.iniciarLog();
        this.concepto = "0016";
        this.canal = "0";
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
    
    private Integer obtenerCodigoOcca(final Integer codigoCajero) {
        Integer codTerminal = 0;
        try {
            final Cajero cajero = this.cajeroServicio.buscar(codigoCajero);
            if (cajero != null) {
                codTerminal = cajero.getOcca().getCodigoOcca();
            }
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("No se pudo obtener el CodigoOcca de el codigoCajero" + ex.getMessage());
        }
        return codTerminal;
    }
    
    private void iniciarLog() {
        if (ReintegrosNotasProcesosSessionBean.configApp == null) {
            try {
                ReintegrosNotasProcesosSessionBean.configApp = SaraConfig.obtenerInstancia();
            }
            catch (IllegalAccessException ex) {
                Logger.getLogger(ReintegrosNotasProcesosSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            catch (Exception ex2) {
                Logger.getLogger(ReintegrosNotasProcesosSessionBean.class.getName()).log(Level.SEVERE, ex2.getMessage(), ex2);
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
    
    private String armarTalonNotaCredito(String talon) {
        for (int i = 0; talon.length() < 6; talon = "0" + talon, ++i) {}
        return talon;
    }
    
    @Override
    public String realizarNotaDebito(final Integer codigoCajero, final BigDecimal valor, final String cuenta, final Integer tipoCuenta, final String usuario, String concepto) {
        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        Integer codigoOcca = 0;
        Date fecha = null;
        TipoCuenta tipoCuentaR = TipoCuenta.CuentaAhorros;
        tipoCuentaR = TipoCuenta.getTipoCuenta(tipoCuenta);
        try {
            codigoOcca = this.obtenerCodigoOcca(codigoCajero);
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalon(codigoCajero);
            respuesta = this.aplicarNotaDebito(codigoCajero, codigoOcca, valor, fechaActual, talon, cuenta, tipoCuentaR, usuario, concepto);
            ReintegrosNotasProcesosSessionBean.configApp.loggerApp.log(Level.FINE, "EN realizarNotaDebito " + tipoCuentaR.nombre + " para codigoCajero : " + codigoCajero.toString() + " por : " + valor.toString() + " Canal: " + this.canal + " la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "NO se pudo Realizar la NotaDebito " + tipoCuentaR.nombre;
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion = respuestaDescripcion + "NotaDebito " + tipoCuentaR.nombre + "Realizado con Exito");
                }
            }
        }
        catch (Exception ex) {
            respuestaDescripcion = "EN NotaDebito " + tipoCuentaR.nombre + "Error: " + ex.getMessage();
            ReintegrosNotasProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaDebito " + tipoCuentaR.nombre + " Error: " + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        return respuesta;
    }
    
    public String aplicarNotaDebito(final Integer codigoCajero, final Integer codigoOcca, final BigDecimal valor, final String fechaActual, final String talon, final String cuenta, final TipoCuenta tipoCuenta, final String usuario,String concepto) {
        String respuesta = "";
        String respuestaDescripcion = "";
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, codigoCajero);
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            switch (tipoCuenta) {
                case CuentaAhorros: {
                    respuesta = this.invocacionServicios.realizarNotaDebitoCtaAhorrosESB(codigoCajero, valor, fechaActual, talon, cuenta, usuario, concepto, this.canal);
                    break;
                }
                case CuentaCorriente: {
                    respuesta = this.invocacionServicios.realizarNotaDebitoCtaCorrienteESB(codigoCajero, valor, fechaActual, talon, cuenta, usuario, concepto, this.canal);
                    break;
                }
            }
        }
        catch (SecurityException ex) {
            respuestaDescripcion = "EN NotaDebito " + tipoCuenta.nombre + " Error al ejecutar el requerimiento:" + ex.getMessage();
            ReintegrosNotasProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaDebito " + tipoCuenta.nombre + "Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaDescripcion = "EN NotaDebito " + tipoCuenta.nombre + "Error al llamar al WS:" + ex2.getMessage();
            ReintegrosNotasProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaDebito " + tipoCuenta.nombre + "Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        return respuesta;
    }
    
    @Override
    public String realizarNotaCredito(final Integer codigoCajero, final BigDecimal valor, final String cuenta, final Integer tipoCuenta, final String usuario, String talon) {
        String fechaActual = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        Integer codigoOcca = 0;
        Date fecha = null;
        TipoCuenta tipoCuentaR = TipoCuenta.CuentaAhorros;
        tipoCuentaR = TipoCuenta.getTipoCuenta(tipoCuenta);
        try {
            codigoOcca = this.obtenerCodigoOcca(codigoCajero);
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalonNotaCredito(talon);
            respuesta = this.aplicarNotaCredito(codigoCajero, codigoOcca, valor, fechaActual, talon, cuenta, tipoCuentaR, usuario);
            ReintegrosNotasProcesosSessionBean.configApp.loggerApp.log(Level.FINE, "EN realizarNotaCredito " + tipoCuentaR.nombre + " para codigoCajero : " + codigoCajero.toString() + " por : " + valor.toString() + "la respuesta del webservice es " + respuesta);
            respuestaDescripcion = respuesta;
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "NO se pudo Realizar la NotaCredito " + tipoCuentaR.nombre;
                }
                else if (respuesta.substring(0, 1).equals("B")) {
                    respuestaDescripcion = (respuestaDescripcion = respuestaDescripcion + "NotaCredito " + tipoCuentaR.nombre + "Realizado con Exito");
                }
            }
        }
        catch (Exception ex) {
            respuestaDescripcion = "EN NotaCredito " + tipoCuentaR.nombre + "Error: " + ex.getMessage();
            ReintegrosNotasProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaCredito " + tipoCuentaR.nombre + " Error: " + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        return respuesta;
    }
    
    public String aplicarNotaCredito(final Integer codigoCajero, final Integer codigoOcca, final BigDecimal valor, final String fechaActual, final String talon, final String cuenta, final TipoCuenta tipoCuenta, final String usuario) {
        String respuesta = "";
        String respuestaDescripcion = "";
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, this.obtenerCodigoTerminal(codigoOcca));
            switch (tipoCuenta) {
                case CuentaAhorros: {
                    respuesta = this.invocacionServicios.realizarNotaCreditoCtaAhorrosESB(codigoCajero, codigoOcca, valor, fechaActual, talon, cuenta, usuario, this.concepto, this.canal);
                    break;
                }
                case CuentaCorriente: {
                    respuesta = this.invocacionServicios.realizarNotaCreditoCtaCorrienteESB(codigoCajero, codigoOcca, valor, fechaActual, talon, cuenta, usuario, this.concepto, this.canal);
                    break;
                }
            }
        }
        catch (SecurityException ex) {
            respuestaDescripcion = "EN NotaCredito " + tipoCuenta.nombre + " Error al ejecutar el requerimiento:" + ex.getMessage();
            ReintegrosNotasProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaCredito " + tipoCuenta.nombre + "Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaDescripcion = "EN NotaCredito " + tipoCuenta.nombre + "Error al llamar al WS:" + ex2.getMessage();
            ReintegrosNotasProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaCredito " + tipoCuenta.nombre + "Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        return respuesta;
    }
    
    public String aplicarNotaCredito(Integer codigoCajero, Integer codigoOcca, BigDecimal valor, String fechaActual, String talon, String cuenta, TipoCuenta tipoCuenta, String usuario, String concepto) {
        String respuesta = "";
        String respuestaDescripcion = "";
        try {
            invocacionServicios = new InvocacionServicios(servidor, puerto, obtenerCodigoTerminal(codigoOcca));
            switch (tipoCuenta) {
                case CuentaAhorros:
                    respuesta = invocacionServicios.realizarNotaCreditoCtaAhorrosESB(codigoCajero, codigoOcca, valor, fechaActual, talon, cuenta, usuario, concepto, canal);
                    break;
                case CuentaCorriente:
                    respuesta = invocacionServicios.realizarNotaCreditoCtaCorrienteESB(codigoCajero, codigoOcca, valor, fechaActual, talon, cuenta, usuario, concepto, canal);
                    break;
                default:
                    break;
            }
        } catch (SecurityException ex) {
            respuestaDescripcion = "EN NotaCredito " + tipoCuenta.nombre + " Error al ejecutar el requerimiento:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN NotaCredito " + tipoCuenta.nombre + "Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        } catch (WebServiceException ex) {
            respuestaDescripcion = "EN NotaCredito " + tipoCuenta.nombre + "Error al llamar al WS:" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN NotaCredito " + tipoCuenta.nombre + "Error al llamar al WS:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        return respuesta;
    }

    public String realizarNotaCredito(Integer codigoCajero, BigDecimal valor, String cuenta, Integer tipoCuenta, String usuario, String talon, String concepto) {
        String fechaActual = "";
        // String talon="";

        String respuesta = "";
        String respuestaDescripcion = "";
        //         String respuestaError="";
        Integer codigoOcca = 0;  //obtener valor de occa
        Date fecha = null;
        TipoCuenta tipoCuentaR = TipoCuenta.CuentaAhorros;
        tipoCuentaR = TipoCuenta.getTipoCuenta(tipoCuenta);

        try {

            codigoOcca = obtenerCodigoOcca(codigoCajero);

            fecha = com.davivienda.utilidades.conversion.Fecha.getCalendarHoy().getTime();
            fechaActual = com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
            // talon=armarTalon(codigoCajero);
            //REVISO QUE EL TALON SEA DE 4 CARACTERES    
//            if(talon.length()<4)
//            {
            //arma talon de 6 caracteres 
            talon = armarTalonNotaCredito(talon);
//            }
            respuesta = aplicarNotaCredito(codigoCajero, codigoOcca, valor, fechaActual, talon, cuenta, tipoCuentaR, usuario, concepto);
            configApp.loggerApp.log(java.util.logging.Level.FINE, "EN realizarNotaCredito " + tipoCuentaR.nombre + " para codigoCajero : " + codigoCajero.toString() + " por : " + valor.toString() + "la respuesta del webservice es " + respuesta);

            respuestaDescripcion = respuesta;
            //respuestaError= respuesta;
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("M")) {
                        respuestaDescripcion = "NO se pudo Realizar la NotaCredito " + tipoCuentaR.nombre;
                    } else if (respuesta.substring(0, 1).equals("B")) {
                        respuestaDescripcion = respuestaDescripcion += "NotaCredito " + tipoCuentaR.nombre + "Realizado con Exito";
                    }
                }
            }

        } catch (Exception ex) {

            respuestaDescripcion = "EN NotaCredito " + tipoCuentaR.nombre + "Error: " + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN NotaCredito " + tipoCuentaR.nombre + " Error: " + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;

        }

//
//	 finally
//         {
//         
//        	guardarHistoricoAjustes(usuario,codigoCajero,codigoOcca,"Ajuste Ingreso",fecha,valorAjuste,talon,respuestaError,respuestaDescripcion);
//         
//         }
        return respuesta;

    }
}
