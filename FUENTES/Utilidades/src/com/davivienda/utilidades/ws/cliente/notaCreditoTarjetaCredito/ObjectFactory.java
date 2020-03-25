
package com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.notaCreditoTarjetaCredito package. 
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

    private final static QName _GenerarNotaCreditoTarjetaCreditoResponse_QNAME = new QName("http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/", "generarNotaCreditoTarjetaCreditoResponse");
    private final static QName _GenerarNotaCreditoTarjetaCredito_QNAME = new QName("http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/", "generarNotaCreditoTarjetaCredito");
    private final static QName _ServicioException_QNAME = new QName("http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/", "ServicioException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.notaCreditoTarjetaCredito
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
     * Create an instance of {@link GenerarNotaCreditoTarjetaCredito }
     * 
     */
    public GenerarNotaCreditoTarjetaCredito createGenerarNotaCreditoTarjetaCredito() {
        return new GenerarNotaCreditoTarjetaCredito();
    }

    /**
     * Create an instance of {@link GenerarNotaCreditoTarjetaCreditoResponse }
     * 
     */
    public GenerarNotaCreditoTarjetaCreditoResponse createGenerarNotaCreditoTarjetaCreditoResponse() {
        return new GenerarNotaCreditoTarjetaCreditoResponse();
    }

    /**
     * Create an instance of {@link RespuestaNotaCreditoTarjetaCreditoDto }
     * 
     */
    public RespuestaNotaCreditoTarjetaCreditoDto createRespuestaNotaCreditoTarjetaCreditoDto() {
        return new RespuestaNotaCreditoTarjetaCreditoDto();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link NotaCreditoTarjetaCreditoDto }
     * 
     */
    public NotaCreditoTarjetaCreditoDto createNotaCreditoTarjetaCreditoDto() {
        return new NotaCreditoTarjetaCreditoDto();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerarNotaCreditoTarjetaCreditoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/", name = "generarNotaCreditoTarjetaCreditoResponse")
    public JAXBElement<GenerarNotaCreditoTarjetaCreditoResponse> createGenerarNotaCreditoTarjetaCreditoResponse(GenerarNotaCreditoTarjetaCreditoResponse value) {
        return new JAXBElement<GenerarNotaCreditoTarjetaCreditoResponse>(_GenerarNotaCreditoTarjetaCreditoResponse_QNAME, GenerarNotaCreditoTarjetaCreditoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerarNotaCreditoTarjetaCredito }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/", name = "generarNotaCreditoTarjetaCredito")
    public JAXBElement<GenerarNotaCreditoTarjetaCredito> createGenerarNotaCreditoTarjetaCredito(GenerarNotaCreditoTarjetaCredito value) {
        return new JAXBElement<GenerarNotaCreditoTarjetaCredito>(_GenerarNotaCreditoTarjetaCredito_QNAME, GenerarNotaCreditoTarjetaCredito.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://notacreditotarjetacreditointerface.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

}
