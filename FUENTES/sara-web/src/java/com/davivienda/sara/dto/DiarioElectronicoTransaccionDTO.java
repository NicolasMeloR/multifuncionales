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
public class DiarioElectronicoTransaccionDTO {
    
    public Integer secuencia;
    
    public String tipoRegistro;
    
    public String datos;

    /**
     * Crea una nueva instancia de <code>DiarioElectronicoBean</code>.
     */
    public DiarioElectronicoTransaccionDTO() {
    }
    
    /**
     * 
     * @return 
     */
    public String getDatos(){
        return this.datos;
    }

    
}
