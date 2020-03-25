package com.davivienda.utilidades.edc.dto;

import java.util.Calendar;
import com.davivienda.utilidades.conversion.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * TipoTransaccionRegistro.java
 *
 * Fecha       :  4/06/2007, 06:47:04 PM
 * Descripción :
 *
 * @author     : jjvargas
 * @version    : $Id$
 */
public class TipoTransaccionRegistroDto {
    
    public enum Estructura {
        TITULO_TRANSACCION(0),
        CODIGO_CAJERO(1),
        CODIGO_OCCA(2),
        TALON(3, true, 0, 4),
        FECHA(4, true, 0, 6),
        HORA(5,true,0,6),
        TIPO_TRANSACCION(6),
        CODIGO_TRANSACCION(7),
        CODIGO_ERROR(8),
        VALOR_TRANSACCION(9),
        TARJETA(10),
        CUENTA(11),
        VALOR_ENTREGADO(12),
        CODIGO_TERMINACION(13, true, 0, 4);
        
        
        
        
        public int posicion ;
        public boolean procesarDato;
        public int inicioDato;
        public int finDato;
        
        Estructura(int pos) {
            this(pos, false, -1, -1);
        }
        
        Estructura(int pos, boolean procesarDato, int inicioDato, int finDato) {
            posicion = pos;
            this.procesarDato = procesarDato;
            this.inicioDato = inicioDato;
            this.finDato = finDato;
        }
    }
    
    public enum TipoTransaccion {
        RETIRO_AHORRO(20, 20, "Retiro Ahorro"),
        RETIRO_CORRIENTE(20, 21, "Retiro Corriente"),
        RETIRO_RED_AHORRO(21, 22, "Retiro otra red Ahorro"),
        RETIRO_RED_CTE(21, 21, "Retiro otra red Corriente"),
        CONSULTA_SALDO(50, 50, "Consulta Saldo Cuenta"),
        AVANCE_TC(20, 24, "Avance Tarjeta crédito"),
        PRIMER_INF_TOTALES(1, 13, "Primer informe"),
        DISMINUCION_PROVISION(1, 4, "Disminución Provisión"),
        AUMENTO_PROVISION(1, 3, "Aumento Provisión"),
        CONTROL_VISITA_PROVEEDOR(1,99,"Control visitas proveedor"),
        AVANCE_TC_REDES(21, 23, "Avance Tarjeta crédito Redes"),
        CONSULTA_MOVIMIENTO(58, 58, "Consulta Movimeinto"),
        CAMBIO_PRIMERA_CLAVE(90, 90, "Consulta Movimeinto"),
        TRAN_21_25(21, 25, "Tran 21 25"),
        TRANSACCION_NO_DEFINIDA(0,0,"Transaccion no definida");
        
        
        public Integer tarea;
        public Integer transaccion;
        public String nombre;
        
        TipoTransaccion(int tarea, int transaccion, String nombre) {
            this.tarea = tarea;
            this.transaccion = transaccion;
            this.nombre = nombre;
        }
        
        public static TipoTransaccion getTipoTransaccion(Integer transaccion) {
            TipoTransaccion tipoTransaccion = TipoTransaccion.TRANSACCION_NO_DEFINIDA;
            if (transaccion != null) {
                for (TipoTransaccion elem : TipoTransaccion.values()) {
                    if (transaccion.equals(elem.transaccion)) {
                        tipoTransaccion = elem;
                        break;
                    }
                }
            }
            return tipoTransaccion;
        }
        
    }
    
    public String[] campos;
    public Collection<String> errores ;
    public String cadena;
    
    public String titulo ;
    public Integer codigoCajero;
    public Integer codigoOcca;
    public Integer talon;
    public String fechaStr;
    public String hora;
    public Calendar fecha;
    public Integer tipoTran;
    public Integer codigoTran;
    public String nombreTran;
    public String codigoErrorHost;
    public Long valorTran;
    public String tarjeta;
    public String cuenta;
    public Long valorEntregado;
    public String codigoTerminacion;
    public String referencia;

    
    
    
    /**
     * Crea una nueva instancia de <code>TipoTransaccionRegistro</code>.
     */
    public TipoTransaccionRegistroDto() {
        errores = new ArrayList<String>();
    }
    
    public TipoTransaccionRegistroDto(String cadena) {
        this.cadena = cadena;
        errores = new ArrayList<String>();        
        setCadena();
    }
    
    public void setCadena() {
        setCadena(this.cadena);
    }
    
