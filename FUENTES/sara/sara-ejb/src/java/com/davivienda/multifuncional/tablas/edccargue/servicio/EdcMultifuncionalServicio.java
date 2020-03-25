// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.edccargue.servicio;

import javax.persistence.Query;
import java.util.logging.Level;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Date;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Edcarguemultifuncional;
import com.davivienda.sara.base.BaseEntityServicio;

public class EdcMultifuncionalServicio extends BaseEntityServicio<Edcarguemultifuncional> implements AdministracionTablasInterface<Edcarguemultifuncional>
{
    public EdcMultifuncionalServicio(final EntityManager em) {
        super(em, Edcarguemultifuncional.class);
    }
    
    @Override
    public Edcarguemultifuncional actualizar(final Edcarguemultifuncional objeto) throws EntityServicioExcepcion {
        Edcarguemultifuncional objetoActual = super.buscar(objeto.getIdedccargue());
        final String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
        if (accion.equals("NUEVO")) {
            super.adicionar(objeto);
            objetoActual = super.buscar(objeto.getIdedccargue());
        }
        else {
            objetoActual = objetoActual.actualizarEntity(objeto);
            super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    public Collection<Edcarguemultifuncional> getEDCCargueXFecha(Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        Collection<Edcarguemultifuncional> items = null;
        if (fechaInicial == null) {
            fechaInicial = Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser mayor a la fecha Inicial");
        }
        try {
            Query query = null;
            query = this.em.createNamedQuery("Edcarguemultifuncional.RangoFecha");
            query.setParameter("fechaInicial", (Object)fechaInicial);
            query.setParameter("fechaFinal", (Object)fechaFinal);
            items = (Collection<Edcarguemultifuncional>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            EdcMultifuncionalServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            EdcMultifuncionalServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
}
