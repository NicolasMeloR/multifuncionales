package com.davivienda.sara.cuadrecifras.general.bean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * MovimientoCuadreCifrasBean.java
 *
 * Fecha       :  17/08/2007, 03:28:43 PM
 * Descripción :
 *
 * @author     : jjvargas
 * @version    : $Id$
 */
public class MovimientoCuadreCifrasBean {
    
    
    public BigInteger id ;
    
    public String fecha;
    
    public Integer codigoCajero ;
    
    public String nombreCajero ;
    
    public Collection<DatosMovimientoCajeroBean> datosCajero ;
    
    public Collection<DatosMovimientoCajeroBean> datosLinea  ;
    
    public Integer codigoOcca ;
        
    
    /**
     * Crea una nueva instancia de <code>MovimientoCuadreCifrasBean</code>.
     */
    public MovimientoCuadreCifrasBean() {
        datosCajero = new ArrayList<DatosMovimientoCajeroBean>();
        datosLinea = new ArrayList<DatosMovimientoCajeroBean>();
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getNombreCajero() {
        return nombreCajero;
    }

    public void setNombreCajero(String nombreCajero) {
        this.nombreCajero = nombreCajero;
    }

    public Collection<DatosMovimientoCajeroBean> getDatosCajero() {
        return datosCajero;
    }

    public void setDatosCajero(Collection<DatosMovimientoCajeroBean> datosCajero) {
        this.datosCajero = datosCajero;
    }

    public Collection<DatosMovimientoCajeroBean> getDatosLinea() {
        return datosLinea;
    }

    public void setDatosLinea(Collection<DatosMovimientoCajeroBean> datosLinea) {
        this.datosLinea = datosLinea;
    }

    public Integer getCodigoOcca() {
        return codigoOcca;
    }

    public void setCodigoOcca(Integer codigoOcca) {
        this.codigoOcca = codigoOcca;
    }


    
    
    
    
    
}

