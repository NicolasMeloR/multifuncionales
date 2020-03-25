//---------------------------------------------------------------------------------------------
//Funciones para el calendario
//---------------------------------------------------------------------------------------------
var abierto=false;
var NS7      =(document.getElementById && !document.all)?1:0;
var losMeses = new Array ("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");
var losDias  = new Array (31,28,31,30,31,30,31,31,30,31,30,31);
var losDiasDeLaSemana = new Array("Domingo","Lunes","Martes","Mi�rcoles","Jueves","Viernes","Sabado");
var diasSemana  = new Array ('L','M','X','J','V','S','D');
var idioma      = "es";
var pattern     = "YYYY-MM-DD";
var calendario = document.getElementById("calendario");

/*****************************************************************************/
function ReconoceCapas(capitas){
 var cadena="";
    if (NS7)  {
          cadena=capitas+"=document.getElementById('"+capitas+"')";
	  eval (cadena);
    }
}

/*****************************************************************************/
function ReconoceCapasArray(capitas){
 var cadena="";
    if (NS7)  {
    	for(var i=0;i<capitas.length;i++){
          cadena=capitas[i]+"=document.getElementById('"+capitas[i]+"')";
	  eval (cadena);
	 }
    }
}
/*****************************************************************************/
function explode(elSeparador,laCadena){
	var elArray = new Array();
	var cadenita = "";
	for (var a=0,indice=0;a<laCadena.length;a++){
	    if (laCadena.charAt(a)==elSeparador){
	        elArray[indice] = cadenita;
	        indice++;
	        cadenita = "";
	    }else{
	        cadenita+=laCadena.charAt(a);
	    }
	}//for a
	elArray[indice] = cadenita;
	return elArray;
}//explode
/*****************************************************************************/
function implode(elSeparador,elArray){
	var cadena = elArray[0];
	for (var a=1;a<elArray.length;a++){
		cadena = cadena + elSeparador + elArray[a];
	}//for a
	return cadena;
}//implode
/*****************************************************************************/
/*****************************************************************************/
var hoy = new Date();
var diaHoy = hoy.getDate();
var mesHoy = hoy.getMonth()+1;
var anoHoy = hoy.getYear();
if (anoHoy<1900) anoHoy+=1900;
var elDia = diaHoy;
var elMes = mesHoy;
var elAno = anoHoy;
/*****************************************************************************/
/*****************************************************************************/
function siguienteMes(mes,ano){
	if (mes==12){
	    mes = 1;
	    ano++;
	}else{
	    mes++;
	}
	return mes+"-"+ano;
}//siguienteMes
/*****************************************************************************/
function anteriorMes(mes,ano){
	if (mes==1){
	    mes = 12;
	    ano--;
	}else{
	    mes--;
	}
	return mes+"-"+ano;
}//anteriorMes
/*****************************************************************************/
function colorear(dia,mes,ano,esDomingo){
	var hoy = new Date();

   	  var diaHoy = hoy.getDate();
 	  var mesHoy = hoy.getMonth()+1;
	  var anoHoy = hoy.getYear();



	if (anoHoy<1900) anoHoy+=1900;
	var celdaDia;
	if ((ano>anoHoy) || ((ano==anoHoy) && (mes>mesHoy)) || ((ano==anoHoy) && (mes==mesHoy) && (dia>=diaHoy))){
		if ((dia+"-"+mes+"-"+ano)==(diaHoy+"-"+mesHoy+"-"+anoHoy))
		    celdaDia = "<td id=\"celda"+dia+"\" style=\"background-color:#000066;color:#FFFFFF;font-weight:bold;cursor:pointer;font-family: Verdana, Arial, Helvetica; font-size: 9px;\" onClick=\"marcar("+dia+","+mes+","+ano+");\" width=\"12\" height=\"12\">"+dia+"</td>";
		else{
		    if (esDomingo)
		        celdaDia = "<td id=\"celda"+dia+"\" style=\"background-color:#FFFFFF;color:#CC0000;cursor:pointer;font-family: Verdana, Arial, Helvetica; font-size: 9px;\" onClick=\"marcar("+dia+","+mes+","+ano+");\" width=\"12\" height=\"12\">"+dia+"</td>";
			else
				celdaDia = "<td id=\"celda"+dia+"\" style=\"background-color:#FFFFFF;color:#000000;cursor:pointer;font-family: Verdana, Arial, Helvetica; font-size: 9px;\" onClick=\"marcar("+dia+","+mes+","+ano+");\" width=\"12\" height=\"12\">"+dia+"</td>";
		}//else
	}else{
        celdaDia = "<td id=\"celda"+dia+"\" style=\"background-color:#FFFFFF;color:#000000;cursor:pointer;font-family: Verdana, Arial, Helvetica; font-size: 9px;\" onClick=\"marcar("+dia+","+mes+","+ano+");\" width=\"12\" height=\"12\">"+dia+"</td>";
		// no poder acceder a dias pasados ->celdaDia = "<td id=\"celda"+dia+"\" style=\"background-color:#aaaaaa;color:#888888;cursor:default;font-family: Verdana, Arial, Helvetica; font-size: 9px;\" width=\"12\" height=\"12\">"+dia+"</td>";
	}
	
	if(dia==elDia && mes==elMes && ano==elAno){
		celdaDia = "<td id=\"celda"+dia+"\" style=\"background-color:#000066;color:#FFFFFF;font-weight:bold;cursor:pointer;font-family: Verdana, Arial, Helvetica; font-size: 9px;\" onClick=\"marcar("+dia+","+mes+","+ano+");\" width=\"12\" height=\"12\">"+dia+"</td>";
	}
	
	return celdaDia;
}//colorear
/*****************************************************************************/
function generarCalendario(mes,ano,rutaImg){
   	  var hoy    = new Date();
   	  var diaHoy = hoy.getDate();
 	  var mesHoy = hoy.getMonth()+1;
	  var anoHoy = hoy.getYear();
      if (anoHoy<1900) anoHoy +=1900;
  	  var dias=losDias[mes-1];
  	  var i,j;
      diaUno=calcularDia(1,mes,ano);
	  var calendarioMes="";
      calendarioMes=calendarioMes+"<table border='0' style=\"border:1px solid #000000;text-align:center;background-color:white;\"><tr>";
      
      var mesAnt = anteriorMes(mes,ano);
      mesAnt = explode("-",mesAnt);
      mesAnt = "onClick=\"mostrarCalendario('"+campo+"',"+mesAnt[0]+","+mesAnt[1]+",'"+rutaImg+"')\"";
      var mesSig = siguienteMes(mes,ano);
      mesSig = explode("-",mesSig);
      mesSig = "onClick=\"mostrarCalendario('"+campo+"',"+mesSig[0]+","+mesSig[1]+",'"+rutaImg+"')\"";
      
      
      var anoAnt =  parseInt(ano) -1;
      anoAnt = "onClick=\"mostrarCalendario('"+campo+"',"+mes+","+anoAnt+",'"+rutaImg+"')\"";
      var anoSig = parseInt(ano)+1;
      anoSig = "onClick=\"mostrarCalendario('"+campo+"',"+mes+","+anoSig+",'"+rutaImg+"')\"";
      
      //A�os
      calendarioMes+="<td width=\"16\" height=\"16\"><img src='"+rutaImg+"/imagenes/izquierda.png' alt='<' "+anoAnt+" style=\"cursor:pointer;width:16px;height:16px;\"/></td>";
      calendarioMes= calendarioMes + "<td colspan='5' class=\"tfecha\" style=\"cursor:default;\">"+ano+"</td>";
      calendarioMes+="<td width=\"16\" height=\"16\"><img src='"+rutaImg+"/imagenes/derecha.png' alt='>' style=\"cursor:pointer;width:16px;height:16px;\""+anoSig+"/></td></tr>";
      //calendarioMes+="<td><img src=\""+rutaImg+"/imagenes/cerrar.png\" border=\"0\" style=\"cursor:pointer;width:12px;height:12px;\" onclick=\"cerrar('calendario');\" title=\"Cerrar calendario\"></td></tr>"; 
      //Meses
      calendarioMes+="<tr><td width=\"16\" height=\"16\"><img src='"+rutaImg+"/imagenes/izquierda.png' alt='<' "+mesAnt+" style=\"cursor:pointer;width:16px;height:16px;\"/></td>";
      calendarioMes= calendarioMes + "<td colspan='5' class=\"tfecha\" style=\"cursor:default;\">"+losMeses[mes-1]+"</td>";
      calendarioMes+="<td width=\"16\" height=\"16\"><img src='"+rutaImg+"/imagenes/derecha.png' alt='>' style=\"cursor:pointer;width:16px;height:16px;\""+mesSig+"/></td></tr>";
      //rellenamos la segunda fila con las primeras letras de cada dia L M X J V S D
	  calendarioMes+="<tr style=\"background-image:url("+rutaImg+"/imagenes/amarillo.gif);font-weight:bold;font-family: Verdana, Arial, Helvetica; font-size: 9px; color: white;\">";
	  for (i=0;i<7;i++)
		if (idioma=="es")
			calendarioMes=calendarioMes+ "<td class='semana'  width=\"12\" height=\"12\" style=\"cursor:default;\">"+diasSemana[i]+"</td>";
		else
			calendarioMes=calendarioMes+ "<td class='semana'  width=\"12\" height=\"12\" style=\"cursor:default;\">"+diasSemanaEn[i]+"</td>";
	calendarioMes=calendarioMes+"</tr>";
  	calendarioMes+="<tr>";
  	//rellenamos hasta el primer dia de Mes a guiones
  	for (i=0;diasSemana[i]!=diaUno;i++)
  			calendarioMes=calendarioMes+"<td style=\"background-color:#aaaaaa;color:#666666;\"  width=\"12\" height=\"12\" style=\"cursor:default;\">-</td>";
  	dias_del_mes = losDias[mes-1];
  	if ((mes==2) && (ano % 4==0)) dias_del_mes++;  //es bisiesto
  	for (j=i,i=1;i<=dias_del_mes;){
		for (;j<7 && i<=dias_del_mes;j++,i++)
		if (j==6){
			calendarioMes+=colorear(i,mes,ano,1);
		}else
			calendarioMes+=colorear(i,mes,ano,0);
		if (j==7){
			calendarioMes+=("</tr><tr>");
			j=0;
		}//if (j==7)
	}//1� for
	if (j>0)
  	for (;j<7;j++)
  		calendarioMes+="<td style=\"background-color:#aaaaaa;color:#666666;\" width=\"12\" height=\"12\" style=\"cursor:default;\">-</td>";
  	calendarioMes+= "</tr></table>";
  	return calendarioMes;
}//dameCalendario
/*****************************************************************************/
function calcularDia(dia,mes,ano){
	var dias=365*(ano-1);
	var i;
	var deSemana;
       for (i=0;i<(mes-1);i++)
		dias+=losDias[i];
  	dias+=dia-1;
  	dias+=(Math.floor((ano-1)/4));
  	if ((mes>2) && (((ano%4)==0)))
  		 dias++;
  	deSemana=dias % 7;
    if (idioma=="es"){
	  	if (deSemana==0) deSemana=6;
	  	else deSemana--;
  	}
   	return diasSemana[deSemana];
}//calcularDia

