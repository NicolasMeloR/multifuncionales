package com.davivienda.utilidades.ws.gestor.cliente;
//old:package com.davivienda.multifuncional.clientews;

import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.AjustarSobranteCajeroDto;
import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ConsultaManejoEfectivoATMDto;
import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.RespAjustarSobranteCajeroDto;
import com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.RespuestaConsultaManejoEfectivoATMDto;
import com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap.AutenticarUsuarioDTO;
import com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap.GrupoDaviviendaDTO;
import com.davivienda.utilidades.ws.cliente.autenticarUsuarioLdap.RespuestaAutenticarUsuarioDTO;
import com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional.ConsultaTotalesMultifuncionalDto;
import com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional.RespuestaConsultaTotalesMultifuncionalDto;
import com.davivienda.utilidades.ws.cliente.egresosVarios.EgresosVariosRespType;
import com.davivienda.utilidades.ws.cliente.egresosVarios.EgresosVariosType;
import com.davivienda.utilidades.ws.cliente.envioTransportadora.EnvioTransportadoraDto;
import com.davivienda.utilidades.ws.cliente.envioTransportadora.RespuestaDisminucionProvisionDto;
import com.davivienda.utilidades.ws.cliente.informeTotalesATM.InformeTotalesATMDto;
import com.davivienda.utilidades.ws.cliente.informeTotalesATM.RespuestaInformeTotalesATMDto;
import com.davivienda.utilidades.ws.cliente.ingresosVarios.IngresosVariosRespType;
import com.davivienda.utilidades.ws.cliente.ingresosVarios.IngresosVariosType;
import com.davivienda.utilidades.ws.cliente.nDebitoCosto.NotaDebitoCostoResponseType;
import com.davivienda.utilidades.ws.cliente.nDebitoCosto.NotaDebitoCostoType;
import com.davivienda.utilidades.ws.cliente.notaCredito.NotaCreditoResponseType;
import com.davivienda.utilidades.ws.cliente.notaCredito.NotaCreditoType;
import com.davivienda.utilidades.ws.cliente.notaCredito.ProductoAhorroCorrienteType;
import com.davivienda.utilidades.ws.cliente.notaCreditoFM.NotaCreditoFMDto;
import com.davivienda.utilidades.ws.cliente.notaCreditoFM.RespuestaNotaCreditoFMDto;
import com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito.NotaCreditoTarjetaCreditoDto;
import com.davivienda.utilidades.ws.cliente.notaCreditoTarjetaCredito.RespuestaNotaCreditoTarjetaCreditoDto;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.AnulacionChequesGdosDto;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.IngresoGiroChqDto;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.RespuestaAnulacionChequesGdosDto;
import com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.RespuestaIngresoGiroChqDto;
import com.davivienda.utilidades.ws.cliente.resumenIDOATM.RespuestaResumenIDOATMDto;
import com.davivienda.utilidades.ws.cliente.resumenIDOATM.ResumenIDOATMDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
import com.davivienda.multifuncional.config.SaraConfigServicios;
import com.davivienda.procesadortransacciones.notacreditofminterface.NotaCreditoFMDto;
import com.davivienda.procesadortransacciones.notacreditofminterface.RespuestaNotaCreditoFMDto;
import com.davivienda.procesadortransacciones.notacreditotarjetacreditointerface.RespuestaNotaCreditoTarjetaCreditoDto;
import com.davivienda.procesadortransacciones.servicios.enviotransportadorainterface.RespuestaDisminucionProvisionDto ;
import com.davivienda.autenticacionldap.servicio.RespuestaAutenticarUsuarioDTO;
import com.davivienda.autenticacionldap.servicio.AutenticarUsuarioDTO;
import com.davivienda.autenticacionldap.servicio.GrupoDaviviendaDTO;
import com.davivienda.procesadortransacciones.impl.servicios.otrosingresosegresos.AnulacionChequesGdosDto;
import com.davivienda.procesadortransacciones.impl.servicios.otrosingresosegresos.IngresoGiroChqDto;
import com.davivienda.procesadortransacciones.impl.servicios.otrosingresosegresos.RespuestaAnulacionChequesGdosDto;
import com.davivienda.procesadortransacciones.impl.servicios.otrosingresosegresos.RespuestaIngresoGiroChqDto;
import com.davivienda.procesadortransacciones.impl.servicios.resumenidoatm.RespuestaResumenIDOATMDto;
import com.davivienda.procesadortransacciones.impl.servicios.resumenidoatm.ResumenIDOATMDto;
import com.davivienda.procesadortransacciones.notacreditotarjetacreditointerface.NotaCreditoTarjetaCreditoDto;
import com.davivienda.procesadortransacciones.servicios.consultatotalesmultifuncional.RespuestaConsultaTotalesMultifuncionalDto;
import com.davivienda.procesadortransacciones.servicios.consultatotalesmultifuncional.ConsultaTotalesMultifuncionalDto;
import com.davivienda.procesadortransacciones.servicios.enviotransportadorainterface.EnvioTransportadoraDto;
import com.davivienda.procesadortransacciones.servicios.solicitudnotacreditoctaahorrosinterface.RespuestaNotaCreditoCtaAhorrosDto;
import com.davivienda.procesadortransacciones.servicios.solicitudnotacreditoctaahorrosinterface.SolicitudNotaCreditoCtaAhorrosDto;
import com.davivienda.procesadortransacciones.servicios.solicitudnotacreditoctacorrienteinterface.RespuestaNotaCreditoCtaCorrienteDto;
import com.davivienda.procesadortransacciones.servicios.solicitudnotacreditoctacorrienteinterface.SolicitudNotaCreditoCtaCorrienteDto;
import com.davivienda.procesadortransacciones.servicios.solictarnotadebitocuentaahorrosserviceinteface.RespuestaNotaDebitoCuentaAhorrosDto;
import com.davivienda.procesadortransacciones.servicios.solictarnotadebitocuentaahorrosserviceinteface.SolicitudNotaDebitoCuentaAhorrosDto;
import com.davivienda.procesadortransacciones.servicios.solictarnotadebitocuentacorrienteinterface.RespuestaNotaDebitoCuentaCorrienteDto;
import com.davivienda.procesadortransacciones.servicios.solictarnotadebitocuentacorrienteinterface.SolicitudNotaDebitoCuentaCorrienteDto;
 */
/**
 * InvocacionServicios - 17/07/2009 Descripci n : Clase para invocar los
 * requerimientos de procesos al host por medio de los clientes web service Es
 * la que es llamada desde Sara-ejb Versi n : 1.0
 *
 * @author mdruiz 2009
 */
public class InvocacionServicios {

    // C digo de la terminal de caja con que se realizaran las peticiones a host
    private Integer terminalCaja;

    /**
     * Constructor de la clase
     *
     * @param servidorWS Direcci n IP o nombre del servidor de plataforma de
     * servicios
     * @param puertoWS Puerto del servidor de plataforma de servicios,
     * normalmente es 80
     * @param terminalCaja C digo de la terminal de caja con que se realizan las
     * peticiones a host
     */
    public InvocacionServicios(String servidorWS, String puertoWS, Integer terminalCaja) {
        SaraConfigServicios.SERVIDOR_WS = servidorWS;
        SaraConfigServicios.PUERTO_SERVIDOR_WS = puertoWS;
        this.terminalCaja = terminalCaja;
    }

    private String armarReferencia16Caracteres(String referencia) {

        for (int i = 0; referencia.length() < 16; i++) {
            referencia = "0" + referencia;
        }
        return referencia;
    }

