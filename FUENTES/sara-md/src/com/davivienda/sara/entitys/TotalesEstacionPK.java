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
 * TotalesEstacionPK
 * Descripción : 
 * Fecha       : 9/02/2008 02:32:38 PM
 * @author     : jjvargas
 **/
@Embeddable
public class TotalesEstacionPK implements Serializable {
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigoCajero;
    @Column(name = "CODIGOTOTAL", nullable = false)
    private Integer codigoTotal;

    public TotalesEstacionPK() {
    }

    public TotalesEstacionPK(Date fecha, Integer codigocajero, Integer codigototal) {
        this.fecha = fecha;
        this.codigoCajero = codigocajero;
        this.codigoTotal = codigototal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigocajero) {
        this.codigoCajero = codigocajero;
    }

    public Integer getCodigoTotal() {
        return codigoTotal;
    }

    public void setCodigoTotal(Integer codigototal) {
        this.codigoTotal = codigototal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (codigoCajero != null ? codigoCajero.hashCode() : 0);
        hash += (codigoTotal != null ? codigoTotal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TotalesEstacionPK)) {
            return false;
        }
        TotalesEstacionPK other = (TotalesEstacionPK) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if ((this.codigoCajero == null && other.codigoCajero != null) || (this.codigoCajero != null && !this.codigoCajero.equals(other.codigoCajero))) {
            return false;
        }
        if ((this.codigoTotal == null && other.codigoTotal != null) || (this.codigoTotal != null && !this.codigoTotal.equals(other.codigoTotal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.TotalesEstacionPK[fecha=" + fecha + ", codigocajero=" + codigoCajero + ", codigototal=" + codigoTotal + "]";
    }

}
