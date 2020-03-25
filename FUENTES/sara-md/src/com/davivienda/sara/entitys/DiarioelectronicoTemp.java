/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author aa.garcia
 */
@Entity
@Table(name = "DIARIOELECTRONICO_TEMP")
@NamedQueries({
    @NamedQuery(
      name = "DiarioelectronicoTemp.RegistroUnico", 
      query = "SELECT object(obj) FROM DiarioelectronicoTemp obj" +
              "  WHERE obj.diarioelectronicoTempPK.codigocajero = :codigoCajero " +
              "    and obj.diarioelectronicoTempPK.fecha = :fecha" ),             
    @NamedQuery(
      name = "DiarioelectronicoTemp.Cajero", 
      query = "SELECT object(obj) FROM DiarioelectronicoTemp obj" +
              "  WHERE obj.diarioelectronicoTempPK.codigocajero = :codigoCajero" ), 
    @NamedQuery(
      name = "DiarioelectronicoTemp.CajeroRangoFecha",
      query = "select object(obj) from DiarioelectronicoTemp obj  " +
      "        where obj.diarioelectronicoTempPK.codigocajero =:codigoCajero and " +
      "               obj.diarioelectronicoTempPK.fecha between :fechaInicial and :fechaFinal order by obj.diarioelectronicoTempPK.secuencia "),
    @NamedQuery(
      name = "DiarioelectronicoTemp.RangoFecha",
      query = "select object(obj) from DiarioelectronicoTemp obj  " +
      "        where obj.diarioelectronicoTempPK.fecha between :fechaInicial and :fechaFinal"),      
    @NamedQuery(
      name = "DiarioelectronicoTemp.Fecha", 
      query = "SELECT d FROM DiarioelectronicoTemp d WHERE d.diarioelectronicoTempPK.fecha = :fecha"), 
    @NamedQuery(
      name = "DiarioelectronicoTemp.TipoRegistro", 
      query = "SELECT d FROM DiarioelectronicoTemp d WHERE d.tiporegistro = :tipoRegistro"),
    @NamedQuery(
      name = "DiarioelectronicoTemp.ProcesoCargaDiariosElectronico", 
      query = "SELECT NEW com.davivienda.sara.dto.diarioelectronico.ProcesoCargaDiariosElectronico(c.codigoCajero, c.nombre, count(obj.diarioelectronicoTempPK.codigocajero)) " +
      "        FROM DiarioelectronicoTemp obj, Cajero c" +
      "        WHERE c.codigoCajero = obj.diarioelectronicoTempPK.codigocajero" +
      "           AND obj.diarioelectronicoTempPK.fecha between :fechaInicial and :fechaFinal" +
      "        GROUP BY obj.diarioelectronicoTempPK.codigocajero, c.codigoCajero, c.nombre" +
      "        ORDER BY c.codigoCajero ")
})
public class DiarioelectronicoTemp implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DiarioelectronicoTempPK diarioelectronicoTempPK;
    @Column(name = "TIPOREGISTRO")
    private Character tiporegistro;
    @Column(name = "INFORMACION")
    private String informacion;
    @Column(name = "VERSION")
    private Integer version;

    public DiarioelectronicoTemp() {
    }

    public DiarioelectronicoTemp(DiarioelectronicoTempPK diarioelectronicoTempPK) {
        this.diarioelectronicoTempPK = diarioelectronicoTempPK;
    }

    public DiarioelectronicoTemp(Integer codigocajero, Integer secuencia, Date fecha) {
        this.diarioelectronicoTempPK = new DiarioelectronicoTempPK(codigocajero, secuencia, fecha);
    }

    public DiarioelectronicoTempPK getDiarioelectronicoTempPK() {
        return diarioelectronicoTempPK;
    }

    public void setDiarioelectronicoTempPK(DiarioelectronicoTempPK diarioelectronicoTempPK) {
        this.diarioelectronicoTempPK = diarioelectronicoTempPK;
    }

    public Character getTiporegistro() {
        return tiporegistro;
    }

    public void setTiporegistro(Character tiporegistro) {
        this.tiporegistro = tiporegistro;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
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
        hash += (diarioelectronicoTempPK != null ? diarioelectronicoTempPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiarioelectronicoTemp)) {
            return false;
        }
        DiarioelectronicoTemp other = (DiarioelectronicoTemp) object;
        if ((this.diarioelectronicoTempPK == null && other.diarioelectronicoTempPK != null) || (this.diarioelectronicoTempPK != null && !this.diarioelectronicoTempPK.equals(other.diarioelectronicoTempPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.DiarioelectronicoTemp[diarioelectronicoTempPK=" + diarioelectronicoTempPK + "]";
    }

}
