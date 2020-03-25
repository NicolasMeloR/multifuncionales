/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.cuadreCajeros;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.TipoAjuste;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasSessionLocal;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.sara.procesos.ajustes.session.AjustesProcesosSessionLocal;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.utilidades.Constantes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author jmcastel
 */
@ManagedBean(name = "realizarAjusteBean")
@ViewScoped
public class RealizarAjusteBean extends BaseBean implements Serializable {

    @EJB
    AjustesProcesosSessionLocal sessionAjustes;

    @EJB
    CajeroSessionLocal cajeroSession;

    @EJB
    CuadreCifrasSessionLocal cuadreCifrasSessionLocal;

    private List<SelectItem> tiposAjuste = new ArrayList<SelectItem>();
    private String codigoCajero;
    private String codigoOcca;
    private String valorAjuste;
    private String ajusteSeleccionado;
    private Logger loggerApp;
    private String respuesta;

    @PostConstruct
    public void RealizarAjusteBean() {

        loggerApp = cargarComponenteAjaxObjectContext().getConfigApp().loggerApp;
        SelectItem item1 = new SelectItem(0, "0 - Inicio");
        this.getTiposAjuste().add(item1);
        SelectItem item2 = new SelectItem(1, "1 - Aumento Provision");
        this.getTiposAjuste().add(item2);
        SelectItem item3 = new SelectItem(2, "2 - Disminucion Provision");
        this.getTiposAjuste().add(item3);
        SelectItem item4 = new SelectItem(3, "3 - Sobrante Efectivo");
        this.getTiposAjuste().add(item4);
        SelectItem item5 = new SelectItem(4, "4 - Faltante Efectivo");
        this.getTiposAjuste().add(item5);
        SelectItem item6 = new SelectItem(5, "9612 - Ajuste Egresos");
        this.getTiposAjuste().add(item6);
        SelectItem item7 = new SelectItem(6, "9710 - Ajuste Ingresos");
        this.getTiposAjuste().add(item7);
        dataInicial();

    }

    public void dataInicial() {
        this.codigoCajero = "0";
        this.codigoOcca = "0";
        this.valorAjuste = "0";
        this.ajusteSeleccionado = "0";
    }

    public String guardarAjuste(String codigoCajero, String codigoOcca, String valorAjuste, String ajusteSeleccionado) {
        this.codigoCajero = codigoCajero;
        this.codigoOcca = codigoOcca;
        this.valorAjuste = valorAjuste;
        this.ajusteSeleccionado = ajusteSeleccionado;
        guardarAjuste();
        return respuesta;
    }

    
        
    
    
