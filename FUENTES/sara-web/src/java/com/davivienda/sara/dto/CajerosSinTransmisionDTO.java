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
public class CajerosSinTransmisionDTO {
    private Integer idRegistro;
    private String codigoCajero;
    private String nombreCajero;

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
    
    
}
