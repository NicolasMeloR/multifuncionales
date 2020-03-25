package com.davivienda.sara.entitys;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Zona
 * Descripción : Zonas de ubicación
 * Fecha       : 18/02/2008 05:52:16 PM
 * @author     : jjvargas
 **/
@Entity
@Table(name = "ZONA")
@NamedQueries( {
    @NamedQuery(
    name = "Zona.RegistroUnico",
            query = "select object(o) from Zona o where o.idZona = :idZona"),
   @NamedQuery(
    name = "Zona.todos",   query = "select object(o) from Zona o order by o.idZona")
})

public class Zona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_ZONA", nullable = false)
    private Integer idZona;
    @Column(name = "ZONA", nullable = false)
    private String zona;
    @Column(name = "DESCRIPCION_ZONA", nullable = false)
    private String descripcionZona;

    public Zona() {
    }

    public Zona(Integer idZona) {
        this.idZona = idZona;
    }

    public Zona(Integer idZona, String zona, String descripcionZona) {
        this.idZona = idZona;
        this.zona = zona;
        this.descripcionZona = descripcionZona;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDescripcionZona() {
        return descripcionZona;
    }

    public void setDescripcionZona(String descripcionZona) {
        this.descripcionZona = descripcionZona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZona != null ? idZona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zona)) {
            return false;
        }
        Zona other = (Zona) object;
        if ((this.idZona == null && other.idZona != null) || (this.idZona != null && !this.idZona.equals(other.idZona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Zona[idZona=" + idZona + "]";
    }
    
    public Zona actualizarEntity(Zona obj){
         setDescripcionZona(obj.descripcionZona);
         setIdZona(obj.idZona);
         setZona(obj.zona);
        return this;
    }
    

}
