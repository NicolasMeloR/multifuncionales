package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ResumenCuadreCifrasPK.java
 * 
 * Fecha       :  16/08/2007, 10:53:24 AM
 * Descripción : 
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */
@Embeddable
public class ResumenCuadreCifrasPK implements Serializable {

    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigoCajero;

    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    /**
     * Crea una nueva instancia de <code>ResumenCuadreCifrasPK</code>.
     */
    public ResumenCuadreCifrasPK() {
    }

    public ResumenCuadreCifrasPK(Integer codigoCajero, Date fecha) {
        this.codigoCajero = codigoCajero;
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
        if (!(object instanceof ResumenCuadreCifrasPK)) {
            return false;
        }
        ResumenCuadreCifrasPK other = (ResumenCuadreCifrasPK) object;
        if (this.codigoCajero != other.codigoCajero && (this.codigoCajero == null || !this.codigoCajero.equals(other.codigoCajero))) {
            return false;
        }
        if (this.fecha != other.fecha && (this.fecha == null || !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ResumenCuadreCifrasPK[codigoCajero=" + codigoCajero + ", fecha=" + fecha + "]";
    }


}

