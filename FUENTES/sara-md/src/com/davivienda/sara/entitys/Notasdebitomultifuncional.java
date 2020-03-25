/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lpulgari
 */
@Entity
@Table(name = "NOTASDEBITOMULTIFUNCIONAL")

@NamedQueries( {
    @NamedQuery(
    name = "Notasdebitomultifuncional.registroUnico",
            query = "select object(txm) from Notasdebitomultifuncional txm where txm.notasdebitomultifuncionalPK.codigocajero = :codigocajero and txm.notasdebitomultifuncionalPK.fecha = :fecha"),             
    @NamedQuery(
    name = "Notasdebitomultifuncional.todos", 
             query = "select object(txm) from Notasdebitomultifuncional txm"),
    @NamedQuery(
         name = "Notasdebitomultifuncional.CajeroFecha",
         query = "select object(obj) from Notasdebitomultifuncional obj " +
            "        where obj.notasdebitomultifuncionalPK.codigocajero =:codigocajero and " +
            "              obj.notasdebitomultifuncionalPK.fecha between :fechaInicial and :fechaFinal " + 
            "        order by obj.notasdebitomultifuncionalPK.fecha"),
    @NamedQuery(
         name = "Notasdebitomultifuncional.RangoFecha",
         query = "select object(obj) from Notasdebitomultifuncional obj " +
            "        where  obj.notasdebitomultifuncionalPK.fecha between :fechaInicial and :fechaFinal " + 
            "        order by obj.notasdebitomultifuncionalPK.fecha"),   
    @NamedQuery(
         name = "Notasdebitomultifuncional.CajeroCuentaFechaValor",
         query = "select object(obj) from Notasdebitomultifuncional obj " +
            "        where  obj.notasdebitomultifuncionalPK.fecha between :fechaInicial and :fechaFinal and" +
            "        obj.notasdebitomultifuncionalPK.codigocajero = :codigoCajero and " +
            "        obj.numerocuenta = :numeroCuenta and "+
            "        obj.valor = :valor "+
            "        order by obj.notasdebitomultifuncionalPK.fecha")
})


public class Notasdebitomultifuncional implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotasdebitomultifuncionalPK notasdebitomultifuncionalPK;
    @Column(name = "TALON", nullable = false)
    private Integer talon;
    @Column(name = "NUMEROCUENTA")
    private String numerocuenta;
    @Column(name = "CODIGOOFICINA", nullable = false)
    private Integer codigooficina;
    @Column(name = "VALOR")
    private Long valor;
    @Column(name = "VALORAJUSTADO")
    private Long valorajustado;
    @Column(name = "ESTADO")
    private Integer estado;
    @Column(name = "USUARIOCREA")
    private String usuariocrea;
    @Column(name = "USUARIOAUTORIZA")
    private String usuarioautoriza;
    @Column(name = "FECHAAPLICA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaaplica;
    @Column(name = "ERROR")
    private String error;
    @Column(name = "TIPOCUENTA")
    private Integer tipocuenta;
    @Column(name = "VERSION")
    private Integer version;

    public Notasdebitomultifuncional() {
    }

    public Notasdebitomultifuncional(NotasdebitomultifuncionalPK notasdebitomultifuncionalPK) {
        this.notasdebitomultifuncionalPK = notasdebitomultifuncionalPK;
    }

    public Notasdebitomultifuncional(NotasdebitomultifuncionalPK notasdebitomultifuncionalPK, Integer talon, Integer codigooficina) {
        this.notasdebitomultifuncionalPK = notasdebitomultifuncionalPK;
        this.talon = talon;
        this.codigooficina = codigooficina;
    }

    public Notasdebitomultifuncional(int codigocajero, Date fecha) {
        this.notasdebitomultifuncionalPK = new NotasdebitomultifuncionalPK(codigocajero, fecha);
    }

    public NotasdebitomultifuncionalPK getNotasdebitomultifuncionalPK() {
        return notasdebitomultifuncionalPK;
    }

    public void setNotasdebitomultifuncionalPK(NotasdebitomultifuncionalPK notasdebitomultifuncionalPK) {
        this.notasdebitomultifuncionalPK = notasdebitomultifuncionalPK;
    }

   
    public String getNumerocuenta() {
        return numerocuenta;
    }

    public void setNumerocuenta(String numerocuenta) {
        this.numerocuenta = numerocuenta;
    }

    
    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Long getValorajustado() {
        return valorajustado;
    }
    
        
    

    public void setValorajustado(Long valorajustado) {
        this.valorajustado = valorajustado;
    }

    public Integer getCodigooficina() {
        return codigooficina;
    }

    public void setCodigooficina(Integer codigooficina) {
        this.codigooficina = codigooficina;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getTalon() {
        return talon;
    }

    public void setTalon(Integer talon) {
        this.talon = talon;
    }

    public Integer getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(Integer tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    

    public String getUsuariocrea() {
        return usuariocrea;
    }

    public void setUsuariocrea(String usuariocrea) {
        this.usuariocrea = usuariocrea;
    }

    public String getUsuarioautoriza() {
        return usuarioautoriza;
    }

    public void setUsuarioautoriza(String usuarioautoriza) {
        this.usuarioautoriza = usuarioautoriza;
    }

    public Date getFechaaplica() {
        return fechaaplica;
    }

    public void setFechaaplica(Date fechaaplica) {
        this.fechaaplica = fechaaplica;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notasdebitomultifuncionalPK != null ? notasdebitomultifuncionalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notasdebitomultifuncional)) {
            return false;
        }
        Notasdebitomultifuncional other = (Notasdebitomultifuncional) object;
        if ((this.notasdebitomultifuncionalPK == null && other.notasdebitomultifuncionalPK != null) || (this.notasdebitomultifuncionalPK != null && !this.notasdebitomultifuncionalPK.equals(other.notasdebitomultifuncionalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Notasdebitomultifuncional[notasdebitomultifuncionalPK=" + notasdebitomultifuncionalPK + "]";
    }
    
     public Notasdebitomultifuncional actualizarEntity(Notasdebitomultifuncional obj) { 
        
        setNotasdebitomultifuncionalPK(obj.notasdebitomultifuncionalPK);
        setTalon(obj.talon);
        setNumerocuenta(obj.numerocuenta);
        setCodigooficina(obj.codigooficina);
        setValor(obj.valor);
        setValorajustado(obj.valorajustado);
        setEstado(obj.estado);
        setUsuariocrea(obj.usuariocrea);
        setUsuarioautoriza(obj.usuarioautoriza);
        setFechaaplica(obj.fechaaplica);
        setError(obj.error);
        setTipocuenta(obj.tipocuenta);
        setVersion(obj.version);
        return this;
            
    }

}
