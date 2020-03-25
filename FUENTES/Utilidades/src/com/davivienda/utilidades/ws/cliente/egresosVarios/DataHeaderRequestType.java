
package com.davivienda.utilidades.ws.cliente.egresosVarios;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataHeaderRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataHeaderRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombreOperacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="total" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="jornada" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="canal" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="modoDeOperacion" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="perfil" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="versionServicio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataHeaderRequestType", propOrder = {
    "nombreOperacion",
    "total",
    "jornada",
    "canal",
    "modoDeOperacion",
    "usuario",
    "perfil",
    "versionServicio"
})
public class DataHeaderRequestType {

    @XmlElement(required = true, defaultValue = "EgresosVarios")
    protected String nombreOperacion;
    protected int total;
    protected short jornada;
    protected short canal;
    protected short modoDeOperacion;
    @XmlElement(required = true)
    protected String usuario;
    protected short perfil;
    @XmlElement(required = true, defaultValue = "1.1.0")
    protected String versionServicio;

    /**
     * Gets the value of the nombreOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreOperacion() {
        return nombreOperacion;
    }

    /**
     * Sets the value of the nombreOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreOperacion(String value) {
        this.nombreOperacion = value;
    }

    /**
     * Gets the value of the total property.
     * 
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     */
    public void setTotal(int value) {
        this.total = value;
    }

    /**
     * Gets the value of the jornada property.
     * 
     */
    public short getJornada() {
        return jornada;
    }

    /**
     * Sets the value of the jornada property.
     * 
     */
    public void setJornada(short value) {
        this.jornada = value;
    }

    /**
     * Gets the value of the canal property.
     * 
     */
    public short getCanal() {
        return canal;
    }

    /**
     * Sets the value of the canal property.
     * 
     */
    public void setCanal(short value) {
        this.canal = value;
    }

    /**
     * Gets the value of the modoDeOperacion property.
     * 
     */
    public short getModoDeOperacion() {
        return modoDeOperacion;
    }

    /**
     * Sets the value of the modoDeOperacion property.
     * 
     */
    public void setModoDeOperacion(short value) {
        this.modoDeOperacion = value;
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
     * Gets the value of the perfil property.
     * 
     */
    public short getPerfil() {
        return perfil;
    }

    /**
     * Sets the value of the perfil property.
     * 
     */
    public void setPerfil(short value) {
        this.perfil = value;
    }

    /**
     * Gets the value of the versionServicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersionServicio() {
        return versionServicio;
    }

    /**
     * Sets the value of the versionServicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionServicio(String value) {
        this.versionServicio = value;
    }

}
