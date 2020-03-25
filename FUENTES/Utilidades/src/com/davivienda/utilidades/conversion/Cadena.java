package com.davivienda.utilidades.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import org.json.JSONObject;

/**
 * Cadena.java
 *
 * Fecha       : 4 de mayo de 2007, 03:51 PM
 *
 * Descripción : Utilidades de conversión de una cadena
 *
 * @author     : jjvargas
 *
 * @version    : $Id: Cadena.java,v 1.3 2007/05/18 23:23:45 jjvargas Exp $
 */
public class Cadena {

    /** Crea una nueva instancia de Cadena */
    public Cadena() {
    }

     /**
     * Convierte un string a Integer
     * @param valor String
     * @return Integer
     * @throws java.lang.NumberFormatException Excepción
     */
    public static BigDecimal aBigDecimal(String valor) throws NumberFormatException {
        BigDecimal numero = null;
        if (valor != null) {
            if (valor.length() > 0) {
                numero = new BigDecimal(valor.trim());
            } else {
                throw new NumberFormatException("Valor a convertir vacio");
            }
        } else {
            throw new NumberFormatException("Valor a convertir nulo");
        }
        return numero;
    }
    
    /**
     * Convierte un string a Integer
     * @param valor String
     * @return Integer
     * @throws java.lang.NumberFormatException Excepción
     */
    public static Integer aInteger(String valor) throws NumberFormatException {
        Integer numero = null;
        if (valor != null) {
            if (valor.length() > 0) {
                numero = new Integer(valor.trim());
            } else {
                throw new NumberFormatException("Valor a convertir vacio");
            }
        } else {
            throw new NumberFormatException("Valor a convertir nulo");
        }
        return numero;
    }

    /**
     * Convierte un string a Integer , si la conversión da error se retorna valorDefecto
     * @param valor
     * @param valorDefecto
     * @return Integer
     */
    public static Integer aInteger(String valor, String valorDefecto) {
        Integer tmp = new Integer(valorDefecto);
        try {
            tmp = aInteger(valor);
        } catch (Exception ex) {
        // Toma el valor por defecto
        }
        return tmp;
    }

    /**
     * Convierte una subcadena a Integer a partir de la cadena dada
     * @param valor String completo
     * @param posIni Posición inical del substring en la cadena , inicia en 0;
     * @param posFinal Posición final del substring en la cadena dada, inicia en 1;
     * @throws java.lang.NumberFormatException Si hay error en la convesión
     * @return Integer
     */
    public static Integer aInteger(String valor, Integer posIni, Integer posFinal) throws NumberFormatException {
        Integer numero = null;
        try {
            numero = aInteger(subCadena(valor, posIni, posFinal));
        } catch (Exception ex) {
            throw new NumberFormatException(ex.getMessage());
        }
        return numero;
    }

    /**
     * Convierte una subcadena a Integer a partir de la cadena dada, si la conversión da error se retorna valorDefecto
     * @param valor
     * @param posIni
     * @param posFinal
     * @param valorDefecto
     * @return Integer
     */
    public static Integer aInteger(String valor, Integer posIni, Integer posFinal, String valorDefecto) {
        Integer tmp = new Integer(valorDefecto);
        try {
            tmp = aInteger(valor, posIni, posFinal);
        } catch (Exception ex) {
        // Toma el valor por defecto
        }
        return tmp;
    }

    /**
     * Retona un entero con notación de Stratus de signi Incluído
     * @see SignoANumero
     * @param valor
     * @param conSignoInculido
     * @return
     */
    public static Integer aInteger(String valor, boolean conSignoInculido) {
        Integer tmp = null;
        if (!conSignoInculido) {
            tmp = aInteger(valor);
        } else {
            String StringTmp = valor.substring(valor.trim().length() - 1).trim();
            int intSigno = SignoANumero.getNumero(StringTmp.charAt(0));
            StringTmp = subCadena(valor, 0, valor.trim().length() - 1) + intSigno;
            tmp = aInteger(StringTmp);
        }
        return tmp;
    }

