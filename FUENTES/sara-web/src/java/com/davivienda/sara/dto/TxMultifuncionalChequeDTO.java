/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import java.util.Date;

/**
 *
 * @author calvarez
 */
public class TxMultifuncionalChequeDTO {

    private Integer idRegistro;
    private Integer codigoTransaccion;
    private Integer codigoCajero;
    private Date fechaCajero;
    private Integer numerocorte;
    private Integer consecutivotransaccion;
    private Date fechacierre;
    private String estado;
    private Integer consecutivochequeconsignacion;
    private String tipocuenta;
    private String numerocuentaconsignar;
    private String numerocuentahomologa;
    private String chequepropio;
    private String ruta;
    private String transito;
    private String cuenta;
    private Long serial;
    private Integer oficina;
    private String valorchequeusuario;
    private Integer secuencia;
    private Integer version;

    private Long idregistroControl;

    private int plazaControl;
    private Date fechaarchivoControl;
    private String montoarchivoControl;
    private Integer cantidadchequesControl;
    private Integer versionControl;

    public Integer getNumerocorte() {
        return numerocorte;
    }

    public void setNumerocorte(Integer numerocorte) {
        this.numerocorte = numerocorte;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getConsecutivochequeconsignacion() {
        return consecutivochequeconsignacion;
    }

    public void setConsecutivochequeconsignacion(Integer consecutivochequeconsignacion) {
        this.consecutivochequeconsignacion = consecutivochequeconsignacion;
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

    public Integer getOficina() {
        return oficina;
    }

    public void setOficina(Integer oficina) {
        this.oficina = oficina;
    }

    public String getValorchequeusuario() {
        return valorchequeusuario;
    }

    public void setValorchequeusuario(String valorchequeusuario) {
        this.valorchequeusuario = valorchequeusuario;
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

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(Integer codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public Date getFechaCajero() {
        return fechaCajero;
    }

    public void setFechaCajero(Date fechaCajero) {
        this.fechaCajero = fechaCajero;
    }

    public Long getIdregistroControl() {
        return idregistroControl;
    }

    public void setIdregistroControl(Long idregistroControl) {
        this.idregistroControl = idregistroControl;
    }

    public int getPlazaControl() {
        return plazaControl;
    }

    public void setPlazaControl(int plazaControl) {
        this.plazaControl = plazaControl;
    }

    public Date getFechaarchivoControl() {
        return fechaarchivoControl;
    }

    public void setFechaarchivoControl(Date fechaarchivoControl) {
        this.fechaarchivoControl = fechaarchivoControl;
    }

    public String getMontoarchivoControl() {
        return montoarchivoControl;
    }

    public void setMontoarchivoControl(String montoarchivoControl) {
        this.montoarchivoControl = montoarchivoControl;
    }

    public Integer getCantidadchequesControl() {
        return cantidadchequesControl;
    }

    public void setCantidadchequesControl(Integer cantidadchequesControl) {
        this.cantidadchequesControl = cantidadchequesControl;
    }

    public Integer getVersionControl() {
        return versionControl;
    }

    public void setVersionControl(Integer versionControl) {
        this.versionControl = versionControl;
    }

    
}
