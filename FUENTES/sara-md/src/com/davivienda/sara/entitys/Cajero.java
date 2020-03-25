package com.davivienda.sara.entitys;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.Version;

/**
 * Cajero.java
 *
 * Fecha :11 de abril de 2007, 01:41 PM
 *
 * Descripción : Entity de la tabla Cajero
 *
 * @author :jjvargas
 *
 * @version : $Id: Cajero.java,v 1.2 2007/05/18 23:22:41 jjvargas Exp $
 *
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Cajero.RegistroUnico",
            query = "select object(obj) from Cajero obj where obj.codigoCajero = :cajero"),
    @NamedQuery(name = "Cajero.todos",
            query = "select object(obj) from Cajero obj order by obj.codigoCajero"),
    @NamedQuery(name = "Cajero.AllActivos",
            query = "select object(obj) from Cajero obj where obj.activo = 1  and obj.tipocajeromulti=0 order by obj.codigoCajero"),
    @NamedQuery(name = "Cajero.NoTransmitidos",
            query = "select object(obj) from Cajero obj where obj.codigoCajero"
            + " NOT IN (select obj2.codigoCajero from EdcCargue obj2 where obj2.ciclo =:ciclo) "
            + " and obj.activo = 1 and obj.tipocajeromulti=0 order by obj.codigoCajero"),
    @NamedQuery(name = "Cajero.AllActivMulti",
            query = "select object(obj) from Cajero obj where obj.activo = 1  and obj.tipocajeromulti=1 order by obj.codigoCajero"),
    @NamedQuery(name = "Cajero.MultiNoTrans",
            query = "select object(obj) from Cajero obj where obj.codigoCajero"
            + " NOT IN (select obj2.codigocajero from Edcarguemultifuncional obj2 where obj2.ciclo =:ciclo and (obj2.error=0 or obj2.error=103)) "
            + " and obj.activo = 1 and obj.tipocajeromulti=1 order by obj.codigoCajero")})

public class Cajero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Integer codigoCajero;
    private String serie;
    private Integer activo;
    private String nombre;
    private String versionAplicativo;
    private Integer tipoProvision;
    private Long provisionAprobada;
    private Integer gav1Denominacion;
    private Integer gav2Denominacion;
    private Integer gav3Denominacion;
    private Integer gav4Denominacion;
    private Integer numeroGavetas;
    private Integer tipoLecturaEDC;
    private String ubicacionEDC;
    private String tipoEncripcion;
    private Integer tipocajeromulti;
    private Integer idtransportadora2;
    private String horario;
    private String red;
    private String direccion;

    @Version
    private Integer version;
    @JoinColumn(name = "CODIGOOCCA", referencedColumnName = "CODIGOOCCA")
    @ManyToOne
    private Occa occa;
    @JoinColumn(name = "CODIGOTIPOCAJERO", referencedColumnName = "CODIGOTIPOCAJERO")
    @ManyToOne
    private TipoCajero tipoCajero;
    @JoinColumn(name = "IDTRANSPORTADORA", referencedColumnName = "IDTRANSPORTADORA")
    @ManyToOne
    private Transportadora transportadora;
    @JoinColumn(name = "CODIGOUBICACION", referencedColumnName = "CODIGOUBICACION")
    @ManyToOne
    private Ubicacion ubicacion;

    @JoinColumn(name = "CODIGOOFICINAMULTI", referencedColumnName = "CODIGOOFICINAMULTI")
    @ManyToOne
    private Oficinamulti oficinaMulti;

    @Column(name = "MULTI_DISPENSADOR")
    private Integer codigoDispensador;

    /**
     * Crea una nueva instancia de Cajero
     */
    public Cajero() {
    }

    /**
     * Creates a new instance of Cajero with the specified values.
     *
     * @param codigocajero the codigoCajero of the Cajero
     */
    public Cajero(Integer codigocajero) {
        this.codigoCajero = codigocajero;
    }

    /**
     * Gets the codigoCajero of this Cajero.
     *
     * @return the codigoCajero
     */
    public Integer getCodigoCajero() {
        return this.codigoCajero;
    }

    /**
     * Sets the codigoCajero of this Cajero to the specified value.
     *
     * @param codigocajero the new codigoCajero
     */
    public void setCodigoCajero(Integer codigocajero) {
        this.codigoCajero = codigocajero;
    }

    /**
     * Sets the codigoCajero of this Cajero to the specified value.
     *
     * @param codigo el cï¿½digo del cajero
     * @throws java.lang.NumberFormatException
     */
    public void setCodigoCajero(String codigo) throws NumberFormatException {
        this.codigoCajero = Integer.valueOf(codigo);
    }

    /**
     * Gets the serie of this Cajero.
     *
     * @return the serie
     */
    public String getSerie() {
        return this.serie;
    }

    /**
     * Sets the serie of this Cajero to the specified value.
     *
     * @param serie the new serie
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * Gets the nombre of this Cajero.
     *
     * @return the nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Sets the nombre of this Cajero to the specified value.
     *
     * @param nombre the new nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the versionAplicativo of this Cajero.
     *
     * @return the versionAplicativo
     */
    public String getVersionAplicativo() {
        return this.versionAplicativo;
    }

    /**
     * Sets the versionAplicativo of this Cajero to the specified value.
     *
     * @param versionaplicativo the new versionAplicativo
     */
    public void setVersionAplicativo(String versionaplicativo) {
        this.versionAplicativo = versionaplicativo;
    }

    /**
     * Gets the tipoProvision of this Cajero.
     *
     * @return the tipoProvision
     */
    public Integer getTipoProvision() {
        return this.tipoProvision;
    }

    /**
     * Sets the tipoProvision of this Cajero to the specified value.
     *
     * @param tipoprovision the new tipoProvision
     */
    public void setTipoProvision(Integer tipoprovision) {
        this.tipoProvision = tipoprovision;
    }

    /**
     * Gets the provisionAprobada of this Cajero.
     *
     * @return the provisionAprobada
     */
    public Long getProvisionAprobada() {
        return this.provisionAprobada;
    }

    /**
     * Sets the provisionAprobada of this Cajero to the specified value.
     *
     * @param provisionaprobada the new provisionAprobada
     */
    public void setProvisionAprobada(Long provisionaprobada) {
        this.provisionAprobada = provisionaprobada;
    }

    /**
     * Gets the gav1Denominacion of this Cajero.
     *
     * @return the gav1Denominacion
     */
    public Integer getGav1Denominacion() {
        return this.gav1Denominacion;
    }

    /**
     * Sets the gav1Denominacion of this Cajero to the specified value.
     *
     * @param gav1Denominacion the new gav1Denominacion
     */
    public void setGav1Denominacion(Integer gav1Denominacion) {
        this.gav1Denominacion = gav1Denominacion;
    }

    /**
     * Gets the gav2Denominacion of this Cajero.
     *
     * @return the gav2Denominacion
     */
    public Integer getGav2Denominacion() {
        return this.gav2Denominacion;
    }

    /**
     * Sets the gav2Denominacion of this Cajero to the specified value.
     *
     * @param gav2Denominacion the new gav2Denominacion
     */
    public void setGav2Denominacion(Integer gav2Denominacion) {
        this.gav2Denominacion = gav2Denominacion;
    }

    /**
     * Gets the gav3Denominacion of this Cajero.
     *
     * @return the gav3Denominacion
     */
    public Integer getGav3Denominacion() {
        return this.gav3Denominacion;
    }

    /**
     * Sets the gav3Denominacion of this Cajero to the specified value.
     *
     * @param gav3Denominacion the new gav3Denominacion
     */
    public void setGav3Denominacion(Integer gav3Denominacion) {
        this.gav3Denominacion = gav3Denominacion;
    }

    /**
     * Gets the gav4Denominacion of this Cajero.
     *
     * @return the gav4Denominacion
     */
    public Integer getGav4Denominacion() {
        return this.gav4Denominacion;
    }

    /**
     * Sets the gav4Denominacion of this Cajero to the specified value.
     *
     * @param gav4Denominacion the new gav4Denominacion
     */
    public void setGav4Denominacion(Integer gav4Denominacion) {
        this.gav4Denominacion = gav4Denominacion;
    }

    /**
     * Gets the numeroGavetas of this Cajero.
     *
     * @return the numeroGavetas
     */
    public Integer getNumeroGavetas() {
        return this.numeroGavetas;
    }

    /**
     * Sets the numeroGavetas of this Cajero to the specified value.
     *
     * @param numeroGavetas the new numeroGavetas
     */
    public void setNumeroGavetas(Integer numeroGavetas) {
        this.numeroGavetas = numeroGavetas;
    }

    /**
     * Gets the version of this Cajero.
     *
     * @return the version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * Sets the version of this Cajero to the specified value.
     *
     * @param version the new version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Gets the occa of this Cajero.
     *
     *
     * @return the occa
     */
    public Occa getOcca() {
        return this.occa;
    }

    /**
     * Sets the occa of this Cajero to the specified value.
     *
     *
     * @param occa the new occa
     */
    public void setOcca(Occa occa) {
        this.occa = occa;
    }

    /**
     * Gets the tipoCajero of this Cajero.
     *
     *
     * @return the tipoCajero
     */
    public TipoCajero getTipoCajero() {
        return this.tipoCajero;
    }

    /**
     * Sets the tipoCajero of this Cajero to the specified value.
     *
     *
     * @param tipoCajero the new tipoCajero
     */
    public void setTipoCajero(TipoCajero tipoCajero) {
        this.tipoCajero = tipoCajero;
    }

    /**
     * Gets the transportadora of this Cajero.
     *
     *
     * @return the transportadora
     */
    public Transportadora getTransportadora() {
        return this.transportadora;
    }

    /**
     * Sets the transportadora of this Cajero to the specified value.
     *
     *
     * @param transportadora the new transportadora
     */
    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
    }

    /**
     * Gets the ubicacion of this Cajero.
     *
     *
     * @return the ubicacion
     */
    public Ubicacion getUbicacion() {
        return this.ubicacion;
    }

    /**
     * Sets the ubicacion of this Cajero to the specified value.
     *
     *
     * @param ubicacion the new ubicacion
     */
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getHorario() {
        return this.horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getRed() {
        return this.red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Returns a hash code value for the object. This implementation computes a
     * hash code value based on the id fields in this object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.codigoCajero != null ? this.codigoCajero.hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this Cajero. The result is
     * <code>true</code> if and only if the argument is not null and is a Cajero
     * object that has the same id field values as this object.
     *
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cajero)) {
            return false;
        }
        Cajero other = (Cajero) object;
        if (this.codigoCajero != other.codigoCajero && (this.codigoCajero == null
                || !this.codigoCajero.equals(other.codigoCajero))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the object. This implementation
     * constructs that representation based on the id fields.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Cajero[codigocajero=" + codigoCajero + "]";
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getTipoLecturaEDC() {
        return tipoLecturaEDC;
    }

    public void setTipoLecturaEDC(Integer tipoLecturaEDC) {
        this.tipoLecturaEDC = tipoLecturaEDC;
    }

    public String getUbicacionEDC() {
        return ubicacionEDC;
    }

    public void setUbicacionEDC(String ubicacionEDC) {
        this.ubicacionEDC = ubicacionEDC;
    }

    /**
     * Get the value of tipoEncripcion
     *
     * @return the value of tipoEncripcion
     */
    public String getTipoEncripcion() {
        return tipoEncripcion;
    }

    /**
     * Set the value of tipoEncripcion
     *
     * @param tipoEncripcion new value of tipoEncripcion
     */
    public void setTipoEncripcion(String tipoEncripcion) {
        this.tipoEncripcion = tipoEncripcion;
    }

    public Integer getIdtransportadora2() {
        return idtransportadora2;
    }

    public void setIdtransportadora2(Integer idtransportadora2) {
        this.idtransportadora2 = idtransportadora2;
    }

    public Integer getTipocajeroMulti() {
        return tipocajeromulti;
    }

    public void setTipocajeroMulti(Integer tipocajeromulti) {
        this.tipocajeromulti = tipocajeromulti;
    }

    public Oficinamulti getOficinaMulti() {
        return oficinaMulti;
    }

    public void setOficinaMulti(Oficinamulti oficinaMulti) {
        this.oficinaMulti = oficinaMulti;
    }

    public Integer getCodigoDispensador() {
        return codigoDispensador;
    }

    public void setCodigoDispensador(Integer codigoDispensador) {
        this.codigoDispensador = codigoDispensador;
    }
    
    

    public Cajero actualizarEntity(Cajero obj) {

        setActivo(obj.activo);
        setCodigoCajero(obj.codigoCajero);
        setGav1Denominacion(obj.gav1Denominacion);
        setGav2Denominacion(obj.gav2Denominacion);
        setGav3Denominacion(obj.gav3Denominacion);
        setGav4Denominacion(obj.gav4Denominacion);
        setNombre(obj.nombre);
        setNumeroGavetas(obj.numeroGavetas);
        setOcca(obj.occa);
        setProvisionAprobada(obj.provisionAprobada);
        setSerie(obj.serie);
        setTipoCajero(obj.tipoCajero);
        setTipoLecturaEDC(obj.tipoLecturaEDC);
        setTipoProvision(obj.tipoProvision);
        setTransportadora(obj.transportadora);
        setUbicacion(obj.ubicacion);
        setUbicacionEDC(obj.ubicacionEDC);
        setVersionAplicativo(obj.versionAplicativo);
        setTipoEncripcion(obj.tipoEncripcion);
        setOficinaMulti(obj.oficinaMulti);
        setTipocajeroMulti(obj.tipocajeromulti);
        setIdtransportadora2(obj.idtransportadora2);
        setHorario(obj.horario);
        setRed(obj.red);
        setDireccion(obj.direccion);
        setCodigoDispensador(obj.getCodigoDispensador());
        return this;
    }
}
