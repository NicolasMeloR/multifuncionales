/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.carguesmasivos;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.entitys.CarmasNotasDebitoTemp;
import com.davivienda.sara.procesos.cargues.masivos.notasdebito.session.CargarNotasDebitoMasivosSessionLocal;
import com.davivienda.sara.reintegros.general.NotasDebitoGeneralObjectContext;
import com.davivienda.sara.reintegros.general.NotasDebitoHelperInterface;
import com.davivienda.sara.reintegros.general.formato.TituloCargasMasivasNotasDebito;
import com.davivienda.sara.reintegros.general.helper.CargarNotasDebitoMasivosServletHelper;
import com.davivienda.utilidades.Constantes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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
@ManagedBean(name = "cargarNotasDebitoMasivosBean")
@ViewScoped
public class CargarNotasDebitoMasivosBean extends BaseBean implements Serializable {

    public static final String LISTA_NOTAS_DEBITO_MASIVOS = "listaNotasDebitoMasivos";

    @EJB
    CargarNotasDebitoMasivosSessionLocal sessionLocal;
    private List<CarmasNotasDebitoTemp> listaData;
    private FileItem archivoNotasDebito = null;
    private Logger loggerApp;
    private NotasDebitoHelperInterface servletHelper;
    private ComponenteAjaxObjectContextWeb objectContext;
    private NotasDebitoGeneralObjectContext notasDebitoGeneralObjectContext;

    public CargarNotasDebitoMasivosBean() {
        this.listaData = new ArrayList<>();
    }

    /**
     * Creates a new instance of CopiarArchivoBean
     */
    @PostConstruct
    public void CargarNotasDebitoMasivosBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            loggerApp = objectContext.getConfigApp().loggerApp;
            notasDebitoGeneralObjectContext = new NotasDebitoGeneralObjectContext(getRequestFaces(), getResponseFaces());
            if (objectContext != null) {
                init();
            }
        } catch (Exception e) {
            abrirModal("SARA", "Error", e);
        }
    }

    public void init() {
        loggerApp = cargarComponenteAjaxObjectContext().getConfigApp().loggerApp;
        if (this.listaData == null) {
            this.listaData.clear();
        }
        this.archivoNotasDebito = null;
    }

    public void ejecutarCargue() {
        loggerApp.info("Cargues masivos notas debito - ejecutar cargue");
        String respuesta = null;
        try {
            if (this.archivoNotasDebito != null) { // Se valida que se haya examinado un archivo
                if (validarExtension(Constantes.EXTENSIONES_ARCHIVO_EXCEL, archivoNotasDebito) == true) { // Se valida la extension del archivo
                    if ("".equals(generarListaData())) {
                        //generarListaData();
                        notasDebitoGeneralObjectContext.setAtributo(LISTA_NOTAS_DEBITO_MASIVOS, listaData);
                        this.servletHelper = new CargarNotasDebitoMasivosServletHelper(notasDebitoGeneralObjectContext, sessionLocal);
                        if (servletHelper != null) {
                            respuesta = servletHelper.obtenerDatos();
                        }
                        if (respuesta.trim().isEmpty()) {
                            abrirModal("SARA", "Solicitud del cargue masivo no procesada", null);
                        } else {
                            abrirModal("SARA", "Respuesta:" + respuesta, null);
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

            this.archivoNotasDebito = null;
        } catch (Exception ex) {
            abrirModal("SARA", "Error Ejecuntando cargue", ex);
        }
    }

    public String generarListaData() throws FileStructureException {

        String usuarioEnSesion = notasDebitoGeneralObjectContext.getUsuarioEnSesion().getUsuario();
        InputStream inputStream = null;
        CarmasNotasDebitoTemp notaDebito = null;
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        Iterator<Row> rowIterator = null;
        Row row = null;
        DataFormatter formatter = new DataFormatter();
        listaData.clear();
        try {
            if (this.archivoNotasDebito != null) {
                inputStream = this.archivoNotasDebito.getInputStream();
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
                                notaDebito = new CarmasNotasDebitoTemp();
                                notaDebito.setUsuario(usuarioEnSesion);
                                notaDebito.setCausal(row.getCell(0) == null ? "" : formatter.formatCellValue(row.getCell(0)).replaceAll("\\D+", ""));
                                notaDebito.setNroIdentificacionDeudor(row.getCell(1) == null ? "" : formatter.formatCellValue(row.getCell(1)).replaceAll("\\D+", ""));
                                notaDebito.setCodigoUnico(row.getCell(2) == null ? "" : formatter.formatCellValue(row.getCell(2)).replaceAll("\\D+", ""));
                                notaDebito.setNroCuenta(row.getCell(3) == null ? "" : formatter.formatCellValue(row.getCell(3)).replaceAll("\\D+", ""));
                                notaDebito.setTipoCuenta(formatter.formatCellValue(row.getCell(4)));
                                notaDebito.setValor(row.getCell(5) == null ? "" : formatter.formatCellValue(row.getCell(5)).replaceAll("\\D+", ""));
                                notaDebito.setTalon(row.getCell(6) == null ? "" : formatter.formatCellValue(row.getCell(6)).replaceAll("\\D+", ""));
                                notaDebito.setComision(row.getCell(7) == null ? "" : formatter.formatCellValue(row.getCell(7)).replaceAll("\\D+", ""));
                                notaDebito.setFecha(row.getCell(8) == null ? "" : formatter.formatCellValue(row.getCell(8)).replaceAll("\\D+", ""));
                                notaDebito.setOficinaRecuado(row.getCell(9) == null ? "" : formatter.formatCellValue(row.getCell(9)).replaceAll("\\D+", ""));
                                notaDebito.setErrorTransaccion(row.getCell(10) == null ? "" : formatter.formatCellValue(row.getCell(10)).replaceAll("\\^[a-zA-Z0-9]+$", ""));
                                notaDebito.setConcepto(row.getCell(11) == null ? "" : formatter.formatCellValue(row.getCell(11)).replaceAll("\\D+", ""));
                                notaDebito.setTipo(formatter.formatCellValue(row.getCell(12)));
                                listaData.add(notaDebito);
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
                    Logger.getLogger(CargarNotasDebitoMasivosBean.class.getName()).log(Level.SEVERE, null, ex);
                    inputStream = null;
                }
            }
        }
        return "";
    }

    private String validarEstructura(Row row) throws FileStructureException {
        String[] titulos = TituloCargasMasivasNotasDebito.tituloColumnas;
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

    public FileItem getArchivoNotasDebito() {
        return archivoNotasDebito;
    }

    public void setArchivoNotasDebito(FileItem archivoNotasDebito) {
        this.archivoNotasDebito = archivoNotasDebito;
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
