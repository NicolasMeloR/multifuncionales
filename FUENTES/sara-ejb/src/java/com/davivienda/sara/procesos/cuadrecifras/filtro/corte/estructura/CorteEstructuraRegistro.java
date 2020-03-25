package com.davivienda.sara.procesos.cuadrecifras.filtro.corte.estructura;
/**
 * EdcEstructuraRegistro - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */


      public enum CorteEstructuraRegistro {

        CAJERO("cajero", 0, 0, 4, false),
        FECHA("fecha", 1, 4, 8, false),
        DENOMINACION_1("denom_1", 2, 56, 4, false),
        TOTAL_1("total_1", 3, 60, 4, false),
        DISPENSADO_1("dispensado_1", 4, 64, 4, false),
        CAJA_1("caja_1", 5, 68, 4, false),
        PURGA_1("purga_1", 6, 72, 4, false),
        RETRACT_1("retract_1", 7, 76, 4, false),
        DENOMINACION_2("denom_2", 8, 80, 4, false),
        TOTAL_2("total_2", 9, 84, 4, false),
        DISPENSADO_2("dispensado_2", 10, 88, 4, false),
        CAJA_2("caja_2", 11, 92, 4, false),
        PURGA_2("purga_2", 12, 96, 4, false),
        RETRACT_2("retract_2", 13, 100, 4, false),
        DENOMINACION_3("denom_3", 14, 104, 4, false),
        TOTAL_3("total_3", 15, 108, 4, false),
        DISPENSADO_3("dispensado_3", 16, 112, 4, false),
        CAJA_3("caja_3", 17, 116, 4, false),
        PURGA_3("purga_3", 18, 120, 4, false),
        RETRACT_3("retract_3", 19, 124, 4, false),
        DENOMINACION_4("denom_4", 20, 128, 4, false),
        TOTAL_4("total_4", 21, 132, 4, false),
        DISPENSADO_4("dispensado_4", 22, 136, 4, false),
        CAJA_4("caja_4", 23, 140, 4, false),
        PURGA_4("purga_4", 24, 144, 4, false),
        RETRACT_4("retract_4", 25, 148, 4, false),
        
        /*5 Gaveta*/
        DENOMINACION_5("denom_5", 26, 152, 4, false),
        TOTAL_5("total_5", 27, 156, 4, false),
        DISPENSADO_5("dispensado_5", 28, 160, 4, false),
        CAJA_5("caja_5", 29, 164, 4, false),
        PURGA_5("purga_5", 30, 168, 4, false),
        RETRACT_5("retract_5", 31, 172, 4, false),        
        DENOMINACION_UF("denom_UF", 32, 176, 4, false),
        TOTAL_UF("total_UF", 33, 180, 4, false),
        DISPENSADO_UF("dispensado_UF", 34, 184, 4, false),
        CAJA_UF("caja_UF", 35, 188, 4, false),
        PURGA_UF("purga_UF", 36, 192, 4, false),
        RETRACT_UF("retract_UF", 37, 196, 4, false),
        
        CAN_RET_DAV("canRetiroDav", 38, 200, 4, false),
        VAL_RET_DAV("valRetiroDav", 39, 204, 12, false),
        CAN_AVAN_DAV("canAvanceDav", 40, 216, 4, false),
        VAL_AVAN_DAV("valAvanceDav", 41, 220, 12, false),
        CAN_RET_RED("canRetiroRed", 42, 232, 4, false),
        VAL_RET_RED("valRetiroRed", 43, 236, 12, false);
        
        
        
        
        public String nombre;
        public int orden;
        public int posIni;
        public int longitud;
        public boolean esFiltro;

        CorteEstructuraRegistro(String nombre, int orden, int posIni, int longitud, boolean esFiltro) {
            this.nombre = nombre;
            this.orden = orden;
            this.posIni = posIni;
            this.longitud = longitud;
            this.esFiltro = esFiltro;
        }
    }

