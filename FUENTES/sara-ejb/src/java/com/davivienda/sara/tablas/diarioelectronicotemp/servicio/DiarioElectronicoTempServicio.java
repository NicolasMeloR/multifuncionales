package com.davivienda.sara.tablas.diarioelectronicotemp.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * DiarioElectronicoServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class DiarioElectronicoTempServicio extends BaseEntityServicio<DiarioelectronicoTemp> implements AdministracionTablasInterface<DiarioelectronicoTemp> {

    
    public DiarioElectronicoTempServicio(EntityManager em) {
        super(em, DiarioelectronicoTemp.class);
    }
    

    /**
     * Retorna el DiarioElectronico del cajero y fecha dado
     * @param codigoCajero
     * @param fechaInicial
     * @return
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     */
    @SuppressWarnings("unchecked")
    public Collection<DiarioelectronicoTemp> getDiarioElectronicoTemp(Integer codigoCajero, Date fechaInicial) throws EntityServicioExcepcion {
        Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
        Date fInicialCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaInicioDia(com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial)).getTime();
        Date fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
        Collection<DiarioelectronicoTemp> items = null;
        try {
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("DiarioelectronicoTemp.CajeroRangoFecha");
                                         
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fInicialCiclo);
                query.setParameter("fechaFinal", fFinalCiclo);
                items = query.getResultList();
            } else {
                throw new EntityServicioExcepcion("Debe seleccionar un solo cajero");
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    }
    

}
