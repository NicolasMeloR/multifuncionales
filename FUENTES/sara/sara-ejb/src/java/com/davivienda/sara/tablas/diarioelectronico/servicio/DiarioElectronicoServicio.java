// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.diarioelectronico.servicio;

import java.io.FileNotFoundException;
import com.davivienda.sara.base.ProcesadorArchivoDiarioElectronicoInterface;
import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.procesador.EdcProcesadorArchivo;
import com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura.EdcArchivo;
import java.util.logging.Logger;
import com.davivienda.sara.entitys.Cajero;
import java.util.Calendar;
import javax.persistence.Query;
import java.util.logging.Level;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.utilidades.conversion.Fecha;
import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.DiarioElectronico;
import com.davivienda.sara.base.BaseEntityServicio;

public class DiarioElectronicoServicio extends BaseEntityServicio<DiarioElectronico> implements AdministracionTablasInterface<DiarioElectronico>
{
    private CajeroServicio cajeroServicio;
    
    public DiarioElectronicoServicio(final EntityManager em) {
        super(em, DiarioElectronico.class);
        this.cajeroServicio = new CajeroServicio(em);
    }
    
    public Collection<DiarioElectronico> getDiarioElectronico(final Integer codigoCajero, final Date fechaInicial) throws EntityServicioExcepcion {
        final Integer cCajero = (codigoCajero != null) ? codigoCajero : 9999;
        final Date fFinalCiclo = Fecha.getFechaFinDia(fechaInicial);
        Collection<DiarioElectronico> items = null;
        try {
            Query query = null;
            if (cCajero.equals(9999)) {
                throw new EntityServicioExcepcion("Debe seleccionar un solo cajero");
            }
            query = this.em.createNamedQuery("DiarioElectronico.CajeroRangoFecha");
            query.setParameter("codigoCajero", (Object)cCajero);
            query.setParameter("fechaInicial", (Object)fechaInicial);
            query.setParameter("fechaFinal", (Object)fFinalCiclo);
            items = (Collection<DiarioElectronico>)query.getResultList();
        }
        catch (IllegalStateException ex) {
            DiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            DiarioElectronicoServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
        return items;
    }
    
    public Collection<DiarioElectronico> getDiarioElectronicoDia(final Integer codigoCajero, final Calendar fecha, final String nombreArchivo) throws FileNotFoundException, EntityServicioExcepcion, IllegalArgumentException {
        final Integer regsProcesados = -1;
        final Cajero cajero = this.cajeroServicio.buscar(codigoCajero);
        ArchivoPlano archivo = null;
        Logger.getLogger("globalApp").info("Se inicia la consulta para el cajero " + cajero.getCodigoCajero());
        ProcesadorArchivoDiarioElectronicoInterface procesador = null;
        Collection<DiarioElectronico> regsDiarioElectronico = null;
        try {
            String directorio = "";
            if (cajero.getUbicacionEDC() != null && cajero.getUbicacionEDC().trim().length() > 1) {
                directorio = cajero.getUbicacionEDC();
            }
            if (directorio.equals("")) {
                directorio = DiarioElectronicoServicio.configApp.DIRECTORIO_UPLOAD;
            }
            archivo = new EdcArchivo(codigoCajero, fecha, directorio, nombreArchivo);
            procesador = new EdcProcesadorArchivo(archivo, Fecha.getCalendar(fecha, -1));
            if (procesador != null) {
                regsDiarioElectronico = procesador.getRegistrosDiarioElectronico();
            }
        }
        catch (Exception ex) {
            Logger.getLogger("globalApp").info("Error leyendo  el archivo diario electronico para el cajero :" + cajero.getCodigoCajero() + " descripcion Error : " + ex.getMessage());
        }
        return regsDiarioElectronico;
    }
}
