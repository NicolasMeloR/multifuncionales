package com.davivienda.sara.tareas.usuarioaplicacion.servicio;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.TareaBdConfAccesoAplicacion;
import com.davivienda.sara.entitys.TareasadminAccesoUsuario;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacionPK;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.tablas.confaccesoaplicacion.servicio.ConfAccesoAplicacionServicio;
import com.davivienda.sara.tablas.tareasadminaccesousuario.servicio.TareasadminAccesoUsuarioServicio;
import com.davivienda.sara.tablas.usuarioaplicacion.servicio.UsuarioAplicacionServicio;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import java.util.Date;
import javax.persistence.EntityManager;

/**
 * EdcMultifuncionalServicio Versión : 1.0
 *
 * @author P-MDRUIZ Davivienda 2011
 */
public class UsuarioAplicacionTareasServicio extends BaseServicio {

    TareasadminAccesoUsuarioServicio tareasadminAccesoUsuarioServicio;
    ConfAccesoAplicacionServicio confAccesoAplicacionServicio;
    UsuarioAplicacionServicio usuarioAplicacionServicio;

    public UsuarioAplicacionTareasServicio(EntityManager em) {
        super(em);
        tareasadminAccesoUsuarioServicio = new TareasadminAccesoUsuarioServicio(em);
        confAccesoAplicacionServicio = new ConfAccesoAplicacionServicio(em);
        usuarioAplicacionServicio = new UsuarioAplicacionServicio(em);
    }

    public void guardarRegTareasAdminUsuario(String usuario, long servicio, short borratodousuario, String usuarioSession, String nombreUsuario, Date fecha) throws EntityServicioExcepcion, IllegalArgumentException {

        usuario = SaraUtil.stripXSS(usuario, Constantes.REGEX_ACEPTAR_DEFAULT);
        TareasadminAccesoUsuario tareasAdminAcces = new TareasadminAccesoUsuario();

        tareasAdminAcces.setServicio(servicio);
        tareasAdminAcces.setUsuario(usuario);
        tareasAdminAcces.setFecha(fecha);
        tareasAdminAcces.setTareaadmin(borratodousuario);
        tareasAdminAcces.setUsuariosession(usuarioSession);
        tareasAdminAcces.setNombreusuario(nombreUsuario);

        try {

            tareasadminAccesoUsuarioServicio.adicionar(tareasAdminAcces);
            tareasadminAccesoUsuarioServicio.actualizarPersistencia();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error cargando en TareasadminAccesoUsuario registro datos   usuario: " + usuario + " y  servicio: " + servicio + " descripcion Error : " + ex.getMessage());
        }

    }

