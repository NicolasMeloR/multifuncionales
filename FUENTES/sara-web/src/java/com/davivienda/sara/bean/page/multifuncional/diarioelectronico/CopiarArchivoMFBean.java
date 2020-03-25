/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.multifuncional.diarioelectronico;

import com.davivienda.sara.base.ProcesosArchivoObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.archivo.ProcesosArchivo;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletException;
import org.apache.commons.fileupload.FileItem;

//import org.primefaces.model.UploadedFile;
/**
 *
 * @author calvarez
 */
@ManagedBean(name = "copiarArchivoMFBean")
@ViewScoped
public class CopiarArchivoMFBean extends BaseBean implements Serializable {

    private FileItem archivoDiarioElectMF;

    public void CopiarArchivoBean() {

    }

    public void subirDiarioElectronicoEMF() {
        System.err.println("copiarArchivoMFBean-subirDiarioElectronico");
        try {
            System.err.println("File copiarArchivoMFBean archivoDiarioElectMF: " + archivoDiarioElectMF);

            if (null != archivoDiarioElectMF) {
//                subirArchivoMultifuncional(archivoDiarioElectMF.getInputstream(),archivoDiarioElectMF.getFileName());
            } else {
//                abrirModal("SARA", "Error: El archivo " + (null == archivoDiarioElectMF ? " " : archivoDiarioElectMF.getFileName()) + " no cumple con el formato de extensión válido " + Constantes.EXTENSIONES_MULTIFUNCIONAL, null);
            }

            this.archivoDiarioElectMF = null;
        } catch (Exception e) {
            abrirModal("SARA", "Error subiendo el archivo: ", e);
            e.printStackTrace();
        }

    }

    public void subirArchivoMultifuncional(InputStream inputStream, String fileName) throws ServletException, IOException {
        if (inputStream != null) {
            ProcesosArchivoObjectContextWeb procesosArchivoObjectContext = new ProcesosArchivoObjectContextWeb(getRequestFaces(), getResponseFaces());

            ArrayList lstArchivos;

            String directorioUpload = "";

            directorioUpload = procesosArchivoObjectContext.getConfigApp().DIRECTORIO_UPLOAD;

            System.err.println("directorioUpload: " + directorioUpload);
            String respuesta = null;
//            lstArchivos = ProcesosArchivo.subirArchivoAServidorWeb(directorioUpload, inputStream,fileName);
//            respuesta = (String) lstArchivos.get(0);

            if (respuesta != null && respuesta.length() == 0) {
                respuesta = "No se ha podido procesar la solicitud.";
            }

            System.err.println("Respuesta: " + respuesta);
            abrirModal("SARA", respuesta, null);

        } else {
            abrirModal("SARA", "Seleccione un archivo válido.", null);
        }
    }

    public FileItem getArchivoDiarioElectMF() {
        return archivoDiarioElectMF;
    }

    public void setArchivoDiarioElectMF(FileItem archivoDiarioElectMF) {
        this.archivoDiarioElectMF = archivoDiarioElectMF;
    }
}
