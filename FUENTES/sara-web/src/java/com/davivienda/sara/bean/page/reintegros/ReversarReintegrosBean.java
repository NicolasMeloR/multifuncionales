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
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.notasdebito.session.NotasDebitoSessionLocal;
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
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.procesos.cargues.masivos.notasdebito.session.CargarNotasDebitoMasivosSessionLocal;
import com.davivienda.sara.reintegros.general.ReversarReintegrosObjectContext;
import com.davivienda.sara.reintegros.general.helper.ReversarReintegrosGeneralServletHelper;
import com.davivienda.sara.tablas.reversarreintegros.session.ReversarReintegrosSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import java.math.BigDecimal;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "reversarReintegrosBean")
@ViewScoped
public class ReversarReintegrosBean extends BaseBean implements Serializable {

    @EJB
    ReintegrosSessionLocal reintegrosSessionLocal;
    @EJB
    CargarNotasDebitoMasivosSessionLocal cargarNotasDebitoMasivosSessionLocal;
    @EJB
    AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal;
    @EJB
    ReversarReintegrosSessionLocal reversarReintegrosSessionLocal;
    @EJB
    ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal;
    @EJB
    ReintegrosNotasProcesosSessionLocal reintegrosNotasProcesosSessionLocal;
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionLocal;

    private ReintegrosGeneralObjectContext reintegrosGeneralObjectContext;
    private ReversarReintegrosObjectContext reversarReintegrosObjectContext;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fechaDesde;
    private String horaDesde;
    private String fechaHasta;
    private String horaHasta;
    private boolean mostrarTabla;
    private List<ReintegrosRevisionDTO> listaData;
    private String respuesta;
    private Date fecha;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void RevisarReintegrosBean() {
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
        System.err.println("reversarReintegrosBean-consultar");
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
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "reversarReintegrosBean" + " por  " + reintegrosGeneralObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosGeneralObjectContext.getdireccionIP());
            reintegrosGeneralObjectContext.setAtributoFechaHoraInicial(this.fechaDesde, this.horaDesde);
            reintegrosGeneralObjectContext.setAtributoFechaHoraFinal(this.fechaHasta, this.horaHasta);
            reintegrosGeneralObjectContext.getConfigApp().loggerAcceso.info("reversarReintegrosBean-consultar: FechaInicial: " + this.fechaDesde + horaDesde + " FechaFinal: " + this.fechaHasta + horaHasta);
            reintegrosHelper = new ReintegrosConsultaServletHelper(reintegrosSessionLocal, reintegrosGeneralObjectContext, EstadoReintegro.FINALIZADOEXITOSO.codigo, false);
            if (reintegrosHelper != null) {
                respuesta = reintegrosHelper.obtenerDatos();

                System.err.println("reversarReintegrosBean resp: " + respuesta);
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

    public void mostrarModalAut() {
        obtenerBienInicial().setMostrarModalAut(true);
    }

    public void guardarData() {
        System.err.println("reversarReintegrosBean-guardarData");
        try {
            ReintegrosHelperInterface helperInterface = null;
            reversarReintegrosObjectContext = new ReversarReintegrosObjectContext(getRequestFaces(), getResponseFaces());
            reversarReintegrosObjectContext.setAtributoReintegrosReversados(listaData);
            reversarReintegrosObjectContext.setAtributo("usuarioL", obtenerBienInicial().getUsuarioAut());
            reversarReintegrosObjectContext.setAtributo("clave", obtenerBienInicial().getPasswordAut());
            helperInterface = new ReversarReintegrosGeneralServletHelper(reversarReintegrosSessionLocal, reversarReintegrosObjectContext, autenticacionLdapWSProcesosSessionLocal, cargarNotasDebitoMasivosSessionLocal, reintegrosSessionLocal, reintegrosNotasProcesosSessionLocal,procesoReintegrosSessionLocal,confModulosAplicacionLocal);
            if (helperInterface != null) {
                respuesta = helperInterface.obtenerDatos();
                dataInicial();
                abrirModal("SARA", respuesta, null);
                this.dataInicial();
            }
        } catch (Exception e) {
            abrirModal("SARA", "Error reversando reintegros", e);
            e.printStackTrace();
        }
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

    public List<SelectItem> getListaHora() {
        return listaHora;
    }

// <editor-fold defaultstate="collapsed" desc="getters and setters">
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

    // </editor-fold>
}
