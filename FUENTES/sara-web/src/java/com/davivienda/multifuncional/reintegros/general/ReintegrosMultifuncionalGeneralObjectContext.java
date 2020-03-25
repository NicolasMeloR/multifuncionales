package com.davivienda.multifuncional.reintegros.general;


import com.davivienda.sara.base.BaseObjectContextWeb;
import  com.davivienda.multifuncional.constantes.TipoCuentaMultifuncional;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ReintegrosMultifuncionalGeneralObjectContext
 * Descripción : 
 * Versión : 1.0 
 *
 * @author P-MDRUIZ
 * Davivienda 2011 
 */
public class ReintegrosMultifuncionalGeneralObjectContext extends BaseObjectContextWeb {

    public ReintegrosMultifuncionalGeneralObjectContext(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super(request, response);
    }

}
