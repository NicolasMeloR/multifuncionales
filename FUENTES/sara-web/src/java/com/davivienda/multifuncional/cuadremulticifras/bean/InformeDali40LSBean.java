/*
 * InformeCuadreCifrasBean.java
 *
 * Created on 26/09/2007, 03:05:55 PM
 *
 * Babel : 1.0
 *
 */

package com.davivienda.multifuncional.cuadremulticifras.bean;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author jjvargas
 */
public class InformeDali40LSBean {
    
    private Date fecha;
    private Integer codigoCajero ;
    
    private Long  depositoEfectivoATM ;
    private Long  depositoChequeATM ;
    private Long  pagosTCEfectivoATM ;
    private Long  pagosTCChequeATM ;
    private Long  pagosFMEfectivoATM ;
    private Long  pagosFMChequeATM ;
    private Long  recaudosEfectivoATM ;
    private Long  recaudosChequeATM ;

    private Long  depositoEfectivoStratus ;
    private Long  depositoChequeStratus ;
    private Long  depositoEfectivoDamasStratus ;
    private Long  depositoChequeDamasFDiarioStratus ;
    private Long  depositoEfectivoFDiarioStratus ;
    private Long  depositoChequeFDiarioStratus ;
    private Long  depositoEfectivoCCorienteStratus ;
    private Long  depositoChequeCCorienteStratus ;
    private Long  pagosTCEfectivoStratus ;
    private Long  pagosTCChequeStratus ;
    private Long  pagosFMEfectivoStratus ;
    private Long  pagosFMChequeStratus ;
    private Long  recaudosEfectivoStratus ;
    private Long  recaudosChequeStratus ;

    
    public DetalleEfectivoBean detEfectivoIniJor  ;
    public DetalleEfectivoBean detEfectivoHayJor  ;
    public DetalleEfectivoBean detEfectivoRecibido ;
    public DetalleEfectivoBean detEfectivoRetract  ;
    
    private Long  totalRetractEfectivoValor ;
    private Long  totalRetractEfectivoCant ;
   
    private Long  totalRetractChequeValor ;
    private Long  totalRetractChequeCant ;
   
    
    private Long  depositosTotalATM ;
    private Long  depositosTotalStratus ;
    
    private Long  faltantesEfectivo ;
    private Long  faltantesCheque ;
    private Long  sobrantesEfectivo ;
    private Long  sobrantesCheque ;
   
    
    
    public InformeDali40LSBean() {
        
    depositoEfectivoATM = new Long(0);
    depositoChequeATM  = new Long(0);
    pagosTCEfectivoATM = new Long(0);
    pagosTCChequeATM  = new Long(0);
    pagosFMEfectivoATM  = new Long(0);
    pagosFMChequeATM  = new Long(0);
    recaudosEfectivoATM  = new Long(0);
    recaudosChequeATM = new Long(0);
    
    depositoEfectivoStratus = new Long(0);
    depositoChequeStratus  = new Long(0);
    pagosTCEfectivoStratus = new Long(0);
    pagosTCChequeStratus  = new Long(0);
    pagosFMEfectivoStratus  = new Long(0);
    pagosFMChequeStratus  = new Long(0);
    recaudosEfectivoStratus  = new Long(0);
    recaudosChequeStratus = new Long(0);
    
    faltantesEfectivo = new Long(0);
    faltantesCheque= new Long(0); 
    sobrantesEfectivo = new Long(0);
    sobrantesCheque = new Long(0);
    
    totalRetractEfectivoValor = new Long(0);
    totalRetractEfectivoCant = new Long(0);
    
    totalRetractChequeValor = new Long(0);
    totalRetractChequeCant = new Long(0);
    
   
        
    }
    
    public String getFecha() {
     
            return Fecha.aCadena(fecha, FormatoFecha.DEFECTO_SEPARADOR_GUION);
      
    }
    
    public void setFecha(Date fecha) {
        
        this.fecha = fecha;
    }