/*****************************************************************************/
function mostrarCalendario(campito,mes,ano,rutaImg){
    campo=campito;
    /*var fecSelected = eval("$('#"+campito+"').value"); //document.getElementById(campito);
    if ( fecSelected != null ) {
    	alert("fecSelected: " + fecSelected);
    }*/
     
	var tablaCalendario = '<table width="100%">';
	tablaCalendario+= "<tr><td>"+generarCalendario(mes,ano,rutaImg)+"</td></tr></table>";
	var celda;

	calendario.innerHTML = tablaCalendario;
	abrirCalendario('calendario');

}//mostrarCalendario
/*****************************************************************************/

/*****************************************************************************/
function iniciaCalendario(campito,rutaImg,pattern){
       calendario = document.getElementById("calendario");
	if(!abierto){
	   campo=campito;
       var tablaCalendario = '<table width="100%">';
	   tablaCalendario+= "<tr><td>"+generarCalendario(elMes.toString(),elAno.toString(),rutaImg)+"</td></tr></table>";
	   calendario.innerHTML = tablaCalendario;
	   abrirCalendario('calendario');
	}else{
	   cerrarCapa('calendario');	
	}
}//mostrarCalendario
/*****************************************************************************/

function menorDe18(dia,mes,ano) {
	var cumpleAnoHoy = anoHoy - 18;
	var cumpleMesHoy = mesHoy;
	var cumpleDiaHoy = diaHoy;
	if (ano>cumpleAnoHoy) {
		return true;
	} else {
	    if (ano == cumpleAnoHoy) {
	        if (mes >cumpleMesHoy ) {
	        	return true;
	        } else {
	            if (mes == cumpleMesHoy ) {
	                if (dia >cumpleDiaHoy ) {
	                	return true;
	                }
	            }
	        }
	    }
	}
	return false;
}
/*****************************************************************************/
function marcar(dia,mes,ano){
	
	if ((elDia<10) && (elDia.toString().length)>1){
	    elDia = elDia.toString().substr(1,1);
	}
	
	elDia = dia;
	elMes = mes;
	elAno = ano;
	devolverFecha(dia,mes,ano);
}//marcar
/*****************************************************************************/
function devolverFecha(elDia,elMes,elAno){
	if (elDia<10) elDia = "0"+elDia;
	if (elMes<10) elMes = "0"+elMes;
	
	var campoInput=buscarInput(campo);
        if(pattern === "YYYY-MM-DD"){
            campoInput.value = elAno+"-"+elMes+"-"+elDia;
        }else {
            campoInput.value = elAno+"/"+elMes+"/"+elDia;
        }
        campoInput.disabled = false;
	cerrarCapa('calendario'); 
        document.getElementById("menuForm:btnDatePicker").click();
        //return true;
}

function buscarInput(id){
	return buscar(document.getElementsByTagName("input"),id);
}

/*****************************************************************************/
function abrirCalendario(laCapa){
	eval(laCapa+".style.visibility='visible'");
	abierto=true;
	//alert(document.getElementById(laCapa)+".style.visibility");
	//eval("document.getElementById('"+laCapa+"').style.visibility='visible'");
}//abrirCapa
/*****************************************************************************/
function abrirCapa(laCapa){

	eval(laCapa+".style.visibility='visible'");
	
}//abrirCapa
/*****************************************************************************/

function cerrarCapa(laCapa){
	calendario.innerHTML = "";
	eval(laCapa+".style.visibility='hidden'");
	eval("document.getElementById('fadeCalendario').style.display='none';document.getElementById('lightCalendario').style.display='none'");	
	abierto=false;
}
/*****************************************************************************/
function cerrar(laCapa){
    cerrarCapa('calendario');
}
/*****************************************************************************/

function buscar(campos,id){
	for(var i=0;i<campos.length;i++){
		var campoSelect=campos[i];
		if(campoSelect.id.indexOf(id)>-1){
			return campoSelect;
		}
	}
}