    public void guardarAjuste() {
        try {
            loggerApp.info("RealizarAjusteBean-guardarAjuste");
            respuesta = "";
            int intRegProcesados = -1;
            String strExepcion = "";
            Integer codigo_Cajero = 0;
            Integer codigo_Occa = 0;
            BigDecimal valorAjuste;
            TipoAjuste tipoAjuste = TipoAjuste.Inicio;
            String usuario = "";
            Integer tipoMovCuadre = 0;
            BigDecimal smallBigDecimal = new BigDecimal(0);
            BigDecimal bigBigDecimal = new BigDecimal(999999999);
            Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            InitBean initBean = (InitBean) viewMap.get("initBean");
            codigo_Cajero = Integer.parseInt(this.codigoCajero);
            codigo_Occa = Integer.parseInt(this.codigoOcca);
            valorAjuste = BigDecimal.valueOf(Long.parseLong(this.valorAjuste.replace(Constantes.CARACTER_COMA, "")));
            tipoAjuste = tipoAjuste.getTipoAjuste(Integer.parseInt(this.ajusteSeleccionado));
            usuario = initBean.getObjectContext().getUsuarioEnSesion().getUsuario();
            loggerApp.log(Level.INFO, "El usuario : {0} empezo a realizar un ajuste al cajero: {1} a la Occa: {2} por valor : {3} ajuste de el tipo: {4}", new Object[]{usuario, codigo_Cajero.toString(), codigo_Occa.toString(), valorAjuste.toString(), tipoAjuste.nombre});
            if (smallBigDecimal.compareTo(valorAjuste) <= 0 && valorAjuste.compareTo(bigBigDecimal) <= 0) {

                try {

                    switch (tipoAjuste) {
                        case Inicio:
                            // Se asocia un object context particular

                            break;
                        case Aumento_Provision:
                            // Se asocia un object context particular
                            respuesta = sessionAjustes.realizarAjusteAumentoProvision(usuario, codigo_Cajero, codigo_Occa, valorAjuste);
                            tipoMovCuadre = com.davivienda.sara.constantes.TipoMovimientoCuadre.AJUSTE_AUMENTO_PROVISION.codigo;

                            break;
                        case Disminucion_Provision:

                            respuesta = sessionAjustes.realizarAjusteDisminucionProvision(usuario, codigo_Cajero, codigo_Occa, valorAjuste);
                            tipoMovCuadre = com.davivienda.sara.constantes.TipoMovimientoCuadre.AJUSTE_DISMINUCION_PROVISION.codigo;

                            break;
                        case Sobrante_Efectivo:
                            respuesta = sessionAjustes.realizarAjustePorSobrante(usuario, codigo_Cajero, codigo_Occa, valorAjuste);
                            tipoMovCuadre = com.davivienda.sara.constantes.TipoMovimientoCuadre.AJUSTE_SOBRANTE.codigo;

                            break;
                        case Faltante_Efectivo:
                            respuesta = sessionAjustes.realizarAjustePorFaltante(usuario, codigo_Cajero, codigo_Occa, valorAjuste);
                            tipoMovCuadre = com.davivienda.sara.constantes.TipoMovimientoCuadre.AJUSTE_FALTANTE.codigo;
                            break;
                        case Ajuste_Egresos:

                            respuesta = sessionAjustes.ajustarEgreso(usuario, codigo_Cajero, codigo_Occa, valorAjuste, new Short("35"));
                            tipoMovCuadre = com.davivienda.sara.constantes.TipoMovimientoCuadre.EGRESO.codigo;
                            break;
                        case Ajuste_Ingresos:
                            respuesta = sessionAjustes.ajustarIngreso(usuario, codigo_Cajero, codigo_Occa, valorAjuste, new Short("16"));
                            tipoMovCuadre = com.davivienda.sara.constantes.TipoMovimientoCuadre.INGRESO.codigo;

                            break;
                        default:

                            break;
                    }

                    loggerApp.log(Level.INFO, "El usuario : {0} realizo un ajuste al cajero {1} a la Occa: {2} por valor : {3} ajuste de el tipo: {4} con respuesta:{5}", new Object[]{usuario, codigo_Cajero.toString(), codigo_Occa.toString(), valorAjuste.toString(), tipoAjuste.nombre, respuesta});
                    if (respuesta != null) {
                        if (respuesta.length() > 0) {
                            if (respuesta.substring(0, 1).equals("B")) {
                                respuesta = "Ajuste Realizado con Exito";
                                guardarAjusteCuadre(codigo_Cajero, valorAjuste, usuario, tipoMovCuadre);
                            } else if (respuesta.substring(0, 1).equals("M")) {
                                respuesta = respuesta + "NO se pudo Realizar el Ajuste";
                            } else {
                                if (respuesta.substring(0, 1).equals("C")) {
                                    respuesta = respuesta + "NO se pudo Realizar el Ajuste y tampoco el Reverso correspondiente";
                                } else if (respuesta.substring(0, 1).equals("F")) {
                                    respuesta = respuesta + "Por favor verificar el  Estado de el Ajuste";
                                }
                            }
                        } else {
                            respuesta = respuesta + "Por favor verificar el  Estado de el Ajuste";
                        }
                    } else {
                        respuesta = "Por favor verificar el  Estado de el Ajuste";
                    }

                    abrirModal("SARA", respuesta, null);
                } catch (EJBException ex) {
                    loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
                } catch (Exception ex) {
                    loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
                }
            } else {
                abrirModal("SARA", "RANGO NO PERMITIDO", null);
            }

        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    private void guardarAjusteCuadre(Integer codigo_Cajero, BigDecimal valorAjuste, String usuario, Integer tipoMovCuadre) throws EntityServicioExcepcion {
        MovimientoCuadre mc;
        mc = new MovimientoCuadre();
        MovimientoCuadre mcDiferencia;
        Cajero cajero = null;
        cajero = cajeroSession.buscar(codigo_Cajero);
        TipoMovimientoCuadre tmc = null;
        //VALIDARA QUE EL CAJERO NO ESTE CREADO
        if (cajero != null) {
            mc.setCajero(cajeroSession.buscar(codigo_Cajero));
            mc.setValorMovimiento(valorAjuste.longValue());
            mc.setIdUsuario(usuario);
            mc.setFecha(com.davivienda.utilidades.conversion.Fecha.getDateAyer());
            mc.setFechaAjuste(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
            tmc = cuadreCifrasSessionLocal.buscarTipoMovimientoCuadre(tipoMovCuadre);
            mc.setTipoMovimientoCuadre(tmc);
            cuadreCifrasSessionLocal.grabarMovimientoCuadre(mc, false);
            try {
                if (tipoMovCuadre != 0) {
                    mcDiferencia = calcularDiferencia(codigo_Cajero, mc);
                    if (mcDiferencia != null) {
                        cuadreCifrasSessionLocal.grabarMovimientoCuadre(mcDiferencia, true);
                    }
                }
            } catch (Exception ex) {
            }

        }

    }

    private MovimientoCuadre calcularDiferencia(Integer codigocajero, MovimientoCuadre mc) throws EntityServicioExcepcion {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        InitBean initBean = (InitBean) viewMap.get("initBean");
        MovimientoCuadre mcDiferencia;
        mcDiferencia = cuadreCifrasSessionLocal.getMovimientoCuadre(codigocajero, com.davivienda.utilidades.conversion.Fecha.getCalendar(mc.getFecha()), com.davivienda.sara.constantes.TipoMovimientoCuadre.DIFERENCIAS.codigo);
        if (mcDiferencia != null) {

            com.davivienda.sara.constantes.TipoMovimientoCuadre tipoAjuste = com.davivienda.sara.constantes.TipoMovimientoCuadre.AJUSTE_AUMENTO_PROVISION;
            Long valorAjuste = mc.getValorMovimiento();//objectContext.getValorAjuste();
            tipoAjuste = com.davivienda.sara.constantes.TipoMovimientoCuadre.getTipoMovimientoCuadre(mc.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre());
            mcDiferencia.setIdUsuario(initBean.getObjectContext().getUsuarioEnSesion().getUsuario());
            mcDiferencia.setFechaAjuste(com.davivienda.utilidades.conversion.Fecha.getDateHoy());

            switch (tipoAjuste) {

                case AJUSTE_AUMENTO_PROVISION:

                    mcDiferencia.setValorMovimiento(mcDiferencia.getValorMovimiento() + valorAjuste);

                    break;

                case AJUSTE_DISMINUCION_PROVISION:
                    mcDiferencia.setValorMovimiento(mcDiferencia.getValorMovimiento() - valorAjuste);

                    break;
                case AJUSTE_SOBRANTE:
                    mcDiferencia.setValorMovimiento(mcDiferencia.getValorMovimiento() - valorAjuste);

                    break;
                case AJUSTE_FALTANTE:
                    mcDiferencia.setValorMovimiento(mcDiferencia.getValorMovimiento() + valorAjuste);
                    break;

                default:

                    break;

            }
        }
        return mcDiferencia;
    }

    /**
     * @return the tiposAjuste
     */
    public List<SelectItem> getTiposAjuste() {
        return tiposAjuste;
    }

    /**
     * @param tiposAjuste the tiposAjuste to set
     */
    public void setTiposAjuste(List<SelectItem> tiposAjuste) {
        this.tiposAjuste = tiposAjuste;
    }

    public String getAjusteSeleccionado() {
        return ajusteSeleccionado;
    }

    public void setAjusteSeleccionado(String ajusteSeleccionado) {
        this.ajusteSeleccionado = ajusteSeleccionado;
    }

    /**
     * @return the codigoCajero
     */
    public String getCodigoCajero() {
        return codigoCajero;
    }

    /**
     * @param codigoCajero the codigoCajero to set
     */
    public void setCodigoCajero(String codigoCajero) {
        this.codigoCajero = codigoCajero;
    }

    /**
     * @return the codigoOcca
     */
    public String getCodigoOcca() {
        return codigoOcca;
    }

    /**
     * @param codigoOcca the codigoOcca to set
     */
    public void setCodigoOcca(String codigoOcca) {
        this.codigoOcca = codigoOcca;
    }

    /**
     * @return the valorAjuste
     */
    public String getValorAjuste() {
        return valorAjuste;
    }

    /**
     * @param valorAjuste the valorAjuste to set
     */
    public void setValorAjuste(String valorAjuste) {
        this.valorAjuste = valorAjuste;
    }

}
