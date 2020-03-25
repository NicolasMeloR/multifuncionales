// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.io.FileNotFoundException;
import com.davivienda.sara.entitys.MovimientoCuadre;
import java.util.Iterator;
import com.davivienda.sara.entitys.ProvisionHostPK;
import java.util.Collection;
import com.davivienda.sara.base.ProcesadorArchivoProvisionesInterface;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.sara.constantes.TipoMovimientoCuadre;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;
import com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.procesador.ProvisionesProcesadorArchivo;
import com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.estructura.ProvisionesArchivo;
import com.davivienda.sara.entitys.ProvisionHost;
import java.util.ArrayList;
import java.util.Calendar;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.movimientocuadre.servicio.MovimientoCuadreServicio;
import com.davivienda.sara.tablas.provisionhost.servicio.ProvisionHostServicio;
import com.davivienda.sara.base.BaseServicio;

public class ProcesadorProvisionesArchivoServicio extends BaseServicio
{
    private ProvisionHostServicio provisionHostServicio;
    private MovimientoCuadreServicio movimientoCuadreServicio;
    
    public ProcesadorProvisionesArchivoServicio(final EntityManager em) {
        super(em);
        this.provisionHostServicio = new ProvisionHostServicio(em);
        this.movimientoCuadreServicio = new MovimientoCuadreServicio(em);
    }
    
