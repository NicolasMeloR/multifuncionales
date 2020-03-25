package com.davivienda.utilidades.archivoplano;

import com.davivienda.utilidades.archivoplano.ArchivoPlano;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.davivienda.utilidades.conversion.Cadena ;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

/**
 * ProcesadorArchivoPlano.java
 *
 * Fecha       :  29/06/2007, 04:36:23 PM
 * Descripción :  Lee y procesa los registros del archivo asignado a este objeto
 *
 * @author     : jjvargas
 * @version    : $Id$
 */
public class ProcesadorArchivoPlano {
    
    private ArchivoPlano archivo ;
    
    private int registrosData  =  0;
    private int registrosDataError  =  0;
    protected List<String> erroresDelProceso = new LinkedList<String>();
    
    
    /**
     * Crea una nueva instancia de <code>ProcesadorArchivoPlano</code>.
     * @param archivo
     */
    public ProcesadorArchivoPlano(ArchivoPlano archivo) {
        this.archivo = archivo;
    }
    
    
    
        /**
     * Lee el archivo y retorna los registros leídos en una colección de tipo LinkedList<String[]>.hasta la cantidad de registros
     * El vector String[] de cada nodo del LinkedList esta conformado por [nombreCampo:valorCampo]...
     * @return
     * @throws java.io.FileNotFoundException
     * @throws java.lang.IllegalArgumentException
     */
    public Collection<String[]> obtenerRegistrosDataXFilas(int regInicial,int cantidadRegistros) throws FileNotFoundException, IllegalArgumentException {
        List<String[]> regs = new LinkedList<String[]>();
        int linea = 0;
        try {
            BufferedReader reader = archivo.getBufferedReader(archivo.registroData.longitudRegistro, archivo.registroData.charSet);
            // salto el registro de control si existe
            if ( archivo.registroControl != null ) {
                String reg = reader.readLine();
                java.util.logging.Logger.getLogger("globalApp").finest("Campo control : \n"+reg);
                linea++;
            }
            for (String cadena = reader.readLine(); cadena != null && linea<(regInicial+cantidadRegistros) ; cadena = reader.readLine()) {
                linea++;
                try {
                    String[] datos = obtenerCamposData(cadena);
                    if (datos != null && linea>regInicial) {
                        regs.add(datos);
                        registrosData++;
                    }
                } catch (Exception ex){
                    registrosDataError++;
                    erroresDelProceso.add("Error al parsear el registro : "  + linea + " : " + cadena + " Causa : " + ex.getMessage() + ex.toString());
                }
            }
        } catch (IOException ex)  {
            erroresDelProceso.add("Error al leer el archivo causa : " + ex.getMessage());
        }
        return regs;
    }
    
    /**
     * Lee el archivo y retorna los registros leídos en una colección de tipo LinkedList<String[]>.
     * El vector String[] de cada nodo del LinkedList esta conformado por [nombreCampo:valorCampo]...
     * @return
     * @throws java.io.FileNotFoundException
     * @throws java.lang.IllegalArgumentException
     */
    public Collection<String[]> obtenerRegistrosData() throws FileNotFoundException, IllegalArgumentException {
        List<String[]> regs = new LinkedList<String[]>();
        int linea = 0;
        try {
            BufferedReader reader = archivo.getBufferedReader(archivo.registroData.longitudRegistro, archivo.registroData.charSet);
            // salto el registro de control si existe
            if ( archivo.registroControl != null ) {
                String reg = reader.readLine();
                java.util.logging.Logger.getLogger("globalApp").finest("Campo control : \n"+reg);
                linea++;
            }
            for (String cadena = reader.readLine(); cadena != null; cadena = reader.readLine()) {
                linea++;
                try {
                    String[] datos = obtenerCamposData(cadena);
                    if (datos != null) {
                        regs.add(datos);
                        registrosData++;
                    }
                } catch (Exception ex){
                    registrosDataError++;
                    erroresDelProceso.add("Error al parsear el registro : "  + linea + " : " + cadena + " Causa : " + ex.getMessage() + ex.toString());
                }
            }
        } catch (IOException ex)  {
            erroresDelProceso.add("Error al leer el archivo causa : " + ex.getMessage());
        }
        return regs;
    }
    
    
    public String[] obtenerCamposData(String registro) {
        String[] camposStr = null;
        if (archivo.registroData.formatoCampo.equals(FormatoCampo.DELIMITADO) ) {
            camposStr = registro.split(archivo.registroData.caracterSeparadorCampos);
        }
        if (archivo.registroData.formatoCampo.equals(FormatoCampo.LONGITUD_FIJA) ) {
            camposStr = camposAVector(registro, archivo.registroData);
        }
        return camposStr;
    }
    
