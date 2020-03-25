package com.davivienda.sara.entitys.seguridad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ServicioAplicacion - 25/05/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

@Entity
@Table(name = "SERVICIO_APLICACION")
@NamedQueries({
    @NamedQuery(name = "ServicioAplicacion.todos", query = "SELECT s FROM ServicioAplicacion s ORDER BY s.modulo, s.servicio "), 
    @NamedQuery(name = "ServicioAplicacion.findByServicio", query = "SELECT s FROM ServicioAplicacion s WHERE s.servicio = :servicio"), 
    @NamedQuery(name = "ServicioAplicacion.findByModulo", query = "SELECT s FROM ServicioAplicacion s WHERE s.modulo = :modulo"), 
    @NamedQuery(name = "ServicioAplicacion.findByDescripcion", query = "SELECT s FROM ServicioAplicacion s WHERE s.descripcion = :descripcion")})
public class ServicioAplicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "SERVICIO", nullable = false)
    private Long servicio;
    @Column(name = "MODULO", nullable = false)
    private String modulo;
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicioAplicacion", fetch=FetchType.EAGER)
    private Collection<ConfAccesoAplicacion> confAccesoAplicacionCollection;

    public ServicioAplicacion() {
    }

    public ServicioAplicacion(Long servicio) {
        this.servicio = servicio;
    }

    public ServicioAplicacion(Long servicio, String modulo, String descripcion) {
        this.servicio = servicio;
        this.modulo = modulo;
        this.descripcion = descripcion;
    }

    public Long getServicio() {
        return servicio;
    }

    public void setServicio(Long servicio) {
        this.servicio = servicio;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<ConfAccesoAplicacion> getConfAccesoAplicacionCollection() {
        return confAccesoAplicacionCollection;
    }

    public void setConfAccesoAplicacionCollection(Collection<ConfAccesoAplicacion> confAccesoAplicacionCollection) {
        this.confAccesoAplicacionCollection = confAccesoAplicacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (servicio != null ? servicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicioAplicacion)) {
            return false;
        }
        ServicioAplicacion other = (ServicioAplicacion) object;
        if ((this.servicio == null && other.servicio != null) || (this.servicio != null && !this.servicio.equals(other.servicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.seguridad.ServicioAplicacion[servicio=" + servicio + "]";
    }

}
