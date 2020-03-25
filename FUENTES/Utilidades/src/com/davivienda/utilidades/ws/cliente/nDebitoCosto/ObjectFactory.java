
package com.davivienda.utilidades.ws.cliente.nDebitoCosto;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.nDebitoCosto package. 
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

    private final static QName _NotaDebitoCostoRequest_QNAME = new QName("http://www.davivienda.com/xml/NotaDebitoCosto", "NotaDebitoCostoRequest");
    private final static QName _NotaDebitoCostoResponse_QNAME = new QName("http://www.davivienda.com/xml/NotaDebitoCosto", "NotaDebitoCostoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.nDebitoCosto
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NotaDebitoCostoResponseType }
     * 
     */
    public NotaDebitoCostoResponseType createNotaDebitoCostoResponseType() {
        return new NotaDebitoCostoResponseType();
    }

    /**
     * Create an instance of {@link NotaDebitoCostoType }
     * 
     */
    public NotaDebitoCostoType createNotaDebitoCostoType() {
        return new NotaDebitoCostoType();
    }

    /**
     * Create an instance of {@link ResponseType }
     * 
     */
    public ResponseType createResponseType() {
        return new ResponseType();
    }

    /**
     * Create an instance of {@link DataHeaderResponseType }
     * 
     */
    public DataHeaderResponseType createDataHeaderResponseType() {
        return new DataHeaderResponseType();
    }

    /**
     * Create an instance of {@link DataType }
     * 
     */
    public DataType createDataType() {
        return new DataType();
    }

    /**
     * Create an instance of {@link DataHeaderType }
     * 
     */
    public DataHeaderType createDataHeaderType() {
        return new DataHeaderType();
    }

    /**
     * Create an instance of {@link DataResponseType }
     * 
     */
    public DataResponseType createDataResponseType() {
        return new DataResponseType();
    }

    /**
     * Create an instance of {@link RequestType }
     * 
     */
    public RequestType createRequestType() {
        return new RequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotaDebitoCostoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.davivienda.com/xml/NotaDebitoCosto", name = "NotaDebitoCostoRequest")
    public JAXBElement<NotaDebitoCostoType> createNotaDebitoCostoRequest(NotaDebitoCostoType value) {
        return new JAXBElement<NotaDebitoCostoType>(_NotaDebitoCostoRequest_QNAME, NotaDebitoCostoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotaDebitoCostoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.davivienda.com/xml/NotaDebitoCosto", name = "NotaDebitoCostoResponse")
    public JAXBElement<NotaDebitoCostoResponseType> createNotaDebitoCostoResponse(NotaDebitoCostoResponseType value) {
        return new JAXBElement<NotaDebitoCostoResponseType>(_NotaDebitoCostoResponse_QNAME, NotaDebitoCostoResponseType.class, null, value);
    }

}
