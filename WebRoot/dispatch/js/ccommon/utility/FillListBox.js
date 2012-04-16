/**
 * @函数功能：填充列表框，发送Ajax请求数据.
 * @参数说明：containerID-加载列表框容器ID.
 * @参数说明：listID-列表框ID.
 * @参数说明：listWidth-宽度.
 * @参数说明：exeSql-SQL语句.
 * @参数说明：allDesc-全选描述(默认"全部").
 * @参数说明：callBack-回调函数.
 * @参数说明：chngFunc-onChange函数名称.
 * @参数说明：dsName-数据源名称.
 * @调用实例：fillListBox("tdsex","sex","150","SELECT dm,dmsm FROM td_code WHERE dmlb='1005'","全部","test()","doOnChange","dsName").
 */
function fillListBox(containerID,listID,listWidth,exeSql,allDesc,callBack,chngFunc,dsName){
	var url = 'common.fillListBox.createList.d';
	url = encodeURI(url);
	var params = "&ContainerID="+containerID+"&ListID="+listID+"&ListWidth="+listWidth+"&ExeSql="+exeSql.replace(/=/g,"@")+"&AllDesc="+allDesc+"&CallBack="+callBack+"&ChngFunc="+chngFunc+"&DsName="+dsName;
	params = encodeURI(params);
    new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showResponse});
}

/**
 * 函数功能：Ajax回调,解析请求返回的数据并展示出来
 */
function showResponse(xmlHttp){
   var strText = xmlHttp.responseText;
   var arrText = strText.split("&");
   var container = arrText[0];//父容器名称
   var conObj = document.getElementById(container);
   var callBack = arrText[1];//回调函数  
   var chngFunc = arrText[2];//onChange函数  
   conObj.innerHTML = arrText[3];//列表框实现

   if (callBack != ""){//执行回调函数     
   	  eval(callBack);
   }

   if (chngFunc != null){//加入OnChange()事件
	  var seleObj = conObj.firstChild;
  	  seleObj.onchange = eval(chngFunc);
   }
      
}

/**
 * @函数功能：创建下拉列表，下拉列表子项的值由使用者传递

 * @参数说明：selectId-准备生成的下拉列表的id.
 * @参数说明：containerId-下拉列表父容器ID.
 * @参数说明：selectValue-列表内容字符串.
 * @调用示例：var selectValue = "K,客车;H,货车;Q,牵引车;Z,专项作业车;D,电车;M,摩托车;N,三轮汽车;T,拖拉机;J,轮式机械;G,全挂车;B,半挂车;X,其它";
 *          createSelect('','',selectValue);
 */
 function createSelect(selectId,containerId,selectValue,changeFunc,width){
    
     var valueArray = selectValue.split(";");
     var inner = "<select id='"+selectId+"' class='text' style='width:"+width+"' onchange='"+changeFunc+"();'>";
     for(var i = 0; i < valueArray.length; i++){
         var perOptionArray = valueArray[i].split(",");
         inner += "<option value='"+perOptionArray[0]+"' />"+perOptionArray[1];
     }
     inner += "</select>";
	 document.getElementById(containerId).innerHTML = inner;
} 




