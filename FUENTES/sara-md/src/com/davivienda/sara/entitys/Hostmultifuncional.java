
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lpulgari
 */
@Entity
@Table(name = "HOSTMULTIFUNCIONAL")

@NamedQueries( {
    @NamedQuery(
    name = "Hostmultifuncional.registroUnico",
            query = "select object(txm) from Hostmultifuncional txm where txm.hostmultifuncionalPK.codigocajero = :codigocajero and txm.hostmultifuncionalPK.fechasistema = :fechasistema and txm.hostmultifuncionalPK.talon = :talon"),             
    @NamedQuery(
    name = "Hostmultifuncional.todos", 
             query = "select object(txm) from Hostmultifuncional txm")  ,
    @NamedQuery(
             name = "Hostmultifuncional.CajeroTxFecha",
         query = "select object(obj) from Hostmultifuncional obj " +
            "        where obj.hostmultifuncionalPK.codigocajero =:codigocajero and " +
            "              obj.fecha between :fechaInicial and :fechaFinal and" +
            "              obj.hostmultifuncionalPK.talon = :talon " + 
            "        order by obj.fecha")
})


public class Hostmultifuncional implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HostmultifuncionalPK hostmultifuncionalPK;
    @Column(name = "NUMEROCUENTA")
    private String numerocuenta;
    @Column(name = "DATOSTARJETA")
    private String datostarjeta;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "TIPOTRANSACCION", nullable = false)
    private Integer tipotransaccion;    
    @Column(name = "CODIGOOFICINA", nullable = false)
    private Integer codigooficina;
    @Column(name = "INDICES")
    private String indices;
    @Column(name = "TIPOTARJETA")
    private String tipotarjeta;
    @Column(name = "VALOR")
    private Long valor;
    @Column(name = "FILLER")
    private String filler;
    @Column(name = "CONCEPTOOCCA")
    private Integer conceptoocca;
    @Column(name = "VERSION")
    private Short version;


    public Hostmultifuncional() {
    }

    public Hostmultifuncional(HostmultifuncionalPK hostmultifuncionalPK) {
        this.hostmultifuncionalPK = hostmultifuncionalPK;
    }

    public Hostmultifuncional(HostmultifuncionalPK hostmultifuncionalPK, Date fecha, Integer tipotransaccion, Integer codigooficina) {
        this.hostmultifuncionalPK = hostmultifuncionalPK;
        this.fecha = fecha;
        this.tipotransaccion = tipotransaccion;
        this.codigooficina = codigooficina;
    }

    public Hostmultifuncional(Integer codigocajero, Date fechasistema, Integer talon) {
        this.hostmultifuncionalPK = new HostmultifuncionalPK(codigocajero, fechasistema, talon);
    }

    public Integer getCodigooficina() {
        return codigooficina;
    }

    public void setCodigooficina(Integer codigooficina) {
        this.codigooficina = codigooficina;
    }

    public Integer getTipotransaccion() {
        return tipotransaccion;
    }

    public void setTipotransaccion(Integer tipotransaccion) {
        this.tipotransaccion = tipotransaccion;
    }
    
    public HostmultifuncionalPK getHostmultifuncionalPK() {
        return hostmultifuncionalPK;
    }

    public void setHostmultifuncionalPK(HostmultifuncionalPK hostmultifuncionalPK) {
        this.hostmultifuncionalPK = hostmultifuncionalPK;
    }

    public String getNumerocuenta() {
        return numerocuenta;
    }

    public void setNumerocuenta(String numerocuenta) {
        this.numerocuenta = numerocuenta;
    }

    public String getDatostarjeta() {
        return datostarjeta;
    }

    public void setDatostarjeta(String datostarjeta) {
        this.datostarjeta = datostarjeta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

   

    public void setCodigooficina(int codigooficina) {
        this.codigooficina = codigooficina;
    }

    public String getIndices() {
        return indices;
    }

    public void setIndices(String indices) {
        this.indices = indices;
    }

    public String getTipotarjeta() {
        return tipotarjeta;
    }

    public void setTipotarjeta(String tipotarjeta) {
        this.tipotarjeta = tipotarjeta;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }
        public Integer getConceptoocca() {
        return conceptoocca;
    }

    public void setConceptoocca(Integer conceptoocca) {
        this.conceptoocca = conceptoocca;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hostmultifuncionalPK != null ? hostmultifuncionalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hostmultifuncional)) {
            return false;
        }
        Hostmultifuncional other = (Hostmultifuncional) object;
        if ((this.hostmultifuncionalPK == null && other.hostmultifuncionalPK != null) || (this.hostmultifuncionalPK != null && !this.hostmultifuncionalPK.equals(other.hostmultifuncionalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Hostmultifuncional[hostmultifuncionalPK=" + hostmultifuncionalPK + "]";
    }
    
    public Hostmultifuncional actualizarEntity(Hostmultifuncional obj) { 
        setHostmultifuncionalPK(obj.hostmultifuncionalPK);    
        setNumerocuenta(obj.numerocuenta);    
        setDatostarjeta(obj.datostarjeta);
        setFecha(obj.fecha);
        setTipotransaccion(obj.tipotransaccion);   
        setCodigooficina(obj.codigooficina);
        setIndices(obj.indices);
        setTipotarjeta(obj.tipotarjeta);
        setValor(obj.valor);
        setFiller(obj.filler);
        return this;
            
    }

}




