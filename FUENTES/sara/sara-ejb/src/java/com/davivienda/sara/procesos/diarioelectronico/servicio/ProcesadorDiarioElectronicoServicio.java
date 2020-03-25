// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.diarioelectronico.servicio;

import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import com.davivienda.sara.base.ProcesadorArchivoDiarioElectronicoTEMPInterface;
import java.util.logging.Level;
import com.davivienda.sara.entitys.TransaccionTemp;
import com.davivienda.sara.procesos.diarioelectronicotemp.filtro.edc.procesador.EdcProcesadorArchivoTemp;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.io.FileNotFoundException;
import java.util.Iterator;
import com.davivienda.sara.entitys.EdcCargue;
import java.util.Collection;
import com.davivienda.sara.base.ProcesadorArchivoDiarioElectronicoInterface;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.procesador.EdcProcesadorArchivo;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.EdcArchivo;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Calendar;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.transacciontemp.servicio.TransaccionTempServicio;
import com.davivienda.sara.tablas.diarioelectronicotemp.servicio.DiarioElectronicoTempServicio;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
import com.davivienda.sara.tablas.diarioelectronico.servicio.DiarioElectronicoServicio;
import com.davivienda.sara.tablas.edccargue.servicio.EdcCargueServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.base.BaseServicio;

public class ProcesadorDiarioElectronicoServicio extends BaseServicio
{
    private CajeroServicio cajeroServicio;
    private EdcCargueServicio edcCargueServicio;
    private DiarioElectronicoServicio diarioElectronicoServicio;
    private TransaccionServicio transaccionServicio;
    private DiarioElectronicoTempServicio diarioElectronicoTempServicio;
    private TransaccionTempServicio transaccionTempServicio;
    
    public ProcesadorDiarioElectronicoServicio(final EntityManager em) {
        super(em);
        this.cajeroServicio = new CajeroServicio(em);
        this.diarioElectronicoServicio = new DiarioElectronicoServicio(em);
        this.transaccionServicio = new TransaccionServicio(em);
        this.edcCargueServicio = new EdcCargueServicio(em);
        this.diarioElectronicoTempServicio = new DiarioElectronicoTempServicio(em);
        this.transaccionTempServicio = new TransaccionTempServicio(em);
    }
    
    public Integer cargarArchivo(final Integer codigoCajero, final Calendar fecha, final String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        if (codigoCajero == null) {
            throw new IllegalArgumentException("Debe ingresar un c\u00f3digo de cajero");
        }
        if (fecha == null || fecha.after(Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha v\u00e1lida");
        }
        Integer regsProcesados = -1;
        Integer regsNOProces = -1;
        final Cajero cajero = this.cajeroServicio.buscar(codigoCajero);
        ArchivoPlano archivo = null;
        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Se inicia el proceso de carga para el cajero " + cajero.getCodigoCajero());
        ProcesadorArchivoDiarioElectronicoInterface procesador = null;
        Collection<DiarioElectronico> regsDiarioElectronico = null;
        Collection<Transaccion> regsTransaccion = null;
        final EdcCargue edcCargue = this.edcCargueServicio.buscarPorArchivo(nombreArchivo);
        if (edcCargue.getEstadoproceso().compareTo(EstadoProceso.FINALIZADO.getEstado()) != 0) {
            edcCargue.setEstadoproceso(EstadoProceso.PROCESANDO.getEstado());
            this.edcCargueServicio.actualizar(edcCargue);
            try {
                if (cajero.getTipoLecturaEDC() == 0) {
                    String directorio = "";
                    if (cajero.getUbicacionEDC() != null && cajero.getUbicacionEDC().trim().length() > 1) {
                        directorio = cajero.getUbicacionEDC();
                    }
                    if (directorio.equals("")) {
                        directorio = ProcesadorDiarioElectronicoServicio.configApp.DIRECTORIO_UPLOAD;
                    }
                    archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);
                    procesador = new EdcProcesadorArchivo(archivo, Fecha.getCalendar(fecha, -1));
                }
                if (procesador != null) {
                    regsDiarioElectronico = procesador.getRegistrosDiarioElectronico();
                    regsTransaccion = procesador.getRegistrosTipoTransaccion();
                }
                final int secuencia = 0;
                if (regsDiarioElectronico != null) {
                    for (final DiarioElectronico diarioElectronico : regsDiarioElectronico) {
                        diarioElectronico.setCajero(cajero);
                        if (diarioElectronico.getCajero().getCodigoCajero().equals(diarioElectronico.getDiarioElectronicoPK().getCodigoCajero())) {
                            this.diarioElectronicoServicio.adicionar(diarioElectronico);
                            ++regsProcesados;
                        }
                        else {
                            ++regsNOProces;
                        }
                    }
                    if (regsNOProces > 0) {
                        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Error cargando " + regsNOProces + " REGISTROS del Cajero Codigo: " + cajero.getCodigoCajero() + " por que vienen con codigo de cajero distinto al del archivo");
                        if (edcCargue != null) {
                            edcCargue.setError(CodigoError.ALGUNOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO.getCodigo());
                            this.edcCargueServicio.actualizar(edcCargue);
                        }
                    }
                    if (regsProcesados > 0) {
                        if (regsTransaccion != null) {
                            for (final Transaccion transaccion : regsTransaccion) {
                                if (transaccion != null) {
                                    transaccion.setCajero(cajero);
                                    if (!transaccion.getCajero().getCodigoCajero().equals(transaccion.getTransaccionPK().getCodigoCajero())) {
                                        continue;
                                    }
                                    this.transaccionServicio.adicionar(transaccion);
                                }
                            }
                        }
                        edcCargue.setEstadoproceso(EstadoProceso.FINALIZADO.getEstado());
                        edcCargue.setFechaEdcCargue(Fecha.getDateHoy());
                        this.edcCargueServicio.actualizar(edcCargue);
                    }
                    else if (edcCargue != null) {
                        edcCargue.setError(CodigoError.TODOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO.getCodigo());
                        this.edcCargueServicio.actualizar(edcCargue);
                    }
                }
            }
            catch (Exception ex) {
                ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Error cargando el archivo diario electronico para el cajero :" + cajero.getCodigoCajero() + " descripcion Error : " + ex.getMessage());
            }
        }
        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Finaliza el proceso de carga para el cajero " + cajero.getCodigoCajero() + " con " + regsProcesados + " registros procesados");
        return regsProcesados;
    }
    
