/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.carguesmasivos;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasSessionLocal;
import com.davivienda.sara.entitys.CarmasTimbresTemp;
import com.davivienda.sara.procesos.ajustes.session.AjustesProcesosSessionLocal;
import com.davivienda.sara.procesos.cargues.masivos.timbres.session.CargarTimbresMasivosSessionLocal;
import com.davivienda.sara.reintegros.general.CargarTimbresMasivosObjectContext;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.formato.TituloCargasMasivasTimbres;
import com.davivienda.sara.reintegros.general.helper.CargarTimbresMasivosServletHelper;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.utilidades.Constantes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.management.InvalidApplicationException;
import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author jediazs
 */
@ManagedBean(name = "cargarTimbresMasivosBean")
@ViewScoped
public class CargarTimbresMasivosBean extends BaseBean implements Serializable {

    @EJB
    CargarTimbresMasivosSessionLocal sessionLocal;
    @EJB
    AjustesProcesosSessionLocal sessionAjustes;
    @EJB
    CuadreCifrasSessionLocal cuadreCifrasSessionLocal;
    @EJB
    CajeroSessionLocal cajeroSession;

    private FileItem file;
    private Logger loggerApp;
    private ReintegrosHelperInterface servletHelper;
    private Collection<CarmasTimbresTemp> listaData;
    private ComponenteAjaxObjectContextWeb objectContext;
    private CargarTimbresMasivosObjectContext cargarTimbresMasivosObjectContext;

    public CargarTimbresMasivosBean() {
        this.listaData = new ArrayList<>();
    }

