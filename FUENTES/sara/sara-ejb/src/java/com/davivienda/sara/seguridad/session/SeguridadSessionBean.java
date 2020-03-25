// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.seguridad.session;

import com.davivienda.sara.entitys.seguridad.ServicioAplicacion;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import com.davivienda.utilidades.SaraUtil;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import javax.annotation.PostConstruct;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.tablas.servicioaplicacion.servicio.ServicioAplicacionServicio;
import com.davivienda.sara.tablas.usuarioaplicacion.servicio.UsuarioAplicacionServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import javax.ejb.Stateless;

@Stateless
public class SeguridadSessionBean implements SeguridadSessionLocal
{
    private static final Logger logger;
    @PersistenceContext
    private EntityManager em;
    private UsuarioAplicacionServicio servicio;
    private ServicioAplicacionServicio servicioAplicacionServicio;
    private SaraConfig configApp;
    
    @PostConstruct
    public void postConstructor() {
        this.servicio = new UsuarioAplicacionServicio(this.em);
        this.servicioAplicacionServicio = new ServicioAplicacionServicio(this.em);
        this.configApp = this.servicio.getConfigApp();
    }
    
    @Override
    public UsuarioAplicacion validarAccesoAplicacion(final String idUsuario, final String direccionIP) {
        return this.validarAccesoAplicacion(idUsuario, direccionIP, null, null);
    }
    
    @Override
    public UsuarioAplicacion validarAccesoAplicacion(String idUsuario, final String direccionIP, final String token, final String claveEstatica) {
        idUsuario = SaraUtil.stripXSS(idUsuario.toLowerCase(), "[^\\dA-Za-z\\.\\-\\s ]");
        UsuarioAplicacion usuario = null;
        Boolean usuarioOk = false;
        Boolean tokenOk = false;
        Boolean direccionIPOk = false;
        Boolean claveEstaticaOk = false;
        SeguridadSessionBean.logger.info("Validando usuario '" + idUsuario + "' en BD");
        try {
            usuario = this.servicio.buscar(idUsuario);
            if (usuario != null) {
                this.em.refresh((Object)usuario);
                usuarioOk = true;
            }
        }
        catch (EntityServicioExcepcion ex) {
            this.configApp.loggerApp.log(Level.SEVERE, null, ex);
            usuario = null;
            usuarioOk = false;
        }
        if (usuarioOk && usuario.getToken() != null) {
            final String tokenRegistrado = usuario.getToken();
            if (!tokenRegistrado.equals("*")) {
                tokenOk = true;
                this.configApp.loggerApp.info("Se solicita la validaci\u00f3n de token y no existe");
            }
            else {
                tokenOk = true;
            }
        }
        if (usuarioOk && usuario.getDireccionIp() != null && usuario.getDireccionIp().length() > 0) {
            String dirIpRegistrada = usuario.getDireccionIp();
            if (!dirIpRegistrada.equals("*") || !dirIpRegistrada.equals("*.*.*.*")) {
                if (direccionIP != null && direccionIP.length() > 6) {
                    dirIpRegistrada = dirIpRegistrada.replaceAll("[*]", "([0-9])*");
                    final String[] arr$;
                    final String[] dirsIp = arr$ = dirIpRegistrada.split(";");
                    for (final String string : arr$) {
                        final Pattern p = Pattern.compile(string);
                        final Matcher m = p.matcher(direccionIP);
                        while (m.find()) {
                            direccionIPOk = true;
                        }
                    }
                }
            }
            else {
                direccionIPOk = true;
            }
        }
        if (usuarioOk && usuario.getClaveEstatica() != null && usuario.getClaveEstatica().length() > 0) {
            final String claveEstaticaRegistrada = usuario.getClaveEstatica();
            if (!claveEstaticaRegistrada.equals("*")) {
                if (claveEstatica != null && usuario.getClaveEstatica().equals(claveEstatica)) {
                    claveEstaticaOk = true;
                }
            }
            else {
                claveEstaticaOk = true;
            }
        }
        if (!usuarioOk || !tokenOk || !direccionIPOk || !claveEstaticaOk) {
            this.configApp.loggerAcceso.info("El usuario " + idUsuario + " desde la IP " + direccionIP + " NO se encuentra registrado en BD.");
            usuario = null;
        }
        else {
            this.configApp.loggerAcceso.info("El usuario " + idUsuario + " desde la IP " + direccionIP + " se encuentra registrado en BD.");
        }
        return usuario;
    }
    
    @Override
    public String getPrivilegiosUsuarioJSon(final String idUsuario) {
        return "";
    }
    
    @Override
    public Collection<ConfAccesoAplicacion> getConfAccesoAplicacion(final String idUsuario) {
        return this.servicio.getConfAccesoAplicacion(idUsuario);
    }
    
    @Override
    public Collection<ServicioAplicacion> geServiciosAplicacion() {
        return this.servicioAplicacionServicio.getTodos();
    }
    
    static {
        logger = Logger.getLogger("SARA_Acceso");
    }
}
