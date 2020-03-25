/*
 * ProcesoCuadreCifrasServicio.java
 *
 * Fecha       :16/09/2007, 12:20:44 AM
 *
 * Descripción :
 *
 * @author     :jjvargas
 *
 */

package com.davivienda.sara.procesos.cuadrecifras.servicio;

import com.davivienda.sara.base.BaseServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.conceptomovimientocuadre.servicio.ConceptoMovimientoCuadreServicio;
import com.davivienda.sara.tablas.movimientocuadre.servicio.MovimientoCuadreServicio;
import com.davivienda.sara.tablas.tipomovimientocuadre.servicio.TipoMovimientoCuadreServicio;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.ConceptoMovimientoCuadre;
import com.davivienda.sara.entitys.MovimientoCuadre;
import com.davivienda.sara.entitys.TipoMovimientoCuadre;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;

/**
 *
 * @author jjvargas
 */
public class ProcesoCuadreCifrasServicio extends BaseServicio{
    
    MovimientoCuadreServicio movimientoCuadreServicio ;
    CajeroServicio cajeroServicio;
    TipoMovimientoCuadreServicio tipoMovimientoCuadreServicio;
    ConceptoMovimientoCuadreServicio conceptoMovimientoCuadreCifrasServicio;
        //OJO PONER EN UNA VARIABLE GLOBAL
     //se utiliza para verificar que en el reporte no se muestren diferencias menores al valor
    private long limiteDiferencia= -1000;
    
    /**
     * Crea una nueva instancia de ProcesoCuadreCifrasServicio
     * @param em
     */
    public ProcesoCuadreCifrasServicio(EntityManager em) {
        movimientoCuadreServicio = new MovimientoCuadreServicio(em);
        cajeroServicio = new CajeroServicio(em);
        tipoMovimientoCuadreServicio = new TipoMovimientoCuadreServicio(em);
        conceptoMovimientoCuadreCifrasServicio = new ConceptoMovimientoCuadreServicio(em);
    }
    
