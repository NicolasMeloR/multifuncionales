/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author jediazs
 */
public class EdcCargueDTO {

    public int idRegistro;
    public String codigoCajero;
    public String nombreArchivo;
    public String ciclo;
    public String fechaCargue;
    public String estadoProceso;
    public String descripcionError;
    public String error;
    
    public EdcCargueDTO (){
    }
    
    public EdcCargueDTO entidadDTO(JSONObject item) throws JSONException {
        EdcCargueDTO rep = new EdcCargueDTO();
        rep.setIdRegistro(Integer.parseInt(item.getString("idRegistro")));
        rep.setCodigoCajero(item.getString("codigoCajero"));
        rep.setNombreArchivo(item.getString("nombreArchivo"));
        rep.setFechaCargue(item.getString("fecha"));
        rep.setCiclo(item.getString("ciclo"));
        rep.setEstadoProceso(item.getString("estadoProceso"));
        rep.setError(item.getString("error"));
        
        String strDescripcionError = "";

        try {
            strDescripcionError = item.getString("descripcionError");
        } catch (Exception e) {
            strDescripcionError = "";
        }

        rep.setDescripcionError(strDescripcionError);
        return rep;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(String codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getFechaCargue() {
        return fechaCargue;
    }

    public void setFechaCargue(String fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    public String getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


   

}
