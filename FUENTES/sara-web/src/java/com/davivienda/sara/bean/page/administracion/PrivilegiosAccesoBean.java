/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.administracion;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.TareaBdConfAccesoAplicacion;
import com.davivienda.sara.dto.ConfAccesoAplicacionDTO;
import com.davivienda.sara.dto.UsuarioAplicacionDTO;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.ServicioAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.sara.tablas.confaccesoaplicacion.session.ConfAccesoAplicacionSessionLocal;
import com.davivienda.sara.tareas.usuarioaplicacion.session.UsuarioAplicacionTareasSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import com.davivienda.sara.tablas.usuarioaplicacion.session.UsuarioAplicacionSessionLocal;
import java.util.logging.Level;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "privilegiosAccesoBean")
@ViewScoped
public class PrivilegiosAccesoBean extends BaseBean implements Serializable {

    public String usuario;
    public String tipoAcceso;
    private boolean consultar;
    private boolean actualizar;
    private boolean crear;
    private boolean borrar;
    private boolean ejecutar;

    public String usuarioSeleccionado;
    private String usuarioSeleccionadoTipoAcceso;
    private String tipoAccesoSeleccionado;
    private List<SelectItem> listaAccesos;
    private List<SelectItem> listaUsuarios;
    private List<SelectItem> listaUsuariosGeneral;
    private List<ConfAccesoAplicacionDTO> privilegiosAcceso;

    public boolean mostrarBtnGrabar;
    public boolean mostrarBtnCancelar;

    public ComponenteAjaxObjectContextWeb objectContext;

    @EJB
    ConfAccesoAplicacionSessionLocal confAccesoAplicacionSession;
    @EJB
    UsuarioAplicacionSessionLocal usuarioAplicacionSession;
    @EJB
    UsuarioAplicacionTareasSessionLocal usuarioAplicacionTareasSessionLocal;

