// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.reintegros.filtro.host.estructura;

import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.io.FileNotFoundException;
import com.davivienda.utilidades.archivoplano.FormatoCampo;
import com.davivienda.utilidades.archivoplano.RegistroArchivoPlano;
import com.davivienda.utilidades.archivoplano.CampoArchivoPlano;
import java.util.Calendar;
import java.util.ArrayList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;

public class HostArchivo extends ArchivoPlano
{
    public String prefijoNombre;
    private Integer codigoCajero;
    private ArrayList<String> erroresConversion;
    public static final String[] TIPOS_TRANSACCION;
    
    public HostArchivo(final String directorio, final String nombreArchivo, final String indOcca) {
        super(directorio, nombreArchivo);
    }
    
    public HostArchivo(final String nombreArchivo) {
        super(nombreArchivo);
    }
    
    public HostArchivo(final Calendar fecha, final String directorio) throws FileNotFoundException {
        super.setDirectorio(directorio);
        this.erroresConversion = new ArrayList<String>();
        this.prefijoNombre = "mvtoatm01.";
        this.setNombreProcesoFecha(fecha);
        this.camposRegistro = new CampoArchivoPlano[13];
        (this.registroData = new RegistroArchivoPlano()).setLongitudRegistro(100);
        this.registroData.setFormatoCampo(FormatoCampo.LONGITUD_FIJA);
        this.registroData.setIncluirNombreCampo(false);
        final HostEstructuraRegistro[] estructura = HostEstructuraRegistro.values();
        for (int i = 0; i < estructura.length; ++i) {
            this.addCampo(estructura[i]);
        }
        this.camposRegistro[HostEstructuraRegistro.TIPO_TRANSACCION.orden].valoresFiltro = HostArchivo.TIPOS_TRANSACCION;
        this.registroData.setCampos(this.camposRegistro);
        super.obtenerArchivo();
    }
    
    private void setNombreProcesoFecha(final Calendar fecha) {
        super.setNombreArchivo(this.prefijoNombre += Fecha.aCadena(fecha, FormatoFecha.AA_MM_DD));
    }
    
    private void addCampo(final HostEstructuraRegistro estructura) {
        this.camposRegistro[estructura.orden] = new CampoArchivoPlano(estructura.nombre, estructura.orden, estructura.posIni, estructura.longitud, estructura.esFiltro);
    }
    
    static {
        TIPOS_TRANSACCION = new String[] { "0046" };
    }
}
