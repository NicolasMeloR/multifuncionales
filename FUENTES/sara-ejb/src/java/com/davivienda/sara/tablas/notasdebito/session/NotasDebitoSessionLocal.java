package com.davivienda.sara.tablas.notasdebito.session;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Notasdebito;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;

/**
 * DiarioElectronicoSessionLocal - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
@Local
public interface NotasDebitoSessionLocal extends AdministracionTablasInterface<Notasdebito> {

    /**
     * Retorna los NotasDebito de la fecha dada
     *
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    public Collection<Notasdebito> getNotasDebito(Date fechaInicial, Date fechaFinal, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion;

    public Notasdebito getNotasDebitoXLlave(Integer codigoCajero, Date fechaProceso, Date fechaHisto) throws EntityServicioExcepcion;

    public Notasdebito getNotasDebitoXCuentaValor(Integer codigoCajero, Date fechaProceso, String numeroCuenta, Long valor, Date fechaHisto) throws EntityServicioExcepcion;

    public String findByPrimayKey(Integer codigoCajero, Date fechaSistema, String numeroCuenta) throws EntityServicioExcepcion;

}
