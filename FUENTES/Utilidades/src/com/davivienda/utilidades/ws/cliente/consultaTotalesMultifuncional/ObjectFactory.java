
package com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional package. 
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

    private final static QName _ConsultarTotalesMultifuncionalResponse_QNAME = new QName("http://ConsultaTotalesMultifuncional.servicios.procesadortransacciones.davivienda.com/", "consultarTotalesMultifuncionalResponse");
    private final static QName _ServicioException_QNAME = new QName("http://ConsultaTotalesMultifuncional.servicios.procesadortransacciones.davivienda.com/", "ServicioException");
    private final static QName _ConsultarTotalesMultifuncional_QNAME = new QName("http://ConsultaTotalesMultifuncional.servicios.procesadortransacciones.davivienda.com/", "consultarTotalesMultifuncional");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultarTotalesMultifuncional }
     * 
     */
    public ConsultarTotalesMultifuncional createConsultarTotalesMultifuncional() {
        return new ConsultarTotalesMultifuncional();
    }

    /**
     * Create an instance of {@link ServicioException }
     * 
     */
    public ServicioException createServicioException() {
        return new ServicioException();
    }

    /**
     * Create an instance of {@link ConsultarTotalesMultifuncionalResponse }
     * 
     */
    public ConsultarTotalesMultifuncionalResponse createConsultarTotalesMultifuncionalResponse() {
        return new ConsultarTotalesMultifuncionalResponse();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link RespuestaConsultaTotalesMultifuncionalDto }
     * 
     */
    public RespuestaConsultaTotalesMultifuncionalDto createRespuestaConsultaTotalesMultifuncionalDto() {
        return new RespuestaConsultaTotalesMultifuncionalDto();
    }

    /**
     * Create an instance of {@link ConsultaTotalesMultifuncionalDto }
     * 
     */
    public ConsultaTotalesMultifuncionalDto createConsultaTotalesMultifuncionalDto() {
        return new ConsultaTotalesMultifuncionalDto();
    }

    /**
     * Create an instance of {@link RespuestaDeTransaccionBaseDTO }
     * 
     */
    public RespuestaDeTransaccionBaseDTO createRespuestaDeTransaccionBaseDTO() {
        return new RespuestaDeTransaccionBaseDTO();
    }

    /**
     * Create an instance of {@link RepetitivoTotalTransaccionDTO }
     * 
     */
    public RepetitivoTotalTransaccionDTO createRepetitivoTotalTransaccionDTO() {
        return new RepetitivoTotalTransaccionDTO();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarTotalesMultifuncionalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ConsultaTotalesMultifuncional.servicios.procesadortransacciones.davivienda.com/", name = "consultarTotalesMultifuncionalResponse")
    public JAXBElement<ConsultarTotalesMultifuncionalResponse> createConsultarTotalesMultifuncionalResponse(ConsultarTotalesMultifuncionalResponse value) {
        return new JAXBElement<ConsultarTotalesMultifuncionalResponse>(_ConsultarTotalesMultifuncionalResponse_QNAME, ConsultarTotalesMultifuncionalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ConsultaTotalesMultifuncional.servicios.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarTotalesMultifuncional }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ConsultaTotalesMultifuncional.servicios.procesadortransacciones.davivienda.com/", name = "consultarTotalesMultifuncional")
    public JAXBElement<ConsultarTotalesMultifuncional> createConsultarTotalesMultifuncional(ConsultarTotalesMultifuncional value) {
        return new JAXBElement<ConsultarTotalesMultifuncional>(_ConsultarTotalesMultifuncional_QNAME, ConsultarTotalesMultifuncional.class, null, value);
    }

}
