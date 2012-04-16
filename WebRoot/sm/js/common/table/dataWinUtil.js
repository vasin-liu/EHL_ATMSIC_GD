

function doQuery(did)
{
	if(did !== '')
	{
		var url="common.datawin.getDataById.d";
		url = encodeURI(url);
		var params = "did="+did;
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showGetXMLResponse, onSuccess:doGetXMLFini});
	}
}

function showGetXMLResponse(xmlHttp)
{

	var xmldoc = xmlHttp.responseXML;
//		alert(xmldoc);
	var didText = document.getElementById('did');
	var dnameText = document.getElementById('dname');
	var rTableText = document.getElementById('relatedTables');
	var desText = document.getElementById('description');
	var results = xmldoc.getElementsByTagName("col");

	didText.readOnly = true;
	dnameText.value = results[0].text;
	dnameText.focus();
	rTableText.value = results[1].text;
	desText.value = results[2].text;
}


function doGetXMLFini()
{
}

function doFini()
{
}


function showResponse(xmlHttp)
{
	xmlText = xmlHttp.responseText;
	alert(xmlText);
}

function modify(insrtOrUpdt)
{       window.returnValue = "fresh";
	    var eleArray = document.getElementsByTagName("input");
		var dataArray = new Array();
		
		for (var i = 0; i < (eleArray.length); i++) {
		     dataArray.push(eleArray[i].value);
	     }
		var url="common.datawin.updateData.d";
		url = encodeURI(url);
		var xmlbody = createLineXMLBody(dataArray,'RFWin');
		var fullbody = createFullXMLBody(xmlbody);
		var reEq = /=/g;
		fullbody = fullbody.replace(reEq,"$");
		var params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt;
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showResponse, onSuccess:doFini});
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