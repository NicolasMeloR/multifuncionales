package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity class Occa
 * Occa.java
 * 
 * Fecha       : 28 de enero de 2007, 08:02 PM
 * 
 * Descripci�n :
 * 
 * @author     : jjvargas
 * @version     : $Id: Occa.java,v 1.2 2007/05/18 23:22:41 jjvargas Exp $
 */

@Entity
@Table(name = "OCCA")

@NamedQueries( {
    @NamedQuery(
    name = "Occa.RegistroUnico",
            query = "select object(o) from Occa o where o.codigoOcca = :codigoocca"),
            @NamedQuery(
    name = "Occa.Todos",
            query = "select object(o) from Occa o order by o.codigoOcca"),
            @NamedQuery(
    name = "Occa.todos", query = "SELECT u FROM Occa u ORDER BY u.codigoOcca")
})

public class Occa implements Serializable {

    @Id
    private Integer codigoOcca;

    private String nombre;
    
    private String nombreArchivoMovimiento;
    
    private String UbicacionArchivoMovimiento;
    
    private Integer codTerminalCaja;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "occa")
    private Collection<Cajero> cajeroCollection;
    
    /** Crea una nueva instancia de Occa */
    public Occa() {
    }

    /**
     * Creates a new instance of Occa with the specified values.
     * 
     * @param codigoOcca the codigoOcca of the Occa
     */
    public Occa(Integer codigoOcca) {
        this.setCodigoOcca(codigoOcca);
    }

    /**
     * Gets the codigoOcca of this Occa.
     * 
     * @return the codigoOcca
     */
    public Integer getCodigoOcca() {
        return this.codigoOcca;
    }
    
   public void  setCodigoTerminal(Integer codTerminalCaja) {
        this.codTerminalCaja=codTerminalCaja;
    }
    public Integer getCodigoTerminal() {
        return this.codTerminalCaja;
    }


    /**
     * Sets the codigoOcca of this Occa to the specified value.
     * 
     * @param codigoOcca the new codigoOcca
     */
    public void setCodigoOcca(Integer codigoOcca) {
        this.codigoOcca = codigoOcca;
    }

    /**
     * Gets the nombre of this Occa.
     * @return the nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Sets the nombre of this Occa to the specified value.
     * @param nombre the new nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the cajeroCollection of this Occa.
     * @return the cajeroCollection
     */
    public Collection<Cajero> getCajeroCollection() {
        return this.cajeroCollection;
    }

    /**
     * Sets the cajeroCollection of this Occa to the specified value.
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
        hash += (this.getCodigoOcca() != null ? this.getCodigoOcca().hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this Occa.  The result is 
     * <code>true</code> if and only if the argument is not null and is a Occa object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Occa)) {
            return false;
        }
        Occa other = (Occa)object;
        if (this.getCodigoOcca() != other.getCodigoOcca() && (this.getCodigoOcca() == null || !this.getCodigoOcca().equals(other.getCodigoOcca()))) return false;
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Occa[codigoocca=" + getCodigoOcca() + "]";
    }

    public String getNombreArchivoMovimiento() {
        return nombreArchivoMovimiento;
    }

    public void setNombreArchivoMovimiento(String nombreArchivoMovimiento) {
        this.nombreArchivoMovimiento = nombreArchivoMovimiento;
    }

    public String getUbicacionArchivoMovimiento() {
        return UbicacionArchivoMovimiento;
    }

    public void setUbicacionArchivoMovimiento(String UbicacionArchivoMovimiento) {
        this.UbicacionArchivoMovimiento = UbicacionArchivoMovimiento;
    }
    
       public Occa actualizarEntity(Occa obj) {
        setNombre(obj.nombre);
        setNombreArchivoMovimiento(obj.nombreArchivoMovimiento);
        setUbicacionArchivoMovimiento(obj.UbicacionArchivoMovimiento);
        setCodigoTerminal(obj.codTerminalCaja);

      
        return this;
    }
    

    
}

