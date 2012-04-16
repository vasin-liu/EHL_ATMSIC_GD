   /** 
	*作者：wyl
	*函数功能:生成24时的选择列表，默认为"" 
	*时间:2008-09-24
	*/
function getHours(){
	var txtHours="";
	txtHours +="<option value=''>--</option>"; //默认
	txtHours +="<option value=00>00</option>"; //零时  
	var isSELECT = "";
	for(var i = 1; i< 24; i++){
		if(i < 10){
		  	txtHours += "<option value='0" + i + "'" + isSELECT + ">0" + i + "</option>";
		}else{
		  	txtHours += "<option value=" + i + " " + isSELECT + ">" + i + "</option>";
		} 
	}
    return 	txtHours;
}

   /**
	* 作者：wyl 
	* 函数功能:24时双下拉框显示 
	* 时间:2008-09-18
	*/
function doLoadHours(){
	var txtTab = "<select name=\"Hours\" id=\"Hours\">";
	txtTab += getHours();//加载"时"下拉框       
	txtTab += "</select>&nbsp;时---&nbsp;";
	txtTab += "<select name=\"nHours\" id=\"nHours\">";
	txtTab += getHours();      
	txtTab += "</select>&nbsp;时";		
	var obj =  window.document.getElementById('divHours'); 
	var html = obj.innerHTML; 
	obj.innerHTML = txtTab; 
}

/** 
*作者linbh
*函数功能:获取系统时间
*参数:offset偏移分钟数*/
   function getSysdate(offset,ignoreTime){
	  var nowDate = new Date();	
	  var cNowDate = new Date(nowDate-offset*60*1000);
	  if(ignoreTime == true){
         var strDateTime = cNowDate.getYear() + "-" + getFull(cNowDate.getMonth()+1) + "-" + getFull(cNowDate.getDate());
      }else{
         var strDateTime = cNowDate.getYear() + "-" + getFull(cNowDate.getMonth()+1) + "-" + getFull(cNowDate.getDate()) + ' ' + getFull(cNowDate.getHours()) + ":" + getFull(cNowDate.getMinutes()); 
      }
      return strDateTime;
   }
   //如果numVar为一位则左侧加零
   function getFull(numVar){
	  if((numVar+"").length >=2){
	     return numVar;
	  }else{
	     return "0"+numVar;
	  }
   }
/** 
*作者wangyali
*函数功能:js在两个列表框之间传值
*时间:2008-11-18
*/
function ifExist(objName,optionValue)
{   //判断值是否在列表中已存在
    var objLength = getSelectLength(objName);
    var obj = getElement(objName);
    for(var i = 0;i<objLength;i++)
    {
        if(obj.options[i].innerText == optionValue)
        {
          
            return true;
        }
    }
    return false;
}
function ifSelect(objName)
{   //判断是否被中
    var selIndex = SelectIndex(objName);
    if(selIndex == -1)
    {
        return false;
    }
    else
    {
        return true;
    }
}
function selectInnerText(objName)
{
     //返回选中的行的文本值
     return getElement(objName).options[SelectIndex(objName)].innerText;
}
function selectInnerValue(objName)
{
     //返回选中的行的文本值
     return getElement(objName).options[SelectIndex(objName)].value;
}
function removeTo(from,to)
{
    if(!ifSelect(from))
    { 
        alert("没有选项被选中");
        return;
    }
    if(ifExist(to,getElement(from).options[SelectIndex(from)].value))
    {
        alert("选项已存在！");
        return;
    }
    var oOption = getElement(from).options[SelectIndex(from)];
    getElement(from).removeChild(oOption);
    getElement(to).appendChild(oOption);
}
function remChild(objName)
{   
    var oOption = getElement(objName).options[SelectIndex(objName)];
    getElement(objName).removeChild(oOption);
}
function SelectIndex(objName)
{       
    //返回元素选中行
    return　getElement(objName).selectedIndex;  
}
function getElement(eleID)
{   
	//返回元素
    return document.getElementById(eleID);
}
function removeAll(from,to)
{
    var selLength = getSelectLength(from);
    for(var i = 0;i<selLength;i++)
    {
        getElement(to).appendChild(getElement(from).options[0]);
    }
}
function setFocus(objName)
{
    getElement(objName).selectedIndex = -1;
}

function changeOption(obj)
{
    getElement("txtUpdate").value = getElement(obj).options[SelectIndex(obj)].value;
}
function getSelectLength(IDStr)
{
    var selLength = getElement(IDStr).length;
    return selLength;
}

/**
 * 打开视频.
 */
function showVideo(tgsID)
{
	if (tgsID != "") {
		var url = "vcas.tgsandcam.getDataById.d";
		url = encodeURI(url);
		var params = "KKBM="+tgsID;
				
		new Ajax.Request(url, 
		{
			method:"post", parameters:params, 
			onComplete:function(xmlHttp){
				var xmldoc   = xmlHttp.responseXML;
				var results = xmldoc.getElementsByTagName("col");
	
				var strIP = results[2].text;
				var strPort = results[3].text;
				var strUser = results[4].text;
				var strPassword = results[5].text;
				
		    	var url = "../../../cctv/ehl/cctv/VideoTGSPlayback.jsp?DVRIP='" + encodeURI(strIP)+ "'&DVRPort='" + encodeURI(strPort) 
		    			  + "'&DVRLoginUser='" + encodeURI(strUser) + "'&DVRLoginPWD='" + encodeURI(strPassword) + "'" ;
		    	url = encodeURI(url);
		    	var wLeft = (screen.availWidth - 670) / 2;
		    	var wTop = (screen.availHeight - 550) / 2;
		    	window.open(url,"","height=550,width=670,left=" + wLeft +",top=" + wTop + ",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=yes");
		    	
			}
		}
		);
	}else{
		alert("卡口视频通讯异常！");
	}
}
