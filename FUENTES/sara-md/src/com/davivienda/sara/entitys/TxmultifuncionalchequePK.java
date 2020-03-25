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
public class TxmultifuncionalchequePK implements Serializable {
    @Column(name = "CODIGOTRANSACCION", nullable = false)
    private int codigotransaccion;
    @Column(name = "CODIGOCAJERO", nullable = false)
    private int codigocajero;
    @Column(name = "FECHACAJERO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacajero;

    public TxmultifuncionalchequePK() {
    }

    public TxmultifuncionalchequePK(Integer codigotransaccion, Integer codigocajero, Date fechacajero) {
        this.codigotransaccion = codigotransaccion;
        this.codigocajero = codigocajero;
        this.fechacajero = fechacajero;
    }

    public int getCodigotransaccion() {
        return codigotransaccion;
    }

    public void setCodigotransaccion(int codigotransaccion) {
        this.codigotransaccion = codigotransaccion;
    }

    public int getCodigocajero() {
        return codigocajero;
    }

    public void setCodigocajero(int codigocajero) {
        this.codigocajero = codigocajero;
    }

    public Date getFechacajero() {
        return fechacajero;
    }

    public void setFechacajero(Date fechacajero) {
        this.fechacajero = fechacajero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigotransaccion;
        hash += (int) codigocajero;
        hash += (fechacajero != null ? fechacajero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxmultifuncionalchequePK)) {
            return false;
        }
        TxmultifuncionalchequePK other = (TxmultifuncionalchequePK) object;
        if (this.codigotransaccion != other.codigotransaccion) {
            return false;
        }
        if (this.codigocajero != other.codigocajero) {
            return false;
        }
        if ((this.fechacajero == null && other.fechacajero != null) || (this.fechacajero != null && !this.fechacajero.equals(other.fechacajero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.TxmultifuncionalchequePK[codigotransaccion=" + codigotransaccion + ", codigocajero=" + codigocajero + ", fechacajero=" + fechacajero + "]";
    }

}
