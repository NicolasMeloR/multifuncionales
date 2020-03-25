package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasSessionLocal;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.CarmasTimbresTemp;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.sara.exception.ServiceInExecutionException;
import com.davivienda.sara.procesos.ajustes.session.AjustesProcesosSessionLocal;
import com.davivienda.sara.procesos.cargues.masivos.timbres.session.CargarTimbresMasivosSessionLocal;
import com.davivienda.sara.reintegros.general.CargarTimbresMasivosObjectContext;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.formato.TituloCargasMasivasTimbres;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import com.davivienda.utilidades.conversion.Cadena;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.management.InvalidApplicationException;

/**
 * CargarTimbresMasivosServletHelper - 22/10/2019 Descripción : Helper para el
 * manejo de los timbres/ajustes : 1.0
 *
 * @author juan.jaime Asesoftware 2019
 */
public class CargarTimbresMasivosServletHelper implements ReintegrosHelperInterface {

    @SuppressWarnings("FieldMayBeFinal")
    private CargarTimbresMasivosSessionLocal sessionLocal;
    @SuppressWarnings("FieldMayBeFinal")
    private CargarTimbresMasivosObjectContext cargarTimbresMasivosObjectContext;
    @SuppressWarnings("FieldMayBeFinal")
    private ComponenteAjaxObjectContextWeb componenteAjaxObjectContextWeb;
    @SuppressWarnings("FieldMayBeFinal")
    private AjustesProcesosSessionLocal sessionAjustes;
    @SuppressWarnings("FieldMayBeFinal")
    private CuadreCifrasSessionLocal cuadreCifrasSessionLocal;
    @SuppressWarnings("FieldMayBeFinal")
    private CajeroSessionLocal cajeroSession;
    private Logger loggerApp;

    /**
     * Constructor
     *
     * @param sessionLocal
     * @param componenteAjaxObjectContextWeb
     * @param sessionAjustes
     * @param cuadreCifrasSessionLocal
     * @param cajeroSession
     */
    public CargarTimbresMasivosServletHelper(CargarTimbresMasivosSessionLocal sessionLocal, CargarTimbresMasivosObjectContext cargarTimbresMasivosObjectContext, ComponenteAjaxObjectContextWeb componenteAjaxObjectContextWeb, AjustesProcesosSessionLocal sessionAjustes, CuadreCifrasSessionLocal cuadreCifrasSessionLocal, CajeroSessionLocal cajeroSession) {
        this.sessionLocal = sessionLocal;
        this.cargarTimbresMasivosObjectContext = cargarTimbresMasivosObjectContext;
        this.componenteAjaxObjectContextWeb = componenteAjaxObjectContextWeb;
        this.sessionAjustes = sessionAjustes;
        this.cuadreCifrasSessionLocal = cuadreCifrasSessionLocal;
        this.cajeroSession = cajeroSession;
        this.loggerApp = this.cargarTimbresMasivosObjectContext.getConfigApp().loggerApp;
    }

