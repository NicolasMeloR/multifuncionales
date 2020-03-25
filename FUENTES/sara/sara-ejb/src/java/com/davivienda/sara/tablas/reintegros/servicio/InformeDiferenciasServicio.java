// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.reintegros.servicio;

import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Calendar;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.InformeDiferencias;
import com.davivienda.sara.base.BaseEntityServicio;

public class InformeDiferenciasServicio extends BaseEntityServicio<InformeDiferencias>
{
    public InformeDiferenciasServicio(final EntityManager em) {
        super(em, InformeDiferencias.class);
    }
    
    public InformeDiferencias actualizar(final InformeDiferencias objetoModificado, final boolean esDesdeJob) throws EntityServicioExcepcion {
        InformeDiferencias objetoActual = super.buscar(objetoModificado.getInformeDiferenciasPK());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getInformeDiferenciasPK());
        }
        else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    public Collection<InformeDiferencias> buscarDiferenciasXFecha(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<InformeDiferencias> regs = null;
        try {
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            final Calendar fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getCalendarHoy();
            final Calendar fFinal = (fechaFinal != null) ? fechaFinal : Fecha.getCalendarHoy();
            Query query = null;
            if (cCajero.equals(9999)) {
                query = this.em.createNamedQuery("InformeDiferencias.RangoFecha");
            }
            else {
                query = this.em.createNamedQuery("InformeDiferencias.CajeroRangoFecha");
                query.setParameter("codigoCajero", (Object)cCajero);
            }
            query.setParameter("fechaInicial", (Object)fInicial.getTime()).setParameter("fechaFinal", (Object)fFinal.getTime());
            query.setHint("javax.persistence.cache.storeMode", (Object)"REFRESH");
            regs = (Collection<InformeDiferencias>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            Logger.getLogger("globalApp").log(Level.WARNING, "El par\u00e1metro no es v\u00e1lido ", ex2);
        }
        return regs;
    }
}
