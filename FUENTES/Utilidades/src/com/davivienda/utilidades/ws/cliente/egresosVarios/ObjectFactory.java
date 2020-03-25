
package com.davivienda.utilidades.ws.cliente.egresosVarios;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.davivienda.sara.clientews.egresosVarios package. 
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

    private final static QName _EgresosVarios_QNAME = new QName("http://www.davivienda.com/xml/EgresosVarios", "EgresosVarios");
    private final static QName _EgresosVariosResponse_QNAME = new QName("http://www.davivienda.com/xml/EgresosVarios", "EgresosVariosResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.davivienda.sara.clientews.egresosVarios
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EgresosVariosType }
     * 
     */
    public EgresosVariosType createEgresosVariosType() {
        return new EgresosVariosType();
    }

    /**
     * Create an instance of {@link EgresosVariosRespType }
     * 
     */
    public EgresosVariosRespType createEgresosVariosRespType() {
        return new EgresosVariosRespType();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link EgresosVariosType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.davivienda.com/xml/EgresosVarios", name = "EgresosVarios")
    public JAXBElement<EgresosVariosType> createEgresosVarios(EgresosVariosType value) {
        return new JAXBElement<EgresosVariosType>(_EgresosVarios_QNAME, EgresosVariosType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EgresosVariosRespType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.davivienda.com/xml/EgresosVarios", name = "EgresosVariosResponse")
    public JAXBElement<EgresosVariosRespType> createEgresosVariosResponse(EgresosVariosRespType value) {
        return new JAXBElement<EgresosVariosRespType>(_EgresosVariosResponse_QNAME, EgresosVariosRespType.class, null, value);
    }

}
