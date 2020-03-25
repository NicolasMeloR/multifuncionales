// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.txmultifuncionalefectivo.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Txmultifuncionalefectivo;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface TxMultifuncionalEfectivoSessionLocal extends AdministracionTablasInterface<Txmultifuncionalefectivo>
{
    Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(final Integer p0, final Date p1, final Date p2, final Date p3) throws EntityServicioExcepcion;
    
    Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(final Integer p0, final Date p1, final Date p2) throws EntityServicioExcepcion;
    
    Txmultifuncionalefectivo getTxEfectivo(final Integer p0, final Date p1, final Integer p2, final Date p3) throws EntityServicioExcepcion;
    
    Collection<Txmultifuncionalefectivo> getColeccionTxEfectivo(final Integer p0, final Date p1, final Date p2, final Integer p3, final String p4, final Date p5) throws EntityServicioExcepcion;
    
    void guardarArchivoMultifuncionalEfectivo(final Integer p0) throws EntityServicioExcepcion;
    
    void cargarDiariosMultiEfectivo() throws EntityServicioExcepcion;
    
    void cargarDiariosMultiCheque() throws EntityServicioExcepcion;
    
    void cargarLogEventos() throws EntityServicioExcepcion;
}
