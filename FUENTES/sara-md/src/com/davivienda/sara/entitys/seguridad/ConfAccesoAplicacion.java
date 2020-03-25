package com.davivienda.sara.entitys.seguridad;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * ConfAccesoAplicacion - 25/05/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Entity
@Table(name = "CONF_ACCESO_APLICACION")
@NamedQueries({
    @NamedQuery(name = "ConfAccesoAplicacion.todos", query = "SELECT c FROM ConfAccesoAplicacion c ORDER BY c.confAccesoAplicacionPK.usuario, c.confAccesoAplicacionPK.servicio",hints=@QueryHint(name="toplink.refresh", value="true")),
    @NamedQuery(name = "ConfAccesoAplicacion.findByUsuario", query = "SELECT c FROM ConfAccesoAplicacion c WHERE c.confAccesoAplicacionPK.usuario = :usuario"),
    @NamedQuery(name = "ConfAccesoAplicacion.findByConsultar", query = "SELECT c FROM ConfAccesoAplicacion c WHERE c.consultar = :consultar"),
    @NamedQuery(name = "ConfAccesoAplicacion.findByActualizar", query = "SELECT c FROM ConfAccesoAplicacion c WHERE c.actualizar = :actualizar"),
    @NamedQuery(name = "ConfAccesoAplicacion.findByEjecutar", query = "SELECT c FROM ConfAccesoAplicacion c WHERE c.ejecutar = :ejecutar"),
    @NamedQuery(name = "ConfAccesoAplicacion.findByAdministrar", query = "SELECT c FROM ConfAccesoAplicacion c WHERE c.administrar = :administrar"),
    @NamedQuery(name = "ConfAccesoAplicacion.findByCrear", query = "SELECT c FROM ConfAccesoAplicacion c WHERE c.crear = :crear"),
    @NamedQuery(name = "ConfAccesoAplicacion.findByBorrar", query = "SELECT c FROM ConfAccesoAplicacion c WHERE c.borrar = :borrar"),
    @NamedQuery(name = "ConfAccesoAplicacion.findByServicio", query = "SELECT c FROM ConfAccesoAplicacion c WHERE c.confAccesoAplicacionPK.servicio = :servicio"),
    @NamedQuery(name = "ConfAccesoAplicacion.deleteByUsuarioServicio", query = "DELETE FROM ConfAccesoAplicacion c WHERE c.confAccesoAplicacionPK.servicio = :servicio AND c.confAccesoAplicacionPK.usuario = :usuario")
})
        

        
public class ConfAccesoAplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConfAccesoAplicacionPK confAccesoAplicacionPK;
    @Column(name = "CONSULTAR")
    private Short consultar;
    @Column(name = "ACTUALIZAR")
    private Short actualizar;
    @Column(name = "EJECUTAR")
    private Short ejecutar;
    @Column(name = "ADMINISTRAR")
    private Short administrar;
    @Column(name = "CREAR")
    private Short crear;
    @Column(name = "BORRAR")
    private Short borrar;
    @JoinColumn(name = "SERVICIO", referencedColumnName = "SERVICIO", insertable = false,  updatable = false, nullable=false)
    @ManyToOne
    private ServicioAplicacion servicioAplicacion;
    @JoinColumn(name = "USUARIO", referencedColumnName = "USUARIO", insertable = false, updatable = false, nullable=false)
    @ManyToOne
    private UsuarioAplicacion usuarioAplicacion;
    @Version
    private Long version;

    public ConfAccesoAplicacion() {
    }

    public ConfAccesoAplicacion(ConfAccesoAplicacionPK confAccesoAplicacionPK) {
        this.confAccesoAplicacionPK = confAccesoAplicacionPK;
    }

    public ConfAccesoAplicacion(String usuario, long servicio) {
        this.confAccesoAplicacionPK = new ConfAccesoAplicacionPK(usuario, servicio);
    }
    
    
    //CREADO ALVARO 18 ENERO
//    public ConfAccesoAplicacion(String usuario) {
//        this.confAccesoAplicacionPK = new ConfAccesoAplicacionPK(usuario);
//    }


    public ConfAccesoAplicacionPK getConfAccesoAplicacionPK() {
        return confAccesoAplicacionPK;
    }

    public void setConfAccesoAplicacionPK(ConfAccesoAplicacionPK confAccesoAplicacionPK) {
        this.confAccesoAplicacionPK = confAccesoAplicacionPK;
    }

    public Short getConsultar() {
        return consultar;
    }

    public void setConsultar(Short consultar) {
        this.consultar = consultar;
    }

    public Short getActualizar() {
        return actualizar;
    }

    public void setActualizar(Short actualizar) {
        this.actualizar = actualizar;
    }

    public Short getEjecutar() {
        return ejecutar;
    }

    public void setEjecutar(Short ejecutar) {
        this.ejecutar = ejecutar;
    }

    public Short getAdministrar() {
        return administrar;
    }

    public void setAdministrar(Short administrar) {
        this.administrar = administrar;
    }

    public Short getCrear() {
        return crear;
    }

    public void setCrear(Short crear) {
        this.crear = crear;
    }

    public Short getBorrar() {
        return borrar;
    }

    public void setBorrar(Short borrar) {
        this.borrar = borrar;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public ServicioAplicacion getServicioAplicacion() {
        return servicioAplicacion;
    }

    public void setServicioAplicacion(ServicioAplicacion servicioAplicacion) {
        this.servicioAplicacion = servicioAplicacion;
    }

    public UsuarioAplicacion getUsuarioAplicacion() {
        return usuarioAplicacion;
    }

    public void setUsuarioAplicacion(UsuarioAplicacion usuarioAplicacion) {
        this.usuarioAplicacion = usuarioAplicacion;
    }
    
    public ConfAccesoAplicacion actualizarEntity(ConfAccesoAplicacion objetoModificado) {
        setActualizar(objetoModificado.actualizar);
        setAdministrar(objetoModificado.administrar);
        setBorrar(objetoModificado.borrar);
        setConsultar(objetoModificado.consultar);
        setCrear(objetoModificado.crear);
        setEjecutar(objetoModificado.ejecutar);
        return this;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (confAccesoAplicacionPK != null ? confAccesoAplicacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfAccesoAplicacion)) {
            return false;
        }
        ConfAccesoAplicacion other = (ConfAccesoAplicacion) object;
        if ((this.confAccesoAplicacionPK == null && other.confAccesoAplicacionPK != null) || (this.confAccesoAplicacionPK != null && !this.confAccesoAplicacionPK.equals(other.confAccesoAplicacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ConfAccesoAplicacion{" + "confAccesoAplicacionPK=" + confAccesoAplicacionPK + ", consultar=" + consultar + ", actualizar=" + actualizar + ", ejecutar=" + ejecutar + ", administrar=" + administrar + ", crear=" + crear + ", borrar=" + borrar + ", servicioAplicacion=" + servicioAplicacion + ", usuarioAplicacion=" + usuarioAplicacion + ", version=" + version + '}';
    }

    
}
