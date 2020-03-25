// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.diarioelectronico.filtro.edc.procesador;

import com.davivienda.sara.entitys.transaccion.TransaccionPK;
import com.davivienda.utilidades.edc.dto.TipoTransaccionRegistroDto;
import com.davivienda.sara.entitys.DiarioElectronicoPK;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.EdcEstructuraRegistro;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.EdcTipoRegistro;
import java.util.ArrayList;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import java.util.Calendar;
import com.davivienda.sara.entitys.DiarioElectronico;
import java.util.Collection;
import com.davivienda.sara.base.ProcesadorArchivoDiarioElectronicoInterface;
import com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano;

public class EdcProcesadorArchivo extends ProcesadorArchivoPlano implements ProcesadorArchivoDiarioElectronicoInterface
{
    private Collection<DiarioElectronico> regs;
    private Calendar fechaTira;
    private Calendar fechaAux;
    
    public EdcProcesadorArchivo(final ArchivoPlano archivo, final Calendar fechaTira) {
        super(archivo);
        this.fechaTira = fechaTira;
    }
    
    public Collection<DiarioElectronico> getRegistrosDiarioElectronico() throws FileNotFoundException, IllegalArgumentException {
        super.getArchivo().obtenerArchivo();
        int secuencia = 1;
        Collection<String[]> regsStr = null;
        regsStr = (Collection<String[]>)super.obtenerRegistrosData();
        super.getArchivo().cerrarArchivo();
        this.regs = new LinkedList<DiarioElectronico>();
        if (regsStr != null) {
            for (final String[] strings : regsStr) {
                this.regs.add(this.aDiarioElectronico(strings, secuencia));
                ++secuencia;
            }
        }
        return this.regs;
    }
    
    public Collection<Transaccion> getRegistrosTipoTransaccion() throws FileNotFoundException, IllegalArgumentException {
        final Collection<Transaccion> regsTransaccion = new ArrayList<Transaccion>();
        if (this.regs == null) {
            this.getRegistrosDiarioElectronico();
        }
        for (final DiarioElectronico de : this.regs) {
            if (EdcTipoRegistro.getTipoRegistro(de.getTipoRegistro()).equals(EdcTipoRegistro.TRANSACTION_DATA) && de.getInformacion().indexOf("TRAN") > -1) {
                final Transaccion transaccion = this.aTransaccion(de.getInformacion());
                regsTransaccion.add(transaccion);
            }
        }
        return regsTransaccion;
    }
    
    private DiarioElectronico aDiarioElectronico(final String[] datos, final int secuencia) {
        final DiarioElectronico da = new DiarioElectronico();
        final Calendar calTmp = this.fechaTira;
        final Integer intTmp = Cadena.aInteger(datos[EdcEstructuraRegistro.SECUENCIA.orden], "0");
        final Integer codCajero = Cadena.aInteger(datos[EdcEstructuraRegistro.CODIGO_CAJERO.orden], "0");
        final DiarioElectronicoPK daPk = new DiarioElectronicoPK(codCajero, intTmp, calTmp.getTime());
        da.setDiarioElectronicoPK(daPk);
        da.setInformacion(datos[EdcEstructuraRegistro.INFORMACION.orden]);
        final Character charTmp = datos[EdcEstructuraRegistro.TIPO_REGISTRO.orden].charAt(0);
        da.setTipoRegistro(charTmp);
        da.getDiarioElectronicoPK().setSecuencia(Integer.valueOf(secuencia));
        return da;
    }
    
    private Transaccion aTransaccion(final String informacionEdc) {
        Transaccion obj = null;
        TransaccionPK objPk = null;
        final TipoTransaccionRegistroDto dto = new TipoTransaccionRegistroDto(informacionEdc);
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
        }
        else {
            super.erroresDelProceso.addAll(dto.errores);
        }
        return obj;
    }
}
