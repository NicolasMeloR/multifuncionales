
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros;

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
 * <p>Java class for SolicitudNotaDebitoCuentaAhorrosDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolicitudNotaDebitoCuentaAhorrosDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificadorOperacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identificadorSesion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="origenLog" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="canal" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="host" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="jornada" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="llaveRepetitivaConsulta" type="{http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes}CampoExtendidoDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="modoDeOperacion" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="parametrosSeguridad" type="{http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes}ParametrosSeguridadDTO"/>
 *         &lt;element name="perfil" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="segundaClave" type="{http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes}validacionSegundaClave"/>
 *         &lt;element name="total" type="{http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes}validacionTotal"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idTarea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ORIGEN_FONDO_CTA_AHORROS" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="concepto" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="fechaMovimiento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="numeroCuentaAhorros" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="origenFondos" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="referenciaDos" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="referenciaUno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="secuencialRegistro" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
@XmlType(name = "SolicitudNotaDebitoCuentaAhorrosDto", propOrder = {
    "identificadorOperacion",
    "identificadorSesion",
    "origenLog",
    "canal",
    "host",
    "jornada",
    "llaveRepetitivaConsulta",
    "modoDeOperacion",
    "parametrosSeguridad",
    "perfil",
    "segundaClave",
    "total",
    "usuario",
    "idTarea",
    "origenfondoctaahorros",
    "concepto",
    "fechaMovimiento",
    "numeroCuentaAhorros",
    "origenFondos",
    "referenciaDos",
    "referenciaUno",
    "secuencialRegistro",
    "valor"
})
public class SolicitudNotaDebitoCuentaAhorrosDto {

    @XmlElement(required = true, nillable = true)
    protected String identificadorOperacion;
    @XmlElement(required = true, nillable = true)
    protected String identificadorSesion;
    @XmlElement(required = true, nillable = true)
    protected String origenLog;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short canal;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer host;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short jornada;
    @XmlElement(nillable = true)
    protected List<CampoExtendidoDto> llaveRepetitivaConsulta;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short modoDeOperacion;
    @XmlElement(required = true, nillable = true)
    protected ParametrosSeguridadDTO parametrosSeguridad;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short perfil;
    @XmlElement(required = true, nillable = true)
    protected String segundaClave;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer total;
    @XmlElement(required = true, nillable = true)
    protected String usuario;
    @XmlElement(required = true, nillable = true)
    protected String idTarea;
    @XmlElement(name = "ORIGEN_FONDO_CTA_AHORROS", required = true, type = Short.class, nillable = true)
    protected Short origenfondoctaahorros;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short concepto;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaMovimiento;
    @XmlElement(required = true, nillable = true)
    protected String numeroCuentaAhorros;
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short origenFondos;
    @XmlElement(required = true, nillable = true)
    protected String referenciaDos;
    @XmlElement(required = true, nillable = true)
    protected String referenciaUno;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer secuencialRegistro;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal valor;

    /**
     * Gets the value of the identificadorOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificadorOperacion() {
        return identificadorOperacion;
    }

    /**
     * Sets the value of the identificadorOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificadorOperacion(String value) {
        this.identificadorOperacion = value;
    }

    /**
     * Gets the value of the identificadorSesion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificadorSesion() {
        return identificadorSesion;
    }

    /**
     * Sets the value of the identificadorSesion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificadorSesion(String value) {
        this.identificadorSesion = value;
    }

    /**
     * Gets the value of the origenLog property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigenLog() {
        return origenLog;
    }

    /**
     * Sets the value of the origenLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigenLog(String value) {
        this.origenLog = value;
    }

    /**
     * Gets the value of the canal property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCanal() {
        return canal;
    }

    /**
     * Sets the value of the canal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCanal(Short value) {
        this.canal = value;
    }

    /**
     * Gets the value of the host property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHost(Integer value) {
        this.host = value;
    }

    /**
     * Gets the value of the jornada property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getJornada() {
        return jornada;
    }

    /**
     * Sets the value of the jornada property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setJornada(Short value) {
        this.jornada = value;
    }

    /**
     * Gets the value of the llaveRepetitivaConsulta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the llaveRepetitivaConsulta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLlaveRepetitivaConsulta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampoExtendidoDto }
     * 
     * 
     */
    public List<CampoExtendidoDto> getLlaveRepetitivaConsulta() {
        if (llaveRepetitivaConsulta == null) {
            llaveRepetitivaConsulta = new ArrayList<CampoExtendidoDto>();
        }
        return this.llaveRepetitivaConsulta;
    }

