package com.davivienda.sara.tablas.tareasadminaccesousuario.servicio;

import com.davivienda.sara.base.AdministracionTablasInterface;
import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.entitys.TareasadminAccesoUsuario;
import javax.persistence.EntityManager;

/**
 * EdcMultifuncionalServicio Versión : 1.0
 *
 * @author P-MDRUIZ Davivienda 2011
 */
public class TareasadminAccesoUsuarioServicio extends BaseEntityServicio<TareasadminAccesoUsuario> implements AdministracionTablasInterface<TareasadminAccesoUsuario> {

    public TareasadminAccesoUsuarioServicio(EntityManager em) {
        super(em, TareasadminAccesoUsuario.class);
    }

    public TareasadminAccesoUsuario obtenerTareaAdmAccesoUsuarioByMax() {
        Long maxValue = 0L;

        em = this.getEm();

        maxValue = (Long) em.createNamedQuery("TareasadminAccesoUsuario.findMax").getSingleResult();
        
        
                
        TareasadminAccesoUsuario  tareasadminAccesoUsuario = (TareasadminAccesoUsuario) em.createNamedQuery("TareasadminAccesoUsuario.findByIdtareaaccesusu")
                    .setParameter("idtareaaccesusu", maxValue).getSingleResult();

        return tareasadminAccesoUsuario;
    }

}
