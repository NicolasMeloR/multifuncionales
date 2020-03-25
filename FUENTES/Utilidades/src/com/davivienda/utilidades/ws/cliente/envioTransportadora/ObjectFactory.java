
package com.davivienda.utilidades.ws.cliente.envioTransportadora;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.envioTransportadora package. 
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

    private final static QName _EnvioTransportadoraResponse_QNAME = new QName("http://enviotransportadorainterface.servicios.procesadortransacciones.davivienda.com/", "envioTransportadoraResponse");
    private final static QName _ServicioException_QNAME = new QName("http://enviotransportadorainterface.servicios.procesadortransacciones.davivienda.com/", "ServicioException");
    private final static QName _EnvioTransportadora_QNAME = new QName("http://enviotransportadorainterface.servicios.procesadortransacciones.davivienda.com/", "envioTransportadora");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.envioTransportadora
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
     * Create an instance of {@link EnvioTransportadoraResponse }
     * 
     */
    public EnvioTransportadoraResponse createEnvioTransportadoraResponse() {
        return new EnvioTransportadoraResponse();
    }

    /**
     * Create an instance of {@link EnvioTransportadora }
     * 
     */
    public EnvioTransportadora createEnvioTransportadora() {
        return new EnvioTransportadora();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link EnvioTransportadoraDto }
     * 
     */
    public EnvioTransportadoraDto createEnvioTransportadoraDto() {
        return new EnvioTransportadoraDto();
    }

    /**
     * Create an instance of {@link RepetitivoDisminucionProvisionDto }
     * 
     */
    public RepetitivoDisminucionProvisionDto createRepetitivoDisminucionProvisionDto() {
        return new RepetitivoDisminucionProvisionDto();
    }

    /**
     * Create an instance of {@link RespuestaDeTransaccionBaseDTO }
     * 
     */
    public RespuestaDeTransaccionBaseDTO createRespuestaDeTransaccionBaseDTO() {
        return new RespuestaDeTransaccionBaseDTO();
    }

    /**
     * Create an instance of {@link RespuestaDisminucionProvisionDto }
     * 
     */
    public RespuestaDisminucionProvisionDto createRespuestaDisminucionProvisionDto() {
        return new RespuestaDisminucionProvisionDto();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link EnvioTransportadoraResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enviotransportadorainterface.servicios.procesadortransacciones.davivienda.com/", name = "envioTransportadoraResponse")
    public JAXBElement<EnvioTransportadoraResponse> createEnvioTransportadoraResponse(EnvioTransportadoraResponse value) {
        return new JAXBElement<EnvioTransportadoraResponse>(_EnvioTransportadoraResponse_QNAME, EnvioTransportadoraResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enviotransportadorainterface.servicios.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnvioTransportadora }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://enviotransportadorainterface.servicios.procesadortransacciones.davivienda.com/", name = "envioTransportadora")
    public JAXBElement<EnvioTransportadora> createEnvioTransportadora(EnvioTransportadora value) {
        return new JAXBElement<EnvioTransportadora>(_EnvioTransportadora_QNAME, EnvioTransportadora.class, null, value);
    }

}
