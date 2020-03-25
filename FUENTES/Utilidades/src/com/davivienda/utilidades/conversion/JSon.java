package com.davivienda.utilidades.conversion;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSon.java
 *
 * Fecha       :  18/05/2007, 11:20:49 AM
 * Descripción :  Utilizada para crear o convertir objectos a Java Script Object Notation
 *
 * @author     : jjvargas
 * @version    : $Id: JSon.java,v 1.1 2007/05/18 23:23:45 jjvargas Exp $
 */
public class JSon {

    /**
     * Crea una nueva instancia de <code>JSon</code>.
     */
    public JSon() {
    }

    /**
     * Retorna una cadena de tipo JSon
     * @param obj
     * @return
     */
    public static String aCadena(JSONObject obj) {
        return obj.toString();
    }

    /**
     * Crea una cadena JSON a partir de los valores dados en el objeto,  filtrando solo los atributos pasados en  atributos
     * @param objeto
     * @param atributos
     * @return
     */
    public static String aCadena(Object objeto, String[] atributos) {
        JSONObject jsonObject = aJSon(objeto, atributos);
        return jsonObject.toString();
    }

    /**
     * Retorna una cadena JSON de tipo respuesta {"error"="codigoError" "Mensaje"="mensaje"} o {"Mensaje"="mensaje"}
     * @param codigoError
     * @param mensaje
     * @return
     */
    public static String getJSonRespuesta(Integer codigoError, String mensaje) {
        String respuesta = "";
        try {
            if (!codigoError.equals(0)) {
                respuesta = new org.json.JSONObject().put("error", codigoError).put("mensaje", mensaje).toString();
            } else {
                respuesta = new JSONObject().put("mensaje", mensaje).toString();
            }
        } catch (JSONException ex) {
            throw new IllegalArgumentException("No se puede crear el objeto " + ex.getMessage());
        }

        return respuesta;
    }

    /**
     * Retorna una cadena JSON de tipo respuesta {"error"="codigoError" "Mensaje"="mensaje"} o {"Mensaje"="mensaje"}
     * El mensaje es contrido a partir de la excepción dada.
     * @param codigoError
     * @param tituloMensaje 
     * @param ex
     * @return 
     */
    public static String getJSonRespuesta(Integer codigoError, String tituloMensaje, Exception ex) {
        String respuesta = "";
        String mensaje = tituloMensaje;
        if (ex != null) {
            mensaje += "\nError : " + ex.getClass().getName();
            mensaje += "\n" + ex.getMessage();
            mensaje += "\n" + ex.getLocalizedMessage();
        }
        try {
            if (!codigoError.equals(0)) {
                respuesta = new org.json.JSONObject().put("error", codigoError).put("mensaje", mensaje).toString();
            } else {
                respuesta = new JSONObject().put("mensaje", mensaje).toString();
            }
        } catch (JSONException ex1) {
            throw new IllegalArgumentException("No se puede crear el objeto " + ex1.getMessage());
        }

        return respuesta;
    }

    public static String getJSonProceso(Integer codigoError, String mensaje, Integer estadoProceso) throws IllegalArgumentException {
        String respuesta = "";
        try {
            if (!codigoError.equals(0)) {
                respuesta = new org.json.JSONObject().put("error", codigoError).put("mensaje", mensaje).put("estadoProceso", estadoProceso).toString();
            } else {
                respuesta = new JSONObject().put("mensaje", estadoProceso).toString();
            }
        } catch (JSONException ex) {
            throw new IllegalArgumentException("No se puede crear el objeto " + ex.getMessage());
        }

        return respuesta;
    }

    /**
     * Retorna el valor del atributo dado desde la cadena JSON
     * @param cadenaJSON
     * @param atributoABuscar
     * @return String
     * @throws java.lang.IllegalArgumentException
     */
    public static String getValorAtributo(String cadenaJSON, String atributoABuscar) throws IllegalArgumentException {
        String resp = "";
        try {
            JSONObject json = new JSONObject(cadenaJSON);
            resp = json.getString(atributoABuscar);
        } catch (Exception ex) {
            throw new IllegalArgumentException("No se puede encontrar el objeto " + atributoABuscar + " " + ex.getMessage());
        }
        return resp;
    }

