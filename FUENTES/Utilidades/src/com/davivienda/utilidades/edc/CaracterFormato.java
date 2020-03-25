package com.davivienda.utilidades.edc;


/**
 * CaracterFormato.java
 *
 * Fecha       :  30/05/2007, 07:49:09 PM
 * Descripción :  Tabla de conversión de caracteres de formato
 *
 * @author     : jjvargas
 * @version    : $Id$
 */


public enum CaracterFormato {
    
    LINE_FEED("a", "\n",true),
    ALT_CHAR_SET("b", "", true),
    CARRIAGE_RETURN("d", "",true),
    INSERT__BLANKS1("e1"," ",false),
    INSERT__BLANKS2("e2","  ",true),
    INSERT__BLANKS3("e3","   ",true),
    INSERT__BLANKS4("e4","    ",true),
    INSERT__BLANKS5("e5","     ",false),
    INSERT__BLANKS6("e6","      ",false),
    INSERT__BLANKS7("e7","       ",true),
    INSERT__BLANKS8("e8","        ",false),
    INSERT__BLANKS9("e9","         ",false),
    INSERT__BLANKS15("e?","              ",true),    
    ESCAPE("s(", "",true);
    
    
    
    public String cadena;
    public String representacion;
    public boolean activo;
    
    /**
     * Constructor de <code>CaracterFormato</code>.
     */
    CaracterFormato(String unaCadena, String unaRepresentacion, boolean activo) {
        this.cadena = unaCadena;
        this.representacion = unaRepresentacion;
        this.activo = activo;
    }
    
    public static CaracterFormato getTipoRegistro(char unCaracter) {
        return getTipoRegistro(String.valueOf(unCaracter));
    }
    
    public static CaracterFormato getTipoRegistro(String unaCadena) {
        CaracterFormato caracterFormato = CaracterFormato.LINE_FEED;
        for (CaracterFormato elem : CaracterFormato.values()) {
            if (elem.cadena.equals(unaCadena)){
                caracterFormato = elem;
                break;
            }
        }
        return caracterFormato;
    }
    
    
    
    
}
