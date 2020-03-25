
package com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.solicitudNotaCreditoCtaCorriente package. 
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

    private final static QName _GenerarNotaCreditoCtaCorrienteResponse_QNAME = new QName("http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes", "generarNotaCreditoCtaCorrienteResponse");
    private final static QName _ServicioException_QNAME = new QName("http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes", "ServicioException");
    private final static QName _GenerarNotaCreditoCtaCorriente_QNAME = new QName("http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes", "generarNotaCreditoCtaCorriente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.solicitudNotaCreditoCtaCorriente
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
     * Create an instance of {@link GenerarNotaCreditoCtaCorrienteResponse }
     * 
     */
    public GenerarNotaCreditoCtaCorrienteResponse createGenerarNotaCreditoCtaCorrienteResponse() {
        return new GenerarNotaCreditoCtaCorrienteResponse();
    }

    /**
     * Create an instance of {@link GenerarNotaCreditoCtaCorriente }
     * 
     */
    public GenerarNotaCreditoCtaCorriente createGenerarNotaCreditoCtaCorriente() {
        return new GenerarNotaCreditoCtaCorriente();
    }

    /**
     * Create an instance of {@link RespuestaNotaCreditoCtaCorrienteDto }
     * 
     */
    public RespuestaNotaCreditoCtaCorrienteDto createRespuestaNotaCreditoCtaCorrienteDto() {
        return new RespuestaNotaCreditoCtaCorrienteDto();
    }

    /**
     * Create an instance of {@link CampoExtendidoDto }
     * 
     */
    public CampoExtendidoDto createCampoExtendidoDto() {
        return new CampoExtendidoDto();
    }

    /**
     * Create an instance of {@link SolicitudNotaCreditoCtaCorrienteDto }
     * 
     */
    public SolicitudNotaCreditoCtaCorrienteDto createSolicitudNotaCreditoCtaCorrienteDto() {
        return new SolicitudNotaCreditoCtaCorrienteDto();
    }

    /**
     * Create an instance of {@link ParametrosSeguridadDTO }
     * 
     */
    public ParametrosSeguridadDTO createParametrosSeguridadDTO() {
        return new ParametrosSeguridadDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerarNotaCreditoCtaCorrienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes", name = "generarNotaCreditoCtaCorrienteResponse")
    public JAXBElement<GenerarNotaCreditoCtaCorrienteResponse> createGenerarNotaCreditoCtaCorrienteResponse(GenerarNotaCreditoCtaCorrienteResponse value) {
        return new JAXBElement<GenerarNotaCreditoCtaCorrienteResponse>(_GenerarNotaCreditoCtaCorrienteResponse_QNAME, GenerarNotaCreditoCtaCorrienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerarNotaCreditoCtaCorriente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitudNotaCreditoCtaCorrienteServiceTypes", name = "generarNotaCreditoCtaCorriente")
    public JAXBElement<GenerarNotaCreditoCtaCorriente> createGenerarNotaCreditoCtaCorriente(GenerarNotaCreditoCtaCorriente value) {
        return new JAXBElement<GenerarNotaCreditoCtaCorriente>(_GenerarNotaCreditoCtaCorriente_QNAME, GenerarNotaCreditoCtaCorriente.class, null, value);
    }

}
