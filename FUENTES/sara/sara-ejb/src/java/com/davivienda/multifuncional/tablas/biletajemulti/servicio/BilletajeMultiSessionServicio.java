// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.biletajemulti.servicio;

import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Calendar;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Billetajemulti;
import com.davivienda.sara.base.BaseEntityServicio;

public class BilletajeMultiSessionServicio extends BaseEntityServicio<Billetajemulti> implements AdministracionTablasInterface<Billetajemulti>
{
    public BilletajeMultiSessionServicio(final EntityManager em) {
        super(em, Billetajemulti.class);
    }
    
    public Collection<Billetajemulti> getBilletajeMultiRangoFecha(final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<Billetajemulti> regs = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("Billetajemulti.Fecha");
            query.setParameter("fechaInicial", (Object)fechaInicial.getTime());
            query.setParameter("fechaFinal", (Object)Fecha.getFechaFinDia(fechaInicial).getTime());
            regs = (Collection<Billetajemulti>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            Logger.getLogger("globalApp").log(Level.WARNING, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        System.out.println(("BilletajeMultiSessionServicio.getBilletajeMultiRangoFecha(): regs " + regs != null) ? regs.size() : 0);
        return regs;
    }
}
