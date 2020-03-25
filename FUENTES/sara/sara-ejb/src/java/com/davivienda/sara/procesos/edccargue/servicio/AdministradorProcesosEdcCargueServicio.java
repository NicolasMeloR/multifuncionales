// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.edccargue.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.io.FileNotFoundException;
import java.util.logging.Logger;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.entitys.Cajero;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import com.davivienda.sara.tablas.edccargue.servicio.EdcCargueServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.base.BaseServicio;

public class AdministradorProcesosEdcCargueServicio extends BaseServicio
{
    private CajeroServicio cajeroServicio;
    private EdcCargueServicio edcCargueServicio;
    @Resource
    private UserTransaction utx;
    
    public AdministradorProcesosEdcCargueServicio(final EntityManager em) {
        super(em);
        this.cajeroServicio = new CajeroServicio(em);
        this.edcCargueServicio = new EdcCargueServicio(em);
    }
    
    public Integer guardarProcesoTransmisionTiras(final String nombreArchivo, final Integer tamano, final Integer ciclo, final Date fecha, final Integer codigoCajero, final Integer estado, final boolean actualiza) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer codigoErrorProceso = 0;
        final Cajero cajero = this.cajeroServicio.buscar(codigoCajero);
        Integer regGuardados = 0;
        final EdcCargue regProcesoTransmisionTira = new EdcCargue();
        if (cajero == null) {
            codigoErrorProceso = CodigoError.CAJERO_NO_EXISTE.getCodigo();
        }
        regProcesoTransmisionTira.setCodigoCajero(codigoCajero);
        regProcesoTransmisionTira.setCiclo(ciclo);
        regProcesoTransmisionTira.setError(codigoErrorProceso);
        regProcesoTransmisionTira.setEstadoproceso(estado);
        regProcesoTransmisionTira.setFechaEdcCargue(fecha);
        regProcesoTransmisionTira.setNombrearchivo(nombreArchivo);
        regProcesoTransmisionTira.setTamano(tamano);
        regProcesoTransmisionTira.setUltimaSecuencia(Integer.valueOf(0));
        regProcesoTransmisionTira.setVersion(Integer.valueOf(0));
        try {
            final EdcCargue edcCargue = this.edcCargueServicio.buscarPorArchivo(nombreArchivo);
            if (edcCargue == null) {
                this.edcCargueServicio.adicionar(regProcesoTransmisionTira);
                regGuardados = 1;
            }
            else if (actualiza) {
                edcCargue.setEstadoproceso(estado);
                this.edcCargueServicio.actualizar(edcCargue);
            }
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error cargando en EDCCARGUE registro datos archivos  :" + nombreArchivo + " descripcion Error : " + ex.getMessage());
        }
        return regGuardados;
    }
}
