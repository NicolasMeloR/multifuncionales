// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.diarioelectronicotemp.filtro.edc.procesador;

import com.davivienda.sara.entitys.TransaccionTempPK;
import com.davivienda.utilidades.edc.dto.TipoTransaccionRegistroDto;
import com.davivienda.sara.entitys.DiarioelectronicoTempPK;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.EdcEstructuraRegistro;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.EdcTipoRegistro;
import java.util.ArrayList;
import com.davivienda.sara.entitys.TransaccionTemp;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import java.util.Calendar;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import java.util.Collection;
import com.davivienda.sara.base.ProcesadorArchivoDiarioElectronicoTEMPInterface;
import com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano;

public class EdcProcesadorArchivoTemp extends ProcesadorArchivoPlano implements ProcesadorArchivoDiarioElectronicoTEMPInterface
{
    private Collection<DiarioelectronicoTemp> regs;
    private Calendar fechaTira;
    private Calendar fechaAux;
    
    public EdcProcesadorArchivoTemp(final ArchivoPlano archivo, final Calendar fechaTira) {
        super(archivo);
        this.fechaTira = fechaTira;
    }
    
    public Collection<DiarioelectronicoTemp> getRegistrosDiarioElectronicoTemp() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        Collection<String[]> regsStr = null;
        regsStr = (Collection<String[]>)super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        this.regs = new LinkedList<DiarioelectronicoTemp>();
        if (regsStr != null) {
            for (final String[] strings : regsStr) {
                this.regs.add(this.aDiarioElectronicoTemp(strings));
            }
        }
        return this.regs;
    }
    
    public Collection<TransaccionTemp> getRegistrosTipoTransaccionTemp() throws FileNotFoundException, IllegalArgumentException {
        final Collection<TransaccionTemp> regsTransaccion = new ArrayList<TransaccionTemp>();
        if (this.regs == null) {
            this.getRegistrosDiarioElectronicoTemp();
        }
        for (final DiarioelectronicoTemp de : this.regs) {
            if (EdcTipoRegistro.getTipoRegistro(de.getTiporegistro()).equals(EdcTipoRegistro.TRANSACTION_DATA) && de.getInformacion().indexOf("TRAN") > -1) {
                final TransaccionTemp transaccion = this.aTransaccionTemp(de.getInformacion());
                regsTransaccion.add(transaccion);
            }
        }
        return regsTransaccion;
    }
    
    private DiarioelectronicoTemp aDiarioElectronicoTemp(final String[] datos) {
        final DiarioelectronicoTemp da = new DiarioelectronicoTemp();
        final String ts = datos[EdcEstructuraRegistro.FECHA.orden] + datos[EdcEstructuraRegistro.HORA.orden];
        final Calendar calTmp = this.fechaTira;
        final Integer intTmp = Cadena.aInteger(datos[EdcEstructuraRegistro.SECUENCIA.orden], "0");
        final Integer codCajero = Cadena.aInteger(datos[EdcEstructuraRegistro.CODIGO_CAJERO.orden], "0");
        final DiarioelectronicoTempPK daPk = new DiarioelectronicoTempPK(codCajero, intTmp, calTmp.getTime());
        da.setDiarioelectronicoTempPK(daPk);
        da.setInformacion(datos[EdcEstructuraRegistro.INFORMACION.orden]);
        final Character charTmp = datos[EdcEstructuraRegistro.TIPO_REGISTRO.orden].charAt(0);
        da.setTiporegistro(charTmp);
        return da;
    }
    
    private TransaccionTemp aTransaccionTemp(final String informacionEdc) {
        TransaccionTemp obj = null;
        TransaccionTempPK objPk = null;
        final TipoTransaccionRegistroDto dto = new TipoTransaccionRegistroDto(informacionEdc);
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
        }
        else {
            super.erroresDelProceso.addAll(dto.errores);
        }
        return obj;
    }
}
