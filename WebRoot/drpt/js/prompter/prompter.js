////////////////////////////////公共变量//////////////////////////////////////////////////////////////////////
//支队循环提示句柄
var _initTimeOut=null;
//大队循环提示句柄
var _daduiInitTimeOut=null;
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//提示支队用户，上报昨天数据function zhidui(jgid){
		_initTimeOut=setTimeout(function(){zhidui(jgid);},600000);
	var url = "drpt.prompter.prompter.d?type=zhidui";
	url = encodeURI(url);
	var params= "&depart="+jgid;
	params = encodeURI(params);
	//alert(params);return;
	new Ajax.Request(url, {method: 'post', parameters: params, onComplete: showZhiduiResponse});
}

/**
* 函数功能:判断支队是否有提交
*/
 function showZhiduiResponse(xmlHttp){
		var xmlDoc = xmlHttp.responseXML;
		var rows = xmlDoc.getElementsByTagName("row");
	   	if(rows.length<=0 ){
	   		var msgDiv=document.getElementById("msgDiv");
	   		if(msgDiv!=null){
	   		msgDiv.prototype.hideTips();	
	   		}
	   		var msgTitle=document.getElementById("msgTitle");
	   		if(msgTitle!=null){
	   		msgTitle.prototype.hideTips();	
	   		}
    		sAlert(MSG_TITLE, MSG_SUCCESS_COMMIT );  
			//alert();
			return;
			//已提交就注销时间循环
	   	}else{
			window.clearTimeout(_initTimeOut);
	   	}
		Popup.prototype.hideTips();	
	}
//////////////////////////////////////////////////大队提示///////////////////////////////////////////////////////////////

//提示大队用户，上报昨天数据
function dadui(jgid){
		_daduiInitTimeOut=setTimeout(function(){dadui(jgid);},600000);
	var url = "drpt.prompter.prompter.d?type=dadui";
	url = encodeURI(url);
	var params= "&depart="+jgid;
	params = encodeURI(params);
	//alert(params);return;
	new Ajax.Request(url, {method: 'post', parameters: params, onComplete: showDaduiResponse});
}

/**
* 函数功能:判断大队是否有提交

*/
 function showDaduiResponse(xmlHttp){
		var xmlDoc = xmlHttp.responseXML;
		var rows = xmlDoc.getElementsByTagName("row");
	   	if(rows.length<=0 ){
	   		var msgDiv=document.getElementById("msgDiv");
	   		if(msgDiv!=null){
	   		msgDiv.prototype.hideTips();	
	   		}
	   		var msgTitle=document.getElementById("msgTitle");
	   		if(msgTitle!=null){
	   		msgTitle.prototype.hideTips();	
	   		}
    		sAlert(MSG_TITLE, MSG_SUCCESS_COMMIT );  
			//alert();
			return;
			//已提交就注销时间循环
	   	}else{
			window.clearTimeout(_daduiInitTimeOut);
	   	}
		Popup.prototype.hideTips();	
	}
////////////////////////////////////////////////////////////////温馨提示//////////////////////////////////////////////////
var	MSG_SUCCESS_COMMIT="昨天的日报未提交！\n请及早提交！";
var MSG_TITLE="温馨提示";


function sAlert(titleStr,str){
    var msgw,msgh,bordercolor;
    msgw=400;//dialog hight
    msgh=100;//dialog width
    titleheight=25 
    bordercolor="#336699";
    titlecolor="#99CCFF";
   
    var sWidth,sHeight;
    sWidth=document.body.offsetWidth;
    sHeight=screen.height;

    var bgObj=document.createElement("div");
    bgObj.setAttribute('id','bgDiv');
    bgObj.style.position="absolute";
    bgObj.style.top="0";
    bgObj.style.background="#777";
    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
    bgObj.style.opacity="0.6";
    bgObj.style.left="0";
    bgObj.style.width=sWidth + "px";
    bgObj.style.height="521px";
    bgObj.style.zIndex = "10000";
    document.body.appendChild(bgObj);
   
    var msgObj=document.createElement("div")
    msgObj.setAttribute("id","msgDiv");
    msgObj.setAttribute("align","center");
    msgObj.style.background="white";
    msgObj.style.border="1px solid " + bordercolor;
    msgObj.style.position = "absolute";
    msgObj.style.left = "50%";
    msgObj.style.top = "50%";
    msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
    msgObj.style.marginLeft = "-225px" ;
    msgObj.style.marginTop = -75+document.documentElement.scrollTop+"px";
    msgObj.style.width = msgw + "px";
    msgObj.style.height =msgh + "px";
    msgObj.style.textAlign = "center";
    msgObj.style.lineHeight ="25px";
    msgObj.style.zIndex = "10001";
	msgObj.onclick=function(){
        document.body.removeChild(bgObj);
        document.getElementById("msgDiv").removeChild(title);
        document.body.removeChild(msgObj);
        }
  
   var title=document.createElement("h4");
   title.setAttribute("id","msgTitle");
   title.setAttribute("align","left");
   title.style.margin="0";
   title.style.padding="3px";
   title.style.background=bordercolor;
   title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
   title.style.opacity="0.75";
   title.style.border="1px solid " + bordercolor;
   title.style.height="18px";
   title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif";
   title.style.color="white";
   title.style.cursor="pointer";
   title.innerHTML=titleStr;
   title.onclick=function(){
        document.body.removeChild(bgObj);
        document.getElementById("msgDiv").removeChild(title);
        document.body.removeChild(msgObj);
        }
   document.body.appendChild(msgObj);
   document.getElementById("msgDiv").appendChild(title);
   var txt=document.createElement("p");
   txt.style.margin="1em 0"
   txt.setAttribute("id","msgTxt");
   txt.innerHTML=str;
   document.getElementById("msgDiv").appendChild(txt);
   } 