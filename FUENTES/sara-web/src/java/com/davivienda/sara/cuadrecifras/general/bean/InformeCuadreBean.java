/*
 * InformeCuadreBean.java
 *
 * Created on 26/09/2007, 03:50:37 PM
 *
 * Babel : 1.0
 */

package com.davivienda.sara.cuadrecifras.general.bean;


import com.davivienda.sara.entitys.Cajero;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

/**
 *
 * @author jjvargas
 */
public class InformeCuadreBean {
   
    public String fecha;
    public Cajero cajero;
    public Collection<InformeCuadreDetalleBean> registros  ;
    public Map<Integer,InformeCuadreDetalleBean> mapRegistros ;
    //OJO PONER EN UNA VARIABLE GLOBAL 
     //se utiliza para verificar que en el reporte no se muestren diferencias menores al valor 
    private long limiteDiferencia= -1000;
    
    
    
    
    public InformeCuadreBean() {
        registros = new ArrayList<InformeCuadreDetalleBean>();
        mapRegistros = new HashMap<Integer,InformeCuadreDetalleBean>();
    }
     public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public Cajero getCajero() {
        return cajero;
    }
    
    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }
    
    
    
    public Collection<InformeCuadreDetalleBean> getRegistros() {
        return registros;
    }
    
    public void setRegistros(Collection<InformeCuadreDetalleBean> registro) {
        this.registros = registro;
    }
    
    public Map<Integer,InformeCuadreDetalleBean> getMapRegistros() {
        return mapRegistros;
    }
    
    public void setMapRegistros(Map<Integer,InformeCuadreDetalleBean> mapRegistros) {
        this.mapRegistros = mapRegistros;
    }
    
    public boolean getDiferencia() {
//        boolean dif = false;
//        for (InformeCuadreDetalleBean informeCuadreDetalleBean : registros) {
//            if (informeCuadreDetalleBean.getTipoMovimientoCuadre().getLineaInfCuadre() == 10) {
//                if (informeCuadreDetalleBean.getValorCol1().compareTo(informeCuadreDetalleBean.getValorCol2()) != 0) {
//                    dif = true;
//                }
//            }            
//        }
//        return dif;
        
        //ojo para pruebas
        
       long  diferenciadiasgte=0;
        boolean dif = true;
        for (InformeCuadreDetalleBean informeCuadreDetalleBean : registros) {
            diferenciadiasgte=0;
         
            if (informeCuadreDetalleBean.getTipoMovimientoCuadre().getLineaInfCuadre() == 10) {
                
                if (informeCuadreDetalleBean.getNumericoValorCol1()==diferenciadiasgte )
                {
                
                }
                else
                { 
                  //diferenciadiasgte=informeCuadreDetalleBean.getNumericoValorCol1()-informeCuadreDetalleBean.getNumericoValorCol2();
                  diferenciadiasgte=informeCuadreDetalleBean.getDiferenciaNumerica();
                  if (diferenciadiasgte<limiteDiferencia )
                        {
                         dif = false;
                        }
                    else
                    {
                          //diferenciadiasgte!=0 
                        if (diferenciadiasgte > 0  )
                        {
                         dif = false;
                        }
                    }
               // if (informeCuadreDetalleBean.getValorCol1().compareTo(informeCuadreDetalleBean.getValorCol2()) == 0) {
//                 if (informeCuadreDetalleBean.getValorCol1().compareTo(informeCuadreDetalleBean.getValorCol2()) == 0) {
//                    dif = true;
//                }
                }
            }            
        }
        return dif;
    }
    
        public long getValorDiferencia() {

        
        long  diferenciadiasgte=0;
        long  diferenciadiasgteRetorna=0;
       
    
        for (InformeCuadreDetalleBean informeCuadreDetalleBean : registros) {
            diferenciadiasgte=-1;
            
            
         
            if (informeCuadreDetalleBean.getTipoMovimientoCuadre().getLineaInfCuadre() == 10) {
                
                //filitro los cajeros que no tiene datos de linea
                if (informeCuadreDetalleBean.getNumericoValorCol1()==diferenciadiasgte )
                {
                    
                       diferenciadiasgteRetorna=0;
                  
                    
                }
                else
                { 
                    //filitro los cajeros que no cortaron
                    if(informeCuadreDetalleBean.getNumericoValorCol1()==0 )
                       {
                               if(informeCuadreDetalleBean.getNumericoValorCol3()!=0 && informeCuadreDetalleBean.getNumericoValorCol2()==-1 )
                                {
                                     diferenciadiasgteRetorna=informeCuadreDetalleBean.getNumericoValorCol3()* -1;

                                }
                               else
                               {
                                    diferenciadiasgteRetorna = 0;
                               }
                        }
                        else
                        {

                                  //diferenciadiasgte=informeCuadreDetalleBean.getNumericoValorCol1()-informeCuadreDetalleBean.getNumericoValorCol2();
                                  diferenciadiasgte=informeCuadreDetalleBean.getDiferenciaNumerica();
                                  if (diferenciadiasgte<limiteDiferencia )
                                        {
                                         diferenciadiasgteRetorna = diferenciadiasgte;
                                        }
                                   else
                                    {
                                          //diferenciadiasgte!=0 
                                        if (diferenciadiasgte > 2  )
                                        {
                                         diferenciadiasgteRetorna = diferenciadiasgte;
                                        }
                                    }
                            }  
                 }
            }            
        }
        if(diferenciadiasgteRetorna==1)
        {
            diferenciadiasgteRetorna = 0;
        }
        return diferenciadiasgteRetorna;
    }
    
    
    
}
