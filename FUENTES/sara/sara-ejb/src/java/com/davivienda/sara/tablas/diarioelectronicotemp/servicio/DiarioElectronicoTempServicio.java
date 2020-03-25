// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.diarioelectronicotemp.servicio;

import javax.persistence.Query;
import java.util.logging.Level;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import com.davivienda.sara.base.BaseEntityServicio;

public class DiarioElectronicoTempServicio extends BaseEntityServicio<DiarioelectronicoTemp> implements AdministracionTablasInterface<DiarioelectronicoTemp>
{
    public DiarioElectronicoTempServicio(final EntityManager em) {
        super(em, DiarioelectronicoTemp.class);
    }
    
    public Collection<DiarioelectronicoTemp> getDiarioElectronicoTemp(final Integer codigoCajero, final Date fechaInicial) throws EntityServicioExcepcion {
        final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
        final Date fInicialCiclo = Fecha.getFechaInicioDia(Fecha.getCalendar(fechaInicial)).getTime();
        final Date fFinalCiclo = Fecha.getFechaFinDia(fechaInicial);
        Collection<DiarioelectronicoTemp> items = null;
        try {
            Query query = null;
            if (cCajero.equals(9999)) {
                throw new EntityServicioExcepcion("Debe seleccionar un solo cajero");
            }
            query = this.em.createNamedQuery("DiarioelectronicoTemp.CajeroRangoFecha");
            query.setParameter("codigoCajero", (Object)cCajero);
            query.setParameter("fechaInicial", (Object)fInicialCiclo);
            query.setParameter("fechaFinal", (Object)fFinalCiclo);
            items = (Collection<DiarioelectronicoTemp>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            DiarioElectronicoTempServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            DiarioElectronicoTempServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
}
