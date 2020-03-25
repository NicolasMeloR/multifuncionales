
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.solicitudNotaCreditoCtaCorrienteServiceATM package. 
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

    private final static QName _SolicitudNotaCreditoCtaCorrienteResponse_QNAME = new QName("http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", "solicitudNotaCreditoCtaCorrienteResponse");
    private final static QName _SolicitudNotaCreditoCtaCorriente_QNAME = new QName("http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", "solicitudNotaCreditoCtaCorriente");
    private final static QName _ServicioException_QNAME = new QName("http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", "ServicioException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.solicitudNotaCreditoCtaCorrienteServiceATM
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
     * Create an instance of {@link SolicitudNotaCreditoCtaCorriente }
     * 
     */
    public SolicitudNotaCreditoCtaCorriente createSolicitudNotaCreditoCtaCorriente() {
        return new SolicitudNotaCreditoCtaCorriente();
    }

    /**
     * Create an instance of {@link SolicitudNotaCreditoCtaCorrienteResponse }
     * 
     */
    public SolicitudNotaCreditoCtaCorrienteResponse createSolicitudNotaCreditoCtaCorrienteResponse() {
        return new SolicitudNotaCreditoCtaCorrienteResponse();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link RespuestaDeTransaccionBaseDTO }
     * 
     */
    public RespuestaDeTransaccionBaseDTO createRespuestaDeTransaccionBaseDTO() {
        return new RespuestaDeTransaccionBaseDTO();
    }

    /**
     * Create an instance of {@link RespuestaNotaCreditoCtaCorrienteDto }
     * 
     */
    public RespuestaNotaCreditoCtaCorrienteDto createRespuestaNotaCreditoCtaCorrienteDto() {
        return new RespuestaNotaCreditoCtaCorrienteDto();
    }

    /**
     * Create an instance of {@link SolicitudNotaCreditoCtaCorrienteDto }
     * 
     */
    public SolicitudNotaCreditoCtaCorrienteDto createSolicitudNotaCreditoCtaCorrienteDto() {
        return new SolicitudNotaCreditoCtaCorrienteDto();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitudNotaCreditoCtaCorrienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", name = "solicitudNotaCreditoCtaCorrienteResponse")
    public JAXBElement<SolicitudNotaCreditoCtaCorrienteResponse> createSolicitudNotaCreditoCtaCorrienteResponse(SolicitudNotaCreditoCtaCorrienteResponse value) {
        return new JAXBElement<SolicitudNotaCreditoCtaCorrienteResponse>(_SolicitudNotaCreditoCtaCorrienteResponse_QNAME, SolicitudNotaCreditoCtaCorrienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitudNotaCreditoCtaCorriente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", name = "solicitudNotaCreditoCtaCorriente")
    public JAXBElement<SolicitudNotaCreditoCtaCorriente> createSolicitudNotaCreditoCtaCorriente(SolicitudNotaCreditoCtaCorriente value) {
        return new JAXBElement<SolicitudNotaCreditoCtaCorriente>(_SolicitudNotaCreditoCtaCorriente_QNAME, SolicitudNotaCreditoCtaCorriente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solicitudnotacreditoctacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

}
