
package com.davivienda.utilidades.ws.cliente.notaCredito;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbonoTCType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbonoTCType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cuentaCredito" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="numDocumento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="valorEfectivo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorCheque" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorTotal" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="indPago" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="emisorTarjeta" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="indCartera" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="indAbonoDirigido" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="fechaUso" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="comprobanteCredito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbonoTCType", propOrder = {
    "cuentaCredito",
    "numDocumento",
    "valorEfectivo",
    "valorCheque",
    "valorTotal",
    "indPago",
    "emisorTarjeta",
    "indCartera",
    "indAbonoDirigido",
    "fechaUso",
    "comprobanteCredito"
})
public class AbonoTCType {

    protected long cuentaCredito;
    protected int numDocumento;
    @XmlElement(required = true)
    protected BigDecimal valorEfectivo;
    @XmlElement(required = true)
    protected BigDecimal valorCheque;
    @XmlElement(required = true)
    protected BigDecimal valorTotal;
    protected short indPago;
    protected short emisorTarjeta;
    protected short indCartera;
    protected short indAbonoDirigido;
    protected int fechaUso;
    protected int comprobanteCredito;

    /**
     * Gets the value of the cuentaCredito property.
     * 
     */
    public long getCuentaCredito() {
        return cuentaCredito;
    }

    /**
     * Sets the value of the cuentaCredito property.
     * 
     */
    public void setCuentaCredito(long value) {
        this.cuentaCredito = value;
    }

    /**
     * Gets the value of the numDocumento property.
     * 
     */
    public int getNumDocumento() {
        return numDocumento;
    }

    /**
     * Sets the value of the numDocumento property.
     * 
     */
    public void setNumDocumento(int value) {
        this.numDocumento = value;
    }

    /**
     * Gets the value of the valorEfectivo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorEfectivo() {
        return valorEfectivo;
    }

    /**
     * Sets the value of the valorEfectivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorEfectivo(BigDecimal value) {
        this.valorEfectivo = value;
    }

    /**
     * Gets the value of the valorCheque property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorCheque() {
        return valorCheque;
    }

    /**
     * Sets the value of the valorCheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorCheque(BigDecimal value) {
        this.valorCheque = value;
    }

    /**
     * Gets the value of the valorTotal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    /**
     * Sets the value of the valorTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorTotal(BigDecimal value) {
        this.valorTotal = value;
    }

    /**
     * Gets the value of the indPago property.
     * 
     */
    public short getIndPago() {
        return indPago;
    }

    /**
     * Sets the value of the indPago property.
     * 
     */
    public void setIndPago(short value) {
        this.indPago = value;
    }

    /**
     * Gets the value of the emisorTarjeta property.
     * 
     */
    public short getEmisorTarjeta() {
        return emisorTarjeta;
    }

    /**
     * Sets the value of the emisorTarjeta property.
     * 
     */
    public void setEmisorTarjeta(short value) {
        this.emisorTarjeta = value;
    }

    /**
     * Gets the value of the indCartera property.
     * 
     */
    public short getIndCartera() {
        return indCartera;
    }

    /**
     * Sets the value of the indCartera property.
     * 
     */
    public void setIndCartera(short value) {
        this.indCartera = value;
    }

    /**
     * Gets the value of the indAbonoDirigido property.
     * 
     */
    public short getIndAbonoDirigido() {
        return indAbonoDirigido;
    }

    /**
     * Sets the value of the indAbonoDirigido property.
     * 
     */
    public void setIndAbonoDirigido(short value) {
        this.indAbonoDirigido = value;
    }

    /**
     * Gets the value of the fechaUso property.
     * 
     */
    public int getFechaUso() {
        return fechaUso;
    }

    /**
     * Sets the value of the fechaUso property.
     * 
     */
    public void setFechaUso(int value) {
        this.fechaUso = value;
    }

    /**
     * Gets the value of the comprobanteCredito property.
     * 
     */
    public int getComprobanteCredito() {
        return comprobanteCredito;
    }

    /**
     * Sets the value of the comprobanteCredito property.
     * 
     */
    public void setComprobanteCredito(int value) {
        this.comprobanteCredito = value;
    }

}
