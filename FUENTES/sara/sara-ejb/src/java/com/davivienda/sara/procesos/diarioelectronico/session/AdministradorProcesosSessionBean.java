// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.diarioelectronico.session;

import java.util.Iterator;
import java.util.Collection;
import com.davivienda.utilidades.edc.Edc;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.Fecha;
import javax.transaction.SystemException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.io.FileNotFoundException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import com.davivienda.sara.tablas.edccargue.session.EdcCargueSessionLocal;
import com.davivienda.sara.procesos.diarioelectronico.servicio.ProcesadorDiarioElectronicoServicio;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ AdministradorProcesosSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class AdministradorProcesosSessionBean implements AdministradorProcesosSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private ProcesadorDiarioElectronicoServicio diarioElectronicoServicio;
    @EJB
    private EdcCargueSessionLocal edcCargueSessionLocal;
    
    @PostConstruct
    public void postConstructor() {
        this.diarioElectronicoServicio = new ProcesadorDiarioElectronicoServicio(this.em);
    }
    
    @Override
    public Integer cargarArchivo(final Integer codigoCajero, final Calendar fecha, String nombreArchivo) {
        Integer registros = -1;
        try {
            this.utx.begin();
            registros = this.diarioElectronicoServicio.cargarArchivo(codigoCajero, fecha, nombreArchivo);
            this.utx.commit();
        }
        catch (FileNotFoundException ex) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            Logger.getLogger("globalApp").info("No existe el archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha " + fecha.toString() + " " + ex.getMessage());
        }
        catch (EntityServicioExcepcion ex2) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex2);
            Logger.getLogger("globalApp").info("Error al grabar los datos  archivo Diario Electronico para codigoCajero " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex2.getMessage());
        }
        catch (IllegalArgumentException ex3) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex3);
            Logger.getLogger("globalApp").info("Error al grabar los datos archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex3.getMessage());
        }
        catch (Exception ex4) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex4);
            Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario Electronico" + ex4.getMessage());
            registros = -1;
        }
        finally {
            try {
                nombreArchivo = "";
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex5) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex5);
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
            catch (SecurityException ex6) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex6);
                Logger.getLogger("globalApp").info(ex6.getMessage());
            }
            catch (SystemException ex7) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", (Throwable)ex7);
                Logger.getLogger("globalApp").info(ex7.getMessage());
            }
        }
        return registros;
    }
    
    public Integer cargarArchivoCiclo(final Integer codigoCajero, final Calendar fecha, String nombreArchivo) {
        Integer registros = -1;
        try {
            this.utx.begin();
            registros = this.diarioElectronicoServicio.cargarArchivoCicloNuevo(codigoCajero, fecha, nombreArchivo);
            this.utx.commit();
        }
        catch (FileNotFoundException ex) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            Logger.getLogger("globalApp").info("No existe el archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha " + fecha.toString() + " " + ex.getMessage());
        }
        catch (EntityServicioExcepcion ex2) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex2);
            Logger.getLogger("globalApp").info("Error al grabar los datos  archivo Diario Electronico para codigoCajero " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex2.getMessage());
        }
        catch (IllegalArgumentException ex3) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex3);
            Logger.getLogger("globalApp").info("Error al grabar los datos archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex3.getMessage());
        }
        catch (Exception ex4) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex4);
            Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario Electronico" + ex4.getMessage());
        }
        finally {
            try {
                nombreArchivo = "";
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex5) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex5);
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
            catch (SecurityException ex6) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex6);
                Logger.getLogger("globalApp").info(ex6.getMessage());
            }
            catch (SystemException ex7) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", (Throwable)ex7);
                Logger.getLogger("globalApp").info(ex7.getMessage());
            }
        }
        return registros;
    }
    
    public void cargarTransaccionesCiclo(final Integer ciclo) {
        try {
            this.utx.begin();
            this.diarioElectronicoServicio.cargarTransaccionesCiclo(ciclo);
            this.utx.commit();
        }
        catch (IllegalArgumentException ex) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            Logger.getLogger("globalApp").info("Error al grabar los datos  de transaccion para el ciclo " + ciclo.toString() + " " + ex.getMessage());
        }
        catch (Exception ex2) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex2);
            Logger.getLogger("globalApp").info("No se grabaran los registros de la tabla transaccion temp en transaccion " + ex2.getMessage());
        }
        finally {
            try {
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex3) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex3);
                Logger.getLogger("globalApp").info(ex3.getMessage());
            }
            catch (SecurityException ex4) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex4);
                Logger.getLogger("globalApp").info(ex4.getMessage());
            }
            catch (SystemException ex5) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", (Throwable)ex5);
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
        }
    }
    
    @Override
    public Integer cargarCiclo(final Calendar fecha) {
        if (fecha == null || fecha.after(Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha v\u00e1lida");
        }
        Integer regsArchivo = 0;
        Integer regsProceso = 0;
        Integer cajProceso = 0;
        Integer ciclo = 0;
        ciclo = Cadena.aInteger(Fecha.aCadena(fecha, FormatoFecha.CICLO_EDC));
        try {
            final Collection<EdcCargue> edcCargueCiclo = this.edcCargueSessionLocal.getCicloSnError(ciclo);
            Logger.getLogger("globalApp").info("Se inicia el proceso del ciclo " + Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD));
            for (final EdcCargue edcCargue : edcCargueCiclo) {
                final String nombreArchivo = edcCargue.getNombrearchivo();
                final Calendar fechaCiclo = Edc.getFechaCiclo(nombreArchivo.substring(4, 8), fecha);
                regsArchivo = this.cargarArchivoCiclo(edcCargue.getCodigoCajero(), fechaCiclo, nombreArchivo);
                if (regsArchivo > 0) {
                    ++cajProceso;
                    regsProceso += regsArchivo;
                }
            }
        }
        catch (Exception ex) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            Logger.getLogger("globalApp").info("No se pudo obtener EdcCargue" + ex.getMessage());
        }
        Logger.getLogger("globalApp").info("Fin del proceso de  Diario Electronico  para el ciclo " + Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD) + ". Cajeros procesados : " + cajProceso);
        return regsProceso;
    }
    
    @Override
    public Integer cargarCicloTemp(final Calendar fecha) {
        if (fecha == null || fecha.after(Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha v\u00e1lida");
        }
        Integer regsArchivo = 0;
        Integer regsProceso = 0;
        Integer cajProceso = 0;
        Integer ciclo = 0;
        ciclo = Cadena.aInteger(Fecha.aCadena(fecha, FormatoFecha.CICLO_EDC));
        try {
            final Collection<EdcCargue> edcCargueCiclo = this.edcCargueSessionLocal.getCicloSnError(ciclo);
            Logger.getLogger("globalApp").info("Se inicia el proceso del ciclo " + Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD));
            for (final EdcCargue edcCargue : edcCargueCiclo) {
                final String nombreArchivo = edcCargue.getNombrearchivo();
                final Calendar fechaCiclo = Edc.getFechaCiclo(nombreArchivo.substring(4, 8), fecha);
                regsArchivo = this.cargarArchivoTemp(edcCargue.getCodigoCajero(), fechaCiclo, nombreArchivo);
                if (regsArchivo > 0) {
                    ++cajProceso;
                    regsProceso += regsArchivo;
                }
            }
        }
        catch (Exception ex) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            Logger.getLogger("globalApp").info("No se pudo obtener EdcCargue" + ex.getMessage());
        }
        Logger.getLogger("globalApp").info("Fin del proceso de  Diario Electronico  para el ciclo " + Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD) + ". Cajeros procesados : " + cajProceso);
        return regsProceso;
    }
    
    public void borrarDiarioElectronicoTemp() {
        final Integer registros = -1;
        try {
            this.utx.begin();
            this.diarioElectronicoServicio.borrarDiarioelectronicoTemp();
            this.utx.commit();
        }
        catch (EntityServicioExcepcion ex) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            Logger.getLogger("globalApp").info("Error al borrar los datos  Diario ElectronicoTemp para codigoCajero" + ex.getMessage());
        }
        catch (IllegalArgumentException ex2) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex2);
            Logger.getLogger("globalApp").info("Error al borrar los datos  Diario ElectronicoTemp " + ex2.getMessage());
        }
        catch (Exception ex3) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex3);
            Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario ElectronicoTemp" + ex3.getMessage());
        }
        finally {
            try {
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex4) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex4);
                Logger.getLogger("globalApp").info(ex4.getMessage());
            }
            catch (SecurityException ex5) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex5);
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
            catch (SystemException ex6) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", (Throwable)ex6);
                Logger.getLogger("globalApp").info(ex6.getMessage());
            }
        }
    }
    
    public void borrarTransaccionTemp() {
        final Integer registros = -1;
        try {
            this.utx.begin();
            this.diarioElectronicoServicio.borrarTransaccionTemp();
            this.utx.commit();
        }
        catch (EntityServicioExcepcion ex) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            Logger.getLogger("globalApp").info("Error al borrar los datos   TransaccionTemp para codigoCajero" + ex.getMessage());
        }
        catch (IllegalArgumentException ex2) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex2);
            Logger.getLogger("globalApp").info("Error al borrar los datos   TransaccionTemp " + ex2.getMessage());
        }
        catch (Exception ex3) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex3);
            Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo  TransaccionTemp" + ex3.getMessage());
        }
        finally {
            try {
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex4) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex4);
                Logger.getLogger("globalApp").info(ex4.getMessage());
            }
            catch (SecurityException ex5) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex5);
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
            catch (SystemException ex6) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", (Throwable)ex6);
                Logger.getLogger("globalApp").info(ex6.getMessage());
            }
        }
    }
    
    @Override
    public Integer cargarArchivoTemp(final Integer codigoCajero, final Calendar fecha, String nombreArchivo) {
        Integer registros = -1;
        try {
            this.utx.begin();
            registros = this.diarioElectronicoServicio.cargarArchivoTemp(codigoCajero, fecha, nombreArchivo);
            this.utx.commit();
        }
        catch (FileNotFoundException ex) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            Logger.getLogger("globalApp").info("No existe el archivo Diario Electronico Temp para codigoCajero  " + codigoCajero.toString() + " con fecha " + fecha.toString() + " " + ex.getMessage());
        }
        catch (EntityServicioExcepcion ex2) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex2);
            Logger.getLogger("globalApp").info("Error al grabar los datos  archivo Diario ElectronicoTemp para codigoCajero " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex2.getMessage());
        }
        catch (IllegalArgumentException ex3) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex3);
            Logger.getLogger("globalApp").info("Error al grabar los datos archivo Diario ElectronicoTemp para codigoCajero  " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex3.getMessage());
        }
        catch (Exception ex4) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex4);
            Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario ElectronicoTemp" + ex4.getMessage());
        }
        finally {
            try {
                nombreArchivo = "";
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex5) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex5);
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
            catch (SecurityException ex6) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex6);
                Logger.getLogger("globalApp").info(ex6.getMessage());
            }
            catch (SystemException ex7) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", (Throwable)ex7);
                Logger.getLogger("globalApp").info(ex7.getMessage());
            }
        }
        return registros;
    }
    
    @Override
    public Integer cargarDiarioElectronicoTemp(final Integer codigoCajero, final Calendar fecha, String nombreArchivo) {
        Integer registros = -1;
        try {
            this.utx.begin();
            registros = this.diarioElectronicoServicio.cargarDiarioElectronicoTemp(codigoCajero, fecha, nombreArchivo);
            this.utx.commit();
        }
        catch (FileNotFoundException ex) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            Logger.getLogger("globalApp").info("No existe el archivo Diario Electronico Temp para codigoCajero  " + codigoCajero.toString() + " con fecha " + fecha.toString() + " " + ex.getMessage());
        }
        catch (EntityServicioExcepcion ex2) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex2);
            Logger.getLogger("globalApp").info("Error al grabar los datos  archivo Diario ElectronicoTemp para codigoCajero " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex2.getMessage());
        }
        catch (IllegalArgumentException ex3) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex3);
            Logger.getLogger("globalApp").info("Error al grabar los datos archivo Diario ElectronicoTemp para codigoCajero  " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex3.getMessage());
        }
        catch (Exception ex4) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex4);
            Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario ElectronicoTemp" + ex4.getMessage());
        }
        finally {
            try {
                nombreArchivo = "";
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex5) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex5);
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
            catch (SecurityException ex6) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex6);
                Logger.getLogger("globalApp").info(ex6.getMessage());
            }
            catch (SystemException ex7) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", (Throwable)ex7);
                Logger.getLogger("globalApp").info(ex7.getMessage());
            }
        }
        return registros;
    }
    
    @Override
    public Integer cargarArchivoSoloDiario(final Integer codigoCajero, final Calendar fecha, String nombreArchivo) {
        Integer registros = -1;
        try {
            this.utx.begin();
            registros = this.diarioElectronicoServicio.cargarArchivoSoloDiario(codigoCajero, fecha, nombreArchivo);
            this.utx.commit();
        }
        catch (FileNotFoundException ex) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            Logger.getLogger("globalApp").info("No existe el archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha " + fecha.toString() + " " + ex.getMessage());
        }
        catch (EntityServicioExcepcion ex2) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex2);
            Logger.getLogger("globalApp").info("Error al grabar los datos  archivo Diario Electronico para codigoCajero " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex2.getMessage());
        }
        catch (IllegalArgumentException ex3) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex3);
            Logger.getLogger("globalApp").info("Error al grabar los datos archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex3.getMessage());
        }
        catch (Exception ex4) {
            this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex4);
            Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario Electronico" + ex4.getMessage());
            registros = -1;
        }
        finally {
            try {
                nombreArchivo = "";
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex5) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex5);
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
            catch (SecurityException ex6) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex6);
                Logger.getLogger("globalApp").info(ex6.getMessage());
            }
            catch (SystemException ex7) {
                this.diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", (Throwable)ex7);
                Logger.getLogger("globalApp").info(ex7.getMessage());
            }
        }
        return registros;
    }
}
