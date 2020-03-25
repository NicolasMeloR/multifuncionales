/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Regional;

/**
 *
 * @author jediazs
 */
public class RegionalDTO {

    public int idRegionalc;
    private Integer idRegional;
    private String regional;

    public void limpiarRegionalDTO() {
        this.idRegionalc = 0;
        this.idRegional = 0;
        this.regional = "";
    }

    public RegionalDTO entidadDTO(Regional item) {
        RegionalDTO respItems = new RegionalDTO();
        respItems.setIdRegional(item.getIdRegional());
        respItems.setRegional(item.getRegional());
        return respItems;
    }

    public Regional entidad() {
        Regional respItems = new Regional();
        respItems.setIdRegional(getIdRegional());
        respItems.setRegional(getRegional());

        return respItems;
    }

    public int getIdRegionalc() {
        return idRegionalc;
    }

    public void setIdRegionalc(int idRegionalc) {
        this.idRegionalc = idRegionalc;
    }

    public Integer getIdRegional() {
        return idRegional;
    }

    public void setIdRegional(Integer idRegional) {
        this.idRegional = idRegional;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    @Override
    public String toString() {
        return "RegionalDTO{" + "idRegionalc=" + idRegionalc + ", idRegional=" + idRegional + ", regional=" + regional + '}';
    }

    
    
}
