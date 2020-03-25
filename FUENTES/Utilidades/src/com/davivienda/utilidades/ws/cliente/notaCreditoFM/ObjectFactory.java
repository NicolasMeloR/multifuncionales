
package com.davivienda.utilidades.ws.cliente.notaCreditoFM;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.notaCreditoFM package. 
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

    private final static QName _ServicioException_QNAME = new QName("http://notacreditofminterface.procesadortransacciones.davivienda.com/", "ServicioException");
    private final static QName _GenerarNotaCreditoFMResponse_QNAME = new QName("http://notacreditofminterface.procesadortransacciones.davivienda.com/", "generarNotaCreditoFMResponse");
    private final static QName _GenerarNotaCreditoFM_QNAME = new QName("http://notacreditofminterface.procesadortransacciones.davivienda.com/", "generarNotaCreditoFM");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.notaCreditoFM
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
     * Create an instance of {@link GenerarNotaCreditoFM }
     * 
     */
    public GenerarNotaCreditoFM createGenerarNotaCreditoFM() {
        return new GenerarNotaCreditoFM();
    }

    /**
     * Create an instance of {@link GenerarNotaCreditoFMResponse }
     * 
     */
    public GenerarNotaCreditoFMResponse createGenerarNotaCreditoFMResponse() {
        return new GenerarNotaCreditoFMResponse();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link RespuestaNotaCreditoFMDto }
     * 
     */
    public RespuestaNotaCreditoFMDto createRespuestaNotaCreditoFMDto() {
        return new RespuestaNotaCreditoFMDto();
    }

    /**
     * Create an instance of {@link RespuestaDeTransaccionBaseDTO }
     * 
     */
    public RespuestaDeTransaccionBaseDTO createRespuestaDeTransaccionBaseDTO() {
        return new RespuestaDeTransaccionBaseDTO();
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
     * Create an instance of {@link NotaCreditoFMDto }
     * 
     */
    public NotaCreditoFMDto createNotaCreditoFMDto() {
        return new NotaCreditoFMDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://notacreditofminterface.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerarNotaCreditoFMResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://notacreditofminterface.procesadortransacciones.davivienda.com/", name = "generarNotaCreditoFMResponse")
    public JAXBElement<GenerarNotaCreditoFMResponse> createGenerarNotaCreditoFMResponse(GenerarNotaCreditoFMResponse value) {
        return new JAXBElement<GenerarNotaCreditoFMResponse>(_GenerarNotaCreditoFMResponse_QNAME, GenerarNotaCreditoFMResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerarNotaCreditoFM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://notacreditofminterface.procesadortransacciones.davivienda.com/", name = "generarNotaCreditoFM")
    public JAXBElement<GenerarNotaCreditoFM> createGenerarNotaCreditoFM(GenerarNotaCreditoFM value) {
        return new JAXBElement<GenerarNotaCreditoFM>(_GenerarNotaCreditoFM_QNAME, GenerarNotaCreditoFM.class, null, value);
    }

}
