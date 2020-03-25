/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Zona;

/**
 *
 * @author jmcastel
 */
public class ZonaDTO {
    
    public int idZonac;
    private Integer idZona;
    private String zona;
    private String descripcionZona;
	
	 public void limpiarZonaDTO() {
        this.idZonac = 0;
        this.idZona = 0;
        this.zona = "";
        this.descripcionZona = "";
    }

    public ZonaDTO entidadDTO(Zona item) {
        ZonaDTO respItems = new ZonaDTO();
        respItems.setIdZona(item.getIdZona());
        respItems.setZona(item.getZona());
        respItems.setDescripcionZona(item.getDescripcionZona());                
        return respItems;
    }

    public Zona entidad() {
        Zona respItems = new Zona();
        respItems.setIdZona(getIdZona());
        respItems.setZona(getZona());
        respItems.setDescripcionZona(getDescripcionZona());  

        return respItems;
    }
    
    public int getIdZonac() {
        return idZonac;
    }

    public void setIdZonac(int idZonac) {
        this.idZonac = idZonac;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDescripcionZona() {
        return descripcionZona;
    }

    public void setDescripcionZona(String descripcionZona) {
        this.descripcionZona = descripcionZona;
    }

    @Override
    public String toString() {
        return "ZonaDTO{" + "idZonac=" + idZonac + ", idZona=" + idZona + ", zona=" + zona + ", descripcionZona=" + descripcionZona + '}';
    }
    
}
