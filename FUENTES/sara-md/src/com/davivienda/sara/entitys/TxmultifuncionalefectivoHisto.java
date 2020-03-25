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
 * @author P-LAPULGAR
 */
@Entity
@Table(name = "TXMULTIFUNCIONALEFECTIVO_HISTO")

@NamedQueries( {
    @NamedQuery(
    name = "TxmultifuncionalefectivoHisto.registroUnico",
            query = "select object(txm) from TxmultifuncionalefectivoHisto txm where txm.txmultifuncionalefectivoPK.codigotx = :codigotx and txm.txmultifuncionalefectivoPK.codigocajero = :codigocajero and txm.txmultifuncionalefectivoPK.fechacajero = :fechacajero"),             
    @NamedQuery(
    name = "TxmultifuncionalefectivoHisto.todos", 
             query = "select object(txm) from TxmultifuncionalefectivoHisto txm"),
    @NamedQuery(
            name = "TxmultifuncionalefectivoHisto.TransaccionBuscarReintegros",
            query = "select object(obj) from TxmultifuncionalefectivoHisto obj " +
            "        where obj.txmultifuncionalefectivoPK.codigocajero =:codigoCajero and " +
            "              obj.txmultifuncionalefectivoPK.fechacajero between :fechaInicial and :fechaFinal and " +
            "              obj.txmultifuncionalefectivoPK.codigotx <> 9820 and "  +
            "              obj.estado  =:estado and "  +
            "              obj.valorconsignacion <> 0  "  +
            "        order by obj.txmultifuncionalefectivoPK.fechacajero"),
    @NamedQuery(
            name = "TxmultifuncionalefectivoHisto.TransaccionReintegrosXTalon",
            query = "select object(obj) from TxmultifuncionalefectivoHisto obj " +
            "        where obj.txmultifuncionalefectivoPK.codigocajero =:codigoCajero and " +
            "              obj.txmultifuncionalefectivoPK.fechacajero between :fechaInicial and :fechaFinal and " +
            "              obj.transaccionconsecutivo  =:talon "  +
            "        order by obj.txmultifuncionalefectivoPK.fechacajero"),
    @NamedQuery(
            name = "TxmultifuncionalefectivoHisto.TransaccionesTalonUnico",
            query = "select COUNT(obj.transaccionconsecutivo),obj.transaccionconsecutivo from TxmultifuncionalefectivoHisto obj " +
            "        where obj.txmultifuncionalefectivoPK.codigocajero =:codigoCajero and " +
            "              obj.txmultifuncionalefectivoPK.fechacajero between :fechaInicial and :fechaFinal  " +
                "        group by obj.transaccionconsecutivo HAVING COUNT(obj.transaccionconsecutivo)=1 order by obj.transaccionconsecutivo")       
            
            
})