    /**
     * Genera los movimientos de cuadre de cajeros
     * Crea el movimiento de Día sgte para la fecha dada y el de provisión inicial para el sgte día
     * @param codigoCajero
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
//     */
//    public void cuadrarCajero(Integer codigoCajero) throws EntityServicioExcepcion {
//        cuadrarCajero(codigoCajero, Fecha.getCalendarAyer());
//    }
    
    
       public void cuadrarCajeroProceso(Cajero cajero, Calendar fecha,TipoMovimientoCuadre tipoMovimientoCuadreNew) throws EntityServicioExcepcion {
        Collection<MovimientoCuadre> regs = movimientoCuadreServicio.getMovimientoCuadreCifras(cajero.getCodigoCajero(),fecha,fecha);
        boolean actualizar = true;
        long valorDiaSgte = -1;
        long valorDiaSgteIdo =-1;
        long valorPagadoIdo = 0;
        long valordiferenciadiasgte=-1;
        long auxvalordiferenciadiasgte=-1;
        long  donaciones=0;
//        long  dispensadoContadores=-1;
        long  dispensado=0;
        long  diferenciaSobrante=0;
       

        Integer tipoMovimientoCuadre=0;
       
        for (MovimientoCuadre movimientoCuadre : regs) {
            //TENER EN CUENTA getModoAfectarCuadre PARA AJUSTES
//            if ( movimientoCuadre.getTipoMovimientoCuadre().getModoAfectarCuadre() == 1){
          
            tipoMovimientoCuadre=movimientoCuadre.getTipoMovimientoCuadre().getCodigoTipoMovimientoCuadre();
                    //codigoCajero=movimientoCuadre.getCajero().getCodigoCajero();
                    com.davivienda.sara.constantes.TipoMovimientoCuadre consultaAjuste=com.davivienda.sara.constantes.TipoMovimientoCuadre.AJUSTE_AUMENTO_PROVISION;
                    consultaAjuste=com.davivienda.sara.constantes.TipoMovimientoCuadre.getTipoMovimientoCuadre(tipoMovimientoCuadre);
                 switch (consultaAjuste)
                        {
                      case PROVISION_DIA_SIGUIENTE:
                               valorDiaSgte=movimientoCuadre.getValorMovimiento();
                        break;

                        case PROVISION_DIA_SIGUIENTE_IDO:
                               valorDiaSgteIdo=movimientoCuadre.getValorMovimiento();
                        break;

                        case PAGADO_IDO:
                             valorPagadoIdo=movimientoCuadre.getValorMovimiento();
                        break;
                        case DISPENSADO:
                                dispensado=movimientoCuadre.getValorMovimiento();
//                                dispensadoContadores = (movimientoCuadre.getDenominacion1() * movimientoCuadre.getBilletes1() +
//                                movimientoCuadre.getDenominacion2() * movimientoCuadre.getBilletes2() +
//                                movimientoCuadre.getDenominacion3() * movimientoCuadre.getBilletes3() +
//                                movimientoCuadre.getDenominacion4() * movimientoCuadre.getBilletes4()) * 1000;
                           break;
                         case DONACIONES:
                             donaciones=movimientoCuadre.getValorMovimiento();
                        break;   
                        default:

                        break;
                       }
//            }
        }
        valordiferenciadiasgte=-1;
        auxvalordiferenciadiasgte=-1;
        //valordiferenciadiasgte=valorDiaSgte-valorDiaSgteIdo;

                if (valorDiaSgte==auxvalordiferenciadiasgte )
                {

                       valordiferenciadiasgte=0;


                }
                else
                {
                    //filtro los cajeros que no cortaron
                    if(valorDiaSgte==0 )
                       {
                               if(valorPagadoIdo!=0 && valorDiaSgteIdo==-1 )
                                {
                                     valordiferenciadiasgte=valorPagadoIdo* -1;

                                }
                               else
                               {
                                    valordiferenciadiasgte = 0;
                               }
                        }
                        else
                        {

                                 
                                 auxvalordiferenciadiasgte=valorDiaSgte-valorDiaSgteIdo;
                                  if (auxvalordiferenciadiasgte<limiteDiferencia )
                                        {
                                         valordiferenciadiasgte = auxvalordiferenciadiasgte;
                                        }
                                   else
                                    {
                                          //diferenciadiasgte!=0
                                        if (auxvalordiferenciadiasgte > 2  )
                                        {
                                         valordiferenciadiasgte = auxvalordiferenciadiasgte;
                                        }
                                    }
                            }
                 }
        
             //OJO SOLO PARA PRUEBAS  VALIDAR RESULTADO    DIC03 TENER EN CUENTA PARA EL DALI 10
        
//           if(dispensado!=0 && valorPagadoIdo!=0)
//             {
//              diferenciaSobrante=dispensado-(valorPagadoIdo-donaciones);
//              if(diferenciaSobrante+valordiferenciadiasgte!=0)
//              valordiferenciadiasgte=valordiferenciadiasgte+diferenciaSobrante;
//                    
//             }
        if(valordiferenciadiasgte==1 ||valordiferenciadiasgte==-1 )
        {
            valordiferenciadiasgte = 0;
        }

    
            actualizar = false;
            MovimientoCuadre mcDiaSgte = new MovimientoCuadre();
            mcDiaSgte.setCajero(cajero);
          
            
            mcDiaSgte.setTipoMovimientoCuadre(tipoMovimientoCuadreNew);
            mcDiaSgte.setFecha(fecha.getTime());
        
        mcDiaSgte.setFechaAjuste(Fecha.getDateHoy());
        mcDiaSgte.setIdUsuario("ATM");
     
//        if(dispensadoContadores!=-1 && dispensado!=-1)
//         {
//           valordiferenciadiasgte=valordiferenciadiasgte+dispensado-dispensadoContadores;
//         }

        mcDiaSgte.setValorMovimiento(valordiferenciadiasgte);

        movimientoCuadreServicio.grabarMovimientoCuadre(mcDiaSgte, actualizar);
      
    }

    
//    /**
//     * Genera los movimientos de cuadre de cajeros
//     * Crea el movimiento de Día sgte para la fecha dada y el de provisión inicial para el sgte día
//     * @param codigoCajero
//     * @param fecha
//     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
//     */
//    public void cuadrarCajero(Integer codigoCajero, Calendar fecha) throws EntityServicioExcepcion {
//        Collection<MovimientoCuadre> regs = movimientoCuadreServicio.getMovimientoCuadreCifras(codigoCajero,fecha,fecha);
//        boolean actualizar = true;
//        long valorDiaSgte = 0;
//        ConceptoMovimientoCuadre conceptoNormal = conceptoMovimientoCuadreCifrasServicio.buscar(new Integer(0)) ;
//        for (MovimientoCuadre movimientoCuadre : regs) {
//            if ( movimientoCuadre.getTipoMovimientoCuadre().getModoAfectarCuadre() == 1){
//                switch (movimientoCuadre.getTipoMovimientoCuadre().getOperador()) {
//                case 1: valorDiaSgte += movimientoCuadre.getValorMovimiento();
//                break;
//                case -1: valorDiaSgte -= movimientoCuadre.getValorMovimiento();
//                break;
//                }
//            }
//        }
//        MovimientoCuadre mcDiaSgte = movimientoCuadreServicio.getMovimientoCuadre(codigoCajero, fecha,com.davivienda.sara.constantes.TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE_REAL.codigo);
//        java.util.logging.Logger.getLogger("globalApp").finest(
//                "Cajero : " + codigoCajero + "Fecha : " +
//                Fecha.aCadena(fecha, FormatoFecha.DEFECTO_SEPARADOR_GUION.formato) +
//                "Mov :" + com.davivienda.sara.constantes.TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE_REAL);
//        MovimientoCuadre mcInicioProv = movimientoCuadreServicio.getMovimientoCuadre(codigoCajero, Fecha.getCalendar(fecha,1) , com.davivienda.sara.constantes.TipoMovimientoCuadre.PROVISION_INICIAL_REAL.codigo);
//        java.util.logging.Logger.getLogger("globalApp").finest(
//                "Cajero : " + codigoCajero +
//                "Fecha : " + Fecha.aCadena(fecha,FormatoFecha.DEFECTO_SEPARADOR_GUION.formato) +
//                "Mov :" + com.davivienda.sara.constantes.TipoMovimientoCuadre.PROVISION_INICIAL_REAL);
//        if (mcDiaSgte == null) {
//            actualizar = false;
//            mcDiaSgte = new MovimientoCuadre();
//            Cajero cajero = cajeroServicio.buscar(codigoCajero);
//            TipoMovimientoCuadre tipoMovimientoCuadre = tipoMovimientoCuadreServicio.buscar(new Integer(com.davivienda.sara.constantes.TipoMovimientoCuadre.PROVISION_DIA_SIGUIENTE_REAL.codigo));
//            mcDiaSgte.setCajero(cajero);
//            mcDiaSgte.setTipoMovimientoCuadre(tipoMovimientoCuadre);
//            mcDiaSgte.setConceptoMovimientoCuadre(conceptoNormal);
//            mcDiaSgte.setFecha(fecha.getTime());
//        }
//        mcDiaSgte.setFechaAjuste(Fecha.getDateHoy());
//        mcDiaSgte.setIdUsuario("ATM");
//        mcDiaSgte.setValorMovimiento(valorDiaSgte);
//        
//        movimientoCuadreServicio.grabarMovimientoCuadre(mcDiaSgte, actualizar);
//        actualizar = true;
//        if(mcInicioProv == null) {
//            actualizar = false;
//            mcInicioProv = new MovimientoCuadre();
//            TipoMovimientoCuadre tipoMovimientoCuadre = tipoMovimientoCuadreServicio.buscar(com.davivienda.sara.constantes.TipoMovimientoCuadre.PROVISION_INICIAL_REAL.codigo);
//            mcInicioProv.setCajero(mcDiaSgte.getCajero());
//            mcInicioProv.setTipoMovimientoCuadre(tipoMovimientoCuadre);
//            mcInicioProv.setConceptoMovimientoCuadre(conceptoNormal);            
//            mcInicioProv.setFecha(Fecha.getDate(mcDiaSgte.getFecha(),1));
//        }
//        mcInicioProv.setFechaAjuste(Fecha.getDateHoy());
//        mcInicioProv.setIdUsuario("ATM");
//        mcInicioProv.setValorMovimiento(valorDiaSgte);
//        movimientoCuadreServicio.grabarMovimientoCuadre(mcInicioProv, actualizar);
//    }
//    
    
