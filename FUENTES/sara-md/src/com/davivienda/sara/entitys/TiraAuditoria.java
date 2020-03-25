/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.entitys;

/**
 *
 * @author nmelo
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Version;

@Entity
public class TiraAuditoria implements Serializable {
    private static final long serialVersionUID = 1L;
    
     private Integer idzona;
     private Integer  codigobanco;
     private Integer  idocca;
     private Integer  idcajero;
     private String  tipomaquina;
     private String   tiporegistro;
     private String   idtransaccion;
     private String  tipotransaccion;
     private String  desctransaccion;
     
     @Temporal(TemporalType.TIMESTAMP)
     private Date fecharealtransaccion;
     
     @Temporal(TemporalType.TIMESTAMP)
     private Date fechacontabletransaccion;
     
     private String numerotarjeta;
     private String numeroproducto;
     private String ptoductorigen;
     private Integer montotransaccionretiro;
     private Integer montotxretiroentregado;
     private Integer valordonacion;
     private Integer costotransaccion;
     private String vueltasdescripcion;
     private Integer montotxdeposito;
     private Integer montotxdepositorecibido;
     private  String referencia1;
     private  String referencia2;
     private  String referencia3;
     private String ptoductodestino;
     private  String  numeroproductodestino;
     private String  numeroaprobacion;
     private  String  codigoerrorhost;
     private String  descripcionerrorhost;
     private  String  codigoerroratm;
     private String  descripcionerroratm;
     private  String  estadoimpresion;
     private String  logaudita;
     private Integer cantidad;
     private Integer valor;
     private Integer cantidad1;
     private Integer valor1;
     private Integer cantidad2;
     private Integer valor2;
     private Integer cantidad3;
     private Integer valor3;
     private Integer cantidad4;
     private Integer valor4;
     private Integer cantidad5;
     private Integer valor5;
     private Integer cantidad6;
     private Integer valor6;
     private Integer cantidad7;
     private Integer valor7;
     private Integer cantidad8;
     private Integer valor8;
     private String denominacionbilletes;
     private Integer cantbilletesprovision;
     private Integer  billetesdispensados;
     private Integer acumbilletesdispensados;
     private Integer billetesdepositados;
     private Integer acumbilletesdepositados;
     private Integer billetesremanentes;
     private Integer billetesrechazados;
     private Integer acumbilletesrechazados;
     private Integer billetesretrac;
     private Integer acumbilletesretract;
     private String denominacionbilletes1;
     private Integer cantbilletesprovision1;
     private Integer  billetesdispensados1;
     private Integer acumbilletesdispensados1;
     private Integer billetesdepositados1;
     private Integer acumbilletesdepositados1;
     private Integer billetesremanentes1;
     private Integer billetesrechazados1;
     private Integer acumbilletesrechazados1;
     private Integer billetesretrac1;
     private Integer acumbilletesretract1;
     private String denominacionbilletes2;
     private Integer cantbilletesprovision2;
     private Integer  billetesdispensados2;
     private Integer acumbilletesdispensados2;
     private Integer billetesdepositados2;
     private Integer acumbilletesdepositados2;
     private Integer billetesremanentes2;
     private Integer billetesrechazados2;
     private Integer acumbilletesrechazados2;
     private Integer billetesretrac2;
     private Integer acumbilletesretract2;
     private String denominacionbilletes3;
     private Integer cantbilletesprovision3;
     private Integer  billetesdispensados3;
     private Integer acumbilletesdispensados3;
     private Integer billetesdepositados3;
     private Integer acumbilletesdepositados3;
     private Integer billetesremanentes3;
     private Integer billetesrechazados3;
     private Integer acumbilletesrechazados3;
     private Integer billetesretrac3;
     private Integer acumbilletesretract3;
     private String denominacionbilletes4;
     private Integer cantbilletesprovision4;
     private Integer  billetesdispensados4;
     private Integer acumbilletesdispensados4;
     private Integer billetesdepositados4;
     private Integer acumbilletesdepositados4;
     private Integer billetesremanentes4;
     private Integer billetesrechazados4;
     private Integer acumbilletesrechazados4;
     private Integer billetesretrac4;
     private Integer acumbilletesretract4;
     private String denominacionbilletes5;
     private Integer cantbilletesprovision5;
     private Integer  billetesdispensados5;
     private Integer acumbilletesdispensados5;
     private Integer billetesdepositados5;
     private Integer acumbilletesdepositados5;
     private Integer billetesremanentes5;
     private Integer billetesrechazados5;
     private Integer acumbilletesrechazados5;
     private Integer billetesretrac5;
     private Integer acumbilletesretract5;

     @Id
     private String  numerotransaccion;
     
    /**
     * Crea una nueva instancia de Cajero
     */
    public TiraAuditoria() {
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    //getter

    public Integer getIdzona() {
        return idzona;
    }

    public Integer getCodigobanco() {
        return codigobanco;
    }

    public Integer getIdocca() {
        return idocca;
    }

    public Integer getIdcajero() {
        return idcajero;
    }

    public String getTipomaquina() {
        return tipomaquina;
    }

    public String getTiporegistro() {
        return tiporegistro;
    }

    public String getIdtransaccion() {
        return idtransaccion;
    }

    public String getTipotransaccion() {
        return tipotransaccion;
    }

    public String getDesctransaccion() {
        return desctransaccion;
    }

    public Date getFecharealtransaccion() {
        return fecharealtransaccion;
    }

    public Date getFechacontabletransaccion() {
        return fechacontabletransaccion;
    }

    public String getNumerotarjeta() {
        return numerotarjeta;
    }

    public String getNumeroproducto() {
        return numeroproducto;
    }

    public String getPtoductorigen() {
        return ptoductorigen;
    }

    public Integer getMontotransaccionretiro() {
        return montotransaccionretiro;
    }

    public Integer getMontotxretiroentregado() {
        return montotxretiroentregado;
    }

    public Integer getValordonacion() {
        return valordonacion;
    }

    public Integer getCostotransaccion() {
        return costotransaccion;
    }

    public String getVueltasdescripcion() {
        return vueltasdescripcion;
    }

    public Integer getMontotxdeposito() {
        return montotxdeposito;
    }

    public Integer getMontotxdepositorecibido() {
        return montotxdepositorecibido;
    }

    public String getReferencia1() {
        return referencia1;
    }

    public String getReferencia2() {
        return referencia2;
    }

    public String getReferencia3() {
        return referencia3;
    }

    public String getPtoductodestino() {
        return ptoductodestino;
    }

    public String getNumeroproductodestino() {
        return numeroproductodestino;
    }

    public String getNumeroaprobacion() {
        return numeroaprobacion;
    }

    public String getCodigoerrorhost() {
        return codigoerrorhost;
    }

    public String getDescripcionerrorhost() {
        return descripcionerrorhost;
    }

    public String getCodigoerroratm() {
        return codigoerroratm;
    }

    public String getDescripcionerroratm() {
        return descripcionerroratm;
    }

    public String getEstadoimpresion() {
        return estadoimpresion;
    }

    public String getLogaudita() {
        return logaudita;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Integer getValor() {
        return valor;
    }

    public Integer getCantidad1() {
        return cantidad1;
    }

    public Integer getValor1() {
        return valor1;
    }

    public Integer getCantidad2() {
        return cantidad2;
    }

    public Integer getValor2() {
        return valor2;
    }

    public Integer getCantidad3() {
        return cantidad3;
    }

    public Integer getValor3() {
        return valor3;
    }

    public Integer getCantidad4() {
        return cantidad4;
    }

    public Integer getValor4() {
        return valor4;
    }

    public Integer getCantidad5() {
        return cantidad5;
    }

    public Integer getValor5() {
        return valor5;
    }

    public Integer getCantidad6() {
        return cantidad6;
    }

    public Integer getValor6() {
        return valor6;
    }

    public Integer getCantidad7() {
        return cantidad7;
    }

    public Integer getValor7() {
        return valor7;
    }

    public Integer getCantidad8() {
        return cantidad8;
    }

    public Integer getValor8() {
        return valor8;
    }

    public String getDenominacionbilletes() {
        return denominacionbilletes;
    }

    public Integer getCantbilletesprovision() {
        return cantbilletesprovision;
    }

    public Integer getBilletesdispensados() {
        return billetesdispensados;
    }

    public Integer getAcumbilletesdispensados() {
        return acumbilletesdispensados;
    }

    public Integer getBilletesdepositados() {
        return billetesdepositados;
    }

    public Integer getAcumbilletesdepositados() {
        return acumbilletesdepositados;
    }

    public Integer getBilletesremanentes() {
        return billetesremanentes;
    }

    public Integer getBilletesrechazados() {
        return billetesrechazados;
    }

    public Integer getAcumbilletesrechazados() {
        return acumbilletesrechazados;
    }

    public Integer getBilletesretrac() {
        return billetesretrac;
    }

    public Integer getAcumbilletesretract() {
        return acumbilletesretract;
    }

    public String getDenominacionbilletes1() {
        return denominacionbilletes1;
    }

    public Integer getCantbilletesprovision1() {
        return cantbilletesprovision1;
    }

    public Integer getBilletesdispensados1() {
        return billetesdispensados1;
    }

    public Integer getAcumbilletesdispensados1() {
        return acumbilletesdispensados1;
    }

    public Integer getBilletesdepositados1() {
        return billetesdepositados1;
    }

    public Integer getAcumbilletesdepositados1() {
        return acumbilletesdepositados1;
    }

    public Integer getBilletesremanentes1() {
        return billetesremanentes1;
    }

    public Integer getBilletesrechazados1() {
        return billetesrechazados1;
    }

    public Integer getAcumbilletesrechazados1() {
        return acumbilletesrechazados1;
    }

    public Integer getBilletesretrac1() {
        return billetesretrac1;
    }

    public Integer getAcumbilletesretract1() {
        return acumbilletesretract1;
    }

    public String getDenominacionbilletes2() {
        return denominacionbilletes2;
    }

    public Integer getCantbilletesprovision2() {
        return cantbilletesprovision2;
    }

    public Integer getBilletesdispensados2() {
        return billetesdispensados2;
    }

    public Integer getAcumbilletesdispensados2() {
        return acumbilletesdispensados2;
    }

    public Integer getBilletesdepositados2() {
        return billetesdepositados2;
    }

    public Integer getAcumbilletesdepositados2() {
        return acumbilletesdepositados2;
    }

    public Integer getBilletesremanentes2() {
        return billetesremanentes2;
    }

    public Integer getBilletesrechazados2() {
        return billetesrechazados2;
    }

    public Integer getAcumbilletesrechazados2() {
        return acumbilletesrechazados2;
    }

    public Integer getBilletesretrac2() {
        return billetesretrac2;
    }

    public Integer getAcumbilletesretract2() {
        return acumbilletesretract2;
    }

    public String getDenominacionbilletes3() {
        return denominacionbilletes3;
    }

    public Integer getCantbilletesprovision3() {
        return cantbilletesprovision3;
    }

    public Integer getBilletesdispensados3() {
        return billetesdispensados3;
    }

    public Integer getAcumbilletesdispensados3() {
        return acumbilletesdispensados3;
    }

    public Integer getBilletesdepositados3() {
        return billetesdepositados3;
    }

    public Integer getAcumbilletesdepositados3() {
        return acumbilletesdepositados3;
    }

    public Integer getBilletesremanentes3() {
        return billetesremanentes3;
    }

    public Integer getBilletesrechazados3() {
        return billetesrechazados3;
    }

    public Integer getAcumbilletesrechazados3() {
        return acumbilletesrechazados3;
    }

    public Integer getBilletesretrac3() {
        return billetesretrac3;
    }

    public Integer getAcumbilletesretract3() {
        return acumbilletesretract3;
    }

    public String getDenominacionbilletes4() {
        return denominacionbilletes4;
    }

    public Integer getCantbilletesprovision4() {
        return cantbilletesprovision4;
    }

    public Integer getBilletesdispensados4() {
        return billetesdispensados4;
    }

    public Integer getAcumbilletesdispensados4() {
        return acumbilletesdispensados4;
    }

    public Integer getBilletesdepositados4() {
        return billetesdepositados4;
    }

    public Integer getAcumbilletesdepositados4() {
        return acumbilletesdepositados4;
    }

    public Integer getBilletesremanentes4() {
        return billetesremanentes4;
    }

    public Integer getBilletesrechazados4() {
        return billetesrechazados4;
    }

    public Integer getAcumbilletesrechazados4() {
        return acumbilletesrechazados4;
    }

    public Integer getBilletesretrac4() {
        return billetesretrac4;
    }

    public Integer getAcumbilletesretract4() {
        return acumbilletesretract4;
    }

    public String getDenominacionbilletes5() {
        return denominacionbilletes5;
    }

    public Integer getCantbilletesprovision5() {
        return cantbilletesprovision5;
    }

    public Integer getBilletesdispensados5() {
        return billetesdispensados5;
    }

    public Integer getAcumbilletesdispensados5() {
        return acumbilletesdispensados5;
    }

    public Integer getBilletesdepositados5() {
        return billetesdepositados5;
    }

    public Integer getAcumbilletesdepositados5() {
        return acumbilletesdepositados5;
    }

    public Integer getBilletesremanentes5() {
        return billetesremanentes5;
    }

    public Integer getBilletesrechazados5() {
        return billetesrechazados5;
    }

    public Integer getAcumbilletesrechazados5() {
        return acumbilletesrechazados5;
    }

    public Integer getBilletesretrac5() {
        return billetesretrac5;
    }

    public Integer getAcumbilletesretract5() {
        return acumbilletesretract5;
    }

    public String getNumerotransaccion() {
        return numerotransaccion;
    }
    
    
    //setter

    public void setIdzona(Integer idzona) {
        this.idzona = idzona;
    }

    public void setCodigobanco(Integer codigobanco) {
        this.codigobanco = codigobanco;
    }

    public void setIdocca(Integer idocca) {
        this.idocca = idocca;
    }

    public void setIdcajero(Integer idcajero) {
        this.idcajero = idcajero;
    }

    public void setTipomaquina(String tipomaquina) {
        this.tipomaquina = tipomaquina;
    }

    public void setTiporegistro(String tiporegistro) {
        this.tiporegistro = tiporegistro;
    }

    public void setIdtransaccion(String idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public void setTipotransaccion(String tipotransaccion) {
        this.tipotransaccion = tipotransaccion;
    }

    public void setDesctransaccion(String desctransaccion) {
        this.desctransaccion = desctransaccion;
    }

    public void setFecharealtransaccion(Date fecharealtransaccion) {
        this.fecharealtransaccion = fecharealtransaccion;
    }

    public void setFechacontabletransaccion(Date fechacontabletransaccion) {
        this.fechacontabletransaccion = fechacontabletransaccion;
    }

    public void setNumerotarjeta(String numerotarjeta) {
        this.numerotarjeta = numerotarjeta;
    }

    public void setNumeroproducto(String numeroproducto) {
        this.numeroproducto = numeroproducto;
    }

    public void setPtoductorigen(String ptoductorigen) {
        this.ptoductorigen = ptoductorigen;
    }

    public void setMontotransaccionretiro(Integer montotransaccionretiro) {
        this.montotransaccionretiro = montotransaccionretiro;
    }

    public void setMontotxretiroentregado(Integer montotxretiroentregado) {
        this.montotxretiroentregado = montotxretiroentregado;
    }

    public void setValordonacion(Integer valordonacion) {
        this.valordonacion = valordonacion;
    }

    public void setCostotransaccion(Integer costotransaccion) {
        this.costotransaccion = costotransaccion;
    }

    public void setVueltasdescripcion(String vueltasdescripcion) {
        this.vueltasdescripcion = vueltasdescripcion;
    }

    public void setMontotxdeposito(Integer montotxdeposito) {
        this.montotxdeposito = montotxdeposito;
    }

    public void setMontotxdepositorecibido(Integer montotxdepositorecibido) {
        this.montotxdepositorecibido = montotxdepositorecibido;
    }

    public void setReferencia1(String referencia1) {
        this.referencia1 = referencia1;
    }

    public void setReferencia2(String referencia2) {
        this.referencia2 = referencia2;
    }

    public void setReferencia3(String referencia3) {
        this.referencia3 = referencia3;
    }

    public void setPtoductodestino(String ptoductodestino) {
        this.ptoductodestino = ptoductodestino;
    }

    public void setNumeroproductodestino(String numeroproductodestino) {
        this.numeroproductodestino = numeroproductodestino;
    }

    public void setNumeroaprobacion(String numeroaprobacion) {
        this.numeroaprobacion = numeroaprobacion;
    }

    public void setCodigoerrorhost(String codigoerrorhost) {
        this.codigoerrorhost = codigoerrorhost;
    }

    public void setDescripcionerrorhost(String descripcionerrorhost) {
        this.descripcionerrorhost = descripcionerrorhost;
    }

    public void setCodigoerroratm(String codigoerroratm) {
        this.codigoerroratm = codigoerroratm;
    }

    public void setDescripcionerroratm(String descripcionerroratm) {
        this.descripcionerroratm = descripcionerroratm;
    }

    public void setEstadoimpresion(String estadoimpresion) {
        this.estadoimpresion = estadoimpresion;
    }

    public void setLogaudita(String logaudita) {
        this.logaudita = logaudita;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public void setCantidad1(Integer cantidad1) {
        this.cantidad1 = cantidad1;
    }

    public void setValor1(Integer valor1) {
        this.valor1 = valor1;
    }

    public void setCantidad2(Integer cantidad2) {
        this.cantidad2 = cantidad2;
    }

    public void setValor2(Integer valor2) {
        this.valor2 = valor2;
    }

    public void setCantidad3(Integer cantidad3) {
        this.cantidad3 = cantidad3;
    }

    public void setValor3(Integer valor3) {
        this.valor3 = valor3;
    }

    public void setCantidad4(Integer cantidad4) {
        this.cantidad4 = cantidad4;
    }

    public void setValor4(Integer valor4) {
        this.valor4 = valor4;
    }

    public void setCantidad5(Integer cantidad5) {
        this.cantidad5 = cantidad5;
    }

    public void setValor5(Integer valor5) {
        this.valor5 = valor5;
    }

    public void setCantidad6(Integer cantidad6) {
        this.cantidad6 = cantidad6;
    }

    public void setValor6(Integer valor6) {
        this.valor6 = valor6;
    }

    public void setCantidad7(Integer cantidad7) {
        this.cantidad7 = cantidad7;
    }

    public void setValor7(Integer valor7) {
        this.valor7 = valor7;
    }

    public void setCantidad8(Integer cantidad8) {
        this.cantidad8 = cantidad8;
    }

    public void setValor8(Integer valor8) {
        this.valor8 = valor8;
    }

    public void setDenominacionbilletes(String denominacionbilletes) {
        this.denominacionbilletes = denominacionbilletes;
    }

    public void setCantbilletesprovision(Integer cantbilletesprovision) {
        this.cantbilletesprovision = cantbilletesprovision;
    }

    public void setBilletesdispensados(Integer billetesdispensados) {
        this.billetesdispensados = billetesdispensados;
    }

    public void setAcumbilletesdispensados(Integer acumbilletesdispensados) {
        this.acumbilletesdispensados = acumbilletesdispensados;
    }

    public void setBilletesdepositados(Integer billetesdepositados) {
        this.billetesdepositados = billetesdepositados;
    }

    public void setAcumbilletesdepositados(Integer acumbilletesdepositados) {
        this.acumbilletesdepositados = acumbilletesdepositados;
    }

    public void setBilletesremanentes(Integer billetesremanentes) {
        this.billetesremanentes = billetesremanentes;
    }

    public void setBilletesrechazados(Integer billetesrechazados) {
        this.billetesrechazados = billetesrechazados;
    }

    public void setAcumbilletesrechazados(Integer acumbilletesrechazados) {
        this.acumbilletesrechazados = acumbilletesrechazados;
    }

    public void setBilletesretrac(Integer billetesretrac) {
        this.billetesretrac = billetesretrac;
    }

    public void setAcumbilletesretract(Integer acumbilletesretract) {
        this.acumbilletesretract = acumbilletesretract;
    }

    public void setDenominacionbilletes1(String denominacionbilletes1) {
        this.denominacionbilletes1 = denominacionbilletes1;
    }

    public void setCantbilletesprovision1(Integer cantbilletesprovision1) {
        this.cantbilletesprovision1 = cantbilletesprovision1;
    }

    public void setBilletesdispensados1(Integer billetesdispensados1) {
        this.billetesdispensados1 = billetesdispensados1;
    }

    public void setAcumbilletesdispensados1(Integer acumbilletesdispensados1) {
        this.acumbilletesdispensados1 = acumbilletesdispensados1;
    }

    public void setBilletesdepositados1(Integer billetesdepositados1) {
        this.billetesdepositados1 = billetesdepositados1;
    }

    public void setAcumbilletesdepositados1(Integer acumbilletesdepositados1) {
        this.acumbilletesdepositados1 = acumbilletesdepositados1;
    }

    public void setBilletesremanentes1(Integer billetesremanentes1) {
        this.billetesremanentes1 = billetesremanentes1;
    }

    public void setBilletesrechazados1(Integer billetesrechazados1) {
        this.billetesrechazados1 = billetesrechazados1;
    }

    public void setAcumbilletesrechazados1(Integer acumbilletesrechazados1) {
        this.acumbilletesrechazados1 = acumbilletesrechazados1;
    }

    public void setBilletesretrac1(Integer billetesretrac1) {
        this.billetesretrac1 = billetesretrac1;
    }

    public void setAcumbilletesretract1(Integer acumbilletesretract1) {
        this.acumbilletesretract1 = acumbilletesretract1;
    }

    public void setDenominacionbilletes2(String denominacionbilletes2) {
        this.denominacionbilletes2 = denominacionbilletes2;
    }

    public void setCantbilletesprovision2(Integer cantbilletesprovision2) {
        this.cantbilletesprovision2 = cantbilletesprovision2;
    }

    public void setBilletesdispensados2(Integer billetesdispensados2) {
        this.billetesdispensados2 = billetesdispensados2;
    }

    public void setAcumbilletesdispensados2(Integer acumbilletesdispensados2) {
        this.acumbilletesdispensados2 = acumbilletesdispensados2;
    }

    public void setBilletesdepositados2(Integer billetesdepositados2) {
        this.billetesdepositados2 = billetesdepositados2;
    }

    public void setAcumbilletesdepositados2(Integer acumbilletesdepositados2) {
        this.acumbilletesdepositados2 = acumbilletesdepositados2;
    }

    public void setBilletesremanentes2(Integer billetesremanentes2) {
        this.billetesremanentes2 = billetesremanentes2;
    }

    public void setBilletesrechazados2(Integer billetesrechazados2) {
        this.billetesrechazados2 = billetesrechazados2;
    }

    public void setAcumbilletesrechazados2(Integer acumbilletesrechazados2) {
        this.acumbilletesrechazados2 = acumbilletesrechazados2;
    }

    public void setBilletesretrac2(Integer billetesretrac2) {
        this.billetesretrac2 = billetesretrac2;
    }

    public void setAcumbilletesretract2(Integer acumbilletesretract2) {
        this.acumbilletesretract2 = acumbilletesretract2;
    }

    public void setDenominacionbilletes3(String denominacionbilletes3) {
        this.denominacionbilletes3 = denominacionbilletes3;
    }

    public void setCantbilletesprovision3(Integer cantbilletesprovision3) {
        this.cantbilletesprovision3 = cantbilletesprovision3;
    }

    public void setBilletesdispensados3(Integer billetesdispensados3) {
        this.billetesdispensados3 = billetesdispensados3;
    }

    public void setAcumbilletesdispensados3(Integer acumbilletesdispensados3) {
        this.acumbilletesdispensados3 = acumbilletesdispensados3;
    }

    public void setBilletesdepositados3(Integer billetesdepositados3) {
        this.billetesdepositados3 = billetesdepositados3;
    }

    public void setAcumbilletesdepositados3(Integer acumbilletesdepositados3) {
        this.acumbilletesdepositados3 = acumbilletesdepositados3;
    }

    public void setBilletesremanentes3(Integer billetesremanentes3) {
        this.billetesremanentes3 = billetesremanentes3;
    }

    public void setBilletesrechazados3(Integer billetesrechazados3) {
        this.billetesrechazados3 = billetesrechazados3;
    }

    public void setAcumbilletesrechazados3(Integer acumbilletesrechazados3) {
        this.acumbilletesrechazados3 = acumbilletesrechazados3;
    }

    public void setBilletesretrac3(Integer billetesretrac3) {
        this.billetesretrac3 = billetesretrac3;
    }

    public void setAcumbilletesretract3(Integer acumbilletesretract3) {
        this.acumbilletesretract3 = acumbilletesretract3;
    }

    public void setDenominacionbilletes4(String denominacionbilletes4) {
        this.denominacionbilletes4 = denominacionbilletes4;
    }

    public void setCantbilletesprovision4(Integer cantbilletesprovision4) {
        this.cantbilletesprovision4 = cantbilletesprovision4;
    }

    public void setBilletesdispensados4(Integer billetesdispensados4) {
        this.billetesdispensados4 = billetesdispensados4;
    }

    public void setAcumbilletesdispensados4(Integer acumbilletesdispensados4) {
        this.acumbilletesdispensados4 = acumbilletesdispensados4;
    }

    public void setBilletesdepositados4(Integer billetesdepositados4) {
        this.billetesdepositados4 = billetesdepositados4;
    }

    public void setAcumbilletesdepositados4(Integer acumbilletesdepositados4) {
        this.acumbilletesdepositados4 = acumbilletesdepositados4;
    }

    public void setBilletesremanentes4(Integer billetesremanentes4) {
        this.billetesremanentes4 = billetesremanentes4;
    }

    public void setBilletesrechazados4(Integer billetesrechazados4) {
        this.billetesrechazados4 = billetesrechazados4;
    }

    public void setAcumbilletesrechazados4(Integer acumbilletesrechazados4) {
        this.acumbilletesrechazados4 = acumbilletesrechazados4;
    }

    public void setBilletesretrac4(Integer billetesretrac4) {
        this.billetesretrac4 = billetesretrac4;
    }

    public void setAcumbilletesretract4(Integer acumbilletesretract4) {
        this.acumbilletesretract4 = acumbilletesretract4;
    }

    public void setDenominacionbilletes5(String denominacionbilletes5) {
        this.denominacionbilletes5 = denominacionbilletes5;
    }

    public void setCantbilletesprovision5(Integer cantbilletesprovision5) {
        this.cantbilletesprovision5 = cantbilletesprovision5;
    }

    public void setBilletesdispensados5(Integer billetesdispensados5) {
        this.billetesdispensados5 = billetesdispensados5;
    }

    public void setAcumbilletesdispensados5(Integer acumbilletesdispensados5) {
        this.acumbilletesdispensados5 = acumbilletesdispensados5;
    }

    public void setBilletesdepositados5(Integer billetesdepositados5) {
        this.billetesdepositados5 = billetesdepositados5;
    }

    public void setAcumbilletesdepositados5(Integer acumbilletesdepositados5) {
        this.acumbilletesdepositados5 = acumbilletesdepositados5;
    }

    public void setBilletesremanentes5(Integer billetesremanentes5) {
        this.billetesremanentes5 = billetesremanentes5;
    }

    public void setBilletesrechazados5(Integer billetesrechazados5) {
        this.billetesrechazados5 = billetesrechazados5;
    }

    public void setAcumbilletesrechazados5(Integer acumbilletesrechazados5) {
        this.acumbilletesrechazados5 = acumbilletesrechazados5;
    }

    public void setBilletesretrac5(Integer billetesretrac5) {
        this.billetesretrac5 = billetesretrac5;
    }

    public void setAcumbilletesretract5(Integer acumbilletesretract5) {
        this.acumbilletesretract5 = acumbilletesretract5;
    }

    public void setNumerotransaccion(String numerotransaccion) {
        this.numerotransaccion = numerotransaccion;
    }
    
    
}

