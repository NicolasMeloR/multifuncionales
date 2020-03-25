// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tareas.usuarioaplicacion.servicio;

import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.constantes.TareaBdConfAccesoAplicacion;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Logger;
import com.davivienda.sara.entitys.TareasadminAccesoUsuario;
import com.davivienda.utilidades.SaraUtil;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.usuarioaplicacion.servicio.UsuarioAplicacionServicio;
import com.davivienda.sara.tablas.confaccesoaplicacion.servicio.ConfAccesoAplicacionServicio;
import com.davivienda.sara.tablas.tareasadminaccesousuario.servicio.TareasadminAccesoUsuarioServicio;
import com.davivienda.sara.base.BaseServicio;

public class UsuarioAplicacionTareasServicio extends BaseServicio
{
    TareasadminAccesoUsuarioServicio tareasadminAccesoUsuarioServicio;
    ConfAccesoAplicacionServicio confAccesoAplicacionServicio;
    UsuarioAplicacionServicio usuarioAplicacionServicio;
    
    public UsuarioAplicacionTareasServicio(final EntityManager em) {
        super(em);
        this.tareasadminAccesoUsuarioServicio = new TareasadminAccesoUsuarioServicio(em);
        this.confAccesoAplicacionServicio = new ConfAccesoAplicacionServicio(em);
        this.usuarioAplicacionServicio = new UsuarioAplicacionServicio(em);
    }
    
    public void guardarRegTareasAdminUsuario(String usuario, final long servicio, final short borratodousuario, final String usuarioSession, final String nombreUsuario, final Date fecha) throws EntityServicioExcepcion, IllegalArgumentException {
        usuario = SaraUtil.stripXSS(usuario, "[^\\dA-Za-z\\.\\-\\s ]");
        final TareasadminAccesoUsuario tareasAdminAcces = new TareasadminAccesoUsuario();
        tareasAdminAcces.setServicio(servicio);
        tareasAdminAcces.setUsuario(usuario);
        tareasAdminAcces.setFecha(fecha);
        tareasAdminAcces.setTareaadmin(borratodousuario);
        tareasAdminAcces.setUsuariosession(usuarioSession);
        tareasAdminAcces.setNombreusuario(nombreUsuario);
        try {
            this.tareasadminAccesoUsuarioServicio.adicionar(tareasAdminAcces);
            this.tareasadminAccesoUsuarioServicio.actualizarPersistencia();
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error cargando en TareasadminAccesoUsuario registro datos   usuario: " + usuario + " y  servicio: " + servicio + " descripcion Error : " + ex.getMessage());
        }
    }
    
    public void addBorrarRegAccesoUsuarioDesdeApp() throws EntityServicioExcepcion, IllegalArgumentException {
        try {
            final TareasadminAccesoUsuario tareasadminAccesoUsuario = this.tareasadminAccesoUsuarioServicio.obtenerTareaAdmAccesoUsuarioByMax();
            Logger.getLogger("globalApp").info("TareasadminAccesoUsuario consultado - id: :" + tareasadminAccesoUsuario.getIdtareaaccesusu());
            final String usuario = tareasadminAccesoUsuario.getUsuario();
            final Long servicio = tareasadminAccesoUsuario.getServicio();
            if (tareasadminAccesoUsuario.getTareaadmin() == TareaBdConfAccesoAplicacion.BORRAREGCONFACCESO.getTareaBD()) {
                this.confAccesoAplicacionServicio.borrarPorUsuarioServicio(usuario, servicio);
                this.confAccesoAplicacionServicio.actualizarPersistencia();
            }
            else if (tareasadminAccesoUsuario.getTareaadmin() == TareaBdConfAccesoAplicacion.BORRATODOSUSUARIO.getTareaBD()) {
                this.confAccesoAplicacionServicio.borrarPorUsuario(usuario);
                this.confAccesoAplicacionServicio.actualizarPersistencia();
                this.usuarioAplicacionServicio.borrarPorUsuario(usuario);
                this.usuarioAplicacionServicio.actualizarPersistencia();
            }
            else if (tareasadminAccesoUsuario.getTareaadmin() == TareaBdConfAccesoAplicacion.INSERTAREGUSUARIO.getTareaBD()) {
                final UsuarioAplicacion pEntity = new UsuarioAplicacion();
                pEntity.setUsuario(usuario);
                pEntity.setNombre(tareasadminAccesoUsuario.getNombreusuario());
                pEntity.setDireccionIp("*");
                pEntity.setToken("*");
                pEntity.setClaveEstatica("*");
                pEntity.setVersion(Long.valueOf(1L));
                pEntity.setSistema(Short.valueOf("0"));
                pEntity.setNormal(Short.valueOf("1"));
                pEntity.setAuditoria(Short.valueOf("0"));
                this.usuarioAplicacionServicio.adicionar(pEntity);
                this.usuarioAplicacionServicio.actualizarPersistencia();
            }
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error cargando en ObtenerMaxRegTareasAdminUsuario registro datos   usuario:  descripcion Error : " + ex.getMessage());
        }
    }
    
