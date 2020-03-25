package com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura;

import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.utilidades.archivoplano.CampoArchivoPlano;
import com.davivienda.utilidades.archivoplano.RegistroArchivoPlano;
import com.davivienda.utilidades.edc.Edc;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * EdcArchivo - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class EdcArchivo extends ArchivoPlano {

    private Integer codigoCajero;
    private ArrayList<String> erroresConversion;

    public EdcArchivo(String directorio, String nombreArchivo) {
        super(directorio, nombreArchivo);
    }

    public EdcArchivo(String nombreArchivo) {
        super(nombreArchivo);
    }

    /**
     * Crea el apuntador al archivo físico y la estructura de los registros para su lectura
     * @param codigoCajero
     * @param fecha
     * @param directorio 
     * @throws java.io.FileNotFoundException
     */
    public EdcArchivo(Integer codigoCajero, Calendar fecha, String directorio,String nombreArchivoRecibido) throws FileNotFoundException {
        super();
        super.setDirectorio(directorio);
        this.codigoCajero = codigoCajero;
        erroresConversion = new ArrayList<String>();

        setNombreProcesoFecha(fecha,nombreArchivoRecibido);

        camposRegistro = new CampoArchivoPlano[6];
        CampoArchivoPlano[] campoControl = new CampoArchivoPlano[1];
        campoControl[0] = new CampoArchivoPlano("control", 0, 0, 1);
        registroData = new RegistroArchivoPlano();
        registroControl = new RegistroArchivoPlano();
        registroData.setLongitudRegistro(70);
        registroData.setFormatoCampo(com.davivienda.utilidades.archivoplano.FormatoCampo.LONGITUD_FIJA);
        registroData.setIncluirNombreCampo(false);
        EdcEstructuraRegistro[] estructura = EdcEstructuraRegistro.values();
        for (int i = 0; i < estructura.length; i++) {
            addCampo(estructura[i]);
        }
        registroData.setCampos(camposRegistro);
        registroControl.setCampos(campoControl);
        super.obtenerArchivo();
    }

    private void setNombreProcesoFecha(Calendar fecha,String nombreArchivoRecibido) {
//        if(nombreArchivoRecibido.equals(""))
//        {
//        super.setNombreArchivo(Edc.obtenerNombreArchivo(codigoCajero, fecha));
//        }
//        else
//        {
//         super.setNombreArchivo(nombreArchivoRecibido);
//        }
        
        //
          super.setNombreArchivo(nombreArchivoRecibido);
       
    }

    private void addCampo(EdcEstructuraRegistro estructura) {
        camposRegistro[estructura.orden] = new CampoArchivoPlano(estructura.nombre, estructura.orden, estructura.posIni, estructura.longitud, estructura.esFiltro);
    }
}
