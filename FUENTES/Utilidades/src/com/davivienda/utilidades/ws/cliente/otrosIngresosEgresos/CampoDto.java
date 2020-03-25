
package com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campoDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="campoDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caracterRelleno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esAuditable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="esEncabezado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="esLlaveDeRepetitivo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="esParametro" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="esRepetitivo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="idCampo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idCampoLlaveRepetitiva" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idFormato" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="iniciaEnCeros" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="justificacion" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="longitud" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreAtributoDto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="posicionInicial" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tipoDato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorActual" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorPorDefecto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "campoDto", propOrder = {
    "caracterRelleno",
    "esAuditable",
    "esEncabezado",
    "esLlaveDeRepetitivo",
    "esParametro",
    "esRepetitivo",
    "idCampo",
    "idCampoLlaveRepetitiva",
    "idFormato",
    "iniciaEnCeros",
    "justificacion",
    "longitud",
    "nombre",
    "nombreAtributoDto",
    "posicionInicial",
    "tipoDato",
    "valorActual",
    "valorPorDefecto"
})
@XmlSeeAlso({
    CampoExtendidoDto.class
})
public class CampoDto {

    protected String caracterRelleno;
    protected Boolean esAuditable;
    protected Boolean esEncabezado;
    protected Boolean esLlaveDeRepetitivo;
    protected Boolean esParametro;
    protected Boolean esRepetitivo;
    protected Long idCampo;
    protected Long idCampoLlaveRepetitiva;
    protected Long idFormato;
    protected Boolean iniciaEnCeros;
    protected Short justificacion;
    protected Integer longitud;
    protected String nombre;
    protected String nombreAtributoDto;
    protected Integer posicionInicial;
    protected String tipoDato;
    protected String valorActual;
    protected String valorPorDefecto;

    /**
     * Gets the value of the caracterRelleno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaracterRelleno() {
        return caracterRelleno;
    }

    /**
     * Sets the value of the caracterRelleno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaracterRelleno(String value) {
        this.caracterRelleno = value;
    }

    /**
     * Gets the value of the esAuditable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsAuditable() {
        return esAuditable;
    }

    /**
     * Sets the value of the esAuditable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsAuditable(Boolean value) {
        this.esAuditable = value;
    }

    /**
     * Gets the value of the esEncabezado property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsEncabezado() {
        return esEncabezado;
    }

    /**
     * Sets the value of the esEncabezado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsEncabezado(Boolean value) {
        this.esEncabezado = value;
    }

    /**
     * Gets the value of the esLlaveDeRepetitivo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsLlaveDeRepetitivo() {
        return esLlaveDeRepetitivo;
    }

    /**
     * Sets the value of the esLlaveDeRepetitivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsLlaveDeRepetitivo(Boolean value) {
        this.esLlaveDeRepetitivo = value;
    }

    /**
     * Gets the value of the esParametro property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsParametro() {
        return esParametro;
    }

    /**
     * Sets the value of the esParametro property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsParametro(Boolean value) {
        this.esParametro = value;
    }

    /**
     * Gets the value of the esRepetitivo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsRepetitivo() {
        return esRepetitivo;
    }

    /**
     * Sets the value of the esRepetitivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsRepetitivo(Boolean value) {
        this.esRepetitivo = value;
    }

    /**
     * Gets the value of the idCampo property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdCampo() {
        return idCampo;
    }

    /**
     * Sets the value of the idCampo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdCampo(Long value) {
        this.idCampo = value;
    }

    /**
     * Gets the value of the idCampoLlaveRepetitiva property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdCampoLlaveRepetitiva() {
        return idCampoLlaveRepetitiva;
    }

    /**
     * Sets the value of the idCampoLlaveRepetitiva property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdCampoLlaveRepetitiva(Long value) {
        this.idCampoLlaveRepetitiva = value;
    }

    /**
     * Gets the value of the idFormato property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdFormato() {
        return idFormato;
    }

    /**
     * Sets the value of the idFormato property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdFormato(Long value) {
        this.idFormato = value;
    }

    /**
     * Gets the value of the iniciaEnCeros property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIniciaEnCeros() {
        return iniciaEnCeros;
    }

    /**
     * Sets the value of the iniciaEnCeros property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIniciaEnCeros(Boolean value) {
        this.iniciaEnCeros = value;
    }

    /**
     * Gets the value of the justificacion property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getJustificacion() {
        return justificacion;
    }

    /**
     * Sets the value of the justificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setJustificacion(Short value) {
        this.justificacion = value;
    }

    /**
     * Gets the value of the longitud property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLongitud() {
        return longitud;
    }

    /**
     * Sets the value of the longitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLongitud(Integer value) {
        this.longitud = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the nombreAtributoDto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreAtributoDto() {
        return nombreAtributoDto;
    }

    /**
     * Sets the value of the nombreAtributoDto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreAtributoDto(String value) {
        this.nombreAtributoDto = value;
    }

    /**
     * Gets the value of the posicionInicial property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPosicionInicial() {
        return posicionInicial;
    }

    /**
     * Sets the value of the posicionInicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPosicionInicial(Integer value) {
        this.posicionInicial = value;
    }

    /**
     * Gets the value of the tipoDato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDato() {
        return tipoDato;
    }

    /**
     * Sets the value of the tipoDato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDato(String value) {
        this.tipoDato = value;
    }

    /**
     * Gets the value of the valorActual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValorActual() {
        return valorActual;
    }

    /**
     * Sets the value of the valorActual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValorActual(String value) {
        this.valorActual = value;
    }

    /**
     * Gets the value of the valorPorDefecto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValorPorDefecto() {
        return valorPorDefecto;
    }

    /**
     * Sets the value of the valorPorDefecto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValorPorDefecto(String value) {
        this.valorPorDefecto = value;
    }

}
