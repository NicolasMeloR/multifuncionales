/*
 * Banco Davivienda 2008
 * Proyecto Utilidades
 * Versión  1.0
 */
package com.davivienda.utilidades.archivo;

import com.davivienda.utilidades.SaraUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.fileupload.FileItem;
import java.util.ArrayList;

/**
 * ProcesosArchivo Descripción : Fecha : 22/01/2008 11:14:01 PM
 *
 * @author : jjvargas
 *
 */
public class ProcesosArchivo {

    public ProcesosArchivo() {
    }
    static ArrayList<String> lstArchivos = new ArrayList<String>();

    /**
     * Descomprime el archivo nombreZip en la carpeta directorioDestino
     *
     * @param directorioDestino
     * @param nombreZip
     * @return int Número de archivos descomprimidos
     * @throws java.lang.Exception
     */
    public static void unzip(String directorioDestino, String nombreZip) throws Exception {
        String nombreArchivo = "";
        int BUFFER_SIZE = 8192;
        BufferedOutputStream dest = null;
        BufferedInputStream bufferedInputStream = null;
        FileInputStream fis = null;
        ZipInputStream zis = null;
        try {
            fis = new FileInputStream(directorioDestino + File.separator + nombreZip);
            bufferedInputStream = new BufferedInputStream(fis);
            zis = new ZipInputStream(bufferedInputStream);
            int count;
            byte data[] = new byte[BUFFER_SIZE];
            ZipEntry entry;
            String archivoNombre[] = new String[2];
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    String entryName = entry.getName();
                    nombreArchivo = entry.getName();
                    java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.FINE, "Se descomprime a " + entryName);
                    if (entryName.contains("/")) {
                        archivoNombre = entryName.split("/");
                        nombreArchivo = archivoNombre[1];
                    }
                    lstArchivos.add(nombreArchivo + ";" + entry.getSize());
                    String destFN = directorioDestino + File.separator + nombreArchivo;
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(destFN);
                        dest = new BufferedOutputStream(fos, BUFFER_SIZE);
                        while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
                            dest.write(data, 0, count);
                        }
                        dest.flush();
                    } catch (Exception e1) {
                        //Next file
                    } finally {
                        if (dest != null) {
                            dest.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    }
                }
            }
        } finally {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (zis != null) {
                zis.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }

    public static ArrayList unzipArray(String directorioDestino, String nombreZip) throws Exception {
        String respuesta = "";
        try {
            lstArchivos.clear();
            lstArchivos.add("datosInicio");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
            respuesta = "Problemas para cargar el diario electrónico " + ex.getMessage();
            lstArchivos.add(respuesta);
        } finally {
            try {
                unzip(directorioDestino, nombreZip);
                int i = lstArchivos.size();
                respuesta = respuesta + " Archivos Contenidos: " + (i - 1);
                lstArchivos.set(0, respuesta);

            } catch (Exception ex) {
                java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return lstArchivos;
    }

    /**
     * Toma el archivo <code>FileItem</code> enviado en el request y lo copia a
     * la carpeta <code>dirDestino</code> del servidor WEB
     *
     * @param dirDestino
     * @param archivo
     * @return String con el resultado de la operación
     */
    public static ArrayList subirArchivoAServidorWeb(String dirDestino, InputStream inputStream, String nombreArchivo) {
        String respuesta = "";
        try {
            lstArchivos.clear();
            Path target = Paths.get(dirDestino.concat(File.separator).concat(nombreArchivo));
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
            respuesta = "Archivo enviado exitosamente";
            lstArchivos.add(respuesta);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, ex.getMessage(), ex);
            respuesta = "Problemas para cargar el diario electrónico " + ex.getMessage();
            lstArchivos.add(respuesta);
        }
        return lstArchivos;
    }

}
