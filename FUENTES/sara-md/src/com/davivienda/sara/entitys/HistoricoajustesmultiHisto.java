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
 * @author lpulgari
 */
@Entity
@Table(name = "HISTORICOAJUSTESMULTI_HISTO")

@NamedQueries( {
    @NamedQuery(
    name = "HistoricoajustesmultiHisto.registroUnico",
            query = "select object(txm) from HistoricoajustesmultiHisto txm where txm.idhistoricoajustes = :idhistoricoajustes"),             
    @NamedQuery(
    name = "HistoricoajustesmultiHisto.todos", 
             query = "select object(txm) from HistoricoajustesmultiHisto txm")            
})

public class HistoricoajustesmultiHisto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "IDHISTORICOAJUSTES", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idhistoricoajustes_SEQ")
    @SequenceGenerator(name = "idhistoricoajustes_SEQ", sequenceName = "SEQ_IDHISTORICOAJUSTES" ,allocationSize=1)    
    private Long idhistoricoajustes;
    @Column(name = "USUARIO", nullable = false)
    private String usuario;
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigocajero;
    @Column(name = "TIPOAJUSTE", nullable = false)
    private String tipoajuste;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "TALON")
    private String talon;
    @Column(name = "VALOR", nullable = false)
    private Long valor;
    @Column(name = "ERROR")
    private String error;
    @Column(name = "DESCRIPCIONERROR")
    private String descripcionerror;
    @Column(name = "VERSION")
    private Integer version;
    @Column(name = "CODIGOOFICINAMULTI")
    private Integer codigooficinamulti;

    
    public HistoricoajustesmultiHisto() {
    }

    public HistoricoajustesmultiHisto(Long idhistoricoajustes) {
        this.idhistoricoajustes = idhistoricoajustes;
    }

    public HistoricoajustesmultiHisto(Long idhistoricoajustes, String usuario, Integer codigocajero, String tipoajuste, Date fecha, Long valor) {
        this.idhistoricoajustes = idhistoricoajustes;
        this.usuario = usuario;
        this.codigocajero = codigocajero;
        this.tipoajuste = tipoajuste;
        this.fecha = fecha;
        this.valor = valor;
    }

   public Integer getCodigocajero() {
        return codigocajero;
    }

    public void setCodigocajero(Integer codigocajero) {
        this.codigocajero = codigocajero;
    }

    public Long getIdhistoricoajustes() {
        return idhistoricoajustes;
    }

    public void setIdhistoricoajustes(Long idhistoricoajustes) {
        this.idhistoricoajustes = idhistoricoajustes;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }  

    public String getTipoajuste() {
        return tipoajuste;
    }

    public void setTipoajuste(String tipoajuste) {
        this.tipoajuste = tipoajuste;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTalon() {
        return talon;
    }

    public void setTalon(String talon) {
        this.talon = talon;
    }  

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescripcionerror() {
        return descripcionerror;
    }

    public void setDescripcionerror(String descripcionerror) {
        this.descripcionerror = descripcionerror;
    }
   public Integer getCodigooficinamulti() {
       return codigooficinamulti;
    }

    public void setCodigooficinamulti(Integer codigooficinamulti) {
        this.codigooficinamulti = codigooficinamulti;

  }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhistoricoajustes != null ? idhistoricoajustes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoajustesmultiHisto)) {
            return false;
        }
        HistoricoajustesmultiHisto other = (HistoricoajustesmultiHisto) object;
        if ((this.idhistoricoajustes == null && other.idhistoricoajustes != null) || (this.idhistoricoajustes != null && !this.idhistoricoajustes.equals(other.idhistoricoajustes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.HistoricoajustesmultiHisto[idhistoricoajustes=" + idhistoricoajustes + "]";
    }

}