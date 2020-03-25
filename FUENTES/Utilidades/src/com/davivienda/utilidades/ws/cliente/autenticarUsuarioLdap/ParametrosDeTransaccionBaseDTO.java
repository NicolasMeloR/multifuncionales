
package com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parametrosDeTransaccionBaseDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="parametrosDeTransaccionBaseDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="canal" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="host" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="idTarea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificadorOperacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="identificadorSesion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jornada" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="llaveRepetitivaConsulta" type="{http://servicio.autenticacionldap.davivienda.com/}campoExtendidoDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="modoDeOperacion" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="origenLog" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parametrosSeguridad" type="{http://servicio.autenticacionldap.davivienda.com/}parametrosSeguridadDTO" minOccurs="0"/>
 *         &lt;element name="perfil" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="segundaClave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parametrosDeTransaccionBaseDTO", propOrder = {
    "canal",
    "host",
    "idTarea",
    "identificadorOperacion",
    "identificadorSesion",
    "jornada",
    "llaveRepetitivaConsulta",
    "modoDeOperacion",
    "origenLog",
    "parametrosSeguridad",
    "perfil",
    "segundaClave",
    "total",
    "usuario"
})
@XmlSeeAlso({
    AutenticarUsuarioDTO.class
})
public class ParametrosDeTransaccionBaseDTO {

    protected Short canal;
    protected Integer host;
    protected String idTarea;
    protected String identificadorOperacion;
    protected String identificadorSesion;
    protected Short jornada;
    @XmlElement(nillable = true)
    protected List<CampoExtendidoDto> llaveRepetitivaConsulta;
    protected Short modoDeOperacion;
    protected String origenLog;
    protected ParametrosSeguridadDTO parametrosSeguridad;
    protected Short perfil;
    protected String segundaClave;
    protected Integer total;
    protected String usuario;

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

}
