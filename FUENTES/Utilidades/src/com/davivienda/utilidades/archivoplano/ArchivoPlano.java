package com.davivienda.utilidades.archivoplano;

import com.davivienda.utilidades.archivo.Archivo;

/**
 * ArchivoPlano.java
 *
 * Fecha       :  29/06/2007, 09:12:26 AM
 * Descripción :  Objecto que maneja el acceso a los archivos físicos
 *
 * @author     : jjvargas
 * @version    : $Id$
 */
public class ArchivoPlano extends Archivo {

    protected RegistroArchivoPlano registroControl;
    protected RegistroArchivoPlano registroData;
    protected RegistroArchivoPlano registroResumen;
    protected CampoArchivoPlano[] camposFiltro;
    protected CampoArchivoPlano[] camposRegistro;
    

    /**
     * Crea una nueva instancia de <code>ArchivoPlano</code>.
     */
    public ArchivoPlano() {
        this(null, null);
    }

    /**
     * Crea un objeto ArchivoPlano con el nombre del archivo asociado
     * @param nombreArchivo
     */
    public ArchivoPlano(String nombreArchivo) {
        this(null, nombreArchivo);
    }

    /**
     * Crea un objeto ArchivoPlano con el nombre y directorio del archivo asociados
     * @param directorio
     * @param nombreArchivo
     */
    public ArchivoPlano(String directorio, String nombreArchivo) {
        super(nombreArchivo, directorio);
    }

    public void setRegistroControl(RegistroArchivoPlano registroControl) {
        this.registroControl = registroControl;
    }

    public void setRegistroData(RegistroArchivoPlano registroData) {
        this.registroData = registroData;
    }

    public void setRegistroResumen(RegistroArchivoPlano registroResumen) {
        this.registroResumen = registroResumen;
    }
}