public class TxmultifuncionalefectivoHisto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TxmultifuncionalefectivoHistoPK txmultifuncionalefectivoPK;
    @Column(name = "ESTADO")
    private Integer estado;    
    @Column(name = "NUMEROCORTE")
    private Integer numerocorte;
    @Column(name = "TRANSACCIONCONSECUTIVO")
    private Integer transaccionconsecutivo;
    @Column(name = "FECHACIERRE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacierre;
    @Column(name = "TIPOCUENTA")
    private Short tipocuenta;
    @Column(name = "NUMEROCUENTACONSIGNAR")
    private String numerocuentaconsignar;
    @Column(name = "NUMEROCUENTAHOMOLOGA")
    private String numerocuentahomologa;
    @Column(name = "VALORCONSIGNACION")
    private Long valorconsignacion;
    @Column(name = "NOBILLETESND")
    private Integer nobilletesnd;
    @Column(name = "NOBILLETESDENOMINACIONF")
    private Integer nobilletesdenominacionf;
    @Column(name = "NOBILLETESDENOMINACIONE")
    private Integer nobilletesdenominacione;
    @Column(name = "NOBILLETESDENOMINACIOND")
    private Integer nobilletesdenominaciond;
    @Column(name = "NOBILLETESDENOMINACIONC")
    private Integer nobilletesdenominacionc;
    @Column(name = "NOBILLETESDENOMINACIONB")
    private Integer nobilletesdenominacionb;
    @Column(name = "NOBILLETESDENOMINACIONA")
    private Integer nobilletesdenominaciona;
    @Column(name = "TOTALBILLETESCONSIGNADOS")
    private Integer totalbilletesconsignados;
    @Column(name = "SECUENCIA", nullable = false)
    private Long secuencia;
    @Column(name = "VERSION")
    private Integer version;

    public TxmultifuncionalefectivoHisto() {
    }

    public TxmultifuncionalefectivoHisto(TxmultifuncionalefectivoHistoPK txmultifuncionalefectivoPK) {
        this.txmultifuncionalefectivoPK = txmultifuncionalefectivoPK;
    }

    public TxmultifuncionalefectivoHisto(TxmultifuncionalefectivoHistoPK txmultifuncionalefectivoPK, long secuencia) {
        this.txmultifuncionalefectivoPK = txmultifuncionalefectivoPK;
        this.secuencia = secuencia;
    }

    public TxmultifuncionalefectivoHisto(Integer codigotx, Integer codigocajero, Date fechacajero) {
        this.txmultifuncionalefectivoPK = new TxmultifuncionalefectivoHistoPK(codigotx, codigocajero, fechacajero);
    }

    public TxmultifuncionalefectivoHistoPK getTxmultifuncionalefectivoHistoPK() {
        return txmultifuncionalefectivoPK;
    }

    public void setTxmultifuncionalefectivoHistoPK(TxmultifuncionalefectivoHistoPK txmultifuncionalefectivoPK) {
        this.txmultifuncionalefectivoPK = txmultifuncionalefectivoPK;
    }

    
    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(Date fechacierre) {
        this.fechacierre = fechacierre;
    }

    public Integer getNobilletesdenominaciona() {
        return nobilletesdenominaciona;
    }

    public void setNobilletesdenominaciona(Integer nobilletesdenominaciona) {
        this.nobilletesdenominaciona = nobilletesdenominaciona;
    }

    public Integer getNobilletesdenominacionb() {
        return nobilletesdenominacionb;
    }

    public void setNobilletesdenominacionb(Integer nobilletesdenominacionb) {
        this.nobilletesdenominacionb = nobilletesdenominacionb;
    }

    public Integer getNobilletesdenominacionc() {
        return nobilletesdenominacionc;
    }

    public void setNobilletesdenominacionc(Integer nobilletesdenominacionc) {
        this.nobilletesdenominacionc = nobilletesdenominacionc;
    }

    public Integer getNobilletesdenominaciond() {
        return nobilletesdenominaciond;
    }

    public void setNobilletesdenominaciond(Integer nobilletesdenominaciond) {
        this.nobilletesdenominaciond = nobilletesdenominaciond;
    }

    public Integer getNobilletesdenominacione() {
        return nobilletesdenominacione;
    }

    public void setNobilletesdenominacione(Integer nobilletesdenominacione) {
        this.nobilletesdenominacione = nobilletesdenominacione;
    }

    public Integer getNobilletesdenominacionf() {
        return nobilletesdenominacionf;
    }

    public void setNobilletesdenominacionf(Integer nobilletesdenominacionf) {
        this.nobilletesdenominacionf = nobilletesdenominacionf;
    }

    public Integer getNobilletesnd() {
        return nobilletesnd;
    }

    public void setNobilletesnd(Integer nobilletesnd) {
        this.nobilletesnd = nobilletesnd;
    }

    public Integer getNumerocorte() {
        return numerocorte;
    }

    public void setNumerocorte(Integer numerocorte) {
        this.numerocorte = numerocorte;
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

    public Long getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Long secuencia) {
        this.secuencia = secuencia;
    }

    public Short getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(Short tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    public Integer getTotalbilletesconsignados() {
        return totalbilletesconsignados;
    }

    public void setTotalbilletesconsignados(Integer totalbilletesconsignados) {
        this.totalbilletesconsignados = totalbilletesconsignados;
    }

    public Integer getTransaccionconsecutivo() {
        return transaccionconsecutivo;
    }

    public void setTransaccionconsecutivo(Integer transaccionconsecutivo) {
        this.transaccionconsecutivo = transaccionconsecutivo;
    }

    public Long getValorconsignacion() {
        return valorconsignacion;
    }

    public void setValorconsignacion(Long valorconsignacion) {
        this.valorconsignacion = valorconsignacion;
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
        hash += (txmultifuncionalefectivoPK != null ? txmultifuncionalefectivoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxmultifuncionalefectivoHisto)) {
            return false;
        }
        TxmultifuncionalefectivoHisto other = (TxmultifuncionalefectivoHisto) object;
        if ((this.txmultifuncionalefectivoPK == null && other.txmultifuncionalefectivoPK != null) || (this.txmultifuncionalefectivoPK != null && !this.txmultifuncionalefectivoPK.equals(other.txmultifuncionalefectivoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Txmultifuncionalefectivo[txmultifuncionalefectivoPK=" + txmultifuncionalefectivoPK + "]";
    }
    
    public TxmultifuncionalefectivoHisto actualizarEntity(TxmultifuncionalefectivoHisto obj) {           
        setTxmultifuncionalefectivoHistoPK(txmultifuncionalefectivoPK);    
        setEstado(obj.estado);    
        setNumerocorte(obj.numerocorte);
        setTransaccionconsecutivo(obj.transaccionconsecutivo);
        setFechacierre(obj.fechacierre);
        setTipocuenta(obj.tipocuenta);
        setNumerocuentaconsignar(obj.numerocuentaconsignar);
        setNumerocuentahomologa(obj.numerocuentahomologa);
        setValorconsignacion(obj.valorconsignacion);
        setNobilletesnd(obj.nobilletesnd);
        setNobilletesdenominacionf(obj.nobilletesdenominacionf);
        setNobilletesdenominacione(obj.nobilletesdenominacione);
        setNobilletesdenominaciond(obj.nobilletesdenominaciond);
        setNobilletesdenominacionc(obj.nobilletesdenominacionc);
        setNobilletesdenominacionb(obj.nobilletesdenominacionb);
        setNobilletesdenominaciona(obj.nobilletesdenominaciona);
        setTotalbilletesconsignados(obj.totalbilletesconsignados);
        setSecuencia(obj.secuencia);
        setVersion(obj.version);
        
    return this;
            
    }

}
