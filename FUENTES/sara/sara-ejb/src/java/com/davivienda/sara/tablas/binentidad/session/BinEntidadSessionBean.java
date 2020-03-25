// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.binentidad.session;

import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.binentidad.servicio.BinEntidadServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.BinEntidad;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class BinEntidadSessionBean extends BaseAdministracionTablas<BinEntidad> implements BinEntidadSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private BinEntidadServicio binEntidadServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.binEntidadServicio = new BinEntidadServicio(this.em);
        super.servicio = this.binEntidadServicio;
    }
    
    @Override
    public BinEntidad getEntidadXBin(final String bin) throws Exception {
        return this.binEntidadServicio.getEntidadXBin(bin);
    }
}
