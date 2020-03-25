/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.reintegros;

import com.davivienda.multifuncional.constantes.TipoCuentaMultifuncional;
import com.davivienda.multifuncional.tablas.procesos.session.ProcesoReintegrosMultiSessionLocal;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.dto.CajeroDTO;
import com.davivienda.sara.dto.listas.TipoCuentaMultifuncionalLista;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.procesos.reintegros.notas.session.ReintegrosNotasProcesosSessionLocal;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author calvarez
 */
@ManagedBean(name = "ingNotaDebitoMultifuncionalBean")
@ViewScoped
public class IngresoNotaDebitoMultifucionalBean extends BaseBean implements Serializable {

    @EJB
    ReintegrosNotasProcesosSessionLocal reintegrosNotasProcesosSessionLocal;

    @EJB
    ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal;

    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionLocal;

    @EJB
    CajeroSessionLocal cajeroSession;

    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionSession;

    @EJB
    ProcesoReintegrosMultiSessionLocal procesoReintegrosMultiSessionLocal;

    public ComponenteAjaxObjectContextWeb objectContext;
    private List<CajeroDTO> listaCajeros;
    private String cajeroSeleccionado;
    private String tipoCuenta;
    private String cuenta;
    private String talon;
    private String valor;
    public List<SelectItem> listaTipoCuenta = new ArrayList<>();

    @PostConstruct
    public void IngresoNotaCreditoBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            listaCajeros = new ArrayList<>();
            if (objectContext != null) {
                listaTipoCuenta = new TipoCuentaMultifuncionalLista().crearLista();
                dataInicial();
            }

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void dataInicial() {
        cargarListaCajeros();
        this.cajeroSeleccionado = "0";
        this.tipoCuenta = "0";
        this.talon = "0";
        this.valor = "0";
        this.cuenta = "0";
    }

    private void cargarListaCajeros() {
        Collection<Cajero> items = null;
        try {
            items = cajeroSession.getTodosActivosMulti(0, 5000);
        } catch (Exception ex) {
            abrirModal("SARA", "Error", ex);
        }
        listaCajeros = cargarListaCajeros(objectContext.getCajeroCB(items));
    }

