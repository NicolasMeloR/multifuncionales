// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.tareasadminaccesousuario.servicio;

import javax.persistence.EntityManager;
import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.entitys.TareasadminAccesoUsuario;
import com.davivienda.sara.base.BaseEntityServicio;

public class TareasadminAccesoUsuarioServicio extends BaseEntityServicio<TareasadminAccesoUsuario> implements AdministracionTablasInterface<TareasadminAccesoUsuario>
{
    public TareasadminAccesoUsuarioServicio(final EntityManager em) {
        super(em, TareasadminAccesoUsuario.class);
    }
    
    public TareasadminAccesoUsuario obtenerTareaAdmAccesoUsuarioByMax() {
        Long maxValue = 0L;
        this.em = this.getEm();
        maxValue = (Long)this.em.createNamedQuery("TareasadminAccesoUsuario.findMax").getSingleResult();
        final TareasadminAccesoUsuario tareasadminAccesoUsuario = (TareasadminAccesoUsuario)this.em.createNamedQuery("TareasadminAccesoUsuario.findByIdtareaaccesusu").setParameter("idtareaaccesusu", (Object)maxValue).getSingleResult();
        return tareasadminAccesoUsuario;
    }
}
