package com.davivienda.sara.procesos.reintegros.filtro.host.procesador;

import com.davivienda.sara.base.ProcesadorArchivoHostInterface;
import com.davivienda.sara.entitys.HostAtm;
import com.davivienda.sara.entitys.HostAtmPK;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.procesos.reintegros.filtro.host.estructura.HostEstructuraRegistro;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano;
import com.davivienda.utilidades.conversion.Cadena;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import com.davivienda.utilidades.conversion.*;

/**
 * EdcProcesadorArchivo - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class HostProcesadorArchivo extends ProcesadorArchivoPlano implements ProcesadorArchivoHostInterface {

   private Collection<HostAtm> regs;

    public HostProcesadorArchivo(ArchivoPlano archivo) {
        super(archivo);
       
    }

 public Collection<HostAtm> getRegistrosHost() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        regs = new LinkedList<HostAtm>();
        if (regsStr != null) {
            for (String[] strings : regsStr) {
               
               
                   regs.add( aHostAtm(strings));
                
            }
        }
        return regs;
    }
 
 
 //Nuevo para carga por filas
  public Collection<HostAtm> getRegistrosHostXFilas(int regInicial, int cantidadRegistros) throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = super.obtenerRegistrosDataXFilas(regInicial, cantidadRegistros);
        super.getArchivo().cerrarArchivo();
        regs = new LinkedList<HostAtm>();
        if (regsStr != null) {
            for (String[] strings : regsStr) {
               
               
                   regs.add( aHostAtm(strings));
                
            }
        }
        return regs;
    }

  


    private HostAtm aHostAtm(String[] datos ) {
            
            HostAtm hostAtm = null;
            HostAtmPK hostAtmPK = null;
            Integer codigoCajero;
            
            try{
           
            codigoCajero = com.davivienda.utilidades.conversion.Cadena.aInteger(datos[HostEstructuraRegistro.CODIGO_CAJERO.orden]);
                   
            Calendar fechaSistema = Cadena.aCalendar(datos[HostEstructuraRegistro.FECHA_SISTEMA.orden]+datos[HostEstructuraRegistro.HORA_SISTEMA.orden],FormatoFecha.FECHA_HOST);
            Calendar fecha = Cadena.aCalendar(datos[HostEstructuraRegistro.FECHA.orden],FormatoFecha.AAAAMMDD);
         
            Cajero cajero = new Cajero(codigoCajero);
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
            hostAtm.setValor(com.davivienda.utilidades.conversion.Cadena.aLong(datos[HostEstructuraRegistro.VALOR.orden], "0"));
            hostAtm.setIndices(datos[HostEstructuraRegistro.INDICES.orden]);
            hostAtm.setFiller(datos[HostEstructuraRegistro.FILLER.orden]);
                        
       
            }
            catch (Exception ex) {
                      java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "error en registro hostatmes creando el objeto  HostAtm del tipo: "+ ex.getMessage(), ex);             
                }
            return hostAtm;
    }

    
}
