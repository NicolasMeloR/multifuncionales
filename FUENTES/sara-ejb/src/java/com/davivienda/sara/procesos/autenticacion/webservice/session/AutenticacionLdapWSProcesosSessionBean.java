package com.davivienda.sara.procesos.autenticacion.webservice.session;

import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ejb.Local;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;

import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;

import com.davivienda.sara.config.SaraConfig;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;

import com.davivienda.utilidades.ws.gestor.cliente.InvocacionServicios;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */

@Stateless
@Local(value = AutenticacionLdapWSProcesosSessionLocal.class)
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AutenticacionLdapWSProcesosSessionBean implements AutenticacionLdapWSProcesosSessionLocal {

    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    private CajeroServicio cajeroServicio;
    private OccaServicio occaServicio;

    private InvocacionServicios invocacionServicios;
    String servidor = "";
    String puerto = "";

    private static SaraConfig configApp;

    /**
     * Método PostConstruct
     */
    @PostConstruct
    public void postConstructor() {

        cajeroServicio = new CajeroServicio(em);
        occaServicio = new OccaServicio(em);
        servidor = em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.SERVIDOR_WS")).getValor().trim();
        puerto = em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.PUERTO_SERVIDOR_WS")).getValor().trim();
        iniciarLog();

    }

    private void iniciarLog() {
        if (configApp == null) {
            // String archivoPropiedadesConfiguracion = dirConfiguracion + "WEB-INF/SARA.properties";
            try {
                configApp = SaraConfig.obtenerInstancia();

                //  iniciarMBeanConfig();
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AutenticacionLdapWSProcesosSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (Exception ex) {
                Logger.getLogger(AutenticacionLdapWSProcesosSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    public String autenticarLdap(String usuario, String clave) {
        String respuesta = "";
        String respuestaDescripcion = "";

        try {
            invocacionServicios = new InvocacionServicios(servidor, puerto, 0);
            respuesta = invocacionServicios.autenticarLdap(usuario, clave);
            respuestaDescripcion = respuesta;

            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("M")) {
                        respuestaDescripcion = "Datos de Autenticacion Errados";
                    } else {
                        if (respuesta.substring(0, 1).equals("E")) {
                            respuestaDescripcion = respuestaDescripcion += "Problemas con el Servicio de Autenticacion Ldap";
                        }
                    }
                }
            }
        } catch (Exception ex) {

            respuestaDescripcion = "Error en AutenticacionLdap :" + ex.getMessage();
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "EN autenticacionLdap  del usuario: " + SaraUtil.stripXSS(usuario, Constantes.REGEX_ACEPTAR_DEFAULT) + " error :" + ex.getMessage());
            respuesta = "Error durante la Autenticación LDAP";

        }

        return respuesta;
        //return "B;GG-Servicios Automatas";

    }
}
