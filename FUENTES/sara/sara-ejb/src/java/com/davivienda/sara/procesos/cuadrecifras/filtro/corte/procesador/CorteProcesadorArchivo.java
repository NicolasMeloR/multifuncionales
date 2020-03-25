// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.corte.procesador;

import java.util.Calendar;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.sara.procesos.cuadrecifras.filtro.corte.estructura.CorteEstructuraRegistro;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.sara.entitys.BilletajeCajero;
import java.util.Collection;
import com.davivienda.sara.base.ProcesadorArchivoCorteInterface;
import com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano;

public class CorteProcesadorArchivo extends ProcesadorArchivoPlano implements ProcesadorArchivoCorteInterface
{
    private Collection<BilletajeCajero> regs;
    
    public CorteProcesadorArchivo(final ArchivoPlano archivo) {
        super(archivo);
    }
    
    public Collection<BilletajeCajero> getRegistrosBilletajeCajero() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = (Collection<String[]>)super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        this.regs = new LinkedList<BilletajeCajero>();
        if (regsStr != null) {
            for (final String[] strings : regsStr) {
                this.regs.add(this.aBilletajeCajero(strings));
            }
        }
        return this.regs;
    }
    
    private BilletajeCajero aBilletajeCajero(final String[] datos) {
        BilletajeCajero billetajeCajero = null;
        final Integer codigoCajero = Cadena.aInteger(datos[CorteEstructuraRegistro.CAJERO.orden]);
        Calendar fechaCal = Cadena.aCalendar(datos[CorteEstructuraRegistro.FECHA.orden], "yyyyMMdd");
        fechaCal = Fecha.getCalendar(fechaCal, -1);
        final Cajero cajero = new Cajero(codigoCajero);
        billetajeCajero = new BilletajeCajero(codigoCajero, fechaCal.getTime());
        billetajeCajero.setCajero(cajero);
        billetajeCajero.setDenominacion1(Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_1.orden], "0"));
        billetajeCajero.setDenominacion2(Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_2.orden], "0"));
        billetajeCajero.setDenominacion3(Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_3.orden], "0"));
        billetajeCajero.setDenominacion4(Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_4.orden], "0"));
        billetajeCajero.setDenominacion5(Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_5.orden], "0"));
        billetajeCajero.setDenominacionUf(Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_UF.orden], "0"));
        billetajeCajero.setTotal1(Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_1.orden], "0"));
        billetajeCajero.setTotal2(Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_2.orden], "0"));
        billetajeCajero.setTotal3(Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_3.orden], "0"));
        billetajeCajero.setTotal4(Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_4.orden], "0"));
        billetajeCajero.setTotal5(Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_5.orden], "0"));
        billetajeCajero.setTotalUf(Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_UF.orden], "0"));
        billetajeCajero.setCaja1(Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_1.orden], "0"));
        billetajeCajero.setCaja2(Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_2.orden], "0"));
        billetajeCajero.setCaja3(Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_3.orden], "0"));
        billetajeCajero.setCaja4(Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_4.orden], "0"));
        billetajeCajero.setCaja5(Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_5.orden], "0"));
        billetajeCajero.setCajaUf(Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_UF.orden], "0"));
        billetajeCajero.setDispensado1(Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_1.orden], "0"));
        billetajeCajero.setDispensado2(Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_2.orden], "0"));
        billetajeCajero.setDispensado3(Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_3.orden], "0"));
        billetajeCajero.setDispensado4(Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_4.orden], "0"));
        billetajeCajero.setDispensado5(Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_5.orden], "0"));
        billetajeCajero.setDispensadoUf(Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_UF.orden], "0"));
        billetajeCajero.setPurga1(Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_1.orden], "0"));
        billetajeCajero.setPurga2(Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_2.orden], "0"));
        billetajeCajero.setPurga3(Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_3.orden], "0"));
        billetajeCajero.setPurga4(Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_4.orden], "0"));
        billetajeCajero.setPurga5(Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_5.orden], "0"));
        billetajeCajero.setPurgaUf(Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_UF.orden], "0"));
        billetajeCajero.setRetract1(Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_1.orden], "0"));
        billetajeCajero.setRetract2(Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_2.orden], "0"));
        billetajeCajero.setRetract3(Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_3.orden], "0"));
        billetajeCajero.setRetract4(Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_4.orden], "0"));
        billetajeCajero.setRetract5(Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_5.orden], "0"));
        billetajeCajero.setRetractUf(Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_UF.orden], "0"));
        billetajeCajero.setCanRetDav(Cadena.aInteger(datos[CorteEstructuraRegistro.CAN_RET_DAV.orden], "0"));
        billetajeCajero.setCanAvan(Cadena.aInteger(datos[CorteEstructuraRegistro.CAN_AVAN_DAV.orden], "0"));
        billetajeCajero.setCanRetRed(Cadena.aInteger(datos[CorteEstructuraRegistro.CAN_RET_RED.orden], "0"));
        billetajeCajero.setValRetDav(Cadena.aLong(datos[CorteEstructuraRegistro.VAL_RET_DAV.orden], "0"));
        billetajeCajero.setValAvan(Cadena.aLong(datos[CorteEstructuraRegistro.VAL_AVAN_DAV.orden], "0"));
        billetajeCajero.setValRetRed(Cadena.aLong(datos[CorteEstructuraRegistro.VAL_RET_RED.orden], "0"));
        return billetajeCajero;
    }
}
