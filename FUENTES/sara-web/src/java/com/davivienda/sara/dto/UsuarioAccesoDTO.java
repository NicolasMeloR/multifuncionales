/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

/**
 *
 * @author jediazs
 */
public class UsuarioAccesoDTO {
    
    public String consultar;
    public String actualizar;
    public String ejecutar;
    public String administrar;
    public String crear;
    public String borrar;

    public String getConsultar() {
        return consultar;
    }

    public void setConsultar(String consultar) {
        this.consultar = consultar;
    }

    public String getActualizar() {
        return actualizar;
    }

    public void setActualizar(String actualizar) {
        this.actualizar = actualizar;
    }

    public String getEjecutar() {
        return ejecutar;
    }

    public void setEjecutar(String ejecutar) {
        this.ejecutar = ejecutar;
    }

    public String getAdministrar() {
        return administrar;
    }

    public void setAdministrar(String administrar) {
        this.administrar = administrar;
    }

    public String getCrear() {
        return crear;
    }

    public void setCrear(String crear) {
        this.crear = crear;
    }

    public String getBorrar() {
        return borrar;
    }

    public void setBorrar(String borrar) {
        this.borrar = borrar;
    }
    
    
    
}
