package com.davivienda.sara.base;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import javax.persistence.EntityExistsException;

/**
 * AdministracionTablasInterface - 7/07/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @param <E> 
 * @author jjvargas
 * Davivienda 2008 
 */
public interface AdministracionTablasInterface<E> {
    
    /**
     * Adiciona el objeto pasado al contenedor de entitys para ser procesado en los procesos de persistencia transaccionales
     * @param objeto desligado al contenedor de entitys
     * @return referencia del objeto manejado por el contenedor
     * @throws EntityServicioExcepcion
     */
    E actualizar(E objeto) throws EntityServicioExcepcion;

    /**
     * Actualiza todo el contenedor en la base de datos
     *
     * @throws EntityServicioExcepcion
     */
    void actualizarPersistencia() throws EntityServicioExcepcion;

    /**
     * Adiciona el entity
     * @param pEntity
     * @throws javax.persistence.EntityExistsException
     * @throws EntityServicioExcepcion
     */
    void adicionar(E pEntity) throws EntityExistsException, EntityServicioExcepcion;

    /**
     * Borra el entity
     * @param entity
     * @throws EntityServicioExcepcion
     */
    void borrar(E entity) throws EntityServicioExcepcion;

    /**
     * Busca el entity asociado a la llave y lo borra
     * @param llave
     * @throws EntityServicioExcepcion
     */
    void borrarPorLlave(Object llave) throws EntityServicioExcepcion;

    /**
     * Retorna el Entity correspondiente a la llave dada
     * @param objClase clase de la llave
     * @param llave valor a buscar
     * @return
     * @throws EntityServicioExcepcion
     */
    @SuppressWarnings(value = "unchecked")
    E buscar(Class objClase, Object llave) throws EntityServicioExcepcion;

    /**
     * Busca el Entity por llave principal , la llave está incluía en los atributos del entity
     * @param llave valor a buscar
     * @return objeto de tipo <E>
     * @throws EntityServicioExcepcion
     */
    @SuppressWarnings(value = "unchecked")
    E buscar(Object llave) throws EntityServicioExcepcion;

    /**
     * Realiza una consulta con paginación del query pasado en el parámetro nombreQuery
     * @param pagina Página
     * Número de la pagina a presentar  es controlado en la capa de presenteción para la paginación
     * @param regsPorPagina
     * Número de registros a retornar, es utilizado para calcular el número de registro inicial de cada página,  -1 para todos
     * @param nombreQuery
     * Nombre del NamedQuery a ejecutar
     * @return
     * Colección con los registros consultados
     * @throws EntityServicioExcepcion
     */
    @SuppressWarnings(value = "unchecked")
    Collection<E> consultarPorQuery(Integer pagina, Integer regsPorPagina, String nombreQuery) throws EntityServicioExcepcion;

    /**
     * Retorna todos los entitys existentes en la tabla asociada a él
     * @return Collection Cajero
     */
    @SuppressWarnings(value = {"unchecked", "unchecked"})
    Collection<E> getTodos();
    
    /**
     * Obtiene <code>regsPorPagina</code>registros de ConfAccesoAplicacion  desde el registro <code>pagina</code>
     * @param pagina
     * @param regsPorPagina
     * @return
     * @throws java.lang.Exception
     */
    public Collection<E> getTodos(Integer pagina, Integer regsPorPagina) throws Exception;
    
    

}
