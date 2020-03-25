package com.davivienda.sara.cuadrecifras.general.bean;

import com.davivienda.utilidades.Constantes;
import java.io.Console;
import java.math.BigInteger;

/**
 * InformeDiferenciasBean.java
 *
 * Fecha : 10/04/2017, 05:53:43 PM Descripción :
 *
 * @author : jediazs@co.ibm.com
 * @version : 1.0
 */
public class InformeDiferenciasBean {

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
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public Integer getCodigoOcca() {
        return codigoOcca;
    }

    public void setCodigoOcca(Integer codigoOcca) {
        this.codigoOcca = codigoOcca;
    }

    public String getValorMaquina() {
        return valorMaquina;
    }

    public void setValorMaquina(String valorMaquina) {
        this.valorMaquina = valorMaquina;
    }

    public String getValorLinea() {
        return valorLinea;
    }

    public void setValorLinea(String valorLinea) {
        this.valorLinea = valorLinea;
    }

    public String getValorDiferencias() {
        return valorDiferencias;
    }

    public void setValorDiferencias(String valorDiferencias) {
        this.valorDiferencias = valorDiferencias;
    }

    public String getAumento() {
        return aumento;
    }

    public Long getAumentoLong() {
        return Long.parseLong(getAumento().replace(Constantes.CARACTER_COMA, ""));
    }

    public void setAumento(String aumento) {
        this.aumento = aumento;
    }

    public String getDisminucion() {
        return disminucion;
    }

    public Long getDisminucionLong() {
        return Long.parseLong(getDisminucion().replace(Constantes.CARACTER_COMA, ""));
    }

    public void setDisminucion(String disminucion) {
        this.disminucion = disminucion;
    }

    public String getFaltante() {
        return faltante;
    }

    public Long getFaltanteLong() {
        return Long.parseLong(getFaltante().replace(Constantes.CARACTER_COMA, ""));
    }

    public void setFaltante(String faltante) {
        this.faltante = faltante;
    }

    public String getSobrante() {
        return sobrante;
    }

    public Long getSobranteLong() {
        return Long.parseLong(getSobrante().replace(Constantes.CARACTER_COMA, "") );
    }

    public void setSobrante(String sobrante) {
        this.sobrante = sobrante;
    }

    public String getAplicado() {
        return aplicado;
    }

    public void setAplicado(String aplicado) {
        this.aplicado = aplicado;
    }

}
