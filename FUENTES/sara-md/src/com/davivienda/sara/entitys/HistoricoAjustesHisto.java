/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author aa.garcia
 */
@Entity
@Table(name = "HISTORICOAJUSTES_HISTO")
@NamedQueries(
{
         @NamedQuery(
         name = "HistoricoAjustesHisto.RegistroUnico",
         query = "select object(obj) from HistoricoAjustesHisto obj where obj.idHistoricoAjustes = :idHistoricoAjustes"),
         @NamedQuery(
         name = "HistoricoAjustesHisto.todos",
         query = "select object(obj) from HistoricoAjustesHisto obj order by obj.idHistoricoAjustes")})
         
public class HistoricoAjustesHisto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "IDHISTORICOAJUSTES", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idHistoricoAjustes_SEQ")
    @SequenceGenerator(name = "idHistoricoAjustes_SEQ", sequenceName = "SEQ_IDHISTORICO_AJUSTES" ,allocationSize=1 )
    private Long idHistoricoAjustes;
  
    @Column(name = "USUARIO", nullable = false)
    private String usuario;
    @Column(name = "CODIGOCAJERO", nullable = false)
    private Integer codigoCajero;
    @Column(name = "CODIGOOCCA", nullable = false)
    private Integer codigoOcca;
    @Column(name = "TIPOAJUSTE", nullable = false)
    private String tipoAjuste;
//    @Column(name = "CUENTA", nullable = false)
//    private String cuenta;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "VALOR", nullable = false)
    private BigDecimal valor;
    @Column(name = "TALON", nullable = false)
    private String talon;
    @Column(name = "ERROR", nullable = false)
    private String error;
    @Column(name = "DESCRIPCIONERROR", nullable = false)
    private String descripcionError;


    public HistoricoAjustesHisto() {
    }

    public HistoricoAjustesHisto(Long idHistoricoAjustes) {
        this.idHistoricoAjustes = idHistoricoAjustes;
    }

    public HistoricoAjustesHisto(Long idHistoricoAjustes, String usuario, Integer codigocajero, Integer codigoocca, String tipoajuste,String cuenta, Date fecha, BigDecimal valor,String talon, String error, String descripcionerror) {
        this.idHistoricoAjustes = idHistoricoAjustes;
        this.usuario = usuario;
        this.codigoCajero = codigocajero;
        this.codigoOcca = codigoocca;
        this.tipoAjuste = tipoajuste;
       // this.cuenta = cuenta;
        this.fecha = fecha;
        this.valor = valor;
        this.talon=talon;
        this.error = error;
        this.descripcionError = descripcionerror;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigocajero) {
        this.codigoCajero = codigocajero;
    }

    public Integer getCodigoOcca() {
        return codigoOcca;
    }

    public void setCodigoOcca(Integer codigoocca) {
        this.codigoOcca = codigoocca;
    }

    public String getTipoAjuste() {
        return tipoAjuste;
    }

    public void setTipoAjuste(String tipoajuste) {
        this.tipoAjuste = tipoajuste;
    }
    
//     public String getCuenta() {
//        return cuenta;
//    }
//
//    public void setCuenta(String cuenta) {
//        this.cuenta = cuenta;
//    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionerror) {
        this.descripcionError = descripcionerror;
    }

    public Long getIdHistoricoAjustes() {
        return idHistoricoAjustes;
    }

    public void setIdHistoricoAjustes(Long idhistoricoAjustes) {
        this.idHistoricoAjustes = idhistoricoAjustes;
    }
    
    public String getTalon() {
        return talon;
    }

    public void setTalon(String talon) {
        this.talon = talon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistoricoAjustes != null ? idHistoricoAjustes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoAjustesHisto)) {
            return false;
        }
        HistoricoAjustesHisto other = (HistoricoAjustesHisto) object;
        if ((this.idHistoricoAjustes == null && other.idHistoricoAjustes != null) || (this.idHistoricoAjustes != null && !this.idHistoricoAjustes.equals(other.idHistoricoAjustes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.HistoricoAjustesHisto[idHistoricoAjustes=" + idHistoricoAjustes + "]";
    }
     public HistoricoAjustesHisto actualizarEntity(HistoricoAjustesHisto obj) {
        setIdHistoricoAjustes(obj.idHistoricoAjustes);
        setUsuario(obj.usuario);
        setCodigoCajero(obj.codigoCajero);
        setCodigoOcca(obj.codigoOcca);
        setFecha(obj.fecha);
        setTipoAjuste(obj.tipoAjuste);
       // setCuenta(obj.cuenta);
        setValor(obj.valor);
        setError(obj.error);
        setDescripcionError(obj.descripcionError);
        setTalon(obj.talon);
      
        return this;
        
            
    }

}
