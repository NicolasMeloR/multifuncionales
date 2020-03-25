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

/**
 *
 * @author aa.garcia
 */
@Entity
@Table(name = "TRANSACCION_TEMP")
@NamedQueries( {
    @NamedQuery(
    name = "TransaccionTemp.RegistroUnico",
            query = "select object(edc) from TransaccionTemp edc where edc.transaccionTempPK.codigocajero = :codigoCajero"),
            @NamedQuery(
            name = "TransaccionTemp.Todos",
            query = "select object(edc) from TransaccionTemp edc order by edc.transaccionTempPK.codigocajero, edc.transaccionTempPK.fechatransaccion"),
            @NamedQuery(
            name = "TransaccionTemp.CajeroRangoFecha",
            query = "select object(obj) from TransaccionTemp obj " +
            "        where obj.transaccionTempPK.codigocajero =:codigoCajero and " +
            "              obj.transaccionTempPK.fechatransaccion between :fechaInicial and :fechaFinal " +
            "        order by obj.transaccionTempPK.fechatransaccion"),
            @NamedQuery(
            name = "TransaccionTemp.CajeroTransaccionTempFecha",
            query = "select object(obj) from TransaccionTemp obj " +
            "        where obj.transaccionTempPK.codigocajero =:codigoCajero and " +
            "              obj.transaccionTempPK.fechatransaccion between :fechaInicial and :fechaFinal and" +
            "              obj.transaccionTempPK.numerotransaccion = :numerotransaccion" +
            "        order by obj.transaccionTempPK.fechatransaccion"),
            @NamedQuery(
            name = "TransaccionTemp.TransaccionTempBuscarReintegros",
            query = "select object(obj) from TransaccionTemp obj " +
            "        where obj.transaccionTempPK.codigocajero =:codigoCajero and " +
            "              obj.transaccionTempPK.fechatransaccion between :fechaInicial and :fechaFinal and " +
            "              obj.codigoterminaciontransaccion  IN ('0121','0030','0131') "  +
            "        order by obj.transaccionTempPK.fechatransaccion"),
            @NamedQuery(
            name = "TransaccionTemp.TransaccionTempBuscarReintegrosXCajero",
            query = "select object(obj) from TransaccionTemp obj " +
            "        where obj.transaccionTempPK.codigocajero =:codigoCajero and " +
            "        obj.codigoterminaciontransaccion  IN ('0121','0030','0131') "  +
            "        order by obj.transaccionTempPK.fechatransaccion"),
            @NamedQuery(
            name = "TransaccionTemp.minFechaTransaccion",
            query = "select min(obj.transaccionTempPK.fechatransaccion)  from TransaccionTemp obj " +
            "        where obj.transaccionTempPK.codigocajero =:codigoCajero ")


})
public class TransaccionTemp implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TransaccionTempPK transaccionTempPK;
    @Column(name = "TIPOTRANSACCION", nullable = false)
    private Integer tipotransaccion;
    @Column(name = "CODIGOTRANSACCION", nullable = false)
    private Integer codigotransaccion;
    @Column(name = "ERRORTRANSACCION")
    private String errortransaccion;
    @Column(name = "VALORSOLICITADO")
    private Long valorsolicitado;
    @Column(name = "VALORENTREGADO")
    private Long valorentregado;
    @Column(name = "TARJETA")
    private String tarjeta;
    @Column(name = "CUENTA")
    private String cuenta;
    @Column(name = "CODIGOTERMINACIONTRANSACCION")
    private String codigoterminaciontransaccion;
    @Column(name = "REFERENCIA")
    private String referencia;
    @Column(name = "VERSION")
    private Integer version;

    public TransaccionTemp() {
    }

    public TransaccionTemp(TransaccionTempPK transaccionTempPK) {
        this.transaccionTempPK = transaccionTempPK;
    }

    public TransaccionTemp(TransaccionTempPK transaccionTempPK, Integer tipotransaccion, Integer codigotransaccion) {
        this.transaccionTempPK = transaccionTempPK;
        this.tipotransaccion = tipotransaccion;
        this.codigotransaccion = codigotransaccion;
    }

    public TransaccionTemp(Integer codigocajero, Integer numerotransaccion, Date fechatransaccion) {
        this.transaccionTempPK = new TransaccionTempPK(codigocajero, numerotransaccion, fechatransaccion);
    }

    public TransaccionTempPK getTransaccionTempPK() {
        return transaccionTempPK;
    }

    public void setTransaccionTempPK(TransaccionTempPK transaccionTempPK) {
        this.transaccionTempPK = transaccionTempPK;
    }

    public Integer getTipotransaccion() {
        return tipotransaccion;
    }

    public void setTipotransaccion(Integer tipotransaccion) {
        this.tipotransaccion = tipotransaccion;
    }

    public Integer getCodigotransaccion() {
        return codigotransaccion;
    }

    public void setCodigotransaccion(Integer codigotransaccion) {
        this.codigotransaccion = codigotransaccion;
    }

    public String getErrortransaccion() {
        return errortransaccion;
    }

    public void setErrortransaccion(String errortransaccion) {
        this.errortransaccion = errortransaccion;
    }

    public Long getValorsolicitado() {
        return valorsolicitado;
    }

    public void setValorsolicitado(Long valorsolicitado) {
        this.valorsolicitado = valorsolicitado;
    }

    public Long getValorentregado() {
        return valorentregado;
    }

    public void setValorentregado(Long valorentregado) {
        this.valorentregado = valorentregado;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getCodigoterminaciontransaccion() {
        return codigoterminaciontransaccion;
    }

    public void setCodigoterminaciontransaccion(String codigoterminaciontransaccion) {
        this.codigoterminaciontransaccion = codigoterminaciontransaccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
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
        hash += (transaccionTempPK != null ? transaccionTempPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionTemp)) {
            return false;
        }
        TransaccionTemp other = (TransaccionTemp) object;
        if ((this.transaccionTempPK == null && other.transaccionTempPK != null) || (this.transaccionTempPK != null && !this.transaccionTempPK.equals(other.transaccionTempPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.TransaccionTemp[transaccionTempPK=" + transaccionTempPK + "]";
    }

}
