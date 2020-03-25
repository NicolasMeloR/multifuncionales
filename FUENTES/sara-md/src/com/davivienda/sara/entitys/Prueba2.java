/*
 * To change this template, choose Tools | Templates
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
 * @author aa.garcia
 */
@Entity
@Table(name = "PRUEBA2")
@NamedQueries({
    @NamedQuery(name = "Prueba2.todos", 
query =  "select object(o) from Prueba2 o order by o.codigo" )})
public class Prueba2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CODIGO", nullable = false)
    private Integer codigo;
    @Column(name = "DATOS")
    private String datos;
    @Column(name = "INFORMACION")
    private String informacion;

    public Prueba2() {
    }

    public Prueba2(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prueba2)) {
            return false;
        }
        Prueba2 other = (Prueba2) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Prueba2[codigo=" + codigo + "]";
    }

}
