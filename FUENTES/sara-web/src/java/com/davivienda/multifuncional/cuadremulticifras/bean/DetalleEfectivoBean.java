/*
 * InformeCuadreCifrasBean.java
 *
 * Created on 26/09/2007, 03:05:55 PM
 *
 * Babel : 1.0
 *
 */

package com.davivienda.multifuncional.cuadremulticifras.bean;


/**
 *
 * @author jjvargas
 */
public class DetalleEfectivoBean {
    
   
    private Long  den1000Valor ;
    private Long  den1000Cantidad ;
    private Long  den2000Valor ;
    private Long  den2000Cantidad ;
    private Long  den5000Valor ;
    private Long  den5000Cantidad ;
    private Long  den10000Valor ;
    private Long  den10000Cantidad ;
    private Long  den20000Valor ;
    private Long  den20000Cantidad ;
    private Long  den50000Valor ;
    private Long  den50000Cantidad ;
    private Long  totalesEfectivoValor ;
    private Long  totalesEfectivoCantidad ;
    
    private Long  totalChequeValor;
    private Long  totalChequeCantidad ;
    

    
    public DetalleEfectivoBean() {
        

     
   den1000Valor= new Long(0);
   den1000Cantidad =new Long(0);
   den2000Valor =new Long(0);
   den2000Cantidad =new Long(0);
   den5000Valor =new Long(0);
   den5000Cantidad =new Long(0);
   den10000Valor =new Long(0);
   den10000Cantidad =new Long(0);
   den20000Valor =new Long(0);
   den20000Cantidad =new Long(0);
   den50000Valor =new Long(0);
   den50000Cantidad =new Long(0);
   totalChequeValor=new Long(0);
   totalChequeCantidad=new Long(0);
   totalesEfectivoValor=new Long(0);
   totalesEfectivoCantidad =new Long(0);
    
    
    }


   public Long getDen10000Cantidad() {
        return den10000Cantidad;
    }

    public void setDen10000Cantidad(Long den10000Cantidad) {
        this.den10000Cantidad = den10000Cantidad;
    }

    public Long getDen10000Valor() {
        return den10000Valor;
    }

    public void setDen10000Valor(Long den10000Valor) {
        this.den10000Valor = den10000Valor;
    }

    public Long getDen1000Cantidad() {
        return den1000Cantidad;
    }

    public void setDen1000Cantidad(Long den1000Cantidad) {
        this.den1000Cantidad = den1000Cantidad;
    }

    public Long getDen1000Valor() {
        return den1000Valor;
    }

    public void setDen1000Valor(Long den1000Valor) {
        this.den1000Valor = den1000Valor;
    }

    public Long getDen20000Cantidad() {
        return den20000Cantidad;
    }

    public void setDen20000Cantidad(Long den20000Cantidad) {
        this.den20000Cantidad = den20000Cantidad;
    }

    public Long getDen20000Valor() {
        return den20000Valor;
    }

    public void setDen20000Valor(Long den20000Valor) {
        this.den20000Valor = den20000Valor;
    }

    public Long getDen2000Cantidad() {
        return den2000Cantidad;
    }

    public void setDen2000Cantidad(Long den2000Cantidad) {
        this.den2000Cantidad = den2000Cantidad;
    }

    public Long getDen2000Valor() {
        return den2000Valor;
    }

    public void setDen2000Valor(Long den2000Valor) {
        this.den2000Valor = den2000Valor;
    }

    public Long getDen50000Cantidad() {
        return den50000Cantidad;
    }

    public void setDen50000Cantidad(Long den50000Cantidad) {
        this.den50000Cantidad = den50000Cantidad;
    }

    public Long getDen50000Valor() {
        return den50000Valor;
    }

    public void setDen50000Valor(Long den50000Valor) {
        this.den50000Valor = den50000Valor;
    }

    public Long getDen5000Cantidad() {
        return den5000Cantidad;
    }

    public void setDen5000Cantidad(Long den5000Cantidad) {
        this.den5000Cantidad = den5000Cantidad;
    }

    public Long getDen5000Valor() {
        return den5000Valor;
    }

    public void setDen5000Valor(Long den5000Valor) {
        this.den5000Valor = den5000Valor;
    }

    public Long getTotalesEfectivoCantidad() {
        return totalesEfectivoCantidad;
    }

    public void setTotalesEfectivoCantidad(Long totalesEfectivoCantidad) {
        this.totalesEfectivoCantidad = totalesEfectivoCantidad;
    }

    public Long getTotalesEfectivoValor() {
        return totalesEfectivoValor;
    }

    public void setTotalesEfectivoValor(Long totalesEfectivoValor) {
        this.totalesEfectivoValor = totalesEfectivoValor;
    }

 
    
    

    public Long getTotalChequeCantidad() {
        return totalChequeCantidad;
    }

    public void setTotalChequeCantidad(Long totalChequeCantidad) {
        this.totalChequeCantidad = totalChequeCantidad;
    }

    public Long getTotalChequeValor() {
        return totalChequeValor;
    }

    public void setTotalChequeValor(Long totalChequeValor) {
        this.totalChequeValor = totalChequeValor;
    }
    
  
   
    
   
    
    
    
    
    
    
}
