// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.estructura;

import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.io.FileNotFoundException;
import com.davivienda.utilidades.archivoplano.FormatoCampo;
import com.davivienda.utilidades.archivoplano.RegistroArchivoPlano;
import com.davivienda.utilidades.archivoplano.CampoArchivoPlano;
import java.util.Calendar;
import java.util.ArrayList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;

public class TotalesEstacionArchivo extends ArchivoPlano
{
    public String prefijoNombre;
    private ArrayList<String> erroresConversion;
    public static final String[] CODIGOS_TOTAL;
    public static final String[] CANAL;
    
    public TotalesEstacionArchivo(final String directorio, final String nombreArchivo) {
        super(directorio, nombreArchivo);
    }
    
    public TotalesEstacionArchivo(final String nombreArchivo) {
        super(nombreArchivo);
    }
    
    public TotalesEstacionArchivo(final Calendar fecha, final String directorio) throws FileNotFoundException {
        super.setDirectorio(directorio);
        this.prefijoNombre = "geato002";
        this.erroresConversion = new ArrayList<String>();
        this.setNombreProcesoFecha(fecha);
        this.camposRegistro = new CampoArchivoPlano[5];
        (this.registroData = new RegistroArchivoPlano()).setLongitudRegistro(70);
        this.registroData.setFormatoCampo(FormatoCampo.LONGITUD_FIJA);
        this.registroData.setIncluirNombreCampo(false);
        final TotalesEstacionEstructuraRegistro[] estructura = TotalesEstacionEstructuraRegistro.values();
        for (int i = 0; i < estructura.length; ++i) {
            this.addCampo(estructura[i]);
        }
        this.camposRegistro[TotalesEstacionEstructuraRegistro.TOTAL.orden].valoresFiltro = TotalesEstacionArchivo.CODIGOS_TOTAL;
        this.camposRegistro[TotalesEstacionEstructuraRegistro.CANAL.orden].valoresFiltro = TotalesEstacionArchivo.CANAL;
        this.registroData.setCampos(this.camposRegistro);
        super.obtenerArchivo();
    }
    
    public void addCampo(final TotalesEstacionEstructuraRegistro estructura) {
        this.camposRegistro[estructura.orden] = new CampoArchivoPlano(estructura.nombre, estructura.orden, estructura.posIni, estructura.longitud, estructura.esFiltro);
    }
    
    public void setNombreProcesoFecha(final Calendar fecha) {
        super.setNombreArchivo(this.prefijoNombre = this.prefijoNombre + '.' + Fecha.aCadena(fecha, FormatoFecha.AA_MM_DD));
    }
    
    static {
        CODIGOS_TOTAL = new String[] { "000003", "000036", "000098" };
        CANAL = new String[] { "1" };
    }
}
