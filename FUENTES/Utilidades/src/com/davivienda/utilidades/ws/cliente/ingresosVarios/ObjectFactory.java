
package com.davivienda.utilidades.ws.cliente.ingresosVarios;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.ingresosVarios package. 
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

    private final static QName _IngresosVarios_QNAME = new QName("http://www.davivienda.com/xml/IngresosVarios", "IngresosVarios");
    private final static QName _IngresosVariosResponse_QNAME = new QName("http://www.davivienda.com/xml/IngresosVarios", "IngresosVariosResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.ingresosVarios
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IngresosVariosRespType }
     * 
     */
    public IngresosVariosRespType createIngresosVariosRespType() {
        return new IngresosVariosRespType();
    }

    /**
     * Create an instance of {@link IngresosVariosType }
     * 
     */
    public IngresosVariosType createIngresosVariosType() {
        return new IngresosVariosType();
    }

    /**
     * Create an instance of {@link DataHeaderResponseType }
     * 
     */
    public DataHeaderResponseType createDataHeaderResponseType() {
        return new DataHeaderResponseType();
    }

    /**
     * Create an instance of {@link ResponseType }
     * 
     */
    public ResponseType createResponseType() {
        return new ResponseType();
    }

    /**
     * Create an instance of {@link DataHeaderRequestType }
     * 
     */
    public DataHeaderRequestType createDataHeaderRequestType() {
        return new DataHeaderRequestType();
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
     * Create an instance of {@link DataRequestType }
     * 
     */
    public DataRequestType createDataRequestType() {
        return new DataRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IngresosVariosType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.davivienda.com/xml/IngresosVarios", name = "IngresosVarios")
    public JAXBElement<IngresosVariosType> createIngresosVarios(IngresosVariosType value) {
        return new JAXBElement<IngresosVariosType>(_IngresosVarios_QNAME, IngresosVariosType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IngresosVariosRespType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.davivienda.com/xml/IngresosVarios", name = "IngresosVariosResponse")
    public JAXBElement<IngresosVariosRespType> createIngresosVariosResponse(IngresosVariosRespType value) {
        return new JAXBElement<IngresosVariosRespType>(_IngresosVariosResponse_QNAME, IngresosVariosRespType.class, null, value);
    }

}
