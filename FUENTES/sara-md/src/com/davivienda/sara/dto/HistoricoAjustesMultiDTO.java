/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Historicoajustesmulti;
import com.davivienda.sara.entitys.HistoricoajustesmultiHisto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jediazs
 */
public class HistoricoAjustesMultiDTO {

    public Logger loggerApp;

    public HistoricoAjustesMultiDTO(Logger logger) {
        this.loggerApp = logger;
    }

    public Collection<Historicoajustesmulti> historicoAjustesMultiHistoAHistoricoAjusteMulti(Collection<HistoricoajustesmultiHisto> historicoAjustesHistos) {

        this.loggerApp.log(Level.INFO, "HistoricoAjustesMultiDTO - historicoAjustesMultiHistoAHistoricoAjusteMulti historicoAjustesHistos: " + historicoAjustesHistos.size());
        Collection<Historicoajustesmulti> resp = new ArrayList<>(historicoAjustesHistos.size());

        for (HistoricoajustesmultiHisto item : historicoAjustesHistos) {
            Historicoajustesmulti historicoAjustes = new Historicoajustesmulti();

            historicoAjustes.setIdhistoricoajustes(item.getIdhistoricoajustes());
            historicoAjustes.setUsuario(item.getUsuario());
            historicoAjustes.setCodigocajero(item.getCodigocajero());
            historicoAjustes.setTipoajuste(item.getTipoajuste());
            historicoAjustes.setFecha(item.getFecha());
            historicoAjustes.setValor(item.getValor());
            historicoAjustes.setTalon(item.getTalon());
            historicoAjustes.setError(item.getError());
            historicoAjustes.setDescripcionerror(item.getDescripcionerror());
            historicoAjustes.setVersion(item.getVersion());
            historicoAjustes.setCodigooficinamulti(item.getCodigooficinamulti());

            resp.add(historicoAjustes);
        }

        this.loggerApp.log(Level.INFO, "HistoricoajustesmultiDTO - historicoAjustesHistoAHistoricoajustesmulti resp: " + resp.size());

        return resp;
    }

    public Historicoajustesmulti historicoAjustesMultiHistoAHistoricoAjusteMulti(HistoricoajustesmultiHisto item) throws Exception {

        Historicoajustesmulti historicoAjustes = new Historicoajustesmulti();

        historicoAjustes.setIdhistoricoajustes(item.getIdhistoricoajustes());
        historicoAjustes.setUsuario(item.getUsuario());
        historicoAjustes.setCodigocajero(item.getCodigocajero());
        historicoAjustes.setTipoajuste(item.getTipoajuste());
        historicoAjustes.setFecha(item.getFecha());
        historicoAjustes.setValor(item.getValor());
        historicoAjustes.setTalon(item.getTalon());
        historicoAjustes.setError(item.getError());
        historicoAjustes.setDescripcionerror(item.getDescripcionerror());
        historicoAjustes.setVersion(item.getVersion());
        historicoAjustes.setCodigooficinamulti(item.getCodigooficinamulti());
        
        return historicoAjustes;
    }

}