    /**
     * Creates a new instance of PrivilegiosAccesoBean
     */
    @PostConstruct
    public void PrivilegiosAccesoBean() {
        String error = "";
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            listaAccesos = new ArrayList<SelectItem>();
            listaUsuarios = new ArrayList<SelectItem>();
            if (objectContext != null) {
                usuarioInicial();
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            error = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void usuarioInicial() {
        try {
            System.err.println("PrivilegiosAccesoBean-usuarioInicial: Inicio");
            Collection<ConfAccesoAplicacion> itemsAccesos = confAccesoAplicacionSession.getTodos();
            this.privilegiosAcceso = objectContext.getConfAccesoAplicacionCB(itemsAccesos);
            listaAccesos = cargarListaAccesos(this.privilegiosAcceso);
            Collection<UsuarioAplicacion> itemsUsuarios = usuarioAplicacionSession.getTodos();
            this.listaUsuariosGeneral = cargarListaUsuariosGeneral(objectContext.getUsuarioAplicacionCB(itemsUsuarios));
            listaUsuarios = cargarListaUsuarios(this.privilegiosAcceso);

            if (!this.privilegiosAcceso.isEmpty()) {
                this.usuarioSeleccionado = String.valueOf(this.listaUsuarios.get(1).getValue());
                this.usuarioSeleccionadoTipoAcceso = buscarCodUsuario(this.privilegiosAcceso.get(0).getUsuarioAplicacion());
                this.tipoAccesoSeleccionado = buscarCodTipoAcceso(this.privilegiosAcceso.get(0).getServicioAplicacion());
                System.err.println("ENTRO A USUARIO INICIAL");
                for (ConfAccesoAplicacionDTO conf : privilegiosAcceso) {
                    this.setActualizar(conf.isActualizar());
                    this.setConsultar(conf.isConsultar());
                    this.setCrear(conf.isCrear());
                    this.setBorrar(conf.isBorrar());
                    this.setEjecutar(conf.isEjecutar());
                    break;
                }
            }
            this.mostrarBtnGrabar = false;
            this.mostrarBtnCancelar = false;
            System.err.println("PrivilegiosAccesoBean-usuarioInicial: Final");
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void nuevoUsuario() {
        this.mostrarBtnCancelar = true;
        this.mostrarBtnGrabar = true;
        this.usuarioSeleccionadoTipoAcceso = "";
        this.tipoAccesoSeleccionado = "";
        this.actualizar = false;
        this.consultar = false;
        this.crear = false;
        this.borrar = false;
        this.ejecutar = true;
    }

    public void grabarUsuario() {
        String resp = "";
        Date fecha = new Date();
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        InitBean initBean = (InitBean) viewMap.get("initBean");
        fecha = com.davivienda.utilidades.conversion.Fecha.getDateHoy();
        try {
            if (null == this.usuarioSeleccionadoTipoAcceso || this.usuarioSeleccionadoTipoAcceso.isEmpty()) {
                abrirModal("SARA", "Por favor seleccionar un Usuario", null);
            } else if (null == this.tipoAccesoSeleccionado || this.tipoAccesoSeleccionado.isEmpty()) {
                abrirModal("SARA", "Por favor seleccionar un Tipo de Acceso", null);
            } else {
                ConfAccesoAplicacion registroEntity = cargarPrivilegioAplicacion();
                usuarioAplicacionTareasSessionLocal.guardarRegTareasAdminUsuario(registroEntity.getConfAccesoAplicacionPK().getUsuario(),
                        registroEntity.getConfAccesoAplicacionPK().getServicio(),
                        (short) TareaBdConfAccesoAplicacion.INSERTAREGCONFACCESO.getTareaBD().intValue(),
                        initBean.getObjectContext().getUsuarioEnSesion().getUsuario(),//AQUI va el usuario en session
                        "",
                        fecha);
                usuarioAplicacionTareasSessionLocal.crearActualizarAccesoUsuario(registroEntity);
                //confAccesoAplicacionSessionLocal.AddBorrarRegAccesoUsuario();
                Collection<ConfAccesoAplicacion> itemsAccesos = confAccesoAplicacionSession.getTodos();
                this.privilegiosAcceso = objectContext.getConfAccesoAplicacionCB(itemsAccesos);
                listaUsuarios = cargarListaUsuarios(this.privilegiosAcceso);
                abrirModal("SARA", "Se ha guardado exitosamente el registro", null);
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (EntityServicioExcepcion ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

        this.mostrarBtnCancelar = false;
        this.mostrarBtnGrabar = false;
        System.err.println("PrivilegiosBean-grabar usuario:" + this.usuarioSeleccionadoTipoAcceso + " Tipo Acceso: " + this.tipoAccesoSeleccionado);

    }

    public void modificarUsuario() {
        this.mostrarBtnCancelar = true;
        this.mostrarBtnGrabar = true;
    }

    public void buscarRegistroGeneral() {
        String user = "";
        String usuario = "";
        String tipoAcceso = "";
        user = obtenerUsuario("BUSCAR");
        if (!user.equalsIgnoreCase("")) {
            String[] split = user.split("-");
            usuario = split[0].trim();
            tipoAcceso = split[1].trim();
        }
        for (ConfAccesoAplicacionDTO conf : this.privilegiosAcceso) {
            if (conf.getUsuarioAplicacion().equalsIgnoreCase(usuario) && conf.getServicioAplicacion().equalsIgnoreCase(tipoAcceso)) {
                this.usuarioSeleccionadoTipoAcceso = buscarCodUsuario(usuario);
                this.tipoAccesoSeleccionado = String.valueOf(conf.getCodigoServicioAplicacion());
                this.actualizar = conf.isActualizar();
                this.consultar = conf.isConsultar();
                this.crear = conf.isCrear();
                this.borrar = conf.isBorrar();
                this.ejecutar = conf.isEjecutar();
                break;
            }
        }
    }

    public void buscarRegistro() {
        String user = "";
        user = obtenerUsuario("BUSCARINTERNO");
        for (ConfAccesoAplicacionDTO conf : this.privilegiosAcceso) {
            if (conf.getUsuarioAplicacion().equalsIgnoreCase(user)) {
                this.tipoAccesoSeleccionado = buscarCodTipoAcceso(conf.getServicioAplicacion());
                this.actualizar = conf.isActualizar();
                this.consultar = conf.isConsultar();
                this.crear = conf.isCrear();
                this.borrar = conf.isBorrar();
                this.ejecutar = conf.isEjecutar();
                break;
            }
        }
    }

    public void borrarRegistro() {
        String resp = "";
        Date fecha = new Date();
        fecha = com.davivienda.utilidades.conversion.Fecha.getDateHoy();

        try {

            ConfAccesoAplicacion registroEntity = cargarPrivilegioAplicacion();
            //guardarRegBorrarConfAcceso(String usuario, long servicio,short borratodousuario,String usuarioSession,Date fecha )

            usuarioAplicacionTareasSessionLocal.guardarRegTareasAdminUsuario(registroEntity.getConfAccesoAplicacionPK().getUsuario(),
                    registroEntity.getConfAccesoAplicacionPK().getServicio(),
                    (short) TareaBdConfAccesoAplicacion.BORRAREGCONFACCESO.getTareaBD().intValue(),
                    objectContext.getUsuarioEnSesion().getUsuario(),//AQUI va el usuario en session
                    "",
                    fecha);
            usuarioAplicacionTareasSessionLocal.addBorrarRegAccesoUsuarioDesdeApp();
            //confAccesoAplicacionSessionLocal.AddBorrarRegAccesoUsuario();
            Collection<ConfAccesoAplicacion> itemsAccesos = confAccesoAplicacionSession.getTodos();
            this.privilegiosAcceso = objectContext.getConfAccesoAplicacionCB(itemsAccesos);
            listaUsuarios = cargarListaUsuarios(this.privilegiosAcceso);
            usuarioInicial();
            abrirModal("SARA", "Se ha borrado exitosamente el registro", null);
            resp = JSon.getJSonRespuesta(CodigoError.SIN_ERROR.getCodigo(), "Se ha borrado exitosamente el registro");

        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", "Ha llegado al último registro ", null);
        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (EntityServicioExcepcion ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

        this.mostrarBtnGrabar = false;
        this.mostrarBtnCancelar = false;
        System.err.println("PrivilegiosBean-borrar usuario:" + this.usuarioSeleccionadoTipoAcceso + " Tipo Acceso: " + this.tipoAccesoSeleccionado);

    }

    public void anteriorRegistro() {
        String user = "";
        String usuario = "";
        String tipoAcceso = "";
        user = obtenerUsuario("BUSCAR");
        if (!user.equalsIgnoreCase("")) {
            String[] split = user.split("-");
            usuario = split[0].trim();
            tipoAcceso = split[1].trim();
        }
        user = buscarCodUsuario(usuario);
        int contadorAnterior = 0;
        for (ConfAccesoAplicacionDTO confg : this.privilegiosAcceso) {
            String codUsuario = buscarCodUsuario(confg.getUsuarioAplicacion());
            if (codUsuario.equalsIgnoreCase(user)
                    && this.tipoAccesoSeleccionado.equalsIgnoreCase(String.valueOf(confg.getCodigoServicioAplicacion()))) {
                contadorAnterior = contadorAnterior - 1;
                int actual = contadorAnterior;
                if (actual < 0) {
                    usuarioInicial();
                    break;
                } else {
                    ConfAccesoAplicacionDTO anteriorConf = this.privilegiosAcceso.get(actual);
                    this.usuarioSeleccionado = buscarCodUsrTipo(anteriorConf.getUsuarioAplicacion(), anteriorConf.getServicioAplicacion());
                    this.usuarioSeleccionadoTipoAcceso = buscarCodUsuario(anteriorConf.getUsuarioAplicacion());
                    this.tipoAccesoSeleccionado = buscarCodTipoAcceso(anteriorConf.getServicioAplicacion());
                    this.actualizar = anteriorConf.isActualizar();
                    this.consultar = anteriorConf.isConsultar();
                    this.crear = anteriorConf.isCrear();
                    this.borrar = anteriorConf.isBorrar();
                    this.ejecutar = anteriorConf.isEjecutar();
                    break;
                }
            } else {
                contadorAnterior = contadorAnterior + 1;
            }
        }
    }

    public void siguienteRegistro() {
        String userActualSeleccionado = this.usuarioSeleccionado;
        String user = "";
        String usuario = "";
        String tipoAcceso = "";
        user = obtenerUsuario("BUSCAR");
        if (!user.equalsIgnoreCase("")) {
            String[] split = user.split("-");
            usuario = split[0].trim();
            tipoAcceso = split[1].trim();
        }
        user = buscarCodUsuario(usuario);
        int contadorAnterior = 0;
        for (ConfAccesoAplicacionDTO confg : this.privilegiosAcceso) {
            String codUsuario = buscarCodUsuario(confg.getUsuarioAplicacion());
            if (codUsuario.equalsIgnoreCase(user)
                    && this.tipoAccesoSeleccionado.equalsIgnoreCase(String.valueOf(confg.getCodigoServicioAplicacion()))) {
                contadorAnterior = contadorAnterior + 1;
                int actual = contadorAnterior;
                if (actual > this.privilegiosAcceso.size()) {
                    usuarioInicial();
                    break;
                } else {
                    ConfAccesoAplicacionDTO anteriorConf = this.privilegiosAcceso.get(actual);
                    this.usuarioSeleccionado = buscarCodUsrTipo(anteriorConf.getUsuarioAplicacion(), anteriorConf.getServicioAplicacion());
                    this.usuarioSeleccionadoTipoAcceso = buscarCodUsuario(anteriorConf.getUsuarioAplicacion());
                    this.tipoAccesoSeleccionado = buscarCodTipoAcceso(anteriorConf.getServicioAplicacion());
                    this.actualizar = anteriorConf.isActualizar();
                    this.consultar = anteriorConf.isConsultar();
                    this.crear = anteriorConf.isCrear();
                    this.borrar = anteriorConf.isBorrar();
                    this.ejecutar = anteriorConf.isEjecutar();
                    break;
                }
            } else {
                contadorAnterior = contadorAnterior + 1;
            }
        }
    }

    public String buscarCodUsuario(String nombreUsr) {
        String codUser = "";
        for (SelectItem usr : listaUsuariosGeneral) {
            if (nombreUsr.equalsIgnoreCase(String.valueOf(usr.getLabel()))) {
                codUser = String.valueOf(usr.getValue());
                break;
            }
        }
        return codUser;
    }

    public String buscarNomUsuario(String codUsr) {
        String nombUsr = "";
        for (SelectItem usr : listaUsuariosGeneral) {
            if (codUsr.equalsIgnoreCase(String.valueOf(usr.getValue()))) {
                nombUsr = usr.getLabel();
                break;
            }
        }
        return nombUsr;
    }

    public String buscarCodUsrTipo(String user, String tipoAcceso) {
        String codUser = "";
        String userCompuesto = user + " - " + tipoAcceso;
        for (SelectItem usr : this.listaUsuarios) {
            if (userCompuesto.equalsIgnoreCase(String.valueOf(usr.getLabel()))) {
                codUser = String.valueOf(usr.getValue());
                break;
            }
        }
        return codUser;

    }

    public String buscarCodTipoAcceso(String nombreTipoAcceso) {
        System.err.println("ENTRO A COD TIPO ACCESPO");
        String codTipoAcceso = "";
        for (SelectItem tipoAcc : listaAccesos) {
            if (nombreTipoAcceso.equalsIgnoreCase(String.valueOf(tipoAcc.getLabel()))) {
                codTipoAcceso = String.valueOf(tipoAcc.getValue());
                break;
            }
        }
        return codTipoAcceso;
    }

    public String exportarExcel() {

        String resp = "";
        Date fecha = new Date();
        Long servicio;
        fecha = com.davivienda.utilidades.conversion.Fecha.getDateHoy();

        try {

            String[] titulosHoja = TituloUsuarioOpciones.tituloHoja;
            titulosHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendar(fecha), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
            String[] titulosColumna = TituloUsuarioOpciones.tituloColumnas;
            Collection<Registro> lineas = new ArrayList<Registro>();
            Collection<ConfAccesoAplicacion> items = confAccesoAplicacionSession.getTodos();
            Short numColumna;

            for (ConfAccesoAplicacion item : items) {
                Registro reg = new Registro();
                numColumna = 0;
                servicio = item.getConfAccesoAplicacionPK().getServicio();
                reg.addCelda(new Celda(numColumna++, item.getConfAccesoAplicacionPK().getUsuario(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getUsuarioAplicacion().getNombre(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, servicio.intValue(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, item.getServicioAplicacion().getDescripcion(), TipoDatoCelda.NORMAL));
                lineas.add(reg);
            }
            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("InformeUsuarios", titulosHoja, titulosColumna, lineas);
            objectContext.enviarArchivoXLS(archivo);

        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            resp = JSon.getJSonRespuesta(CodigoError.ERROR_IO_EN_REGITRO.getCodigo(), "Ha llegado al último registro ");
        } catch (EJBException ex) {
            resp = JSon.getJSonRespuesta(CodigoError.REGISTRO_YA_EXISTE.getCodigo(), Constantes.MSJ_QUERY_ERROR, ex);
        } catch (Exception ex) {
            resp = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_INTERNO, ex);
        }
        return resp;

    }

    public String obtenerUsuario(String operUsuario) {
        String idUsuario = "1";
        String user = "";

        switch (operUsuario) {
            case "BUSCAR":
                idUsuario = this.usuarioSeleccionado;
                for (SelectItem lista : listaUsuarios) {
                    if (String.valueOf(lista.getValue()).equals(idUsuario)) {
                        user = lista.getLabel();
                        this.usuarioSeleccionado = idUsuario;
                        break;
                    }
                }
                break;
            case "BUSCARINTERNO":
                idUsuario = this.usuarioSeleccionadoTipoAcceso;
                for (SelectItem lista : listaUsuariosGeneral) {
                    if (String.valueOf(lista.getValue()).equals(idUsuario)) {
                        user = lista.getLabel();
                        this.usuarioSeleccionadoTipoAcceso = idUsuario;
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return user;
    }

    public void confConsulta(ConfAccesoAplicacionDTO confg) {
        this.usuarioSeleccionadoTipoAcceso = confg.getUsuarioAplicacion();
        this.tipoAccesoSeleccionado = confg.getServicioAplicacion();
        this.setActualizar(confg.isActualizar());
        this.setConsultar(confg.isConsultar());
        this.setCrear(confg.isCrear());
        this.setBorrar(confg.isBorrar());
        this.setEjecutar(confg.isEjecutar());
    }

    private List<SelectItem> cargarListaAccesos(List<ConfAccesoAplicacionDTO> confAccesoAplicacionCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        HashMap<Long, String> mapa = new HashMap<Long, String>();
        for (ConfAccesoAplicacionDTO dto : confAccesoAplicacionCB) {
            if (!mapa.containsKey(String.valueOf(dto.getCodigoServicioAplicacion()))) {

                mapa.put(dto.getCodigoServicioAplicacion(), dto.getServicioAplicacion());
            }
        }
        Map<Long, String> ordenada = new TreeMap<Long, String>(mapa);

        for (Entry<Long, String> e : ordenada.entrySet()) {
            SelectItem item = new SelectItem(String.valueOf(e.getKey()), e.getValue());
            lista.add(item);
            System.out.println("[" + e.getKey() + "=" + e.getValue() + "]");
        }
        return lista;
    }

    private List<SelectItem> cargarListaUsuarios(List<ConfAccesoAplicacionDTO> usuarioAplicacionCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem itemIni = new SelectItem(0, "");
        lista.add(itemIni);
        int cont = 1;
        for (ConfAccesoAplicacionDTO dto : usuarioAplicacionCB) {
            SelectItem item = new SelectItem(cont, dto.getUsuarioAplicacion() + " - " + dto.getServicioAplicacion());
            lista.add(item);
            cont++;
        }
        return lista;
    }

    private List<SelectItem> cargarListaUsuariosGeneral(List<UsuarioAplicacionDTO> usuarioAplicacionCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        int cont = 1;
        for (UsuarioAplicacionDTO dto : usuarioAplicacionCB) {
            SelectItem item = new SelectItem(cont, dto.getUsuario());
            lista.add(item);
            cont++;
        }
        return lista;
    }

    public ConfAccesoAplicacion cargarPrivilegioAplicacion() {
        ConfAccesoAplicacion ConfPrivilegio = null;
        Short shr;
        ServicioAplicacion servicioAplicacion = new ServicioAplicacion();
        UsuarioAplicacion usuarioAplicacion = new UsuarioAplicacion();
        String nomUsr = buscarNomUsuario(SaraUtil.stripXSS(this.usuarioSeleccionadoTipoAcceso, Constantes.REGEX_ACEPTAR_DEFAULT));
        String codTipo = this.tipoAccesoSeleccionado;
        ConfPrivilegio = new ConfAccesoAplicacion(nomUsr, Long.parseLong(codTipo));
        shr = (this.consultar ? new Short("1") : new Short("0"));
        ConfPrivilegio.setConsultar(shr);
        shr = (this.actualizar ? new Short("1") : new Short("0"));
        ConfPrivilegio.setActualizar(shr);
        shr = (this.crear ? new Short("1") : new Short("0"));
        ConfPrivilegio.setCrear(shr);
        shr = (this.borrar ? new Short("1") : new Short("0"));
        ConfPrivilegio.setBorrar(shr);
        shr = (this.ejecutar ? new Short("1") : new Short("0"));
        ConfPrivilegio.setEjecutar(shr);
        ConfPrivilegio.setAdministrar(new Short("0"));

        return ConfPrivilegio;
    }

    public boolean isMostrarBtnGrabar() {
        return mostrarBtnGrabar;
    }

    public void setMostrarBtnGrabar(boolean mostrarBtnGrabar) {
        this.mostrarBtnGrabar = mostrarBtnGrabar;
    }

    public boolean isMostrarBtnCancelar() {
        return mostrarBtnCancelar;
    }

    public void setMostrarBtnCancelar(boolean mostrarBtnCancelar) {
        this.mostrarBtnCancelar = mostrarBtnCancelar;
    }

    /**
     * @return the listaAccesos
     */
    public List<SelectItem> getListaAccesos() {
        return listaAccesos;
    }

    /**
     * @param listaAccesos the listaAccesos to set
     */
    public void setListaAccesos(List<SelectItem> listaAccesos) {
        this.listaAccesos = listaAccesos;
    }

    /**
     * @return the listaUsuarios
     */
    public List<SelectItem> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * @param listaUsuarios the listaUsuarios to set
     */
    public void setListaUsuarios(List<SelectItem> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * @return the usuarioSeleccionado
     */
    public String getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    /**
     * @param usuarioSeleccionado the usuarioSeleccionado to set
     */
    public void setUsuarioSeleccionado(String usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    /**
     * @return the usuarioSeleccionadoTipoAcceso
     */
    public String getUsuarioSeleccionadoTipoAcceso() {
        return usuarioSeleccionadoTipoAcceso;
    }

    /**
     * @param usuarioSeleccionadoTipoAcceso the usuarioSeleccionadoTipoAcceso to
     * set
     */
    public void setUsuarioSeleccionadoTipoAcceso(String usuarioSeleccionadoTipoAcceso) {
        this.usuarioSeleccionadoTipoAcceso = usuarioSeleccionadoTipoAcceso;
    }

    /**
     * @return the tipoAccesoSeleccionado
     */
    public String getTipoAccesoSeleccionado() {
        return tipoAccesoSeleccionado;
    }

    /**
     * @param tipoAccesoSeleccionado the tipoAccesoSeleccionado to set
     */
    public void setTipoAccesoSeleccionado(String tipoAccesoSeleccionado) {
        this.tipoAccesoSeleccionado = tipoAccesoSeleccionado;
    }

    /**
     * @return the consultar
     */
    public boolean isConsultar() {
        return consultar;
    }

    /**
     * @param consultar the consultar to set
     */
    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    /**
     * @return the actualizar
     */
    public boolean isActualizar() {
        return actualizar;
    }

    /**
     * @param actualizar the actualizar to set
     */
    public void setActualizar(boolean actualizar) {
        this.actualizar = actualizar;
    }

    /**
     * @return the crear
     */
    public boolean isCrear() {
        return crear;
    }

    /**
     * @param crear the crear to set
     */
    public void setCrear(boolean crear) {
        this.crear = crear;
    }

    /**
     * @return the borrar
     */
    public boolean isBorrar() {
        return borrar;
    }

    /**
     * @param borrar the borrar to set
     */
    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }

    /**
     * @return the ejecutar
     */
    public boolean isEjecutar() {
        return ejecutar;
    }

    /**
     * @param ejecutar the ejecutar to set
     */
    public void setEjecutar(boolean ejecutar) {
        this.ejecutar = ejecutar;
    }

    /**
     * @return the listaUsuariosGeneral
     */
    public List<SelectItem> getListaUsuariosGeneral() {
        return listaUsuariosGeneral;
    }

    /**
     * @param listaUsuariosGeneral the listaUsuariosGeneral to set
     */
    public void setListaUsuariosGeneral(List<SelectItem> listaUsuariosGeneral) {
        this.listaUsuariosGeneral = listaUsuariosGeneral;
    }

}
