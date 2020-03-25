package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * HostCargue.java
 *
 * Fecha       : 30 de abril de 2007, 11:30 AM
 *
 * Descripción : Entity de la tabla HOSTCARGUE
 *
 * @author     : jjvargas
 *
 * @version    : $Id: HostCargue.java,v 1.1 2007/05/18 23:22:41 jjvargas Exp $
 */



@Entity
@Table(name = "HOSTCARGUE")
@NamedQueries(
value = {
    @NamedQuery(
    name = "HostCargue.Todos",
            query = "select object(hc) from HostCargue hc order by hc.occa.codigoOcca"),
    @NamedQuery(
    name = "HostCargue.RegistroUnico",
            query =
            "select object(hc) from HostCargue hc where hc.occa.codigoOcca = :occa and hc.fechaHostCargue = :fecha")
            
}
)

public class HostCargue implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdHostCargue")
    @SequenceGenerator(name = "IdHostCargue", sequenceName = "IDHOSTCARGUE", allocationSize = 1)
    private Integer idHostCargue;
    

    @Temporal(TemporalType.DATE)
    private Date fechaHostCargue;
    

    private Integer estadoProceso;
    
    
    @Version
    private Integer version;
    
    @JoinColumn(name = "CODIGOOCCA", referencedColumnName = "CODIGOOCCA")
    @ManyToOne
    private Occa occa;
    
    private Integer error;
    
    /**
     * Crea una nueva instancia de HostCargue
     */
    public HostCargue() {
    }
    
    /**
     * Creates a new instance of HostCargue with the specified values.
     *
     *
     * @param idHostCargue the idHostCargue of the HostCargue
     */
    public HostCargue(Integer idhostcargue) {
        this.idHostCargue = idhostcargue;
    }
    
    /**
     * Creates a new instance of HostCargue with the specified values.
     *
     *
     *
     * @param idHostCargue the idHostCargue of the HostCargue
     * @param fechaHostCargue the fechaHostCargue of the HostCargue
     */
    public HostCargue(Integer idhostcargue, Date fechahostcargue) {
        this.idHostCargue = idhostcargue;
        this.fechaHostCargue = fechahostcargue;
    }
    
    /**
     * Gets the idHostCargue of this HostCargue.
     *
     *
     * @return the idHostCargue
     */
    public Integer getIdHostCargue() {
        return this.idHostCargue;
    }
    
    /**
     * Sets the idHostCargue of this HostCargue to the specified value.
     *
     *
     * @param idHostCargue the new idHostCargue
     */
    public void setIdHostCargue(Integer idhostcargue) {
        this.idHostCargue = idhostcargue;
    }
    
    /**
     * Gets the fechaHostCargue of this HostCargue.
     *
     *
     * @return the fechaHostCargue
     */
    public Date getFechaHostCargue() {
        return this.fechaHostCargue;
    }
    
    /**
     * Sets the fechaHostCargue of this HostCargue to the specified value.
     *
     *
     * @param fechaHostCargue the new fechaHostCargue
     */
    public void setFechaHostCargue(Date fechahostcargue) {
        this.fechaHostCargue = fechahostcargue;
    }
    
    /**
     * Gets the estadoProceso of this HostCargue.
     *
     *
     * @return the estadoProceso
     */
    public Integer getEstadoProceso() {
        return this.estadoProceso;
    }
    
    /**
     * Sets the estadoProceso of this HostCargue to the specified value.
     *
     *
     * @param estadoProceso the new estadoProceso
     */
    public void setEstadoProceso(Integer estadoproceso) {
        this.estadoProceso = estadoproceso;
    }
    
    
    /**
     * Gets the version of this HostCargue.
     *
     * @return the version
     */
    public Integer getVersion() {
        return this.version;
    }
    
    /**
     * Sets the version of this HostCargue to the specified value.
     *
     * @param version the new version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * Gets the codigoocca of this HostCargue.
     *
     * @return the occa
     */
    public Occa getOcca() {
        return this.occa;
    }
    
    /**
     * Sets the codigoocca of this HostCargue to the specified value.
     *
     * @param occa the new occa
     */
    public void setOcca(Occa occa) {
        this.occa = occa;
    }
    
    /**
     * Returns a hash code value for the object.  This implementation computes
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idHostCargue != null ? this.idHostCargue.hashCode() : 0);
        return hash;
    }
    
    /**
     * Determines whether another object is equal to this HostCargue.  The result is
     * <code>true</code> if and only if the argument is not null and is a HostCargue object that
     * has the same id field values as this object.
     *
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HostCargue)) {
            return false;
        }
        HostCargue other = (HostCargue)object;
        if (this.idHostCargue != other.idHostCargue && (this.idHostCargue == null || !this.idHostCargue.equals(other.idHostCargue))) return false;
        return true;
    }
    
    /**
     * Returns a string representation of the object.  This implementation constructs
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys[idhostcargue=" + idHostCargue + "]";
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }
    
    
}
