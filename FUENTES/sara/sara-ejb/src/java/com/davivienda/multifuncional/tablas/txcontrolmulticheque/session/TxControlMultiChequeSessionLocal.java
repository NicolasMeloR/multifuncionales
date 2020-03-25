// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.txcontrolmulticheque.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import javax.ejb.Local;
import com.davivienda.sara.entitys.Txcontrolmulticheque;
import com.davivienda.sara.base.AdministracionTablasInterface;

@Local
public interface TxControlMultiChequeSessionLocal extends AdministracionTablasInterface<Txcontrolmulticheque>
{
    Txcontrolmulticheque getRegistroControlCheque(final Long p0) throws EntityServicioExcepcion;
}