    public String autenticarLdap(String usuario, String clave) throws DatatypeConfigurationException {
        String respuesta;
        AutenticarUsuarioDTO datosRequerimiento = new AutenticarUsuarioDTO();
        RespuestaAutenticarUsuarioDTO resp = null;
        datosRequerimiento.setCanal(new Short("0"));
        datosRequerimiento.setNombre(usuario);
        datosRequerimiento.setNombreDeUsuario(usuario);
        datosRequerimiento.setClave(clave);
        AutenticacionLdap cliente = new AutenticacionLdap();
        try {
            resp = cliente.autenticarUsuario(datosRequerimiento);
            respuesta = crearCadenaRespuestaAutentica(resp.getCaracterAcepta(), resp.getGruposUsuario());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;

    }

    private String crearCadenaRespuestaAutentica(String caraterRespuesta, List<GrupoDaviviendaDTO> grupoDaviviendaDTO) {
        String respuesta = "";
        respuesta = caraterRespuesta;
        if (caraterRespuesta.equals("B")) {
            for (int i = 0; i < grupoDaviviendaDTO.size(); i++) {
                respuesta = respuesta + ";" + grupoDaviviendaDTO.get(i).getCn();
            }
        } else {
            respuesta = respuesta + "; Datos de Autenticacion Errados";
        }
        return respuesta;
    }

    public String realizarNotaCreditoFM(Integer codigoCajero, Integer codigoOcca, BigDecimal valor, String fechaMovimiento, Integer talon, String credito, String usuario) throws DatatypeConfigurationException {

        String respuesta;
        NotaCreditoFMDto datosRequerimiento = new NotaCreditoFMDto();
        RespuestaNotaCreditoFMDto resp = null;
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setUsuario(usuario);
        String year = fechaMovimiento.substring(0, 4);
        String month = fechaMovimiento.substring(4, 6);
        String day = fechaMovimiento.substring(6);
        DatatypeFactory factory = DatatypeFactory.newInstance();
        XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day),
                0, 0, 0, 0, 0);
        datosRequerimiento.setIdTarea("FM01");
        datosRequerimiento.setCanal(new Short("26"));
        datosRequerimiento.setConcepto(new Short("117"));
        datosRequerimiento.setFechaAplicacion(fecha.normalize());
        datosRequerimiento.setCredito(credito);
        datosRequerimiento.setValorEfectivo(new BigDecimal(valor.intValue() * 100));
        datosRequerimiento.setTalon(talon);
        NotaCreditoFM cliente = new NotaCreditoFM();
        try {
            resp = cliente.realizarCreditoFM(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;

    }

    public String realizarNotaCreditoTarjetaCredito(long numeroTarjeta, Integer numeroDocumento,
            BigDecimal valor, String fechaAplicacion,
            long referencia1, long referencia2) throws DatatypeConfigurationException {

        String respuesta;
        NotaCreditoTarjetaCreditoDto datosRequerimiento = new NotaCreditoTarjetaCreditoDto();
        RespuestaNotaCreditoTarjetaCreditoDto resp = null;
        datosRequerimiento.setTotal(this.terminalCaja);
        String year = fechaAplicacion.substring(0, 4);
        String month = fechaAplicacion.substring(4, 6);
        String day = fechaAplicacion.substring(6);
        DatatypeFactory factory = DatatypeFactory.newInstance();
        XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day),
                0, 0, 0, 0, 0);
        datosRequerimiento.setNumeroTarjeta(numeroTarjeta);
        datosRequerimiento.setNumeroDocumento(numeroDocumento);
        datosRequerimiento.setReferencia1(referencia1);
        datosRequerimiento.setReferencia2(referencia2);
        datosRequerimiento.setIdTarea("CC02");
        datosRequerimiento.setCanal(new Short("26"));
        datosRequerimiento.setConcepto(new Short("0352"));
        datosRequerimiento.setFechaAplicacion(fecha.normalize());
        datosRequerimiento.setValor(new BigDecimal(valor.intValue() * 100));
        NotaCreditoTarjetaCredito cliente = new NotaCreditoTarjetaCredito();
        try {
            resp = cliente.realizarCreditoTarjetaCredito(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;

    }

    public String realizarEnvioTransportadora(long codigoTransportadora, BigDecimal valorEfectivo, Integer talon) throws DatatypeConfigurationException {

        String respuesta;
        EnvioTransportadoraDto datosRequerimiento = new EnvioTransportadoraDto();
        RespuestaDisminucionProvisionDto resp = null;
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setCodigoTransportadora(codigoTransportadora);
        datosRequerimiento.setValorEfectivo(valorEfectivo.multiply(new BigDecimal(100)));
        datosRequerimiento.setTalon(talon);
        datosRequerimiento.setCantidadRegistros(new Short("1"));
        datosRequerimiento.setIndicadorTransaccion(new Short("2"));
        datosRequerimiento.setIdTarea("OT11");
        datosRequerimiento.setCanal(new Short("26"));
        EnvioTransportadora cliente = new EnvioTransportadora();
        try {
            resp = cliente.realizarEnvioTransportadora(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;

    }

    public String realizarNotaCreditoCtaAhorros(Integer codigoCajero, Integer codigoOcca,
            BigDecimal valor, String fechaMovimiento, String talon,
            String cuenta, String usuario, String concepto, String canal) throws DatatypeConfigurationException {

        String respuesta;
        com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM.SolicitudNotaCreditoCtaAhorrosDto datosRequerimiento = new com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM.SolicitudNotaCreditoCtaAhorrosDto();
        com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorrosServiceATM.RespuestaNotaCreditoCtaAhorrosDto resp = null;
        datosRequerimiento.setCanal(new Short(canal));
        datosRequerimiento.setIdTarea("AH09");
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setUsuario(usuario);
        String year = fechaMovimiento.substring(0, 4);
        String month = fechaMovimiento.substring(4, 6);
        String day = fechaMovimiento.substring(6);
        DatatypeFactory factory = DatatypeFactory.newInstance();
        XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day),
                0, 0, 0, 0, 0);
        datosRequerimiento.setConcepto(concepto);
        datosRequerimiento.setFechaAplicacion(fecha.normalize());
        datosRequerimiento.setCuentaChequera(completarCuenta(cuenta));
        datosRequerimiento.setReferencia1(armarReferencia16Caracteres(codigoCajero.toString()));
        datosRequerimiento.setValorNotaCredito(new BigDecimal(valor.intValue() * 100));
        datosRequerimiento.setOficinaRecaudo(codigoOcca.toString());
        datosRequerimiento.setTalon(talon);
        SolicitudNotaCreditoCtaAhorros cliente = new SolicitudNotaCreditoCtaAhorros();
        try {
            resp = cliente.realizarNotaCreditoCtaAhorros(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());

        }
        return respuesta;

    }

    /**
     * Método para realizar nota credito cta de ahorros
     *
     * @param codigoCajero
     * @param valor
     * @param fechaMovimiento
     * @param talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @param cuenta
     * @return
     * @throws
     * com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros.ServicioException_Exception
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    public String realizarNotaCreditoCtaAhorros(Integer codigoCajero, BigDecimal valor, String fechaMovimiento, String talon, String cuenta) throws com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros.ServicioException_Exception, DatatypeConfigurationException {
        String respuesta;
        com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros.SolicitudNotaCreditoCtaAhorrosDto datosRequerimiento = new com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros.SolicitudNotaCreditoCtaAhorrosDto();
        com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaAhorros.RespuestaNotaCreditoCtaAhorrosDto resp = null;
        datosRequerimiento.setCanal(new Short("0"));
        datosRequerimiento.setIdTarea("AH09");
        datosRequerimiento.setTotal(this.terminalCaja);

        String year = fechaMovimiento.substring(0, 4);
        String month = fechaMovimiento.substring(4, 6);
        String day = fechaMovimiento.substring(6);
        DatatypeFactory factory = DatatypeFactory.newInstance();

        XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day),
                0, 0, 0, 0, 0);

        datosRequerimiento.setConcepto("16");
        datosRequerimiento.setFechaAplicacion(fecha);
        datosRequerimiento.setCuentaChequera(cuenta);
        datosRequerimiento.setReferencia1(codigoCajero.toString());
        datosRequerimiento.setValorNotaCredito(valor);

        NotaCreditoCuentaAhorros cliente = new NotaCreditoCuentaAhorros();
        resp = cliente.realizarNotaDebito(datosRequerimiento);
        try {
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());

        }
        return respuesta;

    }

    public String realizarNotaCreditoCtaCorriente(Integer codigoCajero, Integer codigoOcca, BigDecimal valor,
            String fechaMovimiento, String talon, String cuenta,
            String usuario, String concepto, String canal) throws DatatypeConfigurationException {

        String respuesta;
        com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM.SolicitudNotaCreditoCtaCorrienteDto datosRequerimiento = new com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM.SolicitudNotaCreditoCtaCorrienteDto();
        com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorrienteServiceATM.RespuestaNotaCreditoCtaCorrienteDto resp = null;
        datosRequerimiento.setCanal(new Short(canal));
        datosRequerimiento.setIdTarea("BC09");
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setUsuario(usuario);
        String year = fechaMovimiento.substring(0, 4);
        String month = fechaMovimiento.substring(4, 6);
        String day = fechaMovimiento.substring(6);
        DatatypeFactory factory = DatatypeFactory.newInstance();
        XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day),
                0, 0, 0, 0, 0);
        datosRequerimiento.setConcepto(concepto);
        datosRequerimiento.setFechaAplicacion(fecha);
        datosRequerimiento.setCuentaChequera(completarCuenta(cuenta));
        datosRequerimiento.setTalon(talon);
        datosRequerimiento.setReferencia1(armarReferencia16Caracteres(codigoCajero.toString()));
        datosRequerimiento.setValorNotaCredito(new BigDecimal(valor.intValue() * 100));
        datosRequerimiento.setOficinaRecaudo(codigoOcca.toString());
        SolicitudNotaCreditoCtaCorriente cliente = new SolicitudNotaCreditoCtaCorriente();
        try {
            resp = cliente.realizarNotaCreditoCtaCorrienteDto(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    /**
     * Método para realizar nota credito cta de corriente
     *
     * @param codigoCajero
     * @param valor
     * @param fechaMovimiento
     * @param talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @param cuenta
     * @return
     * @throws
     * com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente.ServicioException_Exception
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    public String realizarNotaCreditoCtaCorriente(Integer codigoCajero, BigDecimal valor, String fechaMovimiento, String talon, String cuenta) throws com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente.ServicioException_Exception, DatatypeConfigurationException {
        String respuesta;
        com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente.SolicitudNotaCreditoCtaCorrienteDto datosRequerimiento = new com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente.SolicitudNotaCreditoCtaCorrienteDto();
        com.davivienda.utilidades.ws.cliente.solicitudNotaCreditoCtaCorriente.RespuestaNotaCreditoCtaCorrienteDto resp = null;
        datosRequerimiento.setCanal(new Short("0"));
        datosRequerimiento.setIdTarea("BC09");
        datosRequerimiento.setTotal(this.terminalCaja);

        String year = fechaMovimiento.substring(0, 4);
        String month = fechaMovimiento.substring(4, 6);
        String day = fechaMovimiento.substring(6);
        DatatypeFactory factory = DatatypeFactory.newInstance();

        XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day),
                0, 0, 0, 0, 0);

        datosRequerimiento.setConcepto("16");
        datosRequerimiento.setFechaAplicacion(fecha);
        datosRequerimiento.setCuentaChequera(cuenta);
        datosRequerimiento.setTalon(talon);
        datosRequerimiento.setReferencia1(codigoCajero.toString());
        datosRequerimiento.setValorNotaCredito(valor);

        NotaCreditoCuentaCorriente cliente = new NotaCreditoCuentaCorriente();
        resp = cliente.realizarNotaDebito(datosRequerimiento);
        try {
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());

        }
        return respuesta;
    }

    public String realizarNotaDebitoCtaAhorros(Integer codigoCajero, BigDecimal valor, String fechaMovimiento,
            String talon, String cuenta, String usuario, String concepto, String canal) throws DatatypeConfigurationException {

        String respuesta;
        com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM.SolicitudNotaDebitoCuentaAhorrosDto datosRequerimiento = new com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM.SolicitudNotaDebitoCuentaAhorrosDto();
        com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaAhorrosServiceATM.RespuestaNotaDebitoCuentaAhorrosDto resp = null;
        datosRequerimiento.setCanal(new Short(canal));
        datosRequerimiento.setIdTarea("AH10");
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setUsuario(usuario);
        String year = fechaMovimiento.substring(0, 4);
        String month = fechaMovimiento.substring(4, 6);
        String day = fechaMovimiento.substring(6);
        DatatypeFactory factory = DatatypeFactory.newInstance();

        XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day),
                0, 0, 0, 0, 0);
        datosRequerimiento.setConcepto(new Short(concepto));
        datosRequerimiento.setFechaMovimiento(fecha);
        datosRequerimiento.setNumeroCuentaAhorros(completarCuenta(cuenta));
        datosRequerimiento.setOrigenFondos(new Short("3"));
        datosRequerimiento.setReferenciaUno(armarReferencia16Caracteres(codigoCajero.toString()));
        datosRequerimiento.setSecuencialRegistro(2);
        datosRequerimiento.setValor(new BigDecimal(valor.intValue() * 100));
        SolicitarNotaDebitoCtaAhorros cliente = new SolicitarNotaDebitoCtaAhorros();
        try {
            resp = cliente.realizarNotaDebitoCuentaAhorros(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());

        }
        return respuesta;
    }

    /**
     * Método para realizar nota debito cta de ahorros
     *
     * @param codigoCajero
     * @param valor
     * @param fechaMovimiento
     * @param talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @param cuenta
     * @return
     * @throws
     * com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros.ServicioException_Exception
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    public String realizarNotaDebitoCtaAhorros(Integer codigoCajero, BigDecimal valor, String fechaMovimiento, String talon, String cuenta) throws com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros.ServicioException_Exception, DatatypeConfigurationException {
        String respuesta;
        com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros.SolicitudNotaDebitoCuentaAhorrosDto datosRequerimiento = new com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros.SolicitudNotaDebitoCuentaAhorrosDto();
        com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaAhorros.RespuestaNotaDebitoCuentaAhorrosDto resp = null;
        datosRequerimiento.setCanal(new Short("0"));
        datosRequerimiento.setIdTarea("AH10");
        datosRequerimiento.setTotal(this.terminalCaja);

        String year = fechaMovimiento.substring(0, 4);
        String month = fechaMovimiento.substring(4, 6);
        String day = fechaMovimiento.substring(6);
        DatatypeFactory factory = DatatypeFactory.newInstance();

        XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day),
                0, 0, 0, 0, 0);

        datosRequerimiento.setConcepto(new Short("16"));
        datosRequerimiento.setFechaMovimiento(fecha);
        datosRequerimiento.setNumeroCuentaAhorros(cuenta);
        datosRequerimiento.setOrigenFondos(new Short("3"));
        datosRequerimiento.setReferenciaUno(codigoCajero.toString());

//        datosRequerimiento.setReferenciaUno("997");
//        datosRequerimiento.setReferenciaDos("997");
        datosRequerimiento.setValor(valor);

        //ahorros 3,corriente 1
        NotaDebitoCuentaAhorros cliente = new NotaDebitoCuentaAhorros();
        resp = cliente.realizarNotaDebito(datosRequerimiento);
        try {
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());

        }
        return respuesta;
    }

    public String realizarNotaDebitoCtaCorriente(Integer codigoCajero, BigDecimal valor, String fechaMovimiento,
            String talon, String cuenta, String usuario, String concepto, String canal) throws DatatypeConfigurationException {

        String respuesta;
        com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM.SolicitudNotaDebitoCuentaCorrienteDto datosRequerimiento = new com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM.SolicitudNotaDebitoCuentaCorrienteDto();
        com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCuentaCorrienteServiceATM.RespuestaNotaDebitoCuentaCorrienteDto resp = null;
        datosRequerimiento.setCanal(new Short(canal));
        datosRequerimiento.setIdTarea("BC10");
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setUsuario(usuario);
        String year = fechaMovimiento.substring(0, 4);
        String month = fechaMovimiento.substring(4, 6);
        String day = fechaMovimiento.substring(6);
        DatatypeFactory factory = DatatypeFactory.newInstance();
        XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day),
                0, 0, 0, 0, 0);
        datosRequerimiento.setConcepto(new Short(concepto));
        datosRequerimiento.setFechaMovimiento(fecha);
        datosRequerimiento.setNumeroCuentaCorriente(cuenta);
        datosRequerimiento.setOrigenFondos(new Short("1"));
        datosRequerimiento.setReferenciaUno(armarReferencia16Caracteres(codigoCajero.toString()));
        datosRequerimiento.setSecuencialRegistro(2);
        datosRequerimiento.setValor(new BigDecimal(valor.intValue() * 100));
        SolicitarNotaDebitoCtaCorriente cliente = new SolicitarNotaDebitoCtaCorriente();
        try {
            resp = cliente.realizarNotaDebitoCuentaCorriente(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;

    }

    /**
     * Método para realizar nota debito cta de ahorros
     *
     * @param codigoCajero
     * @param valor
     * @param fechaMovimiento
     * @param talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @param cuenta
     * @return
     * @throws
     * com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaCorriente.ServicioException_Exception
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    public String realizarNotaDebitoCtaCorriente(Integer codigoCajero, BigDecimal valor, String fechaMovimiento, String talon, String cuenta) throws com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaCorriente.ServicioException_Exception, DatatypeConfigurationException {
        String respuesta;
        com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaCorriente.SolicitudNotaDebitoCuentaCorrienteDto datosRequerimiento = new com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaCorriente.SolicitudNotaDebitoCuentaCorrienteDto();
        com.davivienda.utilidades.ws.cliente.solicitarNotaDebitoCtaCorriente.RespuestaNotaDebitoCuentaCorrienteDto resp = null;
        datosRequerimiento.setCanal(new Short("0"));
        datosRequerimiento.setIdTarea("BC10");
        datosRequerimiento.setTotal(this.terminalCaja);

        String year = fechaMovimiento.substring(0, 4);
        String month = fechaMovimiento.substring(4, 6);
        String day = fechaMovimiento.substring(6);
        DatatypeFactory factory = DatatypeFactory.newInstance();

        XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day),
                0, 0, 0, 0, 0);

        datosRequerimiento.setConcepto(new Short("16"));
        datosRequerimiento.setFechaMovimiento(fecha);
        datosRequerimiento.setNumeroCuentaCorriente(cuenta);
        datosRequerimiento.setOrigenFondos(new Short("1"));
        datosRequerimiento.setReferenciaUno(codigoCajero.toString());
        datosRequerimiento.setValor(valor);

        NotaDebitoCuentaCorriente cliente = new NotaDebitoCuentaCorriente();
        resp = cliente.realizarNotaDebito(datosRequerimiento);
        try {
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());

        }
        return respuesta;

    }

    public String realizarConsultaTotalesMultifuncional(Short tipoConsulta, Short indicadorTotales,
            Short canal) throws DatatypeConfigurationException {

        String respuesta;
        ConsultaTotalesMultifuncionalDto datosRequerimiento = new ConsultaTotalesMultifuncionalDto();
        RespuestaConsultaTotalesMultifuncionalDto resp = null;
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setIdTarea("OT01");
        datosRequerimiento.setCanal(canal);
        datosRequerimiento.setTipoConsulta(tipoConsulta);
        datosRequerimiento.setIndicadorTotales(indicadorTotales);
        ConsultaTotalesMultifuncional cliente = new ConsultaTotalesMultifuncional();

        try {
            resp = cliente.realizarConsultaTotalesMultifuncional(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getCaracterAcepta(), resp.getCodMRespuesta(), resp.getMRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;

    }

    public String ajustarEgreso(Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste,
            String concepto, String fechaAjuste, String talon, String nitTransportadora) throws com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.ServicioException_Exception {

        return this.ajustarEgreso(codigoOcca, valorAjuste, concepto,
                fechaAjuste, talon, Short.parseShort("26"),
                codigoCajero.toString(), armarReferencia16Caracteres(nitTransportadora));

    }

    /**
     * Método para realizar los ajustes de Egresos
     *
     * @param codigoCajero
     * @param codigoOcca
     * @param valorAjuste
     * @param fechaAjuste
     * @param talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @return
     * @throws
     * com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.ServicioException_Exception
     */
    public String ajustarEgreso(Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste, String fechaAjuste, String talon) throws com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.ServicioException_Exception {
        return this.ajustarEgreso(codigoOcca, valorAjuste, "35",
                fechaAjuste, talon, Short.parseShort("0"),
                "000000000000" + talon.substring(0, 4), codigoOcca.toString());

    }

    private String ajustarEgreso(Integer codigoOcca, BigDecimal valorAjuste,
            String concepto, String fechaAjuste, String talon,
            short canal, String ref1, String ref2) throws com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.ServicioException_Exception {

        AnulacionChequesGdosDto datosRequerimiento = new AnulacionChequesGdosDto();
        RespuestaAnulacionChequesGdosDto resp = null;
        datosRequerimiento.setCanal(canal);
        datosRequerimiento.setIdTarea("OT04");
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setConcepto(new Short(concepto));
        datosRequerimiento.setValorEfectivo(valorAjuste);
        datosRequerimiento.setValorTotal(valorAjuste);
        datosRequerimiento.setReferencia1(ref1);
        datosRequerimiento.setReferencia2(ref2);
        datosRequerimiento.setReferencia3(fechaAjuste);
        datosRequerimiento.setReferencia4("");
        datosRequerimiento.setReferencia5(codigoOcca.toString());
        datosRequerimiento.setTalon(talon);
        Otros_IngresosEgresos cliente = new Otros_IngresosEgresos();
        resp = cliente.ajustarEgreso(datosRequerimiento);
        return crearCadenaRespuesta(resp.getCaracterAcepta(), new Short("-1"));
    }

    public String ajustarIngreso(Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste,
            String concepto, String fechaAjuste, String talon, String nitTransportadora) throws com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.ServicioException_Exception {

        return this.ajustarIngreso(codigoOcca, valorAjuste, concepto, fechaAjuste, talon, new Short("26"), codigoCajero.toString(), armarReferencia16Caracteres(nitTransportadora));
    }

    /**
     * Método para realizar los ajustes de Ingresos
     *
     * @param codigoCajero
     * @param codigoOcca
     * @param valorAjuste
     * @param fechaAjuste
     * @param talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @return
     * @throws
     * com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.ServicioException_Exception
     */
    public String ajustarIngreso(Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste, String fechaAjuste, String talon) throws com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.ServicioException_Exception {
        return this.ajustarIngreso(codigoOcca, valorAjuste, "16", fechaAjuste, talon, new Short("0"), "000000000000" + talon.substring(0, 4), codigoOcca.toString());
    }

    private String ajustarIngreso(Integer codigoOcca, BigDecimal valorAjuste,
            String concepto, String fechaAjuste, String talon,
            short canal, String ref1, String ref2) throws com.davivienda.utilidades.ws.cliente.otrosIngresosEgresos.ServicioException_Exception {

        IngresoGiroChqDto datosRequerimiento = new IngresoGiroChqDto();
        RespuestaIngresoGiroChqDto resp = null;
        datosRequerimiento.setCanal(canal);
        datosRequerimiento.setIdTarea("OT04");
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setConcepto(new Short(concepto));
        datosRequerimiento.setValorEfectivo(valorAjuste);
        datosRequerimiento.setValorTotal(valorAjuste);
        datosRequerimiento.setReferencia1(ref1);
        datosRequerimiento.setReferencia2(ref2);
        datosRequerimiento.setReferencia3(fechaAjuste);
        datosRequerimiento.setReferencia4("");
        datosRequerimiento.setReferencia5(codigoOcca.toString());
        datosRequerimiento.setTalon(talon);
        Otros_IngresosEgresos cliente = new Otros_IngresosEgresos();
        resp = cliente.ajustarIngreso(datosRequerimiento);
        return crearCadenaRespuesta(resp.getCaracterAcepta(), new Short("-1"));
    }

    public ResumenAjustesMulti[] consultarInformeTotalesATM(Integer codigoCajero, Integer tipoConsulta, Integer indicadorTotales) throws com.davivienda.utilidades.ws.cliente.consultaTotalesMultifuncional.ServicioException_Exception {

        ResumenAjustesMulti[] resumen;
        ConsultaTotalesMultifuncionalDto datosRequerimiento = new ConsultaTotalesMultifuncionalDto();
        RespuestaConsultaTotalesMultifuncionalDto resp = null;
        datosRequerimiento.setCanal(new Short("26"));
        datosRequerimiento.setIdTarea("OT01");
        datosRequerimiento.setJornada(new Short("0"));
        datosRequerimiento.setTotal(codigoCajero);
        datosRequerimiento.setIndicadorTotales(indicadorTotales.shortValue());
        datosRequerimiento.setTipoConsulta(tipoConsulta.shortValue());
        ConsultaTotalesMultifuncional cliente = new ConsultaTotalesMultifuncional();
        resp = cliente.realizarConsultaTotalesMultifuncional(datosRequerimiento);
        resumen = new ResumenAjustesMulti[resp.getRegistrosRepetitivos().size()];
        for (int c = 0; c < resp.getRegistrosRepetitivos().size(); c++) {
            resumen[c] = new ResumenAjustesMulti();
            resumen[c].setCantidad(resp.getRegistrosRepetitivos().get(c).getCantidad());
            resumen[c].setValor(resp.getRegistrosRepetitivos().get(c).getValor());
            resumen[c].setTitulo(resp.getRegistrosRepetitivos().get(c).getTitulo());
        }
        return resumen;
    }

    private String completarCuenta(String cuenta) {
        String cuentaCompleta = "";
        int longitudCeros = 16 - cuenta.length();
        for (int i = 1; i <= longitudCeros; i++) {
            cuentaCompleta = cuentaCompleta + "0";
        }
        cuentaCompleta = cuentaCompleta + cuenta;
        return cuentaCompleta;
    }

    private String crearCadenaRespuesta(String caraterRespuesta, Short valorRespuesta) {
        String respuesta = "";
        respuesta = caraterRespuesta;
        if (valorRespuesta != (-1)) {
            respuesta = respuesta + valorRespuesta.toString();
        }
        return respuesta;
    }

    /**
     * Método para la consulta de resumen IDO de la Terminal de Caja
     *
     * @return RespuestaResumenIDOATMDto
     * @throws
     * com.davivienda.utilidades.ws.cliente.resumenIDOATM.ServicioException_Exception
     */
    public ResumenAjustes[] consultarResumenIDOTerminal() throws com.davivienda.utilidades.ws.cliente.resumenIDOATM.ServicioException_Exception {
        return consultarResumenIDO(new Short("0"));
    }

    /**
     * MÃ©todo para la consulta de resumen IDO de la oficina OCCA
     *
     * @return RespuestaResumenIDOATMDto
     * @throws
     * com.davivienda.utilidades.ws.cliente.resumenIDOATM.ServicioException_Exception
     */
    public ResumenAjustes[] consultarResumenIDOOficina() throws com.davivienda.utilidades.ws.cliente.resumenIDOATM.ServicioException_Exception {
        return consultarResumenIDO(new Short("1"));
    }

    /**
     * MÃ©todo para la consulta de resumen IDO
     *
     * @param indicadorConsulta [0] EstaciÃ³n de caja [1] Oficina OCCA
     * @return
     * @throws
     * com.davivienda.procesadortransacciones.impl.servicios.resumenidoatm.ServicioException_Exception
     */
    private ResumenAjustes[] consultarResumenIDO(Short indicadorConsulta) throws com.davivienda.utilidades.ws.cliente.resumenIDOATM.ServicioException_Exception {
        ResumenAjustes[] resumen;
        ResumenIDOATMDto datosRequerimiento = new ResumenIDOATMDto();
        RespuestaResumenIDOATMDto resp = null;
        datosRequerimiento.setCanal(new Short("0"));
        datosRequerimiento.setIdTarea("OT01");
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setIndicadorTotales(indicadorConsulta);
        ConsultaResumenIDO cliente = new ConsultaResumenIDO();
        resp = cliente.consultarResumenIDO(datosRequerimiento);
//        resp.getRegistrosRepetitivos().get(terminalCaja).getTituloMensaje();
//        resp.getRegistrosRepetitivos().get(terminalCaja).getCantidad();
//        resp.getRegistrosRepetitivos().get(terminalCaja).getValor();
        resumen = new ResumenAjustes[resp.getRegistrosRepetitivos().size()];
        for (int c = 0; c < resp.getRegistrosRepetitivos().size(); c++) {
            resumen[c] = new ResumenAjustes();
            resumen[c].setCantidad(resp.getRegistrosRepetitivos().get(c).getCantidad());
            resumen[c].setValor(resp.getRegistrosRepetitivos().get(c).getValor());
            resumen[c].setTitulo(resp.getRegistrosRepetitivos().get(c).getTituloMensaje());

        }
        return resumen;
    }

    private String crearCadenaRespuestaNota(String caracterRespuesta, short codMRespuesta, String mRespuesta) {
        String respuesta = "";
        respuesta = caracterRespuesta + codMRespuesta + ";" + mRespuesta;
        return respuesta;
    }

    public String ajustarIngresoESB(Integer codigoCajero, Integer codigoOcca, BigDecimal valor,
            String fechaMovimiento, String talon, String canal, short concepto, String nitTransportadora) {
        String nombreServicio = "IngresosVarios";
        String usuario = "DAV";
        //     short concepto = new Short("98");
        //BigDecimal efectivo = new BigDecimal(0.99);//probar0.00
        // BigDecimal cheques = new BigDecimal(353330.99);//probar0.00
        BigDecimal efectivo = new BigDecimal(0.00);//probar0.00
        BigDecimal cheques = new BigDecimal(0.00);//probar0.00
        String referencia1 = codigoCajero.toString();//codigoCajero
        String referencia2 = armarReferencia16Caracteres(nitTransportadora);//nitTransportadora
        String referencia3 = fechaMovimiento;//fechaMovimiento
        String referencia4 = "0";
        String referencia5 = codigoOcca.toString();//codigoOcca
        String beneficiario = "";//probar vacio o davivienda
        short codigoBanco = new Short("99");
        Integer numCheque = 0;//13999;//probar0
        String chequera = "0";//"950060839999";//probar0

        String respuesta;
        try {
            respuesta = ingresosVarios(nombreServicio, usuario, Short.parseShort(canal),
                    concepto, Integer.parseInt(talon), efectivo, cheques, valor, referencia1, referencia2,
                    referencia3, referencia4, referencia5, beneficiario, codigoBanco,
                    numCheque, chequera);
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    public String ingresosVarios(String nombreOperacion, String usuario, short canal,
            Short concepto, int talon, BigDecimal efectivo, BigDecimal cheques,
            BigDecimal valor, String referencia1, String referencia2, String referencia3,
            String referencia4, String referencia5, String beneficiario, Short codigoBanco,
            Integer numCheque, String chequera) throws DatatypeConfigurationException {

        String respuesta;
        IngresosVariosType datosRequerimiento = new IngresosVariosType();
        IngresosVariosRespType resp = null;
        com.davivienda.utilidades.ws.cliente.ingresosVarios.RequestType datosRequest = new com.davivienda.utilidades.ws.cliente.ingresosVarios.RequestType();
        com.davivienda.utilidades.ws.cliente.ingresosVarios.DataRequestType datos = new com.davivienda.utilidades.ws.cliente.ingresosVarios.DataRequestType();
        com.davivienda.utilidades.ws.cliente.ingresosVarios.DataHeaderRequestType datosHead = new com.davivienda.utilidades.ws.cliente.ingresosVarios.DataHeaderRequestType();

        datosRequest.setData(datos);
        datosRequerimiento.setRequest(datosRequest);

        //ENCABEZADO
        datosHead.setNombreOperacion(nombreOperacion);
        datosHead.setTotal(this.terminalCaja);
        datosHead.setJornada(new Short("0"));
        datosHead.setCanal(canal);
        datosHead.setModoDeOperacion(new Short("0"));
        datosHead.setUsuario(usuario);
        datosHead.setPerfil(new Short("0"));
        datosHead.setVersionServicio("1.1.0");

        //CUERPO PARA PRODUCTO
        datos.setConcepto(concepto);
        datos.setTalon(talon);
        datos.setEfectivo(efectivo.setScale(2, RoundingMode.UP));
        datos.setCheques(cheques.setScale(2, RoundingMode.UP));
        datos.setValorTotal(valor.setScale(2, RoundingMode.UP));
        datos.setReferencia1(referencia1);
        datos.setReferencia2(referencia2);
        datos.setReferencia3(referencia3);
        datos.setReferencia4(referencia4);
        datos.setReferencia5(referencia5);
        datos.setBeneficiario(beneficiario);
        datos.setCodigoBanco(codigoBanco);
        datos.setNumeroDeCheque(numCheque);
        datos.setChequera(chequera);

        datosRequest.setData(datos);
        datosRequest.setDataHeader(datosHead);

        datosRequerimiento.setRequest(datosRequest);

        IngresosVarios cliente = new IngresosVarios(); // caracter acpetacion M - Codigo 32000 - mensaje: Ha ocurrido un error inesperado

        try {
            resp = cliente.realizarIngresosVarios(datosRequerimiento);
            if (resp.getResponse() != null) {
                if (resp.getResponse().getDataHeader().getCaracterAceptacion().equals("B")) {
                    respuesta = crearCadenaRespuestaNota(resp.getResponse().getDataHeader().getCaracterAceptacion(),
                            resp.getResponse().getDataHeader().getCodMsgRespuesta(),
                            "Transaccion Exitosa.");
                } else {
                    respuesta = crearCadenaRespuestaNota(resp.getResponse().getDataHeader().getCaracterAceptacion(),
                            resp.getResponse().getDataHeader().getCodMsgRespuesta(),
                            resp.getResponse().getDataHeader().getMsgRespuesta());
                }
            } else {
                respuesta = crearCadenaRespuestaNota("M", new Short("32000"), "Ha ocurrido un error No Esperado en el ESB.");
            }
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    public String ajustarEgresoESB(Integer codigoCajero, Integer codigoOcca, BigDecimal valor,
            String fechaMovimiento, String talon, String canal, short concepto, String nitTransportadora) {

        String nombreServicio = "EgresosVarios";
        String usuario = "DAV";//"SUN";
        //BigDecimal efectivo = new BigDecimal(0.99);
        //BigDecimal cheques = new BigDecimal(353330.99);
        BigDecimal efectivo = new BigDecimal(0.00);
        BigDecimal cheques = new BigDecimal(0.00);
        String referencia1 = codigoCajero.toString();
        String referencia2 = armarReferencia16Caracteres(nitTransportadora); // no existe el campo nitTrasportadora, se agrego a la firma del metodo
        String referencia3 = fechaMovimiento;
        String referencia4 = "0";
        String referencia5 = codigoOcca.toString();;
        String beneficiario = "";
        short codigoBanco = new Short("99");
        Integer numCheque = 0;//13999;
        String chequera = "";//"950060839999";

        String respuesta;
        try {
            respuesta = egresosVarios(nombreServicio, usuario, Short.parseShort(canal),
                    concepto, Integer.parseInt(talon), efectivo, cheques, valor, referencia1, referencia2,
                    referencia3, referencia4, referencia5, beneficiario, codigoBanco,
                    numCheque, chequera);
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    public String egresosVarios(String nombreOperacion, String usuario, short canal,
            Short concepto, int talon, BigDecimal efectivo, BigDecimal cheques,
            BigDecimal valor, String referencia1, String referencia2, String referencia3,
            String referencia4, String referencia5, String beneficiario, Short codigoBanco,
            Integer numCheque, String chequera) throws DatatypeConfigurationException {

        String respuesta;
        EgresosVariosType datosRequerimiento = new EgresosVariosType();
        EgresosVariosRespType resp = null;
        com.davivienda.utilidades.ws.cliente.egresosVarios.RequestType datosRequest = new com.davivienda.utilidades.ws.cliente.egresosVarios.RequestType();
        com.davivienda.utilidades.ws.cliente.egresosVarios.DataRequestType datos = new com.davivienda.utilidades.ws.cliente.egresosVarios.DataRequestType();
        com.davivienda.utilidades.ws.cliente.egresosVarios.DataHeaderRequestType datosHead = new com.davivienda.utilidades.ws.cliente.egresosVarios.DataHeaderRequestType();

        //ENCABEZADO
        datosHead.setNombreOperacion(nombreOperacion);
        datosHead.setTotal(this.terminalCaja);
        datosHead.setJornada(new Short("0"));
        datosHead.setCanal(canal);
        datosHead.setModoDeOperacion(new Short("0"));
        datosHead.setUsuario(usuario);
        datosHead.setPerfil(new Short("0"));
        datosHead.setVersionServicio("1.1.0");

        //CUERPO PARA PRODUCTO
        datos.setConcepto(concepto);
        datos.setTalon(talon);
        datos.setEfectivo(efectivo.setScale(2, RoundingMode.UP));
        datos.setCheques(cheques.setScale(2, RoundingMode.UP));
        datos.setValorTotal(valor.setScale(2, RoundingMode.UP));
        datos.setReferencia1(referencia1);
        datos.setReferencia2(referencia2);
        datos.setReferencia3(referencia3);
        datos.setReferencia4(referencia4);
        datos.setReferencia5(referencia5);
        datos.setBeneficiario(beneficiario);
        datos.setCodigoBanco(codigoBanco);
        datos.setNumeroDeCheque(numCheque);
        datos.setChequera(chequera);

        datosRequest.setData(datos);
        datosRequest.setDataHeader(datosHead);
        datosRequerimiento.setRequest(datosRequest);

        EgresosVarios cliente = new EgresosVarios();

        try {
            resp = cliente.realizarEgresosVarios(datosRequerimiento);
            if (resp.getResponse() != null) {
                if (resp.getResponse().getDataHeader().getCaracterAceptacion().equals("B")) {
                    respuesta = crearCadenaRespuestaNota(resp.getResponse().getDataHeader().getCaracterAceptacion(),
                            resp.getResponse().getDataHeader().getCodMsgRespuesta(),
                            "Transaccion Exitosa.");
                } else {
                    respuesta = crearCadenaRespuestaNota(resp.getResponse().getDataHeader().getCaracterAceptacion(),
                            resp.getResponse().getDataHeader().getCodMsgRespuesta(),
                            resp.getResponse().getDataHeader().getMsgRespuesta());
                }
            } else {
                respuesta = crearCadenaRespuestaNota("M", new Short("32000"), "Ha ocurrido un error No Esperado en el ESB.");
            }
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    public String realizarNotaCreditoCtaCorrienteESB(Integer codigoCajero, Integer codigoOcca, BigDecimal valor,
            String fechaMovimiento, String talon, String cuenta, String usuario, String concepto, String canal) {
        String nombreServicio = "NotaCredito";
        short codigoBanco = new Short("0");
        int comprobanteCredito = 0;
        String cuentaChequeCredito = "0";
        short indicadorRemesaCredito = new Short("0");
        int fecha = Integer.parseInt(fechaMovimiento);
        // String motivoCredito = "1";//0
        String motivoDevolucionCredito = "1";//0
        short indValidacionNit = 0;
        String nitOrigenCredito = "0";
        // short oficinaRecaudo = new Short("0");
        short origenCredito = new Short("1");
        String respuesta;
        try {
            respuesta = notaCreditoCtaCorriente(nombreServicio, usuario, Short.parseShort(canal), codigoBanco, comprobanteCredito,
                    cuentaChequeCredito, Long.parseLong(cuenta), indicadorRemesaCredito, fecha,
                    motivoDevolucionCredito, codigoCajero, indValidacionNit, nitOrigenCredito, codigoOcca,
                    origenCredito, Integer.parseInt(talon), valor, concepto);
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    private String notaCreditoCtaCorriente(String nombreOperacion, String usuario, short canal,
            short codigoBanco, int comprobanteCredito, String cuentaChequeCredito, long cuenta,
            short indicadorRemesaCredito, int fecha, String motivoDevolucionCredito,
            Integer codigoCajero, Short indValidacionNit, String nitOrigenCredito, Integer codigoOcca, Short origenCredito,
            int talon, BigDecimal valor, String concepto) throws DatatypeConfigurationException {

        String respuesta;
        NotaCreditoType datosRequerimiento = new NotaCreditoType();
        NotaCreditoResponseType resp = null;
        com.davivienda.utilidades.ws.cliente.notaCredito.RequestType datosRequest = new com.davivienda.utilidades.ws.cliente.notaCredito.RequestType();
        com.davivienda.utilidades.ws.cliente.notaCredito.DataType datos = new com.davivienda.utilidades.ws.cliente.notaCredito.DataType();
        com.davivienda.utilidades.ws.cliente.notaCredito.DataHeaderType datosHead = new com.davivienda.utilidades.ws.cliente.notaCredito.DataHeaderType();

        //ENCABEZADO
        datosHead.setNombreOperacion(nombreOperacion);
        datosHead.setTotal(this.terminalCaja);
        datosHead.setJornada(new Short("0"));
        datosHead.setCanal(canal);
        datosHead.setModoDeOperacion(new Short("0"));
        datosHead.setUsuario(usuario);
        datosHead.setPerfil(new Short("0"));
        datosHead.setVersionServicio("1.0.0");

        //CUERPO PARA PRODUCTO
        ProductoAhorroCorrienteType productoAhorroCorriente = new ProductoAhorroCorrienteType();
        productoAhorroCorriente.setOrigenCredito(origenCredito);
        productoAhorroCorriente.setCuentaCredito(cuenta);
        productoAhorroCorriente.setTalonCredito(talon);
        productoAhorroCorriente.setMotivoCredito(new Short(concepto));
        productoAhorroCorriente.setCodigoDelBanco(codigoBanco);
        productoAhorroCorriente.setMotivoDevolucionCredito(new Short(motivoDevolucionCredito));
        productoAhorroCorriente.setFechaCredito(fecha);
        productoAhorroCorriente.setValorCredito(valor);
        productoAhorroCorriente.setCuentaChequeCredito(armarReferencia16Caracteres(cuentaChequeCredito));
        productoAhorroCorriente.setReferencia1(Long.parseLong(armarReferencia16Caracteres(codigoCajero.toString())));
        productoAhorroCorriente.setOficinaRecaudo(codigoOcca.shortValue());
        productoAhorroCorriente.setIndicadorValidacionNit(indValidacionNit);
        productoAhorroCorriente.setNitOrigenCredito(nitOrigenCredito);
        productoAhorroCorriente.setComprobanteCredito(comprobanteCredito);
        productoAhorroCorriente.setIndicadorRemesaCredito(indicadorRemesaCredito);

        datos.setProductoCorriente(productoAhorroCorriente);

        datosRequest.setData(datos);
        datosRequest.setDataHeader(datosHead);
        datosRequerimiento.setRequest(datosRequest);

        NCreditoCosto cliente = new NCreditoCosto();

        try {
            resp = cliente.realizarNotaCredito(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getResponse().getDataHeader().getCaracterAceptacion(),
                    resp.getResponse().getDataHeader().getCodMsgRespuesta(),
                    resp.getResponse().getDataHeader().getMsgRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    public String realizarNotaCreditoCtaAhorrosESB(Integer codigoCajero, Integer codigoOcca, BigDecimal valor,
            String fechaMovimiento, String talon, String cuenta, String usuario, String concepto, String canal) {
        String nombreServicio = "NotaCredito";
        short codigoBanco = new Short("0");
        int comprobanteCredito = 0;
        String cuentaChequeCredito = "0";
        short indicadorRemesaCredito = new Short("0");
        int fecha = Integer.parseInt(fechaMovimiento);
        //  String motivoCredito = "1";
        String motivoDevolucionCredito = "1";
        short indValidacionNit = 0;
        String nitOrigenCredito = "0";
        //  short oficinaRecaudo = new Short("0");
        short origenCredito = new Short("3");
        String respuesta;
        try {
            respuesta = notaCreditoCtaAhorros(nombreServicio, usuario, Short.parseShort(canal), codigoBanco, comprobanteCredito,
                    cuentaChequeCredito, Long.parseLong(cuenta), indicadorRemesaCredito, fecha, concepto,
                    motivoDevolucionCredito, codigoCajero, indValidacionNit, nitOrigenCredito, codigoOcca,
                    origenCredito, Integer.parseInt(talon), valor);
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    private String notaCreditoCtaAhorros(String nombreOperacion, String usuario, short canal,
            short codigoBanco, int comprobanteCredito, String cuentaChequeCredito, long cuenta,
            short indicadorRemesaCredito, int fecha, String concepto, String motivoDevolucionCredito,
            Integer codigoCajero, Short indValidacionNit, String nitOrigenCredito, Integer codigoOcca, Short origenCredito,
            int talon, BigDecimal valor) throws DatatypeConfigurationException {

        String respuesta;
        NotaCreditoType datosRequerimiento = new NotaCreditoType();
        NotaCreditoResponseType resp = null;
        com.davivienda.utilidades.ws.cliente.notaCredito.RequestType datosRequest = new com.davivienda.utilidades.ws.cliente.notaCredito.RequestType();
        com.davivienda.utilidades.ws.cliente.notaCredito.DataType datos = new com.davivienda.utilidades.ws.cliente.notaCredito.DataType();
        com.davivienda.utilidades.ws.cliente.notaCredito.DataHeaderType datosHead = new com.davivienda.utilidades.ws.cliente.notaCredito.DataHeaderType();

        //ENCABEZADO
        datosHead.setNombreOperacion(nombreOperacion);
        datosHead.setTotal(this.terminalCaja);
        datosHead.setJornada(new Short("0"));
        datosHead.setCanal(canal);
        datosHead.setModoDeOperacion(new Short("0"));
        datosHead.setUsuario(usuario);
        datosHead.setPerfil(new Short("0"));
        datosHead.setVersionServicio("1.0.0");

        //CUERPO PARA PRODUCTO
        ProductoAhorroCorrienteType productoAhorroCorriente = new ProductoAhorroCorrienteType();
        productoAhorroCorriente.setOrigenCredito(origenCredito);
        productoAhorroCorriente.setCuentaCredito(cuenta);
        productoAhorroCorriente.setTalonCredito(talon);
        productoAhorroCorriente.setMotivoCredito(new Short(concepto));
        productoAhorroCorriente.setCodigoDelBanco(codigoBanco);
        productoAhorroCorriente.setMotivoDevolucionCredito(new Short(motivoDevolucionCredito));
        productoAhorroCorriente.setFechaCredito(fecha);
        productoAhorroCorriente.setValorCredito(valor);
        productoAhorroCorriente.setCuentaChequeCredito(armarReferencia16Caracteres(cuentaChequeCredito));
        productoAhorroCorriente.setReferencia1(Long.parseLong(armarReferencia16Caracteres(codigoCajero.toString())));
        productoAhorroCorriente.setOficinaRecaudo(codigoOcca.shortValue());
        productoAhorroCorriente.setIndicadorValidacionNit(indValidacionNit);
        productoAhorroCorriente.setNitOrigenCredito(nitOrigenCredito);
        productoAhorroCorriente.setComprobanteCredito(comprobanteCredito);
        productoAhorroCorriente.setIndicadorRemesaCredito(indicadorRemesaCredito);

        datos.setProductoAhorro(productoAhorroCorriente);

        datosRequest.setData(datos);
        datosRequest.setDataHeader(datosHead);
        datosRequerimiento.setRequest(datosRequest);

        NCreditoCosto cliente = new NCreditoCosto();

        try {
            resp = cliente.realizarNotaCredito(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getResponse().getDataHeader().getCaracterAceptacion(),
                    resp.getResponse().getDataHeader().getCodMsgRespuesta(),
                    resp.getResponse().getDataHeader().getMsgRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    public String realizarNotaDebitoCtaAhorrosESB(Integer codigoCajero, BigDecimal valor,
            String fechaMovimiento, String talon, String cuenta, String usuario, String concepto, String canal) {
        String nombreServicio = "NotaDebitoCosto";
        short codigoBanco = new Short("51");
        int fecha = Integer.parseInt(fechaMovimiento);
        // short motivoDebito = new Short("16");
        short motivoDebito = new Short(concepto);
        short motivoDevolucionDebito = new Short("0");
        short origen = new Short("1");
        String tipoNota = "Ahorro";
        String cuentaChequeDebito = "0";
        String respuesta;

        try {
            respuesta = notaDebito(nombreServicio, usuario, Short.parseShort(canal),
                    codigoBanco, Long.parseLong(cuenta), fecha, codigoCajero,
                    Integer.parseInt(talon), valor, tipoNota, motivoDebito, motivoDevolucionDebito,
                    origen, cuentaChequeDebito);
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    public String realizarNotaDebitoCtaCorrienteESB(Integer codigoCajero, BigDecimal valor,
            String fechaMovimiento, String talon, String cuenta, String usuario, String concepto, String canal) {
        String nombreServicio = "NotaDebitoCosto";
        short codigoBanco = new Short("51");
        int fecha = Integer.parseInt(fechaMovimiento);
        // short motivoDebito = new Short("16");
        short motivoDebito = new Short(concepto);
        short motivoDevolucionDebito = new Short("0");
        short origen = new Short("1");
        String tipoNota = "CuentaCorriente";
        String cuentaChequeDebito = "0";
        String respuesta;

        try {
            respuesta = notaDebito(nombreServicio, usuario, Short.parseShort(canal),
                    codigoBanco, Long.parseLong(cuenta), fecha, codigoCajero,
                    Integer.parseInt(talon), valor, tipoNota, motivoDebito, motivoDevolucionDebito,
                    origen, cuentaChequeDebito);
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    public String notaDebito(String nombreOperacion, String usuario, short canal,
            short codigoBanco, long cuenta, int fecha, Integer codigoCajero,
            int talon, BigDecimal valor, String tipoNota, short motivoDebito,
            short motivoDevolucionDebito, short origen, String cuentaChequeDebito) throws DatatypeConfigurationException {
        String respuesta;
        NotaDebitoCostoType datosRequerimiento = new NotaDebitoCostoType();
        NotaDebitoCostoResponseType resp = null;
        com.davivienda.utilidades.ws.cliente.nDebitoCosto.RequestType datosRequest = new com.davivienda.utilidades.ws.cliente.nDebitoCosto.RequestType();
        com.davivienda.utilidades.ws.cliente.nDebitoCosto.DataType datos = new com.davivienda.utilidades.ws.cliente.nDebitoCosto.DataType();
        com.davivienda.utilidades.ws.cliente.nDebitoCosto.DataHeaderType datosHead = new com.davivienda.utilidades.ws.cliente.nDebitoCosto.DataHeaderType();

        datosRequest.setData(datos);
        datosRequerimiento.setRequest(datosRequest);

        //ENCABEZADO
        datosHead.setCanal(canal);
        datosHead.setJornada(new Short("0"));
        datosHead.setModoDeOperacion(new Short("0"));
        datosHead.setNombreOperacion(nombreOperacion);
        datosHead.setPerfil(new Short("0"));
        datosHead.setTotal(this.terminalCaja);
        datosHead.setUsuario(usuario);
        datosHead.setVersionServicio("1.0.0");

        //CUERPO
        datos.setCodigoDelBanco(codigoBanco);
        datos.setCuenta(cuenta);
        datos.setCuentaChequeDebito(armarReferencia16Caracteres(cuentaChequeDebito));
        datos.setFechaDebito(fecha);
        //REVISAR CONVERSION TIPO DATO
        datos.setMotivoDebito(motivoDebito);
        datos.setMotivoDevolucionDebito(motivoDevolucionDebito);
        datos.setOrigen(origen);
        datos.setReferencia1(armarReferencia16Caracteres(codigoCajero.toString()));
        datos.setReferencia2(armarReferencia16Caracteres(codigoCajero.toString()));
        //revisar tamaÃ±o talon
        datos.setTalon(talon);
        datos.setTipoNotaDebito(tipoNota);
        datos.setValorDelDebito(valor);

        datosRequest.setData(datos);
        datosRequest.setDataHeader(datosHead);
        datosRequerimiento.setRequest(datosRequest);
        NDebitoCosto cliente = new NDebitoCosto();

        try {
            resp = cliente.realizarNotaDebito(datosRequerimiento);
            respuesta = crearCadenaRespuestaNota(resp.getResponse().getDataHeader().getCaracterAceptacion(),
                    resp.getResponse().getDataHeader().getCodMsgRespuesta(),
                    //revisar si por bien este parametro eata en el mensaje 
                    resp.getResponse().getDataHeader().getMsgRespuesta());
        } catch (Exception ex) {
            respuesta = crearCadenaRespuestaNota("E", new Short("-1"), ex.getMessage());
        }
        return respuesta;
    }

    /**
     * Método para consultar el informe de totales de un cajero
     *
     * @param codigoCajero Código del cajero a consultar
     * @return RespuestaInformeTotalesATMDto En el Arraylist registrosRepetitivo
     * se tienen uno a uno los registros que se deben presentar
     * @throws
     * com.davivienda.utilidades.ws.cliente.informeTotalesATM.ServicioException_Exception
     */
    public ResumenAjustes[] consultarInformeTotalesATM(Integer codigoCajero) throws com.davivienda.utilidades.ws.cliente.informeTotalesATM.ServicioException_Exception {

        ResumenAjustes[] resumen;
        InformeTotalesATMDto datosRequerimiento = new InformeTotalesATMDto();
        RespuestaInformeTotalesATMDto resp = null;
        datosRequerimiento.setCanal(new Short("0"));
        datosRequerimiento.setIdTarea("OT01");
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setEstacionAtm(codigoCajero.shortValue());
        ConsultaInformeTotalesATM cliente = new ConsultaInformeTotalesATM();
        resp = cliente.consultarInformeTotalesATM(datosRequerimiento);
        resumen = new ResumenAjustes[resp.getRegistrosRepetitivos().size()];

        for (int c = 0; c < resp.getRegistrosRepetitivos().size(); c++) {
            resumen[c] = new ResumenAjustes();
            resumen[c].setCantidad(resp.getRegistrosRepetitivos().get(c).getCantidad());
            resumen[c].setValor(resp.getRegistrosRepetitivos().get(c).getValor());
            resumen[c].setTitulo(resp.getRegistrosRepetitivos().get(c).getTitulo());

        }

        return resumen;
    }

    /**
     * Método para realizar el ajuste sobrante de efectivo del cajero automático
     *
     * @param codigoCajero
     * @param codigoOcca
     * @param valorAjuste
     * @param fechaAjuste formato AAAAMMDD
     * @param talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @return
     * @throws
     * com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception
     */
    public String realizarAjustePorSobrante(Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste, String fechaAjuste, String talon) throws com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception {
        AjustarSobranteCajeroDto datosRequerimiento = new AjustarSobranteCajeroDto();
        RespAjustarSobranteCajeroDto resp = null;
        datosRequerimiento.setCanal(new Short("0"));
        datosRequerimiento.setIdTarea("OT02");
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setUsuario("SARA");
        datosRequerimiento.setPerfil(new Short("1"));
// Campos tx 9188
        datosRequerimiento.setTipoTransaccion(new Short("3"));
        datosRequerimiento.setEstadoAtm(codigoCajero.shortValue());
        datosRequerimiento.setValor(valorAjuste);
// campos tx 9710
        datosRequerimiento.setConcepto(new Short("16"));
        datosRequerimiento.setValorEfectivo(valorAjuste);
        datosRequerimiento.setValorTotal(valorAjuste);
        datosRequerimiento.setReferencia1(codigoCajero.toString());
        datosRequerimiento.setReferencia2(codigoOcca.toString());
        datosRequerimiento.setReferencia3(fechaAjuste);
        datosRequerimiento.setReferencia4("");
        datosRequerimiento.setReferencia5(codigoOcca.toString());
        datosRequerimiento.setTalon(talon);
        AjusteCajeroAutomatico cliente = new AjusteCajeroAutomatico();
        resp = cliente.realizarAjustePorSobrante(datosRequerimiento);

        return crearCadenaRespuesta(resp.getCaracterAcepta(), resp.getValor());
    }

    /**
     * Método para realizar el ajuste faltante de efectivo del cajero automático
     *
     * @param codigoCajero
     * @param codigoOcca
     * @param valorAjuste
     * @param fechaAjuste formato AAAAMMDD
     * @param talon codigoCajero + consecutivo debe ser de 6 posiciones
     * @return
     * @throws
     * com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception
     */
    public String realizarAjustePorFaltante(Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste, String fechaAjuste, String talon) throws com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception {
        AjustarSobranteCajeroDto datosRequerimiento = new AjustarSobranteCajeroDto();
        RespAjustarSobranteCajeroDto resp = null;
        datosRequerimiento.setUsuario("SARA");
        datosRequerimiento.setPerfil(new Short("1"));
        datosRequerimiento.setCanal(new Short("0"));
        datosRequerimiento.setIdTarea("OT02");
        datosRequerimiento.setTotal(this.terminalCaja);
// Campos tx 9188
        datosRequerimiento.setTipoTransaccion(new Short("4"));
        datosRequerimiento.setEstadoAtm(codigoCajero.shortValue());
        datosRequerimiento.setValor(valorAjuste);
// campos tx 9610
        datosRequerimiento.setConcepto(new Short("35"));
        datosRequerimiento.setValorEfectivo(valorAjuste);
        datosRequerimiento.setValorTotal(valorAjuste);
        datosRequerimiento.setReferencia1(codigoCajero.toString());
        datosRequerimiento.setReferencia2(codigoOcca.toString());
        datosRequerimiento.setReferencia3(fechaAjuste);
        datosRequerimiento.setReferencia4("");
        datosRequerimiento.setReferencia5(codigoOcca.toString());
        datosRequerimiento.setTalon(talon);
        AjusteCajeroAutomatico cliente = new AjusteCajeroAutomatico();
        resp = cliente.realizarAjustePorFaltante(datosRequerimiento);
        return crearCadenaRespuesta(resp.getCaracterAcepta(), resp.getValor());
    }

    /**
     * Método para realizar el ajuste de aumento de provisión del cajero
     * automático
     *
     * @param codigoCajero
     * @param valorAjuste
     * @return RespAjustarProvisionCajeroDto
     * @throws
     * com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception
     */
    public String realizarAjusteAumentoProvision(Integer codigoCajero, BigDecimal valorAjuste) throws com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception {
        RespuestaConsultaManejoEfectivoATMDto resp = null;
        resp = this.realizarAjusteProvision(new Short("1"), codigoCajero, valorAjuste);
        return crearCadenaRespuesta(resp.getCaracterAcepta(), new Short("-1"));
    }

    /**
     * Método para realizar el ajuste de disminución de provisión del cajero
     * automático
     *
     * @param codigoCajero
     * @param valorAjuste
     * @return RespAjustarProvisionCajeroDto
     * @throws
     * com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception
     */
    public String realizarAjusteDisminucionProvision(Integer codigoCajero, BigDecimal valorAjuste) throws com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception {
        RespuestaConsultaManejoEfectivoATMDto resp = null;
        resp = this.realizarAjusteProvision(new Short("2"), codigoCajero, valorAjuste);
        return crearCadenaRespuesta(resp.getCaracterAcepta(), new Short("-1"));

    }

    /**
     * Método para realizar el ajuste de provisión del cajero automático
     *
     * @param tipoTransaccion Tipo de ajuste [1] Aumento [2] Disminución
     * @param codigoCajero
     * @param valorAjuste
     * @return RespAjustarProvisionCajeroDto
     * @throws
     * com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception
     */
    private RespuestaConsultaManejoEfectivoATMDto realizarAjusteProvision(Short tipoTransaccion, Integer codigoCajero, BigDecimal valorAjuste) throws com.davivienda.utilidades.ws.cliente.ajusteCajerosAutomaticos.ServicioException_Exception {
        ConsultaManejoEfectivoATMDto datosRequerimiento = new ConsultaManejoEfectivoATMDto();
        RespuestaConsultaManejoEfectivoATMDto resp = null;
        datosRequerimiento.setCanal(new Short("0"));
        datosRequerimiento.setIdTarea("OT02");
        datosRequerimiento.setTotal(this.terminalCaja);
        datosRequerimiento.setUsuario("SARA");
        datosRequerimiento.setPerfil(new Short("1"));
// Campos tx 9188
        datosRequerimiento.setTipoTransaccion(tipoTransaccion);
        datosRequerimiento.setEstadoAtm(codigoCajero.shortValue());
        datosRequerimiento.setValor(valorAjuste);
        AjusteCajeroAutomatico cliente = new AjusteCajeroAutomatico();
        resp = cliente.realizarAjustePorProvision(datosRequerimiento);

        return resp;
    }

    public static void main(String[] args) throws DatatypeConfigurationException {

        //90.4.5.135
        //InvocacionServicios invocacionServicios = new InvocacionServicios("90.4.4.106", "8080", 9990);
        //   InvocacionServicios invocacionServicios = new InvocacionServicios("90.4.5.95", "80", 9990);
        InvocacionServicios invocacionServicios = new InvocacionServicios("90.4.28.5", "80", 9990);

        //autenticarLdap
//         try 
//         {
//               String resp = invocacionServicios.autenticarLdap("S-PLASVS", "Davi10Servicios");
//               System.out.println(resp);
//         } catch (Exception ex) {
//                Logger.global.log(Level.SEVERE, null, ex);
//    } 
//           try 
//         {
//                  String resp = invocacionServicios.realizarNotaCreditoTarjetaCredito( 1131123798, 9973, 
//                                                   new BigDecimal("0"), "20140103",
//                                                    9972, 4599);
//               String resp = invocacionServicios.autenticarLdap("S-PLASVS", "Davi10Servicios");
//               realizarEnvioTransportadora( long codigoTransportadora, BigDecimal valorEfectivo, Integer talon)
//               String resp = invocacionServicios.realizarEnvioTransportadora(0,new BigDecimal("0"),124587);
//             System.out.println(resp);
//         } catch (Exception ex) {
//                Logger.global.log(Level.SEVERE, null, ex);
//         } 
//                 try {
//            ResumenAjustes2[] resumen;
//            resumen = invocacionServicios.consultarResumenIDOOficina();
//          Logger.global.log(Level.INFO, resumen+"consultarInformeTotalesATM" );
//               }catch (Exception ex) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        }
//     }
//    try {
//            String talon = "2212";
//            BigDecimal valor = new BigDecimal(353339.99);
//            String canal = "26";
//            Integer codigoCajero = 0;
//            String fecha = "20131121";
//           
//           String resp =  invocacionServicios.ajustarIngresoESB(codigoCajero, 0, valor, fecha, talon, canal);
//                    
//             System.out.println(resp);
//        }catch (Exception ex) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        } 
//    
//    }
//    try {
//            String talon = "2230";
//            BigDecimal valor = new BigDecimal(353339.99);
//            String canal = "0";
//            Integer codigoCajero = 0;
//            String fecha = "20131121";
//            
//            String resp =  invocacionServicios.ajustarEgresoESB(codigoCajero, 0, valor, fecha, talon, canal, new Short("35"),"0");
//
//            System.out.println(resp);
//        }catch (Exception ex) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        } 
//    
//    }
//    
//try {
//            //String cuenta = "4559861131123798";
//            String cuenta = "0550098300019407";
//            String fecha = "20140203";
//            Integer codigoCajero = 9987;
//            String talon = "784523";
//            BigDecimal valor = new BigDecimal(0);
//            String usuario = "SUN";
//            String canal = "26";
//            
//            String resp =  invocacionServicios.realizarNotaCreditoCtaCorrienteESB(codigoCajero, 0, 
//                   valor, fecha, talon, cuenta, usuario, "", canal);
//                public String realizarNotaCreditoCtaAhorros(Integer codigoCajero, Integer codigoOcca, BigDecimal valor,
//            String fechaMovimiento, String talon,  String cuenta, String usuario, String concepto, String canal)
//            String resp =  invocacionServicios.realizarNotaCreditoCtaAhorrosESB(codigoCajero, 4599, 
//                    valor, fecha, talon, cuenta, usuario, "0016", canal);
//                System.out.println(resp);
//        }catch (Exception ex) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        } }    
//    
//    try {
//            String cuenta = "0560098369997121";
//            String fecha = "20131121";
//            Integer codigoCajero = 21;
//            String talon = "784512";
//            BigDecimal valor = new BigDecimal(5);
//            String usuario = "pru";
//            String canal = "26";
//            String resp =  invocacionServicios.realizarNotaDebitoCtaAhorrosESB(codigoCajero, 
//                    valor, fecha, talon, cuenta, usuario, "0", canal);
////            String resp =  invocacionServicios.realizarNotaDebitoCtaCorrienteESB(codigoCajero, 
////                    valor, fecha, talon, cuenta, usuario, "0", canal);
//                        System.out.println(resp);
//        }catch (Exception ex) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        } }
        // Prueba Ajustes
        // Aumento provisión
//        try {
//           
//            String resp = invocacionServicios.realizarAjusteAumentoProvision(2933,new BigDecimal(1000000));
//                        System.out.println(resp);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }}
//
//        // Prueba consulta informes cajero
//        try {
//            ResumenAjustes[] resumen;
//            resumen = invocacionServicios.consultarInformeTotalesATM(9974);
//            Logger.global.log(Level.INFO, 
//                   "consultarInformeTotalesATM" );
//        } catch (Exception ex) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        }}
//
//
//        // Disminución provisión
//        try {
//            String resp = invocacionServicios.realizarAjusteDisminucionProvision(9975,new BigDecimal(1000000));
//            Logger.global.log(Level.INFO, resp +"realizarAjusteDisminucionProvision");
//        } catch (Exception ex) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        }}
//
        // Prueba consulta informes cajero
//        try {
//            RespuestaInformeTotalesATMDto resp = invocacionServicios.consultarInformeTotalesATM(9974);
//            Logger.global.log(Level.INFO, resp.getCaracterAcepta()+"consultarInformeTotalesATM" );
//            resp.getRegistrosRepetitivos().get(0).getValor().toString();
//         
//        } catch (com.davivienda.procesadortransacciones.impl.servicios.informetotalesatm.ServicioException_Exception ex) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        }
//
//
//        // Por Faltante
//        try {
//            String resp = invocacionServicios.realizarAjustePorFaltante(9973,4599,new BigDecimal(1000000), "20090717", "997401");
//            Logger.global.log(Level.INFO, resp + "realizarAjustePorFaltante ");
//        } catch (Exception ex) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        }}
//
//        // Por Sobrante
//        try {
//            String resp = invocacionServicios.realizarAjustePorSobrante(9971,4599,new BigDecimal(1500000), "20090717", "997402");
//            Logger.global.log(Level.INFO, resp + "realizarAjustePorSobrante " );
//        } catch (Exception ex) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        }}
        // Prueba consulta resumen IDO Oficina
//        try {
//             ResumenAjustes[] resumen;
//            resumen = invocacionServicios.consultarResumenIDOOficina();
//           Logger.global.log(Level.INFO, "" );  
//           
//        } catch (Exception ex ) {
//            Logger.global.log(Level.SEVERE, null, ex);
//        }}
//        
//        // Nota debito
//        try {
//            String resp = invocacionServicios.realizarNotaDebitoCtaAhorros(9974,new BigDecimal(150), "20090814", "2", "099201003375");
//            Logger.global.log(Level.INFO, resp);
//        } catch (Exception ex1) {
//            Logger.global.log(Level.SEVERE, null, ex1);
//        }}
//    
//            // Nota debito corriente
//        try {
//            String resp = invocacionServicios.realizarNotaDebitoCtaCorriente(9974,new BigDecimal(150), "20090814", "1", "098360001790");
//            Logger.global.log(Level.INFO, resp);
//        } catch (Exception ex1) {
//            Logger.global.log(Level.SEVERE, null, ex1);
//        }}
        // Nota credito ahorros
//        try {
//              
//            String resp = invocacionServicios.realizarNotaCreditoCtaAhorros(9974,new BigDecimal(150), "20090911", "997402", "099201003375");
//            Logger.global.log(Level.INFO, resp);
//        } catch (Exception ex1) {
//            Logger.global.log(Level.SEVERE, null, ex1);
//        }}
//        // Nota credito corriente
//        try {
//            String resp = invocacionServicios.realizarNotaCreditoCtaCorriente(9974,new BigDecimal(150), "20090904", "1", "098360001790");
//            Logger.global.log(Level.INFO, resp);
//        } catch (Exception ex1) {
//            Logger.global.log(Level.SEVERE, null, ex1);
//        }}
    }
}
