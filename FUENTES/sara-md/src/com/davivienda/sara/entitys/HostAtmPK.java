/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aa.garcia
 */
@Embeddable
public class HostAtmPK implements Serializable {
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigocajero;
    @Column(name = "FECHASISTEMA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasistema;
    @Column(name = "TALON", nullable = false)
    private Integer talon;

    public HostAtmPK() {
    }

    public HostAtmPK(Integer codigocajero, Date fechasistema, Integer talon) {
        this.codigocajero = codigocajero;
        this.fechasistema = fechasistema;
        this.talon = talon;
    }

    public Integer getCodigoCajero() {
        return codigocajero;
    }

    public void setCodigoCajero(Integer codigocajero) {
        this.codigocajero = codigocajero;
    }

    public Date getFechaSistema() {
        return fechasistema;
    }

    public void setFechaSistema(Date fechasistema) {
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
        hash += (codigocajero != null ? codigocajero.hashCode() : 0);
        hash += (fechasistema != null ? fechasistema.hashCode() : 0);
        hash += (talon != null ? talon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HostAtmPK)) {
            return false;
        }
        HostAtmPK other = (HostAtmPK) object;
        if ((this.codigocajero == null && other.codigocajero != null) || (this.codigocajero != null && !this.codigocajero.equals(other.codigocajero))) {
            return false;
        }
        if ((this.fechasistema == null && other.fechasistema != null) || (this.fechasistema != null && !this.fechasistema.equals(other.fechasistema))) {
            return false;
        }
        if ((this.talon == null && other.talon != null) || (this.talon != null && !this.talon.equals(other.talon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //OJO REVISAR SI EN HostatmPK la a es minsucula
        return "com.davivienda.sara.entitys.HostAtmPK[codigocajero=" + codigocajero + ", fechasistema=" + fechasistema + ", talon=" + talon + "]";
    }

}
