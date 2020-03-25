
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
 * <p>Java class for SolicitudNotaCreditoCtaCorrienteDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolicitudNotaCreditoCtaCorrienteDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificadorOperacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identificadorSesion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="origenLog" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="canal" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="host" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idTarea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="jornada" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="llaveRepetitivaConsulta" type="{http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes}CampoExtendidoDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="modoDeOperacion" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="parametrosSeguridad" type="{http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes}ParametrosSeguridadDTO"/>
 *         &lt;element name="perfil" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="segundaClave" type="{http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes}validacionSegundaClave"/>
 *         &lt;element name="total" type="{http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes}validacionTotal"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codigoBanco" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="concepto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cuentaChequera" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaAplicacion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="motivoDevoluncionCheque" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroCuentaCheque" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oficinaRecaudo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="origenFondos" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="referencia1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="talon" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valorNotaCredito" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolicitudNotaCreditoCtaCorrienteDto", propOrder = {
    "identificadorOperacion",
    "identificadorSesion",
    "origenLog",
    "canal",
    "host",
    "idTarea",
    "jornada",
    "llaveRepetitivaConsulta",
    "modoDeOperacion",
    "parametrosSeguridad",
    "perfil",
    "segundaClave",
    "total",
    "usuario",
    "codigoBanco",
    "concepto",
    "cuentaChequera",
    "fechaAplicacion",
    "motivoDevoluncionCheque",
    "numeroCuentaCheque",
    "oficinaRecaudo",
    "origenFondos",
    "referencia1",
    "talon",
    "valorNotaCredito"
})
public class SolicitudNotaCreditoCtaCorrienteDto {

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
    @XmlElement(required = true, nillable = true)
    protected String idTarea;
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
    @XmlElement(required = true, type = Short.class, nillable = true)
    protected Short codigoBanco;
    @XmlElement(required = true, nillable = true)
    protected String concepto;
    @XmlElement(required = true, nillable = true)
    protected String cuentaChequera;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaAplicacion;
    @XmlElement(required = true, nillable = true)
    protected String motivoDevoluncionCheque;
    @XmlElement(required = true, nillable = true)
    protected String numeroCuentaCheque;
    @XmlElement(required = true, nillable = true)
    protected String oficinaRecaudo;
    @XmlElement(required = true, nillable = true)
    protected String origenFondos;
    @XmlElement(required = true, nillable = true)
    protected String referencia1;
    @XmlElement(required = true, nillable = true)
    protected String talon;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal valorNotaCredito;

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
     * Gets the value of the codigoBanco property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCodigoBanco() {
        return codigoBanco;
    }

    /**
     * Sets the value of the codigoBanco property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCodigoBanco(Short value) {
        this.codigoBanco = value;
    }

    /**
     * Gets the value of the concepto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Sets the value of the concepto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConcepto(String value) {
        this.concepto = value;
    }

    /**
     * Gets the value of the cuentaChequera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaChequera() {
        return cuentaChequera;
    }

    /**
     * Sets the value of the cuentaChequera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaChequera(String value) {
        this.cuentaChequera = value;
    }

    /**
     * Gets the value of the fechaAplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaAplicacion() {
        return fechaAplicacion;
    }

    /**
     * Sets the value of the fechaAplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaAplicacion(XMLGregorianCalendar value) {
        this.fechaAplicacion = value;
    }

    /**
     * Gets the value of the motivoDevoluncionCheque property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoDevoluncionCheque() {
        return motivoDevoluncionCheque;
    }

    /**
     * Sets the value of the motivoDevoluncionCheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoDevoluncionCheque(String value) {
        this.motivoDevoluncionCheque = value;
    }

    /**
     * Gets the value of the numeroCuentaCheque property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroCuentaCheque() {
        return numeroCuentaCheque;
    }

    /**
     * Sets the value of the numeroCuentaCheque property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroCuentaCheque(String value) {
        this.numeroCuentaCheque = value;
    }

    /**
     * Gets the value of the oficinaRecaudo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOficinaRecaudo() {
        return oficinaRecaudo;
    }

    /**
     * Sets the value of the oficinaRecaudo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOficinaRecaudo(String value) {
        this.oficinaRecaudo = value;
    }

    /**
     * Gets the value of the origenFondos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigenFondos() {
        return origenFondos;
    }

    /**
     * Sets the value of the origenFondos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigenFondos(String value) {
        this.origenFondos = value;
    }

    /**
     * Gets the value of the referencia1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferencia1() {
        return referencia1;
    }

    /**
     * Sets the value of the referencia1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencia1(String value) {
        this.referencia1 = value;
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
     * Gets the value of the valorNotaCredito property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValorNotaCredito() {
        return valorNotaCredito;
    }

    /**
     * Sets the value of the valorNotaCredito property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValorNotaCredito(BigDecimal value) {
        this.valorNotaCredito = value;
    }

}