    private String[] camposAVector(String registro, RegistroArchivoPlano registroArchivo) {
        CampoArchivoPlano[] campos = registroArchivo.campos;
        String[] camposStr = new String[campos.length];
        String valorDato ;
        boolean esRegistroValido = true;
        for (int i = 0; i < campos.length; i++) {
            CampoArchivoPlano campo = campos[i];
            int posFinal  =  (campo.longitud > 0) ? campo.posInicial + campo.longitud : campo.posFinal ;
            posFinal = (campo.longitud == -1) ? campo.longitud : posFinal ;
            if (posFinal > campo.posInicial || posFinal == -1) {
                valorDato = Cadena.subCadena(registro, campo.posInicial, posFinal);
                if (!cumpleFiltro(campo, valorDato))  {
                    esRegistroValido = false;
                    break;
                }
                if(registroArchivo.esIncluirNombreCampo()) {
                    camposStr[i] = campo.nombreCampo + ":" + valorDato ;
                } else {
                    camposStr[i] = valorDato;
                }
            }
        }
        if (!esRegistroValido) {
            camposStr = null;
        }
        return camposStr;
    }
    
    private boolean cumpleFiltro(CampoArchivoPlano campoDato, String valorDato) {
        if (!campoDato.esCampoFiltro) return true;
        boolean esCampoValido = false;
        for (int k = 0; k < campoDato.valoresFiltro.length; k++) {
            String valorFiltro = campoDato.valoresFiltro[k].trim();
            if (valorDato.trim().equals(valorFiltro) ){
                esCampoValido = true;
                break;
            }
        }
        return esCampoValido;
    }
    
    
    
//    
//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        ArchivoPlano archivo = new ArchivoPlano("cfamo001.11-07-11","d:");
//        RegistroArchivoPlano estructuraData = new RegistroArchivoPlano();
//        CampoArchivoPlano[] campos = new CampoArchivoPlano[38];
//        archivo.obtenerArchivo();
//        if(archivo.sePuedeLeer()) {
//            try {
//                estructuraData.setLongitudRegistro(256);
//                estructuraData.setFormatoCampo(com.davivienda.utilidades.archivoplano.FormatoCampo.LONGITUD_FIJA);
//             
//                campos[0] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("cajero", 0, 0, 4);
//                campos[1] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("fecha", 1, 4, 8);
//                campos[2] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("hora", 2, 12, 6);
//                campos[3] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("occa", 3, 18, 4);
//                campos[4] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("zona", 4, 22, 1);
//                campos[5] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("claseCajero", 5, 23, 2);
//                campos[6] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("error", 6, 25, 4);
//                campos[7] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("oficina", 7, 29, 4);
//                campos[8] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("denom_1", 8, 56, 4);
//                campos[9] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("total_1", 9, 60, 4);
//                campos[10] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("dispensado_1", 10, 64, 4);
//                campos[11] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("caja_1", 11, 68, 4);
//                campos[12] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("purga_1", 12, 72, 4);
//                campos[13] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("retract_1", 13, 76, 4);
//                campos[14] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("denom_2", 14, 80, 4);
//                campos[15] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("total_2", 15, 84, 4);
//                campos[16] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("dispensado_2", 16, 88, 4);
//                campos[17] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("caja_2", 17, 92, 4);
//                campos[18] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("purga_2", 18, 96, 4);
//                campos[19] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("retract_2", 19, 100, 4);
//                campos[20] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("denom_3", 20, 104, 4);
//                campos[21] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("total_3", 21, 108, 4);
//                campos[22] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("dispensado_3", 22, 112, 4);
//                campos[23] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("caja_3", 23, 116, 4);
//                campos[24] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("purga_3", 24, 120, 4);
//                campos[25] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("retract_3", 25, 124, 4);
//                campos[26] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("denom_4", 26, 128, 4);
//                campos[27] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("total_4", 27, 132, 4);
//                campos[28] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("dispensado_4", 28, 136, 4);
//                campos[29] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("caja_4", 29, 140, 4);
//                campos[30] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("purga_4", 30, 144, 4);
//                campos[31] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("retract_4", 31, 148, 4);
//                campos[32] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("canRetiroDav", 32, 152, 4);
//                campos[33] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("valRetiroDav", 33, 156, 12);
//                campos[34] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("canAvanceDav", 34, 168, 4);
//                campos[35] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("valAvanceDav", 35, 172, 12);
//                campos[36] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("canRetiroRed", 36, 184, 4);
//                campos[37] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("valRetiroRed", 37, 188, 12);
//                estructuraData.setCampos(campos);
//                archivo.setRegistroData(estructuraData);
//                com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano procesador = new com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano(archivo);
//                Collection<String[]> regsStr = null;
//                //regsStr=procesador.obtenerRegistrosData();
//                 regsStr=procesador.obtenerRegistrosDataLotes(2,4);
//               for (String[] strings : regsStr) {
//		System.out.println(strings[0]);
//               }
//
//              
//                archivo.cerrarArchivo();
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger("global").log(Level.SEVERE, null, ex);
//            } catch (IllegalArgumentException ex) {
//                Logger.getLogger("global").log(Level.SEVERE, null, ex);
//            }
//            
//        }
//        
////         File myFile = new File("d:/cfamo001.11-07-11");
////        FileReader fileReader = new FileReader(myFile);
////        LineNumberReader reader = new LineNumberReader(fileReader);
////
////        // Read one line from the FileReader.
////        String lineRead = "";
////        lineRead = reader.readLine();
////        System.out.println(lineRead);
////
////        // Mark the position in the file.
////       reader.mark((int) myFile.length());
////      
////
////        // Read the rest of the file.
////        while ((lineRead = reader.readLine()) != null) {
////         
////            System.out.println(lineRead);
////        }
////
////        // Now reset the reader and re-read from the
////        // FileReader from the marked position on.
////        reader.reset();
////            System.out.println("inicia");
////        while ((lineRead = reader.readLine()) != null) {
////            System.out.println(lineRead);
////        }
////
////        // Close the LineNumberReader and FileReader.
////        fileReader.close();
////        reader.close();
//
//    }
    
    
    
