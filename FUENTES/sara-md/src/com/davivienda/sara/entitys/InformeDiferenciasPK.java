package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * InformeDiferenciasPK
 * Descripción : 
 * Fecha       : 10/04/2017 12:20:20 PM
 * @author     : jediazs@co.ibm.com
 * @version    : 1.0
 **/
@Embeddable
public class InformeDiferenciasPK implements Serializable {
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigoCajero;
    
    @Column(name = "FECHAREGISTRO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    public InformeDiferenciasPK() {
    }

    public InformeDiferenciasPK(Integer codigocajero, Date fecha) {
        this.codigoCajero = codigocajero;
        this.fechaRegistro = fecha;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCajero != null ? codigoCajero.hashCode() : 0);
        hash += (fechaRegistro != null ? fechaRegistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InformeDiferenciasPK)) {
            return false;
        }
        InformeDiferenciasPK other = (InformeDiferenciasPK) object;
        if ((this.codigoCajero == null && other.codigoCajero != null) || (this.codigoCajero != null && !this.codigoCajero.equals(other.codigoCajero))) {
            return false;
        }
        if ((this.fechaRegistro == null && other.fechaRegistro != null) || (this.fechaRegistro != null && !this.fechaRegistro.equals(other.fechaRegistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InformeDiferenciasPK{" + "codigoCajero=" + codigoCajero + ", fechaRegistro=" + fechaRegistro + '}';
    }

    

}
