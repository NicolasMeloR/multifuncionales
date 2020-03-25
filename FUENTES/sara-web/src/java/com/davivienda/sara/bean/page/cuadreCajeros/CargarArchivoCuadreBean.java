/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davivienda.sara.bean.page.cuadreCajeros;

import com.davivienda.sara.base.ComponenteAjaxObjectContextWeb;
import com.davivienda.sara.base.ProcesosArchivoObjectContextWeb;
import com.davivienda.sara.base.exception.EntityServicioExcepcion;
import com.davivienda.sara.bean.BaseBean;
import com.davivienda.sara.diarioelectronico.general.ProcesosArchivoHelperInterface;
import com.davivienda.sara.bean.InitBean;
import com.davivienda.sara.constantes.CodigoError;
import com.davivienda.sara.constantes.EstadoProceso;
import com.davivienda.sara.cuadrecifras.session.CuadreCifrasCargasSessionLocal;
import com.davivienda.sara.diarioelectronico.general.helper.ProcesosArchivoCargarArchivoServletHelper;
import com.davivienda.sara.entitys.Cajero;
import com.davivienda.sara.entitys.Regcarguearchivo;
import com.davivienda.sara.entitys.config.ConfModulosAplicacion;
import com.davivienda.sara.procesos.cuadrecifras.session.ProcesoCuadreCifrasSessionLocal;
import com.davivienda.sara.procesos.diarioelectronico.session.AdministradorProcesosSessionLocal;
import com.davivienda.sara.procesos.edccargue.session.AdministradorProcesosEdcCargueSessionLocal;
import com.davivienda.sara.tablas.cajero.session.CajeroSessionLocal;
import com.davivienda.sara.tablas.confmodulosaplicacion.session.ConfModulosAplicacionLocal;
import com.davivienda.sara.tablas.regcargue.session.RegCargueArchivoSessionLocal;
import com.davivienda.sara.tareas.regcargue.session.AdminTareasRegCargueArchivoSessionLocal;
import com.davivienda.utilidades.Constantes;
import com.davivienda.utilidades.conversion.JSon;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jmcastel
 */
@ManagedBean(name = "cargarArchivoCuandreBean")
@ViewScoped
public class CargarArchivoCuadreBean extends BaseBean implements Serializable {
    
    @EJB
    CajeroSessionLocal cajeroSession;
    
    @EJB
    AdministradorProcesosSessionLocal administradorProcesos;
    
    @EJB
    AdministradorProcesosEdcCargueSessionLocal administradorProcesosEdcCargueSessionLocal;
    
    @EJB
    ConfModulosAplicacionLocal confModulosAplicacionSession;
    
    @EJB
    CuadreCifrasCargasSessionLocal  cuadreCifrasCargasSessionLocal; 
    
    @EJB
    ProcesoCuadreCifrasSessionLocal session;
    
    @EJB
    AdminTareasRegCargueArchivoSessionLocal adminTareasRegCargueArchivoSession;
    
    @EJB
    RegCargueArchivoSessionLocal regCargueArchivoSessionLocal;
    
    private String fechaCargue;
    private String horaCargue;
    private List<SelectItem> listaHora;
    public ComponenteAjaxObjectContextWeb objectContext;
    
    private ProcesosArchivoObjectContextWeb procesosArchivoObjectContext;
    
