/*
 * Banco Davivienda 2008
 * Proyecto Utilidades
 * Versión  1.0
 */
package com.davivienda.utilidades.archivoxls;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ProcesadorArchivoXLS - 11/08/2008
 * Descripción : Clase para la ejecución de procesos sobre archivos XLS
 * Versión : 1.0 
 *
 * @author jjvargas
 * Davivienda 2008 
 */
public class ProcesadorArchivoXLS {

    public static void crearArchivoXLS(String nombreArchivo, String destinoArchivo, String nombreHoja, String[] titulosHoja, String[] titulosColumna, Collection<Registro> lineas) throws IllegalArgumentException, IOException {
        ArchivoXLS archivo = new ArchivoXLS(nombreArchivo, destinoArchivo, nombreHoja);
        ProcesadorArchivoXLS.crearArchivoXLS(archivo, titulosHoja, titulosColumna, lineas);
    }

    public static void crearArchivoXLS(ArchivoXLS archivo, String[] titulosHoja, String[] titulosColumna, Collection<Registro> lineas) throws IllegalArgumentException, IOException {
        if (archivo == null) {
            throw new IllegalArgumentException("Se deben asignar los datos de creación del archivo");
        }
        try {
            archivo.obtenerArchivo();
            archivo.crearTitulosHoja(titulosHoja);
            archivo.crearTitulosColumna(titulosColumna,false);
            archivo.crearLineasNormales(lineas,false);
            archivo.grabarArchivo();
        } finally {
            archivo.cerrarArchivo();
        }
    }
    
       public static void crearArchivoXLSReintegros(String nombreArchivo, String destinoArchivo, String nombreHoja, String[] titulosHoja, String[] titulosColumna, Collection<Registro> lineas) throws IllegalArgumentException, IOException {
        ArchivoXLS archivo = new ArchivoXLS(nombreArchivo, destinoArchivo, nombreHoja);
       // ProcesadorArchivoXLS.crearArchivoXLSReintegros(archivo, titulosHoja, titulosColumna, lineas);
         ProcesadorArchivoXLS.crearArchivoXLSReintegros(archivo, titulosHoja, titulosColumna, lineas);
    }

    public static void crearArchivoXLSReintegros(ArchivoXLS archivo, String[] titulosHoja, String[] titulosColumna, Collection<Registro> lineas) throws IllegalArgumentException, IOException {
        if (archivo == null) {
            throw new IllegalArgumentException("Se deben asignar los datos de creación del archivo");
        }
        try {
            archivo.obtenerArchivo();
            archivo.crearTitulosHojaReintegros(titulosHoja);
            archivo.crearTitulosColumna(titulosColumna,true);
            archivo.crearLineasNormales(lineas,true);
           // archivo.crearSubtituloinferior();
            archivo.grabarArchivo();
        } finally {
            archivo.cerrarArchivo();
        }
    }
    
    /**
     * 
     * Metodo creado CAMILO CHAPARRO 13/06/2011
     *      
     */
    
    public static ArchivoXLSDali40 crearArchivoXLSDali40(String nombreArchivo, String destinoArchivo, String nombreHoja, String[] titulosHoja, String[] titulosColumna, String[] titulosFilas, Collection<Registro> lineas) throws IllegalArgumentException, IOException {
        
//        ArchivoXLSDali40 archivo = new ArchivoXLSDali40(nombreArchivo, destinoArchivo, nombreHoja);
//        ProcesadorArchivoXLS.crearArchivoXLSDali40(archivo, titulosHoja, titulosColumna, titulosFilas,  lineas);
//        return archivo;
        
        ArchivoXLSDali40 archivo = new ArchivoXLSDali40(null, null, nombreHoja);
        archivo.setNombreArchivo(nombreHoja.trim()+".xls");
        ProcesadorArchivoXLS.crearArchivoXLSDali40(archivo, titulosHoja, titulosColumna, titulosFilas,  lineas);
        return archivo;
        
    }
    
        public static void crearArchivoXLSDali40kkk(String nombreArchivo, String destinoArchivo, String nombreHoja, String[] titulosHoja, String[] titulosColumna, String[] titulosFilas, Collection<Registro> lineas) throws IllegalArgumentException, IOException {
        
        ArchivoXLSDali40 archivo = new ArchivoXLSDali40(nombreArchivo, destinoArchivo, nombreHoja);
        ProcesadorArchivoXLS.crearArchivoXLSDali40(archivo, titulosHoja, titulosColumna, titulosFilas,  lineas);
        
      
        
        
    }
    
    /**
     * 
     * Metodo creado CAMILO CHAPARRO 
     *      
     */
    
