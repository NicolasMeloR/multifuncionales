package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
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
 * MovimientoHost.java
 *
 * Fecha       : 30 de abril de 2007, 11:40 AM
 *
 * Descripción :
 *
 * @author     : jjvargas
 *
 * @version    : $Id: MovimientoHost.java,v 1.1 2007/05/18 23:22:41 jjvargas Exp $
 */

@Entity
@Table(name = "MOVIMIENTOHOST")
@NamedQueries(
        value = {
        @NamedQuery(
                name = "MovimientoHost.Todos",
                query =
                "select object(obj) from MovimientoHost obj order by obj.fecha, obj.cajero.codigoCajero"),
        @NamedQuery(
                name = "MovimientoHost.RegistroUnico",
                query =
                "select object(obj) from MovimientoHost obj where obj.cajero.codigoCajero = :cajero and obj.fecha = :fecha and obj.numeroTransaccion = :numeroTransaccion")

}
        )

public class MovimientoHost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdMovimientoHost")
    @SequenceGenerator(name = "IdMovimientoHost", sequenceName = "IDMOVIMIENTOHOST", allocationSize = 100)    
    private Integer idMovimientoHost;

    private Integer codigoocca;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    private Integer numeroTransaccion;

    private String cuenta;

    private String tarjeta;

    private String tipoMovimiento;

    private Character indicadorCorreccion;

    private Character indicadorCiudad;

    private Long valorMovimiento;

    private String conceptomovimiento;

    @Version
    private Integer version;

    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO")
    @ManyToOne
    private Cajero cajero;

    @JoinColumn(name = "IDHOSTCARGUE", referencedColumnName = "IDHOSTCARGUE")
    @ManyToOne
    private HostCargue hostCargue;
    
    /**
     * Crea una nueva instancia de MovimientoHost
     */
    public MovimientoHost() {
    }

    /**
     * Creates a new instance of MovimientoHost with the specified values.
     * 
     * 
     * @param idMovimientoHost the idMovimientoHost of the MovimientoHost
     */
    public MovimientoHost(Integer idmovimientohost) {
        this.idMovimientoHost = idmovimientohost;
    }

    /**
     * Creates a new instance of MovimientoHost with the specified values.
     * 
     * 
     * 
     * @param idMovimientoHost the idMovimientoHost of the MovimientoHost
     * @param codigoocca the codigoocca of the MovimientoHost
     * @param fecha the fecha of the MovimientoHost
     * @param numeroTransaccion the numeroTransaccion of the MovimientoHost
     */
    public MovimientoHost(Integer idmovimientohost, Integer codigoocca, Date fecha, Integer numerotransaccion) {
        this.idMovimientoHost = idmovimientohost;
        this.codigoocca = codigoocca;
        this.fecha = fecha;
        this.numeroTransaccion = numerotransaccion;
    }

    /**
     * Gets the idMovimientoHost of this MovimientoHost.
     * 
     * 
     * @return the idMovimientoHost
     */
    public Integer getIdMovimientoHost() {
        return this.idMovimientoHost;
    }

    /**
     * Sets the idMovimientoHost of this MovimientoHost to the specified value.
     * 
     * 
     * @param idMovimientoHost the new idMovimientoHost
     */
    public void setIdMovimientoHost(Integer idmovimientohost) {
        this.idMovimientoHost = idmovimientohost;
    }

    /**
     * Gets the codigoocca of this MovimientoHost.
     * 
     * @return the codigoocca
     */
    public Integer getCodigoOcca() {
        return this.codigoocca;
    }

    /**
     * Sets the codigoocca of this MovimientoHost to the specified value.
     * 
     * @param codigoocca the new codigoocca
     */
    public void setCodigoOcca(Integer codigoocca) {
        this.codigoocca = codigoocca;
    }

    /**
     * Gets the fecha of this MovimientoHost.
     * 
     * @return the fecha
     */
    public Date getFecha() {
        return this.fecha;
    }

    /**
     * Sets the fecha of this MovimientoHost to the specified value.
     * 
     * @param fecha the new fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Gets the numeroTransaccion of this MovimientoHost.
     * 
     * 
     * @return the numeroTransaccion
     */
    public Integer getNumeroTransaccion() {
        return this.numeroTransaccion;
    }

    /**
     * Sets the numeroTransaccion of this MovimientoHost to the specified value.
     * 
     * 
     * @param numeroTransaccion the new numeroTransaccion
     */
    public void setNumeroTransaccion(Integer numerotransaccion) {
        this.numeroTransaccion = numerotransaccion;
    }

    /**
     * Gets the cuenta of this MovimientoHost.
     * 
     * @return the cuenta
     */
    public String getCuenta() {
        return this.cuenta;
    }

    /**
     * Sets the cuenta of this MovimientoHost to the specified value.
     * 
     * @param cuenta the new cuenta
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * Gets the tarjeta of this MovimientoHost.
     * 
     * @return the tarjeta
     */
    public String getTarjeta() {
        return this.tarjeta;
    }

    /**
     * Sets the tarjeta of this MovimientoHost to the specified value.
     * 
     * @param tarjeta the new tarjeta
     */
    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    /**
     * Gets the tipoMovimiento of this MovimientoHost.
     * 
     * 
     * @return the tipoMovimiento
     */
    public String getTipoMovimiento() {
        return this.tipoMovimiento;
    }

    /**
     * Sets the tipoMovimiento of this MovimientoHost to the specified value.
     * 
     * 
     * @param tipoMovimiento the new tipoMovimiento
     */
    public void setTipoMovimiento(String tipomovimiento) {
        this.tipoMovimiento = tipomovimiento;
    }

    /**
     * Gets the indicadorCorreccion of this MovimientoHost.
     * 
     * 
     * @return the indicadorCorreccion
     */
    public Character getIndicadorCorreccion() {
        return this.indicadorCorreccion;
    }

    /**
     * Sets the indicadorCorreccion of this MovimientoHost to the specified value.
     * 
     * 
     * @param indicadorCorreccion the new indicadorCorreccion
     */
    public void setIndicadorCorreccion(Character indicadorcorreccion) {
        this.indicadorCorreccion = indicadorcorreccion;
    }

    /**
     * Gets the indicadorCiudad of this MovimientoHost.
     * 
     * 
     * @return the indicadorCiudad
     */
    public Character getIndicadorCiudad() {
        return this.indicadorCiudad;
    }

    /**
     * Sets the indicadorCiudad of this MovimientoHost to the specified value.
     * 
     * 
     * @param indicadorCiudad the new indicadorCiudad
     */
    public void setIndicadorCiudad(Character indicadorciudad) {
        this.indicadorCiudad = indicadorciudad;
    }

    /**
     * Gets the valorMovimiento of this MovimientoHost.
     * 
     * 
     * @return the valorMovimiento
     */
    public Long getValorMovimiento() {
        return this.valorMovimiento;
    }

    /**
     * Sets the valorMovimiento of this MovimientoHost to the specified value.
     * 
     * 
     * @param valorMovimiento the new valorMovimiento
     */
    public void setValorMovimiento(Long valormovimiento) {
        this.valorMovimiento = valormovimiento;
    }

    /**
     * Gets the conceptomovimiento of this MovimientoHost.
     * 
     * @return the conceptomovimiento
     */
    public String getConceptoMovimiento() {
        return this.conceptomovimiento;
    }

    /**
     * Sets the conceptomovimiento of this MovimientoHost to the specified value.
     * 
     * @param conceptomovimiento the new conceptomovimiento
     */
    public void setConceptoMovimiento(String conceptomovimiento) {
        this.conceptomovimiento = conceptomovimiento;
    }

    /**
     * Gets the version of this MovimientoHost.
     * 
     * @return the version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * Sets the version of this MovimientoHost to the specified value.
     * 
     * @param version the new version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Gets the cajero of this MovimientoHost.
     * 
     * 
     * @return the cajero
     */
    public Cajero getCajero() {
        return this.cajero;
    }

    /**
     * Sets the cajero of this MovimientoHost to the specified value.
     * 
     * 
     * @param cajero the new cajero
     */
    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    /**
     * Gets the hostCargue of this MovimientoHost.
     * 
     * 
     * @return the hostCargue
     */
    public HostCargue getIdHostCargue() {
        return this.hostCargue;
    }

    /**
     * Sets the hostCargue of this MovimientoHost to the specified value.
     * 
     * 
     * @param hostCargue the new hostCargue
     */
    public void setIdHostCargue(HostCargue idhostcargue) {
        this.hostCargue = idhostcargue;
    }

    /**
     * Returns a hash code value for the object.  This implementation computes 
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idMovimientoHost != null ? this.idMovimientoHost.hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this MovimientoHost.  The result is 
     * <code>true</code> if and only if the argument is not null and is a MovimientoHost object that 
     * has the same id field values as this object.
     * 
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimientoHost)) {
            return false;
        }
        MovimientoHost other = (MovimientoHost)object;
        if (this.idMovimientoHost != other.idMovimientoHost && (this.idMovimientoHost == null || !this.idMovimientoHost.equals(other.idMovimientoHost))) return false;
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys[idmovimientohost=" + idMovimientoHost + "]";
    }
    
}
