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
 * @author aa.garcia
 */
@Entity
@Table(name = "EDCCARGUE")

@NamedQueries( {
    @NamedQuery(
    name = "EdcCargue.registroUnico",
            query = "select object(edc) from EdcCargue edc where edc.idEdcCargue = :idEdcCargue"),
    // Busqueda de un registro por cajero y ciclo utilizadopor el proceso de carga
            @NamedQuery(
    name = "EdcCargue.CicloSnError",
            query = "select object(edc) from EdcCargue edc where edc.ciclo = :ciclo and edc.error=0"),
             @NamedQuery(
    name = "EdcCargue.todos", 
                 query = "select object(edc) from EdcCargue edc order by edc.idEdcCargue"),
                 @NamedQuery(
    name = "EdcCargue.RangoFecha",
    query = "select object(edc) from EdcCargue edc  " +
    "        where edc.fechaEdcCargue between :fechaInicial and :fechaFinal order by edc.ciclo,edc.error") ,
              @NamedQuery(
    name = "EdcCargue.Archivo",
    query = "select object(edc) from EdcCargue edc  " +
    "        where edc.nombreArchivo = :nombreArchivo") 

            
})
public class EdcCargue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "IDEDCCARGUE", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdEdcCargue_SEQ")
    @SequenceGenerator(name = "IdEdcCargue_SEQ", sequenceName = "SEQ_IDEDCCARGUE" ,allocationSize=1 )
    private Long idEdcCargue;   
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigoCajero;
    @Column(name = "FECHAEDCCARGUE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdcCargue;
    @Column(name = "ESTADOPROCESO")
    private Integer estadoProceso;
    @Column(name = "ULTIMASECUENCIA", nullable = false)
    private Integer ultimaSecuencia;
    @Column(name = "CICLO", nullable = false)
    private Integer ciclo;
    @Column(name = "ERROR")
    private Integer error;
    @Column(name = "VERSION")
    private Integer version;
    @Column(name = "NOMBREARCHIVO", nullable = false)
    private String nombreArchivo;
    @Column(name = "TAMANO")
    private Integer tamano;

    public EdcCargue() {
    }

    public EdcCargue(Long idEdcCargue) {
        this.idEdcCargue = idEdcCargue;
    }

    public EdcCargue(Long idEdcCargue, Integer codigocajero, Date fechaEdcCargue, Integer ultimasecuencia, Integer ciclo, String nombrearchivo) {
        this.idEdcCargue = idEdcCargue;
        this.codigoCajero = codigocajero;
        this.fechaEdcCargue = fechaEdcCargue;
        this.ultimaSecuencia = ultimasecuencia;
        this.ciclo = ciclo;
        this.nombreArchivo = nombrearchivo;
    }

    public Long getIdEdcCargue() {
        return idEdcCargue;
    }

    public void setIdEdcCargue(Long idEdcCargue) {
        this.idEdcCargue = idEdcCargue;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigocajero) {
        this.codigoCajero = codigocajero;
    }

    public Date getFechaEdcCargue() {
        return fechaEdcCargue;
    }

    public void setFechaEdcCargue(Date fechaEdcCargue) {
        this.fechaEdcCargue = fechaEdcCargue;
    }

    public Integer getEstadoproceso() {
        return estadoProceso;
    }

    public void setEstadoproceso(Integer estadoproceso) {
        this.estadoProceso = estadoproceso;
    }

    public Integer getUltimaSecuencia() {
        return ultimaSecuencia;
    }

    public void setUltimaSecuencia(Integer ultimasecuencia) {
        this.ultimaSecuencia = ultimasecuencia;
    }

    public Integer getCiclo() {
        return ciclo;
    }

    public void setCiclo(Integer ciclo) {
        this.ciclo = ciclo;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNombrearchivo() {
        return nombreArchivo;
    }

    public void setNombrearchivo(String nombrearchivo) {
        this.nombreArchivo = nombrearchivo;
    }

    public Integer getTamano() {
        return tamano;
    }

    public void setTamano(Integer tamano) {
        this.tamano = tamano;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEdcCargue != null ? idEdcCargue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EdcCargue)) {
            return false;
        }
        EdcCargue other = (EdcCargue) object;
        if ((this.idEdcCargue == null && other.idEdcCargue != null) || (this.idEdcCargue != null && !this.idEdcCargue.equals(other.idEdcCargue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.EdcCargue[idEdcCargue=" + idEdcCargue + "]";
    }
    public EdcCargue actualizarEntity(EdcCargue obj) {
        this.setIdEdcCargue(obj.idEdcCargue);
        this.setFechaEdcCargue(obj.fechaEdcCargue);
        this.setCodigoCajero(obj.codigoCajero);
        this.setNombrearchivo(obj.nombreArchivo);
        this.setTamano(obj.tamano);
        this.setEstadoproceso(obj.estadoProceso);
        this.setError(obj.error);
        this.setCiclo(obj.ciclo);
        this.setUltimaSecuencia(obj.ultimaSecuencia);
       
        return this;
        
            
    }

}
