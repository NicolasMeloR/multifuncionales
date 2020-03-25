/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

/**
 *
 * @author jmcastel
 */
public class NumeroTransaccionesDTO {
    
    private Integer idRegistro;
    private String codigoCajero;
    private String nombreCajero;
    private String fecha;
    private String descripcionTransaccion;
    private String cantidad;

    /**
     * @return the idRegistro
     */
    public Integer getIdRegistro() {
        return idRegistro;
    }

    /**
     * @param idRegistro the idRegistro to set
     */
    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    /**
     * @return the codigoCajero
     */
    public String getCodigoCajero() {
        return codigoCajero;
    }

    /**
     * @param codigoCajero the codigoCajero to set
     */
    public void setCodigoCajero(String codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    /**
     * @return the nombreCajero
     */
    public String getNombreCajero() {
        return nombreCajero;
    }

    /**
     * @param nombreCajero the nombreCajero to set
     */
    public void setNombreCajero(String nombreCajero) {
        this.nombreCajero = nombreCajero;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the descripcionTransaccion
     */
    public String getDescripcionTransaccion() {
        return descripcionTransaccion;
    }

    /**
     * @param descripcionTransaccion the descripcionTransaccion to set
     */
    public void setDescripcionTransaccion(String descripcionTransaccion) {
        this.descripcionTransaccion = descripcionTransaccion;
    }

    /**
     * @return the cantidad
     */
    public String getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
}
