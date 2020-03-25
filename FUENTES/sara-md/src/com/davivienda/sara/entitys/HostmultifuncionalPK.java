/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lpulgari
 */
@Embeddable
public class HostmultifuncionalPK implements Serializable {
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigocajero;
    @Column(name = "FECHASISTEMA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasistema;
    @Column(name = "TALON", nullable = false)
    private Integer talon;

    public HostmultifuncionalPK() {
    }

    public HostmultifuncionalPK(Integer codigocajero, Date fechasistema, Integer talon) {
        this.codigocajero = codigocajero;
        this.fechasistema = fechasistema;
        this.talon = talon;
    }

    public Integer getCodigocajero() {
        return codigocajero;
    }

    public void setCodigocajero(Integer codigocajero) {
        this.codigocajero = codigocajero;
    }

    public Date getFechasistema() {
        return fechasistema;
    }

    public void setFechasistema(Date fechasistema) {
        this.fechasistema = fechasistema;
    }

    public Integer getTalon() {
        return talon;
    }

    public void setTalon(Integer talon) {
        this.talon = talon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigocajero;
        hash += (fechasistema != null ? fechasistema.hashCode() : 0);
        hash += (int) talon;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HostmultifuncionalPK)) {
            return false;
        }
        HostmultifuncionalPK other = (HostmultifuncionalPK) object;
        if (this.codigocajero != other.codigocajero) {
            return false;
        }
        if ((this.fechasistema == null && other.fechasistema != null) || (this.fechasistema != null && !this.fechasistema.equals(other.fechasistema))) {
            return false;
        }
        if (this.talon != other.talon) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.HostmultifuncionalPK[codigocajero=" + codigocajero + ", fechasistema=" + fechasistema + ", talon=" + talon + "]";
    }

}
