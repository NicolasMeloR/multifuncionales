// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.corte.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.io.FileNotFoundException;
import com.davivienda.sara.entitys.MovimientoCuadre;
import java.util.Iterator;
import java.util.Collection;
import com.davivienda.sara.base.ProcesadorArchivoCorteInterface;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.sara.constantes.TipoMovimientoCuadre;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.BilletajeCajero;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;
import com.davivienda.sara.procesos.cuadrecifras.filtro.corte.procesador.CorteProcesadorArchivo;
import com.davivienda.sara.procesos.cuadrecifras.filtro.corte.estructura.CorteArchivo;
import java.util.Calendar;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.movimientocuadre.servicio.MovimientoCuadreServicio;
import com.davivienda.sara.tablas.billetajecajero.servicio.BilletajeCajeroServicio;
import com.davivienda.sara.base.BaseServicio;

public class ProcesadorCorteArchivoServicio extends BaseServicio
{
    private BilletajeCajeroServicio billetajeCajeroServicio;
    private MovimientoCuadreServicio movimientoCuadreServicio;
    
    public ProcesadorCorteArchivoServicio(final EntityManager em) {
        super(em);
        this.billetajeCajeroServicio = new BilletajeCajeroServicio(em);
        this.movimientoCuadreServicio = new MovimientoCuadreServicio(em);
    }
    
