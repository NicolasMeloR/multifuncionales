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
public class DiarioelectronicoTempPK implements Serializable {
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigocajero;
    @Column(name = "SECUENCIA", nullable = false)
    private Integer secuencia;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public DiarioelectronicoTempPK() {
    }

    public DiarioelectronicoTempPK(Integer codigocajero, Integer secuencia, Date fecha) {
        this.codigocajero = codigocajero;
        this.secuencia = secuencia;
        this.fecha = fecha;
    }

    public Integer getCodigocajero() {
        return codigocajero;
    }

    public void setCodigocajero(Integer codigocajero) {
        this.codigocajero = codigocajero;
    }

    public Integer getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
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
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiarioelectronicoTempPK)) {
            return false;
        }
        DiarioelectronicoTempPK other = (DiarioelectronicoTempPK) object;
        if ((this.codigocajero == null && other.codigocajero != null) || (this.codigocajero != null && !this.codigocajero.equals(other.codigocajero))) {
            return false;
        }
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.DiarioelectronicoTempPK[codigocajero=" + codigocajero + ", secuencia=" + secuencia + ", fecha=" + fecha + "]";
    }

}
