/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.contingencia;

import com.davivienda.multifuncional.tablas.edccargue.session.EDCCargueMultifuncionalLocal;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.contingencia.general.ContingenciaManualGeneralObjectContext;
import com.davivienda.sara.contingencia.general.ContingenciaManualHelperInterface;
import com.davivienda.sara.contingencia.general.helper.ContingenciaGeneralConsultaDiariosServletHelper;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasCargasSessionLocal;
import com.davivienda.sara.dto.EdcCargueDTO;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.edccargue.session.EdcCargueSessionLocal;
import com.davivienda.sara.tablas.regcargue.session.RegCargueArchivoSessionLocal;
import com.davivienda.sara.tablas.transacciontemp.session.TransaccionTempSessionLocal;
import com.davivienda.sara.tareas.contingencia.carguearchivos.session.ContingenciaCargueArchivosSessionLocal;
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
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
@ManagedBean(name = "contingenciaConDiariosBean")
@ViewScoped
public class ContingenciaConsultaDiariosBean extends BaseBean implements Serializable {

    @EJB
    ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSessionLocal;

    @EJB
    CuadreCifrasCargasSessionLocal cuadreCifrasCargas;

    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionSession;

    @EJB
    AdminTareasRegCargueArchivoSessionLocal adminTareasRegCargueArchivoSessionLocal;

    @EJB
    RegCargueArchivoSessionLocal regCargueArchivoSessionLocal;

    @EJB
    ContingenciaCargueArchivosSessionLocal contingenciaCargueArchivosSessionLocal;

    @EJB
    TransaccionTempSessionLocal transaccionTempSessionLocal;

    @EJB
    AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;

    @EJB
    EdcCargueSessionLocal edcCargueSessionLocal;

    @EJB
    EDCCargueMultifuncionalLocal eDCCargueMultifuncionalLocal;

    public ContingenciaManualGeneralObjectContext contingenciaManualGeneralObjectContext;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora;
    private String fechaDesde;
    private String horaDesde;
    private String fechaHasta;
    private String horaHasta;
    private String tipoDiario;
    private List<EdcCargueDTO> listaData;
    private boolean mostrarTabla;
    private Logger loggerApp;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void ContingenciaConsultaDiariosBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            contingenciaManualGeneralObjectContext = new ContingenciaManualGeneralObjectContext(getRequestFaces(), getResponseFaces());
            listaHora = new ArrayList<SelectItem>();
            if (objectContext != null) {
                listaHora = cargarListaHora();
                dataInicial();
                loggerApp = contingenciaManualGeneralObjectContext.getConfigApp().loggerApp;
            }

        } catch (Exception ex) {
            abrirModal("SARA", "Error", ex);
        }
    }

    public void dataInicial() {
        this.fechaDesde = "";
        this.horaDesde = "00:00:00";
        this.fechaHasta = "";
        this.horaHasta = "23:59:59";
        this.tipoDiario = "";
        this.listaData = new ArrayList<EdcCargueDTO>();
        this.mostrarTabla = false;
    }

    public void consultar() {
         loggerApp.info("ContingenciaConsultaDiariosBean-consultar");
        this.listaData.clear();
        try {
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                 Date fechaInicial = formatter.parse(this.fechaDesde);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }
            if(null == this.tipoDiario || this.tipoDiario.equals("")){
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_DIARIO, null);
                return;
            }
            
            loggerApp.info("ContingenciaConsultaDiariosBean-fecha y hora: " +fechaDesde+horaDesde + "   " +fechaHasta+horaHasta);
            ContingenciaManualHelperInterface cuadreCifrasHelper = null;
            String directorioUpload = "";
            directorioUpload = contingenciaManualGeneralObjectContext.getConfigApp().DIRECTORIO_UPLOAD;
            
            loggerApp.info("ContingenciaConsultaDiariosBean directorioUpload: " + directorioUpload);
            String respuesta = null;

            if (contingenciaManualGeneralObjectContext.getConfigApp().USUARIO_QUARTZ.equals(contingenciaManualGeneralObjectContext.getAtributoString("usuario"))) {

                contingenciaManualGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "contingenciaGeneral" + " por  USUARIO_QUARTZ " + contingenciaManualGeneralObjectContext.getConfigApp().USUARIO_QUARTZ + " desde " + contingenciaManualGeneralObjectContext.getdireccionIP());

            } else {
                contingenciaManualGeneralObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "contingenciaGeneral" + " por  " + " desde " + contingenciaManualGeneralObjectContext.getdireccionIP());
            }
            
            if(null == this.fechaHasta || this.fechaHasta.isEmpty()){
                this.fechaHasta = this.fechaDesde;
            }


            contingenciaManualGeneralObjectContext.setAtributoFechaHoraInicial(this.fechaDesde, ":"+this.horaDesde);
            contingenciaManualGeneralObjectContext.setAtributoFechaHoraFinal(this.fechaHasta, ":"+this.horaHasta);
            contingenciaManualGeneralObjectContext.setAtributoTipoDiarioString(this.tipoDiario);
            cuadreCifrasHelper = new ContingenciaGeneralConsultaDiariosServletHelper(edcCargueSessionLocal, eDCCargueMultifuncionalLocal, contingenciaManualGeneralObjectContext);

            if (cuadreCifrasHelper != null) {
                respuesta = cuadreCifrasHelper.obtenerDatos();
                loggerApp.info("ContingenciaConsultaDiariosBean-consultar resp: " + respuesta);
                
                if (respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                }else   if (ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "items") != null && ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "items").length()>0) {
                    construirLista(JSon.getValorAtributo(respuesta, "items"));
                    this.mostrarTabla = true;
                }else{
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                }
            }
        } catch (Exception ex) {
             this.mostrarTabla = false;
            abrirModal("SARA", "Error", ex);
        }
    }
    private void construirLista(String lista) throws JSONException {
        JSONArray respItems = new JSONArray(lista);
        
        for (int i = 0; i < respItems.length(); i++) {
            JSONObject itemJSon = respItems.getJSONObject(i);
            EdcCargueDTO itemDto  = new EdcCargueDTO().entidadDTO(itemJSon);
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

    public String getTipoDiario() {
        return tipoDiario;
    }

    public void setTipoDiario(String tipoDiario) {
        this.tipoDiario = tipoDiario;
    }

    public List<EdcCargueDTO> getListaData() {
        return listaData;
    }

    public void setListaData(List<EdcCargueDTO> listaData) {
        this.listaData = listaData;
    }

    public boolean isMostrarTabla() {
        return mostrarTabla;
    }

    public void setMostrarTabla(boolean mostrarTabla) {
        this.mostrarTabla = mostrarTabla;
    }

    
}
