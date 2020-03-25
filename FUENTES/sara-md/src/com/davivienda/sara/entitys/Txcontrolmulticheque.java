/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author P-LAPULGAR
 */
@Entity
@Table(name = "TXCONTROLMULTICHEQUE")

@NamedQueries( {
    @NamedQuery(
    name = "Txcontrolmulticheque.registroUnico",
            query = "select object(txm) from Txcontrolmulticheque txm where txm.idregistro = :idregistro"),             
    @NamedQuery(
    name = "Txcontrolmulticheque.todos", 
             query = "select object(txm) from Txcontrolmulticheque txm")            
})


public class Txcontrolmulticheque implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "IDREGISTRO", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdRegistro_SEQ")
    @SequenceGenerator(name = "IdRegistro_SEQ", sequenceName = "SEQ_IDREGMULTICHEQUE" ,allocationSize=1) 
    private Long idregistro;
    @Column(name = "PLAZA", nullable = false)
    private int plaza;
    @Column(name = "FECHAARCHIVO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaarchivo;
    @Column(name = "MONTOARCHIVO")
    private Long montoarchivo;
    @Column(name = "CANTIDADCHEQUES")
    private Integer cantidadcheques;
    @Column(name = "VERSION")
    private Integer version;
       
    @OneToMany(mappedBy = "idregistrocontrol")
    private Collection<Txmultifuncionalcheque> txmultifuncionalchequeCollection;

    public Txcontrolmulticheque() {
    }

    public Txcontrolmulticheque(Long idregistro) {
        this.idregistro = idregistro;
    }

    public Txcontrolmulticheque(Long idregistro, int plaza, Date fechaarchivo) {
        this.idregistro = idregistro;
        this.plaza = plaza;
        this.fechaarchivo = fechaarchivo;
    }

    public Long getIdregistro() {
        return idregistro;
    }

    public void setIdregistro(Long idregistro) {
        this.idregistro = idregistro;
    }

    public int getPlaza() {
        return plaza;
    }

    public void setPlaza(int plaza) {
        this.plaza = plaza;
    }

    public Date getFechaarchivo() {
        return fechaarchivo;
    }

    public void setFechaarchivo(Date fechaarchivo) {
        this.fechaarchivo = fechaarchivo;
    }

    public Long getMontoarchivo() {
        return montoarchivo;
    }

    public void setMontoarchivo(Long montoarchivo) {
        this.montoarchivo = montoarchivo;
    }

    public Integer getCantidadcheques() {
        return cantidadcheques;
    }

    public void setCantidadcheques(Integer cantidadcheques) {
        this.cantidadcheques = cantidadcheques;
    }

   
    public Collection<Txmultifuncionalcheque> getTxmultifuncionalchequeCollection() {
        return txmultifuncionalchequeCollection;
    }

    public void setTxmultifuncionalchequeCollection(Collection<Txmultifuncionalcheque> txmultifuncionalchequeCollection) {
        this.txmultifuncionalchequeCollection = txmultifuncionalchequeCollection;
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
        hash += (idregistro != null ? idregistro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Txcontrolmulticheque)) {
            return false;
        }
        Txcontrolmulticheque other = (Txcontrolmulticheque) object;
        if ((this.idregistro == null && other.idregistro != null) || (this.idregistro != null && !this.idregistro.equals(other.idregistro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Txcontrolmulticheque[idregistro=" + idregistro + "]";
    }
    
    public Txcontrolmulticheque actualizarEntity(Txcontrolmulticheque obj) {       
        
        setIdregistro(obj.idregistro);    
        setPlaza(obj.plaza);   
        setFechaarchivo(obj.fechaarchivo);   
        setMontoarchivo(obj.montoarchivo);   
        setCantidadcheques(obj.cantidadcheques);   
        setVersion(obj.version);    
        return this;
            
    }
    

}
