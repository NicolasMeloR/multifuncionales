// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.hostatm.servicio;

import javax.persistence.Query;
import java.util.List;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.HostAtm;
import com.davivienda.sara.base.BaseEntityServicio;

public class HostAtmServicio extends BaseEntityServicio<HostAtm> implements AdministracionTablasInterface<HostAtm>
{
    public HostAtmServicio(final EntityManager em) {
        super(em, HostAtm.class);
    }
    
    public HostAtm getReintegrosHost(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal, final Integer talon) throws EntityServicioExcepcion {
        List result = new ArrayList();
        HostAtm reg = null;
        try {
            final Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            final Date fFin = fechaFinal;
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery("HostAtm.CajeroTxFecha");
                query.setParameter("codigocajero", (Object)cCajero);
                query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
                query.setParameter("talon", (Object)talon);
                result = query.getResultList();
                result.size();
                if (result != null && result.size() > 0) {
                    reg = (HostAtm) result.get(0);
                }
            }
        }
        catch (IllegalStateException ex) {
            HostAtmServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            HostAtmServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return reg;
    }
    
    public List getTalonesHost(final Integer codigoCajero, final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        List result = new ArrayList();
        final HostAtm reg = null;
        try {
            final Date fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getDateHoy();
            final Date fFin = fechaFinal;
            final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = this.em.createNamedQuery("HostAtm.TalonXFecha");
                query.setParameter("codigocajero", (Object)cCajero);
                query.setParameter("fechaInicial", (Object)fInicial).setParameter("fechaFinal", (Object)fFin);
                result = query.getResultList();
            }
        }
        catch (IllegalStateException ex) {
            HostAtmServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            HostAtmServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return result;
    }
}
