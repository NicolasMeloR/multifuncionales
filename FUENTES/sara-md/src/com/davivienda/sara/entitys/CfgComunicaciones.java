package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * Entity class CfgComunicaciones
 * CfgComunicaciones.java
 *
 * Fecha       :7 de marzo de 2007, 09:38 PM
 *
 * Descripción : Configuracion de comunicaciones de transmision de archivos
 *
 *
 * @author :jjvargas
 */

@Entity
public class CfgComunicaciones implements Serializable {
    
    @Id
    private Integer codigoCajero;
    
    private String ip;
    
    private String ftpUsuario;
    
    private String ftpClave;
    
    private String directorioTira;
    
    @Version
    private Integer version;
    
    
    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO", insertable = false, updatable = false)
    @OneToOne
    private Cajero cajero;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cfgComunicacion")
    private Collection<ProcesoTransmisionTira> procesoTransmisionTiraCollection;
    
    /**
     * Crea una nueva instancia de CfgComunicaciones
     */
    public CfgComunicaciones() {
    }
    
    /**
     * Creates a new instance of CfgComunicaciones with the specified values.
     *
     * @param codigocajero the codigocajero of the CfgComunicaciones
     */
    public CfgComunicaciones(Integer codigocajero) {
        this.codigoCajero = codigocajero;
    }
    
    /**
     * Creates a new instance of CfgComunicaciones with the specified values.
     *
     * @param codigocajero the codigocajero of the CfgComunicaciones
     * @param ip the ip of the CfgComunicaciones
     */
    public CfgComunicaciones(Integer codigocajero, String ip) {
        this.codigoCajero = codigocajero;
        this.ip = ip;
    }
    
    /**
     * Gets the codigocajero of this CfgComunicaciones.
     *
     * @return the codigocajero
     */
    public Integer getCodigoCajero() {
        return this.codigoCajero;
    }
    
    /**
     * Sets the codigocajero of this CfgComunicaciones to the specified value.
     *
     * @param codigocajero the new codigocajero
     */
    public void setCodigoCajero(Integer codigocajero) {
        this.codigoCajero = codigocajero;
    }
    
    /**
     * Gets the ip of this CfgComunicaciones.
     *
     * @return the ip
     */
    public String getIp() {
        return this.ip;
    }
    
    /**
     * Sets the ip of this CfgComunicaciones to the specified value.
     *
     * @param ip the new ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    /**
     * Gets the ftpUsuario of this CfgComunicaciones.
     *
     *
     * @return the ftpUsuario
     */
    public String getFtpUsuario() {
        return this.ftpUsuario;
    }
    
    /**
     * Sets the ftpUsuario of this CfgComunicaciones to the specified value.
     *
     *
     * @param ftpUsuario the new ftpUsuario
     */
    public void setFtpUsuario(String usuario) {
        this.ftpUsuario = usuario;
    }
    
    /**
     * Gets the ftpClave of this CfgComunicaciones.
     *
     *
     * @return the ftpClave
     */
    public String getFtpClave() {
        return this.ftpClave;
    }
    
    /**
     * Sets the ftpClave of this CfgComunicaciones to the specified value.
     *
     *
     * @param ftpClave the new ftpClave
     */
    public void setFtpClave(String password) {
        this.ftpClave = password;
    }
    
    
    
    /**
     * Gets the version of this CfgComunicaciones.
     *
     * @return the version
     */
    public Integer getVersion() {
        return this.version;
    }
    
    /**
     * Sets the version of this CfgComunicaciones to the specified value.
     *
     * @param version the new version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * Gets the cajero of this CfgComunicaciones.
     *
     * @return the cajero
     */
    public Cajero getCajero() {
        return this.cajero;
    }
    
    /**
     * Sets the cajero of this CfgComunicaciones to the specified value.
     *
     * @param cajero the new cajero
     */
    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }
    
    /**
     * Gets the procesoTransmisionTiraCollection of this CfgComunicaciones.
     * 
     * 
     * 
     * @return the procesoTransmisionTiraCollection
     */
    public Collection<ProcesoTransmisionTira> getTransmisionCargueCollection() {
        return this.procesoTransmisionTiraCollection;
    }
    
    /**
     * Sets the procesoTransmisionTiraCollection of this CfgComunicaciones to the specified value.
     * 
     * 
     * 
     * @param procesoTransmisionTiraCollection the new procesoTransmisionTiraCollection
     */
    public void setTransmisionCargueCollection(Collection<ProcesoTransmisionTira> transmisionCargueCollection) {
        this.procesoTransmisionTiraCollection = transmisionCargueCollection;
    }
    
    /**
     * Returns a hash code value for the object.  This implementation computes
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.codigoCajero != null ? this.codigoCajero.hashCode() : 0);
        return hash;
    }
    
    /**
     * Determines whether another object is equal to this CfgComunicaciones.  The result is
     * <code>true</code> if and only if the argument is not null and is a CfgComunicaciones object that
     * has the same id field values as this object.
     *
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CfgComunicaciones)) {
            return false;
        }
        CfgComunicaciones other = (CfgComunicaciones)object;
        if (this.codigoCajero != other.codigoCajero && (this.codigoCajero == null || !this.codigoCajero.equals(other.codigoCajero))) return false;
        return true;
    }
    
    /**
     * Returns a string representation of the object.  This implementation constructs
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.CfgComunicaciones[codigoCajero=" + codigoCajero + "]";
    }
    
    public String getDirectorioTira() {
        return directorioTira;
    }
    
    public void setDirectorioTira(String directorioTira) {
        this.directorioTira = directorioTira;
    }
    
}
