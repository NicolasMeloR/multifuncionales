
package com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respAjustarSobranteCajeroDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respAjustarSobranteCajeroDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://servicios.impl.procesadortransacciones.davivienda.com/}respuestaDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respAjustarSobranteCajeroDto", propOrder = {
    "valor"
})
public class RespAjustarSobranteCajeroDto
    extends RespuestaDeTransaccionBaseDTO
{

    protected Short valor;

    /**
     * Gets the value of the valor property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getValor() {
        return valor;
    }

    /**
     * Sets the value of the valor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setValor(Short value) {
        this.valor = value;
    }

}
