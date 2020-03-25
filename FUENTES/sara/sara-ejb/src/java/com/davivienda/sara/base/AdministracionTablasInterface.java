// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.base;

import java.util.Collection;
import javax.persistence.EntityExistsException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

public interface AdministracionTablasInterface<E>
{
    E actualizar(final E p0) throws EntityServicioExcepcion;
    
    void actualizarPersistencia() throws EntityServicioExcepcion;
    
    void adicionar(final E p0) throws EntityExistsException, EntityServicioExcepcion;
    
    void borrar(final E p0) throws EntityServicioExcepcion;
    
    void borrarPorLlave(final Object p0) throws EntityServicioExcepcion;
    
    E buscar(final Class p0, final Object p1) throws EntityServicioExcepcion;
    
    E buscar(final Object p0) throws EntityServicioExcepcion;
    
    Collection<E> consultarPorQuery(final Integer p0, final Integer p1, final String p2) throws EntityServicioExcepcion;
    
    Collection<E> getTodos();
    
    Collection<E> getTodos(final Integer p0, final Integer p1) throws Exception;
}
