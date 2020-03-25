/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.reintegros;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.dto.ReintegrosRevisionDTO;
import com.davivienda.sara.procesos.autenticacion.webservice.session.AutenticacionLdapWSProcesosSessionLocal;
import com.davivienda.sara.procesos.reintegros.notas.session.ReintegrosNotasProcesosSessionLocal;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.helper.ReintegrosConsultaServletHelper;
import com.davivienda.sara.reintegros.general.helper.ReintegrosGuardarAutorizacionServletHelper;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
@ManagedBean(name = "autorizarReintegrosBean")
@ViewScoped
public class AutorizarReintegrosBean extends BaseBean implements Serializable {

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

    public ReintegrosGeneralObjectContext reintegrosGeneralObjectContext;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fechaDesde;
    private String horaDesde;
    private String fechaHasta;
    private String horaHasta;
    private boolean mostrarTabla;
    private List<ReintegrosRevisionDTO> listaData;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void AutorizarReintegrosBean() {
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
        this.listaData = new ArrayList<ReintegrosRevisionDTO>();
    }

    public void consultar() {
        System.err.println("AutorizarReintegrosBean-consultar");
        String respuesta = "";
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date fechaI = formatter.parse(this.fechaDesde);
            } catch (Exception ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }

            if (null == this.fechaHasta || this.fechaHasta.isEmpty()) {
                fechaHasta = this.fechaDesde;
            }

            ReintegrosHelperInterface reintegrosHelper = null;
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "autorizarReintegros" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.setAtributoFechaHoraInicial(this.fechaDesde, this.horaDesde);
            reintegrosGeneralObjectContext.setAtributoFechaHoraFinal(this.fechaHasta, this.horaHasta);

            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("AutorizarrReintegrosBean-consultar: FechaInicial: " + this.fechaDesde + horaDesde + " FechaFinal: " + this.fechaHasta + horaHasta);

            reintegrosHelper = new ReintegrosConsultaServletHelper(reintegrosSessionLocal, reintegrosGeneralObjectContext, EstadoReintegro.REVISADOAUTORIZADO.codigo, false);
            if (reintegrosHelper != null) {
                respuesta = reintegrosHelper.obtenerDatos();

                System.err.println("AutorizarReintegrosBean resp: " + respuesta);
                if (respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                } else if (ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "items") != null && ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "items").length() > 0) {
                    construirLista(JSon.getValorAtributo(respuesta, "items"));
                    for (int i = 0; i < listaData.size(); i++) {//Establecer por defecto el estado siguiente del reintegro
                        listaData.get(i).setEstadoReintegro(EstadoReintegro.AUTORIZADOSUPERVISOR.codigo);
                    }
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
        System.err.println("AutorizarReintegrosBean-guardarData");
        String respuesta = "";
        try {
            ReintegrosHelperInterface reintegrosHelper = null;
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "guardarAutorizacion" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.setAtributo("usuarioL", obtenerBienInicial().getUsuarioAut());
            reintegrosGeneralObjectContext.setAtributo("clave", obtenerBienInicial().getPasswordAut());
            reintegrosGeneralObjectContext.setAtributoJsonArray(aJSonArray());
            reintegrosHelper = new ReintegrosGuardarAutorizacionServletHelper(reintegrosSessionLocal, procesoReintegrosSessionLocal, reintegrosNotasProcesosSessionLocal, autenticacionLdapWSProcesosSessionLocal, confModulosAplicacionLocal, reintegrosGeneralObjectContext);
            if (reintegrosHelper != null) {
                respuesta = reintegrosHelper.obtenerDatos();
                if (respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                } else {
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                    dataInicial();
                }
            }
        } catch (Exception ex) {
            this.mostrarTabla = false;
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
        System.err.println("AutorizarReintegrosBean-guardarData resp: " + respuesta);
    }

    public void mostrarModalAut() {
        obtenerBienInicial().setMostrarModalAut(true);
    }

    private JSONArray aJSonArray() {
        JSONArray cadenaJSon = new JSONArray();
        try {
            JSONObject resp = new JSONObject();
            for (ReintegrosRevisionDTO item : this.listaData) {
                if (item.getEstadoReintegro() == EstadoReintegro.REINTEGROCANCELADO.codigo
                        || item.getEstadoReintegro() == EstadoReintegro.AUTORIZADOSUPERVISOR.codigo) {
                    JSONObject itemJSon = new JSONObject();
                    itemJSon.put("idRegistro", item.getIdRegistro());
                    itemJSon.put("codigoCajero", item.getCodigoCajero());
                    itemJSon.put("codigoOcca", item.getCodigoOcca());
                    itemJSon.put("numeroTransaccion", item.getNumeroTransaccion());
                    itemJSon.put("numeroCuenta", item.getNumeroCuenta());
                    itemJSon.put("numeroTarjeta", item.getNumeroTarjeta());
                    itemJSon.put("fecha", item.getFecha());
                    itemJSon.put("valor", item.getValor());
                    itemJSon.put("valorAjustar", SaraUtil.stripXSS(item.getValorAjustar(), Constantes.REGEX_ACEPTAR_NUM_VALOR));
                    itemJSon.put("statusTransaccion", item.getStatusTransaccion());
                    itemJSon.put("valorEntregado", item.getValorEntregado());
                    itemJSon.put("redEnruta", item.getRedEnruta());
                    itemJSon.put("checkSeleccion", item.isCheckSeleccion());
                    itemJSon.put("estadoReintegro", item.getEstadoReintegro());
                    itemJSon.put("concepto", item.getConcepto() == null || item.getConcepto().trim().equals("") || item.getConcepto().trim().equals("0") ? "16" : item.getConcepto());
                    itemJSon.put("comision", item.getComision() == null || item.getComision().trim().equals("") ? "0" : item.getComision());
                    cadenaJSon.put(itemJSon);
                }
            }

        } catch (Exception ex) {
            abrirModal("SARA", "Error", ex);
        }
        System.err.println("AutorizarReintegrosBean-guardarData JSonArray: " + cadenaJSon.toString());
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

    public void sortByIdRegistro() {

        Collections.sort(listaData, new Comparator<ReintegrosRevisionDTO>() {
            @Override
            public int compare(ReintegrosRevisionDTO o1, ReintegrosRevisionDTO o2) {
                return String.valueOf(o2.getIdRegistro()).compareTo(String.valueOf(o1.getIdRegistro()));
            }
        });

    }

    public void cambioEstadoReintegro(ReintegrosRevisionDTO reintegro) {
        System.out.println("sout : " + reintegro.toString());
        for (ReintegrosRevisionDTO r : listaData) {
            if (r.getIdRegistro() == reintegro.getIdRegistro()) {
                r.setCheckSeleccion(true);
            }
        }
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
}
