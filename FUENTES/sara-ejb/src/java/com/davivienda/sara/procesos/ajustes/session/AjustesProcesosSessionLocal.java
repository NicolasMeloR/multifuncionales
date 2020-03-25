/*
 * Banco Davivienda 2008
 * Proyecto Babel - Operación Cajeros Automáticos
 * Versión  1.0
 */
package com.davivienda.sara.procesos.ajustes.session;


import com.davivienda.utilidades.ws.gestor.cliente.ResumenAjustes;
import javax.ejb.Local;
import java.math.BigDecimal;

/**
 *
 * @author jjvargas
 */
@Local
public interface AjustesProcesosSessionLocal {


    public String realizarAjustePorSobrante(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste );
    public String realizarAjustePorFaltante(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste );
    public String realizarAjusteAumentoProvision(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste );
    public String realizarAjusteDisminucionProvision(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste );
    public String ajustarIngreso(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste, short concepto );
    public String ajustarEgreso(String usuario, Integer codigoCajero, Integer codigoOcca, BigDecimal valorAjuste, short concepto );
    public ResumenAjustes[] consultarInformeTotalesATM(Integer codigoCajero, Integer codigoOcca);
    public  ResumenAjustes[] consultarResumenIDOTerminal(Integer codigoOcca);
    public  ResumenAjustes[] consultarResumenIDOOficina(Integer codigoOcca);

  

 
    
    }
