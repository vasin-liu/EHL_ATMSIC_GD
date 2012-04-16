var leftlist;
var rigthlist;
var gourpid;
var rigthlistcode;
var flag =0;


function queryCarnumber() {
    var querycar = document.getElementById("querycar").value;
    var url = "gps.groupctrl.ReadGpsCar.d";
    url = encodeURI(url);
    var params = "";
    params = "querycar=" + querycar;
    params = encodeURI(params);
    new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGpsCar});
}

function showGpsCar(xmlHttp) {
    var xmlDoc = xmlHttp.responseXML;
    var rows = xmlDoc.getElementsByTagName("row");
    leftlist = new Array([rows.length]);
 	for(var i = 0 ; i <rows.length; i++){
       var results = rows[i].childNodes;
       for(var j = 0; j <results.length; j++){
    	     //leftlist[i] = results[j].text;
    	}
    }
     
     var noselrows = xmlDoc.getElementsByTagName("row");
     leftlist = new Array([noselrows.length]);
     if(noselrows.length>0){
	     for(var k=0;k<noselrows.length;k++){
	         var cols = noselrows[k].getElementsByTagName("col");
	         for(var l=0;l<cols.length;l++) {
	             leftlist[k] = cols[l].firstChild.data;
	         }
	        
	     }
     }
         document.getElementById("leftlist").options.length = 0;
	     for(var n=0;n<leftlist.length;n++) {
	        var oOption = document.createElement("option");    
	        document.getElementById("leftList").options.add(oOption);    
	      //  document.getElementById("leftList").options[0].selected=true;    
	        oOption.innerText =leftlist[n].split(",")[1];    
	        oOption.value = leftlist[n].split(",")[0];
	      }
}
function initPage(groupid) {
       query(groupid);
}
function query(groupid) {
    var params = "";
    params ="groupid="+groupid;
    var url = "gps.groupctrl.ReadGpsGroupByGroupId.d";
    url = encodeURI(url);
    params = encodeURI(params);  
    new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGpsGroupList});
}

function showGpsGroupList(xmlHttp){
     var xmlDoc = xmlHttp.responseXML;
     var rows = xmlDoc.getElementsByTagName("selected");

     if(rows.length>0) {
     for(var i=0;i<rows.length;i++){
         var cols = rows[i].getElementsByTagName("selcar");
         rigthlist = new Array([cols.length]);
         for(var j=0;j<cols.length;j++) {
             rigthlist[j] = cols[j].firstChild.data;
             if(cols[j].text!='null'||cols[j].text!="") {
	             rigthlist[j] = cols[j].firstChild.data;
	             var arrcar = cols[j].firstChild.data;
	             var carcode = arrcar.split(",");
	             var car = carcode[0];
	            // rigthlistcode[j] = car;
	            //alert(rigthlist[j]);
             }
            
         }
        
     }
     }
      if(rows.length>0) {
	      for(var n=0;n<rigthlist.length;n++) {
	        var oOption = document.createElement("option");    
	        document.getElementById("rigthlist").options.add(oOption);    
	      //  document.getElementById("leftList").options[0].selected=true;    
	        oOption.innerText =rigthlist[n].split(",")[1];    
	        oOption.value = rigthlist[n].split(",")[0];  
	      }
      }
      
     var noselrows = xmlDoc.getElementsByTagName("noselected");

     if(noselrows.length>0){
	     for(var k=0;k<noselrows.length;k++){
	         var cols = noselrows[k].getElementsByTagName("noselcar");
	         leftlist = new Array([cols.length]);
	         for(var l=0;l<cols.length;l++) {
	             leftlist[l] = cols[l].firstChild.data;
	         }
	        
	     }
     }
  
	 for(var n=0;n<leftlist.length;n++) {
	     var oOption = document.createElement("option");    
	     document.getElementById("leftList").options.add(oOption);    
	      //document.getElementById("leftList").options[0].selected=true;    
	     oOption.innerText =leftlist[n].split(",")[1];    
	     oOption.value = leftlist[n].split(",")[0];  
	  }
}
// moveList用于对两个多选列表进行选项的移动操作 
			// from为"需要移动"的列表名称，to为"移动到"列表名称 
