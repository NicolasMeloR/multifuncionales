/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.reintegros;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.dto.NotaDebitoDTO;
import com.davivienda.sara.procesos.autenticacion.webservice.session.AutenticacionLdapWSProcesosSessionLocal;
import com.davivienda.sara.procesos.reintegros.notas.session.ReintegrosNotasProcesosSessionLocal;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.helper.ReintegrosConsultaNotasDbServletHelper;
import com.davivienda.sara.reintegros.general.helper.ReintegrosGuadarNotasDebitoServletHelper;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.notasdebito.session.NotasDebitoSessionLocal;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
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
@ManagedBean(name = "autorizarNotasDebitoBean")
@ViewScoped
public class AutorizarNotasDebitoBean extends BaseBean implements Serializable {

    @EJB
    ReintegrosSessionLocal reintegrosSessionLocal;
    @EJB
    ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal;
    @EJB
    ReintegrosNotasProcesosSessionLocal reintegrosNotasProcesosSessionLocal;
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionLocal;
    @EJB
    AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal;
    @EJB
    NotasDebitoSessionLocal notasDebitoSessionLocal;

    public ReintegrosGeneralObjectContext reintegrosGeneralObjectContext;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fechaDesde;
    private String horaDesde;
    private String fechaHasta;
    private String horaHasta;
    private boolean mostrarTabla;
    private List<NotaDebitoDTO> listaData;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void AutorizarNotasDebitoBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            reintegrosGeneralObjectContext = new ReintegrosGeneralObjectContext(getRequestFaces(), getResponseFaces());
            listaHora = new ArrayList<SelectItem>();
            if (objectContext != null) {
                dataInicial();
            }

        } catch (Exception ex) {
            abrirModal("SARA", "Error", ex);
        }
    }

    public void dataInicial() {
        this.fechaDesde = "";
        this.horaDesde = "00:00:00";
        this.fechaHasta = "";
        this.horaHasta = "00:00:00";
        this.listaHora = cargarListaHora();
        this.mostrarTabla = false;
        this.listaData = new ArrayList<NotaDebitoDTO>();
    }

    public void consultar() {
        System.err.println("AutorizarNotasDebitoBean-consultar");
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
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "autorizarNotasDebito" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.setAtributoFechaHoraInicial(this.fechaDesde, this.horaDesde);
            reintegrosGeneralObjectContext.setAtributoFechaHoraFinal(this.fechaHasta,  this.horaHasta);
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("AutorizarNotasDebitoBean-consultar: FechaInicial: " + this.fechaDesde+horaDesde + " FechaFinal: "+ this.fechaHasta+horaHasta);
            
            reintegrosHelper = new ReintegrosConsultaNotasDbServletHelper(notasDebitoSessionLocal, reintegrosGeneralObjectContext);
            if (reintegrosHelper != null) {
                respuesta = reintegrosHelper.obtenerDatos();

                System.err.println("AutorizarNotasDebitoBean resp: " + respuesta);
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
        System.err.println("AutorizarNotasDebitoBean-guardarData");
        String respuesta = "";
        try {
            ReintegrosHelperInterface reintegrosHelper = null;
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "guardarNotasDebito" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.setAtributo("usuarioL", obtenerBienInicial().getUsuarioAut());
            reintegrosGeneralObjectContext.setAtributo("clave", obtenerBienInicial().getPasswordAut());
            reintegrosGeneralObjectContext.setAtributoJsonArray(aJSonArray());
            reintegrosHelper = new ReintegrosGuadarNotasDebitoServletHelper(notasDebitoSessionLocal, procesoReintegrosSessionLocal, confModulosAplicacionLocal, autenticacionLdapWSProcesosSessionLocal, reintegrosNotasProcesosSessionLocal, reintegrosGeneralObjectContext);
            if (reintegrosHelper != null) {
                respuesta = reintegrosHelper.obtenerDatos();
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
        System.err.println("AutorizarNotasDebitoBean-guardarData resp: " + respuesta);
    }

    public void mostrarModalAut() {
        obtenerBienInicial().setMostrarModalAut(true);
    }

    private JSONArray aJSonArray() {
        JSONArray cadenaJSon = new JSONArray();
        try {
            JSONObject resp = new JSONObject();
            for (NotaDebitoDTO item : this.listaData) {

                JSONObject itemJSon = new JSONObject();
                itemJSon.put("idRegistro", item.getIdRegistro());
                itemJSon.put("codigoCajero", item.getCodigoCajero());
                itemJSon.put("codigoOcca", item.getCodigoOcca());
                itemJSon.put("numeroCuenta", item.getNumeroCuenta());
                itemJSon.put("fecha", item.getFecha());
                itemJSon.put("valor", item.getValor());
                itemJSon.put("valorAjustar", item.getValorAjustar());
                itemJSon.put("usuarioCrea", item.getUsuarioCrea());
                itemJSon.put("checkSeleccion", item.isCheckSeleccion());
                itemJSon.put("concepto", item.getConcepto());
                itemJSon.put("comision", item.getComision());
                cadenaJSon.put(itemJSon);
            }

        } catch (Exception ex) {
            abrirModal("SARA", "Error", ex);
        }
        System.err.println("AutorizarNotasDebitoBean-guardarData JSonArray: " + cadenaJSon.toString());
        return cadenaJSon;
    }

    private void construirLista(String lista) throws JSONException {
        JSONArray respItems = new JSONArray(lista);

        for (int i = 0; i < respItems.length(); i++) {
            JSONObject itemJSon = respItems.getJSONObject(i);
            NotaDebitoDTO itemDto = new NotaDebitoDTO().entidadDTO(itemJSon);
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

    public List<NotaDebitoDTO> getListaData() {
        return listaData;
    }

    public void setListaData(List<NotaDebitoDTO> listaData) {
        this.listaData = listaData;
    }

}
