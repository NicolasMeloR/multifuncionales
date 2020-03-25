package com.davivienda.sara.procesos.reintegros.filtro.host.estructura;
/**
 * EdcEstructuraRegistro - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */


    public enum HostEstructuraRegistro {

        
//        //ESTRUCTURA ANTERIOR
//        CODIGO_CAJERO("Codigo_Cajero", 0, 0, 4, false),
//        FECHA_SISTEMA("Fecha_sistema", 1, 4, 8, false),
//        HORA_SISTEMA("Hora_sistema", 2, 12, 6, false),
//        TALON("Talon", 3, 18, 6, false),
//        NUMERO_CUENTA("Numero_cuenta", 4, 24, 12, false),
//        DATOS_TARJETA("Datos_tarjeta", 5, 36, 19, false),
//        FECHA("Fecha", 6, 55, 8, false),
//        TIPO_TRANSACCION("Tipo_transaccion", 7, 63, 4, true),
//        OCCA("Occa", 8, 67, 4, false),
//        TIPO_TARJETA("Tipo_tarjeta", 9, 71, 1, false),
//        INDICES("Indices", 10, 72, 2, false),
//        //los 8 caracteres siguientes son ceros
//        VALOR("Valor", 11, 82, 7, false),
//        FILLER("Filler", 12, 89, 7, false);
//        
        
        CODIGO_CAJERO("Codigo_Cajero", 0, 0, 6, false),
        FECHA_SISTEMA("Fecha_sistema", 1, 6, 8, false),
        HORA_SISTEMA("Hora_sistema", 2, 14, 6, false),
        TALON("Talon", 3, 20, 6, false),
        NUMERO_CUENTA("Numero_cuenta", 4, 26, 12, false),
        DATOS_TARJETA("Datos_tarjeta", 5, 38, 19, false),
        FECHA("Fecha", 6, 57, 8, false),
        TIPO_TRANSACCION("Tipo_transaccion", 7, 65, 4, true),
        OCCA("Occa", 8, 69, 4, false),
        TIPO_TARJETA("Tipo_tarjeta", 9, 73, 1, false),
        INDICES("Indices", 10, 74, 2, false),
        //los 8 caracteres siguientes son ceros
        VALOR("Valor", 11, 84, 7, false),
        FILLER("Filler", 12, 91, 7, false);
        



        public String nombre;
        public int orden;
        public int posIni;
        public int longitud;
        public boolean esFiltro;

        HostEstructuraRegistro(String nombre, int orden, int posIni, int longitud, boolean esFiltro) {
            this.nombre = nombre;
            this.orden = orden;
            this.posIni = posIni;
            this.longitud = longitud;
            this.esFiltro = esFiltro;
        }
    }
    
