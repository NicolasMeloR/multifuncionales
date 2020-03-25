
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RespuestaNotaCreditoCtaCorrienteDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RespuestaNotaCreditoCtaCorrienteDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MRespuesta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="caracterAcepta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codMRespuesta" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="consecutivo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="llaveRepetitivoSiguienteTrama" type="{http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes}CampoExtendidoDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="numeroLineas" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="respuestaOriginal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tieneMasRegistros" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ultimoMensaje" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="motivo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oficina" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="referencia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="talon" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valor" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespuestaNotaCreditoCtaCorrienteDto", propOrder = {
    "mRespuesta",
    "caracterAcepta",
    "codMRespuesta",
    "consecutivo",
    "llaveRepetitivoSiguienteTrama",
    "numeroLineas",
    "respuestaOriginal",
    "tieneMasRegistros",
    "total",
    "ultimoMensaje",
    "fecha",
    "motivo",
    "oficina",
    "referencia",
    "talon",
    "valor"
})
public class RespuestaNotaCreditoCtaCorrienteDto {

    @XmlElement(name = "MRespuesta", required = true, nillable = true)
    protected String mRespuesta;
    @XmlElement(required = true, nillable = true)
    protected String caracterAcepta;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short codMRespuesta;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer consecutivo;
    @XmlElement(nillable = true)
    protected List<CampoExtendidoDto> llaveRepetitivoSiguienteTrama;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short numeroLineas;
    @XmlElement(required = true, nillable = true)
    protected String respuestaOriginal;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean tieneMasRegistros;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer total;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short ultimoMensaje;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecha;
    @XmlElement(required = true, nillable = true)
    protected String motivo;
    @XmlElement(required = true, nillable = true)
    protected String oficina;
    @XmlElement(required = true, nillable = true)
    protected String referencia;
    @XmlElement(required = true, nillable = true)
    protected String talon;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal valor;

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

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the motivo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Sets the value of the motivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivo(String value) {
        this.motivo = value;
    }

    /**
     * Gets the value of the oficina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOficina() {
        return oficina;
    }

    /**
     * Sets the value of the oficina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOficina(String value) {
        this.oficina = value;
    }

    /**
     * Gets the value of the referencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Sets the value of the referencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia(String value) {
        this.referencia = value;
    }

    /**
     * Gets the value of the talon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTalon() {
        return talon;
    }

    /**
     * Sets the value of the talon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTalon(String value) {
        this.talon = value;
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
