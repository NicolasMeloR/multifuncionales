/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.estructura;


import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.utilidades.archivoplano.CampoArchivoPlano;
import com.davivienda.utilidades.archivoplano.RegistroArchivoPlano;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;



/**
 * TotalesEstacionArchivo
 * Descripción : Contiene la estructura del archivo de corte manejada en el archivo plano geato002.
 * Fecha       : 31/01/2008 11:50:55 AM
 * @author     : jjvargas
 **/
public class TotalesEstacionArchivo extends ArchivoPlano {

    public String prefijoNombre;
    private ArrayList<String> erroresConversion;
  //  public static final String[] CODIGOS_TOTAL = {"000036", "000039", "000040", "000050", "000071", "000078", "000094", "000206", "000084", "000098"};
    public static final String[] CODIGOS_TOTAL = {"000003","000036", "000098"};

    public static final String[] CANAL = {"1"};

    public TotalesEstacionArchivo(String directorio, String nombreArchivo) {
        super(directorio, nombreArchivo);
    }

    public TotalesEstacionArchivo(String nombreArchivo) {
        super(nombreArchivo);
    }
    

    /**
     * Crea el apuntador al archivo físico y la estructura de los registros para su lectura
     * @param fecha
     * @param directorio 
     * @throws java.io.FileNotFoundException
     */
    public TotalesEstacionArchivo(Calendar fecha, String directorio) throws FileNotFoundException {
       
        super();
        super.setDirectorio(directorio);
        this.prefijoNombre = "geato002";
        erroresConversion = new ArrayList<String>();

        setNombreProcesoFecha(fecha);

        camposRegistro = new CampoArchivoPlano[5];
        registroData = new RegistroArchivoPlano();
        registroData.setLongitudRegistro(70);
        registroData.setFormatoCampo(com.davivienda.utilidades.archivoplano.FormatoCampo.LONGITUD_FIJA);
        registroData.setIncluirNombreCampo(false);
        TotalesEstacionEstructuraRegistro[] estructura = TotalesEstacionEstructuraRegistro.values();
        for (int i = 0; i < estructura.length; i++) {
            addCampo(estructura[i]);
        }
        this.camposRegistro[TotalesEstacionEstructuraRegistro.TOTAL.orden].valoresFiltro = CODIGOS_TOTAL;
        this.camposRegistro[TotalesEstacionEstructuraRegistro.CANAL.orden].valoresFiltro = CANAL;
        registroData.setCampos(camposRegistro);
        super.obtenerArchivo();
      
    }
    
 
    public void addCampo(TotalesEstacionEstructuraRegistro estructura) {
        camposRegistro[estructura.orden] = new CampoArchivoPlano(estructura.nombre, estructura.orden, estructura.posIni, estructura.longitud, estructura.esFiltro);
    }

  
    public void setNombreProcesoFecha(Calendar fecha) {
        // El proceso nomal no necesita extención con fecha
       this.prefijoNombre =this.prefijoNombre + '.' +  com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AA_MM_DD);
       super.setNombreArchivo( this.prefijoNombre );
                //directorio + java.io.File.separator + super.prefijoNombre ;
                // + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AA_MM_DD);
    }

   

   
}
