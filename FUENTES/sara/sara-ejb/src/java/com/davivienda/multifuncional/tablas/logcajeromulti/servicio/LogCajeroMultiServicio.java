// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.logcajeromulti.servicio;

import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Logcajeromulti;
import com.davivienda.sara.base.BaseEntityServicio;

public class LogCajeroMultiServicio extends BaseEntityServicio<Logcajeromulti> implements AdministracionTablasInterface<Logcajeromulti>
{
    public LogCajeroMultiServicio(final EntityManager em) {
        super(em, Logcajeromulti.class);
    }
    
    public Collection<Logcajeromulti> getColeccionLogCajeroMulti(final Integer codigoCajero, final Date fechaInicial) throws EntityServicioExcepcion {
        Collection<Logcajeromulti> regs = null;
        String strQuery = "select object(obj) from Logcajeromulti obj         where  obj.logcajeromultiPK.fechacargue between :fechaInicial and :fechaFinal ";
        final String orderQuery = "        order by obj.logcajeromultiPK.secuencia";
        try {
            if (codigoCajero != 0) {
                strQuery += "          and obj.logcajeromultiPK.codigocajero =:codigoCajero";
            }
            strQuery += orderQuery;
            final Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            final Date fFin = Fecha.getFechaFinDia(fInicial);
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createQuery(strQuery);
                query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
                if (codigoCajero != 0) {
                    query.setParameter("codigoCajero", (Object)cCajero);
                }
                regs = (Collection<Logcajeromulti>)query.getResultList();
            }
        }
        catch (IllegalStateException ex) {
            LogCajeroMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            LogCajeroMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return regs;
    }
}
