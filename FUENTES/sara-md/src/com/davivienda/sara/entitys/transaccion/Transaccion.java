package com.davivienda.sara.entitys.transaccion;

import com.davivienda.sara.entitys.*;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity class Transaccion
 * Transaccion.java
 *
 * Fecha       :24 de enero de 2007, 11:47 PM
 *
 * Descripción :Entity para el registro de transacciones de tipoTransacción en la base de datos.
 *              Se debe tener en cuenta que utiliza queries nativos Oracle para generar estadísticas
 *
 * @author     :jjvargas
 *
 * @version    :$Id: Transaccion.java,v 1.2 2007/05/18 23:22:41 jjvargas Exp $
 */

@Entity
@Table(name = "TRANSACCION")
@NamedQueries( {
    @NamedQuery(
    name = "Transaccion.RegistroUnico",
            query = "select object(edc) from Transaccion edc where edc.cajero = :cajero"),
            @NamedQuery(
            name = "Transaccion.Todos",
            query = "select object(edc) from Transaccion edc order by edc.transaccionPK.codigoCajero, edc.transaccionPK.fechaTransaccion"),
            @NamedQuery(
            name = "Transaccion.CajeroRangoFecha",
            query = "select object(obj) from Transaccion obj " +
            "        where obj.transaccionPK.codigoCajero =:codigoCajero and " +
            "              obj.transaccionPK.fechaTransaccion between :fechaInicial and :fechaFinal " +
            "        order by obj.transaccionPK.fechaTransaccion"),
            @NamedQuery(
            name = "Transaccion.CajeroTransaccionFecha",
            query = "select object(obj) from Transaccion obj " +
            "        where obj.transaccionPK.codigoCajero =:codigoCajero and " +
            "              obj.transaccionPK.fechaTransaccion between :fechaInicial and :fechaFinal and" +
            "              obj.transaccionPK.numeroTransaccion = :numeroTransaccion" +
            "        order by obj.transaccionPK.fechaTransaccion"),
            @NamedQuery(
            name = "Transaccion.TransaccionBuscarReintegros",
            query = "select object(obj) from Transaccion obj " +
            "        where obj.transaccionPK.codigoCajero =:codigoCajero and " +
            "              obj.transaccionPK.fechaTransaccion between :fechaInicial and :fechaFinal and " +
            "              obj.codigoTerminacionTransaccion  IN ('0121','0030','0131') "  +
            "        order by obj.transaccionPK.fechaTransaccion")


})
        
        
        
public class Transaccion implements Serializable {
    
    @EmbeddedId
    protected TransaccionPK transaccionPK;
        
    private Integer tipoTransaccion;
    
    private Integer codigoTransaccion;
    
    private String errorTransaccion;
    
    private Long valorSolicitado;
    
    private Long valorEntregado;
    
    private String tarjeta;
    
    private String cuenta;
    
    private String codigoTerminacionTransaccion;
    
    private String referencia;
    
    @Version
    private Integer version;
    
    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO", insertable = false, updatable = false)
    @ManyToOne
    private Cajero cajero;
    
    
    /** Crea una nueva instancia de Transaccion */
    public Transaccion() {
    }
    
    /**
     * Creates a new instance of Transaccion with the specified values.
     *
     * 
     * @param idtransaccion 
     */
    public Transaccion(TransaccionPK transaccionPK) {
        this.transaccionPK = transaccionPK;
    }

    public Transaccion(TransaccionPK transaccionPK, Integer tipotransaccion, Integer codigotransaccion) {
        this.transaccionPK = transaccionPK;
        this.tipoTransaccion = tipotransaccion;
        this.codigoTransaccion = codigotransaccion;
    }

    public Transaccion(Integer codigocajero, Integer numerotransaccion, Date fechatransaccion) {
        this.transaccionPK = new TransaccionPK(codigocajero, numerotransaccion, fechatransaccion);
    }

    public TransaccionPK getTransaccionPK() {
        return transaccionPK;
    }

    public void setTransaccionPK(TransaccionPK transaccionPK) {
        this.transaccionPK = transaccionPK;
    }
    
    /**
     * Gets the tipoTransaccion of this Transaccion.
     *
     * @return the tipoTransaccion
     */
    public Integer getTipoTransaccion() {
        return this.tipoTransaccion;
    }
    
