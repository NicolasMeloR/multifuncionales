/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.HistoricoAjustes;
import com.davivienda.sara.entitys.HistoricoAjustesHisto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jediazs
 */
public class HistoricoAjustesDTO {

    public Logger loggerApp;

    public HistoricoAjustesDTO(Logger logger) {
        this.loggerApp = logger;
    }

    public Collection<HistoricoAjustes> historicoAjustesHistoAHistoricoAjustes(Collection<HistoricoAjustesHisto> historicoAjustesHistos) {

        this.loggerApp.log(Level.INFO, "HistoricoAjustesDTO - historicoAjustesHistoAHistoricoAjustes historicoAjustesHistos: " + historicoAjustesHistos.size());
        Collection<HistoricoAjustes> resp = new ArrayList<>(historicoAjustesHistos.size());

        for (HistoricoAjustesHisto item : historicoAjustesHistos) {
            HistoricoAjustes historicoAjustes = new HistoricoAjustes();

            historicoAjustes.setIdHistoricoAjustes(item.getIdHistoricoAjustes());
            historicoAjustes.setUsuario(item.getUsuario());
            historicoAjustes.setCodigoCajero(item.getCodigoCajero());
            historicoAjustes.setCodigoOcca(item.getCodigoOcca());
            historicoAjustes.setTipoAjuste(item.getTipoAjuste());
            historicoAjustes.setFecha(item.getFecha());
            historicoAjustes.setValor(item.getValor());
            historicoAjustes.setTalon(item.getTalon());
            historicoAjustes.setError(item.getError());
            historicoAjustes.setDescripcionError(item.getDescripcionError());

            resp.add(historicoAjustes);
        }

        this.loggerApp.log(Level.INFO, "HistoricoAjustesDTO - historicoAjustesHistoAHistoricoAjustes resp: " + resp.size());

        return resp;
    }

    public HistoricoAjustes historicoAjustesHistoAHistoricoAjustes(HistoricoAjustesHisto item) throws Exception {

        HistoricoAjustes historicoAjustes = new HistoricoAjustes();

        historicoAjustes.setIdHistoricoAjustes(item.getIdHistoricoAjustes());
        historicoAjustes.setUsuario(item.getUsuario());
        historicoAjustes.setCodigoCajero(item.getCodigoCajero());
        historicoAjustes.setCodigoOcca(item.getCodigoOcca());
        historicoAjustes.setTipoAjuste(item.getTipoAjuste());
        historicoAjustes.setFecha(item.getFecha());
        historicoAjustes.setValor(item.getValor());
        historicoAjustes.setTalon(item.getTalon());
        historicoAjustes.setError(item.getError());
        historicoAjustes.setDescripcionError(item.getDescripcionError());
        return historicoAjustes;
    }

}
