/* 用于单点登录系统cookie的分发与同步删除
/*--------------------------------------------------------------------------*/
function getSysUrlByPcode(pcode,behaviour)
{
	if(pcode !='')
	{
		var url = "common.ssoutil.getUrlByPcode.d";
		url = encodeURI(url);
		var params = "pcode="+pcode+"&behaviour="+behaviour;
		params = encodeURI(params);
	
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showGetUrlResponse, onSuccess:doGetXMLFini});	
	}
}

function showGetUrlResponse(xmlHttp)
{
	var xmldoc = xmlHttp.responseXML;
	var results = xmldoc.getElementsByTagName("col");
	var pname = document.getElementById("pname").value;
	var cookiet = document.getElementById("cookiet").value;
	var params = "pname="+pname+"&cookiet="+cookiet;
	for(var i=0;i<results.length;i++)
	{
		var url = results[i].text;
		var httpUrl = url+"?"+params;
		httpUrl = encodeURI(httpUrl);
		alert("已加载"+url);
		var iframe = document.getElementById("goal");
		iframe.src = httpUrl;
		
	}
	var jumper = document.getElementById("jump");
	jumper.submit();
}

function doGetXMLFini()
{
}

