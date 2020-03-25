
package com.davivienda.utilidades.ws.cliente.resumenIDOATM;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaResumenIDOATMDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaResumenIDOATMDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/}respuestaDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="registrosRepetitivos" type="{http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/}respuestaResumenIDOATMRepDto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaResumenIDOATMDto", propOrder = {
    "registrosRepetitivos"
})
public class RespuestaResumenIDOATMDto
    extends RespuestaDeTransaccionBaseDTO
{

    @XmlElement(nillable = true)
    protected List<RespuestaResumenIDOATMRepDto> registrosRepetitivos;

    /**
     * Gets the value of the registrosRepetitivos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the registrosRepetitivos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegistrosRepetitivos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RespuestaResumenIDOATMRepDto }
     * 
     * 
     */
    public List<RespuestaResumenIDOATMRepDto> getRegistrosRepetitivos() {
        if (registrosRepetitivos == null) {
            registrosRepetitivos = new ArrayList<RespuestaResumenIDOATMRepDto>();
        }
        return this.registrosRepetitivos;
    }

}
