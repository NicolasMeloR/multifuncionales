/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.cuadreCajeros;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.dto.CajeroDTO;
import com.davivienda.sara.dto.ConsultarAjustesDTO;
import com.davivienda.sara.dto.OccaDTO;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.HistoricoAjustes;
import com.davivienda.sara.entitys.Occa;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.historicoajustes.session.HistoricoAjustesLocal;
import com.davivienda.sara.tablas.occa.session.OccaSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.io.IOException;
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
@ManagedBean(name = "consultarAjustesBean")
@ViewScoped
public class ConsultarAjustesBean extends BaseBean implements Serializable {

    @EJB
    CajeroSessionLocal cajeroSession;

    @EJB
    OccaSessionLocal occaSession;

    @EJB
    HistoricoAjustesLocal sessionAjustes;

    public ComponenteAjaxObjectContextWeb objectContext;
    private List<SelectItem> listaHora = new ArrayList<SelectItem>();
    private List<SelectItem> cajeros = new ArrayList<SelectItem>();
    private List<ConsultarAjustesDTO> ajustes = new ArrayList<ConsultarAjustesDTO>();
    private List<SelectItem> listaOcca;
    private String cajeroSeleccionado;
    private String occaSeleccionado;
    private String fechaInicial;
    private String fechaFinal;
    private String horaInicial;
    private String horaFinal;
    private boolean mostrarRespuesta;
    private boolean mostrarPanelGeneral;

    @PostConstruct
    public void ConsultarAjustesBean() {
        try {

            List<CajeroDTO> cajerosItems = new ArrayList<CajeroDTO>();
            objectContext = cargarComponenteAjaxObjectContext();
            listaHora = cargarListaHora();
            this.mostrarPanelGeneral = true;
            this.mostrarRespuesta = false;
            if (objectContext != null) {
                Collection<Cajero> itemsCaj;

                itemsCaj = cajeroSession.getTodosActivos(0, 5000);
                cajerosItems = objectContext.getCajeroCB(itemsCaj);
                this.setCajeros(cargarListaCajeros(cajerosItems));
                Collection<Occa> itemsOcca = occaSession.getTodos();
                this.setListaOcca(cargarListaOcca(objectContext.getOccaCB(itemsOcca)));
            }
            usuarioInicial();
        } catch (Exception ex) {
            Logger.getLogger(ConsultarAjustesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void usuarioInicial() {
        this.fechaInicial = "";
        this.fechaFinal = "";
        this.horaInicial = "00:00:00";
        this.horaFinal = "23:59:59";
        this.cajeroSeleccionado = "";
        this.occaSeleccionado = "";
        this.listaHora = cargarListaHora();
        this.mostrarPanelGeneral = true;
        this.mostrarRespuesta = false;
    }

    public Collection<HistoricoAjustes> consultar() {
        String error = "";

        Collection<HistoricoAjustes> items = null;
        try {

            Date fechaInicial = null;
            Date fechaFinal = null;
            Integer codigoOcca = 0;
            Integer codigoCajero = 0;

            if (null == this.occaSeleccionado || this.occaSeleccionado.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_OCCA, null);
                return null;
            }

            if (null == this.cajeroSeleccionado || this.cajeroSeleccionado.isEmpty()) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_CAJERO, null);
                return null;
            }

            if (this.occaSeleccionado != null && !this.occaSeleccionado.equals("0")) {
                String[] splitOcca = buscarCodOcca(this.occaSeleccionado).split("-");
                codigoOcca = Integer.parseInt(splitOcca[0].trim());
            }
            if (codigoOcca == 0) {
                codigoOcca = null;
            }
            try {
                if (this.cajeroSeleccionado != null && !this.cajeroSeleccionado.equals("0")) {
                    String[] splitCajero = buscarCodCaj(this.cajeroSeleccionado).split("-");
                    codigoCajero = Integer.parseInt(splitCajero[0].trim());
                } else {
                    codigoCajero = 9999;
                }
            } catch (IllegalArgumentException ex) {
                codigoCajero = 9999;
            }

            try {
                fechaInicial = this.getAtributoFechaHoraInicial().getTime();
            } catch (IllegalArgumentException ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            }
            if(null == this.fechaFinal || this.fechaFinal.isEmpty()){
                fechaFinal = this.getAtributoFechaInicialHoraFinal().getTime();
            }else{
                try {
                    fechaFinal = this.getAtributoFechaHoraFinal().getTime();
                } catch (IllegalArgumentException ex) {
                    fechaFinal =  com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
                }
            }
            try {
                // Consulta los registros según los parámetros tomados del request
                Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
                items = sessionAjustes.getColeccionHistoricoAjustes(codigoCajero, codigoOcca, fechaInicial, fechaFinal, fechaHisto);
                this.ajustes = convertItemsConsultarAjustes(items);
                this.mostrarPanelGeneral = false;
                this.mostrarRespuesta = true;
                if (items.size() == 0) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                    return null;
                }
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //abrirModal("SARA", "Error Consultando Entitys", ex);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return null;
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //abrirModal("SARA", "Error consultando Entitys", null);
                    abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                    return null;
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    abrirModal("SARA", Constantes.MSJ_QUERY_SIN_DATA, null);
                    return null;
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
                return items;
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_QUERY_ERROR, null);
            return items;
        }
        if (items == null || items.isEmpty()) {
            items = null;
        }

