package com.davivienda.sara.base;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.EntityExistsException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import oracle.toplink.essentials.config.CacheUsage;

/**
 * BaseEntityServicio - 24/05/2008 Descripción : Versión : 1.0
 *
 * @param <E> Entity a Administrar
 * @author jjvargas Davivienda 2008
 */
public class BaseEntityServicio<E> extends BaseServicio implements AdministracionTablasInterface<E> {

    private Class objEntity;

    /**
     * Crea una instancia de administración de entity para el Entity claseEntity
     *
     * @param em
     * @param claseEntity
     */
    public BaseEntityServicio(javax.persistence.EntityManager em, Class claseEntity) {
        super(em);
        this.objEntity = claseEntity;

    }

    /**
     * Adiciona el entity
     *
     * @param pEntity
     * @throws javax.persistence.EntityExistsException
     * @throws EntityServicioExcepcion
     */
    public void adicionar(E pEntity) throws EntityExistsException, EntityServicioExcepcion {
        if (pEntity != null) {
            try {
                em.persist(pEntity);
                //Alvaro Garcia 19  marzo
                // em.flush();
            } catch (IllegalStateException ex) {
                configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
                throw new EntityServicioExcepcion(ex);
            } catch (TransactionRequiredException ex) {
                configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Noexiste una transacción activa", ex);
                throw new EntityServicioExcepcion(ex);
            } catch (IllegalArgumentException ex) {
                configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No es un entity", ex);
                throw new EntityServicioExcepcion(ex);
            }

        }
    }

    /**
     * Retorna todos los entitys existentes en la tabla asociada a él
     *
     * @return Collection Cajero
     */
    @SuppressWarnings(value = {"unchecked", "unchecked"})
    public Collection<E> getTodos() {
        String nombreQueryTodos = this.objEntity.getSimpleName() + ".todos";
        Collection<E> regs = new ArrayList<E>();
        try {
            //regs = em.createNamedQuery(nombreQueryTodos).getResultList();
            //ANTERIOR
            //regs = em.createNamedQuery(nombreQueryTodos).setHint("toplink.refresh", "true").getResultList();
            em.clear();
            regs = em.createNamedQuery(nombreQueryTodos).setHint("javax.persistence.cache.storeMode", "REFRESH").getResultList();
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No es un entity", ex);
        } finally {
            if (regs.isEmpty()) {
                configApp.loggerApp.finest("No se encontraron datos con la búsqueda " + nombreQueryTodos);
            }
        }
        return regs;
    }

    /**
     * Realiza una consulta con paginación del query pasado en el parámetro
     * nombreQuery
     *
     * @param pagina Página Número de la pagina a presentar es controlado en la
     * capa de presenteción para la paginación
     * @param regsPorPagina Número de registros a retornar, es utilizado para
     * calcular el número de registro inicial de cada página, -1 para todos
     * @return Colección con los registros consultados
     * @throws EntityServicioExcepcion
     */
    @SuppressWarnings(value = "unchecked")
    public Collection<E> getTodos(Integer pagina, Integer regsPorPagina) throws EntityServicioExcepcion {
        return consultarPorQuery(pagina, regsPorPagina, this.objEntity.getSimpleName() + ".todos");
    }

    /**
     * Retorna el Entity correspondiente a la llave dada
     *
     * @param objClase clase de la llave
     * @param llave valor a buscar
     * @return
     * @throws EntityServicioExcepcion
     */
    @SuppressWarnings(value = "unchecked")
    public E buscar(Class objClase, Object llave) throws EntityServicioExcepcion {
        E entity = null;
        try {
            entity = (E) em.find(objClase, llave);
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No es un entity", ex);
            throw new EntityServicioExcepcion(ex);
        }

        return entity;
    }

    /**
     * Busca el Entity por llave principal , la llave está incluía en los
     * atributos del entity
     *
     * @param llave valor a buscar
     * @return objeto de tipo <E>
     * @throws EntityServicioExcepcion
     */
    @SuppressWarnings(value = "unchecked")
    public E buscar(Object llave) throws EntityServicioExcepcion {
        return this.buscar(this.objEntity, llave);
    }

