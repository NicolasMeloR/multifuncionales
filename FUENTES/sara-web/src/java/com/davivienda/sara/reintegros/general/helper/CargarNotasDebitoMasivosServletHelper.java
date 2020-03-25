/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.entitys.CarmasNotasDebitoTemp;
import com.davivienda.sara.procesos.cargues.masivos.notasdebito.session.CargarNotasDebitoMasivosSessionLocal;
import com.davivienda.sara.reintegros.general.NotasDebitoGeneralObjectContext;
import com.davivienda.sara.reintegros.general.NotasDebitoHelperInterface;
import com.davivienda.sara.reintegros.general.formato.TituloCargasMasivasReintegros;
import com.davivienda.sara.reintegros.general.formato.TituloCargasMasivasNotasDebito;
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
public class CargarNotasDebitoMasivosServletHelper implements NotasDebitoHelperInterface {

    public static final String LISTA_NOTAS_DEBITO_MASIVOS = "listaNotasDebitoMasivos";
    public static final String NOTAS_DEBITO_MASIVOS = "NotasDebitoMasivo";

    private CargarNotasDebitoMasivosSessionLocal sessionLocal;
    private NotasDebitoGeneralObjectContext objectContext;

    public CargarNotasDebitoMasivosServletHelper(NotasDebitoGeneralObjectContext objectContext, CargarNotasDebitoMasivosSessionLocal sessionLocal) {
        this.objectContext = objectContext;
        this.sessionLocal = sessionLocal;
    }

    @Override
    public String obtenerDatos() {
        String respuesta = "";
        List<CarmasNotasDebitoTemp> listaNotasDebitoMasivos = (List<CarmasNotasDebitoTemp>) objectContext.getAtributo(LISTA_NOTAS_DEBITO_MASIVOS);
        if (listaNotasDebitoMasivos != null) {
            String usuarioEnSesion = objectContext.getUsuarioEnSesion().getUsuario();
            List<CarmasNotasDebitoTemp> listaData = (List<CarmasNotasDebitoTemp>) sessionLocal.ejecutarCargue(listaNotasDebitoMasivos, usuarioEnSesion);
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

    public String generarReporte(List<CarmasNotasDebitoTemp> items) {
        String[] titulosHoja = TituloCargasMasivasNotasDebito.tituloHoja;
        String[] titulosColumna = TituloCargasMasivasNotasDebito.tituloColumnas;

        List<Registro> lineas = new ArrayList<>();
        Short numColumna;
        try {
            for (CarmasNotasDebitoTemp item : items) {
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
            ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro(NOTAS_DEBITO_MASIVOS, titulosHoja, titulosColumna, lineas);
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
