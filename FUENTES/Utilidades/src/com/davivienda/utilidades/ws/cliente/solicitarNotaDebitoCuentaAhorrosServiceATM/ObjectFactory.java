
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.solictarNotaDebitoCuentaAhorrosServiceATM package. 
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

    private final static QName _SolictarNotaDebitoCuentaAhorros_QNAME = new QName("http://solictarnotadebitocuentaahorrosserviceinteface.servicios.procesadortransacciones.davivienda.com/", "solictarNotaDebitoCuentaAhorros");
    private final static QName _ServicioException_QNAME = new QName("http://solictarnotadebitocuentaahorrosserviceinteface.servicios.procesadortransacciones.davivienda.com/", "ServicioException");
    private final static QName _SolictarNotaDebitoCuentaAhorrosResponse_QNAME = new QName("http://solictarnotadebitocuentaahorrosserviceinteface.servicios.procesadortransacciones.davivienda.com/", "solictarNotaDebitoCuentaAhorrosResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.solictarNotaDebitoCuentaAhorrosServiceATM
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
     * Create an instance of {@link SolictarNotaDebitoCuentaAhorrosResponse }
     * 
     */
    public SolictarNotaDebitoCuentaAhorrosResponse createSolictarNotaDebitoCuentaAhorrosResponse() {
        return new SolictarNotaDebitoCuentaAhorrosResponse();
    }

    /**
     * Create an instance of {@link SolictarNotaDebitoCuentaAhorros }
     * 
     */
    public SolictarNotaDebitoCuentaAhorros createSolictarNotaDebitoCuentaAhorros() {
        return new SolictarNotaDebitoCuentaAhorros();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link SolicitudNotaDebitoCuentaAhorrosDto }
     * 
     */
    public SolicitudNotaDebitoCuentaAhorrosDto createSolicitudNotaDebitoCuentaAhorrosDto() {
        return new SolicitudNotaDebitoCuentaAhorrosDto();
    }

    /**
     * Create an instance of {@link RespuestaDeTransaccionBaseDTO }
     * 
     */
    public RespuestaDeTransaccionBaseDTO createRespuestaDeTransaccionBaseDTO() {
        return new RespuestaDeTransaccionBaseDTO();
    }

    /**
     * Create an instance of {@link RespuestaNotaDebitoCuentaAhorrosDto }
     * 
     */
    public RespuestaNotaDebitoCuentaAhorrosDto createRespuestaNotaDebitoCuentaAhorrosDto() {
        return new RespuestaNotaDebitoCuentaAhorrosDto();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SolictarNotaDebitoCuentaAhorros }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solictarnotadebitocuentaahorrosserviceinteface.servicios.procesadortransacciones.davivienda.com/", name = "solictarNotaDebitoCuentaAhorros")
    public JAXBElement<SolictarNotaDebitoCuentaAhorros> createSolictarNotaDebitoCuentaAhorros(SolictarNotaDebitoCuentaAhorros value) {
        return new JAXBElement<SolictarNotaDebitoCuentaAhorros>(_SolictarNotaDebitoCuentaAhorros_QNAME, SolictarNotaDebitoCuentaAhorros.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solictarnotadebitocuentaahorrosserviceinteface.servicios.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolictarNotaDebitoCuentaAhorrosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solictarnotadebitocuentaahorrosserviceinteface.servicios.procesadortransacciones.davivienda.com/", name = "solictarNotaDebitoCuentaAhorrosResponse")
    public JAXBElement<SolictarNotaDebitoCuentaAhorrosResponse> createSolictarNotaDebitoCuentaAhorrosResponse(SolictarNotaDebitoCuentaAhorrosResponse value) {
        return new JAXBElement<SolictarNotaDebitoCuentaAhorrosResponse>(_SolictarNotaDebitoCuentaAhorrosResponse_QNAME, SolictarNotaDebitoCuentaAhorrosResponse.class, null, value);
    }

}
