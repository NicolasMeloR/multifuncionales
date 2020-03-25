package com.davivienda.sara.diarioelectronico.general.bean;

/**
 * DiarioElectronicoBean.java
 * 
 * Fecha       :  31/05/2007, 11:38:59 AM
 * Descripcion :  Contine la informacion de cada uno de los regitros de un diario electronico
 * 
 * @author     : jjvargas
 * @version    : $Id$
 */
public class DiarioElectronicoBean {
    
    public Integer secuencia;
    
    public String tipoRegistro;
    
    public String datos;

    /**
     * Crea una nueva instancia de <code>DiarioElectronicoBean</code>.
     */
    public DiarioElectronicoBean() {
    }
    
    /**
     * 
     * @return 
     */
    public String getDatos(){
        return this.datos;
    }


}

