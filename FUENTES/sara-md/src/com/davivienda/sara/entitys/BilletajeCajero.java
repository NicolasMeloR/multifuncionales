package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BilletajeCajero.java
 *
 * Fecha : 2/07/2007, 02:47:13 PM Descripción : Entity para la carga de
 * billetaje en cajeros, utilizado por cuadrecifras
 *
 * @author : jjvargas
 * @version : $Id$
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "BilletajeCajero.CodigoCajero",
            query = "SELECT b FROM BilletajeCajero b WHERE b.billetajeCajeroPK.codigoCajero = :codigoCajero"),
    @NamedQuery(
            name = "BilletajeCajero.Fecha",
            query = "SELECT b FROM BilletajeCajero b WHERE b.billetajeCajeroPK.fecha = :fecha order by b.billetajeCajeroPK.codigoCajero"),
    @NamedQuery(
            name = "BilletajeCajero.FechaCajero",
            query = "SELECT b FROM BilletajeCajero b WHERE b.billetajeCajeroPK.fecha = :fecha and b.billetajeCajeroPK.codigoCajero = :codigoCajero order by b.billetajeCajeroPK.codigoCajero, b.billetajeCajeroPK.fecha")

})
public class BilletajeCajero implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BilletajeCajeroPK billetajeCajeroPK;
    @JoinColumn(name = "CODIGOCAJERO", referencedColumnName = "CODIGOCAJERO", insertable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cajero cajero;
    private Integer denominacion1;
    private Integer total1;
    private Integer dispensado1;
    private Integer caja1;
    private Integer purga1;
    private Integer retract1;
    private Integer denominacion2;
    private Integer total2;
    private Integer dispensado2;
    private Integer caja2;
    private Integer purga2;
    private Integer retract2;
    private Integer denominacion3;
    private Integer total3;
    private Integer dispensado3;
    private Integer caja3;
    private Integer purga3;
    private Integer retract3;
    private Integer denominacion4;
    private Integer total4;
    private Integer dispensado4;
    private Integer caja4;
    private Integer purga4;
    private Integer retract4;
    private Integer canRetDav;
    private Long valRetDav;
    private Integer canAvan;
    private Long valAvan;
    private Integer canRetRed;
    private Long valRetRed;
    
    /**
     * 5 Gaveta
     */
    @Column(name = "DENOMINACION5")
    private Integer denominacion5;
    @Column(name = "TOTAL5")
    private Integer total5;
    @Column(name = "DISPENSADO5")
    private Integer dispensado5;
    @Column(name = "CAJA5")
    private Integer caja5;
    @Column(name = "PURGA5")
    private Integer purga5;
    @Column(name = "RETRACT5")
    private Integer retract5;
    @Column(name = "DENOMINACION_UF", nullable = true)
    private Integer denominacionUf;
    @Column(name = "TOTAL_UF", nullable = true)
    private Integer totalUf;
    @Column(name = "DISPENSADO_UF", nullable = true)
    private Integer dispensadoUf;
    @Column(name = "CAJA_UF", nullable = true)
    private Integer cajaUf;
    @Column(name = "PURGA_UF", nullable = true)
    private Integer purgaUf;
    @Column(name = "RETRACT_UF", nullable = true)
    private Integer retractUf;
    /*private Integer canAvanNd;
    private Long valAvanNd;
    private Integer canretCibc;
    private Long valavanCibc;
    private Integer canretGiros;
    private Long valavanGiros; /*

    /**
     * Crea una nueva instancia de <code>BilletajeCajero</code>.
     */
    public BilletajeCajero() {
    }

    public BilletajeCajero(BilletajeCajeroPK billetajeCajeroPK) {
        this.billetajeCajeroPK = billetajeCajeroPK;
    }

    public BilletajeCajero(Integer codigocajero, Date fecha) {
        this.billetajeCajeroPK = new BilletajeCajeroPK(codigocajero, fecha);
    }

    public BilletajeCajeroPK getBilletajeCajeroPK() {
        return billetajeCajeroPK;
    }

    public void setBilletajeCajeroPK(BilletajeCajeroPK billetajeCajeroPK) {
        this.billetajeCajeroPK = billetajeCajeroPK;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public Integer getDenominacion1() {
        return denominacion1;
    }

    public void setDenominacion1(Integer denominacion1) {
        this.denominacion1 = denominacion1;
    }

    public Integer getTotal1() {
        return total1;
    }

    public void setTotal1(Integer total1) {
        this.total1 = total1;
    }

    public Integer getDispensado1() {
        return dispensado1;
    }

    public void setDispensado1(Integer dispensado1) {
        this.dispensado1 = dispensado1;
    }

    public Integer getCaja1() {
        return caja1;
    }

    public void setCaja1(Integer caja1) {
        this.caja1 = caja1;
    }

    public Integer getPurga1() {
        return purga1;
    }

    public void setPurga1(Integer purga1) {
        this.purga1 = purga1;
    }

    public Integer getRetract1() {
        return retract1;
    }

    public void setRetract1(Integer retract1) {
        this.retract1 = retract1;
    }

    public Integer getDenominacion2() {
        return denominacion2;
    }

    public void setDenominacion2(Integer denominacion2) {
        this.denominacion2 = denominacion2;
    }

    public Integer getTotal2() {
        return total2;
    }

    public void setTotal2(Integer total2) {
        this.total2 = total2;
    }

    public Integer getDispensado2() {
        return dispensado2;
    }

    public void setDispensado2(Integer dispensado2) {
        this.dispensado2 = dispensado2;
    }

    public Integer getCaja2() {
        return caja2;
    }

    public void setCaja2(Integer caja2) {
        this.caja2 = caja2;
    }

    public Integer getPurga2() {
        return purga2;
    }

    public void setPurga2(Integer purga2) {
        this.purga2 = purga2;
    }

    public Integer getRetract2() {
        return retract2;
    }

    public void setRetract2(Integer retract2) {
        this.retract2 = retract2;
    }

    public Integer getDenominacion3() {
        return denominacion3;
    }

    public void setDenominacion3(Integer denominacion3) {
        this.denominacion3 = denominacion3;
    }

    public Integer getTotal3() {
        return total3;
    }

    public void setTotal3(Integer total3) {
        this.total3 = total3;
    }

    public Integer getDispensado3() {
        return dispensado3;
    }

    public void setDispensado3(Integer dispensado3) {
        this.dispensado3 = dispensado3;
    }

    public Integer getCaja3() {
        return caja3;
    }

    public void setCaja3(Integer caja3) {
        this.caja3 = caja3;
    }

    public Integer getPurga3() {
        return purga3;
    }

    public void setPurga3(Integer purga3) {
        this.purga3 = purga3;
    }

    public Integer getRetract3() {
        return retract3;
    }

    public void setRetract3(Integer retract3) {
        this.retract3 = retract3;
    }

    public Integer getDenominacion4() {
        return denominacion4;
    }

    public void setDenominacion4(Integer denominacion4) {
        this.denominacion4 = denominacion4;
    }

    public Integer getTotal4() {
        return total4;
    }

    public void setTotal4(Integer total4) {
        this.total4 = total4;
    }

    public Integer getDispensado4() {
        return dispensado4;
    }

    public void setDispensado4(Integer dispensado4) {
        this.dispensado4 = dispensado4;
    }

    public Integer getCaja4() {
        return caja4;
    }

    public void setCaja4(Integer caja4) {
        this.caja4 = caja4;
    }

    public Integer getPurga4() {
        return purga4;
    }

    public void setPurga4(Integer purga4) {
        this.purga4 = purga4;
    }

    public Integer getRetract4() {
        return retract4;
    }

    public void setRetract4(Integer retract4) {
        this.retract4 = retract4;
    }

    public Integer getCanRetDav() {
        return canRetDav;
    }

    public void setCanRetDav(Integer canRetDav) {
        this.canRetDav = canRetDav;
    }

    public Long getValRetDav() {
        return valRetDav;
    }

    public void setValRetDav(Long valRetDav) {
        this.valRetDav = valRetDav;
    }

    public Integer getCanAvan() {
        return canAvan;
    }

    public void setCanAvan(Integer canAvan) {
        this.canAvan = canAvan;
    }

    public Long getValAvan() {
        return valAvan;
    }

    public void setValAvan(Long valAvan) {
        this.valAvan = valAvan;
    }

    public Integer getCanRetRed() {
        return canRetRed;
    }

    public void setCanRetRed(Integer canRetRed) {
        this.canRetRed = canRetRed;
    }

    public Long getValRetRed() {
        return valRetRed;
    }

    public void setValRetRed(Long valRetRed) {
        this.valRetRed = valRetRed;
    }

    public Integer getDenominacion5() {
        return denominacion5;
    }

    public void setDenominacion5(Integer denominacion5) {
        this.denominacion5 = denominacion5;
    }

    public Integer getTotal5() {
        return total5;
    }

    public void setTotal5(Integer total5) {
        this.total5 = total5;
    }

    public Integer getDispensado5() {
        return dispensado5;
    }

    public void setDispensado5(Integer dispensado5) {
        this.dispensado5 = dispensado5;
    }

    public Integer getCaja5() {
        return caja5;
    }

    public void setCaja5(Integer caja5) {
        this.caja5 = caja5;
    }

    public Integer getPurga5() {
        return purga5;
    }

    public void setPurga5(Integer purga5) {
        this.purga5 = purga5;
    }

    public Integer getRetract5() {
        return retract5;
    }

    public void setRetract5(Integer retract5) {
        this.retract5 = retract5;
    }

    public Integer getDenominacionUf() {
        return denominacionUf;
    }

    public void setDenominacionUf(Integer denominacionUf) {
        this.denominacionUf = denominacionUf;
    }

    public Integer getTotalUf() {
        return totalUf;
    }

    public void setTotalUf(Integer totalUf) {
        this.totalUf = totalUf;
    }

    public Integer getDispensadoUf() {
        return dispensadoUf;
    }

    public void setDispensadoUf(Integer dispensadoUf) {
        this.dispensadoUf = dispensadoUf;
    }

    public Integer getCajaUf() {
        return cajaUf;
    }

    public void setCajaUf(Integer cajaUf) {
        this.cajaUf = cajaUf;
    }

    public Integer getPurgaUf() {
        return purgaUf;
    }

    public void setPurgaUf(Integer purgaUf) {
        this.purgaUf = purgaUf;
    }

    public Integer getRetractUf() {
        return retractUf;
    }

    public void setRetractUf(Integer retractUf) {
        this.retractUf = retractUf;
    }
    /*

    public Integer getCanAvanNd() {
        return canAvanNd;
    }

    public void setCanAvanNd(Integer canAvanNd) {
        this.canAvanNd = canAvanNd;
    }

    public Long getValAvanNd() {
        return valAvanNd;
    }

    public void setValAvanNd(Long valAvanNd) {
        this.valAvanNd = valAvanNd;
    }

    

    public Integer getCanretCibc() {
        return canretCibc;
    }

    public void setCanretCibc(Integer canretCibc) {
        this.canretCibc = canretCibc;
    }

    public Long getValavanCibc() {
        return valavanCibc;
    }

    public void setValavanCibc(Long valavanCibc) {
        this.valavanCibc = valavanCibc;
    }

    public Integer getCanretGiros() {
        return canretGiros;
    }

    public void setCanretGiros(Integer canretGiros) {
        this.canretGiros = canretGiros;
    }

    public Long getValavanGiros() {
        return valavanGiros;
    }

    public void setValavanGiros(Long valavanGiros) {
        this.valavanGiros = valavanGiros;
    }
    */

    @Override
    public String toString() {
        return "BilletajeCajero{" + "billetajeCajeroPK=" + billetajeCajeroPK + ", cajero=" + cajero + ", denominacion1=" + denominacion1 + ", total1=" + total1 + ", dispensado1=" + dispensado1 + ", caja1=" + caja1 + ", purga1=" + purga1 + ", retract1=" + retract1 + ", denominacion2=" + denominacion2 + ", total2=" + total2 + ", dispensado2=" + dispensado2 + ", caja2=" + caja2 + ", purga2=" + purga2 + ", retract2=" + retract2 + ", denominacion3=" + denominacion3 + ", total3=" + total3 + ", dispensado3=" + dispensado3 + ", caja3=" + caja3 + ", purga3=" + purga3 + ", retract3=" + retract3 + ", denominacion4=" + denominacion4 + ", total4=" + total4 + ", dispensado4=" + dispensado4 + ", caja4=" + caja4 + ", purga4=" + purga4 + ", retract4=" + retract4 + ", canRetDav=" + canRetDav + ", valRetDav=" + valRetDav + ", canAvan=" + canAvan + ", valAvan=" + valAvan + ", canRetRed=" + canRetRed + ", valRetRed=" + valRetRed + ", denominacion5=" + denominacion5 + ", total5=" + total5 + ", dispensado5=" + dispensado5 + ", caja5=" + caja5 + ", purga5=" + purga5 + ", retract5=" + retract5 + ", denominacionUf=" + denominacionUf + ", totalUf=" + totalUf + ", dispensadoUf=" + dispensadoUf + ", cajaUf=" + cajaUf + ", purgaUf=" + purgaUf + ", retractUf=" + retractUf + '}';
    }
    
    

}
