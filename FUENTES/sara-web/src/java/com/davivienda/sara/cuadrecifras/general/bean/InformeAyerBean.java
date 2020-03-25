/*
 * InformeCuadreCifrasBean.java
 *
 * Created on 26/09/2007, 03:05:55 PM
 *
 * Babel : 1.0
 *
 */

package com.davivienda.sara.cuadrecifras.general.bean;
import com.davivienda.utilidades.conversion.Fecha;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.Numero;
import java.util.Date;

/**
 *
 * @author jjvargas
 */
public class InformeAyerBean {
    
    private Date fecha;
    private Long pagado ;
    private Long efectivoAtm ;
    private Long provisionEfectivo ;
    private Long provisionDiaSigIdo ;
    private Integer codigoCajero ;
    private Integer codigoOcca ;
    private String nombreOcca ;
    
    private Long  provisionAnterior ;
    private Long provisionDiaSgte ;
    private Long sumaSinPagadoIdo ;
    private Long  provisionAyer ;
  
    
   
    
    
    public InformeAyerBean() {
        pagado = new Long(0);
        efectivoAtm = new Long(0);
        provisionEfectivo= new Long(0);
        provisionDiaSigIdo=new Long(0);
        provisionAnterior= new Long(0);
        provisionDiaSgte=new Long(0);
        sumaSinPagadoIdo=new Long(0);
        sumaSinPagadoIdo=new Long(-1);
       
        
    }
    
    public String getFecha() {
     
            return Fecha.aCadena(fecha, FormatoFecha.DEFECTO_SEPARADOR_GUION);
      
    }
    
    public void setFecha(Date fecha) {
        
        this.fecha = fecha;
    }
    
   
    
    public String getpagado() {
        return Numero.aMoneda(pagado);
    }
    
    public void setpagado(Long pagado) {
        this.pagado = pagado;
    }
    
      public Long getNumericopagado() {
        return pagado;
    }
      
      public Long getNumericoefectivoAtm() {
      return efectivoAtm;
    }
    
    
     public String getefectivoAtm() {
        return Numero.aMoneda(efectivoAtm);
    }
      
    
    

    public void setefectivoAtm(Long efectivoAtm) {
        this.efectivoAtm = efectivoAtm;
    }
    
    public Integer getCodigoCajero() {
        return codigoCajero;
    }
    
    public void setCodigoCajero(Integer codigoCajero) {
        this.codigoCajero = codigoCajero;
    }
    
       
    public Integer getCodigoOcca() {
        return codigoOcca;
    }
    
    public void setCodigoOcca(Integer codigoOcca) {
        this.codigoOcca = codigoOcca;
    }
    

    
    public String getNombreOcca() {
        return nombreOcca;
    }
    
    public void setNombreOcca(String nombreOcca) {
        this.nombreOcca = nombreOcca;
    }
    
    public String getProvisionEfectivo() {
        return Numero.aMoneda(provisionEfectivo);
    }
    
    public void setProvisionEfectivo(Long provisionEfectivo) {
        this.provisionEfectivo = provisionEfectivo;
    }
    
      public Long getNumericoProvisionEfectivo() {
        return provisionEfectivo;
    }
    
    public Long getProvisionEfectivoAnterior() {
       
        return pagado+efectivoAtm;
    }
    
      public void setProvisionDiaSigIdo(Long provisionDiaSigIdo) {
        this.provisionDiaSigIdo = provisionDiaSigIdo;
    }
    
      public Long getProvisionDiaSigIdo() {
        return provisionDiaSigIdo;
    }
      
      
        public void setProvisionDiaSig(Long provisionDiaSgte) {
        this.provisionDiaSgte = provisionDiaSgte;
    }
    
    public Long getProvisionDiaSig() {
        return provisionDiaSgte;
    }
    
      
    public void setProvisionDiaAnterior(Long provisionAnterior) {
        this.provisionAnterior = provisionAnterior;
    }
    
      public Long getProvisionDiaAnterior() {
        return provisionAnterior;
    }
    
    
              
   public void setSumaSinPagadoIdo(Long sumaSinPagadoIdo) {
        this.sumaSinPagadoIdo = sumaSinPagadoIdo;
    }
    
   public Long getSumaSinPagadoIdo() {
        return sumaSinPagadoIdo;
    }

   public void setProvisionAyer(Long provisionAyer) {
        this.provisionAyer = provisionAyer;
    }
   
   public String getProvisionAyer() {
        return Numero.aMoneda( provisionAyer);
    }

  public Long getNumericoProvisionAyer() {
        return provisionAyer;
    }  
    
    
    
    
    
}
