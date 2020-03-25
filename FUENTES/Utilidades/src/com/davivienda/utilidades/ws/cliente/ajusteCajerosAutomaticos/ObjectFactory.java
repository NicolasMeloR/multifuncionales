
package com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos package. 
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

    private final static QName _AjustarFaltanteCajero_QNAME = new QName("http://servicios.impl.procesadortransacciones.davivienda.com/", "ajustarFaltanteCajero");
    private final static QName _AjustarSobranteCajeroResponse_QNAME = new QName("http://servicios.impl.procesadortransacciones.davivienda.com/", "ajustarSobranteCajeroResponse");
    private final static QName _ServicioException_QNAME = new QName("http://servicios.impl.procesadortransacciones.davivienda.com/", "ServicioException");
    private final static QName _AjustarProvisionCajero_QNAME = new QName("http://servicios.impl.procesadortransacciones.davivienda.com/", "ajustarProvisionCajero");
    private final static QName _AjustarSobranteCajero_QNAME = new QName("http://servicios.impl.procesadortransacciones.davivienda.com/", "ajustarSobranteCajero");
    private final static QName _AjustarProvisionCajeroResponse_QNAME = new QName("http://servicios.impl.procesadortransacciones.davivienda.com/", "ajustarProvisionCajeroResponse");
    private final static QName _AjustarFaltanteCajeroResponse_QNAME = new QName("http://servicios.impl.procesadortransacciones.davivienda.com/", "ajustarFaltanteCajeroResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AjustarSobranteCajeroResponse }
     * 
     */
    public AjustarSobranteCajeroResponse createAjustarSobranteCajeroResponse() {
        return new AjustarSobranteCajeroResponse();
    }

    /**
     * Create an instance of {@link AjustarFaltanteCajero }
     * 
     */
    public AjustarFaltanteCajero createAjustarFaltanteCajero() {
        return new AjustarFaltanteCajero();
    }

    /**
     * Create an instance of {@link ServicioException }
     * 
     */
    public ServicioException createServicioException() {
        return new ServicioException();
    }

    /**
     * Create an instance of {@link AjustarSobranteCajero }
     * 
     */
    public AjustarSobranteCajero createAjustarSobranteCajero() {
        return new AjustarSobranteCajero();
    }

    /**
     * Create an instance of {@link AjustarProvisionCajero }
     * 
     */
    public AjustarProvisionCajero createAjustarProvisionCajero() {
        return new AjustarProvisionCajero();
    }

    /**
     * Create an instance of {@link AjustarProvisionCajeroResponse }
     * 
     */
    public AjustarProvisionCajeroResponse createAjustarProvisionCajeroResponse() {
        return new AjustarProvisionCajeroResponse();
    }

    /**
     * Create an instance of {@link AjustarFaltanteCajeroResponse }
     * 
     */
    public AjustarFaltanteCajeroResponse createAjustarFaltanteCajeroResponse() {
        return new AjustarFaltanteCajeroResponse();
    }

    /**
     * Create an instance of {@link RespAjustarSobranteCajeroDto }
     * 
     */
    public RespAjustarSobranteCajeroDto createRespAjustarSobranteCajeroDto() {
        return new RespAjustarSobranteCajeroDto();
    }

    /**
     * Create an instance of {@link ParametrosSeguridadDTO }
     * 
     */
    public ParametrosSeguridadDTO createParametrosSeguridadDTO() {
        return new ParametrosSeguridadDTO();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link RespuestaConsultaManejoEfectivoATMDto }
     * 
     */
    public RespuestaConsultaManejoEfectivoATMDto createRespuestaConsultaManejoEfectivoATMDto() {
        return new RespuestaConsultaManejoEfectivoATMDto();
    }

    /**
     * Create an instance of {@link RespuestaDeTransaccionBaseDTO }
     * 
     */
    public RespuestaDeTransaccionBaseDTO createRespuestaDeTransaccionBaseDTO() {
        return new RespuestaDeTransaccionBaseDTO();
    }

    /**
     * Create an instance of {@link ConsultaManejoEfectivoATMDto }
     * 
     */
    public ConsultaManejoEfectivoATMDto createConsultaManejoEfectivoATMDto() {
        return new ConsultaManejoEfectivoATMDto();
    }

    /**
     * Create an instance of {@link ParametrosDeTransaccionBaseDTO }
     * 
     */
    public ParametrosDeTransaccionBaseDTO createParametrosDeTransaccionBaseDTO() {
        return new ParametrosDeTransaccionBaseDTO();
    }

    /**
     * Create an instance of {@link CampoExtendidoDto }
     * 
     */
    public CampoExtendidoDto createCampoExtendidoDto() {
        return new CampoExtendidoDto();
    }

    /**
     * Create an instance of {@link AjustarSobranteCajeroDto }
     * 
     */
    public AjustarSobranteCajeroDto createAjustarSobranteCajeroDto() {
        return new AjustarSobranteCajeroDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AjustarFaltanteCajero }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.impl.procesadortransacciones.davivienda.com/", name = "ajustarFaltanteCajero")
    public JAXBElement<AjustarFaltanteCajero> createAjustarFaltanteCajero(AjustarFaltanteCajero value) {
        return new JAXBElement<AjustarFaltanteCajero>(_AjustarFaltanteCajero_QNAME, AjustarFaltanteCajero.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AjustarSobranteCajeroResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.impl.procesadortransacciones.davivienda.com/", name = "ajustarSobranteCajeroResponse")
    public JAXBElement<AjustarSobranteCajeroResponse> createAjustarSobranteCajeroResponse(AjustarSobranteCajeroResponse value) {
        return new JAXBElement<AjustarSobranteCajeroResponse>(_AjustarSobranteCajeroResponse_QNAME, AjustarSobranteCajeroResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.impl.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AjustarProvisionCajero }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.impl.procesadortransacciones.davivienda.com/", name = "ajustarProvisionCajero")
    public JAXBElement<AjustarProvisionCajero> createAjustarProvisionCajero(AjustarProvisionCajero value) {
        return new JAXBElement<AjustarProvisionCajero>(_AjustarProvisionCajero_QNAME, AjustarProvisionCajero.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AjustarSobranteCajero }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.impl.procesadortransacciones.davivienda.com/", name = "ajustarSobranteCajero")
    public JAXBElement<AjustarSobranteCajero> createAjustarSobranteCajero(AjustarSobranteCajero value) {
        return new JAXBElement<AjustarSobranteCajero>(_AjustarSobranteCajero_QNAME, AjustarSobranteCajero.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AjustarProvisionCajeroResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.impl.procesadortransacciones.davivienda.com/", name = "ajustarProvisionCajeroResponse")
    public JAXBElement<AjustarProvisionCajeroResponse> createAjustarProvisionCajeroResponse(AjustarProvisionCajeroResponse value) {
        return new JAXBElement<AjustarProvisionCajeroResponse>(_AjustarProvisionCajeroResponse_QNAME, AjustarProvisionCajeroResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AjustarFaltanteCajeroResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.impl.procesadortransacciones.davivienda.com/", name = "ajustarFaltanteCajeroResponse")
    public JAXBElement<AjustarFaltanteCajeroResponse> createAjustarFaltanteCajeroResponse(AjustarFaltanteCajeroResponse value) {
        return new JAXBElement<AjustarFaltanteCajeroResponse>(_AjustarFaltanteCajeroResponse_QNAME, AjustarFaltanteCajeroResponse.class, null, value);
    }

}
