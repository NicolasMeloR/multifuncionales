// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.base;

import java.util.Calendar;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import javax.persistence.EntityManager;

public class BaseEntityServicio<E> extends BaseServicio implements AdministracionTablasInterface<E>
{
    private Class objEntity;
    
    public BaseEntityServicio(final EntityManager em, final Class claseEntity) {
        super(em);
        this.objEntity = claseEntity;
    }
    
    @Override
    public void adicionar(final E pEntity) throws EntityExistsException, EntityServicioExcepcion {
        if (pEntity != null) {
            try {
                this.em.persist((Object)pEntity);
            }
            catch (IllegalStateException ex) {
                BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
                throw new EntityServicioExcepcion(ex);
            }
            catch (TransactionRequiredException ex2) {
                BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "Noexiste una transacci\u00f3n activa", (Throwable)ex2);
                throw new EntityServicioExcepcion((Throwable)ex2);
            }
            catch (IllegalArgumentException ex3) {
                BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No es un entity", ex3);
                throw new EntityServicioExcepcion(ex3);
            }
        }
    }
    
    @Override
    public Collection<E> getTodos() {
        final String nombreQueryTodos = this.objEntity.getSimpleName() + ".todos";
        Collection<E> regs = new ArrayList<E>();
        try {
            this.em.clear();
            regs = (Collection<E>)this.em.createNamedQuery(nombreQueryTodos).setHint("javax.persistence.cache.storeMode", (Object)"REFRESH").getResultList();
        }
        catch (IllegalStateException ex) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
        }
        catch (IllegalArgumentException ex2) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No es un entity", ex2);
        }
        finally {
            if (regs.isEmpty()) {
                BaseEntityServicio.configApp.loggerApp.finest("No se encontraron datos con la b\u00fasqueda " + nombreQueryTodos);
            }
        }
        return regs;
    }
    
    @Override
    public Collection<E> getTodos(final Integer pagina, final Integer regsPorPagina) throws EntityServicioExcepcion {
        return this.consultarPorQuery(pagina, regsPorPagina, this.objEntity.getSimpleName() + ".todos");
    }
    
    @Override
    public E buscar(final Class objClase, final Object llave) throws EntityServicioExcepcion {
        E entity = null;
        try {
            entity = (E)this.em.find(objClase, llave);
        }
        catch (IllegalStateException ex) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No es un entity", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return entity;
    }
    
    @Override
    public E buscar(final Object llave) throws EntityServicioExcepcion {
        return this.buscar(this.objEntity, llave);
    }
    
    @Override
    public Collection<E> consultarPorQuery(Integer pagina, Integer regsPorPagina, final String nombreQuery) throws EntityServicioExcepcion {
        Collection<E> regs = null;
        Query query = null;
        regsPorPagina = ((regsPorPagina == null) ? 1 : regsPorPagina);
        pagina = ((pagina == null) ? 1 : pagina);
        Integer regInicial = pagina * regsPorPagina;
        --regInicial;
        try {
            query = this.em.createNamedQuery(nombreQuery);
            if (regInicial > 0) {
                query.setFirstResult((int)regInicial);
            }
            if (regsPorPagina > 0) {
                query.setMaxResults((int)regsPorPagina);
            }
            regs = (Collection<E>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return regs;
    }
    
    @Override
    public void borrar(final E entity) throws EntityServicioExcepcion {
        try {
            this.em.remove((Object)entity);
        }
        catch (IllegalStateException ex) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (TransactionRequiredException ex2) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "Na existe una transacci\u00f3n activa", (Throwable)ex2);
            throw new EntityServicioExcepcion((Throwable)ex2);
        }
        catch (IllegalArgumentException ex3) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No es un entity", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
        catch (Exception ex4) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No se puede borrar el entity " + ex4.getMessage(), ex4);
            throw new EntityServicioExcepcion(ex4);
        }
    }
    
    @Override
    public E actualizar(final E objeto) throws EntityServicioExcepcion {
        try {
            return (E)this.em.merge((Object)objeto);
        }
        catch (IllegalStateException ex) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (TransactionRequiredException ex2) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "Na existe una transacci\u00f3n activa", (Throwable)ex2);
            throw new EntityServicioExcepcion((Throwable)ex2);
        }
        catch (IllegalArgumentException ex3) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No es un entity", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
    }
    
    @Override
    public void borrarPorLlave(final Object llave) throws EntityServicioExcepcion {
        final E entity = this.buscar(llave);
        this.borrar(entity);
    }
    
    @Override
    public void actualizarPersistencia() throws EntityServicioExcepcion {
        try {
            this.em.flush();
        }
        catch (IllegalStateException ex) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No hay acceso al manejador de entitys ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (TransactionRequiredException ex2) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "Na existe una transacci\u00f3n activa", (Throwable)ex2);
            throw new EntityServicioExcepcion((Throwable)ex2);
        }
        catch (IllegalArgumentException ex3) {
            BaseEntityServicio.configApp.loggerApp.log(Level.SEVERE, "No es un entity", ex3);
            throw new EntityServicioExcepcion(ex3);
        }
    }
    
    public void ejecutarSpDesdeJob(final String nombreSP) {
        BaseEntityServicio.configApp.loggerApp.log(Level.INFO, "Entra  Invocando SP " + nombreSP + " a las: " + Calendar.getInstance().getTime());
        final Query query = this.em.createNativeQuery("  declare     l_jobid number := null;   begin     dbms_job.submit     (       job       =>  l_jobid,       what      =>  'BEGIN " + nombreSP + " END;',       next_date =>  sysdate,       interval  =>  null     );     commit;   end;");
        BaseEntityServicio.configApp.loggerApp.log(Level.INFO, "Query: " + query.toString());
        query.executeUpdate();
        BaseEntityServicio.configApp.loggerApp.log(Level.INFO, "Sale de invocacion SP " + nombreSP + " a las: " + Calendar.getInstance().getTime());
    }
}