    public void addBorrarRegAccesoUsuarioDesdeApp() throws EntityServicioExcepcion, IllegalArgumentException {
        try {
            TareasadminAccesoUsuario tareasadminAccesoUsuario = tareasadminAccesoUsuarioServicio.obtenerTareaAdmAccesoUsuarioByMax();
            java.util.logging.Logger.getLogger("globalApp").info("TareasadminAccesoUsuario consultado - id: :" + tareasadminAccesoUsuario.getIdtareaaccesusu());

            String usuario = tareasadminAccesoUsuario.getUsuario();
            Long servicio = tareasadminAccesoUsuario.getServicio();
                
            if (tareasadminAccesoUsuario.getTareaadmin() == TareaBdConfAccesoAplicacion.BORRAREGCONFACCESO.getTareaBD().intValue()) {
                confAccesoAplicacionServicio.borrarPorUsuarioServicio(usuario, servicio);  
                confAccesoAplicacionServicio.actualizarPersistencia();
            } else if (tareasadminAccesoUsuario.getTareaadmin() == TareaBdConfAccesoAplicacion.BORRATODOSUSUARIO.getTareaBD().intValue()) {
                confAccesoAplicacionServicio.borrarPorUsuario(usuario);
                confAccesoAplicacionServicio.actualizarPersistencia();
                usuarioAplicacionServicio.borrarPorUsuario(usuario);
                usuarioAplicacionServicio.actualizarPersistencia();
            } else if (tareasadminAccesoUsuario.getTareaadmin() == TareaBdConfAccesoAplicacion.INSERTAREGUSUARIO.getTareaBD().intValue()) {

                UsuarioAplicacion pEntity = new UsuarioAplicacion();
                pEntity.setUsuario(usuario);
                pEntity.setNombre(tareasadminAccesoUsuario.getNombreusuario());
                pEntity.setDireccionIp("*");
                pEntity.setToken("*");
                pEntity.setClaveEstatica("*");
                pEntity.setVersion(1L);
                pEntity.setSistema(Short.valueOf("0"));
                pEntity.setNormal(Short.valueOf("1"));
                pEntity.setAuditoria(Short.valueOf("0"));
               
                usuarioAplicacionServicio.adicionar(pEntity);
                usuarioAplicacionServicio.actualizarPersistencia();
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error cargando en ObtenerMaxRegTareasAdminUsuario registro datos   usuario: " + " descripcion Error : " + ex.getMessage());
        }
    }
    
    
    public void crearActualizarAccesoUsuario(ConfAccesoAplicacion pEntity) throws EntityServicioExcepcion, IllegalArgumentException {
        try {
            TareasadminAccesoUsuario tareasadminAccesoUsuario = tareasadminAccesoUsuarioServicio.obtenerTareaAdmAccesoUsuarioByMax();
            java.util.logging.Logger.getLogger("globalApp").info("TareasadminAccesoUsuario consultado - id: :" + tareasadminAccesoUsuario.getIdtareaaccesusu());

            String usuario = tareasadminAccesoUsuario.getUsuario();
            Long servicio = tareasadminAccesoUsuario.getServicio();
                
            if (tareasadminAccesoUsuario.getTareaadmin() == TareaBdConfAccesoAplicacion.INSERTAREGCONFACCESO.getTareaBD().intValue()) {
                
                pEntity.setVersion(1L);
                
                ConfAccesoAplicacion conAcceso = confAccesoAplicacionServicio.buscar(pEntity.getConfAccesoAplicacionPK());
                
                java.util.logging.Logger.getLogger("globalApp").info("TareasadminAccesoUsuario crearActualizarAccesoUsuario CONSULTA BD: " + conAcceso);
                if(null == conAcceso){
                    java.util.logging.Logger.getLogger("globalApp").info("TareasadminAccesoUsuario crearActualizarAccesoUsuario NUEVO ");
                    pEntity.setVersion(1L);
                    confAccesoAplicacionServicio.adicionar(pEntity);
                }else{
                    java.util.logging.Logger.getLogger("globalApp").info("TareasadminAccesoUsuario crearActualizarAccesoUsuario MODIFICAR ");
                    conAcceso.setActualizar(pEntity.getActualizar());
                    conAcceso.setAdministrar(pEntity.getAdministrar());
                    conAcceso.setBorrar(pEntity.getBorrar());
                    conAcceso.setConsultar(pEntity.getConsultar());
                    conAcceso.setCrear(pEntity.getCrear());
                    conAcceso.setEjecutar(pEntity.getEjecutar());      
                    confAccesoAplicacionServicio.actualizar(conAcceso);
                }
                java.util.logging.Logger.getLogger("globalApp").info("TareasadminAccesoUsuario crearActualizarAccesoUsuario conAcceso : " + conAcceso);
                java.util.logging.Logger.getLogger("globalApp").info("TareasadminAccesoUsuario crearActualizarAccesoUsuario pEntity : " + pEntity);
                confAccesoAplicacionServicio.actualizarPersistencia();
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error cargando en ObtenerMaxRegTareasAdminUsuario registro datos   usuario: " + " descripcion Error : " + ex.getMessage());
        }
    }

}

