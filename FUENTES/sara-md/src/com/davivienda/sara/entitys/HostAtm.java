/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
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
 * @author aa.garcia
 */
@Entity
@Table(name = "HOSTATM")
@NamedQueries({
    @NamedQuery(name = "HostAtm.RegistroUnico",
    query = "select object(obj) from HostAtm obj where obj.hostatmPK.codigocajero = :cajero"),
    @NamedQuery(name = "HostAtm.Todos",
    query = "select object(obj) from HostAtm obj order by obj.hostatmPK.codigocajero, obj.hostatmPK.fechasistema"),
    @NamedQuery(name = "HostAtm.CajeroTxFechaValor",
    query = "select object(obj) from HostAtm obj " +
    "        where obj.hostatmPK.codigocajero =:codigocajero and " +
    "              obj.fecha between :fechaInicial and :fechaFinal and" +
    "              obj.hostatmPK.talon = :talon and  obj.valor = :valor " +
    "        order by obj.fecha"),
    @NamedQuery(name = "HostAtm.CajeroTxFecha",
    query = "select object(obj) from HostAtm obj " +
    "        where obj.hostatmPK.codigocajero =:codigocajero and " +
    "              obj.fecha between :fechaInicial and :fechaFinal and" +
    "              obj.hostatmPK.talon = :talon " +
    "        order by obj.fecha"),
    @NamedQuery(name = "HostAtm.TalonXFecha",
    query = "select  obj.hostatmPK.talon from HostAtm obj " +
    "        where obj.hostatmPK.codigocajero =:codigocajero and " +
    "        obj.fecha between :fechaInicial and :fechaFinal " +
    "        order by obj.fecha")
})
public class HostAtm implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HostAtmPK hostatmPK;
    private String numerocuenta;
    private String datostarjeta;
    private Integer tipotransaccion;
    private Integer codigoocca;
    private String indices;
    private String tipotarjeta;
    private Long valor;
    private String filler;
    private Integer ofiRadicacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO", insertable = false, updatable = false)
    @ManyToOne
    private Cajero cajero;

    public HostAtm() {
    }

    public HostAtm(HostAtmPK hostatmPK) {
        this.hostatmPK = hostatmPK;
    }

    public HostAtm(HostAtmPK hostatmPK, Date fecha, Integer tipotransaccion, Integer codigoocca) {
        this.hostatmPK = hostatmPK;
        this.fecha = fecha;
        this.tipotransaccion = tipotransaccion;
        this.codigoocca = codigoocca;
    }

    public HostAtm(Integer codigocajero, Date fechasistema, Integer talon) {
        this.hostatmPK = new HostAtmPK(codigocajero, fechasistema, talon);
    }

    public HostAtmPK getHostAtmPK() {
        return hostatmPK;
    }

    public void setHostAtmPK(HostAtmPK hostatmPK) {
        this.hostatmPK = hostatmPK;
    }

    public String getNumeroCuenta() {
        return numerocuenta;
    }

    public void setNumeroCuenta(String numerocuenta) {
        this.numerocuenta = numerocuenta;
    }

    public String getDatosTarjeta() {
        return datostarjeta;
    }

    public void setDatosTarjeta(String datostarjeta) {
        this.datostarjeta = datostarjeta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTipoTransaccion() {
        return tipotransaccion;
    }

    public void setTipoTransaccion(Integer tipotransaccion) {
        this.tipotransaccion = tipotransaccion;
    }

    public Integer getCodigoOcca() {
        return codigoocca;
    }

    public void setCodigoOcca(Integer codigoocca) {
        this.codigoocca = codigoocca;
    }

    public String getIndices() {
        return indices;
    }

    public void setIndices(String indices) {
        this.indices = indices;
    }

    public String getTipoTarjeta() {
        return tipotarjeta;
    }

    public void setTipoTarjeta(String tipotarjeta) {
        this.tipotarjeta = tipotarjeta;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public Integer getOfiRadicacion() {
        return ofiRadicacion;
    }

    public void setOfiRadicacion(Integer ofiRadicacion) {
        this.ofiRadicacion = ofiRadicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hostatmPK != null ? hostatmPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HostAtm)) {
            return false;
        }
        HostAtm other = (HostAtm) object;
        if ((this.hostatmPK == null && other.hostatmPK != null) || (this.hostatmPK != null && !this.hostatmPK.equals(other.hostatmPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //OJO REVISAR SI ES HostAtm la a minsucula
        return "com.davivienda.sara.entitys.HostAtm[hostatmPK=" + hostatmPK + "]";
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }
}
