package com.davivienda.sara.base;

import com.davivienda.sara.administracion.constantes.AccionMantenimientoTabla;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.dto.ConfAccesoAplicacionDTO;
import com.davivienda.sara.dto.OccaDTO;
import com.davivienda.sara.dto.OficinaMultiDTO;
import com.davivienda.sara.dto.RegionalDTO;
import com.davivienda.sara.dto.ReintegrosRevisionDTO;
import com.davivienda.sara.dto.UsuarioAplicacionDTO;
import com.davivienda.sara.dto.ZonaDTO;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.entitys.Oficinamulti;
import com.davivienda.sara.entitys.Regional;
import com.davivienda.sara.entitys.Zona;
import com.davivienda.sara.entitys.seguridad.ConfAccesoAplicacion;
import com.davivienda.sara.entitys.seguridad.UsuarioAplicacion;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.ArchivoXLSDali40;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.context.FacesContext;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * BaseObjectContextWeb Descripción : Clase Base para todos las clases
 * ObjectContext Fecha : 23/05/2008 03:25:05 PM
 *
 * @author : jjvargas
 *
 */
public class BaseObjectContextWeb {

    /**
     * Configuración de la aplicación
     */
    protected static SaraConfig configApp;
    /**
     * Objeto request
     */
    protected HttpServletRequest request;
    /**
     * Objeto response
     */
    protected HttpServletResponse response;
    /**
     * Listado de errores que generan los procesos de creación de la respuesta
     */
    protected Collection<String> errores = new ArrayList<String>();
    /**
     * Utilzada para enviar un error con notación JSON
     */
    protected StringBuffer errorBuffer = new StringBuffer();
    /**
     * Utilizada para enviar un error con formato writer
     */
    protected StringBuffer errorWriter = new StringBuffer("<textarea>");

    /**
     * Crea una nueva instancia de BaseObjectContext
     *
     * @param request el request
     * @param response el response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public BaseObjectContextWeb(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
    }

    public SaraConfig getConfigApp() {
        return configApp;
    }

    public static void setConfigApp(SaraConfig configApp) {
        BaseObjectContextWeb.configApp = configApp;
    }

    /**
     * Asigna el objeto <code>valor</code> al atributo <code>nombre</code>
     *
     * @param nombre Nombre del atributo
     * @param valor Objecto
     */
    //se cambio era protected
    public void setAtributo(String nombre, Object valor) {
        request.setAttribute(nombre, valor);
    }
    
    /**
     * obtiene el objeto indicado 
     *
     * @param nombre Nombre del atributo
     */
    //se cambio era protected
    public Object getAtributo(String nombre) {
       return request.getAttribute(nombre);
    }

    /**
     * Lee del request el atributo <code>accion</code>, si no existe retorna
     * vacio
     *
     * @return String
     */
    public String getAtributoAccion() {
        String accion = request.getParameter("accion");
        return ((accion != null) ? accion : "paginaError");
    }

    public AccionMantenimientoTabla getAccionMantenimientoTabla() {
        return AccionMantenimientoTabla.getAccionMantenimientoTabla(getAtributoAccion());
    }

    /**
     * Retorna el número de página a consultar para las consultas
     *
     * @return
     */
    public Integer getAtributoPagina() {
        return this.getAtributoInteger("pagina", false);
    }

    /**
     * Retorna el número de registros que se deben consultar por página
     *
     * @return
     */
    public Integer getAtributoRegsPorPagina() {
        return this.getAtributoInteger("regsPorPagina", false);
    }

