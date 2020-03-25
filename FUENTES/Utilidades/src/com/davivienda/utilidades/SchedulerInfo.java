package com.davivienda.utilidades;

/**
 *
 * @author jediazs
 */

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jediazs
 */
public class SchedulerInfo implements Serializable{
    
    private Date proximaEjecucion;
    private String tipoProceso;
    private String cronExpresion;
    private String directorioUpload;
    private String urlApp;

    public Date getProximaEjecucion() {
        return proximaEjecucion;
    }

    public void setProximaEjecucion(Date proximaEjecucion) {
        this.proximaEjecucion = proximaEjecucion;
    }

    public String getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public String getCronExpresion() {
        return cronExpresion;
    }

    public void setCronExpresion(String cronExpresion) {
        this.cronExpresion = cronExpresion;
    }

    public String getDirectorioUpload() {
        return directorioUpload;
    }

    public void setDirectorioUpload(String directorioUpload) {
        this.directorioUpload = directorioUpload;
    }

    public String getUrlApp() {
        return urlApp;
    }

    public void setUrlApp(String urlApp) {
        this.urlApp = urlApp;
    }

    
}

