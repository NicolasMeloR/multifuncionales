

package com.davivienda.sara.procesos.reintegros.servicio;


import com.davivienda.sara.base.BaseEntityServicio;
import com.davivienda.sara.tablas.reintegros.servicio.ReintegrosServicio;
import com.davivienda.sara.base.BaseEstadisticaServicio;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.HostAtm;
import com.davivienda.sara.entitys.transaccion.SumatoriaTransaccionesHostBean;
import com.davivienda.sara.entitys.transaccion.Transaccion;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
import com.davivienda.sara.tablas.hostatm.servicio.HostAtmServicio;

import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.Cadena;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.davivienda.sara.entitys.Reintegros;
import com.davivienda.sara.entitys.ReintegrosPK;
import com.davivienda.sara.constantes.EstadoReintegro; 	
import com.davivienda.sara.constantes.TipoCuentaReintegro;
import com.davivienda.sara.entitys.CarguesMasivosReintegrosTemp;
import com.davivienda.sara.entitys.CarmasReintegrosTemp;
import com.davivienda.sara.entitys.Notasdebito;
import com.davivienda.sara.entitys.NotasdebitoPK;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.tablas.confmodulosaplicacion.servicio.ConfModulosAplicacionServicio;
import com.davivienda.sara.tablas.notasdebito.servicio.NotasDebitoServicio;

/**
 * TransaccionEstadisticaServicio - 1/09/2008
 * Descripción : Estadísticas de las transacciones generadas por los cajeros
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class CarguesMasivosReintegrosTempServicio extends BaseEntityServicio<CarmasReintegrosTemp>{

    public CarguesMasivosReintegrosTempServicio(EntityManager em, Class claseEntity) {
        super(em, claseEntity);
    }

}

