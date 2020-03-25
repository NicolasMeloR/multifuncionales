/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.diarioelectronico;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.ProcesosArchivoObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.diarioelectronico.general.ProcesosArchivoHelperInterface;
import com.davivienda.sara.diarioelectronico.general.helper.ProcesosArchivoCargarArchivoServletHelper;
import com.davivienda.sara.dto.CajeroDTO;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.procesos.diarioelectronico.session.AdministradorProcesosSessionLocal;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "cargarArchivoBean")
@ViewScoped
public class CargarArchivoBean extends BaseBean implements Serializable {

    @EJB
    CajeroSessionLocal cajeroSession;
    @EJB
    AdministradorProcesosSessionLocal administradorProcesos;
    @EJB
    AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionSession;
    public ComponenteAjaxObjectContextWeb objectContext;
    private List<CajeroDTO> listaRegistros;
    private List<SelectItem> listaHora;
    private String fechaDiarioE;
    private String horaDiarioE;
    private String fechaCicloD;
    private String horaCicloD;
    private String cajeroSeleccionado;
    private ProcesosArchivoObjectContextWeb procesosArchivoObjectContext;
    private Logger loggerApp;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void CargarArchivoBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            procesosArchivoObjectContext = new ProcesosArchivoObjectContextWeb(getRequestFaces(), getResponseFaces());
            listaRegistros = new ArrayList<CajeroDTO>();
            listaHora = new ArrayList<SelectItem>();
            if (objectContext != null) {
                dataInicialDiarioE();
                dataInicialCicloD();
                loggerApp = objectContext.getConfigApp().loggerApp;
            }

        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void dataInicialDiarioE() throws Exception {
        this.fechaDiarioE = "";
        this.horaDiarioE = "00:00:00";
        Collection<Cajero> items = cajeroSession.getTodosActivos(0, 5000);
        listaRegistros = cargarListaRegistros(objectContext.getCajeroCB(items));
        listaHora = cargarListaHora();
        this.cajeroSeleccionado = "0";
    }

    public void dataInicialCicloD() {
        this.fechaCicloD = "";
        this.horaCicloD = "00:00:00";
        listaHora = cargarListaHora();
    }

    public void cargarDiarioElectronico() {
        loggerApp.log(Level.INFO, "CargarArchivoBean-cargarDiarioElectronico");
        String respuesta = "";
        try {
            loggerApp.log(Level.INFO, "CargarArchivoBean-cargarDiarioElectronico-fecha y hora: " + fechaDiarioE + horaDiarioE);
            String directorioUpload = "";
            directorioUpload = procesosArchivoObjectContext.getConfigApp().DIRECTORIO_UPLOAD;
            ProcesosArchivoHelperInterface procesosArchivoHelper = null;
            if (procesosArchivoObjectContext.getConfigApp().USUARIO_QUARTZ.equals(procesosArchivoObjectContext.getAtributoString("usuario"))) {
                procesosArchivoObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log CARGAR_ARCHIVO por  USUARIO_QUARTZ " + procesosArchivoObjectContext.getConfigApp().USUARIO_QUARTZ + " desde " + procesosArchivoObjectContext.getdireccionIP());
            } else {
                procesosArchivoObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log CARGAR_ARCHIVO por  " + objectContext.getIdUsuarioEnSesion() + " desde " + procesosArchivoObjectContext.getdireccionIP());
            }
            procesosArchivoObjectContext.setAtributo("codigoCajero", Integer.parseInt(this.cajeroSeleccionado));
            procesosArchivoObjectContext.setAtributoFechaInicial(this.fechaDiarioE);
            procesosArchivoHelper = new ProcesosArchivoCargarArchivoServletHelper(administradorProcesos, procesosArchivoObjectContext, administradorProcesosEdcCargueSessionLocal, confModulosAplicacionSession, directorioUpload, true);

            if (procesosArchivoHelper != null) {
                respuesta = procesosArchivoHelper.obtenerDatos();

                if (respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                    return;
                } else {
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                    return;
                }
            }
        } catch (Exception ex) {
            //abrirModal("SARA", "No se puede realizar la operación"+ ConvertidorJSon.getValorAtributoSinExcepcion(ex.getMessage(), "mensaje"), null);
            loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
            return;
        }
        loggerApp.log(Level.INFO, "cargarDiarioElectronico: " + respuesta);
    }