    /**
     *
     * @return resultado de la ejecución
     * @throws javax.management.InvalidApplicationException
     */
    @Override
    public String obtenerDatos() {
        String respuesta = null;
        String respuestaTimbre = null;
        String usuario = cargarTimbresMasivosObjectContext.getIdUsuarioEnSesion();
        Integer tipoMovCuadre = 0;
        Integer codigo_Cajero;
        Integer codigo_Occa;
        BigDecimal valorAjusteAumento;
        BigDecimal valorAjusteDisminucion;
        BigDecimal valorAjusteSobrante;
        BigDecimal valorAjusteFaltante;
        boolean validarRespuestaAjuste = false;
        boolean realizoAjuste = false;

        try {
            Collection<CarmasTimbresTemp> timbres = (Collection<CarmasTimbresTemp>) cargarTimbresMasivosObjectContext.getAtributo("listaTimbres");
            timbres = this.sessionLocal.ejecutarCargue(timbres, usuario);
            for (CarmasTimbresTemp timbre : timbres) {
                if (timbre != null) {
                    validarRespuestaAjuste = false;
                    realizoAjuste = false;
                    respuesta = "";
                    respuestaTimbre = "Iniciando aplicacion del timbre para : \n";
                    codigo_Cajero = Cadena.aInteger(timbre.getCajero() == null || timbre.getCajero().trim().isEmpty() ? "0" : timbre.getCajero());
                    codigo_Occa = Cadena.aInteger(timbre.getOcca() == null || timbre.getOcca().trim().isEmpty() ? "0" : timbre.getOcca());
                    valorAjusteAumento = timbre.getAumento() != null && !timbre.getAumento().trim().equals("") ? Cadena.aBigDecimal(timbre.getAumento()) : new BigDecimal(0);
                    valorAjusteDisminucion = timbre.getDisminucion() != null && !timbre.getDisminucion().trim().equals("") ? Cadena.aBigDecimal(timbre.getDisminucion()) : new BigDecimal(0);
                    valorAjusteSobrante = timbre.getSobrante() != null && !timbre.getSobrante().trim().equals("") ? Cadena.aBigDecimal(timbre.getSobrante()) : new BigDecimal(0);
                    valorAjusteFaltante = timbre.getFaltante() != null && !timbre.getFaltante().trim().equals("") ? Cadena.aBigDecimal(timbre.getFaltante()) : new BigDecimal(0);
                    respuestaTimbre += "codigo Cajero:" + codigo_Cajero + ", codigo Occa: " + codigo_Occa + ", valor Ajuste Aumento: " + valorAjusteAumento + ", valor Ajuste Disminucion: " + valorAjusteDisminucion + ", valor Ajuste Sobrante: " + valorAjusteSobrante + ", valor Ajuste Faltante: " + valorAjusteFaltante + "\n";
                    if (valorAjusteAumento.intValue() > 0) {
                        try {
                            realizoAjuste = true;
                            respuesta = sessionAjustes.realizarAjusteAumentoProvision(usuario, codigo_Cajero, codigo_Occa, valorAjusteAumento);
                            respuestaTimbre += "\nAplicacion stratus ajuste aumento provision: " + respuesta + ", timbre guardado exitosamente en sara\n";
                        } catch (Exception e) {
                            respuestaTimbre += "\nOcurrio un error realizando el timbre ajuste aumento provision\n" + e.getMessage();
                            loggerApp.log(Level.SEVERE, "Ocurrio un error realizando el timbre sobrante aumento provision " + e.getMessage(), e);
                        }
                    }
                    if (valorAjusteDisminucion.intValue() > 0) {
                        try {
                            realizoAjuste = true;
                            respuesta = sessionAjustes.realizarAjusteDisminucionProvision(usuario, codigo_Cajero, codigo_Occa, valorAjusteDisminucion);
                            respuestaTimbre += "\nAplicacion stratus ajuste disminucion provision: " + respuesta + ", Timbre guardado exitosamente en sara\n";
                            timbre.setResultadoCarga(respuestaTimbre);
                        } catch (Exception e) {
                            respuestaTimbre += "\nOcurrio un error realizando el timbre ajuste disminución provision " + e.getMessage();
                            loggerApp.log(Level.SEVERE, " Ocurrio un error realizando el timbre sobrante disminución provision " + e.getMessage(), e);
                        }
                    }
                    if (valorAjusteSobrante.intValue() > 0) {
                        try {
                            realizoAjuste = true;
                            respuesta = sessionAjustes.realizarAjustePorSobrante(usuario, codigo_Cajero, codigo_Occa, valorAjusteSobrante);
                            respuestaTimbre += "\nAplicacion stratus ajuste sobrante: " + respuesta + ",  Timbre guardado exitosamente en sara\n";
                            timbre.setResultadoCarga(respuestaTimbre);
                        } catch (Exception e) {
                            respuestaTimbre += "\nOcurrio un error realizando el timbre ajuste sobrante provision " + e.getMessage();
                            loggerApp.log(Level.SEVERE, " Ocurrio un error realizando el timbre sobrante faltante provision " + e.getMessage(), e);
                        }
                    }
                    if (valorAjusteFaltante.intValue() > 0) {
                        try {
                            realizoAjuste = true;
                            respuesta = sessionAjustes.realizarAjustePorFaltante(usuario, codigo_Cajero, codigo_Occa, valorAjusteFaltante);
                            respuestaTimbre += "\nAplicacion stratus ajuste faltante, " + respuesta + ", Timbre guardado exitosamente en sara\n";
                            timbre.setResultadoCarga(respuestaTimbre);
                        } catch (Exception e) {
                            respuestaTimbre += "\nOcurrio un error realizando el timbre ajuste faltante provision \n" + e.getMessage();
                            loggerApp.info("Ocurrio un error realizando el timbre ajuste faltante provision : " + e.getMessage());
                        }
                    }
                    try {
                        if (realizoAjuste) {
                            timbre.setResultadoCarga(respuestaTimbre.toUpperCase());
                        } else {
                            timbre.setResultadoCarga("*ERROR AUMENTO, DISMINUCION, SOBRANTE, FALTANTE SUMATORIA ES CERO, NO  ES POSIBLE REALIZAR NINGÚN  TIMBRE.");
                        }
                        sessionLocal.update(timbre);
                    } catch (Exception e) {
                        loggerApp.info("Ocurrio un error actualizando el ajuste en el sistema sara local : " + e.getMessage());
                        timbre.setResultadoCarga(respuestaTimbre + " Ocurrio un error actualizando el ajuste en el sistema sara local: " + e.getMessage());
                    }
                }
            }
            try {
                generarReporte(sessionLocal.getTodos());
                String errorEjecucion = sessionLocal.actualizarLog(usuario);
            } catch (Exception e) {
                loggerApp.info("CargarTimbresMasivosServletHelper obtenerDatos error : " + e.getMessage());
                return "Ocurrio un error generando el reporte por favor valide el cargue de los timbres";
            }
        } catch (ServiceInExecutionException ex) {
            return "El servicio de cargues masivos timbres se encuentra en ejecucion, por favor intente mas tarde.";
        }
        return "";
    }

