package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/*
 * DiarioElectronico.java
 * 
 * Fecha : 29/11/2007, 04:00:10 PM
 * 
 * @author jjvargas
 */
import javax.persistence.Version;
//El número de mi celular es 312 8437663 - 3435797
@Entity
@Table(name = "DIARIOELECTRONICO")
@NamedQueries({
    @NamedQuery(
      name = "DiarioElectronico.RegistroUnico", 
      query = "SELECT object(obj) FROM DiarioElectronico obj" +
              "  WHERE obj.diarioElectronicoPK.codigoCajero = :codigoCajero " +
              "    and obj.diarioElectronicoPK.fecha = :fecha" ),             
    @NamedQuery(
      name = "DiarioElectronico.Cajero", 
      query = "SELECT object(obj) FROM DiarioElectronico obj" +
              "  WHERE obj.diarioElectronicoPK.codigoCajero = :codigoCajero" ), 
    @NamedQuery(
      name = "DiarioElectronico.CajeroRangoFecha",
      query = "select object(obj) from DiarioElectronico obj  " +
      "        where obj.diarioElectronicoPK.codigoCajero =:codigoCajero and " +
      "               obj.diarioElectronicoPK.fecha between :fechaInicial and :fechaFinal order by obj.diarioElectronicoPK.secuencia "),
    @NamedQuery(
      name = "DiarioElectronico.RangoFecha",
      query = "select object(obj) from DiarioElectronico obj  " +
      "        where obj.diarioElectronicoPK.fecha between :fechaInicial and :fechaFinal"),      
    @NamedQuery(
      name = "DiarioElectronico.Fecha", 
      query = "SELECT d FROM DiarioElectronico d WHERE d.diarioElectronicoPK.fecha = :fecha"), 
    @NamedQuery(
      name = "DiarioElectronico.TipoRegistro", 
      query = "SELECT d FROM DiarioElectronico d WHERE d.tipoRegistro = :tipoRegistro"),
    @NamedQuery(
      name = "DiarioElectronico.ProcesoCargaDiariosElectronico", 
      query = "SELECT NEW com.davivienda.sara.dto.diarioelectronico.ProcesoCargaDiariosElectronico(c.codigoCajero, c.nombre, count(obj.diarioElectronicoPK.codigoCajero)) " +
      "        FROM DiarioElectronico obj, Cajero c" +
      "        WHERE c.codigoCajero = obj.diarioElectronicoPK.codigoCajero" +
      "           AND obj.diarioElectronicoPK.fecha between :fechaInicial and :fechaFinal" +
      "        GROUP BY obj.diarioElectronicoPK.codigoCajero, c.codigoCajero, c.nombre" +
      "        ORDER BY c.codigoCajero ")
})
   
        

        
public class DiarioElectronico implements Serializable {

    @EmbeddedId
    protected DiarioElectronicoPK diarioElectronicoPK;

    private Character tipoRegistro;

    private String informacion;

    @Version
    private Integer version;
    
    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO", insertable = false, updatable = false)
    @ManyToOne
    private Cajero cajero;
 
    public DiarioElectronico() {
    }

    public DiarioElectronico(DiarioElectronicoPK diarioElectronicoPK) {
        this.diarioElectronicoPK = diarioElectronicoPK;
    }



    public DiarioElectronicoPK getDiarioElectronicoPK() {
        return diarioElectronicoPK;
    }

    public void setDiarioElectronicoPK(DiarioElectronicoPK diarioElectronicoPK) {
        this.diarioElectronicoPK = diarioElectronicoPK;
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
        hash += (diarioElectronicoPK != null ? diarioElectronicoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiarioElectronico)) {
            return false;
        }
        DiarioElectronico other = (DiarioElectronico) object;
        if (this.diarioElectronicoPK != other.diarioElectronicoPK && (this.diarioElectronicoPK == null || !this.diarioElectronicoPK.equals(other.diarioElectronicoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.DiarioElectronico[diarioElectronicoPK=" + diarioElectronicoPK + "]";
    }

    public Character getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Character tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }
  

    
}
