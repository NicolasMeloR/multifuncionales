/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.base;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jediazs
 */
public class ProcesosArchivoObjectContextWeb extends BaseObjectContextWeb{

    public ProcesosArchivoObjectContextWeb(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super(request, response);
    }
}
