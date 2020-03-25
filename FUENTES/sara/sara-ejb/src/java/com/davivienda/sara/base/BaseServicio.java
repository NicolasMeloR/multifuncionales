// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.base;

import com.davivienda.utilidades.conversion.JSon;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.config.SaraConfig;

public class BaseServicio
{
    protected static SaraConfig configApp;
    protected CodigoError codigoError;
    protected EntityManager em;
    
    public BaseServicio() {
        try {
            BaseServicio.configApp = SaraConfig.obtenerInstancia();
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(BaseServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BaseServicio(final EntityManager em) {
        this();
        this.em = em;
    }
    
    public EntityManager getEm() {
        return this.em;
    }
    
    public void setEm(final EntityManager em) {
        this.em = em;
    }
    
    public CodigoError getCodigoError() {
        return this.codigoError;
    }
    
    public void setCodigoError(final CodigoError codigoError) {
        this.setCodigoError(codigoError, codigoError.toString(), null);
    }
    
    public void setCodigoError(final CodigoError codigoError, final String mensaje) {
        this.setCodigoError(codigoError, mensaje, null);
    }
    
    public void setCodigoError(final CodigoError codigoError, final String mensaje, final Exception ex) {
        if (codigoError != CodigoError.SIN_ERROR) {
            if (ex != null) {
                Logger.getLogger("globalApp").log(Level.SEVERE, mensaje, ex);
            }
            else {
                Logger.getLogger("globalApp").severe(mensaje);
            }
        }
        this.codigoError = codigoError;
    }
    
    public String getErrorProcesoJSon() {
        String resp = "";
        try {
            resp = JSon.getJSonRespuesta(this.codigoError.getCodigo(), this.codigoError.toString());
        }
        catch (Exception ex) {}
        return resp;
    }
    
    public SaraConfig getConfigApp() {
        return BaseServicio.configApp;
    }
}
