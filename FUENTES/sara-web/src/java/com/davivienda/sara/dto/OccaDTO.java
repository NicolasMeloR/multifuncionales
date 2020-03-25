/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Occa;

/**
 *
 * @author jediazs
 */
public class OccaDTO {

    public int idOcca;
    private Integer codigoOcca;
    private String nombre;
    private String nombreArchivoMovimiento;
    private String UbicacionArchivoMovimiento;
    private Integer codTerminalCaja;

    public void limpiarOccaDTO() {
        this.idOcca = 0;
        this.codigoOcca = 0;
        this.nombre = "";
        this.nombreArchivoMovimiento = "";
        this.UbicacionArchivoMovimiento = "";
        this.codTerminalCaja = 0;
    }

    public OccaDTO entidadDTO(Occa item) {
        OccaDTO respItems = new OccaDTO();
        respItems.setCodigoOcca(item.getCodigoOcca());
        respItems.setNombre(item.getNombre());
        respItems.setNombreArchivoMovimiento(item.getNombreArchivoMovimiento());
        respItems.setUbicacionArchivoMovimiento(item.getUbicacionArchivoMovimiento());
        respItems.setCodTerminalCaja(item.getCodigoTerminal());

        return respItems;
    }

    public Occa entidad() {
        Occa respItems = new Occa();
        respItems.setCodigoOcca(getCodigoOcca());
        respItems.setNombre(getNombre());
        respItems.setNombreArchivoMovimiento(getNombreArchivoMovimiento());
        respItems.setUbicacionArchivoMovimiento(getUbicacionArchivoMovimiento());
        respItems.setCodigoTerminal(getCodTerminalCaja());

        return respItems;
    }

    public int getIdOcca() {
        return idOcca;
    }

    public void setIdOcca(int idOcca) {
        this.idOcca = idOcca;
    }

    public Integer getCodigoOcca() {
        return codigoOcca;
    }

    public void setCodigoOcca(Integer codigoOcca) {
        this.codigoOcca = codigoOcca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreArchivoMovimiento() {
        return nombreArchivoMovimiento;
    }

    public void setNombreArchivoMovimiento(String nombreArchivoMovimiento) {
        this.nombreArchivoMovimiento = nombreArchivoMovimiento;
    }

    public String getUbicacionArchivoMovimiento() {
        return UbicacionArchivoMovimiento;
    }

    public void setUbicacionArchivoMovimiento(String UbicacionArchivoMovimiento) {
        this.UbicacionArchivoMovimiento = UbicacionArchivoMovimiento;
    }

    public Integer getCodTerminalCaja() {
        return codTerminalCaja;
    }

    public void setCodTerminalCaja(Integer codTerminalCaja) {
        this.codTerminalCaja = codTerminalCaja;
    }

    @Override
    public String toString() {
        return "OccaDTO{" + "idOcca=" + idOcca + ", codigoOcca=" + codigoOcca + ", nombre=" + nombre + ", nombreArchivoMovimiento=" + nombreArchivoMovimiento + ", UbicacionArchivoMovimiento=" + UbicacionArchivoMovimiento + ", codTerminalCaja=" + codTerminalCaja + '}';
    }

    
}