    public static void crearArchivoXLSDali40(ArchivoXLSDali40 archivo, String[] titulosHoja, String[] titulosColumna, String[] titulosFilas, Collection<Registro> lineas) throws IllegalArgumentException, IOException {
    
        if (archivo == null) {
            throw new IllegalArgumentException("Se deben asignar los datos de creación del archivo");
        }
        try {
            archivo.obtenerArchivo();
            
//            for(int i = 1; i <= (lineas.size());i++){
//                
//                if(i%2 == 0){
//                    archivo.crearTitulosColumnaDali40(titulosColumna,true,1);
//                    archivo.crearTitulosFilasDali40(titulosFilas, 1);
//                }else{
//                    archivo.crearTitulosColumnaDali40(titulosColumna,true,0);
//                    archivo.crearTitulosFilasDali40(titulosFilas,0);                    
//                }
//            }            
            
            for (int i = 1; i <= (lineas.size()); i++) {
                
                    System.out.println("Entra 0: " + i);
                    archivo.crearTitulosColumnaDali40(titulosColumna, true, 0);
                    archivo.crearTitulosFilasDali40(titulosFilas, 0);
                
            }
           
            archivo.crearLineasNormalesDali40(lineas,true);           
            archivo.grabarArchivo();
        } finally {
            archivo.cerrarArchivo();
        }
        
    
    }
    
    
     public static void crearArchivoXLSBanAgrario(ArchivoXLS archivo, String[] titulosHoja, String[] titulosColumna, Collection<Registro> lineas) throws IllegalArgumentException, IOException {
        if (archivo == null) {
            throw new IllegalArgumentException("Se deben asignar los datos de creación del archivo");
        }
        try {
            archivo.obtenerArchivo();
            archivo.crearTitulosHojaBanAgrario(titulosHoja);
            archivo.crearTitulosColumna(titulosColumna,true);
            archivo.crearLineasNormales(lineas,true);
             //archivo.crearSubtituloinferior();
            archivo.grabarArchivo();
        } finally {
            archivo.cerrarArchivo();
        }
    }

    /**
     * Crea un objeto de libro de excel con los datos de los parámetros y lo retorna 
     * @param nombreHoja
     * @param titulosHoja
     * @param titulosColumna
     * @param lineas
     * @return
     */
    public static ArchivoXLS crearLibro(String nombreHoja, String[] titulosHoja, String[] titulosColumna, Collection<Registro> lineas) throws FileNotFoundException, IOException {
        ArchivoXLS archivo = new ArchivoXLS(null, null, nombreHoja);
        archivo.setNombreArchivo(nombreHoja.trim()+".xls");
        archivo.crearTitulosHoja(titulosHoja);
        archivo.crearTitulosColumna(titulosColumna,false);
        archivo.crearLineasNormales(lineas,false);
        return archivo;
    }
        /**
     * Crea un objeto de libro de excel especial para reintegros con los datos de los parámetros y lo retorna 
     * @param nombreHoja
     * @param titulosHoja
     * @param titulosColumna
     * @param lineas
     * @return
     */
    public static ArchivoXLS crearLibroReintegrosDavivienda(String dirLogo,String nombreHoja, String[] titulosHoja, String[] titulosColumna, Collection<Registro> lineas) throws FileNotFoundException, IOException {
        ArchivoXLS archivo = new ArchivoXLS(null, null, nombreHoja,true,dirLogo);
        archivo.setNombreArchivo(nombreHoja.trim()+".xls");
        archivo.crearTitulosHojaReintegros(titulosHoja);
        archivo.crearTitulosColumna(titulosColumna,true);
        archivo.crearLineasNormales(lineas,true);
        return archivo;
   }
    
       /**
     * Crea un objeto de libro de excel especial para reintegros con los datos de los parámetros y lo retorna 
     * @param nombreHoja
     * @param titulosHoja
     * @param titulosColumna
     * @param lineas
     * @return
     */
    public static ArchivoXLS crearLibroBanAgrario(String nombreHoja, String[] titulosHoja,String subtituloHoja, String[] titulosColumna, Collection<Registro> lineas,String[] titulosInfTotales,String[] subtituloInferiorHoja,String[] subFirma) throws FileNotFoundException, IOException {
        ArchivoXLS archivo = new ArchivoXLS(null, null, nombreHoja,false,"");
        archivo.setNombreArchivo(nombreHoja.trim()+".xls");
        archivo.crearTitulosHojaBanAgrario(titulosHoja);
        archivo.crearSubtituloHojaBanAgrario(subtituloHoja);
        archivo.crearTitulosColumna(titulosColumna,true);
        archivo.crearLineasNormales(lineas,true);
        archivo.crearSubtituloinferiorHoja(titulosInfTotales,subtituloInferiorHoja, subFirma);
        return archivo;
    }

