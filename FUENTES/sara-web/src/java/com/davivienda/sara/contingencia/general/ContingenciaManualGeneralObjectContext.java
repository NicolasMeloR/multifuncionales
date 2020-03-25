package com.davivienda.sara.contingencia.general;

import com.davivienda.sara.base.BaseObjectContextWeb;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * DiarioElectronicoGeneralObjectContext - 27/08/2008
 * Descripci�n :
 * Versi�n : 1.0
 *
 * @author jjvargas
 * Davivienda 2008
 */

public class ContingenciaManualGeneralObjectContext extends BaseObjectContextWeb{
    

    public ContingenciaManualGeneralObjectContext(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super(request, response);
    }


     /**
     * Lee del request el atributo <code>nombre</code> y lo retorna como un objeto String, si existe un
     * error de conversi�n retorna vacio y graba un error en el arreglo de errores
     * @param nombre Nombre del atributo
     * @return Cadena string
     */
    public String getAtributoString(String nombre) {
        String param = request.getParameter(nombre);
        return ((param != null) ? param : "");
    }

     public String getCheckStratus() {
       return getAtributoString("guardaStratus");
    }

     public void setAtributoTipoCajeroString(String nombre) {
        request.setAttribute("tipoCajero", nombre);       
    }

     public void setAtributoTipoTareaString(String nombre) {
        request.setAttribute("tipoTarea", nombre);       
    }
     
     public void setAtributoTipoDiarioString(String nombre) {
        request.setAttribute("tipoDiario", nombre);       
    }
     
    public String getAtributoTipoCajeroString(String nombre) {
        return (String)request.getAttribute("tipoCajero");       
    }

     public String getAtributoTipoTareaString(String nombre) {
        return (String)request.getAttribute("tipoTarea");       
    }
     
     public String getAtributoTipoDiarioString(String nombre) {
        return (String)request.getAttribute("tipoDiario");       
    }

}




