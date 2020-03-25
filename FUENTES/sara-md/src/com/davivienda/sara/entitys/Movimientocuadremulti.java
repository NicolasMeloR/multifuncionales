/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author lpulgari
 */
@Entity
@Table(name = "MOVIMIENTOCUADREMULTI")

@NamedQueries( {
    @NamedQuery(
    name = "Movimientocuadremulti.registroUnico",
            query = "select object(txm) from Movimientocuadremulti txm where txm.idmovimiento = :idmovimiento"),             
    @NamedQuery(
    name = "Movimientocuadremulti.todos", 
             query = "select object(txm) from Movimientocuadremulti txm"),
     @NamedQuery(        
     name = "Movimientocuadremulti.UsuarioRangoFecha",
            query = "select object(obj) from Movimientocuadremulti obj  " +
            "        where obj.idusuario =:idusuario and " +
            "               obj.fecha between :fechaInicial and :fechaFinal " +
                    "order by obj.cajero.codigoCajero")
})

public class Movimientocuadremulti implements Serializable {
    private static final long serialVersionUID = 1L;
//    @Id
//    @Column(name = "IDMOVIMIENTO", nullable = false)
//    private Long idmovimiento;
    
    @Id
    @Column(name = "IDMOVIMIENTO", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Idmovimiento_SEQ")
    @SequenceGenerator(name = "Idmovimiento_SEQ", sequenceName = "SEQ_IDMOVIMIENTOCUADREMULTI" , allocationSize=1)
    private BigInteger idmovimiento;
    
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "VALORMOVIMIENTO")
    private Long valormovimiento;
    @Column(name = "IDUSUARIO")
    private String idusuario;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "FECHAAJSUTE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaajsute;
    @Column(name = "CANTIDADBILLETESDENA")
    private Integer cantidadbilletesdena;
    @Column(name = "VALORTOTALBILLESDENA")
    private Long valortotalbillesdena;
    @Column(name = "CANTIDADBILLETESDENB")
    private Integer cantidadbilletesdenb;
    @Column(name = "VALORTOTALBILLESDENB")
    private Long valortotalbillesdenb;
    @Column(name = "CANTIDADBILLETESDENC")
    private Integer cantidadbilletesdenc;
    @Column(name = "VALORTOTALBILLESDENC")
    private Long valortotalbillesdenc;
    @Column(name = "CANTIDADBILLETESDEND")
    private Integer cantidadbilletesdend;
    @Column(name = "VALORTOTALBILLESDEND")
    private Long valortotalbillesdend;
    @Column(name = "CANTIDADBILLETESDENE")
    private Integer cantidadbilletesdene;
    @Column(name = "VALORTOTALBILLESDENE")
    private Long valortotalbillesdene;
    @Column(name = "CANTIDADBILLETESDENF")
    private Integer cantidadbilletesdenf;
    @Column(name = "VALORTOTALBILLESDENF")
    private Long valortotalbillesdenf;
    @Column(name = "CANTIDADBILLETESDENG")
    private Integer cantidadbilletesdeng;
    @Column(name = "VALORTOTALBILLESDENG")
    private Long valortotalbillesdeng;
    @Column(name = "CANTBILLETESRECIBIDOS")
    private Integer cantbilletesrecibidos;
    @Column(name = "VALORTOTALEFECTIVO")
    private Long valortotalefectivo;
    @Column(name = "CANTBILLETESRETRACT")
    private Integer cantbilletesretract;
    @Column(name = "VALOREFECTIVORETRACT")
    private Long valorefectivoretract;
    @Column(name = "CANTNUMEROCHEQUES")
    private Integer cantnumerocheques;
    @Column(name = "VALORCHEQUES")
    private Long valorcheques;
    @Column(name = "CANCHEQUESRETRACT")
    private Integer canchequesretract;
    @Column(name = "VALORCHEQUESRETRACT")
    private Long valorchequesretract;
    @Column(name = "CANTDEPEFECTIVOCTACTE")
    private Integer cantdepefectivoctacte;
    @Column(name = "VALORDEPEFECTIVOCTACTE")
    private Long valordepefectivoctacte;
    @Column(name = "CANTDEPEFECTIVOCTAAHORROS")
    private Integer cantdepefectivoctaahorros;
    @Column(name = "VALORDEPEFECTIVOCTAAHORROS")
    private Long valordepefectivoctaahorros;
    @Column(name = "CANTPAGOEFECTIVOTC")
    private Integer cantpagoefectivotc;
    @Column(name = "VALORPAGOEFECTIVOTC")
    private Long valorpagoefectivotc;
    @Column(name = "CANTIDADPAGOEFECTIVOFM")
    private Integer cantidadpagoefectivofm;
    @Column(name = "VALORPAGOEFECTIVOFM")
    private Long valorpagoefectivofm;
    @Column(name = "CANTRECAUDOEFECTIVO")
    private Integer cantrecaudoefectivo;
    @Column(name = "VALORRECAUDOEFECTIVO")
    private Long valorrecaudoefectivo;
    @Column(name = "CANTDEPCHEQUECTACTE")
    private Integer cantdepchequectacte;
    @Column(name = "VALORDEPCHEQUECTACTE")
    private Long valordepchequectacte;
    @Column(name = "CANTDEPCHEQUECTAAHORROS")
    private Integer cantdepchequectaahorros;
    @Column(name = "VALORDEPCHEQUECTAAHORROS")
    private Long valordepchequectaahorros;
    @Column(name = "CANTIDADPAGOCHEQUETC")
    private Integer cantidadpagochequetc;
    @Column(name = "VALORPAGOCHEQUETC")
    private Long valorpagochequetc;
    @Column(name = "CANTIDADPAGOCHEQUEFM")
    private Integer cantidadpagochequefm;
    @Column(name = "VALORPAGOCHEQUEFM")
    private Long valorpagochequefm;
    @Column(name = "CANTIDADRECAUDOCHEQUE")
    private Integer cantidadrecaudocheque;
    @Column(name = "VALORRECAUDOCHEQUE")
    private Long valorrecaudocheque;
    @Column(name = "CODTIPOMOVIMIENTOCUADREMUTI", nullable = false)
    private long codtipomovimientocuadremuti;
    @Column(name = "VERSION")
    private Integer version;
    
    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO")
    @ManyToOne(cascade = CascadeType.ALL)
    private Cajero cajero;
    
  
    public Movimientocuadremulti() {
    }

   

