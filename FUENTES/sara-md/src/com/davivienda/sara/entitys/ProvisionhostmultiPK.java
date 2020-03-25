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
public class ProvisionhostmultiPK implements Serializable {
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigocajero;
    @Column(name = "MOTIVO", nullable = false)
    private Integer motivo;
    @Column(name = "TIPO", nullable = false)
    private Integer tipo;
    @Column(name = "NUMTALON", nullable = false)
    private String numtalon;

    public ProvisionhostmultiPK() {
    }

    public ProvisionhostmultiPK(Date fecha, Integer codigocajero, Integer motivo, Integer tipo, String numtalon) {
        this.fecha = fecha;
        this.codigocajero = codigocajero;
        this.motivo = motivo;
        this.tipo = tipo;
        this.numtalon = numtalon;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCodigocajero() {
        return codigocajero;
    }

    public void setCodigocajero(Integer codigocajero) {
        this.codigocajero = codigocajero;
    }

    public Integer getMotivo() {
        return motivo;
    }

    public void setMotivo(Integer motivo) {
        this.motivo = motivo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getNumtalon() {
        return numtalon;
    }

    public void setNumtalon(String numtalon) {
        this.numtalon = numtalon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (int) codigocajero;
        hash += (int) motivo;
        hash += (int) tipo;
        hash += (numtalon != null ? numtalon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvisionhostmultiPK)) {
            return false;
        }
        ProvisionhostmultiPK other = (ProvisionhostmultiPK) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if (this.codigocajero != other.codigocajero) {
            return false;
        }
        if (this.motivo != other.motivo) {
            return false;
        }
        if (this.tipo != other.tipo) {
            return false;
        }
        if ((this.numtalon == null && other.numtalon != null) || (this.numtalon != null && !this.numtalon.equals(other.numtalon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ProvisionhostmultiPK[fecha=" + fecha + ", codigocajero=" + codigocajero + ", motivo=" + motivo + ", tipo=" + tipo + ", numtalon=" + numtalon + "]";
    }

}
