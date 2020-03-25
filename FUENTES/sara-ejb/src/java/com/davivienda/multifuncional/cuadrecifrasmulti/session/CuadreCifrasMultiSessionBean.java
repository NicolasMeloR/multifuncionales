/*
 * CuadreCifrasSessionBean.java
 *
 * Fecha       : 25/08/2007, 10:41:24 AM
 * Descripción :
 *
 * Babel Ver   :1.0
 */

package com.davivienda.multifuncional.cuadrecifrasmulti.session;


import com.davivienda.multifuncional.cuadrecifrasmulti.servicio.CuadreCifrasMultiSessionServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Movimientocuadremulti;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.davivienda.multifuncional.tablas.tipomovimientocuadremulti.servicio.TipoMovimientoCuadreMultiServicio;
import com.davivienda.sara.entitys.Tipomovimientocuadremulti;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author mdruiz
 */


@Stateless
@Local(value = CuadreCifrasMultiSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class CuadreCifrasMultiSessionBean implements CuadreCifrasMultiSessionLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    private CuadreCifrasMultiSessionServicio servicio;
    private TipoMovimientoCuadreMultiServicio servicioTipoMov;
    
    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        servicio = new CuadreCifrasMultiSessionServicio(em);
        servicioTipoMov=new TipoMovimientoCuadreMultiServicio(em);
    }
    
   
     public Movimientocuadremulti grabarMovimientoCuadre(Movimientocuadremulti mc, boolean actualizar) throws EntityServicioExcepcion {
      Movimientocuadremulti mCuadremulti = servicio.grabarMovimientoCuadre(mc, actualizar);
      return mCuadremulti;
    }
     public Tipomovimientocuadremulti buscarTipoMovimientoCuadre(Integer codigoTipoMovimientoCuadre) throws EntityServicioExcepcion
     {
         return servicioTipoMov.buscar(codigoTipoMovimientoCuadre);
     }
     public Collection<Movimientocuadremulti> getUsuarioFecha(String usuario, Date fechaInicial) throws EntityServicioExcepcion 
     { 
        return servicio.getUsuarioFecha(usuario,fechaInicial);
     }  
     
    
}
