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
 * InformeDiferencias Descripción : Fecha : 10/04/2017 12:21:20 PM
 *
 * @author : jediazs@co.ibm.com
 * @version : 1.0
 *
 */
@Entity
@Table(name = "INFORME_DIFERENCIAS")
@NamedQueries({
    @NamedQuery(
            name = "InformeDiferencias.CajeroRangoFecha",
            query = "select object(obj) from InformeDiferencias obj  "
            + "        where obj.informeDiferenciasPK.codigoCajero =:codigoCajero and "
            + "               obj.informeDiferenciasPK.fechaRegistro between :fechaInicial and :fechaFinal "
    ),
    @NamedQuery(
            name = "InformeDiferencias.RangoFecha",
            query = "select object(obj) from InformeDiferencias obj  "
            + "        where obj.informeDiferenciasPK.fechaRegistro between :fechaInicial and :fechaFinal "
    )
})
public class InformeDiferencias implements Serializable {

    @EmbeddedId
    protected InformeDiferenciasPK informeDiferenciasPK;

    @Column(name = "CODIGOOCCA", nullable = false)
    private Integer codigoOcca;

    @Column(name = "PROVDIASGTE_MAQUINA")
    private Long valorMaquina;

    @Column(name = "PROVDIASGTE_LINEA")
    private Long valorLinea;

    @Column(name = "DIFERENCIAS")
    private Long valorDiferencias;

    @Column(name = "FECHASISTEMA")
    @Temporal(TemporalType.DATE)
    private Date fechaSistema;

    @Column(name = "AUMENTO")
    private Long aumento;

    @Column(name = "DISMINUCION")
    private Long disminucion;

    @Column(name = "FALTANTE")
    private Long faltante;

    @Column(name = "SOBRANTE")
    private Long sobrante;

    @Column(name = "APLICADO")
    private String aplicado;

    /**
     * Crea una nueva instancia de <code>InformeDiferencias</code>.
     */
    public InformeDiferencias() {
    }

    public InformeDiferenciasPK getInformeDiferenciasPK() {
        return informeDiferenciasPK;
    }

    public void setInformeDiferenciasPK(InformeDiferenciasPK informeDiferenciasPK) {
        this.informeDiferenciasPK = informeDiferenciasPK;
    }

    public Integer getCodigoOcca() {
        return codigoOcca;
    }

    public void setCodigoOcca(Integer codigoOcca) {
        this.codigoOcca = codigoOcca;
    }

    public Long getValorMaquina() {
        return valorMaquina;
    }

    public void setValorMaquina(Long valorMaquina) {
        this.valorMaquina = valorMaquina;
    }

    public Long getValorLinea() {
        return valorLinea;
    }

    public void setValorLinea(Long valorLinea) {
        this.valorLinea = valorLinea;
    }

    public Long getValorDiferencias() {
        return valorDiferencias;
    }

    public void setValorDiferencias(Long valorDiferencias) {
        this.valorDiferencias = valorDiferencias;
    }

    public Date getFechaSistema() {
        return fechaSistema;
    }

    public void setFechaSistema(Date fechaSistema) {
        this.fechaSistema = fechaSistema;
    }

    public Long getAumento() {
        return aumento;
    }

    public void setAumento(Long aumento) {
        this.aumento = aumento;
    }

    public Long getDisminucion() {
        return disminucion;
    }

    public void setDisminucion(Long disminucion) {
        this.disminucion = disminucion;
    }

    public Long getFaltante() {
        return faltante;
    }

    public void setFaltante(Long faltante) {
        this.faltante = faltante;
    }

    public Long getSobrante() {
        return sobrante;
    }

    public void setSobrante(Long sobrante) {
        this.sobrante = sobrante;
    }

    public String getAplicado() {
        return aplicado;
    }

    public void setAplicado(String aplicado) {
        this.aplicado = aplicado;
    }

    @Override
    public String toString() {
        return "InformeDiferencias{" + "informeDiferenciasPK=" + informeDiferenciasPK + ", codigoOcca=" + codigoOcca + ", valorMaquina=" + valorMaquina + ", valorLinea=" + valorLinea + ", valorDiferencias=" + valorDiferencias + ", fechaSistema=" + fechaSistema + ", aumento=" + aumento + ", disminucion=" + disminucion + ", faltante=" + faltante + ", sobrante=" + sobrante + ", aplicado=" + aplicado + '}';
    }

    public InformeDiferencias actualizarEntity(InformeDiferencias obj) {
        setInformeDiferenciasPK(obj.informeDiferenciasPK);
        setCodigoOcca(obj.codigoOcca);
        setValorMaquina(obj.valorMaquina);
        setValorLinea(obj.valorLinea);
        setValorDiferencias(obj.valorDiferencias);
        setAumento(obj.aumento);
        setDisminucion(obj.disminucion);
        setFaltante(obj.faltante);
        setSobrante(obj.sobrante);
        setAplicado(obj.aplicado);
        return this;
    }
}
