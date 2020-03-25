/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.davivienda.sara.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lpulgari
 */
@Entity
@Table(name = "REINTEGROSMULTIEFECTIVO")

@NamedQueries( {
   
    @NamedQuery(
    name = "Reintegrosmultiefectivo.todos", 
             query = "select object(txm) from Reintegrosmultiefectivo txm"),
               @NamedQuery(
         name = "Reintegrosmultiefectivo.RangoFecha",
         query = "select object(obj) from Reintegrosmultiefectivo obj " +
            "        where  obj.hFecha between :fechaInicial and :fechaFinal " + 
            "        order by obj.tFechacajero") ,
     @NamedQuery(
     name = "Reintegrosmultiefectivo.RegistroUnico",
     query = "select object(obj) from Reintegrosmultiefectivo obj " +
                 "where obj.reintegrosmultiefectivoPK.hCodigocajero = :codigoCajero and " +
                        "obj.reintegrosmultiefectivoPK.hFechasistema = :fechaSistema and " + 
                        "obj.reintegrosmultiefectivoPK.hTalon = :talon"),
      @NamedQuery(
         name = "Reintegrosmultiefectivo.CajeroTalonCuenta",
         query = "select object(obj) from Reintegrosmultiefectivo obj " +
            "        where  obj.reintegrosmultiefectivoPK.hFechasistema between :fechaInicial and :fechaFinal and" +
            "        obj.reintegrosmultiefectivoPK.hCodigocajero = :codigoCajero and " +
            "        obj.reintegrosmultiefectivoPK.hTalon = :talon and "+
            "        obj.hNumerocuenta = :numeroCuenta and "+
            "        obj.hValor = :valor "+
            "        order by obj.tFechacajero")
})


