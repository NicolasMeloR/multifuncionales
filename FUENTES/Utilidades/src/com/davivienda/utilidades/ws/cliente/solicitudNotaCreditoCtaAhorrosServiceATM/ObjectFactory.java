
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.solicitudNotaCreditoCtaAhorrosServiceATM package. 
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

    private final static QName _SolicitudNotaCreditoCtaAhorros_QNAME = new QName("http://solicitudnotacreditoctaahorrosinterface.servicios.procesadortransacciones.davivienda.com/", "solicitudNotaCreditoCtaAhorros");
    private final static QName _SolicitudNotaCreditoCtaAhorrosResponse_QNAME = new QName("http://solicitudnotacreditoctaahorrosinterface.servicios.procesadortransacciones.davivienda.com/", "solicitudNotaCreditoCtaAhorrosResponse");
    private final static QName _ServicioException_QNAME = new QName("http://solicitudnotacreditoctaahorrosinterface.servicios.procesadortransacciones.davivienda.com/", "ServicioException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.solicitudNotaCreditoCtaAhorrosServiceATM
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
     * Create an instance of {@link SolicitudNotaCreditoCtaAhorrosResponse }
     * 
     */
    public SolicitudNotaCreditoCtaAhorrosResponse createSolicitudNotaCreditoCtaAhorrosResponse() {
        return new SolicitudNotaCreditoCtaAhorrosResponse();
    }

    /**
     * Create an instance of {@link SolicitudNotaCreditoCtaAhorros }
     * 
     */
    public SolicitudNotaCreditoCtaAhorros createSolicitudNotaCreditoCtaAhorros() {
        return new SolicitudNotaCreditoCtaAhorros();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link SolicitudNotaCreditoCtaAhorrosDto }
     * 
     */
    public SolicitudNotaCreditoCtaAhorrosDto createSolicitudNotaCreditoCtaAhorrosDto() {
        return new SolicitudNotaCreditoCtaAhorrosDto();
    }

    /**
     * Create an instance of {@link RespuestaDeTransaccionBaseDTO }
     * 
     */
    public RespuestaDeTransaccionBaseDTO createRespuestaDeTransaccionBaseDTO() {
        return new RespuestaDeTransaccionBaseDTO();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitudNotaCreditoCtaAhorros }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solicitudnotacreditoctaahorrosinterface.servicios.procesadortransacciones.davivienda.com/", name = "solicitudNotaCreditoCtaAhorros")
    public JAXBElement<SolicitudNotaCreditoCtaAhorros> createSolicitudNotaCreditoCtaAhorros(SolicitudNotaCreditoCtaAhorros value) {
        return new JAXBElement<SolicitudNotaCreditoCtaAhorros>(_SolicitudNotaCreditoCtaAhorros_QNAME, SolicitudNotaCreditoCtaAhorros.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitudNotaCreditoCtaAhorrosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solicitudnotacreditoctaahorrosinterface.servicios.procesadortransacciones.davivienda.com/", name = "solicitudNotaCreditoCtaAhorrosResponse")
    public JAXBElement<SolicitudNotaCreditoCtaAhorrosResponse> createSolicitudNotaCreditoCtaAhorrosResponse(SolicitudNotaCreditoCtaAhorrosResponse value) {
        return new JAXBElement<SolicitudNotaCreditoCtaAhorrosResponse>(_SolicitudNotaCreditoCtaAhorrosResponse_QNAME, SolicitudNotaCreditoCtaAhorrosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solicitudnotacreditoctaahorrosinterface.servicios.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

}
