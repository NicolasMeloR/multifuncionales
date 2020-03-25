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
 * @author aa.garcia
 */
@Embeddable
public class TransaccionTempPK implements Serializable {
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigocajero;
    @Column(name = "NUMEROTRANSACCION", nullable = false)
    private Integer numerotransaccion;
    @Column(name = "FECHATRANSACCION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechatransaccion;

    public TransaccionTempPK() {
    }

    public TransaccionTempPK(Integer codigocajero, Integer numerotransaccion, Date fechatransaccion) {
        this.codigocajero = codigocajero;
        this.numerotransaccion = numerotransaccion;
        this.fechatransaccion = fechatransaccion;
    }

    public Integer getCodigocajero() {
        return codigocajero;
    }

    public void setCodigocajero(Integer codigocajero) {
        this.codigocajero = codigocajero;
    }

    public Integer getNumerotransaccion() {
        return numerotransaccion;
    }

    public void setNumerotransaccion(Integer numerotransaccion) {
        this.numerotransaccion = numerotransaccion;
    }

    public Date getFechatransaccion() {
        return fechatransaccion;
    }

    public void setFechatransaccion(Date fechatransaccion) {
        this.fechatransaccion = fechatransaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigocajero != null ? codigocajero.hashCode() : 0);
        hash += (numerotransaccion != null ? numerotransaccion.hashCode() : 0);
        hash += (fechatransaccion != null ? fechatransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionTempPK)) {
            return false;
        }
        TransaccionTempPK other = (TransaccionTempPK) object;
        if ((this.codigocajero == null && other.codigocajero != null) || (this.codigocajero != null && !this.codigocajero.equals(other.codigocajero))) {
            return false;
        }
        if ((this.numerotransaccion == null && other.numerotransaccion != null) || (this.numerotransaccion != null && !this.numerotransaccion.equals(other.numerotransaccion))) {
            return false;
        }
        if ((this.fechatransaccion == null && other.fechatransaccion != null) || (this.fechatransaccion != null && !this.fechatransaccion.equals(other.fechatransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.TransaccionTempPK[codigocajero=" + codigocajero + ", numerotransaccion=" + numerotransaccion + ", fechatransaccion=" + fechatransaccion + "]";
    }

}
