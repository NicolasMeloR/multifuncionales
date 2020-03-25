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
 * @author AAGARCIA
 */
@Entity
@Table(name = "REGCARGUEARCHIVO")

@NamedQueries({
    @NamedQuery(
    name = "Regcarguearchivo.registroUnico",
    query = "select object(edc) from Regcarguearchivo edc where edc.idregcarguearchivo = :idregcarguearchivo"),
    @NamedQuery(   
    name = "Regcarguearchivo.todos", 
    query = "select object(edc) from Regcarguearchivo edc order by edc.idregcarguearchivo"),                
    @NamedQuery(
    name = "Regcarguearchivo.RangoFecha",
    query = "select object(edc) from Regcarguearchivo edc  " +
            "where edc.fecha between :fechaInicial and :fechaFinal order by edc.fecha") ,
    @NamedQuery(
    name = "Regcarguearchivo.ArchivoFechaAuto",
    query = "select object(edc) from Regcarguearchivo edc  " +
            "where edc.archivotarea = :archivotarea and edc.fechaautomatica = :fechaautomatica and edc.indautomatico = :indautomatico") ,
    @NamedQuery(
    name = "Regcarguearchivo.ArchivoFechaManual",
    query = "select object(edc) from Regcarguearchivo edc  " +
    "        where edc.archivotarea = :archivotarea and edc.fechamanual = :fechamanual and edc.indautomatico = :indautomatico")
})
public class Regcarguearchivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "IDREGCARGUEARCHIVO", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idregcarguearchivo_SEQ")
    @SequenceGenerator(name = "idregcarguearchivo_SEQ", sequenceName = "SEQ_REGCARGUEARCHIVO" ,allocationSize=1 )    
    private Long idregcarguearchivo;
    
    @Column(name = "ARCHIVOTAREA", nullable = false)
    private String archivotarea;
    @Column(name = "FECHAAUTOMATICA")
    private String fechaautomatica;
     @Column(name = "INDAUTOMATICO", nullable = false)
    private Integer indautomatico;
    @Column(name = "FECHAMANUAL")
    private String fechamanual;
    @Column(name = "ESTADOCARGA", nullable = false)
    private Integer estadocarga;
    @Column(name = "NUMREGISTROS")
    private Long numregistros;
    @Column(name = "TAREA", nullable = false)
    private String tarea;
    @Column(name = "DESCRIPCIONERROR")
    private String descripcionerror;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "FECHAFINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafinal;
    @Column(name = "USUARIO")
    private String usuario;

    public Regcarguearchivo() {
    }

    public Regcarguearchivo(Long idregcarguearchivo) {
        this.idregcarguearchivo = idregcarguearchivo;
    }

    public Regcarguearchivo(Long idregcarguearchivo, String archivotarea, Integer indautomatico, Integer estadocarga, String tarea, Date fecha) {
        this.idregcarguearchivo = idregcarguearchivo;
        this.archivotarea = archivotarea;
        this.indautomatico = indautomatico;
        this.estadocarga = estadocarga;
        this.tarea = tarea;
        this.fecha = fecha;
    }

    public Long getIdregcarguearchivo() {
        return idregcarguearchivo;
    }

    public void setIdregcarguearchivo(Long idregcarguearchivo) {
        this.idregcarguearchivo = idregcarguearchivo;
    }

    public String getArchivotarea() {
        return archivotarea;
    }

    public void setArchivotarea(String archivotarea) {
        this.archivotarea = archivotarea;
    }

    public String getFechaautomatica() {
        return fechaautomatica;
    }

    public void setFechaautomatica(String fechaautomatica) {
        this.fechaautomatica = fechaautomatica;
    }

    public Integer getIndautomatico() {
        return indautomatico;
    }

    public void setIndautomatico(Integer indautomatico) {
        this.indautomatico = indautomatico;
    }

    public String getFechamanual() {
        return fechamanual;
    }

    public void setFechamanual(String fechamanual) {
        this.fechamanual = fechamanual;
    }

    public Integer getEstadocarga() {
        return estadocarga;
    }

    public void setEstadocarga(Integer estadocarga) {
        this.estadocarga = estadocarga;
    }

    public Long getNumregistros() {
        return numregistros;
    }

    public void setNumregistros(Long numregistros) {
        this.numregistros = numregistros;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getDescripcionerror() {
        return descripcionerror;
    }

    public void setDescripcionerror(String descripcionerror) {
        this.descripcionerror = descripcionerror;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
     public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idregcarguearchivo != null ? idregcarguearchivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regcarguearchivo)) {
            return false;
        }
        Regcarguearchivo other = (Regcarguearchivo) object;
        if ((this.idregcarguearchivo == null && other.idregcarguearchivo != null) || (this.idregcarguearchivo != null && !this.idregcarguearchivo.equals(other.idregcarguearchivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Regcarguearchivo[idregcarguearchivo=" + idregcarguearchivo + "]";
    }
    
    public Regcarguearchivo actualizarEntity(Regcarguearchivo obj) {  
         
       this.setArchivotarea(obj.archivotarea); 
       this.setEstadocarga(obj.estadocarga);
       this.setFecha(obj.fecha);
       this.setFechaautomatica(obj.fechaautomatica);
       this.setFechamanual(obj.fechamanual);
       this.setIdregcarguearchivo(obj.idregcarguearchivo);
       this.setIndautomatico(obj.indautomatico);
       this.setTarea(obj.tarea);
       this.setDescripcionerror(obj.descripcionerror);
       this.setNumregistros(obj.numregistros);
       this.setFechafinal(obj.fechafinal);
       this.setUsuario(obj.usuario);
      
       
       return this;
        
            
    }
}
