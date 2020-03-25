/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.constantes.CajeroActivo;
import com.davivienda.sara.constantes.TipoCajeroMulti;
import com.davivienda.sara.constantes.TipoProvision;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.entitys.Oficinamulti;
import com.davivienda.sara.entitys.TipoCajero;
import com.davivienda.sara.entitys.Transportadora;
import com.davivienda.sara.entitys.Ubicacion;
import javax.servlet.ServletException;

/**
 *
 * @author jediazs
 */
public class CajeroDTO {

    public int idCajero;
    private Integer codigoCajero;
    private String tipoCajeroFuncion;
    private String serie;
    private Integer activo;
    private String nombre;
    private String versionAplicativo;
    private Integer tipoProvision;
    private Long provisionAprobada;
    private Integer gav1Denominacion;
    private Integer gav2Denominacion;
    private Integer gav3Denominacion;
    private Integer gav4Denominacion;
    private Integer numeroGavetas;
    private Integer tipoLecturaEDC;
    private String ubicacionEDC;
    private String tipoEncripcion;
    private Integer tipocajeromulti;
    private Integer idtransportadora2;
    private String horario;
    private String red;
    private String direccion;
    private Integer version;
    private Integer occa;
    private Integer tipoCajero;
    private Integer transportadora;
    private Integer ubicacion;
    private Integer oficinaMulti;
    private String codigoDispensador;

    public void limpiarCajeroDTO(int occa, int oficina, int tipoCajero, int ubicacion, int transportadora) {
        this.idCajero = 0;
        this.codigoCajero = 0;
        this.tipoCajeroFuncion = "0";
        this.serie = "";
        this.nombre = "";
        this.tipoProvision = 0;
        this.versionAplicativo = "";
        this.gav1Denominacion = 0;
        this.gav2Denominacion = 0;
        this.gav3Denominacion = 0;
        this.gav4Denominacion = 0;
        this.numeroGavetas = 0;
        this.activo = 0;
        this.tipoLecturaEDC = 0;
        this.ubicacionEDC = "";
        this.tipoEncripcion = "";
        this.ubicacion = ubicacion;
        this.tipoCajero = tipoCajero;
        this.occa = occa;
        this.transportadora = transportadora;
        this.idtransportadora2 = 0;
        this.oficinaMulti = oficina;
        this.red = "0";
        this.horario = "";
        this.direccion = "";
        this.codigoDispensador = "";
    }

    public CajeroDTO entidadDTO(Cajero item) {
        CajeroDTO respItems = new CajeroDTO();
        respItems.setCodigoCajero(item.getCodigoCajero());
        respItems.setSerie(item.getSerie());
        respItems.setNombre(item.getNombre());
        respItems.setVersionAplicativo(item.getVersionAplicativo());
        respItems.setTipoProvision(item.getTipoProvision());
        respItems.setProvisionAprobada(item.getProvisionAprobada());
        respItems.setGav1Denominacion(item.getGav1Denominacion());
        respItems.setGav2Denominacion(item.getGav2Denominacion());
        respItems.setGav3Denominacion(item.getGav3Denominacion());
        respItems.setGav4Denominacion(item.getGav4Denominacion());
        respItems.setNumeroGavetas(item.getNumeroGavetas());
        respItems.setActivo(item.getActivo());
        respItems.setTipoLecturaEDC(item.getTipoLecturaEDC());
        respItems.setUbicacionEDC(item.getUbicacionEDC());
        respItems.setTipoEncripcion(item.getTipoEncripcion());
        respItems.setUbicacion(item.getUbicacion().getCodigoUbicacion());
        respItems.setTipoCajero(item.getTipoCajero().getCodigoTipoCajero());
        respItems.setTipocajeromulti(item.getTipocajeroMulti());
        respItems.setOcca(item.getOcca().getCodigoOcca());
        respItems.setTransportadora(item.getTransportadora().getIdTransportadora());
        respItems.setIdtransportadora2(item.getIdtransportadora2() == null ? 0 : item.getIdtransportadora2());
        respItems.setOficinaMulti(item.getOficinaMulti().getCodigooficinamulti());
        respItems.setRed(item.getRed() == null ? "0" : item.getRed());
        respItems.setHorario(item.getHorario() == null ? "" : item.getHorario());
        respItems.setDireccion(item.getDireccion() == null ? "" : item.getDireccion());
        respItems.setTipoCajeroFuncion(String.valueOf(item.getTipocajeroMulti()));
        respItems.setCodigoDispensador(item.getCodigoDispensador() == null ? "" : String.valueOf(item.getCodigoDispensador()));
        return respItems;
    }