    /**
     * Convierte un string a Short
     * @param valor String
     * @return Short
     * @throws java.lang.NumberFormatException Excepción
     */
    public static Short aShort(String valor) throws NumberFormatException {
        return aInteger(valor).shortValue();
    }

    /**
     * Convierte un string a Short , si la conversión da error se retorna valorDefecto
     * @param valor
     * @param valorDefecto
     * @return Short
     */
    public static Short aShort(String valor, String valorDefecto) {
        Short tmp = new Short(valorDefecto);
        try {
            tmp = aShort(valor);
        } catch (Exception ex) {
        // Toma el valor por defecto
        }
        return tmp;
    }

    /**
     * Convierte una subcadena a Short a partir de la cadena dada
     * @param valor String completo
     * @param posIni Posición inical del substring en la cadena , inicia en 0;
     * @param posFinal Posición final del substring en la cadena dada, inicia en 1;
     * @throws java.lang.NumberFormatException Si hay error en la convesión
     * @return Short
     */
    public static Short aShort(String valor, Integer posIni, Integer posFinal) throws NumberFormatException {
        return aInteger(valor, posIni, posFinal).shortValue();
    }

    /**
     * Convierte una subcadena a Short a partir de la cadena dada, si la conversión da error se retorna valorDefecto
     * @param valor
     * @param posIni
     * @param posFinal
     * @param valorDefecto
     * @return Short
     */
    public static Short aShort(String valor, Integer posIni, Integer posFinal, String valorDefecto) {
        return aInteger(valor, posIni, posFinal, valorDefecto).shortValue();
    }

    /**
     * Convierte un string a Long
     * @param valor String
     * @return Long
     * @throws java.lang.NumberFormatException Excepción
     */
    public static Long aLong(String valor) throws NumberFormatException {
        Long numero = null;
        if (valor != null) {
            if (valor.length() > 0) {
                numero = new Long(valor.trim());
            } else {
                throw new NumberFormatException("Valor a convertir vacio");
            }
        } else {
            throw new NumberFormatException("Valor a convertir nulo");
        }
        return numero;
    }

    /**
     * Convierte un string a Long , si la conversión da error se retorna valorDefecto
     * @param valor
     * @param valorDefecto
     * @return Long
     */
    public static Long aLong(String valor, String valorDefecto) {
        Long tmp = new Long(valorDefecto);
        try {
            tmp = aLong(valor);
        } catch (Exception ex) {
        // Toma el valor por defecto
        }
        return tmp;
    }

    /**
     * Convierte una subcadena a Long a partir de la cadena dada
     * @param valor String completo
     * @param posIni Posición inical del substring en la cadena , inicia en 0;
     * @param posFinal Posición final del substring en la cadena dada, inicia en 1;
     * @throws java.lang.NumberFormatException Si hay error en la convesión
     * @return Long
     */
    public static Long aLong(String valor, Integer posIni, Integer posFinal) throws NumberFormatException {
        Long numero = null;
        try {
            String sc = Cadena.subCadena(valor, posIni, posFinal);
            numero = aLong(sc);
        } catch (Exception ex) {
            throw new NumberFormatException(ex.getMessage());
        }
        return numero;
    }

    /**
     * Convierte una subcadena a Long a partir de la cadena dada, si la conversión da error se retorna valorDefecto
     * @param valor
     * @param posIni
     * @param posFinal
     * @param valorDefecto
     * @return Long
     */
    public static Long aLong(String valor, Integer posIni, Integer posFinal, String valorDefecto) {
        Long tmp = new Long(valorDefecto);
        try {
            tmp = aLong(valor, posIni, posFinal);
        } catch (Exception ex) {
        // Toma el valor por defecto
        }
        return tmp;
    }