    public static void main(String[] args) throws IllegalArgumentException {
        String[] titulosHoja = new String[2];
//        String[] titulosColumna = new String[3];
//        Collection<Registro> lineas = new ArrayList<Registro>();
//        titulosHoja[0] = "ELM - Estadísticas, Logs y Monitoreo";
//        titulosHoja[1] = "Estadística - Nombre estadística";
//        titulosColumna[0] = "Canal";
//        titulosColumna[1] = "Fecha";
//        titulosColumna[2] = "Operación";
//     
//        for (short i = 0; i < 5; i++) {
//            Registro reg = new Registro();
//            Celda celda0 = new Celda((short) 0, 5, TipoDatoCelda.NUMERICO);
//            reg.addCelda(celda0);
//            Celda celda1 = new Celda((short) 1, com.davivienda.utilidades.conversion.Fecha.getCalendarHoy(), TipoDatoCelda.FECHA);
//            reg.addCelda(celda1);
//            Celda celda2 = new Celda((short) 2, 100000, TipoDatoCelda.MONEDA);
//            reg.addCelda(celda2);
//            lineas.add(reg);
//        }
//        try {
//          // ProcesadorArchivoXLS.crearArchivoXLS("Estadistica1.xls", "d:/temp", "Estadística", titulosHoja, titulosColumna, lineas);
//           
//            ProcesadorArchivoXLS.crearArchivoXLSReintegros("Estadistica1.xls", "d:/temp", "Estadística", titulosHoja, titulosColumna, lineas);
//        } catch (IllegalArgumentException ex) {
//            Logger.getLogger(ProcesadorArchivoXLS.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ProcesadorArchivoXLS.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        String[] tituloDali40 = {"TOTALES",""};
        String[] tituloColumnas = {"TOTALES", "ATM", "STRATUS", "DIFERENCIA", "INIJORN", "HAYJORN", "RECIB.", "RETRACT", "INIJORN",
                                    "HAYJORN", "RECIB.", "RETRACT", "EFECTIVO", "CHEQUES"};
        //HLGomez: Se agregó "TOTALES" a los títulos de filas.
        String[] tituloFilas = {"CODIGO CAJERO", "DEPOSITO EFE", "DEPOSITO CHQ", "PAGOS TC EFE", "PAGOS TC CHQ", "PAGOS FM EFE","PAGOS FM CHQ",
                                "RECAUDOS EFE", "RECAUDOS CHQ", "DETALLE EFECTIVO", "DENOMINACION 1000", "DENOMINACION 2000", "DENOMINACION 5000",
                                "DENOMINACION 10000", "DENOMINACION 20000", "DENOMINACION 50000", "TOTALES", "DETALLE CHEQUES", "CANTIDAD", "VALOR",
                                "RESUMEN", "FALTANTES", "SOBRANTES"};
        Collection<Registro> lineasDali40 = new ArrayList<Registro>();
         // int i=0;
          Long pruebap=new Long(0);
          Integer ppp=5203;
        
        for(int i= 0; i<2;i++){
            Registro regDali40 = new Registro();
            
             if(i==1)
             {
             pruebap=new Long(10);
             }
            
            if(i==1)
             {
             ppp=9981;
             }
            
            if(i==1)
             {
             pruebap=new Long(10);
             }
            Celda celdao = new Celda((short) 1, ppp, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celdao);
             
            Celda celda1 = new Celda((short) 2, com.davivienda.utilidades.conversion.Numero.aMoneda(pruebap), TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda1);
           
            Celda celda2 = new Celda((short) 3, 2500, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda2);
            Celda celda3 = new Celda((short) 4, 0000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda3);
            Celda celda4 = new Celda((short) 2, 2500, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda4);
            Celda celda5 = new Celda((short) 3, 2300, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda5);
            Celda celda6 = new Celda((short) 4, 200, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda6);
            Celda celda7 = new Celda((short) 2, 2000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda7);
            Celda celda8 = new Celda((short) 3, 2000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda8);
            Celda celda9 = new Celda((short) 4, 0000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda9);
            Celda celda10 = new Celda((short) 2, 4000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda10);
            Celda celda11 = new Celda((short) 3, 3800, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda11);
            Celda celda12 = new Celda((short) 4, 200, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda12);
            Celda celda13 = new Celda((short) 2, 3000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda13);
            Celda celda14 = new Celda((short) 3, 2000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda14);
            Celda celda15 = new Celda((short) 4, 1000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda15);
            Celda celda16 = new Celda((short) 2, 5000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda16);
            Celda celda17 = new Celda((short) 3, 4000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda17);
            Celda celda18 = new Celda((short) 4, 1000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda18);
            Celda celda19 = new Celda((short) 2, 10000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda19);
            Celda celda20 = new Celda((short) 3, 8000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda20);
            Celda celda21 = new Celda((short) 4, 2000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda21);
            Celda celda22 = new Celda((short) 2, 45000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda22);
            Celda celda23 = new Celda((short) 3, 33000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda23);
            Celda celda24 = new Celda((short) 4, 12000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda24);
            Celda celda25 = new Celda((short) 1, ""+200+"/"+20000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda25);
            Celda celda26 = new Celda((short) 2, ""+200+"/"+20000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda26);
            Celda celda27 = new Celda((short) 3, ""+200+"/"+20000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda27);
            Celda celda28 = new Celda((short) 4, ""+200+"/"+20000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda28);
            Celda celda29 = new Celda((short) 1, ""+200+"/"+40000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda29);
            Celda celda30 = new Celda((short) 2, ""+200+"/"+40000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda30);
            Celda celda31 = new Celda((short) 3, ""+200+"/"+40000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda31);
            Celda celda32 = new Celda((short) 4, ""+200+"/"+40000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda32);
            Celda celda33 = new Celda((short) 1, ""+200+"/"+100000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda33);
            Celda celda34 = new Celda((short) 2, ""+200+"/"+100000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda34);
            Celda celda35 = new Celda((short) 3, ""+200+"/"+100000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda35);
            Celda celda36 = new Celda((short) 4, ""+200+"/"+100000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda36);
            Celda celda37 = new Celda((short) 1, ""+200+"/"+2000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda37);
            Celda celda38 = new Celda((short) 2, ""+200+"/"+2000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda38);
            Celda celda39 = new Celda((short) 3, ""+200+"/"+2000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda39);
            Celda celda40 = new Celda((short) 4, ""+200+"/"+2000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda40);
            Celda celda41 = new Celda((short) 1, ""+200+"/"+4000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda41);
            Celda celda42 = new Celda((short) 2, ""+200+"/"+4000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda42);
            Celda celda43 = new Celda((short) 3, ""+200+"/"+4000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda43);
            Celda celda44 = new Celda((short) 4, ""+200+"/"+4000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda44);
            Celda celda45 = new Celda((short) 1, ""+200+"/"+10000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda45);
            Celda celda46 = new Celda((short) 2, ""+200+"/"+10000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda46);
            Celda celda47 = new Celda((short) 3, ""+200+"/"+10000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda47);
            Celda celda48 = new Celda((short) 4, ""+200+"/"+10000000, TipoDatoCelda.NORMAL);
            regDali40.addCelda(celda48);
            //totales
            Celda celda49 = new Celda((short) 1, ""+200+"/"+10000004, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda49);
            Celda celda50 = new Celda((short) 2, ""+200+"/"+10000003, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda50);
            Celda celda51 = new Celda((short) 3, ""+200+"/"+10000002, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda51);
            Celda celda52 = new Celda((short) 4, ""+200+"/"+10000001, TipoDatoCelda.NUMERICO);
             
            regDali40.addCelda(celda52);            
            
            //totales
            Celda celda53 = new Celda((short) 1, 100, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda53);
            Celda celda54 = new Celda((short) 2, 200, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda54);
            Celda celda55 = new Celda((short) 3, 300, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda55);
            Celda celda56 = new Celda((short) 4, 400, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda56);
            Celda celda57 = new Celda((short) 1, 1000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda57);
            Celda celda58 = new Celda((short) 2, 2000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda58);
            Celda celda59 = new Celda((short) 3, 3000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda59);
            Celda celda60 = new Celda((short) 4, 4000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda60);
            Celda celda61 = new Celda((short) 1, 100000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda61);
            Celda celda62 = new Celda((short) 2, 200000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda62);
            Celda celda63 = new Celda((short) 1, 300000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda63);
            Celda celda64 = new Celda((short) 2, 400000, TipoDatoCelda.NUMERICO);
            regDali40.addCelda(celda64);
            
            lineasDali40.add(regDali40);
            //lineasDali40.add(regDali40);
            //lineasDali40.add(regDali40);
            //lineasDali40.add(regDali40);
            //lineasDali40.add(regDali40);
             
         
        }
        try {
            ProcesadorArchivoXLS.crearArchivoXLSDali40kkk("EstadisticaDali40.xls", "d:/temp", "Estadística Dali 40", tituloDali40, tituloColumnas, tituloFilas, lineasDali40);
        } catch (IOException ex) 
        {
            Logger.getLogger(ProcesadorArchivoXLS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
