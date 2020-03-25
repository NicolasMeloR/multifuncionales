package com.davivienda.sara.base;

import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.constantes.CodigoError;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 * BaseServicio - 24/05/2008
 * Descripción : Clase base para todos los servicios
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class BaseServicio {

    /**
     * Configuración de la aplicación
     */
    protected static SaraConfig configApp;
    /**
     * Código del error con el que finaliza un proceso
     */
    protected CodigoError codigoError;
    /**
     * Manejador de entitys
     */
    protected EntityManager em;

    /**
     * Crea una nueva instancia de BaseServicio
     */
    public BaseServicio() {
        try {
            configApp = SaraConfig.obtenerInstancia();
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BaseServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea una nueva instancia de BaseServicio configurando el EntityManager
     * @param em
     */
    public BaseServicio(EntityManager em) {
        this();
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public CodigoError getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(CodigoError codigoError) {
        setCodigoError(codigoError, codigoError.toString(), null);
    }

    public void setCodigoError(CodigoError codigoError, String mensaje) {
        setCodigoError(codigoError, mensaje, null);
    }

    /**
     * Asigna el código de error de finalización de un proceso y escribe un log con el mensaje
     * @param codigoError
     * @param mensaje
     * @param ex
     */
    public void setCodigoError(CodigoError codigoError, String mensaje, Exception ex) {
        if (codigoError != CodigoError.SIN_ERROR) {
            if (ex != null) {
                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, mensaje, ex);
            } else {
                java.util.logging.Logger.getLogger("globalApp").severe(mensaje);
            }
        }
        this.codigoError = codigoError;
    }

    public String getErrorProcesoJSon() {
        String resp = "";
        try {
            resp = com.davivienda.utilidades.conversion.JSon.getJSonRespuesta(codigoError.getCodigo(), codigoError.toString());
        } catch (Exception ex) {
        }
        return resp;
    }

    public SaraConfig getConfigApp() {
        return configApp;
    }
}
