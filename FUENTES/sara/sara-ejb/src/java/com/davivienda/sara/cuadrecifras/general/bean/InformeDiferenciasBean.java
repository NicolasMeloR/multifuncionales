// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.general.bean;

import java.math.BigInteger;

public class InformeDiferenciasBean
{
    public BigInteger id;
    public Integer codigoCajero;
    public Integer codigoOcca;
    public String valorMaquina;
    public String valorLinea;
    public String valorDiferencias;
    public String aumento;
    public String disminucion;
    public String faltante;
    public String sobrante;
    public String aplicado;
    
    public BigInteger getId() {
        return this.id;
    }
    
    public void setId(final BigInteger id) {
        this.id = id;
    }
    
    public Integer getCodigoCajero() {
        return this.codigoCajero;
    }
    
    public void setCodigoCajero(final Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }
    
    public Integer getCodigoOcca() {
        return this.codigoOcca;
    }
    
    public void setCodigoOcca(final Integer codigoOcca) {
        this.codigoOcca = codigoOcca;
    }
    
    public String getValorMaquina() {
        return this.valorMaquina;
    }
    
    public void setValorMaquina(final String valorMaquina) {
        this.valorMaquina = valorMaquina;
    }
    
    public String getValorLinea() {
        return this.valorLinea;
    }
    
    public void setValorLinea(final String valorLinea) {
        this.valorLinea = valorLinea;
    }
    
    public String getValorDiferencias() {
        return this.valorDiferencias;
    }
    
    public void setValorDiferencias(final String valorDiferencias) {
        this.valorDiferencias = valorDiferencias;
    }
    
    public String getAumento() {
        return this.aumento;
    }
    
    public Long getAumentoLong() {
        return Long.parseLong(this.getAumento().replace(",", ""));
    }
    
    public void setAumento(final String aumento) {
        this.aumento = aumento;
    }
    
    public String getDisminucion() {
        return this.disminucion;
    }
    
    public Long getDisminucionLong() {
        return Long.parseLong(this.getDisminucion().replace(",", ""));
    }
    
    public void setDisminucion(final String disminucion) {
        this.disminucion = disminucion;
    }
    
    public String getFaltante() {
        return this.faltante;
    }
    
    public Long getFaltanteLong() {
        return Long.parseLong(this.getFaltante().replace(",", ""));
    }
    
    public void setFaltante(final String faltante) {
        this.faltante = faltante;
    }
    
    public String getSobrante() {
        return this.sobrante;
    }
    
    public Long getSobranteLong() {
        return Long.parseLong(this.getSobrante().replace(",", ""));
    }
    
    public void setSobrante(final String sobrante) {
        this.sobrante = sobrante;
    }
    
    public String getAplicado() {
        return this.aplicado;
    }
    
    public void setAplicado(final String aplicado) {
        this.aplicado = aplicado;
    }
}
