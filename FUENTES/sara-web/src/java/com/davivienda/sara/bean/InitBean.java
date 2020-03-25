package com.davivienda.sara.bean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.davivienda.sara.administracion.session.SaraConfiguracionSession;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.page.administracion.*;
import com.davivienda.sara.bean.page.carguesmasivos.CargarNotasDebitoMasivosBean;
import com.davivienda.sara.bean.page.carguesmasivos.CargarReintegrosMasivosBean;
import com.davivienda.sara.bean.page.carguesmasivos.CargarTimbresMasivosBean;
import com.davivienda.sara.bean.page.contingencia.*;
import com.davivienda.sara.bean.page.cuadreCajeros.ConsultarAjustesBean;
import com.davivienda.sara.bean.page.cuadreCajeros.ConsultarTotalesBean;
import com.davivienda.sara.bean.page.cuadreCajeros.CuadreCifrasBean;
import com.davivienda.sara.bean.page.cuadreCajeros.RealizarAjusteBean;
import com.davivienda.sara.bean.page.diarioelectronico.*;
import com.davivienda.sara.bean.page.multifuncional.cuadrecifras.ConsultarAjustesMFBean;
import com.davivienda.sara.bean.page.multifuncional.cuadrecifras.ConsultarTotalesMFBean;
import com.davivienda.sara.bean.page.multifuncional.cuadrecifras.CuadreCifrasMFBean;
import com.davivienda.sara.bean.page.multifuncional.cuadrecifras.RealizarAjusteMFBean;
import com.davivienda.sara.bean.page.multifuncional.diarioelectronico.TransaccionMFBean;
import com.davivienda.sara.bean.page.multifuncional.diarioelectronico.DiariokalMFBean;
import com.davivienda.sara.bean.page.multifuncional.reintegros.*;
import com.davivienda.sara.bean.page.reintegros.*;
import com.davivienda.sara.dto.UsuarioAccesoDTO;
import com.davivienda.sara.dto.UsuarioAplicacionDTO;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.seguridad.session.SeguridadSessionLocal;
import com.davivienda.sara.tablas.controlusuarioaplicacion.session.ControlUsuarioAplicacionSessionLocal;
import com.davivienda.utilidades.Constantes;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.davivienda.sara.tablas.usuarioaplicacion.session.UsuarioAplicacionSessionLocal;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletException;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "initBean")
@ViewScoped
public class InitBean implements Serializable {

    private static final Logger loggerApp = Logger.getLogger(Constantes.LOGGER_APP);
    private static final Logger loggerAcceso = Logger.getLogger(Constantes.LOGGER_ACCESO);

    @EJB
    SaraConfiguracionSession saraConfiguracionSession;
    
    private boolean mostrarMenuPrincipal;
    private boolean mostrarSubMenuDispensador;
    private boolean mostrarSubMenuMultifuncional;
    private boolean mostrarSubMenuCarguesMasivos;
    private String opcionSubMenu;
    private boolean mostrarOpcionesSubMenus;
    private String opcionPaginaSubMenu;
    private boolean mostrarPaginasSubMenus;
    private boolean mostrarModalMsg;
    private String tituloModal;
    private String descripcionModal;
    private String usuarioAut;
    private String passwordAut;
    private boolean mostrarModalAut;
    private int tiempoSession;

    /**
     * Map con las direcciones URL de páginas
     */
    private Map<String, String> mapUrl;
    /**
     * map con los tipos de respuesta para cada componente
     */
    private Map<String, String> mapTipoRespuestaComponente;

    @EJB
    SeguridadSessionLocal seguridadSession;
    @EJB
    UsuarioAplicacionSessionLocal usuarioAplicacionSession;

    public UsuarioAplicacionDTO usuarioAplicacionDTO;

    ComponenteAjaxObjectContextWeb objectContext;
    private UsuarioAplicacion usuarioAplicacion;

    @EJB
    ControlUsuarioAplicacionSessionLocal controlUsuarioSession;

