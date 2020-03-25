package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * ResumenCuadreCifras.java
 *
 * Fecha       :  16/08/2007, 10:53:19 AM
 * Descripción :
 *
 * @author     : jjvargas
 * @version    : $Id$
 */
@Entity
@Table(name = "RESUMENCUADRECIFRAS")
@NamedQueries({
            @NamedQuery(
            name = "ResumenCuadreCifras.RegistroUnico",
            query = "select object(obj) from ResumenCuadreCifras obj where obj.resumenCuadreCifrasPK = :resumenCuadreCifrasPK"),
            @NamedQuery(
            name = "ResumenCuadreCifras.Todos",
            query = "select object(obj) from ResumenCuadreCifras obj order by obj.resumenCuadreCifrasPK.codigoCajero, obj.resumenCuadreCifrasPK.fecha"),
            @NamedQuery(
            name = "ResumenCuadreCifras.Cajero",
            query = "select object(obj) from ResumenCuadreCifras obj  where obj.resumenCuadreCifrasPK.codigoCajero = :cajero"),
            @NamedQuery(
            name = "ResumenCuadreCifras.Fecha",
            query = "select object(obj) from ResumenCuadreCifras obj  where obj.resumenCuadreCifrasPK.fecha = :fecha"),
            @NamedQuery(
            name = "ResumenCuadreCifras.CajeroRangoFecha",
            query = "select object(obj) from ResumenCuadreCifras obj  " +
            "        where obj.resumenCuadreCifrasPK.codigoCajero =:codigoCajero and " +
            "               obj.resumenCuadreCifrasPK.fecha between :fechaInicial and :fechaFinal"),
            @NamedQuery(
            name = "ResumenCuadreCifras.RangoFecha",
            query = "select object(obj) from ResumenCuadreCifras obj  " +
            "        where obj.resumenCuadreCifrasPK.fecha between :fechaInicial and :fechaFinal")
            
            
            
})
        
        
        public class ResumenCuadreCifras implements Serializable {
    
    @EmbeddedId
    protected ResumenCuadreCifrasPK resumenCuadreCifrasPK;
    
    @Column(name = "DIASGTE")
    private Long diaSgte;
    
    @Column(name = "DISPENSADO")
    private Long dispensado;
    
    @Column(name = "VALORDIASGTECAJERO")
    private Long valorDiaSgteCajero;
    
    @Column(name = "VALORDIASGTELINEA")
    private Long valorDiaSgteLinea;
    
    @Column(name = "VALORDISPENSADOCAJERO")
    private Long valorDispensadoCajero;
    
    @Column(name = "VALORDESCONTADOLINEA")
    private Long valorDescontadoLinea;
    
    @Column(name = "VALORDONACION")
    private Long valorDonacion;
    
    /**
     * Crea una nueva instancia de <code>ResumenCuadreCifras</code>.
     */
    public ResumenCuadreCifras() {
    }
    
    public ResumenCuadreCifras(ResumenCuadreCifrasPK resumenCuadreCifrasPK) {
        this.resumenCuadreCifrasPK = resumenCuadreCifrasPK;
    }
    
    public ResumenCuadreCifras(Integer codigoCajero, Date fecha) {
        this.resumenCuadreCifrasPK = new ResumenCuadreCifrasPK(codigoCajero, fecha);
    }
    
    public ResumenCuadreCifrasPK getResumenCuadreCifrasPK() {
        return resumenCuadreCifrasPK;
    }
    
    public void setResumenCuadreCifrasPK(ResumenCuadreCifrasPK resumenCuadreCifrasPK) {
        this.resumenCuadreCifrasPK = resumenCuadreCifrasPK;
    }
    
    public Long getDiaSgte() {
        return diaSgte;
    }
    
    public void setDiaSgte(Long diaSgte) {
        this.diaSgte = diaSgte;
    }
    
    public Long getDispensado() {
        return dispensado;
    }
    
    public void setDispensado(Long dispensado) {
        this.dispensado = dispensado;
    }
    
    public Long getValorDiaSgteCajero() {
        return valorDiaSgteCajero;
    }
    
    public void setValorDiaSgteCajero(Long valorDiaSgteCajero) {
        this.valorDiaSgteCajero = valorDiaSgteCajero;
    }
    
    public Long getValorDiaSgteLinea() {
        return valorDiaSgteLinea;
    }
    
    public void setValorDiaSgteLinea(Long valorDiaSgteLinea) {
        this.valorDiaSgteLinea = valorDiaSgteLinea;
    }
    
    public Long getValorDispensadoCajero() {
        return valorDispensadoCajero;
    }
    
    public void setValorDispensadoCajero(Long valorDispensadoCajero) {
        this.valorDispensadoCajero = valorDispensadoCajero;
    }
    
    public Long getValorDescontadoLinea() {
        return valorDescontadoLinea;
    }
    
    public void setValorDescontadoLinea(Long valorDescontadoLinea) {
        this.valorDescontadoLinea = valorDescontadoLinea;
    }
    
    public Long getValorDonacion() {
        return valorDonacion;
    }
    
    public void setValorDonacion(Long valorDonacion) {
        this.valorDonacion = valorDonacion;
    }
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resumenCuadreCifrasPK != null ? resumenCuadreCifrasPK.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResumenCuadreCifras)) {
            return false;
        }
        ResumenCuadreCifras other = (ResumenCuadreCifras) object;
        if (this.resumenCuadreCifrasPK != other.resumenCuadreCifrasPK && (this.resumenCuadreCifrasPK == null || !this.resumenCuadreCifrasPK.equals(other.resumenCuadreCifrasPK))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ResumenCuadreCifras[resumenCuadreCifrasPK=" + resumenCuadreCifrasPK + "]";
    }
    
    
}

