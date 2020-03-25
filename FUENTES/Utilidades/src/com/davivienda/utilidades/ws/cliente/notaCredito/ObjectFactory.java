
package com.davivienda.utilidades.ws.cliente.notaCredito;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.notaCredito package. 
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

    private final static QName _NotaCreditoResponse_QNAME = new QName("http://www.davivienda.com/xml/NotaCredito", "NotaCreditoResponse");
    private final static QName _NotaCredito_QNAME = new QName("http://www.davivienda.com/xml/NotaCredito", "NotaCredito");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.notaCredito
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NotaCreditoType }
     * 
     */
    public NotaCreditoType createNotaCreditoType() {
        return new NotaCreditoType();
    }

    /**
     * Create an instance of {@link NotaCreditoResponseType }
     * 
     */
    public NotaCreditoResponseType createNotaCreditoResponseType() {
        return new NotaCreditoResponseType();
    }

    /**
     * Create an instance of {@link DataType }
     * 
     */
    public DataType createDataType() {
        return new DataType();
    }

    /**
     * Create an instance of {@link AbonoFMType }
     * 
     */
    public AbonoFMType createAbonoFMType() {
        return new AbonoFMType();
    }

    /**
     * Create an instance of {@link ResponseType }
     * 
     */
    public ResponseType createResponseType() {
        return new ResponseType();
    }

    /**
     * Create an instance of {@link DataHeaderType }
     * 
     */
    public DataHeaderType createDataHeaderType() {
        return new DataHeaderType();
    }

    /**
     * Create an instance of {@link AbonoTCResponseType }
     * 
     */
    public AbonoTCResponseType createAbonoTCResponseType() {
        return new AbonoTCResponseType();
    }

    /**
     * Create an instance of {@link ProductoCorrienteResponseType }
     * 
     */
    public ProductoCorrienteResponseType createProductoCorrienteResponseType() {
        return new ProductoCorrienteResponseType();
    }

    /**
     * Create an instance of {@link DataResponseType }
     * 
     */
    public DataResponseType createDataResponseType() {
        return new DataResponseType();
    }

    /**
     * Create an instance of {@link ProductoAhorroResponseType }
     * 
     */
    public ProductoAhorroResponseType createProductoAhorroResponseType() {
        return new ProductoAhorroResponseType();
    }

    /**
     * Create an instance of {@link AbonoTCType }
     * 
     */
    public AbonoTCType createAbonoTCType() {
        return new AbonoTCType();
    }

    /**
     * Create an instance of {@link DataHeaderResponseType }
     * 
     */
    public DataHeaderResponseType createDataHeaderResponseType() {
        return new DataHeaderResponseType();
    }

    /**
     * Create an instance of {@link AbonoFMResponseType }
     * 
     */
    public AbonoFMResponseType createAbonoFMResponseType() {
        return new AbonoFMResponseType();
    }

    /**
     * Create an instance of {@link RequestType }
     * 
     */
    public RequestType createRequestType() {
        return new RequestType();
    }

    /**
     * Create an instance of {@link ProductoAhorroCorrienteType }
     * 
     */
    public ProductoAhorroCorrienteType createProductoAhorroCorrienteType() {
        return new ProductoAhorroCorrienteType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotaCreditoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.davivienda.com/xml/NotaCredito", name = "NotaCreditoResponse")
    public JAXBElement<NotaCreditoResponseType> createNotaCreditoResponse(NotaCreditoResponseType value) {
        return new JAXBElement<NotaCreditoResponseType>(_NotaCreditoResponse_QNAME, NotaCreditoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotaCreditoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.davivienda.com/xml/NotaCredito", name = "NotaCredito")
    public JAXBElement<NotaCreditoType> createNotaCredito(NotaCreditoType value) {
        return new JAXBElement<NotaCreditoType>(_NotaCredito_QNAME, NotaCreditoType.class, null, value);
    }

}
