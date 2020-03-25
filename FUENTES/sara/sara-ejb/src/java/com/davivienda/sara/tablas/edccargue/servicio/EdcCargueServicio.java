// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.edccargue.servicio;

import javax.persistence.TransactionRequiredException;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Date;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.Collection;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.base.BaseEntityServicio;

public class EdcCargueServicio extends BaseEntityServicio<EdcCargue> implements AdministracionTablasInterface<EdcCargue>
{
    public EdcCargueServicio(final EntityManager em) {
        super(em, EdcCargue.class);
    }
    
    @Override
    public EdcCargue actualizar(final EdcCargue objeto) throws EntityServicioExcepcion {
        EdcCargue objetoActual = super.buscar(objeto.getIdEdcCargue());
        final String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
        if (accion.equals("NUEVO")) {
            super.adicionar(objeto);
            objetoActual = super.buscar(objeto.getIdEdcCargue());
        }
        else {
            objetoActual = objetoActual.actualizarEntity(objeto);
            super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    public Collection<EdcCargue> getCicloSnError(final Integer ciclo) throws EntityServicioExcepcion {
        Collection<EdcCargue> items = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("EdcCargue.CicloSnError");
            query.setParameter("ciclo", (Object)ciclo);
            items = (Collection<EdcCargue>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            EdcCargueServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            EdcCargueServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public Collection<EdcCargue> getEDCCarguePorFecha(Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        Collection<EdcCargue> items = null;
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
            query = this.em.createNamedQuery("EdcCargue.RangoFecha").setHint("toplink.refresh", (Object)"true");
            query.setParameter("fechaInicial", (Object)fechaInicial);
            query.setParameter("fechaFinal", (Object)fechaFinal);
            items = (Collection<EdcCargue>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            EdcCargueServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            EdcCargueServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public EdcCargue buscarPorArchivo(final String nombreArchivo) throws EntityServicioExcepcion {
        EdcCargue item = null;
        try {
            Query query = null;
            query = this.em.createNamedQuery("EdcCargue.Archivo");
            query.setParameter("nombreArchivo", (Object)nombreArchivo);
            if (nombreArchivo.equals("")) {
                item = null;
            }
            else if (!query.getResultList().isEmpty()) {
                item = (EdcCargue) query.getResultList().get(0);
            }
        }
        catch (IllegalStateException ex) {
            EdcCargueServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            EdcCargueServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return item;
    }
    
    public int borrarCiclo(final Integer ciclo) throws EntityServicioExcepcion {
        int respuesta = 0;
        final String strQuery = "DELETE  FROM EdcCargue s    where s.ciclo = :ciclo ";
        try {
            Query query = null;
            query = this.em.createQuery(strQuery);
            query.setParameter("ciclo", (Object)ciclo);
            respuesta = query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            EdcCargueServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (TransactionRequiredException ex2) {
            EdcCargueServicio.configApp.loggerApp.log(Level.SEVERE, "no es una transaccion ", (Throwable)ex2);
            throw new EntityServicioExcepcion((Throwable)ex2);
        }
        return respuesta;
    }
}
