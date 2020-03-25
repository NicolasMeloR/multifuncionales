/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.carguesmasivos;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.entitys.CarmasReintegrosTemp;
import com.davivienda.sara.procesos.cargues.masivos.reintegros.session.CargarReintegrosMasivosSessionLocal;
import com.davivienda.sara.reintegros.general.ReintegrosGeneralObjectContext;
import com.davivienda.sara.reintegros.general.ReintegrosHelperInterface;
import com.davivienda.sara.reintegros.general.formato.TituloCargasMasivasReintegros;
import com.davivienda.sara.reintegros.general.helper.CargarReintegrosMasivosServletHelper;
import com.davivienda.utilidades.Constantes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
@ManagedBean(name = "cargarReintegrosMasivosBean")
@ViewScoped
public class CargarReintegrosMasivosBean extends BaseBean implements Serializable {

    public static final String LISTA_REINTEGROS_MASIVOS = "listaReintegrosMasivos";

    @EJB
    CargarReintegrosMasivosSessionLocal sessionLocal;
    private List<CarmasReintegrosTemp> listaData;
    private FileItem archivoReintegros = null;
    private Logger loggerApp;
    private ReintegrosHelperInterface servletHelper;
    private ComponenteAjaxObjectContextWeb objectContext;
    private ReintegrosGeneralObjectContext reintegrosGeneralObjectContext;

    public CargarReintegrosMasivosBean() {
        this.listaData = new ArrayList<>();
    }

    @PostConstruct
    public void CargarReintegrosMasivosBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            loggerApp = objectContext.getConfigApp().loggerApp;
            reintegrosGeneralObjectContext = new ReintegrosGeneralObjectContext(getRequestFaces(), getResponseFaces());
            if (objectContext != null) {
                init();
            }
        } catch (Exception ex) {
            abrirModal("SARA", "Error", ex);
        }
    }

    private void init() {
        loggerApp = cargarComponenteAjaxObjectContext().getConfigApp().loggerApp;
        if (this.listaData == null) {
            this.listaData.clear();
        }
        this.archivoReintegros = null;
    }

    public void ejecutarCargue() {
        loggerApp.info("Cargues masivos reintegros - ejecutar cargue");
        String respuesta = null;
        try {
            if (this.archivoReintegros != null) { // Se valida que se haya examinado un archivo
                if (validarExtension(Constantes.EXTENSIONES_ARCHIVO_EXCEL, archivoReintegros)) { // Se valida la extension del archivo
                    if ("".equals(generarListaData())) {
                        //generarListaData();
                        reintegrosGeneralObjectContext.setAtributo(LISTA_REINTEGROS_MASIVOS, listaData);
                        this.servletHelper = new CargarReintegrosMasivosServletHelper(reintegrosGeneralObjectContext, sessionLocal);
                        if (servletHelper != null) {
                            respuesta = servletHelper.obtenerDatos();
                        }
                        if (respuesta.trim().isEmpty()) {
                            abrirModal("SARA", "Solicitud del cargue masivo no procesada", null);
                        }
                    } else {
                        abrirModal("SARA", "Error: " + generarListaData(), null);
                    }
                } else {
                    abrirModal("SARA", "Error: El archivo debe ser de tipo libro: " + Constantes.EXTENSIONES_ARCHIVO_EXCEL, null);
                }
            } else {
                abrirModal("SARA", "Error: Seleccione un archivo con formato .xls", null);
            }
            this.init();
        } catch (FileStructureException e) {
            abrirModal("SARA", "Error Ejecutando cargue", e);
        }
    }

    private String generarListaData() throws FileStructureException {

        String usuarioEnSesion = reintegrosGeneralObjectContext.getUsuarioEnSesion().getUsuario();
        CarmasReintegrosTemp reintegro = null;
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        Iterator<Row> rowIterator = null;
        Row row = null;
        DataFormatter formatter = new DataFormatter();
        listaData.clear();
        InputStream inputStream = null;
        try {
            if (this.archivoReintegros != null) {
                inputStream = this.archivoReintegros.getInputStream();
                workbook = new HSSFWorkbook(inputStream);
                sheet = workbook.getSheetAt(0);
                rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    row = rowIterator.next();
                    if (row != null) {
                        if (row.getRowNum() == 0) {
                            if (!"".equals(validarEstructura(row))) {
                                return validarEstructura(row);
                            }
                        } else {
                            if (!isEmptyRow(row)) {
                                reintegro = new CarmasReintegrosTemp();
                                reintegro.setUsuario(usuarioEnSesion);
                                reintegro.setCausal(row.getCell(0) == null ? "" : formatter.formatCellValue(row.getCell(0)).replaceAll("\\D+", ""));
                                reintegro.setNroIdentificacionDeudor(row.getCell(1) == null ? "" : formatter.formatCellValue(row.getCell(1)).replaceAll("\\D+", ""));
                                reintegro.setCodigoUnico(row.getCell(2) == null ? "" : formatter.formatCellValue(row.getCell(2)).replaceAll("\\D+", ""));
                                reintegro.setNroCuenta(row.getCell(3) == null ? "" : formatter.formatCellValue(row.getCell(3)).replaceAll("\\D+", ""));
                                reintegro.setTipoCuenta(formatter.formatCellValue(row.getCell(4)));
                                reintegro.setValor(row.getCell(5) == null ? "" : formatter.formatCellValue(row.getCell(5)).replaceAll("\\D+", ""));
                                reintegro.setTalon(row.getCell(6) == null ? "" : formatter.formatCellValue(row.getCell(6)).replaceAll("\\D+", ""));
                                reintegro.setComision(row.getCell(7) == null ? "" : formatter.formatCellValue(row.getCell(7)).replaceAll("\\D+", ""));
                                reintegro.setFecha(row.getCell(8) == null ? "" : formatter.formatCellValue(row.getCell(8)).replaceAll("\\D+", ""));
                                reintegro.setOficinaRecuado(row.getCell(9) == null ? "" : formatter.formatCellValue(row.getCell(9)).replaceAll("\\D+", ""));
                                reintegro.setErrorTransaccion(row.getCell(10) == null ? "" : formatter.formatCellValue(row.getCell(10)).replaceAll("\\^[a-zA-Z0-9]+$", ""));
                                reintegro.setConcepto(row.getCell(11) == null ? "" : formatter.formatCellValue(row.getCell(11)).replaceAll("\\D+", ""));
                                reintegro.setTipo(formatter.formatCellValue(row.getCell(12)));
                                listaData.add(reintegro);
                            }
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
        return "";
    }

    private String validarEstructura(Row row) throws FileStructureException {
        String[] titulos = TituloCargasMasivasReintegros.tituloColumnasReintegros;
        String msgError = "";
        boolean val = true;
        for (int i = 0; i < titulos.length - 1; i++) {
            if (!row.getCell(i).toString().trim().equalsIgnoreCase(titulos[i])) {
                val = false;
            }
        }

        if (val == false) {
            msgError += "Estructura del archivo inválida. La estructura correcta es:\n";
            for (int i = 0; i < titulos.length - 1; i++) {
                msgError += titulos[i] + "\n";
            }
        }
        return msgError;
    }

    public FileItem getArchivoReintegros() {
        return archivoReintegros;
    }

    public void setArchivoReintegros(FileItem archivoReintegros) {
        this.archivoReintegros = archivoReintegros;
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
}
