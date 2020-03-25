/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.utilidades.conversion.FormatoFecha;

/**
 *
 * @author jmcastel
 */
public class TransaccionDTO {
    
    private Integer idRegistro;
    private String nombreCajero;
    private String codigoCajero;
    private String numeroSerial;
    private String fecha;
    private String tipoTransaccion;
    private String codigoTransaccion;
    private String descripcionTransaccion;
    private String codigoTerminacion;
    private String codigoError;
    private String cuenta;
    private String referencia;
    private String tarjeta;
    private String valorEntregado;
    private String valorSolicitado;

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
     * @return the numeroSerial
     */
    public String getNumeroSerial() {
        return numeroSerial;
    }

    /**
     * @param numeroSerial the numeroSerial to set
     */
    public void setNumeroSerial(String numeroSerial) {
        this.numeroSerial = numeroSerial;
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
     * @return the tipoTransaccion
     */
    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion the tipoTransaccion to set
     */
    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * @return the codigoTransaccion
     */
    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }

    /**
     * @param codigoTransaccion the codigoTransaccion to set
     */
    public void setCodigoTransaccion(String codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
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
     * @return the codigoTerminacion
     */
    public String getCodigoTerminacion() {
        return codigoTerminacion;
    }

    /**
     * @param codigoTerminacion the codigoTerminacion to set
     */
    public void setCodigoTerminacion(String codigoTerminacion) {
        this.codigoTerminacion = codigoTerminacion;
    }

    /**
     * @return the codigoError
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * @param codigoError the codigoError to set
     */
    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the tarjeta
     */
    public String getTarjeta() {
        return tarjeta;
    }

    /**
     * @param tarjeta the tarjeta to set
     */
    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    /**
     * @return the valorEntregado
     */
    public String getValorEntregado() {
        return valorEntregado;
    }

    /**
     * @param valorEntregado the valorEntregado to set
     */
    public void setValorEntregado(String valorEntregado) {
        this.valorEntregado = valorEntregado;
    }

    /**
     * @return the valorSolicitado
     */
    public String getValorSolicitado() {
        return valorSolicitado;
    }

    /**
     * @param valorSolicitado the valorSolicitado to set
     */
    public void setValorSolicitado(String valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }
}
