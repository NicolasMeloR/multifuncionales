package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Entity class ProcesoArchivoArj
 * ProcesoArchivoArj.java
 *
 * Fecha       :16 de marzo de 2007, 09:31 AM
 *
 * Descripción :
 *
 * @author     :jjvargas
 * @deprecated
 */

@Entity

@NamedQueries( {
        @NamedQuery(
                name = "ProcesoArchivoArj.RegistroUnico",
                query = "select object(obj) from ProcesoArchivoArj obj where obj.fecha = :fecha and obj.nombreArchivo = :archivo"),
        @NamedQuery(
                name = "ProcesoArchivoArj.Todos",
                query = "select object(obj) from ProcesoArchivoArj obj order by obj.idProcesoArchivoArj")        
    })

public class ProcesoArchivoArj implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idProcesoArchivoArj")
    @SequenceGenerator(name = "idProcesoArchivoArj", sequenceName = "idProcesoArchivoArj", allocationSize = 1)        
    private Integer idProcesoArchivoArj;
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    private String nombreArchivo;
    
    private String ubicacion;
    
    private Long tamano;
    
    private Integer estadoProceso;
    
    private Integer error;
    
    @Version
    private Integer version;
    
    /** Crea una nueva instancia de ProcesoArchivoArj */
    public ProcesoArchivoArj() {
    }
    
    /**
     * Creates a new instance of ProcesoArchivoArj with the specified values.
     *
     * @param idProcesoArchivoArj the idProcesoArchivoArj of the ProcesoArchivoArj
     */
    public ProcesoArchivoArj(Integer idprocesoarchivoarj) {
        this.idProcesoArchivoArj = idprocesoarchivoarj;
    }
    
    /**
     * Creates a new instance of ProcesoArchivoArj with the specified values.
     *
     *
     * @param idProcesoArchivoArj the idProcesoArchivoArj of the ProcesoArchivoArj
     * @param fecha the fecha of the ProcesoArchivoArj
     * @param nombreArchivo the nombreArchivo of the ProcesoArchivoArj
     * @param ubicacion the ubicacion of the ProcesoArchivoArj
     */
    public ProcesoArchivoArj(Integer idprocesoarchivoarj, Date fecha, String nombrearchivo, String ubicacion) {
        this.idProcesoArchivoArj = idprocesoarchivoarj;
        this.fecha = fecha;
        this.nombreArchivo = nombrearchivo;
        this.ubicacion = ubicacion;
    }
    
    /**
     * Gets the idProcesoArchivoArj of this ProcesoArchivoArj.
     *
     * @return the idProcesoArchivoArj
     */
    public Integer getIdprocesoarchivoarj() {
        return this.idProcesoArchivoArj;
    }
    
    /**
     * Sets the idProcesoArchivoArj of this ProcesoArchivoArj to the specified value.
     *
     * @param idProcesoArchivoArj the new idProcesoArchivoArj
     */
    public void setIdprocesoarchivoarj(Integer idprocesoarchivoarj) {
        this.idProcesoArchivoArj = idprocesoarchivoarj;
    }
    
    /**
     * Gets the fecha of this ProcesoArchivoArj.
     * @return the fecha
     */
    public Date getFecha() {
        return this.fecha;
    }
    
    /**
     * Sets the fecha of this ProcesoArchivoArj to the specified value.
     * @param fecha the new fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Gets the nombreArchivo of this ProcesoArchivoArj.
     *
     * @return the nombreArchivo
     */
    public String getNombrearchivo() {
        return this.nombreArchivo;
    }
    
    /**
     * Sets the nombreArchivo of this ProcesoArchivoArj to the specified value.
     *
     * @param nombreArchivo the new nombreArchivo
     */
    public void setNombrearchivo(String nombrearchivo) {
        this.nombreArchivo = nombrearchivo;
    }
    
    /**
     * Gets the ubicacion of this ProcesoArchivoArj.
     * @return the ubicacion
     */
    public String getUbicacion() {
        return this.ubicacion;
    }
    
    /**
     * Sets the ubicacion of this ProcesoArchivoArj to the specified value.
     * @param ubicacion the new ubicacion
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    /**
     * Gets the tamano of this ProcesoArchivoArj.
     * @return the tamano
     */
    public Long getTamano() {
        return this.tamano;
    }
    
    /**
     * Sets the tamano of this ProcesoArchivoArj to the specified value.
     * @param tamano the new tamano
     */
    public void setTamano(Long tamano) {
        this.tamano = tamano;
    }
    
    /**
     * Gets the estadoProceso of this ProcesoArchivoArj.
     *
     * @return the estadoProceso
     */
    public Integer getEstadoproceso() {
        return this.estadoProceso;
    }
    
    /**
     * Sets the estadoProceso of this ProcesoArchivoArj to the specified value.
     *
     * @param estadoProceso the new estadoProceso
     */
    public void setEstadoproceso(Integer estadoproceso) {
        this.estadoProceso = estadoproceso;
    }
    
    /**
     * Gets the error of this ProcesoArchivoArj.
     * @return the error
     */
    public Integer getError() {
        return this.error;
    }
    
    /**
     * Sets the error of this ProcesoArchivoArj to the specified value.
     * @param error the new error
     */
    public void setError(Integer error) {
        this.error = error;
    }
    
    /**
     * Gets the version of this ProcesoArchivoArj.
     * @return the version
     */
    public Integer getVersion() {
        return this.version;
    }
    
    /**
     * Sets the version of this ProcesoArchivoArj to the specified value.
     * @param version the new version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * Returns a hash code value for the object.  This implementation computes
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idProcesoArchivoArj != null ? this.idProcesoArchivoArj.hashCode() : 0);
        return hash;
    }
    
    /**
     * Determines whether another object is equal to this ProcesoArchivoArj.  The result is
     * <code>true</code> if and only if the argument is not null and is a ProcesoArchivoArj object that
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcesoArchivoArj)) {
            return false;
        }
        ProcesoArchivoArj other = (ProcesoArchivoArj)object;
        if (this.idProcesoArchivoArj != other.idProcesoArchivoArj && (this.idProcesoArchivoArj == null || !this.idProcesoArchivoArj.equals(other.idProcesoArchivoArj))) return false;
        return true;
    }
    
    /**
     * Returns a string representation of the object.  This implementation constructs
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.ProcesoArchivoArj[idprocesoarchivoarj=" + idProcesoArchivoArj + "]";
    }
    
}