public class Reintegrosmultiefectivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReintegrosmultiefectivoPK reintegrosmultiefectivoPK;
    @Column(name = "H_NUMEROCUENTA")
    private String hNumerocuenta;
    @Column(name = "H_DATOSTARJETA")
    private String hDatostarjeta;
    @Column(name = "H_FECHA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date hFecha;
    @Column(name = "H_TIPOTRANSACCION", nullable = false)
    private Integer hTipotransaccion;
    @Column(name = "H_CODIGOOFICINA", nullable = false)
    private Integer hCodigooficina;
    @Column(name = "H_INDICES")
    private String hIndices;
    @Column(name = "H_TIPOTARJETA")
    private String hTipotarjeta;
    @Column(name = "H_VALOR")
    private Long hValor;
    @Column(name = "H_FILLER")
    private String hFiller;
    @Column(name = "T_CODIGOTRANSACCION", nullable = false)
    private Integer tCodigotransaccion;
    @Column(name = "T_ESTADO")
    private Integer tEstado;
    @Column(name = "T_CODIGOCAJERO", nullable = false)
    private Integer tCodigocajero;
    @Column(name = "T_NUMEROCORTE")
    private Integer tNumerocorte;
    @Column(name = "T_TRANSACCIONCONSECUTIVO")
    private Integer tTransaccionconsecutivo;
    @Column(name = "T_FECHACIERRE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tFechacierre;
    @Column(name = "T_FECHACAJERO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tFechacajero;
    @Column(name = "T_TIPOCUENTA")
    private Integer tTipocuenta;
    @Column(name = "T_NUMERODECUENTACONSIGNAR")
    private String tNumerodecuentaconsignar;
    @Column(name = "T_NUMERODECUENTAHOMOLOGA")
    private String tNumerodecuentahomologa;
    @Column(name = "T_VALORCONSIGNACION")
    private Long tValorconsignacion;
    @Column(name = "T_NOBILLETESND")
    private Integer tNobilletesnd;
    @Column(name = "T_NOBILLETESDENF")
    private Integer tNobilletesdenf;
    @Column(name = "T_NOBILLETESDENE")
    private Integer tNobilletesdene;
    @Column(name = "T_NOBILLETESDEND")
    private Integer tNobilletesdend;
    @Column(name = "T_NOBILLETESDENC")
    private Integer tNobilletesdenc;
    @Column(name = "T_NOBILLETESDENB")
    private Integer tNobilletesdenb;
    @Column(name = "T_NOBILLETESDENA")
    private Integer tNobilletesdena;
    @Column(name = "T_TOTALBILLETESCONSIG")
    private Integer tTotalbilletesconsig;
    @Column(name = "T_SECUENCIA", nullable = false)
    private Long tSecuencia;
    @Column(name = "VALORAJUSTADO")
    private Long valorajustado;
    @Column(name = "ESTADOREINTEGRO")
    private Integer estadoreintegro;
    @Column(name = "USUARIOSREVISA")
    private String usuariosrevisa;
    @Column(name = "USUARIOAUTORIZA")
    private String usuarioautoriza;
    @Column(name = "FECHAREINTEGRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechareintegro;
    @Column(name = "ERRORREINTEGRO")
    private String errorreintegro;
    @Column(name = "TIPOCUENTAREINTEGRO")
    private Integer tipocuentareintegro;
    @Column(name = "VERSION")
    private Integer version;

    public Reintegrosmultiefectivo() {
    }

    public Reintegrosmultiefectivo(ReintegrosmultiefectivoPK reintegrosmultiefectivoPK) {
        this.reintegrosmultiefectivoPK = reintegrosmultiefectivoPK;
    }

    public Reintegrosmultiefectivo(ReintegrosmultiefectivoPK reintegrosmultiefectivoPK, Date hFecha, Integer hTipotransaccion, Integer hCodigooficina, Integer tCodigotransaccion, Integer tCodigocajero, Date tFechacajero, Long tSecuencia) {
        this.reintegrosmultiefectivoPK = reintegrosmultiefectivoPK;
        this.hFecha = hFecha;
        this.hTipotransaccion = hTipotransaccion;
        this.hCodigooficina = hCodigooficina;
        this.tCodigotransaccion = tCodigotransaccion;
        this.tCodigocajero = tCodigocajero;
        this.tFechacajero = tFechacajero;
        this.tSecuencia = tSecuencia;
    }

    public Reintegrosmultiefectivo(Integer hCodigocajero, Date hFechasistema, Integer hTalon) {
        this.reintegrosmultiefectivoPK = new ReintegrosmultiefectivoPK(hCodigocajero, hFechasistema, hTalon);
    }

    public ReintegrosmultiefectivoPK getReintegrosmultiefectivoPK() {
        return reintegrosmultiefectivoPK;
    }

    public void setReintegrosmultiefectivoPK(ReintegrosmultiefectivoPK reintegrosmultiefectivoPK) {
        this.reintegrosmultiefectivoPK = reintegrosmultiefectivoPK;
    }

    public String getHNumerocuenta() {
        return hNumerocuenta;
    }

    public void setHNumerocuenta(String hNumerocuenta) {
        this.hNumerocuenta = hNumerocuenta;
    }

    public String getHDatostarjeta() {
        return hDatostarjeta;
    }

    public void setHDatostarjeta(String hDatostarjeta) {
        this.hDatostarjeta = hDatostarjeta;
    }

    public Date getHFecha() {
        return hFecha;
    }

    public void setHFecha(Date hFecha) {
        this.hFecha = hFecha;
    }
   

    public String getHIndices() {
        return hIndices;
    }

    public void setHIndices(String hIndices) {
        this.hIndices = hIndices;
    }

    public String getHTipotarjeta() {
        return hTipotarjeta;
    }

    public void setHTipotarjeta(String hTipotarjeta) {
        this.hTipotarjeta = hTipotarjeta;
    }

    public Long getHValor() {
        return hValor;
    }

    public void setHValor(Long hValor) {
        this.hValor = hValor;
    }

    public String getHFiller() {
        return hFiller;
    }

    public void setHFiller(String hFiller) {
        this.hFiller = hFiller;
    }

   
   
    public Integer getTTransaccionconsecutivo() {
        return tTransaccionconsecutivo;
    }

    public void setTTransaccionconsecutivo(Integer tTransaccionconsecutivo) {
        this.tTransaccionconsecutivo = tTransaccionconsecutivo;
    }

    public Date getTFechacierre() {
        return tFechacierre;
    }

    public void setTFechacierre(Date tFechacierre) {
        this.tFechacierre = tFechacierre;
    }

    public Date getTFechacajero() {
        return tFechacajero;
    }

    public void setTFechacajero(Date tFechacajero) {
        this.tFechacajero = tFechacajero;
    }

    public Integer getTTipocuenta() {
        return tTipocuenta;
    }

    public void setTTipocuenta(Integer tTipocuenta) {
        this.tTipocuenta = tTipocuenta;
    }

    public String getTNumerodecuentaconsignar() {
        return tNumerodecuentaconsignar;
    }

    public void setTNumerodecuentaconsignar(String tNumerodecuentaconsignar) {
        this.tNumerodecuentaconsignar = tNumerodecuentaconsignar;
    }

    public String getTNumerodecuentahomologa() {
        return tNumerodecuentahomologa;
    }

    public void setTNumerodecuentahomologa(String tNumerodecuentahomologa) {
        this.tNumerodecuentahomologa = tNumerodecuentahomologa;
    }

    public Long getTValorconsignacion() {
        return tValorconsignacion;
    }

    public void setTValorconsignacion(Long tValorconsignacion) {
        this.tValorconsignacion = tValorconsignacion;
    }   
   

    public Long getValorajustado() {
        return valorajustado;
    }

    public void setValorajustado(Long valorajustado) {
        this.valorajustado = valorajustado;
    }

    

    public String getUsuariosrevisa() {
        return usuariosrevisa;
    }

    public void setUsuariosrevisa(String usuariosrevisa) {
        this.usuariosrevisa = usuariosrevisa;
    }

    public String getUsuarioautoriza() {
        return usuarioautoriza;
    }

    public void setUsuarioautoriza(String usuarioautoriza) {
        this.usuarioautoriza = usuarioautoriza;
    }

    public Date getFechareintegro() {
        return fechareintegro;
    }

    public void setFechareintegro(Date fechareintegro) {
        this.fechareintegro = fechareintegro;
    }

    public String getErrorreintegro() {
        return errorreintegro;
    }

    public void setErrorreintegro(String errorreintegro) {
        this.errorreintegro = errorreintegro;
    }

        public Integer getEstadoreintegro() {
        return estadoreintegro;
    }

    public void setEstadoreintegro(Integer estadoreintegro) {
        this.estadoreintegro = estadoreintegro;
    }

    public Integer getHCodigooficina() {
        return hCodigooficina;
    }

    public void setHCodigooficina(Integer hCodigooficina) {
        this.hCodigooficina = hCodigooficina;
    }

    public Integer getHTipotransaccion() {
        return hTipotransaccion;
    }

    public void setHTipotransaccion(Integer hTipotransaccion) {
        this.hTipotransaccion = hTipotransaccion;
    }

    public Integer getTCodigocajero() {
        return tCodigocajero;
    }

    public void setTCodigocajero(Integer tCodigocajero) {
        this.tCodigocajero = tCodigocajero;
    }

    public Integer getTCodigotransaccion() {
        return tCodigotransaccion;
    }

    public void setTCodigotransaccion(Integer tCodigotransaccion) {
        this.tCodigotransaccion = tCodigotransaccion;
    }

    public Integer getTEstado() {
        return tEstado;
    }

    public void setTEstado(Integer tEstado) {
        this.tEstado = tEstado;
    }

    public Integer getTNobilletesdena() {
        return tNobilletesdena;
    }

    public void setTNobilletesdena(Integer tNobilletesdena) {
        this.tNobilletesdena = tNobilletesdena;
    }

    public Integer getTNobilletesdenb() {
        return tNobilletesdenb;
    }

    public void setTNobilletesdenb(Integer tNobilletesdenb) {
        this.tNobilletesdenb = tNobilletesdenb;
    }

    public Integer getTNobilletesdenc() {
        return tNobilletesdenc;
    }

    public void setTNobilletesdenc(Integer tNobilletesdenc) {
        this.tNobilletesdenc = tNobilletesdenc;
    }

    public Integer getTNobilletesdend() {
        return tNobilletesdend;
    }

    public void setTNobilletesdend(Integer tNobilletesdend) {
        this.tNobilletesdend = tNobilletesdend;
    }

    public Integer getTNobilletesdene() {
        return tNobilletesdene;
    }

    public void setTNobilletesdene(Integer tNobilletesdene) {
        this.tNobilletesdene = tNobilletesdene;
    }

    public Integer getTNobilletesdenf() {
        return tNobilletesdenf;
    }

    public void setTNobilletesdenf(Integer tNobilletesdenf) {
        this.tNobilletesdenf = tNobilletesdenf;
    }

    public Integer getTNobilletesnd() {
        return tNobilletesnd;
    }

    public void setTNobilletesnd(Integer tNobilletesnd) {
        this.tNobilletesnd = tNobilletesnd;
    }

    public Integer getTNumerocorte() {
        return tNumerocorte;
    }

    public void setTNumerocorte(Integer tNumerocorte) {
        this.tNumerocorte = tNumerocorte;
    }

    public Long getTSecuencia() {
        return tSecuencia;
    }

    public void setTSecuencia(Long tSecuencia) {
        this.tSecuencia = tSecuencia;
    }

    public Integer getTTotalbilletesconsig() {
        return tTotalbilletesconsig;
    }

    public void setTTotalbilletesconsig(Integer tTotalbilletesconsig) {
        this.tTotalbilletesconsig = tTotalbilletesconsig;
    }

    public Integer getTipocuentareintegro() {
        return tipocuentareintegro;
    }

    public void setTipocuentareintegro(Integer tipocuentareintegro) {
        this.tipocuentareintegro = tipocuentareintegro;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reintegrosmultiefectivoPK != null ? reintegrosmultiefectivoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reintegrosmultiefectivo)) {
            return false;
        }
        Reintegrosmultiefectivo other = (Reintegrosmultiefectivo) object;
        if ((this.reintegrosmultiefectivoPK == null && other.reintegrosmultiefectivoPK != null) || (this.reintegrosmultiefectivoPK != null && !this.reintegrosmultiefectivoPK.equals(other.reintegrosmultiefectivoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.davivienda.sara.entitys.Reintegrosmultiefectivo[reintegrosmultiefectivoPK=" + reintegrosmultiefectivoPK + "]";
    }
    
     public Reintegrosmultiefectivo actualizarEntity(Reintegrosmultiefectivo obj) {   
         
    setReintegrosmultiefectivoPK(obj.reintegrosmultiefectivoPK);    
    setHNumerocuenta(obj.hNumerocuenta);    
    setHDatostarjeta(obj.hDatostarjeta);
    setHFecha(obj.hFecha);   
    setHTipotransaccion(obj.hTipotransaccion);
    setHCodigooficina(obj.hCodigooficina);
    setHIndices(obj.hIndices);
    setHTipotarjeta(obj.hTipotarjeta);
    setHValor(obj.hValor);
    setHFiller(obj.hFiller);
    setTCodigotransaccion(obj.tCodigotransaccion);
    setTEstado(obj.tEstado);
    setTCodigocajero(obj.tCodigocajero);
    setTNumerocorte(obj.tNumerocorte);
    setTTransaccionconsecutivo(obj.tTransaccionconsecutivo);
    setTFechacierre(obj.tFechacierre);
    setTFechacajero(obj.tFechacajero);
    setTTipocuenta(obj.tTipocuenta);
    setTNumerodecuentaconsignar(obj.tNumerodecuentaconsignar);
    setTNumerodecuentahomologa(obj.tNumerodecuentahomologa);
    setTValorconsignacion(obj.tValorconsignacion);
    setTNobilletesnd(obj.tNobilletesnd);
    setTNobilletesdenf(obj.tNobilletesdenf);
    setTNobilletesdene(obj.tNobilletesdene);
    setTNobilletesdend(obj.tNobilletesdend);
    setTNobilletesdenc(obj.tNobilletesdenc);
    setTNobilletesdenb(obj.tNobilletesdenb);
    setTNobilletesdena(obj.tNobilletesdena);
    setTTotalbilletesconsig(obj.tTotalbilletesconsig);
    setTSecuencia(obj.tSecuencia);
    setValorajustado(obj.valorajustado);
    setEstadoreintegro(obj.estadoreintegro);
    setUsuariosrevisa(obj.usuariosrevisa);
    setUsuarioautoriza(obj.usuarioautoriza);
    setFechareintegro(obj.fechareintegro);
    setErrorreintegro(obj.errorreintegro);
    setTipocuentareintegro(obj.tipocuentareintegro);
    setVersion(obj.version);
       
    return this;
            
    }
    

}
