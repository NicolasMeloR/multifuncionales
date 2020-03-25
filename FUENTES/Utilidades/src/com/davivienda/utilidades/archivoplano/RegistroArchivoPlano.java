package com.davivienda.utilidades.archivoplano;

import com.davivienda.utilidades.archivoplano.ArchivoPlano;



/**
 * RegistroArchivoPlano.java
 * 
 * Fecha       :  29/06/2007, 02:29:11 PM
 * Descripción :  Descripción de los registros del archivo Plano
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */
public class RegistroArchivoPlano {
    
    protected String nombreRegistro;
    
    protected FormatoCampo formatoCampo;
    
    private boolean filtrarRegistros;
        
    protected String caracterSeparadorCampos;
    
    protected boolean incluirNombreCampo = true;
    
    protected int longitudRegistro = ArchivoPlano.TAMANO_BUFFER_LECTURA; 
    
    protected String charSet = ArchivoPlano.CHAR_SET;
    
    protected CampoArchivoPlano[] campos;
    


    /**
     * Crea una nueva instancia de <code>RegistroArchivoPlano</code>.
     */
    public RegistroArchivoPlano() {
    }

    /**
     * Crea una nueva instancia de <code>RegistroArchivoPlano</code>.
     * @param nombreRegistro 
     * @param formatoCampo 
     * @param tamanoRegistro 
     */
    public RegistroArchivoPlano(String nombreRegistro, FormatoCampo formatoCampo, int tamanoRegistro) {
        this.formatoCampo = formatoCampo;
        this.nombreRegistro = nombreRegistro;
        this.longitudRegistro = tamanoRegistro;
    }
    
    /**
     * Asigan el caracter separador de campos
     * @param caracterSeparadorCampos 
     */
    public void setCaracterSeparadorCampos(String caracterSeparadorCampos) {
        this.caracterSeparadorCampos = caracterSeparadorCampos;
    }
    
    /**
     * Asigana los campos del registro
     * @param campos 
     */
    public void setCampos(CampoArchivoPlano[] campos) {
        this.campos = campos;
        for (int i = 0; i < campos.length; i++) {
            CampoArchivoPlano campo = campos[i];
            if ( campo.esCampoFiltro ) {
                filtrarRegistros = true;
                break;
            }
        }
    }
    
    public void setLongitudRegistro(int longitud) {
        this.longitudRegistro = longitud;
    }
    
    public void setFormatoCampo(FormatoCampo formato) {
        this.formatoCampo = formato;
    }

    public boolean esIncluirNombreCampo() {
        return incluirNombreCampo;
    }

    public void setIncluirNombreCampo(boolean incluirNombreCampo) {
        this.incluirNombreCampo = incluirNombreCampo;
    }
    
    


    
}

