// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.procesador;

import java.util.Calendar;
import com.davivienda.sara.entitys.ProvisionHostPK;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.estructura.ProvisionesEstructuraRegistro;
import java.util.LinkedList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.sara.entitys.ProvisionHost;
import java.util.Collection;
import com.davivienda.sara.base.ProcesadorArchivoProvisionesInterface;
import com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano;

public class ProvisionesProcesadorArchivo extends ProcesadorArchivoPlano implements ProcesadorArchivoProvisionesInterface
{
    private Collection<ProvisionHost> regs;
    
    public ProvisionesProcesadorArchivo(final ArchivoPlano archivo) {
        super(archivo);
    }
    
    public Collection<ProvisionHost> getRegistrosProvisiones() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = (Collection<String[]>)super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        this.regs = new LinkedList<ProvisionHost>();
        if (regsStr != null) {
            for (final String[] strings : regsStr) {
                try {
                    if (this.obtenerCodigoCajero(strings[ProvisionesEstructuraRegistro.MOTIVO_MOVIMIENTO.orden].trim(), strings) == null) {
                        continue;
                    }
                    this.regs.add(this.aProvisiones(strings));
                }
                catch (Exception ex) {
                    Logger.getLogger("globalApp").log(Level.SEVERE, "error en registro provisiones : " + strings + " del tipo: " + ex.getMessage(), ex);
                }
            }
        }
        return this.regs;
    }
    
    private ProvisionHost aProvisiones(final String[] datos) {
        ProvisionHost provision = null;
        ProvisionHostPK provisionPK = null;
        try {
            final Integer codigoCajero = this.obtenerCodigoCajero(datos[ProvisionesEstructuraRegistro.MOTIVO_MOVIMIENTO.orden].trim(), datos);
            final Calendar fechaCal = Cadena.aCalendar(datos[ProvisionesEstructuraRegistro.FECHA_SISTEMA.orden], FormatoFecha.AAAAMMDD);
            final Cajero cajero = new Cajero(codigoCajero);
            provisionPK = new ProvisionHostPK();
            provision = new ProvisionHost(provisionPK);
            provision.setCajero(cajero);
            provisionPK.setCodigoCajero(codigoCajero);
            provisionPK.setFecha(fechaCal.getTime());
            provisionPK.setHora(datos[ProvisionesEstructuraRegistro.HORA_SISTEMA.orden]);
            provisionPK.setMotivo((short)Cadena.aShort(datos[ProvisionesEstructuraRegistro.MOTIVO_MOVIMIENTO.orden]));
            provision.setReferencia(datos[ProvisionesEstructuraRegistro.REFERENCIA.orden]);
            provisionPK.setTipo((short)Cadena.aShort(datos[ProvisionesEstructuraRegistro.TIPO_MOVIMIENTO.orden]));
            provision.setUsuarioHost(datos[ProvisionesEstructuraRegistro.USUARIO.orden]);
            provision.setValor((long)Cadena.aValor(datos[ProvisionesEstructuraRegistro.VALOR.orden]));
            provision.setFechaAplicacion(Cadena.aDate(datos[ProvisionesEstructuraRegistro.FECHA_APLICACION.orden], FormatoFecha.AAAAMMDD));
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "error en registro provisiones creando el objeto de provisiones del tipo: " + ex.getMessage(), ex);
        }
        return provision;
    }
    
    public Integer obtenerCodigoCajero(final String motivo, final String[] datos) throws NumberFormatException {
        Integer codigoCajero = null;
        try {
            if (motivo.equals("0112")) {
                codigoCajero = Cadena.aInteger(datos[ProvisionesEstructuraRegistro.IDENTIFICACION_TERMINAL.orden]);
            }
            if (motivo.equals("0070") || motivo.equals("0075")) {
                codigoCajero = Cadena.aInteger(datos[ProvisionesEstructuraRegistro.TALON.orden]);
            }
            if (motivo.equals("0016") || motivo.equals("0035")) {
                final String codigo = Cadena.subCadena(datos[ProvisionesEstructuraRegistro.CUENTA.orden], Integer.valueOf(12), Integer.valueOf(16));
                codigoCajero = Cadena.aInteger(codigo);
            }
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "error en registro provisiones creando el objeto de provisiones del tipo: " + ex.getMessage(), ex);
        }
        return codigoCajero;
    }
}