    public Cajero entidad() throws ServletException {
        Cajero tc = new Cajero();
        Integer intTmp = null;
        Long lngTmp = null;
        String strTmp;
        intTmp = getCodigoCajero();
        if (intTmp == null) {
            throw new ServletException("Código Cajero inválido");
        }
        tc.setCodigoCajero(intTmp);

        strTmp = getSerie();
        if (strTmp.length() == 0) {
            throw new ServletException("serie Cajero inválido");
        }
        tc.setSerie(strTmp);

        strTmp = getNombre();
        if (strTmp.length() == 0) {
            throw new ServletException("nombre Cajero inválido");
        }
        tc.setNombre(strTmp);

        strTmp = getVersionAplicativo();
        if (strTmp.length() == 0) {
            throw new ServletException("versionApp Cajero inválido");
        }
        tc.setVersionAplicativo(strTmp);

        intTmp = getTipoProvision();
        if (intTmp == null) {
            throw new ServletException("Tipo Provision inválido");
        }
        tc.setTipoProvision(intTmp);

        lngTmp = getProvisionAprobada();
        if (lngTmp == null) {
            throw new ServletException("provAprobada oficina inválido");
        }
        tc.setProvisionAprobada(lngTmp);

        intTmp = getGav1Denominacion();
        if (intTmp == null) {
            throw new ServletException("gaveta 1 Denominacion inválido");
        }
        tc.setGav1Denominacion(intTmp);

        intTmp = getGav2Denominacion();
        if (intTmp == null) {
            throw new ServletException("gaveta 2 Denominacion inválido");
        }
        tc.setGav2Denominacion(intTmp);

        intTmp = getGav3Denominacion();
        if (intTmp == null) {
            throw new ServletException("gaveta 3 Denominacion inválido");
        }
        tc.setGav3Denominacion(intTmp);

        intTmp = getGav4Denominacion();
        if (intTmp == null) {
            throw new ServletException("gaveta 4 Denominacion inválido");
        }
        tc.setGav4Denominacion(intTmp);

        intTmp = getNumeroGavetas();
        if (intTmp == null) {
            throw new ServletException("numero de gavetas inválido");
        }
        tc.setNumeroGavetas(intTmp);

        intTmp = getActivo();
        if (intTmp == null) {
            throw new ServletException("cajero activo inválido");
        }
        tc.setActivo(intTmp);

        intTmp = Integer.parseInt(getTipoCajeroFuncion());
        if (intTmp == null) {
            throw new ServletException("TipoCajeroMulti activo inválido");
        }
        tc.setTipocajeroMulti(intTmp);

        intTmp = getTipoLecturaEDC();
        if (intTmp == null) {
            throw new ServletException("tipoLectura EDC activo inválido");
        }
        tc.setTipoLecturaEDC(intTmp);

        strTmp = getUbicacionEDC();
        if (strTmp.length() == 0) {
            throw new ServletException("ubicacionEDC inválida");
        }
        tc.setUbicacionEDC(strTmp);

        strTmp = getTipoEncripcion();
        if (strTmp.length() == 0) {
            throw new ServletException("tipoEncripcion inválida");
        }
        tc.setTipoEncripcion(strTmp);

        intTmp = getTransportadora();
        if (intTmp == null) {
            throw new ServletException("Codigo Transportadora inválido");
        }
        Transportadora transportadora = new Transportadora();
        transportadora.setIdTransportadora(intTmp);
        tc.setTransportadora(transportadora);

        intTmp = getIdtransportadora2();
        if (intTmp == null) {
            throw new ServletException("Codigo Transportadora2 inválido");
        }

        tc.setIdtransportadora2(intTmp);

        intTmp = getTipoCajero();
        if (intTmp == null) {
            throw new ServletException("Tipo Cajero inválido");
        }
        TipoCajero tipoCajero = new TipoCajero();
        tipoCajero.setCodigoTipoCajero(intTmp);
        tc.setTipoCajero(tipoCajero);

        intTmp = getOcca();
        if (intTmp == null) {
            throw new ServletException("Codigo Occa inválido");
        }
        Occa occa = new Occa();
        occa.setCodigoOcca(intTmp);
        tc.setOcca(occa);

        intTmp = getUbicacion();
        if (intTmp == null) {
            throw new ServletException("Codigo Ubicacion inválido");
        }
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setCodigoUbicacion(intTmp);
        tc.setUbicacion(ubicacion);

        intTmp = getOficinaMulti();
        if (intTmp == null) {
            throw new ServletException("CodigoOficinaMulti inválido");
        }
        Oficinamulti oficinamulti = new Oficinamulti();
        oficinamulti.setCodigooficinamulti(intTmp);
        tc.setOficinaMulti(oficinamulti);

        strTmp = getHorario();
        if (strTmp.length() == 0) {
            throw new ServletException("horario inválido");
        }
        tc.setHorario(strTmp);

        strTmp = getRed();
        if (strTmp.length() == 0) {
            throw new ServletException("red inválida");
        }
        tc.setRed(strTmp);

        strTmp = getDireccion();
        if (strTmp.length() == 0) {
            throw new ServletException("direccion inválida");
        }
        tc.setDireccion(strTmp);
        
        
        if(null != getCodigoDispensador() && !getCodigoDispensador().equals("")){
            tc.setCodigoDispensador(Integer.parseInt(getCodigoDispensador()));
        }
        
        return tc;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    public String getTipoCajeroFuncion() {
        return tipoCajeroFuncion;
    }

    public void setTipoCajeroFuncion(String tipoCajeroFuncion) {
        this.tipoCajeroFuncion = tipoCajeroFuncion;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVersionAplicativo() {
        return versionAplicativo;
    }

    public void setVersionAplicativo(String versionAplicativo) {
        this.versionAplicativo = versionAplicativo;
    }

    public Integer getTipoProvision() {
        return tipoProvision;
    }

    public void setTipoProvision(Integer tipoProvision) {
        this.tipoProvision = tipoProvision;
    }

    public Long getProvisionAprobada() {
        return provisionAprobada;
    }

    public void setProvisionAprobada(Long provisionAprobada) {
        this.provisionAprobada = provisionAprobada;
    }

    public Integer getGav1Denominacion() {
        return gav1Denominacion;
    }

    public void setGav1Denominacion(Integer gav1Denominacion) {
        this.gav1Denominacion = gav1Denominacion;
    }

    public Integer getGav2Denominacion() {
        return gav2Denominacion;
    }

    public void setGav2Denominacion(Integer gav2Denominacion) {
        this.gav2Denominacion = gav2Denominacion;
    }

    public Integer getGav3Denominacion() {
        return gav3Denominacion;
    }

    public void setGav3Denominacion(Integer gav3Denominacion) {
        this.gav3Denominacion = gav3Denominacion;
    }

    public Integer getGav4Denominacion() {
        return gav4Denominacion;
    }

    public void setGav4Denominacion(Integer gav4Denominacion) {
        this.gav4Denominacion = gav4Denominacion;
    }

    public Integer getNumeroGavetas() {
        return numeroGavetas;
    }

    public void setNumeroGavetas(Integer numeroGavetas) {
        this.numeroGavetas = numeroGavetas;
    }

    public Integer getTipoLecturaEDC() {
        return tipoLecturaEDC;
    }

    public void setTipoLecturaEDC(Integer tipoLecturaEDC) {
        this.tipoLecturaEDC = tipoLecturaEDC;
    }

    public String getUbicacionEDC() {
        return ubicacionEDC;
    }

    public void setUbicacionEDC(String ubicacionEDC) {
        this.ubicacionEDC = ubicacionEDC;
    }

    public String getTipoEncripcion() {
        return tipoEncripcion;
    }

    public void setTipoEncripcion(String tipoEncripcion) {
        this.tipoEncripcion = tipoEncripcion;
    }

    public Integer getTipocajeromulti() {
        return tipocajeromulti;
    }

    public void setTipocajeromulti(Integer tipocajeromulti) {
        this.tipocajeromulti = tipocajeromulti;
    }

    public Integer getIdtransportadora2() {
        return idtransportadora2;
    }

    public void setIdtransportadora2(Integer idtransportadora2) {
        this.idtransportadora2 = idtransportadora2;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getOcca() {
        return occa;
    }

    public void setOcca(Integer occa) {
        this.occa = occa;
    }

    public Integer getTipoCajero() {
        return tipoCajero;
    }

    public void setTipoCajero(Integer tipoCajero) {
        this.tipoCajero = tipoCajero;
    }

    public Integer getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Integer transportadora) {
        this.transportadora = transportadora;
    }

    public Integer getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getOficinaMulti() {
        return oficinaMulti;
    }

    public void setOficinaMulti(Integer oficinaMulti) {
        this.oficinaMulti = oficinaMulti;
    }

    public String getCodigoDispensador() {
        return codigoDispensador;
    }

    public void setCodigoDispensador(String codigoDispensador) {
        this.codigoDispensador = codigoDispensador;
    }

    

    
    @Override
    public String toString() {
        return "CajeroDTO{" + "idCajero=" + idCajero + ", codigoCajero=" + codigoCajero + ", tipoCajeroFuncion=" + tipoCajeroFuncion + ", serie=" + serie + ", activo=" + activo + ", nombre=" + nombre + ", versionAplicativo=" + versionAplicativo + ", tipoProvision=" + tipoProvision + ", provisionAprobada=" + provisionAprobada + ", gav1Denominacion=" + gav1Denominacion + ", gav2Denominacion=" + gav2Denominacion + ", gav3Denominacion=" + gav3Denominacion + ", gav4Denominacion=" + gav4Denominacion + ", numeroGavetas=" + numeroGavetas + ", tipoLecturaEDC=" + tipoLecturaEDC + ", ubicacionEDC=" + ubicacionEDC + ", tipoEncripcion=" + tipoEncripcion + ", tipocajeromulti=" + tipocajeromulti + ", idtransportadora2=" + idtransportadora2 + ", horario=" + horario + ", red=" + red + ", direccion=" + direccion + ", version=" + version + ", occa=" + occa + ", tipoCajero=" + tipoCajero + ", transportadora=" + transportadora + ", ubicacion=" + ubicacion + ", oficinaMulti=" + oficinaMulti + '}';
    }

}
