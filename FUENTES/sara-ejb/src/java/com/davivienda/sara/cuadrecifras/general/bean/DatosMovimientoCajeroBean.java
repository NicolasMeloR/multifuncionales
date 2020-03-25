package com.davivienda.sara.cuadrecifras.general.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * DatosMovimientoCajeroBean.java
 * 
 * Fecha       :  20/08/2007, 02:15:43 PM
 * Descripcion : 
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */
public class DatosMovimientoCajeroBean {
    
    public BigInteger idRegistro ;
    
    public String descripcion ;
    
    public String valor;
    
    public Integer concepto;
    
    public String contadores;
     
    public String observacion;
    
    public String idUsuarioObservacion ;
    
       
   //NUEVO ALVARO
    public String fecha;

    /**
     * Crea una nueva instancia de <code>DatosMovimientoCajeroBean</code>.
     */
    public DatosMovimientoCajeroBean() {
    }

    public BigInteger getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(BigInteger idRegistro) {
        this.idRegistro = idRegistro;
    }
    
    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getContadores() {
        return contadores;
    }

    public void setContadores(String contadores) {
        this.contadores = contadores;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getIdUsuarioObservacion() {
        return idUsuarioObservacion;
    }

    public void setIdUsuarioObservacion(String idUsuarioObservacion) {
        this.idUsuarioObservacion = idUsuarioObservacion;
    }

    public Integer getConcepto() {
        return concepto;
    }

    public void setConcepto(Integer concepto) {
        this.concepto = concepto;
    }
    
    
     public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    


}

