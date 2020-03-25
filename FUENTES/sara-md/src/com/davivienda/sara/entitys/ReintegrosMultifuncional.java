package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * ReintegrosMultifuncional.java
 *
 * Fecha :20 de abril de 2017, 09:36 AM
 
 * Descripción : Entity de la tabla REINTEGROS_MULTIFUNCIONAL
 *
 * @author :jediazs
 *
 * @version : $Id: ReintegrosMultifuncional.java,v 1.0 2017/04/20 09:36:20 jediazs Exp $
 *
 */
@Entity
@Table(name = "REINTEGROS_MULTIFUNCIONAL")
@NamedQueries({
   @NamedQuery(name = "ReintegrosMultifuncional.RangoFecha",
               query  = "select object(obj) from ReintegrosMultifuncional obj " +
                "        where  obj.fechacajero between :fechaInicial and :fechaFinal " + 
                "        order by obj.fechacajero"), 
    @NamedQuery(name = "ReintegrosMultifuncional.CajeroFecha",
                query = "select object(obj) from ReintegrosMultifuncional obj " +
                "        where obj.codigoCajero =:codigocajero and " +
                "              obj.fechacajero between :fechaInicial and :fechaFinal" +
                "        order by obj.fechacajero")})

public class ReintegrosMultifuncional implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    @Column(name = "FECHA_HORA_DISPENSADOR")
    private String fechaHoraDispensador;
    @Column(name = "BILLETAJE_DISPENSADOR")
    private String billetajeDispensador;
    @Column(name = "TOTAL_DISPENSADOR")
    private String totalDispensador;
    @Column(name = "CODIGOTX")
    private Integer codigoTx; 
    @Column(name = "ESTADO")
    private Integer estado;    
    @Id
    @Column(name = "CODIGOCAJERO")
    private Integer codigoCajero;
    @Column(name = "NUMEROCORTE")
    private Integer numerocorte;
    @Column(name = "TRANSACCIONCONSECUTIVO")
    private Integer transaccionconsecutivo;
    @Column(name = "FECHACIERRE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacierre;
    @Column(name = "FECHACAJERO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacajero;
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

    
    /**
     * Crea una nueva instancia de ReintegrosMultifuncional
     */
    public ReintegrosMultifuncional() {
    }

    public String getFechaHoraDispensador() {
        return fechaHoraDispensador;
    }

    public void setFechaHoraDispensador(String fechaHoraDispensador) {
        this.fechaHoraDispensador = fechaHoraDispensador;
    }

    public String getBilletajeDispensador() {
        return billetajeDispensador;
    }

    public void setBilletajeDispensador(String billetajeDispensador) {
        this.billetajeDispensador = billetajeDispensador;
    }

    public String getTotalDispensador() {
        return totalDispensador;
    }

    public void setTotalDispensador(String totalDispensador) {
        this.totalDispensador = totalDispensador;
    }

    public Integer getCodigoTx() {
        return codigoTx;
    }

    public void setCodigoTx(Integer codigoTx) {
        this.codigoTx = codigoTx;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public Integer getNumerocorte() {
        return numerocorte;
    }

    public void setNumerocorte(Integer numerocorte) {
        this.numerocorte = numerocorte;
    }

    public Integer getTransaccionconsecutivo() {
        return transaccionconsecutivo;
    }

    public void setTransaccionconsecutivo(Integer transaccionconsecutivo) {
        this.transaccionconsecutivo = transaccionconsecutivo;
    }

    public Date getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(Date fechacierre) {
        this.fechacierre = fechacierre;
    }

    public Date getFechacajero() {
        return fechacajero;
    }

    public void setFechacajero(Date fechacajero) {
        this.fechacajero = fechacajero;
    }
    
    
    public Short getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(Short tipocuenta) {
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

    public Long getValorconsignacion() {
        return valorconsignacion;
    }

    public void setValorconsignacion(Long valorconsignacion) {
        this.valorconsignacion = valorconsignacion;
    }

    public Integer getNobilletesnd() {
        return nobilletesnd;
    }

    public void setNobilletesnd(Integer nobilletesnd) {
        this.nobilletesnd = nobilletesnd;
    }

    public Integer getNobilletesdenominacionf() {
        return nobilletesdenominacionf;
    }

    public void setNobilletesdenominacionf(Integer nobilletesdenominacionf) {
        this.nobilletesdenominacionf = nobilletesdenominacionf;
    }

    public Integer getNobilletesdenominacione() {
        return nobilletesdenominacione;
    }

    public void setNobilletesdenominacione(Integer nobilletesdenominacione) {
        this.nobilletesdenominacione = nobilletesdenominacione;
    }

    public Integer getNobilletesdenominaciond() {
        return nobilletesdenominaciond;
    }

    public void setNobilletesdenominaciond(Integer nobilletesdenominaciond) {
        this.nobilletesdenominaciond = nobilletesdenominaciond;
    }

    public Integer getNobilletesdenominacionc() {
        return nobilletesdenominacionc;
    }

    public void setNobilletesdenominacionc(Integer nobilletesdenominacionc) {
        this.nobilletesdenominacionc = nobilletesdenominacionc;
    }

    public Integer getNobilletesdenominacionb() {
        return nobilletesdenominacionb;
    }

    public void setNobilletesdenominacionb(Integer nobilletesdenominacionb) {
        this.nobilletesdenominacionb = nobilletesdenominacionb;
    }

    public Integer getNobilletesdenominaciona() {
        return nobilletesdenominaciona;
    }

    public void setNobilletesdenominaciona(Integer nobilletesdenominaciona) {
        this.nobilletesdenominaciona = nobilletesdenominaciona;
    }

    public Integer getTotalbilletesconsignados() {
        return totalbilletesconsignados;
    }

    public void setTotalbilletesconsignados(Integer totalbilletesconsignados) {
        this.totalbilletesconsignados = totalbilletesconsignados;
    }

    public Long getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Long secuencia) {
        this.secuencia = secuencia;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    
    
   
}
