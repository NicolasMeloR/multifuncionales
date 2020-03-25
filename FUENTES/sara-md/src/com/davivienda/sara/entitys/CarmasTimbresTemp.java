/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CARMAS_TIMBRES_TEMP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarmasTimbresTemp.findAll", query = "SELECT c FROM CarmasTimbresTemp c")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByCajero", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.cajero = :cajero")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByProvdiasgteMaq", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.provdiasgteMaq = :provdiasgteMaq")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByProvdiasgteLin", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.provdiasgteLin = :provdiasgteLin")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByDiferencias", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.diferencias = :diferencias")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByObservacion", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.observacion = :observacion")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByOcca", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.occa = :occa")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByAumento", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.aumento = :aumento")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByDisminucion", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.disminucion = :disminucion")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findBySobrante", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.sobrante = :sobrante")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByFaltante", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.faltante = :faltante")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByNovedad", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.novedad = :novedad")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByAsignadoA", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.asignadoA = :asignadoA")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByProveedor", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.proveedor = :proveedor")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByClasificacion", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.clasificacion = :clasificacion")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByTipificacionTransportadora", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.tipificacionTransportadora = :tipificacionTransportadora")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.todos", query = "SELECT c FROM CarmasTimbresTemp c")
    ,
    @NamedQuery(name = "CarmasTimbresTemp.findByIdCargueMasivoTimbresTmp", query = "SELECT c FROM CarmasTimbresTemp c WHERE c.idCargueMasivoTimbresTmp = :idCargueMasivoTimbresTmp")})
public class CarmasTimbresTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "CAJERO")
    private String cajero;
    @Column(name = "PROVDIASGTE_MAQ")
    private String provdiasgteMaq;
    @Column(name = "PROVDIASGTE_LIN")
    private String provdiasgteLin;
    @Column(name = "DIFERENCIAS")
    private String diferencias;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "OCCA")
    private String occa;
    @Column(name = "AUMENTO")
    private String aumento;
    @Column(name = "DISMINUCION")
    private String disminucion;
    @Column(name = "SOBRANTE")
    private String sobrante;
    @Column(name = "FALTANTE")
    private String faltante;
    @Column(name = "NOVEDAD")
    private String novedad;
    @Column(name = "ASIGNADO_A")
    private String asignadoA;
    @Column(name = "PROVEEDOR")
    private String proveedor;
    @Column(name = "CLASIFICACION")
    private String clasificacion;
    @Column(name = "TIPIFICACION_TRANSPORTADORA")
    private String tipificacionTransportadora;
    @Column(name = "RESULTADO_CARGA")
    private String resultadoCarga;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CARMAS_TIMBRES_TEMP")
    private BigDecimal idCargueMasivoTimbresTmp;

    public CarmasTimbresTemp() {
    }

    public CarmasTimbresTemp(BigDecimal idCargueMasivoTimbresTmp) {
        this.idCargueMasivoTimbresTmp = idCargueMasivoTimbresTmp;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getProvdiasgteMaq() {
        return provdiasgteMaq;
    }

    public void setProvdiasgteMaq(String provdiasgteMaq) {
        this.provdiasgteMaq = provdiasgteMaq;
    }

    public String getProvdiasgteLin() {
        return provdiasgteLin;
    }

    public void setProvdiasgteLin(String provdiasgteLin) {
        this.provdiasgteLin = provdiasgteLin;
    }

    public String getDiferencias() {
        return diferencias;
    }

    public void setDiferencias(String diferencias) {
        this.diferencias = diferencias;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getOcca() {
        return occa;
    }

    public void setOcca(String occa) {
        this.occa = occa;
    }

    public String getAumento() {
        return aumento;
    }

    public void setAumento(String aumento) {
        this.aumento = aumento;
    }

    public String getDisminucion() {
        return disminucion;
    }

    public void setDisminucion(String disminucion) {
        this.disminucion = disminucion;
    }

    public String getSobrante() {
        return sobrante;
    }

    public void setSobrante(String sobrante) {
        this.sobrante = sobrante;
    }

    public String getFaltante() {
        return faltante;
    }

    public void setFaltante(String faltante) {
        this.faltante = faltante;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getTipificacionTransportadora() {
        return tipificacionTransportadora;
    }

    public void setTipificacionTransportadora(String tipificacionTransportadora) {
        this.tipificacionTransportadora = tipificacionTransportadora;
    }

    public BigDecimal getIdCargueMasivoTimbresTmp() {
        return idCargueMasivoTimbresTmp;
    }

    public void setIdCargueMasivoTimbresTmp(BigDecimal idCargueMasivoTimbresTmp) {
        this.idCargueMasivoTimbresTmp = idCargueMasivoTimbresTmp;
    }

    public String getResultadoCarga() {
        return resultadoCarga;
    }

    public void setResultadoCarga(String resultadoCarga) {
        this.resultadoCarga = resultadoCarga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargueMasivoTimbresTmp != null ? idCargueMasivoTimbresTmp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarmasTimbresTemp)) {
            return false;
        }
        CarmasTimbresTemp other = (CarmasTimbresTemp) object;
        if ((this.idCargueMasivoTimbresTmp == null && other.idCargueMasivoTimbresTmp != null) || (this.idCargueMasivoTimbresTmp != null && !this.idCargueMasivoTimbresTmp.equals(other.idCargueMasivoTimbresTmp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CarmasTimbresTemp{" + "cajero=" + cajero + ", provdiasgteMaq=" + provdiasgteMaq + ", provdiasgteLin=" + provdiasgteLin + ", diferencias=" + diferencias + ", observacion=" + observacion + ", occa=" + occa + ", aumento=" + aumento + ", disminucion=" + disminucion + ", sobrante=" + sobrante + ", faltante=" + faltante + ", novedad=" + novedad + ", asignadoA=" + asignadoA + ", proveedor=" + proveedor + ", clasificacion=" + clasificacion + ", tipificacionTransportadora=" + tipificacionTransportadora + ", idCarugeMasivoTimbresTmp=" + idCargueMasivoTimbresTmp + '}';
    }

}
