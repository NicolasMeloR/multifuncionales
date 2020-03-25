
package com.davivienda.utilidades.ws.cliente.notaCreditoFM;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaDeTransaccionBaseDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaDeTransaccionBaseDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caracterAcepta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codMRespuesta" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="consecutivo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="llaveRepetitivoSiguienteTrama" type="{http://notacreditofminterface.procesadortransacciones.davivienda.com/}campoExtendidoDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="MRespuesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroLineas" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="respuestaOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tieneMasRegistros" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ultimoMensaje" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaDeTransaccionBaseDTO", propOrder = {
    "caracterAcepta",
    "codMRespuesta",
    "consecutivo",
    "llaveRepetitivoSiguienteTrama",
    "mRespuesta",
    "numeroLineas",
    "respuestaOriginal",
    "tieneMasRegistros",
    "total",
    "ultimoMensaje"
})
@XmlSeeAlso({
    RespuestaNotaCreditoFMDto.class
})
public class RespuestaDeTransaccionBaseDTO {

    protected String caracterAcepta;
    protected Short codMRespuesta;
    protected Integer consecutivo;
    @XmlElement(nillable = true)
    protected List<CampoExtendidoDto> llaveRepetitivoSiguienteTrama;
    @XmlElement(name = "MRespuesta")
    protected String mRespuesta;
    protected Short numeroLineas;
    protected String respuestaOriginal;
    protected Boolean tieneMasRegistros;
    protected Integer total;
    protected Short ultimoMensaje;

    /**
     * Gets the value of the caracterAcepta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaracterAcepta() {
        return caracterAcepta;
    }

    /**
     * Sets the value of the caracterAcepta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaracterAcepta(String value) {
        this.caracterAcepta = value;
    }

    /**
     * Gets the value of the codMRespuesta property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCodMRespuesta() {
        return codMRespuesta;
    }

    /**
     * Sets the value of the codMRespuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCodMRespuesta(Short value) {
        this.codMRespuesta = value;
    }

    /**
     * Gets the value of the consecutivo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getConsecutivo() {
        return consecutivo;
    }

    /**
     * Sets the value of the consecutivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setConsecutivo(Integer value) {
        this.consecutivo = value;
    }

    /**
     * Gets the value of the llaveRepetitivoSiguienteTrama property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the llaveRepetitivoSiguienteTrama property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLlaveRepetitivoSiguienteTrama().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampoExtendidoDto }
     * 
     * 
     */
    public List<CampoExtendidoDto> getLlaveRepetitivoSiguienteTrama() {
        if (llaveRepetitivoSiguienteTrama == null) {
            llaveRepetitivoSiguienteTrama = new ArrayList<CampoExtendidoDto>();
        }
        return this.llaveRepetitivoSiguienteTrama;
    }

    /**
     * Gets the value of the mRespuesta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMRespuesta() {
        return mRespuesta;
    }

    /**
     * Sets the value of the mRespuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMRespuesta(String value) {
        this.mRespuesta = value;
    }

    /**
     * Gets the value of the numeroLineas property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumeroLineas() {
        return numeroLineas;
    }

    /**
     * Sets the value of the numeroLineas property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumeroLineas(Short value) {
        this.numeroLineas = value;
    }

    /**
     * Gets the value of the respuestaOriginal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRespuestaOriginal() {
        return respuestaOriginal;
    }

    /**
     * Sets the value of the respuestaOriginal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRespuestaOriginal(String value) {
        this.respuestaOriginal = value;
    }

    /**
     * Gets the value of the tieneMasRegistros property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTieneMasRegistros() {
        return tieneMasRegistros;
    }

    /**
     * Sets the value of the tieneMasRegistros property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTieneMasRegistros(Boolean value) {
        this.tieneMasRegistros = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotal(Integer value) {
        this.total = value;
    }

    /**
     * Gets the value of the ultimoMensaje property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getUltimoMensaje() {
        return ultimoMensaje;
    }

    /**
     * Sets the value of the ultimoMensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setUltimoMensaje(Short value) {
        this.ultimoMensaje = value;
    }

}
