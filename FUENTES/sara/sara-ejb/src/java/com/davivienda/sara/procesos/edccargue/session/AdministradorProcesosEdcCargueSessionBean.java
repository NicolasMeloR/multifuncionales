// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.edccargue.session;

import javax.transaction.SystemException;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.Numero;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.procesos.edccargue.servicio.AdministradorProcesosEdcCargueServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local({ AdministradorProcesosEdcCargueSessionLocal.class })
@TransactionManagement(TransactionManagementType.BEAN)
public class AdministradorProcesosEdcCargueSessionBean implements AdministradorProcesosEdcCargueSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private CajeroServicio cajeroServicio;
    private AdministradorProcesosEdcCargueServicio administradorProcesosEdcCargueServicio;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    
    @PostConstruct
    public void postConstructor() {
        this.administradorProcesosEdcCargueServicio = new AdministradorProcesosEdcCargueServicio(this.em);
        this.cajeroServicio = new CajeroServicio(this.em);
    }
    
    @Override
    public Integer actualizarEdcCargue(final ArrayList nombreArchivos, final String nombreZip, final Calendar fecha) {
        final Integer registros = -1;
        return registros;
    }
    
    @Override
    public Integer registrarCicloEdcCargue(final ArrayList nombreArchivos, final String nombreZip, final Calendar fecha, final Integer estado, final boolean actualiza) {
        String[] cadenaArray = new String[2];
        Integer tamano = 0;
        Date fechaCarga = new Date();
        String nombreArchivo = "";
        Integer ciclo = 0;
        String strYear = "";
        Integer regsProceso = 0;
        Logger.getLogger("globalApp").info("Se inicia el proceso del ciclo " + Fecha.aCadena(fecha, FormatoFecha.AAAAMMDD));
        try {
            for (int i = 1; i < nombreArchivos.size(); ++i) {
                fechaCarga = fecha.getTime();
                strYear = Numero.aMoneda(Integer.valueOf(fechaCarga.getYear())).substring(2);
                cadenaArray = nombreArchivos.get(i).toString().split(";");
                nombreArchivo = cadenaArray[0];
                tamano = Cadena.aInteger(cadenaArray[1]);
                final Integer codigoCajero = Cadena.aInteger(nombreArchivo.substring(0, 4));
                if (nombreArchivo.substring(4).equals(strYear)) {}
                ciclo = Cadena.aInteger(nombreZip.substring(3, 7) + strYear);
                regsProceso += this.registrarArchivoEdcCargue(codigoCajero, nombreArchivo, tamano, ciclo, estado, actualiza);
            }
        }
        catch (IllegalArgumentException ex) {
            Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE para el archivo " + nombreArchivo + " " + ex.getMessage());
        }
        catch (Exception ex2) {
            Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE  para el archivo :" + nombreArchivo + " descripcion Error : " + ex2.getMessage());
        }
        Logger.getLogger("globalApp").info("Fin del proceso de grabar los datos en EDCCARGUE   para el  " + fechaCarga.toString() + ". registros procesados : " + regsProceso);
        return regsProceso;
    }
    
    @Override
    public Integer registrarArchivoEdcCargue(final Integer codigoCajero, final String nombreArchivo, final Integer tamano, final Integer ciclo, final Integer estado, final boolean actualiza) {
        Date fechaCarga = new Date();
        Integer regGuardados = 0;
        try {
            this.utx.begin();
            fechaCarga = Fecha.getDateHoy();
            regGuardados = this.administradorProcesosEdcCargueServicio.guardarProcesoTransmisionTiras(nombreArchivo, tamano, ciclo, fechaCarga, codigoCajero, estado, actualiza);
            this.utx.commit();
        }
        catch (IllegalArgumentException ex) {
            Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE para el archivo " + nombreArchivo + " " + ex.getMessage());
        }
        catch (Exception ex2) {
            Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE  para el archivo :" + nombreArchivo + " descripcion Error : " + ex2.getMessage());
        }
        finally {
            try {
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex3) {
                Logger.getLogger("globalApp").info(ex3.getMessage());
            }
            catch (SecurityException ex4) {
                Logger.getLogger("globalApp").info(ex4.getMessage());
            }
            catch (SystemException ex5) {
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
        }
        return regGuardados;
    }
    
    public void guardarProcesoTransmisionTiras(final Integer codigoCajero, final String nombreArchivo, final Integer tamano, final Integer ciclo, final Integer estado, final boolean actualiza) {
        Date fechaCarga = new Date();
        try {
            this.utx.begin();
            fechaCarga = Fecha.getDateHoy();
            this.administradorProcesosEdcCargueServicio.guardarProcesoTransmisionTiras(nombreArchivo, tamano, ciclo, fechaCarga, codigoCajero, estado, actualiza);
            this.utx.commit();
        }
        catch (IllegalArgumentException ex) {
            Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE para el archivo " + nombreArchivo + " " + ex.getMessage());
        }
        catch (Exception ex2) {
            Logger.getLogger("globalApp").info("Error al grabar los datos en EDCCARGUE  para el archivo :" + nombreArchivo + " descripcion Error : " + ex2.getMessage());
        }
        finally {
            try {
                if (this.utx.getStatus() != 6) {
                    this.utx.rollback();
                }
            }
            catch (IllegalStateException ex3) {
                Logger.getLogger("globalApp").info(ex3.getMessage());
            }
            catch (SecurityException ex4) {
                Logger.getLogger("globalApp").info(ex4.getMessage());
            }
            catch (SystemException ex5) {
                Logger.getLogger("globalApp").info(ex5.getMessage());
            }
        }
    }
}
