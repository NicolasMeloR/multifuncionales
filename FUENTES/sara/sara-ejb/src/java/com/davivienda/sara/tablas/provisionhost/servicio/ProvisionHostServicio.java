// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.provisionhost.servicio;

import javax.persistence.Query;
import java.util.Collection;
import java.util.Calendar;
import javax.persistence.TransactionRequiredException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.davivienda.sara.entitys.Cajero;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.ProvisionHost;
import com.davivienda.sara.base.BaseEntityServicio;

public class ProvisionHostServicio extends BaseEntityServicio<ProvisionHost> implements AdministracionTablasInterface<ProvisionHost>
{
    public ProvisionHostServicio(final EntityManager em) {
        super(em, ProvisionHost.class);
    }
    
    public void grabarProvisionHost(final ProvisionHost provisionHost) throws EntityServicioExcepcion {
        try {
            if (provisionHost.getCajero().getActivo() == 1) {
                provisionHost.setCajero((Cajero)this.em.merge((Object)provisionHost.getCajero()));
                this.em.persist((Object)provisionHost);
            }
        }
        catch (IllegalStateException ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        catch (TransactionRequiredException ex3) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "Se requiere un entorno de transacci\u00f3n ", (Throwable)ex3);
            throw new EntityServicioExcepcion((Throwable)ex3);
        }
    }
    
    public Collection<ProvisionHost> getProvisionHostRangoFecha(final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<ProvisionHost> regs = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("ProvisionHost.RangoFecha");
            query.setParameter("fechaInicial", (Object)fechaInicial.getTime());
            query.setParameter("fechaFinal", (Object)fechaFinal.getTime());
            query.setHint("javax.persistence.cache.storeMode", (Object)"REFRESH");
            regs = (Collection<ProvisionHost>)query.getResultList();
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
