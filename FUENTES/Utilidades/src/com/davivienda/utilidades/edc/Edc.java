package com.davivienda.utilidades.edc;

import java.util.Calendar;
import com.davivienda.utilidades.conversion.Cadena;
import com.davivienda.utilidades.conversion.Numero;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.davivienda.utilidades.conversion.FormatoFecha;
import com.davivienda.utilidades.conversion.Fecha;
import java.math.BigDecimal;
import java.util.Date;
import  javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
/**
 * Edc.java
 *
 * Fecha       :  30/05/2007, 07:07:52 PM
 * Descripción : Utilidad de de trabajo para operacions con el EDC de cajeros automáticos
 *
 * @author     : jjvargas
 * @version    : $Id$
 */
public class Edc {
    
    /**
     * Crea una nueva instancia de <code>Edc</code>.
     */
    public Edc() {
    }
    
    public static String aCadena(String linea){
        StringBuffer cadena = new StringBuffer();
        if (linea != null) {
            linea = linea.trim();
            if (linea.length() > 8 ) {
                String datos = linea.substring(27, linea.length() - 8);
                if (linea.charAt(0) != TipoRegistro.BEGIN_FILE.codigo) {
                    cadena.append(linea.substring(1,7)).append(" ");
                    cadena.append(TipoRegistro.getTipoRegistro(linea.charAt(0))).append(" ");
                    cadena.append(linea.substring(20,27)).append(" ");
                    for (CaracterFormato elem : CaracterFormato.values()) {
                        if (elem.activo) {
                            datos = datos.replace(elem.cadena, elem.representacion);
                        }
                    }
                    cadena.append(datos);
                } else {
                    cadena.append(TipoRegistro.BEGIN_FILE);
                }
            }
        }
        return cadena.toString();
    }
    
    /**
     * Formatea la información de diario electrónico y lo retorna como una cadena
     * @param tipoRegistro
     * @param informacion
     * @return String Cadena formateada
     */
    public static String aCadena(Character tipoRegistro, String informacion) {
                 StringBuffer cadena = new StringBuffer();
                 //ALVARO 12 MARZO
                 boolean blTRANSACTION_DATA =false;
                   //ALVARO 12 MARZO
        if (informacion != null) {
            if (informacion.length() > 8) {
                String datos  = informacion.substring(0, informacion.length() - 8);
                cadena.append(TipoRegistro.getTipoRegistro(tipoRegistro)).append(" ").append("\n");
                for (CaracterFormato elem : CaracterFormato.values()) {
                    if (elem.activo) {
                        datos = datos.replace(elem.cadena, elem.representacion);
                    }
                }
                //OJO REMPLAZAR EN BD SI ES NECESARIO AL GUARDAR EN BD reviso si es registro tipo 1 o No
                  //ALVARO 12 MARZO
                if (TipoRegistro.getTipoRegistro(tipoRegistro).equals(TipoRegistro.TRANSACTION_DATA)){
                    if(datos.contains("TOTALES CAJERO") == false)
                    {
                      blTRANSACTION_DATA=true;
                    }
                 }
                      
                  //ALVARO 12 MARZO
                  //if (TipoRegistro.getTipoRegistro(tipoRegistro).equals(TipoRegistro.TRANSACTION_DATA)) {
                    if ( blTRANSACTION_DATA){    
                    try {
                        String[] regTransaccion = datos.split(",");
                        cadena.append("Talón                   : ").append(regTransaccion[3]).append("\n");
                        cadena.append("Fecha                   : ").append(regTransaccion[4]).append(" ").append(regTransaccion[5]).append("\n");
                        cadena.append("Transacción             : ").append(regTransaccion[7]).append("\n");
                        cadena.append("Error Host              : ").append(regTransaccion[8]).append("\n");
                        cadena.append("Valor solicitado        : ").append(Numero.aMoneda(Cadena.aValor(regTransaccion[9],"0"))).append(" \n");
                        cadena.append("Tarjeta                 : ").append(regTransaccion[10]).append("\n");
                        cadena.append("Cuenta                  : ").append(regTransaccion[11]).append("\n");
                        cadena.append("Valor entregado         : ").append(Numero.aMoneda(Cadena.aValor(regTransaccion[12],"0"))).append("\n");
                        cadena.append("CódigoTerminación       : ").append(regTransaccion[13]).append(" \n");
                        if (regTransaccion.length > 14) {
                           // cadena.append("Contadores        : ").append(regTransaccion[14]).append("\n");
                        cadena.append("Transaccion InfoEstado  : ").append(regTransaccion[14].substring(1,5)).append(" \n");
                        cadena.append("Terminación Transacción : ").append(regTransaccion[14].substring(5,6)).append(" \n");
                        cadena.append("Estado de Retract       : ").append(regTransaccion[14].substring(6,7)).append(" \n");
                        cadena.append("Denominacion Gaveta 1   : ").append(Numero.aMoneda(Cadena.aInteger(DenominacionBillete.getDenominacionBillete(regTransaccion[14].charAt(11)).toString()))).append(" \n");
                        cadena.append("Denominacion Gaveta 2   : ").append(Numero.aMoneda(Cadena.aInteger(DenominacionBillete.getDenominacionBillete(regTransaccion[14].charAt(12)).toString()))).append(" \n");
                        cadena.append("Denominacion Gaveta 3   : ").append(Numero.aMoneda(Cadena.aInteger(DenominacionBillete.getDenominacionBillete(regTransaccion[14].charAt(13)).toString()))).append(" \n");
                        cadena.append("Denominacion Gaveta 4   : ").append(Numero.aMoneda(Cadena.aInteger(DenominacionBillete.getDenominacionBillete(regTransaccion[14].charAt(14)).toString()))).append(" \n");
                        cadena.append("# Billetes Ent Gaveta 1 : ").append(Cadena.aInteger(regTransaccion[14],15,20)).append(" \n");
                        cadena.append("# Billetes Ent Gaveta 2 : ").append(Cadena.aInteger(regTransaccion[14],20,25)).append(" \n");
                        cadena.append("# Billetes Ent Gaveta 3 : ").append(Cadena.aInteger(regTransaccion[14],25,30)).append(" \n");
                        cadena.append("# Billetes Ent Gaveta 4 : ").append(Cadena.aInteger(regTransaccion[14],30,35)).append(" \n");
                        }
                    } catch (Exception ex ) {
                        
                        cadena.append("\n DATOS LOG " + ex.getMessage()+ "\n");
                          //ALVARO 12 MARZO
                       // cadena.append("\nNo se puede formatear la transacción " + ex.getMessage()+ "\n");
                    } finally {
                        cadena.append("Registro :").append(datos).append("\n");
                    }
                } else {
                    cadena.append(datos);
                }
            }
        }
        return cadena.toString();        
    }
    
