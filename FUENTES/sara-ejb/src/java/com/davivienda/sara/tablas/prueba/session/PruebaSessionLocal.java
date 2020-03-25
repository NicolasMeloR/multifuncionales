/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.tablas.prueba.session;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.Prueba2;
import java.util.Collection;
import javax.ejb.Local;


/**
 *
 * @author aa.garcia
 */

@Local
public interface  PruebaSessionLocal extends AdministracionTablasInterface<Prueba2> {
    
    public Collection<Prueba2> getTodos() ;

}
