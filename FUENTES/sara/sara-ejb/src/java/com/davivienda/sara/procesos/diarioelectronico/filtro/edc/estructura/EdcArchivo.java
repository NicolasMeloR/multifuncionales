// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura;

import java.io.FileNotFoundException;
import com.davivienda.utilidades.archivoplano.FormatoCampo;
import com.davivienda.utilidades.archivoplano.RegistroArchivoPlano;
import com.davivienda.utilidades.archivoplano.CampoArchivoPlano;
import java.util.Calendar;
import java.util.ArrayList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;

public class EdcArchivo extends ArchivoPlano
{
    private Integer codigoCajero;
    private ArrayList<String> erroresConversion;
    
    public EdcArchivo(final String directorio, final String nombreArchivo) {
        super(directorio, nombreArchivo);
    }
    
    public EdcArchivo(final String nombreArchivo) {
        super(nombreArchivo);
    }
    
    public EdcArchivo(final Integer codigoCajero, final Calendar fecha, final String directorio, final String nombreArchivoRecibido) throws FileNotFoundException {
        super.setDirectorio(directorio);
        this.codigoCajero = codigoCajero;
        this.erroresConversion = new ArrayList<String>();
        this.setNombreProcesoFecha(fecha, nombreArchivoRecibido);
        this.camposRegistro = new CampoArchivoPlano[6];
        final CampoArchivoPlano[] campoControl = { new CampoArchivoPlano("control", 0, 0, 1) };
        this.registroData = new RegistroArchivoPlano();
        this.registroControl = new RegistroArchivoPlano();
        this.registroData.setLongitudRegistro(70);
        this.registroData.setFormatoCampo(FormatoCampo.LONGITUD_FIJA);
        this.registroData.setIncluirNombreCampo(false);
        final EdcEstructuraRegistro[] estructura = EdcEstructuraRegistro.values();
        for (int i = 0; i < estructura.length; ++i) {
            this.addCampo(estructura[i]);
        }
        this.registroData.setCampos(this.camposRegistro);
        this.registroControl.setCampos(campoControl);
        super.obtenerArchivo();
    }
    
    private void setNombreProcesoFecha(final Calendar fecha, final String nombreArchivoRecibido) {
        super.setNombreArchivo(nombreArchivoRecibido);
    }
    
    private void addCampo(final EdcEstructuraRegistro estructura) {
        this.camposRegistro[estructura.orden] = new CampoArchivoPlano(estructura.nombre, estructura.orden, estructura.posIni, estructura.longitud, estructura.esFiltro);
    }
}
