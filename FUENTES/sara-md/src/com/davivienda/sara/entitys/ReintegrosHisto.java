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
 * @author AA.Garcia
 */
@Entity
@Table(name = "REINTEGROS_HISTO")
@NamedQueries({
    @NamedQuery(name = "ReintegrosHisto.RegistroUnico",
            query = "select object(obj) from ReintegrosHisto obj "
            + "where obj.reintegrosPK.hCodigocajero = :codigoCajero and "
            + "obj.reintegrosPK.hFechasistema = :fechaSistema and "
            + "obj.reintegrosPK.hTalon = :talon and "
            + "(obj.difeDescuadre != 'N' or obj.difeDescuadre is NULL )"),
    @NamedQuery(name = "ReintegrosHisto.Todos",
            query = "select object(obj) from ReintegrosHisto obj "
            + " where (obj.difeDescuadre != 'N' or obj.difeDescuadre is NULL )"
            + " order by obj.reintegrosPK.hCodigocajero, obj.reintegrosPK.hFechasistema"),
    @NamedQuery(name = "ReintegrosHisto.CajeroFecha",
            query = "select object(obj) from ReintegrosHisto obj "
            + "        where obj.reintegrosPK.hCodigocajero =:codigocajero and "
            + "              obj.hFecha between :fechaInicial and :fechaFinal  and"
            + " (obj.difeDescuadre != 'N' or obj.difeDescuadre is NULL )"
            + "        order by obj.tFechatransaccion"),
    @NamedQuery(name = "ReintegrosHisto.RangoFecha",
            query = "select object(obj) from ReintegrosHisto obj "
            + "        where  obj.hFecha between :fechaInicial and :fechaFinal and"
            + " (obj.difeDescuadre != 'N' or obj.difeDescuadre is NULL )"
            + "        order by obj.tFechatransaccion"),
    @NamedQuery(name = "ReintegrosHisto.RangoFechasXCajero",
            query = "select object(obj) from ReintegrosHisto obj "
            + "        where  obj.reintegrosPK.hCodigocajero = :codigoCajero "
            + "        and  obj.reintegrosPK.hFechasistema between :fechaInicial and :fechaFinal "
            + "        and  obj.hFecha between :fechaInicial and :fechaFinal  and"
            + " (obj.difeDescuadre != 'N' or obj.difeDescuadre is NULL )"),
    @NamedQuery(name = "ReintegrosHisto.CajeroTalonCuenta",
            query = "select object(obj) from ReintegrosHisto obj "
            + "        where  obj.reintegrosPK.hFechasistema between :fechaInicial and :fechaFinal and"
            + "        obj.reintegrosPK.hCodigocajero = :codigoCajero and "
            + "        obj.reintegrosPK.hTalon = :talon and "
            + "        obj.hNumerocuenta = :numeroCuenta and "
            + "        obj.hValor = :valor and"
            + " (obj.difeDescuadre != 'N' or obj.difeDescuadre is NULL )"
            + "        order by obj.tFechatransaccion"),
    @NamedQuery(name = "ReintegrosHisto.CreadosXCajero",
            //query = "select obj.reintegrosPK.hCodigocajero ,count(obj.reintegrosPK.hCodigocajero)  from ReintegrosHisto obj " +
            query = "select obj.reintegrosPK.hCodigocajero   from ReintegrosHisto obj "
            + "        where  obj.hFecha between :fechaInicial and :fechaFinal and "
            + "        obj.estadoreintegro = 9 and"
            + " (obj.difeDescuadre != 'N' or obj.difeDescuadre is NULL )"
            + "        group by  obj.reintegrosPK.hCodigocajero order by  obj.reintegrosPK.hCodigocajero")
})
public class ReintegrosHisto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReintegrosHistoPK reintegrosPK;
    @Column(name = "H_NUMEROCUENTA")
    private String hNumerocuenta;
    @Column(name = "H_DATOSTARJETA")
    private String hDatostarjeta;
    @Column(name = "H_FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date hFecha;
    @Column(name = "H_TIPOTRANSACCION", nullable = false)
    private Integer hTipotransaccion;
    @Column(name = "H_CODIGOOCCA", nullable = false)
    private Integer hCodigoocca;
    @Column(name = "H_INDICES")
    private String hIndices;
    @Column(name = "H_TIPOTARJETA")
    private String hTipotarjeta;
    @Column(name = "H_VALOR")
    private Long hValor;
    @Column(name = "H_FILLER")
    private String hFiller;
    @Column(name = "T_CODIGOCAJERO", nullable = false)
    private Integer tCodigocajero;
    @Column(name = "T_NUMEROTRANSACCION", nullable = false)
    private Integer tNumerotransaccion;
    @Column(name = "T_FECHATRANSACCION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tFechatransaccion;
    @Column(name = "T_TIPOTRANSACCION", nullable = false)
    private Integer tTipotransaccion;
    @Column(name = "T_CODIGOTRANSACCION", nullable = false)
    private Integer tCodigotransaccion;
    @Column(name = "T_ERRORTRANSACCION")
    private String tErrortransaccion;
    @Column(name = "T_VALORSOLICITADO")
    private Long tValorsolicitado;
    @Column(name = "T_VALORENTREGADO")
    private Long tValorentregado;
    @Column(name = "T_TARJETA")
    private String tTarjeta;
    @Column(name = "T_CUENTA")
    private String tCuenta;
    @Column(name = "T_CODIGOTERMINACIONTRANSACCION")
    private String tCodigoterminaciontransaccion;
    @Column(name = "T_REFERENCIA")
    private String tReferencia;
    @Column(name = "VALORAJUSTADO")
    private Long valorajustado;
    @Column(name = "ESTADOREINTEGRO")
    private Integer estadoreintegro;
    @Column(name = "USUARIOREVISA")
    private String usuariorevisa;
    @Column(name = "USUARIOAUTORIZA")
    private String usuarioautoriza;
    @Column(name = "FECHAREINTEGRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechareintegro;
    @Column(name = "ERRORREINTEGRO")
    private String errorreintegro;
    @Column(name = "TIPOCUENTAREINTEGRO")
    private Integer tipocuentareintegro;
    @Column(name = "H_OFI_RADICACION")
    private Integer hOfiRadicacion;
    @Column(name = "H_NUMEROCUENTA_ORIGEN")
    private String hNumerocuentaOrigen;
    @Column(name = "DISPENSED")
    private String dispensed;
    @Column(name = "REMAINING")
    private String remaining;
    @Column(name = "DIFERENCIA_DESCUADRE")
    private String difeDescuadre;

    public ReintegrosHisto() {
    }

    public ReintegrosHisto(ReintegrosHistoPK reintegrosPK) {
        this.reintegrosPK = reintegrosPK;
    }

    public ReintegrosHisto(ReintegrosHistoPK reintegrosPK, Date hFecha, Integer hTipotransaccion, Integer hCodigoocca, Integer tCodigocajero, Integer tNumerotransaccion, Date tFechatransaccion, Integer tTipotransaccion, Integer tCodigotransaccion, Integer tipocuentareintegro) {
        this.reintegrosPK = reintegrosPK;
        this.hFecha = hFecha;
        this.hTipotransaccion = hTipotransaccion;
        this.hCodigoocca = hCodigoocca;
        this.tCodigocajero = tCodigocajero;
        this.tNumerotransaccion = tNumerotransaccion;
        this.tFechatransaccion = tFechatransaccion;
        this.tTipotransaccion = tTipotransaccion;
        this.tCodigotransaccion = tCodigotransaccion;
        this.tipocuentareintegro = tipocuentareintegro;
    }

    public ReintegrosHisto(Integer hCodigocajero, Date hFechasistema, Integer hTalon) {
        this.reintegrosPK = new ReintegrosHistoPK(hCodigocajero, hFechasistema, hTalon);
    }

    public ReintegrosHistoPK getReintegrosHistoPK() {
        return reintegrosPK;
    }

    public void setReintegrosHistoPK(ReintegrosHistoPK reintegrosPK) {
        this.reintegrosPK = reintegrosPK;
    }

    public String getHNumerocuenta() {
        return hNumerocuenta;
    }

    public void setHNumerocuenta(String hNumerocuenta) {
        this.hNumerocuenta = hNumerocuenta;
    }

    public String getHDatostarjeta() {
        return hDatostarjeta;
    }

    public void setHDatostarjeta(String hDatostarjeta) {
        this.hDatostarjeta = hDatostarjeta;
    }

    public Date getHFecha() {
        return hFecha;
    }

    public void setHFecha(Date hFecha) {
        this.hFecha = hFecha;
    }

    public Integer getHTipotransaccion() {
        return hTipotransaccion;
    }

    public void setHTipotransaccion(Integer hTipotransaccion) {
        this.hTipotransaccion = hTipotransaccion;
    }

    public Integer getHCodigoocca() {
        return hCodigoocca;
    }

    public void setHCodigoocca(Integer hCodigoocca) {
        this.hCodigoocca = hCodigoocca;
    }

    public String getHIndices() {
        return hIndices;
    }

    public void setHIndices(String hIndices) {
        this.hIndices = hIndices;
    }

    public String getHTipotarjeta() {
        return hTipotarjeta;
    }

    public void setHTipotarjeta(String hTipotarjeta) {
        this.hTipotarjeta = hTipotarjeta;
    }

    public Long getHValor() {
        return hValor;
    }

    public void setHValor(Long hValor) {
        this.hValor = hValor;
    }

    public String getHFiller() {
        return hFiller;
    }

    public void setHFiller(String hFiller) {
        this.hFiller = hFiller;
    }

    public Integer getTCodigocajero() {
        return tCodigocajero;
    }

    public void setTCodigocajero(Integer tCodigocajero) {
        this.tCodigocajero = tCodigocajero;
    }

    public Integer getTNumerotransaccion() {
        return tNumerotransaccion;
    }

    public void setTNumerotransaccion(Integer tNumerotransaccion) {
        this.tNumerotransaccion = tNumerotransaccion;
    }

    public Date getTFechatransaccion() {
        return tFechatransaccion;
    }

    public void setTFechatransaccion(Date tFechatransaccion) {
        this.tFechatransaccion = tFechatransaccion;
    }

    public Integer getTTipotransaccion() {
        return tTipotransaccion;
    }

    public void setTTipotransaccion(Integer tTipotransaccion) {
        this.tTipotransaccion = tTipotransaccion;
    }

    public Integer getTCodigotransaccion() {
        return tCodigotransaccion;
    }

    public void setTCodigotransaccion(Integer tCodigotransaccion) {
        this.tCodigotransaccion = tCodigotransaccion;
    }

    public String getTErrortransaccion() {
        return tErrortransaccion;
    }

    public void setTErrortransaccion(String tErrortransaccion) {
        this.tErrortransaccion = tErrortransaccion;
    }

    public Long getTValorsolicitado() {
        return tValorsolicitado;
    }

    public void setTValorsolicitado(Long tValorsolicitado) {
        this.tValorsolicitado = tValorsolicitado;
    }

    public Long getTValorentregado() {
        return tValorentregado;
    }

    public void setTValorentregado(Long tValorentregado) {
        this.tValorentregado = tValorentregado;
    }

    public String getTTarjeta() {
        return tTarjeta;
    }

    public void setTTarjeta(String tTarjeta) {
        this.tTarjeta = tTarjeta;
    }

    public String getTCuenta() {
        return tCuenta;
    }

    public void setTCuenta(String tCuenta) {
        this.tCuenta = tCuenta;
    }

    public String getTCodigoterminaciontransaccion() {
        return tCodigoterminaciontransaccion;
    }

    public void setTCodigoterminaciontransaccion(String tCodigoterminaciontransaccion) {
        this.tCodigoterminaciontransaccion = tCodigoterminaciontransaccion;
    }

    public String getTReferencia() {
        return tReferencia;
    }

    public void setTReferencia(String tReferencia) {
        this.tReferencia = tReferencia;
    }

    public Long getValorajustado() {
        return valorajustado;
    }

    public void setValorajustado(Long valorajustado) {
        this.valorajustado = valorajustado;
    }

    public Integer getEstadoreintegro() {
        return estadoreintegro;
    }

    public void setEstadoreintegro(Integer estadoreintegro) {
        this.estadoreintegro = estadoreintegro;
    }

    public String getUsuariorevisa() {
        return usuariorevisa;
    }

    public void setUsuariorevisa(String usuariorevisa) {
        this.usuariorevisa = usuariorevisa;
    }

    public String getUsuarioautoriza() {
        return usuarioautoriza;
    }

    public void setUsuarioautoriza(String usuarioautoriza) {
        this.usuarioautoriza = usuarioautoriza;
    }

    public Date getFechareintegro() {
        return fechareintegro;
    }

    public void setFechareintegro(Date fechareintegro) {
        this.fechareintegro = fechareintegro;
    }

    public String getErrorreintegro() {
        return errorreintegro;
    }

    public void setErrorreintegro(String errorreintegro) {
        this.errorreintegro = errorreintegro;
    }

    public Integer getTipoCuentaReintegro() {
        return tipocuentareintegro;
    }

    public void setTipoCuentaReintegro(Integer tipocuentareintegro) {
        this.tipocuentareintegro = tipocuentareintegro;
    }

    public Integer getHOfiRadicacion() {
        return hOfiRadicacion;
    }

    public void setHOfiRadicacion(Integer hOfiRadicacion) {
        this.hOfiRadicacion = hOfiRadicacion;
    }

    public String gethNumerocuentaOrigen() {
        return hNumerocuentaOrigen;
    }

    public void sethNumerocuentaOrigen(String hNumerocuentaOrigen) {
        this.hNumerocuentaOrigen = hNumerocuentaOrigen;
    }

    public String getDispensed() {
        return dispensed;
    }

    public void setDispensed(String dispensed) {
        this.dispensed = dispensed;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getDifeDescuadre() {
        return difeDescuadre;
    }

    public void setDifeDescuadre(String difeDescuadre) {
        this.difeDescuadre = difeDescuadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reintegrosPK != null ? reintegrosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReintegrosHisto)) {
            return false;
        }
        ReintegrosHisto other = (ReintegrosHisto) object;
        if ((this.reintegrosPK == null && other.reintegrosPK != null) || (this.reintegrosPK != null && !this.reintegrosPK.equals(other.reintegrosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ReintegrosHisto[reintegrosPK=" + reintegrosPK + "]";
    }
}
