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
 * Descripción : Regional de ubicación
 * Fecha       : 18/02/2008 05:52:16 PM
 * @author     : jjvargas
 **/
@Entity
@Table(name = "REGIONAL")
@NamedQueries( {
    @NamedQuery(
    name = "Regional.RegistroUnico",
            query = "select object(o) from Regional o where o.idRegional = :idRegional"),
    @NamedQuery(
    name = "Regional.Todos",
            query = "select object(o) from Regional o order by o.idRegional"),
    @NamedQuery(
    name = "Regional.todos", query = "SELECT u FROM Regional u ORDER BY u.idRegional")
})

public class Regional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_REGIONAL", nullable = false)
    private Integer idRegional;
    @Column(name = "REGIONAL", nullable = false)
    private String regional;


    public Regional() {
    }

    public Regional(Integer idRegional) {
        this.idRegional = idRegional;
    }

    public Regional(Integer idRegional, String regional) {
        this.idRegional = idRegional;
        this.regional = regional;
    
    }
    public Integer getIdRegional() {
        return idRegional;
    }

    public void setIdRegional(Integer idRegional) {
        this.idRegional = idRegional;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

   



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegional != null ? idRegional.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regional)) {
            return false;
        }
        Regional other = (Regional) object;
        if ((this.idRegional == null && other.idRegional != null) || (this.idRegional != null && !this.idRegional.equals(other.idRegional))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Regional[idRegional=" + idRegional + "]";
    }
    
    public Regional actualizarEntity(Regional obj){
     
         setIdRegional(obj.idRegional);
         setRegional(obj.regional);
        return this;
    }
    

}
