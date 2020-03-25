/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.reintegros;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.dto.ReintegrosRevisionDTO;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.helper.ReintegrosConsultaServletHelper;
import com.davivienda.sara.reintegros.general.helper.ReintegrosGuadarRevisionServletHelper;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "reintegrosCreadosBean")
@ViewScoped
public class ReintegrosCreadosBean extends BaseBean implements Serializable {

    @EJB
    ReintegrosSessionLocal reintegrosSessionLocal;
    @EJB
    ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal;

    public ReintegrosGeneralObjectContext reintegrosGeneralObjectContext;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fechaDesde;
    private String horaDesde;
    private String fechaHasta;
    private String horaHasta;
    private boolean mostrarTabla;
    private List<ReintegrosRevisionDTO> listaData;
    private String usuario;
    private String password;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void ReintegrosCreadosBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            reintegrosGeneralObjectContext = new ReintegrosGeneralObjectContext(getRequestFaces(), getResponseFaces());
            listaHora = new ArrayList<SelectItem>();
            if (objectContext != null) {
                dataInicial();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dataInicial() {
        this.fechaDesde = "";
        this.horaDesde = "00:00:00";
        this.fechaHasta = "";
        this.horaHasta = "00:00:00";
        this.listaHora = cargarListaHora();
        this.mostrarTabla = false;
        this.listaData = new ArrayList<ReintegrosRevisionDTO>();
        this.usuario = "";
        this.password = "";
    }

    public void consultar() {
        System.err.println("ReintegrosCreadosBean-consultar");
        String respuesta = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date fechaI = formatter.parse(this.fechaDesde);
            } catch (Exception ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }
            
            if(null == this.fechaHasta || this.fechaHasta.isEmpty()){
                fechaHasta = this.fechaDesde;
            }
            
            ReintegrosHelperInterface reintegrosHelper = null;
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "revisarCreados" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.setAtributoFechaHoraInicial(this.fechaDesde, this.horaDesde);
            reintegrosGeneralObjectContext.setAtributoFechaHoraFinal(this.fechaHasta,  this.horaHasta);
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("ReintegrosCreadosBean-consultar: FechaInicial: " + this.fechaDesde+horaDesde + " FechaFinal: "+ this.fechaHasta+horaHasta);
            
            reintegrosHelper = new ReintegrosConsultaServletHelper(reintegrosSessionLocal, reintegrosGeneralObjectContext, EstadoReintegro.REINTEGROTIPOCREADO.codigo, true);
            if (reintegrosHelper != null) {
                respuesta = reintegrosHelper.obtenerDatos();

                System.err.println("ReintegrosCreadosBean resp: " + respuesta);
                if (respuesta.length() == 0) {
                    respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), "No se ha podido procesar la solicitud ");
                } else if (ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "items") != null && ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "items").length() > 0) {
                    construirLista(JSon.getValorAtributo(respuesta, "items"));
                    this.mostrarTabla = true;
                }
            }
        } catch (Exception ex) {
            this.mostrarTabla = false;
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void guardarData() {
        System.err.println("ReintegrosCreadosBean-guardarData");
        String respuesta = "";
        try {
            ReintegrosHelperInterface reintegrosHelper = null;
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "guardarCreados" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.setAtributoJsonArray(aJSonArray());
            reintegrosHelper = new ReintegrosGuadarRevisionServletHelper(reintegrosSessionLocal, procesoReintegrosSessionLocal, reintegrosGeneralObjectContext, true);
            if (reintegrosHelper != null) {
                respuesta = reintegrosHelper.obtenerDatos();

                if (respuesta.length() == 0) {
                    respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), "No se ha podido procesar la solicitud ");
                }
            }
        } catch (Exception ex) {
            this.mostrarTabla = false;
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

        System.err.println("ReintegrosCreadosBean-guardarData resp: " + respuesta);
        if (respuesta != null) {
            String[] split = respuesta.split(":");
            respuesta = split[1].replaceAll("}", "");
        }
        abrirModal("SARA", respuesta, null);
    }

    private JSONArray aJSonArray() {
        JSONArray cadenaJSon = new JSONArray();
        try {
            JSONObject resp = new JSONObject();
            for (ReintegrosRevisionDTO item : this.listaData) {

                JSONObject itemJSon = new JSONObject();
                itemJSon.put("idRegistro", item.getIdRegistro());
                itemJSon.put("codigoCajero", item.getCodigoCajero());
                itemJSon.put("codigoOcca", item.getCodigoOcca());
                itemJSon.put("numeroTransaccion", item.getNumeroTransaccion());
                itemJSon.put("numeroCuenta", item.getNumeroCuenta());
                itemJSon.put("numeroTarjeta", item.getNumeroTarjeta());
                itemJSon.put("fecha", item.getFecha());
                itemJSon.put("valor", item.getValor());
                itemJSon.put("valorAjustar", item.getValorAjustar());
                itemJSon.put("statusTransaccion", item.getStatusTransaccion());
                itemJSon.put("valorEntregado", item.getValorEntregado());
                itemJSon.put("redEnruta", item.getRedEnruta());
                itemJSon.put("checkSeleccion", item.isCheckSeleccion());
                cadenaJSon.put(itemJSon);
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.severe("No se puede pasar a JSON \n " + ex.getMessage());
        }
        System.err.println("ReintegrosCreadosBean-guardarData JSonArray: " + cadenaJSon.toString());
        return cadenaJSon;
    }

    private void construirLista(String lista) throws JSONException {
        JSONArray respItems = new JSONArray(lista);

        for (int i = 0; i < respItems.length(); i++) {
            JSONObject itemJSon = respItems.getJSONObject(i);
            ReintegrosRevisionDTO itemDto = new ReintegrosRevisionDTO().entidadDTO(itemJSon);
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

    public HttpServletRequest getRequestFaces() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request;
    }

    public HttpServletResponse getResponseFaces() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        return response;
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

    public List<ReintegrosRevisionDTO> getListaData() {
        return listaData;
    }

    public void setListaData(List<ReintegrosRevisionDTO> listaData) {
        this.listaData = listaData;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