    /**
     * Realiza una consulta con paginación del query pasado en el parámetro
     * nombreQuery
     *
     * @param pagina Página Número de la pagina a presentar es controlado en la
     * capa de presenteción para la paginación
     * @param regsPorPagina Número de registros a retornar, es utilizado para
     * calcular el número de registro inicial de cada página, -1 para todos
     * @param nombreQuery Nombre del NamedQuery a ejecutar
     * @return Colección con los registros consultados
     * @throws EntityServicioExcepcion
     */
    @SuppressWarnings(value = "unchecked")
    public Collection<E> consultarPorQuery(Integer pagina, Integer regsPorPagina, String nombreQuery) throws EntityServicioExcepcion {
        Collection<E> regs = null;
        Query query = null;
        regsPorPagina = (regsPorPagina == null) ? 1 : regsPorPagina;
        pagina = (pagina == null) ? 1 : pagina;
        Integer regInicial = pagina * regsPorPagina;
        regInicial -= 1;
        try {
            query = em.createNamedQuery(nombreQuery);
            if (regInicial > 0) {
                query.setFirstResult(regInicial);
            }
            if (regsPorPagina > 0) {
                query.setMaxResults(regsPorPagina);
            }
            regs = query.getResultList();
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return regs;
    }

    /**
     * Borra el entity
     *
     * @param entity
     * @throws EntityServicioExcepcion
     */
    public void borrar(E entity) throws EntityServicioExcepcion {
        try {

            ///   em.remove(em.merge(entity)) ;
            em.remove(entity);

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (TransactionRequiredException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Na existe una transacción activa", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No es un entity", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se puede borrar el entity " + ex.getMessage(), ex);
            throw new EntityServicioExcepcion(ex);
        }
    }

    /**
     * Adiciona el objeto pasado al contenedor de entitys para ser procesado en
     * los procesos de persistencia transaccionales
     *
     * @param objeto desligado al contenedor de entitys
     * @return referencia del objeto manejado por el contenedor
     * @throws EntityServicioExcepcion
     */
    public E actualizar(E objeto) throws EntityServicioExcepcion {
        try {
            //    em.clear();

            return em.merge(objeto);
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (TransactionRequiredException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Na existe una transacción activa", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No es un entity", ex);
            throw new EntityServicioExcepcion(ex);
        }
    }

    /**
     * Busca el entity asociado a la llave y lo borra
     *
     * @param llave
     * @throws EntityServicioExcepcion
     */
    public void borrarPorLlave(Object llave) throws EntityServicioExcepcion {
        E entity = this.buscar(llave);

        this.borrar(entity);
        //ConfAccesoAplicacion entity2 = em.merge((ConfAccesoAplicacion)entity);
        //ConfAccesoAplicacion entity2 =(ConfAccesoAplicacion)entity;
        //entity2.setConfAccesoAplicacionPK(null);  
        //em.remove(entity);

    }

    /**
     * Actualiza todo el contenedor en la base de datos
     *
     * @throws EntityServicioExcepcion
     */
    public void actualizarPersistencia() throws EntityServicioExcepcion {

        try {

            em.flush();

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (TransactionRequiredException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Na existe una transacción activa", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No es un entity", ex);
            throw new EntityServicioExcepcion(ex);
        }
    }

    public void ejecutarSpDesdeJob(String nombreSP) {
        configApp.loggerApp.log(java.util.logging.Level.INFO, "Entra  Invocando SP " + nombreSP + " a las: " + Calendar.getInstance().getTime());
        Query query = em.createNativeQuery("  declare     l_jobid number := null;   begin     dbms_job.submit     (       job       =>  l_jobid,       what      =>  'BEGIN " + nombreSP + " END;',       next_date =>  sysdate,       interval  =>  null     );     commit;   end;");
        configApp.loggerApp.log(java.util.logging.Level.INFO, "Query: " + query.toString());
        query.executeUpdate();
        configApp.loggerApp.log(java.util.logging.Level.INFO, "Sale de invocacion SP " + nombreSP + " a las: " + Calendar.getInstance().getTime());
    }
}
