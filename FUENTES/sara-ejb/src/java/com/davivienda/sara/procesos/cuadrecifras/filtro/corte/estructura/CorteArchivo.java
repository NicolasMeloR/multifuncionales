/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.procesos.cuadrecifras.filtro.corte.estructura;


import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.utilidades.archivoplano.CampoArchivoPlano;
import com.davivienda.utilidades.archivoplano.RegistroArchivoPlano;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;



/**
 * CorteArchivo
 * Descripción : Contiene la estructura del archivo de corte manejada en el archivo plano cfamo001.
 * Fecha       : 31/01/2008 11:50:55 AM
 * @author     : jjvargas
 **/
public class CorteArchivo extends ArchivoPlano {

    public String prefijoNombre;
    private ArrayList<String> erroresConversion;

    public CorteArchivo(String directorio, String nombreArchivo) {
        super(directorio, nombreArchivo);
    }

    public CorteArchivo(String nombreArchivo) {
        super(nombreArchivo);
    }
    

    /**
     * Crea el apuntador al archivo físico y la estructura de los registros para su lectura
     * @param fecha
     * @param directorio 
     * @throws java.io.FileNotFoundException
     */
    public CorteArchivo(Calendar fecha, String directorio) throws FileNotFoundException {
       
        super();
        super.setDirectorio(directorio);
        this.prefijoNombre = "cfamo001";
        erroresConversion = new ArrayList<String>();

        setNombreProcesoFecha(fecha);

        //camposRegistro = new CampoArchivoPlano[32];
        
        /*5 Gaveta*/
        camposRegistro = new CampoArchivoPlano[44];
//        CampoArchivoPlano[] campoControl = new CampoArchivoPlano[1];
//        campoControl[0] = new CampoArchivoPlano("control", 0, 0, 1);
        registroData = new RegistroArchivoPlano();
       // registroControl = new RegistroArchivoPlano();
        //registroData.setLongitudRegistro(256);
        
        /*5 Gaveta*/
        registroData.setLongitudRegistro(304);
        registroData.setFormatoCampo(com.davivienda.utilidades.archivoplano.FormatoCampo.LONGITUD_FIJA);
        registroData.setIncluirNombreCampo(false);
        CorteEstructuraRegistro[] estructura = CorteEstructuraRegistro.values();
        for (int i = 0; i < estructura.length; i++) {
            addCampo(estructura[i]);
        }
        registroData.setCampos(camposRegistro);
       // registroControl.setCampos(campoControl);
        super.obtenerArchivo();
      
    }



    public void addCampo(CorteEstructuraRegistro estructura) {
        camposRegistro[estructura.orden] = new CampoArchivoPlano(estructura.nombre, estructura.orden, estructura.posIni, estructura.longitud, estructura.esFiltro);
    }

  
    public void setNombreProcesoFecha(Calendar fecha) {
      this.prefijoNombre =this.prefijoNombre + '.' + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AA_MM_DD);
      super.setNombreArchivo( this.prefijoNombre );
      //directorio + java.io.File.separator + this.prefijoNombre   + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AA_MM_DD);
    }

   

   
}
