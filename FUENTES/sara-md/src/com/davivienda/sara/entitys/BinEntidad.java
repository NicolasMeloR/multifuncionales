/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
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
@Table(name = "BIN_ENTIDAD")
@NamedQueries({
         @NamedQuery(
     name = "BinEntidad.RegistroUnico",
     query = "select object(obj) from BinEntidad  obj where obj.bin  = :bin "),
     @NamedQuery(
       name = "BinEntidad.Todos",
       query = "select object(obj) from BinEntidad obj order by obj.entidad , obj.bin ")
      
 })
public class BinEntidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "ENTIDAD", nullable = false)
    private Integer entidad;
    @Id
    @Column(name = "BIN", nullable = false)
    private String bin;
    @Column(name = "TIPOCUENTA")
    private Integer tipocuenta;

    public BinEntidad() {
    }

    public BinEntidad(String bin) {
        this.bin = bin;
    }

    public BinEntidad(String bin, Integer entidad) {
        this.bin = bin;
        this.entidad = entidad;
    }

    public Integer getEntidad() {
        return entidad;
    }

    public void setEntidad(Integer entidad) {
        this.entidad = entidad;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public Integer getTipocuenta() {
        return tipocuenta;
    }

    public void setTipocuenta(Integer tipocuenta) {
        this.tipocuenta = tipocuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bin != null ? bin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BinEntidad)) {
            return false;
        }
        BinEntidad other = (BinEntidad) object;
        if ((this.bin == null && other.bin != null) || (this.bin != null && !this.bin.equals(other.bin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.BinEntidad[bin=" + bin + "]";
    }

}
