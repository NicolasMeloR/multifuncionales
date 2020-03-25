package com.davivienda.sara.diarioelectronico.general;

import com.davivienda.sara.base.BaseObjectContextWeb;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DiarioElectronicoGeneralObjectContext 
 * Descripci�n : 
 * Versi�n : 1.0 
 *
 * @author jjvargas
 * Davivienda 2011 
 */

public class DiarioElectronicoGeneralObjectContext extends BaseObjectContextWeb{

    public DiarioElectronicoGeneralObjectContext(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super(request, response);
    }
    
}
