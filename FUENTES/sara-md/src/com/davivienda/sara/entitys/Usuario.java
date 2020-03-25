package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Usuario.java
 *
 * Fecha       :  7/06/2007, 12:11:27 PM
 * Descripción :  Entity con el contenido de los usuarios de la aplicación
 *
 * @author     : jjvargas
 * @version    : $Id$
 */
@Entity
@Table(name = "USUARIO")
@NamedQueries({
    @NamedQuery(
    name = "Usuario.RegistroUnico",
            query = "select object(obj) from Usuario obj where obj.idUsuario = :idUsuario"),
    @NamedQuery(
    name = "Usuario.Todos",
            query = "select object(obj) from Usuario obj order by obj.idUsuario")

})
public class Usuario implements Serializable {
    
    @Id
    private String idUsuario;
    
    private String nombreUsuario;
    
    private Integer perfil;
    
    private String direccionIp;
    
    private String correoJefe;
    
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    
    private String usuarioModificacion;
    
    @Version
    private Integer version;
    
    
    /**
     * Crea una nueva instancia de <code>Usuario</code>.
     */
    public Usuario() {
    }
    
    public Usuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public Usuario(String idUsuario, String nombreUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public Integer getPerfil() {
        return perfil;
    }
    
    public void setPerfil(Integer perfil) {
        this.perfil = perfil;
    }
    
    public String getDireccionIp() {
        return direccionIp;
    }
    
    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }
    
    public String getCorreoJefe() {
        return correoJefe;
    }
    
    public void setCorreoJefe(String correoJefe) {
        this.correoJefe = correoJefe;
    }
    
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }
    
    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if (this.idUsuario != other.idUsuario && (this.idUsuario == null || !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Usuario[idUsuario=" + idUsuario + "]";
    }
    
    
}

