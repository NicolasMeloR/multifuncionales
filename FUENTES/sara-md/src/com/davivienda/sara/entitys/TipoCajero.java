package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * Entity class TipoCajero
 * TipoCajero.java
 * 
 * Fecha       :28 de enero de 2007, 08:02 PM
 * 
 * Descripción :
 * 
 * 
 * @author :jjvargas
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "TipoCajero.RegistroUnico",
    query = "select object(o) from TipoCajero o where o.codigoTipoCajero = :codigoTipoCajero"),
    @NamedQuery(name = "TipoCajero.Todos",
    query = "select object(o) from TipoCajero o order by o.codigoTipoCajero"),
    @NamedQuery(name = "TipoCajero.todos", 
    query = "SELECT u FROM TipoCajero u ORDER BY u.codigoTipoCajero")        
})
public class TipoCajero implements Serializable {

    /**
     * id del tipo de cajero
     */
    @Id
    private Integer codigoTipoCajero;
    /**
     * Descripción de la tipificación del cajero
     */
    private String descripcion;
    /**
     * marca del cajero
     */
    private Integer marca;
    /**
     * modelo del cajero
     */
    private String modelo;
    /**
     * Sistema operativo del cajero
     */
    private Integer sistemaOperativo;
    /**
     * Aplicativo que controla la capa de negocio
     */
    private Integer aplicativoCajero;
    /**
     * Formato del diario electrónico
     */
    private Integer formatoDiarioElectronico;

    private String papelImpresion;
    /**
     * Versión del entity
     */
    @Version
    private Integer version;
     /**
     * Versión del entity
     */
  

    /**
     * Colección de cajeros de este tipo
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoCajero")
    private Collection<Cajero> cajeroCollection;

    /**
     * Crea una nueva instancia de TipoCajero
     */
    public TipoCajero() {
    }

    /**
     * Creates a new instance of TipoCajero with the specified values.
     * @param codigotipocajero 
     */
    public TipoCajero(Integer codigotipocajero) {
        this.codigoTipoCajero = codigotipocajero;
    }

    /**
     * Creates a new instance of TipoCajero with the specified values.
     * 
     * 
     * 
     * 
     * @param codigotipocajero 
     * @param sistemaoperativo 
     * @param descripcion the descripcion of the TipoCajero
     * @param marca the marca of the TipoCajero
     * @param modelo the modelo of the TipoCajero
     * @param aplicativocajero the aplicativocajero of the TipoCajero
     */
    public TipoCajero(Integer codigotipocajero, String descripcion, Integer marca, String modelo, Integer sistemaoperativo, Integer aplicativocajero, Integer formatoDiarioElectronico,String papelImpresion) {
        this.codigoTipoCajero = codigotipocajero;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.sistemaOperativo = sistemaoperativo;
        this.aplicativoCajero = aplicativocajero;
        this.formatoDiarioElectronico = formatoDiarioElectronico;
        this.papelImpresion=papelImpresion;
    }

    /**
     * Gets the codigoTipoCajero of this TipoCajero.
     * 
     * 
     * @return the codigoTipoCajero
     */
    public Integer getCodigoTipoCajero() {
        return this.codigoTipoCajero;
    }

    /**
     * Sets the codigoTipoCajero of this TipoCajero to the specified value.
     * 
     * 
     * 
     * @param codigotipocajero 
     */
    public void setCodigoTipoCajero(Integer codigotipocajero) {
        this.codigoTipoCajero = codigotipocajero;
    }

    /**
     * Gets the descripcion of this TipoCajero.
     * 
     * @return the descripcion
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * Sets the descripcion of this TipoCajero to the specified value.
     * 
     * @param descripcion the new descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets the marca of this TipoCajero.
     * @return retorna un Integer de la marca de cajero según se describe en {@link com.davivienda.atm.servidortea.administracion.constantes.MarcaCajero}
     */
    public Integer getMarca() {
        return this.marca;
    }

    /**
     * Sets the marca of this TipoCajero to the specified value.
     * 
     * @param marca the new marca
     */
    public void setMarca(Integer marca) {
        this.marca = marca;
    }

    /**
     * Gets the modelo of this TipoCajero.
     * 
     * @return the modelo
     */
    public String getModelo() {
        return this.modelo;
    }

    /**
     * Sets the modelo of this TipoCajero to the specified value.
     * 
     * @param modelo the new modelo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Gets the sistemaOperativo of this TipoCajero.
     * 
     * 
     * @return the sistemaOperativo
     */
    public Integer getSistemaOperativo() {
        return this.sistemaOperativo;
    }

    /**
     * Sets the sistemaOperativo of this TipoCajero to the specified value.
     * @param sistemaoperativo Sistema operativo
     */
    public void setSistemaOperativo(Integer sistemaoperativo) {
        this.sistemaOperativo = sistemaoperativo;
    }

    /**
     * Gets the aplicativocajero of this TipoCajero.
     * 
     * @return the aplicativocajero
     */
    public Integer getAplicativoCajero() {
        return this.aplicativoCajero;
    }

    /**
     * Sets the aplicativocajero of this TipoCajero to the specified value.
     * 
     * @param aplicativocajero the new aplicativocajero
     */
    public void setAplicativoCajero(Integer aplicativocajero) {
        this.aplicativoCajero = aplicativocajero;
    }

    /**
     * Gets the version of this TipoCajero.
     * 
     * @return the version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * Sets the version of this TipoCajero to the specified value.
     * 
     * @param version the new version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Gets the cajeroCollection of this TipoCajero.
     * 
     * @return the cajeroCollection
     */
    public Collection<Cajero> getCajeroCollection() {
        return this.cajeroCollection;
    }

    /**
     * Sets the cajeroCollection of this TipoCajero to the specified value.
     * 
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
        hash += (this.codigoTipoCajero != null ? this.codigoTipoCajero.hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this TipoCajero.  The result is 
     * <code>true</code> if and only if the argument is not null and is a TipoCajero object that 
     * has the same id field values as this object.
     * 
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCajero)) {
            return false;
        }
        TipoCajero other = (TipoCajero) object;
        if (this.codigoTipoCajero != other.codigoTipoCajero && (this.codigoTipoCajero == null || !this.codigoTipoCajero.equals(other.codigoTipoCajero))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Tipocajero[codigoTipoCajero=" + codigoTipoCajero + "]";
    }

    public Integer getFormatoDiarioElectronico() {
        return formatoDiarioElectronico;
    }

    public void setFormatoDiarioElectronico(Integer formatoDiarioElectronico) {
        this.formatoDiarioElectronico = formatoDiarioElectronico;
    }
    
    
 
    /**
     * Get the value of papelImpresion
     *
     * @return the value of papelImpresion
     */
    public String getPapelImpresion() {
        return papelImpresion;
    }

    /**
     * Set the value of papelImpresion
     *
     * @param papelImpresion new value of papelImpresion
     */
    public void setPapelImpresion(String papelImpresion) {
        this.papelImpresion = papelImpresion;
    }
    

    public TipoCajero actualizarEntity(TipoCajero obj) {
        setAplicativoCajero(obj.aplicativoCajero);
        setCodigoTipoCajero(obj.codigoTipoCajero);
        setDescripcion(obj.descripcion);
        setFormatoDiarioElectronico(obj.formatoDiarioElectronico);
        setMarca(obj.marca);
        setModelo(obj.modelo);
        setSistemaOperativo(obj.sistemaOperativo);
        setPapelImpresion(obj.papelImpresion);
        return this;
    }
}
