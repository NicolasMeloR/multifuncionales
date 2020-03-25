package com.davivienda.utilidades.archivoxls;

import com.davivienda.utilidades.archivo.Archivo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author p-cchapa 13/06/2011
 */
public class ArchivoXLSDali40 extends Archivo {

    protected HSSFWorkbook libro;
    protected HSSFSheet hoja;
    protected String nombreHoja;
    protected Integer lineasTitulos = 0;
    protected Integer lineaTitulosFilas = 0;
    protected Integer lineaDatos = 0;
    protected Integer saltoColumna = 10;
    //protected Integer saltoColumna = 2;

    //protected Integer saltoColumna = 5;
    protected Integer saltoFila = 15;

    protected Integer saltoFila1 = this.saltoFila - 2;

    private HSSFRow lineaTituloColumna;
    private HSSFRow lineaTituloFila;
    protected HSSFCellStyle estiloTituloGrandeHoja;
    protected HSSFCellStyle estiloTituloHoja;
    protected HSSFCellStyle estiloTituloColumna;
    protected HSSFCellStyle estiloLineaNormal;
    protected HSSFCellStyle estiloCeldaDatoFecha;
    protected HSSFCellStyle estiloCeldaDatoNumerico;
    protected HSSFCellStyle estiloCeldaDatoMoneda;
    protected HSSFCellStyle estiloTituloHojaNormal;
    protected HSSFCellStyle estiloTituloHojaNormalCentrado;
    protected HSSFCellStyle estiloSubTituloNormal;
    protected HSSFCellStyle estiloSubTituloFirma;
    protected HSSFCellStyle estiloTituloSeccion;
    protected Collection<HSSFRow> lineas;
    //nuevo
    protected HSSFCellStyle estiloSubtituloTituloHoja;
    protected boolean encuentraGrafica;
    HSSFCellStyle estiloDatosSeccion;
    HSSFCellStyle estiloTituloFila;
    private HSSFCellStyle estiloDatoVacio;

    /**
     * Crea una nueva instancia de <code>ArchivoXLS</code>.
     */
    public ArchivoXLSDali40() throws FileNotFoundException, IOException {
        this(null, null, "Hoja1");
    }

    /**
     * Crea un objeto ArchivoXLS con el nombre del archivo asociado
     *
     * @param nombreArchivo
     */
    public ArchivoXLSDali40(String nombreArchivo) throws FileNotFoundException, IOException {
        this(null, nombreArchivo, "Hoja1");
    }

    /**
     * Crea un objeto ArchivoXLS con el nombre y directorio del archivo
     * asociados
     *
     * @param directorio
     * @param nombreArchivo
     * @param nombreHoja
     */
    public ArchivoXLSDali40(String directorio, String nombreArchivo, String nombreHoja) throws FileNotFoundException, IOException {
        super(nombreArchivo, directorio);
        this.nombreHoja = nombreHoja;
        this.libro = new HSSFWorkbook();
        this.hoja = libro.createSheet(this.nombreHoja);

        this.crearEstilos();

    }

    public ArchivoXLSDali40(String directorio, String nombreArchivo, String nombreHoja, boolean blngraph, String dirLogo) throws FileNotFoundException, IOException {
        super(nombreArchivo, directorio);
        this.nombreHoja = nombreHoja;
        this.libro = new HSSFWorkbook();
        this.hoja = libro.createSheet(this.nombreHoja);
        if (blngraph) {
            creargrafico(dirLogo);
        }
        this.crearEstilos();
    }