    public void crearActualizarAccesoUsuario(final ConfAccesoAplicacion pEntity) throws EntityServicioExcepcion, IllegalArgumentException {
        try {
            final TareasadminAccesoUsuario tareasadminAccesoUsuario = this.tareasadminAccesoUsuarioServicio.obtenerTareaAdmAccesoUsuarioByMax();
            Logger.getLogger("globalApp").info("TareasadminAccesoUsuario consultado - id: :" + tareasadminAccesoUsuario.getIdtareaaccesusu());
            final String usuario = tareasadminAccesoUsuario.getUsuario();
            final Long servicio = tareasadminAccesoUsuario.getServicio();
            if (tareasadminAccesoUsuario.getTareaadmin() == TareaBdConfAccesoAplicacion.INSERTAREGCONFACCESO.getTareaBD()) {
                pEntity.setVersion(Long.valueOf(1L));
                final ConfAccesoAplicacion conAcceso = this.confAccesoAplicacionServicio.buscar(pEntity.getConfAccesoAplicacionPK());
                Logger.getLogger("globalApp").info("TareasadminAccesoUsuario crearActualizarAccesoUsuario CONSULTA BD: " + conAcceso);
                if (null == conAcceso) {
                    Logger.getLogger("globalApp").info("TareasadminAccesoUsuario crearActualizarAccesoUsuario NUEVO ");
                    pEntity.setVersion(Long.valueOf(1L));
                    this.confAccesoAplicacionServicio.adicionar(pEntity);
                }
                else {
                    Logger.getLogger("globalApp").info("TareasadminAccesoUsuario crearActualizarAccesoUsuario MODIFICAR ");
                    conAcceso.setActualizar(pEntity.getActualizar());
                    conAcceso.setAdministrar(pEntity.getAdministrar());
                    conAcceso.setBorrar(pEntity.getBorrar());
                    conAcceso.setConsultar(pEntity.getConsultar());
                    conAcceso.setCrear(pEntity.getCrear());
                    conAcceso.setEjecutar(pEntity.getEjecutar());
                    this.confAccesoAplicacionServicio.actualizar(conAcceso);
                }
                Logger.getLogger("globalApp").info("TareasadminAccesoUsuario crearActualizarAccesoUsuario conAcceso : " + conAcceso);
                Logger.getLogger("globalApp").info("TareasadminAccesoUsuario crearActualizarAccesoUsuario pEntity : " + pEntity);
                this.confAccesoAplicacionServicio.actualizarPersistencia();
            }
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error cargando en ObtenerMaxRegTareasAdminUsuario registro datos   usuario:  descripcion Error : " + ex.getMessage());
        }
    }
}
