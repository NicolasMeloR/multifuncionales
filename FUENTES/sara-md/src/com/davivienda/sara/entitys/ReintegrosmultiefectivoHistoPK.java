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
public class ReintegrosmultiefectivoHistoPK implements Serializable {
    @Column(name = "H_CODIGOCAJERO", nullable = false)
    private Integer hCodigocajero;
    @Column(name = "H_FECHASISTEMA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date hFechasistema;
    @Column(name = "H_TALON", nullable = false)
    private Integer hTalon;

    public ReintegrosmultiefectivoHistoPK() {
    }

    public ReintegrosmultiefectivoHistoPK(Integer hCodigocajero, Date hFechasistema, Integer hTalon) {
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
        hash += (int) hCodigocajero;
        hash += (hFechasistema != null ? hFechasistema.hashCode() : 0);
        hash += (int) hTalon;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReintegrosmultiefectivoHistoPK)) {
            return false;
        }
        ReintegrosmultiefectivoHistoPK other = (ReintegrosmultiefectivoHistoPK) object;
        if (this.hCodigocajero != other.hCodigocajero) {
            return false;
        }
        if ((this.hFechasistema == null && other.hFechasistema != null) || (this.hFechasistema != null && !this.hFechasistema.equals(other.hFechasistema))) {
            return false;
        }
        if (this.hTalon != other.hTalon) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ReintegrosmultiefectivoHistoPK[hCodigocajero=" + hCodigocajero + ", hFechasistema=" + hFechasistema + ", hTalon=" + hTalon + "]";
    }

}
