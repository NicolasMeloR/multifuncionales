// 
// Decompiled by Procyon v0.5.36
// 

package com.davivienda.multifuncional.tablas.procesos.servicio;

import com.davivienda.sara.entitys.NotasdebitomultifuncionalPK;
import com.davivienda.sara.entitys.Notasdebitomultifuncional;
import java.util.Date;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import java.util.logging.Level;
import com.davivienda.sara.constantes.EstadoReintegro;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.ReintegrosmultiefectivoPK;
import com.davivienda.sara.entitys.Reintegrosmultiefectivo;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import java.util.List;
import com.davivienda.multifuncional.tablas.notasdebito.servicio.NotasDebitoMultiServicio;
import com.davivienda.multifuncional.tablas.reintegrosmultiefectivo.servicio.ReintegrosMultiEfectivoServicio;
import com.davivienda.sara.tablas.hostatm.servicio.HostAtmServicio;
import com.davivienda.sara.tablas.cajero.servicio.CajeroServicio;
import com.davivienda.sara.tablas.transaccion.servicio.TransaccionServicio;
import com.davivienda.sara.base.BaseEstadisticaServicio;

public class ReintegrosProcesosMultiServicio extends BaseEstadisticaServicio
{
    TransaccionServicio transaccionServicio;
    private CajeroServicio cajeroServicio;
    HostAtmServicio hostAtmServicio;
    ReintegrosMultiEfectivoServicio reintegrosMultiEfectivoServicio;
    NotasDebitoMultiServicio notasDebitoMultiServicio;
    private long valorMinimoReintegros;
    private long valorMinimoReintegrosNegativo;
    List<String> binesBanagrario;
    
    public ReintegrosProcesosMultiServicio(final EntityManager em) {
        super(em);
        this.valorMinimoReintegros = 9000L;
        this.valorMinimoReintegrosNegativo = -9000L;
        this.binesBanagrario = new ArrayList<String>();
        this.transaccionServicio = new TransaccionServicio(em);
        this.cajeroServicio = new CajeroServicio(em);
        this.hostAtmServicio = new HostAtmServicio(em);
        this.reintegrosMultiEfectivoServicio = new ReintegrosMultiEfectivoServicio(em);
        this.notasDebitoMultiServicio = new NotasDebitoMultiServicio(em);
    }
    
    public void guardarReintegro(final Integer codigoCajero, final Long valor, final String cuenta, final Integer tipoCuenta, final String usuario, final String talon, final Integer codigoOficinaMulti) throws EntityServicioExcepcion {
        final Reintegrosmultiefectivo newReintegro = new Reintegrosmultiefectivo();
        final ReintegrosmultiefectivoPK newReintegroPK = new ReintegrosmultiefectivoPK();
        try {
            final Cajero cajero = this.cajeroServicio.buscar(codigoCajero);
            final Date fecha = Fecha.getDateHoy();
            newReintegroPK.setHCodigocajero(codigoCajero);
            newReintegroPK.setHFechasistema(fecha);
            newReintegroPK.setHTalon(Cadena.aInteger(talon));
            newReintegro.setReintegrosmultiefectivoPK(newReintegroPK);
            newReintegro.setHDatostarjeta(cuenta);
            newReintegro.setHFecha(fecha);
            newReintegro.setHFiller("000000");
            newReintegro.setHIndices("00");
            newReintegro.setHNumerocuenta(cuenta);
            newReintegro.setHTipotarjeta("A");
            newReintegro.setHTipotransaccion(Integer.valueOf(46));
            newReintegro.setHValor(valor);
            newReintegro.setTCodigocajero(codigoCajero);
            newReintegro.setTFechacajero(fecha);
            newReintegro.setTSecuencia(new Long("0"));
            newReintegro.setTCodigotransaccion(new Integer(talon));
            newReintegro.setTNumerodecuentaconsignar(cuenta);
            newReintegro.setTValorconsignacion(valor);
            newReintegro.setTTipocuenta(tipoCuenta);
            newReintegro.setEstadoreintegro(EstadoReintegro.INICIADO.getEstado());
            newReintegro.setValorajustado(valor);
            newReintegro.setHCodigooficina(codigoOficinaMulti);
            newReintegro.setTipocuentareintegro(tipoCuenta);
            this.reintegrosMultiEfectivoServicio.adicionar(newReintegro);
        }
        catch (IllegalStateException ex) {
            ReintegrosProcesosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ReintegrosProcesosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
    
    public void actualizar(final Reintegrosmultiefectivo objetoModificado) throws EntityServicioExcepcion {
        this.reintegrosMultiEfectivoServicio.actualizar(objetoModificado);
    }
    
    public void actualizarNotaDebito(final Notasdebitomultifuncional objetoModificado) throws EntityServicioExcepcion {
        this.notasDebitoMultiServicio.actualizar(objetoModificado);
    }
    
    public void guardarNotaDebito(final Integer codigoCajero, final Long valor, final String cuenta, final Integer tipoCuenta, final String usuario) throws EntityServicioExcepcion {
        final Notasdebitomultifuncional newNotaDebito = new Notasdebitomultifuncional();
        final NotasdebitomultifuncionalPK newNotaDebitoPK = new NotasdebitomultifuncionalPK();
        try {
            final Cajero cajero = this.cajeroServicio.buscar(codigoCajero);
            final Date fecha = Fecha.getDateHoy();
            newNotaDebitoPK.setCodigocajero(codigoCajero);
            newNotaDebitoPK.setFecha(fecha);
            newNotaDebito.setNotasdebitomultifuncionalPK(newNotaDebitoPK);
            newNotaDebito.setUsuariocrea(usuario);
            newNotaDebito.setCodigooficina(cajero.getOficinaMulti().getCodigooficinamulti());
            newNotaDebito.setNumerocuenta(cuenta);
            newNotaDebito.setValor(valor);
            newNotaDebito.setValorajustado(valor);
            newNotaDebito.setEstado(EstadoReintegro.INICIADO.getEstado());
            newNotaDebito.setTipocuenta(tipoCuenta);
            this.notasDebitoMultiServicio.adicionar(newNotaDebito);
        }
        catch (IllegalStateException ex) {
            ReintegrosProcesosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "No se tiene acceso a la unidad de persistencia ", ex);
            throw new EntityServicioExcepcion(ex);
        }
        catch (IllegalArgumentException ex2) {
            ReintegrosProcesosMultiServicio.configApp.loggerApp.log(Level.SEVERE, "El par\u00e1metro no es v\u00e1lido ", ex2);
            throw new EntityServicioExcepcion(ex2);
        }
    }
}
