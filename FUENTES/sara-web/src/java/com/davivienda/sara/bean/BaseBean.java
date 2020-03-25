/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
//import org.primefaces.model.UploadedFile;

/**
 *
 * @author jediazs
 */
public class BaseBean implements Serializable {

    public ComponenteAjaxObjectContextWeb cargarComponenteAjaxObjectContext() {
        ComponenteAjaxObjectContextWeb object = obtenerBienInicial().getObjectContext();
        return object;
    }

    public HttpServletRequest getRequestFaces() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request;
    }

    public HttpServletResponse getResponseFaces() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        return response;
    }

    public InitBean obtenerBienInicial() {
        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        InitBean initBean = (InitBean) viewMap.get("initBean");
        return initBean;
    }

    public void abrirModal(String tituloModal, String descripcionModal, Exception ex) {
        obtenerBienInicial().setTituloModal(tituloModal);
        String mensaje = descripcionModal;
        if (ex != null) {
            mensaje += "\nError : " + ex.getClass().getName();
            mensaje += "\n" + ex.getMessage();
            mensaje += "\n" + ex.getLocalizedMessage();
        }
        obtenerBienInicial().setDescripcionModal(mensaje);
        obtenerBienInicial().setMostrarModalMsg(true);
    }

    public boolean validarExtension(String extension, FileItem file) {
        try {
            Logger logger = cargarComponenteAjaxObjectContext().getConfigApp().loggerApp;
            if (file == null) {
                return false;
            }
            logger.log(Level.INFO, "Extension: {0} Archivo: getContentType-> {1} getName-> {2}", new Object[]{extension, file.getContentType(), file.getName()});
            String nombreArchivo = file.getName();
            String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf("."), nombreArchivo.length());
            return extensionArchivo.equalsIgnoreCase(extension);
        } catch (Exception ex) {
            return false;
        }
    }

}
