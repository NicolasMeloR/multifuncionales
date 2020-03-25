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
public class NotaDebitoMultifuncionalDTO {

    public int idRegistro;
    public String codigoCajero;
    public String numeroCuenta;
    public String codigoOficina;
    public String fecha;
    public String valor;
    public String valorAjustar;
    public boolean checkSeleccion;
    public String usuarioCrea;

    public NotaDebitoMultifuncionalDTO() {
    }

    public NotaDebitoMultifuncionalDTO entidadDTO(JSONObject item) throws JSONException {
        NotaDebitoMultifuncionalDTO rep = new NotaDebitoMultifuncionalDTO();
        rep.setIdRegistro(Integer.parseInt(item.getString("idRegistro")));
        rep.setCodigoCajero(item.getString("codigoCajero"));
        rep.setCodigoOficina(item.getString("codigoOficina"));
        rep.setNumeroCuenta(item.getString("numeroCuenta"));
        rep.setFecha(item.getString("fecha"));
        rep.setValor(item.getString("valor"));
        rep.setValorAjustar(item.getString("valorAjustar"));
        rep.setUsuarioCrea(item.getString("usuarioCrea"));
        rep.setCheckSeleccion(Boolean.parseBoolean(item.getString("checkSeleccion")));

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

    public String getCodigoOficina() {
        return codigoOficina;
    }

    public void setCodigoOficina(String codigoOficina) {
        this.codigoOficina = codigoOficina;
    }

   
    

    
}
