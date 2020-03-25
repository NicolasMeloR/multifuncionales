package com.davivienda.sara.procesos.diarioelectronico.servicio;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.base.ProcesadorArchivoDiarioElectronicoInterface;
import com.davivienda.sara.base.ProcesadorArchivoDiarioElectronicoTEMPInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.entitys.TransaccionTemp;
import com.davivienda.sara.entitys.DiarioelectronicoTemp;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.EdcArchivo;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.procesador.EdcProcesadorArchivo;
import com.davivienda.sara.procesos.diarioelectronicotemp.filtro.edc.procesador.EdcProcesadorArchivoTemp;
import com.davivienda.sara.tablas.diarioelectronicotemp.servicio.DiarioElectronicoTempServicio;
import com.davivienda.sara.tablas.transacciontemp.servicio.TransaccionTempServicio;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;

import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.diarioelectronico.servicio.DiarioElectronicoServicio;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.EntityManager;

import com.davivienda.sara.tablas.edccargue.servicio.EdcCargueServicio;
import com.davivienda.sara.entitys.EdcCargue;
import java.util.logging.Level;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ProcesadorDiarioElectronicoServicio extends BaseServicio {
    
    private CajeroServicio cajeroServicio;
    private EdcCargueServicio edcCargueServicio;
    private DiarioElectronicoServicio diarioElectronicoServicio;
    private TransaccionServicio transaccionServicio;
    private DiarioElectronicoTempServicio diarioElectronicoTempServicio;
    private TransaccionTempServicio transaccionTempServicio;
    
    public ProcesadorDiarioElectronicoServicio(EntityManager em) {
        super(em);
        cajeroServicio = new CajeroServicio(em);
        diarioElectronicoServicio = new DiarioElectronicoServicio(em);
        transaccionServicio = new TransaccionServicio(em);
        edcCargueServicio = new EdcCargueServicio(em);
        diarioElectronicoTempServicio = new DiarioElectronicoTempServicio(em);
        transaccionTempServicio = new TransaccionTempServicio(em);
    }

    /**
     * Carga un archivo de Diario Electronico en la tabla DIARIOELECTRONICO y
     * TRANSACCION
     *
     * @param codigoCajero
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarArchivo(Integer codigoCajero, Calendar fecha, String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        if (codigoCajero == null) {
            throw new IllegalArgumentException("Debe ingresar un código de cajero");
        }
        if (fecha == null || fecha.after(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha válida");
        }
        Integer regsProcesados = -1;

        //Alvaro Garcia 31 Marzo
        Integer regsNOProces = -1;
        
        Cajero cajero = cajeroServicio.buscar(codigoCajero);
        ArchivoPlano archivo = null;
        configApp.loggerApp.info("Se inicia el proceso de carga para el cajero " + cajero.getCodigoCajero());
        ProcesadorArchivoDiarioElectronicoInterface procesador = null;
        Collection<DiarioElectronico> regsDiarioElectronico = null;
        Collection<Transaccion> regsTransaccion = null;

        //CAMBIO ESTADO DE CARGUE ARCHIVO a 10 PROCESANDO 
        EdcCargue edcCargue = edcCargueServicio.buscarPorArchivo(nombreArchivo);
        //validacion para reprocesos si ya lo proceso no cambie el estado proceso
        if (edcCargue.getEstadoproceso().compareTo(EstadoProceso.FINALIZADO.getEstado()) != 0) {
            
            edcCargue.setEstadoproceso(EstadoProceso.PROCESANDO.getEstado());
            
            edcCargueServicio.actualizar(edcCargue);
            //  edcCargueServicio.actualizar(edcCargue);

            // Asigno el tipo de archivo correspondiente al DiarioElectronico
            try {
                
                if (cajero.getTipoLecturaEDC() == 0) {
                    // Diarios Electronicos tipo EDC

                    //OJOOOO COMENTAREADO MIENTRAS SE REVISA
                    // String directorio = (cajero.getUbicacionEDC() != null || cajero.getUbicacionEDC().trim().length() > 1 ) ? cajero.getUbicacionEDC() : configApp.DIRECTORIO_UPLOAD;
                    String directorio = "";
                    
                    if (cajero.getUbicacionEDC() != null) {
                        if (cajero.getUbicacionEDC().trim().length() > 1) {
                            directorio = cajero.getUbicacionEDC();
                            
                        }
                        
                    }
                    if (directorio.equals("")) {
                        directorio = configApp.DIRECTORIO_UPLOAD;
                    }
                    
                    archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);
                    //Alvaro Garcia 17 de marzo
                    procesador = new EdcProcesadorArchivo(archivo, com.davivienda.utilidades.conversion.Fecha.getCalendar(fecha, -1));
                }

                // Leo los registros en el archivo asignado
                if (procesador != null) {
                    //Alvaro Garcia 16 Marzo
                    regsDiarioElectronico = procesador.getRegistrosDiarioElectronico();
                    regsTransaccion = procesador.getRegistrosTipoTransaccion();
                }
                //Alvaro Garcia 20 Marzo
                //        try
                //        {
                //Alvaro Garcia 20 Marzo
                // Cargo los registro en la base de datos
                int secuencia = 0;
                if (regsDiarioElectronico != null) {
                    // En la tabla DIARIOELECTRONICO
                    for (DiarioElectronico diarioElectronico : regsDiarioElectronico) {
                        diarioElectronico.setCajero(cajero);
                        //Alvaro Garcia 31 Marzo

                        //secuencia=diarioElectronico.getDiarioElectronicoPK().getSecuencia();
                        //controla que venga en la tira un cajero diferente al de el nombre
                        if (diarioElectronico.getCajero().getCodigoCajero().equals(diarioElectronico.getDiarioElectronicoPK().getCodigoCajero())) {
                            //controla que venga en la tira la secuencia de los registros en desorden 
//                       if (secuencia < (regsProcesados+1))
//                       {
//                           
//                       diarioElectronico.getDiarioElectronicoPK().setSecuencia(regsProcesados+1);
//
//                       }
                            diarioElectronicoServicio.adicionar(diarioElectronico);
                            regsProcesados++;
                        } else {
                            regsNOProces++;
                        }                        
                    }
                    if (regsNOProces > 0) {
                        configApp.loggerApp.info("Error cargando " + regsNOProces + " REGISTROS del Cajero Codigo: " + cajero.getCodigoCajero() + " por que vienen con codigo de cajero distinto al del archivo");;
                        
                        if (edcCargue != null) {
                            edcCargue.setError(CodigoError.ALGUNOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO.getCodigo());
                            edcCargueServicio.actualizar(edcCargue);
                        }
                    }
                    
                    if (regsProcesados > 0) {
                        if (regsTransaccion != null) {
                            // En la tabla TRANSACCION

                            for (Transaccion transaccion : regsTransaccion) {
                                if (transaccion != null) {                                    
                                    transaccion.setCajero(cajero);
                                    if (transaccion.getCajero().getCodigoCajero().equals(transaccion.getTransaccionPK().getCodigoCajero())) {
                                        transaccionServicio.adicionar(transaccion);
                                        
                                    }
                                }
                            }
                        }
                        //CAMBIO ESTADO DE CARGUE ARCHIVO a 0 FINALIZADO 
                        edcCargue.setEstadoproceso(EstadoProceso.FINALIZADO.getEstado());
                        edcCargue.setFechaEdcCargue(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
                        edcCargueServicio.actualizar(edcCargue);
                    } else //  EdcCargue edcCargue=edcCargueServicio.buscarPorArchivo(nombreArchivo) ;
                    if (edcCargue != null) {
                        edcCargue.setError(CodigoError.TODOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO.getCodigo());
                        edcCargueServicio.actualizar(edcCargue);
                    }
                }
                
            } catch (Exception ex) {
                configApp.loggerApp.info("Error cargando el archivo diario electronico para el cajero :" + cajero.getCodigoCajero() + " descripcion Error : " + ex.getMessage());;                
            }
            //  if(edcCargue.getEstadoproceso()!= EstadoProceso.FINALIZADO.getEstado())  
        }
        configApp.loggerApp.info("Finaliza el proceso de carga para el cajero " + cajero.getCodigoCajero() + " con " + regsProcesados + " registros procesados");
        return regsProcesados;
        
    }
    
    public Integer cargarArchivoTemp(Integer codigoCajero, Calendar fecha, String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        
        Integer regsProcesados = -1;

        //Alvaro Garcia 31 Marzo
        // Integer regsNOProces = -1;
        ArchivoPlano archivo = null;
        configApp.loggerApp.info("Se inicia el proceso de carga en tabla temp para el cajero " + codigoCajero);
        ProcesadorArchivoDiarioElectronicoTEMPInterface procesador = null;
        // Collection<DiarioelectronicoTemp> regsDiarioElectronico = null;
        Collection<TransaccionTemp> regsTransaccion = null;

//        //CAMBIO ESTADO DE CARGUE ARCHIVO a 10 PROCESANDO 
//          EdcCargue edcCargue=edcCargueServicio.buscarPorArchivo(nombreArchivo) ;
//          //validacion para reprocesos si ya lo proceso no cambie el estado proceso
//          if(edcCargue.getEstadoproceso().compareTo(EstadoProceso.FINALIZADO.getEstado())!=0 )
//          {
//          edcCargue.setEstadoproceso(EstadoProceso.PROCESANDO.getEstado());
//         
//          edcCargueServicio.actualizar(edcCargue);
        //  edcCargueServicio.actualizar(edcCargue);
        // Asigno el tipo de archivo correspondiente al DiarioElectronico
        try {
            
            String directorio = "";
            
            if (directorio.equals("")) {
                directorio = configApp.DIRECTORIO_UPLOAD;
            }
            
            archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);

            //Alvaro Garcia 17 de marzo
            procesador = new EdcProcesadorArchivoTemp(archivo, com.davivienda.utilidades.conversion.Fecha.getCalendar(fecha, -1));

            // Leo los registros en el archivo asignado
            if (procesador != null) {
                //Alvaro Garcia 16 Marzo
                // regsDiarioElectronico = procesador.getRegistrosDiarioElectronicoTemp();
                regsTransaccion = procesador.getRegistrosTipoTransaccionTemp();
                
            }
            
            if (regsTransaccion != null) {
                // En la tabla TRANSACCION

                for (TransaccionTemp transaccion : regsTransaccion) {
                    if (transaccion != null) {                        
                        
                        transaccionTempServicio.adicionar(transaccion);
                        regsProcesados++;                        
                        
                    }
                }
            }
            
        } catch (Exception ex) {
            configApp.loggerApp.log(Level.SEVERE, "", ex);
            configApp.loggerApp.info("Error cargando el archivo diario electronico para el cajero :" + codigoCajero + " descripcion Error : " + ex.getMessage());;            
        }
        //  if(edcCargue.getEstadoproceso()!= EstadoProceso.FINALIZADO.getEstado())  
//       }
        //configApp.loggerApp.info("Finaliza el proceso de carga para el cajero " + codigoCajero + " con " + regsProcesados + " registros procesados");
        return regsProcesados;
        
    }
    
    public void cargarCicloTempXStoreP() throws EntityServicioExcepcion {
        
        try {
            transaccionTempServicio.cargarCicloTempXStoreP();
            
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure CARGUETRANSACCIONTEMP No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error al ejecutar el store procedure CARGUETRANSACCIONTEMP El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        
    }
    
    public Integer cargarArchivoSoloDiario(Integer codigoCajero, Calendar fecha, String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        if (codigoCajero == null) {
            throw new IllegalArgumentException("Debe ingresar un código de cajero");
        }
        if (fecha == null || fecha.after(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy())) {
            throw new IllegalArgumentException("Debe ingresar una fecha válida");
        }
        Integer regsProcesados = -1;

        //Alvaro Garcia 31 Marzo
        Integer regsNOProces = -1;
        
        Cajero cajero = cajeroServicio.buscar(codigoCajero);
        ArchivoPlano archivo = null;
        configApp.loggerApp.info("Se inicia el proceso de carga para el cajero " + cajero.getCodigoCajero());
        ProcesadorArchivoDiarioElectronicoInterface procesador = null;
        Collection<DiarioElectronico> regsDiarioElectronico = null;

        //CAMBIO ESTADO DE CARGUE ARCHIVO a 10 PROCESANDO
        EdcCargue edcCargue = edcCargueServicio.buscarPorArchivo(nombreArchivo);
        //validacion para reprocesos si ya lo proceso no cambie el estado proceso
        if (edcCargue.getEstadoproceso().compareTo(EstadoProceso.FINALIZADO.getEstado()) != 0) {
            
            edcCargue.setEstadoproceso(EstadoProceso.PROCESANDO.getEstado());
            
            edcCargueServicio.actualizar(edcCargue);
            //  edcCargueServicio.actualizar(edcCargue);

            // Asigno el tipo de archivo correspondiente al DiarioElectronico
            try {
                
                if (cajero.getTipoLecturaEDC() == 0) {
                    // Diarios Electronicos tipo EDC

                    //OJOOOO COMENTAREADO MIENTRAS SE REVISA
                    // String directorio = (cajero.getUbicacionEDC() != null || cajero.getUbicacionEDC().trim().length() > 1 ) ? cajero.getUbicacionEDC() : configApp.DIRECTORIO_UPLOAD;
                    String directorio = "";
                    
                    if (cajero.getUbicacionEDC() != null) {
                        if (cajero.getUbicacionEDC().trim().length() > 1) {
                            directorio = cajero.getUbicacionEDC();
                            
                        }
                        
                    }
                    if (directorio.equals("")) {
                        directorio = configApp.DIRECTORIO_UPLOAD;
                    }
                    configApp.loggerApp.info("Directorio de carga para el cajero " + cajero.getCodigoCajero() + " : " + directorio);
                    archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);
                    //Alvaro Garcia 17 de marzo
                    procesador = new EdcProcesadorArchivo(archivo, com.davivienda.utilidades.conversion.Fecha.getCalendar(fecha, -1));
                }

                // Leo los registros en el archivo asignado
                if (procesador != null) {
                    //Alvaro Garcia 16 Marzo
                    regsDiarioElectronico = procesador.getRegistrosDiarioElectronico();
                    
                }
                //Alvaro Garcia 20 Marzo
                //        try
                //        {
                //Alvaro Garcia 20 Marzo
                // Cargo los registro en la base de datos
                int secuencia = 0;
                if (regsDiarioElectronico != null) {
                    // En la tabla DIARIOELECTRONICO
                    for (DiarioElectronico diarioElectronico : regsDiarioElectronico) {
                        diarioElectronico.setCajero(cajero);
                        //Alvaro Garcia 31 Marzo

                        //secuencia=diarioElectronico.getDiarioElectronicoPK().getSecuencia();
                        //controla que venga en la tira un cajero diferente al de el nombre
                        if (diarioElectronico.getCajero().getCodigoCajero().equals(diarioElectronico.getDiarioElectronicoPK().getCodigoCajero())) {
                            //controla que venga en la tira la secuencia de los registros en desorden
//                       if (secuencia < (regsProcesados+1))
//                       {
//
//                       diarioElectronico.getDiarioElectronicoPK().setSecuencia(regsProcesados+1);
//
//                       }
                            diarioElectronicoServicio.adicionar(diarioElectronico);
                            regsProcesados++;
                        } else {
                            regsNOProces++;
                        }
                    }
                    if (regsNOProces > 0) {
                        configApp.loggerApp.info("Error cargando " + regsNOProces + " REGISTROS del Cajero Codigo: " + cajero.getCodigoCajero() + " por que vienen con codigo de cajero distinto al del archivo");;
                        
                        if (edcCargue != null) {
                            edcCargue.setError(CodigoError.ALGUNOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO.getCodigo());
                            edcCargueServicio.actualizar(edcCargue);
                        }
                    }
                    
                }
                
            } catch (Exception ex) {
                configApp.loggerApp.log(Level.SEVERE, "", ex);
                configApp.loggerApp.info("Error cargando el archivo diario electronico para el cajero :" + cajero.getCodigoCajero() + " descripcion Error : " + ex.getMessage());;
            }
            //  if(edcCargue.getEstadoproceso()!= EstadoProceso.FINALIZADO.getEstado())
        }
        configApp.loggerApp.info("Finaliza el proceso de carga para el cajero " + cajero.getCodigoCajero() + " con " + regsProcesados + " registros procesados");
        return regsProcesados;
        
    }
    
    public Integer cargarDiarioElectronicoTemp(Integer codigoCajero, Calendar fecha, String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        
        Integer regsProcesados = -1;

        //Alvaro Garcia 31 Marzo
        Integer regsNOProces = -1;
        
        ArchivoPlano archivo = null;
        configApp.loggerApp.info("Se inicia el proceso de carga en tabla diarioelectronicotemp para el cajero " + codigoCajero);
        ProcesadorArchivoDiarioElectronicoTEMPInterface procesador = null;
        Collection<DiarioelectronicoTemp> regsDiarioElectronico = null;
        //Collection<TransaccionTemp> regsTransaccion = null;
        
        try {
            
            String directorio = "";
            
            if (directorio.equals("")) {
                directorio = configApp.DIRECTORIO_UPLOAD;
            }
            
            archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);
            //Alvaro Garcia 17 de marzo
            procesador = new EdcProcesadorArchivoTemp(archivo, com.davivienda.utilidades.conversion.Fecha.getCalendar(fecha, -1));

            // Leo los registros en el archivo asignado
            if (procesador != null) {
                //Alvaro Garcia 16 Marzo
                regsDiarioElectronico = procesador.getRegistrosDiarioElectronicoTemp();
                //  regsTransaccion = procesador.getRegistrosTipoTransaccionTemp();
                
            }
            
            int secuencia = 0;
            if (regsDiarioElectronico != null) {
                // En la tabla DIARIOELECTRONICO
                for (DiarioelectronicoTemp diarioElectronico : regsDiarioElectronico) {
                    diarioElectronico.getDiarioelectronicoTempPK().setCodigocajero(codigoCajero);
                    //Alvaro Garcia 31 Marzo

                    secuencia = diarioElectronico.getDiarioelectronicoTempPK().getSecuencia();
                    //controla que venga en la tira un cajero diferente al de el nombre

                    //controla que venga en la tira la secuencia de los registros en desorden 
                    if (secuencia < (regsProcesados + 1)) {
                        
                        diarioElectronico.getDiarioelectronicoTempPK().setSecuencia(regsProcesados + 1);
                        
                    }
                    diarioElectronicoTempServicio.adicionar(diarioElectronico);
                    regsProcesados++;
                    
                }
                
            }
            
        } catch (Exception ex) {
            configApp.loggerApp.info("Error cargando el archivo diario electronico temp para el cajero :" + codigoCajero + " descripcion Error : " + ex.getMessage());;            
        }
        //  if(edcCargue.getEstadoproceso()!= EstadoProceso.FINALIZADO.getEstado())  
//       }
        //configApp.loggerApp.info("Finaliza el proceso de carga para el cajero " + codigoCajero + " con " + regsProcesados + " registros procesados");
        return regsProcesados;
        
    }
    
    public int borrarDiarioelectronicoTemp() throws EntityServicioExcepcion {
        int respuesta = 0;

//        String strQuery = "delete object(obj) from EdcCargue obj " +
//                "        where obj.ciclo = : codigoCiclo ";
        String strQuery = "DELETE  from DiarioelectronicoTemp obj ";
        
        try {
            
            Query query = null;
            query = em.createQuery(strQuery);
            respuesta = query.executeUpdate();
            
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (TransactionRequiredException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "no es una transaccion ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return respuesta;
        
    }

    public int borrarTransaccionTemp() throws EntityServicioExcepcion {
        int respuesta = 0;

//        String strQuery = "delete object(obj) from EdcCargue obj " +
//                "        where obj.ciclo = : codigoCiclo ";
        String strQuery = "DELETE   from TransaccionTemp obj ";
        
        try {
            
            Query query = null;
            query = em.createQuery(strQuery);
            respuesta = query.executeUpdate();
            
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (TransactionRequiredException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "no es una transaccion ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return respuesta;
        
    }

    /**
     * Carga el ciclo de cajeros diario
     *
     * @param fecha
     * @return
     */
    public void cargarTransaccionesCiclo(Integer ciclo) {
        
        try {
            
            transaccionServicio.copiarTablasTransaccion();
            
        } catch (EntityServicioExcepcion ex) {
            configApp.loggerApp.severe("Error al grabar los datos  en tabla transaccion  para el ciclo " + ciclo + "    " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.severe("Error al grabar los datos  en tabla  transaccion  para el ciclo " + ciclo + "  " + ex.getMessage());
        }
    }
    
    public Integer cargarArchivoCicloNuevo(Integer codigoCajero, Calendar fecha, String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        
        Integer regsProcesados = -1;

        //Alvaro Garcia 31 Marzo
        Integer regsNOProces = -1;
        
        Cajero cajero = cajeroServicio.buscar(codigoCajero);
        ArchivoPlano archivo = null;
        configApp.loggerApp.info("Se inicia el proceso de carga para el cajero " + cajero.getCodigoCajero());
        ProcesadorArchivoDiarioElectronicoInterface procesador = null;
        Collection<DiarioElectronico> regsDiarioElectronico = null;

        //CAMBIO ESTADO DE CARGUE ARCHIVO a 10 PROCESANDO 
        EdcCargue edcCargue = edcCargueServicio.buscarPorArchivo(nombreArchivo);
        //validacion para reprocesos si ya lo proceso no cambie el estado proceso
        if (edcCargue.getEstadoproceso().compareTo(EstadoProceso.FINALIZADO.getEstado()) != 0) {
            
            edcCargue.setEstadoproceso(EstadoProceso.PROCESANDO.getEstado());
            
            edcCargueServicio.actualizar(edcCargue);
            //  edcCargueServicio.actualizar(edcCargue);

            // Asigno el tipo de archivo correspondiente al DiarioElectronico
            try {
                
                String directorio = "";
                
                if (cajero.getUbicacionEDC() != null) {
                    if (cajero.getUbicacionEDC().trim().length() > 1) {
                        directorio = cajero.getUbicacionEDC();
                        
                    }
                    
                }
                if (directorio.equals("")) {
                    directorio = configApp.DIRECTORIO_UPLOAD;
                }
                
                archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);
                //Alvaro Garcia 17 de marzo
                procesador = new EdcProcesadorArchivo(archivo, com.davivienda.utilidades.conversion.Fecha.getCalendar(fecha, -1));

                // Leo los registros en el archivo asignado
                if (procesador != null) {
                    //Alvaro Garcia 16 Marzo
                    regsDiarioElectronico = procesador.getRegistrosDiarioElectronico();
                    
                }
                
                int secuencia = 0;
                if (regsDiarioElectronico != null) {
                    // En la tabla DIARIOELECTRONICO
                    for (DiarioElectronico diarioElectronico : regsDiarioElectronico) {
                        diarioElectronico.setCajero(cajero);
                        secuencia = diarioElectronico.getDiarioElectronicoPK().getSecuencia();
                        //controla que venga en la tira un cajero diferente al de el nombre
                        if (diarioElectronico.getCajero().getCodigoCajero().equals(diarioElectronico.getDiarioElectronicoPK().getCodigoCajero())) {
                            //controla que venga en la tira la secuencia de los registros en desorden 
                            if (secuencia < (regsProcesados + 1)) {
                                diarioElectronico.getDiarioElectronicoPK().setSecuencia(regsProcesados + 1);
                            }
                            diarioElectronicoServicio.adicionar(diarioElectronico);
                            regsProcesados++;
                        } else {
                            regsNOProces++;
                        }                        
                    }
                    if (regsNOProces > 0) {
                        configApp.loggerApp.info("Error cargando " + regsNOProces + " REGISTROS del Cajero Codigo: " + cajero.getCodigoCajero() + " por que vienen con codigo de cajero distinto al del archivo");;
                        
                        if (edcCargue != null) {
                            edcCargue.setError(CodigoError.ALGUNOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO.getCodigo());
                            edcCargueServicio.actualizar(edcCargue);
                        }
                    }
                    
                    if (regsProcesados <= 0) {
                        
                        if (edcCargue != null) {
                            edcCargue.setError(CodigoError.TODOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO.getCodigo());
                            edcCargueServicio.actualizar(edcCargue);
                        }
                        
                    }
                    
                }
                
            } catch (Exception ex) {
                configApp.loggerApp.log(Level.SEVERE, "", ex);
                configApp.loggerApp.info("Error cargando el archivo diario electronico para el cajero :" + cajero.getCodigoCajero() + " descripcion Error : " + ex.getMessage());;                
            }
            
        }
        configApp.loggerApp.info("Finaliza el proceso de carga para el cajero " + cajero.getCodigoCajero() + " con " + regsProcesados + " registros procesados");
        return regsProcesados;
        
    }
    
}
