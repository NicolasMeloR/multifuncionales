/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.entitys.ReintegrosmultiefectivoHisto;
import com.davivienda.sara.entitys.ReintegrosmultiefectivoPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jediazs
 */
public class ReintegrosMultiEfeDTO {

    public Logger loggerApp;

    public ReintegrosMultiEfeDTO(Logger logger) {
        this.loggerApp = logger;
    }

    public Collection<Reintegrosmultiefectivo> reintegrosMultiEfeHistoAReintegrosMultiEfe(Collection<ReintegrosmultiefectivoHisto> reintegrosmultiefectivoHistos) {

        this.loggerApp.log(Level.INFO, "ReintegrosMultiEfeDTO - reintegrosMultiEfeHistoAReintegrosMultiEfe reintegrosmultiefectivoHistos: " + reintegrosmultiefectivoHistos.size());
        Collection<Reintegrosmultiefectivo> resp = new ArrayList<>(reintegrosmultiefectivoHistos.size());

        for (ReintegrosmultiefectivoHisto item : reintegrosmultiefectivoHistos) {
            Reintegrosmultiefectivo reintegro = new Reintegrosmultiefectivo();

            ReintegrosmultiefectivoPK reintegrosPK = new ReintegrosmultiefectivoPK();
            reintegrosPK.setHCodigocajero(item.getReintegrosmultiefectivoHistoPK().getHCodigocajero());
            reintegrosPK.setHFechasistema(item.getReintegrosmultiefectivoHistoPK().getHFechasistema());
            reintegrosPK.setHTalon(item.getReintegrosmultiefectivoHistoPK().getHTalon());

            reintegro.setReintegrosmultiefectivoPK(reintegrosPK);
            reintegro.setHNumerocuenta(item.getHNumerocuenta());
            reintegro.setHDatostarjeta(item.getHDatostarjeta());
            reintegro.setHFecha(item.getHFecha());
            reintegro.setHTipotransaccion(item.getHTipotransaccion());
            reintegro.setHCodigooficina(item.getHCodigooficina());
            reintegro.setHIndices(item.getHIndices());
            reintegro.setHTipotarjeta(item.getHTipotarjeta());
            reintegro.setHValor(item.getHValor());
            reintegro.setHFiller(item.getHFiller());
            reintegro.setTCodigotransaccion(item.getTCodigotransaccion());
            reintegro.setTEstado(item.getTEstado());
            reintegro.setTCodigocajero(item.getTCodigocajero());
            reintegro.setTNumerocorte(item.getTNumerocorte());
            reintegro.setTTransaccionconsecutivo(item.getTTransaccionconsecutivo());
            reintegro.setTFechacierre(item.getTFechacierre());
            reintegro.setTFechacajero(item.getTFechacajero());
            reintegro.setTTipocuenta(item.getTTipocuenta());
            reintegro.setTNumerodecuentaconsignar(item.getTNumerodecuentaconsignar());
            reintegro.setTNumerodecuentahomologa(item.getTNumerodecuentahomologa());
            reintegro.setTValorconsignacion(item.getTValorconsignacion());
            reintegro.setTNobilletesnd(item.getTNobilletesnd());
            reintegro.setTNobilletesdenf(item.getTNobilletesdenf());
            reintegro.setTNobilletesdene(item.getTNobilletesdene());
            reintegro.setTNobilletesdend(item.getTNobilletesdend());
            reintegro.setTNobilletesdenc(item.getTNobilletesdenc());
            reintegro.setTNobilletesdenb(item.getTNobilletesdenb());
            reintegro.setTNobilletesdena(item.getTNobilletesdena());
            reintegro.setTTotalbilletesconsig(item.getTTotalbilletesconsig());
            reintegro.setTSecuencia(item.getTSecuencia());
            reintegro.setValorajustado(item.getValorajustado());
            reintegro.setEstadoreintegro(item.getEstadoreintegro());
            reintegro.setUsuariosrevisa(item.getUsuariosrevisa());
            reintegro.setUsuarioautoriza(item.getUsuarioautoriza());
            reintegro.setFechareintegro(item.getFechareintegro());
            reintegro.setErrorreintegro(item.getErrorreintegro());
            reintegro.setTipocuentareintegro(item.getTipocuentareintegro());
            reintegro.setVersion(item.getVersion());

            resp.add(reintegro);

        }

        this.loggerApp.log(Level.INFO, "ReintegrosMultiEfeDTO - reintegrosMultiEfeHistoAReintegrosMultiEfe resp: " + resp.size());

        return resp;
    }

