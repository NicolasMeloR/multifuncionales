/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.reintegros;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.dto.CajeroDTO;
import com.davivienda.sara.dto.listas.TipoCuenta;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.procesos.reintegros.notas.session.ReintegrosNotasProcesosSessionLocal;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.ReintegrosNotasObjectContext;
import com.davivienda.sara.reintegros.general.helper.ReintegrosNotasGeneralServletHelper;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.util.ConvertidorJSon;
import com.davivienda.utilidades.Constantes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "ingNotaDebitoBean")
@ViewScoped
public class IngresoNotaDebitoBean extends BaseBean implements Serializable {

    @EJB
    ReintegrosNotasProcesosSessionLocal reintegrosNotasProcesosSessionLocal;

    @EJB
    ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal;

    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionLocal;
    @EJB
    CajeroSessionLocal cajeroSession;

    public ComponenteAjaxObjectContextWeb objectContext;
    private List<CajeroDTO> listaRegistros;
    private String cajeroSeleccionado;
    private String cuentaSeleccionada;
    private String cuenta;
    private String valor;
    public List<SelectItem> listaTipoCuenta = new ArrayList<SelectItem>();

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void IngresoNotaDebitoBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            listaRegistros = new ArrayList<CajeroDTO>();
            if (objectContext != null) {
                Collection<Cajero> items = cajeroSession.getTodosActivos(0, 5000);
                listaRegistros = cargarListaRegistros(objectContext.getCajeroCB(items));
                listaTipoCuenta = new TipoCuenta().crearLista();
                dataInicial();
            }

        } catch (Exception ex) {
            abrirModal("SARA", "Error", ex);
        }
    }

    public void dataInicial() {
        this.cajeroSeleccionado = "";
        this.cuentaSeleccionada = "";
        this.valor = "0";
        this.cuenta = "0";
    }

    public void guardarNota() {
        objectContext.getConfigApp().loggerApp.log(Level.INFO,"IngresoNotaDebitoBean-guardarNota");
        String respuesta = "";
        try {
           
            if (this.cajeroSeleccionado.equals("")) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return;
            }

            if (this.cuentaSeleccionada.equals("") || this.cuentaSeleccionada.equals("0")) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_CUENTA, null);
                return;
            }

            ReintegrosNotasObjectContext reintegrosNotasObjectContext = new ReintegrosNotasObjectContext(getRequestFaces(), getResponseFaces());
            ReintegrosHelperInterface reintegrosHelper = null;
            reintegrosNotasObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log " + "guardarNotaDebito" + " por  " + reintegrosNotasObjectContext.getIdUsuarioEnSesion() + " desde " + reintegrosNotasObjectContext.getdireccionIP());

            reintegrosNotasObjectContext.setCodigoCajeroComboBox(Integer.parseInt(this.cajeroSeleccionado));
            reintegrosNotasObjectContext.setAtributoValor(new BigDecimal(this.valor.replace(Constantes.CARACTER_COMA, "")));
            reintegrosNotasObjectContext.setAtributoCuenta(this.cuenta);
            reintegrosNotasObjectContext.setAtributo("tipoCuenta", this.cuentaSeleccionada);

            reintegrosHelper = new ReintegrosNotasGeneralServletHelper(reintegrosNotasProcesosSessionLocal, reintegrosNotasObjectContext, procesoReintegrosSessionLocal, confModulosAplicacionLocal, false);

            if (reintegrosHelper != null) {
                respuesta = reintegrosHelper.obtenerDatos();

                if (respuesta.length() == 0) {
                    abrirModal("SARA", "No se ha podido procesar la solicitud", null);
                } else {
                    abrirModal("SARA", ConvertidorJSon.getValorAtributoSinExcepcion(respuesta, "mensaje"), null);
                }
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

        objectContext.getConfigApp().loggerApp.log(Level.INFO,"IngresoNotaDebitoBean guardarNota: resp " + respuesta);
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

    public String getCajeroSeleccionado() {
        return cajeroSeleccionado;
    }

    public void setCajeroSeleccionado(String cajeroSeleccionado) {
        this.cajeroSeleccionado = cajeroSeleccionado;
    }

    public String getCuentaSeleccionada() {
        return cuentaSeleccionada;
    }

    public void setCuentaSeleccionada(String cuentaSeleccionada) {
        this.cuentaSeleccionada = cuentaSeleccionada;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<SelectItem> getListaTipoCuenta() {
        return listaTipoCuenta;
    }

    public void setListaTipoCuenta(List<SelectItem> listaTipoCuenta) {
        this.listaTipoCuenta = listaTipoCuenta;
    }

}
