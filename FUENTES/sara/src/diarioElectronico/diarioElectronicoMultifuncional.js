if(!dojo._hasResource["multifuncional.diarioElectronico.diarioElectronicoMultifuncional"]){ 
    dojo._hasResource["multifuncional.diarioElectronico.diarioElectronicoMultifuncional"] = true;
    /**
     * logCanal - 11/05/2011
     * Descripción : 
     * Versión : 1.0 
     *
     * @author hlgomez
     * Davivienda 2011 
     */
    dojo.require("multifuncional.base.adminDiarioMultifuncional");
    dojo.provide("multifuncional.diarioElectronico.diarioElectronicoMultifuncional");
    dojo.declare("multifuncional.diarioElectronico.diarioElectronicoMultifuncional",
//    [sara.base.adminDiarioElectronico],
    [multifuncional.diarioElectronico.adminDiarioMultifuncional],
    {
        constructor: function(args){
            console.debug("se inicia multifuncional.diarioElectronico.diarioElectronicoMultifuncional", args);
        },
        
        _datos :{},
        _origenDatos : null,
        _objGrid : null,
        //        _menu : null,
                        
        
        // Modificado HLGomez
        _estructuraGridTransaccionalMumtifuncionalEfectivo : [
            [
                { name: "Código Transacción", field: "codigoTransaccion", width: "20px" },                        
                { name: "Estado", field: "estado", width: "50px" },                        
                { name: "Código Cajero", field: "codigoCajero", width: "250px" },                        
                { name: "Número de corte", field: "numeroDeCorte", width: "130px" },
                { name: "Transacción consecutivo", field: "transaccionConsecutivo", width: "250px" },                                        
                { name: "Fecha Cierre", field: "fechaCierre", width: "150px" },
                { name: "Fecha Cajero", field: "fechaCajero", width: "150px" },
                { name: "Tipo Cuenta", field: "tipoCuenta", width: "150px" },
                { name: "Numero cuenta consignar", field: "numeroCuentaConsignar", width: "150px" },
                { name: "Numero cuenta homóloga", field: "numeroCuentaHomologa", width: "150px" },
                { name: "Valor Consignacion", field: "valorConsignacion", width: "150px" },
                { name: "NobilletesND", field: "noBbilletesND", width: "150px" },
                { name: "Nro Billetes denominación XXX", field: "noBilletesDenominacionF", width: "150px" },
                { name: "Nro Billetes denominación XXX", field: "noBilletesDenominacionE", width: "150px" },
                { name: "Nro Billetes denominación XXX", field: "noBilletesDenominacionD", width: "150px" },
                { name: "Nro Billetes denominación XXX", field: "noBilletesDenominacionC", width: "150px" },
                { name: "Nro Billetes denominación XXX", field: "noBilletesDenominacionB", width: "150px" },
                { name: "Nro Billetes denominación XXX", field: "noBilletesDenominacionA", width: "150px" },
                { name: "Total billetes consignados", field: "totalBilletesConsignados", width: "150px" },
            ]
        ],
        
        _estructuraGridTransaccionalMumtifuncionalCheque : [
            [
                { name: "Código Transacción", field: "codigoTransaccion", width: "20px" },                        
                { name: "Código Cajero", field: "codigoCajero", width: "250px" },                        
                { name: "Fecha Cajero", field: "fechaCajero", width: "150px" },
                { name: "Número de corte", field: "numeroDeCorte", width: "130px" },
                { name: "Consecutivo Transacción", field: "consecutivoTransaccion", width: "250px" },                                        
                { name: "Fecha Cierre", field: "fechaCierre", width: "150px" },
                { name: "Estado", field: "estado", width: "50px" },
                { name: "Consecutivo Cheque Consignaciono", field: "consecutivoChequeConsignacion", width: "50px" },
                { name: "Tipo Cuenta", field: "tipoCuenta", width: "150px" },
                { name: "Numero cuenta consignar", field: "numeroCuentaConsignar", width: "150px" },
                { name: "Numero cuenta homóloga", field: "numeroCuentaHomologa", width: "150px" },
                { name: "Cheque Propio", field: "chequePropio", width: "150px" },
                { name: "Ruta", field: "ruta", width: "150px" },
                { name: "Transito", field: "transito", width: "150px" },
                { name: "Cuenta", field: "cuenta", width: "150px" },
                { name: "Serial", field: "serial", width: "150px" },
                { name: "Oficina", field: "oficina", width: "150px" },
                { name: "Valor Cheque Usuario", field: "valorChequeUsuario", width: "150px" },
                { name: "idRegistroControl", width: "150px" },
                { name: "secuencia", width: "150px" },
                { name: "version", width: "150px" },
                { name: "plaza", field: "Plaza", width: "150px" },
                { name: "Fecha Archivo", field: "fechaArchivo", width: "150px" },
                { name: "Monto Archivo", field: "montoArchivo", width: "150px" },
                { name: "Cantidad Cheques", field: "cantidadCheques", width: "150px" },
            ]
        ],
        _estructuraGridConversionEDCCargueMultifuncional : [ 
            [
                { name: " # ", field: "idRegistro", width: "20px" },                        
                { name: "Cajero", field: "codigoCajero", width: "50px" },                        
                { name: "Ciclo", field: "ciclo", width: "80px" },                       
                { name: "Fecha", field: "fecha", width: "200px" },
                { name: "Estado proceso", field: "estadoProceso", width: "200px" },
                { name: "Error", field: "error", width: "320px" },
                { name: "Descripción Error", field: "descripcionError", width: "320px" },
                { name: "Tamaño Bytes", field: "tamanoBytes", width: "320px" },
                //PARA MANEJAR CELDAS EDITABLES
               // { name: "Select", field: "Prueba",width: "auto", styles: "text-align: center", type: 
               // dojox.grid.cells.Bool, editable: true }, 
                  
            ]
        ],
            _estructuraGridCajerosSinTransmitir : [
            [
                { name: " # ", field: "idRegistro", width: "20px" },                        
                { name: "Cajero", field: "codigoCajero", width: "50px" },                        
                { name: "Nombre Cajero", field: "nombreCajero", width: "160px" }
                // { name: "Select", field: "Prueba",width: "auto", styles: "text-align: center", type: 
                // dojox.grid.cells.Bool, editable: true }, 
                  
            ]
        ],
        
                
        // Toma los valores dados en el parámetro y los asigna a la forma de despliegue general
        setValoresForma:function (valoresJSon){
            console.debug("sara.diarioelectronico.generales.diarioElectronico.setValoresForma.valoresJSon",valoresJSon);
            if (this._objGrid != null) {
                this._objGrid.destroy();
                this._objGrid = null;
            }            
            if (this._origenDatos != null) {
                this._origenDatos = null;
            }
            this._datos = valoresJSon;
            this._origenDatos = new dojo.data.ItemFileWriteStore({ data: this._datos });
            this._origenDatos._forceLoad();            

            if (this._accionDiarioElectronico.getValue()  == "consultarTotalTransaccionPorFechaCajero") {
                this._objGrid = new dojox.grid.DataGrid({
                    id: "de.consulta.grid.diarioElectronicoGrid",
                    store: this._origenDatos,
                    structure: this._estructuraGridTotalTransaccionPorCajero,
                    rowSelector : "20px",
                    columnToggling : true,
                    //       headerMenu : this._menu,
                    class : "elmweb"
                });                
            } else {
                  if (this._accionDiarioElectronico.getValue()  == "consultarCajerosSnTransmitir") { 
                  this._objGrid = new dojox.grid.DataGrid({
                      id: "de.consulta.grid.diarioElectronicoGrid",
                      store: this._origenDatos,
                      structure: this._estructuraCajerosSnTransmitir,
                      rowSelector : "20px",
                      columnToggling : true,
                      //       headerMenu : this._menu,
                      class : "elmweb"
                  });
                  } else {
                          if (this._accionDiarioElectronico.getValue()  == "consultarTransaccion") { 
                          this._objGrid = new dojox.grid.DataGrid({
                              id: "de.consulta.grid.diarioElectronicoGrid",
                              store: this._origenDatos,
                              structure: this._estructuraGridGeneral,
                              rowSelector : "20px",
                              columnToggling : true,
                              //       headerMenu : this._menu,
                              class : "elmweb"
                          });
                           } else {
                                this._objGrid = new dojox.grid.DataGrid({
                              id: "de.consulta.grid.diarioElectronicoGrid",
                              store: this._origenDatos,
                              structure: this._estructuraGridHistoricoCargue,
                              rowSelector : "20px",
                              columnToggling : true,
                              //       headerMenu : this._menu,
                              class : "elmweb"
                          });
                         }
                 }  
            }
            dojo.byId("de.consulta.grid").style.display = "";
            dojo.byId("de.consulta.grid").appendChild(this._objGrid.domNode);
            
            this._objGrid.render();

            
            console.debug("STORE : ",this._origenDatos);            
            console.debug("ESTRUCTURA : ", this._estructuraGrid);
            console.debug("GRID : ", this._objGrid);      
            this.inherited(arguments);  
        }
        
        
        
        
    });
}



