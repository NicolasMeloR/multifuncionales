/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author aagarcia
 */
@Entity
@Table(name = "LOGCAJEROMULTI")
@NamedQueries( {
    @NamedQuery(
    name = "Logcajeromulti.registroUnico",
            query = "select object(txm) from Logcajeromulti txm where txm.logcajeromultiPK.codigocajero = :codigocajero and txm.logcajeromultiPK.secuencia = :secuencia and txm.logcajeromultiPK.fechacargue = :fechacargue"),             
    @NamedQuery(
    name = "Logcajeromulti.todos", 
             query = "select object(txm) from Logcajeromulti txm")
})



public class Logcajeromulti implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LogcajeromultiPK logcajeromultiPK;
    @Column(name = "DATOS", nullable = false)
    private String datos;
    @Column(name = "NOMBREARCHIVO", nullable = false)
    private String nombrearchivo;

    public Logcajeromulti() {
    }

    public Logcajeromulti(LogcajeromultiPK logcajeromultiPK) {
        this.logcajeromultiPK = logcajeromultiPK;
    }

    public Logcajeromulti(LogcajeromultiPK logcajeromultiPK, String datos, String nombrearchivo) {
        this.logcajeromultiPK = logcajeromultiPK;
        this.datos = datos;
        this.nombrearchivo = nombrearchivo;
    }

    public Logcajeromulti(int codigocajero, int secuencia, Date fechacargue) {
        this.logcajeromultiPK = new LogcajeromultiPK(codigocajero, secuencia, fechacargue);
    }

    public LogcajeromultiPK getLogcajeromultiPK() {
        return logcajeromultiPK;
    }

    public void setLogcajeromultiPK(LogcajeromultiPK logcajeromultiPK) {
        this.logcajeromultiPK = logcajeromultiPK;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getNombrearchivo() {
        return nombrearchivo;
    }

    public void setNombrearchivo(String nombrearchivo) {
        this.nombrearchivo = nombrearchivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logcajeromultiPK != null ? logcajeromultiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logcajeromulti)) {
            return false;
        }
        Logcajeromulti other = (Logcajeromulti) object;
        if ((this.logcajeromultiPK == null && other.logcajeromultiPK != null) || (this.logcajeromultiPK != null && !this.logcajeromultiPK.equals(other.logcajeromultiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Logcajeromulti[logcajeromultiPK=" + logcajeromultiPK + "]";
    }

}
