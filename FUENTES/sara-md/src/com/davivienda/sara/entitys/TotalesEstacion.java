package com.davivienda.sara.entitys;

import java.io.Serializable;
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
 * TotalesEstacion.java
 * 
 * Fecha       :  1/08/2007, 09:21:46 PM
 * Descripción :  Información de los totales afectados en una estación de cajero en línea
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */
@Entity
@Table(name = "TOTALESESTACION")
@NamedQueries({
            @NamedQuery(
            name = "TotalesEstacion.PrimaryKeys",
            query = "SELECT b FROM TotalesEstacion b WHERE b.totalesEstacionPK.fecha = :fecha and b.totalesEstacionPK.codigoCajero = :codigoCajero "
                    + "and b.totalesEstacionPK.codigoTotal = :codigoTotal order by b.totalesEstacionPK.codigoCajero, b.totalesEstacionPK.fecha")
})
public class TotalesEstacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TotalesEstacionPK totalesEstacionPK;

    private Short canal;

    private Long cantidadevento;

    private Long valorevento;

    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO", insertable = false, updatable = false)
    @ManyToOne
    private Cajero cajero;
    
    /**
     * Crea una nueva instancia de <code>TotalesEstacion</code>.
     */
    public TotalesEstacion() {
    }

    public TotalesEstacion(TotalesEstacionPK totalesEstacionPK) {
        this.totalesEstacionPK = totalesEstacionPK;
    }

    public TotalesEstacion(Date fecha, Integer codigocajero, Integer codigototal) {
        this.totalesEstacionPK = new TotalesEstacionPK(fecha, codigocajero, codigototal);
    }

    public TotalesEstacionPK getTotalesEstacionPK() {
        return totalesEstacionPK;
    }

    public void setTotalesEstacionPK(TotalesEstacionPK totalesEstacionPK) {
        this.totalesEstacionPK = totalesEstacionPK;
    }


    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    
    

    public Short getCanal() {
        return canal;
    }

    public void setCanal(Short canal) {
        this.canal = canal;
    }

    public Long getCantidadevento() {
        return cantidadevento;
    }

    public void setCantidadevento(Long cantidadevento) {
        this.cantidadevento = cantidadevento;
    }

    public Long getValorevento() {
        return valorevento;
    }

    public void setValorevento(Long valorevento) {
        this.valorevento = valorevento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (totalesEstacionPK != null ? totalesEstacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TotalesEstacion)) {
            return false;
        }
        TotalesEstacion other = (TotalesEstacion) object;
        if ((this.totalesEstacionPK == null && other.totalesEstacionPK != null) || (this.totalesEstacionPK != null && !this.totalesEstacionPK.equals(other.totalesEstacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.TotalesEstacion[totalesEstacionPK=" + totalesEstacionPK + "]";
    }


}

