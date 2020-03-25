/*
 * Banco Davivienda 2008
 * Proyecto Utilidades
 * Versi�n  1.0
 */
package com.davivienda.utilidades.archivoxls;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * Celda - 10/08/2008
 * Descripci�n : Informaci�n de cada celda
 * Versi�n : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class Celda {

    protected Short columna;
    protected Object valor;
    protected TipoDatoCelda tipoDato;

    
    
    /**
     * 
     * @param columna
     * @param valor
     * @param tipoDatoCelda 
     * @throws java.lang.IllegalArgumentException
     */
    public Celda(Short columna, Object valor, TipoDatoCelda tipoDatoCelda) throws IllegalArgumentException {
        if (columna == null ) throw new IllegalArgumentException("Debe asignar el n�mero de la celda indicando el n�mero de la columna iniciando en cero");
        if (valor == null ) throw new IllegalArgumentException("No se adminten celdas en nulo");
        this.columna = columna;        
        this.valor = valor;
        this.tipoDato = (tipoDatoCelda != null) ? tipoDatoCelda : TipoDatoCelda.NUMERICO;
    }

}
