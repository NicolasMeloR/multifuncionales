// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.servicio;

import com.davivienda.sara.constantes.TipoMovimientoCuadre;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.io.FileNotFoundException;
import com.davivienda.sara.entitys.MovimientoCuadre;
import java.util.Iterator;
import java.util.Collection;
import com.davivienda.sara.base.ProcesadorArchivoTotalesEstacionInterface;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;
import com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.procesador.TotalesEstacionProcesadorArchivo;
import com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.estructura.TotalesEstacionArchivo;
import com.davivienda.sara.entitys.TotalesEstacion;
import java.util.ArrayList;
import java.util.Calendar;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.movimientocuadre.servicio.MovimientoCuadreServicio;
import com.davivienda.sara.tablas.totalesestacion.servicio.TotalesEstacionServicio;
import com.davivienda.sara.base.BaseServicio;

public class ProcesadorTotalesEstacionArchivoServicio extends BaseServicio
{
    private TotalesEstacionServicio totalesEstacionServicio;
    private MovimientoCuadreServicio movimientoCuadreServicio;
    
    public ProcesadorTotalesEstacionArchivoServicio(final EntityManager em) {
        super(em);
        this.totalesEstacionServicio = new TotalesEstacionServicio(em);
        this.movimientoCuadreServicio = new MovimientoCuadreServicio(em);
    }
    
    public Integer cargarArchivoTotalesEstacion(final Calendar fecha) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer regsProcesados = -1;
        ArchivoPlano archivo = null;
        ProcesadorArchivoTotalesEstacionInterface procesador = null;
        Collection<TotalesEstacion> regsTotalesEstacion = null;
        final ArrayList<TotalesEstacion> regsTotalesEstacionCompara = new ArrayList<TotalesEstacion>();
        final String directorio = ProcesadorTotalesEstacionArchivoServicio.configApp.DIRECTORIO_CUADRE;
        archivo = new TotalesEstacionArchivo(fecha, directorio);
        procesador = new TotalesEstacionProcesadorArchivo(archivo, fecha);
        if (procesador != null) {
            regsTotalesEstacion = procesador.getRegistrosTotalesEstacion();
        }
        if (regsTotalesEstacion != null) {
            ProcesadorTotalesEstacionArchivoServicio.configApp.loggerApp.log(Level.INFO, "Inicia el proceso de carga para el archivo {0} con fecha {1} a las: {2} con {3} registros leidos", new Object[] { archivo.getNombreArchivo(), Fecha.aCadena(fecha, "yyyy/MM/dd"), Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regsTotalesEstacion.size() });
            for (final TotalesEstacion totalesEstacion : regsTotalesEstacion) {
                try {
                    final Cajero cajero = (Cajero)this.em.find((Class)new Cajero().getClass(), (Object)totalesEstacion.getCajero().getCodigoCajero());
                    if (cajero != null) {
                        totalesEstacion.setCajero(cajero);
                        try {
                            if (!this.buscarRegistro(regsTotalesEstacionCompara, totalesEstacion)) {
                                final TotalesEstacion existeTotales = this.totalesEstacionServicio.buscar(totalesEstacion.getTotalesEstacionPK());
                                if (existeTotales == null) {
                                    final MovimientoCuadre mc = this.obtenerMovimientoCuadreLinea(totalesEstacion);
                                    if (mc == null) {
                                        continue;
                                    }
                                    this.totalesEstacionServicio.adicionar(totalesEstacion);
                                    ++regsProcesados;
                                    this.movimientoCuadreServicio.adicionar(mc);
                                    TotalesEstacion regprov = new TotalesEstacion();
                                    regprov = totalesEstacion;
                                    regsTotalesEstacionCompara.add(regprov);
                                }
                                else {
                                    ProcesadorTotalesEstacionArchivoServicio.configApp.loggerApp.log(Level.INFO, "Registro TotalesEstacion  {0} ya se encuentra en base de datos, no se cargara.", totalesEstacion);
                                }
                            }
                            else {
                                ProcesadorTotalesEstacionArchivoServicio.configApp.loggerApp.info("Registro Repetido " + totalesEstacion.getCajero());
                            }
                        }
                        catch (Exception ex) {
                            ProcesadorTotalesEstacionArchivoServicio.configApp.loggerApp.log(Level.INFO, "error en procesador totales estacion {0}", ex.getMessage());
                        }
                    }
                    else {
                        ProcesadorTotalesEstacionArchivoServicio.configApp.loggerApp.log(Level.INFO, "en Registro {0} No existe el cajero : {1}", new Object[] { regsProcesados + 1, totalesEstacion.getCajero().getCodigoCajero().toString() });
                    }
                }
                catch (Exception e) {
                    ProcesadorTotalesEstacionArchivoServicio.configApp.loggerApp.log(Level.WARNING, "Error: {0}", e.getMessage());
                }
            }
            ProcesadorTotalesEstacionArchivoServicio.configApp.loggerApp.log(Level.INFO, "Finaliza el proceso de carga para el archivo de totales estacion  del {0} a las: {1} con {2} registros procesados", new Object[] { Fecha.aCadena(fecha, "yyyy/MM/dd"), Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regsProcesados });
        }
        else {
            ProcesadorTotalesEstacionArchivoServicio.configApp.loggerApp.log(Level.INFO, "TotalesEstacion esta nulo");
        }
        return regsProcesados;
    }
    
    private boolean buscarRegistro(final ArrayList<TotalesEstacion> regsProvisionHostCompara, final TotalesEstacion regProvisionHost) {
        boolean buscaReg = false;
        if (regsProvisionHostCompara != null) {
            final Iterator iterador = regsProvisionHostCompara.listIterator();
            while (iterador.hasNext()) {
                final TotalesEstacion totalesEstacion = (TotalesEstacion) iterador.next();
                if (totalesEstacion.getTotalesEstacionPK().equals((Object)regProvisionHost.getTotalesEstacionPK())) {
                    buscaReg = true;
                }
            }
        }
        return buscaReg;
    }
    
    private MovimientoCuadre obtenerMovimientoCuadreLinea(final TotalesEstacion te) throws Exception {
        TipoMovimientoCuadre tm = null;
        MovimientoCuadre mc = new MovimientoCuadre();
        if (te.getTotalesEstacionPK().getCodigoTotal() == 36) {
            tm = TipoMovimientoCuadre.PAGADO_IDO;
        }
        if (te.getTotalesEstacionPK().getCodigoTotal() == 98) {
            tm = TipoMovimientoCuadre.DONACIONES;
        }
        if (te.getTotalesEstacionPK().getCodigoTotal() == 3) {
            tm = TipoMovimientoCuadre.PROVISION_AYER;
        }
        if (tm != null) {
            final com.davivienda.sara.entitys.TipoMovimientoCuadre tmc = (com.davivienda.sara.entitys.TipoMovimientoCuadre)this.em.find((Class)new com.davivienda.sara.entitys.TipoMovimientoCuadre().getClass(), (Object)tm.codigo);
            if (tmc != null) {
                mc.setCajero(te.getCajero());
                mc.setFecha(te.getTotalesEstacionPK().getFecha());
                mc.setTipoMovimientoCuadre(tmc);
                mc.setValorMovimiento(te.getValorevento());
                mc.setIdUsuario("HOST");
            }
            else {
                mc = null;
            }
        }
        else {
            mc = null;
        }
        return mc;
    }
}