    public BigInteger getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(BigInteger idmovimiento) {
        this.idmovimiento = idmovimiento;
    }
    
      public Cajero getCajero() {
        return cajero;
    }
    
    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }
    


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getValormovimiento() {
        return valormovimiento;
    }

    public void setValormovimiento(Long valormovimiento) {
        this.valormovimiento = valormovimiento;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaajsute() {
        return fechaajsute;
    }

    public void setFechaajsute(Date fechaajsute) {
        this.fechaajsute = fechaajsute;
    }

    public long getCodtipomovimientocuadremuti() {
        return codtipomovimientocuadremuti;
    }

    public void setCodtipomovimientocuadremuti(long codtipomovimientocuadremuti) {
        this.codtipomovimientocuadremuti = codtipomovimientocuadremuti;
    }


    public Integer getCanchequesretract() {
        return canchequesretract;
    }

    public void setCanchequesretract(Integer canchequesretract) {
        this.canchequesretract = canchequesretract;
    }

    public Integer getCantbilletesrecibidos() {
        return cantbilletesrecibidos;
    }

    public void setCantbilletesrecibidos(Integer cantbilletesrecibidos) {
        this.cantbilletesrecibidos = cantbilletesrecibidos;
    }

    public Integer getCantbilletesretract() {
        return cantbilletesretract;
    }

    public void setCantbilletesretract(Integer cantbilletesretract) {
        this.cantbilletesretract = cantbilletesretract;
    }

    public Integer getCantdepchequectaahorros() {
        return cantdepchequectaahorros;
    }

    public void setCantdepchequectaahorros(Integer cantdepchequectaahorros) {
        this.cantdepchequectaahorros = cantdepchequectaahorros;
    }

    public Integer getCantdepchequectacte() {
        return cantdepchequectacte;
    }

    public void setCantdepchequectacte(Integer cantdepchequectacte) {
        this.cantdepchequectacte = cantdepchequectacte;
    }

    public Integer getCantdepefectivoctaahorros() {
        return cantdepefectivoctaahorros;
    }

    public void setCantdepefectivoctaahorros(Integer cantdepefectivoctaahorros) {
        this.cantdepefectivoctaahorros = cantdepefectivoctaahorros;
    }

    public Integer getCantdepefectivoctacte() {
        return cantdepefectivoctacte;
    }

    public void setCantdepefectivoctacte(Integer cantdepefectivoctacte) {
        this.cantdepefectivoctacte = cantdepefectivoctacte;
    }

    public Integer getCantidadbilletesdena() {
        return cantidadbilletesdena;
    }

    public void setCantidadbilletesdena(Integer cantidadbilletesdena) {
        this.cantidadbilletesdena = cantidadbilletesdena;
    }

    public Integer getCantidadbilletesdenb() {
        return cantidadbilletesdenb;
    }

    public void setCantidadbilletesdenb(Integer cantidadbilletesdenb) {
        this.cantidadbilletesdenb = cantidadbilletesdenb;
    }

    public Integer getCantidadbilletesdenc() {
        return cantidadbilletesdenc;
    }

    public void setCantidadbilletesdenc(Integer cantidadbilletesdenc) {
        this.cantidadbilletesdenc = cantidadbilletesdenc;
    }

    public Integer getCantidadbilletesdend() {
        return cantidadbilletesdend;
    }

    public void setCantidadbilletesdend(Integer cantidadbilletesdend) {
        this.cantidadbilletesdend = cantidadbilletesdend;
    }

    public Integer getCantidadbilletesdene() {
        return cantidadbilletesdene;
    }

    public void setCantidadbilletesdene(Integer cantidadbilletesdene) {
        this.cantidadbilletesdene = cantidadbilletesdene;
    }

    public Integer getCantidadbilletesdenf() {
        return cantidadbilletesdenf;
    }

    public void setCantidadbilletesdenf(Integer cantidadbilletesdenf) {
        this.cantidadbilletesdenf = cantidadbilletesdenf;
    }

    public Integer getCantidadbilletesdeng() {
        return cantidadbilletesdeng;
    }

    public void setCantidadbilletesdeng(Integer cantidadbilletesdeng) {
        this.cantidadbilletesdeng = cantidadbilletesdeng;
    }

    public Integer getCantidadpagochequefm() {
        return cantidadpagochequefm;
    }

    public void setCantidadpagochequefm(Integer cantidadpagochequefm) {
        this.cantidadpagochequefm = cantidadpagochequefm;
    }

    public Integer getCantidadpagochequetc() {
        return cantidadpagochequetc;
    }

    public void setCantidadpagochequetc(Integer cantidadpagochequetc) {
        this.cantidadpagochequetc = cantidadpagochequetc;
    }

    public Integer getCantidadpagoefectivofm() {
        return cantidadpagoefectivofm;
    }

    public void setCantidadpagoefectivofm(Integer cantidadpagoefectivofm) {
        this.cantidadpagoefectivofm = cantidadpagoefectivofm;
    }

    public Integer getCantidadrecaudocheque() {
        return cantidadrecaudocheque;
    }

    public void setCantidadrecaudocheque(Integer cantidadrecaudocheque) {
        this.cantidadrecaudocheque = cantidadrecaudocheque;
    }

    public Integer getCantnumerocheques() {
        return cantnumerocheques;
    }

    public void setCantnumerocheques(Integer cantnumerocheques) {
        this.cantnumerocheques = cantnumerocheques;
    }

    public Integer getCantpagoefectivotc() {
        return cantpagoefectivotc;
    }

    public void setCantpagoefectivotc(Integer cantpagoefectivotc) {
        this.cantpagoefectivotc = cantpagoefectivotc;
    }

    public Integer getCantrecaudoefectivo() {
        return cantrecaudoefectivo;
    }

    public void setCantrecaudoefectivo(Integer cantrecaudoefectivo) {
        this.cantrecaudoefectivo = cantrecaudoefectivo;
    }

    public Long getValorcheques() {
        return valorcheques;
    }

    public void setValorcheques(Long valorcheques) {
        this.valorcheques = valorcheques;
    }

    public Long getValorchequesretract() {
        return valorchequesretract;
    }

    public void setValorchequesretract(Long valorchequesretract) {
        this.valorchequesretract = valorchequesretract;
    }

    public Long getValordepchequectaahorros() {
        return valordepchequectaahorros;
    }

    public void setValordepchequectaahorros(Long valordepchequectaahorros) {
        this.valordepchequectaahorros = valordepchequectaahorros;
    }

    public Long getValordepchequectacte() {
        return valordepchequectacte;
    }

    public void setValordepchequectacte(Long valordepchequectacte) {
        this.valordepchequectacte = valordepchequectacte;
    }

    public Long getValordepefectivoctaahorros() {
        return valordepefectivoctaahorros;
    }

    public void setValordepefectivoctaahorros(Long valordepefectivoctaahorros) {
        this.valordepefectivoctaahorros = valordepefectivoctaahorros;
    }

    public Long getValordepefectivoctacte() {
        return valordepefectivoctacte;
    }

    public void setValordepefectivoctacte(Long valordepefectivoctacte) {
        this.valordepefectivoctacte = valordepefectivoctacte;
    }

    public Long getValorefectivoretract() {
        return valorefectivoretract;
    }

    public void setValorefectivoretract(Long valorefectivoretract) {
        this.valorefectivoretract = valorefectivoretract;
    }

    public Long getValorpagochequefm() {
        return valorpagochequefm;
    }

    public void setValorpagochequefm(Long valorpagochequefm) {
        this.valorpagochequefm = valorpagochequefm;
    }

    public Long getValorpagochequetc() {
        return valorpagochequetc;
    }

    public void setValorpagochequetc(Long valorpagochequetc) {
        this.valorpagochequetc = valorpagochequetc;
    }

    public Long getValorpagoefectivofm() {
        return valorpagoefectivofm;
    }

    public void setValorpagoefectivofm(Long valorpagoefectivofm) {
        this.valorpagoefectivofm = valorpagoefectivofm;
    }

    public Long getValorpagoefectivotc() {
        return valorpagoefectivotc;
    }

    public void setValorpagoefectivotc(Long valorpagoefectivotc) {
        this.valorpagoefectivotc = valorpagoefectivotc;
    }

    public Long getValorrecaudocheque() {
        return valorrecaudocheque;
    }

    public void setValorrecaudocheque(Long valorrecaudocheque) {
        this.valorrecaudocheque = valorrecaudocheque;
    }

    public Long getValorrecaudoefectivo() {
        return valorrecaudoefectivo;
    }

    public void setValorrecaudoefectivo(Long valorrecaudoefectivo) {
        this.valorrecaudoefectivo = valorrecaudoefectivo;
    }

    public Long getValortotalbillesdena() {
        return valortotalbillesdena;
    }

    public void setValortotalbillesdena(Long valortotalbillesdena) {
        this.valortotalbillesdena = valortotalbillesdena;
    }

    public Long getValortotalbillesdenb() {
        return valortotalbillesdenb;
    }

    public void setValortotalbillesdenb(Long valortotalbillesdenb) {
        this.valortotalbillesdenb = valortotalbillesdenb;
    }

    public Long getValortotalbillesdenc() {
        return valortotalbillesdenc;
    }

    public void setValortotalbillesdenc(Long valortotalbillesdenc) {
        this.valortotalbillesdenc = valortotalbillesdenc;
    }

    public Long getValortotalbillesdend() {
        return valortotalbillesdend;
    }

    public void setValortotalbillesdend(Long valortotalbillesdend) {
        this.valortotalbillesdend = valortotalbillesdend;
    }

    public Long getValortotalbillesdene() {
        return valortotalbillesdene;
    }

    public void setValortotalbillesdene(Long valortotalbillesdene) {
        this.valortotalbillesdene = valortotalbillesdene;
    }

    public Long getValortotalbillesdenf() {
        return valortotalbillesdenf;
    }

    public void setValortotalbillesdenf(Long valortotalbillesdenf) {
        this.valortotalbillesdenf = valortotalbillesdenf;
    }

    public Long getValortotalbillesdeng() {
        return valortotalbillesdeng;
    }

    public void setValortotalbillesdeng(Long valortotalbillesdeng) {
        this.valortotalbillesdeng = valortotalbillesdeng;
    }

    public Long getValortotalefectivo() {
        return valortotalefectivo;
    }

    public void setValortotalefectivo(Long valortotalefectivo) {
        this.valortotalefectivo = valortotalefectivo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmovimiento != null ? idmovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimientocuadremulti)) {
            return false;
        }
        Movimientocuadremulti other = (Movimientocuadremulti) object;
        if ((this.idmovimiento == null && other.idmovimiento != null) || (this.idmovimiento != null && !this.idmovimiento.equals(other.idmovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Movimientocuadremulti[idmovimiento=" + idmovimiento + "]";
    }
    
    
    public Movimientocuadremulti actualizarEntity(Movimientocuadremulti obj) { 
        setIdmovimiento(obj.idmovimiento);
       
   
    setValormovimiento(obj.valormovimiento);
    
    setIdusuario(obj.idusuario);
   
    setObservacion(obj.observacion);
   
    setFechaajsute(obj.fechaajsute);
  
    setCantidadbilletesdena(obj.cantidadbilletesdena);
    
    setValortotalbillesdena(obj.valortotalbillesdena);
    
    setCantidadbilletesdenb(obj.cantidadbilletesdenb);
   
    setValortotalbillesdenb(obj.valortotalbillesdenb);
   
    setCantidadbilletesdenc(obj.cantidadbilletesdenc);
   
    setValortotalbillesdenc(obj.valortotalbillesdenc);
   
    setCantidadbilletesdend(obj.cantidadbilletesdend);
   
    setValortotalbillesdend(obj.valortotalbillesdend);
   
    setCantidadbilletesdene(obj.cantidadbilletesdene);
   
    setValortotalbillesdene(obj.valortotalbillesdene);
   
    setCantidadbilletesdenf(obj.cantidadbilletesdenf);
   
    setValortotalbillesdenf(obj.valortotalbillesdenf);
  
    setCantidadbilletesdeng(obj.cantidadbilletesdeng);
   
    setValortotalbillesdeng(obj.valortotalbillesdeng);
    
    setCantbilletesrecibidos(obj.cantbilletesrecibidos);
   
    setValortotalefectivo(obj.valortotalefectivo);
   
    setCantbilletesretract(obj.cantbilletesretract);
   
    setValorefectivoretract(obj.valorefectivoretract);
   
    setCantnumerocheques(obj.cantnumerocheques);
    
    setValorcheques(obj.valorcheques);
   
    setCanchequesretract(obj.canchequesretract);
   
    setValorchequesretract(obj.valorchequesretract);
    
    setCantdepefectivoctacte(obj.cantdepefectivoctacte);
    
    setValordepefectivoctacte(obj.valordepefectivoctacte);
    
    setCantdepefectivoctaahorros(obj.cantdepefectivoctaahorros);
   
    setValordepefectivoctaahorros(obj.valordepefectivoctaahorros);
    
    setCantpagoefectivotc(obj.cantpagoefectivotc);
    
    setValorpagoefectivotc(obj.valorpagoefectivotc);
    
    setCantidadpagoefectivofm(obj.cantidadpagoefectivofm);
    
    setValorpagoefectivofm(obj.valorpagoefectivofm);
    
    setCantrecaudoefectivo(obj.cantrecaudoefectivo);
    
    setValorrecaudoefectivo(obj.valorrecaudoefectivo);
    
    setCantdepchequectacte(obj.cantdepchequectacte);
    
    setValordepchequectacte(obj.valordepchequectacte);
    
    setCantdepchequectaahorros(obj.cantdepchequectaahorros);
    
    setValordepchequectaahorros(obj.valordepchequectaahorros);
   
    setCantidadpagochequetc(obj.cantidadpagochequetc);
    
    setValorpagochequetc(obj.valorpagochequetc);
    
    setCantidadpagochequefm(obj.cantidadpagochequefm);
   
    setValorpagochequefm(obj.valorpagochequefm);
   
    setCantidadrecaudocheque(obj.cantidadrecaudocheque);
    
    setValorrecaudocheque(obj.valorrecaudocheque);
    
    setCodtipomovimientocuadremuti(obj.codtipomovimientocuadremuti);
    
    setVersion(obj.version);
        
           
        return this;
    }

}