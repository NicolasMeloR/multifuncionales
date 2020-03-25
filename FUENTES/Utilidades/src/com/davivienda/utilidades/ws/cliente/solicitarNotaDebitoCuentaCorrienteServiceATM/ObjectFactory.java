
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.solicitarNotaDebitoCuentaCorrienteServiceATM package. 
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

    private final static QName _SolictarNotaDebitoCuentaCorrienteResponse_QNAME = new QName("http://solictarnotadebitocuentacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", "solictarNotaDebitoCuentaCorrienteResponse");
    private final static QName _ServicioException_QNAME = new QName("http://solictarnotadebitocuentacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", "ServicioException");
    private final static QName _SolictarNotaDebitoCuentaCorriente_QNAME = new QName("http://solictarnotadebitocuentacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", "solictarNotaDebitoCuentaCorriente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.solicitarNotaDebitoCuentaCorrienteServiceATM
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SolictarNotaDebitoCuentaCorriente }
     * 
     */
    public SolictarNotaDebitoCuentaCorriente createSolictarNotaDebitoCuentaCorriente() {
        return new SolictarNotaDebitoCuentaCorriente();
    }

    /**
     * Create an instance of {@link ServicioException }
     * 
     */
    public ServicioException createServicioException() {
        return new ServicioException();
    }

    /**
     * Create an instance of {@link SolictarNotaDebitoCuentaCorrienteResponse }
     * 
     */
    public SolictarNotaDebitoCuentaCorrienteResponse createSolictarNotaDebitoCuentaCorrienteResponse() {
        return new SolictarNotaDebitoCuentaCorrienteResponse();
    }

    /**
     * Create an instance of {@link CampoDto }
     * 
     */
    public CampoDto createCampoDto() {
        return new CampoDto();
    }

    /**
     * Create an instance of {@link RespuestaNotaDebitoCuentaCorrienteDto }
     * 
     */
    public RespuestaNotaDebitoCuentaCorrienteDto createRespuestaNotaDebitoCuentaCorrienteDto() {
        return new RespuestaNotaDebitoCuentaCorrienteDto();
    }

    /**
     * Create an instance of {@link RespuestaDeTransaccionBaseDTO }
     * 
     */
    public RespuestaDeTransaccionBaseDTO createRespuestaDeTransaccionBaseDTO() {
        return new RespuestaDeTransaccionBaseDTO();
    }

    /**
     * Create an instance of {@link SolicitudNotaDebitoCuentaCorrienteDto }
     * 
     */
    public SolicitudNotaDebitoCuentaCorrienteDto createSolicitudNotaDebitoCuentaCorrienteDto() {
        return new SolicitudNotaDebitoCuentaCorrienteDto();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SolictarNotaDebitoCuentaCorrienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solictarnotadebitocuentacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", name = "solictarNotaDebitoCuentaCorrienteResponse")
    public JAXBElement<SolictarNotaDebitoCuentaCorrienteResponse> createSolictarNotaDebitoCuentaCorrienteResponse(SolictarNotaDebitoCuentaCorrienteResponse value) {
        return new JAXBElement<SolictarNotaDebitoCuentaCorrienteResponse>(_SolictarNotaDebitoCuentaCorrienteResponse_QNAME, SolictarNotaDebitoCuentaCorrienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solictarnotadebitocuentacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolictarNotaDebitoCuentaCorriente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solictarnotadebitocuentacorrienteinterface.servicios.procesadortransacciones.davivienda.com/", name = "solictarNotaDebitoCuentaCorriente")
    public JAXBElement<SolictarNotaDebitoCuentaCorriente> createSolictarNotaDebitoCuentaCorriente(SolictarNotaDebitoCuentaCorriente value) {
        return new JAXBElement<SolictarNotaDebitoCuentaCorriente>(_SolictarNotaDebitoCuentaCorriente_QNAME, SolictarNotaDebitoCuentaCorriente.class, null, value);
    }

}
