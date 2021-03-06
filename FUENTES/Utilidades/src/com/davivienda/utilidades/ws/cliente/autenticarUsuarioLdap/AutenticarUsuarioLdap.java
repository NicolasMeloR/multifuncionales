
package com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "AutenticarUsuarioLdap", targetNamespace = "http://servicio.autenticacionldap.davivienda.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AutenticarUsuarioLdap {


    /**
     * 
     * @param parameter
     * @return
     *     returns com.davivienda.sara.clientews.autenticarUsuarioLdap.RespuestaAutenticarUsuarioDTO
     * @throws ServicioException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "autenticarUsuario", targetNamespace = "http://servicio.autenticacionldap.davivienda.com/", className = "com.davivienda.sara.clientews.autenticarUsuarioLdap.AutenticarUsuario")
    @ResponseWrapper(localName = "autenticarUsuarioResponse", targetNamespace = "http://servicio.autenticacionldap.davivienda.com/", className = "com.davivienda.sara.clientews.autenticarUsuarioLdap.AutenticarUsuarioResponse")
    public RespuestaAutenticarUsuarioDTO autenticarUsuario(
        @WebParam(name = "parameter", targetNamespace = "")
        AutenticarUsuarioDTO parameter)
        throws ServicioException_Exception
    ;

}
