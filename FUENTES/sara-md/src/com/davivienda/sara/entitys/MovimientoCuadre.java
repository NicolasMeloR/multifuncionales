package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.CascadeType;
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
 * Movimientocuadre.java
 *
 * Fecha       :  14/07/2007, 11:16:58 AM
 * Descripción :
 *
 * @author     : jjvargas
 * @version    : $Id$
 */
@Entity
@Table(name = "MOVIMIENTOCUADRE" )
@NamedQueries({
    
            @NamedQuery(
            name = "MovimientoCuadre.idMovimientoCuadre",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.idMovimientoCuadre =:idMovimientoCuadre"),
            @NamedQuery(
            name = "MovimientoCuadre.RegistroUnico",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.cajero.codigoCajero = :codigoCajero and " +
            "        obj.fecha = :fecha and " +        
            "        obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre = :codigoTipoMovimientoCuadre "),                    
            @NamedQuery(
            name = "MovimientoCuadre.CajeroRangoFecha",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.cajero.codigoCajero =:codigoCajero and " +
            "               obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal " +
                    "order by obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre"),
            @NamedQuery(
            name = "MovimientoCuadre.CajeroRangoFechaMostrar",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.cajero.codigoCajero =:codigoCajero and " +
            "               obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal and " +
                    "obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre in (710,711,721,741,299,130,100,199,298,743,230,231) " +                    
                    "order by obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre"),        
            @NamedQuery(
            name = "MovimientoCuadre.RangoFecha",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal   " +
                       //Solo para pruebas
                     //"and obj.cajero.codigoCajero in (433,505,525,646,730)  " +
                    "order by obj.cajero.codigoCajero, obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre"),     
            @NamedQuery(
            name = "MovimientoCuadre.RangoFechaMostrar",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal and " +
                    "obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre in (710,711,721,741,299,130,100,199,298,230,743,231) " +   
                    //para ajuste de pagado en diferencias poner el codigo 230
                    //Solo para pruebas
                   //  "and obj.cajero.codigoCajero in (433,505,525,646,730)  " +
                    "order by obj.cajero.codigoCajero, obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre"),  
            @NamedQuery(
            name = "MovimientoCuadre.RangoFechaOccaMostrar",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal and " +
            "        obj.cajero.occa.codigoOcca =:codigoOcca and  " +
                    "obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre in (710,711,721,741,299,130,100,199,298,230,743) " +   
                    "order by obj.cajero.codigoCajero, obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre"),          
            @NamedQuery(
            name = "MovimientoCuadre.CajeroRangoFechaAjustes",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.cajero.codigoCajero =:codigoCajero and " +
            "               obj.fechaAjuste >= :fechaInicial and obj.fechaAjuste <= :fechaFinal and " +
            "               obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre >= 700 " +
            "        order by obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre")  ,                  
            @NamedQuery(
            name = "MovimientoCuadre.RangoFechaAjustes",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.fechaAjuste >= :fechaInicial and obj.fechaAjuste <= :fechaFinal and " +
            "              obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre >= 700 " +
            "        order by obj.cajero.codigoCajero, obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre")  ,                                      
            @NamedQuery(
            name = "MovimientoCuadre.CajeroRangoFechaContadores",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.cajero.codigoCajero =:codigoCajero and " +
            "               obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal and " +
            "        ( obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre between 100 and 199 or " +
            "          obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre between 300 and 499 ) " +
            "        order by obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre")  ,                  
            @NamedQuery(
            name = "MovimientoCuadre.RangoFechaContadores",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal and " +
            "        ( obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre between 100 and 199 or " +
            "          obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre between 300 and 499 ) " +
            "        order by obj.cajero.codigoCajero, obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre")  ,                  
            @NamedQuery(
            name = "MovimientoCuadre.CajeroRangoFechaLinea",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.cajero.codigoCajero =:codigoCajero and " +
            "               obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal and " +
            "        obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre between 200 and 299 " +                  
            "        order by obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre") ,
            @NamedQuery(                    
            name = "MovimientoCuadre.RangoFechaLinea",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal and " +
            "        obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre between 200 and 299 " +                  
            "        order by obj.cajero.codigoCajero, obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre") ,
            @NamedQuery(
            name = "MovimientoCuadre.CajeroRangoFechaProvision",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.cajero.codigoCajero =:codigoCajero and " +
            "               obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal and " +
                    "obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre in (210, 211, 711 )" +
                    "order by obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre") ,
            @NamedQuery(                    
            name = "MovimientoCuadre.RangoFechaProvision",
            query = "select object(obj) from MovimientoCuadre obj  " +
            "        where obj.fecha >= :fechaInicial and obj.fecha <= :fechaFinal and " +
                    "obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre in (210, 211, 711 )" +
                    "order by obj.cajero.codigoCajero, obj.fecha, obj.tipoMovimientoCuadre.codigoTipoMovimientoCuadre")                    
                    
                    

    
})
public class MovimientoCuadre implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idMovimientoCuadre")
    @SequenceGenerator(name = "idMovimientoCuadre", sequenceName = "idMovimientoCuadre" , allocationSize=1)
    private BigInteger idMovimientoCuadre;
    
    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO")
    @ManyToOne(cascade = CascadeType.ALL)
    private Cajero cajero;
    
    @JoinColumn(name = "CODIGOTIPOMOVIMIENTOCUADRE", referencedColumnName = "CODIGOTIPOMOVIMIENTOCUADRE")
    @ManyToOne(cascade = CascadeType.ALL)
    private TipoMovimientoCuadre tipoMovimientoCuadre;
    
    @JoinColumn(name = "CODIGOCONCEPTO", referencedColumnName = "CODIGOCONCEPTO")
    @ManyToOne(cascade = CascadeType.ALL)
    private ConceptoMovimientoCuadre conceptoMovimientoCuadre;
    
    
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Temporal(TemporalType.DATE)
    private Date fechaAjuste;

    private Long valorMovimiento;
    
    private Short denominacion1;
    
    private Short denominacion2;
    
    private Short denominacion3;
    
    private Short denominacion4;
    
    private Integer billetes1;
    
    private Integer billetes2;
    
    private Integer billetes3;
    
    private Integer billetes4;
    
    private String idUsuario;
    
    private String observacion ;
        
    private String idUsuarioObservacion ;
    
    private Short denominacion5;
    
    @Column(name = "DENOMINACION_UF", nullable = true)
    private Short denominacionUf;
    
    @Column(name = "BILLETES5", nullable = true)
    private Integer billetes5;
    
    @Column(name = "BILLETES_UF", nullable = true)
    private Integer billetesUf;
    
    @Version
    private Integer version;
    
    /**
     * Crea una nueva instancia de <code>MovimientoCuadre</code>.
     */
    public MovimientoCuadre() {
    }
    
    public BigInteger getIdMovimientoCuadre() {
        return idMovimientoCuadre;
    }
    
    public void setIdMovimientoCuadre(BigInteger idMovimientoCuadre) {
        this.idMovimientoCuadre = idMovimientoCuadre;
    }
    
    public Cajero getCajero() {
        return cajero;
    }
    
    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }
    
    public TipoMovimientoCuadre getTipoMovimientoCuadre() {
        return tipoMovimientoCuadre;
    }
    
    public void setTipoMovimientoCuadre(TipoMovimientoCuadre tipoMovimientoCuadre) {
        this.tipoMovimientoCuadre = tipoMovimientoCuadre;
    }
    
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaAjuste() {
        return fechaAjuste;
    }

    public void setFechaAjuste(Date fechaAjuste) {
        this.fechaAjuste = fechaAjuste;
    }
        
    
    public Long getValorMovimiento() {
        return valorMovimiento;
    }
    
    public void setValorMovimiento(Long valorMovimeinto) {
        this.valorMovimiento = valorMovimeinto;
    }
    
    public Short getDenominacion1() {
        return denominacion1;
    }
    
    public void setDenominacion1(Short denominacion1) {
        this.denominacion1 = denominacion1;
    }
    
    public Short getDenominacion2() {
        return denominacion2;
    }
    
    public void setDenominacion2(Short denominacion2) {
        this.denominacion2 = denominacion2;
    }
    
    public Short getDenominacion3() {
        return denominacion3;
    }
    
    public void setDenominacion3(Short denominacion3) {
        this.denominacion3 = denominacion3;
    }
    
    public Short getDenominacion4() {
        return denominacion4;
    }
    
    public void setDenominacion4(Short denominacion4) {
        this.denominacion4 = denominacion4;
    }
    
    public Integer getBilletes1() {
        return billetes1;
    }
    
    public void setBilletes1(Integer billetes1) {
        this.billetes1 = billetes1;
    }
    
    public Integer getBilletes2() {
        return billetes2;
    }
    
    public void setBilletes2(Integer billetes2) {
        this.billetes2 = billetes2;
    }
    
    public Integer getBilletes3() {
        return billetes3;
    }
    
    public void setBilletes3(Integer billetes3) {
        this.billetes3 = billetes3;
    }
    
    public Integer getBilletes4() {
        return billetes4;
    }
    
    public void setBilletes4(Integer billetes4) {
        this.billetes4 = billetes4;
    }
    
    public String getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getIdUsuarioObservacion() {
        return idUsuarioObservacion;
    }

    public void setIdUsuarioObservacion(String idUsuarioObservacion) {
        this.idUsuarioObservacion = idUsuarioObservacion;
    }

    public ConceptoMovimientoCuadre getConceptoMovimientoCuadre() {
        return conceptoMovimientoCuadre;
    }

    public void setConceptoMovimientoCuadre(ConceptoMovimientoCuadre conceptoMovimientoCuadre) {
        this.conceptoMovimientoCuadre = conceptoMovimientoCuadre;
    }
    
    
    
    /**
     * Calcula el valor a partir de las denominaciones y billetes asignados
     */
    public void setValor() {
        long valorTmp = 0;
        if(denominacion1 != null && billetes1 != null){
            valorTmp = denominacion1 * billetes1;
        }
        if(denominacion2 != null && billetes2 != null){
            valorTmp += denominacion2 * billetes2;
        }
        if(denominacion3 != null && billetes3 != null){
            valorTmp += denominacion3 * billetes3;
        }
        if(denominacion4 != null && billetes4 != null){
            valorTmp += denominacion4 * billetes4;
        }
        setValorMovimiento(valorTmp*1000);
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Short getDenominacion5() {
        return denominacion5;
    }

    public void setDenominacion5(Short denominacion5) {
        this.denominacion5 = denominacion5;
    }

    public Short getDenominacionUf() {
        return denominacionUf;
    }

    public void setDenominacionUf(Short denominacionUf) {
        this.denominacionUf = denominacionUf;
    }

    public Integer getBilletes5() {
        return billetes5;
    }

    public void setBilletes5(Integer billetes5) {
        this.billetes5 = billetes5;
    }

    public Integer getBilletesUf() {
        return billetesUf;
    }

    public void setBilletesUf(Integer billetesUf) {
        this.billetesUf = billetesUf;
    }
    
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovimientoCuadre != null ? idMovimientoCuadre.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimientoCuadre)) {
            return false;
        }
        MovimientoCuadre other = (MovimientoCuadre) object;
        if (this.idMovimientoCuadre != other.idMovimientoCuadre && (this.idMovimientoCuadre == null || !this.idMovimientoCuadre.equals(other.idMovimientoCuadre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MovimientoCuadre{" + "idMovimientoCuadre=" + idMovimientoCuadre + ", tipoMovimientoCuadre=" + tipoMovimientoCuadre + ", conceptoMovimientoCuadre=" + conceptoMovimientoCuadre + ", fecha=" + fecha + ", fechaAjuste=" + fechaAjuste + ", valorMovimiento=" + valorMovimiento + ", denominacion1=" + denominacion1 + ", denominacion2=" + denominacion2 + ", denominacion3=" + denominacion3 + ", denominacion4=" + denominacion4 + ", billetes1=" + billetes1 + ", billetes2=" + billetes2 + ", billetes3=" + billetes3 + ", billetes4=" + billetes4 + ", idUsuario=" + idUsuario + ", observacion=" + observacion + ", idUsuarioObservacion=" + idUsuarioObservacion + ", denominacion5=" + denominacion5 + ", denominacionUf=" + denominacionUf + ", billetes5=" + billetes5 + ", billetesUf=" + billetesUf + ", version=" + version + '}';
    }
    
   
    
}

