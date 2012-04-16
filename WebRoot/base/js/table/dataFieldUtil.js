
/** 
	* 作者:郭亮亮
	*
	* 功能 ：查询数据库得到一条数据
	* 时间：2008-03-27
	*
	*
	*/
function doQuery(fid)
{
	if(fid !== '')
	{
		var url="base.datafield.getDataById.d";
		url = encodeURI(url);
		var params = "fid="+fid;
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showGetXMLResponse, onSuccess:doGetXMLFini});
	}
}

/** 
	* 修改:郭亮亮
	*
	* 功能 ：编辑 --将数据放入编辑框 
	* 修改目的: 不可编辑数据控制
	* 时间：2008-03-27
	*
	*
	*/
function showGetXMLResponse(xmlHttp)
{

	var xmldoc = xmlHttp.responseXML;
	
	
	var fidText = document.getElementById('fid');
	fidText.readOnly=true;

	var tidText = document.getElementById('tid');
	tidText.readOnly=true;
	
	var fldnameText = document.getElementById('fldname');
	var displaynameText = document.getElementById('displayname');
	var widthText = document.getElementById('width');
	var typeText=document.getElementById('type');
	var alignText=document.getElementById('align');
	var classText=document.getElementById('class');
	var mapnameText=document.getElementById('mapname');
	
	var results = xmldoc.getElementsByTagName("col");
	
	tidText.value = results[0].text;
	
	fldnameText.value = results[1].text;
	fldnameText.focus();
	
	displaynameText.value=results[2].text;
	widthText.value=results[3].text;
	typeText.value=results[4].text;
	alignText.value=results[5].text;
	classText.value=results[6].text;
	mapnameText.value=results[7].text;
}	


/** 
	* 作者:郭亮亮
	*
	* 功能 ：修改 
	* 时间：2008-03-31
	*
	*
	*/
function modify(insrtOrUpdt)
{
	if(insrtOrUpdt == '0')
	{
		var eleArray = document.getElementsByTagName("input");		 
		var dataArray = new Array();

		for(var i=0;i<eleArray.length;i++)
		{
			var tempArray = new Array();
			if(eleArray[i].atrbt)
			{
				tempArray.push(eleArray[i].atrbt);
				if(eleArray[i].atrbt == "width")
				{
					tempArray.push(eleArray[i].value);
				}
				else
				{
					tempArray.push(eleArray[i].value);
				}
				dataArray.push(tempArray);
			}
		}
		var url="base.pageCust.settingReport.d";
		url = encodeURI(url);
		var xmlbody = createLineXMLBody(dataArray,'Fields');
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
				if(eleArray[i].atrbt == "width")
				{
					tempArray.push(eleArray[i].value);
				}
				else
				{
					tempArray.push(eleArray[i].value);
				}
				dataArray.push(tempArray);
			}
		}
		var url="base.datafield.updateData.d";
		url = encodeURI(url);
		var xmlbody = createLineXMLBody(dataArray,'Fields');
		var fullbody = createFullXMLBody(xmlbody);
		var reEq = /=/g;
		fullbody = fullbody.replace(reEq,"$");
		var params = "xmlStr="+ fullbody;
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showResponse, onSuccess:doFini});
	}
}
 //重置函数
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
