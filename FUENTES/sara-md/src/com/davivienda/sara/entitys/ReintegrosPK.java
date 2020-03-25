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
 * @author AA.Garcia
 */
@Embeddable
public class ReintegrosPK implements Serializable {
    @Column(name = "H_CODIGOCAJERO", nullable = false)
    private Integer hCodigocajero;
    @Column(name = "H_FECHASISTEMA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date hFechasistema;
    @Column(name = "H_TALON", nullable = false)
    private Integer hTalon;

    public ReintegrosPK() {
    }

    public ReintegrosPK(Integer hCodigocajero, Date hFechasistema, Integer hTalon) {
        this.hCodigocajero = hCodigocajero;
        this.hFechasistema = hFechasistema;
        this.hTalon = hTalon;
    }

    public Integer getHCodigocajero() {
        return hCodigocajero;
    }

    public void setHCodigocajero(Integer hCodigocajero) {
        this.hCodigocajero = hCodigocajero;
    }

    public Date getHFechasistema() {
        return hFechasistema;
    }

    public void setHFechasistema(Date hFechasistema) {
        this.hFechasistema = hFechasistema;
    }

    public Integer getHTalon() {
        return hTalon;
    }

    public void setHTalon(Integer hTalon) {
        this.hTalon = hTalon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hCodigocajero != null ? hCodigocajero.hashCode() : 0);
        hash += (hFechasistema != null ? hFechasistema.hashCode() : 0);
        hash += (hTalon != null ? hTalon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReintegrosPK)) {
            return false;
        }
        ReintegrosPK other = (ReintegrosPK) object;
        if ((this.hCodigocajero == null && other.hCodigocajero != null) || (this.hCodigocajero != null && !this.hCodigocajero.equals(other.hCodigocajero))) {
            return false;
        }
        if ((this.hFechasistema == null && other.hFechasistema != null) || (this.hFechasistema != null && !this.hFechasistema.equals(other.hFechasistema))) {
            return false;
        }
        if ((this.hTalon == null && other.hTalon != null) || (this.hTalon != null && !this.hTalon.equals(other.hTalon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ReintegrosPK[hCodigocajero=" + hCodigocajero + ", hFechasistema=" + hFechasistema + ", hTalon=" + hTalon + "]";
    }

}
