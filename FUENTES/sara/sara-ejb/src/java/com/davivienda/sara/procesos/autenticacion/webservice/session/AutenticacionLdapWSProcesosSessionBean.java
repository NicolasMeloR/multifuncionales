// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.autenticacion.webservice.session;

import com.davivienda.utilidades.SaraUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.utilidades.ws.gestor.cliente.InvocacionServicios;
import com.davivienda.sara.tablas.occa.servicio.OccaServicio;
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
@Local({ AutenticacionLdapWSProcesosSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class AutenticacionLdapWSProcesosSessionBean implements AutenticacionLdapWSProcesosSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private CajeroServicio cajeroServicio;
    private OccaServicio occaServicio;
    private InvocacionServicios invocacionServicios;
    String servidor;
    String puerto;
    private static SaraConfig configApp;
    
    public AutenticacionLdapWSProcesosSessionBean() {
        this.servidor = "";
        this.puerto = "";
    }
    
    @PostConstruct
    public void postConstructor() {
        this.cajeroServicio = new CajeroServicio(this.em);
        this.occaServicio = new OccaServicio(this.em);
        this.servidor = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.SERVIDOR_WS"))).getValor().trim();
        this.puerto = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.PUERTO_SERVIDOR_WS"))).getValor().trim();
        this.iniciarLog();
    }
    
    private void iniciarLog() {
        if (AutenticacionLdapWSProcesosSessionBean.configApp == null) {
            try {
                AutenticacionLdapWSProcesosSessionBean.configApp = SaraConfig.obtenerInstancia();
            }
            catch (IllegalAccessException ex) {
                Logger.getLogger(AutenticacionLdapWSProcesosSessionBean.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            catch (Exception ex2) {
                Logger.getLogger(AutenticacionLdapWSProcesosSessionBean.class.getName()).log(Level.SEVERE, ex2.getMessage(), ex2);
            }
        }
    }
    
    @Override
    public String autenticarLdap(final String usuario, final String clave) {
        String respuesta = "";
        String respuestaDescripcion = "";
        try {
            this.invocacionServicios = new InvocacionServicios(this.servidor, this.puerto, Integer.valueOf(0));
            respuesta = (respuestaDescripcion = this.invocacionServicios.autenticarLdap(usuario, clave));
            if (respuesta != null && respuesta.length() > 0) {
                if (respuesta.substring(0, 1).equals("M")) {
                    respuestaDescripcion = "Datos de Autenticacion Errados";
                }
                else if (respuesta.substring(0, 1).equals("E")) {
                    respuestaDescripcion = (respuestaDescripcion += "Problemas con el Servicio de Autenticacion Ldap");
                }
            }
        }
        catch (Exception ex) {
            respuestaDescripcion = "Error en AutenticacionLdap :" + ex.getMessage();
            AutenticacionLdapWSProcesosSessionBean.configApp.loggerApp.log(Level.SEVERE, "EN autenticacionLdap  del usuario: " + SaraUtil.stripXSS(usuario, "[^\\dA-Za-z\\.\\-\\s ]") + " error :" + ex.getMessage());
            respuesta = "Error durante la Autenticaci\u00f3n LDAP";
        }
        return respuesta;
    }
}
