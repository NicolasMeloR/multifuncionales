package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProvisionHost.java
 * 
 * Fecha       :  28/07/2007, 11:28:31 AM
 * Descripción : 
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */
@Entity
@Table(name = "PROVISIONHOST")
@NamedQueries({
    
            @NamedQuery(
            name = "ProvisionHost.RangoFecha",
            query = "select object(obj) from ProvisionHost obj  "
            + "        where obj.provisionHostPK.fecha between :fechaInicial and :fechaFinal   "
            + " order by obj.cajero.codigoCajero"),
            @NamedQuery(
            name = "ProvisionHost.PrimaryKeys",
            query = "SELECT b FROM ProvisionHost b WHERE b.provisionHostPK.fecha = :fecha and b.provisionHostPK.codigoCajero = :codigoCajero "
                    + "and b.provisionHostPK.hora = :hora  and b.provisionHostPK.motivo = :motivo  "
                    + "and b.provisionHostPK.tipo = :tipo  order by b.provisionHostPK.codigoCajero, b.provisionHostPK.fecha")


            })
public class ProvisionHost implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProvisionHostPK provisionHostPK;

    @Column(name = "VALOR", nullable = false)
    private long valor;

    private String usuarioHost;

    private String referencia;
    
    @Temporal(TemporalType.DATE)    
    private Date fechaAplicacion;
    
    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO", insertable = false, updatable = false)
    @ManyToOne
    private Cajero cajero;
    
    
    /**
     * Crea una nueva instancia de <code>ProvisionHost</code>.
     */
    public ProvisionHost() {
    }

    public ProvisionHost(ProvisionHostPK provisionHostPK) {
        this.provisionHostPK = provisionHostPK;
    }

    public ProvisionHost(ProvisionHostPK provisionHostPK, long valor) {
        this.provisionHostPK = provisionHostPK;
        this.valor = valor;
    }

    public ProvisionHost(Integer codigocajero, Date fecha, String hora, short motivo, short tipo) {
        this.provisionHostPK = new ProvisionHostPK(codigocajero, fecha, hora, motivo, tipo);
    }

    public ProvisionHostPK getProvisionHostPK() {
        return provisionHostPK;
    }

    public void setProvisionHostPK(ProvisionHostPK provisionHostPK) {
        this.provisionHostPK = provisionHostPK;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }


    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }

    public String getUsuarioHost() {
        return usuarioHost;
    }

    public void setUsuarioHost(String usuarioHost) {
        this.usuarioHost = usuarioHost;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(Date fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }


    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (provisionHostPK != null ? provisionHostPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProvisionHost)) {
            return false;
        }
        ProvisionHost other = (ProvisionHost) object;
        if ((this.provisionHostPK == null && other.provisionHostPK != null) || (this.provisionHostPK != null && !this.provisionHostPK.equals(other.provisionHostPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ProvisionHost[provisionHostPK=" + provisionHostPK + "]";
    }


}

