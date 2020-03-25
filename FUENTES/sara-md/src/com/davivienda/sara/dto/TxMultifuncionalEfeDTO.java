/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.entitys.TxmultifuncionalefectivoHisto;
import com.davivienda.sara.entitys.TxmultifuncionalefectivoPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jediazs
 */
public class TxMultifuncionalEfeDTO {

    public Logger loggerApp;

    public TxMultifuncionalEfeDTO(Logger logger) {
        this.loggerApp = logger;
    }

    public Collection<Txmultifuncionalefectivo> txMultiEfeHistoATxMultiEfe(Collection<TxmultifuncionalefectivoHisto> txmultifuncionalefectivoHisto) {

        this.loggerApp.log(Level.INFO, "TxMultifuncionalEfeDTO - txMultiEfeHistoATxMultiEfe txmultifuncionalefectivoHisto: " + txmultifuncionalefectivoHisto.size());
        Collection<Txmultifuncionalefectivo> resp = new ArrayList<>(txmultifuncionalefectivoHisto.size());

        for (TxmultifuncionalefectivoHisto item : txmultifuncionalefectivoHisto) {
            Txmultifuncionalefectivo txmultifuncionalefectivo = new Txmultifuncionalefectivo();

            TxmultifuncionalefectivoPK txmultifuncionalefectivoPK = new TxmultifuncionalefectivoPK();
            txmultifuncionalefectivoPK.setCodigotx(item.getTxmultifuncionalefectivoHistoPK().getCodigotx());
            txmultifuncionalefectivoPK.setCodigocajero(item.getTxmultifuncionalefectivoHistoPK().getCodigocajero());
            txmultifuncionalefectivoPK.setFechacajero(item.getTxmultifuncionalefectivoHistoPK().getFechacajero());

            txmultifuncionalefectivo.setTxmultifuncionalefectivoPK(txmultifuncionalefectivoPK);
            txmultifuncionalefectivo.setEstado(item.getEstado());
            txmultifuncionalefectivo.setNumerocorte(item.getNumerocorte());
            txmultifuncionalefectivo.setTransaccionconsecutivo(item.getTransaccionconsecutivo());
            txmultifuncionalefectivo.setFechacierre(item.getFechacierre());
            txmultifuncionalefectivo.setTipocuenta(item.getTipocuenta());
            txmultifuncionalefectivo.setNumerocuentaconsignar(item.getNumerocuentaconsignar());
            txmultifuncionalefectivo.setNumerocuentahomologa(item.getNumerocuentahomologa());
            txmultifuncionalefectivo.setValorconsignacion(item.getValorconsignacion());
            txmultifuncionalefectivo.setNobilletesnd(item.getNobilletesnd());
            txmultifuncionalefectivo.setNobilletesdenominacionf(item.getNobilletesdenominacionf());
            txmultifuncionalefectivo.setNobilletesdenominacione(item.getNobilletesdenominacione());
            txmultifuncionalefectivo.setNobilletesdenominaciond(item.getNobilletesdenominaciond());
            txmultifuncionalefectivo.setNobilletesdenominacionc(item.getNobilletesdenominacionc());
            txmultifuncionalefectivo.setNobilletesdenominacionb(item.getNobilletesdenominacionb());
            txmultifuncionalefectivo.setNobilletesdenominaciona(item.getNobilletesdenominaciona());
            txmultifuncionalefectivo.setTotalbilletesconsignados(item.getTotalbilletesconsignados());
            txmultifuncionalefectivo.setSecuencia(item.getSecuencia());
            txmultifuncionalefectivo.setVersion(item.getVersion());

            resp.add(txmultifuncionalefectivo);

        }

        this.loggerApp.log(Level.INFO, "TxMultifuncionalEfeDTO - txMultiEfeHistoATxMultiEfe resp: " + resp.size());

        return resp;
    }

    public Txmultifuncionalefectivo txMultiEfeHistoATxMultiEfe(TxmultifuncionalefectivoHisto item) throws Exception {

        Txmultifuncionalefectivo txmultifuncionalefectivo = new Txmultifuncionalefectivo();

        TxmultifuncionalefectivoPK txmultifuncionalefectivoPK = new TxmultifuncionalefectivoPK();
        txmultifuncionalefectivoPK.setCodigotx(item.getTxmultifuncionalefectivoHistoPK().getCodigotx());
        txmultifuncionalefectivoPK.setCodigocajero(item.getTxmultifuncionalefectivoHistoPK().getCodigocajero());
        txmultifuncionalefectivoPK.setFechacajero(item.getTxmultifuncionalefectivoHistoPK().getFechacajero());

        txmultifuncionalefectivo.setTxmultifuncionalefectivoPK(txmultifuncionalefectivoPK);
        txmultifuncionalefectivo.setEstado(item.getEstado());
        txmultifuncionalefectivo.setNumerocorte(item.getNumerocorte());
        txmultifuncionalefectivo.setTransaccionconsecutivo(item.getTransaccionconsecutivo());
        txmultifuncionalefectivo.setFechacierre(item.getFechacierre());
        txmultifuncionalefectivo.setTipocuenta(item.getTipocuenta());
        txmultifuncionalefectivo.setNumerocuentaconsignar(item.getNumerocuentaconsignar());
        txmultifuncionalefectivo.setNumerocuentahomologa(item.getNumerocuentahomologa());
        txmultifuncionalefectivo.setValorconsignacion(item.getValorconsignacion());
        txmultifuncionalefectivo.setNobilletesnd(item.getNobilletesnd());
        txmultifuncionalefectivo.setNobilletesdenominacionf(item.getNobilletesdenominacionf());
        txmultifuncionalefectivo.setNobilletesdenominacione(item.getNobilletesdenominacione());
        txmultifuncionalefectivo.setNobilletesdenominaciond(item.getNobilletesdenominaciond());
        txmultifuncionalefectivo.setNobilletesdenominacionc(item.getNobilletesdenominacionc());
        txmultifuncionalefectivo.setNobilletesdenominacionb(item.getNobilletesdenominacionb());
        txmultifuncionalefectivo.setNobilletesdenominaciona(item.getNobilletesdenominaciona());
        txmultifuncionalefectivo.setTotalbilletesconsignados(item.getTotalbilletesconsignados());
        txmultifuncionalefectivo.setSecuencia(item.getSecuencia());
        txmultifuncionalefectivo.setVersion(item.getVersion());

        return txmultifuncionalefectivo;
    }

}
