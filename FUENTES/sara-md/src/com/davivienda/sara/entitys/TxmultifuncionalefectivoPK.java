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
public class TxmultifuncionalefectivoPK implements Serializable {
    @Column(name = "CODIGOTX", nullable = false)
    private int codigotx;
    @Column(name = "CODIGOCAJERO", nullable = false)
    private int codigocajero;
    @Column(name = "FECHACAJERO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacajero;

    public TxmultifuncionalefectivoPK() {
    }

    public TxmultifuncionalefectivoPK(Integer codigotx, Integer codigocajero, Date fechacajero) {
        this.codigotx = codigotx;
        this.codigocajero = codigocajero;
        this.fechacajero = fechacajero;
    }

    public int getCodigotx() {
        return codigotx;
    }

    public void setCodigotx(int codigotx) {
        this.codigotx = codigotx;
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
        hash += (int) codigotx;
        hash += (int) codigocajero;
        hash += (fechacajero != null ? fechacajero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxmultifuncionalefectivoPK)) {
            return false;
        }
        TxmultifuncionalefectivoPK other = (TxmultifuncionalefectivoPK) object;
        if (this.codigotx != other.codigotx) {
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
        return "com.davivienda.sara.entitys.TxmultifuncionalefectivoPK[codigotx=" + codigotx + ", codigocajero=" + codigocajero + ", fechacajero=" + fechacajero + "]";
    }

}
