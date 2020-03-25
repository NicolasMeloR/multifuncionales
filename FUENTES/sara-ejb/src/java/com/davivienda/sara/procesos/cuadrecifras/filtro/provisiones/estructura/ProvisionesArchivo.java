/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.estructura;


import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.utilidades.archivoplano.CampoArchivoPlano;
import com.davivienda.utilidades.archivoplano.RegistroArchivoPlano;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;



/**
 * ProvisionesArchivo
 * Descripción : Contiene la estructura del archivo de corte manejada en el archivo plano cfamo001.
 * Fecha       : 31/01/2008 11:50:55 AM
 * @author     : jjvargas
 **/
public class ProvisionesArchivo extends ArchivoPlano {

    public String prefijoNombre;
    private ArrayList<String> erroresConversion;
    public static final String[] TIPOS_MOVIMIENTO_PROVISION = {"0098", "0099"};
    //public static final String[] MOTIVOS_PROVISION = {"0016", "0035", "0075", "0112"};
    public static final String[] MOTIVOS_PROVISION = {"0016", "0035", "0075", "0112","0070"};


    public ProvisionesArchivo(String directorio, String nombreArchivo) {
        super(directorio, nombreArchivo);
    }

    public ProvisionesArchivo(String nombreArchivo) {
        super(nombreArchivo);
    }
    

    /**
     * Crea el apuntador al archivo físico y la estructura de los registros para su lectura
     * @param fecha
     * @param directorio 
     * @throws java.io.FileNotFoundException
     */
    public ProvisionesArchivo(Calendar fecha, String directorio) throws FileNotFoundException {
       
        super();
        super.setDirectorio(directorio);
        this.prefijoNombre = "otbmo001";
        erroresConversion = new ArrayList<String>();

        setNombreProcesoFecha(fecha);

        camposRegistro = new CampoArchivoPlano[20];
       
        registroData = new RegistroArchivoPlano();
       
        registroData.setLongitudRegistro(400);
        registroData.setFormatoCampo(com.davivienda.utilidades.archivoplano.FormatoCampo.LONGITUD_FIJA);
        registroData.setIncluirNombreCampo(false);
        ProvisionesEstructuraRegistro[] estructura = ProvisionesEstructuraRegistro.values();
        for (int i = 0; i < estructura.length; i++) {
            addCampo(estructura[i]);
        }
        this.camposRegistro[ProvisionesEstructuraRegistro.TIPO_MOVIMIENTO.orden].valoresFiltro = TIPOS_MOVIMIENTO_PROVISION;
        this.camposRegistro[ProvisionesEstructuraRegistro.MOTIVO_MOVIMIENTO.orden].valoresFiltro = MOTIVOS_PROVISION;
        registroData.setCampos(camposRegistro);
     
        super.obtenerArchivo();
      
    }

    public void addCampo(ProvisionesEstructuraRegistro estructura) {
        camposRegistro[estructura.orden] = new CampoArchivoPlano(estructura.nombre, estructura.orden, estructura.posIni, estructura.longitud, estructura.esFiltro);
    }

  
    public void setNombreProcesoFecha(Calendar fecha) {
      this.prefijoNombre =this.prefijoNombre  + '.' + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AA_MM_DD);
      super.setNombreArchivo( this.prefijoNombre );
    }

   

   
}
