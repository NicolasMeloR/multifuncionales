// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tareas.regcargue.servicio;

import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import com.davivienda.sara.constantes.EstadoProceso;
import java.util.Date;
import javax.persistence.Query;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import com.davivienda.sara.entitys.Regcarguearchivo;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.regcargue.servicio.RegCargueArchivoServicio;
import com.davivienda.sara.base.BaseServicio;

public class AdminTareasRegCargueArchivoServicio extends BaseServicio
{
    RegCargueArchivoServicio regCargueArchivoServicio;
    
    public AdminTareasRegCargueArchivoServicio(final EntityManager em) {
        super(em);
        this.regCargueArchivoServicio = new RegCargueArchivoServicio(em);
    }
    
    public Regcarguearchivo buscarPorArchivoFecha(final String archivoTarea, final String fecha, final boolean IndAuto) throws EntityServicioExcepcion {
        Regcarguearchivo item = null;
        String strNamedQuery = "";
        String strParamFecha = "";
        try {
            Query query = null;
            Short stIndAuto;
            if (IndAuto) {
                strNamedQuery = "Regcarguearchivo.ArchivoFechaAuto";
                stIndAuto = 1;
                strParamFecha = "fechaautomatica";
            }
            else {
                strNamedQuery = "Regcarguearchivo.ArchivoFechaManual";
                stIndAuto = 0;
                strParamFecha = "fechamanual";
            }
            query = this.em.createNamedQuery(strNamedQuery);
            query.setParameter("archivotarea", (Object)archivoTarea);
            query.setParameter("indautomatico", (Object)stIndAuto);
            query.setParameter(strParamFecha, (Object)fecha);
            if (archivoTarea.equals("")) {
                item = null;
            }
            else if (!query.getResultList().isEmpty()) {
                item = (Regcarguearchivo) query.getResultList().get(0);
            }
        }
        catch (IllegalStateException ex) {
            AdminTareasRegCargueArchivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            AdminTareasRegCargueArchivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return item;
    }
    
    public void guardarRegCargueArchivo(final String archivoTarea, final boolean IndAuto, final String fechaTarea, final String tarea, final Date fecha, final String usuario, final String descrpcionError) throws EntityServicioExcepcion, IllegalArgumentException {
        final Regcarguearchivo regcarguearchivo = new Regcarguearchivo();
        regcarguearchivo.setArchivotarea(archivoTarea);
        regcarguearchivo.setTarea(tarea);
        regcarguearchivo.setFecha(fecha);
        regcarguearchivo.setEstadocarga(EstadoProceso.INICIADO.getEstado());
        regcarguearchivo.setUsuario(usuario);
        regcarguearchivo.setDescripcionerror(descrpcionError);
        Integer stIndAuto;
        if (IndAuto) {
            regcarguearchivo.setFechaautomatica(fechaTarea);
            stIndAuto = 1;
        }
        else {
            stIndAuto = 0;
            regcarguearchivo.setFechamanual(fechaTarea);
        }
        regcarguearchivo.setIndautomatico(stIndAuto);
        AdminTareasRegCargueArchivoServicio.configApp.loggerApp.log(Level.INFO, "Guardando el registro RegCargueArchivo para el archivo: {0}", regcarguearchivo);
        try {
            this.regCargueArchivoServicio.adicionar(regcarguearchivo);
            this.regCargueArchivoServicio.actualizarPersistencia();
        }
        catch (Exception ex) {
            AdminTareasRegCargueArchivoServicio.configApp.loggerApp.log(Level.SEVERE, "", ex);
            AdminTareasRegCargueArchivoServicio.configApp.loggerApp.log(Level.INFO, "Error cargando en Regcarguearchivo registro datos archivo  :{0}{1} descripcion Error : {2}", new Object[] { archivoTarea, fechaTarea, ex.getMessage() });
        }
    }
    
    public void actualizar(final Regcarguearchivo objetoModificado) throws EntityServicioExcepcion {
        this.regCargueArchivoServicio.actualizar(objetoModificado);
    }
    
    public Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        Collection<Regcarguearchivo> items = null;
        if (fechaInicial == null) {
            fechaInicial = Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser mayor a la fecha Inicial");
        }
        try {
            Query query = null;
            query = this.em.createNamedQuery("EdcCargue.RangoFecha");
            query.setParameter("fechaInicial", (Object)fechaInicial);
            query.setParameter("fechaFinal", (Object)fechaFinal);
            items = (Collection<Regcarguearchivo>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            AdminTareasRegCargueArchivoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            AdminTareasRegCargueArchivoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
}