    public Integer cargarArchivoTemp(final Integer codigoCajero, final Calendar fecha, final String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer regsProcesados = -1;
        ArchivoPlano archivo = null;
        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Se inicia el proceso de carga en tabla temp para el cajero " + codigoCajero);
        ProcesadorArchivoDiarioElectronicoTEMPInterface procesador = null;
        Collection<TransaccionTemp> regsTransaccion = null;
        try {
            String directorio = "";
            if (directorio.equals("")) {
                directorio = ProcesadorDiarioElectronicoServicio.configApp.DIRECTORIO_UPLOAD;
            }
            archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);
            procesador = new EdcProcesadorArchivoTemp(archivo, Fecha.getCalendar(fecha, -1));
            if (procesador != null) {
                regsTransaccion = procesador.getRegistrosTipoTransaccionTemp();
            }
            if (regsTransaccion != null) {
                for (final TransaccionTemp transaccion : regsTransaccion) {
                    if (transaccion != null) {
                        this.transaccionTempServicio.adicionar(transaccion);
                        ++regsProcesados;
                    }
                }
            }
        }
        catch (Exception ex) {
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "", ex);
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Error cargando el archivo diario electronico para el cajero :" + codigoCajero + " descripcion Error : " + ex.getMessage());
        }
        return regsProcesados;
    }
    
    public void cargarCicloTempXStoreP() throws EntityServicioExcepcion {
        try {
            this.transaccionTempServicio.cargarCicloTempXStoreP();
        }
        catch (IllegalStateException ex) {
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure CARGUETRANSACCIONTEMP No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "Error al ejecutar el store procedure CARGUETRANSACCIONTEMP El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public Integer cargarArchivoSoloDiario(final Integer codigoCajero, final Calendar fecha, final String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        if (codigoCajero == null) {
            throw new IllegalArgumentException("Debe ingresar un c\u00f3digo de cajero");
        }
        if (fecha == null || fecha.after(Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha v\u00e1lida");
        }
        Integer regsProcesados = -1;
        Integer regsNOProces = -1;
        final Cajero cajero = this.cajeroServicio.buscar(codigoCajero);
        ArchivoPlano archivo = null;
        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Se inicia el proceso de carga para el cajero " + cajero.getCodigoCajero());
        ProcesadorArchivoDiarioElectronicoInterface procesador = null;
        Collection<DiarioElectronico> regsDiarioElectronico = null;
        final EdcCargue edcCargue = this.edcCargueServicio.buscarPorArchivo(nombreArchivo);
        if (edcCargue.getEstadoproceso().compareTo(EstadoProceso.FINALIZADO.getEstado()) != 0) {
            edcCargue.setEstadoproceso(EstadoProceso.PROCESANDO.getEstado());
            this.edcCargueServicio.actualizar(edcCargue);
            try {
                if (cajero.getTipoLecturaEDC() == 0) {
                    String directorio = "";
                    if (cajero.getUbicacionEDC() != null && cajero.getUbicacionEDC().trim().length() > 1) {
                        directorio = cajero.getUbicacionEDC();
                    }
                    if (directorio.equals("")) {
                        directorio = ProcesadorDiarioElectronicoServicio.configApp.DIRECTORIO_UPLOAD;
                    }
                    ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Directorio de carga para el cajero " + cajero.getCodigoCajero() + " : " + directorio);
                    archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);
                    procesador = new EdcProcesadorArchivo(archivo, Fecha.getCalendar(fecha, -1));
                }
                if (procesador != null) {
                    regsDiarioElectronico = procesador.getRegistrosDiarioElectronico();
                }
                final int secuencia = 0;
                if (regsDiarioElectronico != null) {
                    for (final DiarioElectronico diarioElectronico : regsDiarioElectronico) {
                        diarioElectronico.setCajero(cajero);
                        if (diarioElectronico.getCajero().getCodigoCajero().equals(diarioElectronico.getDiarioElectronicoPK().getCodigoCajero())) {
                            this.diarioElectronicoServicio.adicionar(diarioElectronico);
                            ++regsProcesados;
                        }
                        else {
                            ++regsNOProces;
                        }
                    }
                    if (regsNOProces > 0) {
                        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Error cargando " + regsNOProces + " REGISTROS del Cajero Codigo: " + cajero.getCodigoCajero() + " por que vienen con codigo de cajero distinto al del archivo");
                        if (edcCargue != null) {
                            edcCargue.setError(CodigoError.ALGUNOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO.getCodigo());
                            this.edcCargueServicio.actualizar(edcCargue);
                        }
                    }
                }
            }
            catch (Exception ex) {
                ProcesadorDiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "", ex);
                ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Error cargando el archivo diario electronico para el cajero :" + cajero.getCodigoCajero() + " descripcion Error : " + ex.getMessage());
            }
        }
        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Finaliza el proceso de carga para el cajero " + cajero.getCodigoCajero() + " con " + regsProcesados + " registros procesados");
        return regsProcesados;
    }
    
    public Integer cargarDiarioElectronicoTemp(final Integer codigoCajero, final Calendar fecha, final String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer regsProcesados = -1;
        final Integer regsNOProces = -1;
        ArchivoPlano archivo = null;
        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Se inicia el proceso de carga en tabla diarioelectronicotemp para el cajero " + codigoCajero);
        ProcesadorArchivoDiarioElectronicoTEMPInterface procesador = null;
        Collection<DiarioelectronicoTemp> regsDiarioElectronico = null;
        try {
            String directorio = "";
            if (directorio.equals("")) {
                directorio = ProcesadorDiarioElectronicoServicio.configApp.DIRECTORIO_UPLOAD;
            }
            archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);
            procesador = new EdcProcesadorArchivoTemp(archivo, Fecha.getCalendar(fecha, -1));
            if (procesador != null) {
                regsDiarioElectronico = procesador.getRegistrosDiarioElectronicoTemp();
            }
            int secuencia = 0;
            if (regsDiarioElectronico != null) {
                for (final DiarioelectronicoTemp diarioElectronico : regsDiarioElectronico) {
                    diarioElectronico.getDiarioelectronicoTempPK().setCodigocajero(codigoCajero);
                    secuencia = diarioElectronico.getDiarioelectronicoTempPK().getSecuencia();
                    if (secuencia < regsProcesados + 1) {
                        diarioElectronico.getDiarioelectronicoTempPK().setSecuencia(Integer.valueOf(regsProcesados + 1));
                    }
                    this.diarioElectronicoTempServicio.adicionar(diarioElectronico);
                    ++regsProcesados;
                }
            }
        }
        catch (Exception ex) {
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Error cargando el archivo diario electronico temp para el cajero :" + codigoCajero + " descripcion Error : " + ex.getMessage());
        }
        return regsProcesados;
    }
    
    public int borrarDiarioelectronicoTemp() throws EntityServicioExcepcion {
        int respuesta = 0;
        final String strQuery = "DELETE  from DiarioelectronicoTemp obj ";
        try {
            Query query = null;
            query = this.em.createQuery(strQuery);
            respuesta = query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (TransactionRequiredException ex2) {
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "no es una transaccion ", (Throwable)ex2);
            throw new EntityServicioExcepcion((Throwable)ex2);
        }
        return respuesta;
    }
    
    public int borrarTransaccionTemp() throws EntityServicioExcepcion {
        int respuesta = 0;
        final String strQuery = "DELETE   from TransaccionTemp obj ";
        try {
            Query query = null;
            query = this.em.createQuery(strQuery);
            respuesta = query.executeUpdate();
        }
        catch (IllegalStateException ex) {
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (TransactionRequiredException ex2) {
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "no es una transaccion ", (Throwable)ex2);
            throw new EntityServicioExcepcion((Throwable)ex2);
        }
        return respuesta;
    }
    
    public void cargarTransaccionesCiclo(final Integer ciclo) {
        try {
            this.transaccionServicio.copiarTablasTransaccion();
        }
        catch (EntityServicioExcepcion ex) {
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.severe("Error al grabar los datos  en tabla transaccion  para el ciclo " + ciclo + "    " + ex.getMessage());
        }
        catch (IllegalArgumentException ex2) {
            ProcesadorDiarioElectronicoServicio.configApp.loggerApp.severe("Error al grabar los datos  en tabla  transaccion  para el ciclo " + ciclo + "  " + ex2.getMessage());
        }
    }
    
    public Integer cargarArchivoCicloNuevo(final Integer codigoCajero, final Calendar fecha, final String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        Integer regsProcesados = -1;
        Integer regsNOProces = -1;
        final Cajero cajero = this.cajeroServicio.buscar(codigoCajero);
        ArchivoPlano archivo = null;
        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Se inicia el proceso de carga para el cajero " + cajero.getCodigoCajero());
        ProcesadorArchivoDiarioElectronicoInterface procesador = null;
        Collection<DiarioElectronico> regsDiarioElectronico = null;
        final EdcCargue edcCargue = this.edcCargueServicio.buscarPorArchivo(nombreArchivo);
        if (edcCargue.getEstadoproceso().compareTo(EstadoProceso.FINALIZADO.getEstado()) != 0) {
            edcCargue.setEstadoproceso(EstadoProceso.PROCESANDO.getEstado());
            this.edcCargueServicio.actualizar(edcCargue);
            try {
                String directorio = "";
                if (cajero.getUbicacionEDC() != null && cajero.getUbicacionEDC().trim().length() > 1) {
                    directorio = cajero.getUbicacionEDC();
                }
                if (directorio.equals("")) {
                    directorio = ProcesadorDiarioElectronicoServicio.configApp.DIRECTORIO_UPLOAD;
                }
                archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);
                procesador = new EdcProcesadorArchivo(archivo, Fecha.getCalendar(fecha, -1));
                if (procesador != null) {
                    regsDiarioElectronico = procesador.getRegistrosDiarioElectronico();
                }
                int secuencia = 0;
                if (regsDiarioElectronico != null) {
                    for (final DiarioElectronico diarioElectronico : regsDiarioElectronico) {
                        diarioElectronico.setCajero(cajero);
                        secuencia = diarioElectronico.getDiarioElectronicoPK().getSecuencia();
                        if (diarioElectronico.getCajero().getCodigoCajero().equals(diarioElectronico.getDiarioElectronicoPK().getCodigoCajero())) {
                            if (secuencia < regsProcesados + 1) {
                                diarioElectronico.getDiarioElectronicoPK().setSecuencia(Integer.valueOf(regsProcesados + 1));
                            }
                            this.diarioElectronicoServicio.adicionar(diarioElectronico);
                            ++regsProcesados;
                        }
                        else {
                            ++regsNOProces;
                        }
                    }
                    if (regsNOProces > 0) {
                        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Error cargando " + regsNOProces + " REGISTROS del Cajero Codigo: " + cajero.getCodigoCajero() + " por que vienen con codigo de cajero distinto al del archivo");
                        if (edcCargue != null) {
                            edcCargue.setError(CodigoError.ALGUNOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO.getCodigo());
                            this.edcCargueServicio.actualizar(edcCargue);
                        }
                    }
                    if (regsProcesados <= 0 && edcCargue != null) {
                        edcCargue.setError(CodigoError.TODOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO.getCodigo());
                        this.edcCargueServicio.actualizar(edcCargue);
                    }
                }
            }
            catch (Exception ex) {
                ProcesadorDiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "", ex);
                ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Error cargando el archivo diario electronico para el cajero :" + cajero.getCodigoCajero() + " descripcion Error : " + ex.getMessage());
            }
        }
        ProcesadorDiarioElectronicoServicio.configApp.loggerApp.info("Finaliza el proceso de carga para el cajero " + cajero.getCodigoCajero() + " con " + regsProcesados + " registros procesados");
        return regsProcesados;
    }
}
