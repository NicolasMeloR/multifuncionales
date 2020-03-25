package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.tablas.notasdebito.session.NotasDebitoSessionLocal;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.JSon;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;

/**
 * ReintegrosRevisarServletHelper - 27/08/2008 Descripción : Helper para el
 * manejo de los requerimientos de Reintegros Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ConsultarReintegrosReversadosServletHelper implements ReintegrosHelperInterface {

    private NotasDebitoSessionLocal session = null;
    private String respuestaJSon;
    private ReintegrosGeneralObjectContext objectContext;

    public ConsultarReintegrosReversadosServletHelper(NotasDebitoSessionLocal session, ReintegrosGeneralObjectContext objectContext) {
        this.session = session;
        this.objectContext = objectContext;

    }

    @Override
    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        try {
            try {
                Integer codigoCajero = Cadena.aInteger(objectContext.getAtributoString("codigoCajero"));
                Date fechaReversado = Cadena.aDate(objectContext.getAtributoString("fechaReversado"), FormatoFecha.FECHA_HORA);
                String numeroCuenta = objectContext.getAtributoString("numeroCuenta");
                respuesta = session.findByPrimayKey(codigoCajero, fechaReversado, numeroCuenta);
            } catch (EJBException ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_SIN_DATA);
                }
            } catch (Exception ex) {
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
            }

        } catch (IllegalArgumentException ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
        }
        return respuesta;
    }

}