    /**
     * Constructur de la clase
     */
    @PostConstruct
    public void CargarTimbresMasivosBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            loggerApp = objectContext.getConfigApp().loggerApp;
            cargarTimbresMasivosObjectContext = new CargarTimbresMasivosObjectContext(getRequestFaces(), getResponseFaces());
            if (objectContext != null) {
                init();
            }
        } catch (Exception ex) {
            abrirModal("SARA", "Error", ex);
        }
    }

    /**
     * constructor
     */
    public void init() {
        loggerApp = cargarComponenteAjaxObjectContext().getConfigApp().loggerApp;
        if (this.listaData == null) {
            this.listaData.clear();
        }
        this.file = null;
    }

    /**
     * Ejecutar proceso cargue masivo reintegros
     */
    public void ejecutarCargue() {
        String respuesta = null;
        try {
            loggerApp.info("Cargues masivos timbres - ejecutar cargue");
            if (!validarExtension(Constantes.EXTENSIONES_ARCHIVO_EXCEL, file)) {
                abrirModal("SARA", "Error: El archivo " + (null == file ? " " : file.getName()) + " no cumple con el formato de extensión válido " + Constantes.EXTENSIONES_ARCHIVO_EXCEL, null);
                return;
            }
            generarListaData();
            cargarTimbresMasivosObjectContext.setAtributo("listaTimbres", listaData);
            this.servletHelper = new CargarTimbresMasivosServletHelper(sessionLocal, cargarTimbresMasivosObjectContext, objectContext, sessionAjustes, cuadreCifrasSessionLocal, cajeroSession);
            if (this.servletHelper != null) {
                respuesta = servletHelper.obtenerDatos();
            }
            if (respuesta != null && !respuesta.trim().isEmpty()) {
                abrirModal("SARA", respuesta, null);
            }
        } catch (FileStructureException ex) {
            ex.printStackTrace();
            loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", ex.getMessage(), null);
        } catch (Exception ex) {
            ex.printStackTrace();
            loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }

    }

    /**
     * Generar listado de registros para poblar la tabla temporal de bd
     */
    private void generarListaData() throws FileStructureException {
        CarmasTimbresTemp timbre = null;
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        Iterator<Row> rowIterator = null;
        Row row = null;
        DataFormatter formatter = new DataFormatter(Locale.getDefault());
        listaData.clear();
        InputStream inputStream = null;
        try {
            if (this.file != null) {
                inputStream = this.file.getInputStream();
                workbook = new HSSFWorkbook(inputStream);
                sheet = workbook.getSheetAt(0);
                rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    row = rowIterator.next();
                    if (row.getRowNum() == 0) {
                        validarEstructura(row);
                    } else if (row != null) {
                        if (!isEmptyRow(row)) {
                            timbre = new CarmasTimbresTemp();
                            timbre.setCajero(formatter.formatCellValue(row.getCell(0)).replaceAll("\\D+", ""));
                            timbre.setProvdiasgteMaq(formatter.formatCellValue(row.getCell(1)).replaceAll("\\D+", ""));
                            timbre.setProvdiasgteLin(formatter.formatCellValue(row.getCell(2)).replaceAll("\\D+", ""));
                            timbre.setDiferencias(formatter.formatCellValue(row.getCell(3)).replaceAll("\\D+", ""));
                            timbre.setObservacion(row.getCell(4) == null ? "" : row.getCell(4).toString().toUpperCase().replaceAll("\\^[a-zA-Z0-9]+$", ""));
                            timbre.setOcca(formatter.formatCellValue(row.getCell(5)).replaceAll("\\D+", ""));
                            timbre.setAumento(formatter.formatCellValue(row.getCell(6)).replaceAll("\\D+", ""));
                            timbre.setDisminucion(formatter.formatCellValue(row.getCell(7)).replaceAll("\\D+", ""));
                            timbre.setSobrante(formatter.formatCellValue(row.getCell(8)).replaceAll("\\D+", ""));
                            timbre.setFaltante(formatter.formatCellValue(row.getCell(9)).replaceAll("\\D+", ""));
                            timbre.setNovedad(row.getCell(10) == null ? "" : row.getCell(10).toString().toUpperCase().replaceAll("\\^[a-zA-Z0-9]+$", ""));
                            timbre.setAsignadoA(row.getCell(11) == null ? "" : row.getCell(11).toString().toUpperCase().replaceAll("\\^[a-zA-Z0-9]+$", ""));
                            timbre.setProveedor(row.getCell(12) == null ? "" : row.getCell(12).toString().toUpperCase().replaceAll("\\^[a-zA-Z0-9]+$", ""));
                            timbre.setClasificacion(row.getCell(13) == null ? "" : row.getCell(13).toString().toUpperCase().replaceAll("\\^[a-zA-Z0-9]+$", ""));
                            timbre.setTipificacionTransportadora(row.getCell(14) == null ? "" : row.getCell(14).toString().toUpperCase().replaceAll("\\^[a-zA-Z0-9]+$", ""));
                            listaData.add(timbre);
                        }
                    }
                }
            }
        } catch (OfficeXmlFileException ex) {
            abrirModal("SARA", "Formato Invalido", ex);
        } catch (IOException ex) {
            abrirModal("SARA", "Seleccione archivo", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(CargarTimbresMasivosBean.class.getName()).log(Level.SEVERE, null, ex);
                    inputStream = null;
                }
            }
        }
    }

    private boolean isEmptyRow(Row row) {
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if ((cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)) {
                return false;
            }
        }
        return true;
    }

    public FileItem getFile() {
        return file;
    }

    public void setFile(FileItem file) {
        this.file = file;
    }

    private void validarEstructura(Row row) throws FileStructureException {
        String[] titulos = TituloCargasMasivasTimbres.tituloColumnasIngreso;
        String mensaje = "Estructura del archivo inválida, revise el/los encabezado(s):\n";
        boolean error = false;
        for (int i = 0; i < titulos.length; i++) {
            String titulo = titulos[i];
            if (row == null || row.getCell(i)==null || !row.getCell(i).toString().trim().equalsIgnoreCase(titulo)) {
                error = true;
                mensaje += "-" + titulo + "\n";
            }
        }
        if (error) {
            throw new FileStructureException(mensaje);
        }
    }
}
