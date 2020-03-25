// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tareas.regcargue.session;

import java.util.Collection;
import java.util.logging.Logger;
import javax.transaction.SystemException;
import java.util.logging.Level;
import java.util.Date;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Regcarguearchivo;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tareas.regcargue.servicio.AdminTareasRegCargueArchivoServicio;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ AdminTareasRegCargueArchivoSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class AdminTareasRegCargueArchivoSessionBean implements AdminTareasRegCargueArchivoSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private AdminTareasRegCargueArchivoServicio adminTareasRegCargueArchivoServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.adminTareasRegCargueArchivoServicio = new AdminTareasRegCargueArchivoServicio(this.em);
    }
    
    @Override
    public void actualizar(final Regcarguearchivo objetoModificado) throws EntityServicioExcepcion {
        this.adminTareasRegCargueArchivoServicio.actualizar(objetoModificado);
    }
    
    @Override
    public Regcarguearchivo buscarPorArchivoFecha(final String archivoTarea, final String fecha, final boolean IndAuto) throws EntityServicioExcepcion {
        return this.adminTareasRegCargueArchivoServicio.buscarPorArchivoFecha(archivoTarea, fecha, IndAuto);
    }
    
    @Override
    public void guardarRegCargueArchivo(final String archivoTarea, final boolean IndAuto, final String fechaTarea, final String tarea, final Date fecha, final String usuario, final boolean esCargueDipensador, final String descrpcionError) throws EntityServicioExcepcion, IllegalArgumentException {
        boolean creaRegNuevo = false;
        final Regcarguearchivo edcCargue = this.buscarPorArchivoFecha(archivoTarea, fechaTarea, IndAuto);
        if (esCargueDipensador) {
            if (!IndAuto && edcCargue == null) {
                creaRegNuevo = true;
            }
        }
        else {
            creaRegNuevo = true;
        }
        if (creaRegNuevo) {
            final Logger loggerApp = this.adminTareasRegCargueArchivoServicio.getConfigApp().loggerApp;
            try {
                this.utx.begin();
                this.adminTareasRegCargueArchivoServicio.guardarRegCargueArchivo(archivoTarea, IndAuto, fechaTarea, tarea, fecha, usuario, descrpcionError);
                this.utx.commit();
            }
            catch (IllegalArgumentException ex) {
                loggerApp.log(Level.SEVERE, "", ex);
                loggerApp.log(Level.INFO, "Error al grabar los datos en RegCargueArchivo para el archivo {0}{1} {2}", new Object[] { archivoTarea, fechaTarea, ex.getMessage() });
            }
            catch (Exception ex2) {
                loggerApp.log(Level.SEVERE, "", ex2);
                loggerApp.log(Level.INFO, "Error al grabar los datos en RegCargueArchivo  para el archivo :{0}{1} descripcion Error : {2}", new Object[] { archivoTarea, fechaTarea, ex2.getMessage() });
            }
            finally {
                try {
                    if (this.utx.getStatus() != 6) {
                        this.utx.rollback();
                    }
                }
                catch (IllegalStateException ex3) {
                    loggerApp.info(ex3.getMessage());
                }
                catch (SecurityException ex4) {
                    loggerApp.info(ex4.getMessage());
                }
                catch (SystemException ex5) {
                    loggerApp.info(ex5.getMessage());
                }
            }
        }
    }
    
    @Override
    public Collection<Regcarguearchivo> getRegCargueArchivoPorFecha(final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        return this.adminTareasRegCargueArchivoServicio.getRegCargueArchivoPorFecha(fechaInicial, fechaFinal);
    }
}