    public void setCadena(String cadena) {
        try {
            campos = cadena.split(",");
            titulo = subCadena(Estructura.TITULO_TRANSACCION);
            codigoCajero = aInteger(Estructura.CODIGO_CAJERO);
            codigoErrorHost = subCadena(Estructura.CODIGO_ERROR);
            codigoOcca = aInteger(Estructura.CODIGO_OCCA,0);
            codigoTerminacion = subCadena(Estructura.CODIGO_TERMINACION);
            codigoTran = aInteger(Estructura.CODIGO_TRANSACCION);
            nombreTran = TipoTransaccion.getTipoTransaccion(codigoTran).nombre;
            cuenta = subCadena(Estructura.CUENTA);
            fechaStr = subCadena(Estructura.FECHA);
            hora = subCadena(Estructura.HORA);
            fecha = aCalendar(fechaStr + hora);
            talon = aInteger(Estructura.TALON);
            tarjeta = subCadena(Estructura.TARJETA);
            tipoTran = aInteger(Estructura.TIPO_TRANSACCION);
            valorEntregado = aValor(Estructura.VALOR_ENTREGADO);
            valorTran = aValor(Estructura.VALOR_TRANSACCION);
        } catch (Exception ex ) {
            errores.add("No se puede convertir el registro " + ex.getMessage());
        }
    }
    
    
    
    private Long aValor(Estructura campo) {
        Long valor = 0L;
        if (campos[campo.posicion] != null)
        {
        if (campos[campo.posicion].equals("")) {
            return null;
        }
        try {
            if (campo.procesarDato) {
                valor = Cadena.aValor(campos[campo.posicion], campo.inicioDato, campo.finDato);
            } else {
                valor = Cadena.aValor(campos[campo.posicion]);
            }
        } catch (NumberFormatException ex ) {
            errores.add("No se puede convertir a valor " + ex.getMessage());
        }
        }
        return valor;
    }
    
    
    private String subCadena(Estructura campo) {
        String resp = "";
        try {
            if (campo.procesarDato) {
                resp = Cadena.subCadena(campos[campo.posicion], campo.inicioDato, campo.finDato);
            } else {
                resp = campos[campo.posicion];
            }
        } catch (IllegalArgumentException ex) {
            errores.add("No se puede generar la subcadena " + ex.getMessage());
        }
        return resp.trim();
    }
    
    private Integer aInteger(Estructura campo) {
        Integer resp = 0;
        try {
            if (campo.procesarDato) {
                resp  = Cadena.aInteger(campos[campo.posicion], campo.inicioDato, campo.finDato);
            } else {
                resp = Cadena.aInteger(campos[campo.posicion]);
            }
        } catch (NumberFormatException ex) {
            errores.add("No se puede convertir a entero " + ex.getMessage());
        }
        return resp;
    }

    private Integer aInteger(Estructura campo, Integer valorDefecto) {
        Integer resp = valorDefecto;
        try {
            if (campo.procesarDato) {
                resp  = Cadena.aInteger(campos[campo.posicion], campo.inicioDato, campo.finDato);
            } else {
                resp = Cadena.aInteger(campos[campo.posicion]);
            }
        } catch (NumberFormatException ex) {
            resp = valorDefecto;
        }
        return resp;
    }
    
    private Calendar aCalendar(String dato) {
        Calendar tmp = null ;
        try {
            tmp = Cadena.aCalendar(dato, FormatoFecha.FECHA_EDC);
        } catch (IllegalArgumentException ex) {
            errores.add("No se puede convertir a fecha Calendar " + ex.getMessage());
        }
        return tmp;
    }
    
    
    @Override
    public String toString() {
        StringBuffer cadenaTmp = new StringBuffer();
        
        cadenaTmp.append("Talón             : ").append(talon).append("\n");
        cadenaTmp.append("Fecha             : ").append(fechaStr).append(hora).append("\n");
        cadenaTmp.append("Transacción       : ").append(codigoTran).append("\n");
        cadenaTmp.append("                    ").append(nombreTran).append("\n");
        cadenaTmp.append("Error Host        : ").append(codigoErrorHost).append("\n");
        cadenaTmp.append("Valor solicitado  : ").append(Numero.aMoneda(valorTran)).append("\n");
        cadenaTmp.append("Tarjeta           : ").append(tarjeta).append("\n");
        cadenaTmp.append("Cuenta            : ").append(cuenta).append("\n");
        cadenaTmp.append("Valor entregado   : ").append(Numero.aMoneda(valorEntregado)).append("\n");
        cadenaTmp.append("CódigoTerminación : ").append(codigoTerminacion).append("\n");
        
        return cadenaTmp.toString();
        
    }
    
    
    
}

