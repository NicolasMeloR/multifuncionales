/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author jediazs
 */
public class ReintegrosMultifuncionalDTO {

    public int idRegistro;
    public String codigoCajero;    
    public String codigoOcca;
    public String numeroTransaccion;
    public String fechaCajero;
    public String numeroCuenta;
    public String tipoCuenta;
    public String numeroTarjeta;
    public String fecha;
    public String valor;
    public String totalBilletesConsignados;
    public String valorAjustar;
    public String statusTransaccion;
    public boolean checkSeleccion;
    public String usuarioRevisa;
    public String valorEntregado;
    
    private static final String valorAjustarRegexp = "[^0-9\\\\.\\\\,]*$";
    private static final Pattern valorAjustarPattern = Pattern.compile(valorAjustarRegexp);

    public ReintegrosMultifuncionalDTO() {
    }

    public ReintegrosMultifuncionalDTO entidadRevisionDTO(JSONObject item) throws JSONException {
        ReintegrosMultifuncionalDTO rep = new ReintegrosMultifuncionalDTO();
        rep.setIdRegistro(Integer.parseInt(item.getString("idRegistro")));
        rep.setCodigoCajero(item.getString("codigoCajero"));

        try {
            rep.setCodigoOcca(item.getString("codigoOcca"));
        } catch (JSONException ex) {
            rep.setCodigoOcca("");
        }

        rep.setNumeroTransaccion(item.getString("numeroTransaccion"));
        rep.setFechaCajero(item.getString("fechaCajero"));

        rep.setNumeroCuenta(item.getString("numeroCuenta"));
        rep.setTipoCuenta(item.getString("tipoCuenta"));

        try {
            rep.setNumeroTarjeta(item.getString("numeroTarjeta"));
        } catch (JSONException ex) {
            rep.setNumeroTarjeta("");
        }

        rep.setFecha(item.getString("fecha"));
        rep.setValor(item.getString("valor"));

        try {
            rep.setTotalBilletesConsignados(item.getString("totalBilletesConsignados"));
        } catch (JSONException ex) {
            rep.setTotalBilletesConsignados("");
        }
         
        rep.setValorAjustar(item.getString("valorAjustar"));
        
        try {
            rep.setUsuarioRevisa(item.getString("usuarioRevisa"));
        } catch (JSONException ex) {
            rep.setUsuarioRevisa("");
        }

        try {
            rep.setValorEntregado(item.getString("valorEntregado"));
        } catch (JSONException ex) {
            rep.setValorEntregado("");
        }

        try {
            rep.setStatusTransaccion(item.getString("statusTransaccion"));
        } catch (JSONException ex) {
            rep.setStatusTransaccion("");
        }

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

    public String getCodigoOcca() {
        return codigoOcca;
    }

    public void setCodigoOcca(String codigoOcca) {
        this.codigoOcca = codigoOcca;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
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
        
        if (valorAjustar != null) {
            valorAjustar = SaraUtil.stripXSS(valorAjustar, Constantes.REGEX_ACEPTAR_NUM_VALOR);
            Matcher m = valorAjustarPattern.matcher(valorAjustar);
            if (valorAjustar.trim().length() > 18 || !m.find()) {
               this.valorAjustar = "";
            }else{
                this.valorAjustar = valorAjustar;
            }
        }else{
            this.valorAjustar = "";
        }
        
    }

    public String getStatusTransaccion() {
        return statusTransaccion;
    }

    public void setStatusTransaccion(String statusTransaccion) {
        this.statusTransaccion = statusTransaccion;
    }

    public boolean isCheckSeleccion() {
        return checkSeleccion;
    }

    public void setCheckSeleccion(boolean checkSeleccion) {
        this.checkSeleccion = checkSeleccion;
    }

    public String getUsuarioRevisa() {
        return usuarioRevisa;
    }

    public void setUsuarioRevisa(String usuarioRevisa) {
        this.usuarioRevisa = usuarioRevisa;
    }

    public String getFechaCajero() {
        return fechaCajero;
    }

    public void setFechaCajero(String fechaCajero) {
        this.fechaCajero = fechaCajero;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getTotalBilletesConsignados() {
        return totalBilletesConsignados;
    }

    public void setTotalBilletesConsignados(String totalBilletesConsignados) {
        this.totalBilletesConsignados = totalBilletesConsignados;
    }

    public String getValorEntregado() {
        return valorEntregado;
    }

    public void setValorEntregado(String valorEntregado) {
        this.valorEntregado = valorEntregado;
    }

    
    
}