    @PostConstruct
    public void InitBean() {
        loggerAcceso.log(Level.FINEST, "Entro Init InitBean ");
        try {
            initConfig();
            cargarPaginaInicial();
            
            this.tiempoSession = saraConfiguracionSession.getAppConfig().TIEMPO_SESION;
            this.getRequestFaces().getSession().setMaxInactiveInterval(this.tiempoSession*60);
            

        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, "Error al inicializar aplicación: " + ex.getMessage(), ex);
        }
    }
    
    public void cargarPaginaInicial() throws IOException, ServletException{
            this.tituloModal = "SARA";
            this.descripcionModal = "";
            this.mostrarSubMenuDispensador = false;
            this.mostrarSubMenuMultifuncional = false;
            this.mostrarSubMenuCarguesMasivos = false;
            this.mostrarMenuPrincipal = true;
            this.opcionSubMenu = "";
            this.mostrarOpcionesSubMenus = false;
            this.opcionPaginaSubMenu = "";
            this.mostrarPaginasSubMenus = false;
            this.mostrarModalMsg = false;
            this.mostrarModalAut = false;
            this.usuarioAut = "";
            this.passwordAut = "";

            objectContext = new ComponenteAjaxObjectContextWeb(getRequestFaces(), getResponseFaces());
            usuarioAplicacionDTO = (UsuarioAplicacionDTO) getRequestFaces().getSession().getAttribute(Constantes.SESSION_SECURITY_DTO);
            usuarioAplicacion = usuarioAplicacionDTO.getUsuarioAplicacion();
            crearMenuPrincipal(usuarioAplicacionDTO);
    }

    public String cerrarSesion() {
        this.getRequestFaces().getSession().removeAttribute(Constantes.SESSION_SECURITY_DTO);
        this.getRequestFaces().getSession().invalidate();
        controlUsuarioSession.eliminarDatosControlXUsuario(usuarioAplicacion.getUsuario());
        
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String path = context.getRequestContextPath();

            loggerApp.log(Level.INFO, "Cerrando sesión:");
            //loggerApp.log(Level.INFO, context.getRequestContextPath()); //  /sara-web
            //loggerApp.log(Level.INFO, context.getRequestServletPath()); //  /pages/sara.xhtml

//            HttpServletRequest request = (HttpServletRequest) context.getRequest();
//            String url = request.getRequestURL().toString();
//            String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
            String baseURL = saraConfiguracionSession.getAppConfig().URL_ACCESO_APP;
            FacesContext.getCurrentInstance().responseComplete();
            context.redirect(baseURL);
        } catch (IOException ex) {
            Logger.getLogger(InitBean.class.getName()).log(Level.SEVERE, "Error cerrando sesión", ex);
            return "/login.xhtml?faces-redirect=true";
        }
        
        return "";
    }

    private void crearMenuPrincipal(UsuarioAplicacionDTO usuarioAplicacionDTO) {

        Map map = usuarioAplicacionDTO.getUsuarioAcceso();
        UsuarioAccesoDTO acceso = (UsuarioAccesoDTO) map.get("srvc1");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc1(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc2");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc2(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc3");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc3(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc4");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc4(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc5");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc5(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc6");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc6(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc7");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc7(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc8");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc8(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc9");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc9(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc101");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc101(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc102");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc102(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc121");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc121(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc201");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc201(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc202");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc202(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc211");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc211(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc212");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc212(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc221");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc221(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc301");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc301(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc302");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc302(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc311");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc311(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc312");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc312(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc321");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc321(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc322");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc322(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc331");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc331(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc332");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc332(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc333");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc333(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc334");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc334(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc341");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc341(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc401");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc401(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc402");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc402(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc411");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc411(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc412");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc412(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc421");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc421(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc422");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc422(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc431");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc431(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc432");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc432(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc441");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc441(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc442");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc442(true);
        }
        
        
        acceso = (UsuarioAccesoDTO) map.get("srvc451");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc451(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc452");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc452(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc501");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc501(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc502");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc502(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc511");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc511(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc512");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc512(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc601");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc601(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc602");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc602(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc701");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc701(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc702");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc702(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc711");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc711(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc712");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc712(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc721");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc721(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc722");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc722(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc801");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc801(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc802");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc802(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc811");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc811(true);
        }
        
        acceso = (UsuarioAccesoDTO) map.get("srvc812");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc812(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc901");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc901(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc902");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc902(true);
        }
        acceso = (UsuarioAccesoDTO) map.get("srvc903");
        if (acceso != null && acceso.getConsultar().equals("1")) {
            this.usuarioAplicacionDTO.setSrvc903(true);
        }
    }

    public void cargarSubMenuMultifuncional() {
        this.mostrarSubMenuDispensador = false;
        this.mostrarSubMenuMultifuncional = true;
        this.mostrarSubMenuCarguesMasivos = false;
        this.mostrarMenuPrincipal = false;
    }
    
    public void cargarSubMenuCarguesMasivos(){
        this.mostrarSubMenuDispensador = false;
        this.mostrarSubMenuMultifuncional = false;
        this.mostrarSubMenuCarguesMasivos = true;
        this.mostrarMenuPrincipal = false;
    }
    
    public void cargarSubMenuDispensador() {
        this.mostrarSubMenuDispensador = true;
        this.mostrarSubMenuMultifuncional = false;
        this.mostrarSubMenuCarguesMasivos = false;
        this.mostrarMenuPrincipal = false;
    }

    public void cargarOpcionesSubMenu() {
        String strParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("opcionSubMenu");
        this.opcionSubMenu = strParam;
        this.mostrarOpcionesSubMenus = !this.opcionSubMenu.isEmpty();
    }

    public void cargarPaginaOpcionesSubMenu() {
        try {
            String strParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("opcionPaginaSubMenu");
            this.opcionPaginaSubMenu = strParam;
            this.mostrarPaginasSubMenus = !this.opcionPaginaSubMenu.isEmpty();

            Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            
            if (this.opcionPaginaSubMenu.equals("CARGAR_DIARIO_E")) {
                CargarArchivoBean cargarArchivoBeanD = (CargarArchivoBean) viewMap.get("cargarArchivoBean");
                if (cargarArchivoBeanD != null) {
                    cargarArchivoBeanD.dataInicialDiarioE();
                }
            } else if (this.opcionPaginaSubMenu.equals("CARGAR_ARCHIVO_CD")) {
                CargarArchivoBean cargarArchivoBeanC = (CargarArchivoBean) viewMap.get("cargarArchivoBean");
                if (cargarArchivoBeanC != null) {
                    cargarArchivoBeanC.dataInicialCicloD();
                }
            } else if (this.opcionPaginaSubMenu.equals("USUARIOS")) {
                UsuariosBean usuariosBean = (UsuariosBean) viewMap.get("usuariosBean");
                if (usuariosBean != null) {
                    usuariosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("PRIVILEGIOS_ACCESO")) {
                PrivilegiosAccesoBean privilegiosBean = (PrivilegiosAccesoBean) viewMap.get("privilegiosAccesoBean");
                if (privilegiosBean != null) {
                    privilegiosBean.usuarioInicial();
                }

            } else if (this.opcionPaginaSubMenu.equals("CONFIGURACION_MODULOS")) {
                ConfiguracionModulosBean configuracionModulosBean = (ConfiguracionModulosBean) viewMap.get("configuracionModulosBean");
                if (configuracionModulosBean != null) {
                    configuracionModulosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("TRANSACCION_MULTIFUNCIONAL")) {
                TransaccionMFBean transaccionMFBean = (TransaccionMFBean) viewMap.get("transaccionMFBean");
                if (transaccionMFBean != null) {
                    transaccionMFBean.dataInicial();
                }
            } /* nuevo*/
            else if (this.opcionPaginaSubMenu.equals("DIARIOKAL_MULTIFUNCIONAL")) {
                DiariokalMFBean diariokalMFBean = (DiariokalMFBean) viewMap.get("diariokalMFBean");
                if(diariokalMFBean != null){
                    diariokalMFBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CUADRE_CIFRAS_SUB_MULTIFUNCIONAL")) {
                CuadreCifrasMFBean cuadreCifrasMFBean = (CuadreCifrasMFBean) viewMap.get("cuadreCifrasMFBean");
                if (cuadreCifrasMFBean != null) {
                    cuadreCifrasMFBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CREAR_NOTA_CREDITO_MULTIFUNCIONAL")) {
                IngresoNotaCreditoMultifucionalBean ingresoNotaCreditoMultifucionalBean = (IngresoNotaCreditoMultifucionalBean) viewMap.get("ingNotaCreditoMultifuncionalBean");
                if (ingresoNotaCreditoMultifucionalBean != null) {
                    ingresoNotaCreditoMultifucionalBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CREAR_NOTA_DEBITO_MULTIFUNCIONAL")) {
                IngresoNotaDebitoMultifucionalBean ingresoNotaDebitoMultifucionalBean = (IngresoNotaDebitoMultifucionalBean) viewMap.get("ingNotaDebitoMultifuncionalBean");
                if (ingresoNotaDebitoMultifucionalBean != null) {
                    ingresoNotaDebitoMultifucionalBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CAJERO")) {
                InfoCajeroBean infoCajeroBean = (InfoCajeroBean) viewMap.get("infoCajeroBean");
                if (infoCajeroBean != null) {
                    infoCajeroBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("TIPO_CAJERO")) {
                InfoTipoCajeroBean infoTipoCajeroBean = (InfoTipoCajeroBean) viewMap.get("infoTipoCajeroBean");
                if (infoTipoCajeroBean != null) {
                    infoTipoCajeroBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("UBICACION")) {
                InfoUbicacionBean infoUbicacionBean = (InfoUbicacionBean) viewMap.get("infoUbicacionBean");
                if (infoUbicacionBean != null) {
                    infoUbicacionBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("ZONA")) {
            } else if (this.opcionPaginaSubMenu.equals("REGIONAL")) {
            } else if (this.opcionPaginaSubMenu.equals("TRANSPORTADORA")) {
                InfoTransportadoraBean infoTransportadoraBean = (InfoTransportadoraBean) viewMap.get("infoTransportadoraBean");
                if (infoTransportadoraBean != null) {
                    infoTransportadoraBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("OCCA")) {
            } else if (this.opcionPaginaSubMenu.equals("CONTIGENCIA_CAJERO")) {
                InfoConCajeroBean infoConCajeroBean = (InfoConCajeroBean) viewMap.get("infoConCajeroBean");
                if (infoConCajeroBean != null) {
                    infoConCajeroBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CONS_GENERAL_CONTINGENCIA")) {
                ContingenciaConsultaGeneralBean contingenciaConsultaGBean = (ContingenciaConsultaGeneralBean) viewMap.get("contingenciaConsultaGBean");
                if (contingenciaConsultaGBean != null) {
                    contingenciaConsultaGBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CONS_DIARIOSE_CONTINGENCIA")) {
                ContingenciaConsultaDiariosBean contingenciaConDiariosBean = (ContingenciaConsultaDiariosBean) viewMap.get("contingenciaConDiariosBean");
                if (contingenciaConDiariosBean != null) {
                    contingenciaConDiariosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CREAR_NOTA_CREDITO")) {
                IngresoNotaCreditoBean ingNotaCreditoBean = (IngresoNotaCreditoBean) viewMap.get("ingNotaCreditoBean");
                if (ingNotaCreditoBean != null) {
                    ingNotaCreditoBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CREAR_NOTA_DEBITO")) {
                IngresoNotaDebitoBean ingNotaDebitoBean = (IngresoNotaDebitoBean) viewMap.get("ingNotaDebitoBean");
                if (ingNotaDebitoBean != null) {
                    ingNotaDebitoBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CARGAR_ARCHIVO_HOST")) {
                CargarArchivoHostBean cargarArchivoHostBean = (CargarArchivoHostBean) viewMap.get("cargarArchivoHostBean");
                if (cargarArchivoHostBean != null) {
                    cargarArchivoHostBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CALCULAR_REINTEGROS")) {
                CalcularReintegrosBean calcularReintegrosBean = (CalcularReintegrosBean) viewMap.get("calcularReintegrosBean");
                if (calcularReintegrosBean != null) {
                    calcularReintegrosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("INFORMES_REINTEGROS")) {
                InformesReintegrosBean informesReintegrosBean = (InformesReintegrosBean) viewMap.get("informesReintegrosBean");
                if (informesReintegrosBean != null) {
                    informesReintegrosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("REVISAR_REINTEGROS")) {
                RevisarReintegrosBean revisarReintegrosBean = (RevisarReintegrosBean) viewMap.get("revisarReintegrosBean");
                if (revisarReintegrosBean != null) {
                    revisarReintegrosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("REVERSAR_REINTEGROS")) {
                ReversarReintegrosBean reversarReintegrosBean = (ReversarReintegrosBean) viewMap.get("reversarReintegrosBean");
                if (reversarReintegrosBean != null) {
                    reversarReintegrosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CONSULTAR_REINTEGROS_REVERSADOS")) {
                ConsultarReintegrosReversadosBean consultarReintegrosReversadosBean = (ConsultarReintegrosReversadosBean) viewMap.get("consultarReintegrosReversadosBean");
                if (consultarReintegrosReversadosBean != null) {
                    consultarReintegrosReversadosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("AUTORIZAR_REINTEGROS")) {
                AutorizarReintegrosBean autorizarReintegrosBean = (AutorizarReintegrosBean) viewMap.get("autorizarReintegrosBean");
                if (autorizarReintegrosBean != null) {
                    autorizarReintegrosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("AUTORIZAR_REINTEGROS_MULTIFUNCIONAL")) {
                AutorizarReintegrosMultifuncionalBean autorizarReintegrosMultifuncionalBean = (AutorizarReintegrosMultifuncionalBean) viewMap.get("autorizarReintegrosMultifuncionalBean");
                if (autorizarReintegrosMultifuncionalBean != null) {
                    autorizarReintegrosMultifuncionalBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("REINTEGROS_CREADOS")) {
                ReintegrosCreadosBean reintegrosCreadosBean = (ReintegrosCreadosBean) viewMap.get("reintegrosCreadosBean");
                if (reintegrosCreadosBean != null) {
                    reintegrosCreadosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("AUTORIZAR_NOTAS_DEBITO")) {
                AutorizarNotasDebitoBean autorizarNotasDebitoBean = (AutorizarNotasDebitoBean) viewMap.get("autorizarNotasDebitoBean");
                if (autorizarNotasDebitoBean != null) {
                    autorizarNotasDebitoBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("REVISAR_CREADOS")) {
                RevisarCreadosBean revisarCreadosBean = (RevisarCreadosBean) viewMap.get("revisarCreadosBean");
                if (revisarCreadosBean != null) {
                    revisarCreadosBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("INFORMES_REINTEGROS_MULTIFUNCIONAL")) {
                InformesReintegrosMfBean informesReintegrosMfBean = (InformesReintegrosMfBean) viewMap.get("informesReintegrosMfBean");
                if (informesReintegrosMfBean != null) {
                    informesReintegrosMfBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("REVISAR_REINTEGROS_MULTIFUNCIONAL")) {
                RevisarReintegrosMfBean revisarReintegrosMfBean = (RevisarReintegrosMfBean) viewMap.get("revisarReintegrosMfBean");
                if (revisarReintegrosMfBean != null) {
                    revisarReintegrosMfBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("TRANSACCION")) {
                TransaccionBean transaccionBean = (TransaccionBean) viewMap.get("transaccionBean");
                if (transaccionBean != null) {
                    transaccionBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("REALIZAR_AJUSTE")) {
                RealizarAjusteBean realizarAjusteBean = (RealizarAjusteBean) viewMap.get("realizarAjusteBean");
                if (realizarAjusteBean != null) {
                    realizarAjusteBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CONSULTAR_TOTALES")) {
                ConsultarTotalesBean consultarTotalesBean = (ConsultarTotalesBean) viewMap.get("consultarTotalesBean");
                if (consultarTotalesBean != null) {
                    consultarTotalesBean.usuarioInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CONSULTAR_AJUSTES")) {
                ConsultarAjustesBean consultarAjustesBean = (ConsultarAjustesBean) viewMap.get("consultarAjustesBean");
                if (consultarAjustesBean != null) {
                    consultarAjustesBean.usuarioInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CUADRE_CIFRAS")) {
                CuadreCifrasBean cuadreCifrasBean = (CuadreCifrasBean) viewMap.get("cuadreCifrasBean");
                if (cuadreCifrasBean != null) {
                    cuadreCifrasBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("AUTORIZAR_NOTAS_DEBITO_MULTIFUNCIONAL")) {
                AutorizarNotasDebitoMfBean autorizarNotasDebitoMfBean = (AutorizarNotasDebitoMfBean) viewMap.get("autorizarNotasDebitoMfBean");
                if (autorizarNotasDebitoMfBean != null) {
                    autorizarNotasDebitoMfBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("REALIZAR_AJUSTE_MULTIFUNCIONAL")) {
                RealizarAjusteMFBean realizarAjusteMFBean = (RealizarAjusteMFBean) viewMap.get("realizarAjusteMFBean");
                if (realizarAjusteMFBean != null) {
                    realizarAjusteMFBean.dataInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CONSULTAR_TOTALES_MULTIFUNCIONAL")) {
                ConsultarTotalesMFBean consultarTotalesMFBean = (ConsultarTotalesMFBean) viewMap.get("consultarTotalesMFBean");
                if (consultarTotalesMFBean != null) {
                    consultarTotalesMFBean.usuarioInicial();
                }
            } else if (this.opcionPaginaSubMenu.equals("CONSULTAR_AJUSTE_MULTIFUNCIONAL")) {
                ConsultarAjustesMFBean consultarAjustesMFBean = (ConsultarAjustesMFBean) viewMap.get("consultarAjustesMFBean");
                if (consultarAjustesMFBean != null) {
                    consultarAjustesMFBean.usuarioInicial();
                }
            }else if (this.opcionPaginaSubMenu.equals("CARGAR_ARCHIVO_REINTEGROS")) {
                CargarReintegrosMasivosBean cargarReintegrosMasivosBean = (CargarReintegrosMasivosBean) viewMap.get("cargarReintegrosMasivosBean");
                if (cargarReintegrosMasivosBean != null) {
//                    cargarReintegrosMasivosBean.init();
                }
            }else if (this.opcionPaginaSubMenu.equals("CARGAR_ARCHIVO_TIMBRES")) {
                CargarTimbresMasivosBean cargarTimbresMasivosBean = (CargarTimbresMasivosBean) viewMap.get("cargarTimbresMasivosBean");
                if (cargarTimbresMasivosBean != null) {
                    cargarTimbresMasivosBean.init();
                }
            }else if (this.opcionPaginaSubMenu.equals("CARGAR_ARCHIVO_NOTAS_DEBITO")) {
                CargarNotasDebitoMasivosBean cargarNotasDebitoMasivosBean = (CargarNotasDebitoMasivosBean) viewMap.get("cargarNotasDebitoMasivosBean");
                if (cargarNotasDebitoMasivosBean != null) {
                    cargarNotasDebitoMasivosBean.init();
                }
            }
        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, "Error al cargar pagina: " + ex.getMessage(), ex);
        }

    }

    public void initConfig() throws Exception {
        // Secuencias de incio del servlet
        mapUrl = new HashMap<String, String>();
        mapUrl.put("paginaError", "administracion/error.jsp");
        mapUrl.put("ingresoApp", "writer");
        mapUrl.put("usuarioAplicacionCB", "writer");
        mapUrl.put("confAccesoAplicacionCB", "writer");
        mapUrl.put("servicioAplicacionCB", "writer");
        mapUrl.put("cajeroCB", "writer");
        mapUrl.put("cajeroSinFiltroCB", "writer");
        mapUrl.put("zonaCB", "writer");
        mapUrl.put("tipoCajeroCB", "writer");
        mapUrl.put("TransportadoraCB", "writer");
        mapUrl.put("OccaCB", "writer");
        mapUrl.put("RegionalCB", "writer");
        mapUrl.put("UbicacionCB", "writer");
        mapUrl.put("ConfModulosAplicacionCB", "writer");
        mapUrl.put("ConceptosMovimientoCuadreCB", "writer");
        mapUrl.put("TipoMovimientoCuadreCB", "writer");
        mapUrl.put("TipoMovimientoCuadreCBContador", "writer");
        mapUrl.put("OfimultiCB", "writer");
        mapUrl.put("cajeroMultiCB", "writer");

        // Registro del tipo de respuesta para cada componente
        mapTipoRespuestaComponente = new HashMap<String, String>();
        mapTipoRespuestaComponente.put("paginaError", "text/html");
        mapTipoRespuestaComponente.put("ingresoApp", "text/json-comment-filtered");
        mapTipoRespuestaComponente.put("usuarioAplicacionCB", "text/json");
        mapTipoRespuestaComponente.put("confAccesoAplicacionCB", "text/json");
        mapTipoRespuestaComponente.put("servicioAplicacionCB", "text/json");
        mapTipoRespuestaComponente.put("cajeroCB", "text/json");
        mapTipoRespuestaComponente.put("cajeroSinFiltroCB", "text/json");
        mapTipoRespuestaComponente.put("zonaCB", "text/json");
        mapTipoRespuestaComponente.put("tipoCajeroCB", "text/json");
        mapTipoRespuestaComponente.put("TransportadoraCB", "text/json");
        mapTipoRespuestaComponente.put("OccaCB", "text/json");
        mapTipoRespuestaComponente.put("RegionalCB", "text/json");
        mapTipoRespuestaComponente.put("UbicacionCB", "text/json");
        mapTipoRespuestaComponente.put("ConfModulosAplicacionCB", "text/json");
        mapTipoRespuestaComponente.put("ConceptosMovimientoCuadreCB", "text/json");
        mapTipoRespuestaComponente.put("TipoMovimientoCuadreCB", "text/json");
        mapTipoRespuestaComponente.put("TipoMovimientoCuadreCBContador", "text/json");
        mapTipoRespuestaComponente.put("OfimultiCB", "text/json");
        mapTipoRespuestaComponente.put("cajeroMultiCB", "text/json");

    }

    public void cerrarModal() {
        this.descripcionModal = "";
        this.mostrarModalMsg = false;
    }

    public void autenticar() {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        switch (this.opcionPaginaSubMenu) {
            case "AUTORIZAR_REINTEGROS":
                AutorizarReintegrosBean autorizarReintegrosBean = (AutorizarReintegrosBean) viewMap.get("autorizarReintegrosBean");
                if (autorizarReintegrosBean != null) {
                    autorizarReintegrosBean.guardarData();
                }
                break;
            case "AUTORIZAR_NOTAS_DEBITO":
                AutorizarNotasDebitoBean autorizarNotasDebitoBean = (AutorizarNotasDebitoBean) viewMap.get("autorizarNotasDebitoBean");
                if (autorizarNotasDebitoBean != null) {
                    autorizarNotasDebitoBean.guardarData();
                }
                break;
            case "AUTORIZAR_REINTEGROS_MULTIFUNCIONAL":
                AutorizarReintegrosMultifuncionalBean autorizarReintegrosMultifuncionalBean = (AutorizarReintegrosMultifuncionalBean) viewMap.get("autorizarReintegrosMultifuncionalBean");
                if (autorizarReintegrosMultifuncionalBean != null) {
                    autorizarReintegrosMultifuncionalBean.guardarData();
                }
                break;
            case "AUTORIZAR_NOTAS_DEBITO_MULTIFUNCIONAL":
                AutorizarNotasDebitoMfBean autorizarNotasDebitoMfBean = (AutorizarNotasDebitoMfBean) viewMap.get("autorizarNotasDebitoMfBean");
                if (autorizarNotasDebitoMfBean != null) {
                    autorizarNotasDebitoMfBean.guardarData();
                }
                break;
            case "REVERSAR_REINTEGROS":
                ReversarReintegrosBean reversarReintegrosBean = (ReversarReintegrosBean) viewMap.get("reversarReintegrosBean");
                if (reversarReintegrosBean != null) {
                    reversarReintegrosBean.guardarData();
                }
                break;
        }
        cerrarModalAut();
    }

    public void cerrarModalAut() {
        this.usuarioAut = "";
        this.passwordAut = "";
        this.mostrarModalAut = false;
    }

    /**
     * Metodo que retorna la IP del Cliente
     *
     * @return
     */
    public String getIPClient() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return (ipAddress);
    }

    public HttpServletRequest getRequestFaces() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request;
    }

    public HttpServletResponse getResponseFaces() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        return response;
    }

    public UsuarioAplicacionDTO getUsuarioAplicacionDTO() {
        return usuarioAplicacionDTO;
    }

    public void setUsuarioAplicacionDTO(UsuarioAplicacionDTO usuarioAplicacionDTO) {
        this.usuarioAplicacionDTO = usuarioAplicacionDTO;
    }

    public ComponenteAjaxObjectContextWeb getObjectContext() {
        return objectContext;
    }

    public void setObjectContext(ComponenteAjaxObjectContextWeb objectContext) {
        this.objectContext = objectContext;
    }

    public String getTituloModal() {
        return tituloModal;
    }

    public void setTituloModal(String tituloModal) {
        this.tituloModal = tituloModal;
    }

    public String getDescripcionModal() {
        return descripcionModal;
    }

    public void setDescripcionModal(String descripcionModal) {
        this.descripcionModal = descripcionModal;
    }

    public UsuarioAplicacion getUsuarioAplicacion() {
        return usuarioAplicacion;
    }

    public void setUsuarioAplicacion(UsuarioAplicacion usuarioAplicacion) {
        this.usuarioAplicacion = usuarioAplicacion;
    }

    public boolean isMostrarSubMenuDispensador() {
        return mostrarSubMenuDispensador;
    }

    public void setMostrarSubMenuDispensador(boolean mostrarSubMenuDispensador) {
        this.mostrarSubMenuDispensador = mostrarSubMenuDispensador;
    }

    public boolean isMostrarSubMenuMultifuncional() {
        return mostrarSubMenuMultifuncional;
    }

    public void setMostrarSubMenuMultifuncional(boolean mostrarSubMenuMultifuncional) {
        this.mostrarSubMenuMultifuncional = mostrarSubMenuMultifuncional;
    }

    public boolean isMostrarMenuPrincipal() {
        return mostrarMenuPrincipal;
    }

    public void setMostrarMenuPrincipal(boolean mostrarMenuPrincipal) {
        this.mostrarMenuPrincipal = mostrarMenuPrincipal;
    }

    public String getOpcionSubMenu() {
        return opcionSubMenu;
    }

    public void setOpcionSubMenu(String opcionSubMenu) {
        this.opcionSubMenu = opcionSubMenu;
    }

    public boolean isMostrarOpcionesSubMenus() {
        return mostrarOpcionesSubMenus;
    }

    public void setMostrarOpcionesSubMenus(boolean mostrarOpcionesSubMenus) {
        this.mostrarOpcionesSubMenus = mostrarOpcionesSubMenus;
    }

    public String getOpcionPaginaSubMenu() {
        return opcionPaginaSubMenu;
    }

    public void setOpcionPaginaSubMenu(String opcionPaginaSubMenu) {
        this.opcionPaginaSubMenu = opcionPaginaSubMenu;
    }

    public boolean isMostrarPaginasSubMenus() {
        return mostrarPaginasSubMenus;
    }

    public void setMostrarPaginasSubMenus(boolean mostrarPaginasSubMenus) {
        this.mostrarPaginasSubMenus = mostrarPaginasSubMenus;
    }

    public boolean isMostrarModalMsg() {
        return mostrarModalMsg;
    }

    public void setMostrarModalMsg(boolean mostrarModalMsg) {
        this.mostrarModalMsg = mostrarModalMsg;
    }

    public String getUsuarioAut() {
        return usuarioAut;
    }

    public void setUsuarioAut(String usuarioAut) {
        this.usuarioAut = usuarioAut;
    }

    public String getPasswordAut() {
        return passwordAut;
    }

    public void setPasswordAut(String passwordAut) {
        this.passwordAut = passwordAut;
    }

    public boolean isMostrarModalAut() {
        return mostrarModalAut;
    }

    public void setMostrarModalAut(boolean mostrarModalAut) {
        this.mostrarModalAut = mostrarModalAut;
    }

    public String getVersion() {
        return Constantes.VERSION_APP;
    }

    public String getLoggedUser() {
        return usuarioAplicacionDTO.getNombre().toUpperCase();
    }

    public int getTiempoSession() {
        return tiempoSession;
    }

    public void setTiempoSession(int tiempoSession) {
        this.tiempoSession = tiempoSession;
    }

    public boolean isMostrarSubMenuCarguesMasivos() {
        return mostrarSubMenuCarguesMasivos;
    }

    public void setMostrarSubMenuCarguesMasivos(boolean mostrarSubMenuCarguesMasivos) {
        this.mostrarSubMenuCarguesMasivos = mostrarSubMenuCarguesMasivos;
    }
}
