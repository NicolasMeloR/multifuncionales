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
public class InformeAyerMulti {
 //"PROV.CAJA", "DISMI.PROV", "EF.RECIBIDO", "EF.PAGADO","EF.CAJA"};

    private Integer codigoCajero ;

    private Long provCaja ;
    private Long disminProv ;
    private Long efectRecibido ;
    private Long efectPagado ;
    private Long efectCaja ;
    private Long efectRecHorAnt ;
    private Long efectRecHorSig ;
  
  





    public InformeAyerMulti() {
        provCaja = new Long(0);
        disminProv = new Long(0);
        efectRecibido = new Long(0);
        efectPagado = new Long(0);
        efectCaja = new Long(0);
        efectRecHorAnt = new Long(0);
        efectRecHorSig = new Long(0);
        
       


    }

    public Long getDisminProv() {
        return disminProv;
    }

    public void setDisminProv(Long disminProv) {
        this.disminProv = disminProv;
    }

    public Long getEfectCaja() {
        return efectCaja;
    }

    public void setEfectCaja(Long efectCaja) {
        this.efectCaja = efectCaja;
    }

    public Long getEfectPagado() {
        return efectPagado;
    }

    public void setEfectPagado(Long efectPagado) {
        this.efectPagado = efectPagado;
    }

    public Long getEfectRecibido() {
        return efectRecibido;
    }

    public void setEfectRecibido(Long efectRecibido) {
        this.efectRecibido = efectRecibido;
    }

    public Long getProvCaja() {
        return provCaja;
    }

    public void setProvCaja(Long provCaja) {
        this.provCaja = provCaja;
    }

    public Long getEfectRecHorAnt() {
        return efectRecHorAnt;
    }

    public void setEfectRecHorAnt(Long efectRecHorAnt) {
        this.efectRecHorAnt = efectRecHorAnt ;
    }

    public Long getEfectRecHorSig() {
        return efectRecHorSig;
    }

    public void setEfectRecHorSig(Long efectRecHorSig) {
        this.efectRecHorSig = efectRecHorSig;
    }




  

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

   

   




}
