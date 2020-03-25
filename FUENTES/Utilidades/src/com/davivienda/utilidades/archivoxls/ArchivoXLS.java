/*
 * Banco Davivienda 2008
 * Proyecto Utilidades
 * Versión  1.0
 */
package com.davivienda.utilidades.archivoxls;

import com.davivienda.utilidades.archivo.Archivo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * ArchivoXLS - 10/08/2008 Descripción : Contiene el acceso a un archivo de tipo
 * xls Versión : 1.0
 *
 * @author jjvargas Davivienda 2008
 */
public class ArchivoXLS extends Archivo {

    protected HSSFWorkbook libro;
    protected HSSFSheet hoja;
    protected String nombreHoja;
    protected Integer lineasTitulos;
    private HSSFRow lineaTituloColumna;
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
    protected Collection<HSSFRow> lineas;
    //nuevo
    protected HSSFCellStyle estiloSubtituloTituloHoja;
    protected boolean encuentraGrafica;

    /**
     * Crea una nueva instancia de <code>ArchivoXLS</code>.
     */
    public ArchivoXLS() throws FileNotFoundException, IOException {
        this(null, null, "Hoja1");
    }

    /**
     * Crea un objeto ArchivoXLS con el nombre del archivo asociado
     *
     * @param nombreArchivo
     */
    public ArchivoXLS(String nombreArchivo) throws FileNotFoundException, IOException {
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
    public ArchivoXLS(String directorio, String nombreArchivo, String nombreHoja) throws FileNotFoundException, IOException {
        super(nombreArchivo, directorio);
        this.nombreHoja = nombreHoja;
        this.libro = new HSSFWorkbook();
        this.hoja = libro.createSheet(this.nombreHoja);

        this.crearEstilos();

    }

    public ArchivoXLS(String directorio, String nombreArchivo, String nombreHoja, boolean blngraph, String dirLogo) throws FileNotFoundException, IOException {
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

    public void crearTitulosHoja(String[] titulosHoja) {
        this.lineasTitulos = 0;
        for (String vString : titulosHoja) {
            LineaXLS linea = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.TITULO_HOJA);
            Celda celda = new Celda((short) 0, (Object) vString, TipoDatoCelda.TITULO_HOJA);
            linea.asignarCelda(celda);
            this.lineasTitulos++;
        }
    }

    public void crearTitulosHojaReintegros(String[] titulosHoja) {

        this.lineasTitulos = 0;
        int minLineastitulo = 2;
//        LineaXLS linea = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.TITULO_GRANDE);
//        Celda celda = new Celda((short) 0, (Object) titulosHoja[0], TipoDatoCelda.TITULO_HOJA);
//        linea.asignarCelda(celda);
//        this.lineasTitulos++;
        if (encuentraGrafica) {
            LineaXLS linea = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.TITULO_HOJA_NORMAL);
            Celda celda = new Celda((short) 0, "", TipoDatoCelda.TITULO_HOJA);
            linea.asignarCelda(celda);
            this.lineasTitulos++;

            LineaXLS linea5 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.TITULO_HOJA_NORMAL);
            Celda celda5 = new Celda((short) 0, "", TipoDatoCelda.TITULO_HOJA);
            linea5.asignarCelda(celda5);
            this.lineasTitulos++;

            LineaXLS linea6 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.TITULO_HOJA_NORMAL);
            Celda celda6 = new Celda((short) 0, "", TipoDatoCelda.TITULO_HOJA);
            linea6.asignarCelda(celda6);
            this.lineasTitulos++;
            minLineastitulo = 4;
        } else {
            LineaXLS linea = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.TITULO_GRANDE);
            Celda celda = new Celda((short) 0, (Object) titulosHoja[0], TipoDatoCelda.TITULO_HOJA);
            linea.asignarCelda(celda);
            this.lineasTitulos++;

        }

        LineaXLS linea1 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.TITULO_HOJA_NORMAL);
        Celda celda1 = new Celda((short) 0, (Object) titulosHoja[1], TipoDatoCelda.TITULO_COLUMNA);
        linea1.asignarCelda(celda1);
        //this.lineasTitulos++;
        for (String vString : titulosHoja) {
            //if(lineasTitulos>2)
            if (lineasTitulos > minLineastitulo) {
                LineaXLS linea2 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.SUBTITULO_HOJA);
                Celda celda2 = new Celda((short) 0, (Object) vString, TipoDatoCelda.TITULO_COLUMNA);
                linea2.asignarCelda(celda2);
            }
            this.lineasTitulos++;
        }
    }

    public void crearTitulosHojaBanAgrario(String[] titulosHoja) {

        this.lineasTitulos = 0;
        estiloSubtituloTituloHoja.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        LineaXLS linea = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.TITULO_HOJA_NORMAL_CENTRADO);
        Celda celda = new Celda((short) 0, (Object) titulosHoja[0], TipoDatoCelda.TITULO_HOJA);
        linea.asignarCelda(celda);
        this.lineasTitulos++;

        estiloSubtituloTituloHoja.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        for (String vString : titulosHoja) {

            if (lineasTitulos > 1) {
                LineaXLS linea2 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.SUBTITULO_HOJA);
                Celda celda2 = new Celda((short) 0, (Object) vString, TipoDatoCelda.TITULO_COLUMNA);
                linea2.asignarCelda(celda2);
            }

            this.lineasTitulos++;
        }
    }

    public void crearSubtituloHojaBanAgrario(String subtitulosHoja) {

        LineaXLS linea2 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.DATOS);
        Celda celda2 = new Celda((short) 0, subtitulosHoja, TipoDatoCelda.TITULO_COLUMNA);
        linea2.asignarCelda(celda2);

        this.lineasTitulos++;

    }

    public void crearSubtituloinferiorHoja(String[] titulosInfTotales, String[] subtitulosInfHoja, String[] subFirma) {

        this.lineasTitulos = this.lineasTitulos + 5;
        LineaXLS linea2 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.SUBTITULO_HOJA_NORMAL);
        Celda celda2 = new Celda((short) 0, (Object) titulosInfTotales[0], TipoDatoCelda.TITULO_HOJA);
        linea2.asignarCelda(celda2);

        this.lineasTitulos++;

        LineaXLS linea3 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.SUBTITULO_HOJA_NORMAL);
        Celda celda3 = new Celda((short) 0, (Object) titulosInfTotales[1], TipoDatoCelda.TITULO_HOJA);
        linea3.asignarCelda(celda3);

        this.lineasTitulos++;
        LineaXLS linea4 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.SUBTITULO_HOJA);
        Celda celda4 = new Celda((short) 0, (Object) titulosInfTotales[2], TipoDatoCelda.TITULO_HOJA);
        linea4.asignarCelda(celda4);

        this.lineasTitulos++;

        this.lineasTitulos = this.lineasTitulos + 3;

        LineaXLS linea5 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.SUBTITULO_HOJA);
        Celda celda5 = new Celda((short) 0, (Object) subtitulosInfHoja[0], TipoDatoCelda.TITULO_HOJA);
        linea5.asignarCelda(celda5);

        this.lineasTitulos++;
        LineaXLS linea6 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.SUBTITULO_HOJA);
        Celda celda6 = new Celda((short) 0, (Object) subtitulosInfHoja[1], TipoDatoCelda.TITULO_HOJA);
        linea6.asignarCelda(celda6);

        this.lineasTitulos++;

        LineaXLS linea7 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.SUBTITULO_HOJA);
        Celda celda7 = new Celda((short) 0, (Object) subtitulosInfHoja[2], TipoDatoCelda.TITULO_HOJA);
        linea7.asignarCelda(celda7);

        this.lineasTitulos++;
        this.lineasTitulos = this.lineasTitulos + 4;

        LineaXLS linea8 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.SUBTITULO_FIRMA);
        Celda celda8 = new Celda((short) 0, (Object) subFirma[0], TipoDatoCelda.TITULO_HOJA);
        linea8.asignarCelda(celda8);

        this.lineasTitulos++;
        LineaXLS linea9 = new LineaXLS(this, this.lineasTitulos, EstiloLineaXLS.SUBTITULO_FIRMA);
        Celda celda9 = new Celda((short) 0, (Object) subFirma[1], TipoDatoCelda.TITULO_HOJA);
        linea9.asignarCelda(celda9);

        this.lineasTitulos++;

    }

    public void crearTitulosColumna(String[] titulosColumna, boolean borde) {
        if (borde) {
            crearEstilosBorde();
        }
        short numColumna = 0;
        LineaXLS linea = new LineaXLS(this, ++this.lineasTitulos, EstiloLineaXLS.TITULO_COLUMNA);
        this.lineaTituloColumna = linea.getLinea();
        for (String vString : titulosColumna) {
            Celda celda = new Celda(numColumna, (Object) vString, TipoDatoCelda.TITULO_COLUMNA);
            linea.asignarCelda(celda);
            numColumna++;
        }
        --numColumna;
        for (short j = 0; j < this.lineasTitulos; j++) {
            this.hoja.addMergedRegion(new Region(j, (short) 0, j, numColumna));
        }

    }

    public void crearLineasNormales(Collection<Registro> lineas, boolean borde) {
        if (borde) {
            crearEstilosBorde();
        }
        Integer numLinea = this.lineasTitulos;
        for (Registro registro : lineas) {
            LineaXLS linea = new LineaXLS(this, ++numLinea, EstiloLineaXLS.DATOS);
            for (Celda celda : registro.getCeldas()) {
                linea.asignarCelda(celda);

            }
            this.lineasTitulos++;
        }
        this.hoja.setColumnWidth((short) 0, (short) 5000);
        for (Iterator it = this.lineaTituloColumna.cellIterator(); it.hasNext();) {
            HSSFCell celda = (HSSFCell) it.next();
            if (celda.getCellNum() > 0) {
                this.hoja.autoSizeColumn(celda.getCellNum());
            }

        }

    }

    public void grabarArchivo() throws IOException {
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
        fSubTituloHoja.setFontHeightInPoints((short) 12);
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
        fTituloHoja.setFontHeightInPoints((short) 14);
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
