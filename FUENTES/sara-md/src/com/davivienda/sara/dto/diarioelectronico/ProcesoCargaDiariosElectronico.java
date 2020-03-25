package com.davivienda.sara.dto.diarioelectronico;

import java.util.Date;

/**
 * 
 * @author jjvargas
 */
public class ProcesoCargaDiariosElectronico {
    private Integer codigoCajero ;
    private String nombre;
    private Integer registros;
    private Date fecha;
    
    public ProcesoCargaDiariosElectronico(){
        
    }

    public ProcesoCargaDiariosElectronico(Integer codigoCajero, String nombre, Long registros) {
        this.codigoCajero = codigoCajero;
        this.nombre = nombre;
        this.registros = registros.intValue();
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getRegistros() {
        return registros;
    }

    public void setRegistros(Integer registros) {
        this.registros = registros;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    

}
