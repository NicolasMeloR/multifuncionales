package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity class Transportadora
 * Transportadora.java
 * 
 * Fecha       :28 de enero de 2007, 08:02 PM
 * 
 * Descripción :
 * 
 * @author     :jjvargas
 */

@Entity
@Table(name = "TRANSPORTADORA")
@NamedQueries( {
    @NamedQuery(
    name = "Transportadora.RegistroUnico",
            query = "select object(o) from Transportadora o where o.idTransportadora = :idTransportadora"),
            @NamedQuery(
    name = "Transportadora.Todos",
            query = "select object(o) from Transportadora o order by o.idTransportadora"),
             @NamedQuery(
    name = "Transportadora.todos", query = "SELECT u FROM Transportadora u ORDER BY u.idTransportadora")
})

public class Transportadora implements Serializable {

    @Id
    @Column(name = "IDTRANSPORTADORA", nullable = false)
    private Integer idTransportadora;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "DIRARCHIVOARQUEO", nullable = false)
    private String dirArchivoArqueo;

    @Version
    private Integer version;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportadora")
    private Collection<Cajero> cajeroCollection;
    
    /** Crea una nueva instancia de Transportadora */
    public Transportadora() {
    }

    
    /**
     * Creates a new instance of Transportadora with the specified values.
     * 
     * @param idTransportadora the idTransportadora of the Transportadora
     */
    public Transportadora(Integer idTransportadora) {
        this.idTransportadora = idTransportadora;
    }

    /**
     * Creates a new instance of Transportadora with the specified values.
     * 
     * 
     * @param idTransportadora the idTransportadora of the Transportadora
     * @param nombre the nombre of the Transportadora
     * @param dirArchivoArqueo 
     * @paramdirArchivoArqueoo thedirArchivoArqueoo of the Transportadora
     */
    public Transportadora(Integer idTransportadora, String nombre, String dirArchivoArqueo) {
        this.idTransportadora = idTransportadora;
        this.nombre = nombre;
        this.dirArchivoArqueo = dirArchivoArqueo;
    }

    /**
     * Gets the idTransportadora of this Transportadora.
     * 
     * @return the idTransportadora
     */
    public Integer getIdTransportadora() {
        return this.idTransportadora;
    }

    /**
     * Sets the idTransportadora of this Transportadora to the specified value.
     * 
     * @param idTransportadora the new idTransportadora
     */
    public void setIdTransportadora(Integer idTransportadora) {
        this.idTransportadora = idTransportadora;
    }

    /**
     * Gets the nombre of this Transportadora.
     * @return the nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Sets the nombre of this Transportadora to the specified value.
     * @param nombre the new nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the dirArchivoArqueo of this Transportadora.
     * 
     * @return the dirArchivoArqueo
     */
    public String getDirArchivoArqueo() {
        return this.dirArchivoArqueo;
    }

    /**
     * Sets the dirArchivoArqueo of this Transportadora to the specified value.
     * 
     * @param dirArchivoArqueo the new dirArchivoArqueo
     */
    public void setDirArchivoArqueo(String dirArchivoArqueo) {
        this.dirArchivoArqueo = dirArchivoArqueo;
    }

    /**
     * Gets the version of this Transportadora.
     * @return the version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * Sets the version of this Transportadora to the specified value.
     * @param version the new version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Gets the cajeroCollection of this Transportadora.
     * @return the cajeroCollection
     */
    public Collection<Cajero> getCajeroCollection() {
        return this.cajeroCollection;
    }

    /**
     * Sets the cajeroCollection of this Transportadora to the specified value.
     * @param cajeroCollection the new cajeroCollection
     */
    public void setCajeroCollection(Collection<Cajero> cajeroCollection) {
        this.cajeroCollection = cajeroCollection;
    }

    /**
     * Returns a hash code value for the object.  This implementation computes 
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idTransportadora != null ? this.idTransportadora.hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this Transportadora.  The result is 
     * <code>true</code> if and only if the argument is not null and is a Transportadora object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transportadora)) {
            return false;
        }
        Transportadora other = (Transportadora)object;
        if (this.idTransportadora != other.idTransportadora && (this.idTransportadora == null || !this.idTransportadora.equals(other.idTransportadora))) return false;
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Transportadora[idtransportadora=" + idTransportadora + "]";
    }
     public Transportadora actualizarEntity(Transportadora obj) {
        setNombre(obj.nombre);
        setDirArchivoArqueo(obj.dirArchivoArqueo);
        return this;
    }
    
}
