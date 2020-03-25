/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.util;

import org.json.JSONObject;

/**
 *
 * @author jediazs
 */
public class ConvertidorJSon {
    
    
    public static String getValorAtributoSinExcepcion(String cadenaJSON, String atributoABuscar){
       String resp = "";
       try {
           JSONObject json = new JSONObject(cadenaJSON);
           resp = json.getString(atributoABuscar);
       } catch (Exception ex) {
           resp = "";
           //throw new IllegalArgumentException("No se puede encontrar el objeto " + atributoABuscar + " " + ex.getMessage());
       }
       return resp;
   }
    
}