function moveList(from,to) { 
	var fromList = $(from); 
	var fromLen = fromList.options.length; 
	var toList = $(to); 
	var toLen = toList.options.length; 
	
	// current 为"需要移动"列表中的当前选项序号 
	var current = fromList.selectedIndex; 
	// 如果"需要移动"列表中有选择项，则进行移动操作 
	while (current>-1) { 
	// o为"需要移动"列表中当前选择项对象 
	var o = fromList.options[current]; 
	var t = o.text; 
	var v = o.value; 
	// 根据已选项新建一个列表选项 
	var optionName = new Option(t, v, false, false); 
	// 将该选项添加到"移动到"列表中 
	toList.options[toLen]= optionName; 
	toLen++; 
	// 将该选项从"需要移动"列表中清除 
	fromList.options[current]=null; 
	current = fromList.selectedIndex; 
	} 
}

function moveAll(from,to) {
	var fbox = $(from); 
	var tbox = $(to);
	
	//循环第一个列表框的长度
	for(var i=0; i<fbox.options.length; i++) {
		if(fbox.options[i].value != "") {
			//新项的对象
			var no = new Option();
			//循环第一个列表框里的value赋给新项
			no.value = fbox.options[i].value;
			//内容同样赋给新项
			no.text = fbox.options[i].text;
			
			//默认选中
			no.selected=true;
			tbox.options.add(no);
			//把循环得到的值赋给后一个列表框			
			//tbox.options[tbox.options.length] = no;
			//fbox.options[i].value = "";
			//fbox.options[i].text = "";
			//fbox.removeChild(fbox.options[i]);
		}
	}
	fbox.options.length=0; 
}

function moveLeftAll(from,to) {
	var fbox = $(from); 
	var tbox = $(to);
	for(var j=0;j<fbox.options.length;j++) {
	   //fbox.options[j].value="";
	   //fbox.options[j].text="";
	   if(fbox.options[j].value==""||fbox.options[j].text==""){
	      fbox.removeChild(fbox.options[j]);
	   } 
	   //alert(fbox.options[j].value);
	}
	//循环第一个列表框的长度
	for(var i=0; i<tbox.options.length; i++) {
		if(tbox.options[i].value != "") {
			//新项的对象
			//var no = new Option();
			var no = document.createElement("option");    
			//循环第一个列表框里的value赋给新项
			//no.value = tbox.options[i].value;
			//内容同样赋给新项
			//no.text = tbox.options[i].text;
			no.innerText = tbox.options[i].text;
			no.value = tbox.options[i].value;
			//默认选中
			no.selected=true;
			fbox.options.add(no);
			//document.getElementById("rigthlist").options.add(no); 
			//把循环得到的值赋给后一个列表框			
			//fbox.options[fbox.options.length] = no;
			//tbox.options[i].value = "";
			//tbox.options[i].text = "";
			//tbox.removeChild(tbox.options[i]);
		}
	}
	tbox.options.length=0; 
}
function doSave(groupid){
    var o = document.getElementById('rightList');
    var intvalue="";
    var t = "";
    for(var i=0;i<o.length;i++){   
        if(!o.options[i].selected||o.options[i].selected){
            intvalue+=o.options[i].value+",";
        }
    }
    t = intvalue.substr(0,intvalue.length-1);
    var params="groupid="+groupid+"&carnumber="+t;
    params = encodeURI(params);
    
    var url = "gps.groupctrl.saveGpsGroup.d";
    url = encodeURI(url);
	new Ajax.Request(url, 
			{
				method: 'post', 
				parameters: params, 
				onComplete:showGpsGroup1
			}
		);
  
}
function showGpsGroup1(xmlHttp) {
    alert(xmlHttp.responseText);
    if(xmlHttp.responseText=='数据保存成功'){
       window.returnValue ="fresh";
	   window.close();
	}
}

function doClose(){
		window.returnValue ="fresh";
	 	window.close();
	} 
	