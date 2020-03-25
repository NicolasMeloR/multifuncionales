package com.davivienda.sara.procesos.cuadrecifras.filtro.corte.servicio;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.base.ProcesadorArchivoCorteInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.BilletajeCajero;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.constantes.TipoMovimientoCuadre;
//import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.sara.procesos.cuadrecifras.filtro.corte.estructura.CorteArchivo;
import com.davivienda.sara.procesos.cuadrecifras.filtro.corte.procesador.CorteProcesadorArchivo;
import com.davivienda.sara.tablas.billetajecajero.servicio.BilletajeCajeroServicio;
import com.davivienda.sara.tablas.movimientocuadre.servicio.MovimientoCuadreServicio;

import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Collection;
import javax.persistence.EntityManager;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;

/**
 * ProcesadorDiarioElectronicoServicio - 22/08/2008 Descripción : Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ProcesadorCorteArchivoServicio extends BaseServicio {

    private BilletajeCajeroServicio billetajeCajeroServicio;
    private MovimientoCuadreServicio movimientoCuadreServicio;

    public ProcesadorCorteArchivoServicio(EntityManager em) {
        super(em);

        billetajeCajeroServicio = new BilletajeCajeroServicio(em);
        movimientoCuadreServicio = new MovimientoCuadreServicio(em);
    }

    /**
     * Carga un archivo de Corte diario en la tabla BilletajeCajero y
     * movimientoCuadre
     *
     * @param fecha
     * @return
     * @throws java.io.FileNotFoundException
     * @throws com.davivienda.sara.base.exception.EntityServicioExcepcion
     * @throws java.lang.IllegalArgumentException
     */
    public Integer cargarArchivoCorte(Calendar fecha) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {

        
        //configApp.loggerApp.log(Level.INFO, "ProcesadorCorteArchivoServicio -- cargarArchivoCorte");
        Integer regsProcesados = -1;

        ArchivoPlano archivo = null;
        ProcesadorArchivoCorteInterface procesador = null;
        Collection<BilletajeCajero> regsBilletajeCajero = null;

        String directorio = "";
        directorio = configApp.DIRECTORIO_CUADRE;

        archivo = new CorteArchivo(fecha, directorio);
        procesador = new CorteProcesadorArchivo(archivo);
        
        configApp.loggerApp.log(Level.INFO, "ProcesadorCorteArchivoServicio -- directorio: " + directorio + " -- archivo: " +archivo);

        // Leo los registros en el archivo asignado
        if (procesador != null) {
            regsBilletajeCajero = procesador.getRegistrosBilletajeCajero();
        }

        if (regsBilletajeCajero != null) {
            configApp.loggerApp.log(Level.INFO, "Inicia el proceso de carga para el archivo {0} con fecha {1} a las: {2} con {3} registros leidos", new Object[]{archivo.getNombreArchivo(), Fecha.aCadena(fecha, "yyyy/MM/dd"), Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regsBilletajeCajero.size()});
            for (BilletajeCajero billetajeCajero : regsBilletajeCajero) {
                //configApp.loggerApp.log(Level.INFO, "cargarArchivoCorte -- Registro a Insertar billetajeCajero " + billetajeCajero.toString() );
                try {
                    Cajero cajero = em.find(new Cajero().getClass(), billetajeCajero.getCajero().getCodigoCajero());
                    if (cajero != null) {
                        billetajeCajero.setCajero(cajero);
                        BilletajeCajero existeBilletaje = billetajeCajeroServicio.buscar(billetajeCajero.getBilletajeCajeroPK());

                        if (existeBilletaje == null) {
                            billetajeCajeroServicio.adicionar(billetajeCajero);
                            regsProcesados++;
                            MovimientoCuadre mcDispensado = obtenerMovimientoCuadreLinea(TipoMovimientoCuadre.DISPENSADO, billetajeCajero);
                            movimientoCuadreServicio.adicionar(mcDispensado);
                            MovimientoCuadre mcProvDiaAnt = obtenerMovimientoCuadreLinea(TipoMovimientoCuadre.PROVISION_DIA_ANTERIOR, billetajeCajero);
                            movimientoCuadreServicio.adicionar(mcProvDiaAnt);
                            MovimientoCuadre mcProvDiaSte = obtenerMovimientoCuadreLinea(TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE, billetajeCajero);
                            movimientoCuadreServicio.adicionar(mcProvDiaSte);
                            
                            
                            //configApp.loggerApp.log(Level.INFO, "cargarArchivoCorte -- Registro a Insertar mcDispensado: " + mcDispensado.toString() );
                            //configApp.loggerApp.log(Level.INFO, "cargarArchivoCorte -- Registro a Insertar mcProvDiaAnt: " + mcProvDiaAnt.toString() );
                            //configApp.loggerApp.log(Level.INFO, "cargarArchivoCorte -- Registro a Insertar mcProvDiaSte: " + mcProvDiaSte.toString() );
                        } else {
                            configApp.loggerApp.log(Level.INFO, "El Registro BilletajeCajero {0} y fecha: {1} existe en base de datos, no se guardara.",
                                    new Object[]{existeBilletaje.getBilletajeCajeroPK().getCodigoCajero(), existeBilletaje.getBilletajeCajeroPK().getFecha()});
                        }
                    } else {
                        configApp.loggerApp.log(Level.INFO, "en Registro {0} No existe el cajero", (regsProcesados + 1));
                    }
                } catch (Exception e) {
                    configApp.loggerApp.log(Level.WARNING, "Error: {0}", e.getMessage());
                }
            }
            configApp.loggerApp.log(Level.INFO, "Finaliza el proceso de carga para el archivo de corte diario del {0} a las: {1} con {2} registros procesados", new Object[]{Fecha.aCadena(fecha, "yyyy/MM/dd"), Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regsProcesados});
        }else{
            configApp.loggerApp.log(Level.INFO, "RegsBilletajeCajero esta nulo");
        }

        return regsProcesados;
    }

    private MovimientoCuadre obtenerMovimientoCuadreLinea(TipoMovimientoCuadre tm, BilletajeCajero bc) {
        com.davivienda.sara.entitys.TipoMovimientoCuadre tmc;
        tmc = em.find(new com.davivienda.sara.entitys.TipoMovimientoCuadre().getClass(), tm.codigo);
        //   tmc = tipoMovimientoCuadreServicio.buscar(new com.davivienda.sara.entitys.TipoMovimientoCuadre().getClass(),tm.codigo);
        MovimientoCuadre mc = null;
        if (tmc != null) {
            mc = new MovimientoCuadre();
            mc.setCajero(bc.getCajero());
            mc.setDenominacion1(bc.getDenominacion1().shortValue());
            mc.setDenominacion2(bc.getDenominacion2().shortValue());
            mc.setDenominacion3(bc.getDenominacion3().shortValue());
            mc.setDenominacion4(bc.getDenominacion4().shortValue());
            mc.setDenominacion5(bc.getDenominacion5().shortValue());
            mc.setDenominacionUf(bc.getDenominacionUf().shortValue());
            mc.setFecha(bc.getBilletajeCajeroPK().getFecha());
            mc.setTipoMovimientoCuadre(tmc);
            mc.setIdUsuario("ATM");
            long valor = 0;
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
            if (valor == 0) {
                valor = (mc.getDenominacion1() * mc.getBilletes1()
                        + mc.getDenominacion2() * mc.getBilletes2()
                        + mc.getDenominacion3() * mc.getBilletes3()
                        + mc.getDenominacion4() * mc.getBilletes4()
                        + mc.getDenominacion5() * mc.getBilletes5()
                        + mc.getDenominacionUf() * mc.getBilletesUf()) * 1000;
            }
            mc.setValorMovimiento(valor);
        }
        return mc;
    }

}
