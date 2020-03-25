package com.davivienda.sara.base;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import javax.persistence.EntityExistsException;

/**
 * BaseAdministracionTablas - 7/07/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @param <E> 
 * @author jjvargas
 * Davivienda 2008 
 */
public class BaseAdministracionTablas<E> implements AdministracionTablasInterface<E> {

    protected AdministracionTablasInterface servicio = null;

    public BaseAdministracionTablas(AdministracionTablasInterface servicio) {
        this.servicio = servicio;
    }

    public BaseAdministracionTablas() {
    }

    @SuppressWarnings("unchecked")
    public Collection<E> getTodos() {
        return servicio.getTodos();
    }

    @SuppressWarnings("unchecked")
    public void adicionar(E entity) throws EntityExistsException, EntityServicioExcepcion {
        servicio.adicionar(entity);
    }

    @SuppressWarnings({"unchecked", "unchecked"})
    public E actualizar(E entity) throws EntityServicioExcepcion {
        return (E) servicio.actualizar(entity);
    }

    @SuppressWarnings("unchecked")
    public void borrar(E entity) throws EntityServicioExcepcion {
        servicio.borrar(entity);
    }

    public void actualizarPersistencia() throws EntityServicioExcepcion {
        servicio.actualizarPersistencia();
    }

    //CREADO ALVARO 18 ENERO
    public void borrarPorLlave(Object llave) throws EntityServicioExcepcion {
        servicio.borrarPorLlave(llave);
    }

    @SuppressWarnings("unchecked")
    public E buscar(Class objClase, Object llave) throws EntityServicioExcepcion {
        return (E) servicio.buscar(objClase, llave);
    }

    @SuppressWarnings("unchecked")
    public E buscar(Object llave) throws EntityServicioExcepcion {
        return (E) servicio.buscar(llave);
    }

    @SuppressWarnings("unchecked")
    public Collection<E> consultarPorQuery(Integer pagina, Integer regsPorPagina, String nombreQuery) throws EntityServicioExcepcion {
        return (Collection<E>) servicio.consultarPorQuery(pagina, regsPorPagina, nombreQuery);
    }

    /**
     * Obtiene <code>regsPorPagina</code>registros de ConfAccesoAplicacion  desde el registro <code>pagina</code>
     * @param pagina
     * @param regsPorPagina
     * @return
     * @throws java.lang.Exception
     */

    @SuppressWarnings("unchecked")
    public Collection<E> getTodos(Integer pagina, Integer regsPorPagina) throws Exception {
        return (Collection<E>) servicio.getTodos(pagina, regsPorPagina);
    }
}
