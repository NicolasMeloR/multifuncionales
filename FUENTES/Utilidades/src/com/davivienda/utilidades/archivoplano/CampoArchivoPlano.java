package com.davivienda.utilidades.archivoplano;

/**
 * CampoArchivoPlano.java
 * 
 * Fecha       :  29/06/2007, 01:56:16 PM
 * Descripción :  Estructura de un registro del archivo plano a leer
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */
public class CampoArchivoPlano {
    
    /**
     * Nombre con el cual se bsucará el valor despues de  haber sido leído
     */
    public String nombreCampo;
    
    /**
     * Posición del campo en el registro
     */
    public int orden;
    
    /**
     * Posición inicial del campo
     */
    public int posInicial ;
    
    /**
     * Longitud el campo
     */
    public int longitud = 0;
    
    /**
     * Posición final del campo, si longitud es diferente a 0 se toma este valor
     */
    public int posFinal ;
    
    public boolean esCampoFiltro = false;
    
    public String[] valoresFiltro ;
    

    
    
    

    /**
     * Crea una nueva instancia de <code>EstructuraRegistro</code>.
     */
    public CampoArchivoPlano() {
        
    }
    
    /**
     * Crea una nueva instancia de <code>EstructuraRegistro</code>.
     * @param nombreCampo 
     * @param orden 
     * @param posInicial 
     * @param longitud 
     * @param posFinal 
     * @param esCampoFiltro 
     */
    public CampoArchivoPlano(String nombreCampo,int orden,int posInicial,int longitud,int posFinal, boolean esCampoFiltro) {
        this.nombreCampo = nombreCampo;
        this.longitud = longitud;
        this.orden = orden;
        this.posFinal = posFinal;
        this.posInicial = posInicial;
        this.esCampoFiltro = esCampoFiltro;
    }
    
    public CampoArchivoPlano(String nombreCampo,int orden,int posInicial, int longitud ) {
        this(nombreCampo, orden, posInicial, longitud, 0, false);
    }
    
    public CampoArchivoPlano(String nombreCampo,int orden,int posInicial, int longitud, boolean esCampoFiltro ) {
        this(nombreCampo, orden, posInicial, longitud, 0, esCampoFiltro);
    }

}