    public Reintegrosmultiefectivo reintegrosMultiEfeHistoAReintegrosMultiEfe(ReintegrosmultiefectivoHisto item) throws Exception {

        this.loggerApp.log(Level.INFO, "ReintegrosMultiEfeDTO - reintegrosMultiEfeHistoAReintegrosMultiEfe ReintegrosmultiefectivoHisto: " + item);
        
        Reintegrosmultiefectivo reintegro = new Reintegrosmultiefectivo();

        ReintegrosmultiefectivoPK reintegrosPK = new ReintegrosmultiefectivoPK();
        reintegrosPK.setHCodigocajero(item.getReintegrosmultiefectivoHistoPK().getHCodigocajero());
        reintegrosPK.setHFechasistema(item.getReintegrosmultiefectivoHistoPK().getHFechasistema());
        reintegrosPK.setHTalon(item.getReintegrosmultiefectivoHistoPK().getHTalon());

        reintegro.setReintegrosmultiefectivoPK(reintegrosPK);
        reintegro.setHNumerocuenta(item.getHNumerocuenta());
        reintegro.setHDatostarjeta(item.getHDatostarjeta());
        reintegro.setHFecha(item.getHFecha());
        reintegro.setHTipotransaccion(item.getHTipotransaccion());
        reintegro.setHCodigooficina(item.getHCodigooficina());
        reintegro.setHIndices(item.getHIndices());
        reintegro.setHTipotarjeta(item.getHTipotarjeta());
        reintegro.setHValor(item.getHValor());
        reintegro.setHFiller(item.getHFiller());
        reintegro.setTCodigotransaccion(item.getTCodigotransaccion());
        reintegro.setTEstado(item.getTEstado());
        reintegro.setTCodigocajero(item.getTCodigocajero());
        reintegro.setTNumerocorte(item.getTNumerocorte());
        reintegro.setTTransaccionconsecutivo(item.getTTransaccionconsecutivo());
        reintegro.setTFechacierre(item.getTFechacierre());
        reintegro.setTFechacajero(item.getTFechacajero());
        reintegro.setTTipocuenta(item.getTTipocuenta());
        reintegro.setTNumerodecuentaconsignar(item.getTNumerodecuentaconsignar());
        reintegro.setTNumerodecuentahomologa(item.getTNumerodecuentahomologa());
        reintegro.setTValorconsignacion(item.getTValorconsignacion());
        reintegro.setTNobilletesnd(item.getTNobilletesnd());
        reintegro.setTNobilletesdenf(item.getTNobilletesdenf());
        reintegro.setTNobilletesdene(item.getTNobilletesdene());
        reintegro.setTNobilletesdend(item.getTNobilletesdend());
        reintegro.setTNobilletesdenc(item.getTNobilletesdenc());
        reintegro.setTNobilletesdenb(item.getTNobilletesdenb());
        reintegro.setTNobilletesdena(item.getTNobilletesdena());
        reintegro.setTTotalbilletesconsig(item.getTTotalbilletesconsig());
        reintegro.setTSecuencia(item.getTSecuencia());
        reintegro.setValorajustado(item.getValorajustado());
        reintegro.setEstadoreintegro(item.getEstadoreintegro());
        reintegro.setUsuariosrevisa(item.getUsuariosrevisa());
        reintegro.setUsuarioautoriza(item.getUsuarioautoriza());
        reintegro.setFechareintegro(item.getFechareintegro());
        reintegro.setErrorreintegro(item.getErrorreintegro());
        reintegro.setTipocuentareintegro(item.getTipocuentareintegro());
        reintegro.setVersion(item.getVersion());

        return reintegro;
    }

}
