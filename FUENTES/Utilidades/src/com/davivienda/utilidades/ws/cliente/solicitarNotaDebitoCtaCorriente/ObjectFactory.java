
package com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaCorriente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.solicitarNotaDebitoCtaCorriente package. 
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

    private final static QName _SolicitarNotaDebitoCuentaCorrienteResponse_QNAME = new QName("http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteTypes", "solicitarNotaDebitoCuentaCorrienteResponse");
    private final static QName _ServicioException_QNAME = new QName("http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteTypes", "ServicioException");
    private final static QName _SolicitarNotaDebitoCuentaCorriente_QNAME = new QName("http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteTypes", "solicitarNotaDebitoCuentaCorriente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.solicitarNotaDebitoCtaCorriente
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
     * Create an instance of {@link SolicitarNotaDebitoCuentaCorrienteResponse }
     * 
     */
    public SolicitarNotaDebitoCuentaCorrienteResponse createSolicitarNotaDebitoCuentaCorrienteResponse() {
        return new SolicitarNotaDebitoCuentaCorrienteResponse();
    }

    /**
     * Create an instance of {@link SolicitarNotaDebitoCuentaCorriente }
     * 
     */
    public SolicitarNotaDebitoCuentaCorriente createSolicitarNotaDebitoCuentaCorriente() {
        return new SolicitarNotaDebitoCuentaCorriente();
    }

    /**
     * Create an instance of {@link CampoExtendidoDto }
     * 
     */
    public CampoExtendidoDto createCampoExtendidoDto() {
        return new CampoExtendidoDto();
    }

    /**
     * Create an instance of {@link SolicitudNotaDebitoCuentaCorrienteDto }
     * 
     */
    public SolicitudNotaDebitoCuentaCorrienteDto createSolicitudNotaDebitoCuentaCorrienteDto() {
        return new SolicitudNotaDebitoCuentaCorrienteDto();
    }

    /**
     * Create an instance of {@link RespuestaNotaDebitoCuentaCorrienteDto }
     * 
     */
    public RespuestaNotaDebitoCuentaCorrienteDto createRespuestaNotaDebitoCuentaCorrienteDto() {
        return new RespuestaNotaDebitoCuentaCorrienteDto();
    }

    /**
     * Create an instance of {@link ParametrosSeguridadDTO }
     * 
     */
    public ParametrosSeguridadDTO createParametrosSeguridadDTO() {
        return new ParametrosSeguridadDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarNotaDebitoCuentaCorrienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteTypes", name = "solicitarNotaDebitoCuentaCorrienteResponse")
    public JAXBElement<SolicitarNotaDebitoCuentaCorrienteResponse> createSolicitarNotaDebitoCuentaCorrienteResponse(SolicitarNotaDebitoCuentaCorrienteResponse value) {
        return new JAXBElement<SolicitarNotaDebitoCuentaCorrienteResponse>(_SolicitarNotaDebitoCuentaCorrienteResponse_QNAME, SolicitarNotaDebitoCuentaCorrienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServicioException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteTypes", name = "ServicioException")
    public JAXBElement<ServicioException> createServicioException(ServicioException value) {
        return new JAXBElement<ServicioException>(_ServicioException_QNAME, ServicioException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolicitarNotaDebitoCuentaCorriente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.davivienda.com/solicitarNotaDebitoCuentaCorrienteTypes", name = "solicitarNotaDebitoCuentaCorriente")
    public JAXBElement<SolicitarNotaDebitoCuentaCorriente> createSolicitarNotaDebitoCuentaCorriente(SolicitarNotaDebitoCuentaCorriente value) {
        return new JAXBElement<SolicitarNotaDebitoCuentaCorriente>(_SolicitarNotaDebitoCuentaCorriente_QNAME, SolicitarNotaDebitoCuentaCorriente.class, null, value);
    }

}
