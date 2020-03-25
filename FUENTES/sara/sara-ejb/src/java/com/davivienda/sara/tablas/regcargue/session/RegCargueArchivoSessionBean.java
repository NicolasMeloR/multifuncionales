// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.tablas.regcargue.session;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.Collection;
import java.util.Date;
import javax.annotation.PostConstruct;
import com.davivienda.sara.tablas.regcargue.servicio.RegCargueArchivoServicio;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.ejb.Stateless;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.base.BaseAdministracionTablas;

@Stateless
public class RegCargueArchivoSessionBean extends BaseAdministracionTablas<Regcarguearchivo> implements RegCargueArchivoSessionLocal
{
    @PersistenceContext
    private EntityManager em;
    private RegCargueArchivoServicio regCargueArchivoServicio;
    
    @PostConstruct
    public void postConstructor() {
        this.regCargueArchivoServicio = new RegCargueArchivoServicio(this.em);
        super.servicio = this.regCargueArchivoServicio;
    }
    
    @Override
    public Collection<Regcarguearchivo> getRegCargueXFecha(final Date fechaInicial, final Date fechaFinal) throws EntityServicioExcepcion {
        return this.regCargueArchivoServicio.getRegCargueXFecha(fechaInicial, fechaFinal);
    }
    
    public Regcarguearchivo buscarPorArchivoFecha(final String archivoTarea, final String fecha, final boolean IndAuto) throws EntityServicioExcepcion {
        return this.regCargueArchivoServicio.buscarPorArchivoFecha(archivoTarea, fecha, IndAuto);
    }
}
