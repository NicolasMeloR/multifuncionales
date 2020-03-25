
package com.davivienda.utilidades.ws.cliente.resumenIDOATM;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.resumenIDOATM package. 
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

    private final static QName _ServicioException_QNAME = new QName("http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/", "ServicioException");
    private final static QName _ResumenIDOATMResponse_QNAME = new QName("http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/", "resumenIDOATMResponse");
    private final static QName _ResumenIDOATM_QNAME = new QName("http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/", "resumenIDOATM");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.resumenIDOATM
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResumenIDOATMResponse }
     * 
     */
    public ResumenIDOATMResponse createResumenIDOATMResponse() {
        return new ResumenIDOATMResponse();
    }

    /**
     * Create an instance of {@link ServicioException }
     * 
     */
    public ServicioException createServicioException() {
        return new ServicioException();
    }

    /**
     * Create an instance of {@link ResumenIDOATM_Type }
     * 
     */
    public ResumenIDOATM_Type createResumenIDOATM_Type() {
        return new ResumenIDOATM_Type();
    }

    /**
     * Create an instance of {@link ResumenIDOATMDto }
     * 
     */
    public ResumenIDOATMDto createResumenIDOATMDto() {
        return new ResumenIDOATMDto();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link RespuestaResumenIDOATMRepDto }
     * 
     */
    public RespuestaResumenIDOATMRepDto createRespuestaResumenIDOATMRepDto() {
        return new RespuestaResumenIDOATMRepDto();
    }

    /**
     * Create an instance of {@link RespuestaResumenIDOATMDto }
     * 
     */
    public RespuestaResumenIDOATMDto createRespuestaResumenIDOATMDto() {
        return new RespuestaResumenIDOATMDto();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResumenIDOATMResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/", name = "resumenIDOATMResponse")
    public JAXBElement<ResumenIDOATMResponse> createResumenIDOATMResponse(ResumenIDOATMResponse value) {
        return new JAXBElement<ResumenIDOATMResponse>(_ResumenIDOATMResponse_QNAME, ResumenIDOATMResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResumenIDOATM_Type }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ResumenIDOATM.servicios.impl.procesadortransacciones.davivienda.com/", name = "resumenIDOATM")
    public JAXBElement<ResumenIDOATM_Type> createResumenIDOATM(ResumenIDOATM_Type value) {
        return new JAXBElement<ResumenIDOATM_Type>(_ResumenIDOATM_QNAME, ResumenIDOATM_Type.class, null, value);
    }

}
