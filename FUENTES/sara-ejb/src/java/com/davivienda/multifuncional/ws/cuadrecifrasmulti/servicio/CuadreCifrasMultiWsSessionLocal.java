 /*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.multifuncional.ws.cuadrecifrasmulti.servicio;


import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustesMulti;
import javax.ejb.Local;
import java.math.BigDecimal;

/**
 *
 * @author jjvargas
 */
@Local
public interface CuadreCifrasMultiWsSessionLocal {


    public String realizarAjustePorSobrante(String usuario, Integer codigoCajero, Integer codigoOficina, BigDecimal valorAjuste );
    public String realizarAjustePorFaltante(String usuario, Integer codigoCajero, Integer codigoOficina, BigDecimal valorAjuste );
    public String realizarAjustePorSobranteArqueo(String usuario, Integer codigoCajero, Integer codigoOficina, BigDecimal valorAjuste,String nitTransportadora  );
    public String realizarAjustePorFaltanteArqueo(String usuario, Integer codigoCajero, Integer codigoOficina, BigDecimal valorAjuste,String nitTransportadora  );
    public String realizarDisminucionDeposito(String usuario,long codigoTransportadora,Integer codigoCajero, BigDecimal valorEfectivo, Integer codigoOficina);
    public ResumenAjustesMulti[] consultarInformeTotalesMultifuncional(Integer codigoCajero, Integer codigoOficina,Integer tipoConsulta, Integer indicadorTotales);


  

 
    
    }
