
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author aagarcia
 */
@Entity
@Table(name = "UBICACION")
@NamedQueries( {
    @NamedQuery(
    name = "Ubicacion.RegistroUnico",
            query = "select object(o) from Ubicacion o where o.codigoUbicacion = :codigoUbicacion"),
            @NamedQuery(
    name = "Ubicacion.Todos",
            query = "select object(o) from Ubicacion o order by o.codigoUbicacion"),
             @NamedQuery(
    name = "Ubicacion.todos", query = "SELECT u FROM Ubicacion u ORDER BY u.codigoUbicacion")
})

public class Ubicacion implements Serializable {
    private static final long serialVersionUID = 1L;

    
    @Id
    @Column(name = "CODIGOUBICACION", nullable = false)
    private Integer codigoUbicacion;
    @Column(name = "TIPOUBICACION", nullable = false)
    private Integer tipoUbicacion;
    @Column(name = "SUCURSAL", nullable = false)
    private Integer sucursal;
    @Column(name = "OFICINA")
    private Integer oficina;
    @Column(name = "DIRECCION", nullable = false)
    private String direccion;
    @Column(name = "CODIGOCIUDAD")
    private Long codigoCiudad;
    @Column(name = "HORARIO")
    private String horario;
    
    
    @Column(name = "NOMBREOFICINA")
    private String nombreoficina;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "DIASHORARIONORMAL")
    private String diashorarionormal;
    @Column(name = "HORARIONORMAL")
    private String horarionormal;
    @Column(name = "HORARIOADICIONAL")
    private String horarioadicional;
    @Column(name = "HORARIOSABADO")
    private String horariosabado;
    @Column(name = "HORARIODOMINGO")
    private String horariodomingo;
    @Column(name = "TIPOOFICINA")
    private String tipooficina;
    @Column(name = "ESTADO")
    private Integer estado;
    @Column(name = "NOMBRECIUDAD")
    private String nombreciudad;
    
    @JoinColumn(name = "Regional", referencedColumnName = "ID_REGIONAL")
    @ManyToOne
    private Regional regional;
    @JoinColumn(name = "ZONA", referencedColumnName = "ID_ZONA")
    @ManyToOne
    private Zona zona;
    

    public Ubicacion() {
    }



    
     public Ubicacion(Integer codigoubicacion) {
        this.codigoUbicacion = codigoubicacion;
    }

    public Ubicacion(Integer codigoubicacion, Integer tipoubicacion, Integer sucursal, String direccion) {
        this.codigoUbicacion = codigoubicacion;
        this.tipoUbicacion = tipoubicacion;
        this.sucursal = sucursal;
        this.direccion = direccion;
    }

    public Integer getCodigoUbicacion() {
        return codigoUbicacion;
    }

    public void setCodigoUbicacion(Integer codigoubicacion) {
        this.codigoUbicacion = codigoubicacion;
    }

    public Integer getTipoUbicacion() {
        return tipoUbicacion;
    }

    public void setTipoUbicacion(Integer tipoubicacion) {
        this.tipoUbicacion = tipoubicacion;
    }

    public Integer getSucursal() {
        return sucursal;
    }

    public void setSucursal(Integer sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getOficina() {
        return oficina;
    }

    public void setOficina(Integer oficina) {
        this.oficina = oficina;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(Long codigociudad) {
        this.codigoCiudad = codigociudad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
    
     public Regional getRegional() {
     return regional;
    }

    public void setRegional(Regional regional) {
        this.regional = regional;
    }
    
    public String getNombreoficina() {
        return nombreoficina;
    }

    public void setNombreoficina(String nombreoficina) {
        this.nombreoficina = nombreoficina;
    }
    
   public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDiashorarionormal() {
        return diashorarionormal;
    }

    public void setDiashorarionormal(String diashorarionormal) {
        this.diashorarionormal = diashorarionormal;
    }

    public String getHorarionormal() {
        return horarionormal;
    }

    public void setHorarionormal(String horarionormal) {
        this.horarionormal = horarionormal;
    }

    public String getHorarioadicional() {
        return horarioadicional;
    }

    public void setHorarioadicional(String horarioadicional) {
        this.horarioadicional = horarioadicional;
    }

    public String getHorariosabado() {
        return horariosabado;
    }

    public void setHorariosabado(String horariosabado) {
        this.horariosabado = horariosabado;
    }

    public String getHorariodomingo() {
        return horariodomingo;
    }

    public void setHorariodomingo(String horariodomingo) {
        this.horariodomingo = horariodomingo;
    }

    public String getTipooficina() {
        return tipooficina;
    }

    public void setTipooficina(String tipooficina) {
        this.tipooficina = tipooficina ;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    public String getNombreciudad() {
        return nombreciudad;
    }

    public void setNombreciudad(String nombreciudad) {
        this.nombreciudad = nombreciudad;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoUbicacion != null ? codigoUbicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ubicacion)) {
            return false;
        }
        Ubicacion other = (Ubicacion) object;
        if ((this.codigoUbicacion == null && other.codigoUbicacion != null) || (this.codigoUbicacion != null && 
!this.codigoUbicacion.equals(other.codigoUbicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Ubicacion[codigoubicacion=" + codigoUbicacion + "]";
    }
    
     public Ubicacion actualizarEntity(Ubicacion obj) {
        setCodigoUbicacion(obj.codigoUbicacion);
        setTipoUbicacion(obj.tipoUbicacion);
        setSucursal(obj.sucursal);
        setOficina(obj.oficina);
        setZona(obj.zona);
        setDireccion(obj.direccion);
        setCodigoCiudad(obj.codigoCiudad);
        setHorario(obj.horario);
        setRegional(obj.regional);
        setNombreoficina(obj.nombreoficina);
        setTelefono(obj.telefono);
        setDiashorarionormal(obj.diashorarionormal);
        setHorarionormal(obj.horarionormal);
        setHorarioadicional(obj.horarioadicional);
        setHorariosabado(obj.horariosabado);
        setHorariodomingo(obj.horariodomingo);
        setTipooficina(obj.tipooficina);
        setEstado(obj.estado);
        setNombreciudad(obj.nombreciudad);

        return this;
    }

}