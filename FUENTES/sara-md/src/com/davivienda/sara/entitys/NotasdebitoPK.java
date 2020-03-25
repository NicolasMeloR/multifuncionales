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
public class NotasdebitoPK implements Serializable {

    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigocajero;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public NotasdebitoPK() {
    }

    public NotasdebitoPK(Integer codigocajero, Date fecha) {
        this.codigocajero = codigocajero;
        this.fecha = fecha;
    }

    public Integer getCodigocajero() {
        return codigocajero;
    }

    public void setCodigocajero(Integer codigocajero) {
        this.codigocajero = codigocajero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigocajero != null ? codigocajero.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotasdebitoPK)) {
            return false;
        }
        NotasdebitoPK other = (NotasdebitoPK) object;
        if ((this.codigocajero == null && other.codigocajero != null) || (this.codigocajero != null && !this.codigocajero.equals(other.codigocajero))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{codigocajero=" + codigocajero + ", fecha=" + fecha + '}';
    }

}
