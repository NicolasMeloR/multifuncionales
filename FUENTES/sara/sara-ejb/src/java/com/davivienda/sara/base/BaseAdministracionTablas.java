// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.base;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.persistence.EntityExistsException;
import java.util.Collection;

public class BaseAdministracionTablas<E> implements AdministracionTablasInterface<E>
{
    protected AdministracionTablasInterface servicio;
    
    public BaseAdministracionTablas(final AdministracionTablasInterface servicio) {
        this.servicio = null;
        this.servicio = servicio;
    }
    
    public BaseAdministracionTablas() {
        this.servicio = null;
    }
    
    @Override
    public Collection<E> getTodos() {
        return this.servicio.getTodos();
    }
    
    @Override
    public void adicionar(final E entity) throws EntityExistsException, EntityServicioExcepcion {
        this.servicio.adicionar(entity);
    }
    
    @Override
    public E actualizar(final E entity) throws EntityServicioExcepcion {
        return (E) this.servicio.actualizar(entity);
    }
    
    @Override
    public void borrar(final E entity) throws EntityServicioExcepcion {
        this.servicio.borrar(entity);
    }
    
    @Override
    public void actualizarPersistencia() throws EntityServicioExcepcion {
        this.servicio.actualizarPersistencia();
    }
    
    @Override
    public void borrarPorLlave(final Object llave) throws EntityServicioExcepcion {
        this.servicio.borrarPorLlave(llave);
    }
    
    @Override
    public E buscar(final Class objClase, final Object llave) throws EntityServicioExcepcion {
        return (E) this.servicio.buscar(objClase, llave);
    }
    
    @Override
    public E buscar(final Object llave) throws EntityServicioExcepcion {
        return (E) this.servicio.buscar(llave);
    }
    
    @Override
    public Collection<E> consultarPorQuery(final Integer pagina, final Integer regsPorPagina, final String nombreQuery) throws EntityServicioExcepcion {
        return this.servicio.consultarPorQuery(pagina, regsPorPagina, nombreQuery);
    }
    
    @Override
    public Collection<E> getTodos(final Integer pagina, final Integer regsPorPagina) throws Exception {
        return this.servicio.getTodos(pagina, regsPorPagina);
    }
}