    public void creargrafico(String dirLogo) throws IOException {
        InputStream is = null;
        try {
            encuentraGrafica = true;
            is = new FileInputStream(dirLogo);
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = libro.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG);
            HSSFPatriarch drawing = hoja.createDrawingPatriarch();
            HSSFClientAnchor anchor;
            anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 0, 0, (short) 0, 0);
            HSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize();
        } catch (Exception ex) {
            encuentraGrafica = false;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @Override
    public void obtenerArchivo() throws IllegalArgumentException {
        super.obtenerArchivo();
        try {
            super.getFileOutputStream();
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public HSSFWorkbook getLibro() {
        return libro;
    }

    public void crearTitulosHoja(String titulosHoja, Integer lineaTitulo) {
        this.lineasTitulos = lineaTitulo;

        LineaXLSDali40 linea = new LineaXLSDali40(this, this.lineasTitulos, EstiloLineaXLS.TITULO_HOJA);
        Celda celda = new Celda((short) 0, (Object) titulosHoja, TipoDatoCelda.TITULO_HOJA);
        linea.asignarCelda(celda);
        this.lineasTitulos++;

    }

    public void crearTitulosHojaDali40(String[] titulosHoja) {
        this.lineasTitulos = 0;

        LineaXLSDali40 linea1 = new LineaXLSDali40(this, this.lineasTitulos, EstiloLineaXLS.TITULO_HOJA_NORMAL);
        Celda celda1 = new Celda((short) 0, (Object) titulosHoja[1], TipoDatoCelda.TITULO_COLUMNA);
        linea1.asignarCelda(celda1);
//
    }

    public void crearTitulosFilasDali40(String[] titulosFila, Integer p) {

        crearEstilosBorde();

        Integer lineasAdicionadas = this.lineaTitulosFilas;
        LineaXLSDali40 linea = null;
        Celda celda = null;
        this.saltoColumna = 0;
        for (String vString : titulosFila) {

            if (vString.equals("DETALLE EFECTIVO") || vString.equals("RESUMEN") || vString.equals("DETALLE CHEQUES")) {
                //this.hoja.addMergedRegion(new Region(this.lineaTitulosFilas, (short) (0 + (saltoColumna * p)), this.lineaTitulosFilas, (short) (4 + (saltoColumna * p))));
                this.hoja.addMergedRegion(new CellRangeAddress(this.lineaTitulosFilas, this.lineaTitulosFilas, (short) (0 + (saltoColumna * p)), (short) (4 + (saltoColumna * p))));
                linea = new LineaXLSDali40(this, this.lineaTitulosFilas, EstiloLineaXLS.TITULO_SECCION);
                celda = new Celda((short) (0 + (10 * p)), (Object) vString, TipoDatoCelda.TITULO_HOJA);
                linea.asignarCelda(celda);

                Celda cel = new Celda((short) (celda.columna + 1), "", TipoDatoCelda.DATO_VACIO);
                linea.asignarCelda(cel);
                Celda cel1 = new Celda((short) (celda.columna + 2), "", TipoDatoCelda.DATO_VACIO);
                linea.asignarCelda(cel1);
                Celda cel2 = new Celda((short) (celda.columna + 3), "", TipoDatoCelda.DATO_VACIO);
                linea.asignarCelda(cel2);
                Celda cel3 = new Celda((short) (celda.columna + 3), "", TipoDatoCelda.DATO_VACIO);
                linea.asignarCelda(cel3);
                Celda cel4 = new Celda((short) (celda.columna + 4), "", TipoDatoCelda.TITULO_HOJA);
                linea.asignarCelda(cel4);

                this.lineaTitulosFilas = this.lineaTitulosFilas + 2;

            } else {
                linea = new LineaXLSDali40(this, this.lineaTitulosFilas, EstiloLineaXLS.TITULO_FILA);
                celda = new Celda((short) (0 + (10 * p)), (Object) vString, TipoDatoCelda.TITULO_HOJA);
                linea.asignarCelda(celda);
            }

            if (vString.equals("CODIGO CAJERO")) {
                this.lineaTitulosFilas = this.lineaTitulosFilas + 3;
            } else if (vString.equals("DEPOSITO CHQ") || vString.equals("PAGOS TC CHQ") || vString.equals("PAGOS FM CHQ") || vString.equals("RECAUDOS CHQ")) {
                //this.hoja.addMergedRegion(new Region(this.lineaTitulosFilas, (short) (0 + (saltoColumna * p)), this.lineaTitulosFilas, (short) (1 + (saltoColumna * p))));
                this.hoja.addMergedRegion(new CellRangeAddress(this.lineaTitulosFilas, this.lineaTitulosFilas, (short) (0 + (saltoColumna * p)), (short) (1 + (saltoColumna * p))));
                Celda cel = new Celda((short) (celda.columna + 1), "", TipoDatoCelda.TITULO_HOJA);
                linea.asignarCelda(cel);

                this.lineaTitulosFilas++;

//                 if(vString.equals("RECAUDOS CHQ"))
//                 {
//                   this.lineaTitulosFilas=this.lineaTitulosFilas+2;
//                 }
//                 else
//                 {
//                    this.lineaTitulosFilas++;
//                 }
                //this.hoja.addMergedRegion(new Region(this.lineaTitulosFilas, (short) (0 + (saltoColumna * p)), this.lineaTitulosFilas, (short) (1 + (saltoColumna * p))));
                this.hoja.addMergedRegion(new CellRangeAddress(this.lineaTitulosFilas, this.lineaTitulosFilas, (short) (0 + (saltoColumna * p)), (short) (1 + (saltoColumna * p))));
                linea = new LineaXLSDali40(this, this.lineaTitulosFilas, EstiloLineaXLS.TITULO_FILA);
                Celda cel1 = new Celda((short) (0 + this.saltoColumna * p), "", TipoDatoCelda.TITULO_HOJA);
                linea.asignarCelda(cel1);
                Celda cel2 = new Celda((short) (1 + this.saltoColumna * p), "", TipoDatoCelda.TITULO_HOJA);
                linea.asignarCelda(cel2);

//  HLGomez: Se agregó "TOTALES" dentro del if.
            } else if (vString.equals("TOTALES") || vString.equals("DENOMINACION 50000") || vString.equals("CANTIDAD") || vString.equals("FALTANTES") || vString.equals("DENOMINACION 1000") || vString.equals("DENOMINACION 2000") || vString.equals("DENOMINACION 5000") || vString.equals("DENOMINACION 10000") || vString.equals("DENOMINACION 20000") || vString.equals("VALOR")) {

                this.lineaTitulosFilas++;
//                if (vString.equals("TOTALES"))
//                {
//                 this.lineaTitulosFilas++;
//                }

            } else if (vString.equals("DEPOSITO EFE") || vString.equals("PAGOS TC EFE") || vString.equals("PAGOS FM EFE") || vString.equals("RECAUDOS EFE")) {
                //this.hoja.addMergedRegion(new Region(this.lineaTitulosFilas, (short) (0 + (saltoColumna * p)), this.lineaTitulosFilas, (short) (1 + (saltoColumna * p))));
                this.hoja.addMergedRegion(new CellRangeAddress(this.lineaTitulosFilas, this.lineaTitulosFilas, (short) (0 + (saltoColumna * p)), (short) (1 + (saltoColumna * p))));
                Celda cel = new Celda((short) (celda.columna + 1), "", TipoDatoCelda.TITULO_HOJA);
                linea.asignarCelda(cel);

                this.lineaTitulosFilas++;
            } else if (vString.equals("SOBRANTES")) {
                this.lineaTitulosFilas = this.lineaTitulosFilas + saltoFila1;
            }
        }

//        if (p == 0) {
//            this.lineaTitulosFilas = lineasAdicionadas;
//        }
    }

    public void crearTitulosColumnaDali40(String[] titulosColumna, boolean borde, Integer p) {

        if (borde) {
            crearEstilosBorde();
        }

        Integer lineasAdicionadas = this.lineasTitulos;
        short numColumna = 1;
        LineaXLSDali40 linea = new LineaXLSDali40(this, ++this.lineasTitulos, EstiloLineaXLS.TITULO_COLUMNA);
        this.saltoColumna = 0;
        int posicion = 0;
        for (String vString : titulosColumna) {
            if (posicion == 0) {
                linea = new LineaXLSDali40(this, this.lineasTitulos, EstiloLineaXLS.TITULO_SECCION);
                Celda celda = new Celda((short) ((short) (0 + (saltoColumna * p))), (Object) vString, TipoDatoCelda.TITULO_HOJA);
                linea.asignarCelda(celda);

                //this.hoja.addMergedRegion(new Region(this.lineasTitulos, (short) (0 + (saltoColumna * p)), this.lineasTitulos, (short) (4 + (saltoColumna * p))));
                CellRangeAddress cellAddress = new CellRangeAddress(this.lineasTitulos, this.lineasTitulos, (short) (0 + (saltoColumna * p)), (short) (4 + (saltoColumna * p)));
                this.hoja.addMergedRegion(cellAddress);

                Celda cel = new Celda((short) (celda.columna + 1), "", TipoDatoCelda.DATO_VACIO);
                linea.asignarCelda(cel);
                Celda cel1 = new Celda((short) (celda.columna + 2), "", TipoDatoCelda.DATO_VACIO);
                linea.asignarCelda(cel1);
                Celda cel2 = new Celda((short) (celda.columna + 3), "", TipoDatoCelda.DATO_VACIO);
                linea.asignarCelda(cel2);
                Celda cel3 = new Celda((short) (celda.columna + 3), "", TipoDatoCelda.DATO_VACIO);
                linea.asignarCelda(cel3);
                Celda cel4 = new Celda((short) (celda.columna + 4), "", TipoDatoCelda.TITULO_HOJA);
                linea.asignarCelda(cel4);

                numColumna++;
                this.lineasTitulos++;
                posicion++;
            } else if (posicion >= 1 && posicion < 4) {
                if (posicion == 1) {
                    numColumna = 2;
                    linea = new LineaXLSDali40(this, this.lineasTitulos, EstiloLineaXLS.TITULO_COLUMNA);
                }
                linea = new LineaXLSDali40(this, this.lineasTitulos, EstiloLineaXLS.TITULO_COLUMNA);
                Celda celda = new Celda((short) (numColumna + (saltoColumna * p)), (Object) vString, TipoDatoCelda.TITULO_COLUMNA);
                linea.asignarCelda(celda);
                numColumna++;
                posicion++;
            } else if (posicion >= 4 && posicion < 8) {
                if (posicion == 4) {
                    this.lineasTitulos = this.lineasTitulos + 10;
                    numColumna = 1;
                    linea = new LineaXLSDali40(this, this.lineasTitulos, EstiloLineaXLS.TITULO_COLUMNA);
                }
                linea = new LineaXLSDali40(this, this.lineasTitulos, EstiloLineaXLS.TITULO_COLUMNA);
                Celda celda = new Celda((short) (numColumna + (saltoColumna * p)), (Object) vString, TipoDatoCelda.TITULO_COLUMNA);
                linea.asignarCelda(celda);
                numColumna++;
                posicion++;
            } else if (posicion >= 8 && posicion < 12) {
                if (posicion == 8) {
                    this.lineasTitulos = this.lineasTitulos + 9;
                    numColumna = 1;
                    linea = new LineaXLSDali40(this, this.lineasTitulos, EstiloLineaXLS.TITULO_COLUMNA);
                }
                Celda celda = new Celda((short) (numColumna + (saltoColumna * p)), (Object) vString, TipoDatoCelda.TITULO_COLUMNA);
                linea.asignarCelda(celda);
                numColumna++;
                posicion++;
            } else if (posicion >= 12) {
                if (posicion == 12) {
                    this.lineasTitulos = this.lineasTitulos + 4;
                    numColumna = 1;
                    linea = new LineaXLSDali40(this, this.lineasTitulos, EstiloLineaXLS.TITULO_COLUMNA);
                }
                Celda celda = new Celda((short) (numColumna + (this.saltoColumna * p)), (Object) vString, TipoDatoCelda.TITULO_COLUMNA);
                linea.asignarCelda(celda);
                numColumna++;
                posicion++;
            }

            if (posicion == 13) {
                this.lineasTitulos = this.lineasTitulos + this.saltoFila;
            }
        }

//        if (p == 0) {
//            this.lineasTitulos = lineasAdicionadas;
//        }
    }

    public void crearLineasNormalesDali40(Collection<Registro> lineas, boolean borde) {

        if (borde) {
            crearEstilosBorde();
        }
        Boolean p = true;
        LineaXLSDali40 linea = null;
        Integer cont, aux = 0;
        this.saltoColumna = 0;
        for (Registro registro : lineas) {
            Integer lineasAdicionadas = this.lineaDatos;
            aux++;
            cont = 0;
            Collection<Celda> listaCeldas = registro.getCeldas();
            for (Celda celda : listaCeldas) {

                if (aux % 2 == 0) {
                    celda.columna = (short) (celda.columna + saltoColumna);
                } else if (celda.columna > 10) {
                    celda.columna = (short) (celda.columna - saltoColumna);
                }

                if (cont == 1 || cont == 53 || cont == 61) {
                    this.lineaDatos = this.lineaDatos + 3;
                } else if (cont == 4 || cont == 10 || cont == 49 || cont == 16 || cont == 22 || cont == 57
                        || cont == 63 || cont == 29 || cont == 33 || cont == 37 || cont == 41 || cont == 45) {
                    this.lineaDatos++;
                } else if (cont == 7 || cont == 13 || cont == 19) {
                    this.lineaDatos = this.lineaDatos + 1;
                } else if (cont == 25) {
                    this.lineaDatos = this.lineaDatos + 3;
                }

                HSSFRow currentLine = this.libro.getSheet(this.nombreHoja).getRow(this.lineaDatos);

                if (cont == 0) {
//                    //Region r1 = new Region(this.lineaDatos, celda.columna, this.lineaDatos, (short) (celda.columna + 3));
//                    CellRangeAddress r1 = new CellRangeAddress(this.lineaDatos, this.lineaDatos, celda.columna, (short) (celda.columna + 3));
//                    this.hoja.addMergedRegion(r1);
//                    
//                    Celda cel = new Celda((short) (celda.columna + 1), "", TipoDatoCelda.DATO_VACIO);
//                    linea.asignarCelda(cel);
//                    Celda cel1 = new Celda((short) (celda.columna + 2), "", TipoDatoCelda.DATO_VACIO);
//                    linea.asignarCelda(cel1);
//                    Celda cel2 = new Celda((short) (celda.columna + 3), "", TipoDatoCelda.DATO_VACIO);
//                    linea.asignarCelda(cel2);

                    CellRangeAddress r1 = new CellRangeAddress(this.lineaDatos, this.lineaDatos, celda.columna, (celda.columna + 3));
                    this.hoja.addMergedRegion(r1);
                }
                HSSFCell currentCell = currentLine.createCell(Integer.parseInt(celda.columna.toString()));
                LineaXLSDali40 currentXls = new LineaXLSDali40();
                currentXls.archivo = this;
                currentXls.asignarEstiloCelda(currentCell, celda);
                currentXls.asignarValorCelda(currentCell, celda);

                if (cont == 0) {
                    currentCell = currentLine.createCell(Integer.parseInt(celda.columna.toString()) + 1);
                    currentXls = new LineaXLSDali40();
                    currentXls.archivo = this;
                    currentXls.asignarEstiloCelda(currentCell, celda);

                    currentCell = currentLine.createCell(Integer.parseInt(celda.columna.toString()) + 2);
                    currentXls = new LineaXLSDali40();
                    currentXls.archivo = this;
                    currentXls.asignarEstiloCelda(currentCell, celda);

                    currentCell = currentLine.createCell(Integer.parseInt(celda.columna.toString()) + 3);
                    currentXls = new LineaXLSDali40();
                    currentXls.archivo = this;
                    currentXls.asignarEstiloCelda(currentCell, celda);
                }

//                linea.asignarCelda(celda);
                cont++;

            }

            if (cont == 65) {
                this.lineaDatos = this.lineaDatos + saltoFila1;
            }

//            if (p == true) {
//                this.lineaDatos = lineasAdicionadas;
//            }
            p = !p;

        }

    }

    public void grabarArchivo() throws IOException {

        this.hoja.setColumnWidth(0, 7000);
        this.hoja.setColumnWidth(1, 5000);
        this.hoja.setColumnWidth(2, 5000);
        this.hoja.setColumnWidth(3, 5000);
        this.hoja.setColumnWidth(4, 5000);
        this.hoja.setColumnWidth((0 + this.saltoColumna), 7000);
        this.hoja.setColumnWidth((1 + this.saltoColumna), 5000);
        this.hoja.setColumnWidth((2 + this.saltoColumna), 5000);
        this.hoja.setColumnWidth((3 + this.saltoColumna), 5000);
        this.hoja.setColumnWidth((4 + this.saltoColumna), 5000);

        libro.write(fos);
    }

    private void crearEstilos() {
        estiloCeldaDatoFecha = libro.createCellStyle();
        estiloCeldaDatoFecha.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        estiloCeldaDatoMoneda = libro.createCellStyle();
        estiloCeldaDatoMoneda.setDataFormat(HSSFDataFormat.getBuiltinFormat("($#,##0_);[Red]($#,##0)"));
        estiloCeldaDatoNumerico = libro.createCellStyle();
        estiloCeldaDatoNumerico.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

        HSSFFont fTituloHoja = libro.createFont();
        fTituloHoja.setFontHeightInPoints((short) 14);
        fTituloHoja.setColor(HSSFFont.COLOR_RED);
        estiloTituloHoja = libro.createCellStyle();
        estiloTituloHoja.setFont(fTituloHoja);

        HSSFFont fTituloHojaNormal = libro.createFont();
        fTituloHojaNormal.setFontHeightInPoints((short) 14);
        fTituloHojaNormal.setColor(HSSFFont.COLOR_NORMAL);
        estiloTituloHojaNormal = libro.createCellStyle();
        estiloTituloHojaNormal.setFont(fTituloHojaNormal);

        HSSFFont fTituloHojaNormalCentrado = libro.createFont();
        fTituloHojaNormalCentrado.setFontHeightInPoints((short) 12);
        fTituloHojaNormalCentrado.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fTituloHojaNormalCentrado.setColor(HSSFFont.COLOR_NORMAL);
        estiloTituloHojaNormalCentrado = libro.createCellStyle();
        estiloTituloHojaNormalCentrado.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloTituloHojaNormalCentrado.setFont(fTituloHojaNormalCentrado);

        HSSFFont fTituloGrandeHoja = libro.createFont();
        fTituloGrandeHoja.setFontHeightInPoints((short) 24);
        fTituloGrandeHoja.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fTituloGrandeHoja.setColor(HSSFFont.COLOR_RED);
        estiloTituloGrandeHoja = libro.createCellStyle();
        estiloTituloGrandeHoja.setFont(fTituloGrandeHoja);

        HSSFFont fTituloColumna = libro.createFont();
        fTituloColumna.setFontHeightInPoints((short) 12);
        fTituloColumna.setColor(HSSFFont.COLOR_NORMAL);
        fTituloColumna.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloTituloColumna = libro.createCellStyle();
        estiloTituloColumna.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloTituloColumna.setFont(fTituloColumna);

        HSSFFont fnormal = libro.createFont();
        fnormal.setFontHeightInPoints((short) 10);
        fnormal.setFontName(HSSFFont.FONT_ARIAL);
        estiloLineaNormal = libro.createCellStyle();
        estiloLineaNormal.setFont(fnormal);
        //nuevo
        HSSFFont fSubTituloHoja = libro.createFont();
        fSubTituloHoja.setFontHeightInPoints((short) 10);
        fSubTituloHoja.setColor(HSSFFont.COLOR_NORMAL);
        fSubTituloHoja.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloSubtituloTituloHoja = libro.createCellStyle();
        //estiloSubtituloTituloHoja.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        estiloSubtituloTituloHoja.setFont(fSubTituloHoja);

        HSSFFont fSubTituloHojaNormal = libro.createFont();
        fSubTituloHojaNormal.setFontHeightInPoints((short) 12);
        fSubTituloHojaNormal.setColor(HSSFFont.COLOR_NORMAL);
        estiloSubTituloNormal = libro.createCellStyle();
        estiloSubTituloNormal.setFont(fSubTituloHojaNormal);

        HSSFFont fSubTituloFirma = libro.createFont();
        fSubTituloFirma.setFontHeightInPoints((short) 11);
        fSubTituloFirma.setColor(HSSFFont.COLOR_NORMAL);
        fSubTituloFirma.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloSubTituloFirma = libro.createCellStyle();
        estiloSubTituloFirma.setFont(fSubTituloFirma);

        HSSFFont fTituloSeccion = libro.createFont();
        fTituloSeccion.setFontHeightInPoints((short) 10);
        fTituloSeccion.setColor(HSSFFont.COLOR_NORMAL);
        fTituloSeccion.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloTituloSeccion = libro.createCellStyle();
        estiloTituloSeccion.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloTituloSeccion.setFont(fTituloSeccion);

        HSSFFont fDatoVacio = libro.createFont();
        fDatoVacio.setFontHeightInPoints((short) 12);
        fDatoVacio.setColor(HSSFFont.COLOR_NORMAL);
        fDatoVacio.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloDatoVacio = libro.createCellStyle();
        estiloDatoVacio.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloDatoVacio.setFont(fDatoVacio);

        HSSFFont fTituloFila = libro.createFont();
        fTituloFila.setFontHeightInPoints((short) 12);
        fTituloFila.setColor(HSSFFont.COLOR_NORMAL);
        fTituloFila.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloTituloFila = libro.createCellStyle();
        estiloTituloFila.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloTituloFila.setFont(fDatoVacio);

    }

    private void crearEstilosBorde() {
        estiloCeldaDatoFecha = libro.createCellStyle();
        estiloCeldaDatoFecha.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        estiloCeldaDatoFecha.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloCeldaDatoFecha.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        estiloCeldaDatoFecha.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        estiloCeldaDatoFecha.setBorderRight(HSSFCellStyle.BORDER_THIN);
        estiloCeldaDatoFecha.setBorderTop(HSSFCellStyle.BORDER_THIN);

        estiloCeldaDatoMoneda = libro.createCellStyle();
        estiloCeldaDatoMoneda.setDataFormat(HSSFDataFormat.getBuiltinFormat("($#,##0_);[Red]($#,##0)"));
        estiloCeldaDatoMoneda.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloCeldaDatoMoneda.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        estiloCeldaDatoMoneda.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        estiloCeldaDatoMoneda.setBorderRight(HSSFCellStyle.BORDER_THIN);
        estiloCeldaDatoMoneda.setBorderTop(HSSFCellStyle.BORDER_THIN);

        estiloCeldaDatoNumerico = libro.createCellStyle();
        estiloCeldaDatoNumerico.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
        estiloCeldaDatoNumerico.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloCeldaDatoNumerico.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        estiloCeldaDatoNumerico.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        estiloCeldaDatoNumerico.setBorderRight(HSSFCellStyle.BORDER_THIN);
        estiloCeldaDatoNumerico.setBorderTop(HSSFCellStyle.BORDER_THIN);

        HSSFFont fTituloHoja = libro.createFont();
        fTituloHoja.setFontHeightInPoints((short) 10);
        fTituloHoja.setColor(HSSFFont.COLOR_RED);
        estiloTituloHoja = libro.createCellStyle();
        estiloTituloHoja.setFont(fTituloHoja);

        HSSFFont fTituloHojaNormal = libro.createFont();
        fTituloHojaNormal.setFontHeightInPoints((short) 14);
        fTituloHojaNormal.setColor(HSSFFont.COLOR_NORMAL);
        estiloTituloHojaNormal = libro.createCellStyle();
        estiloTituloHojaNormal.setFont(fTituloHojaNormal);

        //estiloTituloHojaNormalCentrado
        HSSFFont fTituloHojaNormalCentrado = libro.createFont();
        fTituloHojaNormalCentrado.setFontHeightInPoints((short) 12);
        fTituloHojaNormalCentrado.setColor(HSSFFont.COLOR_NORMAL);
        estiloTituloHojaNormalCentrado = libro.createCellStyle();
        estiloTituloHojaNormalCentrado.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloTituloHojaNormalCentrado.setFont(fTituloHojaNormalCentrado);

        HSSFFont fTituloGrandeHoja = libro.createFont();
        fTituloGrandeHoja.setFontHeightInPoints((short) 24);
        fTituloGrandeHoja.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fTituloGrandeHoja.setColor(HSSFFont.COLOR_RED);
        estiloTituloGrandeHoja = libro.createCellStyle();
        estiloTituloGrandeHoja.setFont(fTituloGrandeHoja);

        HSSFFont fTituloColumna = libro.createFont();
        fTituloColumna.setFontHeightInPoints((short) 12);
        fTituloColumna.setColor(HSSFFont.COLOR_NORMAL);
        fTituloColumna.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloTituloColumna = libro.createCellStyle();
        estiloTituloColumna.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloTituloColumna.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        estiloTituloColumna.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        estiloTituloColumna.setBorderRight(HSSFCellStyle.BORDER_THIN);
        estiloTituloColumna.setBorderTop(HSSFCellStyle.BORDER_THIN);
        estiloTituloColumna.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
        estiloTituloColumna.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        estiloTituloColumna.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        estiloTituloColumna.setFont(fTituloColumna);

        HSSFFont fTituloSeccion = libro.createFont();
        fTituloSeccion.setFontHeightInPoints((short) 10);
        fTituloSeccion.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloTituloSeccion = libro.createCellStyle();
        estiloTituloSeccion.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloTituloSeccion.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        estiloTituloSeccion.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        estiloTituloSeccion.setBorderRight(HSSFCellStyle.BORDER_THIN);
        estiloTituloSeccion.setBorderTop(HSSFCellStyle.BORDER_THIN);
        estiloTituloSeccion.setFont(fTituloSeccion);

        HSSFFont fDatoVacio = libro.createFont();
        fDatoVacio.setFontHeightInPoints((short) 12);
        fDatoVacio.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloDatoVacio = libro.createCellStyle();
        estiloDatoVacio.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloDatoVacio.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        estiloDatoVacio.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        estiloDatoVacio.setFont(fDatoVacio);

        HSSFFont fTituloFila = libro.createFont();
        fTituloFila.setFontHeightInPoints((short) 10);
        fTituloFila.setColor(HSSFFont.COLOR_NORMAL);
        fTituloFila.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloTituloFila = libro.createCellStyle();
        estiloTituloFila.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        estiloTituloFila.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        estiloTituloFila.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        estiloTituloFila.setBorderRight(HSSFCellStyle.BORDER_THIN);
        estiloTituloFila.setBorderTop(HSSFCellStyle.BORDER_THIN);
        estiloTituloFila.setFillBackgroundColor(HSSFColor.BRIGHT_GREEN.index);
        estiloTituloFila.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        estiloTituloFila.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        estiloTituloFila.setFont(fDatoVacio);

        HSSFFont fnormal = libro.createFont();
        fnormal.setFontHeightInPoints((short) 10);
        fnormal.setFontName(HSSFFont.FONT_ARIAL);
        estiloLineaNormal = libro.createCellStyle();
        estiloLineaNormal.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        estiloLineaNormal.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        estiloLineaNormal.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        estiloLineaNormal.setBorderRight(HSSFCellStyle.BORDER_THIN);
        estiloLineaNormal.setBorderTop(HSSFCellStyle.BORDER_THIN);
        estiloLineaNormal.setFont(fnormal);
        //nuevo
        HSSFFont fSubTituloHoja = libro.createFont();
        fSubTituloHoja.setFontHeightInPoints((short) 12);
        fSubTituloHoja.setColor(HSSFFont.COLOR_NORMAL);
        fSubTituloHoja.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        estiloSubtituloTituloHoja = libro.createCellStyle();
        //estiloSubtituloTituloHoja.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        estiloSubtituloTituloHoja.setFont(fSubTituloHoja);

//        HSSFFont fSubTituloHojaNormal = libro.createFont();
//        fSubTituloHojaNormal.setFontHeightInPoints((short) 12);
//        fSubTituloHojaNormal.setColor(HSSFFont.COLOR_NORMAL);
//        estiloSubTituloNormal = libro.createCellStyle();
//        estiloSubTituloNormal.setFont(fSubTituloHojaNormal);
//
//        HSSFFont fSubTituloFirma = libro.createFont();
//        fSubTituloFirma.setFontHeightInPoints((short) 11);
//        fSubTituloFirma.setColor(HSSFFont.COLOR_NORMAL);
//        estiloSubTituloFirma = libro.createCellStyle();
//        estiloSubTituloFirma.setFont(fSubTituloFirma);
    }
}
