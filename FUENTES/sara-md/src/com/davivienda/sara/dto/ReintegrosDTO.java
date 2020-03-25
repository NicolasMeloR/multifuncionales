/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosHisto;
import com.davivienda.sara.entitys.ReintegrosPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jediazs
 */
public class ReintegrosDTO {

    public Logger loggerApp;
    
    public ReintegrosDTO(Logger logger) {
        this.loggerApp = logger;
    }

    public Collection<Reintegros> reintegrosHistoAReintegros(Collection<ReintegrosHisto> reintegrosHisto){

        this.loggerApp.log(Level.INFO, "ReintegrosDTO - reintegrosHistoAReintegros reintegrosHisto: " +reintegrosHisto.size());
        Collection<Reintegros> resp = new ArrayList<>(reintegrosHisto.size());

        for (ReintegrosHisto item : reintegrosHisto) {
            Reintegros reintegro = new Reintegros();

            ReintegrosPK reintegrosPK = new ReintegrosPK();
            reintegrosPK.setHCodigocajero(item.getReintegrosHistoPK().getHCodigocajero());
            reintegrosPK.setHFechasistema(item.getReintegrosHistoPK().getHFechasistema());
            reintegrosPK.setHTalon(item.getReintegrosHistoPK().getHTalon());

            reintegro.setReintegrosPK(reintegrosPK);
            reintegro.setHNumerocuenta(item.getHNumerocuenta());
            reintegro.setHDatostarjeta(item.getHDatostarjeta());
            reintegro.setHFecha(item.getHFecha());
            reintegro.setHTipotransaccion(item.getHTipotransaccion());
            reintegro.setHCodigoocca(item.getHCodigoocca());
            reintegro.setHIndices(item.getHIndices());
            reintegro.setHTipotarjeta(item.getHTipotarjeta());
            reintegro.setHValor(item.getHValor());
            reintegro.setHFiller(item.getHFiller());
            reintegro.setTCodigocajero(item.getTCodigocajero());
            reintegro.setTNumerotransaccion(item.getTNumerotransaccion());
            reintegro.setTFechatransaccion(item.getTFechatransaccion());
            reintegro.setTTipotransaccion(item.getTTipotransaccion());
            reintegro.setTCodigotransaccion(item.getTCodigotransaccion());
            reintegro.setTErrortransaccion(item.getTErrortransaccion());
            reintegro.setTValorsolicitado(item.getTValorsolicitado());
            reintegro.setTValorentregado(item.getTValorentregado());
            reintegro.setTTarjeta(item.getTTarjeta());
            reintegro.setTCuenta(item.getTCuenta());
            reintegro.setTCodigoterminaciontransaccion(item.getTCodigoterminaciontransaccion());
            reintegro.setTReferencia(item.getTReferencia());
            reintegro.setValorajustado(item.getValorajustado());
            reintegro.setEstadoreintegro(item.getEstadoreintegro());
            reintegro.setUsuariorevisa(item.getUsuariorevisa());
            reintegro.setUsuarioautoriza(item.getUsuarioautoriza());
            reintegro.setFechareintegro(item.getFechareintegro());
            reintegro.setErrorreintegro(item.getErrorreintegro());
            reintegro.setTipoCuentaReintegro(item.getTipoCuentaReintegro());
            reintegro.setHOfiRadicacion(item.getHOfiRadicacion());
            reintegro.sethNumerocuentaOrigen(item.gethNumerocuentaOrigen());
            reintegro.setDispensed(item.getDispensed());
            reintegro.setRemaining(item.getRemaining());
            reintegro.setDifeDescuadre(item.getDifeDescuadre());
            resp.add(reintegro);

        }
        
        this.loggerApp.log(Level.INFO, "ReintegrosDTO - reintegrosHistoAReintegros resp: " +resp.size());

        return resp;
    }

    public Reintegros reintegrosHistoAReintegros(ReintegrosHisto item) throws Exception {

        Reintegros reintegro = new Reintegros();

        ReintegrosPK reintegrosPK = new ReintegrosPK();
        reintegrosPK.setHCodigocajero(item.getReintegrosHistoPK().getHCodigocajero());
        reintegrosPK.setHFechasistema(item.getReintegrosHistoPK().getHFechasistema());
        reintegrosPK.setHTalon(item.getReintegrosHistoPK().getHTalon());

        reintegro.setReintegrosPK(reintegrosPK);
        reintegro.setHNumerocuenta(item.getHNumerocuenta());
        reintegro.setHDatostarjeta(item.getHDatostarjeta());
        reintegro.setHFecha(item.getHFecha());
        reintegro.setHTipotransaccion(item.getHTipotransaccion());
        reintegro.setHCodigoocca(item.getHCodigoocca());
        reintegro.setHIndices(item.getHIndices());
        reintegro.setHTipotarjeta(item.getHTipotarjeta());
        reintegro.setHValor(item.getHValor());
        reintegro.setHFiller(item.getHFiller());
        reintegro.setTCodigocajero(item.getTCodigocajero());
        reintegro.setTNumerotransaccion(item.getTNumerotransaccion());
        reintegro.setTFechatransaccion(item.getTFechatransaccion());
        reintegro.setTTipotransaccion(item.getTTipotransaccion());
        reintegro.setTCodigotransaccion(item.getTCodigotransaccion());
        reintegro.setTErrortransaccion(item.getTErrortransaccion());
        reintegro.setTValorsolicitado(item.getTValorsolicitado());
        reintegro.setTValorentregado(item.getTValorentregado());
        reintegro.setTTarjeta(item.getTTarjeta());
        reintegro.setTCuenta(item.getTCuenta());
        reintegro.setTCodigoterminaciontransaccion(item.getTCodigoterminaciontransaccion());
        reintegro.setTReferencia(item.getTReferencia());
        reintegro.setValorajustado(item.getValorajustado());
        reintegro.setEstadoreintegro(item.getEstadoreintegro());
        reintegro.setUsuariorevisa(item.getUsuariorevisa());
        reintegro.setUsuarioautoriza(item.getUsuarioautoriza());
        reintegro.setFechareintegro(item.getFechareintegro());
        reintegro.setErrorreintegro(item.getErrorreintegro());
        reintegro.setTipoCuentaReintegro(item.getTipoCuentaReintegro());
        reintegro.setHOfiRadicacion(item.getHOfiRadicacion());
        reintegro.sethNumerocuentaOrigen(item.gethNumerocuentaOrigen());
        reintegro.setDispensed(item.getDispensed());
        reintegro.setRemaining(item.getRemaining());
        reintegro.setDifeDescuadre(item.getDifeDescuadre());

        return reintegro;
    }

}
