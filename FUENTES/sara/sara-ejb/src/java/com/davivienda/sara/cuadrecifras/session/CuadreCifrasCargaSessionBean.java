// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.cuadrecifras.session;

import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.constantes.EstadoProceso;
import javax.transaction.SystemException;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.regcargue.servicio.RegCargueArchivoServicio;
import com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.servicio.ProcesadorProvisionesArchivoServicio;
import com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.servicio.ProcesadorTotalesEstacionArchivoServicio;
import com.davivienda.sara.procesos.cuadrecifras.filtro.corte.servicio.ProcesadorCorteArchivoServicio;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ CuadreCifrasCargasSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class CuadreCifrasCargaSessionBean implements CuadreCifrasCargasSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private ProcesadorCorteArchivoServicio corteServicio;
    private ProcesadorTotalesEstacionArchivoServicio totalesEstacionServicio;
    private ProcesadorProvisionesArchivoServicio provisionesServicio;
    private RegCargueArchivoServicio regCargueArchivoServicio;
    public String msgRegError;
    
    @PostConstruct
    public void postConstructor() {
        this.corteServicio = new ProcesadorCorteArchivoServicio(this.em);
        this.totalesEstacionServicio = new ProcesadorTotalesEstacionArchivoServicio(this.em);
        this.provisionesServicio = new ProcesadorProvisionesArchivoServicio(this.em);
        this.regCargueArchivoServicio = new RegCargueArchivoServicio(this.em);
        this.msgRegError = "";
    }
    
    @Override
    public Integer cargarArchivoCorte(final Calendar fecha, final boolean cargueautomatico) {
        this.corteServicio.getConfigApp().loggerApp.info("CuadreCifrasCargaSessionBean  -- cargarArchivoCorte");
        Integer registros = -1;
        String msgError = "";
        final String strFecha = Fecha.aCadena(fecha, FormatoFecha.FECHA_HORA);
        try {
            this.utx.begin();
            registros = this.corteServicio.cargarArchivoCorte(fecha);
            this.utx.commit();
        }
        catch (FileNotFoundException ex) {
            this.corteServicio.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            msgError = "No existe el archivo Corte para la fecha  " + strFecha + " " + ex.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        }
        catch (EntityServicioExcepcion ex2) {
            this.corteServicio.getConfigApp().loggerApp.log(Level.SEVERE, null, ex2);
            msgError = "Error al grabar archivo Corte para la fecha  " + strFecha + " " + ex2.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        }
        catch (IllegalArgumentException ex3) {
            this.corteServicio.getConfigApp().loggerApp.log(Level.SEVERE, null, ex3);
            msgError = "Error al grabar archivo Corte para la fecha  " + strFecha + " " + ex3.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        }
        catch (EJBTransactionRolledbackException ex4) {
            this.corteServicio.getConfigApp().loggerApp.log(Level.SEVERE, null, (Throwable)ex4);
            msgError = "Error al grabar archivo Corte para la fecha  " + strFecha + " " + ex4.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        }
        catch (EJBException ex5) {
            msgError = "Error al grabar archivo Corte para la fecha  " + strFecha + " " + ex5.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        }
        catch (Exception ex6) {
            this.corteServicio.getConfigApp().loggerApp.log(Level.SEVERE, null, ex6);
            msgError = "Error al grabar archivo Corte para la fecha  " + strFecha + " " + ex6.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        }
        return registros;
    }
    
    @Override
    public Integer cargarArchivoTotalesEgresos(final Calendar fecha, final boolean cargueautomatico) {
        Integer registros = -1;
        String msgError = "";
        try {
            this.utx.begin();
            registros = this.totalesEstacionServicio.cargarArchivoTotalesEstacion(fecha);
            this.utx.commit();
        }
        catch (FileNotFoundException ex) {
            msgError = "No existe el archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        catch (EntityServicioExcepcion ex2) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex2.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        catch (IllegalArgumentException ex3) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex3.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        catch (EJBTransactionRolledbackException ex4) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex4.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        catch (EJBException ex5) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex5.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        catch (Exception ex6) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex6.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        return registros;
    }
    
    @Override
    public Integer cargarArchivoProvisiones(final Calendar fecha, final boolean cargueautomatico) {
        Integer registros = -1;
        String msgError = "";
        try {
            this.utx.begin();
            registros = this.provisionesServicio.cargarArchivoProvisiones(fecha);
            this.utx.commit();
        }
        catch (FileNotFoundException ex) {
            msgError = "No existe el archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        catch (EntityServicioExcepcion ex2) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex2.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        catch (IllegalArgumentException ex3) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex3.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        catch (EJBTransactionRolledbackException ex4) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex4.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        catch (EJBException ex5) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex5.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        catch (Exception ex6) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex6.getMessage();
            this.corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        finally {
            try {
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
                try {
                    this.actualizarRegCargueArchivo("cfamo001", cargueautomatico, fecha, msgError);
                }
                catch (Exception ex7) {
                    msgError = "Error al actualizar registro RegCargueArchivo para la fecha  " + fecha.toString() + " " + ex7.getMessage();
                    this.corteServicio.getConfigApp().loggerApp.info(msgError);
                }
            }
            catch (IllegalStateException ex8) {
                Logger.getLogger("globalApp").info(ex8.getMessage());
            }
            catch (SecurityException ex9) {
                Logger.getLogger("globalApp").info(ex9.getMessage());
            }
            catch (SystemException ex10) {
                Logger.getLogger("globalApp").info(ex10.getMessage());
            }
        }
        return registros;
    }
    
    private void actualizarRegCargueArchivo(final String archivoTarea, final boolean IndAuto, final Calendar fechaTarea, final String msgError) throws EntityServicioExcepcion {
        String strFechaTarea = "";
        strFechaTarea = Fecha.aCadena(fechaTarea, "yyMMdd");
        final Regcarguearchivo edcCargue = this.regCargueArchivoServicio.buscarPorArchivoFecha(archivoTarea, strFechaTarea, IndAuto);
        if (edcCargue != null) {
            if (msgError.equals("")) {
                edcCargue.setEstadocarga(EstadoProceso.FINALIZADO.getEstado());
            }
            else {
                edcCargue.setEstadocarga(Integer.valueOf(3));
                edcCargue.setDescripcionerror(msgError);
            }
            edcCargue.setFecha(Fecha.getDateHoy());
            this.regCargueArchivoServicio.actualizar(edcCargue);
            this.regCargueArchivoServicio.actualizarPersistencia();
        }
    }
}
