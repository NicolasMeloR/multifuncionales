package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BilletajeCajeroPK
 * Descripción : 
 * Fecha       : 9/02/2008 09:25:52 AM
 * @author     : jjvargas
 **/
@Embeddable
public class BilletajeCajeroPK implements Serializable {
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigoCajero;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public BilletajeCajeroPK() {
    }

    public BilletajeCajeroPK(Integer codigocajero, Date fecha) {
        this.codigoCajero = codigocajero;
        this.fecha = fecha;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
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
        hash += (codigoCajero != null ? codigoCajero.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BilletajeCajeroPK)) {
            return false;
        }
        BilletajeCajeroPK other = (BilletajeCajeroPK) object;
        if ((this.codigoCajero == null && other.codigoCajero != null) || (this.codigoCajero != null && !this.codigoCajero.equals(other.codigoCajero))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.BilletajeCajeroPK[codigocajero=" + codigoCajero + ", fecha=" + fecha + "]";
    }

}
