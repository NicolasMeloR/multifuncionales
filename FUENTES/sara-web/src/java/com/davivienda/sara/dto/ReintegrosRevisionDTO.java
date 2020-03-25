/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author jediazs
 */
public class ReintegrosRevisionDTO {

    public int idRegistro;
    public String codigoCajero;
    public String codigoOcca;
    public String numeroTransaccion;
    public String numeroCuenta;
    public String numeroTarjeta;
    public String fecha;
    public String fechaReversado;
    public String valor;
    public String valorAjustar;
    public String statusTransaccion;
    public String valorEntregado;
    public boolean checkSeleccion;
    public String redEnruta;
    public String usuarioRevisa;
    public int estadoReintegro;
    public String talon;
    public int tipoCuenta;
    public String concepto;
    public String comision;
    public Long fechaSistema;
    public Long consecutivo;

    private static final String valorAjustarRegexp = "[^0-9\\\\.\\\\,]*$";
    private static final Pattern valorAjustarPattern = Pattern.compile(valorAjustarRegexp);

    public ReintegrosRevisionDTO() {
    }

    public ReintegrosRevisionDTO entidadDTO(JSONObject item) throws JSONException {
        ReintegrosRevisionDTO rep = new ReintegrosRevisionDTO();
        try {
            rep.setIdRegistro(Integer.parseInt(item.getString("idRegistro")));
        } catch (JSONException ex) {
            rep.setIdRegistro(0);
        }

        try {
            rep.setCodigoCajero(item.getString("codigoCajero"));
        } catch (JSONException ex) {
            rep.setCodigoCajero("");
        }

        try {
            rep.setCodigoOcca(item.getString("codigoOcca"));
        } catch (JSONException ex) {
            rep.setCodigoOcca("");
        }

        try {
            rep.setNumeroTransaccion(item.getString("numeroTransaccion"));
        } catch (JSONException ex) {
            rep.setNumeroTransaccion("");
        }

        try {
            rep.setNumeroCuenta(item.getString("numeroCuenta"));
        } catch (JSONException ex) {
            rep.setNumeroCuenta("");
        }

        try {
            rep.setNumeroTarjeta(item.getString("numeroTarjeta"));
        } catch (JSONException ex) {
            rep.setNumeroTarjeta("");
        }

        try {
            String fechaSistema = item.getString("fechaSistema");
            Long fechaSistemaLong = new Long(fechaSistema);
            rep.setFechaSistema(fechaSistemaLong);
        } catch (JSONException ex) {
            rep.setFechaSistema(null);
        }
        try {
            rep.setFecha(item.getString("fecha"));
        } catch (JSONException ex) {
            rep.setFecha("");
        }

        try {
            rep.setFechaReversado(item.getString("fechaReversado"));
        } catch (JSONException ex) {
            rep.setFechaReversado("");
        }

        try {
            rep.setValor(item.getString("valor"));
        } catch (JSONException ex) {
            rep.setValor("");
        }

        try {
            rep.setValorAjustar(item.getString("valorAjustar"));
        } catch (JSONException ex) {
            rep.setValorAjustar("");
        }

        try {
            rep.setStatusTransaccion(item.getString("statusTransaccion"));
        } catch (JSONException ex) {
            rep.setStatusTransaccion("");
        }

        try {
            rep.setValorEntregado(item.getString("valorEntregado"));
        } catch (JSONException ex) {
            rep.setValorEntregado("");
        }

        try {
            rep.setCheckSeleccion(Boolean.parseBoolean(item.getString("checkSeleccion")));
        } catch (JSONException ex) {
            rep.setCheckSeleccion(false);
        }

        try {
            rep.setRedEnruta(item.getString("redEnruta"));
        } catch (JSONException ex) {
            rep.setRedEnruta("");
        }

        try {
            rep.setUsuarioRevisa(item.getString("usuarioRevisa"));
        } catch (JSONException ex) {
            rep.setUsuarioRevisa("");
        }

        try {
            rep.setEstadoReintegro(item.getInt("estadoReintegro"));
        } catch (JSONException ex) {
            rep.setEstadoReintegro(0);
        }

        try {
            rep.setTipoCuenta(Integer.parseInt(item.getString("tipocuentareintegro")));
        } catch (JSONException ex) {
            rep.setTipoCuenta(0);
        }

        try {
            Long consecutivo = new Long(item.getString("consecutivo"));
            rep.setConsecutivo(consecutivo);
        } catch (JSONException ex) {
            rep.setFechaSistema(null);
        }

        try {
            rep.setConcepto(item.getString("concepto"));
        } catch (JSONException ex) {
            rep.setConcepto("");
        }

        try {
            rep.setComision(item.getString("comision"));
        } catch (JSONException ex) {
            rep.setComision("");
        }

        return rep;
    }

