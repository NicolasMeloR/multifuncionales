/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ControlUsuarioAplicacion.java
 *
 * Fecha       :  05/09/2017, 09:52:27 AM
 * Descripción :  Entity con el contenido de control de acceso de los usuarios a la aplicacion *
 * @author     : jediazs
 * @version    : $Id$
 */
@Entity
@Table(name = "CONTROL_USUARIO_APLICACION")
@NamedQueries({
    @NamedQuery(
    name = "ControlUsuarioAplicacion.RegistroUnico",
            query = "select object(obj) from ControlUsuarioAplicacion obj where obj.usuario = :usuario"),
    @NamedQuery(
    name = "ControlUsuarioAplicacion.EliminarRegistroUnico",
            query = "delete  from ControlUsuarioAplicacion obj where obj.usuario = :usuario"),
    @NamedQuery(
    name = "ControlUsuarioAplicacion.Todos",
            query = "select object(obj) from ControlUsuarioAplicacion obj order by obj.usuario")

})
public class ControlUsuarioAplicacion implements Serializable {
    
    @Id
	@Column(name = "USUARIO", nullable = false)
    private String usuario;
    
	@Column(name = "DIRECCION_IP", nullable = false)
    private String direccionIp;
    
    @Column(name = "FECHA_INGRESO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    
    
    /**
     * Crea una nueva instancia de <code>Usuario</code>.
     */
    public ControlUsuarioAplicacion() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlUsuarioAplicacion)) {
            return false;
        }
        ControlUsuarioAplicacion other = (ControlUsuarioAplicacion) object;
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ControlUsuarioAplicacion[usuaario=" + usuario + "]";
    }
    
    
}