    /**
     * Lee del request el atributo <code>nombre</code> y lo retorna como un
     * objeto Calendar, si existe un error de conversión retorna null y graba un
     * error en el arreglo de errores
     *
     * @param nombre Nombre del atributo
     * @return Fecha en formato Calendar , null si no se puede crear el objeto
     * @throws IllegalArgumentException
     */
    protected Calendar getAtributoCalendar(String nombre) throws IllegalArgumentException {
        Calendar calendar = null;
        String fechaStr = (String) request.getAttribute(nombre);
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr);
        } catch (IllegalArgumentException ex) {
            BaseObjectContextWeb.configApp.loggerApp.severe(ex.getMessage());
            throw ex;
        }
        return calendar;
    }

    /**
     * Lee del request el atributo <code>nombre</code> y lo retorna como un
     * objeto Date, si existe un error de conversión retorna null y graba un
     * error en el arreglo de errores
     *
     * @param nombre Nombre del atributo
     * @return objeto Date
     * @throws IllegalArgumentException
     */
    protected Date getAtributoDate(String nombre) throws IllegalArgumentException {
        Date fecha = null;
        try {
            fecha = getAtributoCalendar(nombre).getTime();
        } catch (Exception ex) {
            BaseObjectContextWeb.configApp.loggerApp.severe(ex.getMessage());
            throw new IllegalArgumentException(ex);
        }
        return fecha;
    }

    /**
     * Lee del request el atributo <code>nombre</code> y lo retorna como un
     * objeto String, si existe un error de conversión retorna vacio y graba un
     * error en el arreglo de errores
     *
     * @param nombre Nombre del atributo
     * @return Cadena string
     */
    public String getAtributoString(String nombre) {
        String param = (String) request.getAttribute(nombre);
        return ((param != null) ? param : "");
    }

    /**
     * Lee del request el atributo <code>nombre</code> y lo retorna como un
     * objeto Integer, si existe un error de conversión retorna 0 y graba un
     * error en el arreglo de errores
     *
     * @param nombre
     * @return 0 si no se puede crear el objeto
     * @throws IllegalArgumentException
     */
    protected Integer getAtributoInteger(String nombre) throws IllegalArgumentException {
        Integer objetoInt = null;
        try {
            String codigo = (String) request.getAttribute(nombre);
            if (codigo != null) {
                if (!codigo.trim().equals("")) {
                    objetoInt = Integer.valueOf(codigo);
                }
            }
        } catch (Exception ex) {
            BaseObjectContextWeb.configApp.loggerApp.severe(ex.getMessage());
            throw new IllegalArgumentException(ex);
        }
        return objetoInt;
    }

    protected Short getAtributoShort(String nombre) {
        Short objetoShort = null;
        try {
            String codigo = request.getParameter(nombre);
            if (codigo != null) {
                if (!codigo.trim().equals("")) {
                    objetoShort = Short.valueOf(codigo);
                }
            }
        } catch (Exception ex) {
            setError("Parámetro " + nombre + " no se puede convertir a Short " + ex.getMessage(), CodigoError.PARAMETRO_INVALIDO, ex);
        }
        return objetoShort;
    }

    protected Long getAtributoLong(String nombre) {
        Long objetoLong = null;
        try {
            String codigo = request.getParameter(nombre);
            if (codigo != null) {
                if (!codigo.trim().equals("")) {
                    objetoLong = Long.valueOf(codigo);
                }
            }
        } catch (Exception ex) {
            setError("Parámetro " + nombre + " no se puede convertir a Long " + ex.getMessage(), CodigoError.PARAMETRO_INVALIDO, ex);
        }
        return objetoLong;

    }

    protected BigInteger getAtributoBigInteger(String nombre) {
        BigInteger objetoLong = null;
        try {
            objetoLong = BigInteger.valueOf(getAtributoLong(nombre));
        } catch (Exception ex) {
            setError("Parámetro " + nombre + " no se puede convertir a Long " + ex.getMessage(), CodigoError.PARAMETRO_INVALIDO, ex);
        }
        return objetoLong;
    }

    protected BigDecimal getAtributoBigDecimal(String nombre) {
        BigDecimal objetoBigDecimal = null;
        try {
            objetoBigDecimal = BigDecimal.valueOf(getAtributoLong(nombre));
        } catch (Exception ex) {
            setError("Parámetro " + nombre + " no se puede convertir a BigDecimal " + ex.getMessage(), CodigoError.PARAMETRO_INVALIDO, ex);
        }
        return objetoBigDecimal;
    }

    /**
     * Lee del request el atributo <code>nombre</code> de tipo agrupamiento un
     * ComboBox o List y lo retorna como un objeto Integer
     *
     * @param nombre
     * @param campoEsSeleccion
     * @return Integer 0 si no se puede crear el objeto
     * @throws IllegalArgumentException
     */
    protected Integer getAtributoInteger(String nombre, boolean campoEsSeleccion) throws IllegalArgumentException {
        if (campoEsSeleccion) {
            return getAtributoInteger(nombre.trim() + "_selected");
        } else {
            return getAtributoInteger(nombre);
        }
    }

    public void setAtributoJsonArray(JSONArray json) {
        request.setAttribute("events", json.toString());
    }

    public void setAtributoReintegrosReversados(List<ReintegrosRevisionDTO> listaData) {
        request.setAttribute("reintegrosReversados", listaData);
    }
    
    
    public List<ReintegrosRevisionDTO> getAtributoReintegrosReversados() {
        return (List<ReintegrosRevisionDTO>) request.getAttribute("reintegrosReversados");
    }
    
    public JSONArray getAtributoJsonArray(String nombre) throws JSONException {
        JSONArray myArrayList = null;

        if (request.getAttribute(nombre) != null) {
            myArrayList = new JSONArray(String.valueOf(request.getAttribute(nombre)));
        }
        return myArrayList;
    }

    /**
     * Retorna el nombre de un diario electronico EDC a partir de los atributos
     * obtenidos por getCodigoCajeroComboBox() y getFechaCalendar() El código
     * del cajero debe venir en formato de un comboBox
     *
     * @return
     */
    public String getNombreDiarioElectronicoEDC() {
        Integer codCajero = getCodigoCajeroComboBox();
        Calendar fecha = getFechaCalendar();
        return com.davivienda.utilidades.edc.Edc.obtenerNombreArchivo(codCajero, fecha);
    }

    /**
     * Asigna un elemento de error a la respuesta
     *
     * @param mensaje
     */
    public void setError(String mensaje) {
        setError(mensaje, 9999, null);
    }

    /**
     * Asigna un elemento de error a la respuesta e imprime en el logger de la
     * configuración
     *
     * @param mensaje
     * @param codError error de aplicacion
     * @param ex Excepción generada
     */
    public void setError(String mensaje, Integer codError, Exception ex) {

    }

    /**
     * Asigna un elemento de error a la respuesta e imprime en el logger de la
     * configuración
     *
     * @param mensaje
     * @param codError CodigoError
     * @param ex
     */
    public void setError(String mensaje, CodigoError codError, Exception ex) {
        setError(mensaje, codError.getCodigo(), ex);
    }

    /**
     * Genera un error de aplicación
     *
     * @param mensaje
     * @param codError
     */
    public void setError(String mensaje, Integer codError) {
        setError(mensaje, codError, null);
    }

    public String prepararEnvioError() {
        String cadena = "";
        if (errorBuffer.length() > 3) {
            errorBuffer.setCharAt(errorBuffer.length() - 1, ' ');
            cadena = errorBuffer.toString();
        }
        return cadena;
    }

    /**
     * Direccionamiento forward
     *
     * @param dirUrl URL de la página
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void forward(String dirUrl) throws ServletException, IOException {
        setAtributo("listaError", errores);
        setAtributo("error", prepararEnvioError());
        request.getRequestDispatcher(dirUrl).forward(request, response);
    }

    /**
     * Direcciona hacia otra página la respuesta del requerimiento con
     * validación de errores
     *
     * @param dirUrl Dirección de la página
     * @param manejarJSonError Si es true se verifica que no existan errores en
     * el proceso de ser asi se envía hacia la página de error
     * administracion/error.jsp
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void forward(String dirUrl, boolean manejarJSonError) throws ServletException, IOException {
        if (manejarJSonError) {
            if (errorBuffer.length() > 3) {
                setAtributo("error", prepararEnvioError());
                dirUrl = "administracion/error.jsp";
            }
        }
        request.getRequestDispatcher(dirUrl).forward(request, response);
    }

    public void responderConError(Integer codError, String mensaje) {
        try {
            response.sendError(codError, mensaje);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.WARNING, ex.getMessage(), ex);
        }
    }

    /**
     * Toma la fecha de la solicitud el nombre del param debe ser
     * <code>fecha</code>
     *
     * @return Date
     */
    public Date getFecha() {
        return getAtributoDate("fecha");
    }

    public Date getFechaReversado() {
        return getAtributoDate("(fechaReversado");
    }
    
    /**
     * Toma la fecha de la solicitud el nombre del param debe ser
     * <code>fecha</code>
     *
     * @return Calendar
     */
    public Calendar getFechaCalendar() {
        return getAtributoCalendar("fecha");
    }

    /**
     * Toma el atributo fechaInicial de la solicitud
     *
     * @return Calendar
     * @throws IllegalArgumentException
     */
    public Calendar getAtributoFechaInicial() throws IllegalArgumentException {
        return getAtributoCalendar("fechaInicial");
    }

    public void setAtributoFechaInicial(String fechaInicial) throws IllegalArgumentException {
        request.setAttribute("fechaInicial", fechaInicial);
    }

    public void setAtributoFechaHoraInicial(String fechaInicial, String horaInicial) throws IllegalArgumentException {
        request.setAttribute("fechaInicial", fechaInicial);
        request.setAttribute("horaInicial", horaInicial);
    }

    public void setAtributoTipoReporte(String tipoReporte) throws IllegalArgumentException {
        request.setAttribute("tipoReporte", tipoReporte);
    }

    /**
     * Toma el atributo fechaInicial y horaInicial de la solicitud
     *
     * @return Calendar
     * @throws IllegalArgumentException
     */
    public Calendar getAtributoFechaHoraInicial() throws IllegalArgumentException {
        String fechaStr = (String) request.getAttribute("fechaInicial");
        String horaStr = (String) request.getAttribute("horaInicial");

        fechaStr = fechaStr.concat(horaStr);
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (IllegalArgumentException ex) {
            BaseObjectContextWeb.configApp.loggerApp.severe(ex.getMessage());
            throw ex;
        }
        return calendar;
    }

    public void setAtributoFechaHoraFinal(String fechaFinal, String horaFinal) throws IllegalArgumentException {
        request.setAttribute("fechaFinal", fechaFinal);
        request.setAttribute("horaFinal", horaFinal);
    }

    /**
     * Toma el atributo fechaFinal y horaFinal de la solicitud
     *
     * @return Calendar
     * @throws IllegalArgumentException
     */
    public Calendar getAtributoFechaHoraFinal() throws IllegalArgumentException {
        String fechaStr = (String) request.getAttribute("fechaFinal");
        String horaStr = (String) request.getAttribute("horaFinal");
        fechaStr = fechaStr.concat(horaStr);
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (IllegalArgumentException ex) {
            BaseObjectContextWeb.configApp.loggerApp.severe(ex.getMessage());
            throw ex;
        }
        return calendar;
    }

    /**
     * Toma el atributo fechaFianl de la solicitud
     *
     * @return
     * @throws IllegalArgumentException
     */
    public Calendar getAtributoFechaFinal() throws IllegalArgumentException {
        return getAtributoCalendar("fechaFinal");
    }

    public Integer getCodigoCajero() {
        return getAtributoInteger("codigoCajero");
    }

    public Integer getAtributoCodigoCajeroInteger() {
        return (Integer) request.getAttribute("codigoCajero");
    }

    public Integer getCodigoOficina() {
        return getAtributoInteger("codigoOficinaMultifuncional");
    }

    public Integer getNumeroTransaccion() {
        return getAtributoInteger("numeroTransaccion");
    }

    public String getReferencia() {
        return getAtributoString("referencia");
    }

    public String getProductoOrigen() {
        return getAtributoString("prodOrigen");
    }

    public String getTarjeta() {
        return getAtributoString("tarjeta");
    }

    public Integer getCodigoOcca() {
        return getAtributoInteger("codigoOcca");
    }

    /**
     * Obtiene del request el atributo observacion , utilizado para grabar una
     * nota de movimiento
     *
     * @return
     */
    public String getNotaObservacion() {
        return getAtributoString("observacion");
    }

    /**
     * btiene del request el atributo idRegistro , utilizado para grabar una
     * nota de movimiento
     *
     * @return
     */
    public BigInteger getIdRegMovimiento() {
        return getAtributoBigInteger("idRegistro");
    }

    public ServletOutputStream getServletOutputStream() throws IOException {
        response.setContentType("application/octet-stream");
        return response.getOutputStream();
    }

    /**
     * Retorna la estructura JSON de privilegios de usuario
     *
     * @param usuarioAplicacion
     * @return
     * @throws UsuarioAplicacionDTO
     */
    /**
     * Retorna la estructura JSON de privilegios de usuario
     *
     * @param usuarioAplicacion
     * @return
     * @throws UsuarioAplicacionDTO
     */
    public ConfAccesoAplicacionDTO getConfAccesoAplicacionDTO(ConfAccesoAplicacion confAplicacion) {

        ConfAccesoAplicacionDTO confAccesoAplicacionDTO = new ConfAccesoAplicacionDTO();
        if (confAplicacion != null && confAplicacion.getUsuarioAplicacion() != null) {
            confAccesoAplicacionDTO.setUsuarioAplicacion(confAplicacion.getUsuarioAplicacion().getUsuario());
            confAccesoAplicacionDTO.setServicioAplicacion(confAplicacion.getServicioAplicacion().getDescripcion());
            confAccesoAplicacionDTO.setCodigoServicioAplicacion(confAplicacion.getConfAccesoAplicacionPK().getServicio());
            confAccesoAplicacionDTO.setActualizar(confAplicacion.getActualizar().compareTo(new Short("1")) == 0);
            confAccesoAplicacionDTO.setAdministrar(confAplicacion.getAdministrar().compareTo(new Short("1")) == 0);
            confAccesoAplicacionDTO.setBorrar(confAplicacion.getBorrar().compareTo(new Short("1")) == 0);
            confAccesoAplicacionDTO.setConsultar(confAplicacion.getConsultar().compareTo(new Short("1")) == 0);
            confAccesoAplicacionDTO.setCrear(confAplicacion.getCrear().compareTo(new Short("1")) == 0);
            confAccesoAplicacionDTO.setEjecutar(confAplicacion.getEjecutar().compareTo(new Short("1")) == 0);
            confAccesoAplicacionDTO.setVersion(confAplicacion.getVersion());
        }
        return confAccesoAplicacionDTO;
    }

    public ZonaDTO getZonaDTO(Zona zona) {

        ZonaDTO zonaDTO = new ZonaDTO();

        zonaDTO.setIdZona(zona.getIdZona());
        zonaDTO.setZona(zona.getZona());
        zonaDTO.setDescripcionZona(zona.getDescripcionZona());

        return zonaDTO;
    }

    public OccaDTO getOccaDTO(Occa occa) {

        OccaDTO occaDTO = new OccaDTO();

        occaDTO.setCodigoOcca(occa.getCodigoOcca());
        occaDTO.setNombre(occa.getNombre());
        occaDTO.setNombreArchivoMovimiento(occa.getNombreArchivoMovimiento());
        occaDTO.setUbicacionArchivoMovimiento(occa.getUbicacionArchivoMovimiento());
        occaDTO.setCodTerminalCaja(occa.getCodigoTerminal());

        return occaDTO;
    }

    public RegionalDTO getRegionalDTO(Regional regional) {

        RegionalDTO regionalDTO = new RegionalDTO();

        regionalDTO.setIdRegional(regional.getIdRegional());
        regionalDTO.setRegional(regional.getRegional());

        return regionalDTO;
    }

    public OficinaMultiDTO getOfincinaMultiDTO(Oficinamulti oficina) {

        OficinaMultiDTO oficinaMultiDTO = new OficinaMultiDTO();

        oficinaMultiDTO.setCodigooficinamulti(oficina.getCodigooficinamulti());
        oficinaMultiDTO.setNombre(oficina.getNombre());
        oficinaMultiDTO.setCodigocentroefectivo(oficina.getCodigocentroefectivo());

        return oficinaMultiDTO;
    }

    /**
     * Toma el código de cajero desde una selección de un combo box el nombre
     * del param debe ser <code>codigoCajero</code>
     *
     * @return Integer
     */
    public Integer getCodigoCajeroComboBox() {
        return getAtributoInteger("codigoCajero", true);
    }

    public Integer getAtributoCodigoCajeroComboBox() {
        return (Integer) request.getAttribute(("codigoCajero"));
    }

    public void setCodigoCajeroComboBox(Integer codigoCajero) {
        request.setAttribute("codigoCajero", codigoCajero);
    }

    public void reponderEnWriter() {
        java.io.PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            //out.println("<textarea></textarea>");
            out.println(errorWriter.toString() + "</textarea>");
            out.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "No se puede obtener o escribir en el writer ", ex);
        }

    }

    /**
     * Retorna la deirección IP del usuario
     *
     * @return String
     */
    public String getdireccionIP() {
        return request.getRemoteAddr();
    }

    /**
     * Retona el nombre del usuario que ingreso a la aplicación
     *
     * @return
     */
    public UsuarioAplicacion getUsuarioEnSesion() {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        InitBean initBean = (InitBean) viewMap.get("initBean");
        return initBean.getUsuarioAplicacion();
    }

    public String getIdUsuarioEnSesion() {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        InitBean initBean = (InitBean) viewMap.get("initBean");
        return initBean.getUsuarioAplicacion().getUsuario();
    }

    /**
     * Asigna el atributo <code>usuario</code> a la sesion
     *
     * @param usuario
     */
    public void setUsuarioEnSesion(String usuario) {
        request.getSession().setAttribute("usuario", usuario);
    }

    /**
     * Retona el perfil del usuario que ingreso a la aplicación
     *
     * @return
     */
    public String getPerfilUsuarioEnSesion() {
        return (String) request.getSession().getAttribute("usuarioPerfil");
    }

    /**
     * Asigna los privilegios de acceso del usuario en sesion como un
     * Map<idServicio,ConfAccesoAplicacion>
     *
     * @param configuracionAcceso
     */
    public void setPrivilegiosAccesoEnSesion(Collection<ConfAccesoAplicacion> configuracionAcceso) {
        Map<Long, ConfAccesoAplicacion> bean = new HashMap<Long, ConfAccesoAplicacion>();
        for (ConfAccesoAplicacion confAccesoAplicacion : configuracionAcceso) {
            bean.put(confAccesoAplicacion.getConfAccesoAplicacionPK().getServicio(), confAccesoAplicacion);
        }
        request.getSession().getServletContext().setAttribute("privilegios", bean);
        //request.getSession().setAttribute("privilegios", bean);
    }

    @SuppressWarnings({"unchecked", "unchecked"})
    public Map<Long, ConfAccesoAplicacion> getPrivilegiosAccesoEnSesion() {
        return (Map<Long, ConfAccesoAplicacion>) request.getSession().getServletContext().getAttribute("privilegios");
    }

    /**
     * Asigna la cedena en formato JSon al request, este atributo es usado por
     * el jsp ajax/comun/respuestaJSon
     *
     * @param cadenaJson
     */
    public void setCadenaJson(String cadenaJson) {
        setAtributo("cadenaJSon", cadenaJson);
    }

    /**
     * Asigna la cadena "Transacción procesada correctamente" como respuesta en
     * un objeto JSon, la redirección siempre se debe hacer a la página
     * ajax/comun/respuestaJSon.jsp o una que maneje la variable cadenaJSon
     */
    public void setRespuestaJSonOk() {
        setAtributo("cadenaJSon", "{\"mensaje\":\"Transacción procesada correctamente\", \"error\":0}");
    }

    /**
     * Asigna la cedena en formato JSon al request mediante el atributo
     * cadenaJSon, este atributo es usado por el jsp ajax/comun/respuestaJSon
     *
     * @param cadenajSon
     */
    public void setRespuestaEnObjJSon(String cadenajSon) {
        setCadenaJson(cadenajSon);
    }

    //metodos ajustes
    public Integer getCodigo_Cajero() {
        return getAtributoInteger("codigo_Cajero");
    }

    public Integer getAtributoCodigo_Cajero() {
        return (Integer.valueOf(request.getAttribute("codigo_Cajero").toString()));
    }

    public void setAtributoCodigo_Cajero(String codigoCajero) {
        request.setAttribute("codigo_Cajero", codigoCajero);
    }

    public void setAtributoCodigo_Occa(Integer codigoOcca) {
        request.setAttribute("codigo_Occa", codigoOcca);
    }

    public Integer getCodigo_Occa() {
        return getAtributoInteger("codigo_Occa");
    }

    public Integer getCodigo_Oficina() {
        return getAtributoInteger("codigo_Oficina");
    }

    public BigDecimal getValorAjuste() {
        return getAtributoBigDecimal("valorAjuste");
    }

    public String getNitTansportadora() {
        return getAtributoString("nit_transportadora");
    }

    //metodos notas credito y debito
    public BigDecimal getValor() {
        return getAtributoBigDecimal("valor");
    }

    public BigDecimal getAtributoValor() {
        return (BigDecimal) request.getAttribute("valor");
    }

    public void setAtributoValor(BigDecimal valor) {
        request.setAttribute("valor", valor);
    }

    public String getCuenta() {
        return getAtributoString("cuenta");
    }

    public String getAtributoCuenta() {
        return (String) request.getAttribute("cuenta");
    }

    public void setAtributoCuenta(String valor) {
        request.setAttribute("cuenta", valor);
    }

    public String getTalon() {
        return getAtributoString("talon");
    }

    public String getAtributoTalon() {
        return (String) request.getAttribute("talon");
    }

    public Notasdebito getAtributoNotaDebito() {
        return (Notasdebito) request.getAttribute("notaDebito");
    }

    public void setAtributoNotaDebito(Notasdebito notadebito) {
        request.setAttribute("notaDebito", notadebito);
    }

    public ReintegrosRevisionDTO getAtributoReintegro() {
        return (ReintegrosRevisionDTO) request.getAttribute("reintegro");
    }

    public void setAtributoReintegro(ReintegrosRevisionDTO reintegro) {
        request.setAttribute("reintegro", reintegro);
    }
    
    public void setAtributoTalon(String valor) {
        request.setAttribute("talon", valor);
    }

    public void setAtributoTipoCuenta(String valor) {
        request.setAttribute("tipoCuenta", valor);
    }

    public String getUsuarioLdap() {
        return getAtributoString("usuarioL");
    }

    public String getClaveLdap() {
        return getAtributoString("clave");
    }

    //EJEMPLO PRUEBA
    //metodos notas credito y debito
    public Integer getCodigoPrueba() {
        return getAtributoInteger("codigo");
    }

    public void enviarArchivoXLS(ArchivoXLS archivo) throws IOException {
        try {

            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/octet-stream");
            res.setHeader("Content-disposition", "attachment; filename=" + archivo.getNombreArchivo());
            ServletOutputStream out = res.getOutputStream();
            archivo.getLibro().write(out);
            out.flush();
            out.close();
            FacesContext faces = FacesContext.getCurrentInstance();
            faces.responseComplete();

        } catch (IOException ex) {
            getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }
    }

    public void enviarArchivoXLS(ArchivoXLSDali40 archivo) throws IOException {
        try {

            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            res.setContentType("application/octet-stream");
            res.setHeader("Content-disposition", "attachment; filename=" + archivo.getNombreArchivo());
            ServletOutputStream out = res.getOutputStream();
            archivo.getLibro().write(out);
            out.flush();
            out.close();
            FacesContext faces = FacesContext.getCurrentInstance();
            faces.responseComplete();

        } catch (IOException ex) {
            getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }
    }

}
