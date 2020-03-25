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
 * @author aagarcia
 */
@Entity
@Table(name = "PROVISIONHOSTMULTI")
@NamedQueries( {
    @NamedQuery(
    name = "Provisionhostmulti.registroUnico",
            query = "select object(txm) from Provisionhostmulti txm where txm.provisionhostmultiPK.fecha = :fecha and txm.provisionhostmultiPK.codigocajero = :codigocajero and txm.provisionhostmultiPK.motivo = :motivo and txm.provisionhostmultiPK.tipo = :tipo and txm.provisionhostmultiPK.numtalon = :numtalon"),             
    @NamedQuery(
    name = "Provisionhostmulti.todos", 
             query = "select object(txm) from Provisionhostmulti txm") ,
    @NamedQuery(
            name = "Provisionhostmulti.RangoFecha",
            query = "select object(obj) from Provisionhostmulti obj  " +
            "        where obj.provisionhostmultiPK.fecha between :fechaInicial and :fechaFinal   " +
                    " order by obj.provisionhostmultiPK.codigocajero")


})
public class Provisionhostmulti implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProvisionhostmultiPK provisionhostmultiPK;
   @Column(name = "VALOR", nullable = false)
    private Long valor;
    @Column(name = "USUARIOHOST")
    private String usuariohost;
    @Column(name = "REFERENCIA1")
    private String referencia1;
    @Column(name = "REFERENCIA2")
    private String referencia2;
    @Column(name = "FECHAAPLICACION")
    @Temporal(TemporalType.TIMESTAMP)       
    private Date fechaaplicacion;
    @Column(name = "OFIMULTI")
    private Integer ofiMulti;
    @Column(name = "CODPRODUCTO")
    private Integer codProducto;
    @Column(name = "NUMCUENTA")
    private String numCuenta;
    @Column(name = "VERSION")
    private Integer version;

    public Provisionhostmulti() {
    }

    public Provisionhostmulti(ProvisionhostmultiPK provisionhostmultiPK) {
        this.provisionhostmultiPK = provisionhostmultiPK;
    }

    public Provisionhostmulti(ProvisionhostmultiPK provisionhostmultiPK, long valor) {
        this.provisionhostmultiPK = provisionhostmultiPK;
        this.valor = valor;
    }

    public Provisionhostmulti(Date fecha, int codigocajero, int motivo, int tipo, String numtalon) {
        this.provisionhostmultiPK = new ProvisionhostmultiPK(fecha, codigocajero, motivo, tipo, numtalon);
    }

    public ProvisionhostmultiPK getProvisionhostmultiPK() {
        return provisionhostmultiPK;
    }

    public void setProvisionhostmultiPK(ProvisionhostmultiPK provisionhostmultiPK) {
        this.provisionhostmultiPK = provisionhostmultiPK;
    }

   
    public String getUsuariohost() {
        return usuariohost;
    }

    public void setUsuariohost(String usuariohost) {
        this.usuariohost = usuariohost;
    }

    

    public Date getFechaaplicacion() {
        return fechaaplicacion;
    }

    public void setFechaaplicacion(Date fechaaplicacion) {
        this.fechaaplicacion = fechaaplicacion;
    }

   public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(Integer codProducto) {
        this.codProducto = codProducto;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

  
    public Integer getOfiMulti() {
        return ofiMulti;
    }

    public void setOfiMulti(Integer ofiMulti) {
        this.ofiMulti = ofiMulti;
    }

    public String getReferencia1() {
        return referencia1;
    }

    public void setReferencia1(String referencia1) {
        this.referencia1 = referencia1;
    }

    public String getReferencia2() {
        return referencia2;
    }

    public void setReferencia2(String referencia2) {
        this.referencia2 = referencia2;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (provisionhostmultiPK != null ? provisionhostmultiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provisionhostmulti)) {
            return false;
        }
        Provisionhostmulti other = (Provisionhostmulti) object;
        if ((this.provisionhostmultiPK == null && other.provisionhostmultiPK != null) || (this.provisionhostmultiPK != null && !this.provisionhostmultiPK.equals(other.provisionhostmultiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Provisionhostmulti[provisionhostmultiPK=" + provisionhostmultiPK + "]";
    }

        public Provisionhostmulti actualizarEntity(Provisionhostmulti obj) { 
        setProvisionhostmultiPK(obj.getProvisionhostmultiPK());
        setValor(obj.getValor());
        setUsuariohost(obj.usuariohost);
        setFechaaplicacion(obj.fechaaplicacion);
        setVersion(obj.version);        
        return this;
    }
}
