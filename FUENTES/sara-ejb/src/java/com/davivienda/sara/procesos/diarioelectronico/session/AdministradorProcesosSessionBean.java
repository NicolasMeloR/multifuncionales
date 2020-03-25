package com.davivienda.sara.procesos.diarioelectronico.session;

import com.davivienda.sara.procesos.diarioelectronico.servicio.ProcesadorDiarioElectronicoServicio;
import java.util.Collection;
import java.util.Calendar;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.EdcCargue;
import javax.ejb.Local;
import javax.annotation.Resource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;
import com.davivienda.utilidades.conversion.FormatoFecha;

import java.io.FileNotFoundException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
//import javax.net.ssl.SSLEngineResult.Status;
import com.davivienda.sara.tablas.edccargue.session.EdcCargueSessionLocal;
import javax.ejb.EJB;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
@Stateless
@Local(value = AdministradorProcesosSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AdministradorProcesosSessionBean implements AdministradorProcesosSessionLocal {

    @PersistenceContext
    private EntityManager em;
    // private TransaccionTempServicio transaccionTempServicio;
    @Resource
    private UserTransaction utx;

    private ProcesadorDiarioElectronicoServicio diarioElectronicoServicio;

    //privete EdcCargueSessionBean dcCargueSessionBean
    @EJB
    private EdcCargueSessionLocal edcCargueSessionLocal;
    // private String nombreArchivo;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        diarioElectronicoServicio = new ProcesadorDiarioElectronicoServicio(em);
        // transaccionTempServicio = new TransaccionTempServicio(em);

        //  nombreArchivo="";
    }

    /**
     * Carga un archivo de Diario Electronico en la tabla DIARIOELECTRONICO y
     * TRANSACCION
     *
     * @param codigoCajero
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarArchivo(Integer codigoCajero, Calendar fecha, String nombreArchivo) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer registros = -1;
        try {
            utx.begin();

            registros = diarioElectronicoServicio.cargarArchivo(codigoCajero, fecha, nombreArchivo);
            utx.commit();
        } catch (FileNotFoundException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No existe el archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha " + fecha.toString() + " " + ex.getMessage());

        } catch (EntityServicioExcepcion ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos  archivo Diario Electronico para codigoCajero " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex.getMessage());
        } catch (Exception ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario Electronico" + ex.getMessage());
            registros = -1;
        } finally {

            try {
                nombreArchivo = "";
                //Status.STATUS_COMMITTED tiene valor 6 
                if (utx.getStatus() != 6) {
                    utx.rollback();
                }

            } catch (IllegalStateException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SecurityException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SystemException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }

        }

        return registros;

    }

    /**
     * Carga un archivo de Diario Electronico en la tabla DIARIOELECTRONICO
     * solamente
     *
     * @param codigoCajero
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarArchivoCiclo(Integer codigoCajero, Calendar fecha, String nombreArchivo) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer registros = -1;
        try {
            utx.begin();

            registros = diarioElectronicoServicio.cargarArchivoCicloNuevo(codigoCajero, fecha, nombreArchivo);
            utx.commit();
        } catch (FileNotFoundException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No existe el archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha " + fecha.toString() + " " + ex.getMessage());

        } catch (EntityServicioExcepcion ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos  archivo Diario Electronico para codigoCajero " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex.getMessage());
        } catch (Exception ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario Electronico" + ex.getMessage());
        } finally {

            try {
                nombreArchivo = "";
                //Status.STATUS_COMMITTED tiene valor 6 
                if (utx.getStatus() != 6) {
                    utx.rollback();
                }

            } catch (IllegalStateException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SecurityException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SystemException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }

        }

        return registros;

    }

    /**
     * Carga un archivo de Diario Electronico en la tabla DIARIOELECTRONICO
     * solamente
     *
     * @param codigoCajero
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public void cargarTransaccionesCiclo(Integer ciclo) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        try {
            utx.begin();

            diarioElectronicoServicio.cargarTransaccionesCiclo(ciclo);
            utx.commit();

        } catch (IllegalArgumentException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos  de transaccion para el ciclo " + ciclo.toString() + " " + ex.getMessage());
        } catch (Exception ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No se grabaran los registros de la tabla transaccion temp en transaccion " + ex.getMessage());
        } finally {

            try {

                if (utx.getStatus() != 6) {
                    utx.rollback();
                }

            } catch (IllegalStateException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SecurityException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SystemException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }

        }

    }

    /**
     * Carga el ciclo del dia por un store Procedure
     */
//public void cargarCicloTempXStoreP() throws EntityServicioExcepcion
//{
//    
//    transaccionTempServicio.cargarCicloTempXStoreP();
//
//}
//      
    /**
     * Carga el ciclo de cajeros diario
     *
     * @param fecha
     * @return
     */
    public Integer cargarCiclo(Calendar fecha) {
        if (fecha == null || fecha.after(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha válida");

        }
        Calendar fechaCiclo;
        String nombreArchivo;
        Integer regsArchivo = 0;
        Integer regsProceso = 0;
        Integer cajProceso = 0;

        Integer ciclo = 0;

        ciclo = com.davivienda.utilidades.conversion.Cadena.aInteger(com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.CICLO_EDC));
        try {

            Collection<EdcCargue> edcCargueCiclo = edcCargueSessionLocal.getCicloSnError(ciclo);
            // Collection<Cajero> cajeros = cajeroServicio.getTodos();

            java.util.logging.Logger.getLogger("globalApp").info("Se inicia el proceso del ciclo " + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD));

            for (EdcCargue edcCargue : edcCargueCiclo) {

                nombreArchivo = edcCargue.getNombrearchivo();
                fechaCiclo = com.davivienda.utilidades.edc.Edc.getFechaCiclo(nombreArchivo.substring(4, 8), fecha);

                //modificado Alvaro Garcia 25 enero
                regsArchivo = this.cargarArchivoCiclo(edcCargue.getCodigoCajero(), fechaCiclo, nombreArchivo);
                if (regsArchivo > 0) {
                    cajProceso++;
                    regsProceso = regsProceso + regsArchivo;
                }

            }
        } catch (Exception ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No se pudo obtener EdcCargue" + ex.getMessage());

        }

        java.util.logging.Logger.getLogger("globalApp").info("Fin del proceso de  Diario Electronico  para el ciclo " + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD) + ". Cajeros procesados : " + cajProceso);
        return regsProceso;
    }

    public Integer cargarCicloTemp(Calendar fecha) {
        if (fecha == null || fecha.after(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha válida");

        }
        Calendar fechaCiclo;
        String nombreArchivo;
        Integer regsArchivo = 0;
        Integer regsProceso = 0;
        Integer cajProceso = 0;

        Integer ciclo = 0;

        ciclo = com.davivienda.utilidades.conversion.Cadena.aInteger(com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.CICLO_EDC));
        try {

            //modificado Alvaro enero 25      
//        borrarDiarioElectronicoTemp();     
//         borrarTransaccionTemp();
            Collection<EdcCargue> edcCargueCiclo = edcCargueSessionLocal.getCicloSnError(ciclo);
            // Collection<Cajero> cajeros = cajeroServicio.getTodos();

            java.util.logging.Logger.getLogger("globalApp").info("Se inicia el proceso del ciclo " + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD));

            for (EdcCargue edcCargue : edcCargueCiclo) {

                nombreArchivo = edcCargue.getNombrearchivo();
                fechaCiclo = com.davivienda.utilidades.edc.Edc.getFechaCiclo(nombreArchivo.substring(4, 8), fecha);
                regsArchivo = this.cargarArchivoTemp(edcCargue.getCodigoCajero(), fechaCiclo, nombreArchivo);
                if (regsArchivo > 0) {
                    cajProceso++;
                    regsProceso = regsProceso + regsArchivo;
                }

            }
        } catch (Exception ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No se pudo obtener EdcCargue" + ex.getMessage());

        }

        java.util.logging.Logger.getLogger("globalApp").info("Fin del proceso de  Diario Electronico  para el ciclo " + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD) + ". Cajeros procesados : " + cajProceso);
        return regsProceso;
    }

    public void borrarDiarioElectronicoTemp() {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer registros = -1;
        try {
            utx.begin();

            diarioElectronicoServicio.borrarDiarioelectronicoTemp();
            utx.commit();
        } catch (EntityServicioExcepcion ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al borrar los datos  Diario ElectronicoTemp para codigoCajero" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al borrar los datos  Diario ElectronicoTemp " + ex.getMessage());
        } catch (Exception ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario ElectronicoTemp" + ex.getMessage());
        } finally {

            try {

                //Status.STATUS_COMMITTED tiene valor 6 
                if (utx.getStatus() != 6) {
                    utx.rollback();
                }

            } catch (IllegalStateException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SecurityException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SystemException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }

        }

    }

    public void borrarTransaccionTemp() {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer registros = -1;
        try {
            utx.begin();

            diarioElectronicoServicio.borrarTransaccionTemp();
            utx.commit();
        } catch (EntityServicioExcepcion ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al borrar los datos   TransaccionTemp para codigoCajero" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al borrar los datos   TransaccionTemp " + ex.getMessage());
        } catch (Exception ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo  TransaccionTemp" + ex.getMessage());
        } finally {

            try {

                //Status.STATUS_COMMITTED tiene valor 6 
                if (utx.getStatus() != 6) {
                    utx.rollback();
                }

            } catch (IllegalStateException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SecurityException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SystemException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }

        }

    }

    /**
     * Carga un archivo de Diario Electronico Temp en la tabla TRANSACCIONTemp
     *
     * @param codigoCajero
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarArchivoTemp(Integer codigoCajero, Calendar fecha, String nombreArchivo) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer registros = -1;
        try {
            utx.begin();

            registros = diarioElectronicoServicio.cargarArchivoTemp(codigoCajero, fecha, nombreArchivo);
            utx.commit();
        } catch (FileNotFoundException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No existe el archivo Diario Electronico Temp para codigoCajero  " + codigoCajero.toString() + " con fecha " + fecha.toString() + " " + ex.getMessage());
        } catch (EntityServicioExcepcion ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos  archivo Diario ElectronicoTemp para codigoCajero " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos archivo Diario ElectronicoTemp para codigoCajero  " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex.getMessage());
        } catch (Exception ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario ElectronicoTemp" + ex.getMessage());
        } finally {

            try {
                nombreArchivo = "";
                //Status.STATUS_COMMITTED tiene valor 6 
                if (utx.getStatus() != 6) {
                    utx.rollback();
                }

            } catch (IllegalStateException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SecurityException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SystemException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }

        }

        return registros;

    }

    /**
     * Carga un archivo de Diario Electronico Temp en la tabla DIARIOELECTRONICO
     * Temp
     *
     * @param codigoCajero
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarDiarioElectronicoTemp(Integer codigoCajero, Calendar fecha, String nombreArchivo) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer registros = -1;
        try {
            utx.begin();
            registros = diarioElectronicoServicio.cargarDiarioElectronicoTemp(codigoCajero, fecha, nombreArchivo);
            utx.commit();
        } catch (FileNotFoundException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No existe el archivo Diario Electronico Temp para codigoCajero  " + codigoCajero.toString() + " con fecha " + fecha.toString() + " " + ex.getMessage());
        } catch (EntityServicioExcepcion ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos  archivo Diario ElectronicoTemp para codigoCajero " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos archivo Diario ElectronicoTemp para codigoCajero  " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex.getMessage());
        } catch (Exception ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario ElectronicoTemp" + ex.getMessage());
        } finally {

            try {
                nombreArchivo = "";
                //Status.STATUS_COMMITTED tiene valor 6 
                if (utx.getStatus() != 6) {
                    utx.rollback();
                }

            } catch (IllegalStateException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SecurityException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SystemException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }

        }

        return registros;

    }

    /**
     * Carga un archivo de Diario Electronico en la tabla DIARIOELECTRONICO
     *
     * @param codigoCajero
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarArchivoSoloDiario(Integer codigoCajero, Calendar fecha, String nombreArchivo) {//throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer registros = -1;
        try {
            utx.begin();

            registros = diarioElectronicoServicio.cargarArchivoSoloDiario(codigoCajero, fecha, nombreArchivo);
            utx.commit();
        } catch (FileNotFoundException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No existe el archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha " + fecha.toString() + " " + ex.getMessage());
        } catch (EntityServicioExcepcion ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos  archivo Diario Electronico para codigoCajero " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos archivo Diario Electronico para codigoCajero  " + codigoCajero.toString() + " con fecha  " + fecha.toString() + " " + ex.getMessage());
        } catch (Exception ex) {
            diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex);
            java.util.logging.Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo Diario Electronico" + ex.getMessage());
            registros = -1;
        } finally {

            try {
                nombreArchivo = "";
                //Status.STATUS_COMMITTED tiene valor 6
                if (utx.getStatus() != 6) {
                    utx.rollback();
                }

            } catch (IllegalStateException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SecurityException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SystemException ex1) {
                diarioElectronicoServicio.getConfigApp().loggerApp.log(Level.SEVERE, "", ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }
        }

        return registros;

    }

}
