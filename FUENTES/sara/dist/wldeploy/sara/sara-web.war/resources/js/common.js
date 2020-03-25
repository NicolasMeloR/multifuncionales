function numbersDecOnly(evt){
    // Backspace = 8, '0' = 48, '9' = 57, Coma = '44', Punto = '46',  0 = TAB y Flechas
    var nav4 = window.Event ? true : false;
    var ie = window.navigator.appName;
    if(ie == 'Microsoft Internet Explorer')
        nav4 = false;
    var key = nav4 ? evt.which : evt.keyCode;
    return ((key >= 48 && key <= 57) || key==46 || key==8 || key==9 || key == 0);
}

function numbersOnly(evt){
    // Backspace = 8, '0' = 48, '9' = 57, 0 = TAB y Flechas
    var nav4 = window.Event ? true : false;
    var ie = window.navigator.appName;
    if(ie == 'Microsoft Internet Explorer')
        nav4 = false;
    var key = nav4 ? evt.which : evt.keyCode;
    return ((key >= 48 && key <= 57) || key==8 || key==9 || key == 0);
}

var parametro1=987654321;

function setParametro1(valor){
    parametro1 = valor;
}

function getParametro1(){
    return parametro1;
}

function decirHola(){
    alert('hola');
}