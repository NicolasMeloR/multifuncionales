// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.corte.estructura;

import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.io.FileNotFoundException;
import com.davivienda.utilidades.archivoplano.FormatoCampo;
import com.davivienda.utilidades.archivoplano.RegistroArchivoPlano;
import com.davivienda.utilidades.archivoplano.CampoArchivoPlano;
import java.util.Calendar;
import java.util.ArrayList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;

public class CorteArchivo extends ArchivoPlano
{
    public String prefijoNombre;
    private ArrayList<String> erroresConversion;
    
    public CorteArchivo(final String directorio, final String nombreArchivo) {
        super(directorio, nombreArchivo);
    }
    
    public CorteArchivo(final String nombreArchivo) {
        super(nombreArchivo);
    }
    
    public CorteArchivo(final Calendar fecha, final String directorio) throws FileNotFoundException {
        super.setDirectorio(directorio);
        this.prefijoNombre = "cfamo001";
        this.erroresConversion = new ArrayList<String>();
        this.setNombreProcesoFecha(fecha);
        this.camposRegistro = new CampoArchivoPlano[44];
        (this.registroData = new RegistroArchivoPlano()).setLongitudRegistro(304);
        this.registroData.setFormatoCampo(FormatoCampo.LONGITUD_FIJA);
        this.registroData.setIncluirNombreCampo(false);
        final CorteEstructuraRegistro[] estructura = CorteEstructuraRegistro.values();
        for (int i = 0; i < estructura.length; ++i) {
            this.addCampo(estructura[i]);
        }
        this.registroData.setCampos(this.camposRegistro);
        super.obtenerArchivo();
    }
    
    public void addCampo(final CorteEstructuraRegistro estructura) {
        this.camposRegistro[estructura.orden] = new CampoArchivoPlano(estructura.nombre, estructura.orden, estructura.posIni, estructura.longitud, estructura.esFiltro);
    }
    
    public void setNombreProcesoFecha(final Calendar fecha) {
        super.setNombreArchivo(this.prefijoNombre = this.prefijoNombre + '.' + Fecha.aCadena(fecha, FormatoFecha.AA_MM_DD));
    }
}
