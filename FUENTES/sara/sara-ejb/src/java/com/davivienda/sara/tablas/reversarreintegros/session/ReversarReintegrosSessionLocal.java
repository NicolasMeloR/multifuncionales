package com.davivienda.sara.tablas.reversarreintegros.session;

import com.davivienda.sara.tablas.notasdebito.session.*;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosReversados;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

@Local
public interface ReversarReintegrosSessionLocal extends AdministracionTablasInterface<ReintegrosReversados> {

    public Reintegros findByPrimayKey(Integer codigoCajero, Date fechaSistema, Integer talon, Date fechaHisto) throws EntityServicioExcepcion;

}