    public Integer getCodigoCajero() {
        return codigoCajero;
    }

    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }
    
    

    public Long getDepositoChequeATM() {
        return depositoChequeATM;
    }

    public void setDepositoChequeATM(Long depositoChequeATM) {
        this.depositoChequeATM = depositoChequeATM;
    }

    public Long getDepositoChequeStratus() {
        return depositoChequeStratus;
    }

    public void setDepositoChequeStratus(Long depositoChequeStratus) {
        this.depositoChequeStratus = depositoChequeStratus;
    }

    public Long getDepositoEfectivoATM() {
        return depositoEfectivoATM;
    }

    public void setDepositoEfectivoATM(Long depositoEfectivoATM) {
        this.depositoEfectivoATM = depositoEfectivoATM;
    }

    public Long getDepositoEfectivoStratus() {
        return depositoEfectivoStratus;
    }

    public void setDepositoEfectivoStratus(Long depositoEfectivoStratus) {
        this.depositoEfectivoStratus = depositoEfectivoStratus;
    }

    public Long getDepositoChequeCCorienteStratus() {
        return depositoChequeCCorienteStratus;
    }

    public void setDepositoChequeCCorienteStratus(Long depositoChequeCCorienteStratus) {
        this.depositoChequeCCorienteStratus = depositoChequeCCorienteStratus;
    }

    public Long getDepositoChequeDamasFDiarioStratus() {
        return depositoChequeDamasFDiarioStratus;
    }

    public void setDepositoChequeDamasFDiarioStratus(Long depositoChequeDamasFDiarioStratus) {
        this.depositoChequeDamasFDiarioStratus = depositoChequeDamasFDiarioStratus;
    }

    public Long getDepositoChequeFDiarioStratus() {
        return depositoChequeFDiarioStratus;
    }

    public void setDepositoChequeFDiarioStratus(Long depositoChequeFDiarioStratus) {
        this.depositoChequeFDiarioStratus = depositoChequeFDiarioStratus;
    }

    public Long getDepositoEfectivoCCorienteStratus() {
        return depositoEfectivoCCorienteStratus;
    }

    public void setDepositoEfectivoCCorienteStratus(Long depositoEfectivoCCorienteStratus) {
        this.depositoEfectivoCCorienteStratus = depositoEfectivoCCorienteStratus;
    }

    public Long getDepositoEfectivoDamasStratus() {
        return depositoEfectivoDamasStratus;
    }

    public void setDepositoEfectivoDamasStratus(Long depositoEfectivoDamasStratus) {
        this.depositoEfectivoDamasStratus = depositoEfectivoDamasStratus;
    }

    public Long getDepositoEfectivoFDiarioStratus() {
        return depositoEfectivoFDiarioStratus;
    }

    public void setDepositoEfectivoFDiarioStratus(Long depositoEfectivoFDiarioStratus) {
        this.depositoEfectivoFDiarioStratus = depositoEfectivoFDiarioStratus;
    }

    public Long getPagosFMChequeATM() {
        return pagosFMChequeATM;
    }

    public void setPagosFMChequeATM(Long pagosFMChequeATM) {
        this.pagosFMChequeATM = pagosFMChequeATM;
    }

    public Long getPagosFMChequeStratus() {
        return pagosFMChequeStratus;
    }

    public void setPagosFMChequeStratus(Long pagosFMChequeStratus) {
        this.pagosFMChequeStratus = pagosFMChequeStratus;
    }

    public Long getPagosFMEfectivoATM() {
        return pagosFMEfectivoATM;
    }

    public void setPagosFMEfectivoATM(Long pagosFMEfectivoATM) {
        this.pagosFMEfectivoATM = pagosFMEfectivoATM;
    }

    public Long getPagosFMEfectivoStratus() {
        return pagosFMEfectivoStratus;
    }

    public void setPagosFMEfectivoStratus(Long pagosFMEfectivoStratus) {
        this.pagosFMEfectivoStratus = pagosFMEfectivoStratus;
    }

    public Long getPagosTCChequeATM() {
        return pagosTCChequeATM;
    }

    public void setPagosTCChequeATM(Long pagosTCChequeATM) {
        this.pagosTCChequeATM = pagosTCChequeATM;
    }

    public Long getPagosTCChequeStratus() {
        return pagosTCChequeStratus;
    }

    public void setPagosTCChequeStratus(Long pagosTCChequeStratus) {
        this.pagosTCChequeStratus = pagosTCChequeStratus;
    }

    public Long getPagosTCEfectivoATM() {
        return pagosTCEfectivoATM;
    }

    public void setPagosTCEfectivoATM(Long pagosTCEfectivoATM) {
        this.pagosTCEfectivoATM = pagosTCEfectivoATM;
    }

    public Long getPagosTCEfectivoStratus() {
        return pagosTCEfectivoStratus;
    }

    public void setPagosTCEfectivoStratus(Long pagosTCEfectivoStratus) {
        this.pagosTCEfectivoStratus = pagosTCEfectivoStratus;
    }

    public Long getRecaudosChequeATM() {
        return recaudosChequeATM;
    }

    public void setRecaudosChequeATM(Long recaudosChequeATM) {
        this.recaudosChequeATM = recaudosChequeATM;
    }

    public Long getRecaudosChequeStratus() {
        return recaudosChequeStratus;
    }

    public void setRecaudosChequeStratus(Long recaudosChequeStratus) {
        this.recaudosChequeStratus = recaudosChequeStratus;
    }

    public Long getRecaudosEfectivoATM() {
        return recaudosEfectivoATM;
    }

    public void setRecaudosEfectivoATM(Long recaudosEfectivoATM) {
        this.recaudosEfectivoATM = recaudosEfectivoATM;
    }

    public Long getRecaudosEfectivoStratus() {
        return recaudosEfectivoStratus;
    }

    public void setRecaudosEfectivoStratus(Long recaudosEfectivoStratus) {
        this.recaudosEfectivoStratus = recaudosEfectivoStratus;
    }

    public DetalleEfectivoBean getDetEfectivoHayJor() {
        return detEfectivoHayJor;
    }

    public void setDetEfectivoHayJor(DetalleEfectivoBean detEfectivoHayJor) {
        this.detEfectivoHayJor = detEfectivoHayJor;
    }

    public DetalleEfectivoBean getDetEfectivoIniJor() {
        return detEfectivoIniJor;
    }

    public void setDetEfectivoIniJor(DetalleEfectivoBean detEfectivoIniJor) {
        this.detEfectivoIniJor = detEfectivoIniJor;
    }

    public DetalleEfectivoBean getDetEfectivoRecibido() {
        return detEfectivoRecibido;
    }

    public void setDetEfectivoRecibido(DetalleEfectivoBean detEfectivoRecibido) {
        this.detEfectivoRecibido = detEfectivoRecibido;
    }

    public DetalleEfectivoBean getDetEfectivoRetract() {
        return detEfectivoRetract;
    }

    public void setDetEfectivoRetract(DetalleEfectivoBean detEfectivoRetract) {
        this.detEfectivoRetract = detEfectivoRetract;
    }

    public Long getTotalRetractChequeCant() {
        return totalRetractChequeCant;
    }

    public void setTotalRetractChequeCant(Long totalRetractChequeCant) {
        this.totalRetractChequeCant = totalRetractChequeCant;
    }

    public Long getTotalRetractChequeValor() {
        return totalRetractChequeValor;
    }

    public void setTotalRetractChequeValor(Long totalRetractChequeValor) {
        this.totalRetractChequeValor = totalRetractChequeValor;
    }

 

    
    
    
    public Long getDepositosTotalATM() {
        return depositosTotalATM;
    }

    public void setDepositosTotalATM(Long depositosTotalATM) {
        this.depositosTotalATM = depositosTotalATM;
    }

    public Long getDepositosTotalStratus() {
        return depositosTotalStratus;
    }

    public void setDepositosTotalStratus(Long depositosTotalStratus) {
        this.depositosTotalStratus = depositosTotalStratus;
    }

   
   
    public Long getFaltantesCheque() {
        return faltantesCheque;
    }

    public void setFaltantesCheque(Long faltantesCheque) {
        this.faltantesCheque = faltantesCheque;
    }

    public Long getFaltantesEfectivo() {
        return faltantesEfectivo;
    }

    public void setFaltantesEfectivo(Long faltantesEfectivo) {
        this.faltantesEfectivo = faltantesEfectivo;
        
    }

    public Long getTotalRetractEfectivoCant() {
        return totalRetractEfectivoCant;
    }

    public void setTotalRetractEfectivoCant(Long totalRetractEfectivoCant) {
        this.totalRetractEfectivoCant = totalRetractEfectivoCant;
    }

    public Long getTotalRetractEfectivoValor() {
        return totalRetractEfectivoValor;
    }

    public void setTotalRetractEfectivoValor(Long totalRetractEfectivoValor) {
        this.totalRetractEfectivoValor = totalRetractEfectivoValor;
    }
    
    

    public Long getSobrantesCheque() {
        return sobrantesCheque;
    }

    public void setSobrantesCheque(Long sobrantesCheque) {
        this.sobrantesCheque = sobrantesCheque;
    }

    public Long getSobrantesEfectivo() {
        return sobrantesEfectivo;
    }

    public void setSobrantesEfectivo(Long sobrantesEfectivo) {
        this.sobrantesEfectivo = sobrantesEfectivo;
    }
    

    
    
    
    
    
    
}
