package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TotalesHost.java
 * 
 * Fecha       :  13/07/2007, 04:39:13 PM
 * Descripción :  Entity para almacenar los acumulados de provisiones, egresos y caja de totales en host
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */
@Entity
@Table(name = "TOTALESHOST")
@NamedQueries({})
public class TotalesHost implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdTotalesHost")
    @SequenceGenerator(name = "IdTotalesHost", sequenceName = "IdTotalesHost", allocationSize = 100)
    private Integer idTotalesHost;
   
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO")
    @ManyToOne(cascade = CascadeType.ALL)
    private Cajero cajero;
    
    private Short corteCajero;

    private Long cantidadRetiros;

    private Long valorEntregado;

    private Long valorProvision;

    private Long valorCaja;

    /**
     * Crea una nueva instancia de <code>Totaleshost</code>.
     */
    public TotalesHost() {
    }

    public Integer getIdTotalesHost() {
        return idTotalesHost;
    }

    public void setIdTotalesHost(Integer idTotalesHost) {
        this.idTotalesHost = idTotalesHost;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public Short getCorteCajero() {
        return corteCajero;
    }

    public void setCorteCajero(Short corteCajero) {
        this.corteCajero = corteCajero;
    }

    public Long getCantidadRetiros() {
        return cantidadRetiros;
    }

    public void setCantidadRetiros(Long cantidadRetiros) {
        this.cantidadRetiros = cantidadRetiros;
    }

    public Long getValorEntregado() {
        return valorEntregado;
    }

    public void setValorEntregado(Long valorEntregado) {
        this.valorEntregado = valorEntregado;
    }

    public Long getValorProvision() {
        return valorProvision;
    }

    public void setValorProvision(Long valorProvision) {
        this.valorProvision = valorProvision;
    }

    public Long getValorCaja() {
        return valorCaja;
    }

    public void setValorCaja(Long valorCaja) {
        this.valorCaja = valorCaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTotalesHost != null ? idTotalesHost.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TotalesHost)) {
            return false;
        }
        TotalesHost other = (TotalesHost) object;
        if (this.idTotalesHost != other.idTotalesHost && (this.idTotalesHost == null || !this.idTotalesHost.equals(other.idTotalesHost))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Totaleshost[idtotaleshost=" + idTotalesHost + "]";
    }


}