        return items;
    }

    public void generarAjustesXML() {
        Collection<HistoricoAjustes> regs;
        regs = this.consultar();
        Integer idRegistro = 0;
        this.mostrarRespuesta = false;
        if (null != regs && !regs.isEmpty()) {
            //Creo la hoja de cálculo
            String[] titulosHoja = TituloHistoricoAGeneral.tituloHoja;
            String[] titulosColumna = TituloHistoricoAGeneral.tituloColumnas;
            //CAJERO", "NOMBRE", "TRANSACCION", "FECHA", "TALON", "CUENTA", "TARJETA", "VALOR ENTREGADO","REFERENCIA","CODIGO ERROR","CODIGO TERMINACION"
            Collection<Registro> lineas = new ArrayList<Registro>();
            Short numColumna;
            try {

                for (HistoricoAjustes item : regs) {
                    Registro reg = new Registro();
                    numColumna = 0;
                    reg.addCelda(new Celda(numColumna++, ++idRegistro, TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getTipoAjuste(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getUsuario(), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getCodigoOcca(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, item.getCodigoCajero(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFecha(), FormatoFecha.FECHA_HORA), TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, item.getTalon(), TipoDatoCelda.NUMERICO));
                    reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValor()), TipoDatoCelda.MONEDA));
                    reg.addCelda(new Celda(numColumna++, (item.getError() != null) ? item.getError() : "", TipoDatoCelda.NORMAL));
                    reg.addCelda(new Celda(numColumna++, (item.getDescripcionError() != null) ? item.getDescripcionError() : "", TipoDatoCelda.NORMAL));
                    lineas.add(reg);
                }

                if (!lineas.isEmpty()) {
                    ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("Ajustes", titulosHoja, titulosColumna, lineas);
                    objectContext.enviarArchivoXLS(archivo);
                    usuarioInicial();
                }
            } catch (IllegalArgumentException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", Constantes.MSJ_ERROR_CREAR_ARCHIVO, null);
            } catch (IOException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", Constantes.MSJ_ERROR_DESCARGAR_ARCHIVO, null);
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
            }

        }

    }

    public List<ConsultarAjustesDTO> convertItemsConsultarAjustes(Collection<HistoricoAjustes> items) {
        List<ConsultarAjustesDTO> consultaAjustes = new ArrayList<ConsultarAjustesDTO>();
        Integer idRegistro = 0;

        for (HistoricoAjustes item : items) {
            ConsultarAjustesDTO ajuste = new ConsultarAjustesDTO();

            ajuste.setIdRegistro(++idRegistro);
            ajuste.setTipoAjuste(item.getTipoAjuste());
            ajuste.setUsuario(item.getUsuario());
            ajuste.setCodigoOcca(String.valueOf(item.getCodigoOcca()));
            ajuste.setCodigoCajero(String.valueOf(item.getCodigoCajero()));
            ajuste.setFecha(com.davivienda.utilidades.conversion.Fecha.aCadena(item.getFecha(), FormatoFecha.FECHA_HORA));
            ajuste.setTalon(item.getTalon());
            ajuste.setValor(com.davivienda.utilidades.conversion.Numero.aMoneda(item.getValor()));
            ajuste.setCodigoError(item.getError());
            ajuste.setDescripcionError(item.getDescripcionError());

            consultaAjustes.add(ajuste);
        }
        return consultaAjustes;
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

    private List<SelectItem> cargarListaOcca(List<OccaDTO> occaCB) {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem(0, "Seleccione una");
        lista.add(item);
        int cont = 1;
        for (OccaDTO dto : occaCB) {
            item = new SelectItem(cont, dto.getCodigoOcca() + " - " + dto.getNombre());
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

    public String buscarCodOcca(String cod) {
        String nomOcca = "";
        for (SelectItem usr : listaOcca) {
            if (cod.equalsIgnoreCase(String.valueOf(usr.getValue()))) {
                nomOcca = String.valueOf(usr.getLabel());
                break;
            }
        }
        return nomOcca;
    }

    public String buscarCodCaj(String cod) {
        String codCaj = "";
        for (SelectItem usr : cajeros) {
            if (cod.equalsIgnoreCase(String.valueOf(usr.getValue()))) {
                codCaj = String.valueOf(usr.getLabel());
                break;
            }
        }
        return codCaj;
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

    /**
     * @return the listaOcca
     */
    public List<SelectItem> getListaOcca() {
        return listaOcca;
    }

    /**
     * @param listaOcca the listaOcca to set
     */
    public void setListaOcca(List<SelectItem> listaOcca) {
        this.listaOcca = listaOcca;
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

    /**
     * @return the occaSeleccionado
     */
    public String getOccaSeleccionado() {
        return occaSeleccionado;
    }

    /**
     * @param occaSeleccionado the occaSeleccionado to set
     */
    public void setOccaSeleccionado(String occaSeleccionado) {
        this.occaSeleccionado = occaSeleccionado;
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
