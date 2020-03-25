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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Entity class ProcesoTransmisionTira
 * ProcesoTransmisionTira.java
 *
 * Fecha       :7 de marzo de 2007, 09:38 PM
 *
 * Descripción : Procesos de transmisión de tiras de auditoría
 *
 *
 * @author :jjvargas
 * @deprecated
 */

@Entity

@NamedQueries( {
    @NamedQuery(
    name = "ProcesoTransmisionTira.registroUnico",
            query = "select object(obj) from ProcesoTransmisionTira obj where obj.cfgComunicacion.codigoCajero = :cajero and obj.fecha = :fecha"),
            @NamedQuery(
    name = "ProcesoTransmisionTira.todos",
            query = "select object(obj) from ProcesoTransmisionTira obj")
})

public class ProcesoTransmisionTira implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idProcesoTransmisionTira")
    @SequenceGenerator(name = "idProcesoTransmisionTira", sequenceName = "IDPROCESOTRANSMISIONTIRA", allocationSize = 1)
    private Integer idProcesoTransmisionTira;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    private String nombreArchivo;
    
    private Integer tamano;
    
    private Integer registros;
    
    private Integer estadoProceso;
    
    private Integer error;
    
    @Version
    private Integer version;
    
    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO")
    @ManyToOne
    private CfgComunicaciones cfgComunicacion;
    
    /**
     * Crea una nueva instancia de ProcesoTransmisionTira
     */
    public ProcesoTransmisionTira() {
    }
    
    /**
     * Creates a new instance of ProcesoTransmisionTira with the specified values.
     *
     *
     *
     * @param idProcesoTransmisionTira the idProcesoTransmisionTira of the ProcesoTransmisionTira
     */
    public ProcesoTransmisionTira(Integer idProcesoTransmisionTira) {
        this.idProcesoTransmisionTira = idProcesoTransmisionTira;
    }
    
    /**
     * Creates a new instance of ProcesoTransmisionTira with the specified values.
     *
     *
     *
     * @param idProcesoTransmisionTira the idProcesoTransmisionTira of the ProcesoTransmisionTira
     * @param fecha the fecha of the ProcesoTransmisionTira
     */
    public ProcesoTransmisionTira(Integer idProcesoTransmisionTira, Date fecha) {
        this.idProcesoTransmisionTira = idProcesoTransmisionTira;
        this.fecha = fecha;
    }
    
    /**
     * Gets the idProcesoTransmisionTira of this ProcesoTransmisionTira.
     *
     *
     *
     * @return the idProcesoTransmisionTira
     */
    public Integer getIdProcesoTransmisionTira() {
        return this.idProcesoTransmisionTira;
    }
    
    /**
     * Sets the idProcesoTransmisionTira of this ProcesoTransmisionTira to the specified value.
     *
     *
     *
     * @param idProcesoTransmisionTira the new idProcesoTransmisionTira
     */
    public void setIdProcesoTransmisionTira(Integer idProcesoTransmisionTira) {
        this.idProcesoTransmisionTira = idProcesoTransmisionTira;
    }
    
    /**
     * Gets the fecha of this ProcesoTransmisionTira.
     *
     * @return the fecha
     */
    public Date getFecha() {
        return this.fecha;
    }
    
    /**
     * Sets the fecha of this ProcesoTransmisionTira to the specified value.
     *
     * @param fecha the new fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Gets the nombreArchivo of this ProcesoTransmisionTira.
     *
     *
     * @return the nombreArchivo
     */
    public String getNombrearchivo() {
        return this.nombreArchivo;
    }
    
    /**
     * Sets the nombreArchivo of this ProcesoTransmisionTira to the specified value.
     *
     *
     * @param nombreArchivo the new nombreArchivo
     */
    public void setNombrearchivo(String nombrearchivo) {
        this.nombreArchivo = nombrearchivo;
    }
    
    /**
     * Gets the tamano of this ProcesoTransmisionTira.
     *
     * @return the tamano
     */
    public Integer getTamano() {
        return this.tamano;
    }
    
    /**
     * Sets the tamano of this ProcesoTransmisionTira to the specified value.
     *
     * @param tamano the new tamano
     */
    public void setTamano(Integer tamano) {
        this.tamano = tamano;
    }
    
    /**
     * Gets the registros of this ProcesoTransmisionTira.
     *
     * @return the registros
     */
    public Integer getRegistros() {
        return this.registros;
    }
    
    /**
     * Sets the registros of this ProcesoTransmisionTira to the specified value.
     *
     * @param registros the new registros
     */
    public void setRegistros(Integer registros) {
        this.registros = registros;
    }
    
    /**
     * Gets the estadoProceso of this ProcesoTransmisionTira.
     *
     *
     * @return the estadoProceso
     */
    public Integer getEstadoproceso() {
        return this.estadoProceso;
    }
    
    /**
     * Sets the estadoProceso of this ProcesoTransmisionTira to the specified value.
     *
     *
     * @param estadoProceso the new estadoProceso
     */
    public void setEstadoproceso(Integer estadoproceso) {
        this.estadoProceso = estadoproceso;
    }
    
    /**
     * Gets the error of this ProcesoTransmisionTira.
     *
     * @return the error
     */
    public Integer getError() {
        return this.error;
    }
    
    /**
     * Sets the error of this ProcesoTransmisionTira to the specified value.
     *
     * @param error the new error
     */
    public void setError(Integer error) {
        this.error = error;
    }
    
    /**
     * Gets the version of this ProcesoTransmisionTira.
     *
     * @return the version
     */
    public Integer getVersion() {
        return this.version;
    }
    
    /**
     * Sets the version of this ProcesoTransmisionTira to the specified value.
     *
     * @param version the new version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * Gets the cfgComunicacion of this ProcesoTransmisionTira.
     *
     *
     *
     *
     * @return the cfgComunicacion
     */
    public CfgComunicaciones getCfgComunicaciones() {
        return this.cfgComunicacion;
    }
    
    /**
     * Sets the cfgComunicacion of this ProcesoTransmisionTira to the specified value.
     *
     *
     *
     *
     * @param cfgComunicacion the new cfgComunicacion
     */
    public void setCfgComunicaciones(CfgComunicaciones cfgComunicaciones) {
        this.cfgComunicacion = cfgComunicaciones;
    }
    
    /**
     * Returns a hash code value for the object.  This implementation computes
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idProcesoTransmisionTira != null ? this.idProcesoTransmisionTira.hashCode() : 0);
        return hash;
    }
    
    /**
     * Determines whether another object is equal to this ProcesoTransmisionTira.  The result is
     * <code>true</code> if and only if the argument is not null and is a ProcesoTransmisionTira object that
     * has the same id field values as this object.
     *
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcesoTransmisionTira)) {
            return false;
        }
        ProcesoTransmisionTira other = (ProcesoTransmisionTira)object;
        if (this.idProcesoTransmisionTira != other.idProcesoTransmisionTira && (this.idProcesoTransmisionTira == null || !this.idProcesoTransmisionTira.equals(other.idProcesoTransmisionTira))) return false;
        return true;
    }
    
    /**
     * Returns a string representation of the object.  This implementation constructs
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ProcesoTransmisionTira[IdProcesoTransmisionTira=" + idProcesoTransmisionTira + "]";
    }
    
}
