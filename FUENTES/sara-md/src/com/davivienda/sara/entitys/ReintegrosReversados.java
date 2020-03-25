/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "REINTEGROS_REVERSADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReintegrosReversados.findAll", query = "SELECT r FROM ReintegrosReversados r"),
    @NamedQuery(name = "ReintegrosReversados.findByNumerocuenta", query = "SELECT r FROM ReintegrosReversados r WHERE r.reintegrosReversadosPK.numerocuenta = :numerocuenta"),
    @NamedQuery(name = "ReintegrosReversados.findByHCodigocajero", query = "SELECT r FROM ReintegrosReversados r WHERE r.reintegrosReversadosPK.hCodigocajero = :hCodigocajero"),
    @NamedQuery(name = "ReintegrosReversados.findByHFechasistemaReintegro", query = "SELECT r FROM ReintegrosReversados r WHERE r.reintegrosReversadosPK.hFechasistemaReintegro = :hFechasistemaReintegro"),
    @NamedQuery(name = "ReintegrosReversados.findByHTalonReintegro", query = "SELECT r FROM ReintegrosReversados r WHERE r.reintegrosReversadosPK.hTalonReintegro = :hTalonReintegro"),
    @NamedQuery(name = "ReintegrosReversados.findByFecha", query = "SELECT r FROM ReintegrosReversados r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "ReintegrosReversados.findByCodigoocca", query = "SELECT r FROM ReintegrosReversados r WHERE r.codigoocca = :codigoocca"),
    @NamedQuery(name = "ReintegrosReversados.findByTalon", query = "SELECT r FROM ReintegrosReversados r WHERE r.talon = :talon"),
    @NamedQuery(name = "ReintegrosReversados.findByValor", query = "SELECT r FROM ReintegrosReversados r WHERE r.valor = :valor"),
    @NamedQuery(name = "ReintegrosReversados.findByValorajustado", query = "SELECT r FROM ReintegrosReversados r WHERE r.valorajustado = :valorajustado"),
    @NamedQuery(name = "ReintegrosReversados.findByTipocuenta", query = "SELECT r FROM ReintegrosReversados r WHERE r.tipocuenta = :tipocuenta"),
    @NamedQuery(name = "ReintegrosReversados.findByEstado", query = "SELECT r FROM ReintegrosReversados r WHERE r.estado = :estado"),
    @NamedQuery(name = "ReintegrosReversados.findByUsuariocrea", query = "SELECT r FROM ReintegrosReversados r WHERE r.usuariocrea = :usuariocrea"),
    @NamedQuery(name = "ReintegrosReversados.findByUsuarioautoriza", query = "SELECT r FROM ReintegrosReversados r WHERE r.usuarioautoriza = :usuarioautoriza"),
    @NamedQuery(name = "ReintegrosReversados.findByFechaaplica", query = "SELECT r FROM ReintegrosReversados r WHERE r.fechaaplica = :fechaaplica"),
    @NamedQuery(name = "ReintegrosReversados.findByError", query = "SELECT r FROM ReintegrosReversados r WHERE r.error = :error")})
public class ReintegrosReversados implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReintegrosReversadosPK reintegrosReversadosPK;
    @Column(name = "FECHA")
    private String fecha;
    @Column(name = "CODIGOOCCA")
    private String codigoocca;
    @Column(name = "TALON")
    private String talon;
    @Column(name = "VALOR")
    private String valor;
    @Column(name = "VALORAJUSTADO")
    private String valorajustado;
    @Column(name = "TIPOCUENTA")
    private String tipocuenta;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "USUARIOCREA")
    private String usuariocrea;
    @Column(name = "USUARIOAUTORIZA")
    private String usuarioautoriza;
    @Column(name = "FECHAAPLICA")
    private String fechaaplica;
    @Column(name = "ERROR")
    private String error;

    public ReintegrosReversados() {
    }

    public ReintegrosReversados(ReintegrosReversadosPK reintegrosReversadosPK) {
        this.reintegrosReversadosPK = reintegrosReversadosPK;
    }

    public ReintegrosReversados(String numerocuenta, String hCodigocajero, String hFechasistemaReintegro, String hTalonReintegro) {
        this.reintegrosReversadosPK = new ReintegrosReversadosPK(numerocuenta, hCodigocajero, hFechasistemaReintegro, hTalonReintegro);
    }

    public ReintegrosReversadosPK getReintegrosReversadosPK() {
        return reintegrosReversadosPK;
    }

    public void setReintegrosReversadosPK(ReintegrosReversadosPK reintegrosReversadosPK) {
        this.reintegrosReversadosPK = reintegrosReversadosPK;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodigoocca() {
        return codigoocca;
    }

    public void setCodigoocca(String codigoocca) {
        this.codigoocca = codigoocca;
    }

    public String getTalon() {
        return talon;
    }

    public void setTalon(String talon) {
        this.talon = talon;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorajustado() {
        return valorajustado;
    }

    public void setValorajustado(String valorajustado) {
        this.valorajustado = valorajustado;
    }

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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

    public String getFechaaplica() {
        return fechaaplica;
    }

    public void setFechaaplica(String fechaaplica) {
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
        hash += (reintegrosReversadosPK != null ? reintegrosReversadosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReintegrosReversados)) {
            return false;
        }
        ReintegrosReversados other = (ReintegrosReversados) object;
        if ((this.reintegrosReversadosPK == null && other.reintegrosReversadosPK != null) || (this.reintegrosReversadosPK != null && !this.reintegrosReversadosPK.equals(other.reintegrosReversadosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReintegrosReversados{" + "reintegrosReversadosPK=" + reintegrosReversadosPK + ", fecha=" + fecha + ", codigoocca=" + codigoocca + ", talon=" + talon + ", valor=" + valor + ", valorajustado=" + valorajustado + ", tipocuenta=" + tipocuenta + ", estado=" + estado + ", usuariocrea=" + usuariocrea + ", usuarioautoriza=" + usuarioautoriza + ", fechaaplica=" + fechaaplica + ", error=" + error + '}';
    }

    
    
}
