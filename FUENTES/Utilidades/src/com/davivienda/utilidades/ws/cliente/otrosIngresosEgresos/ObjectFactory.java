
package com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.otrosIngresosEgresos package. 
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

    private final static QName _OtrosIngresos_QNAME = new QName("http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", "otrosIngresos");
    private final static QName _ServicioException_QNAME = new QName("http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", "ServicioException");
    private final static QName _OtrosIngresosResponse_QNAME = new QName("http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", "otrosIngresosResponse");
    private final static QName _OtrosEgresos_QNAME = new QName("http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", "otrosEgresos");
    private final static QName _OtrosEgresosResponse_QNAME = new QName("http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", "otrosEgresosResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.otrosIngresosEgresos
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
     * Create an instance of {@link OtrosIngresos }
     * 
     */
    public OtrosIngresos createOtrosIngresos() {
        return new OtrosIngresos();
    }

    /**
     * Create an instance of {@link OtrosEgresosResponse }
     * 
     */
    public OtrosEgresosResponse createOtrosEgresosResponse() {
        return new OtrosEgresosResponse();
    }

    /**
     * Create an instance of {@link OtrosEgresos }
     * 
     */
    public OtrosEgresos createOtrosEgresos() {
        return new OtrosEgresos();
    }

    /**
     * Create an instance of {@link OtrosIngresosResponse }
     * 
     */
    public OtrosIngresosResponse createOtrosIngresosResponse() {
        return new OtrosIngresosResponse();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link AnulacionChequesGdosDto }
     * 
     */
    public AnulacionChequesGdosDto createAnulacionChequesGdosDto() {
        return new AnulacionChequesGdosDto();
    }

    /**
     * Create an instance of {@link RespuestaAnulacionChequesGdosDto }
     * 
     */
    public RespuestaAnulacionChequesGdosDto createRespuestaAnulacionChequesGdosDto() {
        return new RespuestaAnulacionChequesGdosDto();
    }

    /**
     * Create an instance of {@link RespuestaDeTransaccionBaseDTO }
     * 
     */
    public RespuestaDeTransaccionBaseDTO createRespuestaDeTransaccionBaseDTO() {
        return new RespuestaDeTransaccionBaseDTO();
    }

    /**
     * Create an instance of {@link IngresoGiroChqDto }
     * 
     */
    public IngresoGiroChqDto createIngresoGiroChqDto() {
        return new IngresoGiroChqDto();
    }

    /**
     * Create an instance of {@link RespuestaIngresoGiroChqDto }
     * 
     */
    public RespuestaIngresoGiroChqDto createRespuestaIngresoGiroChqDto() {
        return new RespuestaIngresoGiroChqDto();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link OtrosIngresos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", name = "otrosIngresos")
    public JAXBElement<OtrosIngresos> createOtrosIngresos(OtrosIngresos value) {
        return new JAXBElement<OtrosIngresos>(_OtrosIngresos_QNAME, OtrosIngresos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OtrosIngresosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", name = "otrosIngresosResponse")
    public JAXBElement<OtrosIngresosResponse> createOtrosIngresosResponse(OtrosIngresosResponse value) {
        return new JAXBElement<OtrosIngresosResponse>(_OtrosIngresosResponse_QNAME, OtrosIngresosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OtrosEgresos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", name = "otrosEgresos")
    public JAXBElement<OtrosEgresos> createOtrosEgresos(OtrosEgresos value) {
        return new JAXBElement<OtrosEgresos>(_OtrosEgresos_QNAME, OtrosEgresos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OtrosEgresosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://OtrosIngresosEgresos.servicios.impl.procesadortransacciones.davivienda.com/", name = "otrosEgresosResponse")
    public JAXBElement<OtrosEgresosResponse> createOtrosEgresosResponse(OtrosEgresosResponse value) {
        return new JAXBElement<OtrosEgresosResponse>(_OtrosEgresosResponse_QNAME, OtrosEgresosResponse.class, null, value);
    }

}
