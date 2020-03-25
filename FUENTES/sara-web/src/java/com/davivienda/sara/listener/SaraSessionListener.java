/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.listener;

import com.davivienda.utilidades.Constantes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author ocaldero
 */
public class SaraSessionListener implements HttpSessionListener {

    private static Logger logger = Logger.getLogger(Constantes.LOGGER_ACCESO);

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.log(Level.FINEST, "Sesión creada "+  event.getSession().getId());

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        logger.log(Level.FINEST, "Sesión destruida " + event.getSession().getId());
    }
}
