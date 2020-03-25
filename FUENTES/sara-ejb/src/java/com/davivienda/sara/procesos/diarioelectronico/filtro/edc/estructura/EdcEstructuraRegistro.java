package com.davivienda.sara.procesos.diarioelectronico.filtro.edc.estructura;
/**
 * EdcEstructuraRegistro - 22/08/2008
 * Descripción : 
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */


    public enum EdcEstructuraRegistro {

        TIPO_REGISTRO("Tipo_registro", 0, 0, 1, false),
        SECUENCIA("Secuencia", 1, 1, 6, false),
        FECHA("Fecha", 2, 7, 6, false),
        HORA("Hora", 3, 13, 6, false),
        CODIGO_CAJERO("Codigo_Cajero", 4, 23, 4, false),
        INFORMACION("Informacion", 5, 28, -1, false);
        public String nombre;
        public int orden;
        public int posIni;
        public int longitud;
        public boolean esFiltro;

        EdcEstructuraRegistro(String nombre, int orden, int posIni, int longitud, boolean esFiltro) {
            this.nombre = nombre;
            this.orden = orden;
            this.posIni = posIni;
            this.longitud = longitud;
            this.esFiltro = esFiltro;
        }
    }

