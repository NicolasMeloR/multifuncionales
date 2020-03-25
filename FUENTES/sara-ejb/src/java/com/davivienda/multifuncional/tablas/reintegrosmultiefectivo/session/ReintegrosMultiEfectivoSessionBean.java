/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session;

import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.servicio.ReintegrosMultiEfectivoServicio;
import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.tablas.reintegros.servicio.ReintegrosServicio;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author P-CCHAPA
 */
@Stateless
public class ReintegrosMultiEfectivoSessionBean extends BaseAdministracionTablas<Reintegrosmultiefectivo> implements ReintegrosMultiEfectivoSessionLocal {

    @PersistenceContext
    private EntityManager em;
    ReintegrosMultiEfectivoServicio reintegrosServicio;

    @PostConstruct
    public void postConstructor() {
        reintegrosServicio = new ReintegrosMultiEfectivoServicio(em);
        super.servicio = reintegrosServicio;

    }
    
    @SuppressWarnings("unchecked")

    public Collection<Reintegrosmultiefectivo> getReintegros(Date fechaInicial, Date fechaFinal, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion {
       return reintegrosServicio.getReintegros(fechaInicial, fechaFinal, codigCajero, fechaHisto);
       
    }
        
    public Reintegrosmultiefectivo getReintegroXLlave(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Date fechaHisto) throws EntityServicioExcepcion {
        return reintegrosServicio.getReintegroXLlave( codigoCajero,  fechaProceso,  numeroTransaccion, fechaHisto);

    }

    public Reintegrosmultiefectivo getReintegroXCuentaValor(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, String numeroCuenta, Long valor,Date fechaHisto) throws EntityServicioExcepcion {
        return reintegrosServicio.getReintegroXCuentaValor( codigoCajero,  fechaProceso,  numeroTransaccion,numeroCuenta,valor, fechaHisto);
    }
     
}