    public ReintegrosRevisionDTO entidadDTOCreados(JSONObject item) throws JSONException {
        ReintegrosRevisionDTO rep = new ReintegrosRevisionDTO();
        rep.setIdRegistro(Integer.parseInt(item.getString("idRegistro")));
        rep.setCodigoCajero(item.getString("codigoCajero"));
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
            } else {
                this.valorAjustar = valorAjustar;
            }
        } else {
            this.valorAjustar = "";
        }

    }

    public String getStatusTransaccion() {
        return statusTransaccion;
    }

    public void setStatusTransaccion(String statusTransaccion) {
        this.statusTransaccion = statusTransaccion;
    }

    public String getValorEntregado() {
        return valorEntregado;
    }

    public void setValorEntregado(String valorEntregado) {
        this.valorEntregado = valorEntregado;
    }

    public boolean isCheckSeleccion() {
        return checkSeleccion;
    }

    public void setCheckSeleccion(boolean checkSeleccion) {
        this.checkSeleccion = checkSeleccion;
    }

    public String getRedEnruta() {
        return redEnruta;
    }

    public void setRedEnruta(String redEnruta) {
        this.redEnruta = redEnruta;
    }

    public String getUsuarioRevisa() {
        return usuarioRevisa;
    }

    public void setUsuarioRevisa(String usuarioRevisa) {
        this.usuarioRevisa = usuarioRevisa;
    }

    public int getEstadoReintegro() {
        return estadoReintegro;
    }

    public void setEstadoReintegro(int estadoReintegro) {
        this.estadoReintegro = estadoReintegro;
    }

    public String getTalon() {
        return talon;
    }

    public void setTalon(String talon) {
        this.talon = talon;
    }

    public int getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(int tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getFechaReversado() {
        return fechaReversado;
    }

    public void setFechaReversado(String fechaReversado) {
        this.fechaReversado = fechaReversado;
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

    public Long getFechaSistema() {
        return fechaSistema;
    }

    public void setFechaSistema(Long fechaSistema) {
        this.fechaSistema = fechaSistema;
    }

    public Long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    @Override
    public String toString() {
        return "ReintegrosRevisionDTO{" + "idRegistro=" + idRegistro + ", codigoCajero=" + codigoCajero + ", codigoOcca=" + codigoOcca + ", numeroTransaccion=" + numeroTransaccion + ", numeroCuenta=" + numeroCuenta + ", numeroTarjeta=" + numeroTarjeta + ", fecha=" + fecha + ", valor=" + valor + ", valorAjustar=" + valorAjustar + ", statusTransaccion=" + statusTransaccion + ", valorEntregado=" + valorEntregado + ", checkSeleccion=" + checkSeleccion + ", redEnruta=" + redEnruta + ", usuarioRevisa=" + usuarioRevisa + ", estadoReintegro=" + estadoReintegro + ", talon=" + talon + ", tipoCuenta=" + tipoCuenta + '}';
    }

}
