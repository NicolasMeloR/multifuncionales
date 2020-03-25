package com.davivienda.sara.entitys.seguridad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ConfAccesoAplicacionPK - 25/05/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

@Embeddable
public class ConfAccesoAplicacionPK implements Serializable {
    @Column(name = "USUARIO", nullable = false)
    private String usuario;
    @Column(name = "SERVICIO", nullable = false)
    private long servicio;

    public ConfAccesoAplicacionPK() {
    }

    public ConfAccesoAplicacionPK(String usuario, long servicio) {
        this.usuario = usuario;
        this.servicio = servicio;
    }
    
      //CREADO ALVARO 18 ENERO
//     public ConfAccesoAplicacionPK(String usuario) {
//        this.usuario = usuario;
//     }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public long getServicio() {
        return servicio;
    }

    public void setServicio(long servicio) {
        this.servicio = servicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        hash += (int) servicio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfAccesoAplicacionPK)) {
            return false;
        }
        ConfAccesoAplicacionPK other = (ConfAccesoAplicacionPK) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        if (this.servicio != other.servicio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacionPK[usuario=" + usuario + ", servicio=" + servicio + "]";
    }

}
