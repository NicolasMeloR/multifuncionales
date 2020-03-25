// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.servicio;

import java.util.logging.Level;
import java.util.Date;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Iterator;
import java.util.Collection;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import java.util.Calendar;
import com.davivienda.sara.entitys.Cajero;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.conceptomovimientocuadre.servicio.ConceptoMovimientoCuadreServicio;
import com.davivienda.sara.tablas.tipomovimientocuadre.servicio.TipoMovimientoCuadreServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.movimientocuadre.servicio.MovimientoCuadreServicio;
import com.davivienda.sara.base.BaseServicio;

public class ProcesoCuadreCifrasServicio extends BaseServicio
{
    MovimientoCuadreServicio movimientoCuadreServicio;
    CajeroServicio cajeroServicio;
    TipoMovimientoCuadreServicio tipoMovimientoCuadreServicio;
    ConceptoMovimientoCuadreServicio conceptoMovimientoCuadreCifrasServicio;
    private long limiteDiferencia;
    
    public ProcesoCuadreCifrasServicio(final EntityManager em) {
        this.limiteDiferencia = -1000L;
        this.movimientoCuadreServicio = new MovimientoCuadreServicio(em);
        this.cajeroServicio = new CajeroServicio(em);
        this.tipoMovimientoCuadreServicio = new TipoMovimientoCuadreServicio(em);
        this.conceptoMovimientoCuadreCifrasServicio = new ConceptoMovimientoCuadreServicio(em);
    }
    
    public void cuadrarCajeroProceso(final Cajero cajero, final Calendar fecha, final TipoMovimientoCuadre tipoMovimientoCuadreNew) throws EntityServicioExcepcion {
        final Collection<MovimientoCuadre> regs = this.movimientoCuadreServicio.getMovimientoCuadreCifras(cajero.getCodigoCajero(), fecha, fecha);
        boolean actualizar = true;
        long valorDiaSgte = -1L;
        long valorDiaSgteIdo = -1L;
        long valorPagadoIdo = 0L;
        long valordiferenciadiasgte = -1L;
        long auxvalordiferenciadiasgte = -1L;
        long donaciones = 0L;
        long dispensado = 0L;
        final long diferenciaSobrante = 0L;
        Integer tipoMovimientoCuadre = 0;
        for (final MovimientoCuadre movimientoCuadre : regs) {
            tipoMovimientoCuadre = movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre();
            com.davivienda.sara.constantes.TipoMovimientoCuadre consultaAjuste = com.davivienda.sara.constantes.TipoMovimientoCuadre.AJUSTE_AUMENTO_PROVISION;
            consultaAjuste = com.davivienda.sara.constantes.TipoMovimientoCuadre.getTipoMovimientoCuadre((int)tipoMovimientoCuadre);
            switch (consultaAjuste) {
                case PROVISION_DIA_SIGUIENTE: {
                    valorDiaSgte = movimientoCuadre.getValorMovimiento();
                    continue;
                }
                case PROVISION_DIA_SIGUIENTE_IDO: {
                    valorDiaSgteIdo = movimientoCuadre.getValorMovimiento();
                    continue;
                }
                case PAGADO_IDO: {
                    valorPagadoIdo = movimientoCuadre.getValorMovimiento();
                    continue;
                }
                case DISPENSADO: {
                    dispensado = movimientoCuadre.getValorMovimiento();
                    continue;
                }
                case DONACIONES: {
                    donaciones = movimientoCuadre.getValorMovimiento();
                    continue;
                }
            }
        }
        valordiferenciadiasgte = -1L;
        auxvalordiferenciadiasgte = -1L;
        if (valorDiaSgte == auxvalordiferenciadiasgte) {
            valordiferenciadiasgte = 0L;
        }
        else if (valorDiaSgte == 0L) {
            if (valorPagadoIdo != 0L && valorDiaSgteIdo == -1L) {
                valordiferenciadiasgte = valorPagadoIdo * -1L;
            }
            else {
                valordiferenciadiasgte = 0L;
            }
        }
        else {
            auxvalordiferenciadiasgte = valorDiaSgte - valorDiaSgteIdo;
            if (auxvalordiferenciadiasgte < this.limiteDiferencia) {
                valordiferenciadiasgte = auxvalordiferenciadiasgte;
            }
            else if (auxvalordiferenciadiasgte > 2L) {
                valordiferenciadiasgte = auxvalordiferenciadiasgte;
            }
        }
        if (valordiferenciadiasgte == 1L || valordiferenciadiasgte == -1L) {
            valordiferenciadiasgte = 0L;
        }
        actualizar = false;
        final MovimientoCuadre mcDiaSgte = new MovimientoCuadre();
        mcDiaSgte.setCajero(cajero);
        mcDiaSgte.setTipoMovimientoCuadre(tipoMovimientoCuadreNew);
        mcDiaSgte.setFecha(fecha.getTime());
        mcDiaSgte.setFechaAjuste(Fecha.getDateHoy());
        mcDiaSgte.setIdUsuario("ATM");
        mcDiaSgte.setValorMovimiento(Long.valueOf(valordiferenciadiasgte));
        this.movimientoCuadreServicio.grabarMovimientoCuadre(mcDiaSgte, actualizar);
    }
    
    public void cuadrarCajero(final Cajero cajero, final Calendar fecha) throws EntityServicioExcepcion {
        final TipoMovimientoCuadre tipoMovimientoCuadreNew = this.tipoMovimientoCuadreServicio.buscar(new Integer(com.davivienda.sara.constantes.TipoMovimientoCuadre.DIFERENCIAS.codigo));
        this.cuadrarCajeroProceso(cajero, fecha, tipoMovimientoCuadreNew);
    }
    
    public void cuadrarCajero(final Cajero cajero, final Date fecha) throws EntityServicioExcepcion {
        this.cuadrarCajero(cajero, Fecha.getCalendar(fecha));
    }
    
    public Integer cudrarCajero(final Calendar fecha) throws EntityServicioExcepcion {
        Integer regProcesado = 0;
        final Collection<Cajero> regs = this.cajeroServicio.getCajerosActivos();
        ProcesoCuadreCifrasServicio.configApp.loggerApp.log(Level.INFO, "Inicio Proceso cuadre {0}", Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"));
        for (final Cajero item : regs) {
            try {
                this.cuadrarCajero(item, fecha);
                ++regProcesado;
            }
            catch (EntityServicioExcepcion ex) {
                ProcesoCuadreCifrasServicio.configApp.loggerApp.log(Level.SEVERE, "No se procesar\u00e1 el cajero " + item.getCodigoCajero() + ex.getMessage(), ex);
            }
        }
        ProcesoCuadreCifrasServicio.configApp.loggerApp.log(Level.INFO, "Fin Proceso cuadre {0} con numero de registros: {1}", new Object[] { Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regProcesado });
        return regProcesado;
    }
}
