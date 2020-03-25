/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.cuadrecifras;

import com.davivienda.multifuncional.constantes.TipoAjusteMultifuncional;
import com.davivienda.multifuncional.ws.cuadrecifrasmulti.servicio.CuadreCifrasMultiWsSessionLocal;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasSessionLocal;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.entitys.Movimientocuadremulti;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.sara.procesos.ajustes.session.AjustesProcesosSessionLocal;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.utilidades.Constantes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
@ManagedBean(name = "realizarAjusteMFBean")
@ViewScoped
public class RealizarAjusteMFBean extends BaseBean implements Serializable {

    @EJB
    AjustesProcesosSessionLocal sessionAjustes;

    @EJB
    CajeroSessionLocal cajeroSession;

    @EJB
    CuadreCifrasSessionLocal cuadreCifrasSessionLocal;

    @EJB
    CuadreCifrasMultiWsSessionLocal cuadreCifrasMultiWsSessionLocal;

    private List<SelectItem> tiposAjuste = new ArrayList<SelectItem>();
    private String codigoCajero;
    private String codigoOcca;
    private String valorAjuste;
    private String ajusteSeleccionado;

    @PostConstruct
    public void RealizarAjusteBean() {

        SelectItem item1 = new SelectItem(0, "0 - Inicio");
        this.getTiposAjuste().add(item1);
        SelectItem item2 = new SelectItem(1, "1 - Sobrante");
        this.getTiposAjuste().add(item2);
        SelectItem item3 = new SelectItem(2, "2 - Faltante");
        this.getTiposAjuste().add(item3);
        SelectItem item4 = new SelectItem(3, "3 - Sobrante X Arqueo");
        this.getTiposAjuste().add(item4);
        SelectItem item5 = new SelectItem(4, "4 - Faltante X Arqueo");
        this.getTiposAjuste().add(item5);
        SelectItem item6 = new SelectItem(5, "5 - Disminucion");
        this.getTiposAjuste().add(item6);
        dataInicial();
    }

    public void dataInicial() {
        this.codigoCajero = "0";
        this.valorAjuste = "0";
        this.ajusteSeleccionado = "0";
    }

