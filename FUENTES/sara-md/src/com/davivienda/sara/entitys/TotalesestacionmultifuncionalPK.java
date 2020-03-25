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
 * @author P-LAPULGAR
 */
@Embeddable
public class TotalesestacionmultifuncionalPK implements Serializable {
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigocajero;
    @Column(name = "CODIGOTOTAL", nullable = false)
    private Integer codigototal;

    public TotalesestacionmultifuncionalPK() {
    }

    public TotalesestacionmultifuncionalPK(Date fecha, Integer codigocajero, Integer codigototal) {
        this.fecha = fecha;
        this.codigocajero = codigocajero;
        this.codigototal = codigototal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCodigocajero() {
        return codigocajero;
    }

    public void setCodigocajero(Integer codigocajero) {
        this.codigocajero = codigocajero;
    }

    public Integer getCodigototal() {
        return codigototal;
    }

    public void setCodigototal(Integer codigototal) {
        this.codigototal = codigototal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (int) codigocajero;
        hash += (int) codigototal;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TotalesestacionmultifuncionalPK)) {
            return false;
        }
        TotalesestacionmultifuncionalPK other = (TotalesestacionmultifuncionalPK) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if (this.codigocajero != other.codigocajero) {
            return false;
        }
        if (this.codigototal != other.codigototal) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.TotalesestacionmultifuncionalPK[fecha=" + fecha + ", codigocajero=" + codigocajero + ", codigototal=" + codigototal + "]";
    }

}