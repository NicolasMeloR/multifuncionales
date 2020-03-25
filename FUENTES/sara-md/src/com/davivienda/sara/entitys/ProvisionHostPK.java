package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProvisionHostPK
 * Descripción : 
 * Fecha       : 9/02/2008 09:55:29 PM
 * @author     : jjvargas
 **/
@Embeddable
public class ProvisionHostPK implements Serializable {
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigoCajero;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "HORA", nullable = false)
    private String hora;
    @Column(name = "MOTIVO", nullable = false)
    private short motivo;
    @Column(name = "TIPO", nullable = false)
    private short tipo;

    public ProvisionHostPK() {
    }

    public ProvisionHostPK(Integer codigocajero, Date fecha, String hora, short motivo, short tipo) {
        this.codigoCajero = codigocajero;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.tipo = tipo;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public short getMotivo() {
        return motivo;
    }

    public void setMotivo(short motivo) {
        this.motivo = motivo;
    }

    public short getTipo() {
        return tipo;
    }

    public void setTipo(short tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCajero != null ? codigoCajero.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (hora != null ? hora.hashCode() : 0);
        hash += (int) motivo;
        hash += (int) tipo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvisionHostPK)) {
            return false;
        }
        ProvisionHostPK other = (ProvisionHostPK) object;
        if ((this.codigoCajero == null && other.codigoCajero != null) || (this.codigoCajero != null && !this.codigoCajero.equals(other.codigoCajero))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if ((this.hora == null && other.hora != null) || (this.hora != null && !this.hora.equals(other.hora))) {
            return false;
        }
        if (this.motivo != other.motivo) {
            return false;
        }
        if (this.tipo != other.tipo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ProvisionHostPK[codigocajero=" + codigoCajero + ", fecha=" + fecha + ", hora=" + hora + ", motivo=" + motivo + ", tipo=" + tipo + "]";
    }

}
