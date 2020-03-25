/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.cuadrecifras;

import com.davivienda.multifuncional.tablas.historicoajustesmulti.session.HistoricoAjustesMultiLocal;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.dto.CajeroDTO;
import com.davivienda.sara.dto.ConsultarAjustesDTO;
import com.davivienda.sara.dto.OficinaMultiDTO;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.Historicoajustesmulti;
import com.davivienda.sara.entitys.Oficinamulti;
import com.davivienda.sara.multifuncional.cuadrecifras.formato.TituloHistoricoConsultarAjustes;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.ofimulti.session.OfimultiSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author jmcastel
 */
@ManagedBean(name = "consultarAjustesMFBean")
@ViewScoped
public class ConsultarAjustesMFBean extends BaseBean implements Serializable {

    @EJB
    CajeroSessionLocal cajeroSession;

    @EJB
    OfimultiSessionLocal ofimultiSessionLocal;

    @EJB
    HistoricoAjustesMultiLocal session;

    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora = new ArrayList<SelectItem>();
    private List<SelectItem> cajeros = new ArrayList<SelectItem>();
    private List<ConsultarAjustesDTO> ajustes = new ArrayList<ConsultarAjustesDTO>();
    private Collection<Historicoajustesmulti> items;
    private List<SelectItem> listaOficina;
    private String cajeroSeleccionado;
    private String oficinaSeleccionado;
    private String fechaInicial;
    private String fechaFinal;
    private String horaInicial;
    private String horaFinal;
    private boolean mostrarRespuesta;
    private boolean mostrarPanelGeneral;

