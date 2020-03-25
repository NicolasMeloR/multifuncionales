// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.sara.procesos.historicoajustes.servicio;

import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Logger;
import com.davivienda.sara.entitys.HistoricoAjustes;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import com.davivienda.sara.tablas.historicoajustes.servicio.HistoricoAjustesServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.base.BaseServicio;

public class AdministradorProcesosHistoricoAjustesServicio extends BaseServicio
{
    private CajeroServicio cajeroServicio;
    private HistoricoAjustesServicio historicoAjusteServicio;
    @Resource
    private UserTransaction utx;
    
    public AdministradorProcesosHistoricoAjustesServicio(final EntityManager em) {
        super(em);
        this.cajeroServicio = new CajeroServicio(em);
        this.historicoAjusteServicio = new HistoricoAjustesServicio(em);
    }
    
    public void guardarHistoricoAjustes(final String usuario, final Integer codigoCajero, final Integer codigoOcca, final String tipoAjuste, final Date fecha, final BigDecimal valor, final String talon, final String error, final String descripcionError) throws EntityServicioExcepcion, IllegalArgumentException {
        final Integer codigoErrorProceso = 0;
        final HistoricoAjustes regHistoricoAjustes = new HistoricoAjustes();
        regHistoricoAjustes.setCodigoCajero(codigoCajero);
        regHistoricoAjustes.setCodigoOcca(codigoOcca);
        regHistoricoAjustes.setFecha(fecha);
        regHistoricoAjustes.setError(error);
        regHistoricoAjustes.setTipoAjuste(tipoAjuste);
        regHistoricoAjustes.setUsuario(usuario);
        regHistoricoAjustes.setValor(valor);
        regHistoricoAjustes.setTalon(talon);
        regHistoricoAjustes.setDescripcionError(descripcionError);
        try {
            this.historicoAjusteServicio.adicionar(regHistoricoAjustes);
        }
        catch (IllegalArgumentException ex) {
            this.historicoAjusteServicio.getConfigApp().loggerApp.info("Error al grabar los datos en HistoricoAjustes para codigoCajero " + codigoCajero + " " + ex.getMessage());
        }
        catch (Exception ex2) {
            Logger.getLogger("globalApp").info("Error cargando en HistoricoAjustes registro datos codigoCajero  :" + codigoCajero + " descripcion Error : " + ex2.getMessage());
        }
    }
}
