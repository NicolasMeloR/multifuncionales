package com.davivienda.sara.reintegros.general;


import com.davivienda.sara.constantes.TipoCuenta;
import com.davivienda.sara.constantes.prueba;

import com.davivienda.sara.base.BaseObjectContextWeb;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DiarioElectronicoGeneralObjectContext - 27/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */

public class ReintegrosNotasObjectContext extends BaseObjectContextWeb{

    public ReintegrosNotasObjectContext(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super(request, response);
    }
    

    
    public TipoCuenta getTipoCuenta() throws ServletException {
       
       TipoCuenta tipoCuenta = null;
       tipoCuenta = TipoCuenta.getTipoCuenta(super.getAtributoString("tipoCuenta"));
       if (tipoCuenta == null) 
         {
                throw new ServletException("tipo cuenta No válido");
        }
       return tipoCuenta;
    }
        
        //OJO PENDIENTE
     public prueba getPRUEBA() throws ServletException {
       
       prueba pruebar = null;
       pruebar = prueba.getPrueba(super.getAtributoString("tipoprueba"));
       if (pruebar == null) 
         {
                throw new ServletException(" PRUEBA No válido");
        }
       return pruebar;
    }


}
