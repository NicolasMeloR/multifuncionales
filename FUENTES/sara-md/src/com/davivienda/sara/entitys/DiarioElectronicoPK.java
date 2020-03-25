package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * DiarioElectronicoPK.java
 * 
 * Fecha : 29/11/2007, 04:00:10 PM
 * 
 * @autor jjvargas
 */
@Embeddable
public class DiarioElectronicoPK implements Serializable {

    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigoCajero;
    
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @Column(name = "SECUENCIA", nullable = false)
    private Integer secuencia;

    public DiarioElectronicoPK() {
    }

    public DiarioElectronicoPK(Integer codigocajero, Integer secuencia, Date fecha) {
        this.codigoCajero = codigocajero;
        this.secuencia = secuencia;
        this.fecha = fecha;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigocajero) {
        this.codigoCajero = codigocajero;
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
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiarioElectronicoPK)) {
            return false;
        }
        DiarioElectronicoPK other = (DiarioElectronicoPK) object;
        if ((this.codigoCajero == null && other.codigoCajero != null) || (this.codigoCajero != null && !this.codigoCajero.equals(other.codigoCajero))) {
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
        return "com.davivienda.sara.entitys.DiarioElectronicoPK[codigocajero=" + codigoCajero + ", secuencia=" + secuencia + ", fecha=" + fecha + "]";
    }

    public Integer getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }
}
