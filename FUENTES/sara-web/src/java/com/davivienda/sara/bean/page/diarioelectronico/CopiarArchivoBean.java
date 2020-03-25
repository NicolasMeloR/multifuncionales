/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.diarioelectronico;

import com.davivienda.sara.base.ProcesosArchivoObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.SaraUtil;
import com.davivienda.utilidades.archivo.ProcesosArchivo;
import com.davivienda.utilidades.conversion.JSon;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletException;
import org.apache.commons.fileupload.FileItem;
//import org.primefaces.model.UploadedFile;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "copiarArchivoBean")
@ViewScoped
public class CopiarArchivoBean extends BaseBean implements Serializable {

    @EJB
    AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;
    private Map<String, String> mapUrl;
    private FileItem archivoDiarioE;
    private FileItem archivoCicloD;
    private Logger loggerApp;

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void CopiarArchivoBean() {
        loggerApp = cargarComponenteAjaxObjectContext().getConfigApp().loggerApp;
    }

    public void subirDiarioElectronico() {
        loggerApp.info("CopiarArchivoBean-subirDiarioElectronico");
        try {

            if (validarExtension(Constantes.EXTENSIONES_DIARIO_ELECTRONICO, archivoDiarioE)) {
                subirArchivo(archivoDiarioE);
            } else {
                abrirModal("SARA", "Error: El archivo " + (null == archivoDiarioE ? " " : archivoDiarioE.getName()) + " no cumple con el formato de extensión válido " + Constantes.EXTENSIONES_DIARIO_ELECTRONICO, null);
            }
            this.archivoDiarioE = null;
        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    public void subirCicloDiario() {
        loggerApp.info("CopiarArchivoBean-subirCicloDiario");
        try {
            loggerApp.info("File copiarArchivoBean subirCicloDiario: " + archivoCicloD);
            if (validarExtension(Constantes.EXTENSIONES_CICLO_DIARIO, archivoCicloD)) {
                subirArchivo(archivoCicloD);
            } else {
                abrirModal("SARA", "Error: El archivo " + (null == archivoCicloD ? " " : archivoCicloD.getName()) + " no cumple con el formato de extensión válido " + Constantes.EXTENSIONES_CICLO_DIARIO, null);
            }
            this.archivoCicloD = null;
        } catch (Exception ex) {
            loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
    }

    public void subirArchivo(FileItem file) throws ServletException, IOException {
        if (file != null) {
            ProcesosArchivoObjectContextWeb procesosArchivoObjectContext = new ProcesosArchivoObjectContextWeb(getRequestFaces(), getResponseFaces());

            ArrayList lstArchivos;

            String directorioUpload = "";

            directorioUpload = procesosArchivoObjectContext.getConfigApp().DIRECTORIO_UPLOAD;

            loggerApp.info("directorioUpload: " + directorioUpload);
            String respuesta = null;
//            lstArchivos = ProcesosArchivo.subirArchivoAServidorWeb(directorioUpload, file.getInputStream(),file.getName());
//            respuesta = (String) lstArchivos.get(0);

            if (respuesta != null && respuesta.length() == 0) {
                respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), "No se ha podido procesar la solicitud ");
            }
            respuesta = SaraUtil.stripXSS(respuesta, Constantes.REGEX_ACEPTAR_DEFAULT);
            loggerApp.info("Respuesta: " + respuesta);
            abrirModal("SARA", respuesta, null);

        }
    }

    public FileItem getArchivoDiarioE() {
        return archivoDiarioE;
    }

    public void setArchivoDiarioE(FileItem archivoDiarioE) {
        this.archivoDiarioE = archivoDiarioE;
    }

    public FileItem getArchivoCicloD() {
        return archivoCicloD;
    }

    public void setArchivoCicloD(FileItem archivoCicloD) {
        this.archivoCicloD = archivoCicloD;
    }
}
