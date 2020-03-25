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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author P-LAPULGAR
 */
@Entity
@Table(name = "TXMULTIFUNCIONALCHEQUE")

@NamedQueries( {
    @NamedQuery(
    name = "Txmultifuncionalcheque.registroUnico",
            query = "select object(txm) from Txmultifuncionalcheque txm where txm.txmultifuncionalchequePK.codigotransaccion = :codigotransaccion and txm.txmultifuncionalchequePK.codigocajero = :codigocajero and txm.txmultifuncionalchequePK.fechacajero = :fechacajero"),             
    @NamedQuery(
    name = "Txmultifuncionalcheque.todos", 
             query = "select object(txm) from Txmultifuncionalcheque txm")            
})


public class Txmultifuncionalcheque implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TxmultifuncionalchequePK txmultifuncionalchequePK;
    @Column(name = "NUMEROCORTE")
    private Integer numerocorte;    
    @Column(name = "CONSECUTIVOTRANSACCION")
    private Integer consecutivotransaccion;
    @Column(name = "FECHACIERRE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacierre;
    @Column(name = "ESTADO")
    private Integer estado;
    @Column(name = "CONSECUTIVOCHEQUECONSIGNACION")
    private Integer consecutivochequeconsignacion;
    @Column(name = "TIPOCUENTA")
    private String tipocuenta;
    @Column(name = "NUMEROCUENTACONSIGNAR")
    private String numerocuentaconsignar;
    @Column(name = "NUMEROCUENTAHOMOLOGA")
    private String numerocuentahomologa;
    @Column(name = "CHEQUEPROPIO")
    private String chequepropio;
    @Column(name = "RUTA")
    private String ruta;
    @Column(name = "TRANSITO")
    private String transito;
    @Column(name = "CUENTA")
    private String cuenta;
    @Column(name = "SERIAL")
    private Long serial;
    @Column(name = "OFICINA")
    private Integer oficina;
    @Column(name = "VALORCHEQUEUSUARIO")
    private Long valorchequeusuario;
    @Column(name = "SECUENCIA")
    private Integer secuencia;
    @Column(name = "VERSION")
    private Integer version;
    @JoinColumn(name = "IDREGISTROCONTROL", referencedColumnName = "IDREGISTRO")
    @ManyToOne
    private Txcontrolmulticheque idregistrocontrol;

    public Txmultifuncionalcheque() {
    }

    public Txmultifuncionalcheque(TxmultifuncionalchequePK txmultifuncionalchequePK) {
        this.txmultifuncionalchequePK = txmultifuncionalchequePK;
    }

    public Txmultifuncionalcheque(Integer codigotransaccion, Integer codigocajero, Date fechacajero) {
        this.txmultifuncionalchequePK = new TxmultifuncionalchequePK(codigotransaccion, codigocajero, fechacajero);
    }

    public TxmultifuncionalchequePK getTxmultifuncionalchequePK() {
        return txmultifuncionalchequePK;
    }

    public void setTxmultifuncionalchequePK(TxmultifuncionalchequePK txmultifuncionalchequePK) {
        this.txmultifuncionalchequePK = txmultifuncionalchequePK;
    }

    

    public Integer getConsecutivotransaccion() {
        return consecutivotransaccion;
    }

    public void setConsecutivotransaccion(Integer consecutivotransaccion) {
        this.consecutivotransaccion = consecutivotransaccion;
    }

    public Date getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(Date fechacierre) {
        this.fechacierre = fechacierre;
    }

   

    public String getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(String tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public String getNumerocuentaconsignar() {
        return numerocuentaconsignar;
    }

    public void setNumerocuentaconsignar(String numerocuentaconsignar) {
        this.numerocuentaconsignar = numerocuentaconsignar;
    }

    public String getNumerocuentahomologa() {
        return numerocuentahomologa;
    }

    public void setNumerocuentahomologa(String numerocuentahomologa) {
        this.numerocuentahomologa = numerocuentahomologa;
    }

    public String getChequepropio() {
        return chequepropio;
    }

    public void setChequepropio(String chequepropio) {
        this.chequepropio = chequepropio;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTransito() {
        return transito;
    }

    public void setTransito(String transito) {
        this.transito = transito;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Long getSerial() {
        return serial;
    }

    public void setSerial(Long serial) {
        this.serial = serial;
    }

   

    public Long getValorchequeusuario() {
        return valorchequeusuario;
    }

    public void setValorchequeusuario(Long valorchequeusuario) {
        this.valorchequeusuario = valorchequeusuario;
    }
    
    public Txcontrolmulticheque getIdregistrocontrol() {
        return idregistrocontrol;
    }

    public void setIdregistrocontrol(Txcontrolmulticheque idregistrocontrol) {
        this.idregistrocontrol = idregistrocontrol;
    }
    
    public Integer getConsecutivochequeconsignacion() {
        return consecutivochequeconsignacion;
    }

    public void setConsecutivochequeconsignacion(Integer consecutivochequeconsignacion) {
        this.consecutivochequeconsignacion = consecutivochequeconsignacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getNumerocorte() {
        return numerocorte;
    }

    public void setNumerocorte(Integer numerocorte) {
        this.numerocorte = numerocorte;
    }

    public Integer getOficina() {
        return oficina;
    }

    public void setOficina(Integer oficina) {
        this.oficina = oficina;
    }

    public Integer getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txmultifuncionalchequePK != null ? txmultifuncionalchequePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Txmultifuncionalcheque)) {
            return false;
        }
        Txmultifuncionalcheque other = (Txmultifuncionalcheque) object;
        if ((this.txmultifuncionalchequePK == null && other.txmultifuncionalchequePK != null) || (this.txmultifuncionalchequePK != null && !this.txmultifuncionalchequePK.equals(other.txmultifuncionalchequePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Txmultifuncionalcheque[txmultifuncionalchequePK=" + txmultifuncionalchequePK + "]";
    }
    
    public Txmultifuncionalcheque actualizarEntity(Txmultifuncionalcheque obj) {          
        setTxmultifuncionalchequePK(obj.txmultifuncionalchequePK);
        setNumerocorte(obj.numerocorte);       
        setConsecutivotransaccion(obj.consecutivotransaccion);    
        setFechacierre(obj.fechacierre);   
        setEstado(obj.estado);    
        setConsecutivochequeconsignacion(obj.consecutivochequeconsignacion);    
        setTipocuenta(obj.tipocuenta);   
        setNumerocuentaconsignar(obj.numerocuentaconsignar);   
        setNumerocuentahomologa(obj.numerocuentahomologa);   
        setChequepropio(obj.chequepropio);    
        setRuta(obj.ruta);    
        setTransito(obj.transito);    
        setCuenta(obj.cuenta);    
        setSerial(obj.serial);   
        setOficina(obj.oficina);   
        setValorchequeusuario(obj.valorchequeusuario);    
        setSecuencia(obj.secuencia);   
        setVersion(obj.version);
    
        return this;
            
    }
    

}
