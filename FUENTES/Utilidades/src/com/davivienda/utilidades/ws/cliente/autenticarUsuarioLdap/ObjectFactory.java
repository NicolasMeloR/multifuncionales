
package com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.autenticarUsuarioLdap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AutenticarUsuario_QNAME = new QName("http://servicio.autenticacionldap.davivienda.com/", "autenticarUsuario");
    private final static QName _AutenticarUsuarioResponse_QNAME = new QName("http://servicio.autenticacionldap.davivienda.com/", "autenticarUsuarioResponse");
    private final static QName _ServicioException_QNAME = new QName("http://servicio.autenticacionldap.davivienda.com/", "ServicioException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.autenticarUsuarioLdap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServicioException }
     * 
     */
    public ServicioException createServicioException() {
        return new ServicioException();
    }

    /**
     * Create an instance of {@link AutenticarUsuarioResponse }
     * 
     */
    public AutenticarUsuarioResponse createAutenticarUsuarioResponse() {
        return new AutenticarUsuarioResponse();
    }

    /**
     * Create an instance of {@link AutenticarUsuario }
     * 
     */
    public AutenticarUsuario createAutenticarUsuario() {
        return new AutenticarUsuario();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link GrupoDaviviendaDTO }
     * 
     */
    public GrupoDaviviendaDTO createGrupoDaviviendaDTO() {
        return new GrupoDaviviendaDTO();
    }

    /**
     * Create an instance of {@link RespuestaDeTransaccionBaseDTO }
     * 
     */
    public RespuestaDeTransaccionBaseDTO createRespuestaDeTransaccionBaseDTO() {
        return new RespuestaDeTransaccionBaseDTO();
    }

    /**
     * Create an instance of {@link RespuestaAutenticarUsuarioDTO }
     * 
     */
    public RespuestaAutenticarUsuarioDTO createRespuestaAutenticarUsuarioDTO() {
        return new RespuestaAutenticarUsuarioDTO();
    }

    /**
     * Create an instance of {@link AutenticarUsuarioDTO }
     * 
     */
    public AutenticarUsuarioDTO createAutenticarUsuarioDTO() {
        return new AutenticarUsuarioDTO();
    }

    /**
     * Create an instance of {@link CampoExtendidoDto }
     * 
     */
    public CampoExtendidoDto createCampoExtendidoDto() {
        return new CampoExtendidoDto();
    }

    /**
     * Create an instance of {@link ParametrosDeTransaccionBaseDTO }
     * 
     */
    public ParametrosDeTransaccionBaseDTO createParametrosDeTransaccionBaseDTO() {
        return new ParametrosDeTransaccionBaseDTO();
    }

    /**
     * Create an instance of {@link ParametrosSeguridadDTO }
     * 
     */
    public ParametrosSeguridadDTO createParametrosSeguridadDTO() {
        return new ParametrosSeguridadDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AutenticarUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.autenticacionldap.davivienda.com/", name = "autenticarUsuario")
    public JAXBElement<AutenticarUsuario> createAutenticarUsuario(AutenticarUsuario value) {
        return new JAXBElement<AutenticarUsuario>(_AutenticarUsuario_QNAME, AutenticarUsuario.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AutenticarUsuarioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.autenticacionldap.davivienda.com/", name = "autenticarUsuarioResponse")
    public JAXBElement<AutenticarUsuarioResponse> createAutenticarUsuarioResponse(AutenticarUsuarioResponse value) {
        return new JAXBElement<AutenticarUsuarioResponse>(_AutenticarUsuarioResponse_QNAME, AutenticarUsuarioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.autenticacionldap.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

}
