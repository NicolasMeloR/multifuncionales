/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author P-LAPULGAR
 */
@Entity
@Table(name = "EDCARGUEMULTIFUNCIONAL")

@NamedQueries( {
    @NamedQuery(
    name = "Edcarguemultifuncional.registroUnico",
            query = "select object(edc) from Edcarguemultifuncional edc where edc.idedccargue = :idedccargue"),
    // Busqueda de un registro por cajero y ciclo utilizadopor el proceso de carga
            @NamedQuery(
    name = "Edcarguemultifuncional.CicloSnError",
            query = "select object(edc) from Edcarguemultifuncional edc where edc.ciclo = :ciclo and edc.error=0"),
             @NamedQuery(
    name = "Edcarguemultifuncional.todos", 
             query = "select object(edc) from Edcarguemultifuncional edc order by edc.idedccargue"),
                 @NamedQuery(
    name = "Edcarguemultifuncional.RangoFecha",
    query = "select object(edc) from Edcarguemultifuncional edc  " +
    "        where edc.fechaedccargue between :fechaInicial and :fechaFinal order by edc.ciclo,edc.error") ,
              @NamedQuery(
    name = "Edcarguemultifuncional.Archivo",
    query = "select object(edc) from Edcarguemultifuncional edc  " +
    "        where edc.nombrearchivo = :nombrearchivo") 

            
})



public class Edcarguemultifuncional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "IDEDCCARGUE", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdEdcCargueMulti_SEQ")
    @SequenceGenerator(name = "IdEdcCargueMulti_SEQ", sequenceName = "SEQ_IDEDCCARGUEMULTI" ,allocationSize=1 )    
    private Long idedccargue;
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigocajero;
    @Column(name = "ESTADOPROCESO")
    private Short estadoproceso;
    @Column(name = "ULTIMASECUENCIA", nullable = false)
    private Integer ultimasecuencia;
    @Column(name = "CICLO", nullable = false)
    private Integer ciclo;
    @Column(name = "ERROR")
    private Integer error;    
    @Column(name = "VERSION")
    private Integer version;
    @Column(name = "NOMBREARCHIVO", nullable = false)
    private String nombrearchivo;
    @Column(name = "FECHAEDCCARGUE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaedccargue;
     @Column(name = "DESCRIPCIONERROR")
    private String descripcionerror;

    public Edcarguemultifuncional() {
    }

    public Edcarguemultifuncional(Long idedccargue) {
        this.idedccargue = idedccargue;
    }

    public Edcarguemultifuncional(Long idedccargue, Integer codigocajero, Integer ultimasecuencia, Integer ciclo, String nombrearchivo, Date fechaedccargue) {
        this.idedccargue = idedccargue;
        this.codigocajero = codigocajero;
        this.ultimasecuencia = ultimasecuencia;
        this.ciclo = ciclo;
        this.nombrearchivo = nombrearchivo;
        this.fechaedccargue = fechaedccargue;
    }

    public Long getIdedccargue() {
        return idedccargue;
    }

    public void setIdedccargue(Long idedccargue) {
        this.idedccargue = idedccargue;
    }

   

    public Short getEstadoproceso() {
        return estadoproceso;
    }

    public void setEstadoproceso(Short estadoproceso) {
        this.estadoproceso = estadoproceso;
    }

    
    

    public String getNombrearchivo() {
        return nombrearchivo;
    }

    public void setNombrearchivo(String nombrearchivo) {
        this.nombrearchivo = nombrearchivo;
    }

    public Date getFechaedccargue() {
        return fechaedccargue;
    }

    public void setFechaedccargue(Date fechaedccargue) {
        this.fechaedccargue = fechaedccargue;
    }
    
    public Integer getCiclo() {
        return ciclo;
    }

    public void setCiclo(Integer ciclo) {
        this.ciclo = ciclo;
    }

    public Integer getCodigocajero() {
        return codigocajero;
    }

    public void setCodigocajero(Integer codigocajero) {
        this.codigocajero = codigocajero;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Integer getUltimasecuencia() {
        return ultimasecuencia;
    }

    public void setUltimasecuencia(Integer ultimasecuencia) {
        this.ultimasecuencia = ultimasecuencia;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
        public String getDescripcionerror() {
        return descripcionerror;
    }

    public void setDescripcionerror(String descripcionerror) {
        this.descripcionerror = descripcionerror;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idedccargue != null ? idedccargue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Edcarguemultifuncional)) {
            return false;
        }
        Edcarguemultifuncional other = (Edcarguemultifuncional) object;
        if ((this.idedccargue == null && other.idedccargue != null) || (this.idedccargue != null && !this.idedccargue.equals(other.idedccargue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Edcarguemultifuncional[idedccargue=" + idedccargue + "]";
    }
    
    public Edcarguemultifuncional actualizarEntity(Edcarguemultifuncional obj) {               
       setCodigocajero(obj.codigocajero); 
       setEstadoproceso(obj.estadoproceso);
       setUltimasecuencia(obj.ultimasecuencia);
       setCiclo(obj.ciclo);
       setError(obj.error); 
       setVersion(obj.version);
       setNombrearchivo(obj.nombrearchivo);
       setFechaedccargue(obj.fechaedccargue);
       setDescripcionerror(obj.descripcionerror);
       return this;
        
            
    }

}