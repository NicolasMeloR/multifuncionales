
package com.davivienda.utilidades.ws.cliente.informeTotalesATM;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.informeTotalesATM package. 
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

    private final static QName _InformeTotalesATM_QNAME = new QName("http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/", "informeTotalesATM");
    private final static QName _ServicioException_QNAME = new QName("http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/", "ServicioException");
    private final static QName _InformeTotalesATMResponse_QNAME = new QName("http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/", "informeTotalesATMResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.informeTotalesATM
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
     * Create an instance of {@link InformeTotalesATM_Type }
     * 
     */
    public InformeTotalesATM_Type createInformeTotalesATM_Type() {
        return new InformeTotalesATM_Type();
    }

    /**
     * Create an instance of {@link InformeTotalesATMResponse }
     * 
     */
    public InformeTotalesATMResponse createInformeTotalesATMResponse() {
        return new InformeTotalesATMResponse();
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
     * Create an instance of {@link RespuestaInformeTotalesRepATMDto }
     * 
     */
    public RespuestaInformeTotalesRepATMDto createRespuestaInformeTotalesRepATMDto() {
        return new RespuestaInformeTotalesRepATMDto();
    }

    /**
     * Create an instance of {@link InformeTotalesATMDto }
     * 
     */
    public InformeTotalesATMDto createInformeTotalesATMDto() {
        return new InformeTotalesATMDto();
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
     * Create an instance of {@link RespuestaInformeTotalesATMDto }
     * 
     */
    public RespuestaInformeTotalesATMDto createRespuestaInformeTotalesATMDto() {
        return new RespuestaInformeTotalesATMDto();
    }

    /**
     * Create an instance of {@link ParametrosSeguridadDTO }
     * 
     */
    public ParametrosSeguridadDTO createParametrosSeguridadDTO() {
        return new ParametrosSeguridadDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformeTotalesATM_Type }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/", name = "informeTotalesATM")
    public JAXBElement<InformeTotalesATM_Type> createInformeTotalesATM(InformeTotalesATM_Type value) {
        return new JAXBElement<InformeTotalesATM_Type>(_InformeTotalesATM_QNAME, InformeTotalesATM_Type.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformeTotalesATMResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://InformeTotalesATM.servicios.impl.procesadortransacciones.davivienda.com/", name = "informeTotalesATMResponse")
    public JAXBElement<InformeTotalesATMResponse> createInformeTotalesATMResponse(InformeTotalesATMResponse value) {
        return new JAXBElement<InformeTotalesATMResponse>(_InformeTotalesATMResponse_QNAME, InformeTotalesATMResponse.class, null, value);
    }

}
