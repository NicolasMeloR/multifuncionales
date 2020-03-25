// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.binentidad.servicio;

import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.BinEntidad;
import com.davivienda.sara.base.BaseEntityServicio;

public class BinEntidadServicio extends BaseEntityServicio<BinEntidad>
{
    public BinEntidadServicio(final EntityManager em) {
        super(em, BinEntidad.class);
    }
    
    public BinEntidad getEntidadXBin(final String bin) throws EntityServicioExcepcion {
        BinEntidad be = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("BinEntidad.RegistroUnico");
            query.setParameter("bin", (Object)bin);
            be = (BinEntidad)query.getSingleResult();
        }
        catch (IllegalStateException ex) {
            BinEntidadServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            BinEntidadServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return be;
    }
}