      public static void main(String[] args) throws FileNotFoundException, IOException {
       // ArchivoPlano archivo = new ArchivoPlano("mvtoatm01.11-10-28","d:");
          ArchivoPlano archivo = new ArchivoPlano("mvtoatm01.11-10-20","d:");
        RegistroArchivoPlano estructuraData = new RegistroArchivoPlano();
        CampoArchivoPlano[] campos = new CampoArchivoPlano[13];
        archivo.obtenerArchivo();
        if(archivo.sePuedeLeer()) {
            try {
                estructuraData.setLongitudRegistro(100);
                estructuraData.setFormatoCampo(com.davivienda.utilidades.archivoplano.FormatoCampo.LONGITUD_FIJA);
                
                
     
             
                campos[0] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Codigo_Cajero", 0, 0, 6);
                campos[1] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Fecha_sistema", 1, 6, 8);
                campos[2] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Hora_sistema", 2, 14, 6);
                campos[3] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Talon", 3, 20, 6);
                campos[4] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Numero_cuenta", 4, 26, 12);
                campos[5] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Datos_tarjeta", 5, 38, 19);
                campos[6] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Fecha", 6, 57, 8);
                campos[7] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Tipo_transaccion", 7, 65, 4);
                campos[8] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Occa", 8, 69, 4);
                campos[9] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Tipo_tarjeta", 9, 73, 1);
                campos[10] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Indices", 10, 74, 2);
                campos[11] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Valor", 11, 84, 7);
                campos[12] = new com.davivienda.utilidades.archivoplano.CampoArchivoPlano("Filler", 12, 91, 7);
                estructuraData.setCampos(campos);
                archivo.setRegistroData(estructuraData);
                com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano procesador = new com.davivienda.utilidades.archivoplano.ProcesadorArchivoPlano(archivo);
              
//                int tamArchivo=0;
//                tamArchivo=archivo.cuentaRegistros();
                 Collection<String[]> regsStr = null;
                  regsStr=procesador.obtenerRegistrosDataXFilas(0,10);
                  
                int i=0;
                for(i=0;regsStr!=null||i==0;i=i+10)
                {
                    
                    regsStr=procesador.obtenerRegistrosDataXFilas(i,10);



                   for (String[] strings : regsStr) {
                    System.out.println(strings[0]+strings[4] +"i: "+ i);

                   }
                }

              
                archivo.cerrarArchivo();
            } catch (FileNotFoundException ex) {
                Logger.getLogger("global").log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger("global").log(Level.SEVERE, null, ex);
            }
            
        }
        
//         File myFile = new File("d:/cfamo001.11-07-11");
//        FileReader fileReader = new FileReader(myFile);
//        LineNumberReader reader = new LineNumberReader(fileReader);
//
//        // Read one line from the FileReader.
//        String lineRead = "";
//        lineRead = reader.readLine();
//        System.out.println(lineRead);
//
//        // Mark the position in the file.
//       reader.mark((int) myFile.length());
//      
//
//        // Read the rest of the file.
//        while ((lineRead = reader.readLine()) != null) {
//         
//            System.out.println(lineRead);
//        }
//
//        // Now reset the reader and re-read from the
//        // FileReader from the marked position on.
//        reader.reset();
//            System.out.println("inicia");
//        while ((lineRead = reader.readLine()) != null) {
//            System.out.println(lineRead);
//        }
//
//        // Close the LineNumberReader and FileReader.
//        fileReader.close();
//        reader.close();

    }
    
    
    public ArchivoPlano getArchivo() {
        return archivo;
    }
    
    
    public int getRegistrosData() {
        return registrosData;
    }
    
    
    public int getRegistrosDataError() {
        return registrosDataError;
    }
    
    
    public List<String> getErroresDelProceso() {
        return erroresDelProceso;
    }
    
    
}

