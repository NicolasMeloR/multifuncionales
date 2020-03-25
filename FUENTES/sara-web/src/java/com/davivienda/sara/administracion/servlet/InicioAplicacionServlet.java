package com.davivienda.sara.administracion.servlet;

import com.davivienda.sara.administracion.session.SaraConfiguracionSession;
import com.davivienda.sara.base.BaseObjectContextWeb;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.tablas.controlusuarioaplicacion.session.ControlUsuarioAplicacionSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.log.LogFormatter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;
import java.util.logging.SimpleFormatter;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * InicioAplicacionServlet - 16/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class InicioAplicacionServlet extends HttpServlet {

    @EJB
    SaraConfiguracionSession saraConfiguracionSession;
    BaseObjectContextWeb objectContextBase;
    
    @EJB
    ControlUsuarioAplicacionSessionLocal controlUsuarioSession;
    SaraConfig configApp;
    private FileHandler fh;
    private FileHandler fhAcceso;
    private MemoryHandler mh;
    private MemoryHandler mhAcceso;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Extrae el path donde se encuentra el contexto
        saraConfiguracionSession.iniciarConfiguracion(null);
        configApp = saraConfiguracionSession.getAppConfig();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(InicioAplicacionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (configApp.loggerAcceso == null || configApp.loggerApp == null) {
                crearLoggerAplicacion();
        }
        BaseObjectContextWeb.setConfigApp(configApp);
    }

    private void crearLoggerAplicacion() {
        configApp.loggerApp = Logger.getLogger(Constantes.LOGGER_APP);
        configApp.loggerAcceso = Logger.getLogger(Constantes.LOGGER_ACCESO);
        if (configApp.loggerAcceso.getHandlers().length == 0) {
            Level nivelLog = Level.ALL;
            try {
                configApp.loggerApp.setLevel(Level.ALL);
                configApp.loggerAcceso.setLevel(Level.ALL);
                if (fh == null) {
                    System.out.println("seteando handler");
                    fh = new FileHandler(configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS.trim() + "sara_%g.log", (int) configApp.TAMANO_ARCHIVO_LOG, 5, true);
                    fh.setFormatter(new SimpleFormatter());
                    fh.setLevel(nivelLog);
                    configApp.loggerApp.addHandler(fh);
                }
                if (fhAcceso == null) {
                    System.out.println("seteando level 2");
                    fhAcceso = new FileHandler(configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS.trim() + "sara_acceso_%g.log", (int) configApp.TAMANO_ARCHIVO_LOG, 5, true);
                    fhAcceso.setFormatter(new SimpleFormatter());
                    fhAcceso.setLevel(nivelLog);
                    configApp.loggerAcceso.addHandler(fhAcceso);
                }

            } catch (IOException ex) {
                configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                System.err.println("No se puede cargar la configuración de logs : " + ex.getMessage());
                ex.printStackTrace();
            } catch (SecurityException ex) {
                configApp.loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                System.err.println("No se puede cargar la configuración de logs : " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                if (fh != null) {
                    if (mh == null) {
                        mh = new MemoryHandler(fh, 2000, Level.ALL);
                        mh.setLevel(nivelLog);
                    // configApp.loggerApp.addHandler(mh);
                    }
                    configApp.loggerApp.info("<<< SARA-WAR " + Constantes.VERSION_APP + " >>>");
                }
                if (fhAcceso != null) {
                    if (mhAcceso == null) {
                        mhAcceso = new MemoryHandler(fhAcceso, 2000, Level.ALL);
                        mhAcceso.setLevel(nivelLog);
                    // configApp.loggerAcceso.addHandler(mhAcceso);
                    }
                }
                try {
                    nivelLog = Level.parse(configApp.CONFIGURACION_LOG_NIVEL_LOG);
                } catch (Exception ex) {
                    nivelLog = Level.ALL;
                    configApp.loggerApp.log(Level.SEVERE, "No se puede asignar el  nivel del log , se asigno por defecto en ALL", ex);
                }
                configApp.cambiarNivelLog("ALL");
                configApp.loggerApp.info("Se inició el Log en " + configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS + "sara.log con Nivel " + configApp.loggerApp.getLevel().toString());
                configApp.loggerApp.info("Se inició el Log de Acceso en " + configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS + "sara_acceso.log con Nivel " + configApp.loggerAcceso.getLevel().toString());
                configApp.loggerAcceso.info("Se inició el Log de Acceso en " + configApp.CONFIGURACION_LOG_DIRECTORIO_LOGS + "sara_acceso.log con Nivel " + configApp.loggerAcceso.getLevel().toString());
            }
        }
    }

    private void destruirLoggerAplicacion() {
        try {
            configApp.loggerApp.severe("Se da inicio al fin del módulo SARA-WAR ... ");
            configApp.loggerApp.severe(">>> Se termina el módulo SARA-WAR " + Constantes.VERSION_APP + " <<<");
            Handler[] handlers = configApp.loggerAcceso.getHandlers();
            for (int i = 0; i < handlers.length; i++) {
                Handler handler = handlers[i];
                handler.flush();
                configApp.loggerAcceso.severe("Se finaliza el log de Acceso:" + handler.getClass().getSimpleName() + " con nivel " + handler.getLevel().toString());
                configApp.loggerApp.severe("Se finaliza el log de Acceso:" + handler.getClass().getSimpleName() + " con nivel " + handler.getLevel().toString());
                handler.close();
                configApp.loggerAcceso.removeHandler(handler);
            }
            handlers = configApp.loggerApp.getHandlers();
            for (int i = 0; i < handlers.length; i++) {
                Handler handler = handlers[i];
                handler.flush();
                configApp.loggerApp.severe("Se finaliza el log :" + handler.getClass().getSimpleName() + " con nivel " + handler.getLevel().toString());
                handler.close();
                
                configApp.loggerApp.info("Inicia proceso de eliminar usuarios de control de sesion");
                configApp.loggerApp.removeHandler(handler);
            }
            
            controlUsuarioSession.eliminarDatosControlUsuario();
        } catch (Exception ex) {
            configApp.loggerApp.log(Level.SEVERE, "Finalización con error de la aplicación ", ex);
            System.err.println(">>> Se termina el módulo SARA-WAR " + Constantes.VERSION_APP + " con ERROR " + ex.getMessage() + "  <<<");
        } finally {
            System.err.println(">>> Se termina el módulo SARA-WAR " + Constantes.VERSION_APP + " <<<");
        }
    }

    @Override
    public void destroy() {
        try {
            super.destroy();            
            saraConfiguracionSession = null;
            Thread.sleep(3000);
            destruirLoggerAplicacion();
            BaseObjectContextWeb.setConfigApp(null);
        } catch (InterruptedException ex) {
            Logger.getLogger(InicioAplicacionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InicioAplicacionServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InicioAplicacionServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return String
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
