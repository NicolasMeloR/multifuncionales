package com.davivienda.sara.tareas.usuarioaplicacion.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;

import com.davivienda.sara.tablas.confaccesoaplicacion.servicio.ConfAccesoAplicacionServicio;
import com.davivienda.sara.tablas.usuarioaplicacion.servicio.UsuarioAplicacionServicio;
import com.davivienda.sara.tareas.usuarioaplicacion.servicio.UsuarioAplicacionTareasServicio;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * ConfAccesoAplicacionSessionBean - 19/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
@Stateless
@Local(value = UsuarioAplicacionTareasSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class UsuarioAplicacionTareasSessionBean implements UsuarioAplicacionTareasSessionLocal {

    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private UsuarioAplicacionServicio usuarioAplicacionServicio;
    private ConfAccesoAplicacionServicio confAccesoAplicacionServicio;
    private UsuarioAplicacionTareasServicio usuarioAplicacionTareasServicio;

    @PostConstruct
    public void postConstructor() {
        //super.servicio = new ConfAccesoAplicacionServicio(em);
        usuarioAplicacionServicio = new UsuarioAplicacionServicio(em);
        confAccesoAplicacionServicio = new ConfAccesoAplicacionServicio(em);
        usuarioAplicacionTareasServicio = new UsuarioAplicacionTareasServicio(em);

    }

    public void guardarRegTareasAdminUsuario(String usuario, long servicio, short borratodousuario, String usuarioSession, String nombreUsuario, Date fecha) throws EntityServicioExcepcion, IllegalArgumentException {
        try {
            utx.begin();
            usuario = SaraUtil.stripXSS(usuario, Constantes.REGEX_ACEPTAR_DEFAULT);
            usuarioAplicacionTareasServicio.guardarRegTareasAdminUsuario(usuario, servicio, borratodousuario, usuarioSession, nombreUsuario, fecha);
            utx.commit();
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en BorrarConfAccesoAplicacion para usuario: " + usuario + " servicio: " + servicio + " descripcion Error : " + ex.getMessage());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en BorrarConfAccesoAplicacion para usuario: " + usuario + " servicio: " + servicio + " descripcion Error : " + ex.getMessage());
        } finally {
            try {
                //Status.STATUS_COMMITTED tiene valor 6 
                if (utx.getStatus() != 6) {
                    utx.rollback();
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

    }

    public void addBorrarRegAccesoUsuarioDesdeApp() throws EntityServicioExcepcion, IllegalArgumentException {
        try {
            utx.begin();
            usuarioAplicacionTareasServicio.addBorrarRegAccesoUsuarioDesdeApp();
            utx.commit();
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error al leer los datos en addBorrarRegAccesoUsuarioDesdeApp para usuario: " +" descripcion Error : " + ex.getMessage());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error al leer los datos en addBorrarRegAccesoUsuarioDesdeApp para usuario: " +" descripcion Error : " + ex.getMessage());
        } finally {
            try {
                //Status.STATUS_COMMITTED tiene valor 6 
                if (utx.getStatus() != 6) {
                    utx.rollback();
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

    }
    
    public void crearActualizarAccesoUsuario(ConfAccesoAplicacion pEntity) throws EntityServicioExcepcion, IllegalArgumentException {
        try {
            utx.begin();
            usuarioAplicacionTareasServicio.crearActualizarAccesoUsuario(pEntity);
            utx.commit();
        } catch (IllegalArgumentException ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error al leer los datos en crearActualizarAccesoUsuario para usuario: " +" descripcion Error : " + ex.getMessage());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error al leer los datos en crearActualizarAccesoUsuario para usuario: " +" descripcion Error : " + ex.getMessage());
        } finally {
            try {
                //Status.STATUS_COMMITTED tiene valor 6 
                if (utx.getStatus() != 6) {
                    utx.rollback();
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

    }

}
