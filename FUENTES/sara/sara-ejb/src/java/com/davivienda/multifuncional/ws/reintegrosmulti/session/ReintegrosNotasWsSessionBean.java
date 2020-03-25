// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.ws.reintegrosmulti.session;

import javax.xml.datatype.DatatypeConfigurationException;
import com.davivienda.multifuncional.constantes.TipoCuentaMultifuncional;
import javax.xml.ws.WebServiceException;
import java.util.Date;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.sara.constantes.TipoCuenta;
import java.math.BigDecimal;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.davivienda.sara.entitys.Cajero;
import javax.annotation.PostConstruct;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.utilidades.ws.gestor.cliente.InvocacionServicios;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ ReintegrosNotasWsSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class ReintegrosNotasWsSessionBean implements ReintegrosNotasWsSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private CajeroServicio cajeroServicio;
    private OccaServicio occaServicio;
    private InvocacionServicios invocacionServicios;
    String servidor;
    String puerto;
    String canal;
    String concepto;
    private static SaraConfig configApp;
    
    public ReintegrosNotasWsSessionBean() {
        this.servidor = "";
        this.puerto = "";
        this.canal = "";
        this.concepto = "";
    }
    
    @PostConstruct
    public void postConstructor() {
        this.cajeroServicio = new CajeroServicio(this.em);
        this.occaServicio = new OccaServicio(this.em);
        this.servidor = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.SERVIDOR_WS"))).getValor().trim();
        this.puerto = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.PUERTO_SERVIDOR_WS"))).getValor().trim();
        this.canal = "26";
        this.concepto = "0352";
        this.iniciarLog();
    }
    
    private Integer obtenerCodigoOficina(final Integer codigoCajero) {
        Integer codTerminal = 0;
        try {
            final Cajero cajero = this.cajeroServicio.buscar(codigoCajero);
            if (cajero != null) {
                codTerminal = cajero.getOficinaMulti().getCodigooficinamulti();
            }
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("No se pudo obtener el CodigoOcca de el codigoCajero" + ex.getMessage());
        }
        return codTerminal;
    }
    
    private void iniciarLog() {
        if (ReintegrosNotasWsSessionBean.configApp == null) {
            try {
                ReintegrosNotasWsSessionBean.configApp = SaraConfig.obtenerInstancia();
            }
            catch (IllegalAccessException ex) {
                Logger.getLogger(ReintegrosNotasWsSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            catch (Exception ex2) {
                Logger.getLogger(ReintegrosNotasWsSessionBean.class.getName()).log(Level.SEVERE, ex2.getMessage(), ex2);
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
    public String realizarNotaDebito(final Integer codigoCajero, final BigDecimal valor, final String cuenta, final Integer tipoCuenta, final String usuario) {
        String fechaActual = "";
        String talon = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        Integer codigoOficina = 0;
        Date fecha = null;
        TipoCuenta tipoCuentaR = TipoCuenta.CuentaAhorros;
        tipoCuentaR = TipoCuenta.getTipoCuenta(tipoCuenta);
        try {
            codigoOficina = this.obtenerCodigoOficina(codigoCajero);
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalon(codigoCajero);
            respuesta = this.aplicarNotaDebito(codigoCajero, codigoOficina, valor, fechaActual, talon, cuenta, tipoCuentaR, usuario);
            ReintegrosNotasWsSessionBean.configApp.loggerApp.log(Level.FINE, "EN realizarNotaDebito " + tipoCuentaR.nombre + " para codigoCajero : " + codigoCajero.toString() + " por : " + valor.toString() + " Canal: " + this.canal + " la respuesta del webservice es " + respuesta);
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
            ReintegrosNotasWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaDebito " + tipoCuentaR.nombre + " Error: " + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        return respuesta;
    }
    
    public String aplicarNotaDebito(final Integer codigoCajero, final Integer codigoOcca, final BigDecimal valor, final String fechaActual, final String talon, final String cuenta, final TipoCuenta tipoCuenta, final String usuario) {
        String respuesta = "";
        String respuestaDescripcion = "";
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, codigoCajero);
            switch (tipoCuenta) {
                case CuentaAhorros: {
                    respuesta = this.invocacionServicios.realizarNotaDebitoCtaAhorrosESB(codigoCajero, valor, fechaActual, talon, cuenta, usuario, this.concepto, this.canal);
                    break;
                }
                case CuentaCorriente: {
                    respuesta = this.invocacionServicios.realizarNotaDebitoCtaCorrienteESB(codigoCajero, valor, fechaActual, talon, cuenta, usuario, this.concepto, this.canal);
                    break;
                }
            }
        }
        catch (SecurityException ex) {
            respuestaDescripcion = "EN NotaDebito " + tipoCuenta.nombre + " Error al ejecutar el requerimiento:" + ex.getMessage();
            ReintegrosNotasWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaDebito " + tipoCuenta.nombre + "Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaDescripcion = "EN NotaDebito " + tipoCuenta.nombre + "Error al llamar al WS:" + ex2.getMessage();
            ReintegrosNotasWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaDebito " + tipoCuenta.nombre + "Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        return respuesta;
    }
    
    @Override
    public String realizarNotaCredito(final Integer codigoCajero, final BigDecimal valor, final String cuenta, final Integer tipoCuenta, final String usuario, String talon) {
        String fechaActual = "";
        String respuesta = "";
        String respuestaDescripcion = "";
        Integer codigoOficina = 0;
        Date fecha = null;
        TipoCuentaMultifuncional tipoCuentaR = TipoCuentaMultifuncional.CuentaAhorros;
        tipoCuentaR = TipoCuentaMultifuncional.getTipoCuentaMultifuncional(tipoCuenta);
        try {
            codigoOficina = this.obtenerCodigoOficina(codigoCajero);
            fecha = Fecha.getCalendarHoy().getTime();
            fechaActual = Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD);
            talon = this.armarTalonNotaCredito(talon);
            respuesta = this.aplicarNotaCredito(codigoCajero, codigoOficina, valor, fechaActual, talon, cuenta, tipoCuentaR, usuario, this.concepto);
            ReintegrosNotasWsSessionBean.configApp.loggerApp.log(Level.FINE, "EN realizarNotaCredito " + tipoCuentaR.nombre + " para codigoCajero : " + codigoCajero.toString() + " por : " + valor.toString() + " Para la fecha: " + fechaActual.toString() + " la respuesta del webservice es " + respuesta);
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
            ReintegrosNotasWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaCredito " + tipoCuentaR.nombre + " Error: " + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        return respuesta;
    }
    
    public String aplicarNotaCredito(final Integer codigoCajero, final Integer codigoOficina, final BigDecimal valor, final String fechaActual, final String talon, final String cuenta, final TipoCuentaMultifuncional tipoCuenta, final String usuario, final String conceptoNC) {
        String respuesta = "";
        String respuestaDescripcion = "";
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, codigoCajero);
            switch (tipoCuenta) {
                case CuentaAhorros: {
                    respuesta = this.invocacionServicios.realizarNotaCreditoCtaAhorrosESB(codigoCajero, codigoOficina, valor, fechaActual, talon, cuenta, usuario, conceptoNC, this.canal);
                    break;
                }
                case CuentaCorriente: {
                    respuesta = this.invocacionServicios.realizarNotaCreditoCtaCorrienteESB(codigoCajero, codigoOficina, valor, fechaActual, talon, cuenta, usuario, conceptoNC, this.canal);
                    break;
                }
                case CreditoFM: {
                    respuesta = this.invocacionServicios.realizarNotaCreditoFM(codigoCajero, codigoCajero, valor, fechaActual, new Integer(talon), cuenta, usuario);
                    break;
                }
                case TarjetaCredito: {
                    respuesta = this.invocacionServicios.realizarNotaCreditoTarjetaCredito((long)new Long(cuenta), codigoCajero, valor, fechaActual, (long)codigoCajero, (long)codigoOficina);
                    break;
                }
                case CrediExpress: {
                    respuesta = this.invocacionServicios.realizarNotaCreditoFM(codigoCajero, codigoCajero, valor, fechaActual, new Integer(talon), cuenta, usuario);
                    break;
                }
            }
        }
        catch (SecurityException ex) {
            respuestaDescripcion = "EN NotaCredito " + tipoCuenta.nombre + " Error al ejecutar el requerimiento:" + ex.getMessage();
            ReintegrosNotasWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaCredito " + tipoCuenta.nombre + "Error al ejecutar el requerimiento:" + ex.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (WebServiceException ex2) {
            respuestaDescripcion = "EN NotaCredito " + tipoCuenta.nombre + "Error al llamar al WS:" + ex2.getMessage();
            ReintegrosNotasWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaCredito " + tipoCuenta.nombre + "Error al llamar al WS:" + ex2.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        catch (DatatypeConfigurationException ex3) {
            respuestaDescripcion = "EN NotaCredito " + tipoCuenta.nombre + "Error en DatatypeConfigurationException:" + ex3.getMessage();
            ReintegrosNotasWsSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN NotaCreditoCtaAhorros Error al llamar al WS:" + ex3.getMessage());
            respuesta = "F" + respuestaDescripcion;
        }
        return respuesta;
    }
}
