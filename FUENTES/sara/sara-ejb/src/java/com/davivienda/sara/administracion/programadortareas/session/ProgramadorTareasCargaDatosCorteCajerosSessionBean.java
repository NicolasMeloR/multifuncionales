// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.administracion.programadortareas.session;

import com.davivienda.sara.entitys.config.ConfModulosAplicacionPK;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import javax.ejb.Timeout;
import java.util.Calendar;
import java.util.logging.Level;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.util.logging.Logger;
import java.io.Serializable;
import com.davivienda.sara.config.SaraConfig;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import javax.ejb.EJB;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasCargasSessionLocal;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Local;
import javax.ejb.Stateless;
import com.davivienda.sara.administracion.programadortareas.ProgramadorTareas;

@Stateless
@Local({ ProgramadorTareasCargaDatosCorteCajerosSession.class })
public class ProgramadorTareasCargaDatosCorteCajerosSessionBean extends ProgramadorTareas implements ProgramadorTareasCargaDatosCorteCajerosSession
{
    @Resource
    private SessionContext ctx;
    @PersistenceContext
    private EntityManager em;
    @EJB
    private CuadreCifrasCargasSessionLocal cuadreCifrasCargas;
    @EJB
    private ProcesoCuadreCifrasSessionLocal procesoCuadreCifrasSessionLocal;
    @EJB
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    
    @Override
    public void configuraProgramador(final SaraConfig appConfiguracion) {
        super.appConfiguracion = appConfiguracion;
        final long horaInicio = this.getHoraInicioProgramador(appConfiguracion.HORA_INICIO_TRANSMISION_ARCHIVOS_CORTE);
        final TimerService timerService = this.ctx.getTimerService();
        final Timer timerProcesoDatosCorte = timerService.createTimer(horaInicio, 86400000L, (Serializable)null);
        Logger.getLogger("globalApp").info("Se ha iniciado el Timer para la carga de archivos de datos de corte cajeros, la primera ejecuci\u00f3n ser\u00e1 el " + Fecha.aCadena(timerProcesoDatosCorte.getNextTimeout(), FormatoFecha.FECHA_HORA) + " , " + horaInicio + " milisegundos");
    }
    
    @Timeout
    public void ejecutaTimer(final Timer timer) {
        if (this.ConsultarEstadoCargue().equals("0")) {
            try {
                this.CambiarEstadoCargue("1");
                Calendar fechaProceso = Fecha.getCalendarHoy();
                Logger.getLogger("globalApp").info("Se ejecuta el Timer Carga Archivos Corte Cajeros " + Fecha.aCadena(Fecha.getCalendarHoy(), FormatoFecha.FECHA_HORA));
                Logger.getLogger("globalApp").info("Tarea 1 Cargar Archivo Corte " + Fecha.aCadena(fechaProceso, FormatoFecha.FECHA_HORA));
                this.cuadreCifrasCargas.cargarArchivoCorte(fechaProceso, true);
                Logger.getLogger("globalApp").info("Tarea 2 Cargar Archivo Totales Egresos " + Fecha.aCadena(Fecha.getCalendarHoy(), FormatoFecha.FECHA_HORA));
                this.cuadreCifrasCargas.cargarArchivoTotalesEgresos(fechaProceso, true);
                Logger.getLogger("globalApp").info("Tarea 3 Cargar Archivo provisiones " + Fecha.aCadena(Fecha.getCalendarHoy(), FormatoFecha.FECHA_HORA));
                this.cuadreCifrasCargas.cargarArchivoProvisiones(fechaProceso, true);
                Logger.getLogger("globalApp").info("Tarea cuadrea Automatico " + Fecha.aCadena(Fecha.getCalendarHoy(), FormatoFecha.FECHA_HORA));
                fechaProceso = Fecha.getCalendar(-1);
                this.procesoCuadreCifrasSessionLocal.procesarCuadreCifras(fechaProceso);
            }
            catch (SecurityException ex) {
                Logger.getLogger("globalApp").info("No se puede leer el archivo por permisos ,descripcion exception : " + ex.getMessage());
            }
            catch (Exception ex2) {
                Logger.getLogger("globalApp").log(Level.SEVERE, "No se pudo ejecutar el proceso de carga archivos o  el cuadre automatico:" + ex2.getMessage(), ex2);
            }
            finally {
                Logger.getLogger("globalApp").info("Pr\u00f3xima ejecuci\u00f3n del Timer Carga Archivos Corte Cajeros " + Fecha.aCadena(timer.getNextTimeout(), FormatoFecha.FECHA_HORA));
                this.CambiarEstadoCargue("0");
            }
        }
        else {
            Logger.getLogger("globalApp").info("No Se ejecuta el Timer Carga Archivos cuadre cajeros " + Fecha.aCadena(Fecha.getCalendarHoy(), FormatoFecha.FECHA_HORA) + "debido a que el indicador de caraga de diarios electronicos esta en proceso");
        }
    }
    
    private String ConsultarEstadoCargue() {
        String strEstadoCargue = "0";
        try {
            strEstadoCargue = ((ConfModulosAplicacion)this.em.find((Class)ConfModulosAplicacion.class, (Object)new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGACUADRE"))).getValor().trim();
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error obteniendo estado cargue : " + ex.getMessage());
            strEstadoCargue = "0";
        }
        return strEstadoCargue;
    }
    
    private void CambiarEstadoCargue(final String strEstado) {
        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
            registroEntityConsulta = this.confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            final ConfModulosAplicacion registroEntity = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
            registroEntity.setValor(strEstado);
            registroEntity.setDescripcion(registroEntityConsulta.getDescripcion());
            this.confModulosAplicacionSession.actualizar(registroEntity);
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error cambiando estado cargue : " + ex.getMessage());
        }
    }
}