    @PostConstruct
    public void CargarArchivoCuadreBean() {
        try {
            objectContext = cargarComponenteAjaxObjectContext();
            procesosArchivoObjectContext = new ProcesosArchivoObjectContextWeb(getRequestFaces(), getResponseFaces());
            //listaRegistros = new ArrayList<CajeroDTO>();
            listaHora = new ArrayList<SelectItem>();
            if (objectContext != null) {
                Collection<Cajero> items = cajeroSession.getTodos();
                //listaRegistros = cargarListaRegistros(objectContext.getCajeroSinFiltroCB(items));
                listaHora = cargarListaHora();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private List<SelectItem> cargarListaHora() {
        List<SelectItem> lista = new ArrayList<SelectItem>();
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
    
    public void cargarCicloDiario() {
        procesosArchivoObjectContext.getConfigApp().loggerAcceso.info("CargarArchivoBean-cargarCicloDiario");
        String respuesta = "";
        try {
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date fechaI = formatter.parse(this.fechaCargue);
                Calendar fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaI);
            } catch (Exception ex) {
                //fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendarAyer();
                abrirModal("SARA", Constantes.MSJ_SELECCIONAR_FECHA, null);
                return;
            }
            
            String directorioUpload = "";
            directorioUpload = procesosArchivoObjectContext.getConfigApp().DIRECTORIO_UPLOAD;
            ProcesosArchivoHelperInterface procesosArchivoHelper = null;

            if (procesosArchivoObjectContext.getConfigApp().USUARIO_QUARTZ.equals(procesosArchivoObjectContext.getAtributoString("usuario"))) {
                procesosArchivoObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log CARGAR_ARCHIVO por  USUARIO_QUARTZ " + procesosArchivoObjectContext.getConfigApp().USUARIO_QUARTZ + " desde " + procesosArchivoObjectContext.getdireccionIP());
            } else {
                procesosArchivoObjectContext.getConfigApp().loggerAcceso.info("Solicitud generar log CARGAR_ARCHIVO por  " + objectContext.getIdUsuarioEnSesion() + " desde " + procesosArchivoObjectContext.getdireccionIP());
            }
            procesosArchivoObjectContext.getConfigApp().loggerAcceso.info("CargarArchivoBean-cargarCicloDiario: Fecha: " + this.fechaCargue + " Hora: "+ this.horaCargue);
            procesosArchivoObjectContext.setAtributoFechaInicial(this.fechaCargue);
            respuesta = this.obtenerDatos();
            abrirModal("SARA", respuesta, null);  
            
  
        } catch (Exception ex) {
            objectContext.getConfigApp().loggerApp.log(Level.SEVERE, ex.getMessage(), ex);
            abrirModal("SARA", Constantes.MSJ_ERROR_INTERNO, null);
        }
        
    }
    
    public void inicializar(ProcesoCuadreCifrasSessionLocal session,CuadreCifrasCargasSessionLocal  cuadreCifrasCargasSessionLocal,ConfModulosAplicacionLocal confModulosAplicacionSession,AdminTareasRegCargueArchivoSessionLocal adminTareasRegCargueArchivoSession,RegCargueArchivoSessionLocal regCargueArchivoSessionLocal) {
        this.session = session;
        this.objectContext = objectContext;
        this.cuadreCifrasCargasSessionLocal=cuadreCifrasCargasSessionLocal;
        this.confModulosAplicacionSession=confModulosAplicacionSession;
        this.adminTareasRegCargueArchivoSession=adminTareasRegCargueArchivoSession;
        this.regCargueArchivoSessionLocal=regCargueArchivoSessionLocal;
    } 
     public String obtenerDatos() {
        String respuesta = "";
        int intRegProcesadosCorte = -1;
        int intRegProcesadosTotal = -1;
        int intRegProcesadosProvision = -1;
        int intRegCuadreCifras = -1;
        String strExepcion="";
        boolean cargueautomatico=false;
        String strArchivoCarga="";
        Calendar fechaInicial = null;
        Calendar fechaProceso = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            try {
                fechaInicial =  com.davivienda.utilidades.conversion.Fecha.getCalendar(formatter.parse(this.fechaCargue));
            } catch (IllegalArgumentException ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
                cargueautomatico=true;
            } catch (ParseException ex) {
                fechaInicial = com.davivienda.utilidades.conversion.Fecha.getCalendar(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
                cargueautomatico=true;
            }
         
            try {
                // Consulta los registros según los parámetros tomados del request
                
            if(ConsultarEstadoCargue().equals("0")){
                      
            CambiarEstadoCargue("1");
     
            strArchivoCarga=  "cfamo001";       
            guardarRegistroTxArchivo(strArchivoCarga,cargueautomatico,fechaInicial,"Cuadre");         
            intRegProcesadosCorte=cuadreCifrasCargasSessionLocal.cargarArchivoCorte( fechaInicial,cargueautomatico);
            actualizarRegCargueArchivo(strArchivoCarga,cargueautomatico,fechaInicial,"",intRegProcesadosCorte); 
            
            strArchivoCarga=  "geato002";       
            guardarRegistroTxArchivo(strArchivoCarga,cargueautomatico,fechaInicial,"Cuadre");      
            intRegProcesadosTotal=cuadreCifrasCargasSessionLocal.cargarArchivoTotalesEgresos( fechaInicial,cargueautomatico);
            actualizarRegCargueArchivo(strArchivoCarga,cargueautomatico,fechaInicial,"",intRegProcesadosTotal);
            
            strArchivoCarga=  "otbmo001";       
            guardarRegistroTxArchivo(strArchivoCarga,cargueautomatico,fechaInicial,"Cuadre");   
            intRegProcesadosProvision= cuadreCifrasCargasSessionLocal.cargarArchivoProvisiones( fechaInicial,cargueautomatico);
            actualizarRegCargueArchivo(strArchivoCarga,cargueautomatico,fechaInicial,"",intRegProcesadosProvision);
            fechaProceso=com.davivienda.utilidades.conversion.Fecha.getCalendar(fechaInicial,-1);
            guardarRegistroTxArchivo("CuadreCifrasDispensador",cargueautomatico,fechaInicial,"Cuadre");   
            intRegCuadreCifras=session.procesarCuadreCifras(fechaProceso);
            actualizarRegCargueArchivo("CuadreCifrasDispensador",cargueautomatico,fechaInicial,"",intRegCuadreCifras);
            
            //  administradorProcesosEdcCargueSessionLocal.registrarCicloEdcCargue(lstArchivos,nombreArchivo,com.davivienda.utilidades.conversion.Fecha.getCalendarHoy());
                  
                   
                   
                   if (intRegProcesadosCorte >= 0)
                   {
                       
                       
                       
                     respuesta = "Se Actualizaron con exito en la bd : " + intRegProcesadosCorte + " Registros de Cuadre diario ";
                     respuesta =respuesta + " " + intRegProcesadosTotal + " Registros de Totales Egresos ";
                     respuesta =respuesta + " " + intRegProcesadosProvision + " Registros de Provisiones ";
                   }
                 }   

            }
            
            catch (EJBException ex) 
            {
                
               
                if (ex.getMessage() == null )
                {
                strExepcion=ex.getCause().getMessage();
                }
                else
                {
                 strExepcion=ex.getMessage();
                }
                 
                objectContext.getConfigApp().loggerApp.log(Level.SEVERE,strExepcion); 
                objectContext.setError( strExepcion,CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                respuesta  = "Error consultando Entitys";
                
               
            }
            catch (Exception ex) {
                strExepcion=ex.getMessage();
                objectContext.setError( ex.getMessage(),CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
                 respuesta  = "Error consultando Entitys";
            }

        } catch (IllegalArgumentException ex) {
            strExepcion=ex.getMessage();
            objectContext.setError( ex.getMessage(),CodigoError.ERROR_CONSULTANDO_ENTITYS.getCodigo());
             respuesta  = "Error consultando Entitys";
        }
        finally
        {
        
                 CambiarEstadoCargue("0");
              if(!strExepcion.equals("")|| intRegProcesadosProvision==-1)
              {     
                    try 
                    {
                            strExepcion="Error al grabar archivo no se pudo grabar el archivo o no existe "+strExepcion;
                            respuesta  = "Error al grabar archivo no se pudo grabar el archivo o no existe";
                            actualizarRegCargueArchivo(strArchivoCarga, cargueautomatico, fechaInicial, strExepcion, 0);
                    } catch (EntityServicioExcepcion ex) {
                         respuesta  = "Error al grabar archivo no se pudo grabar el archivo o no existe";
                       
                    }
              }      
        
        }
      return respuesta;      
    }
     
    private void CambiarEstadoCargue(String strEstado)
    {
       
        try {
//         ConfModulosAplicacion registroEntity =em.find(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
//                  // new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
//         registroEntity.setValor(strEstado);
//         em.persist(registroEntity);
//         em.flush();
         
         ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
         registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
            
         //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
         ConfModulosAplicacion registroEntity =new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
                  // new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGADIARIO");
         registroEntity.setValor(strEstado);
         registroEntity.setDescripcion(registroEntityConsulta.getDescripcion());
//       utx.begin();
         confModulosAplicacionSession.actualizar(registroEntity);
        
         } catch (Exception ex) {
                abrirModal("SARA", "Error cambiando estado cargue", ex);  
         }
      
    
    }
    
    
    private String ConsultarEstadoCargue()
    {
        String strEstadoCargue = "0";
       
        try {
             ConfModulosAplicacion registroEntityConsulta = new ConfModulosAplicacion("SARA", "SARA.ESTADOCARGACUADRE");
            registroEntityConsulta = confModulosAplicacionSession.buscar(registroEntityConsulta.getConfModulosAplicacionPK());
         //ConfModulosAplicacion registroEntityConsulta=confModulosAplicacionSession.buscar(ConfModulosAplicacion.class, new ConfModulosAplicacionPK("SARA", "SARA.ESTADOCARGADIARIO"));
         strEstadoCargue=registroEntityConsulta.getValor();
        
         } catch (Exception ex) {
                abrirModal("SARA", "Error obteniendo estado cargue ", ex);  
                 strEstadoCargue = "0";
         }
        return strEstadoCargue;
    
    }
    
    private void guardarRegistroTxArchivo(String archivoTarea,boolean IndAuto,Calendar fechaTarea ,String tarea )
    {
       
             Date fechaCarga = new Date();
             String strFechaTarea="";
            
               try 
               {
                 
                
                    fechaCarga=com.davivienda.utilidades.conversion.Fecha.getDateHoy();
                    strFechaTarea=com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");
                    adminTareasRegCargueArchivoSession.guardarRegCargueArchivo(archivoTarea, IndAuto, strFechaTarea, tarea, fechaCarga,"SARA",false,"") ;      
                    
                }
                catch (IllegalArgumentException ex) {
                  java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en RegCargueArchivo para el archivo " + archivoTarea+fechaTarea + " " + ex.getMessage());
                }
                catch (Exception ex){
                   java.util.logging.Logger.getLogger("globalApp").info("Error al grabar los datos en RegCargueArchivo  para el archivo :" + archivoTarea+fechaTarea + " descripcion Error : " + ex.getMessage());            
                }
             
         
         
       }
    
     private void actualizarRegCargueArchivo(String archivoTarea,boolean IndAuto,Calendar fechaTarea ,String msgError,Integer numRegistros ) throws EntityServicioExcepcion 
     {
          
         String strFechaTarea="";
         Long lngNumRegistros=new Long(0);
         
         lngNumRegistros=com.davivienda.utilidades.conversion.Cadena.aLong(Integer.toString(numRegistros)) ;
         strFechaTarea=com.davivienda.utilidades.conversion.Fecha.aCadena(fechaTarea, "yyMMdd");
         
         Regcarguearchivo edcCargue=adminTareasRegCargueArchivoSession.buscarPorArchivoFecha(archivoTarea, strFechaTarea, IndAuto) ;
         if (edcCargue!=null)
         {
                if(msgError.equals(""))
                {
                    edcCargue.setEstadocarga(EstadoProceso.FINALIZADO.getEstado());
                    edcCargue.setNumregistros(lngNumRegistros) ;
                }
                else
                {
                    edcCargue.setEstadocarga(EstadoProceso.ERROR.getEstado());
                }    
                edcCargue.setDescripcionerror(msgError);
                edcCargue.setFechafinal(com.davivienda.utilidades.conversion.Fecha.getDateHoy());
                regCargueArchivoSessionLocal.actualizar(edcCargue);   
             
        }
          

    }
    
    public HttpServletRequest getRequestFaces() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request;
    }

    public HttpServletResponse getResponseFaces() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        return response;
    }

   

    /**
     * @return the listaHora
     */
    public List<SelectItem> getListaHora() {
        return listaHora;
    }

    /**
     * @param listaHora the listaHora to set
     */
    public void setListaHora(List<SelectItem> listaHora) {
        this.listaHora = listaHora;
    }

    /**
     * @return the fechaCargue
     */
    public String getFechaCargue() {
        return fechaCargue;
    }

    /**
     * @param fechaCargue the fechaCargue to set
     */
    public void setFechaCargue(String fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    /**
     * @return the horaCargue
     */
    public String getHoraCargue() {
        return horaCargue;
    }

    /**
     * @param horaCargue the horaCargue to set
     */
    public void setHoraCargue(String horaCargue) {
        this.horaCargue = horaCargue;
    }
    
}
