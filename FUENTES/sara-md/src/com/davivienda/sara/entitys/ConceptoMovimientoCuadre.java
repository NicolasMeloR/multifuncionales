/*
 * ConceptoMovimientoCuadre.java
 * 
 * Created on 18/09/2007, 09:11:05 AM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jjvargas
 */
@Entity
@Table(name = "CONCEPTOMOVIMIENTOCUADRE")
@NamedQueries({
    @NamedQuery(
    name = "ConceptoMovimientoCuadre.Todos",
            query = "select object(obj) from ConceptoMovimientoCuadre obj order by obj.codigoConcepto"),
    @NamedQuery(
    name = "ConceptoMovimientoCuadre.todos", query = "SELECT u FROM ConceptoMovimientoCuadre u ORDER BY u.codigoConcepto")

})
public class ConceptoMovimientoCuadre implements Serializable {

    @Id
    @Column(name = "CODIGOCONCEPTO", nullable = false)
    private Integer codigoConcepto;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    public ConceptoMovimientoCuadre() {
    }

    public ConceptoMovimientoCuadre(Integer codigoconcepto) {
        this.codigoConcepto = codigoconcepto;
    }

    public ConceptoMovimientoCuadre(Integer codigoconcepto, String descripcion) {
        this.codigoConcepto = codigoconcepto;
        this.descripcion = descripcion;
    }

    public Integer getCodigoConcepto() {
        return codigoConcepto;
    }

    public void setCodigoConcepto(Integer codigoconcepto) {
        this.codigoConcepto = codigoconcepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoConcepto != null ? codigoConcepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptoMovimientoCuadre)) {
            return false;
        }
        ConceptoMovimientoCuadre other = (ConceptoMovimientoCuadre) object;
        if (this.codigoConcepto != other.codigoConcepto && (this.codigoConcepto == null || !this.codigoConcepto.equals(other.codigoConcepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ConceptoMovimientoCuadre[codigoConcepto=" + codigoConcepto + "]";
    }

}
