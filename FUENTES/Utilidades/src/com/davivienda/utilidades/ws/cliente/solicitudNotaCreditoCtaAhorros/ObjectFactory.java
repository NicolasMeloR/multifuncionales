
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.solicitudNotaCreditoCtaAhorros package. 
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

    private final static QName _GenerarNotaCreditoCtaAhorros_QNAME = new QName("http://servicios.davivienda.com/solicitudNotaCreditoCtaAhorrosServiceTypes", "generarNotaCreditoCtaAhorros");
    private final static QName _ServicioException_QNAME = new QName("http://servicios.davivienda.com/solicitudNotaCreditoCtaAhorrosServiceTypes", "ServicioException");
    private final static QName _GenerarNotaCreditoCtaAhorrosResponse_QNAME = new QName("http://servicios.davivienda.com/solicitudNotaCreditoCtaAhorrosServiceTypes", "generarNotaCreditoCtaAhorrosResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.solicitudNotaCreditoCtaAhorros
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GenerarNotaCreditoCtaAhorros }
     * 
     */
    public GenerarNotaCreditoCtaAhorros createGenerarNotaCreditoCtaAhorros() {
        return new GenerarNotaCreditoCtaAhorros();
    }

    /**
     * Create an instance of {@link ServicioException }
     * 
     */
    public ServicioException createServicioException() {
        return new ServicioException();
    }

    /**
     * Create an instance of {@link GenerarNotaCreditoCtaAhorrosResponse }
     * 
     */
    public GenerarNotaCreditoCtaAhorrosResponse createGenerarNotaCreditoCtaAhorrosResponse() {
        return new GenerarNotaCreditoCtaAhorrosResponse();
    }

    /**
     * Create an instance of {@link RespuestaNotaCreditoCtaAhorrosDto }
     * 
     */
    public RespuestaNotaCreditoCtaAhorrosDto createRespuestaNotaCreditoCtaAhorrosDto() {
        return new RespuestaNotaCreditoCtaAhorrosDto();
    }

    /**
     * Create an instance of {@link CampoExtendidoDto }
     * 
     */
    public CampoExtendidoDto createCampoExtendidoDto() {
        return new CampoExtendidoDto();
    }

    /**
     * Create an instance of {@link SolicitudNotaCreditoCtaAhorrosDto }
     * 
     */
    public SolicitudNotaCreditoCtaAhorrosDto createSolicitudNotaCreditoCtaAhorrosDto() {
        return new SolicitudNotaCreditoCtaAhorrosDto();
    }

    /**
     * Create an instance of {@link ParametrosSeguridadDTO }
     * 
     */
    public ParametrosSeguridadDTO createParametrosSeguridadDTO() {
        return new ParametrosSeguridadDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerarNotaCreditoCtaAhorros }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitudNotaCreditoCtaAhorrosServiceTypes", name = "generarNotaCreditoCtaAhorros")
    public JAXBElement<GenerarNotaCreditoCtaAhorros> createGenerarNotaCreditoCtaAhorros(GenerarNotaCreditoCtaAhorros value) {
        return new JAXBElement<GenerarNotaCreditoCtaAhorros>(_GenerarNotaCreditoCtaAhorros_QNAME, GenerarNotaCreditoCtaAhorros.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitudNotaCreditoCtaAhorrosServiceTypes", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerarNotaCreditoCtaAhorrosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitudNotaCreditoCtaAhorrosServiceTypes", name = "generarNotaCreditoCtaAhorrosResponse")
    public JAXBElement<GenerarNotaCreditoCtaAhorrosResponse> createGenerarNotaCreditoCtaAhorrosResponse(GenerarNotaCreditoCtaAhorrosResponse value) {
        return new JAXBElement<GenerarNotaCreditoCtaAhorrosResponse>(_GenerarNotaCreditoCtaAhorrosResponse_QNAME, GenerarNotaCreditoCtaAhorrosResponse.class, null, value);
    }

}
