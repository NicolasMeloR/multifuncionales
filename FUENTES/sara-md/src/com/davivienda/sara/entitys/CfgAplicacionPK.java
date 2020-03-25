/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CfgAplicacionPK
 * Descripción : 
 * Fecha       : 7/02/2008 09:25:43 PM
 * @author     : jjvargas
 **/
@Embeddable
public class CfgAplicacionPK implements Serializable {
    @Column(name = "MODULO", nullable = false)
    private String modulo;
    @Column(name = "ATRIBUTO", nullable = false)
    private String atributo;

    public CfgAplicacionPK() {
    }

    public CfgAplicacionPK(String modulo, String atributo) {
        this.modulo = modulo;
        this.atributo = atributo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modulo != null ? modulo.hashCode() : 0);
        hash += (atributo != null ? atributo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgAplicacionPK)) {
            return false;
        }
        CfgAplicacionPK other = (CfgAplicacionPK) object;
        if ((this.modulo == null && other.modulo != null) || (this.modulo != null && !this.modulo.equals(other.modulo))) {
            return false;
        }
        if ((this.atributo == null && other.atributo != null) || (this.atributo != null && !this.atributo.equals(other.atributo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.CfgAplicacionPK[modulo=" + modulo + ", atributo=" + atributo + "]";
    }

}
