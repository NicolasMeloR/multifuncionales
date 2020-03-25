/*
 * ProcesoCuadreCifrasSessionLocal.java
 *
 * Created on 25/09/2007, 09:32:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.davivienda.sara.procesos.cargues.masivos.timbres.session;

import com.davivienda.sara.entitys.CarmasTimbresTemp;
import com.davivienda.sara.exception.ServiceInExecutionException;
import java.util.Collection;
import javax.ejb.Local;
import javax.management.InvalidApplicationException;

/**
 *
 * @author juan jaime
 */
@Local
public interface CargarTimbresMasivosSessionLocal {

    /**
     * Ejecuar el cargue de los timbres en el modelo temporal de sara
     *
     * @param timbres
     * @return
     */
    public Collection<CarmasTimbresTemp> ejecutarCargue(Collection<CarmasTimbresTemp> timbres, String usuario) throws RuntimeException, ServiceInExecutionException;

    /**
     * Actualizar el timbre en el modelo temporal de sara
     *
     * @param timbre
     * @return
     */
    public boolean update(CarmasTimbresTemp timbre);

    /**
     * Obtener todos los timbres del modelo temporal de sara
     *
     * @return
     */
    public Collection<CarmasTimbresTemp> getTodos();

    /**
     * Ejecutar volcado de información de la tabla temporal al modelo de log del
     * sistema sara
     */
    public String actualizarLog(String usuario);

}
