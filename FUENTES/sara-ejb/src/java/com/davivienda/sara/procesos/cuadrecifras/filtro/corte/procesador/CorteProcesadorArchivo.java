    package com.davivienda.sara.procesos.cuadrecifras.filtro.corte.procesador;

import com.davivienda.sara.base.ProcesadorArchivoCorteInterface;
import com.davivienda.sara.entitys.BilletajeCajero;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.procesos.cuadrecifras.filtro.corte.estructura.CorteEstructuraRegistro;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;

/**
 * EdcProcesadorArchivo - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class CorteProcesadorArchivo extends ProcesadorArchivoPlano implements ProcesadorArchivoCorteInterface {

   private Collection<BilletajeCajero> regs;

    public CorteProcesadorArchivo(ArchivoPlano archivo) {
        super(archivo);
       
    }

 public Collection<BilletajeCajero> getRegistrosBilletajeCajero() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        regs = new LinkedList<BilletajeCajero>();
        if (regsStr != null) {
            for (String[] strings : regsStr) {
                regs.add(aBilletajeCajero(strings));
            }
        }
        return regs;
    }
 


  


    private BilletajeCajero aBilletajeCajero(String[] datos ) {
            
            BilletajeCajero billetajeCajero = null;
            Integer codigoCajero;
            codigoCajero = com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.CAJERO.orden]);
            Calendar fechaCal = com.davivienda.utilidades.conversion.Cadena.aCalendar(datos[CorteEstructuraRegistro.FECHA.orden], "yyyyMMdd");
            fechaCal= com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaCal, -1);
            //fechaCal.add(Calendar.DATE, -1);
            //OJOOOOO REVISAR
            Cajero cajero = new Cajero(codigoCajero);
            billetajeCajero = new BilletajeCajero(codigoCajero,fechaCal.getTime());
            billetajeCajero.setCajero(cajero);
            billetajeCajero.setDenominacion1(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_1.orden], "0"));
            billetajeCajero.setDenominacion2(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_2.orden], "0"));
            billetajeCajero.setDenominacion3(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_3.orden], "0"));
            billetajeCajero.setDenominacion4(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_4.orden], "0"));
            billetajeCajero.setDenominacion5(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_5.orden], "0"));
            billetajeCajero.setDenominacionUf(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DENOMINACION_UF.orden], "0"));
            billetajeCajero.setTotal1(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_1.orden], "0"));
            billetajeCajero.setTotal2(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_2.orden], "0"));
            billetajeCajero.setTotal3(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_3.orden], "0"));
            billetajeCajero.setTotal4(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_4.orden], "0"));
            billetajeCajero.setTotal5(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_5.orden], "0"));
            billetajeCajero.setTotalUf(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.TOTAL_UF.orden], "0"));
            billetajeCajero.setCaja1(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_1.orden], "0"));
            billetajeCajero.setCaja2(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_2.orden], "0"));
            billetajeCajero.setCaja3(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_3.orden], "0"));
            billetajeCajero.setCaja4(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_4.orden], "0"));
            billetajeCajero.setCaja5(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_5.orden], "0"));
            billetajeCajero.setCajaUf(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.CAJA_UF.orden], "0"));
            billetajeCajero.setDispensado1(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_1.orden], "0"));
            billetajeCajero.setDispensado2(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_2.orden], "0"));
            billetajeCajero.setDispensado3(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_3.orden], "0"));
            billetajeCajero.setDispensado4(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_4.orden], "0"));
            billetajeCajero.setDispensado5(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_5.orden], "0"));
            billetajeCajero.setDispensadoUf(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.DISPENSADO_UF.orden], "0"));
            billetajeCajero.setPurga1(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_1.orden], "0"));
            billetajeCajero.setPurga2(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_2.orden], "0"));
            billetajeCajero.setPurga3(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_3.orden], "0"));
            billetajeCajero.setPurga4(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_4.orden], "0"));
            billetajeCajero.setPurga5(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_5.orden], "0"));
            billetajeCajero.setPurgaUf(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.PURGA_UF.orden], "0"));
            billetajeCajero.setRetract1(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_1.orden], "0"));
            billetajeCajero.setRetract2(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_2.orden], "0"));
            billetajeCajero.setRetract3(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_3.orden], "0"));
            billetajeCajero.setRetract4(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_4.orden], "0"));
            billetajeCajero.setRetract5(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_5.orden], "0"));
            billetajeCajero.setRetractUf(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.RETRACT_UF.orden], "0"));
            billetajeCajero.setCanRetDav(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.CAN_RET_DAV.orden], "0"));
            billetajeCajero.setCanAvan(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.CAN_AVAN_DAV.orden], "0"));
            billetajeCajero.setCanRetRed(com.davivienda.utilidades.conversion.Cadena.aInteger(datos[CorteEstructuraRegistro.CAN_RET_RED.orden], "0"));
            billetajeCajero.setValRetDav(com.davivienda.utilidades.conversion.Cadena.aLong(datos[CorteEstructuraRegistro.VAL_RET_DAV.orden], "0"));
            billetajeCajero.setValAvan(com.davivienda.utilidades.conversion.Cadena.aLong(datos[CorteEstructuraRegistro.VAL_AVAN_DAV.orden], "0"));
            billetajeCajero.setValRetRed(com.davivienda.utilidades.conversion.Cadena.aLong(datos[CorteEstructuraRegistro.VAL_RET_RED.orden], "0"));
        return billetajeCajero;
    }
    
}
