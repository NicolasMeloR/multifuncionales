package com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.procesador;

import com.davivienda.sara.base.ProcesadorArchivoProvisionesInterface;
import com.davivienda.sara.entitys.ProvisionHost;
import com.davivienda.sara.entitys.ProvisionHostPK;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.estructura.ProvisionesEstructuraRegistro;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano;
import java.io.FileNotFoundException;
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
public class ProvisionesProcesadorArchivo extends ProcesadorArchivoPlano implements ProcesadorArchivoProvisionesInterface {

   private Collection<ProvisionHost> regs;

    public ProvisionesProcesadorArchivo(ArchivoPlano archivo) {
        super(archivo);
       
    }

 public Collection<ProvisionHost> getRegistrosProvisiones() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        regs = new LinkedList<ProvisionHost>();
          //ProvisionHost provision = null;
        if (regsStr != null) {
            for (String[] strings : regsStr) {
                //OJO VALIDAR CORRECTAMENTE SI SE GUARAD EL REGISTRO
                try{
                   
                
                 if (obtenerCodigoCajero(strings[ProvisionesEstructuraRegistro.MOTIVO_MOVIMIENTO.orden].trim(), strings)!=null)
                 {
                   regs.add( aProvisiones(strings));
                 }
                 else
                 {
                  int io=0;
                 }
                // provision= aProvisiones(strings);        
              
                 }
                  catch (Exception ex) {
                       java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "error en registro provisiones : " +strings+" del tipo: "+ ex.getMessage(), ex);             
                }
            }
        }
        return regs;
    }

  


    private ProvisionHost aProvisiones(String[] datos ) {
            
            ProvisionHost provision = null;
            ProvisionHostPK provisionPK = null;
            Integer codigoCajero;
            
            try{
            codigoCajero = obtenerCodigoCajero(datos[ProvisionesEstructuraRegistro.MOTIVO_MOVIMIENTO.orden].trim(), datos);
            // java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "cargando codigocajero : " +codigoCajero.toString());

           
            Calendar fechaCal = Cadena.aCalendar(datos[ProvisionesEstructuraRegistro.FECHA_SISTEMA.orden],FormatoFecha.AAAAMMDD);
            //fechaCal.add(Calendar.DATE, -1);
            //OJOOOOO REVISAR
            
            Cajero cajero = new Cajero(codigoCajero);
            provisionPK = new ProvisionHostPK();
            provision = new ProvisionHost(provisionPK);
            provision.setCajero(cajero);
            provisionPK.setCodigoCajero(codigoCajero);
            provisionPK.setFecha(fechaCal.getTime());
            provisionPK.setHora(datos[ProvisionesEstructuraRegistro.HORA_SISTEMA.orden]);
            provisionPK.setMotivo(Cadena.aShort(datos[ProvisionesEstructuraRegistro.MOTIVO_MOVIMIENTO.orden]));
            provision.setReferencia(datos[ProvisionesEstructuraRegistro.REFERENCIA.orden]);
            provisionPK.setTipo(Cadena.aShort(datos[ProvisionesEstructuraRegistro.TIPO_MOVIMIENTO.orden]));
            provision.setUsuarioHost(datos[ProvisionesEstructuraRegistro.USUARIO.orden]);
            provision.setValor(Cadena.aValor(datos[ProvisionesEstructuraRegistro.VALOR.orden]));
            provision.setFechaAplicacion(Cadena.aDate(datos[ProvisionesEstructuraRegistro.FECHA_APLICACION.orden], FormatoFecha.AAAAMMDD));
            }
            catch (Exception ex) {
                      java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "error en registro provisiones creando el objeto de provisiones del tipo: "+ ex.getMessage(), ex);             
                }
            return provision;
    }
       public Integer obtenerCodigoCajero(String motivo, String[] datos) throws NumberFormatException {
        Integer codigoCajero = null;
         try{
        if (motivo.equals("0112")) {
            codigoCajero = com.davivienda.utilidades.conversion.Cadena.aInteger(datos[ProvisionesEstructuraRegistro.IDENTIFICACION_TERMINAL.orden]);
        }
        if (motivo.equals("0070") || motivo.equals("0075")) {
            codigoCajero = com.davivienda.utilidades.conversion.Cadena.aInteger(datos[ProvisionesEstructuraRegistro.TALON.orden]);
        }
        if (motivo.equals("0016") || motivo.equals("0035")) {
            String codigo = com.davivienda.utilidades.conversion.Cadena.subCadena(datos[ProvisionesEstructuraRegistro.CUENTA.orden], 12, 16);
            codigoCajero = com.davivienda.utilidades.conversion.Cadena.aInteger(codigo);
        }
        }catch (Exception ex) {
                      java.util.logging.Logger.getLogger("globalApp").log(java.util.logging.Level.SEVERE, "error en registro provisiones creando el objeto de provisiones del tipo: "+ ex.getMessage(), ex);             
                }
        return codigoCajero;
    }
    
}
