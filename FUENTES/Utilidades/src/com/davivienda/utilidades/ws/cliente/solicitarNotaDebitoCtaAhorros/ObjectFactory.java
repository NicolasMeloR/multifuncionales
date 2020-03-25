
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.solicitarNotaDebitoCtaAhorros package. 
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

    private final static QName _SolicitarNotaDebitoCuentaAhorros_QNAME = new QName("http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes", "solicitarNotaDebitoCuentaAhorros");
    private final static QName _SolicitarNotaDebitoCuentaAhorrosResponse_QNAME = new QName("http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes", "solicitarNotaDebitoCuentaAhorrosResponse");
    private final static QName _ServicioException_QNAME = new QName("http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes", "ServicioException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.solicitarNotaDebitoCtaAhorros
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
     * Create an instance of {@link SolicitarNotaDebitoCuentaAhorrosResponse }
     * 
     */
    public SolicitarNotaDebitoCuentaAhorrosResponse createSolicitarNotaDebitoCuentaAhorrosResponse() {
        return new SolicitarNotaDebitoCuentaAhorrosResponse();
    }

    /**
     * Create an instance of {@link SolicitarNotaDebitoCuentaAhorros }
     * 
     */
    public SolicitarNotaDebitoCuentaAhorros createSolicitarNotaDebitoCuentaAhorros() {
        return new SolicitarNotaDebitoCuentaAhorros();
    }

    /**
     * Create an instance of {@link CampoExtendidoDto }
     * 
     */
    public CampoExtendidoDto createCampoExtendidoDto() {
        return new CampoExtendidoDto();
    }

    /**
     * Create an instance of {@link ParametrosSeguridadDTO }
     * 
     */
    public ParametrosSeguridadDTO createParametrosSeguridadDTO() {
        return new ParametrosSeguridadDTO();
    }

    /**
     * Create an instance of {@link SolicitudNotaDebitoCuentaAhorrosDto }
     * 
     */
    public SolicitudNotaDebitoCuentaAhorrosDto createSolicitudNotaDebitoCuentaAhorrosDto() {
        return new SolicitudNotaDebitoCuentaAhorrosDto();
    }

    /**
     * Create an instance of {@link RespuestaNotaDebitoCuentaAhorrosDto }
     * 
     */
    public RespuestaNotaDebitoCuentaAhorrosDto createRespuestaNotaDebitoCuentaAhorrosDto() {
        return new RespuestaNotaDebitoCuentaAhorrosDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarNotaDebitoCuentaAhorros }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes", name = "solicitarNotaDebitoCuentaAhorros")
    public JAXBElement<SolicitarNotaDebitoCuentaAhorros> createSolicitarNotaDebitoCuentaAhorros(SolicitarNotaDebitoCuentaAhorros value) {
        return new JAXBElement<SolicitarNotaDebitoCuentaAhorros>(_SolicitarNotaDebitoCuentaAhorros_QNAME, SolicitarNotaDebitoCuentaAhorros.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarNotaDebitoCuentaAhorrosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes", name = "solicitarNotaDebitoCuentaAhorrosResponse")
    public JAXBElement<SolicitarNotaDebitoCuentaAhorrosResponse> createSolicitarNotaDebitoCuentaAhorrosResponse(SolicitarNotaDebitoCuentaAhorrosResponse value) {
        return new JAXBElement<SolicitarNotaDebitoCuentaAhorrosResponse>(_SolicitarNotaDebitoCuentaAhorrosResponse_QNAME, SolicitarNotaDebitoCuentaAhorrosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitarNotaDebitoCuentaAhorrosServiceTypes", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

}
