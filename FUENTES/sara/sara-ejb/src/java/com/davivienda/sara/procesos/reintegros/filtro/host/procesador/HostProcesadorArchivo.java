// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.reintegros.filtro.host.procesador;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.davivienda.sara.entitys.HostAtmPK;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.sara.procesos.reintegros.filtro.host.estructura.HostEstructuraRegistro;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.sara.entitys.HostAtm;
import java.util.Collection;
import com.davivienda.sara.base.ProcesadorArchivoHostInterface;
import com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano;

public class HostProcesadorArchivo extends ProcesadorArchivoPlano implements ProcesadorArchivoHostInterface
{
    private Collection<HostAtm> regs;
    
    public HostProcesadorArchivo(final ArchivoPlano archivo) {
        super(archivo);
    }
    
    public Collection<HostAtm> getRegistrosHost() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = (Collection<String[]>)super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        this.regs = new LinkedList<HostAtm>();
        if (regsStr != null) {
            for (final String[] strings : regsStr) {
                this.regs.add(this.aHostAtm(strings));
            }
        }
        return this.regs;
    }
    
    public Collection<HostAtm> getRegistrosHostXFilas(final int regInicial, final int cantidadRegistros) throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = (Collection<String[]>)super.obtenerRegistrosDataXFilas(regInicial, cantidadRegistros);
        super.getArchivo().cerrarArchivo();
        this.regs = new LinkedList<HostAtm>();
        if (regsStr != null) {
            for (final String[] strings : regsStr) {
                this.regs.add(this.aHostAtm(strings));
            }
        }
        return this.regs;
    }
    
    private HostAtm aHostAtm(final String[] datos) {
        HostAtm hostAtm = null;
        HostAtmPK hostAtmPK = null;
        try {
            final Integer codigoCajero = Cadena.aInteger(datos[HostEstructuraRegistro.CODIGO_CAJERO.orden]);
            final Calendar fechaSistema = Cadena.aCalendar(datos[HostEstructuraRegistro.FECHA_SISTEMA.orden] + datos[HostEstructuraRegistro.HORA_SISTEMA.orden], FormatoFecha.FECHA_HOST);
            final Calendar fecha = Cadena.aCalendar(datos[HostEstructuraRegistro.FECHA.orden], FormatoFecha.AAAAMMDD);
            final Cajero cajero = new Cajero(codigoCajero);
            hostAtmPK = new HostAtmPK();
            hostAtm = new HostAtm(hostAtmPK);
            hostAtm.setCajero(cajero);
            hostAtmPK.setCodigoCajero(codigoCajero);
            hostAtmPK.setFechaSistema(fechaSistema.getTime());
            hostAtmPK.setTalon(Cadena.aInteger(datos[HostEstructuraRegistro.TALON.orden]));
            hostAtm.setFecha(fecha.getTime());
            hostAtm.setNumeroCuenta(datos[HostEstructuraRegistro.NUMERO_CUENTA.orden]);
            hostAtm.setDatosTarjeta(datos[HostEstructuraRegistro.DATOS_TARJETA.orden]);
            hostAtm.setTipoTransaccion(Cadena.aInteger(datos[HostEstructuraRegistro.TIPO_TRANSACCION.orden]));
            hostAtm.setTipoTarjeta(datos[HostEstructuraRegistro.TIPO_TARJETA.orden]);
            hostAtm.setCodigoOcca(Cadena.aInteger(datos[HostEstructuraRegistro.OCCA.orden]));
            hostAtm.setValor(Cadena.aLong(datos[HostEstructuraRegistro.VALOR.orden], "0"));
            hostAtm.setIndices(datos[HostEstructuraRegistro.INDICES.orden]);
            hostAtm.setFiller(datos[HostEstructuraRegistro.FILLER.orden]);
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").log(Level.SEVERE, "error en registro hostatmes creando el objeto  HostAtm del tipo: " + ex.getMessage(), ex);
        }
        return hostAtm;
    }
}
