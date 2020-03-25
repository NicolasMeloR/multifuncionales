if(!dojo._hasResource["multifuncional.base.adminDiarioMultifuncional"]){ 
    dojo._hasResource["multifuncional.base.adminDiarioMultifuncional"] = true;

    /**
     * adminDiarioMultifuncional - 11/05/2011
     * Descripción : Script base para las administración de consultas de diarios electrónicos multifuncionales
     * Versión : 1.0 
     *
     * @author hlgomez
     * Davivienda 2011 
     */

    dojo.provide("multifuncional.base.adminDiarioMultifuncional");
    dojo.declare("multifuncional.base.adminDiarioMultifuncional",
    null,
    {        
        constructor: function(args, nodo) {
            console.debug("se inicia multifuncional.base.adminDiarioMultifuncional", args);
            dojo.mixin(this, args);            
            if (this.idFormaCriterios==""){
                this.idFormaCriterios = this.idForma + ".criterios";
            }                
            this.idForma = this.idForma + ".det";     
            this.widgetCajero = dijit.byId(this.idFormaCriterios + ".codigoCajero");
            this._accion = dijit.byId(this.idFormaCriterios+".accion");
            this._accionDiarioMultifuncional = dijit.byId(this.idFormaCriterios + ".accionDiarioMultifuncional");
        },
        
        urlAdmin : "",
        _accion : null,
        _accionDiarioElectronico : null,
        tiempoEspera : 180000,
        idFormaCriterios : "",
        idForma : "",
        widgetCajero : null,
        error : "",        
        nombreLog : "",
        
        
        
        setCodigoCajero : function(codigoCajero){
            dijit.byId(this.idFormaCriterios + ".codigoCajero").setValue(codigoCajero);
        },
        

        consultar : function (){
            this.setAccion("consultar");
            this.hacerRequerimiento();          
        },
           consultar2 : function (){
            this.setAccion("consultar");
            this.hacerRequerimiento();          
        },
                
//        
//        generarXLS : function (){
//            this.setAccion("generarXLS");
//            dojo.io.iframe.send({
//                url: this.urlAdmin,
//                form:dojo.byId(this.idFormaCriterios),
//                timeout: 3000,
//                preventCache:true,
//                mimetype:"application/octet-stream",
//                handle: function(data,ioa){
//                    setBarraTituloProceso("normal",tituloApp);            
//                    setAlertaPopUp(" ","Se ha enviado la solicitud puede cerrar esta ventana y continuar con las consultas ...");                        
//                }
//            });
//        },

          generarXLS : function (){
            this.setAccion("generarXLS");
            setAlertaPopUp(" ","Se ha enviado la solicitud puede cerrar esta ventana y continuar con las consultas ...");                        
            dojo.io.iframe.send({
                url: this.urlAdmin,
                form:dojo.byId(this.idFormaCriterios),
                timeout:3000,
                preventCache:true,
                mimetype:"application/octet-stream",
                handle: function(data,ioa){
                    setBarraTituloProceso("normal",tituloApp);                                
                }
            });
        },
        
        
        setAccion:function(accion){
            this._accion.setValue(accion);                
        },
        
        setAccionDiarioMultifuncional : function (accion) {
            this._accionDiarioMultifuncional.setValue(accion);
        },
        
        
        // Funcion para asignar datos de despliegue de objetos espesificos
        presentarDatos:function(){
            console.debug("sara.base.adminDiarioElectronico.presentarDatos");  
            dojo.byId(this.idFormaCriterios).style.display = "none";        
        },
        
        
        // Toma los valoressetValoresForma:function dados en el parámetro y los asigna a la forma de despliegue general
        setValoresForma:function (valoresJSon){
            console.debug("sara.base.adminDiarioElectronico.setValoresForma.valoresJSon",valoresJSon);
            //this.widgetForma.setValues(valoresJSon);
            this.presentarDatos();            
        },
        
         cargarPagina : function(){
            console.debug("paginaUrl",this.urlAdmin);
            cargarPaginaPpal(this.urlAdmin);
          
        },
                        
        
        
        // Prepara datos de la forma antes del envío de un requeriminto
        preRequerimiento : function(){
            setBarraTituloProceso("procesar",this.tituloProceso);
            this._accionDiarioMUltifuncional.setValue(this.accionDiarioMultifuncional);
        },
        
        // realiza procesos con la información respuesta de un requerimiento
        postRequerimiento :function (respuestaJSon) {
            setBarraTituloProceso("normal",tituloApp);            
            if (this.error == "") {
                this.setValoresForma(respuestaJSon);   
            } else {
               // setAlertaPopUp("No se puede realizar la solicitud",respuestaJSon);     
                setAlertaPopUp("Mensaje",respuestaJSon);  
               
            }
        },
        
                
        // Tratamiento de la respuesta del servidor por bien         
        rqItem : function(response, ioArgs){
            this.error = "";
            var mensaje = "";
            console.debug("hacerRequerimiento[load] item :", response);
            if (typeof(response.error) != "undefined" && response.error != "0000" ) {
                this.error = response.mensaje;
                mensaje = response.mensaje;
            } else {
                if (typeof(response.error) == "undefined" && typeof(response.mensaje) != "undefined"){
                    this.error = response.mensaje;
                    mensaje = response.mensaje;
                } else {
                    mensaje = response;
                }
            }
            this.postRequerimiento(mensaje);                                
        },
        
        
        // Tratamiento de la respuesta del servidor por error 
        rqError : function(response, ioArgs){
            var mensaje = "";
            this.error = "";
            if (response.dojoType=="cancel")
                mensaje="Respuesta cancelada."
            else if (response.dojoType=="timeout")
                mensaje="Respuesta fuera de tiempo. Intente luego."
            else
                mensaje=response;        
            console.debug("hacerRequerimiento[error] :", mensaje);
            this.error = mensaje;
            this.postRequerimiento(mensaje);
        },

        // Realiza un requerimiento al servidor para traer los datos de registro solicitado
        hacerRequerimiento : function(){            
            this.preRequerimiento();            
            dojo.xhrPost({
                url: this.urlAdmin,
                form:dojo.byId(this.idFormaCriterios),
                handleAs: "json",
                timeout: this.tiempoEspera,
                preventCache:true,
                mimetype:"application/json",
                load: dojo.hitch(this,"rqItem"), 
                error: dojo.hitch(this,"rqError")
            });
                },
           hacerRequerimientoGet : function(){            
            this.preRequerimiento();            
            dojo.xhrGet({
                url: this.urlAdmin,
                form:dojo.byId(this.idFormaCriterios),
                handleAs: "text",
                timeout: this.tiempoEspera,
                preventCache:true,
                mimetype: "text/plain",
                load: dojo.hitch(this,"rqItemGet"),
                error: dojo.hitch(this,"rqError")
             
            });
                },
            rqItemGet : function(response, ioArgs){

            this.error = "";
            var mensaje = "";
            console.debug("hacerRequerimiento[load] item :", response);
            if (typeof(response.error) != "undefined" && response.error != "0000" ) {
                this.error = response.mensaje;
                mensaje = response.mensaje;
            } else {
                if (typeof(response.error) == "undefined" && typeof(response.mensaje) != "undefined"){
                    this.error = response.mensaje;
                    mensaje = response.mensaje;
                } else {
                    mensaje = response;
                }
            }
            setContenidoPpal(response);   
           
                                        
        },
                    
     
           
        

           subirArchivo: function(){                 
        
            this.preRequerimiento(); 
               
              
           dojo.io.iframe.send({
                url: this.urlAdmin,
                form:dojo.byId(this.idFormaCriterios).toString(),
                handleAs: "json",
                timeout: this.tiempoEspera,
                preventCache:true,
                mimetype:"application/json",
                handle: function(data,ioa){
                setBarraTituloProceso("normal",tituloApp);            
                setAlertaPopUp(" ","Se ha enviado El archivo puede cerrar esta ventana" + this.urlAdmin);                        
                }
              
            });
        }            
        

        
        
        
    });

}


