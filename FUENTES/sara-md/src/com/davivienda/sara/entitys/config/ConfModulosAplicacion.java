package com.davivienda.sara.entitys.config;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * ConfModulosAplicacion
 * Descripción : Entity con la configuración de módulos
 * Fecha       : 21/05/2008 11:35:20 AM
 * @author     : jjvargas
 **/
@Entity
@Table(name = "CONF_MODULOS_APLICACION")
@NamedQueries({
    @NamedQuery(name = "ConfModulosAplicacion.RegistroUnico",
    query = "SELECT c FROM ConfModulosAplicacion c WHERE c.confModulosAplicacionPK.modulo = :modulo AND c.confModulosAplicacionPK.atributo = :atributo"),
    @NamedQuery(name = "ConfModulosAplicacion.All",
    query = "SELECT c FROM ConfModulosAplicacion c ORDER BY c.confModulosAplicacionPK.modulo, c.confModulosAplicacionPK.atributo"),
             @NamedQuery(name = "ConfModulosAplicacion.Todos",
    query = "SELECT c FROM ConfModulosAplicacion c ORDER BY c.confModulosAplicacionPK.modulo, c.confModulosAplicacionPK.atributo"),
    @NamedQuery(name = "ConfModulosAplicacion.findByModulo",
    query = "SELECT c FROM ConfModulosAplicacion c WHERE c.confModulosAplicacionPK.modulo = :modulo"),
    @NamedQuery(name = "ConfModulosAplicacion.findByAtributo",
    query = "SELECT c FROM ConfModulosAplicacion c WHERE c.confModulosAplicacionPK.atributo = :atributo"),
    @NamedQuery(name = "ConfModulosAplicacion.findByDescripcion",
    query = "SELECT c FROM ConfModulosAplicacion c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "ConfModulosAplicacion.findByValor",
    query = "SELECT c FROM ConfModulosAplicacion c WHERE c.valor = :valor")
})
public class ConfModulosAplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConfModulosAplicacionPK confModulosAplicacionPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "VALOR", nullable = false)
    private String valor;
    @Version
    private Integer version;

    public ConfModulosAplicacion() {
    }

    public ConfModulosAplicacion(ConfModulosAplicacionPK confModulosAplicacionPK) {
        this.confModulosAplicacionPK = confModulosAplicacionPK;
    }

    public ConfModulosAplicacion(ConfModulosAplicacionPK confModulosAplicacionPK, String valor) {
        this.confModulosAplicacionPK = confModulosAplicacionPK;
        this.valor = valor;
    }

    public ConfModulosAplicacion(String modulo, String atributo) {
        this.confModulosAplicacionPK = new ConfModulosAplicacionPK(modulo, atributo);
    }

    public ConfModulosAplicacionPK getConfModulosAplicacionPK() {
        return confModulosAplicacionPK;
    }

    public void setConfModulosAplicacionPK(ConfModulosAplicacionPK confModulosAplicacionPK) {
        this.confModulosAplicacionPK = confModulosAplicacionPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (confModulosAplicacionPK != null ? confModulosAplicacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfModulosAplicacion)) {
            return false;
        }
        ConfModulosAplicacion other = (ConfModulosAplicacion) object;
        if ((this.confModulosAplicacionPK == null && other.confModulosAplicacionPK != null) || (this.confModulosAplicacionPK != null && !this.confModulosAplicacionPK.equals(other.confModulosAplicacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.config.ConfModulosAplicacion[confModulosAplicacionPK=" + confModulosAplicacionPK + "]";
    }

    public ConfModulosAplicacion actualizarEntity(ConfModulosAplicacion obj) {
        setConfModulosAplicacionPK(obj.confModulosAplicacionPK);
        setDescripcion(obj.descripcion);
        setValor(obj.valor);
        return this;
    }
}