    public Integer cargarArchivoCorte(final Calendar fecha) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer regsProcesados = -1;
        ArchivoPlano archivo = null;
        ProcesadorArchivoCorteInterface procesador = null;
        Collection<BilletajeCajero> regsBilletajeCajero = null;
        String directorio = "";
        directorio = ProcesadorCorteArchivoServicio.configApp.DIRECTORIO_CUADRE;
        archivo = new CorteArchivo(fecha, directorio);
        procesador = new CorteProcesadorArchivo(archivo);
        ProcesadorCorteArchivoServicio.configApp.loggerApp.log(Level.INFO, "ProcesadorCorteArchivoServicio -- directorio: " + directorio + " -- archivo: " + archivo);
        if (procesador != null) {
            regsBilletajeCajero = procesador.getRegistrosBilletajeCajero();
        }
        if (regsBilletajeCajero != null) {
            ProcesadorCorteArchivoServicio.configApp.loggerApp.log(Level.INFO, "Inicia el proceso de carga para el archivo {0} con fecha {1} a las: {2} con {3} registros leidos", new Object[] { archivo.getNombreArchivo(), Fecha.aCadena(fecha, "yyyy/MM/dd"), Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regsBilletajeCajero.size() });
            for (final BilletajeCajero billetajeCajero : regsBilletajeCajero) {
                try {
                    final Cajero cajero = (Cajero)this.em.find((Class)new Cajero().getClass(), (Object)billetajeCajero.getCajero().getCodigoCajero());
                    if (cajero != null) {
                        billetajeCajero.setCajero(cajero);
                        final BilletajeCajero existeBilletaje = this.billetajeCajeroServicio.buscar(billetajeCajero.getBilletajeCajeroPK());
                        if (existeBilletaje == null) {
                            this.billetajeCajeroServicio.adicionar(billetajeCajero);
                            ++regsProcesados;
                            final MovimientoCuadre mcDispensado = this.obtenerMovimientoCuadreLinea(TipoMovimientoCuadre.DISPENSADO, billetajeCajero);
                            this.movimientoCuadreServicio.adicionar(mcDispensado);
                            final MovimientoCuadre mcProvDiaAnt = this.obtenerMovimientoCuadreLinea(TipoMovimientoCuadre.PROVISION_DIA_ANTERIOR, billetajeCajero);
                            this.movimientoCuadreServicio.adicionar(mcProvDiaAnt);
                            final MovimientoCuadre mcProvDiaSte = this.obtenerMovimientoCuadreLinea(TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE, billetajeCajero);
                            this.movimientoCuadreServicio.adicionar(mcProvDiaSte);
                        }
                        else {
                            ProcesadorCorteArchivoServicio.configApp.loggerApp.log(Level.INFO, "El Registro BilletajeCajero {0} y fecha: {1} existe en base de datos, no se guardara.", new Object[] { existeBilletaje.getBilletajeCajeroPK().getCodigoCajero(), existeBilletaje.getBilletajeCajeroPK().getFecha() });
                        }
                    }
                    else {
                        ProcesadorCorteArchivoServicio.configApp.loggerApp.log(Level.INFO, "en Registro {0} No existe el cajero", regsProcesados + 1);
                    }
                }
                catch (Exception e) {
                    ProcesadorCorteArchivoServicio.configApp.loggerApp.log(Level.WARNING, "Error: {0}", e.getMessage());
                }
            }
            ProcesadorCorteArchivoServicio.configApp.loggerApp.log(Level.INFO, "Finaliza el proceso de carga para el archivo de corte diario del {0} a las: {1} con {2} registros procesados", new Object[] { Fecha.aCadena(fecha, "yyyy/MM/dd"), Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regsProcesados });
        }
        else {
            ProcesadorCorteArchivoServicio.configApp.loggerApp.log(Level.INFO, "RegsBilletajeCajero esta nulo");
        }
        return regsProcesados;
    }
    
    private MovimientoCuadre obtenerMovimientoCuadreLinea(final TipoMovimientoCuadre tm, final BilletajeCajero bc) {
        final com.davivienda.sara.entitys.TipoMovimientoCuadre tmc = (com.davivienda.sara.entitys.TipoMovimientoCuadre)this.em.find((Class)new com.davivienda.sara.entitys.TipoMovimientoCuadre().getClass(), (Object)tm.codigo);
        MovimientoCuadre mc = null;
        if (tmc != null) {
            mc = new MovimientoCuadre();
            mc.setCajero(bc.getCajero());
            mc.setDenominacion1(Short.valueOf(bc.getDenominacion1().shortValue()));
            mc.setDenominacion2(Short.valueOf(bc.getDenominacion2().shortValue()));
            mc.setDenominacion3(Short.valueOf(bc.getDenominacion3().shortValue()));
            mc.setDenominacion4(Short.valueOf(bc.getDenominacion4().shortValue()));
            mc.setDenominacion5(Short.valueOf(bc.getDenominacion5().shortValue()));
            mc.setDenominacionUf(Short.valueOf(bc.getDenominacionUf().shortValue()));
            mc.setFecha(bc.getBilletajeCajeroPK().getFecha());
            mc.setTipoMovimientoCuadre(tmc);
            mc.setIdUsuario("ATM");
            long valor = 0L;
            if (tm == TipoMovimientoCuadre.DISPENSADO) {
                mc.setBilletes1(bc.getDispensado1());
                mc.setBilletes2(bc.getDispensado2());
                mc.setBilletes3(bc.getDispensado3());
                mc.setBilletes4(bc.getDispensado4());
                mc.setBilletes5(bc.getDispensado5());
                mc.setBilletesUf(bc.getDispensadoUf());
                valor = bc.getValAvan() + bc.getValRetDav() + bc.getValRetRed();
            }
            if (tm == TipoMovimientoCuadre.PROVISION_DIA_ANTERIOR) {
                mc.setBilletes1(bc.getTotal1());
                mc.setBilletes2(bc.getTotal2());
                mc.setBilletes3(bc.getTotal3());
                mc.setBilletes4(bc.getTotal4());
                mc.setBilletes5(bc.getTotal5());
                mc.setBilletesUf(bc.getTotalUf());
            }
            if (tm == TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE) {
                mc.setBilletes1(bc.getCaja1());
                mc.setBilletes2(bc.getCaja2());
                mc.setBilletes3(bc.getCaja3());
                mc.setBilletes4(bc.getCaja4());
                mc.setBilletes5(bc.getCaja5());
                mc.setBilletesUf(bc.getCajaUf());
            }
            if (valor == 0L) {
                valor = (mc.getDenominacion1() * mc.getBilletes1() + mc.getDenominacion2() * mc.getBilletes2() + mc.getDenominacion3() * mc.getBilletes3() + mc.getDenominacion4() * mc.getBilletes4() + mc.getDenominacion5() * mc.getBilletes5() + mc.getDenominacionUf() * mc.getBilletesUf()) * 1000;
            }
            mc.setValorMovimiento(Long.valueOf(valor));
        }
        return mc;
    }
}
