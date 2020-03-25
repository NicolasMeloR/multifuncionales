package com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.procesador;

import com.davivienda.sara.base.ProcesadorArchivoTotalesEstacionInterface;
import com.davivienda.sara.entitys.TotalesEstacion;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.estructura.TotalesEstacionEstructuraRegistro;
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
public class TotalesEstacionProcesadorArchivo extends ProcesadorArchivoPlano implements ProcesadorArchivoTotalesEstacionInterface {

   private Collection<TotalesEstacion> regs;
   private Calendar fecha;
    public TotalesEstacionProcesadorArchivo(ArchivoPlano archivo,Calendar fecha) {
        super(archivo);
        this.fecha=fecha;
       
    }

 public Collection<TotalesEstacion> getRegistrosTotalesEstacion() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        regs = new LinkedList<TotalesEstacion>();
        if (regsStr != null) {
            for (String[] strings : regsStr) {
                regs.add(aTotalesEstacion(strings));
            }
        }
        return regs;
    }

  


    private TotalesEstacion aTotalesEstacion(String[] datos ) {
            
            TotalesEstacion totalesEstacion = null;
            Integer codigoCajero;
            Integer codigoTotal;
            codigoCajero = com.davivienda.utilidades.conversion.Cadena.aInteger(datos[TotalesEstacionEstructuraRegistro.ESTACION.orden]);
            codigoTotal = com.davivienda.utilidades.conversion.Cadena.aInteger(datos[TotalesEstacionEstructuraRegistro.TOTAL.orden]);
         
            Cajero cajero = new Cajero(codigoCajero);
            totalesEstacion = new TotalesEstacion( com.davivienda.utilidades.conversion.Fecha.getDate(fecha.getTime(),-1),codigoCajero,codigoTotal);
            totalesEstacion.setCajero(cajero);
          
            totalesEstacion.setCanal(com.davivienda.utilidades.conversion.Cadena.aShort(datos[TotalesEstacionEstructuraRegistro.CANAL.orden], "0"));
            totalesEstacion.setCantidadevento(com.davivienda.utilidades.conversion.Cadena.aLong(datos[TotalesEstacionEstructuraRegistro.CANTIDAD_EVENTOS.orden], "0"));
            totalesEstacion.setValorevento(com.davivienda.utilidades.conversion.Cadena.aLong(datos[TotalesEstacionEstructuraRegistro.VALOR_EVENTOS.orden], "0"));
            return totalesEstacion;
    }
    
}