    public void cargarCicloDiario() {
        loggerApp.log(Level.INFO, "CargarArchivoBean-cargarCicloDiario");
        String respuesta = "";
        try {
            loggerApp.log(Level.INFO, "CargarArchivoBean-cargarCicloDiario-fecha y hora: " + fechaCicloD + horaCicloD);
            String directorioUpload = "";
            directorioUpload = procesosArchivoObjectContext.getConfigApp().DIRECTORIO_UPLOAD;
            ProcesosArchivoHelperInterface procesosArchivoHelper = null;

            if (procesosArchivoObjectContext.getConfigApp().USUARIO_QUARTZ.equals(procesosArchivoObjectContext.getAtributoString("usuario"))) {
                procesosArchivoObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log CARGAR_ARCHIVO por  USUARIO_QUARTZ " + procesosArchivoObjectContext.getConfigApp().USUARIO_QUARTZ + " desde " + procesosArchivoObjectContext.getdireccionIP());
            } else {
                procesosArchivoObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log CARGAR_ARCHIVO por  " + objectContext.getIdUsuarioEnSesion() + " desde " + procesosArchivoObjectContext.getdireccionIP());
            }
            procesosArchivoObjectContext.setAtributoFechaInicial(this.fechaCicloD);
            procesosArchivoHelper = new ProcesosArchivoCargarArchivoServletHelper(administradorProcesos, procesosArchivoObjectContext, administradorProcesosEdcCargueSessionLocal, confModulosAplicacionSession, directorioUpload, false);

            if (procesosArchivoHelper != null) {
                respuesta = procesosArchivoHelper.obtenerDatos();

                if (respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                    return;
                } else {
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                    return;
                }
            }

        } catch (Exception ex) {
            //abrirModal("SARA", "No se puede realizar la operación"+ ConvertidorJSon.getValorAtributoSinExcepcion(ex.getMessage(), "mensaje"), null);
            loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
            return;
        }

        loggerApp.log(Level.INFO, "cargarCicloDiario: " + respuesta);
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

    private List<CajeroDTO> cargarListaRegistros(List<CajeroDTO> cajeroDTO) {
        List<CajeroDTO> lista = new ArrayList<CajeroDTO>();
        int cont = 1;
        for (CajeroDTO dto : cajeroDTO) {
            dto.setIdCajero(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    public List<CajeroDTO> getListaRegistros() {
        return listaRegistros;
    }

    public void setListaRegistros(List<CajeroDTO> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    public String getFechaDiarioE() {
        return fechaDiarioE;
    }

    public void setFechaDiarioE(String fechaDiarioE) {
        this.fechaDiarioE = fechaDiarioE;
    }

    public String getHoraDiarioE() {
        return horaDiarioE;
    }

    public void setHoraDiarioE(String horaDiarioE) {
        this.horaDiarioE = horaDiarioE;
    }

    public String getFechaCicloD() {
        return fechaCicloD;
    }

    public void setFechaCicloD(String fechaCicloD) {
        this.fechaCicloD = fechaCicloD;
    }

    public String getHoraCicloD() {
        return horaCicloD;
    }

    public void setHoraCicloD(String horaCicloD) {
        this.horaCicloD = horaCicloD;
    }

    public String getCajeroSeleccionado() {
        return cajeroSeleccionado;
    }

    public void setCajeroSeleccionado(String cajeroSeleccionado) {
        this.cajeroSeleccionado = cajeroSeleccionado;
    }

    public List<SelectItem> getListaHora() {
        return listaHora;
    }

    public void setListaHora(List<SelectItem> listaHora) {
        this.listaHora = listaHora;
    }

}
