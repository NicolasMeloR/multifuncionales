package com.davivienda.sara.bean.page.multifuncional.diarioelectronico;

import com.davivienda.multifuncional.tablas.logcajeromulti.session.LogCajeroMultiSessionLocal;
import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.diarioelectronico.general.formato.TituloDiarioElectronicoGeneral;
import com.davivienda.sara.dto.CajeroDTO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.TiraAuditoria;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.utilidades.Constantes;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import com.davivienda.sara.tablas.TiraAuditoria.session.TiraAuditoriaSessionLocal;
import com.davivienda.utilidades.archivoxls.ArchivoXLS;
import com.davivienda.utilidades.archivoxls.Celda;
import com.davivienda.utilidades.archivoxls.ProcesadorArchivoXLS;
import com.davivienda.utilidades.archivoxls.Registro;
import com.davivienda.utilidades.archivoxls.TipoDatoCelda;
import com.davivienda.utilidades.conversion.JSon;
import java.io.IOException;
import java.util.logging.Level;


@ManagedBean(name = "diariokalMFBean")
@ViewScoped
public class DiariokalMFBean extends BaseBean implements Serializable {

    @EJB
    CajeroSessionLocal cajeroSession;

    @EJB
    LogCajeroMultiSessionLocal logSession;
    
    @EJB
    TiraAuditoriaSessionLocal tiraSession;

    public ComponenteAjaxObjectContextWeb objectContext;
    private Integer cajeroSeleccionado;/***/
    private Date fechaInicial;
    private Date fechaFinal;
    private String fechaActual;
    private String horaInicial;
    private String horaFinal;
    public Date fechaInicialReporte = null;

    private boolean mostrarPanelGeneral;/***/
    

    private List<SelectItem> listaHora;/***/
    private String fechaDesde;/***/
    private String horaDesde;/***/
    private String fechaHasta;/***/
    private String horaHasta;/***/

    private List<CajeroDTO> listaCajeros;/***/
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    public void TransaccionMFBean() {
        objectContext = cargarComponenteAjaxObjectContext();
        listaHora = new ArrayList<>();
        if (objectContext != null) {
            dataInicial();
        }
    }

