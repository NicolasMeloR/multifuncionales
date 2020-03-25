/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PGECORDO
 */
@Entity
@Table(name = "CARMAS_NOTDEB_TEMP")
//@XmlRootElement
/*
@NamedQueries({
    @NamedQuery(name = "carmas_notdeb_temp.Todos",
            query = "select object(obj) from Reintegros obj "
            + " where (obj.causal='55')"
            + " order by obj.causal, obj.fecha")
})
*/
public class CarmasNotasDebitoTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "CONSECUTIVO")
    private BigDecimal consecutivo;
    //private String consecutivo;
    @GeneratedValue(strategy = GenerationType.AUTO)
    //private Long id;
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

    public CarmasNotasDebitoTemp() {
    }

    public CarmasNotasDebitoTemp(BigDecimal consecutivo) {
        this.consecutivo = consecutivo;
    }

    public BigDecimal getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(BigDecimal consecutivo) {
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consecutivo != null ? consecutivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarmasNotasDebitoTemp)) {
            return false;
        }
        CarmasNotasDebitoTemp other = (CarmasNotasDebitoTemp) object;
        if ((this.consecutivo == null && other.consecutivo != null) || (this.consecutivo != null && !this.consecutivo.equals(other.consecutivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.CarmasReintegrosTemp[ consecutivo=" + consecutivo + " ]";
    }
    
}
