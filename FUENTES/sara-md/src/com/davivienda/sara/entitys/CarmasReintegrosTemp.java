/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
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
@Table(name = "CARMAS_REINTEGROS_TEMP")
/*
@XmlRootElement

@NamedQueries({
    @NamedQuery(name = "CarmasReintegrosTemp.getTodos", query = "SELECT c FROM CarmasReintegrosTemp c")
})

@NamedQueries({
    @NamedQuery(name = "carmas_reintegros_temp.Todos",
            query = "select object(obj) from carmas_reintegros_temp obj "
            + " where (obj.difeDescuadre != 'N' or obj.difeDescuadre is NULL )"
            + " order by obj.reintegrosPK.hCodigocajero, obj.reintegrosPK.hFechasistema")
})
 */
public class CarmasReintegrosTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CONSECUTIVO")
    private BigDecimal consecutivo;

    //public BigDecimal getIdCarmasReintegrosTemp() {
        //return consecutivo;
    //}
    //private String consecutivo;
    //@GeneratedValue(strategy = GenerationType.AUTO)
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

    public CarmasReintegrosTemp() {
    }

    public void CarmasReintegrosTemp(BigDecimal consecutivo) {
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
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.usuario);
        hash = 71 * hash + Objects.hashCode(this.causal);
        hash = 71 * hash + Objects.hashCode(this.nroIdentificacionDeudor);
        hash = 71 * hash + Objects.hashCode(this.codigoUnico);
        hash = 71 * hash + Objects.hashCode(this.nroCuenta);
        hash = 71 * hash + Objects.hashCode(this.tipoCuenta);
        hash = 71 * hash + Objects.hashCode(this.valor);
        hash = 71 * hash + Objects.hashCode(this.talon);
        hash = 71 * hash + Objects.hashCode(this.comision);
        hash = 71 * hash + Objects.hashCode(this.fecha);
        hash = 71 * hash + Objects.hashCode(this.oficinaRecuado);
        hash = 71 * hash + Objects.hashCode(this.errorTransaccion);
        hash = 71 * hash + Objects.hashCode(this.concepto);
        hash = 71 * hash + Objects.hashCode(this.tipo);
        hash = 71 * hash + Objects.hashCode(this.resultadoCarga);
        hash = 71 * hash + Objects.hashCode(this.consecutivo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CarmasReintegrosTemp other = (CarmasReintegrosTemp) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.causal, other.causal)) {
            return false;
        }
        if (!Objects.equals(this.nroIdentificacionDeudor, other.nroIdentificacionDeudor)) {
            return false;
        }
        if (!Objects.equals(this.codigoUnico, other.codigoUnico)) {
            return false;
        }
        if (!Objects.equals(this.nroCuenta, other.nroCuenta)) {
            return false;
        }
        if (!Objects.equals(this.tipoCuenta, other.tipoCuenta)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.talon, other.talon)) {
            return false;
        }
        if (!Objects.equals(this.comision, other.comision)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (!Objects.equals(this.oficinaRecuado, other.oficinaRecuado)) {
            return false;
        }
        if (!Objects.equals(this.errorTransaccion, other.errorTransaccion)) {
            return false;
        }
        if (!Objects.equals(this.concepto, other.concepto)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.resultadoCarga, other.resultadoCarga)) {
            return false;
        }
        if (!Objects.equals(this.consecutivo, other.consecutivo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CarmasReintegrosTemp{" + "consecutivo=" + consecutivo + ", usuario=" + usuario + ", causal=" + causal + ", nroIdentificacionDeudor=" + nroIdentificacionDeudor + ", codigoUnico=" + codigoUnico + ", nroCuenta=" + nroCuenta + ", tipoCuenta=" + tipoCuenta + ", valor=" + valor + ", talon=" + talon + ", comision=" + comision + ", fecha=" + fecha + ", oficinaRecuado=" + oficinaRecuado + ", errorTransaccion=" + errorTransaccion + ", concepto=" + concepto + ", tipo=" + tipo + ", resultadoCarga=" + resultadoCarga + '}';
    }

    

}
