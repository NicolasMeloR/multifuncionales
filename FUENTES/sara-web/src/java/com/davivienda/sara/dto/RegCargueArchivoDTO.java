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
public class RegCargueArchivoDTO {

    public int idRegistro;
    public String archivoTarea;
    public String fechaArchivo;
    public String tarea;
    public String fechaInicial;
    public String fechaFinal;
    public String tareaAutomatica;
    public String estadoCarga;
    public String numRegistros;
    public String usuario;
    public String descripcionError;

    public RegCargueArchivoDTO() {

    }

    public RegCargueArchivoDTO entidadDTO(JSONObject item) throws JSONException {
        RegCargueArchivoDTO itemDTO = new RegCargueArchivoDTO();

        itemDTO.setIdRegistro(Integer.parseInt(item.getString("idRegistro")));
        itemDTO.setArchivoTarea(item.getString("archivoTarea"));
        itemDTO.setFechaArchivo(item.getString("fechaArchivo"));
        itemDTO.setTarea(item.getString("tarea"));
        itemDTO.setFechaInicial(item.getString("fecha"));
        itemDTO.setFechaFinal(item.getString("fechaFinal"));
        itemDTO.setTareaAutomatica(String.valueOf(item.getString("tareaAutomatica")));
        itemDTO.setEstadoCarga(item.getString("estadoCarga"));
        itemDTO.setNumRegistros(item.getString("numRegistros"));
        itemDTO.setUsuario(item.getString("usuario"));
        itemDTO.setDescripcionError(item.getString("descripcionError"));

        return itemDTO;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getArchivoTarea() {
        return archivoTarea;
    }

    public void setArchivoTarea(String archivoTarea) {
        this.archivoTarea = archivoTarea;
    }

    public String getFechaArchivo() {
        return fechaArchivo;
    }

    public void setFechaArchivo(String fechaArchivo) {
        this.fechaArchivo = fechaArchivo;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

   
    public String getTareaAutomatica() {
        return tareaAutomatica;
    }

    public void setTareaAutomatica(String tareaAutomatica) {
        this.tareaAutomatica = tareaAutomatica;
    }

    public String getEstadoCarga() {
        return estadoCarga;
    }

    public void setEstadoCarga(String estadoCarga) {
        this.estadoCarga = estadoCarga;
    }

    

   

    public String getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(String numRegistros) {
        this.numRegistros = numRegistros;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    @Override
    public String toString() {
        return "RegCargueArchivoDTO{" + "idRegistro=" + idRegistro + ", archivoTarea=" + archivoTarea + ", fechaArchivo=" + fechaArchivo + ", tarea=" + tarea + ", fechaInicial=" + fechaInicial + ", fechaFinal=" + fechaFinal + ", tareaAutomatica=" + tareaAutomatica + ", estadoCarga=" + estadoCarga + ", numRegistros=" + numRegistros + ", usuario=" + usuario + ", descripcionError=" + descripcionError + '}';
    }

    
}
