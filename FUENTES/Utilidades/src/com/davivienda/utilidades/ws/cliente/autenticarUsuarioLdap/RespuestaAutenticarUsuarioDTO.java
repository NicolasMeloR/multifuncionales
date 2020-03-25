
package com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaAutenticarUsuarioDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaAutenticarUsuarioDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://servicio.autenticacionldap.davivienda.com/}respuestaDeTransaccionBaseDTO">
 *       &lt;sequence>
 *         &lt;element name="gruposUsuario" type="{http://servicio.autenticacionldap.davivienda.com/}grupoDaviviendaDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="respuesta" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaAutenticarUsuarioDTO", propOrder = {
    "gruposUsuario",
    "respuesta"
})
public class RespuestaAutenticarUsuarioDTO
    extends RespuestaDeTransaccionBaseDTO
{

    @XmlElement(nillable = true)
    protected List<GrupoDaviviendaDTO> gruposUsuario;
    protected Boolean respuesta;

    /**
     * Gets the value of the gruposUsuario property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gruposUsuario property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGruposUsuario().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GrupoDaviviendaDTO }
     * 
     * 
     */
    public List<GrupoDaviviendaDTO> getGruposUsuario() {
        if (gruposUsuario == null) {
            gruposUsuario = new ArrayList<GrupoDaviviendaDTO>();
        }
        return this.gruposUsuario;
    }

    /**
     * Gets the value of the respuesta property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRespuesta() {
        return respuesta;
    }

    /**
     * Sets the value of the respuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRespuesta(Boolean value) {
        this.respuesta = value;
    }

}
