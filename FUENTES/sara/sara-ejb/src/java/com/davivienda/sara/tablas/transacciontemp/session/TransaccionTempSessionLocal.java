// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.transacciontemp.session;

import java.util.Map;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.TransaccionTemp;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface TransaccionTempSessionLocal extends AdministracionTablasInterface<TransaccionTemp>
{
    Collection<TransaccionTemp> getColeccionTransaccionTemp(final Integer p0, final Date p1, final Date p2) throws EntityServicioExcepcion;
    
    Collection<TransaccionTemp> getColeccionTransaccionTemp(final Integer p0, final Date p1) throws EntityServicioExcepcion;
    
    TransaccionTemp getTransaccionTemp(final Integer p0, final Date p1, final Integer p2) throws EntityServicioExcepcion;
    
    Collection<TransaccionTemp> getColeccionTransaccionTemp(final Integer p0, final Date p1, final Date p2, final Integer p3, final Map p4) throws EntityServicioExcepcion;
    
    Date getFechaMinimaTx(final Integer p0) throws EntityServicioExcepcion;
    
    void cargarCicloTempXStoreP() throws EntityServicioExcepcion;
    
    void mantenimientoDiariosStoreP() throws EntityServicioExcepcion;
    
    void mantenimientoDiariosBorraStoreP() throws EntityServicioExcepcion;
    
    void cargarDiariosElectronicosXStoreP() throws EntityServicioExcepcion;
    
    void cargarDiariosElectronicosXStoreP_Automatico() throws EntityServicioExcepcion;
    
    void calcReintegrosDAuto() throws EntityServicioExcepcion;
    
    void descomprimirEDC() throws EntityServicioExcepcion;
    
    void descomprimirEDC_Automatico() throws EntityServicioExcepcion;
}
