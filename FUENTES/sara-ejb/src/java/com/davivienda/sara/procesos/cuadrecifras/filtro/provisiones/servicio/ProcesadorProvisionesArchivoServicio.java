package com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.servicio;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.base.ProcesadorArchivoProvisionesInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.ProvisionHost;
import com.davivienda.sara.entitys.ProvisionHostPK;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.constantes.TipoMovimientoCuadre;
//import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.estructura.ProvisionesArchivo;
import com.davivienda.sara.procesos.cuadrecifras.filtro.provisiones.procesador.ProvisionesProcesadorArchivo;
import com.davivienda.sara.tablas.provisionhost.servicio.ProvisionHostServicio;
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
import java.util.LinkedList;
import java.util.logging.Level;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ProcesadorProvisionesArchivoServicio extends BaseServicio {

    private ProvisionHostServicio provisionHostServicio;
    private MovimientoCuadreServicio movimientoCuadreServicio;

    public ProcesadorProvisionesArchivoServicio(EntityManager em) {
        super(em);
        provisionHostServicio = new ProvisionHostServicio(em);
        movimientoCuadreServicio = new MovimientoCuadreServicio(em);

    }

    /**
     * Carga un archivo de provisiones en la tabla ProvisionHost y
     * MovimientoCuadre
     *
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    @SuppressWarnings("empty-statement")
    public Integer cargarArchivoProvisiones(Calendar fecha) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        Integer regsProcesados = -1;

        try {

            ArchivoPlano archivo = null;
            // configApp.loggerApp.info("Se inicia el proceso de carga para el cajero " + cajero.getCodigoCajero());
            ProcesadorArchivoProvisionesInterface procesador = null;
            Collection<ProvisionHost> regsProvisionHost = null;
            ArrayList<ProvisionHost> regsProvisionHostCompara = new ArrayList<ProvisionHost>();
            ProvisionHostPK ProvisionHostPKfecha = null;

            String directorio;

            directorio = configApp.DIRECTORIO_CUADRE;

            archivo = new ProvisionesArchivo(fecha, directorio);
            procesador = new ProvisionesProcesadorArchivo(archivo);

            // Leo los registros en el archivo asignado
            if (procesador != null) {

                regsProvisionHost = procesador.getRegistrosProvisiones();

            }
            com.davivienda.sara.entitys.TipoMovimientoCuadre tmc;

            if (regsProvisionHost != null) {
                configApp.loggerApp.log(Level.INFO, "Inicia el proceso de carga para el archivo {0} con fecha {1} a las: {2} con {3} registros leidos", new Object[]{archivo.getNombreArchivo(), Fecha.aCadena(fecha, "yyyy/MM/dd"), Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regsProvisionHost.size()});
                for (ProvisionHost provisionHost : regsProvisionHost) {
                    //OJOOO VALIDAR QUE SE CARGUE EL CAJERO EN EL ARCHIVO PLANO
                    try {
                        if (provisionHost != null) {
                            Cajero cajero = em.find(new Cajero().getClass(), provisionHost.getCajero().getCodigoCajero());
                            if (cajero != null) {
                                provisionHost.setCajero(cajero);

                                if (buscarRegistro(regsProvisionHostCompara, provisionHost) == false) {
                                    MovimientoCuadre mc = obtenerMovimientoCuadreLinea(provisionHost);
                                    if (mc != null) {
                                        if (mc.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.AUMENTO_PROVISION_LINEA.codigo) || mc.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre().equals(TipoMovimientoCuadre.DISMINUCION_PROVISION_LINEA.codigo)) {
                                            ProvisionHostPKfecha = provisionHost.getProvisionHostPK();
                                            ProvisionHostPKfecha.setFecha(mc.getFecha());
                                            provisionHost.setProvisionHostPK(ProvisionHostPKfecha);
                                        }
                                        configApp.loggerApp.info("Registro ProvisionHost  codigo:" + provisionHost.getProvisionHostPK().getCodigoCajero() + " fecha: " + provisionHost.getProvisionHostPK().getFecha() + " hora: " + provisionHost.getProvisionHostPK().getHora() + " motivo: " + provisionHost.getProvisionHostPK().getMotivo() + " tipo: " + provisionHost.getProvisionHostPK().getTipo() + " valor: " + provisionHost.getValor());
                                        ProvisionHost existeProvision = provisionHostServicio.buscar(provisionHost.getProvisionHostPK());

                                        if (existeProvision == null) {
                                            provisionHostServicio.grabarProvisionHost(provisionHost);
                                            regsProcesados++;
                                            movimientoCuadreServicio.adicionar(mc);
                                            //em.persist(mc);
                                            ProvisionHost regprov = new ProvisionHost();
                                            regprov = provisionHost;
                                            regsProvisionHostCompara.add(regprov);
                                        } else {
                                            configApp.loggerApp.info("Registro ProvisionHost  " + provisionHost + " ya se encuentra en base de datos, no se cargara.");
                                        }

                                    }
                                } else {
                                    configApp.loggerApp.info("Registro Repetido " + provisionHost.getCajero());
                                }

                            } else {
                                configApp.loggerApp.info("en Registro " + (regsProcesados + 1) + " No existe el cajero : " + provisionHost.getCajero().getCodigoCajero().toString());
                            }
                        }
                    } catch (Exception ex) {
                        configApp.loggerApp.log(Level.WARNING, "Error: {0}", ex.getMessage());
                    }
                }
                configApp.loggerApp.info("Finaliza el proceso de carga para el archivo de provisiones del " + Fecha.aCadena(fecha, "yyyy/MM/dd") + " a las: " + Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss") + " con " + regsProcesados + " registros procesados");
            } else {
                configApp.loggerApp.log(Level.INFO, "ProvisionHost esta nulo");
            }
        } catch (Exception ex) {
            configApp.loggerApp.info("Error cargando el archivo provisiones  descripcion Error : " + ex.getMessage());;
        }

        return regsProcesados;
    }

    //obtengo que objetos ProvisionHost vienen con llave repetida en el archivo
    private boolean buscarRegistro(ArrayList<ProvisionHost> regsProvisionHostCompara, ProvisionHost regProvisionHost) {

        boolean buscaReg = false;

        if (regsProvisionHostCompara != null) {

            Iterator iterador = regsProvisionHostCompara.listIterator();
            while (iterador.hasNext()) {
                ProvisionHost provisionHost = (ProvisionHost) iterador.next(); //Obtengo el elemento contenido 
                //pero visto como un provisionHost
                if (provisionHost.getProvisionHostPK().equals(regProvisionHost.getProvisionHostPK())) {
                    buscaReg = true;
                }
            }
        }

        return buscaReg;
    }

    private MovimientoCuadre obtenerMovimientoCuadreLinea(ProvisionHost ph) {
        TipoMovimientoCuadre tm = null;
        MovimientoCuadre mc = new MovimientoCuadre();
        mc.setIdUsuario("HOST");

        if (ph.getProvisionHostPK().getMotivo() == 75 && ph.getProvisionHostPK().getTipo() == 99) {

            tm = TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE_IDO;//299
        }

        //OJO REVISAR CON JOHN ESTE CASO
        if (ph.getProvisionHostPK().getMotivo() == 70 && ph.getProvisionHostPK().getTipo() == 98) {
            tm = TipoMovimientoCuadre.AUMENTO_PROVISION_LINEA;//215
        }
        if (ph.getProvisionHostPK().getMotivo() == 70 && ph.getProvisionHostPK().getTipo() == 99) {
            tm = TipoMovimientoCuadre.DISMINUCION_PROVISION_LINEA;//216
        }
        if (ph.getProvisionHostPK().getMotivo() == 112 && ph.getProvisionHostPK().getTipo() == 98) {
            tm = TipoMovimientoCuadre.AUMENTO_PROVISION_IDO;//210
        }
        if (ph.getProvisionHostPK().getMotivo() == 112 && ph.getProvisionHostPK().getTipo() == 99) {
            tm = TipoMovimientoCuadre.DISMINUCION_PROVISION_IDO;//211
        }
        if (ph.getProvisionHostPK().getMotivo() == 16 && ph.getProvisionHostPK().getTipo() == 98 && ph.getUsuarioHost().trim().length() == 0) {
            tm = TipoMovimientoCuadre.SOBRANTE_IDO;//220
        }
        if (ph.getProvisionHostPK().getMotivo() == 16 && ph.getProvisionHostPK().getTipo() == 98 && ph.getUsuarioHost().trim().length() > 0) {
            tm = TipoMovimientoCuadre.SOBRANTE_MANUAL;//221
            mc.setIdUsuario(ph.getUsuarioHost());
        }
        if (ph.getProvisionHostPK().getMotivo() == 35 && ph.getProvisionHostPK().getTipo() == 99 && ph.getUsuarioHost().trim().length() == 0) {
            tm = TipoMovimientoCuadre.FALTANTE_IDO;//240
        }
        //REVISAR ESTOS ITEMS FALTANTE_MANUAL Y SOBRANTE_MANUAL 16 99 Y 35 98
        if (ph.getProvisionHostPK().getMotivo() == 35 && ph.getProvisionHostPK().getTipo() == 99 && ph.getUsuarioHost().trim().length() > 0) {
            tm = TipoMovimientoCuadre.FALTANTE_MANUAL;//241
            mc.setIdUsuario(ph.getUsuarioHost());
        }


        if (tm != null) {
            com.davivienda.sara.entitys.TipoMovimientoCuadre tmc;
            tmc = em.find(new com.davivienda.sara.entitys.TipoMovimientoCuadre().getClass(), tm.codigo);
            if (tmc != null) {
                mc.setCajero(ph.getCajero());
                if (tm == TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE_IDO) {
                    mc.setFecha(com.davivienda.utilidades.conversion.Fecha.getDate(ph.getProvisionHostPK().getFecha(), -1));
                } else //ajuste para dejar estos items en la fecha que corresponda y no la que viene en el archivo del dia siguiente
                if (tm.equals(TipoMovimientoCuadre.AUMENTO_PROVISION_LINEA) || tm.equals(TipoMovimientoCuadre.DISMINUCION_PROVISION_LINEA)) {
                    mc.setFecha(Fecha.getDate(ph.getProvisionHostPK().getFecha(), -1));
                } else {
                    mc.setFecha(ph.getProvisionHostPK().getFecha());
                }
                mc.setTipoMovimientoCuadre(tmc);
                mc.setValorMovimiento(ph.getValor());


            } else {
                mc = null;
            }
        } else {
            mc = null;
        }
        return mc;
    }

}
