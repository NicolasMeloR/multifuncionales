/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aagarcia
 */
@Entity
@Table(name = "TAREASADMIN_ACCESO_USUARIO")
@NamedQueries({
    @NamedQuery(name = "TareasadminAccesoUsuario.findByIdtareaaccesusu", query = "SELECT t FROM TareasadminAccesoUsuario t WHERE t.idtareaaccesusu = :idtareaaccesusu"),
    @NamedQuery(name = "TareasadminAccesoUsuario.findByUsuario", query = "SELECT t FROM TareasadminAccesoUsuario t WHERE t.usuario = :usuario"),
    @NamedQuery(name = "TareasadminAccesoUsuario.findByServicio", query = "SELECT t FROM TareasadminAccesoUsuario t WHERE t.servicio = :servicio"),
    @NamedQuery(name = "TareasadminAccesoUsuario.findByUsuariosession", query = "SELECT t FROM TareasadminAccesoUsuario t WHERE t.usuariosession = :usuariosession"),
    @NamedQuery(name = "TareasadminAccesoUsuario.findByNombreusuario", query = "SELECT t FROM TareasadminAccesoUsuario t WHERE t.nombreusuario = :nombreusuario"),
    @NamedQuery(name = "TareasadminAccesoUsuario.findByFecha", query = "SELECT t FROM TareasadminAccesoUsuario t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TareasadminAccesoUsuario.findByTareaadmin", query = "SELECT t FROM TareasadminAccesoUsuario t WHERE t.tareaadmin = :tareaadmin"),
    @NamedQuery(name = "TareasadminAccesoUsuario.findMax", query = "select max(e.idtareaaccesusu) from TareasadminAccesoUsuario e")})
public class TareasadminAccesoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDTAREAACCESUSU", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idtareaaccesusu_SEQ")
    @SequenceGenerator(name = "idtareaaccesusu_SEQ", sequenceName = "SEQ_TAREASADMINACCESUSU", allocationSize = 1)
    private Long idtareaaccesusu;

    @Column(name = "USUARIO", nullable = false)
    private String usuario;
    @Column(name = "SERVICIO", nullable = false)
    private long servicio;
    @Column(name = "USUARIOSESSION")
    private String usuariosession;
    @Column(name = "NOMBREUSUARIO")
    private String nombreusuario;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "TAREAADMIN", nullable = false)
    private short tareaadmin;

    public TareasadminAccesoUsuario() {
    }

    public TareasadminAccesoUsuario(Long idtareaaccesusu) {
        this.idtareaaccesusu = idtareaaccesusu;
    }

    public TareasadminAccesoUsuario(Long idtareaaccesusu, String usuario, long servicio, short tareaadmin) {
        this.idtareaaccesusu = idtareaaccesusu;
        this.usuario = usuario;
        this.servicio = servicio;
        this.tareaadmin = tareaadmin;
    }

    public Long getIdtareaaccesusu() {
        return idtareaaccesusu;
    }

    public void setIdtareaaccesusu(Long idtareaaccesusu) {
        this.idtareaaccesusu = idtareaaccesusu;
    }

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

    public String getUsuariosession() {
        return usuariosession;
    }

    public void setUsuariosession(String usuariosession) {
        this.usuariosession = usuariosession;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public short getTareaadmin() {
        return tareaadmin;
    }

    public void setTareaadmin(short tareaadmin) {
        this.tareaadmin = tareaadmin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtareaaccesusu != null ? idtareaaccesusu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TareasadminAccesoUsuario)) {
            return false;
        }
        TareasadminAccesoUsuario other = (TareasadminAccesoUsuario) object;
        if ((this.idtareaaccesusu == null && other.idtareaaccesusu != null) || (this.idtareaaccesusu != null && !this.idtareaaccesusu.equals(other.idtareaaccesusu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.TareasadminAccesoUsuario[idtareaaccesusu=" + idtareaaccesusu + "]";
    }

}
