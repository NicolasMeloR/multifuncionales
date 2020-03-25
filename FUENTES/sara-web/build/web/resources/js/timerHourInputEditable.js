/* 
	ComboBox Object 
	http://www.zoonman.com/projects/combobox/

	Copyright (c) 2011, Tkachev Philipp
	All rights reserved.
	BSD License
	
*/
TimerHourInputEditable = function(object_name) {
  // Edit element cache
  this.edit = object_name;
  // Items Container
  var ddl = object_name
      .parentNode
      .parentNode
      .getElementsByTagName('DIV');
  this.dropdownlist = ddl[0];
  // Current Item
  this.currentitem = null;
  // Current Item Index
  this.currentitemindex = null;
  // Visible Items Count
  this.visiblecount = 0;
  // Closure Object
  var parobject = this;
  
  parobject.dropdownlist.style.display = 'block';
  var blockTimerHourEditable2 = document.getElementsByClassName("divTimerHourInputEditable")[1] ? document.getElementsByClassName("divTimerHourInputEditable")[1].childNodes[0] : null;
  
  if(blockTimerHourEditable2 && this.edit == blockTimerHourEditable2){
	  document.getElementsByClassName("divTimerHourInputEditable")[1].style.display = "block"; 
  }else if(blockTimerHourEditable2){
	  document.getElementsByClassName("divTimerHourInputEditable")[1].style.display = "none";
  }
  
  // Hide Items when edit lost focus
  this.edit.onblur = function() {
    if (allowLoose) {
      parobject.dropdownlist.style.display = 'none';
      if(blockTimerHourEditable2 && this.edit == blockTimerHourEditable2){
          document.getElementsByClassName("divTimerHourInputEditable")[1].style.display = "block"; 
      }else if(blockTimerHourEditable2){
          document.getElementsByClassName("divTimerHourInputEditable")[1].style.display = "block";
      }
      document.getElementById("menuForm:btnTimerHourInputEditable").click();
    }
  };
  
  //Handle when input change
  this.edit.onchange = function() {
    var re1 = /^([01]\d|2[0-3]):?([0-5]\d):?([0-5]\d)$/
    if(!re1.test(this.value)){
        parobject.edit.value = "00:00:00";
        parobject.dropdownlist.style.display = 'none';
    }
    
    if(blockTimerHourEditable2 && this.edit == blockTimerHourEditable2){
	  document.getElementsByClassName("divTimerHourInputEditable")[1].style.display = "block"; 
    }else if(blockTimerHourEditable2){
	  document.getElementsByClassName("divTimerHourInputEditable")[1].style.display = "none";
    }
    
    document.getElementById("menuForm:btnTimerHourInputEditable").click();
  };
  
  var allowLoose = true;
  // IE fix
  parobject.dropdownlist.onmousedown = function(event) {
    allowLoose = false;
  };
  parobject.dropdownlist.onmouseup = function(event) {
    setTimeout(function() {
      allowLoose = true;
    }, 150);
  };
  // Get Items
  this.listitems = this.dropdownlist.getElementsByTagName('A');
  for (var i = 0; i < this.listitems.length; i++) {
    var t = i;
    // Binding Click Event
    this.listitems[i].onclick = function() {
      var upv = this.innerHTML;
      upv = upv.replace(/\<b\>/ig, '');
      upv = upv.replace(/\<\/b\>/ig, '');
      parobject.edit.value = upv;
      parobject.dropdownlist.style.display = 'none';
      
      if(blockTimerHourEditable2 && this.edit !== blockTimerHourEditable2){
           document.getElementsByClassName("divTimerHourInputEditable")[1].style.display = "block";
      }
      
      document.getElementById("menuForm:btnTimerHourInputEditable").click();
      return true;
    };
    // Binding OnMouseOver Event
    this.listitems[i].onmouseover = function(e) {
      for (var i = 0; i < parobject.listitems.length; i++) {
        if (this === parobject.listitems[i]) {
          if (parobject.currentitem) {
            parobject.currentitem.className = parobject.currentitem.className.replace(
                /light/g,
                ''
            );
          }
          parobject.currentitem = parobject.listitems[i];
          parobject.currentitemindex = i;
          parobject.currentitem.className += ' light';
        }
      }
    };
  }
};
