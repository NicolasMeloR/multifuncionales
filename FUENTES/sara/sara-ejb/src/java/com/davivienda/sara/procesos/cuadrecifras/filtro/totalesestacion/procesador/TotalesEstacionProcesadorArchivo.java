// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.procesador;

import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.estructura.TotalesEstacionEstructuraRegistro;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import java.util.Calendar;
import com.davivienda.sara.entitys.TotalesEstacion;
import java.util.Collection;
import com.davivienda.sara.base.ProcesadorArchivoTotalesEstacionInterface;
import com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano;

public class TotalesEstacionProcesadorArchivo extends ProcesadorArchivoPlano implements ProcesadorArchivoTotalesEstacionInterface
{
    private Collection<TotalesEstacion> regs;
    private Calendar fecha;
    
    public TotalesEstacionProcesadorArchivo(final ArchivoPlano archivo, final Calendar fecha) {
        super(archivo);
        this.fecha = fecha;
    }
    
    public Collection<TotalesEstacion> getRegistrosTotalesEstacion() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = (Collection<String[]>)super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        this.regs = new LinkedList<TotalesEstacion>();
        if (regsStr != null) {
            for (final String[] strings : regsStr) {
                this.regs.add(this.aTotalesEstacion(strings));
            }
        }
        return this.regs;
    }
    
    private TotalesEstacion aTotalesEstacion(final String[] datos) {
        TotalesEstacion totalesEstacion = null;
        final Integer codigoCajero = Cadena.aInteger(datos[TotalesEstacionEstructuraRegistro.ESTACION.orden]);
        final Integer codigoTotal = Cadena.aInteger(datos[TotalesEstacionEstructuraRegistro.TOTAL.orden]);
        final Cajero cajero = new Cajero(codigoCajero);
        totalesEstacion = new TotalesEstacion(Fecha.getDate(this.fecha.getTime(), -1), codigoCajero, codigoTotal);
        totalesEstacion.setCajero(cajero);
        totalesEstacion.setCanal(Cadena.aShort(datos[TotalesEstacionEstructuraRegistro.CANAL.orden], "0"));
        totalesEstacion.setCantidadevento(Cadena.aLong(datos[TotalesEstacionEstructuraRegistro.CANTIDAD_EVENTOS.orden], "0"));
        totalesEstacion.setValorevento(Cadena.aLong(datos[TotalesEstacionEstructuraRegistro.VALOR_EVENTOS.orden], "0"));
        return totalesEstacion;
    }
}
