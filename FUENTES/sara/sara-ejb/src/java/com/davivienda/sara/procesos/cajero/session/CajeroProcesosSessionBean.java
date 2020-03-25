// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cajero.session;

import javax.transaction.SystemException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Logger;
import com.davivienda.sara.entitys.Cajero;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ CajeroProcesosSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class CajeroProcesosSessionBean implements CajeroProcesosSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private CajeroServicio cajeroServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.cajeroServicio = new CajeroServicio(this.em);
    }
    
    @Override
    public Cajero actualizarCajero(final Cajero objeto) throws EntityServicioExcepcion {
        Cajero objetoActual = null;
        String srtError = "";
        try {
            this.utx.begin();
            objetoActual = this.cajeroServicio.actualizar(objeto);
            this.utx.commit();
        }
        catch (EntityServicioExcepcion ex) {
            srtError = ex.getMessage();
            Logger.getLogger("globalApp").info("Error al actualizar  los datos  de el cajero del tipo " + ex.getMessage());
        }
        catch (IllegalArgumentException ex2) {
            Logger.getLogger("globalApp").info("Error al actualizar  los datos  de el cajero del tipo " + ex2.getMessage());
        }
        catch (Exception ex3) {
            Logger.getLogger("globalApp").info("Error al actualizar  los datos  de el cajero del tipo " + ex3.getMessage());
        }
        finally {
            try {
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex4) {
                Logger.getLogger("globalApp").info(ex4.getMessage());
            }
            catch (SecurityException ex5) {
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
            catch (SystemException ex6) {
                Logger.getLogger("globalApp").info(ex6.getMessage());
            }
        }
        if (!srtError.equals("")) {
            throw new EntityServicioExcepcion(srtError);
        }
        return objetoActual;
    }
}