    /**
     * Sets the tipoTransaccion of this Transaccion to the specified value.
     *
     * @param tipotransaccion 
     */
    public void setTipoTransaccion(Integer tipotransaccion) {
        this.tipoTransaccion = tipotransaccion;
    }
    
    /**
     * Gets the codigoTransaccion of this Transaccion.
     *
     * @return the codigoTransaccion
     */
    public Integer getCodigoTransaccion() {
        return this.codigoTransaccion;
    }
    
    /**
     * Sets the codigoTransaccion of this Transaccion to the specified value.
     *
     * @param codigotransaccion 
     */
    public void setCodigoTransaccion(Integer codigotransaccion) {
        this.codigoTransaccion = codigotransaccion;
    }
    
    
    
    /**
     * Gets the errorTransaccion of this Transaccion.
     *
     * @return the errorTransaccion
     */
    public String getErrorTransaccion() {
        return this.errorTransaccion;
    }
    
    /**
     * Sets the errorTransaccion of this Transaccion to the specified value.
     *
     * @param errortransaccion 
     */
    public void setErrorTransaccion(String errortransaccion) {
        this.errorTransaccion = errortransaccion;
    }
    
    /**
     * Gets the valorSolicitado of this Transaccion.
     *
     * @return the valorSolicitado
     */
    public Long getValorSolicitado() {
        return this.valorSolicitado;
    }
    
    /**
     * Sets the valorSolicitado of this Transaccion to the specified value.
     *
     * @param valorsolicitado 
     */
    public void setValorSolicitado(Long valorsolicitado) {
        this.valorSolicitado = valorsolicitado;
    }
    
    /**
     * Gets the valorEntregado of this Transaccion.
     *
     * @return the valorEntregado
     */
    public Long getValorEntregado() {
        return this.valorEntregado;
    }
    
    /**
     * Sets the valorEntregado of this Transaccion to the specified value.
     *
     * @param valorentregado 
     */
    public void setValorEntregado(Long valorentregado) {
        this.valorEntregado = valorentregado;
    }
    
    /**
     * Gets the tarjeta of this Transaccion.
     * @return the tarjeta
     */
    public String getTarjeta() {
        return this.tarjeta;
    }
    
    /**
     * Sets the tarjeta of this Transaccion to the specified value.
     * @param tarjeta the new tarjeta
     */
    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
    
    /**
     * Gets the cuenta of this Transaccion.
     * @return the cuenta
     */
    public String getCuenta() {
        return this.cuenta;
    }
    
    /**
     * Sets the cuenta of this Transaccion to the specified value.
     * @param cuenta the new cuenta
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    
    /**
     * Gets the codigoTerminacionTransaccion of this Transaccion.
     *
     * @return the codigoTerminacionTransaccion
     */
    public String getCodigoTerminacionTransaccion() {
        return this.codigoTerminacionTransaccion;
    }
    
    /**
     * Sets the codigoTerminacionTransaccion of this Transaccion to the specified value.
     *
     * @param codigoterminaciontransaccion 
     */
    public void setCodigoTerminacionTransaccion(String codigoterminaciontransaccion) {
        this.codigoTerminacionTransaccion = codigoterminaciontransaccion;
    }
    
    /**
     * Gets the referencia of this Transaccion.
     * @return the referencia
     */
    public String getReferencia() {
        return this.referencia;
    }
    
    /**
     * Sets the referencia of this Transaccion to the specified value.
     * @param referencia the new referencia
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    /**
     * Gets the version of this Transaccion.
     * @return the version
     */
    public Integer getVersion() {
        return this.version;
    }
    
    /**
     * Sets the version of this Transaccion to the specified value.
     * @param version the new version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     * Gets the cajero of this Transaccion.
     *
     * @return the cajero
     */
    public Cajero getCajero() {
        return this.cajero;
    }
    
    /**
     * Sets the cajero of this Transaccion to the specified value.
     *
     * @param cajero 
     */
    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }
    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.transaccionPK == null && other.transaccionPK != null) || (this.transaccionPK != null && !this.transaccionPK.equals(other.transaccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.transaccionPK != null ? this.transaccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.transaccion.Transaccion[transaccionPK=" + transaccionPK + "]";
    }
    
}
