package com.davivienda.sara.entitys.transaccion;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CantidadTransaccionesBean - 1/09/2008
 * Descripción :  Bean para el manejo de estadísticas de transacciones realizadas en el cajero
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class SumatoriaTransaccionesHostBean implements Serializable{
    
    Integer codigoCajero;
    String fechaInicial;
    String fechaFinal;
    BigDecimal sumTransaccion;
    BigDecimal sumHost;
    BigDecimal diferencia;

    public SumatoriaTransaccionesHostBean() {
    }

   
    
    public BigDecimal getSumTransaccion() {
        return sumTransaccion;
    }

    public void setSumTransaccion(BigDecimal sumTransaccion) {
        this.sumTransaccion = sumTransaccion;
    }
    
    
   public BigDecimal getSumHost() {
        return sumHost;
    }

    public void setSumHost(BigDecimal sumHost) {
        this.sumHost = sumHost;
    }

    public BigDecimal getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(BigDecimal diferencia) {
        this.diferencia = diferencia;
    }
    
    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }
    
    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

   
    
    
    
    

}

