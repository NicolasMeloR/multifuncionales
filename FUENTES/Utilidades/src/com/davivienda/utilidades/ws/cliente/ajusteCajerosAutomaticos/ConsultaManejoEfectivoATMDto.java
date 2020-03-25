
package com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultaManejoEfectivoATMDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultaManejoEfectivoATMDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://servicios.impl.procesadortransacciones.davivienda.com/}parametrosDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="estadoAtm" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="tipoTransaccion" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaManejoEfectivoATMDto", propOrder = {
    "estadoAtm",
    "tipoTransaccion",
    "valor"
})
public class ConsultaManejoEfectivoATMDto
    extends ParametrosDeTransaccionBaseDTO
{

    protected Short estadoAtm;
    protected Short tipoTransaccion;
    protected BigDecimal valor;

    /**
     * Gets the value of the estadoAtm property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getEstadoAtm() {
        return estadoAtm;
    }

    /**
     * Sets the value of the estadoAtm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setEstadoAtm(Short value) {
        this.estadoAtm = value;
    }

    /**
     * Gets the value of the tipoTransaccion property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * Sets the value of the tipoTransaccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setTipoTransaccion(Short value) {
        this.tipoTransaccion = value;
    }

    /**
     * Gets the value of the valor property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Sets the value of the valor property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValor(BigDecimal value) {
        this.valor = value;
    }

}
