/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CARMAS_REINTEGROS_TEMP")
//@Table(name = "CARGUES_MASIVOS_REINTEGROS_TEMP")
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findAll", query = "SELECT c FROM CarguesMasivosReintegrosTemp c"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByCausal", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.causal = :causal"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByNroIdentificacionDeudor", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.nroIdentificacionDeudor = :nroIdentificacionDeudor"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByCodigoUnico", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.codigoUnico = :codigoUnico"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByNroCuenta", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.nroCuenta = :nroCuenta"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByTipoCuenta", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.tipoCuenta = :tipoCuenta"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByValor", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.valor = :valor"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByTalon", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.talon = :talon"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByComision", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.comision = :comision"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByFecha", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByOficinaRecuado", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.oficinaRecuado = :oficinaRecuado"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByErrorTransaccion", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.errorTransaccion = :errorTransaccion"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByConcepto", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.concepto = :concepto"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByTipo", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByResultadoCarga", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.resultadoCarga = :resultadoCarga"),
    @NamedQuery(name = "CarguesMasivosReintegrosTemp.findByConsecutivo", query = "SELECT c FROM CarguesMasivosReintegrosTemp c WHERE c.consecutivo = :consecutivo")})

public class CarguesMasivosReintegrosTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "CAUSAL")
    private String causal;
    @Column(name = "NRO_IDENTIFICACION_DEUDOR")
    private String nroIdentificacionDeudor;
    @Column(name = "CODIGO_UNICO")
    private String codigoUnico;
    @Column(name = "NRO_CUENTA")
    private String nroCuenta;
    @Column(name = "TIPO_CUENTA")
    private String tipoCuenta;
    @Column(name = "VALOR")
    private String valor;
    @Column(name = "TALON")
    private String talon;
    @Column(name = "COMISION")
    private String comision;
    @Column(name = "FECHA")
    private String fecha;
    @Column(name = "OFICINA_RECAUDO")
    private String oficinaRecuado;
    @Column(name = "ERROR_TRANSACCION")
    private String errorTransaccion;
    @Column(name = "CONCEPTO")
    private String concepto;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "RESULTADO_CARGA")
    private String resultadoCarga;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CONSECUTIVO")
    private BigDecimal consecutivo;

    public CarguesMasivosReintegrosTemp() {
    }

    public CarguesMasivosReintegrosTemp(BigDecimal consecutivo) {
        this.consecutivo = consecutivo;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCausal() {
        return causal;
    }

    public void setCausal(String causal) {
        this.causal = causal;
    }

    public String getNroIdentificacionDeudor() {
        return nroIdentificacionDeudor;
    }

    public void setNroIdentificacionDeudor(String nroIdentificacionDeudor) {
        this.nroIdentificacionDeudor = nroIdentificacionDeudor;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTalon() {
        return talon;
    }

    public void setTalon(String talon) {
        this.talon = talon;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getOficinaRecuado() {
        return oficinaRecuado;
    }

    public void setOficinaRecuado(String oficinaRecuado) {
        this.oficinaRecuado = oficinaRecuado;
    }

    public String getErrorTransaccion() {
        return errorTransaccion;
    }

    public void setErrorTransaccion(String errorTransaccion) {
        this.errorTransaccion = errorTransaccion;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getResultadoCarga() {
        return resultadoCarga;
    }

    public void setResultadoCarga(String resultadoCarga) {
        this.resultadoCarga = resultadoCarga;
    }

    public BigDecimal getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(BigDecimal consecutivo) {
        this.consecutivo = consecutivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consecutivo != null ? consecutivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarguesMasivosReintegrosTemp)) {
            return false;
        }
        CarguesMasivosReintegrosTemp other = (CarguesMasivosReintegrosTemp) object;
        if ((this.consecutivo == null && other.consecutivo != null) || (this.consecutivo != null && !this.consecutivo.equals(other.consecutivo))) {
            return false;
        }
        return true;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return "CarguesMasivosReintegrosTemp{" + "causal=" + causal + ", nroIdentificacionDeudor=" + nroIdentificacionDeudor + ", codigoUnico=" + codigoUnico + ", nroCuenta=" + nroCuenta + ", tipoCuenta=" + tipoCuenta + ", valor=" + valor + ", talon=" + talon + ", comision=" + comision + ", fecha=" + fecha + ", oficinaRecuado=" + oficinaRecuado + ", errorTransaccion=" + errorTransaccion + ", concepto=" + concepto + ", tipo=" + tipo + ", resultadoCarga=" + resultadoCarga + ", consecutivo=" + consecutivo + '}';
    }
}
