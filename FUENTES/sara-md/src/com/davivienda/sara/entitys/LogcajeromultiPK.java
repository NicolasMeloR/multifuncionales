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
 * @author aagarcia
 */
@Embeddable
public class LogcajeromultiPK implements Serializable {
    @Column(name = "CODIGOCAJERO", nullable = false)
    private int codigocajero;
    @Column(name = "SECUENCIA", nullable = false)
    private int secuencia;
    @Column(name = "FECHACARGUE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacargue;

    public LogcajeromultiPK() {
    }

    public LogcajeromultiPK(int codigocajero, int secuencia, Date fechacargue) {
        this.codigocajero = codigocajero;
        this.secuencia = secuencia;
        this.fechacargue = fechacargue;
    }

    public int getCodigocajero() {
        return codigocajero;
    }

    public void setCodigocajero(int codigocajero) {
        this.codigocajero = codigocajero;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechacargue() {
        return fechacargue;
    }

    public void setFechacargue(Date fechacargue) {
        this.fechacargue = fechacargue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigocajero;
        hash += (int) secuencia;
        hash += (fechacargue != null ? fechacargue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogcajeromultiPK)) {
            return false;
        }
        LogcajeromultiPK other = (LogcajeromultiPK) object;
        if (this.codigocajero != other.codigocajero) {
            return false;
        }
        if (this.secuencia != other.secuencia) {
            return false;
        }
        if ((this.fechacargue == null && other.fechacargue != null) || (this.fechacargue != null && !this.fechacargue.equals(other.fechacargue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.LogcajeromultiPK[codigocajero=" + codigocajero + ", secuencia=" + secuencia + ", fechacargue=" + fechacargue + "]";
    }

}
