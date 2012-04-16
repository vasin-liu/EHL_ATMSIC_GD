

function doQuery(tid)
{
	if(tid !== '')
	{
		var url="base.datatable.getDataById.d";
		url = encodeURI(url);
		var params = "tid="+tid;
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showGetXMLResponse, onSuccess:doGetXMLFini});
	}
}

function showGetXMLResponse(xmlHttp)
{

	var xmldoc = xmlHttp.responseXML;
	
	var tidText = document.getElementById('tid');
	tidText.readOnly=true;
	
	var tnameText = document.getElementById('tname');
	
	var tablenameText = document.getElementById('tablename');
	var selconditionText = document.getElementById('selcondition');
	var widthText=document.getElementById('width');
	var heightText=document.getElementById('height');
	var dragText=document.getElementById('drag');
	var pcrelText=document.getElementById('pcrel');
	var parentidText=document.getElementById('parentid');
	
	var results = xmldoc.getElementsByTagName("col");
	
	tnameText.value = results[0].text;
	tnameText.focus();
	
	tablenameText.value = results[1].text;
	selconditionText.value=results[2].text;
	var width = results[3].text;
	var heigth = results[4].text;
	widthText.value=width.substring(0,3);
	heightText.value=heigth.substring(0,3);
	dragText.value=results[5].text;
	pcrelText.value=results[6].text;
	parentidText.value=results[7].text;
	
}



function modify(insrtOrUpdt)
{
	var selWidth=document.getElementById("widthed").value;
	var selHeight=document.getElementById("heighted").value;
	if(insrtOrUpdt == '0'){   
		var eleArray = document.getElementsByTagName("input");
		var dataArray = new Array();

		if(eleArray[0].value === ""){
	     alert("请输入表ID");
	     return ;
	  	}
	  	if(eleArray[0].value.length>10){
	   	 	alert("输入表ID过长");
	  	 	return ;
		} 
		if(eleArray[1].value==""){
	  	 	alert("请输入表名称");
	  		return ;
		} 

		for(var i=0;i<eleArray.length;i++){
			var tempArray = new Array();
			if(eleArray[i].atrbt){
				tempArray.push(eleArray[i].atrbt);
				if(eleArray[i].atrbt == "width" ){
					tempArray.push(eleArray[i].value + selWidth);
				}else if(eleArray[i].atrbt == "height"){
					tempArray.push(eleArray[i].value + selHeight);
				}else{
					tempArray.push(eleArray[i].value);
				}
				dataArray.push(tempArray);
			}
		}		
		var url="base.pageCust.settingReport.d";
		url = encodeURI(url);
		var xmlbody = createLineXMLBody(dataArray,'Tables');
		var fullbody = createFullXMLBody(xmlbody);
		var reEq = /=/g;
		fullbody = fullbody.replace(reEq,"$");
		var params = "xmlStr="+ fullbody;
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showResponse, onSuccess:doFini});
	}
	else if(insrtOrUpdt == '1')
	{
		 
		var eleArray = document.getElementsByTagName("input");
		var dataArray = new Array();
		for(var i=0;i<eleArray.length;i++)
		{
			var tempArray = new Array();
			if(eleArray[i].atrbt)
			{
				tempArray.push(eleArray[i].atrbt);
				if(eleArray[i].atrbt == "width" )
				{
					tempArray.push(eleArray[i].value + selWidth);
				}
				else if(eleArray[i].atrbt == "height")
				{
					tempArray.push(eleArray[i].value + selHeight);
				}
				else
				{
					tempArray.push(eleArray[i].value);
				}
				dataArray.push(tempArray);
			}
		}		
		var url="base.datatable.updateData.d";
		url = encodeURI(url);
		var xmlbody = createLineXMLBody(dataArray,'Tables');
		var fullbody = createFullXMLBody(xmlbody);
		var reEq = /=/g;
		fullbody = fullbody.replace(reEq,"$");
		var params = "xmlStr="+ fullbody;
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showResponse, onSuccess:doFini});
	}
}

function reset() {
    var input;
    var select;
    var textarea;
    var queryTable = window.document.getElementById("dataTable");
    if (queryTable != null) {
        input = queryTable.getElementsByTagName('input');
        select = queryTable.getElementsByTagName("select");
        textarea = queryTable.getElementsByTagName('textarea');
    } else {
        input = document.getElementsByTagName("input");
        select = document.getElementsByTagName("select");
        textarea = document.getElementsByTagName('textarea');
    }
    for (var i = 0; i < input.length-3; i++) {
        var item = input[i];
        if (item.type != 'button' && !item.readOnly) {
            item.value = '';
        }
    }
    for (var i = 0; i < textarea.length; i++) {
        var item = textarea[i];
        if (item.type != 'button' && !item.readOnly) {
            item.value = '';
        }
    }
    for (var i = 0; i < select.length; i++) {
		if (select[i].type != 'button' && !select[i].readOnly) {
			select[i].value = '-1';
		}
    }
}


function doGetXMLFini()
{
}

function doFini()
{
}

function showResponse(xmlHttp)
{
	var xmlText = xmlHttp.responseText;
	alert(xmlText);
}