    /**
     * Gets the value of the modoDeOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getModoDeOperacion() {
        return modoDeOperacion;
    }

    /**
     * Sets the value of the modoDeOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setModoDeOperacion(Short value) {
        this.modoDeOperacion = value;
    }

    /**
     * Gets the value of the parametrosSeguridad property.
     * 
     * @return
     *     possible object is
     *     {@link ParametrosSeguridadDTO }
     *     
     */
    public ParametrosSeguridadDTO getParametrosSeguridad() {
        return parametrosSeguridad;
    }

    /**
     * Sets the value of the parametrosSeguridad property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametrosSeguridadDTO }
     *     
     */
    public void setParametrosSeguridad(ParametrosSeguridadDTO value) {
        this.parametrosSeguridad = value;
    }

    /**
     * Gets the value of the perfil property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getPerfil() {
        return perfil;
    }

    /**
     * Sets the value of the perfil property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setPerfil(Short value) {
        this.perfil = value;
    }

    /**
     * Gets the value of the segundaClave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegundaClave() {
        return segundaClave;
    }

    /**
     * Sets the value of the segundaClave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegundaClave(String value) {
        this.segundaClave = value;
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
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuario(String value) {
        this.usuario = value;
    }

    /**
     * Gets the value of the idTarea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTarea() {
        return idTarea;
    }

    /**
     * Sets the value of the idTarea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTarea(String value) {
        this.idTarea = value;
    }

    /**
     * Gets the value of the origenfondoctaahorros property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getORIGENFONDOCTAAHORROS() {
        return origenfondoctaahorros;
    }

    /**
     * Sets the value of the origenfondoctaahorros property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setORIGENFONDOCTAAHORROS(Short value) {
        this.origenfondoctaahorros = value;
    }

    /**
     * Gets the value of the concepto property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getConcepto() {
        return concepto;
    }

    /**
     * Sets the value of the concepto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setConcepto(Short value) {
        this.concepto = value;
    }

    /**
     * Gets the value of the fechaMovimiento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaMovimiento() {
        return fechaMovimiento;
    }

    /**
     * Sets the value of the fechaMovimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaMovimiento(XMLGregorianCalendar value) {
        this.fechaMovimiento = value;
    }

    /**
     * Gets the value of the numeroCuentaAhorros property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCuentaAhorros() {
        return numeroCuentaAhorros;
    }

    /**
     * Sets the value of the numeroCuentaAhorros property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCuentaAhorros(String value) {
        this.numeroCuentaAhorros = value;
    }

    /**
     * Gets the value of the origenFondos property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getOrigenFondos() {
        return origenFondos;
    }

    /**
     * Sets the value of the origenFondos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setOrigenFondos(Short value) {
        this.origenFondos = value;
    }

    /**
     * Gets the value of the referenciaDos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenciaDos() {
        return referenciaDos;
    }

    /**
     * Sets the value of the referenciaDos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenciaDos(String value) {
        this.referenciaDos = value;
    }

    /**
     * Gets the value of the referenciaUno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenciaUno() {
        return referenciaUno;
    }

    /**
     * Sets the value of the referenciaUno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenciaUno(String value) {
        this.referenciaUno = value;
    }

    /**
     * Gets the value of the secuencialRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSecuencialRegistro() {
        return secuencialRegistro;
    }

    /**
     * Sets the value of the secuencialRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSecuencialRegistro(Integer value) {
        this.secuencialRegistro = value;
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
