
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campoExtendidoDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="campoExtendidoDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://solicitudnotacreditoctaahorrosinterface.servicios.procesadortransacciones.davivienda.com/}campoDto">
 *       &lt;sequence>
 *         &lt;element name="JMascara" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PMascara" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "campoExtendidoDto", propOrder = {
    "jMascara",
    "pMascara",
    "valor"
})
public class CampoExtendidoDto
    extends CampoDto
{

    @XmlElement(name = "JMascara")
    protected String jMascara;
    @XmlElement(name = "PMascara")
    protected String pMascara;
    protected String valor;

    /**
     * Gets the value of the jMascara property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJMascara() {
        return jMascara;
    }

    /**
     * Sets the value of the jMascara property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJMascara(String value) {
        this.jMascara = value;
    }

    /**
     * Gets the value of the pMascara property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPMascara() {
        return pMascara;
    }

    /**
     * Sets the value of the pMascara property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPMascara(String value) {
        this.pMascara = value;
    }

    /**
     * Gets the value of the valor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValor() {
        return valor;
    }

    /**
     * Sets the value of the valor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValor(String value) {
        this.valor = value;
    }

}
