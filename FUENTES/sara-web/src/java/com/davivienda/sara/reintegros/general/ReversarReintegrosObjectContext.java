package com.davivienda.sara.reintegros.general;

import com.davivienda.sara.constantes.TipoCuenta;
import com.davivienda.sara.constantes.prueba;

import com.davivienda.sara.base.BaseObjectContextWeb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DiarioElectronicoGeneralObjectContext - 27/08/2008 Descripción : Versión :
 * 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReversarReintegrosObjectContext extends BaseObjectContextWeb {

    public ReversarReintegrosObjectContext(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super(request, response);
    }

}
