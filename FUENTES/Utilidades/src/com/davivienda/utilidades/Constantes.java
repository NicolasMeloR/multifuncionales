package com.davivienda.utilidades;

public class Constantes {

    public static final String VERSION_APP = "12.2.8.0";
    public static final String LOGGER_APP = "SARA_App";
    public static final String LOGGER_ACCESO = "SARA_Acceso";
    public static final String SESSION_SECURITY_DTO = "SESSION_SECURITY_DTO";
    public static final String CSRFTOKEN_NAME = "SARA_TOKEN_NAME";
    public static final String CSRFTOKEN_ATTR = "CSRFTokenAttack";
    public static final String DOMINIOS_VALIDOS = "DOMINIOS_VALIDOS";

    /*Constantes para Tiempo Sesion Usuario*/
    public static final String TIEMPO_SESION_USUARIO = "TIEMPO_SESION_USUARIO";
    public static final String TIEMPO_LOGIN_USUARIO = "TIEMPO_LOGIN_USUARIO";
    public static final int TIEMPO_SESION_DEFAULT = 20;
    public static final int TIEMPO_LOGIN_DEFAULT = 5;

    /*Constantes para Quartz*/
    public static final String EXP_TRIGGER_DEFECTO = "0 30 23 * * ?";
    public static final String PACKAGE_JOBS = "com.davivienda.sara.administracion.tareas.programadas.";
    public static final String ESTRUCTURA_LLAVE_NOMBRE_CLASE_JOB = "Job{0}";

    /*Constantes para Quartz Archivos Cuadre*/
    public static final String JOB_ARCHIVOS_CUADRE_CFAMO = "ArchivoCfamo";
    public static final String JOB_ARCHIVOS_CUADRE_GEATO = "ArchivoGeato";
    public static final String JOB_ARCHIVOS_CUADRE_OTBMO = "ArchivoOtbmo";

    public static final String JOB_ARCHIVOS_CUADRE_EXP = "QUARTZ.ARCHIVOS_CUADRE.EXP";

    /*Constantes para Quartz Cargue Diarios*/
    public static final String JOB_CARGUE_DIARIOS = "CargueDiarios";
    public static final String JOB_CARGUE_DIARIOS_EXP = "QUARTZ.CARGUE_DIARIOS.EXP";

    /*Constantes para Quartz Dali Auto*/
    public static final String JOB_DALI_AUTO = "DaliAuto";
    public static final String JOB_DALI_AUTO_EXP = "QUARTZ.DALI_AUTO.EXP";

    /*Constantes para el uso de Expresiones Regulares*/
    public static final String REGEX_ACEPTAR_NUM_VALOR = "[^0-9\\.\\,]";
    public static final String REGEX_ACEPTAR_DEFAULT = "[^\\dA-Za-z\\.\\-\\s ]";

    public static final String PARAM_NOMBRE_NODO_CLUSTER = "QUARTZ.NOMBRE_NODO_CLUSTER";

    /*Constantes para Quartz Informe Diferencias*/
    public static final String JOB_INFORME_DIFERENCIAS_EXP = "QUARTZ.INFORME_DIFERENCIAS.EXP";
    public static final String JOB_INFORME_DIFERENCIAS = "InformeDiferencias";

    public static final String FORMATO_MONEDA_INFO_REINTEGROS = "$###,###,###.##";

    public static final String PARAM_REINTEGROS_DESCUADRE = "Y";
    public static final String PARAM_REINTEGROS_DESCUADRE_N = "N";
    public static final String PARAM_INFORME_DIFERENCIAS_SI = "SI";
    public static final String PARAM_INFORME_DIFERENCIAS_NO = "NO";

    /*Extension archivo validos DiarioElectronicos-CopiarArchivoBean*/
    public static final String EXTENSIONES_ARCHIVO_EXCEL = ".xls";
    public static final String EXTENSIONES_ARCHIVO_EXCEL_XLSX = ".xlsx";
    public static final String EXTENSIONES_DIARIO_ELECTRONICO = ".0";
    public static final String EXTENSIONES_CICLO_DIARIO = ".zip";
    public static final String EXTENSIONES_MULTIFUNCIONAL = ".txt";
    /**
     * Variables generales
     */
    public static final String CARACTER_COMA = ",";
    public static final String PARAM_URL_ACCESO_APP = "URL_ACCESO_APP";
    public static final String FECHA_HISTORICAS_CONSULTA = "SARA.FECHA_HISTORICAS_CONSULTA";

    /**
     * Variables para obtener la IP
     */
    public static final String HEADER_HTTP_IP_1 = "x-forwarded-for";
    public static final String HEADER_HTTP_IP_2 = "Proxy-Client-IP";
    public static final String HEADER_HTTP_IP_3 = "WL-Proxy-Client-IP";
    public static final String HEADER_HTTP_IP_4 = "HTTP_CLIENT_IP";
    public static final String HEADER_HTTP_IP_5 = "HTTP_X_FORWARDED_FOR";
    public static final String HEADER_HTTP_IP_6 = "rlnclientipaddr";

    /**
     * Variables mensajes modal
     *
     */
    public static final String MSJ_SELECCIONAR_FECHA = "Debe seleccionar una fecha Inicial";
    public static final String MSJ_SELECCIONAR_FECHA_ERRONEA = "Debe seleccionar una fecha antes o igual a la actual";
    public static final String MSJ_SELECCIONAR_FECHA_MAYOR = "La fecha inicial no puede ser posterior a la fecha final";
    public static final String MSJ_SELECCIONAR_FECHA_RANGO_MAYOR= "El rango de fechas no puede ser mayor a dos días, o seleccione un cajero para aumentar el rango";
    public static final String MSJ_SELECCIONAR_FECHA_RANGO_MAYOR2= "El rango de fechas no puede ser superior a 30 días";
    public static final String MSJ_SELECCIONAR_FECHA_RANGO = "Debe seleccionar una fecha Inicial y una fecha final";
    public static final String MSJ_SELECCIONAR_CAJERO = "Debe seleccionar un cajero";
    public static final String MSJ_SELECCIONAR_TIPO_CONSULTA = "Debe seleccionar un Tipo de Consulta";
    public static final String MSJ_SELECCIONAR_OCCA = "Debe seleccionar un occa";
    public static final String MSJ_SELECCIONAR_TIPO_CUENTA = "Debe seleccionar un Tipo de Cuenta";
    public static final String MSJ_SELECCIONAR_TIPO_REPORTE = "Debe seleccionar un Tipo de Reporte";
    public static final String MSJ_SELECCIONAR_TIPO_TAREA = "Debe seleccionar un Tipo de Tarea";
    public static final String MSJ_SELECCIONAR_TIPO_DIARIO = "Debe seleccionar un Tipo  Diario";
    public static final String MSJ_SELECCIONAR_OFICINA = "Debe seleccionar una Oficina";
    public static final String MSJ_SELECCIONAR_TIPO_AJUSTE = "Debe seleccionar un Tipo de Ajuste";
    public static final String MSJ_QUERY_SIN_DATA = "No hay registros que cumplan con los criterios de la consulta";
    public static final String MSJ_QUERY_ERROR = "Error interno en la consulta";

    public static final String MSJ_ERROR_CREAR_ARCHIVO = "Error al crear el archivo";
    public static final String MSJ_ERROR_DESCARGAR_ARCHIVO = "Error al descargar el archivo";
    public static final String MSJ_ERROR_INTERNO = "Error interno durante el proceso";

}