    /**
     * Retona un Long con notación de Stratus de signo Incluído
     * @see SignoANumero
     * @param valor
     * @param conSignoInculido
     * @return
     */
    public static Long aLong(String valor, boolean conSignoInculido) {
        Long tmp = null;
        if (!conSignoInculido) {
            tmp = aLong(valor);
        } else {
            String StringTmp = valor.substring(valor.trim().length() - 1).trim();
            int intSigno = SignoANumero.getNumero(StringTmp.charAt(0));
            StringTmp = subCadena(valor, 0, valor.trim().length() - 1) + intSigno;
            tmp = aLong(StringTmp);
        }
        return tmp;
    }

    /**
     * Retorna un objeto Long quitando las dos últimas posiciones
     * @param valor
     * @return
     * @throws java.lang.NumberFormatException
     */
    public static Long aValor(String valor) throws NumberFormatException {
        return aLong(valor) / 100;
    }

    /**
     * Retorna un objeto Long quitando las dos últimas posiciones, si la conversión da error se toma el valor por defecto
     * @param valor
     * @param valorDefecto 
     * @return
     * @throws java.lang.NumberFormatException
     */
    public static Long aValor(String valor, String valorDefecto) throws NumberFormatException {
        Long lngTmp = aLong(valor, valorDefecto);
        if (lngTmp.longValue() != 0L) {
            lngTmp = lngTmp/100;
        }
        return lngTmp;
    }
    
