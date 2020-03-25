/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.entitys.CarmasReintegrosTemp;
import com.davivienda.sara.procesos.cargues.masivos.reintegros.session.CargarReintegrosMasivosSessionLocal;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.formato.TituloCargasMasivasReintegros;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author jaiarca
 */
public class CargarReintegrosMasivosServletHelper implements ReintegrosHelperInterface {

    public static final String LISTA_REINTEGROS_MASIVOS = "listaReintegrosMasivos";
    public static final String REINTEGROS_MASIVOS = "Reporte cargas masivas reintegros";

    private CargarReintegrosMasivosSessionLocal sessionLocal;
    private ReintegrosGeneralObjectContext objectContext;

    public CargarReintegrosMasivosServletHelper(ReintegrosGeneralObjectContext objectContext, CargarReintegrosMasivosSessionLocal sessionLocal) {
        this.objectContext = objectContext;
        this.sessionLocal = sessionLocal;
    }

    @Override
    public String obtenerDatos() {
        String respuesta = "";
        List<CarmasReintegrosTemp> listaReintegrosMasivos = (List<CarmasReintegrosTemp>) objectContext.getAtributo(LISTA_REINTEGROS_MASIVOS);
        if (listaReintegrosMasivos != null) {
            String usuarioEnSesion = objectContext.getUsuarioEnSesion().getUsuario();
            List<CarmasReintegrosTemp> listaData = (List<CarmasReintegrosTemp>) sessionLocal.ejecutarCargue(listaReintegrosMasivos, usuarioEnSesion);
            if (listaData != null) {
                if (listaData.size() > 0) {
                    respuesta = generarReporte(listaData);
                } else {
                    respuesta = "Error cargando el archivo";
                }
            } else {
                respuesta = "Error cargando el archivo";
            }
        } else {
            respuesta = "Error cargando el archivo";
        }
        return respuesta;
    }

    public String generarReporte(List<CarmasReintegrosTemp> items) {
        String[] titulosHoja = TituloCargasMasivasReintegros.tituloHoja;
        String[] titulosColumna = TituloCargasMasivasReintegros.tituloColumnasReintegros;

        List<Registro> lineas = new ArrayList<>();
        Short numColumna;
        try {
            for (CarmasReintegrosTemp item : items) {
                Registro reg = new Registro();
                numColumna = 0;

                reg.addCelda(new Celda(numColumna++, valNull((String) item.getCausal()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getNroIdentificacionDeudor()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getCodigoUnico()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getNroCuenta()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getTipoCuenta()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getValor()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getTalon()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getComision()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getFecha()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getOficinaRecuado()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getErrorTransaccion()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getConcepto()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getTipo()), TipoDatoCelda.NORMAL));
                reg.addCelda(new Celda(numColumna++, valNull((String) item.getResultadoCarga()), TipoDatoCelda.NORMAL));
                lineas.add(reg);
            }
            titulosHoja[1] = com.davivienda.utilidades.conversion.Fecha.aCadena(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), com.davivienda.utilidades.conversion.FormatoFecha.FECHA_HORA);
            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro(REINTEGROS_MASIVOS, titulosHoja, titulosColumna, lineas);
            objectContext.enviarArchivoXLS(archivo);
        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Cargue procesado con exito";
    }

    public String valNull(String value) {
        return (value == null) ? "" : value;
    }
}
