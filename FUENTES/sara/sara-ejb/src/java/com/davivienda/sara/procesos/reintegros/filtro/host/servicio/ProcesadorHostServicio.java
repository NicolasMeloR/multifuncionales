// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.reintegros.filtro.host.servicio;

import java.io.IOException;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.io.FileNotFoundException;
import java.util.Iterator;
import com.davivienda.sara.base.ProcesadorArchivoHostInterface;
import java.util.Collection;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.HostAtm;
import com.davivienda.sara.procesos.reintegros.filtro.host.procesador.HostProcesadorArchivo;
import com.davivienda.sara.procesos.reintegros.filtro.host.estructura.HostArchivo;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Calendar;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.hostatm.servicio.HostAtmServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.base.BaseServicio;

public class ProcesadorHostServicio extends BaseServicio
{
    private CajeroServicio cajeroServicio;
    private HostAtmServicio hostAtmServicio;
    
    public ProcesadorHostServicio(final EntityManager em) {
        super(em);
        this.hostAtmServicio = new HostAtmServicio(em);
        this.cajeroServicio = new CajeroServicio(em);
    }
    
    public Integer cargarArchivoHostAnterior(final Calendar fecha) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        if (fecha == null || fecha.after(Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha v\u00e1lida");
        }
        Integer regsProcesados = -1;
        Integer regsNOProces = -1;
        ArchivoPlano archivo = null;
        Collection<HostAtm> regsHostAtm = null;
        ProcesadorArchivoHostInterface procesador = null;
        String directorio = "";
        if (directorio.equals("")) {
            directorio = ProcesadorHostServicio.configApp.DIRECTORIO_CUADRE;
        }
        archivo = new HostArchivo(fecha, directorio);
        procesador = new HostProcesadorArchivo(archivo);
        ProcesadorHostServicio.configApp.loggerApp.info("Inicia el proceso de carga para el archivo host : " + archivo.getNombreArchivo() + "  a las " + Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"));
        if (procesador != null) {
            regsHostAtm = procesador.getRegistrosHost();
        }
        if (regsHostAtm != null) {
            for (final HostAtm regHost : regsHostAtm) {
                final Cajero cajero = this.cajeroServicio.buscar(regHost.getCajero().getCodigoCajero());
                if (cajero != null) {
                    this.hostAtmServicio.adicionar(regHost);
                    ++regsProcesados;
                }
                else {
                    ++regsNOProces;
                }
            }
        }
        ProcesadorHostServicio.configApp.loggerApp.info("Finaliza el proceso de carga para el archivo host : " + archivo.getNombreArchivo() + " con " + regsProcesados + " registros procesados a las " + Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"));
        return regsProcesados;
    }
    
    public Integer cargarArchivoHost(final Calendar fecha, final int numRegistros) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException, IOException {
        if (fecha == null || fecha.after(Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha v\u00e1lida");
        }
        Integer regsProcesados = -1;
        Integer regsNOProces = -1;
        ArchivoPlano archivo = null;
        Collection<HostAtm> regsHostAtm = null;
        ProcesadorArchivoHostInterface procesador = null;
        String directorio = "";
        if (directorio.equals("")) {
            directorio = ProcesadorHostServicio.configApp.DIRECTORIO_CUADRE;
        }
        archivo = new HostArchivo(fecha, directorio);
        procesador = new HostProcesadorArchivo(archivo);
        ProcesadorHostServicio.configApp.loggerApp.info("Inicia el proceso de cargar para el archivo host : " + archivo.getNombreArchivo() + " para  " + (numRegistros + 20000) + " registros a las " + Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"));
        regsHostAtm = procesador.getRegistrosHostXFilas(numRegistros, 20000);
        if (regsHostAtm != null) {
            for (final HostAtm regHost : regsHostAtm) {
                final Cajero cajero = this.cajeroServicio.buscar(regHost.getCajero().getCodigoCajero());
                if (cajero != null) {
                    this.hostAtmServicio.adicionar(regHost);
                    ++regsProcesados;
                }
                else {
                    ++regsNOProces;
                }
            }
        }
        ProcesadorHostServicio.configApp.loggerApp.info("Finaliza el proceso de cargar para el archivo host : " + archivo.getNombreArchivo() + " " + (numRegistros + 20000) + " registros procesados a las " + Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"));
        return regsProcesados;
    }
    
    public int cuentaRegistros(final Calendar fecha) throws FileNotFoundException, IOException {
        ArchivoPlano archivo = null;
        int numRegistros = 0;
        String directorio = "";
        if (directorio.equals("")) {
            directorio = ProcesadorHostServicio.configApp.DIRECTORIO_CUADRE;
        }
        archivo = new HostArchivo(fecha, directorio);
        numRegistros = archivo.cuentaRegistros();
        return numRegistros;
    }
}