      /**
     * Retorna el día gregoriano de un EDC,  constante DAY_OF_YEAR y adiciona el último dígito del año
     * @param fecha Calendar
     * @return Integer
     */
    public static Integer getCiclo(Calendar fecha) {
        Integer ciclo = 0;
        String strCiclo = String.valueOf(fecha.get(Calendar.DAY_OF_YEAR)  ) +  String.valueOf(fecha.get(Calendar.YEAR)).substring(3);
        try {
            ciclo = Integer.parseInt(strCiclo);
        } catch (Exception ex) {
            ciclo = -1;
        }
        return ciclo;
    }
    

//    
//    /**
//     * Retorna la fecha de el  día gregoriano de un EDC
//     * @param fecha Calendar
//     * @return Integer
//     */
    public static Calendar getFechaCiclo(String fechaGregoriana,Calendar fecha) {
       
        Calendar fechaCal =null;
        String   strFecha ="";
        Integer intGregoriano=0;
        
       
        
        try {
            
          intGregoriano=com.davivienda.utilidades.conversion.Cadena.aInteger(fechaGregoriana.substring(0,3));
          strFecha=Fecha.aCadena(fecha, FormatoFecha.YEAR);  
          strFecha=Fecha.aCadena(fecha, FormatoFecha.YEAR);
          fechaCal = Calendar.getInstance();//com.davivienda.utilidades.conversion.Cadena.aCalendar("2010/01/01");
         
          fechaCal.set(Calendar.DAY_OF_YEAR, intGregoriano);
          fechaCal.set(Calendar.YEAR,Cadena.aInteger(strFecha));
         
          if(strFecha.substring(3, 4).compareTo(fechaGregoriana.substring(3, 4))!=0)
               fechaCal.add(Calendar.YEAR,-1);
      
        } catch (Exception ex) {
            fechaCal=fecha;
        }
        return fechaCal;
    }

    /**
     * Crea a partir del código de codigoCajero y la fecha el nombre del archivo edc
     *
     * @param codigoCajero
     * @param prmFecha
     * @return
     */
    public static String obtenerNombreArchivo(Integer codigoCajero, Calendar prmFecha){
        String sufijo, cajeroStr = "";
        String diaStr = String.valueOf(prmFecha.get(Calendar.DAY_OF_YEAR));
        if (diaStr.length() == 1 ) {
            diaStr = "00" + diaStr;
        }
        if (diaStr.length() == 2 ) {
            diaStr = "0" + diaStr;
        }
        sufijo = diaStr  + String.valueOf(prmFecha.get(Calendar.YEAR)).substring(3);
        cajeroStr =  (codigoCajero < 1000) ? "0" + codigoCajero : codigoCajero.toString();
        return cajeroStr + sufijo + ".0";
    }
    
    
    public static Integer getCiclo(java.util.Date fecha ) {
        
        Calendar calTmp = Calendar.getInstance();
        calTmp.setTime(fecha);
        return getCiclo(calTmp);
    }
    
