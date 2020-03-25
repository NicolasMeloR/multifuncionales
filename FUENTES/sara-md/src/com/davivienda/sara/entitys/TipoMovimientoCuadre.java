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
 * TipoMovimientoCuadre.java
 * 
 * Fecha       :  14/07/2007, 11:03:41 AM
 * Descripción : 
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */
@Entity
@Table(name = "TIPOMOVIMIENTOCUADRE")
@NamedQueries({
    @NamedQuery(
    name = "TipoMovimientoCuadre.Todos",
            query = "select object(obj) from TipoMovimientoCuadre obj order by obj.codigoTipoMovimientoCuadre"),
    @NamedQuery(
    name = "TipoMovimientoCuadre.todos",
            query = "SELECT u FROM TipoMovimientoCuadre u ORDER BY u.codigoTipoMovimientoCuadre")

})
public class TipoMovimientoCuadre implements Serializable {

    @Id
    private Integer codigoTipoMovimientoCuadre;

    private String descripcion;

    private short operador;

    private short tipoDato;
    
    private short modoAfectarCuadre;
    
    private Integer lineaInfCuadre ;
    
    private Integer columnaInfCuadre ;

    /**
     * Crea una nueva instancia de <code>TipoMovimientoCuadre</code>.
     */
    public TipoMovimientoCuadre() {
    }

    public Integer getCodigoTipoMovimientoCuadre() {
        return codigoTipoMovimientoCuadre;
    }

    public void setCodigoTipoMovimientoCuadre(Integer codigoTipoMovimientoCuadre) {
        this.codigoTipoMovimientoCuadre = codigoTipoMovimientoCuadre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public short getOperador() {
        return operador;
    }

    public void setOperador(short operador) {
        this.operador = operador;
    }

    public short getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(short tipoDato) {
        this.tipoDato = tipoDato;
    }

    public short getModoAfectarCuadre() {
        return modoAfectarCuadre;
    }

    public void setModoAfectarCuadre(short modoAfectarCuadre) {
        this.modoAfectarCuadre = modoAfectarCuadre;
    }

    public Integer getLineaInfCuadre() {
        return lineaInfCuadre;
    }

    public void setLineaInfCuadre(Integer lineaInfCuadre) {
        this.lineaInfCuadre = lineaInfCuadre;
    }

    public Integer getColumnaInfCuadre() {
        return columnaInfCuadre;
    }

    public void setColumnaInfCuadre(Integer columnaInfCuadre) {
        this.columnaInfCuadre = columnaInfCuadre;
    }
    
    


    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoTipoMovimientoCuadre != null ? codigoTipoMovimientoCuadre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMovimientoCuadre)) {
            return false;
        }
        TipoMovimientoCuadre other = (TipoMovimientoCuadre) object;
        if (this.codigoTipoMovimientoCuadre != other.codigoTipoMovimientoCuadre && (this.codigoTipoMovimientoCuadre == null || !this.codigoTipoMovimientoCuadre.equals(other.codigoTipoMovimientoCuadre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.TipoMovimientoCuadre[codigoTipoMovimientoCuadre=" + codigoTipoMovimientoCuadre + "]";
    }


}

