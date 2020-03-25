package com.davivienda.sara.reintegros.general.helper;

import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.sara.constantes.TipoCuenta;
import com.davivienda.sara.dto.ReintegrosRevisionDTO;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.entitys.NotasdebitoPK;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosPK;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.procesos.autenticacion.webservice.session.AutenticacionLdapWSProcesosSessionLocal;
import com.davivienda.sara.procesos.cargues.masivos.notasdebito.session.CargarNotasDebitoMasivosSessionLocal;
import com.davivienda.sara.procesos.reintegros.notas.session.ReintegrosNotasProcesosSessionLocal;
import com.davivienda.sara.procesos.reintegros.session.ProcesoReintegrosSessionLocal;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.ReversarReintegrosObjectContext;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.reintegros.session.ReintegrosSessionLocal;
import com.davivienda.sara.tablas.reversarreintegros.session.ReversarReintegrosSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.JSon;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.EJBException;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ReintegrosRevisarServletHelper - 27/08/2008 Descripción : Helper para el
 * manejo de los requerimientos de Reintegros Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ReversarReintegrosGeneralServletHelper extends BaseBean implements ReintegrosHelperInterface {

    private ReversarReintegrosSessionLocal session;
    private ReintegrosSessionLocal reintegrosSessionLocal;
    private ReversarReintegrosObjectContext objectContext;
    private String respuestaJSon;
    private ConfModulosAplicacionLocal confModulosAplicacionSession;
    private AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal;
    private ReintegrosNotasProcesosSessionLocal reintegrosNotasProcesosSessionLocal;
    private CargarNotasDebitoMasivosSessionLocal cargarNotasDebitoMasivosSessionLocal;
    private ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal;
    private List<String> respuestas = new ArrayList<>();
    private String respuesta = new String();

    public ReversarReintegrosGeneralServletHelper(ReversarReintegrosSessionLocal session, ReversarReintegrosObjectContext objectContext, AutenticacionLdapWSProcesosSessionLocal autenticacionLdapWSProcesosSessionLocal, CargarNotasDebitoMasivosSessionLocal cargarNotasDebitoMasivosSessionLocal, ReintegrosSessionLocal reintegrosSessionLocal, ReintegrosNotasProcesosSessionLocal reintegrosNotasProcesosSessionLocal, ProcesoReintegrosSessionLocal procesoReintegrosSessionLocal, ConfModulosAplicacionLocal confModulosAplicacionLocal) {
        this.session = session;
        this.objectContext = objectContext;
        this.respuestaJSon = "";
        this.autenticacionLdapWSProcesosSessionLocal = autenticacionLdapWSProcesosSessionLocal;
        this.cargarNotasDebitoMasivosSessionLocal = cargarNotasDebitoMasivosSessionLocal;
        this.reintegrosSessionLocal = reintegrosSessionLocal;
        this.reintegrosNotasProcesosSessionLocal = reintegrosNotasProcesosSessionLocal;
        this.procesoReintegrosSessionLocal = procesoReintegrosSessionLocal;
        this.confModulosAplicacionSession = confModulosAplicacionLocal;
    }

    public String obtenerDatos() {
        respuestaJSon = "";
        String respuesta = "";
        try {
            try {

                //DESCOMENTAREAR SOLO PARA PRUEBAS
                if (getAutenticacion()) {
                    actualizarJsonSelecionados(objectContext.getAtributoReintegrosReversados());
                } else {
                    return "Acceso denegado.";
                }

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
        return respuestas.toString();
    }

    private void actualizarJsonSelecionados(List<ReintegrosRevisionDTO> listaData) {
        if (listaData != null) {
            respuesta += "Iniciando proceso de reversar reintegros:";
            for (ReintegrosRevisionDTO reintegro : listaData) {
                respuesta = new String();
                if (reintegro.getEstadoReintegro() == EstadoReintegro.REVERSADOINICIADO.codigo) {
                    respuesta += "Reversando reintegro => Codigo del cajero: " + reintegro.getCodigoCajero() + ", fecha:" + reintegro.fecha + ", cuenta:" + reintegro.getNumeroCuenta() + ", valor:" + reintegro.valor;
                    boolean estadoTransaccion = crearNotaDebitoReintegro(reintegro);
                    respuesta += estadoTransaccion ? "***REVERSADO***" : "***FALLIDO***";
                    this.respuestas.add(respuesta);
                }
            }
        }
    }

    private boolean crearNotaDebitoReintegro(ReintegrosRevisionDTO reintegro) {
        Date fechaReintegroReversado = Fecha.getDateHoy();
        String usuario = objectContext.getIdUsuarioEnSesion();
        boolean estadoTransaccion = false;
        try {
            String mensaje = GuardarStratusNotaDebito(new Integer(reintegro.getCodigoCajero().replaceAll("\\D+", "")), new BigDecimal(reintegro.getValor().replaceAll("\\D+", "")), reintegro.getNumeroCuenta().replaceAll("\\D+", ""), reintegro.getTipoCuenta(),reintegro.getConcepto());
            if (mensaje.substring(0, 1).equals("B")) {
                respuesta += " Reintegro aplicado correctamente en stratus,";
                estadoTransaccion = guardarNotaDebito(reintegro, fechaReintegroReversado, usuario);
                if (estadoTransaccion) {
                    estadoTransaccion = actualizarReintegro(reintegro, fechaReintegroReversado);
                }
            } else {
                respuesta += " Reversar reintegro fallido, no se pudo aplicar en stratus la nota debito.";
            }
        } catch (Exception ex) {
            respuesta += " Error interno en la consulta ";
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage());
        }
        return estadoTransaccion;
    }

    public String GuardarStratusNotaDebito(Integer codigoCajero, BigDecimal valor, String cuenta, Integer tipoCuenta, String concepto) {
        String respuesta = "";
        String tipoNota = "Nota Debito ";
        String strExepcion = "";
        try {
            String usuario = objectContext.getUsuarioEnSesion().getUsuario();
            respuesta = reintegrosNotasProcesosSessionLocal.realizarNotaDebito(codigoCajero, valor, cuenta, tipoCuenta, usuario, concepto);
            tipoNota = tipoNota + TipoCuenta.getTipoCuenta(tipoCuenta).nombre;
            objectContext.getConfigApp().loggerApp.info("El usuario : " + usuario
                    + "  realizo una " + tipoNota + "  al cajero " + codigoCajero.toString()
                    + " por valor : " + valor.toString()
                    + " a la cuenta : " + cuenta
                    + " con respuesta:" + respuesta);
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("B")) {
                        respuesta = respuesta + " " + tipoNota + " Realizada con Exito";
                    } else if (respuesta.substring(0, 1).equals("M")) {
                        respuesta = respuesta + " NO se pudo Realizar  la " + tipoNota;
                    } else if (respuesta.substring(0, 1).equals("F")) {
                        respuesta = respuesta + " Por favor verificar el  Estado de la " + tipoNota;
                    }
                } else {
                    respuesta = respuesta + " Por favor verificar el  Estado de la " + tipoNota;
                }
            } else {
                respuesta = respuesta + " Por favor verificar el  Estado de la " + tipoNota;
            }
        } catch (EJBException ex) {
            if (ex.getMessage() == null) {
                strExepcion = ex.getCause().getMessage();
            } else {
                strExepcion = ex.getMessage();
            }
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, strExepcion);
            objectContext.setError(strExepcion, CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), strExepcion);

        } catch (Exception ex) {
            objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            respuesta = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
            respuestaJSon += JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), "Error interno en la consulta " + ex.getMessage());
        }
        return respuesta;
    }

    private boolean getAutenticacion() {
        boolean blnAutorizado = false;
        String respuesta = "";
        String grupoSaraNotaCredito = "";
        String[] gruposUsuario;
        try {
            grupoSaraNotaCredito = getParametro("SARA.GRUPO_NOTA_CREDITO", "");
            respuesta = autenticacionLdapWSProcesosSessionLocal.autenticarLdap(objectContext.getUsuarioLdap(), objectContext.getClaveLdap());
            if (respuesta != null) {
                if (respuesta.length() > 0) {
                    if (respuesta.substring(0, 1).equals("B")) {
                        gruposUsuario = respuesta.split(";");
                        for (int i = 0; i < gruposUsuario.length; i++) {
                            if (grupoSaraNotaCredito.equals(gruposUsuario[i])) {
                                blnAutorizado = true;
                                // respuestaJSon= JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.codigo, gruposUsuario[i]);
                            }
                        }
                    } else {
                        respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.codigo, respuesta);

                    }
                }
            }
        } catch (Exception ex) {
            objectContext.setError(ex.getMessage(), CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
            respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), ex.getMessage());
        }

        return blnAutorizado;

    }

    private String getParametro(String strParametro, String strValorDefault) {
        String strValParametro = "";
        try {
            ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", strParametro);
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            strValParametro = registroEntityConsulta.getValor();

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger("globalApp").info("Error obteniendo el valor del parametro: " + strParametro + "  " + ex.getMessage());
            strValParametro = strValorDefault;
        }
        return strValParametro;
    }

    private boolean guardarNotaDebito(ReintegrosRevisionDTO reintegro, Date fechaReintegroReversado, String usuario) {
        try {
            Notasdebito newNotaDebito = new Notasdebito();
            NotasdebitoPK newNotaDebitoPK = new NotasdebitoPK();
            newNotaDebitoPK.setCodigocajero(new Integer(reintegro.getCodigoCajero()));
            newNotaDebitoPK.setFecha(fechaReintegroReversado);
            newNotaDebito.setNotasdebitoPK(newNotaDebitoPK);
            newNotaDebito.setUsuariocrea(usuario);
            newNotaDebito.setCodigoocca(new Integer(reintegro.getCodigoOcca()));
            newNotaDebito.setNumerocuenta(reintegro.getNumeroCuenta());
            newNotaDebito.setValor(new Long(reintegro.getValor().replaceAll("\\D+", "")));
            newNotaDebito.setValorajustado(new Long(reintegro.getValor().replaceAll("\\D+", "")));
            newNotaDebito.setEstado(EstadoReintegro.FINALIZADOEXITOSO.getEstado());
            newNotaDebito.setTipocuenta(reintegro.getTipoCuenta());
            newNotaDebito.setUsuarioautoriza(usuario);
            newNotaDebito.setError("Nota Débito/Reintegro aplicada éxitosamente.");
            newNotaDebito.setFechaaplica(fechaReintegroReversado);
            newNotaDebito.setTalon(Cadena.aInteger(reintegro.getNumeroTransaccion()));
            newNotaDebito.setUsuarioautoriza(usuario);
            newNotaDebito.setFechaaplica(fechaReintegroReversado);
            newNotaDebito.setConcepto(reintegro.getConcepto());
            newNotaDebito.setComision(reintegro.getComision());
            procesoReintegrosSessionLocal.guardarNotaDebito(newNotaDebito);
            respuesta += " Nota débito guardada correctamente en el sistema SARA, ";
            return true;
        } catch (Exception ex) {
            respuesta += " Error Nota débito no pudo ser guardada al sistema sara, ";
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage());
            return false;
        }
    }

    private boolean actualizarReintegro(ReintegrosRevisionDTO reintegro, Date fechaReversado) {
        String usuario = "";
        usuario = objectContext.getIdUsuarioEnSesion();
        Reintegros regReintegros = new Reintegros();
        regReintegros.setReintegrosPK(new ReintegrosPK());
        boolean estadoTransaccion = false;
        Date fechaProceso = Cadena.aDate(reintegro.getFecha(), FormatoFecha.FECHA_HORA);
        try {
            regReintegros = findByPrimayKey(Cadena.aInteger(reintegro.getCodigoCajero()), fechaProceso, Cadena.aInteger(reintegro.getNumeroTransaccion()));
            regReintegros.setErrorreintegro("Reintegro reversado exitosamente");
            regReintegros.setUsuarioautoriza(usuario);
            regReintegros.setEstadoreintegro(EstadoReintegro.REVERSADOEXITOSO.codigo);
            regReintegros.setFechareversado(fechaReversado);
            procesoReintegrosSessionLocal.actualizar(regReintegros);
            respuesta += " Reintegro actualizado éxitosamente.";
            estadoTransaccion = true;
        } catch (Exception ex) {
            respuesta += " Error interno al actualizar el reintegro, ";
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage());
        }
        return estadoTransaccion;
    }

    //obtengo el reintegro pro la llave formada por  codigoCajero , fechaProceso , fechaProceso
    private Reintegros findByPrimayKey(Integer codigoCajero, Date fechaProceso, Integer numeroTransaccion) throws Exception {
        Reintegros regReintegros = null;
        try {
            Date fechaHisto = objectContext.getConfigApp().FECHA_HISTORICAS_CONSULTA;
            regReintegros = session.findByPrimayKey(codigoCajero, fechaProceso, numeroTransaccion, fechaHisto);
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
        }

        return regReintegros;
    }
}