    public void guardarAjuste() {
        String respuesta = "";
        try {
            if (null == ajusteSeleccionado || ajusteSeleccionado.equals("0") || ajusteSeleccionado.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_TIPO_AJUSTE, null);
                return;
            }
            
              if (null == codigoCajero || codigoCajero.equals("0") || codigoCajero.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return;
            }
              

            String strExepcion = "";
            Integer codigo_Cajero = 0;
            Integer codigoOficina = 0;
            BigDecimal valorAjuste;

            String nit_transportadora;
            long nit_transportadoraLng;

            TipoAjusteMultifuncional tipoAjusteMultifuncional = TipoAjusteMultifuncional.Inicio;
            String usuario = "";
            Integer tipoMovCuadre = 0;
            boolean bln_esDepositario = false;
            boolean bln_esCajero = false;

            Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
            InitBean initBean = (InitBean) viewMap.get("initBean");
            nit_transportadoraLng = new Long(0);
            valorAjuste = new BigDecimal(this.valorAjuste.replace(Constantes.CARACTER_COMA, ""));
            tipoAjusteMultifuncional = TipoAjusteMultifuncional.getTipoAjusteMultifuncional(Integer.parseInt(this.getAjusteSeleccionado()));

            Movimientocuadremulti mc = null;
            boolean actualizar = true;

            nit_transportadora = String.valueOf(new Long(0));

            codigo_Cajero = Integer.parseInt(this.codigoCajero);
            Cajero cajero;//= new Cajero(codigo_Cajero);
            try {
                cajero = cajeroSession.buscar(codigo_Cajero);
                if (cajero != null) {
                    bln_esCajero = true;
                    codigoOficina = cajero.getOficinaMulti().getCodigooficinamulti();
                    bln_esDepositario = (cajero.getTipocajeroMulti() != 0);
                }
            } catch (EntityServicioExcepcion ex) {
                //abrirModal("SARA", "", ex);
                abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
            }

            valorAjuste = new BigDecimal(this.valorAjuste.replace(Constantes.CARACTER_COMA, ""));

            //tipoAjusteMultifuncional = objectContext.getTipoAjuste();
            usuario = initBean.getObjectContext().getUsuarioEnSesion().getUsuario();

            System.out.println("El usuario : " + usuario + " empezo a realizar un ajuste al cajero: " + codigo_Cajero.toString() + " a la Occa: " + codigoOficina.toString() + " a la transportadora: " + nit_transportadoraLng + " por valor : " + valorAjuste.toString() + " ajuste de el tipo: " + tipoAjusteMultifuncional.nombre);

            //Se revisa si el cajero es depositario
            if (bln_esDepositario && bln_esCajero) {

                try {

                    switch (tipoAjusteMultifuncional) {
                        case Inicio:
                            // Se asocia un object context particular 
                            //                objectContext = diarioElectronicoGeneralObjectContext;

                            break;
                        case Sobrante:
                            respuesta = cuadreCifrasMultiWsSessionLocal.realizarAjustePorSobrante(usuario, codigo_Cajero, codigoOficina, valorAjuste);
                            tipoMovCuadre = com.davivienda.multifuncional.constantes.TipoMovimientoCuadreMultifuncional.Sobrante.codigo;

                            break;
                        case Faltante:
                            respuesta = cuadreCifrasMultiWsSessionLocal.realizarAjustePorFaltante(usuario, codigo_Cajero, codigoOficina, valorAjuste);
                            tipoMovCuadre = com.davivienda.multifuncional.constantes.TipoMovimientoCuadreMultifuncional.Faltante.codigo;

                            break;
                        case Sobrante_Arqueo:
                            respuesta = cuadreCifrasMultiWsSessionLocal.realizarAjustePorSobranteArqueo(usuario, codigo_Cajero, codigoOficina, valorAjuste, nit_transportadora);
                            tipoMovCuadre = com.davivienda.multifuncional.constantes.TipoMovimientoCuadreMultifuncional.Sobrante_Arqueo.codigo;

                            break;
                        case Faltante_Arqueo:
                            respuesta = cuadreCifrasMultiWsSessionLocal.realizarAjustePorFaltanteArqueo(usuario, codigo_Cajero, codigoOficina, valorAjuste, nit_transportadora);
                            tipoMovCuadre = com.davivienda.multifuncional.constantes.TipoMovimientoCuadreMultifuncional.Faltante_Arqueo.codigo;
                            break;
                        case Disminucion:
                            //public String                           realizarDisminucionDeposito(long codigoTransportadora, BigDecimal valorEfectivo, Integer codigoOficina);
                            respuesta = cuadreCifrasMultiWsSessionLocal.realizarDisminucionDeposito(usuario, nit_transportadoraLng, codigo_Cajero, valorAjuste, codigoOficina);
                            tipoMovCuadre = com.davivienda.multifuncional.constantes.TipoMovimientoCuadreMultifuncional.Disiminucion.codigo;
                            break;
                        default:
                            break;
                    }
                    try {
                        //PENDIENTE GUARDAR HISTORICO
                        guardarAjusteCuadre(codigo_Cajero, valorAjuste, usuario, tipoMovCuadre);
                    } catch (EntityServicioExcepcion ex) {
                        //abrirModal("SARA", "Error Guardando Ajuste", ex);
                        abrirModal("SARA", "Error Guardando Ajuste", null);
                    }
                    System.out.println("El usuario : " + usuario + " realizo un ajuste al cajero " + codigo_Cajero.toString() + " a la Occa: " + codigoOficina.toString() + " por valor : " + valorAjuste.toString() + " ajuste de el tipo: " + tipoAjusteMultifuncional.nombre + " con respuesta:" + respuesta);
                    if (respuesta != null) {
                        if (respuesta.length() > 0) {
                            if (respuesta.substring(0, 1).equals("B")) {
                                respuesta = "Ajuste Realizado con Exito";
                                //                        try {
                                //                            cuadreCifrasMultiSessionLocal.grabarMovimientoCuadre(mc, actualizar);
                                //                        } catch (EntityServicioExcepcion ex) {
                                //                            Logger.getLogger(CuadreCifrasMultiGuardarAjusteServletHelper.class.getName()).log(Level.SEVERE, null, ex);
                                //                        }
                            } else if (respuesta.substring(0, 1).equals("M")) {
                                respuesta = respuesta + "NO se pudo Realizar el Ajuste";
                            } else if (respuesta.substring(0, 1).equals("C")) {
                                respuesta = respuesta + "NO se pudo Realizar el Ajuste y tampoco el Reverso correspondiente";
                            } else if (respuesta.substring(0, 1).equals("F")) {
                                respuesta = respuesta + "Por favor verificar el  Estado de el Ajuste";
                            }
                        } else {
                            respuesta = respuesta + "Por favor verificar el  Estado de el Ajuste";
                        }
                    } else {
                        respuesta = respuesta + "Por favor verificar el  Estado de el Ajuste";
                    }
                    abrirModal("SARA", respuesta, null);
                } catch (EJBException ex) {
                    if (ex.getMessage() == null) {
                        strExepcion = ex.getCause().getMessage();
                    } else {
                        strExepcion = ex.getMessage();
                    }
                    //abrirModal("SARA", "Error consultando Entyties", ex);
                     abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                }
            } else if (bln_esCajero) {
                abrirModal("SARA", "El Cajero Ingresado NO es Depostitario", null);
            } else {
                abrirModal("SARA", "El Cajero Ingresado NO esta creado", null);
            }

        } catch (Exception ex) {
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
