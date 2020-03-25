/*
 * Banco Davivienda 2008
 * Proyecto Utilidades
 * Versión  1.0
 */
package com.davivienda.utilidades.archivoxls;

import java.util.Calendar;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * LineaXLS - 10/08/2008
 * Descripción : representa una línea de la hoja
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class LineaXLS {

    protected ArchivoXLS archivo;
    protected Integer numeroLinea;
    protected HSSFCellStyle estilo;
    protected EstiloLineaXLS estiloLinea;
    private HSSFRow linea;

    public LineaXLS(ArchivoXLS archivo, Integer numeroLinea, EstiloLineaXLS estiloLinea) {
        this.numeroLinea = numeroLinea;
        this.archivo = archivo;
        this.linea = this.archivo.hoja.createRow(numeroLinea);
        this.estiloLinea = estiloLinea;
        asignarEstiloLinea();
    }

    public HSSFRow getLinea() {
        return linea;
    }

    public void setLinea(HSSFRow linea) {
        this.linea = linea;
    }
    
    

    /**
     * Crea el objeto HSSFCell de la celda en la columna de la línea dada
     * @param c
     * @throws IllegalArgumentException 
     */
    public void asignarCelda(Celda c) throws IllegalArgumentException {
        if (this.linea == null) {
            throw new IllegalArgumentException("No se asignó correctamente la línea a la hoja");
        }
        HSSFCell celda = linea.createCell(c.columna);
        asignarValorCelda(celda, c);
        asignarEstiloCelda(celda, c);
    }

    private void asignarValorCelda(HSSFCell celda, Celda c) {
        if (c.valor instanceof String) {
            HSSFRichTextString ts = new HSSFRichTextString((String) c.valor);
            celda.setCellValue(ts);
        }
        if (c.valor instanceof Date) {
            celda.setCellValue((Date) c.valor);
        }
        if (c.valor instanceof Calendar) {
            celda.setCellValue((Calendar) c.valor);
        }
        if (c.valor instanceof Integer) {
            Double d = ((Integer) c.valor).doubleValue();
            celda.setCellValue(d);
        }
        if (c.valor instanceof Short) {
            Double d = ((Short) c.valor).doubleValue();
            celda.setCellValue(d);
        }        
        if (c.valor instanceof Double) {
            Double d =  (Double)c.valor;
            celda.setCellValue(d);
        }
    }

    private void asignarEstiloCelda(HSSFCell celda, Celda c) {
        switch (c.tipoDato) {
            case NUMERICO:
                celda.setCellStyle(archivo.estiloCeldaDatoNumerico);
                break;
            case FECHA:
                celda.setCellStyle(archivo.estiloCeldaDatoFecha);
                break;
            case MONEDA:
                celda.setCellStyle(archivo.estiloCeldaDatoMoneda);
                break;
            case NORMAL:
                celda.setCellStyle(archivo.estiloLineaNormal);
                break;                
            default:
                celda.setCellStyle(this.estilo);
        }
    }

    private void asignarEstiloLinea() {
        if (estiloLinea != null) {
            switch (estiloLinea) {
                case DATOS:
                    estilo = archivo.estiloLineaNormal;
                    break;
                case TITULO_COLUMNA:
                    estilo = archivo.estiloTituloColumna;
                    break;
                case TITULO_HOJA:
                    estilo = archivo.estiloTituloHoja;
                    break;
                    //SUBTITULO_COLUMNA
                case SUBTITULO_HOJA:
                    estilo = archivo.estiloSubtituloTituloHoja;
                    break;
                 case TITULO_GRANDE:
                    estilo = archivo.estiloTituloGrandeHoja;
                    break;   
                    
                     case TITULO_HOJA_NORMAL:
                    estilo = archivo.estiloTituloHojaNormal;
                    break;   
                   
                 case TITULO_HOJA_NORMAL_CENTRADO:
                    estilo = archivo.estiloTituloHojaNormalCentrado;
                    break; 
                    
                 case SUBTITULO_HOJA_NORMAL:
                    estilo = archivo.estiloSubTituloNormal;
                    break; 
                
                case SUBTITULO_FIRMA:
                    estilo = archivo.estiloSubTituloFirma;
                    break; 
                default:
                    estilo = archivo.estiloLineaNormal;
            }
        } else {
            estiloLinea = EstiloLineaXLS.DATOS;
            estilo = archivo.estiloLineaNormal;
        }

    }
}
