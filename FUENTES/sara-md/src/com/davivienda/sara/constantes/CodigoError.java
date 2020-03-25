package com.davivienda.sara.constantes;

/**
 * CodigoError.java
 *
 * Fecha       :11 de abril de 2007, 12:18 PM
 *
 * Descripción : Enumeración de los posibles códigos de error que existen en la aplicación
 *
 * @author     : jjvargas
 *
 * @version    : $Id$
 *
 */

public enum CodigoError {
    SIN_ERROR(0),
    PARAMETRO_INVALIDO(1),
    SQL_INVALIDO(2),
    ERROR_IO_COMUNICACIONES(3),
    ERROR_EN_TRANSMISION(4),
    REGISTRO_NO_EXISTE(5),
    //Creado Alvaro Garcia
    REGISTRO_YA_EXISTE(6),   
    SIN_AUTORIZACION(10),
    ARCHIVO_NO_EXISTE(101),
    ERROR_IO_EN_REGITRO(102),
    ERROR_PARSEAR_REGISTRO(103),
    CAJERO_NO_EXISTE(201),
    NO_EXISTEN_CAJEROS(202),
    TRANSACCION_NO_EXISTE(203),
    DIARIO_ELECTRONICO_NO_EXISTE(204),
     //Creado Alvaro Garcia
    TODOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO(620),
     //Creado Alvaro Garcia
    ALGUNOSREGISTROSCONCODIGOCAJERO_DISTINTOAARCHIVO(621),
    ERROR_EN_AUTENTICACION(300),
    ERROR_BASE_DATOS(2),
    ERROR_CONSULTANDO_ENTITYS(200),
    ERROR_INSERTANDO_ENTITYS(250),
    ERROR_ACTUALIZANDO_ENTITYS(300),
    ERROR_BORRANDO_ENTITYS(350),
    ERROR_NO_DEFINIDO(999);
    
    
    
    
    /**
     * Código del error
     */
    public Integer codigo;
    
    CodigoError(Integer codigoError) {
        this.codigo = codigoError;
    }
        public Integer getCodigo() {
        return this.codigo;
    }

    public static CodigoError getCodigoError(Integer codigo){
        CodigoError codigoError = CodigoError.ERROR_NO_DEFINIDO;
        for (CodigoError elem : CodigoError.values()) {
            if (elem.codigo.equals(codigo)){
                codigoError = elem;
                break;
            }
        }
        return codigoError;
    }
    
}
