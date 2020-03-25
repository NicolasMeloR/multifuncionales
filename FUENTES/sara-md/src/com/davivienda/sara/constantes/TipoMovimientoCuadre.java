package com.davivienda.sara.constantes;


/**
 * TipoMovimientoCuadre.java
 * 
 * Fecha       :  14/07/2007, 03:56:25 PM
 * Descripción :  Tipos de movimiento de cuadre cajeros, la configuración de cada uno lo encuentra con el entity TipoMovimientoCuadre
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */

/**

3	100	Provisión Día Anterior	1	1	0	0
4	110	Aumento Provisión	1	1	0	0
5	111	Disminución Provisión	-1	1	0	0
6	130	Dispensado	-1	1	0	0
7	199	Provisión Día Siguiente	0	1	0	0
12	200	Provisión Inicial IDO	0	0	0	0
17	201	Provisión Inicial Real	1	0	1	0
13	210	Aumento Provisión IDO	1	0	1	0
14	211	Disminución Provisión IDO	-1	0	1	0
10	220	Sobrante IDO	1	0	1	0
15	221	Sobrante Manual	0	0	0	0
8	230	Pagado IDO	-1	0	1	0
2	231	Donaciones	1	0	1	0
11	240	Faltante IDO	-1	0	1	0
16	241	Faltante Manual	0	0	0	0
18	298	Provisión Dia Sgte Real	0	0	0	0
9	299	Provisión Dia Siguiente IDO	0	0	0	0
19	710	Ajuste Aumento Provisión	1	0	1	0
20	711	Ajuste Disminución Provisión	-1	0	1	0
1	712	Arqueo	0	1	0	0
21	721	Ajuste Sobrante	1	0	1	0
22	741	Ajuste Faltante	-1	0	1	0 * 
 * 
 */

public enum TipoMovimientoCuadre {
    PROVISION_DIA_ANTERIOR(100),    
    AUMENTO_PROVISION(110),
    DISMINUCION_PROVISION(111),
    DISPENSADO(130),
    PROVISION_DIA_SIGUIENTE(199),
    PROVISION_INICIAL_IDO(200),    
    PROVISION_INICIAL_REAL(201),
    AUMENTO_PROVISION_IDO(210),
    DISMINUCION_PROVISION_IDO(211),    
    SOBRANTE_IDO(220), 
    SOBRANTE_MANUAL(221),     
    PAGADO_IDO(230),
    DONACIONES(231),
    FALTANTE_IDO(240),    
    FALTANTE_MANUAL(241),        
    PROVISION_DIA_SIGUIENTE_REAL(298),
    PROVISION_DIA_SIGUIENTE_IDO(299),
    //PROVISION_DIA_SIGUIENTE_LINEA(299),
    AJUSTE_AUMENTO_PROVISION(710),
    AJUSTE_DISMINUCION_PROVISION(711),
    ARQUEO(712),
    AJUSTE_SOBRANTE(721),
    AJUSTE_FALTANTE(741),
    DIFERENCIAS(743),
    //OJO NUEVOS INCLUIR EN LOS AMBIENTES produccion y pruebas
    AUMENTO_PROVISION_LINEA(215),
    DISMINUCION_PROVISION_LINEA(216),
     //NUEVO MARZO 
    PROVISION_AYER(101),
    INGRESO(9710),
    EGRESO(9612);


    public Integer codigo;
    /**
     * Constructor de <code>TipoMovimientoCuadre</code>.
     */
    TipoMovimientoCuadre(Integer codigo) {
        this.codigo = codigo;
    }
      public static TipoMovimientoCuadre getTipoMovimientoCuadre(final int codigo) {
        for (TipoMovimientoCuadre tipoMovimientoCuadre : TipoMovimientoCuadre.values()) {
            if( tipoMovimientoCuadre.codigo == codigo) {
                return tipoMovimientoCuadre;
            }
        }
        return null;
    }
      
  
    


}