    private List<CajeroDTO> cargarListaCajeros(List<CajeroDTO> cajeroDTO) {
        List<CajeroDTO> lista = new ArrayList<>();
        int cont = 1;
        for (CajeroDTO dto : cajeroDTO) {
            dto.setIdCajero(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    public String guardarNota() {
        String respuesta = "";
        int intRegProcesados = -1;
        String strExepcion = "";
        Integer codigoCajeroLocal = 0;
        String cuentaLocal = "0";
        String talonLocal = "0";
        BigDecimal valorLocal;
        String tipoNota = "";
        TipoCuentaMultifuncional tipoCuentaLocal = TipoCuentaMultifuncional.CuentaAhorros;

        String usuario = "";
        Cajero cajero = null;
        Integer oficinaMulti = 0;
        boolean esNotaCredito = false;

        try {
            if (null == cajeroSeleccionado || cajeroSeleccionado.isEmpty() || cajeroSeleccionado.equals("") || cajeroSeleccionado.equals("0")) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return "";
            }

            if (null == tipoCuenta || tipoCuenta.isEmpty() || tipoCuenta.equals("") || tipoCuenta.equals("0")) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_CUENTA, null);
                return "";
            }
            codigoCajeroLocal = Integer.parseInt(cajeroSeleccionado);
            try {
                valorLocal = BigDecimal.valueOf(Long.parseLong(valor.replace(Constantes.CARACTER_COMA, "")));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("El valor del campo \"Valor\" debe ser numérico");
            }
            tipoCuentaLocal = TipoCuentaMultifuncional.getTipoCuentaMultifuncional(tipoCuenta);
            usuario = objectContext.getUsuarioEnSesion().getUsuario();
            cuentaLocal = cuenta;
            try {
                Long.parseLong(talon);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("El valor del campo \"Talon\" debe ser numérico");
            }
            talonLocal = talon;
            cajero = cajeroSession.buscar(codigoCajeroLocal);
            oficinaMulti = cajero.getOficinaMulti().getCodigooficinamulti();
            if (valorLocal.longValue() <= ConsultarValMaxReintegro()) {

                objectContext.getConfigApp().loggerApp.log(Level.INFO, "El usuario : {0} empezo a realizar una nota  al cajero: {1} por valor : {2} a la cuenta : {3} nota de el tipo: {4}", new Object[]{usuario, codigoCajeroLocal.toString(), valorLocal.toString(), cuentaLocal, tipoCuentaLocal.nombre});

                if (esNotaCredito) {
                    procesoReintegrosMultiSessionLocal.guardarReintegro(codigoCajeroLocal, valorLocal.longValue(), cuentaLocal, tipoCuentaLocal.codigo, usuario, talonLocal, oficinaMulti);
                    respuesta = "Bien";
                    tipoNota = "Nota Credito ";
                } else {
                    procesoReintegrosMultiSessionLocal.guardarNotaDebito(codigoCajeroLocal, valorLocal.longValue(), cuentaLocal, tipoCuentaLocal.codigo, usuario);
                    respuesta = "Bien";
                    tipoNota = "Nota Debito ";
                }

                tipoNota = tipoNota + tipoCuentaLocal.nombre;
                objectContext.getConfigApp().loggerApp.log(Level.INFO, "El usuario : {0}  realizo una {1}  al cajero {2} por valor : {3} a la cuenta : {4} con respuesta:{5}", new Object[]{usuario, tipoNota, codigoCajeroLocal.toString(), valorLocal.toString(), cuentaLocal, respuesta});
                if (respuesta != null) {
                    if (respuesta.length() > 0) {
                        if (respuesta.substring(0, 1).equals("B")) {
                            respuesta = tipoNota + " Realizada con Exito";

                        } else if (respuesta.substring(0, 1).equals("M")) {
                            respuesta = respuesta + "NO se pudo Realizar  la " + tipoNota;
                        } else if (respuesta.substring(0, 1).equals("F")) {
                            respuesta = respuesta + "Por favor verificar el  Estado de la " + tipoNota;
                        }
                    } else {
                        respuesta = respuesta + "Por favor verificar el  Estado de la " + tipoNota;
                    }
                } else {
                    respuesta = respuesta + "Por favor verificar el  Estado de la " + tipoNota;
                }

                respuesta = JSon.getJSonRespuesta(0, respuesta);
            } else {
                respuesta = JSon.getJSonRespuesta(0, "El valor de la nota credito es mayor al permitido");
            }
            if (respuesta != null) {
                String[] split = respuesta.split(":");
                respuesta = split[1].replaceAll("}", "");
            }

            abrirModal("SARA", respuesta, null);
        } catch (EJBException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            objectContext.setError(strExepcion, CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            abrirModal("SARA", respuesta, null);
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            //respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
            abrirModal("SARA", respuesta, null);
        }

        return respuesta;
    }

    private Long ConsultarValMaxReintegro() {
        Long longValReintegro = new Long("0");

        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("MULTIFUNCIONAL", "MULTIFUNCIONAL.MAX_VALOR_REINTEGRO");
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            longValReintegro = com.davivienda.utilidades.conversion.Cadena.aLong(registroEntityConsulta.getValor());

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").log(Level.INFO, "Error obteniendo el valor maximo de el reintegro : {0}", ex.getMessage());
            longValReintegro = new Long("9999999");
        }
        return longValReintegro;

    }

    public String getCajeroSeleccionado() {
        return cajeroSeleccionado;
    }

    public void setCajeroSeleccionado(String cajeroSeleccionado) {
        this.cajeroSeleccionado = cajeroSeleccionado;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getTalon() {
        return talon;
    }

    public void setTalon(String talon) {
        this.talon = talon;
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

    public List<CajeroDTO> getListaCajeros() {
        return listaCajeros;
    }

    public void setListaCajeros(List<CajeroDTO> listaCajeros) {
        this.listaCajeros = listaCajeros;
    }

}
