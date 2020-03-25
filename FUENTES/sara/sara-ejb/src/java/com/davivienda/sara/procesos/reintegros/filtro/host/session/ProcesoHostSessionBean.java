// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.reintegros.filtro.host.session;

import javax.transaction.SystemException;
import java.io.FileNotFoundException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import com.davivienda.sara.procesos.reintegros.filtro.host.servicio.ProcesadorHostServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ ProcesoHostSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcesoHostSessionBean implements ProcesoHostSessionLocal
{
    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;
    private ProcesadorHostServicio servicio;
    
    @PostConstruct
    public void postConstructor() {
        this.servicio = new ProcesadorHostServicio(this.em);
    }
    
    @Override
    public Integer CargarArchivo(final Calendar fecha) throws EntityServicioExcepcion, FileNotFoundException, IllegalArgumentException {
        Integer regHost = 0;
        int regArchivo = 0;
        int i = 0;
        try {
            for (regArchivo = this.servicio.cuentaRegistros(fecha), i = 0; i == 0 || i <= regArchivo; i += 20000) {
                regHost += this.CargarArchivoxFilas(fecha, i);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(ProcesoHostSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return regHost;
    }
    
    public Integer CargarArchivoxFilas(final Calendar fecha, final int numRegistros) throws EntityServicioExcepcion, FileNotFoundException, IllegalArgumentException {
        Integer registros = -1;
        try {
            this.utx.begin();
            registros = this.servicio.cargarArchivoHost(fecha, numRegistros);
            this.utx.commit();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger("globalApp").info("No existe el archivo host   ATM con fecha " + fecha.toString() + " " + ex.getMessage());
        }
        catch (EntityServicioExcepcion ex2) {
            Logger.getLogger("globalApp").info("Error al grabar los datos  archivo host   ATM con fecha " + fecha.toString() + " " + ex2.getMessage());
        }
        catch (IllegalArgumentException ex3) {
            Logger.getLogger("globalApp").info("Error al grabar los datos archivo host   ATM con fecha " + fecha.toString() + " " + ex3.getMessage());
        }
        catch (Exception ex4) {
            Logger.getLogger("globalApp").info("No se grabaran los registros procesados de archivo host   ATM con fecha " + fecha.toString() + " " + ex4.getMessage());
        }
        finally {
            try {
                if (this.utx.getStatus() != 6) {
                    Logger.getLogger("globalApp").info("No se grabaran los registros procesados se realiza rollback de cargue archivo host   ATM   con fecha " + fecha.toString() + " ");
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex5) {
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
            catch (SecurityException ex6) {
                Logger.getLogger("globalApp").info(ex6.getMessage());
            }
            catch (SystemException ex7) {
                Logger.getLogger("globalApp").info(ex7.getMessage());
            }
        }
        return registros;
    }
}