    @PostConstruct
    public void ConsultarAjustesMFBean() {
        try {

            List<CajeroDTO> cajerosItems = new ArrayList<CajeroDTO>();
            objectContext = cargarComponenteAjaxObjectContext();
            listaHora = cargarListaHora();
            this.mostrarRespuesta = false;
            this.mostrarPanelGeneral = true;
            if (objectContext != null) {
                Collection<Cajero> itemsCaj;

                itemsCaj = cajeroSession.getTodosActivosMulti(0, 5000);
                cajerosItems = objectContext.getCajeroCB(itemsCaj);
                this.setCajeros(cargarListaCajeros(cajerosItems));
                Collection<Oficinamulti> itemsOficina = ofimultiSessionLocal.getTodos();
                this.setListaOficina(cargarListaOficina(objectContext.getOfimultiCB(itemsOficina)));
            }

        } catch (Exception ex) {
            Logger.getLogger(ConsultarAjustesMFBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void usuarioInicial() {

        this.cajeroSeleccionado = "";
        this.oficinaSeleccionado = "-1";
        this.mostrarRespuesta = false;
        this.mostrarPanelGeneral = true;
        this.horaInicial = "00:00:00";
        this.horaFinal = "23:59:59";
    }

    public void consultar() {
        try {

            /*
            if (null == cajeroSeleccionado || cajeroSeleccionado.equals("") || cajeroSeleccionado.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return;
            }

            if (null == oficinaSeleccionado || oficinaSeleccionado.equals("-1") || oficinaSeleccionado.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_OFICINA, null);
                return;
            }
            */

            items = null;
            Date fechaInicial = null;
            Date fechaFinal = null;
            Integer codigoOficina = 0;
            Integer codigoCajero = 0;
            
            if (null == this.fechaInicial || this.fechaInicial.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }

            try {
                String[] splitCaj = buscarCodCaj(this.cajeroSeleccionado).split("-");
                codigoCajero = Integer.parseInt(splitCaj[0]);
                if (codigoCajero == 0) {
                    codigoCajero = 9999;
                }
            } catch (IllegalArgumentException ex) {
                codigoCajero = 9999;
            }
            try {
                //String[] splitOfc = buscarCodOficina(this.oficinaSeleccionado);
                codigoOficina = Integer.parseInt(this.oficinaSeleccionado);

            } catch (IllegalArgumentException ex) {
                codigoOficina = -1;
            }
//             Cajero cajero ;
//            cajero = cajeroSessionLocal.buscar(codigoCajero);
//            codigoOficina = cajero.getOficinaMulti().getCodigooficinamulti();

            if (codigoOficina == -1) {
                codigoOficina = null;
            }
            try {
                fechaInicial = this.getAtributoFechaHoraInicial().getTime();
            } catch (IllegalArgumentException ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            }
            try {
                fechaFinal = this.getAtributoFechaHoraFinal().getTime();
            } catch (IllegalArgumentException ex) {
                fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
               
            }

            try {
                objectContext.getConfigApp().loggerApp.info("ConsultarAjustesMfBean-consultar - fechaInicial " + fechaInicial  + " fechaFinal " + fechaFinal );
                
                // Consulta los registros según los parámetros tomados del request
                Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
                items = session.getColeccionHistoricoAjustes(codigoCajero, codigoOficina, fechaInicial, fechaFinal, fechaHisto);
                this.ajustes = convertItemsConsultarAjustes(items);
                this.mostrarRespuesta = true;
                this.mostrarPanelGeneral = false;
                if (items.size() == 0) {
                    abrirModal("SARA", "No se encontraron Registros para esta consulta", null);
                }
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            }

        } //        catch (EntityServicioExcepcion ex) {
        //            Logger.getLogger(CuadreCifrasMultiajustesUsuarioServletHelper.class.getName()).log(Level.SEVERE, null, ex);
        //        }
        catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
        }
        if (items == null || items.isEmpty()) {
            items = null;
        }

    }

    public void exportarExcel() {

        this.consultar();
        this.mostrarRespuesta = false;

        try {
            if (!this.ajustes.isEmpty()) {

                String[] titulosHoja = TituloHistoricoConsultarAjustes.tituloHoja;
                String[] titulosColumna = TituloHistoricoConsultarAjustes.tituloColumnas;
                Integer idRegistro = 0;
                Collection<Registro> lineas = new ArrayList<Registro>();
                Short numColumna;

                for (Historicoajustesmulti item : items) {
                    Registro reg = new Registro();
                    numColumna = 0;
                    reg.addCelda(new Celda(numColumna++, ++idRegistro, TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getTipoajuste(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getUsuario(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, String.valueOf(item.getCodigooficinamulti()), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getCodigocajero(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFecha(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getTalon(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValor()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, item.getError(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getDescripcionerror(), TipoDatoCelda.NORMAL));
                    lineas.add(reg);
                }

                ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("AjustesMultifuncional", titulosHoja, titulosColumna, lineas);
                objectContext.enviarArchivoXLS(archivo);
                this.mostrarPanelGeneral = true;
                this.mostrarRespuesta = false;
            }
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_CREAR_ARCHIVO, null);
        }
    }

    public List<ConsultarAjustesDTO> convertItemsConsultarAjustes(Collection<Historicoajustesmulti> items) {
        List<ConsultarAjustesDTO> consultaAjustes = new ArrayList<ConsultarAjustesDTO>();
        Integer idRegistro = 0;

        for (Historicoajustesmulti item : items) {
            ConsultarAjustesDTO ajuste = new ConsultarAjustesDTO();

            ajuste.setIdRegistro(++idRegistro);
            ajuste.setTipoAjuste(item.getTipoajuste());
            ajuste.setUsuario(item.getUsuario());
            ajuste.setCodigoOficina(String.valueOf(item.getCodigooficinamulti()));
            ajuste.setCodigoCajero(String.valueOf(item.getCodigocajero()));
            ajuste.setFecha(com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFecha(), FormatoFecha.FECHA_HORA));
            ajuste.setTalon(item.getTalon());
            ajuste.setValor(com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValor()));
            ajuste.setCodigoError(item.getError());
            ajuste.setDescripcionError(item.getDescripcionerror());

            consultaAjustes.add(ajuste);

        }
        return consultaAjustes;
    }

    public String buscarCodCaj(String codCaj) {
        String nomUser = "";
        for (SelectItem usr : cajeros) {
            if (codCaj.equalsIgnoreCase(String.valueOf(usr.getValue()))) {
                nomUser = String.valueOf(usr.getLabel());
                break;
            }
        }
        return nomUser;
    }

    public Calendar getAtributoFechaHoraInicial() throws IllegalArgumentException {
        String fechaStr = this.fechaInicial;
        String horaStr = this.horaInicial;
        fechaStr = fechaStr.concat(horaStr);
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        return calendar;
    }

    public Calendar getAtributoFechaHoraFinal() throws IllegalArgumentException {
        String fechaStr = this.fechaFinal;
        String horaStr = this.horaFinal;
        fechaStr = fechaStr.concat(horaStr);
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        return calendar;
    }
    
    public Calendar getAtributoFechaInicialHoraFinal() throws IllegalArgumentException {
        String fechaStr = this.fechaInicial;
        String horaStr = this.horaFinal;
        fechaStr = fechaStr.concat(horaStr);
        Calendar calendar = null;
        try {
            calendar = com.davivienda.utilidades.conversion.Cadena.aCalendar(fechaStr, FormatoFecha.FECHA_HORA_DOJO);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
        return calendar;
    }


    private List<SelectItem> cargarListaOficina(List<OficinaMultiDTO> oficinaCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem(-1, "Seleccione una");
        lista.add(item);
        int cont = 1;
        for (OficinaMultiDTO dto : oficinaCB) {
            item = new SelectItem(dto.getCodigooficinamulti(), dto.getNombre());
            lista.add(item);
            cont++;
        }
        return lista;
    }

    private List<SelectItem> cargarListaCajeros(List<CajeroDTO> cajerosCB) {
        int cont = 1;
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem item1 = new SelectItem(0, "Seleccione uno ...");
        lista.add(item1);

        for (CajeroDTO dto : cajerosCB) {
            SelectItem item = new SelectItem(cont, String.valueOf(dto.getCodigoCajero()) + "-" + dto.getNombre());
            lista.add(item);
            cont++;
        }
        return lista;
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

    public String buscarCodOficina(String cod) {
        String nomOcca = "";
        for (SelectItem usr : listaOficina) {
            if (cod.equalsIgnoreCase(String.valueOf(usr.getValue()))) {
                nomOcca = String.valueOf(usr.getLabel());
                break;
            }
        }
        return nomOcca;
    }

    /**
     * @return the cajeros
     */
    public List<SelectItem> getCajeros() {
        return cajeros;
    }

    /**
     * @param cajeros the cajeros to set
     */
    public void setCajeros(List<SelectItem> cajeros) {
        this.cajeros = cajeros;
    }

    public List<SelectItem> getListaOficina() {
        return listaOficina;
    }

    public void setListaOficina(List<SelectItem> listaOficina) {
        this.listaOficina = listaOficina;
    }

    /**
     * @return the cajeroSeleccionado
     */
    public String getCajeroSeleccionado() {
        return cajeroSeleccionado;
    }

    /**
     * @param cajeroSeleccionado the cajeroSeleccionado to set
     */
    public void setCajeroSeleccionado(String cajeroSeleccionado) {
        this.cajeroSeleccionado = cajeroSeleccionado;
    }

    public String getOficinaSeleccionado() {
        return oficinaSeleccionado;
    }

    public void setOficinaSeleccionado(String oficinaSeleccionado) {
        this.oficinaSeleccionado = oficinaSeleccionado;
    }

    /**
     * @return the ajustes
     */
    public List<ConsultarAjustesDTO> getAjustes() {
        return ajustes;
    }

    /**
     * @param ajustes the ajustes to set
     */
    public void setAjustes(List<ConsultarAjustesDTO> ajustes) {
        this.ajustes = ajustes;
    }

    public List<SelectItem> getListaHora() {
        return listaHora;
    }

    public void setListaHora(List<SelectItem> listaHora) {
        this.listaHora = listaHora;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public boolean isMostrarRespuesta() {
        return mostrarRespuesta;
    }

    public void setMostrarRespuesta(boolean mostrarRespuesta) {
        this.mostrarRespuesta = mostrarRespuesta;
    }

    public boolean isMostrarPanelGeneral() {
        return mostrarPanelGeneral;
    }

    public void setMostrarPanelGeneral(boolean mostrarPanelGeneral) {
        this.mostrarPanelGeneral = mostrarPanelGeneral;
    }

}
