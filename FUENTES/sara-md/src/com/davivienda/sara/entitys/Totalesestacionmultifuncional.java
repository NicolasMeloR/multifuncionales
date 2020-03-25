/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author P-LAPULGAR
 */
@Entity
@Table(name = "TOTALESESTACIONMULTIFUNCIONAL")

@NamedQueries( {
    @NamedQuery(
    name = "Totalesestacionmultifuncional.registroUnico",
            query = "select object(txm) from Totalesestacionmultifuncional txm where txm.totalesestacionmultifuncionalPK.fecha = :fecha and txm.totalesestacionmultifuncionalPK.codigocajero = :codigocajero and txm.totalesestacionmultifuncionalPK.codigototal = :codigototal"),             
    @NamedQuery(
    name = "Totalesestacionmultifuncional.todos", 
             query = "select object(txm) from Totalesestacionmultifuncional txm"),
    @NamedQuery(
    name = "Totalesestacionmultifuncional.Fecha", 
            query = "SELECT b FROM Totalesestacionmultifuncional b WHERE b.totalesestacionmultifuncionalPK.fecha between :fechaInicial and :fechaFinal order by b.totalesestacionmultifuncionalPK.codigocajero"),
    @NamedQuery(
    name = "Totalesestacionmultifuncional.FechaCajero", 
            query = "SELECT b FROM Totalesestacionmultifuncional b WHERE b.totalesestacionmultifuncionalPK.fecha between :fechaInicial and :fechaFinal and b.totalesestacionmultifuncionalPK.codigocajero = :codigoCajero order by b.totalesestacionmultifuncionalPK.codigocajero, b.totalesestacionmultifuncionalPK.fecha")
  
         
             
})




public class Totalesestacionmultifuncional implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TotalesestacionmultifuncionalPK totalesestacionmultifuncionalPK;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "CANAL")
    private Integer canal;
    @Column(name = "CANTIDADEVENTO")
    private Long cantidadevento;
    @Column(name = "VALOREVENTO")
    private Long valorevento;
    @Column(name = "CODIGOOFICINA")
    private Integer codigooficina;
    @Column(name = "VERSION")
    private Integer version;

    public Totalesestacionmultifuncional() {
    }

    public Totalesestacionmultifuncional(TotalesestacionmultifuncionalPK totalesestacionmultifuncionalPK) {
        this.totalesestacionmultifuncionalPK = totalesestacionmultifuncionalPK;
    }

    public Totalesestacionmultifuncional(Date fecha, Integer codigocajero, Integer codigototal) {
        this.totalesestacionmultifuncionalPK = new TotalesestacionmultifuncionalPK(fecha, codigocajero, codigototal);
    }

    public TotalesestacionmultifuncionalPK getTotalesestacionmultifuncionalPK() {
        return totalesestacionmultifuncionalPK;
    }

    public void setTotalesestacionmultifuncionalPK(TotalesestacionmultifuncionalPK totalesestacionmultifuncionalPK) {
        this.totalesestacionmultifuncionalPK = totalesestacionmultifuncionalPK;
    }

    public Integer getCanal() {
        return canal;
    }

    public void setCanal(Integer canal) {
        this.canal = canal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    
    public Integer getCodigooficina() {
        return codigooficina;
    }

    public void setCodigooficina(Integer codigooficina) {
        this.codigooficina = codigooficina;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (totalesestacionmultifuncionalPK != null ? totalesestacionmultifuncionalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Totalesestacionmultifuncional)) {
            return false;
        }
        Totalesestacionmultifuncional other = (Totalesestacionmultifuncional) object;
        if ((this.totalesestacionmultifuncionalPK == null && other.totalesestacionmultifuncionalPK != null) || (this.totalesestacionmultifuncionalPK != null && !this.totalesestacionmultifuncionalPK.equals(other.totalesestacionmultifuncionalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Totalesestacionmultifuncional[totalesestacionmultifuncionalPK=" + totalesestacionmultifuncionalPK + "]";
    }
    
    public Totalesestacionmultifuncional actualizarEntity(Totalesestacionmultifuncional obj) {          
        setTotalesestacionmultifuncionalPK(totalesestacionmultifuncionalPK);    
        setFecha(obj.fecha);    
        setCanal(obj.canal);
        setCantidadevento(obj.cantidadevento);
        setValorevento(obj.valorevento);
        setCodigooficina(obj.codigooficina);
        setVersion(obj.version);        
        return this;
            
    }
}