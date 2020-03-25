/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import java.util.Objects;

/**
 *
 * @author jediazs
 */
public class ConfAccesoAplicacionDTO {
    
    private boolean consultar;
    private boolean actualizar;
    private boolean ejecutar;
    private boolean administrar;
    private boolean crear;
    private boolean borrar;
    private String servicioAplicacion;
    private long codigoServicioAplicacion;
    private String usuarioAplicacion;
    private Long version;
    
    /**
     * @return the consultar
     */
    public boolean isConsultar() {
        return consultar;
    }

    /**
     * @param consultar the consultar to set
     */
    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    /**
     * @return the actualizar
     */
    public boolean isActualizar() {
        return actualizar;
    }

    /**
     * @param actualizar the actualizar to set
     */
    public void setActualizar(boolean actualizar) {
        this.actualizar = actualizar;
    }

    /**
     * @return the ejecutar
     */
    public boolean isEjecutar() {
        return ejecutar;
    }

    /**
     * @param ejecutar the ejecutar to set
     */
    public void setEjecutar(boolean ejecutar) {
        this.ejecutar = ejecutar;
    }

    /**
     * @return the administrar
     */
    public boolean isAdministrar() {
        return administrar;
    }

    /**
     * @param administrar the administrar to set
     */
    public void setAdministrar(boolean administrar) {
        this.administrar = administrar;
    }

    /**
     * @return the crear
     */
    public boolean isCrear() {
        return crear;
    }

    /**
     * @param crear the crear to set
     */
    public void setCrear(boolean crear) {
        this.crear = crear;
    }

    /**
     * @return the borrar
     */
    public boolean isBorrar() {
        return borrar;
    }

    /**
     * @param borrar the borrar to set
     */
    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }

    /**
     * @return the servicioAplicacion
     */
    public String getServicioAplicacion() {
        return servicioAplicacion;
    }

    /**
     * @param servicioAplicacion the servicioAplicacion to set
     */
    public void setServicioAplicacion(String servicioAplicacion) {
        this.servicioAplicacion = servicioAplicacion;
    }

    /**
     * @return the usuarioAplicacion
     */
    public String getUsuarioAplicacion() {
        return usuarioAplicacion;
    }

    /**
     * @param usuarioAplicacion the usuarioAplicacion to set
     */
    public void setUsuarioAplicacion(String usuarioAplicacion) {
        this.usuarioAplicacion = usuarioAplicacion;
    }

    /**
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return the codigoServicioAplicacion
     */
    public long getCodigoServicioAplicacion() {
        return codigoServicioAplicacion;
    }

    /**
     * @param codigoServicioAplicacion the codigoServicioAplicacion to set
     */
    public void setCodigoServicioAplicacion(long codigoServicioAplicacion) {
        this.codigoServicioAplicacion = codigoServicioAplicacion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.servicioAplicacion);
        hash = 73 * hash + (int) (this.codigoServicioAplicacion ^ (this.codigoServicioAplicacion >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfAccesoAplicacionDTO other = (ConfAccesoAplicacionDTO) obj;
        if (this.codigoServicioAplicacion != other.codigoServicioAplicacion) {
            return false;
        }
        if (!Objects.equals(this.servicioAplicacion, other.servicioAplicacion)) {
            return false;
        }
        return true;
    }

}
