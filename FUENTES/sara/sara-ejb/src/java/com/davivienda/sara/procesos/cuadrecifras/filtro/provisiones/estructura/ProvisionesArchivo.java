// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.estructura;

import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.io.FileNotFoundException;
import com.davivienda.utilidades.archivoplano.FormatoCampo;
import com.davivienda.utilidades.archivoplano.RegistroArchivoPlano;
import com.davivienda.utilidades.archivoplano.CampoArchivoPlano;
import java.util.Calendar;
import java.util.ArrayList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;

public class ProvisionesArchivo extends ArchivoPlano
{
    public String prefijoNombre;
    private ArrayList<String> erroresConversion;
    public static final String[] TIPOS_MOVIMIENTO_PROVISION;
    public static final String[] MOTIVOS_PROVISION;
    
    public ProvisionesArchivo(final String directorio, final String nombreArchivo) {
        super(directorio, nombreArchivo);
    }
    
    public ProvisionesArchivo(final String nombreArchivo) {
        super(nombreArchivo);
    }
    
    public ProvisionesArchivo(final Calendar fecha, final String directorio) throws FileNotFoundException {
        super.setDirectorio(directorio);
        this.prefijoNombre = "otbmo001";
        this.erroresConversion = new ArrayList<String>();
        this.setNombreProcesoFecha(fecha);
        this.camposRegistro = new CampoArchivoPlano[20];
        (this.registroData = new RegistroArchivoPlano()).setLongitudRegistro(400);
        this.registroData.setFormatoCampo(FormatoCampo.LONGITUD_FIJA);
        this.registroData.setIncluirNombreCampo(false);
        final ProvisionesEstructuraRegistro[] estructura = ProvisionesEstructuraRegistro.values();
        for (int i = 0; i < estructura.length; ++i) {
            this.addCampo(estructura[i]);
        }
        this.camposRegistro[ProvisionesEstructuraRegistro.TIPO_MOVIMIENTO.orden].valoresFiltro = ProvisionesArchivo.TIPOS_MOVIMIENTO_PROVISION;
        this.camposRegistro[ProvisionesEstructuraRegistro.MOTIVO_MOVIMIENTO.orden].valoresFiltro = ProvisionesArchivo.MOTIVOS_PROVISION;
        this.registroData.setCampos(this.camposRegistro);
        super.obtenerArchivo();
    }
    
    public void addCampo(final ProvisionesEstructuraRegistro estructura) {
        this.camposRegistro[estructura.orden] = new CampoArchivoPlano(estructura.nombre, estructura.orden, estructura.posIni, estructura.longitud, estructura.esFiltro);
    }
    
    public void setNombreProcesoFecha(final Calendar fecha) {
        super.setNombreArchivo(this.prefijoNombre = this.prefijoNombre + '.' + Fecha.aCadena(fecha, FormatoFecha.AA_MM_DD));
    }
    
    static {
        TIPOS_MOVIMIENTO_PROVISION = new String[] { "0098", "0099" };
        MOTIVOS_PROVISION = new String[] { "0016", "0035", "0075", "0112", "0070" };
    }
}
