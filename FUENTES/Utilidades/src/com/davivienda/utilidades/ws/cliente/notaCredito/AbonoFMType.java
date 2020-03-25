
package com.davivienda.utilidades.ws.cliente.notaCredito;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbonoFMType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbonoFMType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cuentaCredito" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="talonCredito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="indAbono" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="valorEfectivo" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorCheque" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="valorTotal" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="indAbonoDirigido" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numeroUtilizacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbonoFMType", propOrder = {
    "cuentaCredito",
    "talonCredito",
    "indAbono",
    "valorEfectivo",
    "valorCheque",
    "valorTotal",
    "indAbonoDirigido",
    "numeroUtilizacion"
})
public class AbonoFMType {

    protected long cuentaCredito;
    protected int talonCredito;
    protected short indAbono;
    @XmlElement(required = true)
    protected BigDecimal valorEfectivo;
    @XmlElement(required = true)
    protected BigDecimal valorCheque;
    @XmlElement(required = true)
    protected BigDecimal valorTotal;
    protected int indAbonoDirigido;
    protected int numeroUtilizacion;

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
     * Gets the value of the talonCredito property.
     * 
     */
    public int getTalonCredito() {
        return talonCredito;
    }

    /**
     * Sets the value of the talonCredito property.
     * 
     */
    public void setTalonCredito(int value) {
        this.talonCredito = value;
    }

    /**
     * Gets the value of the indAbono property.
     * 
     */
    public short getIndAbono() {
        return indAbono;
    }

    /**
     * Sets the value of the indAbono property.
     * 
     */
    public void setIndAbono(short value) {
        this.indAbono = value;
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
     * Gets the value of the indAbonoDirigido property.
     * 
     */
    public int getIndAbonoDirigido() {
        return indAbonoDirigido;
    }

    /**
     * Sets the value of the indAbonoDirigido property.
     * 
     */
    public void setIndAbonoDirigido(int value) {
        this.indAbonoDirigido = value;
    }

    /**
     * Gets the value of the numeroUtilizacion property.
     * 
     */
    public int getNumeroUtilizacion() {
        return numeroUtilizacion;
    }

    /**
     * Sets the value of the numeroUtilizacion property.
     * 
     */
    public void setNumeroUtilizacion(int value) {
        this.numeroUtilizacion = value;
    }

}
