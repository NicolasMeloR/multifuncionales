// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.administracion.programadortareas.session;

import javax.ejb.Timeout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.io.FileNotFoundException;
import com.davivienda.utilidades.archivo.ProcesosArchivo;
import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.utilidades.edc.Edc;
import java.util.logging.Logger;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.io.Serializable;
import com.davivienda.sara.config.SaraConfig;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import javax.ejb.EJB;
import com.davivienda.sara.procesos.diarioelectronico.session.AdministradorProcesosSessionLocal;
import com.davivienda.sara.procesos.diarioelectronico.servicio.ProcesadorDiarioElectronicoServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Local;
import javax.ejb.Stateless;
import com.davivienda.sara.administracion.programadortareas.ProgramadorTareas;

@Stateless
@Local({ ProgramadorTareasDiarioElectronicoSession.class })
public class ProgramadorTareasDiarioElectronicoSessionBean extends ProgramadorTareas implements ProgramadorTareasDiarioElectronicoSession
{
    @Resource
    private SessionContext ctx;
    @PersistenceContext
    private EntityManager em;
    ProcesadorDiarioElectronicoServicio servicio;
    @EJB
    private AdministradorProcesosSessionLocal diarioElectronicoCarga;
    @EJB
    private AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;
    @EJB
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    
    @PostConstruct
    public void postConstructor() {
        this.servicio = new ProcesadorDiarioElectronicoServicio(this.em);
    }
    
    @Override
    public void configuraProgramador(final SaraConfig appConfiguracion) {
        super.appConfiguracion = appConfiguracion;
        final long horaInicio = this.getHoraInicioProgramador(appConfiguracion.HORA_INICIO_TRANSMISION_DIARIOS_ELECTRONICOS);
        final TimerService timerService = this.ctx.getTimerService();
        final Timer timerProcesoDiarioElectronico = timerService.createTimer(horaInicio, 86400000L, (Serializable)null);
        appConfiguracion.loggerApp.info("Se ha iniciado el Timer para DiarioElectronico, la primera ejecuci\u00f3n ser\u00e1 el " + Fecha.aCadena(timerProcesoDiarioElectronico.getNextTimeout(), FormatoFecha.FECHA_HORA) + " , " + horaInicio + " milisegundos");
    }
    
    @Timeout
    public void ejecutaTimerDiarioElectronico(final Timer timer) {
        String nombreArchivo = "";
        String directorioArchivo = "";
        if (this.ConsultarEstadoCargue().equals("0")) {
            try {
                this.CambiarEstadoCargue("1");
                Logger.getLogger("globalApp").info("Se ejecuta el Timer DiarioElectronico " + Fecha.aCadena(Fecha.getCalendarHoy(), FormatoFecha.FECHA_HORA));
                nombreArchivo = Edc.getNombreArchivoCiclosComprimido(Fecha.getCalendarHoy());
                Logger.getLogger("globalApp").info("el nombre de el archivo a descomprimir es :" + nombreArchivo);
                directorioArchivo = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.directorioUpLoad"))).getValor().trim();
                Logger.getLogger("globalApp").info("el directorio de el archivo a descomprimir es :" + directorioArchivo);
                final ArrayList lstArchivos = ProcesosArchivo.unzipArray(directorioArchivo, nombreArchivo);
                this.diarioElectronicoCarga.cargarCiclo(Fecha.getCalendarHoy());
                Logger.getLogger("globalApp").info("Se ejecuta el Timer DiarioElectronico " + Fecha.aCadena(Fecha.getCalendarHoy(), FormatoFecha.FECHA_HORA));
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger("globalApp").info("No se puede encontrar el archivo ,descripcion exception : " + ex.getMessage());
            }
            catch (SecurityException ex2) {
                Logger.getLogger("globalApp").info("No se puede descomprimir el archivo por permisos ,descripcion exception : " + ex2.getMessage());
            }
            catch (Exception ex3) {
                Logger.getLogger("globalApp").log(Level.SEVERE, "No se puede descomprimir el archivo exception del tipo :" + ex3.getMessage(), ex3);
            }
            finally {
                this.CambiarEstadoCargue("0");
                try {
                    Logger.getLogger("globalApp").info("Pr\u00f3xima ejecuci\u00f3n del Timer DiarioElectronico " + Fecha.aCadena(timer.getNextTimeout(), FormatoFecha.FECHA_HORA));
                }
                catch (Exception ex4) {
                    Logger.getLogger("globalApp").info("No se puede obtener la  ejecuci\u00f3n del Timer DiarioElectronico ,descripcion exception : " + ex4.getMessage());
                }
            }
        }
        else {
            Logger.getLogger("globalApp").info("No Se ejecuta el Timer DiarioElectronico " + Fecha.aCadena(Fecha.getCalendarHoy(), FormatoFecha.FECHA_HORA) + "debido a que el indicador de caraga de diarios electronicos esta en proceso");
        }
    }
    
    private String ConsultarEstadoCargue() {
        String strEstadoCargue = "0";
        try {
            strEstadoCargue = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"))).getValor().trim();
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error obteniendo estado cargue : " + ex.getMessage());
            strEstadoCargue = "0";
        }
        return strEstadoCargue;
    }
    
    private void CambiarEstadoCargue(final String strEstado) {
        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            registroEntityConsulta = this.confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            final ConfModulosAplicacion registroEntity = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
            registroEntity.setValor(strEstado);
            registroEntity.setDescripcion(registroEntityConsulta.getDescripcion());
            this.confModulosAplicacionSession.actualizar(registroEntity);
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error cambiando estado cargue : " + ex.getMessage());
        }
    }
}
