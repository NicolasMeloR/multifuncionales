/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;

/**
 *
 * @author jmcastel
 */
public class HistoricoCargueDTO {
    
    private Integer idRegistro;
    private String codigoCajero;
    private String nombreArchivo;
    private String ciclo;
    private String fecha;
    private EstadoProceso estadoProceso;
    private String Error;
    private CodigoError descripcionError;
    private String TamanoBytes;
    private boolean Prueba;
    private String descripcionErrorMultifuncional;

    /**
     * @return the idRegistro
     */
    public Integer getIdRegistro() {
        return idRegistro;
    }

    /**
     * @param idRegistro the idRegistro to set
     */
    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    /**
     * @return the codigoCajero
     */
    public String getCodigoCajero() {
        return codigoCajero;
    }

    /**
     * @param codigoCajero the codigoCajero to set
     */
    public void setCodigoCajero(String codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the ciclo
     */
    public String getCiclo() {
        return ciclo;
    }

    /**
     * @param ciclo the ciclo to set
     */
    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the estadoProceso
     */
    public EstadoProceso getEstadoProceso() {
        return estadoProceso;
    }

    /**
     * @param estadoProceso the estadoProceso to set
     */
    public void setEstadoProceso(EstadoProceso estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    /**
     * @return the Error
     */
    public String getError() {
        return Error;
    }

    /**
     * @param Error the Error to set
     */
    public void setError(String Error) {
        this.Error = Error;
    }

    /**
     * @return the descripcionError
     */
    public CodigoError getDescripcionError() {
        return descripcionError;
    }

    /**
     * @param descripcionError the descripcionError to set
     */
    public void setDescripcionError(CodigoError descripcionError) {
        this.descripcionError = descripcionError;
    }

    /**
     * @return the TamanoBytes
     */
    public String getTamanoBytes() {
        return TamanoBytes;
    }

    /**
     * @param TamanoBytes the TamanoBytes to set
     */
    public void setTamanoBytes(String TamanoBytes) {
        this.TamanoBytes = TamanoBytes;
    }

    /**
     * @return the Prueba
     */
    public boolean getPrueba() {
        return Prueba;
    }

    /**
     * @param Prueba the Prueba to set
     */
    public void setPrueba(boolean Prueba) {
        this.Prueba = Prueba;
    }

    public String getDescripcionErrorMultifuncional() {
        return descripcionErrorMultifuncional;
    }

    public void setDescripcionErrorMultifuncional(String descripcionErrorMultifuncional) {
        this.descripcionErrorMultifuncional = descripcionErrorMultifuncional;
    }
    
    
}
