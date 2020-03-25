/*
 * InformeCuadreCifrasBean.java
 *
 * Created on 26/09/2007, 03:05:55 PM
 *
 * Babel : 1.0
 *
 */

package com.davivienda.sara.cuadrecifras.general.bean;

import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.Numero;
import java.util.Date;

/**
 *
 * @author jjvargas
 */
public class InformeCuadreDetalleBean {
    
    private Date fecha;
    private Date fechaAjuste;
    private Long valorCol1 ;
    private Long valorCol2 ;
    private Long valorCol3 ;
    private String usuario;
    private Integer diferencia ;
    private TipoMovimientoCuadre tipoMovimientoCuadre;
    
    
    public InformeCuadreDetalleBean() {
        valorCol1 = new Long(-1);
        valorCol2 = new Long(-1);
        valorCol3 = new Long(-1);
        diferencia =-1;
    }
    
    public String getFecha() {
        if (fechaAjuste != null) {
            return Fecha.aCadena(fecha, FormatoFecha.DEFECTO_SEPARADOR_GUION);
        } else {
            return "";
        }
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getFechaAjuste() {
        if (fechaAjuste != null) {
            return Fecha.aCadena(fechaAjuste, FormatoFecha.DEFECTO_SEPARADOR_GUION);
        } else {
            return "";
        }
    }
    
    public void setFechaAjuste(Date fechaAjuste) {
        this.fechaAjuste = fechaAjuste;
    }
    
    public String getValorCol1() {
        return Numero.aMoneda(valorCol1);
    }
    
    public void setValorCol1(Long valorCol1) {
        this.valorCol1 = valorCol1;
    }
    
      public Long getNumericoValorCol1() {
        return valorCol1;
    }
      
      public Long getNumericoValorCol2() {
      return valorCol2;
    }
    
    
     public String getValorCol2() {
        return Numero.aMoneda(valorCol2);
    }
      
    
    

    public void setValorCol2(Long valorCol2) {
        this.valorCol2 = valorCol2;
    }
    
       public String getValorCol3() {
        return Numero.aMoneda(valorCol3);
    }
    
    public void setValorCol3(Long valorCol3) {
        this.valorCol3 = valorCol3;
    }
    
      public Long getNumericoValorCol3() {
        return valorCol3;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public Integer getDiferencia() {
        if (this.tipoMovimientoCuadre.getLineaInfCuadre() == 10) {
            if (this.valorCol1.compareTo(valorCol2) != 0 ) {
                diferencia = 1;
            }
        }
        return diferencia;
    }
    
    public Long getDiferenciaNumerica() {
       
        return valorCol1-valorCol2;
    }
    
    public TipoMovimientoCuadre getTipoMovimientoCuadre() {
        return tipoMovimientoCuadre;
    }
    
    public void setTipoMovimientoCuadre(TipoMovimientoCuadre tipoMovimientoCuadre) {
        this.tipoMovimientoCuadre = tipoMovimientoCuadre;
    }
    
    
    
    
    
    
    
}
