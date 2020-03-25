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

/**
 *
 * @author aagarcia
 */
@Entity
@Table(name = "BILLETAJEMULTI")
@NamedQueries({
@NamedQuery(
  name = "Billetajemulti.Fecha", 
  query = "SELECT b FROM Billetajemulti b WHERE b.billetajemultiPK.fecha between :fechaInicial and :fechaFinal order by b.billetajemultiPK.codigocajero"),
@NamedQuery(
  name = "Billetajemulti.FechaCajero", 
  query = "SELECT b FROM Billetajemulti b WHERE b.billetajemultiPK.fecha between :fechaInicial and :fechaFinal and b.billetajemultiPK.codigocajero = :codigoCajero order by b.billetajemultiPK.codigocajero, b.billetajemultiPK.fecha")
  
})
public class Billetajemulti implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BilletajemultiPK billetajemultiPK;
    @Column(name = "OFICINA")
    private Integer oficina;
    @Column(name = "INDATM")
    private String indatm;
    @Column(name = "DATOATM")
    private Short datoatm;
    @Column(name = "COPIAOFICINA")
    private Integer copiaoficina;
    @Column(name = "CANTIDADBILLETESDENA")
    private Short cantidadbilletesdena;
    @Column(name = "VALORTOTALBILLESDENA")
    private Long valortotalbillesdena;
    @Column(name = "CANTIDADBILLETESDENB")
    private Short cantidadbilletesdenb;
    @Column(name = "VALORTOTALBILLESDENB")
    private Long valortotalbillesdenb;
    @Column(name = "CANTIDADBILLETESDENC")
    private Short cantidadbilletesdenc;
    @Column(name = "VALORTOTALBILLESDENC")
    private Long valortotalbillesdenc;
    @Column(name = "CANTIDADBILLETESDEND")
    private Short cantidadbilletesdend;
    @Column(name = "VALORTOTALBILLESDEND")
    private Long valortotalbillesdend;
    @Column(name = "CANTIDADBILLETESDENE")
    private Short cantidadbilletesdene;
    @Column(name = "VALORTOTALBILLESDENE")
    private Long valortotalbillesdene;
    @Column(name = "CANTIDADBILLETESDENF")
    private Short cantidadbilletesdenf;
    @Column(name = "VALORTOTALBILLESDENF")
    private Long valortotalbillesdenf;
    @Column(name = "CANTIDADBILLETESDENG")
    private Short cantidadbilletesdeng;
    @Column(name = "VALORTOTALBILLESDENG")
    private Long valortotalbillesdeng;
    @Column(name = "CANBILLETESRECIBIDOS")
    private Short canbilletesrecibidos;
    @Column(name = "VALORTOTALEFECTIVO")
    private Long valortotalefectivo;
    @Column(name = "CANTIDADBILLETESRETRACT")
    private Short cantidadbilletesretract;
    @Column(name = "VALOREFECTIVORETRACT")
    private Long valorefectivoretract;
    @Column(name = "CANTIDADNUMEROCHEQUES")
    private Short cantidadnumerocheques;
    @Column(name = "VALORCHEQUES")
    private Long valorcheques;
    @Column(name = "CANTIDADCHEQUESRETRACT")
    private Short cantidadchequesretract;
    @Column(name = "VALORCHEQUESRETRACT")
    private Long valorchequesretract;
    @Column(name = "CANDEPEFECTIVOCTACTE")
    private Short candepefectivoctacte;
    @Column(name = "VALORDEPEFECTIVOCTACTE")
    private Long valordepefectivoctacte;
    @Column(name = "CANDEPEFECTIVOCTAAHO")
    private Short candepefectivoctaaho;
    @Column(name = "VALORDEPEFECTIVOCTAAHO")
    private Long valordepefectivoctaaho;
    @Column(name = "CANTIDADPAGOEFECTIVOTC")
    private Short cantidadpagoefectivotc;
    @Column(name = "VALORPAGOEFECTIVOTC")
    private Long valorpagoefectivotc;
    @Column(name = "CANTIDADPAGOEFECTIVOFM")
    private Short cantidadpagoefectivofm;
    @Column(name = "VALORPAGOEFECTIVOFM")
    private Long valorpagoefectivofm;
    @Column(name = "CANTIDADRECAUDOEFECTIVO")
    private Short cantidadrecaudoefectivo;
    @Column(name = "VALORRECAUDOEFECTIVO")
    private Long valorrecaudoefectivo;
    @Column(name = "CANTIDADDEPCHEQUECTACTE")
    private Short cantidaddepchequectacte;
    @Column(name = "VALORDEPCHEQUECTACTE")
    private Long valordepchequectacte;
    @Column(name = "CANTIDADDEPCHEQUECTAAHO")
    private Short cantidaddepchequectaaho;
    @Column(name = "VALORDEPCHEQUECTAAH")
    private Long valordepchequectaah;
    @Column(name = "CANTIDADPAGOCHEQUETC")
    private Short cantidadpagochequetc;
    @Column(name = "VALORPAGOCHEQUETC")
    private Long valorpagochequetc;
    @Column(name = "CANTIDADPAGOCHEQUEFM")
    private Short cantidadpagochequefm;
    @Column(name = "VALORPAGOCHEQUEFM")
    private Long valorpagochequefm;
    @Column(name = "CANTIDADRECAUDOCHEQUE")
    private Short cantidadrecaudocheque;
    @Column(name = "VALORRECAUDOCHEQUE")
    private Long valorrecaudocheque;
    @Column(name = "VERSION")
    private Short version;
    @Column(name = "CODIGOATMDISPENSADOR")
    private Integer codigoatmdispensador;
    @Column(name = "ZONAOFICINA")
    private String zonaoficina;

    public Billetajemulti() {
    }

    public Billetajemulti(BilletajemultiPK billetajemultiPK) {
        this.billetajemultiPK = billetajemultiPK;
    }

    public Billetajemulti(int codigocajero, Date fecha) {
        this.billetajemultiPK = new BilletajemultiPK(codigocajero, fecha);
    }

    public BilletajemultiPK getBilletajemultiPK() {
        return billetajemultiPK;
    }

    public void setBilletajemultiPK(BilletajemultiPK billetajemultiPK) {
        this.billetajemultiPK = billetajemultiPK;
    }

    public Integer getOficina() {
        return oficina;
    }

    public void setOficina(Integer oficina) {
        this.oficina = oficina;
    }

    public String getIndatm() {
        return indatm;
    }

    public void setIndatm(String indatm) {
        this.indatm = indatm;
    }

    public Short getDatoatm() {
        return datoatm;
    }

    public void setDatoatm(Short datoatm) {
        this.datoatm = datoatm;
    }

    public Integer getCopiaoficina() {
        return copiaoficina;
    }

    public void setCopiaoficina(Integer copiaoficina) {
        this.copiaoficina = copiaoficina;
    }

    public Short getCantidadbilletesdena() {
        return cantidadbilletesdena;
    }

    public void setCantidadbilletesdena(Short cantidadbilletesdena) {
        this.cantidadbilletesdena = cantidadbilletesdena;
    }

    public Long getValortotalbillesdena() {
        return valortotalbillesdena;
    }

    public void setValortotalbillesdena(Long valortotalbillesdena) {
        this.valortotalbillesdena = valortotalbillesdena;
    }

    public Short getCantidadbilletesdenb() {
        return cantidadbilletesdenb;
    }

    public void setCantidadbilletesdenb(Short cantidadbilletesdenb) {
        this.cantidadbilletesdenb = cantidadbilletesdenb;
    }

    public Long getValortotalbillesdenb() {
        return valortotalbillesdenb;
    }

    public void setValortotalbillesdenb(Long valortotalbillesdenb) {
        this.valortotalbillesdenb = valortotalbillesdenb;
    }

    public Short getCantidadbilletesdenc() {
        return cantidadbilletesdenc;
    }

    public void setCantidadbilletesdenc(Short cantidadbilletesdenc) {
        this.cantidadbilletesdenc = cantidadbilletesdenc;
    }

    public Long getValortotalbillesdenc() {
        return valortotalbillesdenc;
    }

    public void setValortotalbillesdenc(Long valortotalbillesdenc) {
        this.valortotalbillesdenc = valortotalbillesdenc;
    }

    public Short getCantidadbilletesdend() {
        return cantidadbilletesdend;
    }

    public void setCantidadbilletesdend(Short cantidadbilletesdend) {
        this.cantidadbilletesdend = cantidadbilletesdend;
    }

    public Long getValortotalbillesdend() {
        return valortotalbillesdend;
    }

    public void setValortotalbillesdend(Long valortotalbillesdend) {
        this.valortotalbillesdend = valortotalbillesdend;
    }

    public Short getCantidadbilletesdene() {
        return cantidadbilletesdene;
    }

    public void setCantidadbilletesdene(Short cantidadbilletesdene) {
        this.cantidadbilletesdene = cantidadbilletesdene;
    }

    public Long getValortotalbillesdene() {
        return valortotalbillesdene;
    }

    public void setValortotalbillesdene(Long valortotalbillesdene) {
        this.valortotalbillesdene = valortotalbillesdene;
    }

    public Short getCantidadbilletesdenf() {
        return cantidadbilletesdenf;
    }

    public void setCantidadbilletesdenf(Short cantidadbilletesdenf) {
        this.cantidadbilletesdenf = cantidadbilletesdenf;
    }

    public Long getValortotalbillesdenf() {
        return valortotalbillesdenf;
    }

    public void setValortotalbillesdenf(Long valortotalbillesdenf) {
        this.valortotalbillesdenf = valortotalbillesdenf;
    }

    public Short getCantidadbilletesdeng() {
        return cantidadbilletesdeng;
    }

    public void setCantidadbilletesdeng(Short cantidadbilletesdeng) {
        this.cantidadbilletesdeng = cantidadbilletesdeng;
    }

    public Long getValortotalbillesdeng() {
        return valortotalbillesdeng;
    }

    public void setValortotalbillesdeng(Long valortotalbillesdeng) {
        this.valortotalbillesdeng = valortotalbillesdeng;
    }

    public Short getCanbilletesrecibidos() {
        return canbilletesrecibidos;
    }

    public void setCanbilletesrecibidos(Short canbilletesrecibidos) {
        this.canbilletesrecibidos = canbilletesrecibidos;
    }

    public Long getValortotalefectivo() {
        return valortotalefectivo;
    }

    public void setValortotalefectivo(Long valortotalefectivo) {
        this.valortotalefectivo = valortotalefectivo;
    }

    public Short getCantidadbilletesretract() {
        return cantidadbilletesretract;
    }

    public void setCantidadbilletesretract(Short cantidadbilletesretract) {
        this.cantidadbilletesretract = cantidadbilletesretract;
    }

    public Long getValorefectivoretract() {
        return valorefectivoretract;
    }

    public void setValorefectivoretract(Long valorefectivoretract) {
        this.valorefectivoretract = valorefectivoretract;
    }

    public Short getCantidadnumerocheques() {
        return cantidadnumerocheques;
    }

    public void setCantidadnumerocheques(Short cantidadnumerocheques) {
        this.cantidadnumerocheques = cantidadnumerocheques;
    }

    public Long getValorcheques() {
        return valorcheques;
    }

    public void setValorcheques(Long valorcheques) {
        this.valorcheques = valorcheques;
    }

    public Short getCantidadchequesretract() {
        return cantidadchequesretract;
    }

    public void setCantidadchequesretract(Short cantidadchequesretract) {
        this.cantidadchequesretract = cantidadchequesretract;
    }

    public Long getValorchequesretract() {
        return valorchequesretract;
    }

    public void setValorchequesretract(Long valorchequesretract) {
        this.valorchequesretract = valorchequesretract;
    }

    public Short getCandepefectivoctacte() {
        return candepefectivoctacte;
    }

    public void setCandepefectivoctacte(Short candepefectivoctacte) {
        this.candepefectivoctacte = candepefectivoctacte;
    }

    public Long getValordepefectivoctacte() {
        return valordepefectivoctacte;
    }

    public void setValordepefectivoctacte(Long valordepefectivoctacte) {
        this.valordepefectivoctacte = valordepefectivoctacte;
    }

    public Short getCandepefectivoctaaho() {
        return candepefectivoctaaho;
    }

    public void setCandepefectivoctaaho(Short candepefectivoctaaho) {
        this.candepefectivoctaaho = candepefectivoctaaho;
    }

    public Long getValordepefectivoctaaho() {
        return valordepefectivoctaaho;
    }

    public void setValordepefectivoctaaho(Long valordepefectivoctaaho) {
        this.valordepefectivoctaaho = valordepefectivoctaaho;
    }

    public Short getCantidadpagoefectivotc() {
        return cantidadpagoefectivotc;
    }

    public void setCantidadpagoefectivotc(Short cantidadpagoefectivotc) {
        this.cantidadpagoefectivotc = cantidadpagoefectivotc;
    }

    public Long getValorpagoefectivotc() {
        return valorpagoefectivotc;
    }

    public void setValorpagoefectivotc(Long valorpagoefectivotc) {
        this.valorpagoefectivotc = valorpagoefectivotc;
    }

    public Short getCantidadpagoefectivofm() {
        return cantidadpagoefectivofm;
    }

    public void setCantidadpagoefectivofm(Short cantidadpagoefectivofm) {
        this.cantidadpagoefectivofm = cantidadpagoefectivofm;
    }

    public Long getValorpagoefectivofm() {
        return valorpagoefectivofm;
    }

    public void setValorpagoefectivofm(Long valorpagoefectivofm) {
        this.valorpagoefectivofm = valorpagoefectivofm;
    }

    public Short getCantidadrecaudoefectivo() {
        return cantidadrecaudoefectivo;
    }

    public void setCantidadrecaudoefectivo(Short cantidadrecaudoefectivo) {
        this.cantidadrecaudoefectivo = cantidadrecaudoefectivo;
    }

    public Long getValorrecaudoefectivo() {
        return valorrecaudoefectivo;
    }

    public void setValorrecaudoefectivo(Long valorrecaudoefectivo) {
        this.valorrecaudoefectivo = valorrecaudoefectivo;
    }

    public Short getCantidaddepchequectacte() {
        return cantidaddepchequectacte;
    }

    public void setCantidaddepchequectacte(Short cantidaddepchequectacte) {
        this.cantidaddepchequectacte = cantidaddepchequectacte;
    }

    public Long getValordepchequectacte() {
        return valordepchequectacte;
    }

    public void setValordepchequectacte(Long valordepchequectacte) {
        this.valordepchequectacte = valordepchequectacte;
    }

    public Short getCantidaddepchequectaaho() {
        return cantidaddepchequectaaho;
    }

    public void setCantidaddepchequectaaho(Short cantidaddepchequectaaho) {
        this.cantidaddepchequectaaho = cantidaddepchequectaaho;
    }

    public Long getValordepchequectaah() {
        return valordepchequectaah;
    }

    public void setValordepchequectaah(Long valordepchequectaah) {
        this.valordepchequectaah = valordepchequectaah;
    }

    public Short getCantidadpagochequetc() {
        return cantidadpagochequetc;
    }

    public void setCantidadpagochequetc(Short cantidadpagochequetc) {
        this.cantidadpagochequetc = cantidadpagochequetc;
    }

    public Long getValorpagochequetc() {
        return valorpagochequetc;
    }

    public void setValorpagochequetc(Long valorpagochequetc) {
        this.valorpagochequetc = valorpagochequetc;
    }

    public Short getCantidadpagochequefm() {
        return cantidadpagochequefm;
    }

    public void setCantidadpagochequefm(Short cantidadpagochequefm) {
        this.cantidadpagochequefm = cantidadpagochequefm;
    }

    public Long getValorpagochequefm() {
        return valorpagochequefm;
    }

    public void setValorpagochequefm(Long valorpagochequefm) {
        this.valorpagochequefm = valorpagochequefm;
    }

    public Short getCantidadrecaudocheque() {
        return cantidadrecaudocheque;
    }

    public void setCantidadrecaudocheque(Short cantidadrecaudocheque) {
        this.cantidadrecaudocheque = cantidadrecaudocheque;
    }

    public Long getValorrecaudocheque() {
        return valorrecaudocheque;
    }

    public void setValorrecaudocheque(Long valorrecaudocheque) {
        this.valorrecaudocheque = valorrecaudocheque;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public Integer getCodigoatmdispensador() {
        return codigoatmdispensador;
    }

    public void setCodigoatmdispensador(Integer codigoatmdispensador) {
        this.codigoatmdispensador = codigoatmdispensador;
    }

    public String getZonaoficina() {
        return zonaoficina;
    }

    public void setZonaoficina(String zonaoficina) {
        this.zonaoficina = zonaoficina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billetajemultiPK != null ? billetajemultiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Billetajemulti)) {
            return false;
        }
        Billetajemulti other = (Billetajemulti) object;
        if ((this.billetajemultiPK == null && other.billetajemultiPK != null) || (this.billetajemultiPK != null && !this.billetajemultiPK.equals(other.billetajemultiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Billetajemulti[billetajemultiPK=" + billetajemultiPK + "]";
    }

}