    /**
     * Genera los movimientos de cuadre de cajeros
     * Crea el movimiento de Día sgte para la fecha dada y el de provisión inicial para el sgte día
     * @param cajero
     * @param fecha
     * @throws com.davivienda.adminatm.base.excepcion.EntityServicioExcepcion
     */
      public void cuadrarCajero(Cajero cajero, Calendar fecha) throws EntityServicioExcepcion {
         TipoMovimientoCuadre tipoMovimientoCuadreNew = tipoMovimientoCuadreServicio.buscar(new Integer(com.davivienda.sara.constantes.TipoMovimientoCuadre.DIFERENCIAS.codigo));
        cuadrarCajeroProceso(cajero,fecha,tipoMovimientoCuadreNew);
    }

    
    public void cuadrarCajero(Cajero cajero, Date fecha) throws EntityServicioExcepcion {
        cuadrarCajero(cajero, Fecha.getCalendar(fecha));
    }
    
    /**
     * Recorre todos los movimientos de los cajeros y genera los registros de Dia Sgte e Inicio Real
     * @param fecha 
     * @return 
     */
    public Integer cudrarCajero(Calendar fecha) throws EntityServicioExcepcion { 
              Integer regProcesado = 0;

        //OJO SE CAMBIO POR GETTODOS
       // Collection<Cajero> regs = cajeroServicio.buscarTodos();
        Collection<Cajero> regs = cajeroServicio.getCajerosActivos();
        //TipoMovimientoCuadre tipoMovimientoCuadreNew = tipoMovimientoCuadreServicio.buscar(new Integer(com.davivienda.sara.constantes.TipoMovimientoCuadre.DIFERENCIAS.codigo));
         configApp.loggerApp.log(Level.INFO, "Inicio Proceso cuadre {0}", Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"));
        for (Cajero item : regs) {
            try {
                //configApp.loggerApp.info("CUADRANDO CAJERO "+ item.getCodigoCajero());
                cuadrarCajero(item, fecha);
                regProcesado++;
            } catch (EntityServicioExcepcion ex) {
                configApp.loggerApp.log(java.util.logging.Level.SEVERE,
                        "No se procesará el cajero " + item.getCodigoCajero() + ex.getMessage(), ex);
            }
        }
         configApp.loggerApp.log(Level.INFO, "Fin Proceso cuadre {0} con numero de registros: {1}", 
                            new Object[]{Fecha.aCadena(Fecha.getCalendarHoy(), "HHmmss"), regProcesado});

        return regProcesado;
    }
    
}
