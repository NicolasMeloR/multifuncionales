package com.davivienda.sara.procesos.diarioelectronicotemp.filtro.edc.procesador;

import com.davivienda.sara.base.ProcesadorArchivoDiarioElectronicoTEMPInterface;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import com.davivienda.sara.entitys.DiarioelectronicoTempPK;
import com.davivienda.sara.entitys.TransaccionTemp;
import com.davivienda.sara.entitys.TransaccionTempPK;
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
public class EdcProcesadorArchivoTemp extends ProcesadorArchivoPlano implements ProcesadorArchivoDiarioElectronicoTEMPInterface {

    private Collection<DiarioelectronicoTemp> regs;
    private Calendar fechaTira;
      private Calendar fechaAux;

    public EdcProcesadorArchivoTemp(ArchivoPlano archivo,Calendar fechaTira) {
        super(archivo);
        this.fechaTira=fechaTira;
    }

    public Collection<DiarioelectronicoTemp> getRegistrosDiarioElectronicoTemp() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        regs = new LinkedList<DiarioelectronicoTemp>();
        if (regsStr != null) {
            for (String[] strings : regsStr) {
                regs.add(aDiarioElectronicoTemp(strings));
            }
        }
        return regs;
    }

    public Collection<TransaccionTemp> getRegistrosTipoTransaccionTemp() throws FileNotFoundException, IllegalArgumentException {
        Collection<TransaccionTemp> regsTransaccion = new ArrayList<TransaccionTemp>();
        if (regs == null) {
            getRegistrosDiarioElectronicoTemp();
        }
        for (DiarioelectronicoTemp de : regs) {
            if (EdcTipoRegistro.getTipoRegistro(de.getTiporegistro()).equals(EdcTipoRegistro.TRANSACTION_DATA) &&  de.getInformacion().indexOf("TRAN") > -1)  {
                TransaccionTemp transaccion = aTransaccionTemp(de.getInformacion());
                regsTransaccion.add(transaccion);
            }
        }
        return regsTransaccion;
    }


    private DiarioelectronicoTemp aDiarioElectronicoTemp(String[] datos) {
        DiarioelectronicoTemp da = new DiarioelectronicoTemp();
        String ts = datos[EdcEstructuraRegistro.FECHA.orden] + datos[EdcEstructuraRegistro.HORA.orden];
        //Alvaro Garcia 17 de marzo
       //  Calendar calTmp = com.davivienda.utilidades.conversion.Cadena.aCalendar(ts, "MMddyyHHmmss");
         Calendar calTmp =this.fechaTira;
        
        Integer intTmp = com.davivienda.utilidades.conversion.Cadena.aInteger(datos[EdcEstructuraRegistro.SECUENCIA.orden], "0");
        Integer codCajero = com.davivienda.utilidades.conversion.Cadena.aInteger(datos[EdcEstructuraRegistro.CODIGO_CAJERO.orden], "0");
        DiarioelectronicoTempPK daPk = new DiarioelectronicoTempPK(codCajero, intTmp, calTmp.getTime());
        da.setDiarioelectronicoTempPK(daPk);
        da.setInformacion(datos[EdcEstructuraRegistro.INFORMACION.orden]);
        Character charTmp = datos[EdcEstructuraRegistro.TIPO_REGISTRO.orden].charAt(0);
        da.setTiporegistro(charTmp);
        return da;
    }

    private TransaccionTemp aTransaccionTemp(String informacionEdc) {
        TransaccionTemp obj = null;
        TransaccionTempPK objPk = null;
        com.davivienda.utilidades.edc.dto.TipoTransaccionRegistroDto dto = new com.davivienda.utilidades.edc.dto.TipoTransaccionRegistroDto(informacionEdc);
        if (dto.errores.isEmpty()) {
            obj = new TransaccionTemp();
            objPk = new TransaccionTempPK();
            objPk.setCodigocajero(dto.codigoCajero);
            obj.setTransaccionTempPK(objPk);
            obj.setCodigoterminaciontransaccion(dto.codigoTerminacion);
            obj.setCodigotransaccion(dto.codigoTran);
            obj.setCuenta(dto.cuenta);
            obj.setErrortransaccion(dto.codigoErrorHost);
         
            objPk.setFechatransaccion(dto.fecha.getTime());
            
            objPk.setNumerotransaccion(dto.talon);
            obj.setReferencia(dto.referencia);
            obj.setTarjeta(dto.tarjeta);
            obj.setTipotransaccion(dto.tipoTran);
            obj.setValorentregado(dto.valorEntregado);
            obj.setValorsolicitado(dto.valorTran);
        } else {
            super.erroresDelProceso.addAll(dto.errores);
        }
        return obj;
    }
}
