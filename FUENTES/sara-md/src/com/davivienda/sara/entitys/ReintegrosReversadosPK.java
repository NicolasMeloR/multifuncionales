/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Admin
 */
@Embeddable
public class ReintegrosReversadosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NUMEROCUENTA")
    private String numerocuenta;
    @Basic(optional = false)
    @Column(name = "H_CODIGOCAJERO")
    private String hCodigocajero;
    @Basic(optional = false)
    @Column(name = "H_FECHASISTEMA_REINTEGRO")
    private String hFechasistemaReintegro;
    @Basic(optional = false)
    @Column(name = "H_TALON_REINTEGRO")
    private String hTalonReintegro;

    public ReintegrosReversadosPK() {
    }

    public ReintegrosReversadosPK(String numerocuenta, String hCodigocajero, String hFechasistemaReintegro, String hTalonReintegro) {
        this.numerocuenta = numerocuenta;
        this.hCodigocajero = hCodigocajero;
        this.hFechasistemaReintegro = hFechasistemaReintegro;
        this.hTalonReintegro = hTalonReintegro;
    }

    public String getNumerocuenta() {
        return numerocuenta;
    }

    public void setNumerocuenta(String numerocuenta) {
        this.numerocuenta = numerocuenta;
    }

    public String getHCodigocajero() {
        return hCodigocajero;
    }

    public void setHCodigocajero(String hCodigocajero) {
        this.hCodigocajero = hCodigocajero;
    }

    public String getHFechasistemaReintegro() {
        return hFechasistemaReintegro;
    }

    public void setHFechasistemaReintegro(String hFechasistemaReintegro) {
        this.hFechasistemaReintegro = hFechasistemaReintegro;
    }

    public String getHTalonReintegro() {
        return hTalonReintegro;
    }

    public void setHTalonReintegro(String hTalonReintegro) {
        this.hTalonReintegro = hTalonReintegro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numerocuenta != null ? numerocuenta.hashCode() : 0);
        hash += (hCodigocajero != null ? hCodigocajero.hashCode() : 0);
        hash += (hFechasistemaReintegro != null ? hFechasistemaReintegro.hashCode() : 0);
        hash += (hTalonReintegro != null ? hTalonReintegro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReintegrosReversadosPK)) {
            return false;
        }
        ReintegrosReversadosPK other = (ReintegrosReversadosPK) object;
        if ((this.numerocuenta == null && other.numerocuenta != null) || (this.numerocuenta != null && !this.numerocuenta.equals(other.numerocuenta))) {
            return false;
        }
        if ((this.hCodigocajero == null && other.hCodigocajero != null) || (this.hCodigocajero != null && !this.hCodigocajero.equals(other.hCodigocajero))) {
            return false;
        }
        if ((this.hFechasistemaReintegro == null && other.hFechasistemaReintegro != null) || (this.hFechasistemaReintegro != null && !this.hFechasistemaReintegro.equals(other.hFechasistemaReintegro))) {
            return false;
        }
        if ((this.hTalonReintegro == null && other.hTalonReintegro != null) || (this.hTalonReintegro != null && !this.hTalonReintegro.equals(other.hTalonReintegro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CargarTimbresMasivosSessionLocal.controller.ReintegrosReversadosPK[ numerocuenta=" + numerocuenta + ", hCodigocajero=" + hCodigocajero + ", hFechasistemaReintegro=" + hFechasistemaReintegro + ", hTalonReintegro=" + hTalonReintegro + " ]";
    }
    
}
