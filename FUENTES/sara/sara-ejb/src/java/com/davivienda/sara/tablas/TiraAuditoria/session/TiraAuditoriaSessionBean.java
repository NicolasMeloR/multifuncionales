/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.tablas.TiraAuditoria.session;

import com.davivienda.sara.base.BaseAdministracionTablas;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.TiraAuditoria;
import com.davivienda.sara.tablas.TiraAuditoria.servicio.TiraAuditoriaServicio;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nmelo
 */
@Stateless
public class TiraAuditoriaSessionBean implements TiraAuditoriaSessionLocal {
    
    @PersistenceContext
    private EntityManager em;
    private TiraAuditoriaServicio TiraAuditoriaServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.TiraAuditoriaServicio = new TiraAuditoriaServicio(this.em);
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<TiraAuditoria> getTira(Integer p0, Date p1, Date p2){
        
        try {
            List<TiraAuditoria> list;
            list= this.TiraAuditoriaServicio.getTira(p0, p1, p2);
            return list;
            //To change body of generated methods, choose Tools | Templates.
        } catch (EntityServicioExcepcion ex) {
            Logger.getLogger(TiraAuditoriaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public TiraAuditoria actualizar(TiraAuditoria p0) throws EntityServicioExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarPersistencia() throws EntityServicioExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adicionar(TiraAuditoria p0) throws EntityExistsException, EntityServicioExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrar(TiraAuditoria p0) throws EntityServicioExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrarPorLlave(Object p0) throws EntityServicioExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TiraAuditoria buscar(Class p0, Object p1) throws EntityServicioExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TiraAuditoria buscar(Object p0) throws EntityServicioExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TiraAuditoria> consultarPorQuery(Integer p0, Integer p1, String p2) throws EntityServicioExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TiraAuditoria> getTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TiraAuditoria> getTodos(Integer p0, Integer p1) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
