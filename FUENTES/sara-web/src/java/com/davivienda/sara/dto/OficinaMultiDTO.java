/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Oficinamulti;

/**
 *
 * @author jediazs
 */
public class OficinaMultiDTO {

    public int idOficina;
    private Integer codigooficinamulti;
    private String nombre;
    private Integer codigocentroefectivo;
    private Integer version;

    public void limpiarOficinaDTO() {
        this.idOficina = 0;
        this.codigooficinamulti = 0;
        this.nombre = "";
        this.codigocentroefectivo = 0;
        this.version = 0;
    }

    public OficinaMultiDTO entidadDTO(Oficinamulti item) {
        OficinaMultiDTO respItems = new OficinaMultiDTO();
        respItems.setCodigooficinamulti(item.getCodigooficinamulti());
        respItems.setNombre(item.getNombre());
        respItems.setCodigocentroefectivo(item.getCodigocentroefectivo());
        respItems.setVersion(item.getVersion());

        return respItems;
    }

    public Oficinamulti entidad() {
        Oficinamulti respItems = new Oficinamulti();
        respItems.setCodigooficinamulti(getCodigooficinamulti());
        respItems.setNombre(getNombre());
        respItems.setCodigocentroefectivo(getCodigocentroefectivo());
        respItems.setVersion(getVersion());

        return respItems;
    }

    public int getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(int idOficina) {
        this.idOficina = idOficina;
    }

    public Integer getCodigooficinamulti() {
        return codigooficinamulti;
    }

    public void setCodigooficinamulti(Integer codigooficinamulti) {
        this.codigooficinamulti = codigooficinamulti;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCodigocentroefectivo() {
        return codigocentroefectivo;
    }

    public void setCodigocentroefectivo(Integer codigocentroefectivo) {
        this.codigocentroefectivo = codigocentroefectivo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "OficinaMultiDTO{" + "idOficina=" + idOficina + ", codigooficinamulti=" + codigooficinamulti + ", nombre=" + nombre + ", codigocentroefectivo=" + codigocentroefectivo + ", version=" + version + '}';
    }

   

}
