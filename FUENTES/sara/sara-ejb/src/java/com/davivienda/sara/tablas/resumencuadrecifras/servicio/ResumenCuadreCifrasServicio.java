// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.resumencuadrecifras.servicio;

import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Calendar;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.ResumenCuadreCifras;
import com.davivienda.sara.base.BaseEntityServicio;

public class ResumenCuadreCifrasServicio extends BaseEntityServicio<ResumenCuadreCifras> implements AdministracionTablasInterface<ResumenCuadreCifras>
{
    public ResumenCuadreCifrasServicio(final EntityManager em) {
        super(em, ResumenCuadreCifras.class);
    }
    
    public Collection<ResumenCuadreCifras> getResumenCuadreDiferencia(final Integer codigoCajero, final Calendar fechaInicial, final Calendar fechaFinal) throws EntityServicioExcepcion {
        Collection<ResumenCuadreCifras> regs = null;
        final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
        final Calendar fInicial = (fechaInicial != null) ? fechaInicial : Fecha.getCalendarHoy();
        final Calendar fFinal = (fechaFinal != null) ? fechaFinal : Fecha.getCalendarHoy();
        Query query = null;
        try {
            if (cCajero.equals(9999)) {
                query = this.em.createNamedQuery("ResumenCuadreCifras.RangoFecha");
            }
            else {
                query = this.em.createNamedQuery("ResumenCuadreCifras.CajeroRangoFecha");
                query.setParameter("codigoCajero", (Object)codigoCajero);
            }
            query.setParameter("fechaInicial", (Object)fInicial.getTime()).setParameter("fechaFinal", (Object)fFinal.getTime());
            regs = (Collection<ResumenCuadreCifras>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
        }
        return regs;
    }
}
