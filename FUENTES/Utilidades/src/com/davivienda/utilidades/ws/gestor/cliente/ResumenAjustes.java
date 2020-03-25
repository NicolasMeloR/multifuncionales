package com.davivienda.utilidades.ws.gestor.cliente;
//OLD: package com.davivienda.multifuncional.clientews;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author AA.Garcia
 */
public class ResumenAjustes implements Serializable{
    
private String titulo;
private Long cantidad;
private BigDecimal valor;

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
    
     public BigDecimal getValor() {
        return this.valor;
    }

 
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    } 
}