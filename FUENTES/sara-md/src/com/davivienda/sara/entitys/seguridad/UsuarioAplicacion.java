package com.davivienda.sara.entitys.seguridad;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Version;

/**
 * UsuarioAplicacion - 25/05/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
@Entity
@Table(name = "USUARIO_APLICACION")
@NamedQueries({
    @NamedQuery(name = "UsuarioAplicacion.todos", query = "SELECT u FROM UsuarioAplicacion u ORDER BY u.usuario"),
    @NamedQuery(name = "UsuarioAplicacion.buscarPorUsuario", query = "SELECT u FROM UsuarioAplicacion u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "UsuarioAplicacion.findByNombre", query = "SELECT u FROM UsuarioAplicacion u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "UsuarioAplicacion.findByToken", query = "SELECT u FROM UsuarioAplicacion u WHERE u.token = :token"),
    @NamedQuery(name = "UsuarioAplicacion.findBySistema", query = "SELECT u FROM UsuarioAplicacion u WHERE u.sistema = :sistema"),
    @NamedQuery(name = "UsuarioAplicacion.findByNormal", query = "SELECT u FROM UsuarioAplicacion u WHERE u.normal = :normal"),
    @NamedQuery(name = "UsuarioAplicacion.findByAuditoria", query = "SELECT u FROM UsuarioAplicacion u WHERE u.auditoria = :auditoria"),
    @NamedQuery(name = "UsuarioAplicacion.deleteByUsuario", query = "DELETE FROM UsuarioAplicacion  u WHERE u.usuario = :usuario")
})
public class UsuarioAplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "USUARIO", nullable = false)
    private String usuario;
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @Column(name = "DIRECCION_IP")
    private String direccionIp;
    @Column(name = "TOKEN")
    private String token;
    @Column(name = "CLAVE_ESTATICA")
    private String claveEstatica;
    @Column(name = "SISTEMA")
    private Short sistema;
    @Column(name = "NORMAL")
    private Short normal;
    @Column(name = "AUDITORIA")
    private Short auditoria;
    @Version
    private Long version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioAplicacion", fetch = FetchType.EAGER)
    private Collection<ConfAccesoAplicacion> confAccesoAplicacionCollection;

    public UsuarioAplicacion() {
    }

public UsuarioAplicacion(String usuario) {
        this.usuario = usuario;
    }

    public UsuarioAplicacion(String usuario, String nombre) {
        this.usuario = usuario;
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario.toLowerCase();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClaveEstatica() {
        return claveEstatica;
    }

    public void setClaveEstatica(String claveEstatica) {
        this.claveEstatica = claveEstatica;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Short getSistema() {
        return sistema;
    }

    public void setSistema(Short sistema) {
        this.sistema = sistema;
    }

    public Short getNormal() {
        return normal;
    }

    public void setNormal(Short normal) {
        this.normal = normal;
    }

    public Short getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Short auditoria) {
        this.auditoria = auditoria;
    }

    public Collection<ConfAccesoAplicacion> getConfAccesoAplicacionCollection() {
        return confAccesoAplicacionCollection;
    }

    public void setConfAccesoAplicacionCollection(Collection<ConfAccesoAplicacion> confAccesoAplicacionCollection) {
        this.confAccesoAplicacionCollection = confAccesoAplicacionCollection;
    }

    public UsuarioAplicacion actualizarEntity(UsuarioAplicacion obj) {
        setAuditoria(obj.auditoria);
        setClaveEstatica(obj.claveEstatica);
        setConfAccesoAplicacionCollection(obj.confAccesoAplicacionCollection);
        setDireccionIp(obj.direccionIp);
        setNombre(obj.nombre);
        setNormal(obj.getNormal());
        setSistema(obj.getSistema());
        setToken(obj.getToken());
        setUsuario(obj.usuario);
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioAplicacion)) {
            return false;
        }
        UsuarioAplicacion other = (UsuarioAplicacion) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.seguridad.UsuarioAplicacion[usuario=" + usuario + "]";
    }
}
