/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.reintegros;

import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalGeneralObjectContext;
import com.davivienda.multifuncional.reintegros.general.ReintegrosMultifuncionalHelperInterface;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.procesos.autenticacion.webservice.session.AutenticacionLdapWSProcesosSessionLocal;
import com.davivienda.multifuncional.reintegros.helper.ReintegrosConsultaNotasDbServletHelper;
import com.davivienda.multifuncional.reintegros.helper.ReintegrosMultiGuardarNotasGeneralServletHelper;
import com.davivienda.multifuncional.tablas.notasdebitomulti.session.NotasDebitoMultiSessionLocal;
import com.davivienda.multifuncional.tablas.procesos.session.ProcesoReintegrosMultiSessionLocal;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.session.ReintegrosMultiEfectivoSessionLocal;
import com.davivienda.multifuncional.ws.reintegrosmulti.session.ReintegrosNotasWsSessionLocal;
import com.davivienda.sara.dto.NotaDebitoMultifuncionalDTO;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "autorizarNotasDebitoMfBean")
@ViewScoped
public class AutorizarNotasDebitoMfBean extends BaseBean implements Serializable {

    @EJB
    NotasDebitoMultiSessionLocal notasDebitoMultiSessionLocal;
    @EJB
    ProcesoReintegrosMultiSessionLocal procesoReintegrosMultiSessionLocal;
    @EJB
    ReintegrosMultiEfectivoSessionLocal reintegrosMultiEfectivoSessionLocal;
    @EJB
    AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal;
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionSession;
    @EJB
    CajeroSessionLocal cajeroSessionLocal;
    @EJB
    ReintegrosNotasWsSessionLocal reintegrosNotasWsSessionLocal;
  
