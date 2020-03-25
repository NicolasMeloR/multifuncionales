/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.multifuncional.tablas.notasdebito.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.base.BaseEntityServicio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.dto.NotasDebitoMultiDTO;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import com.davivienda.sara.entitys.NotasdebitomultifuncionalHisto;
import java.util.logging.Level;

/**
 * NotasDebitoMultifuncional NotasDebitoMultiServicio
 *
 * @author P-CCHAPA
 */
public class NotasDebitoMultiServicio extends BaseEntityServicio<Notasdebitomultifuncional> implements AdministracionTablasInterface<Notasdebitomultifuncional> {

    {
    }

    public NotasDebitoMultiServicio(EntityManager em) {
        super(em, Notasdebitomultifuncional.class);
    }

    @SuppressWarnings("unchecked")
    public Collection<Notasdebitomultifuncional> getNotasDebito(Date fechaInicial, Date fechaFinal, Integer codigCajero, Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - getNotasDebito fechaInicial: " + fechaInicial + " fechaFinal: " + fechaFinal
                + " codigCajero: " + codigCajero + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaInicial.compareTo(fechaHisto) < 0 || fechaInicial.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla historico: " + NotasdebitomultifuncionalHisto.class.getSimpleName());
            nombreTabla = "NotasdebitomultifuncionalHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla : " + Notasdebitomultifuncional.class.getSimpleName());
            nombreTabla = "Notasdebitomultifuncional";
        }
        Date fFinalCiclo = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);
        Collection<Notasdebitomultifuncional> items = null;
        try {
            Query query = null;
            if (codigCajero == 0) {
                query = em.createNamedQuery(nombreTabla + ".RangoFecha");
            } else {
                query = em.createNamedQuery(nombreTabla + ".CajeroFecha");
                query.setParameter("codigocajero", codigCajero);
            }
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("fechaFinal", fFinalCiclo);
            if (tablaHisto) {
                items = new NotasDebitoMultiDTO(configApp.loggerApp).notasdebitoMultiHistoANotasdebitoMulti(query.getResultList());
            } else {
                items = query.getResultList();
            }

            configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - getReintegros items: " + items.size());

        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return items;
    }

    public Notasdebitomultifuncional getNotasDebitoXLlave(Integer codigoCajero, Date fechaProceso, Date fechaHisto) throws EntityServicioExcepcion {
        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - getNotasDebitoXLlave fechaProceso: " + fechaProceso
                + " codigCajero: " + fechaProceso + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla historico: " + NotasdebitomultifuncionalHisto.class.getSimpleName());
            nombreTabla = "NotasdebitomultifuncionalHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla : " + Notasdebitomultifuncional.class.getSimpleName());
            nombreTabla = "Notasdebitomultifuncional";
        }

        Notasdebitomultifuncional reg = null;
        try {

            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;

            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla + ".RegistroUnico");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fecha", fechaProceso);
                reg = (Notasdebitomultifuncional) query.getSingleResult();

                if (tablaHisto) {
                    reg = new NotasDebitoMultiDTO(configApp.loggerApp).notasdebitoMultiHistoANotasdebitoMulti((NotasdebitomultifuncionalHisto) query.getResultList());
                } else {
                    reg = (Notasdebitomultifuncional) query.getSingleResult();
                }

                configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Retornando reg : " + reg);
            }
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error no conocido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }

    public Notasdebitomultifuncional getNotasDebitoXCuentaValor(Integer codigoCajero, Date fechaProceso, String numeroCuenta, Long valor, Date fechaHisto) throws EntityServicioExcepcion {

        String nombreTabla = "";
        configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - getNotasDebitoXCuentaValor fechaProceso: " + fechaProceso
                + " codigCajero: " + fechaProceso + " fechaHisto: " + fechaHisto);
        boolean tablaHisto = false;
        if (fechaProceso.compareTo(fechaHisto) < 0 || fechaProceso.compareTo(fechaHisto) == 0) {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla historico: " + NotasdebitomultifuncionalHisto.class.getSimpleName());
            nombreTabla = "NotasdebitomultifuncionalHisto";
            tablaHisto = true;
        } else {
            configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Consultando tabla : " + Notasdebitomultifuncional.class.getSimpleName());
            nombreTabla = "Notasdebitomultifuncional";
        }
        
        Notasdebitomultifuncional reg = null;
        Date fechaInicial;
        Date fechaFinal;

        try {
            Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
            fechaInicial = Fecha.getFechaFinDia(Fecha.getCalendar(Fecha.getCalendar(fechaProceso), -1)).getTime();
            fechaFinal = Fecha.getFechaFinDia(Fecha.getCalendar(fechaProceso)).getTime();
            Query query = null;
            if (!cCajero.equals(9999)) {
                query = em.createNamedQuery(nombreTabla+".CajeroCuentaFechaValor");
                query.setParameter("codigoCajero", cCajero);
                query.setParameter("fechaInicial", fechaInicial);
                query.setParameter("fechaFinal", fechaFinal);
                query.setParameter("numeroCuenta", numeroCuenta);
                query.setParameter("valor", valor);
                
                if (tablaHisto) {
                    List result = new ArrayList<NotasdebitomultifuncionalHisto>();
                    result = query.getResultList();
                    if (result.size() > 0) {
                        reg = new NotasDebitoMultiDTO(configApp.loggerApp).notasdebitoMultiHistoANotasdebitoMulti((NotasdebitomultifuncionalHisto) result.get(0));
                    }                    
                } else {
                    List result = new ArrayList<Notasdebitomultifuncional>();
                    result = query.getResultList();
                    if (result != null) {
                        if (result.size() > 0) {
                            reg = (Notasdebitomultifuncional) result.get(0);
                        }
                    }
                }
            }
            
            configApp.loggerApp.log(Level.INFO, "NotasDebitoMultiServicio - Retornando reg : " + reg);
        } catch (IllegalStateException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        } catch (IllegalArgumentException ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "El parámetro no es válido ", ex);
            throw new EntityServicioExcepcion(ex);
        }catch (Exception ex) {
            configApp.loggerApp.log(java.util.logging.Level.SEVERE, "Error no conocido ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        return reg;
    }
}
