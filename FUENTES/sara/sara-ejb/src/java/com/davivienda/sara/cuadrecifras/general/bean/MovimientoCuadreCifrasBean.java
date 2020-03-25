// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.general.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.math.BigInteger;

public class MovimientoCuadreCifrasBean
{
    public BigInteger id;
    public String fecha;
    public Integer codigoCajero;
    public String nombreCajero;
    public Collection<DatosMovimientoCajeroBean> datosCajero;
    public Collection<DatosMovimientoCajeroBean> datosLinea;
    public Integer codigoOcca;
    
    public MovimientoCuadreCifrasBean() {
        this.datosCajero = new ArrayList<DatosMovimientoCajeroBean>();
        this.datosLinea = new ArrayList<DatosMovimientoCajeroBean>();
    }
    
    public BigInteger getId() {
        return this.id;
    }
    
    public void setId(final BigInteger id) {
        this.id = id;
    }
    
    public String getFecha() {
        return this.fecha;
    }
    
    public void setFecha(final String fecha) {
        this.fecha = fecha;
    }
    
    public Integer getCodigoCajero() {
        return this.codigoCajero;
    }
    
    public void setCodigoCajero(final Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }
    
    public String getNombreCajero() {
        return this.nombreCajero;
    }
    
    public void setNombreCajero(final String nombreCajero) {
        this.nombreCajero = nombreCajero;
    }
    
    public Collection<DatosMovimientoCajeroBean> getDatosCajero() {
        return this.datosCajero;
    }
    
    public void setDatosCajero(final Collection<DatosMovimientoCajeroBean> datosCajero) {
        this.datosCajero = datosCajero;
    }
    
    public Collection<DatosMovimientoCajeroBean> getDatosLinea() {
        return this.datosLinea;
    }
    
    public void setDatosLinea(final Collection<DatosMovimientoCajeroBean> datosLinea) {
        this.datosLinea = datosLinea;
    }
    
    public Integer getCodigoOcca() {
        return this.codigoOcca;
    }
    
    public void setCodigoOcca(final Integer codigoOcca) {
        this.codigoOcca = codigoOcca;
    }
}
