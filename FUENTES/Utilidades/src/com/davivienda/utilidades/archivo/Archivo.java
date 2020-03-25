package com.davivienda.utilidades.archivo;

import com.davivienda.utilidades.conversion.Cadena;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Archivo - 10/08/2008 Descripción : Clase con utilidades generales de archivos
 * Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class Archivo {

    protected File archivoFile;
    /**
     * Nombre del archivo
     */
    private String nombreArchivo;
    /**
     * Ubicación del archivo
     */
    private String directorio;
    protected BufferedReader br;
    protected InputStreamReader isr;
    protected FileInputStream fis;
    protected FileOutputStream fos;

    /**
     * Tabla de caracteres a usar para la conversión de byte a Char
     */
    public static String CHAR_SET = "ISO-8859-1";
    /**
     * Tamaño por defecto del bufer de lectura de una archivo
     */
    public static int TAMANO_BUFFER_LECTURA = 1024;

    /**
     * Crea un objeto ArchivoPlano con el nombre y directorio del archivo
     * asociados
     *
     * @param directorio
     * @param nombreArchivo
     */
    public Archivo(String directorio, String nombreArchivo) {
        setNombreArchivo(nombreArchivo);
        setDirectorio(directorio);
    }

    /**
     * Retorna el nombre del archivo, si ya se tiene acceso al archivo lo
     * retorna del objeto File, de lo contrario del atributo nombreArchivo de
     * este objeto
     *
     * @return String
     */
    public String getNombreArchivo() {
        return (this.archivoFile != null && sePuedeLeer()) ? this.archivoFile.getName() : this.nombreArchivo;
    }

    /**
     * Retorna el directorio del archivo, si ya se tiene acceso al archivo lo
     * retorna del objeto File, de lo contrario del atributo directorio de este
     * objeto
     *
     * @return String
     */
    public String getDirectorio() {
        return (this.archivoFile != null && sePuedeLeer()) ? this.archivoFile.getPath() : this.directorio;
    }

    /**
     * Asigna el nombre del archivo
     *
     * @param nombreArchivo
     */
    public void setNombreArchivo(String nombreArchivo) {
        if (Cadena.contieneAlgo(nombreArchivo)) {
            this.nombreArchivo = nombreArchivo.trim();
        }
    }

    /**
     * Asigna el directorio de ubicación del archivo
     *
     * @param directorio
     */
    public void setDirectorio(String directorio) {
        if (Cadena.contieneAlgo(directorio)) {
            this.directorio = directorio.trim();
        } else {
            this.directorio = "./";
        }
    }

    /**
     * Accede al archivo en el FileSystem
     *
     * @throws java.lang.IllegalArgumentException
     */
    public void obtenerArchivo() throws IllegalArgumentException {
        String nombreCompleto;
        if (!Cadena.contieneAlgo(this.nombreArchivo)) {
            throw new IllegalArgumentException("No se ha asignado el nombre del archivo");
        }
        nombreCompleto = (!Cadena.contieneAlgo(directorio)) ? this.nombreArchivo : this.directorio + File.separator + this.nombreArchivo;
        try {
            this.archivoFile = new File(nombreCompleto);
        } catch (Exception ex) {
            throw new IllegalArgumentException("No se encuentra el archivo " + nombreCompleto + " error : " + ex.getMessage());
        }
    }

    /**
     * Valida si se tiene acceso al archivo
     *
     * @return boolean
     */
    public boolean sePuedeLeer() {
        return this.archivoFile.canRead();
    }

    /**
     * Retona la longitud del archivo en bytes
     *
     * @return Long en bytes
     * @throws java.lang.IllegalArgumentException
     */
    public Long getTamArchivo() throws IllegalArgumentException {
        if (this.archivoFile == null || !sePuedeLeer()) {
            throw new IllegalArgumentException("No se ha asignado el nombre del archivo o no se tiene acceso a él ");
        }
        return this.archivoFile.length();
    }

    /**
     * Retorna la longitud del archivo dependiendo de tipoLongitud
     *
     * @param tipoLongitud B:Bytes K:KiloBytes M: MegaBytes G: GigaBytes
     * @return Integer longitud
     * @throws java.lang.IllegalArgumentException
     */
    public Integer getTamArchivo(Character tipoLongitud) throws IllegalArgumentException {
        Long tamano = getTamArchivo();
        int tmpInt = 0;
        switch (tipoLongitud) {
            case 'B':
                tmpInt = tamano.intValue();
                break;
            case 'K':
                tmpInt = tamano.intValue() / 1024;
                break;
            case 'M':
                tmpInt = tamano.intValue() / 1024 / 1024;
                break;
            case 'G':
                tmpInt = tamano.intValue() / 1024 / 1024 / 1024;
                break;
            default:
                tmpInt = tamano.intValue();
                break;
        }
        return tmpInt;
    }

    /**
     * Obtiene un canal de lectura en bytes del archivo
     *
     * @return FileInputStream
     * @throws java.io.FileNotFoundException
     * @throws java.lang.IllegalArgumentException
     */
    public FileInputStream getFileInputStream() throws FileNotFoundException, IllegalArgumentException {
        if (this.archivoFile == null || !sePuedeLeer()) {
            throw new IllegalArgumentException("No se ha asignado el nombre del archivo o no se tiene acceso a él ");
        }
        if (this.fis == null) {
            this.fis = new FileInputStream(this.archivoFile);
        }
        return this.fis;
    }

    /**
     * Obtiene un canal de escritura en bytes al archivo
     *
     * @return FileOutputStream
     * @throws java.io.FileNotFoundException
     * @throws java.lang.IllegalArgumentException
     */
    public FileOutputStream getFileOutputStream() throws FileNotFoundException, IllegalArgumentException {
        if (this.archivoFile == null) {
            throw new IllegalArgumentException("No se ha asignado el nombre del archivo o no se tiene acceso a él ");
        }
        if (this.fos == null) {
            this.fos = new FileOutputStream(this.archivoFile);
        }
        return this.fos;
    }

    public void setFos(FileOutputStream fos) throws IllegalArgumentException {
        if (fos == null) {
            throw new IllegalArgumentException("No se puede asignar un objeto nulo ");
        } else {
            // cierro el actual
            try {
                fos.close();
            } catch (IOException ex) {
                // no importa
            }
        }
        this.fos = fos;
    }

    /**
     * Obtiene a partir del canal de lectura los bytes leídos del archivo y los
     * convierte en caracteres, utiliza la codificación implementada en <code>charSet<code>
     *
     * @param charSet Codificación utilizada para convertir de byte a Char
     * @return InputStreamReader
     * @throws java.io.FileNotFoundException
     * @throws java.lang.IllegalArgumentException
     */
    public InputStreamReader getInputStreamReader(String charSet) throws FileNotFoundException, IllegalArgumentException {
        if (this.archivoFile == null || !sePuedeLeer()) {
            throw new IllegalArgumentException("No se ha asignado el nombre del archivo o no se tiene acceso a él ");
        }
        if (this.fis == null) {
            getFileInputStream();
        }
        if (this.isr == null) {
            try {
                this.isr = new java.io.InputStreamReader(this.fis, charSet);
            } catch (UnsupportedEncodingException ex) {
                throw new IllegalArgumentException("No se puede decodificar con el CharSet " + charSet + " Error: " + ex.getMessage());
            }
        }
        return this.isr;
    }

    /**
     * Obtiene a partir del canal de lectura los bytes leídos del archivo y los
     * convierte en caracteres, utiliza la codificación implementada en <code>CHAR_SET<code>
     *
     * @see CHAR_SET
     * @return InputStreamReader con codificación ISO-8859-1
     * @throws java.io.FileNotFoundException
     * @throws java.lang.IllegalArgumentException
     */
    public InputStreamReader getInputStreamReader() throws FileNotFoundException, IllegalArgumentException {
        return getInputStreamReader(CHAR_SET);
    }

    /**
     * Lee del buffer de lectura creado el número <code>tamanoBuffer</code> de
     * caracteres con conversion <code>charSet</code> Si ya existe cierra todos
     * los accesos al archivo y los abre nuevamente
     *
     * @param tamanoBuffer
     * @param charSet Codificación utilizada para convertir de byte a Char
     * @return BufferedReader
     * @throws java.lang.IllegalArgumentException
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public BufferedReader getBufferedReader(int tamanoBuffer, String charSet) throws IllegalArgumentException, FileNotFoundException, IOException {
        FileReader s;
        if (this.archivoFile == null || !sePuedeLeer()) {
            throw new IllegalArgumentException("No se ha asignado el nombre del archivo o no se tiene acceso a él ");
        }
        if (this.br != null) {
            cerrarArchivo();
        }
        if (this.fis == null) {
            getFileInputStream();
        }
        if (this.isr == null) {
            getInputStreamReader(charSet);
        }
        if (this.br != null) {
            this.br.close();
        }
        this.br = new BufferedReader(this.isr, tamanoBuffer);
        return this.br;
    }

    /**
     * Lee del buffer de lectura creado el número <code>tamanoBuffer</code> de
     * caracteres con conversion <code>CHAR_SET</code> Si ya existe cierra todos
     * los accesos al archivo y los abre nuevamente
     *
     * @param tamanoBuffer
     * @see CHAR_SET
     * @return BufferedReader
     * @throws java.lang.IllegalArgumentException
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public BufferedReader getBufferedReader(int tamanoBuffer) throws IllegalArgumentException, FileNotFoundException, IOException {
        return getBufferedReader(tamanoBuffer, CHAR_SET);
    }

    /**
     * Lee del buffer de lectura creado el número
     * <code>TAMANO_BUFFER_LECTURA</code> de caracteres con conversion
     * <code>CHAR_SET</code> Si ya existe cierra todos los accesos al archivo y
     * los abre nuevamente
     *
     * @see TAMANO_BUFFER_LECTURA
     * @see CHAR_SET
     * @return BufferedReader de un tamaño de 1024
     * @throws java.lang.IllegalArgumentException
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public BufferedReader getBufferedReader() throws IllegalArgumentException, FileNotFoundException, IOException {
        return getBufferedReader(TAMANO_BUFFER_LECTURA, CHAR_SET);
    }

    /**
     * Cierra el canal, conversor y buffer del archivo
     */
    public void cerrarArchivo() {
        if (this.br != null) {
            try {
                this.br.close();
                this.br = null;
            } catch (IOException ex) {
                // no importa
            }
        }
        if (this.isr != null) {
            try {
                this.isr.close();
                this.isr = null;
            } catch (IOException ex) {
                // No importa
            }
        }
        if (this.fis != null) {
            try {
                this.fis.close();
                this.fis = null;
            } catch (IOException ex) {
                // No importa
            }
        }

        if (this.fos != null) {
            try {
                this.fos.close();
                this.fos = null;
            } catch (IOException ex) {
                // No importa
            }
        }
    }

    /**
     * Retona la cantidad de registros de un archivo
     *
     * @return long
     */
    public int cuentaRegistros() throws IOException {
        String nombreCompleto = (!Cadena.contieneAlgo(directorio)) ? this.nombreArchivo : this.directorio + File.separator + this.nombreArchivo;
        FileReader fileReader = null;
        BufferedReader in = null;
        int n = 0;
        try {
            fileReader = new FileReader(nombreCompleto);
            in = new BufferedReader(fileReader);
            while (in.readLine() != null) {
                n++;
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
        return n;
    }

}
