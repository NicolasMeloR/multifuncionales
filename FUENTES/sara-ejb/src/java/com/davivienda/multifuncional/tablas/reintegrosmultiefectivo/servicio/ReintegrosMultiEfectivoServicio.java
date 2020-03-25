/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.dto.ReintegrosMultiEfeDTO;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosMultifuncional;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import com.davivienda.sara.entitys.ReintegrosmultiefectivoHisto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.logging.Level;

/**
 *
 * @author P-CCHAPA
 */
public class ReintegrosMultiEfectivoServicio extends BaseEntityServicio<Reintegrosmultiefectivo> implements AdministracionTablasInterface<Reintegrosmultiefectivo> {

    public ReintegrosMultiEfectivoServicio(EntityManager em) {
        super(em, Reintegrosmultiefectivo.class);
    }

    @SuppressWarnings("unchecked")
    public Collection<Reintegrosmultiefectivo> getReintegros(Date fechaInicial, Date fechaFinal, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion {
        
        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - getReintegros fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal 
                                + " codigCajero: " + codigCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla historico: " + ReintegrosmultiefectivoHisto.class.getSimpleName());
            nombreTabla = "ReintegrosmultiefectivoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla : " + Reintegrosmultiefectivo.class.getSimpleName());
            nombreTabla = "Reintegrosmultiefectivo";
        }
        
        Date fFinalCiclo = fechaFinal;
        if (fechaInicial.equals(fechaFinal)) {
            fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
        }


        Collection<Reintegrosmultiefectivo> items = null;
        try {
            Query query = null;
            if (codigCajero == 0) {
                query = em.createNamedQuery(nombreTabla+".RangoFecha");
            } else {
                query = em.createNamedQuery(nombreTabla+".CajeroFecha");
                query.setParameter("codigocajero", codigCajero);
            }
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fFinalCiclo);

            if (tablaHisto) {
                items = new ReintegrosMultiEfeDTO(configApp.loggerApp).reintegrosMultiEfeHistoAReintegrosMultiEfe(query.getResultList());
            } else {
                items = query.getResultList();
            }           
            

            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - getReintegros items: " + items.size());

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    }
    
    
    @SuppressWarnings("unchecked")
    public Collection<ReintegrosMultifuncional> getReintegrosMultifuncional(Date fechaInicial, Date fechaFinal, Integer codigCajero) throws EntityServicioExcepcion {
                
        Date fFinalCiclo = fechaFinal;
        if (fechaInicial.equals(fechaFinal)) {
            fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
        }


        Collection<ReintegrosMultifuncional> items = null;
        try {
            Query query = null;
            if (codigCajero == 0 || codigCajero == 9999) {
                query = em.createNamedQuery("ReintegrosMultifuncional.RangoFecha");
                configApp.loggerApp.info("getReintegrosMultifuncional: RangoFecha" );
            } else {
                query = em.createNamedQuery("ReintegrosMultifuncional.CajeroFecha");
                configApp.loggerApp.info("getReintegrosMultifuncional: CajeroFecha" );
                query.setParameter("codigocajero", codigCajero);
            }
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fFinalCiclo);
            configApp.loggerApp.info("getReintegrosMultifuncional: fechaInicial " + fechaInicial);
            configApp.loggerApp.info("getReintegrosMultifuncional: fFinalCiclo " + fFinalCiclo);
             query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            items = query.getResultList();
            
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - getReintegrosMultifuncional items: " + items.size());

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    }
    
    
    public Reintegrosmultiefectivo getReintegroXLlave(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion, Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - getReintegros fechaProceso: " + fechaProceso 
                                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla historico: " + ReintegrosmultiefectivoHisto.class.getSimpleName());
            nombreTabla = "ReintegrosmultiefectivoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla : " + Reintegrosmultiefectivo.class.getSimpleName());
            nombreTabla = "Reintegrosmultiefectivo";
        }
        
        
        Reintegrosmultiefectivo reg = null;
        try {
          
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0000;
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla+".RegistroUnico");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaSistema", fechaProceso);
                query.setParameter("talon", nTran);
                
                if (tablaHisto) {
                    reg = new ReintegrosMultiEfeDTO(configApp.loggerApp).reintegrosMultiEfeHistoAReintegrosMultiEfe((ReintegrosmultiefectivoHisto) query.getSingleResult());
                } else {
                    reg = (Reintegrosmultiefectivo) query.getSingleResult();
                }
                
                configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Retornando reg : " + reg);
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error no conocido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }
    
    public Reintegrosmultiefectivo getReintegroXCuentaValor(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion,
            String numeroCuenta, Long valor, Date fechaHisto) throws EntityServicioExcepcion {

         String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - getReintegros fechaProceso: " + fechaProceso 
                                + " codigCajero: " + codigoCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla historico: " + ReintegrosmultiefectivoHisto.class.getSimpleName());
            nombreTabla = "ReintegrosmultiefectivoHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Consultando tabla : " + Reintegrosmultiefectivo.class.getSimpleName());
            nombreTabla = "Reintegrosmultiefectivo";
        }
        
        Reintegrosmultiefectivo reg = null;
        Date fechaInicial;
        Date fechaFinal;

        try {
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            Integer nTran = (numeroTransaccion != null) ? numeroTransaccion : 0000;
            fechaInicial = Fecha.getFechaFinDia(Fecha.getCalendar(Fecha.getCalendar(fechaProceso), -1)).getTime();
            fechaFinal = Fecha.getFechaFinDia(Fecha.getCalendar(fechaProceso)).getTime();
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla+".CajeroTalonCuenta");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fechaFinal);
                query.setParameter("talon", nTran);
                query.setParameter("numeroCuenta", numeroCuenta);
                query.setParameter("valor", valor);
                
                if (tablaHisto) {
                    List result = new ArrayList<Reintegrosmultiefectivo>();
                    result = query.getResultList();
                    if (result.size() > 0) {
                        reg = new ReintegrosMultiEfeDTO(configApp.loggerApp).reintegrosMultiEfeHistoAReintegrosMultiEfe((ReintegrosmultiefectivoHisto) result.get(0));
                    }                    
                } else {
                    List result = new ArrayList<Reintegrosmultiefectivo>();
                    result = query.getResultList();
                    if (result != null) {
                        if (result.size() > 0) {
                            reg = (Reintegrosmultiefectivo) result.get(0);
                        }
                    }
                }
                
                configApp.loggerApp.log(Level.INFO, "ReintegrosMultiEfectivoServicio - Retornando reg : " + reg);
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error no conocido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }
}
