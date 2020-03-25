package com.davivienda.sara.tablas.reintegros.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.entitys.InformeDiferencias;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Calendar;

/**
 * OccaServicio - 11/04/2017 Descripción : Versión : 1.0
 *
 * @author jediazs Davivienda 2017
 */
public class InformeDiferenciasServicio extends BaseEntityServicio<InformeDiferencias> {

    public InformeDiferenciasServicio(EntityManager em) {
        super(em, InformeDiferencias.class);
    }

    
    public InformeDiferencias actualizar(InformeDiferencias objetoModificado, boolean esDesdeJob)  throws EntityServicioExcepcion{ 
        InformeDiferencias objetoActual = super.buscar(objetoModificado.getInformeDiferenciasPK());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getInformeDiferenciasPK());
        } else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }        
        return objetoActual;
    }
    
    @SuppressWarnings(value = "unchecked")
    public Collection<InformeDiferencias> buscarDiferenciasXFecha(Integer codigoCajero, Calendar fechaInicial, Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<InformeDiferencias> regs = null;
        try {
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Calendar fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getCalendarHoy();
            Calendar fFinal = (fechaFinal != null) ? fechaFinal : Fecha.getCalendarHoy();
            Query query = null;
            if (cCajero.equals(9999)) {
                query = em.createNamedQuery("InformeDiferencias.RangoFecha");

            } else {
                query = em.createNamedQuery("InformeDiferencias.CajeroRangoFecha");
                query.setParameter("codigoCajero", cCajero);
            }

            query.setParameter("fechaInicial", fInicial.getTime()).setParameter("fechaFinal", fFinal.getTime());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            regs = query.getResultList();
        } catch (IllegalStateException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.WARNING, "El parámetro no es válido ", ex);
        }
        return regs;
    }

}
