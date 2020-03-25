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
 * @author aa.garcia
 */
@Entity
@Table(name = "NOTASDEBITO_HISTO")
@NamedQueries({
         @NamedQuery(
     name = "NotasdebitoHisto.RegistroUnico",
     query = "select object(obj) from NotasdebitoHisto obj " +
                 "where obj.notasdebitoPK.codigocajero = :codigoCajero and " +
                        "obj.notasdebitoPK.fecha = :fecha"),
     @NamedQuery(
       name = "NotasdebitoHisto.Todos",
       query = "select object(obj) from NotasdebitoHisto obj order by obj.notasdebitoPK.codigocajero, obj.notasdebitoPK.fecha"),
      @NamedQuery(
         name = "NotasdebitoHisto.CajeroFecha",
         query = "select object(obj) from NotasdebitoHisto obj " +
            "        where obj.notasdebitoPK.codigocajero =:codigocajero and " +
            "              obj.notasdebitoPK.fecha between :fechaInicial and :fechaFinal " + 
            "        order by obj.notasdebitoPK.fecha"),
      @NamedQuery(
         name = "NotasdebitoHisto.RangoFecha",
         query = "select object(obj) from NotasdebitoHisto obj " +
            "        where  obj.notasdebitoPK.fecha between :fechaInicial and :fechaFinal " + 
            "        order by obj.notasdebitoPK.fecha"),   
         @NamedQuery(
         name = "NotasdebitoHisto.CajeroCuentaFechaValor",
         query = "select object(obj) from NotasdebitoHisto obj " +
            "        where  obj.notasdebitoPK.fecha between :fechaInicial and :fechaFinal and" +
            "        obj.notasdebitoPK.codigocajero = :codigoCajero and " +
            "        obj.numerocuenta = :numeroCuenta and "+
            "        obj.valor = :valor "+
            "        order by obj.notasdebitoPK.fecha")
 })

public class NotasdebitoHisto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotasdebitoHistoPK notasdebitoPK;
    @Column(name = "NUMEROCUENTA")
    private String numerocuenta;
    @Column(name = "CODIGOOCCA", nullable = false)
    private Integer codigoocca;
    @Column(name = "TALON")
    private Integer talon;
    @Column(name = "VALOR")
    private Long valor;
    @Column(name = "VALORAJUSTADO")
    private Long valorajustado;
    @Column(name = "TIPOCUENTA")
    private Integer tipocuenta;
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

    public NotasdebitoHisto() {
    }

    public NotasdebitoHisto(NotasdebitoHistoPK notasdebitoPK) {
        this.notasdebitoPK = notasdebitoPK;
    }

    public NotasdebitoHisto(NotasdebitoHistoPK notasdebitoPK, Integer codigoocca) {
        this.notasdebitoPK = notasdebitoPK;
        this.codigoocca = codigoocca;
    }

    public NotasdebitoHisto(Integer codigocajero, Date fecha) {
        this.notasdebitoPK = new NotasdebitoHistoPK(codigocajero, fecha);
    }

    public NotasdebitoHistoPK getNotasdebitoHistoPK() {
        return notasdebitoPK;
    }

    public void setNotasdebitoHistoPK(NotasdebitoHistoPK notasdebitoPK) {
        this.notasdebitoPK = notasdebitoPK;
    }

    public String getNumerocuenta() {
        return numerocuenta;
    }

    public void setNumerocuenta(String numerocuenta) {
        this.numerocuenta = numerocuenta;
    }

    public Integer getCodigoocca() {
        return codigoocca;
    }

    public void setCodigoocca(Integer codigoocca) {
        this.codigoocca = codigoocca;
    }

    public Integer getTalon() {
        return talon;
    }

    public void setTalon(Integer talon) {
        this.talon = talon;
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

    public Integer getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(Integer tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
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
        hash += (notasdebitoPK != null ? notasdebitoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotasdebitoHisto)) {
            return false;
        }
        NotasdebitoHisto other = (NotasdebitoHisto) object;
        if ((this.notasdebitoPK == null && other.notasdebitoPK != null) || (this.notasdebitoPK != null && !this.notasdebitoPK.equals(other.notasdebitoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.NotasdebitoHisto[notasdebitoPK=" + notasdebitoPK + "]";
    }
 public NotasdebitoHisto actualizarEntity(NotasdebitoHisto obj) {
        setNotasdebitoHistoPK( notasdebitoPK);
        setNumerocuenta(numerocuenta);
        setCodigoocca( codigoocca);
        setTalon( talon) ;
        setValor(valor);
        setValorajustado(valorajustado);
        setTipocuenta(tipocuenta);
        setEstado(estado);
        setUsuarioautoriza(usuarioautoriza);
        setUsuariocrea(usuariocrea);
        setFechaaplica(fechaaplica);
        setError(error);

        return this;
        
            
    }
}