    /**
     * Retona el nombre del archivo comprimido de la fecha <code>cal</cal>
     * @param cal
     * @return
     */
    public static String getNombreArchivoCiclosComprimido(Calendar cal){
        return "EDC" + com.davivienda.utilidades.conversion.Fecha.aCadena(cal, com.davivienda.utilidades.conversion.FormatoFecha.MMDD) + ".ZIP";
    }
    
    
    
    
    
    
    
    public static void main (String[] args) {
        
//        try{
//         DatatypeFactory factory = DatatypeFactory.newInstance();
//
//        
//         XMLGregorianCalendar fecha = factory.newXMLGregorianCalendar(2010,
//                                                                     8,
//                                                                     3,
//                                                                     0, 0, 0, 0, 0);
//           System.out.println("1 -->"+ fecha.toXMLFormat());
//        }catch (Exception ex)
//        {
//         }
         

//        
//        System.out.println("1 --> \n" + Edc.aCadena("* MAC=YES RCDNUM=(1,6) RECORDS=REL CUSTOM_FORMAT=NO"));
//        System.out.println("2 --> \n" + Edc.aCadena("1000000042107012945aDAV6093aTRAN,6093,1299,0114 B,070421/A,012925,20,20,000,000005000000,5895140966227891753,589514-126070494847,000005000000,0110a497DEAF8"));
//        System.out.println("3 --> \n" + Edc.aCadena("0000120042107121959aDAV6093a002D901:2D:04:00  21/04/07 12:19:59a31CE64F9"));
//        System.out.println("4 --> \n" + Edc.aCadena("0000121042107121959aDAV6093a    SERIAL # 0234da    CASSETTE LOWa    BILLS DISPENSED:  C-00  C-05  C-00  B-00a8321EC9E"));
//        System.out.println("5 --> \n" + Edc.aCadena("0000120042107121959aDAV6093a002D901:2D:04:00  21/04/07 12:19:59a31CE64F9"));
//        System.out.println("6 --> \n" + Edc.aCadena("0000164042107144700aDAV6093a    SERIAL # 0273da        MAINTENANCE LOGONa9596606E"));
//        System.out.println("7 --> \n" + Edc.aCadena("2000165042107144706aDAV6093a d1B06AFDC"));
//        System.out.println("8 --> \n" + Edc.aCadena("2000183042107145017aDAV6093as(         BILLS REPLENISHED         aBIN/ID     QUANTITYa 1/C        02000a 2/C        02000a 3/C        02000a 4/B        02000aDD608AD2"));
//        System.out.println("9 --> \n" + Edc.aCadena("1000301042207001146aDAV6093aTOTALES CAJERO :  6093aTRA.  EVENTOS    TOTALES EN $addddddd20      101   18360000.00a21        2     350000.00a22        5    1150000.00a23        5     850000.00a24        6    1060000.00addddadA7E95F33"));
//        System.out.println("10 --> \n" + Edc.aCadena("1000302042207001147aDAV6093aTOTALES CAJERO :  6093aRET CRDS 00000aPOS ID  DISP.   DIVERT  RETRACT REMAINa1e3Ce300074e300002e300000e301926a2e3Ce300063e300002e300000e301937a3e3Ce300048e300002e300000e301952a4e3Be300018e300002e300000e301982a- - - - - - - - - - a34C4576E"));
//        System.out.println("11 --> \n" + Edc.aCadena("1000303042207001148aDAV6093aTOTALES CAJERO :  6093aTRA.  EVENTOS    TOTALES EN $addddddd20        0          0.00a21        0          0.00a22        0          0.00a23        0          0.00a24        0          0.00addddad8F13EE03"));
//        System.out.println("12 --> \n" + Edc.aCadena("2000165042107144706aDAV6093a d1B06AFDC"));
//        System.out.println("12 --> \n" + Edc.aCadena("1000189042107145057aDAV6093aTRAN,6093,    ,0273 L,070421/N,145000,01,13,000,000000000000,0001600888800000026,e?e4,000000000000,0010as(EST. OFI.    FECHA    HORA   SERIALa6093e7070421/N  145000e20273 LadPRIMER INF. TOTALES COMPUT.adTOT.PROVI.   1  39,140,000.0 aEGRE.EFE.   80  17,890,000.0 aEFEC. CAJA   0  21,250,000.0 aCAMB.CLAV    7           0.0 aAVANCE DAV   2     450,000.0 aDONACION M   4       2,000.0 a             0           0.0 adadC2A2BD15"));
//        System.out.println("13 --> \n" + Edc.aCadena("1000126052407112202aDAV1805aTRAN,1805,4599,3073 B,070524/N,112244,20,20,000,000020000000,5895140966233627670,589514-002870384969,000020000000,0110,6307210DI01ECCB00000000000000000030a8B903C0A"));
//        System.out.println("14 --> \n" + Edc.aCadena("2000177042107144952aDAV6093as(DISPENSERa  00004 TOTAL DIVERTSa  00000 DUMPSaCASSETTE 1 / Ca  00294 DISPENSEDa  00057 REMAININGa  00000 DIVERT 1a0097EDF4"));
//        System.out.println("15 --> \n" + Edc.aCadena("2000180042107144953aDAV6093as(CASSETTE 4 / Ba  00027 DISPENSEDa  01817 REMAININGa  00000 DIVERT 1a  00001 DIVERT 2a  00000 RETRACTSaBILL COUNTS LAST CLEAREDa1406ED6A"));
//        System.out.println("16 --> \n" + Edc.aCadena("2000181042107144954aDAV6093as(   21/04/07 00:10:50aBILL COUNTS BEING CLEAREDaCURRENT TIME AND DATEa   21/04/07 14:49:52a d5D468E47    "));
//        System.out.println("20 --> \n" + Edc.aCadena('1', "TRAN,6093,1299,0114 B,070421/A,012925,20,20,000,000005000000,5895140966227891753,589514-126070494847,000005000000,0110a497DEAF8"));
//       
//        
//        System.out.println("21 --> \n" + Edc.aCadena('8', "TOTALES CAJERO :  1006aRET CRDS 00000aPOS ID  DISP.   DIVERT  RETRACT REMAINa1e3Ce300000e300000e300000e301178a2e3Ce300000e300000e300000e301135a3e3Ce300000e300000e300000e301050a4e3Be300000e300000e300000e301818a- - - - - - - - - - aE20CC688"));
//                                                          // TOTALES CAJERO :  0433aRET CRDS 00000aPOS ID  DISP.   DIVERT  RETRACT REMAINa1e3Ee300615e300000e300000e301155a2e3Ce300108e300000e300000e301832a3e3Ee300565e300000e300000e301215a4e3Be300035e300000e300000e301948a- - - - - - - - - - a8DEEBCA5
//        System.out.println("22 --> \n" + Edc.aCadena('8',"TOTALES CAJERO :  0433aTRA.  EVENTOS    TOTALES EN $add20      253   50520000.00a21       24    8110000.00a22        5    1230000.00a23        4    1600000.00a24        1      50000.00addddadAE2E82B6"));
//        System.out.println("23 --> \n" + Edc.aCadena("1000042112807074508aDAV1803aTRAN,1803,4599,0787 B,071128/N,074349,20,20,000,000030000000,5895140966213193198,589514-006080216705,000030000000,0110,6078610DI01ECCB00000000000001500000aF780BF7F"));
//        System.out.println("24 --> \n" + Edc.aCadena('1',"TRAN,0505,4599,1002 B,090316/N,092556,50,50,0000,000000000000,5895140966238916037,589514-002500110339,000000000000,0110,6100110DI01ECEB00000000010000400001a617BA7C3"));
//        System.out.println("25 --> \n" + Edc.aCadena('1',"TRAN,0505,4599,1003 B,090316/N,092656,20,20,0000,000005000000,5895140966238916037,589514-002500110339,000005000000,0110,6100310DI01ECEB00000000000000000000a905DDF9E"));
//        System.out.println("26 --> \n" + Edc.aCadena('1',"TRAN,0505,4599,1004 B,090316/N,093216,20,20,0000,000020000000,5895140966244048486,589514-002600097576,000020000000,0110,6100310DI01ECEB00001000000000000000a30CAAE9C"));
//        Calendar x = com.davivienda.utilidades.conversion.Cadena.aCalendar("2008/01/30");
//        Integer ciclo = getCiclo(x);
//        System.out.println("Ciclo " + ciclo);
//        System.out.println("Archivo " + getNombreArchivoCiclosComprimido(x));
        
           String fechaActual="";
        
         Date fecha=null;
        
        fecha=com.davivienda.utilidades.conversion.Fecha.getDateAyer();
        fechaActual=com.davivienda.utilidades.conversion.Fecha.aCadena(fecha, com.davivienda.utilidades.conversion.FormatoFecha.AAAAMMDD);
        System.out.println(fechaActual.toString());
        BigDecimal valorEfectivo;
        BigDecimal valorEfectivo2;
        valorEfectivo=new BigDecimal(1999900000);
        
        valorEfectivo2=valorEfectivo.multiply(new BigDecimal(100));
        //System.out.println(valorEfectivo.toString());
      //  System.out.println(valorEfectivo2.toString());

      
    }
    
    
    
    
    
    
    
}

