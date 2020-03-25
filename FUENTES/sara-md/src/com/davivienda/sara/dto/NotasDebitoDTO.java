/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.entitys.NotasdebitoHisto;
import com.davivienda.sara.entitys.NotasdebitoPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jediazs
 */
public class NotasDebitoDTO {

    public Logger loggerApp;

    public NotasDebitoDTO(Logger logger) {
        this.loggerApp = logger;
    }

    public Collection<Notasdebito> notasdebitoHistoANotasdebito(Collection<NotasdebitoHisto> notasdebitoHisto) {

        this.loggerApp.log(Level.INFO, "NotasDebitoDTO - notasdebitoHistoANotasdebito notasdebitoHisto: " + notasdebitoHisto.size());
        Collection<Notasdebito> resp = new ArrayList<>(notasdebitoHisto.size());

        for (NotasdebitoHisto item : notasdebitoHisto) {
            Notasdebito notaDebito = new Notasdebito();

            NotasdebitoPK notasdebitoPK = new NotasdebitoPK();
            notasdebitoPK.setCodigocajero(item.getNotasdebitoHistoPK().getCodigocajero());
            notasdebitoPK.setFecha(item.getNotasdebitoHistoPK().getFecha());

            notaDebito.setNotasdebitoPK(notasdebitoPK);
            notaDebito.setNumerocuenta(item.getNumerocuenta());
            notaDebito.setCodigoocca(item.getCodigoocca());
            notaDebito.setTalon(item.getTalon());
            notaDebito.setValor(item.getValor());
            notaDebito.setValorajustado(item.getValorajustado());
            notaDebito.setTipocuenta(item.getTipocuenta());
            notaDebito.setEstado(item.getEstado());
            notaDebito.setUsuariocrea(item.getUsuariocrea());
            notaDebito.setUsuarioautoriza(item.getUsuarioautoriza());
            notaDebito.setFechaaplica(item.getFechaaplica());
            notaDebito.setError(item.getError());
            resp.add(notaDebito);
        }

        this.loggerApp.log(Level.INFO, "NotasdebitoDTO - notasdebitoHistoANotasdebito resp: " + resp.size());

        return resp;
    }

    public Notasdebito notasdebitoHistoANotasdebito(NotasdebitoHisto item) throws Exception {

        Notasdebito notaDebito = new Notasdebito();

        NotasdebitoPK notasdebitoPK = new NotasdebitoPK();
        notasdebitoPK.setCodigocajero(item.getNotasdebitoHistoPK().getCodigocajero());
        notasdebitoPK.setFecha(item.getNotasdebitoHistoPK().getFecha());

        notaDebito.setNotasdebitoPK(notasdebitoPK);
        notaDebito.setNumerocuenta(item.getNumerocuenta());
        notaDebito.setCodigoocca(item.getCodigoocca());
        notaDebito.setTalon(item.getTalon());
        notaDebito.setValor(item.getValor());
        notaDebito.setValorajustado(item.getValorajustado());
        notaDebito.setTipocuenta(item.getTipocuenta());
        notaDebito.setEstado(item.getEstado());
        notaDebito.setUsuariocrea(item.getUsuariocrea());
        notaDebito.setUsuarioautoriza(item.getUsuarioautoriza());
        notaDebito.setFechaaplica(item.getFechaaplica());
        notaDebito.setError(item.getError());
        return notaDebito;
    }

}