    public void dataInicial() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.setFechaActual(df.format(new Date()));
        this.fechaDesde = "";
        this.horaDesde = "00:00:00";
        this.fechaHasta = "";
        this.horaHasta = "23:59:59";
        this.listaHora = cargarListaHora();
        cargarListaCajeros();
        this.setMostrarPanelGeneral(true);
    }

    private void cargarListaCajeros() {
        Collection<Cajero> items = null;
        try {
            items = cajeroSession.getTodosActivosMulti(0, 5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        listaCajeros = cargarListaCajeros(objectContext.getCajeroCB(items));
    }

    private List<CajeroDTO> cargarListaCajeros(List<CajeroDTO> cajeroDTO) {
        List<CajeroDTO> lista = new ArrayList<>();
        int cont = 1;
        for (CajeroDTO dto : cajeroDTO) {
            dto.setIdCajero(cont);
            lista.add(dto);
            cont++;
        }
        return lista;
    }

    public String generarReporteSinTrasmision() {
        String respuesta = "";
        List<TiraAuditoria> regs=null;
        String cajeroNombre="";
        try{
            try {
                fechaInicial = formatter.parse(this.fechaDesde);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }
            

            try {
                fechaFinal = formatter.parse(this.fechaHasta);
            } catch (IllegalArgumentException | ParseException ex) {
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return "";
            }
            
            if(fechaInicial.after(new Date()) || fechaFinal.after(new Date())){
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA_ERRONEA, null);
                return "";
            }
            
            if(fechaInicial.after(fechaFinal)){
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA_MAYOR, null);
                return "";
            }
            
            if (null == cajeroSeleccionado) {
                cajeroNombre="ATMs";
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaInicial);
                cal.add(Calendar.DATE, 3);
                Date fecha = cal.getTime();
                if (fechaFinal.before(fecha)){
                    cal.setTime(fechaFinal);
                    cal.add(Calendar.DATE, 1);
                    fechaFinal=cal.getTime();
                    try{
                        regs = tiraSession.getTira(-1, fechaInicial, fechaFinal);
                    }catch(Exception ex){}
                }
                else{
                   abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA_RANGO_MAYOR, null);
                   return "";
                }
            }
            if(cajeroSeleccionado != null){
                cajeroNombre="COD"+cajeroSeleccionado.toString();
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaInicial);
                cal.add(Calendar.DATE, 30);
                Date fecha = cal.getTime();
                if (fechaFinal.before(fecha)){
                    cal.setTime(fechaFinal);
                    cal.add(Calendar.DATE, 1);
                    fechaFinal=cal.getTime();
                    try{
                        regs = tiraSession.getTira(cajeroSeleccionado, fechaInicial, fechaFinal);
                    }catch(Exception ex){}
                }
                else{
                   abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA_RANGO_MAYOR2, null);
                   return "";
                }   
            }
        
            
            if (regs != null) {
                //Creo la hoja de cálculo
                String[] tituloHoja;
                tituloHoja = new String[3];
                tituloHoja[0] = "Tira Auditoria";
                tituloHoja[1] = this.fechaDesde;
                tituloHoja[2] = this.fechaHasta;
                String[] titulosHoja = tituloHoja;
                String[] titulosColumna = TituloDiarioElectronicoGeneral.tituloColumnasTira;

                Collection<Registro> lineas = new ArrayList<>();
                Short numColumna;
                try {
                    for (TiraAuditoria item : regs) {
                        Registro reg = new Registro();
                        numColumna = 0;
                        reg.addCelda(new Celda(numColumna++, item.getCodigobanco(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getIdzona(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getIdocca(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getIdcajero(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getTipomaquina(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getTiporegistro(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getNumerotransaccion(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getIdtransaccion(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getTipotransaccion(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getDesctransaccion(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getFecharealtransaccion(), TipoDatoCelda.FECHA));
                        reg.addCelda(new Celda(numColumna++, item.getFechacontabletransaccion(), TipoDatoCelda.FECHA));
                        reg.addCelda(new Celda(numColumna++, item.getNumerotarjeta(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getNumeroproducto(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getPtoductorigen(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getMontotransaccionretiro(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getMontotxretiroentregado(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getValordonacion(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getCostotransaccion(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getVueltasdescripcion(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getMontotxdeposito(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getMontotxdepositorecibido(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getReferencia1(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getReferencia2(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getReferencia3(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getPtoductodestino(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getNumeroproductodestino(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getNumeroaprobacion(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getCodigoerrorhost(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getDescripcionerrorhost(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getCodigoerroratm(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getDescripcionerroratm(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getEstadoimpresion(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getLogaudita(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getCantidad(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getValor(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getCantidad1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getValor1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getCantidad2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getValor2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getCantidad3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getValor3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getCantidad4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getValor4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getCantidad5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getValor5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getCantidad6(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getValor6(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getCantidad7(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getValor7(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getCantidad8(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getValor8(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getDenominacionbilletes(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getCantbilletesprovision(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdispensados(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdispensados(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdepositados(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdepositados(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesremanentes(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesrechazados(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesrechazados(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesretrac(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesretract(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getDenominacionbilletes1(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getCantbilletesprovision1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdispensados1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdispensados1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdepositados1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdepositados1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesremanentes1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesrechazados1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesrechazados1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesretrac1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesretract1(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getDenominacionbilletes2(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getCantbilletesprovision2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdispensados2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdispensados2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdepositados2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdepositados2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesremanentes2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesrechazados2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesrechazados2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesretrac2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesretract2(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getDenominacionbilletes3(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getCantbilletesprovision3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdispensados3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdispensados3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdepositados3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdepositados3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesremanentes3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesrechazados3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesrechazados3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesretrac3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesretract3(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getDenominacionbilletes4(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getCantbilletesprovision4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdispensados4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdispensados4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdepositados4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdepositados4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesremanentes4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesrechazados4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesrechazados4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesretrac4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesretract4(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getDenominacionbilletes5(), TipoDatoCelda.NORMAL));
                        reg.addCelda(new Celda(numColumna++, item.getCantbilletesprovision5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdispensados5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdispensados5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesdepositados5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesdepositados5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesremanentes5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesrechazados5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesrechazados5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getBilletesretrac5(), TipoDatoCelda.NUMERICO));
                        reg.addCelda(new Celda(numColumna++, item.getAcumbilletesretract5(), TipoDatoCelda.NUMERICO));
                        lineas.add(reg);
                    }
                    ArchivoXLS archivo = ProcesadorArchivoXLS.crearLibro("TiraAuditoria"+cajeroNombre+this.fechaDesde, titulosHoja, titulosColumna, lineas);
                    objectContext.enviarArchivoXLS(archivo);
                    //respuesta = "Se ha enviado la solicitud puede cerrar esta ventana y continuar con las consultas ...";
                } catch (IllegalArgumentException ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.PARAMETRO_INVALIDO.getCodigo(), ex.getMessage());
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_QUERY_ERROR);
                    //abrirModal("SARA", respuestaJSon, ex);
                } catch (IOException ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), ex.getMessage());
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ARCHIVO_NO_EXISTE.getCodigo(), Constantes.MSJ_ERROR_CREAR_ARCHIVO);
                    //abrirModal("SARA", respuestaJSon, ex);
                } catch (Exception ex) {
                    objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_NO_DEFINIDO.getCodigo(), ex.getMessage());
                    //respuestaJSon = JSon.getJSonRespuesta(CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo(), Constantes.MSJ_ERROR_INTERNO);
                    //abrirModal("SARA", respuestaJSon, ex);
                }

            } else {
                //respuesta = this.respuestaJSon;
                //abrirModal("SARA", respuesta, null);
            }
        }    
        catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
            return null;
        }
        return respuesta;
    }
   
    private List<SelectItem> cargarListaHora() {
        List<SelectItem> lista = new ArrayList<>();
        boolean iniciar = true;
        int mn = 0;
        int hr = 0;
        while (iniciar) {
            SelectItem item = null;
            if (hr < 24) {
                if (mn == 0) {
                    if (hr < 10) {
                        item = new SelectItem("0" + hr + ":00:00", "0" + hr + ":00:00");
                    } else {
                        item = new SelectItem(+hr + ":00:00", +hr + ":00:00");
                    }
                    mn += 15;
                } else if (mn < 60) {
                    if (hr < 10) {
                        item = new SelectItem("0" + hr + ":" + mn + ":00", "0" + hr + ":" + mn + ":00");
                    } else {
                        item = new SelectItem(+hr + ":" + mn + ":00", +hr + ":" + mn + ":00");
                    }
                    mn += 15;
                } else {
                    hr++;
                    mn = 0;
                    if (hr < 24) {
                        if (hr < 10) {
                            item = new SelectItem("0" + hr + ":00:00", "0" + hr + ":00:00");
                        } else {
                            item = new SelectItem(+hr + ":00:00", +hr + ":00:00");
                        }
                    }
                    mn += 15;

                }
                if (item != null) {
                    lista.add(item);
                }

            } else {
                iniciar = false;
            }
        }
        return lista;
    }

   
    public Integer getCajeroSeleccionado() {
        return cajeroSeleccionado;
    }

    public void setCajeroSeleccionado(Integer cajeroSeleccionado) {
        this.cajeroSeleccionado = cajeroSeleccionado;
    }

    /**
     * @return the fechaInicial
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param fechaInicial the fechaInicial to set
     */
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the horaInicial
     */
    public String getHoraInicial() {
        return horaInicial;
    }

    /**
     * @param horaInicial the horaInicial to set
     */
    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    /**
     * @return the horaFinal
     */
    public String getHoraFinal() {
        return horaFinal;
    }

    /**
     * @param horaFinal the horaFinal to set
     */
    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

   
    /**
     * @return the mostrarPanelGeneral
     */
    public boolean isMostrarPanelGeneral() {
        return mostrarPanelGeneral;
    }

    /**
     * @param mostrarPanelGeneral the mostrarPanelGeneral to set
     */
    public void setMostrarPanelGeneral(boolean mostrarPanelGeneral) {
        this.mostrarPanelGeneral = mostrarPanelGeneral;
    }


    public List<SelectItem> getListaHora() {
        return listaHora;
    }

    public void setListaHora(List<SelectItem> listaHora) {
        this.listaHora = listaHora;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getHoraDesde() {
        return horaDesde;
    }

    public void setHoraDesde(String horaDesde) {
        this.horaDesde = horaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getHoraHasta() {
        return horaHasta;
    }

    public void setHoraHasta(String horaHasta) {
        this.horaHasta = horaHasta;
    }

    public List<CajeroDTO> getListaCajeros() {
        return listaCajeros;
    }

    public void setListaCajeros(List<CajeroDTO> listaCajeros) {
        this.listaCajeros = listaCajeros;
    }

    /**
     * @return the fechaActual
     */
    public String getFechaActual() {
        return fechaActual;
    }

    /**
     * @param fechaActual the fechaActual to set
     */
    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }


}
