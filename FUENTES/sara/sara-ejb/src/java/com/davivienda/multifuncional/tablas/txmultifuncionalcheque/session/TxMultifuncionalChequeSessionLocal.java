// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.txmultifuncionalcheque.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Txmultifuncionalcheque;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface TxMultifuncionalChequeSessionLocal extends AdministracionTablasInterface<Txmultifuncionalcheque>
{
    Collection<Txmultifuncionalcheque> getColeccionTxCheque(final Integer p0, final Date p1, final Date p2) throws EntityServicioExcepcion;
    
    Collection<Txmultifuncionalcheque> getColeccionTxCheque(final Integer p0, final Date p1) throws EntityServicioExcepcion;
    
    Txmultifuncionalcheque getTxCheque(final Integer p0, final Date p1, final Integer p2) throws EntityServicioExcepcion;
    
    Collection<Txmultifuncionalcheque> getColeccionTxCheque(final Integer p0, final Date p1, final Date p2, final Integer p3, final String p4) throws EntityServicioExcepcion;
}