    public Integer cargarArchivoProvisiones(final Calendar fecha) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer regsProcesados = -1;
        try {
            ArchivoPlano archivo = null;
            ProcesadorArchivoProvisionesInterface procesador = null;
            Collection<ProvisionHost> regsProvisionHost = null;
            final ArrayList<ProvisionHost> regsProvisionHostCompara = new ArrayList<ProvisionHost>();
            ProvisionHostPK ProvisionHostPKfecha = null;
            final String directorio = ProcesadorProvisionesArchivoServicio.configApp.DIRECTORIO_CUADRE;
            archivo = new ProvisionesArchivo(fecha, directorio);
            procesador = new ProvisionesProcesadorArchivo(archivo);
            if (procesador != null) {
                regsProvisionHost = procesador.getRegistrosProvisiones();
            }
            if (regsProvisionHost != null) {
                ProcesadorProvisionesArchivoServicio.configApp.loggerApp.log(Level.INFO, "Inicia el proceso de carga para el archivo {0} con fecha {1} a las: {2} con {3} registros leidos", new Object[] { archivo.getNombreArchivo(), Fecha.aCadena(fecha, "yyyy/MM/dd"), Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regsProvisionHost.size() });
                for (final ProvisionHost provisionHost : regsProvisionHost) {
                    try {
                        if (provisionHost == null) {
                            continue;
                        }
                        final Cajero cajero = (Cajero)this.em.find((Class)new Cajero().getClass(), (Object)provisionHost.getCajero().getCodigoCajero());
                        if (cajero != null) {
                            provisionHost.setCajero(cajero);
                            if (!this.buscarRegistro(regsProvisionHostCompara, provisionHost)) {
                                final MovimientoCuadre mc = this.obtenerMovimientoCuadreLinea(provisionHost);
                                if (mc == null) {
                                    continue;
                                }
                                if (mc.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.AUMENTO_PROVISION_LINEA.codigo) || mc.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.DISMINUCION_PROVISION_LINEA.codigo)) {
                                    ProvisionHostPKfecha = provisionHost.getProvisionHostPK();
                                    ProvisionHostPKfecha.setFecha(mc.getFecha());
                                    provisionHost.setProvisionHostPK(ProvisionHostPKfecha);
                                }
                                ProcesadorProvisionesArchivoServicio.configApp.loggerApp.info("Registro ProvisionHost  codigo:" + provisionHost.getProvisionHostPK().getCodigoCajero() + " fecha: " + provisionHost.getProvisionHostPK().getFecha() + " hora: " + provisionHost.getProvisionHostPK().getHora() + " motivo: " + provisionHost.getProvisionHostPK().getMotivo() + " tipo: " + provisionHost.getProvisionHostPK().getTipo() + " valor: " + provisionHost.getValor());
                                final ProvisionHost existeProvision = this.provisionHostServicio.buscar(provisionHost.getProvisionHostPK());
                                if (existeProvision == null) {
                                    this.provisionHostServicio.grabarProvisionHost(provisionHost);
                                    ++regsProcesados;
                                    this.movimientoCuadreServicio.adicionar(mc);
                                    ProvisionHost regprov = new ProvisionHost();
                                    regprov = provisionHost;
                                    regsProvisionHostCompara.add(regprov);
                                }
                                else {
                                    ProcesadorProvisionesArchivoServicio.configApp.loggerApp.info("Registro ProvisionHost  " + provisionHost + " ya se encuentra en base de datos, no se cargara.");
                                }
                            }
                            else {
                                ProcesadorProvisionesArchivoServicio.configApp.loggerApp.info("Registro Repetido " + provisionHost.getCajero());
                            }
                        }
                        else {
                            ProcesadorProvisionesArchivoServicio.configApp.loggerApp.info("en Registro " + (regsProcesados + 1) + " No existe el cajero : " + provisionHost.getCajero().getCodigoCajero().toString());
                        }
                    }
                    catch (Exception ex) {
                        ProcesadorProvisionesArchivoServicio.configApp.loggerApp.log(Level.WARNING, "Error: {0}", ex.getMessage());
                    }
                }
                ProcesadorProvisionesArchivoServicio.configApp.loggerApp.info("Finaliza el proceso de carga para el archivo de provisiones del " + Fecha.aCadena(fecha, "yyyy/MM/dd") + " a las: " + Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss") + " con " + regsProcesados + " registros procesados");
            }
            else {
                ProcesadorProvisionesArchivoServicio.configApp.loggerApp.log(Level.INFO, "ProvisionHost esta nulo");
            }
        }
        catch (Exception ex2) {
            ProcesadorProvisionesArchivoServicio.configApp.loggerApp.info("Error cargando el archivo provisiones  descripcion Error : " + ex2.getMessage());
        }
        return regsProcesados;
    }
    
    private boolean buscarRegistro(final ArrayList<ProvisionHost> regsProvisionHostCompara, final ProvisionHost regProvisionHost) {
        boolean buscaReg = false;
        if (regsProvisionHostCompara != null) {
            final Iterator iterador = regsProvisionHostCompara.listIterator();
            while (iterador.hasNext()) {
                final ProvisionHost provisionHost = (ProvisionHost) iterador.next();
                if (provisionHost.getProvisionHostPK().equals((Object)regProvisionHost.getProvisionHostPK())) {
                    buscaReg = true;
                }
            }
        }
        return buscaReg;
    }
    
    private MovimientoCuadre obtenerMovimientoCuadreLinea(final ProvisionHost ph) {
        TipoMovimientoCuadre tm = null;
        MovimientoCuadre mc = new MovimientoCuadre();
        mc.setIdUsuario("HOST");
        if (ph.getProvisionHostPK().getMotivo() == 75 && ph.getProvisionHostPK().getTipo() == 99) {
            tm = TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE_IDO;
        }
        if (ph.getProvisionHostPK().getMotivo() == 70 && ph.getProvisionHostPK().getTipo() == 98) {
            tm = TipoMovimientoCuadre.AUMENTO_PROVISION_LINEA;
        }
        if (ph.getProvisionHostPK().getMotivo() == 70 && ph.getProvisionHostPK().getTipo() == 99) {
            tm = TipoMovimientoCuadre.DISMINUCION_PROVISION_LINEA;
        }
        if (ph.getProvisionHostPK().getMotivo() == 112 && ph.getProvisionHostPK().getTipo() == 98) {
            tm = TipoMovimientoCuadre.AUMENTO_PROVISION_IDO;
        }
        if (ph.getProvisionHostPK().getMotivo() == 112 && ph.getProvisionHostPK().getTipo() == 99) {
            tm = TipoMovimientoCuadre.DISMINUCION_PROVISION_IDO;
        }
        if (ph.getProvisionHostPK().getMotivo() == 16 && ph.getProvisionHostPK().getTipo() == 98 && ph.getUsuarioHost().trim().length() == 0) {
            tm = TipoMovimientoCuadre.SOBRANTE_IDO;
        }
        if (ph.getProvisionHostPK().getMotivo() == 16 && ph.getProvisionHostPK().getTipo() == 98 && ph.getUsuarioHost().trim().length() > 0) {
            tm = TipoMovimientoCuadre.SOBRANTE_MANUAL;
            mc.setIdUsuario(ph.getUsuarioHost());
        }
        if (ph.getProvisionHostPK().getMotivo() == 35 && ph.getProvisionHostPK().getTipo() == 99 && ph.getUsuarioHost().trim().length() == 0) {
            tm = TipoMovimientoCuadre.FALTANTE_IDO;
        }
        if (ph.getProvisionHostPK().getMotivo() == 35 && ph.getProvisionHostPK().getTipo() == 99 && ph.getUsuarioHost().trim().length() > 0) {
            tm = TipoMovimientoCuadre.FALTANTE_MANUAL;
            mc.setIdUsuario(ph.getUsuarioHost());
        }
        if (tm != null) {
            final com.davivienda.sara.entitys.TipoMovimientoCuadre tmc = (com.davivienda.sara.entitys.TipoMovimientoCuadre)this.em.find((Class)new com.davivienda.sara.entitys.TipoMovimientoCuadre().getClass(), (Object)tm.codigo);
            if (tmc != null) {
                mc.setCajero(ph.getCajero());
                if (tm == TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE_IDO) {
                    mc.setFecha(Fecha.getDate(ph.getProvisionHostPK().getFecha(), -1));
                }
                else if (tm.equals((Object)TipoMovimientoCuadre.AUMENTO_PROVISION_LINEA) || tm.equals((Object)TipoMovimientoCuadre.DISMINUCION_PROVISION_LINEA)) {
                    mc.setFecha(Fecha.getDate(ph.getProvisionHostPK().getFecha(), -1));
                }
                else {
                    mc.setFecha(ph.getProvisionHostPK().getFecha());
                }
                mc.setTipoMovimientoCuadre(tmc);
                mc.setValorMovimiento(Long.valueOf(ph.getValor()));
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
