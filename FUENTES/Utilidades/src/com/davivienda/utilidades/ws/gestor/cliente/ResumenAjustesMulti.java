/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.utilidades.ws.gestor.cliente;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author AA.Garcia
 */
public class ResumenAjustesMulti implements Serializable {
private String titulo;
private Long cantidad;
private String valor;

  public String getTitulo() {
        return this.titulo;
    }

 
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    } 
     public Long getCantidad() {
        return this.cantidad;
    }

 
    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    } 
    
     public String getValor() {
        return this.valor;
    }

 
    public void setValor(String valor) {
        this.valor = valor;
    } 
}
