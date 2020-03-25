/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.dto;

import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.entitys.NotasdebitomultifuncionalHisto;
import com.davivienda.sara.entitys.NotasdebitomultifuncionalPK;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jediazs
 */
public class NotasDebitoMultiDTO {

    public Logger loggerApp;

    public NotasDebitoMultiDTO(Logger logger) {
        this.loggerApp = logger;
    }

    public Collection<Notasdebitomultifuncional> notasdebitoMultiHistoANotasdebitoMulti(Collection<NotasdebitomultifuncionalHisto> notasdebitomultifuncionalHisto) {

        this.loggerApp.log(Level.INFO, "NotasDebitoMultiDTO - notasdebitoMultiHistoANotasdebitoMulti notasdebitomultifuncionalHisto: " + notasdebitomultifuncionalHisto.size());
        Collection<Notasdebitomultifuncional> resp = new ArrayList<>(notasdebitomultifuncionalHisto.size());

        for (NotasdebitomultifuncionalHisto item : notasdebitomultifuncionalHisto) {
            Notasdebitomultifuncional notaDebitoMulti = new Notasdebitomultifuncional();

            NotasdebitomultifuncionalPK notasdebitoPK = new NotasdebitomultifuncionalPK();
            notasdebitoPK.setCodigocajero(item.getNotasdebitomultifuncionalHistoPK().getCodigocajero());
            notasdebitoPK.setFecha(item.getNotasdebitomultifuncionalHistoPK().getFecha());

            notaDebitoMulti.setNotasdebitomultifuncionalPK(notasdebitoPK);
            notaDebitoMulti.setTalon(item.getTalon());
            notaDebitoMulti.setNumerocuenta(item.getNumerocuenta());
            notaDebitoMulti.setCodigooficina(item.getCodigooficina());
            notaDebitoMulti.setValor(item.getValor());
            notaDebitoMulti.setValorajustado(item.getValorajustado());
            notaDebitoMulti.setEstado(item.getEstado());
            notaDebitoMulti.setUsuariocrea(item.getUsuariocrea());
            notaDebitoMulti.setUsuarioautoriza(item.getUsuarioautoriza());
            notaDebitoMulti.setFechaaplica(item.getFechaaplica());
            notaDebitoMulti.setTipocuenta(item.getTipocuenta());
            notaDebitoMulti.setError(item.getError());
            notaDebitoMulti.setVersion(item.getVersion());

            resp.add(notaDebitoMulti);
        }

        this.loggerApp.log(Level.INFO, "NotasDebitoMultiDTO - notasdebitoMultiHistoANotasdebitoMulti resp: " + resp.size());

        return resp;
    }

    public Notasdebitomultifuncional notasdebitoMultiHistoANotasdebitoMulti(NotasdebitomultifuncionalHisto item) throws Exception {

        Notasdebitomultifuncional notaDebitoMulti = new Notasdebitomultifuncional();

        NotasdebitomultifuncionalPK notasdebitoPK = new NotasdebitomultifuncionalPK();
        notasdebitoPK.setCodigocajero(item.getNotasdebitomultifuncionalHistoPK().getCodigocajero());
        notasdebitoPK.setFecha(item.getNotasdebitomultifuncionalHistoPK().getFecha());

        notaDebitoMulti.setNotasdebitomultifuncionalPK(notasdebitoPK);
        notaDebitoMulti.setTalon(item.getTalon());
        notaDebitoMulti.setNumerocuenta(item.getNumerocuenta());
        notaDebitoMulti.setCodigooficina(item.getCodigooficina());
        notaDebitoMulti.setValor(item.getValor());
        notaDebitoMulti.setValorajustado(item.getValorajustado());
        notaDebitoMulti.setEstado(item.getEstado());
        notaDebitoMulti.setUsuariocrea(item.getUsuariocrea());
        notaDebitoMulti.setUsuarioautoriza(item.getUsuarioautoriza());
        notaDebitoMulti.setFechaaplica(item.getFechaaplica());
        notaDebitoMulti.setTipocuenta(item.getTipocuenta());
        notaDebitoMulti.setError(item.getError());
        notaDebitoMulti.setVersion(item.getVersion());
        
        return notaDebitoMulti;
    }

}
