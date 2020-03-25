package com.davivienda.sara.tablas.resumencuadrecifras.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ResumenCuadreCifras;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Calendar;
import com.davivienda.utilidades.conversion.Fecha;

/**
 * DiarioElectronicoServicio - 21/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class ResumenCuadreCifrasServicio extends BaseEntityServicio<ResumenCuadreCifras> implements AdministracionTablasInterface<ResumenCuadreCifras> {

    
    public ResumenCuadreCifrasServicio(EntityManager em) {
        super(em, ResumenCuadreCifras.class);
    }


    /**
     * Retorna todos los registros de ResumenCuadreDiferencia que cumplan con los parámetros dados
     * @param codigoCajero si es null se retornan todos
     * @param fechaInicial si es null se toma la fecha del dia
     * @param fechaFinal   si es null se toma la fecha del dia
     * @return colección de ResumenCuadreCifras
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion 
     */
    @SuppressWarnings(value = "unchecked")
    public Collection<ResumenCuadreCifras> getResumenCuadreDiferencia(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<ResumenCuadreCifras> regs = null;
        Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
        Calendar fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getCalendarHoy();
        Calendar fFinal = (fechaFinal != null) ? fechaFinal : Fecha.getCalendarHoy();
        Query query = null;
        try {
            if (cCajero.equals(9999)) {
                query = em.createNamedQuery("ResumenCuadreCifras.RangoFecha");
            } else {
                query = em.createNamedQuery("ResumenCuadreCifras.CajeroRangoFecha");
                query.setParameter("codigoCajero", codigoCajero);
            }
            query.setParameter("fechaInicial", fInicial.getTime()).setParameter("fechaFinal", fFinal.getTime());
            regs = query.getResultList();
        } catch (IllegalStateException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
        } 
        return regs;
    }
    

   
    

}
