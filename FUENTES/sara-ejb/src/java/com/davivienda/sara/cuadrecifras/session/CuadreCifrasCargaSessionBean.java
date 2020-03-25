package com.davivienda.sara.cuadrecifras.session;

import com.davivienda.sara.procesos.cuadrecifras.filtro.corte.servicio.ProcesadorCorteArchivoServicio;
import com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.servicio.ProcesadorTotalesEstacionArchivoServicio;
import com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.servicio.ProcesadorProvisionesArchivoServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.entitys.InformeDiferencias;
import com.davivienda.sara.entitys.Regcarguearchivo;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Local;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.SystemException;
import com.davivienda.sara.tablas.regcargue.servicio.RegCargueArchivoServicio;
import com.davivienda.sara.tablas.reintegros.servicio.InformeDiferenciasServicio;
import java.util.Date;
import java.util.logging.Level;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */

@Stateless
@Local(value = CuadreCifrasCargasSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CuadreCifrasCargaSessionBean implements CuadreCifrasCargasSessionLocal {

    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private ProcesadorCorteArchivoServicio corteServicio;
    private ProcesadorTotalesEstacionArchivoServicio totalesEstacionServicio;
    private ProcesadorProvisionesArchivoServicio provisionesServicio;
    private RegCargueArchivoServicio regCargueArchivoServicio;
    public String msgRegError;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {
        corteServicio = new ProcesadorCorteArchivoServicio(em);
        totalesEstacionServicio = new ProcesadorTotalesEstacionArchivoServicio(em);
        provisionesServicio = new ProcesadorProvisionesArchivoServicio(em);
        regCargueArchivoServicio = new RegCargueArchivoServicio(em);

        msgRegError = "";
    }

    /**
     * Carga un archivo de Corte diario en la tabla BilletajeCajero y
     * movimientoCuadre
     *
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarArchivoCorte(Calendar fecha, boolean cargueautomatico) {
        
        corteServicio.getConfigApp().loggerApp.info("CuadreCifrasCargaSessionBean  -- cargarArchivoCorte");
        Integer registros = -1;
        String msgError = "";
        String strFecha = com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
        try {

            // guardarRegistroTxArchivo("cfamo001",cargueautomatico,fecha,"Cuadre");
            utx.begin();
            registros = corteServicio.cargarArchivoCorte(fecha);
            utx.commit();
        } catch (FileNotFoundException ex) {
            corteServicio.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            msgError = "No existe el archivo Corte para la fecha  " + strFecha + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        } catch (EntityServicioExcepcion ex) {
            corteServicio.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            msgError = "Error al grabar archivo Corte para la fecha  " + strFecha + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        } catch (IllegalArgumentException ex) {
            corteServicio.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            msgError = "Error al grabar archivo Corte para la fecha  " + strFecha + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        } catch (EJBTransactionRolledbackException ex) {
            corteServicio.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            msgError = "Error al grabar archivo Corte para la fecha  " + strFecha + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        } catch (EJBException ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + strFecha + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        } catch (Exception ex) {
            corteServicio.getConfigApp().loggerApp.log(Level.SEVERE, null, ex);
            msgError = "Error al grabar archivo Corte para la fecha  " + strFecha + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
            registros = -1;
        } finally {

//               try
//               {
//                    actualizarRegCargueArchivo("cfamo001",cargueautomatico,fecha,msgError);
//               }
//               catch (Exception ex) 
//               {
//                    msgError="Error al actualizar registro RegCargueArchivo para la fecha  " +  fecha.toString()  + " " + ex.getMessage();
//                    corteServicio.getConfigApp().loggerApp.info(msgError);
//               }
        }
        return registros;

    }

    /**
     * Carga un archivo de Corte diario en la tabla TotalesEstacion y
     * movimientoCuadre
     *
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarArchivoTotalesEgresos(Calendar fecha, boolean cargueautomatico) {
        Integer registros = -1;
        String msgError = "";
        try {
            utx.begin();
            registros = totalesEstacionServicio.cargarArchivoTotalesEstacion(fecha);
            utx.commit();
        } catch (FileNotFoundException ex) {
            msgError = "No existe el archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } catch (EntityServicioExcepcion ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } catch (IllegalArgumentException ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } catch (EJBTransactionRolledbackException ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } catch (EJBException ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } catch (Exception ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        }
        return registros;

    }

    /**
     * Carga un archivo de provisiones en la tabla ProvisionHost y
     * MovimientoCuadre
     *
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */

    public Integer cargarArchivoProvisiones(Calendar fecha, boolean cargueautomatico) {
        Integer registros = -1;
        String msgError = "";
        try {
            utx.begin();
            registros = provisionesServicio.cargarArchivoProvisiones(fecha);
            utx.commit();
        } catch (FileNotFoundException ex) {
            msgError = "No existe el archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } catch (EntityServicioExcepcion ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } catch (IllegalArgumentException ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } catch (EJBTransactionRolledbackException ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } catch (EJBException ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } catch (Exception ex) {
            msgError = "Error al grabar archivo Corte para la fecha  " + fecha.toString() + " " + ex.getMessage();
            corteServicio.getConfigApp().loggerApp.info(msgError);
        } finally {

            try {

                //Status.STATUS_COMMITTED tiene valor 6 
                if (utx.getStatus() != 6) {
                    utx.rollback();
                }
                try {
                    actualizarRegCargueArchivo("cfamo001", cargueautomatico, fecha, msgError);
                } catch (Exception ex) {
                    msgError = "Error al actualizar registro RegCargueArchivo para la fecha  " + fecha.toString() + " " + ex.getMessage();
                    corteServicio.getConfigApp().loggerApp.info(msgError);
                }

            } catch (IllegalStateException ex1) {
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SecurityException ex1) {
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            } catch (SystemException ex1) {
                //Logger.getLogger(AdministradorProcesosSessionBean.class.getName()).log(Level.SEVERE, null, ex1);
                java.util.logging.Logger.getLogger("globalApp").info(ex1.getMessage());
            }
        }
        return registros;

    }

    private void actualizarRegCargueArchivo(String archivoTarea, boolean IndAuto, Calendar fechaTarea, String msgError) throws EntityServicioExcepcion {

        String strFechaTarea = "";
        strFechaTarea = com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");

        Regcarguearchivo edcCargue = regCargueArchivoServicio.buscarPorArchivoFecha(archivoTarea, strFechaTarea, IndAuto);
        if (edcCargue != null) {
            if (msgError.equals("")) {
                edcCargue.setEstadocarga(EstadoProceso.FINALIZADO.getEstado());
            } else {
                edcCargue.setEstadocarga(3);
                edcCargue.setDescripcionerror(msgError);
            }

            edcCargue.setFecha(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
            regCargueArchivoServicio.actualizar(edcCargue);
            regCargueArchivoServicio.actualizarPersistencia();

        }

    }

}
