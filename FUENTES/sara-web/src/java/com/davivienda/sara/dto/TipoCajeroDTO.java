/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.constantes.AplicativoCajero;
import com.davivienda.sara.constantes.MarcaCajero;
import com.davivienda.sara.constantes.SistemaOperativo;
import com.davivienda.sara.entitys.TipoCajero;
import javax.servlet.ServletException;

/**
 *
 * @author jediazs
 */
public class TipoCajeroDTO {

    public int idCajero;
    private Integer codigoTipoCajero;
    private String descripcion;
    private Integer marca;
    private String modelo;
    private Integer sistemaOperativo;
    private Integer aplicativoCajero;
    private Integer formatoDiarioElectronico;
    private String papelImpresion;

    public void limpiarTipoCajeroDTO() {
        this.idCajero = 0;
        this.codigoTipoCajero = 0;
        this.descripcion = "";
        this.marca = 0;
        this.modelo = "";
        this.sistemaOperativo = 0;
        this.aplicativoCajero = 0;
        this.formatoDiarioElectronico = 0;
    }

    public TipoCajeroDTO entidadDTO(TipoCajero item) {
        TipoCajeroDTO respItems = new TipoCajeroDTO();
        respItems.setCodigoTipoCajero(item.getCodigoTipoCajero());
        respItems.setDescripcion(item.getDescripcion());
        respItems.setMarca(item.getMarca());
        respItems.setModelo(item.getModelo());
        respItems.setSistemaOperativo(item.getSistemaOperativo());
        respItems.setAplicativoCajero(item.getAplicativoCajero());
        respItems.setFormatoDiarioElectronico(item.getFormatoDiarioElectronico());
        respItems.setPapelImpresion(item.getPapelImpresion());
        return respItems;
    }

    public TipoCajero entidad() throws ServletException {
        TipoCajero tc = new TipoCajero();
        Integer intTmp = null;
        String strTmp = "";

        intTmp = getCodigoTipoCajero();
        if (intTmp == null) {
            throw new ServletException("Código TipoCajero inválido");
        }

        tc.setCodigoTipoCajero(intTmp);

        strTmp = getDescripcion();
        if (strTmp.length() == 0) {
            throw new ServletException("Descripción inválida");
        }
        tc.setDescripcion(strTmp);

        intTmp = getMarca();
        if (intTmp == null) {
            throw new ServletException("marca inválida");
        }
        tc.setMarca(intTmp);

        strTmp = getModelo();
        if (strTmp.length() == 0) {
            throw new ServletException("modelo inválido");
        }
        tc.setModelo(strTmp);

        intTmp = getSistemaOperativo();
        if (intTmp == null) {
            throw new ServletException("sistemaOperativo inválido");
        }
        tc.setSistemaOperativo(intTmp);

        intTmp = getAplicativoCajero();
        if (intTmp == null) {
            throw new ServletException("aplicativoCajero inválido");
        }
        tc.setAplicativoCajero(intTmp);

        intTmp = getFormatoDiarioElectronico();
        if (intTmp == null) {
            throw new ServletException("formato Diario Electronico inválido");
        }
        tc.setFormatoDiarioElectronico(intTmp);

        strTmp = getPapelImpresion();
        if (strTmp.length() == 0) {
            throw new ServletException("papel Impresion inválido");
        }
        tc.setPapelImpresion(strTmp);
        return tc;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public Integer getCodigoTipoCajero() {
        return codigoTipoCajero;
    }

    public void setCodigoTipoCajero(Integer codigoTipoCajero) {
        this.codigoTipoCajero = codigoTipoCajero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getMarca() {
        return marca;
    }

    public void setMarca(Integer marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(Integer sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public Integer getAplicativoCajero() {
        return aplicativoCajero;
    }

    public void setAplicativoCajero(Integer aplicativoCajero) {
        this.aplicativoCajero = aplicativoCajero;
    }

    public Integer getFormatoDiarioElectronico() {
        return formatoDiarioElectronico;
    }

    public void setFormatoDiarioElectronico(Integer formatoDiarioElectronico) {
        this.formatoDiarioElectronico = formatoDiarioElectronico;
    }

    public String getPapelImpresion() {
        return papelImpresion;
    }

    public void setPapelImpresion(String papelImpresion) {
        this.papelImpresion = papelImpresion;
    }

    @Override
    public String toString() {
        return "TipoCajeroDTO{" + "idCajero=" + idCajero + ", codigoTipoCajero=" + codigoTipoCajero + ", descripcion=" + descripcion + ", marca=" + marca + ", modelo=" + modelo + ", sistemaOperativo=" + sistemaOperativo + ", aplicativoCajero=" + aplicativoCajero + ", formatoDiarioElectronico=" + formatoDiarioElectronico + ", papelImpresion=" + papelImpresion + '}';
    }

}
