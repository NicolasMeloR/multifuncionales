/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author lpulgari
 */
@Entity
@Table(name = "OFICINAMULTI")

@NamedQueries( {
    @NamedQuery(
    name = "Oficinamulti.registroUnico",
            query = "select object(txm) from Oficinamulti txm where txm.codigooficinamulti = :codigooficinamulti"),             
    @NamedQuery(
    name = "Oficinamulti.todos", 
             query = "select object(txm) from Oficinamulti txm"),
       @NamedQuery(
    name = "Oficinamulti.Todos",
            query = "select object(o) from Oficinamulti o order by o.codigooficinamulti")
       
})

public class Oficinamulti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CODIGOOFICINAMULTI", nullable = false)
    private Integer codigooficinamulti;
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @Column(name = "CODIGOCENTROEFECTIVO")
    private Integer codigocentroefectivo;
    @Column(name = "VERSION")
    private Integer version;

    public Oficinamulti() {
    }

    public Oficinamulti(Integer codigooficinamulti) {
        this.codigooficinamulti = codigooficinamulti;
    }

    public Oficinamulti(Integer codigooficinamulti, String nombre) {
        this.codigooficinamulti = codigooficinamulti;
        this.nombre = nombre;
    }

    public Integer getCodigooficinamulti() {
        return codigooficinamulti;
    }

    public void setCodigooficinamulti(Integer codigooficinamulti) {
        this.codigooficinamulti = codigooficinamulti;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCodigocentroefectivo() {
        return codigocentroefectivo;
    }

    public void setCodigocentroefectivo(Integer codigocentroefectivo) {
        this.codigocentroefectivo = codigocentroefectivo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }    
   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigooficinamulti != null ? codigooficinamulti.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oficinamulti)) {
            return false;
        }
        Oficinamulti other = (Oficinamulti) object;
        if ((this.codigooficinamulti == null && other.codigooficinamulti != null) || (this.codigooficinamulti != null && !this.codigooficinamulti.equals(other.codigooficinamulti))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Oficinamulti[codigooficinamulti=" + codigooficinamulti + "]";
    }
    
    public Oficinamulti actualizarEntity(Oficinamulti obj) { 
        setCodigooficinamulti(obj.codigooficinamulti);   
        setNombre(obj.nombre);    
        setCodigocentroefectivo(obj.codigocentroefectivo);
        setVersion(obj.version); 
        return this;
    }

}
