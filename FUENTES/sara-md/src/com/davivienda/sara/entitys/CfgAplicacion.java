package com.davivienda.sara.entitys;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * CfgAplicacion
 * Descripción : Entity para manejar la persitencia de la configuración de la aplicación
 * Fecha       : 7/02/2008 09:25:43 PM
 * @author     : jjvargas
 **/
@Entity
@Table(name = "CFGAPLICACION")
@NamedQueries({
    @NamedQuery(
      name = "CfgAplicacion.RegistroUnico", 
      query = "SELECT c FROM CfgAplicacion c WHERE c.cfgAplicacionPK.modulo = :modulo AND c.cfgAplicacionPK.atributo = :atributo"
     ), 
     @NamedQuery(
      name = "CfgAplicacion.Todos", 
      query = "SELECT c FROM CfgAplicacion c ORDER BY c.cfgAplicacionPK.modulo, c.cfgAplicacionPK.atributo"
     )
})

public class CfgAplicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CfgAplicacionPK cfgAplicacionPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "VALOR", nullable = false)
    private String valor;

    public CfgAplicacion() {
    }

    public CfgAplicacion(CfgAplicacionPK cfgAplicacionPK) {
        this.cfgAplicacionPK = cfgAplicacionPK;
    }

    public CfgAplicacion(CfgAplicacionPK cfgAplicacionPK, String valor) {
        this.cfgAplicacionPK = cfgAplicacionPK;
        this.valor = valor;
    }

    public CfgAplicacion(String modulo, String atributo) {
        this.cfgAplicacionPK = new CfgAplicacionPK(modulo, atributo);
    }

    public CfgAplicacionPK getCfgAplicacionPK() {
        return cfgAplicacionPK;
    }

    public void setCfgAplicacionPK(CfgAplicacionPK cfgAplicacionPK) {
        this.cfgAplicacionPK = cfgAplicacionPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cfgAplicacionPK != null ? cfgAplicacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgAplicacion)) {
            return false;
        }
        CfgAplicacion other = (CfgAplicacion) object;
        if ((this.cfgAplicacionPK == null && other.cfgAplicacionPK != null) || (this.cfgAplicacionPK != null && !this.cfgAplicacionPK.equals(other.cfgAplicacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.CfgAplicacion[cfgAplicacionPK=" + cfgAplicacionPK.getModulo() + cfgAplicacionPK.getAtributo() + "]";
    }

}
