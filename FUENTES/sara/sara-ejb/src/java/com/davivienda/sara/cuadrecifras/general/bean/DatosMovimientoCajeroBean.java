// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.general.bean;

import java.math.BigInteger;

public class DatosMovimientoCajeroBean
{
    public BigInteger idRegistro;
    public String descripcion;
    public String valor;
    public Integer concepto;
    public String contadores;
    public String observacion;
    public String idUsuarioObservacion;
    public String fecha;
    
    public BigInteger getIdRegistro() {
        return this.idRegistro;
    }
    
    public void setIdRegistro(final BigInteger idRegistro) {
        this.idRegistro = idRegistro;
    }
    
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getValor() {
        return this.valor;
    }
    
    public void setValor(final String valor) {
        this.valor = valor;
    }
    
    public String getContadores() {
        return this.contadores;
    }
    
    public void setContadores(final String contadores) {
        this.contadores = contadores;
    }
    
    public String getObservacion() {
        return this.observacion;
    }
    
    public void setObservacion(final String observacion) {
        this.observacion = observacion;
    }
    
    public String getIdUsuarioObservacion() {
        return this.idUsuarioObservacion;
    }
    
    public void setIdUsuarioObservacion(final String idUsuarioObservacion) {
        this.idUsuarioObservacion = idUsuarioObservacion;
    }
    
    public Integer getConcepto() {
        return this.concepto;
    }
    
    public void setConcepto(final Integer concepto) {
        this.concepto = concepto;
    }
    
    public String getFecha() {
        return this.fecha;
    }
    
    public void setFecha(final String fecha) {
        this.fecha = fecha;
    }
}
