    /*打开关于页面*/
	function showHelpPage(aboutJSP,url){
		var returnValuestr = window.showModalDialog(aboutJSP+"?versionXmlUrl="+url, "", "dialogWidth:431px;dialogHeight:355px");
		if (returnValuestr == "insertOrUpdate") {
			fresh();
		}
	}
	
	//打开帮助
	function help(helpJsp,specifiedHtm){
	   var vtop = (screen.availHeight-660)/2;
	   var vleft = (screen.availWidth-800)/2;
	   if(typeof specifiedHtm == "undefined"){	   
	      window.open(helpJsp,"newwindow","height=640,width=800,top="+vtop+",left="+vleft+",toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no");	  
	   }else{	      
	      window.open(helpJsp,"newwindow?htm="+specifiedHtm,"newwindow","height=700,width=900,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no");
	
	   }
	}
	
	
	  /*点击标签换页*/
	function selectTag(showContent,selfObj,smImgUrl){
		  // 操作标签
			  var tag = document.getElementById("tags").getElementsByTagName("li");
			  var taglength = tag.length;
			  
			  for(i=0; i<taglength; i++){
			    tag[i].className = "";
			    tag[i].style.background="url('"+smImgUrl+"/bg.gif')  right bottom";
			  }
			  selfObj.parentNode.className = "selectTag";
			   selfObj.parentNode.style.background="url('"+smImgUrl+"/bg2.gif')  right bottom";
			  
			  // 操作内容
			  j=document.getElementById("tagContent").getElementsByTagName("div");
			  
			  for(i=0; i<j.length; i++){
			    j[i].style.display = "none";
			  }
			  document.getElementById(showContent).style.display = "block";
			 selfObj.style.background = "left bottom";
			 document.getElementById(showContent).focus();
		}
	
//根据xml文件获取版本历史信息
 function createHistory(xmlURL){
    var verStr = "<span style='width:390;height:217;overflow-x:hidden;overflow-y:scroll;font-family: 宋体'>";
        verStr += "<table  style='font-size:9pt;'>";
    var divObj = document.getElementById("ver");
    var info = document.getElementById("info");
    info.focus();
    var hisDom = getDom(xmlURL);
    var verNodes = hisDom.documentElement.childNodes;
    var len = verNodes.length;
    if(len > 5){
       len = 5;
    }
    for (var i = 0;i < len; i+=1){
       verStr += "<tr><td style='font-size:10pt'><b>";
       verStr += verNodes[i].getAttribute("no");
       verStr += "</b></td></tr>";
       var sortNodes = verNodes[i].childNodes;
       for (var j = 0; j <sortNodes.length; j+=1){
           verStr += "<tr><td>";
           verStr += sortNodes[j].text;
           verStr += "</td></tr>";
       }
    }
   verStr += "</table>";
   verStr += "</span>";
   divObj.innerHTML = verStr;
   window.status = "version"; 
 }
	
	
	/*创建XmlDom*/	
function createXMLDOM() {
	var arrSignatures = ["MSXML2.DOMDocument.5.0", "MSXML2.DOMDocument.4.0", "MSXML2.DOMDocument.3.0", "MSXML2.DOMDocument", "Microsoft.XmlDom"];
	for (var i = 0; i < arrSignatures.length; i++) {
		try {
			var oXmlDom = new ActiveXObject(arrSignatures[i]);
			return oXmlDom;
		}
		catch (oError) {
	        //ignore
		}
	}
	throw new Error("你的系统没有安装MSXML");
}     
	
/*根据xml文件创建dom对象*/
function getDom(xmlFile) {
	var oXmlDom = createXMLDOM();
	oXmlDom.async = "false";
	oXmlDom.load(xmlFile);
	if (oXmlDom.parseError != 0) {
		var oError = oXmlDom.parseError;
		alert("an error occurred:\nError code:" + oError.errorCode + "\n" + "Line:" + oError.line + "\n" + "Line Pos:" + oError.linepos + "\n" + "Reason:" + oError.reason);
	}
	return oXmlDom;
}

