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
public class ConsultarAjustesDTO {
    
    private Integer idRegistro;
    private String tipoAjuste;
    private String usuario;
    private String codigoOcca;
    private String codigoOficina;
    private String codigoCajero;
    private String fecha;
    private String talon;
    private String valor;
    private String codigoError;
    private String descripcionError;

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
     * @return the tipoAjuste
     */
    public String getTipoAjuste() {
        return tipoAjuste;
    }

    /**
     * @param tipoAjuste the tipoAjuste to set
     */
    public void setTipoAjuste(String tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the codigoOcca
     */
    public String getCodigoOcca() {
        return codigoOcca;
    }

    /**
     * @param codigoOcca the codigoOcca to set
     */
    public void setCodigoOcca(String codigoOcca) {
        this.codigoOcca = codigoOcca;
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
     * @return the talon
     */
    public String getTalon() {
        return talon;
    }

    /**
     * @param talon the talon to set
     */
    public void setTalon(String talon) {
        this.talon = talon;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
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
     * @return the descripcionError
     */
    public String getDescripcionError() {
        return descripcionError;
    }

    /**
     * @param descripcionError the descripcionError to set
     */
    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    public String getCodigoOficina() {
        return codigoOficina;
    }

    public void setCodigoOficina(String codigoOficina) {
        this.codigoOficina = codigoOficina;
    }
    
}
