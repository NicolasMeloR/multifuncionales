package com.davivienda.sara.procesos.reintegros.filtro.host.estructura;

import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.utilidades.archivoplano.CampoArchivoPlano;
import com.davivienda.utilidades.archivoplano.RegistroArchivoPlano;
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
public class HostArchivo extends ArchivoPlano {

    public String prefijoNombre;
    private Integer codigoCajero;
    private ArrayList<String> erroresConversion;
    //public static final String[] TIPOS_TRANSACCION = {"0046","0106","0055","0034"};
      public static final String[] TIPOS_TRANSACCION = {"0046"};

    public HostArchivo(String directorio, String nombreArchivo,String indOcca) {
        super(directorio, nombreArchivo);
    }

    public HostArchivo(String nombreArchivo) {
        super(nombreArchivo);
    }

    /**
     * Crea el apuntador al archivo físico y la estructura de los registros para su lectura
     * @param codigoCajero
     * @param fecha
     * @param directorio 
     * @throws java.io.FileNotFoundException
     */
    public HostArchivo(Calendar fecha, String directorio) throws FileNotFoundException {
        super();
        super.setDirectorio(directorio);
       
        erroresConversion = new ArrayList<String>();
        this.prefijoNombre = "mvtoatm01.";

        setNombreProcesoFecha(fecha);

        camposRegistro = new CampoArchivoPlano[13];
//        CampoArchivoPlano[] campoControl = new CampoArchivoPlano[1];
//        campoControl[0] = new CampoArchivoPlano("control", 0, 0, 1);
        registroData = new RegistroArchivoPlano();
//        registroControl = new RegistroArchivoPlano();
        registroData.setLongitudRegistro(100);
        registroData.setFormatoCampo(com.davivienda.utilidades.archivoplano.FormatoCampo.LONGITUD_FIJA);
        registroData.setIncluirNombreCampo(false);
        HostEstructuraRegistro[] estructura = HostEstructuraRegistro.values();
        for (int i = 0; i < estructura.length; i++) 
        {
            addCampo(estructura[i]);
        }
         this.camposRegistro[HostEstructuraRegistro.TIPO_TRANSACCION.orden].valoresFiltro = TIPOS_TRANSACCION;
        registroData.setCampos(camposRegistro);
       // registroControl.setCampos(campoControl);
        super.obtenerArchivo();
    }

    private void setNombreProcesoFecha(Calendar fecha) {

      this.prefijoNombre =this.prefijoNombre  + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AA_MM_DD);
      super.setNombreArchivo( this.prefijoNombre );
       
    }

    private void addCampo(HostEstructuraRegistro estructura) {
        camposRegistro[estructura.orden] = new CampoArchivoPlano(estructura.nombre, estructura.orden, estructura.posIni, estructura.longitud, estructura.esFiltro);
    }
}


