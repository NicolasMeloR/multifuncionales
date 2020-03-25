package com.davivienda.sara.procesos.diarioelectronico.filtro.edc.procesador;

import com.davivienda.sara.base.ProcesadorArchivoDiarioElectronicoInterface;
import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.entitys.DiarioElectronicoPK;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.entitys.transaccion.TransaccionPK;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.EdcEstructuraRegistro;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.EdcTipoRegistro;
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
public class EdcProcesadorArchivo extends ProcesadorArchivoPlano implements ProcesadorArchivoDiarioElectronicoInterface {

    private Collection<DiarioElectronico> regs;
    private Calendar fechaTira;
      private Calendar fechaAux;

    public EdcProcesadorArchivo(ArchivoPlano archivo,Calendar fechaTira) {
        super(archivo);
        this.fechaTira=fechaTira;
    }

    public Collection<DiarioElectronico> getRegistrosDiarioElectronico() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
         int secuencia=1;
        Collection<String[]> regsStr = null;
        regsStr = super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        regs = new LinkedList<DiarioElectronico>();
        if (regsStr != null) {
            for (String[] strings : regsStr) {
                regs.add(aDiarioElectronico(strings,secuencia));
                secuencia++;
            }
        }
        return regs;
    }

    public Collection<Transaccion> getRegistrosTipoTransaccion() throws FileNotFoundException, IllegalArgumentException {
        Collection<Transaccion> regsTransaccion = new ArrayList<Transaccion>();
       
        if (regs == null) {
            getRegistrosDiarioElectronico();
        }
        for (DiarioElectronico de : regs) {
            if (EdcTipoRegistro.getTipoRegistro(de.getTipoRegistro()).equals(EdcTipoRegistro.TRANSACTION_DATA) &&  de.getInformacion().indexOf("TRAN") > -1)  {
                Transaccion transaccion = aTransaccion(de.getInformacion());
                regsTransaccion.add(transaccion);
            }
        }
        return regsTransaccion;
    }


    private DiarioElectronico aDiarioElectronico(String[] datos,int secuencia) {
        DiarioElectronico da = new DiarioElectronico();
       // String ts = datos[EdcEstructuraRegistro.FECHA.orden] + datos[EdcEstructuraRegistro.HORA.orden];
        //Alvaro Garcia 17 de marzo
       //  Calendar calTmp = com.davivienda.utilidades.conversion.Cadena.aCalendar(ts, "MMddyyHHmmss");
         Calendar calTmp =this.fechaTira;
        
        Integer intTmp = com.davivienda.utilidades.conversion.Cadena.aInteger(datos[EdcEstructuraRegistro.SECUENCIA.orden], "0");
        Integer codCajero = com.davivienda.utilidades.conversion.Cadena.aInteger(datos[EdcEstructuraRegistro.CODIGO_CAJERO.orden], "0");
        DiarioElectronicoPK daPk = new DiarioElectronicoPK(codCajero, intTmp, calTmp.getTime());
        da.setDiarioElectronicoPK(daPk);
        da.setInformacion(datos[EdcEstructuraRegistro.INFORMACION.orden]);
        Character charTmp = datos[EdcEstructuraRegistro.TIPO_REGISTRO.orden].charAt(0);
        da.setTipoRegistro(charTmp);
        da.getDiarioElectronicoPK().setSecuencia(secuencia);
        return da;
    }

    private Transaccion aTransaccion(String informacionEdc) {
        Transaccion obj = null;
        TransaccionPK objPk = null;
        com.davivienda.utilidades.edc.dto.TipoTransaccionRegistroDto dto = new com.davivienda.utilidades.edc.dto.TipoTransaccionRegistroDto(informacionEdc);
        if (dto.errores.isEmpty()) {
            obj = new Transaccion();
            objPk = new TransaccionPK();
            objPk.setCodigoCajero(dto.codigoCajero);
            obj.setTransaccionPK(objPk);
            obj.setCodigoTerminacionTransaccion(dto.codigoTerminacion);
            obj.setCodigoTransaccion(dto.codigoTran);
            obj.setCuenta(dto.cuenta);
            obj.setErrorTransaccion(dto.codigoErrorHost);
         
            objPk.setFechaTransaccion(dto.fecha.getTime());
            
            objPk.setNumeroTransaccion(dto.talon);
            obj.setReferencia(dto.referencia);
            obj.setTarjeta(dto.tarjeta);
            obj.setTipoTransaccion(dto.tipoTran);
            obj.setValorEntregado(dto.valorEntregado);
            obj.setValorSolicitado(dto.valorTran);
        } else {
            super.erroresDelProceso.addAll(dto.errores);
        }
        return obj;
    }
}