    public String valNull(String value) {
        return (value == null) ? "0" : value;
    }

    /**
     * Generar el reporte del sistema
     *
     * @param items
     * @return
     */
    public String generarReporte(Collection<CarmasTimbresTemp> items) {

        String respuesta = "";
        String[] titulosHoja = TituloCargasMasivasTimbres.tituloHoja;
        String[] titulosColumna = TituloCargasMasivasTimbres.tituloColumnas;

        Collection<Registro> lineas = new ArrayList<Registro>();
        Short numColumna;
        try {
            for (CarmasTimbresTemp item : items) {
                Registro reg = new Registro();
                numColumna = 0;
                reg.addCelda(new Celda(numColumna++, item.getCajero() == null ? "" : item.getCajero(), TipoDatoCelda.NUMERICO));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(Integer.parseInt(item.getProvdiasgteMaq() == null ? "0" : item.getProvdiasgteMaq())), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(Integer.parseInt(item.getProvdiasgteLin() == null ? "0" : item.getProvdiasgteLin())), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(Integer.parseInt(item.getDiferencias() == null ? "0" : item.getDiferencias())), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, item.getObservacion() == null ? "" : item.getObservacion(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getOcca() == null ? "0" : item.getOcca(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(Integer.parseInt(item.getAumento() == null ? "0" : item.getAumento())), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(Integer.parseInt(item.getDisminucion() == null ? "0" : item.getDisminucion())), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(Integer.parseInt(item.getSobrante() == null ? "0" : item.getSobrante())), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, com.davivienda.utilidades.conversion.Numero.aMoneda(Integer.parseInt(item.getFaltante() == null ? "0" : item.getFaltante())), TipoDatoCelda.MONEDA));
                reg.addCelda(new Celda(numColumna++, item.getNovedad() == null ? "" : item.getNovedad(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getAsignadoA() == null ? "" : item.getAsignadoA(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getProveedor() == null ? "" : item.getProveedor(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getClasificacion() == null ? "" : item.getClasificacion(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getTipificacionTransportadora() == null ? "" : item.getTipificacionTransportadora(), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, item.getResultadoCarga() == null ? "" : item.getResultadoCarga(), TipoDatoCelda.NORMAL));
                lineas.add(reg);
            }

            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("Timbre Masivo Para Ajustes", titulosHoja, titulosColumna, lineas);
            cargarTimbresMasivosObjectContext.enviarArchivoXLS(archivo);
        } catch (IllegalArgumentException ex) {
            cargarTimbresMasivosObjectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            cargarTimbresMasivosObjectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return respuesta;
    }

}
