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
public class InformeDiferenciasMulti {


    private Integer codigoCajero ;

    private Long recibidoEfectivoAtm ;
    private Long recibidoEfectivoLinea ;
    private Long diferencia ;
    private String Observaciones ;
  
  





    public InformeDiferenciasMulti() {
        recibidoEfectivoAtm = new Long(0);
        recibidoEfectivoLinea = new Long(0);
        diferencia = new Long(0);
        Observaciones="";


    }




  

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public Long getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(Long diferencia) {
        this.diferencia = diferencia;
    }

    public Long getRecibidoEfectivoAtm() {
        return recibidoEfectivoAtm;
    }

    public void setRecibidoEfectivoAtm(Long recibidoEfectivoAtm) {
        this.recibidoEfectivoAtm = recibidoEfectivoAtm;
    }

    public Long getRecibidoEfectivoLinea() {
        return recibidoEfectivoLinea;
    }

    public void setRecibidoEfectivoLinea(Long recibidoEfectivoLinea) {
        this.recibidoEfectivoLinea = recibidoEfectivoLinea;
    }


   




}