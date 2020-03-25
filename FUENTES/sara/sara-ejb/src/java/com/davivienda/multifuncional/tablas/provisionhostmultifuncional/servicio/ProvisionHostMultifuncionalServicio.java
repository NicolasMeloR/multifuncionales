// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.provisionhostmultifuncional.servicio;

import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Calendar;
import com.davivienda.sara.entitys.ProvisionHost;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Provisionhostmulti;
import com.davivienda.sara.base.BaseEntityServicio;

public class ProvisionHostMultifuncionalServicio extends BaseEntityServicio<Provisionhostmulti> implements AdministracionTablasInterface<Provisionhostmulti>
{
    public ProvisionHostMultifuncionalServicio(final EntityManager em) {
        super(em, ProvisionHost.class);
    }
    
    public Collection<Provisionhostmulti> getProvisionHostRangoFecha(final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<Provisionhostmulti> regs = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("Provisionhostmulti.RangoFecha");
            query.setParameter("fechaInicial", (Object)fechaInicial.getTime());
            query.setParameter("fechaFinal", (Object)Fecha.getFechaFinDia(fechaInicial).getTime());
            regs = (Collection<Provisionhostmulti>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            Logger.getLogger("globalApp").log(Level.WARNING, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return regs;
    }
}
