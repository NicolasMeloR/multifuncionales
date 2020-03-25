
package com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaConsultaManejoEfectivoATMDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaConsultaManejoEfectivoATMDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://servicios.impl.procesadortransacciones.davivienda.com/}respuestaDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="valorProvision" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaConsultaManejoEfectivoATMDto", propOrder = {
    "valorProvision"
})
public class RespuestaConsultaManejoEfectivoATMDto
    extends RespuestaDeTransaccionBaseDTO
{

    protected BigDecimal valorProvision;

    /**
     * Gets the value of the valorProvision property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorProvision() {
        return valorProvision;
    }

    /**
     * Sets the value of the valorProvision property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorProvision(BigDecimal value) {
        this.valorProvision = value;
    }

}