    /**
     * Retorna un objeto Long de la subcadena quitando las dos últimas posiciones
     * @param valor
     * @param posIni
     * @param posFinal
     * @return
     * @throws java.lang.NumberFormatException
     */
    public static Long aValor(String valor, Integer posIni, Integer posFinal) throws NumberFormatException {
        return aLong(valor, posIni, posFinal) / 100;
    }

    
    /**
     * Retorna la subcadena del string dado
     * @return la subcadena
     * @param valor String completo
     * @param posIni Posición inical del substring en la cadena , inicia en 0;
     * @param posFinal Posición final del substring en la cadena dada, inicia en 1, si se da -1 se toma el valor de la 
     * longitud de la cadena pasada en el parámetro valor;
     * @throws java.lang.IllegalArgumentException Si un parï¿½metro es nulo o vacio
     */
    public static String subCadena(String valor, Integer posIni, Integer posFinal) throws IllegalArgumentException {
        String cadena = null;
        if (valor == null || posIni == null || posFinal == null) {
            throw new IllegalArgumentException("Parámetro nulo");
        }
        if (valor.length() == 0) {
            throw new IllegalArgumentException("Parámetro vacio");
        }
        try {
            if (posFinal > 0) {
                cadena = valor.substring(posIni, posFinal);
            } else {
                cadena = valor.substring(posIni);
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return cadena;
    }

    /**
     * Convierte una cadena fecha formateada con el formato <code>formato</code> a Calendar
     * @param valor la fecha
     * @param formato el formato en que viene la cadena
     * @return objeto Calendar
     * @see com.davivienda.utilidades.conversion.FormatoFecha
     * @throws java.lang.IllegalArgumentException Si no se puede crear el objeto Calendar
     */
    public static Calendar aCalendar(String valor, String formato) throws IllegalArgumentException {
        if (valor == null || formato == null) {
            throw new IllegalArgumentException("Parámetro nulo");
        }
        if (valor.length() == 0 || formato.length() == 0) {
            throw new IllegalArgumentException("Parámetro nulo");
        }
        Calendar fecha = Calendar.getInstance(Fecha.ZONA_FECHA);
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Fecha.ZONA_FECHA);
        sdf.setLenient(false);
        try {
            sdf.parse(valor);
            fecha = sdf.getCalendar();

        } catch (Exception ex) {
            throw new IllegalArgumentException("La cadena de fecha " + valor + " no es válida con el formato: " + formato);
        }
        sdf = null;
        return fecha;
    }

    /**
     * Convierte una cadena fecha formateada con el formato <code>FormatoFecha</code> a Calendar
     * @param valor la fecha
     * @param formatoFecha objeto de tipo FormatoFecha{@link com.davivienda.utilidades.conversion.FormatoFecha}
     * @return objeto Calendar
     * @throws java.lang.IllegalArgumentException Si no se puede obtener el objecto Calendar
     */
    public static Calendar aCalendar(String valor, FormatoFecha formatoFecha) throws IllegalArgumentException {
        return aCalendar(valor, formatoFecha.formato);
    }
    
    //adiciona la cantidad tamano de ceros a la izquierda de intNumero
     public static String aCerosIzquierda(Integer intNumero, int tamano) {
        String numZeros=  intNumero.toString();
        try
        {
        Formatter fmt = new Formatter();
        numZeros=(String) fmt.format("%0"+tamano+"d",intNumero).toString(); 
        }
        catch(Exception ex){}
        return numZeros;
    }


    /**
     * Convierte una cadena fecha formateada con el formato <code>FormatoFecha.DEFAULT</code> a Calendar
     * @param valor La fecha
     * @throws java.lang.IllegalArgumentException Si no se puede obtener el objecto Calendar
     * @return Objeto Calendar
     * @see com.davivienda.utilidades.conversion.FormatoFecha
     */
    public static Calendar aCalendar(String valor) throws IllegalArgumentException {
        if (valor == null) {
            throw new IllegalArgumentException("Parámetro inválido");
        }
        FormatoFecha formato = FormatoFecha.DEFECTO;
        if (valor.indexOf('-') > 0) {
            formato = FormatoFecha.DEFECTO_SEPARADOR_GUION;
        }
        return aCalendar(valor, formato);
    }

    /**
     * Convierte una cadena fecha formateada con el formato <code>formato</code> a Date
     * @param valor La fecha
     * @param formato el formato en que viene la cadena
     * @throws java.lang.IllegalArgumentException Si no se puede obtener un objeto Date
     * @return Objeto Date
     * @see com.davivienda.utilidades.conversion.FormatoFecha
     */
    public static Date aDate(String valor, String formato) throws IllegalArgumentException {
        Calendar calendar = aCalendar(valor, formato);
        return calendar.getTime();
    }

    /**
     * Convierte una cadena fecha formateada con el formato <code>FormatoFecha</code> a Date
     * @param valor la fecha
     * @param formatoFecha objeto de tipo FormatoFecha{@link com.davivienda.utilidades.conversion.FormatoFecha}
     * @return objeto Date
     * @throws java.lang.IllegalArgumentException Si no se puede obtener el objecto Date
     */
    public static Date aDate(String valor, FormatoFecha formatoFecha) throws IllegalArgumentException {
        return aDate(valor, formatoFecha.formato);
    }

    /**
     * Convierte una cadena fecha formateada con el formato <code>FormatoFecha.DEFAULT</code> a Date
     * @param valor La fecha
     * @throws java.lang.IllegalArgumentException Si no se puede obtener el objeto Date
     * @return objeto Date
     * @see com.davivienda.utilidades.conversion.FormatoFecha
     */
    public static Date aDate(String valor) throws IllegalArgumentException {
        return aDate(valor, FormatoFecha.DEFECTO.formato);
    }

    public static JSONObject aJSON(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Parámetro nulo");
        }
        if (valor.length() <= 2) {
            throw new IllegalArgumentException("Parámetro inválido");
        }
        JSONObject obj = null;
        try {
            obj = new JSONObject().optJSONObject(valor);
        } catch (Exception ex) {
            throw new IllegalArgumentException("La cadena no contiene un formato válido JSON " + ex.getMessage());
        }
        return obj;
    }

    /**
     * Valida que la cadena dada no sea null y tenga al menos un caracter
     * @param valor
     * @return boolean
     */
    public static boolean contieneAlgo(String valor) {
        if (valor == null) {
            return false;
        } else {
            if (valor.length() <= 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

       
        System.out.print(aDate("2018/07/30 14:23:51", FormatoFecha.FECHA_HORA));
    }
}



