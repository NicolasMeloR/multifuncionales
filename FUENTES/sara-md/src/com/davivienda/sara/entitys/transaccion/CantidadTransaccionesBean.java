package com.davivienda.sara.entitys.transaccion;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CantidadTransaccionesBean - 1/09/2008
 * Descripción :  Bean para el manejo de estadísticas de transacciones realizadas en el cajero
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class CantidadTransaccionesBean implements Serializable{
    
    Integer codigoCajero;
    String nombreCajero;
    String fecha;
    String tipoTransacion;
    BigDecimal cantidad;

    public CantidadTransaccionesBean() {
    }

    public String getNombreCajero() {
        return nombreCajero;
    }

    public void setNombreCajero(String nombreCajero) {
        this.nombreCajero = nombreCajero;
    }

    
    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoTransacion() {
        return tipoTransacion;
    }

    public void setTipoTransacion(String tipoTransacion) {
        this.tipoTransacion = tipoTransacion;
    }
    
    
    
    

}