    public ReintegrosMultifuncionalGeneralObjectContext reintegrosGeneralObjectContext;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fechaDesde;
    private String horaDesde;
    private String fechaHasta;
    private String horaHasta;
    private boolean mostrarTabla;
    private List<NotaDebitoMultifuncionalDTO> listaData;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void AutorizarNotasDebitoMfBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            reintegrosGeneralObjectContext = new ReintegrosMultifuncionalGeneralObjectContext(getRequestFaces(), getResponseFaces());
            listaHora = new ArrayList<SelectItem>();
            if (objectContext != null) {
                dataInicial();
            }

       } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void dataInicial() {
        this.fechaDesde = "";
        this.horaDesde = "00:00:00";
        this.fechaHasta = "";
        this.horaHasta = "23:59:59";
        this.listaHora = cargarListaHora();
        this.mostrarTabla = false;
        this.listaData = new ArrayList<NotaDebitoMultifuncionalDTO>();
    }

    public void consultar() {
        System.err.println("AutorizarNotasDebitoMfBean-consultar");
        String respuesta = "";
        try {
            try {
                Date fechaInicial = formatter.parse(this.fechaDesde);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }

            if(null == this.fechaHasta || this.fechaHasta.isEmpty()){
                this.fechaHasta = this.fechaDesde;
            }
            
            ReintegrosMultifuncionalHelperInterface reintegroMultifuncionalGeneralObjectContext = null;
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "autorizarNotasDebito" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.setAtributoFechaHoraInicial(this.fechaDesde, this.horaDesde);
            reintegrosGeneralObjectContext.setAtributoFechaHoraFinal(this.fechaHasta,  this.horaHasta);;
            
            objectContext.getConfigApp().loggerApp.info("AutorizarNotasDebitoMfBean-consultar - fechaInicial " + fechaDesde +" "+ horaDesde + " fechaFinal " + fechaHasta +" "+ horaHasta);
            
            reintegroMultifuncionalGeneralObjectContext = new ReintegrosConsultaNotasDbServletHelper(notasDebitoMultiSessionLocal, reintegrosGeneralObjectContext);
            if (reintegroMultifuncionalGeneralObjectContext != null) {
                respuesta = reintegroMultifuncionalGeneralObjectContext.obtenerDatos();

                System.err.println("AutorizarNotasDebitoMfBean resp: " + respuesta);
                if (respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                } else if (ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "items") != null && ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "items").length() > 0) {
                    construirLista(JSon.getValorAtributo(respuesta, "items"));
                    this.mostrarTabla = true;
                } else {
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                }
            }
        } catch (Exception ex) {
            this.mostrarTabla = false;
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void guardarData() {
        System.err.println("AutorizarNotasDebitoMfBean - guardarData");
        String respuesta = "";
        try {
            ReintegrosMultifuncionalHelperInterface reintegroMultifuncionalHelper = null;
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "guardarNotasDebito" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.setAtributo("usuarioL", obtenerBienInicial().getUsuarioAut());
            reintegrosGeneralObjectContext.setAtributo("clave", obtenerBienInicial().getPasswordAut());
            reintegrosGeneralObjectContext.setAtributoJsonArray(aJSonArray());
            reintegroMultifuncionalHelper = new ReintegrosMultiGuardarNotasGeneralServletHelper(notasDebitoMultiSessionLocal, reintegrosGeneralObjectContext, procesoReintegrosMultiSessionLocal, autenticacionLdapWSProcesosSessionLocal, confModulosAplicacionSession, reintegrosNotasWsSessionLocal, true);
            if (reintegroMultifuncionalHelper != null) {
                respuesta = reintegroMultifuncionalHelper.obtenerDatos();
                if (respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                } else {
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                }
            }
        } catch (Exception ex) {
            this.mostrarTabla = false;
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
        System.err.println("AutorizarNotasDebitoMfBean-guardarData resp: " + respuesta);
    }

    public void mostrarModalAut() {
        obtenerBienInicial().setMostrarModalAut(true);
    }

    private JSONArray aJSonArray() {
        JSONArray cadenaJSon = new JSONArray();
        try {
            JSONObject resp = new JSONObject();
            for (NotaDebitoMultifuncionalDTO item : this.listaData) {

                JSONObject itemJSon = new JSONObject();
                itemJSon.put("idRegistro", item.getIdRegistro());
                itemJSon.put("codigoCajero", item.getCodigoCajero());
                itemJSon.put("codigoOficina", item.getCodigoOficina());
                itemJSon.put("numeroCuenta", item.getNumeroCuenta());
                itemJSon.put("fecha", item.getFecha());
                itemJSon.put("valor", item.getValor());
                itemJSon.put("valorAjustar", SaraUtil.stripXSS(item.getValorAjustar(), Constantes.REGEX_ACEPTAR_NUM_VALOR));
                itemJSon.put("usuarioCrea", item.getUsuarioCrea());
                itemJSon.put("checkSeleccion", item.isCheckSeleccion());
                cadenaJSon.put(itemJSon);
            }

        } catch (Exception ex) {
            abrirModal("SARA", "Error", ex);
        }
        System.err.println("AutorizarNotasDebitoMfBean-guardarData JSonArray: " + cadenaJSon.toString());
        return cadenaJSon;
    }

    private void construirLista(String lista) throws JSONException {
        JSONArray respItems = new JSONArray(lista);

        for (int i = 0; i < respItems.length(); i++) {
            JSONObject itemJSon = respItems.getJSONObject(i);
            NotaDebitoMultifuncionalDTO itemDto = new NotaDebitoMultifuncionalDTO().entidadDTO(itemJSon);
            itemDto.setValorAjustar(SaraUtil.stripXSS(itemDto.getValorAjustar(), Constantes.REGEX_ACEPTAR_NUM_VALOR));
            this.listaData.add(itemDto);
        }
    }

    private List<SelectItem> cargarListaHora() {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        boolean iniciar = true;
        int mn = 0;
        int hr = 0;
        while (iniciar) {
            SelectItem item = null;
            if (hr < 24) {
                if (mn == 0) {
                    if (hr < 10) {
                        item = new SelectItem("0" + hr + ":00:00", "0" + hr + ":00:00");
                    } else {
                        item = new SelectItem(+hr + ":00:00", +hr + ":00:00");
                    }
                    mn += 15;
                } else if (mn < 60) {
                    if (hr < 10) {
                        item = new SelectItem("0" + hr + ":" + mn + ":00", "0" + hr + ":" + mn + ":00");
                    } else {
                        item = new SelectItem(+hr + ":" + mn + ":00", +hr + ":" + mn + ":00");
                    }
                    mn += 15;
                } else {
                    hr++;
                    mn = 0;
                    if (hr < 24) {
                        if (hr < 10) {
                            item = new SelectItem("0" + hr + ":00:00", "0" + hr + ":00:00");
                        } else {
                            item = new SelectItem(+hr + ":00:00", +hr + ":00:00");
                        }
                    }
                    mn += 15;

                }
                if (item != null) {
                    lista.add(item);
                }

            } else {
                iniciar = false;
            }
        }
        return lista;
    }

    public List<SelectItem> getListaHora() {
        return listaHora;
    }

    public void setListaHora(List<SelectItem> listaHora) {
        this.listaHora = listaHora;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(String horaDesde) {
        this.horaDesde = horaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(String horaHasta) {
        this.horaHasta = horaHasta;
    }

    public boolean isMostrarTabla() {
        return mostrarTabla;
    }

    public void setMostrarTabla(boolean mostrarTabla) {
        this.mostrarTabla = mostrarTabla;
    }

    public List<NotaDebitoMultifuncionalDTO> getListaData() {
        return listaData;
    }

    public void setListaData(List<NotaDebitoMultifuncionalDTO> listaData) {
        this.listaData = listaData;
    }

}
