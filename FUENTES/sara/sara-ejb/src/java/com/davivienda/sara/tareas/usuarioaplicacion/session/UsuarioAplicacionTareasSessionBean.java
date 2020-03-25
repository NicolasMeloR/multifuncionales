// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tareas.usuarioaplicacion.session;

import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.transaction.SystemException;
import java.util.logging.Logger;
import com.davivienda.utilidades.SaraUtil;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tareas.usuarioaplicacion.servicio.UsuarioAplicacionTareasServicio;
import com.davivienda.sara.tablas.confaccesoaplicacion.servicio.ConfAccesoAplicacionServicio;
import com.davivienda.sara.tablas.usuarioaplicacion.servicio.UsuarioAplicacionServicio;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ UsuarioAplicacionTareasSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class UsuarioAplicacionTareasSessionBean implements UsuarioAplicacionTareasSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private UsuarioAplicacionServicio usuarioAplicacionServicio;
    private ConfAccesoAplicacionServicio confAccesoAplicacionServicio;
    private UsuarioAplicacionTareasServicio usuarioAplicacionTareasServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.usuarioAplicacionServicio = new UsuarioAplicacionServicio(this.em);
        this.confAccesoAplicacionServicio = new ConfAccesoAplicacionServicio(this.em);
        this.usuarioAplicacionTareasServicio = new UsuarioAplicacionTareasServicio(this.em);
    }
    
    @Override
    public void guardarRegTareasAdminUsuario(String usuario, final long servicio, final short borratodousuario, final String usuarioSession, final String nombreUsuario, final Date fecha) throws EntityServicioExcepcion, IllegalArgumentException {
        try {
            this.utx.begin();
            usuario = SaraUtil.stripXSS(usuario, "[^\\dA-Za-z\\.\\-\\s ]");
            this.usuarioAplicacionTareasServicio.guardarRegTareasAdminUsuario(usuario, servicio, borratodousuario, usuarioSession, nombreUsuario, fecha);
            this.utx.commit();
        }
        catch (IllegalArgumentException ex) {
            Logger.getLogger("globalApp").info("Error al grabar los datos en BorrarConfAccesoAplicacion para usuario: " + usuario + " servicio: " + servicio + " descripcion Error : " + ex.getMessage());
        }
        catch (Exception ex2) {
            Logger.getLogger("globalApp").info("Error al grabar los datos en BorrarConfAccesoAplicacion para usuario: " + usuario + " servicio: " + servicio + " descripcion Error : " + ex2.getMessage());
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
        }
    }
    
    @Override
    public void addBorrarRegAccesoUsuarioDesdeApp() throws EntityServicioExcepcion, IllegalArgumentException {
        try {
            this.utx.begin();
            this.usuarioAplicacionTareasServicio.addBorrarRegAccesoUsuarioDesdeApp();
            this.utx.commit();
        }
        catch (IllegalArgumentException ex) {
            Logger.getLogger("globalApp").info("Error al leer los datos en addBorrarRegAccesoUsuarioDesdeApp para usuario:  descripcion Error : " + ex.getMessage());
        }
        catch (Exception ex2) {
            Logger.getLogger("globalApp").info("Error al leer los datos en addBorrarRegAccesoUsuarioDesdeApp para usuario:  descripcion Error : " + ex2.getMessage());
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
        }
    }
    
    @Override
    public void crearActualizarAccesoUsuario(final ConfAccesoAplicacion pEntity) throws EntityServicioExcepcion, IllegalArgumentException {
        try {
            this.utx.begin();
            this.usuarioAplicacionTareasServicio.crearActualizarAccesoUsuario(pEntity);
            this.utx.commit();
        }
        catch (IllegalArgumentException ex) {
            Logger.getLogger("globalApp").info("Error al leer los datos en crearActualizarAccesoUsuario para usuario:  descripcion Error : " + ex.getMessage());
        }
        catch (Exception ex2) {
            Logger.getLogger("globalApp").info("Error al leer los datos en crearActualizarAccesoUsuario para usuario:  descripcion Error : " + ex2.getMessage());
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
        }
    }
}
