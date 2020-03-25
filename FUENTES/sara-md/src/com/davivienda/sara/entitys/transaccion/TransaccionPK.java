package com.davivienda.sara.entitys.transaccion;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TransaccionPK
 * Descripción : 
 * Fecha       : 7/02/2008 11:39:29 PM
 * @author     : jjvargas
 **/
@Embeddable
public class TransaccionPK implements Serializable {
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigoCajero;
    @Column(name = "NUMEROTRANSACCION", nullable = false)
    private Integer numeroTransaccion;
    @Column(name = "FECHATRANSACCION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTransaccion;

    public TransaccionPK() {
    }

    public TransaccionPK(Integer codigocajero, Integer numerotransaccion, Date fechatransaccion) {
        this.codigoCajero = codigocajero;
        this.numeroTransaccion = numerotransaccion;
        this.fechaTransaccion = fechatransaccion;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public Integer getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(Integer numerotransaccion) {
        this.numeroTransaccion = numerotransaccion;
    }

    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechatransaccion) {
        this.fechaTransaccion = fechatransaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCajero != null ? codigoCajero.hashCode() : 0);
        hash += (numeroTransaccion != null ? numeroTransaccion.hashCode() : 0);
        hash += (fechaTransaccion != null ? fechaTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionPK)) {
            return false;
        }
        TransaccionPK other = (TransaccionPK) object;
        if ((this.codigoCajero == null && other.codigoCajero != null) || (this.codigoCajero != null && !this.codigoCajero.equals(other.codigoCajero))) {
            return false;
        }
        if ((this.numeroTransaccion == null && other.numeroTransaccion != null) || (this.numeroTransaccion != null && !this.numeroTransaccion.equals(other.numeroTransaccion))) {
            return false;
        }
        if ((this.fechaTransaccion == null && other.fechaTransaccion != null) || (this.fechaTransaccion != null && !this.fechaTransaccion.equals(other.fechaTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.transaccion.TransaccionPK[codigocajero=" + codigoCajero + ", numerotransaccion=" + numeroTransaccion + ", fechatransaccion=" + fechaTransaccion + "]";
    }

}
