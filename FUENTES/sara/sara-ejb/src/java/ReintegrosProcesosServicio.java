package com.davivienda.sara.procesos.reintegros.servicio;

import com.davivienda.sara.tablas.reintegros.servicio.ReintegrosServicio;
import com.davivienda.sara.base.BaseEstadisticaServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.HostAtm;
import com.davivienda.sara.entitys.transaccion.SumatoriaTransaccionesHostBean;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
import com.davivienda.sara.tablas.hostatm.servicio.HostAtmServicio;

import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.Cadena;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosPK;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.constantes.TipoCuentaReintegro;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.entitys.NotasdebitoPK;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.tablas.confmodulosaplicacion.servicio.ConfModulosAplicacionServicio;
import com.davivienda.sara.tablas.notasdebito.servicio.NotasDebitoServicio;

/**
 * TransaccionEstadisticaServicio - 1/09/2008 Descripción : Estadísticas de las
 * transacciones generadas por los cajeros Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReintegrosProcesosServicio extends BaseEstadisticaServicio {

    TransaccionServicio transaccionServicio;
    private CajeroServicio cajeroServicio;
    HostAtmServicio hostAtmServicio;
    ReintegrosServicio reintegrosServicio;
    NotasDebitoServicio notasDebitoServicio;
    ConfModulosAplicacionServicio confModulosAplicacionServicio;
    //variable que maneja el rango de diferencia minima para reinetgos
    //OJO VALIDAR SI ESTE RANGO SE GUARDA EN BASE  DE DATOS
    private long valorMinimoReintegros = 9000;
    private long valorMinimoReintegrosNegativo = -9000;
    List<String> binesBanagrario = new ArrayList<String>();

    Collection<Reintegros> colleccionReintegros;

    public ReintegrosProcesosServicio(EntityManager em) {
        super(em);
        transaccionServicio = new TransaccionServicio(em);
        cajeroServicio = new CajeroServicio(em);
        hostAtmServicio = new HostAtmServicio(em);
        reintegrosServicio = new ReintegrosServicio(em);
        notasDebitoServicio = new NotasDebitoServicio(em);

    }

    private SumatoriaTransaccionesHostBean getSumatoria(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion, IllegalArgumentException {
        //   Collection vectorRegs = null;
        List result = new ArrayList<Integer>();
        SumatoriaTransaccionesHostBean reg = new SumatoriaTransaccionesHostBean();
        Query query = null;
        String strQuery
                = //        
                //                "select  obj.codigocajero , sum(obj.valor) as sumTransaccion from Transaccion obj " +
                //                " where   obj.fechatransaccion (+) between ? and ?" +
                //                "   and  obj.errortransaccion='0000' and  obj.tipotransaccion IN (21,24,20,33) "  +
                //                " group by obj.codigocajero order by obj.codigocajero" ;
                "(select   sum(obj.valorentregado) as sumTx from Transaccion obj "
                + " where obj.codigocajero= ? and  "
                + " obj.fechatransaccion (+) between ? and ?"
                + " and  obj.errortransaccion='0000' and  obj.tipotransaccion IN (21,24,20,33)) "
                + " UNION ALL "
                + " (select   sum(obj.valor) as sumTx from HostAtm obj "
                + " where obj.codigocajero= ? and   obj.fecha (+) between ? and ?)";

        BigDecimal sumTransaccion = new BigDecimal(0);
        BigDecimal sumHost = new BigDecimal(0);

        if (codigoCajero == null || codigoCajero == 0) {
            throw new IllegalArgumentException("Se debe seleccionar un cajero");
        }

        if (fechaInicial == null) {
            fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser despues de la fecha Inicial");
        }
        try {
            query = em.createNativeQuery(strQuery);

            query.setParameter(1, codigoCajero);
            query.setParameter(2, fechaInicial).setParameter(3, fechaFinal);
            query.setParameter(4, codigoCajero);
            query.setParameter(5, fechaInicial).setParameter(6, fechaFinal);
            result = query.getResultList();

//            for (Iterator it = vectorRegs.iterator(); it.hasNext();) {
//                Vector vec = (Vector) it.next();
//                sumTransaccion=(BigDecimal) vec.get(0);
//                sumHost=(BigDecimal) vec.get(1);
            if (result != null) {

                if (result.get(1).toString().contains("[null]") == false) {
                    sumHost = BigDecimal.valueOf(Cadena.aLong(result.get(1).toString().replace("[", "").replace("]", "")));
                }

                if (result.get(0).toString().contains("[null]") == false) {
                    sumTransaccion = BigDecimal.valueOf(Cadena.aLong(result.get(0).toString().replace("[", "").replace("]", "")));
                } else {
                    //esta revision se realiza para cuando no hay la tira de ese cajero no cree sobrantes creados
                    //faltaria la revision en la tabla de cargue tiras que la tira se haya transmitido    
                    sumTransaccion = sumHost;

                }

                reg.setSumTransaccion(sumTransaccion);
                reg.setSumHost(sumHost);
                reg.setDiferencia(sumHost.subtract(sumTransaccion));
                reg.setCodigoCajero(codigoCajero);
                reg.setFechaInicial(com.davivienda.utilidades.conversion.Fecha.aCadena(fechaInicial, FormatoFecha.AAAAMMDD));
                reg.setFechaFinal(com.davivienda.utilidades.conversion.Fecha.aCadena(fechaFinal, FormatoFecha.AAAAMMDD));
            }

//            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

        return reg;
    }

    public Integer calcularReintegros(Calendar fecha) {
//        if (fecha == null || fecha.after(com.davivienda.utilidades.conversion.Fecha.getCalendarHoy())) {
//            throw new IllegalArgumentException("Debe ingresar una fecha válida");
//            
//        }

        Date fFin = null;
        Integer regsProceso = 0;

        //Collection<SumatoriaTransaccionesHostBean> regs = new ArrayList<SumatoriaTransaccionesHostBean>();
        try {

            Date fInicial = fecha.getTime();

            fFin = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fecha.getTime());

            Collection<Cajero> cajeros = cajeroServicio.getCajerosActivos();

            java.util.logging.Logger.getLogger("globalApp").info("Se inicia el proceso de Calcular reintegros " + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD));

            for (Cajero cajero : cajeros) {

                java.util.logging.Logger.getLogger("globalApp").info("Inicia Sumatoria Cajero :" + cajero.getCodigoCajero().toString());
                SumatoriaTransaccionesHostBean reg = new SumatoriaTransaccionesHostBean();
                reg = (getSumatoria(cajero.getCodigoCajero(), fInicial, fFin));
                if (reg.getDiferencia().longValue() > valorMinimoReintegros) {
                    regsProceso = regsProceso + buscarGuardarReintegros(reg.getCodigoCajero(), fInicial, fFin);
                    regsProceso = regsProceso + buscarGuardarReintegrosCreados(reg.getCodigoCajero(), fInicial, fFin);

                }

                if (reg.getDiferencia().longValue() < valorMinimoReintegrosNegativo && reg.getDiferencia().longValue() != 0) {
                    regsProceso = regsProceso + buscarGuardarReintegros(reg.getCodigoCajero(), fInicial, fFin);
                    regsProceso = regsProceso + buscarGuardarReintegrosCreados(reg.getCodigoCajero(), fInicial, fFin);

                }

            }

            //   regs.add( getSumatoria(1032,fecha.getTime(),com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fecha.getTime())));
        } catch (Exception ex) {

            java.util.logging.Logger.getLogger("globalApp").info("Error Calculando reintegros " + ex.getMessage());
            regsProceso = -1;
        }

        java.util.logging.Logger.getLogger("globalApp").info("Fin del proceso de  Calculo de reintegros   para el dia " + com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD) + "  reintegros encontrados : " + regsProceso);
        return regsProceso;
    }

    /**
     * Retorna un objeto Transaccion que cumpla con los parámetros
     *
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Transaccion
     * @throws EntityServicioExcepcion
     */
    public Collection<Transaccion> getTransaccionReintegros(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        Collection<Transaccion> reg = null;
        try {
            Date fInicial = (fechaInicial != null) ? fechaInicial : com.davivienda.utilidades.conversion.Fecha.getDateHoy();
            Date fFin = fechaFinal;
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("Transaccion.TransaccionBuscarReintegros");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fInicial).setParameter("fechaFinal", fFin);
                reg = query.getResultList();
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }

    private Integer buscarGuardarReintegros(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {
        Collection<Transaccion> regsTxReintegros = null;
        HostAtm regHostAtm = new HostAtm();
        Integer regsProceso = 0;
        try {
            regsTxReintegros = getTransaccionReintegros(codigoCajero, fechaInicial, fechaFinal);
            for (Transaccion regTxReintegro : regsTxReintegros) {
                regHostAtm = hostAtmServicio.getReintegrosHost(regTxReintegro.getCajero().getCodigoCajero(), fechaInicial, fechaFinal, regTxReintegro.getTransaccionPK().getNumeroTransaccion());

                //OJO VALIDAR CANTIDAD SOLICITADA TRANSACCION  regTxReintegro.getValorSolicitado() CONTRA VALOR HOST ANALIZAR CRITERIO
                if (regHostAtm != null) {
                    guardarReintegro(regHostAtm, regTxReintegro);
                    regsProceso++;
                }
            }
        } catch (Exception ex) {

            java.util.logging.Logger.getLogger("globalApp").info("Error buscando reintegros  " + ex.getMessage());

        }

        return regsProceso;

    }

    public void guardarReintegro(HostAtm regHostAtm, Transaccion regTxReintegro) throws EntityServicioExcepcion {

        Reintegros newReintegro = new Reintegros();
        ReintegrosPK newReintegroPK = new ReintegrosPK();
        try {
            newReintegroPK.setHCodigocajero(regHostAtm.getHostAtmPK().getCodigoCajero());
            newReintegroPK.setHFechasistema(regHostAtm.getHostAtmPK().getFechaSistema());
            newReintegroPK.setHTalon(regHostAtm.getHostAtmPK().getTalon());
            newReintegro.setReintegrosPK(newReintegroPK);

            newReintegro.setHCodigoocca(regHostAtm.getCodigoOcca());
            newReintegro.setHDatostarjeta(regHostAtm.getDatosTarjeta());
            newReintegro.setHFecha(regHostAtm.getFecha());
            newReintegro.setHFiller(regHostAtm.getFiller());
            newReintegro.setHIndices(regHostAtm.getIndices());
            newReintegro.setHNumerocuenta(regHostAtm.getNumeroCuenta());
            newReintegro.setHTipotarjeta(regHostAtm.getTipoTarjeta());
            newReintegro.setHTipotransaccion(regHostAtm.getTipoTransaccion());
            newReintegro.setHValor(regHostAtm.getValor());
            newReintegro.setTCodigocajero(regTxReintegro.getTransaccionPK().getCodigoCajero());
            newReintegro.setTFechatransaccion(regTxReintegro.getTransaccionPK().getFechaTransaccion());
            newReintegro.setTNumerotransaccion(regTxReintegro.getTransaccionPK().getNumeroTransaccion());
            newReintegro.setTCodigoterminaciontransaccion(regTxReintegro.getCodigoTerminacionTransaccion());
            newReintegro.setTCodigotransaccion(regTxReintegro.getTipoTransaccion());
            newReintegro.setTCuenta(regTxReintegro.getCuenta());
            newReintegro.setTErrortransaccion(regTxReintegro.getErrorTransaccion());
            newReintegro.setTReferencia(regTxReintegro.getReferencia());
            newReintegro.setTTarjeta(regTxReintegro.getTarjeta());
            newReintegro.setTValorentregado(regTxReintegro.getValorEntregado());
            newReintegro.setTValorsolicitado(regTxReintegro.getValorSolicitado());
            newReintegro.setTTipotransaccion(regTxReintegro.getTipoTransaccion());
            newReintegro.setEstadoreintegro(EstadoReintegro.INICIADO.getEstado());
            newReintegro.setValorajustado(regTxReintegro.getValorSolicitado());
            newReintegro.setTipoCuentaReintegro(clasificarSobrante(regHostAtm.getNumeroCuenta(), regTxReintegro.getTarjeta(), regTxReintegro.getTipoTransaccion()));

            reintegrosServicio.adicionar(newReintegro);

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
//   catch (Exception ex) {
//     configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error guardando en la db el reintegro ", ex);
//         
// } 

    }
    //(Integer codigoCajero, BigDecimal valor,String cuenta,Integer tipoCuenta,String usuario,String talon)

    public void guardarReintegroNuevo(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, String talon) throws EntityServicioExcepcion {

        Reintegros newReintegro = new Reintegros();
        ReintegrosPK newReintegroPK = new ReintegrosPK();
        try {
            Cajero cajero = cajeroServicio.buscar(codigoCajero);
            Date fecha = Fecha.getDateHoy();
            newReintegroPK.setHCodigocajero(codigoCajero);
            newReintegroPK.setHFechasistema(fecha);
            newReintegroPK.setHTalon(Cadena.aInteger(talon));
            newReintegro.setReintegrosPK(newReintegroPK);

            newReintegro.setHCodigoocca(cajero.getOcca().getCodigoOcca());
            newReintegro.setHDatostarjeta(cuenta);
            newReintegro.setHFecha(fecha);
            newReintegro.setHFiller("000000");
            newReintegro.setHIndices("00");
            newReintegro.setHNumerocuenta(cuenta);
            newReintegro.setHTipotarjeta("A");
            newReintegro.setHTipotransaccion(46);
            newReintegro.setHValor(valor);
            newReintegro.setTCodigocajero(codigoCajero);
            newReintegro.setTFechatransaccion(fecha);
            newReintegro.setTNumerotransaccion(Cadena.aInteger(talon));
            newReintegro.setTCodigoterminaciontransaccion("00");
            //OJO REVISAR
            newReintegro.setTCodigotransaccion(20);
            newReintegro.setTCuenta(cuenta);
            newReintegro.setTErrortransaccion("0000");

            newReintegro.setTTarjeta(cuenta);
            newReintegro.setTValorentregado(new Long(0));
            newReintegro.setTValorsolicitado(valor);
            //OJO REVISAR
            newReintegro.setTTipotransaccion(20);
            newReintegro.setEstadoreintegro(EstadoReintegro.INICIADO.getEstado());
            newReintegro.setValorajustado(valor);
            newReintegro.setTipoCuentaReintegro(tipoCuenta);
            newReintegro.setComision("0");
            newReintegro.setConcepto("16");
            reintegrosServicio.adicionar(newReintegro);

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
//   catch (Exception ex) {
//     configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error guardando en la db el reintegro ", ex);
//         
// } 

    }

    /**
     * Retorna un objeto Reintegro que cumpla con los parámetros
     *
     * @param codigoCajero
     * @param fechaProceso
     * @param numeroTransaccion
     * @return Reintegros
     * @throws EntityServicioExcepcion
     */
    public HostAtm getReintegroXTalon(Integer codigoCajero, Date fechaInicial, Date fechaFinal, Integer numeroTransaccion) throws EntityServicioExcepcion {

        List result = new ArrayList<HostAtm>();
        HostAtm reg = null;

        try {

            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0000;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery("HostAtm.CajeroTxFecha");
                query.setParameter("codigocajero", cCajero);
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fechaFinal);
                query.setParameter("talon", nTran);

                result = query.getResultList();
                if (result != null) {

                    if (result.size() > 0) {
                        reg = (HostAtm) result.get(0);
                    }

                }

            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }

    private Integer buscarGuardarReintegrosCreados(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion {

        List result = new ArrayList<String>();

        Integer regsProceso = 0;
        Integer talon = 0;
        fechaInicial = Fecha.getDate(fechaFinal, -1);
        try {
            result = getTxReintegrosCreados(codigoCajero, fechaInicial, fechaFinal);

            if (result != null) {
                // if (result.size()<getCantidadMaxReintegros())
                //{

                for (Object strTalon : result) {

                    talon = Cadena.aInteger(strTalon.toString().replace("[", "").replace("]", ""));
                    guardarReintegroCreado(getReintegroXTalon(codigoCajero, fechaInicial, fechaFinal, talon));
                    regsProceso++;

                }
                //}

            }
        } catch (Exception ex) {

            java.util.logging.Logger.getLogger("globalApp").info("Error buscando reintegros  " + ex.getMessage());

        }

        return regsProceso;

    }

    public void guardarReintegroCreado(HostAtm regHostAtm) throws EntityServicioExcepcion {

        Reintegros newReintegro = new Reintegros();
        ReintegrosPK newReintegroPK = new ReintegrosPK();
        Integer tipoTransaccion = 20;
        try {
            //OJO REVISAR ESTA VALIDACION
            if (regHostAtm.getTipoTarjeta().equals("D")) {
                tipoTransaccion = 21;
            }

            newReintegroPK.setHCodigocajero(regHostAtm.getHostAtmPK().getCodigoCajero());
            newReintegroPK.setHFechasistema(regHostAtm.getHostAtmPK().getFechaSistema());
            newReintegroPK.setHTalon(regHostAtm.getHostAtmPK().getTalon());
            newReintegro.setReintegrosPK(newReintegroPK);

            newReintegro.setHCodigoocca(regHostAtm.getCodigoOcca());
            newReintegro.setHDatostarjeta(regHostAtm.getDatosTarjeta());
            newReintegro.setHFecha(regHostAtm.getFecha());
            newReintegro.setHFiller(regHostAtm.getFiller());
            newReintegro.setHIndices(regHostAtm.getIndices());
            newReintegro.setHNumerocuenta(regHostAtm.getNumeroCuenta());
            newReintegro.setHTipotarjeta(regHostAtm.getTipoTarjeta());
            newReintegro.setHTipotransaccion(regHostAtm.getTipoTransaccion());
            newReintegro.setHValor(regHostAtm.getValor());
            newReintegro.setTCodigocajero(regHostAtm.getHostAtmPK().getCodigoCajero());
            newReintegro.setTFechatransaccion(regHostAtm.getHostAtmPK().getFechaSistema());
            newReintegro.setTNumerotransaccion(regHostAtm.getHostAtmPK().getTalon());
            newReintegro.setTCodigoterminaciontransaccion("00");
            //OJO REVISAR
            newReintegro.setTCodigotransaccion(tipoTransaccion);
            newReintegro.setTCuenta(regHostAtm.getNumeroCuenta());
            newReintegro.setTErrortransaccion("0000");

            newReintegro.setTTarjeta(regHostAtm.getDatosTarjeta());
            newReintegro.setTValorentregado(new Long(0));
            newReintegro.setTValorsolicitado(regHostAtm.getValor());
            //OJO REVISAR
            newReintegro.setTTipotransaccion(tipoTransaccion);
            newReintegro.setEstadoreintegro(EstadoReintegro.REINTEGROTIPOCREADO.getEstado());
            newReintegro.setValorajustado(regHostAtm.getValor());
            newReintegro.setTipoCuentaReintegro(clasificarSobrante(regHostAtm.getNumeroCuenta(), regHostAtm.getDatosTarjeta(), tipoTransaccion));

            reintegrosServicio.adicionar(newReintegro);

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
//   catch (Exception ex) {
//     configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error guardando en la db el reintegro ", ex);
//         
// } 

    }

    private List<String> getTxReintegrosCreados(Integer codigoCajero, Date fechaInicial, Date fechaFinal) throws EntityServicioExcepcion, IllegalArgumentException {
        //   Collection vectorRegs = null;
        List result = new ArrayList<String>();
        //Collection<HostAtm>  result = null;

        Query query = null;
        String strQuery
                = "select  obj.Talon  from HostAtm obj "
                + " where obj.codigocajero= ? and   obj.fecha (+) between ? and ? and "
                + " obj.Talon NOT IN"
                + "(select  obj.numerotransaccion from Transaccion obj "
                + " where obj.codigocajero= ? and  "
                + " obj.fechatransaccion (+) between ? and ? ) ";

        if (codigoCajero == null || codigoCajero == 0) {
            throw new IllegalArgumentException("Se debe seleccionar un cajero");
        }

        if (fechaInicial == null) {
            fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
        }
        if (fechaFinal == null) {
            fechaFinal = fechaInicial;
        }
        if (fechaFinal.before(fechaInicial)) {
            throw new IllegalArgumentException("La fecha final debe ser despues de la fecha Inicial");
        }
        try {
            query = em.createNativeQuery(strQuery);

            query.setParameter(1, codigoCajero);
            query.setParameter(2, fechaInicial).setParameter(3, fechaFinal);
            query.setParameter(4, codigoCajero);
            query.setParameter(5, fechaInicial).setParameter(6, fechaFinal);
            result = query.getResultList();

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }

        return result;
    }

    private Integer clasificarSobrante(String NumeroCuenta, String NumeroTarjeta, Integer tipoTransaccion) {

//    OtraRed(0, "Otra Red"),
//    CuentaCorriente(1, "Cuenta Corriente"),
//    CuentaAhorros(3, "Cuenta Ahorros"),
//    TarjetaCredito(4, "Tarjeta Credito"),
//    VisaPagos(5, "Visa Pagos"),
//    Bancafe(6, "Bancafe"),
//    Banagrario(7, "Banagrario"),
//    NoDefinida(10, "NoDefinida"),
        Integer tipoSobrante = TipoCuentaReintegro.getTipoCuentaReintegro(TipoCuentaReintegro.NoDefinida.toString()).codigo;
        //PRIMERO SE AVERIGUA SI ES TARJETA CREDITO
        if (NumeroCuenta.substring(0, 4).equals("0036")) {
            //tambien se valida si tipoTransaccion=24

            tipoSobrante = TipoCuentaReintegro.getTipoCuentaReintegro(TipoCuentaReintegro.TarjetaCredito.toString()).codigo;

        } else //LUEGO Se consulta si ES davivienda o no
        {
            if (tipoTransaccion == 20) {
                tipoSobrante = clasificarTipoCuentaDavivienda(NumeroCuenta);
            } //si NO es davivienda SE CONSULTA EN DB
            else {
                tipoSobrante = clasificarTipoCuentaOtraRed(NumeroCuenta.substring(0, 6));
            }
        }

        return tipoSobrante;
    }

    /*
   clasifica en una cuenta davivienda si es de ahorros o corriente
     */
    private Integer clasificarTipoCuentaDavivienda(String NumeroCuenta) {

        Integer tipoCuenta = TipoCuentaReintegro.getTipoCuentaReintegro(TipoCuentaReintegro.CuentaAhorros.toString()).codigo;
        //REVISAR SI ES 6 o 4 Y QUE ESTE EN LA POSICION CORRECTA
        if (NumeroCuenta.substring(4, 5).equals("6")) {
            tipoCuenta = TipoCuentaReintegro.getTipoCuentaReintegro(TipoCuentaReintegro.CuentaCorriente.toString()).codigo;
        }
        return tipoCuenta;
    }

    private Integer clasificarTipoCuentaOtraRed(String binCuenta) {

        cargarBinesBanagrario();
        Integer tipoCuenta = TipoCuentaReintegro.getTipoCuentaReintegro(TipoCuentaReintegro.OtraRed.toString()).codigo;

        if (binCuenta.equals("403899")) {
            tipoCuenta = TipoCuentaReintegro.getTipoCuentaReintegro(TipoCuentaReintegro.VisaPagos.toString()).codigo;
        } else if (binesBanagrario.contains(binCuenta)) {
            tipoCuenta = TipoCuentaReintegro.getTipoCuentaReintegro(TipoCuentaReintegro.Banagrario.toString()).codigo;
        }

        return tipoCuenta;
    }

    public void actualizar(Reintegros objetoModificado) throws EntityServicioExcepcion {
        reintegrosServicio.actualizar(objetoModificado);
    }

    private void cargarBinesBanagrario() {
        //esto se haria recorriendo una base de datos lo mismo que para el bin de visa pagos
        binesBanagrario.add("448185");
        binesBanagrario.add("448186");
        binesBanagrario.add("448187");
        binesBanagrario.add("480258");
        binesBanagrario.add("449744");
    }

    public void actualizarNotaDebito(Notasdebito objetoModificado) throws EntityServicioExcepcion {

        notasDebitoServicio.actualizar(objetoModificado);

    }

    public void guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario) throws EntityServicioExcepcion {

        Notasdebito newNotaDebito = new Notasdebito();
        NotasdebitoPK newNotaDebitoPK = new NotasdebitoPK();
        try {
            Cajero cajero = cajeroServicio.buscar(codigoCajero);
            Date fecha = Fecha.getDateHoy();
            newNotaDebitoPK.setCodigocajero(codigoCajero);
            newNotaDebitoPK.setFecha(fecha);

            newNotaDebito.setNotasdebitoPK(newNotaDebitoPK);
            newNotaDebito.setUsuariocrea(usuario);

            newNotaDebito.setCodigoocca(cajero.getOcca().getCodigoOcca());
            newNotaDebito.setNumerocuenta(cuenta);
            newNotaDebito.setValor(valor);
            newNotaDebito.setValorajustado(valor);
            newNotaDebito.setEstado(EstadoReintegro.INICIADO.getEstado());
            newNotaDebito.setTipocuenta(tipoCuenta);
            newNotaDebito.setConcepto("16");
            newNotaDebito.setComision("0");
            notasDebitoServicio.adicionar(newNotaDebito);

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
//   catch (Exception ex) {
//     configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error guardando en la db el reintegro ", ex);
//         
// } 

    }

    public void guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, Date fecha) throws EntityServicioExcepcion {

        Notasdebito newNotaDebito = new Notasdebito();
        NotasdebitoPK newNotaDebitoPK = new NotasdebitoPK();
        try {
            Cajero cajero = cajeroServicio.buscar(codigoCajero);
            newNotaDebitoPK.setCodigocajero(codigoCajero);
            newNotaDebitoPK.setFecha(fecha);

            newNotaDebito.setNotasdebitoPK(newNotaDebitoPK);
            newNotaDebito.setUsuariocrea(usuario);

            newNotaDebito.setCodigoocca(cajero.getOcca().getCodigoOcca());
            newNotaDebito.setNumerocuenta(cuenta);
            newNotaDebito.setValor(valor);
            newNotaDebito.setValorajustado(valor);
            newNotaDebito.setEstado(EstadoReintegro.INICIADO.getEstado());
            newNotaDebito.setTipocuenta(tipoCuenta);

            notasDebitoServicio.adicionar(newNotaDebito);

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
    }

    public Notasdebito guardarNotaDebito(Integer codigoCajero, Long valor, String cuenta, Integer tipoCuenta, String usuario, Date fecha, int estado) throws EntityServicioExcepcion {

        Notasdebito newNotaDebito = new Notasdebito();
        NotasdebitoPK newNotaDebitoPK = new NotasdebitoPK();
        try {
            Cajero cajero = cajeroServicio.buscar(codigoCajero);
            newNotaDebitoPK.setCodigocajero(codigoCajero);
            newNotaDebitoPK.setFecha(fecha);

            newNotaDebito.setNotasdebitoPK(newNotaDebitoPK);
            newNotaDebito.setUsuariocrea(usuario);

            newNotaDebito.setCodigoocca(cajero.getOcca().getCodigoOcca());
            newNotaDebito.setNumerocuenta(cuenta);
            newNotaDebito.setValor(valor);
            newNotaDebito.setValorajustado(valor);
            newNotaDebito.setEstado(estado);
            newNotaDebito.setTipocuenta(tipoCuenta);

            notasDebitoServicio.adicionar(newNotaDebito);

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return newNotaDebito;
    }

    private Integer getCantidadMaxReintegros() {
        Integer intMaxReintegro = new Integer("0");

        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.MAX_CANT_REINTEGROSXCAJERO");
            registroEntityConsulta = confModulosAplicacionServicio.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.MAX_VALOR_REINTEGRO"));
            intMaxReintegro = com.davivienda.utilidades.conversion.Cadena.aInteger(registroEntityConsulta.getValor());

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error obteniendo la cantidad maxima de Reintegros : " + ex.getMessage());
            intMaxReintegro = new Integer("50");
        }
        return intMaxReintegro;

    }

    public void guardarNotaDebito(Notasdebito newNotaDebito) throws EntityServicioExcepcion {
        notasDebitoServicio.adicionar(newNotaDebito);
    }

}
