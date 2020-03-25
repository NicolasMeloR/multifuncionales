package com.davivienda.sara.contingencia.general.helper;

import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.contingencia.general.ContingenciaManualGeneralObjectContext;
import com.davivienda.sara.contingencia.general.ContingenciaManualHelperInterface;
import com.davivienda.sara.diarioelectronico.general.DiarioElectronicoGeneralObjectContext;
import com.davivienda.sara.diarioelectronico.general.DiarioElectronicoHelperInterface;
import com.davivienda.sara.entitys.EdcCargue;
import com.davivienda.sara.tablas.edccargue.session.EdcCargueSessionLocal;
import com.davivienda.utilidades.conversion.JSon;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.davivienda.sara.diarioelectronico.general.bean.DiarioElectronicoBean;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.tareas.contingencia.carguearchivos.servicio.ContingenciaCargueArchivosServicio;
import com.davivienda.sara.tareas.contingencia.carguearchivos.session.ContingenciaCargueArchivosSessionLocal;
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;

/**
 * DiarioElectronicoGeneralTransaccionServletHelper - 27/08/2008 Descripcion :
 * Helper para el manejo de los requerimientos de Transaccion Version : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ContingenciaGeneralConsultaServletHelper implements ContingenciaManualHelperInterface {

    private ContingenciaCargueArchivosSessionLocal session = null;
    private ContingenciaManualGeneralObjectContext objectContext = null;
    private String respuestaJSon;

    public ContingenciaGeneralConsultaServletHelper(ContingenciaCargueArchivosSessionLocal session, ContingenciaManualGeneralObjectContext objectContext) {
        this.session = session;
        this.objectContext = objectContext;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        Collection<Regcarguearchivo> items = null;
        int codigoCiclo;
        try {

            Date fechaInicial = null;
            Date fechaFinal = null;

            try {
                fechaInicial = objectContext.getAtributoFechaHoraInicial().getTime();

            } catch (IllegalArgumentException ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getDateAyer();
            }
            try {
                fechaFinal = objectContext.getAtributoFechaHoraFinal().getTime();
            } catch (IllegalArgumentException ex) {
                fechaFinal = fechaInicial;
            }
            fechaFinal = com.davivienda.utilidades.conversion.Fecha.getFechaFinDia(fechaInicial);

            try {
                // Consulta los registros segun los parometros tomados del request
                items = session.getRegCargueArchivoPorFecha(fechaInicial, fechaFinal);
            } catch (EJBException ex) {
                if (ex.getLocalizedMessage().contains("EntityServicioExcepcion")) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }
                if (ex.getLocalizedMessage().contains("IllegalArgumentException")) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
                }
                if (ex.getLocalizedMessage().contains("NoResultException")) {
                    respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
                }
            } catch (Exception ex) {
                respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), "Error interno en la consulta");
            }

        } catch (IllegalArgumentException ex) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
        }
        if (items == null || items.isEmpty()) {
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.REGISTRO_NO_EXISTE.getCodigo(), "No hay registros que cumplan los criterios de la consulta");
        }

        if (items != null && this.respuestaJSon.length() <= 1) {
            respuesta = aJSon(items);  // paso los items a JSON
        } else {
            respuesta = this.respuestaJSon;
        }

        return respuesta;
    }

    private String aJSon(Collection<Regcarguearchivo> items) {
        String cadenaJSon = "";
        try {
            Integer idRegistro = 0;
            JSONObject resp = new JSONObject();
            JSONArray respItems = new JSONArray();
            String strFechaArchivo = "";
            String srtDescripcionError = "";
            Long lngNumRegistros = new Long(0);
            Date fechaFinal;
            String usuario = "";
            for (Regcarguearchivo item : items) {
                JSONObject itemJSon = new JSONObject();
                strFechaArchivo = "";
                srtDescripcionError = "";

                itemJSon.put("idRegistro", ++idRegistro);
                itemJSon.put("archivoTarea", item.getArchivotarea());
                itemJSon.put("tarea", item.getTarea());
                itemJSon.put("fecha", item.getFecha());
                itemJSon.put("tareaAutomatica", item.getIndautomatico());
                itemJSon.put("estadoCarga", com.davivienda.sara.constantes.EstadoProceso.getEstadoProceso(item.getEstadocarga()));
                if (item.getFechaautomatica() != null) {
                    strFechaArchivo = item.getFechaautomatica();
                } else {
                    strFechaArchivo = item.getFechamanual();

                }
                itemJSon.put("fechaArchivo", strFechaArchivo);

                if (item.getDescripcionerror() != null) {
                    srtDescripcionError = item.getDescripcionerror();
                }
                itemJSon.put("descripcionError", srtDescripcionError);

                if (item.getNumregistros() != null) {
                    lngNumRegistros = item.getNumregistros();
                }
                itemJSon.put("numRegistros", lngNumRegistros);

                if (item.getFechafinal() != null) {
                    fechaFinal = item.getFechafinal();
                } else {
                    fechaFinal = item.getFecha();
                }
                itemJSon.put("fechaFinal", fechaFinal);

                if (item.getUsuario() != null) {
                    usuario = item.getUsuario();
                }
                itemJSon.put("usuario", usuario);

                respItems.put(itemJSon);
            }
            resp.put("identifier", "idRegistro");
            resp.put("label", "codigoCajero");
            resp.put("items", respItems);
            cadenaJSon = resp.toString();

        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.severe("No se puede pasar a JSON \n " + ex.getMessage());
            cadenaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_PARSEAR_REGISTRO.getCodigo(), ex.getMessage());
        }
        return cadenaJSon;
    }

    public String generarDiarioelectronicoXML() {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<DiarioElectronicoBean> obtenerDatosCollectionDE() {

        throw new UnsupportedOperationException("Not supported yet.");
    }

}
