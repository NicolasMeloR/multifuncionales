package com.davivienda.multifuncional.tablas.edccargue.servicio;


import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Edcarguemultifuncional;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
/**
 * EdcMultifuncionalServicio 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */

public class EdcMultifuncionalServicio extends BaseEntityServicio<Edcarguemultifuncional> implements AdministracionTablasInterface<Edcarguemultifuncional> {

    
    public EdcMultifuncionalServicio(EntityManager em) {
        super(em, Edcarguemultifuncional.class);
    }

    @Override
    public Edcarguemultifuncional actualizar(Edcarguemultifuncional objeto) throws EntityServicioExcepcion {
        Edcarguemultifuncional objetoActual = super.buscar(objeto.getIdedccargue());
        String accion = (objetoActual == null) ? "NUEVO" : "ACTUALIZAR";
        if (accion.equals("NUEVO")) {
            // Es nuevo y debo asociar las relaciones
            super.adicionar(objeto);
            objetoActual = super.buscar(objeto.getIdedccargue());
        } else {
            // Se actualizan solo datos
            
            objetoActual = objetoActual.actualizarEntity(objeto);
            super.actualizar(objetoActual);
        }
        return objetoActual;
    }
    
    

    public Collection<Edcarguemultifuncional> getEDCCargueXFecha(Date fechaInicial, Date fechaFinal)throws EntityServicioExcepcion
    {
   
        Collection<Edcarguemultifuncional> items = null;
        if (fechaInicial == null) {
            fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser mayor a la fecha Inicial");
        }
        try {
            Query query = null;
                                          
                query = em.createNamedQuery("Edcarguemultifuncional.RangoFecha");
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fechaFinal);
                items = query.getResultList();
           
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    
    }
    
    



}
