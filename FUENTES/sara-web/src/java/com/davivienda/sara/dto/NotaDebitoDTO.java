/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author jediazs
 */
public class NotaDebitoDTO {

    public int idRegistro;
    public String codigoCajero;
    public String codigoOcca;
    public String numeroCuenta;
    public String fecha;
    public String valor;
    public String valorAjustar;
    public boolean checkSeleccion;
    public String usuarioCrea;
    public String concepto;
    public String comision;

    public NotaDebitoDTO() {
    }

    public NotaDebitoDTO entidadDTO(JSONObject item) throws JSONException {
        NotaDebitoDTO rep = new NotaDebitoDTO();
        rep.setIdRegistro(Integer.parseInt(item.getString("idRegistro")));
        rep.setCodigoCajero(item.getString("codigoCajero"));
        rep.setCodigoOcca(item.getString("codigoOcca"));
        rep.setNumeroCuenta(item.getString("numeroCuenta"));
        rep.setFecha(item.getString("fecha"));
        rep.setValor(item.getString("valor"));
        rep.setValorAjustar(item.getString("valorAjustar"));
        rep.setCheckSeleccion(Boolean.parseBoolean(item.getString("checkSeleccion")));
        rep.setConcepto(item.getString("concepto")==null || item.getString("concepto").trim().isEmpty() ? "16" : item.getString("concepto"));
        rep.setComision(item.getString("comision")==null || item.getString("comision").trim().isEmpty() ? "0" : item.getString("comision"));
        
        try {
            rep.setUsuarioCrea(item.getString("usuarioCrea"));
        } catch (JSONException ex) {
            rep.setUsuarioCrea("");
        }

        return rep;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(String codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getCodigoOcca() {
        return codigoOcca;
    }

    public void setCodigoOcca(String codigoOcca) {
        this.codigoOcca = codigoOcca;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorAjustar() {
        return valorAjustar;
    }

    public void setValorAjustar(String valorAjustar) {
        this.valorAjustar = valorAjustar;
    }

    public boolean isCheckSeleccion() {
        return checkSeleccion;
    }

    public void setCheckSeleccion(boolean checkSeleccion) {
        this.checkSeleccion = checkSeleccion;
    }

    public String getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(String usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    
    
    @Override
    public String toString() {
        return "NotaDebitoDTO{" + "idRegistro=" + idRegistro + ", codigoCajero=" + codigoCajero + ", codigoOcca=" + codigoOcca + ", numeroCuenta=" + numeroCuenta + ", fecha=" + fecha + ", valor=" + valor + ", valorAjustar=" + valorAjustar + ", checkSeleccion=" + checkSeleccion + ", usuarioCrea=" + usuarioCrea + '}';
    }
}
