/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lpulgari
 */
@Entity
@Table(name = "TIPOMOVIMIENTOCUADREMULTI")

@NamedQueries( {
    @NamedQuery(
    name = "Tipomovimientocuadremulti.registroUnico",
            query = "select object(txm) from Tipomovimientocuadremulti txm where txm.codigotipomovimientocuadre = :codigotipomovimientocuadre"),             
    @NamedQuery(
    name = "Tipomovimientocuadremulti.todos", 
             query = "select object(txm) from Tipomovimientocuadremulti txm where txm.codigotipomovimientocuadre = :codigotipomovimientocuadre")            
})
 

public class Tipomovimientocuadremulti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CODIGOTIPOMOVIMIENTOCUADRE", nullable = false)
    private Long codigotipomovimientocuadre;
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
    @Column(name = "OPERADOR", nullable = false)
    private short operador;
    @Column(name = "TIPODATO", nullable = false)
    private short tipodato;
    @Column(name = "MODOAFECTARCUADRE", nullable = false)
    private short modoafectarcuadre;
    @Column(name = "COLUMNAINFCUADRE", nullable = false)
    private Integer columnainfcuadre;
    @Column(name = "LINEAINFCUADRE", nullable = false)
    private short lineainfcuadre;
    @Column(name = "VERSION")
    private Integer version;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codtipomovimientocuadremuti")
//    private Collection<Movimientocuadremulti> movimientocuadremultiCollection;

    public Tipomovimientocuadremulti() {
    }

    public Tipomovimientocuadremulti(Long codigotipomovimientocuadre) {
        this.codigotipomovimientocuadre = codigotipomovimientocuadre;
    }

    public Tipomovimientocuadremulti(Long codigotipomovimientocuadre, String descripcion, short operador, short tipodato, short modoafectarcuadre, Integer columnainfcuadre, short lineainfcuadre) {
        this.codigotipomovimientocuadre = codigotipomovimientocuadre;
        this.descripcion = descripcion;
        this.operador = operador;
        this.tipodato = tipodato;
        this.modoafectarcuadre = modoafectarcuadre;
        this.columnainfcuadre = columnainfcuadre;
        this.lineainfcuadre = lineainfcuadre;
    }

    public Long getCodigotipomovimientocuadre() {
        return codigotipomovimientocuadre;
    }

    public void setCodigotipomovimientocuadre(Long codigotipomovimientocuadre) {
        this.codigotipomovimientocuadre = codigotipomovimientocuadre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public short getOperador() {
        return operador;
    }

    public void setOperador(short operador) {
        this.operador = operador;
    }

    public short getTipodato() {
        return tipodato;
    }

    public void setTipodato(short tipodato) {
        this.tipodato = tipodato;
    }

    public short getModoafectarcuadre() {
        return modoafectarcuadre;
    }

    public void setModoafectarcuadre(short modoafectarcuadre) {
        this.modoafectarcuadre = modoafectarcuadre;
    }

    
    public short getLineainfcuadre() {
        return lineainfcuadre;
    }

    public void setLineainfcuadre(short lineainfcuadre) {
        this.lineainfcuadre = lineainfcuadre;
    }

    public Integer getColumnainfcuadre() {
        return columnainfcuadre;
    }

    public void setColumnainfcuadre(Integer columnainfcuadre) {
        this.columnainfcuadre = columnainfcuadre;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

//    public Collection<Movimientocuadremulti> getMovimientocuadremultiCollection() {
//        return movimientocuadremultiCollection;
//    }
//
//    public void setMovimientocuadremultiCollection(Collection<Movimientocuadremulti> movimientocuadremultiCollection) {
//        this.movimientocuadremultiCollection = movimientocuadremultiCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigotipomovimientocuadre != null ? codigotipomovimientocuadre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipomovimientocuadremulti)) {
            return false;
        }
        Tipomovimientocuadremulti other = (Tipomovimientocuadremulti) object;
        if ((this.codigotipomovimientocuadre == null && other.codigotipomovimientocuadre != null) || (this.codigotipomovimientocuadre != null && !this.codigotipomovimientocuadre.equals(other.codigotipomovimientocuadre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Tipomovimientocuadremulti[codigotipomovimientocuadre=" + codigotipomovimientocuadre + "]";
    }
    
    public Tipomovimientocuadremulti actualizarEntity(Tipomovimientocuadremulti obj) {       
        
        setCodigotipomovimientocuadre(obj.codigotipomovimientocuadre);
        setDescripcion(obj.descripcion);
        setOperador(obj.operador);
        setTipodato(obj.tipodato);
        setModoafectarcuadre(obj.modoafectarcuadre);
        setColumnainfcuadre(obj.columnainfcuadre);
        setLineainfcuadre(obj.lineainfcuadre);
        setVersion(obj.version);      
        
        return this;
            
    }

}
