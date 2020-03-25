package com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.servicio;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.base.ProcesadorArchivoTotalesEstacionInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.TotalesEstacion;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.constantes.TipoMovimientoCuadre;
//import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.estructura.TotalesEstacionArchivo;
import com.davivienda.sara.procesos.cuadrecifras.filtro.totalesestacion.procesador.TotalesEstacionProcesadorArchivo;
import com.davivienda.sara.tablas.totalesestacion.servicio.TotalesEstacionServicio;
import com.davivienda.sara.tablas.movimientocuadre.servicio.MovimientoCuadreServicio;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ProcesadorTotalesEstacionArchivoServicio extends BaseServicio {

    private TotalesEstacionServicio totalesEstacionServicio;
    private MovimientoCuadreServicio movimientoCuadreServicio;

    public ProcesadorTotalesEstacionArchivoServicio(EntityManager em) {
        super(em);
        totalesEstacionServicio = new TotalesEstacionServicio(em);
        movimientoCuadreServicio = new MovimientoCuadreServicio(em);

    }

    /**
     * Carga un archivo de Corte diario en la tabla TotalesEstacion y
     * movimientoCuadre
     *
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarArchivoTotalesEstacion(Calendar fecha) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        Integer regsProcesados = -1;

        ArchivoPlano archivo = null;
        ProcesadorArchivoTotalesEstacionInterface procesador = null;
        Collection<TotalesEstacion> regsTotalesEstacion = null;
        ArrayList<TotalesEstacion> regsTotalesEstacionCompara = new ArrayList<TotalesEstacion>();

        String directorio;

        directorio = configApp.DIRECTORIO_CUADRE;

        archivo = new TotalesEstacionArchivo(fecha, directorio);
        procesador = new TotalesEstacionProcesadorArchivo(archivo, fecha);

        if (procesador != null) {
            regsTotalesEstacion = procesador.getRegistrosTotalesEstacion();
        }
        com.davivienda.sara.entitys.TipoMovimientoCuadre tmc;

        if (regsTotalesEstacion != null) {
            configApp.loggerApp.log(Level.INFO, "Inicia el proceso de carga para el archivo {0} con fecha {1} a las: {2} con {3} registros leidos", new Object[]{archivo.getNombreArchivo(), Fecha.aCadena(fecha, "yyyy/MM/dd"), Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regsTotalesEstacion.size()});
            for (TotalesEstacion totalesEstacion : regsTotalesEstacion) {
                try {
                    //OJOOO VALIDAR QUE SE CARGUE EL CAJERO EN EL ARCHIVO PLANO
                    Cajero cajero = em.find(new Cajero().getClass(), totalesEstacion.getCajero().getCodigoCajero());
                    if (cajero != null) {
                        totalesEstacion.setCajero(cajero);

                        try {

                            if (buscarRegistro(regsTotalesEstacionCompara, totalesEstacion) == false) {

                                TotalesEstacion existeTotales = totalesEstacionServicio.buscar(totalesEstacion.getTotalesEstacionPK());
                                if (existeTotales == null) {
                                    MovimientoCuadre mc = obtenerMovimientoCuadreLinea(totalesEstacion);
                                    if (mc != null) {
                                        totalesEstacionServicio.adicionar(totalesEstacion);
                                        regsProcesados++;
                                        movimientoCuadreServicio.adicionar(mc);
                                        TotalesEstacion regprov = new TotalesEstacion();
                                        regprov = totalesEstacion;
                                        regsTotalesEstacionCompara.add(regprov);
                                    }
                                } else {
                                    configApp.loggerApp.log(Level.INFO, "Registro TotalesEstacion  {0} ya se encuentra en base de datos, no se cargara.", totalesEstacion);
                                }
                            } else {

                                configApp.loggerApp.info("Registro Repetido " + totalesEstacion.getCajero());
                            }

                        } catch (Exception ex) {
                            configApp.loggerApp.log(Level.INFO, "error en procesador totales estacion {0}", ex.getMessage());

                        }
                    } else {
                        configApp.loggerApp.log(Level.INFO, "en Registro {0} No existe el cajero : {1}", new Object[]{regsProcesados + 1, totalesEstacion.getCajero().getCodigoCajero().toString()});
                    }
                } catch (Exception e) {
                    configApp.loggerApp.log(Level.WARNING, "Error: {0}", e.getMessage());

                }
            }
            configApp.loggerApp.log(Level.INFO, "Finaliza el proceso de carga para el archivo de totales estacion  del {0} a las: {1} con {2} registros procesados", new Object[]{Fecha.aCadena(fecha, "yyyy/MM/dd"), Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regsProcesados});
        }else{
            configApp.loggerApp.log(Level.INFO, "TotalesEstacion esta nulo");
        }

        

        return regsProcesados;
    }

    //obtengo que objetos TotalesEstacion vienen con llave repetida en el archivo
    private boolean buscarRegistro(ArrayList<TotalesEstacion> regsProvisionHostCompara, TotalesEstacion regProvisionHost) {

        boolean buscaReg = false;

        if (regsProvisionHostCompara != null) {

            Iterator iterador = regsProvisionHostCompara.listIterator();
            while (iterador.hasNext()) {
                TotalesEstacion totalesEstacion = (TotalesEstacion) iterador.next(); //Obtengo el elemento contenido 
                //pero visto como un totalesEstacion
                if (totalesEstacion.getTotalesEstacionPK().equals(regProvisionHost.getTotalesEstacionPK())) {
                    buscaReg = true;
                }
            }
        }

        return buscaReg;
    }

    private MovimientoCuadre obtenerMovimientoCuadreLinea(TotalesEstacion te) throws Exception {
        TipoMovimientoCuadre tm = null;
        MovimientoCuadre mc = new MovimientoCuadre();
        if (te.getTotalesEstacionPK().getCodigoTotal() == 36) {
            tm = TipoMovimientoCuadre.PAGADO_IDO;//230
        }
        if (te.getTotalesEstacionPK().getCodigoTotal() == 98) {
            tm = TipoMovimientoCuadre.DONACIONES;//231
        }
        if (te.getTotalesEstacionPK().getCodigoTotal() == 3) {
            tm = TipoMovimientoCuadre.PROVISION_AYER;//101
        }

        if (tm != null) {
            com.davivienda.sara.entitys.TipoMovimientoCuadre tmc;
            tmc = em.find(new com.davivienda.sara.entitys.TipoMovimientoCuadre().getClass(), tm.codigo);
            if (tmc != null) {
                mc.setCajero(te.getCajero());
                mc.setFecha(te.getTotalesEstacionPK().getFecha());
                mc.setTipoMovimientoCuadre(tmc);
                mc.setValorMovimiento(te.getValorevento());
                mc.setIdUsuario("HOST");
            } else {
                mc = null;
            }
        } else {
            mc = null;
        }
        return mc;
    }
}
