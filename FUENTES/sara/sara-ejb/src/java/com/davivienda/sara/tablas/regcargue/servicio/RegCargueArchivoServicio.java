// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.regcargue.servicio;

import javax.persistence.Query;
import java.util.logging.Level;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Date;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.base.BaseEntityServicio;

public class RegCargueArchivoServicio extends BaseEntityServicio<Regcarguearchivo>
{
    public RegCargueArchivoServicio(final EntityManager em) {
        super(em, Regcarguearchivo.class);
    }
    
    @Override
    public Regcarguearchivo actualizar(final Regcarguearchivo objetoModificado) throws EntityServicioExcepcion {
        Regcarguearchivo objetoActual = super.buscar(objetoModificado.getIdregcarguearchivo());
        if (objetoActual == null) {
            super.adicionar(objetoModificado);
            objetoActual = super.buscar(objetoModificado.getIdregcarguearchivo());
        }
        else {
            objetoActual.actualizarEntity(objetoModificado);
            objetoActual = super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    public Collection<Regcarguearchivo> getRegCargueXFecha(Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        Collection<Regcarguearchivo> items = null;
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
            query = this.em.createNamedQuery("Regcarguearchivo.RangoFecha");
            query.setParameter("fechaInicial", (Object)fechaInicial);
            query.setParameter("fechaFinal", (Object)fechaFinal);
            items = (Collection<Regcarguearchivo>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            RegCargueArchivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            RegCargueArchivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public Regcarguearchivo buscarPorArchivoFecha(final String archivoTarea, final String fecha, final boolean IndAuto) throws EntityServicioExcepcion {
        Regcarguearchivo item = null;
        String strNamedQuery = "";
        String strParamFecha = "";
        try {
            Query query = null;
            Short stIndAuto;
            if (IndAuto) {
                strNamedQuery = "Regcarguearchivo.ArchivoFechaAuto";
                stIndAuto = 1;
                strParamFecha = "fechaautomatica";
            }
            else {
                strNamedQuery = "Regcarguearchivo.ArchivoFechaManual";
                stIndAuto = 0;
                strParamFecha = "fechamanual";
            }
            query = this.em.createNamedQuery(strNamedQuery);
            query.setParameter("archivotarea", (Object)archivoTarea);
            query.setParameter("indautomatico", (Object)stIndAuto);
            query.setParameter(strParamFecha, (Object)fecha);
            if (archivoTarea.equals("")) {
                item = null;
            }
            else if (!query.getResultList().isEmpty()) {
                item = (Regcarguearchivo) query.getResultList().get(0);
            }
        }
        catch (IllegalStateException ex) {
            RegCargueArchivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            RegCargueArchivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return item;
    }
}