    /**
     * Crea un objeto JSON a partir de los valores dados en el objeto,
     * Los atributos resultado son determinados por el arreglo atributos compuesto por nombre y formato del valor por ejm :
     * El atributo diferencia de tipo Long se debe pasar a JSon con formato moneda seria :
     * [diferencia;MONEDA]
     *
     * Formatos aceptados :
     * MONEDA : Convierte un número  a $###,###,###
     * FECHA :  Convierte la fecha a formato yyyy-MM-dd
     * @param objeto
     * @param atributos
     * @return
     */
    public static JSONObject aJSon(Object objeto, String[] atributos) {
        JSONObject jsonObject = new JSONObject();
        Class c = objeto.getClass();
        for (int i = 0; i < atributos.length; i += 1) {
            try {
                String[] atributo = atributos[i].split(";");
                String nombre = atributo[0];
                Field field = c.getField(nombre);
                Object valor = field.get(objeto);
                String formato = "";
                String valorConFormato = "";
                if (atributo.length == 2) {
                    formato = atributo[1];
                    if (formato.equals("MONEDA")) {
                        if (valor instanceof Long) {
                            valorConFormato = Numero.aMoneda((Long) valor);
                        }
                    }
                    if (formato.equals("FECHA")) {
                        if (valor instanceof java.util.Date) {
                            valorConFormato = Fecha.aCadena((java.util.Date) valor, FormatoFecha.DEFECTO_SEPARADOR_GUION);
                        }
                        if (valor instanceof java.util.Calendar) {
                            valorConFormato = Fecha.aCadena((java.util.Calendar) valor, FormatoFecha.DEFECTO_SEPARADOR_GUION);
                        }
                    }
                    java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.FINEST, "Nombre -->" + nombre + " Dato --> " + valor + " Formato --> " + formato + " " + valorConFormato);
                    jsonObject.put(nombre, valorConFormato);
                }
                if (formato.equals("")) {
                    jsonObject.put(nombre, valor);
                }
            } catch (Exception e) {
                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, e.getMessage(), e);
            }
        }
        return jsonObject;
    }

    /**
     * Retorna un string JSon con el mensaje de Transacción procesada correctamente y error cero
     * @return
     */
    public static String getRespuestaJSonOk() {
        return "{\"mensaje\":\"Transacción procesada correctamente\", \"error\":0}";
    }

    /**
     * Crea una cadena JSon a partir de los atributos y valores dados, se toma la correspondencia entre ambos arreglos
     * para determinar las parejas atributo-valor
     * @param atributos
     * @param valores
     * @return
     */
    public static String crearCadenaJSon(String[] atributos, Object[] valores) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < atributos.length; i++) {
            try {
                java.lang.String strTmp = atributos[i];
                jsonObject.put(strTmp, valores[i]);
            } catch (JSONException ex) {
            // No dee pasar
            }
        }
        return jsonObject.toString();
    }

    /**
     * Crea una cadena JSon a partir de las parejas dadas en el Map , la llave es el nombre del atributo y el objeto su valor
     * @param valores
     * @return - String JSON
     */
    public static String crearCadenaJSon(Map<String, Object> valores) {
        JSONObject jsonObject = new JSONObject();
        for (Iterator it = valores.keySet().iterator(); it.hasNext();) {
            try {
                String atributo = (String) it.next();
                jsonObject.put(atributo, valores.get(atributo));
            } catch (JSONException ex) {
                java.util.logging.Logger.getLogger("globalApp").log(Level.SEVERE, null, ex);
            }
        }
        return jsonObject.toString();
    }

    /**
     * Crea una cadena JSon a partir de la pareja atributo-valor dada
     * @param atributo
     * @param valor
     * @return
     */
    public static String crearCadenaJSon(String atributo, Object valor) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(atributo, valor);
        } catch (JSONException ex) {
        // No dee pasar
        }
        return jsonObject.toString();
    }
}

