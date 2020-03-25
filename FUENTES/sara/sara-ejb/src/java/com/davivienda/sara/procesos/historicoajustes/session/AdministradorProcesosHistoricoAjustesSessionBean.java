// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.historicoajustes.session;

import javax.transaction.SystemException;
import java.util.logging.Logger;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import com.davivienda.sara.procesos.historicoajustes.servicio.AdministradorProcesosHistoricoAjustesServicio;
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
@Local({ AdministradorProcesosHistoricoAjustesSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class AdministradorProcesosHistoricoAjustesSessionBean implements AdministradorProcesosHistoricoAjustesSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private CajeroServicio cajeroServicio;
    private AdministradorProcesosHistoricoAjustesServicio administradorProcesosHistoricoAjustesServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.administradorProcesosHistoricoAjustesServicio = new AdministradorProcesosHistoricoAjustesServicio(this.em);
        this.cajeroServicio = new CajeroServicio(this.em);
    }
    
    public Integer actualizarEdcCargue(final ArrayList nombreArchivos, final String nombreZip, final Calendar fecha) {
        final Integer registros = -1;
        return registros;
    }
    
    @Override
    public void guardarHistoricoAjustes(final String usuario, final Integer codigoCajero, final Integer codigoOcca, final String tipoAjuste, final Date fecha, final BigDecimal valor, final String talon, final String error, final String descripcionError) {
        try {
            this.utx.begin();
            this.administradorProcesosHistoricoAjustesServicio.guardarHistoricoAjustes(usuario, codigoCajero, codigoOcca, tipoAjuste, fecha, valor, talon, error, descripcionError);
            this.utx.commit();
        }
        catch (IllegalArgumentException ex) {
            this.administradorProcesosHistoricoAjustesServicio.getConfigApp().loggerApp.info("Error al grabar los datos en HistoricoAjustes para codigoCajero " + codigoCajero + " " + ex.getMessage());
        }
        catch (Exception ex2) {
            Logger.getLogger("globalApp").info("Error cargando en HistoricoAjustes registro datos codigoCajero  :" + codigoCajero + " descripcion Error : " + ex2.getMessage());
        }
        finally {
            try {
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex3) {
                Logger.getLogger("globalApp").info(ex3.getMessage());
            }
            catch (SecurityException ex4) {
                Logger.getLogger("globalApp").info(ex4.getMessage());
            }
            catch (SystemException ex5) {
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
            this.administradorProcesosHistoricoAjustesServicio.getConfigApp().loggerApp.info("Fin del proceso de grabar los datos en Historico Ajustes   para el  codigoCajero" + codigoCajero);
        }
    }
}
