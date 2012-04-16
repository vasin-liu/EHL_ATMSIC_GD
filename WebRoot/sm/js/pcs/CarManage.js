/**
   * 说明：实现对车辆表进行 新增、编辑、过滤、删除、刷新操作
   */
var staticdeptcode;

 function onCarload() {	
	var strUrl = "pcs.carManage.onload.d";	
	strUrl = encodeURI(strUrl);	
	mygridt_car_s.clearAll();                                       //把表格的内容全部清空
	mygridt_car_s.loadXML(strUrl,pageLoaded);                      //重新加载参数
}    
 /** 
    * desc:  删除 
    */  
function doDelete() {
    var CLBM = getPId();
	if ( !CLBM.length < 1) {           //作删除的操作时，复选框选中的数组不为空时
		if (confirm("确认删除车辆?")) { //提示用户是否删除记录
			var strUrl = "pcs.carManage.delete.d";
			strUrl = encodeURI(strUrl);
			var params = "CLBM=" + CLBM;
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});
		} else {                                                   //放弃删除的操作的时候，跳出程序
			return;
		}
	} else {                                                      //当没有勾选复选框的时候，弹出此信息
		alert("\u8bf7\u81f3\u5c11\u9009\u62e9\u4e00\u6761\u8bb0\u5f55");
	}
}

function showResponseDelete(xmlHttp) {
	var xmlText = xmlHttp.responseText;
    if(xmlText=="ok"){
       alert("车辆删除成功！");
	   Filter();
    }else if(xmlText=="fail"){
       alert("车辆删除失败！");
       Filter();
    }else{
       alert(xmlText);
       return;
    }	
}
/** 
    * desc:  过滤数据.
    * author：郭田雨
    * date:   2008-04-16
    */
function Filter() {
	var HPZLObj = document.getElementById("HPZL");
    var HPHMLBObj = document.getElementById("HPHMLB");
	var carCodeObj = document.getElementById("cardCode");
	var CLZLObj = document.getElementById("CLZL");
	var SSJGObj = document.getElementById("SSJG");
	var hphm = carCodeObj.value;
	hphm = hphm.toUpperCase();//转换成大写
	
	if(!checkTextDataForNORMAL(carCodeObj.value) ){
	    alert("查询条件含有特殊字符，请检查！");
	    return;
	}
	var strUrl = "pcs.carManage.filter.d";
	if(G_jgID == null || typeof G_jgID =="undefined"){
	   G_jgID ="";
	}
	strUrl = encodeURI(strUrl);
	var params = "HPZL=" + HPZLObj.value + "&&HPHM=" + HPHMLBObj.value + hphm+ "&&CLZL=" + CLZL.value + "&&SSJG=" + G_jgID;
	params = encodeURI(params);                                   //进行编码转换
	mygridt_car_s.clearAll();                                   //把表格的内容全部清空
	mygridt_car_s.loadXML(strUrl + "?" + params, pageLoaded);    //重新加载参数
}
/** 函数功能 ：刷新页面*/
function fresh() {
	var HPZL = document.getElementById("HPZL");
	HPZL.value = "";
	var HPHMLB = document.getElementById("HPHMLB");
	HPHMLB.value = "";
	var cardCode = document.getElementById("cardCode");
	cardCode.value = "";	
	var CLZL = document.getElementById("CLZL");
	CLZL.value = "";
	var SSJG = document.getElementById("SSJG");
	SSJG.value = "";
	G_jgID = "";
	var strUrl = "pcs.carManage.onload.d";	
	strUrl = encodeURI(strUrl);	
	mygridt_car_s.clearAll();                                       //把表格的内容全部清空
	mygridt_car_s.loadXML(strUrl,pageLoaded);                      //重新加载参数
}
/** 
	* 功能 ：编辑 --将数据放入编辑框 
	* 时间：2008-04-16
	*/
function doQuery(CLBM) {
	if (CLBM != "null"&&CLBM != null) {
		var url = "pcs.carManage.getDataById.d";
		url = encodeURI(url);
		var params = "CLBM=" + CLBM;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showPerson});
	}
}
/** 
	* 功能 ：编辑页面带值

	* 时间：2008-04-16
	*/
function showPerson(xmlHttp) {

	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	
	var HPZLObj = document.getElementById("HPZL");
    var HPHMLBObj = document.getElementById("HPHMLB");
	var carCodeObj = document.getElementById("cardCode");
	var SSJGObj = document.getElementById("SSJG");
	var CLZLObj = document.getElementById("CLZL");
	var CLPPObj = document.getElementById("CLPP");
	var CJHObj = document.getElementById("CJH");
	var FDJHObj = document.getElementById("FDJH");
	var HDZKLObj = document.getElementById("HDZKL");
	var GZRQObj = document.getElementById("GZRQ");
	var SJXMObj = document.getElementById("SJXM");
	var LXDHObj = document.getElementById("LXDH");
	var GPS = document.getElementById("GPS");
	var CLZTObj = document.getElementById("CLZT");
	var SBZKObj = document.getElementById("SBZK");
	var RYZKObj = document.getElementById("RYZK");
	var CLXHObj = document.getElementById("CLXH");
	var HJHMObj = document.getElementById("HJHM");
	var CZTObj = document.getElementById("CZT");
	var CZTZHObj = document.getElementById("CZTZH");
	HPZLObj.value = results[0].text;
	HPHMLBObj.value = results[1].text.substr(0,1);
	carCodeObj.value = results[1].text.substr(1);
	SSJGObj.value = results[3].text;
	G_jgID = results[2].text;
	CLZLObj.value = results[4].text;
	CLPPObj.value = results[5].text;
	CJHObj.value = results[6].text;
	FDJHObj.value = results[7].text;
	HDZKLObj.value = results[8].text;
	GZRQObj.value = results[9].text;
	SJXMObj.value = results[10].text;
	LXDHObj.value = results[11].text;
	GPS.value = results[12].text;
	CLZTObj.value = results[13].text;
	SBZKObj.value = results[14].text;
	RYZKObj.value = results[15].text;
	CLXHObj.value = results[16].text;
	HJHMObj.value = results[17].text;
	CZTObj.value = results[18].text;
	CZTZHObj.value = results[19].text;
}

/** ---功能 ：增加、编辑 --绑定前端数据到后端进行业务处理 * 
    * 
    * 作者：郭田雨

    *
    * 时间：2008-03-17
    * 
    *修改:  2008-05-19 js验证数据格式
    */
function modify(insrtOrUpdt,CLDM) {
    if(CLDM == "null" || CLDM == null){
       CLDM = "";
    }
    window.returnValue = "fresh";
	//验证非法字符
	var ele_Array = document.getElementsByTagName("input");
	for(var i=0;i<ele_Array.length;i++){
	    if(!checkNormalStr(ele_Array[i].value)){
	      alert("不允许输入非法字符");
	      ele_Array[i].focus();
	      return;
	    }
     }
   
	var dataArray = new Array();
	var HPZLObj = document.getElementById("HPZL");
    var HPHMLBObj = document.getElementById("HPHMLB");
	var carCodeObj = document.getElementById("cardCode");
	var CLZLObj = document.getElementById("CLZL");
	var CLPPObj = document.getElementById("CLPP");
	var CJHObj = document.getElementById("CJH");
	var FDJHObj = document.getElementById("FDJH");
	var HDZKLObj = document.getElementById("HDZKL");
	var GZRQObj = document.getElementById("GZRQ");
	var SJXMObj = document.getElementById("SJXM");
	var LXDHObj = document.getElementById("LXDH");
	var CLXHObj = document.getElementById("CLXH");
	var HJHMObj = document.getElementById("HJHM");
	var CZTZHObj = document.getElementById("CZTZH");
	var GPS = document.getElementById("GPS");
	var CLZTObj = document.getElementById("CLZT");
	var SBZKObj = document.getElementById("SBZK");
	var RYZKObj = document.getElementById("RYZK");
	var CZTObj = document.getElementById("CZT");
	var SSJGObj = document.getElementById("SSJG");
   if(CLZTObj.value.Trim() == "") {
		alert("请输入车辆状态!");
		CLZTObj.focus();
		return;
	}
    if(!checkMath(HDZKLObj.value.Trim())) {
		alert("核定载客量填入了无效数字!");
		HDZKLObj.focus();
		return;
	}
	if(carCodeObj.value.Trim() == "") {
		alert("请输入号牌号码!");
		carCodeObj.focus();
		return;
	}
	if(GZRQObj.value.Trim() == "") {
		alert("请输入购置日期!");
		GZRQObj.focus();
		return;
	}
	if(HPZLObj.value.Trim() == "") {
		alert("请选择号牌种类!");
		HPZLObj.focus();
		return;
	}
    if(SSJGObj.value.Trim() == "") {
		alert("请选择所属单位!");
		return;
	}
	if(SSJGObj.value.Trim() == "undefined") {
		alert("请选择所属单位!");
		return;
	}
	
	if(!checkNormalStr(SBZKObj.value)){
	   alert("设备状况输入了无效字符！");
	   SBZKObj.focus();
	   return;
	}
	if(!checkNormalStr(RYZKObj.value)){
	   alert("人员状况输入了无效字符！");
	   RYZKObj.focus();
	   return;
	}
	var strHphm = HPHMLBObj.value+carCodeObj.value;
	strHphm = strHphm.toUpperCase();
	var strCJH=CJHObj.value;	
	var strFDJH=FDJHObj.value;	
	var strCLXH=CLXHObj.value;	
	var strHJHM=HJHMObj.value;	
	var strCZT=CZTObj.value;	
    var strCZTZ=CZTZHObj.value;	
	strCJH = strCJH.toUpperCase();//转成大写
	strFDJH = strFDJH.toUpperCase();//转成大写
	strCLXH = strCLXH.toUpperCase();//转成大写
	strHJHM = strHJHM.toUpperCase();//转成大写	
	strCZT = strCZT.toUpperCase();//转成大写
	strCZTZ = strCZTZ.toUpperCase();//转成大写

	dataArray.push(CLDM);
	dataArray.push(HPZLObj.value);
	dataArray.push(strHphm);
	dataArray.push(CLZLObj.value);
	dataArray.push(CLPPObj.value);
	dataArray.push(strCJH); 
	dataArray.push(strFDJH); 
	dataArray.push(HDZKLObj.value); 
	dataArray.push(GZRQObj.value); 
	dataArray.push(SJXMObj.value);
	dataArray.push(LXDHObj.value); 
	dataArray.push(GPS.value);
	dataArray.push(CLZTObj.value);
	dataArray.push(SBZKObj.value);
	dataArray.push(RYZKObj.value);
	dataArray.push(G_jgID);
	dataArray.push(strCLXH);
	dataArray.push(strHJHM);
	dataArray.push(strCZT);
	dataArray.push(strCZTZ);
	var url = "pcs.carManage.updateData.d";
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	var params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseCar});
}
function showResponseCar(xmlHttp) {
	var text = xmlHttp.responseText;
    if(text=="数据修改成功！"){
        alert(text);
        window.close();
        Filter()
    }else if(text=="数据录入成功！"){
        alert(text);
        window.close();
    }else{
 	    alert(text);
	    return;   
    }
}
  /** 
    * desc:  获得id
    * param:
    * return:
    * version:
    */
function getPId() {
	var iRowsNum = mygridt_car_s.getRowsNum();                   //获取表格总的行数
	
	var array = new Array();                                      //定义数组array
	for (var i = 0; i < iRowsNum; i += 1) {                       //循环遍历数据表的行

		if (mygridt_car_s.cells2(i, 0).getValue() == 1) {        //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态


			var strId = mygridt_car_s.cells2(i, 1).getValue();   //获取i 行 1 列单元格数据
			
			array.push(strId);
		}                           //将数据存入数组delArray
	}
	
	return array;
}

/** 
    * desc:  明细数据
    * param:
    * return:
    * author：ZHAOYU
    * date:   2008-11-20
    * version:
    */
function showInfo(CLBM) {
	if (CLBM !== "null") {
		var url = "pcs.carManage.getDataListById.d";
		url = encodeURI(url);
		var params = "CLBM=" + CLBM;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showListGetXMLResponse});
	}
}

/** 
    * desc:  明细页面写如数据.
    * param:
    * return:
    * author：zhaoyu
    * date:   2008-11-20
    * version:
    */
function showListGetXMLResponse(xmlHttp) {
//顺序是号牌种类，号牌号码，车辆种类，车辆品牌，车架号，发动机号，
//	 *       核定载客量，购置日期，司机姓名，联系电话，有无GPS，车辆状态，设备状况，人员状况，机构ID,车辆型号，呼叫号码
	
	var xmlDoc = xmlHttp.responseXML;
	
	var results = xmlDoc.getElementsByTagName("col");
	var HPZLObj = document.getElementById("HPZL");
    var HPHMObj = document.getElementById("HPHM");
	var CLZLObj = document.getElementById("CLZL");
	var CLPPObj = document.getElementById("CLPP");
	var CJHObj = document.getElementById("CJH");
	var FDJHObj = document.getElementById("FDJH");
	var HDZKLObj = document.getElementById("HDZKL");
	var GZRQObj = document.getElementById("GZRQ");
	var SJXMObj = document.getElementById("SJXM");
	var LXDHObj = document.getElementById("LXDH");
	var GPS = document.getElementById("GPS");
	var CLZTObj = document.getElementById("CLZT");
	var SBZKObj = document.getElementById("SBZK");
	var RYZKObj = document.getElementById("RYZK");
	var SSJGObj = document.getElementById("SSJG");
	var CLXHObj = document.getElementById("CLXH");
	var HJHMObj = document.getElementById("HJHM");
	var CZTObj = document.getElementById("CZT");
	var CZTZHObj = document.getElementById("CZTZH");
	
	HPZLObj.value = results[0].text;
	HPHMObj.value = results[1].text;
	CLZLObj.value = results[2].text;
	CLPPObj.value = results[3].text;
	CJHObj.value = results[4].text;
	FDJHObj.value = results[5].text;
	HDZKLObj.value = results[6].text;
	GZRQObj.value = results[7].text;
	SJXMObj.value = results[8].text;
	LXDHObj.value = results[9].text;
	if(results[10].text == 0){
	    GPS.value = "未安装";
	}else{
	    GPS.value = "已安装";
	}
	CLZTObj.value = results[11].text;
	SBZKObj.value = results[12].text;
	RYZKObj.value = results[13].text;
	SSJGObj.value = results[14].text;
	CLXHObj.value = results[15].text;
	HJHMObj.value = results[16].text;
	CZTObj.value = results[17].text;
	CZTZHObj.value = results[18].text;
}
/** 
    * desc:  新增与修改的重置函数
    * param:
    * return:
    * author：郭田雨
    * date:  2008-07-16
    * version:
    */
function reset() {
	var input;
	var select;
	var textarea;
	var queryTable = window.document.getElementById("dataTable");
	if (queryTable != null) {
		input = queryTable.getElementsByTagName("input");
		select = queryTable.getElementsByTagName("select");
		textarea = queryTable.getElementsByTagName("textarea");
	} else {
		input = document.getElementsByTagName("input");
		select = document.getElementsByTagName("select");
		textarea = document.getElementsByTagName("textarea");
	}
	for (var i = 0; i < input.length - 3; i++) {
		var item = input[i];
		if (item.type != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for (var i = 0; i < select.length; i++) {
		if (select[i].type != "button" && !select[i].readOnly) {
			//select[i].value = "-1";
			select[i].value = "";
		}
	}
	var strDepartmentName  = document.getElementById("SSJG");
	var strGZRQ  = document.getElementById("GZRQ"); 
	var strCZT  = document.getElementById("CZT"); 	
	strDepartmentName.value = "";	
	strGZRQ.value = "";	
	strCZT.value = "";	
}
  //查看车辆日志
  function showLog(){
     var CLBM = getPId();
	 if(CLBM.length >= 2 || CLBM.length < 1){
      alert("请选择一条数据!");
      return null;
     }
     var top=(screen.availHeight-460)/2;		
	 var left=(screen.availWidth-735)/2;
     window.open("CarLog.jsp?CLBM=" + CLBM , "", "width=625px,height=455px,top="+top+",left="+left);
  }
  
  //根据条件展示车辆日志 
 function loadLog(CLBM){
      mygridt_carlog_s.setImagePath("../../../sm/image/table/");
	  mygridt_carlog_s.setHeader(",流水号,车辆编码,车辆状态,记录日期");
	  mygridt_carlog_s.setInitWidths("0,140,140,140,185");
	  mygridt_carlog_s.setColAlign("center,center,center,center,center");
	  mygridt_carlog_s.setColTypes("ch,ro,ro,ro,ro");
	   mygridt_carlog_s.setColSorting("int,str,str,str,str");
	  mygridt_carlog_s.init();
     var params = "";
     if(typeof CLBM != "undefined" ){
        params = "CLBM="+CLBM;
     }
     var strUrl = "pcs.carManage.loadLog.d";
     params = encodeURI(params);   
     mygridt_carlog_s.clearAll();                                   //把表格的内容全部清空
	 mygridt_carlog_s.loadXML(strUrl + "?" + params, pageLoaded);    //重新加载参数
 }


  //链接到车辆明细
  function t_car_03(){
     var rId = mygridt_car_s.getSelectedId();
	 var str = mygridt_car_s.cells2(rId, 1).getValue();
	 window.showModalDialog("CarInfo.jsp?insrtOrUpdt=1&CLBM=" + str , "", "dialogWidth:410px;dialogHeight:655px");
  }
  
  //链接到机构明细
   function t_car_07(){
      var rId = mygridt_car_s.getSelectedId();
	var str = mygridt_car_s.cells2(rId, 10).getValue();
	str = encodeURI(str);
      window.showModalDialog("DepartmentInfo.jsp?jgid=" + str + "", "", "dialogWidth:650px;dialogHeight:450px");
   }
   
    function setValue(hpzl,hplb,clzl){
	var hpzlObj = document.getElementById("HPZL");
	var hplbObj = document.getElementById("HPHMLB");
	var clzlObj = document.getElementById("CLZL");
	if(hpzl==""){
	}else{
		//下拉列表中的值与查询的值相同则选中
		for (var i = 0; i < hpzlObj.length; i++) {	        
		    if (hpzlObj.options[i].value == hpzl) {				
			   hpzlObj.options[i].selected = true;
			}
	     }
     }
     if(clzl==""){
	}else{
		//下拉列表中的值与查询的值相同则选中
		for (var k = 0; k < clzlObj.length; k++) {	        
		    if (clzlObj.options[k].value == clzl) {				
			   clzlObj.options[k].selected = true;
			}
	     }
     }
     if(hplb==""){
	}else{
		//下拉列表中的值与查询的值相同则选中
		for (var j = 0; j < hplbObj.length; j++) {	        
		    if (hplbObj.options[j].value == hplb) {				
			   hplbObj.options[j].selected = true;
			}
	     }
     }
}
/**
  *名称：工具栏组件
  *功能 ： 可对当前文件进行save（保存）、New(增加)、Edit（修改）、Delete(删除)、Print（打印）操作
  */

	function onButtonClick(itemId, itemValue) {
		var id = itemId;
		if (id == "0_new") {
			var hpzlStr = document.getElementById("HPZL").value;
			var hplbStr = document.getElementById("HPHMLB").value;
			var clzlStr= document.getElementById("CLZL").value;;
			var returnValue = window.showModalDialog('CarModify.jsp?insrtOrUpdt=0&hpzlStr='+hpzlStr+'&hplbStr='+hplbStr+'&clzlStr='+clzlStr+'', "", "dialogWidth:420px;dialogHeight:650px");
			if(returnValue == "fresh")
			{
			   Filter();
			}
 		}
		if (id == "0_edit") {
		   var CLBM = getPId();
			if(CLBM.length >= 2 || CLBM.length < 1){
		      alert("请选择一条数据!");
		      return null;
		   }
			var returnValue = window.showModalDialog("CarModify.jsp?insrtOrUpdt=1&CLBM=" + CLBM , "", "dialogWidth:420px;dialogHeight:650px");
			    if(returnValue == "fresh")
				{
				  Filter();
				}
		}
		if (id == "0_delete") {
			doDelete();
		}
		if (id == "0_filter") {
			Filter();
		}

		if (id == "0_fresh") {
			fresh();
		}
		
		if (id == "0_excel") {
		
		    var dataSql = "select f_get_name('011001',HPZL),HPHM,f_get_name('013014',CLZL)";
		       dataSql+= ",CLPP,CJH,FDJH,HDZK,to_char(GZRQ,'yyyy-mm-dd'),SJXM,LXDH,SFZYGPS";
		       dataSql+= ",f_get_name('013015',CLZT),SBZK,RYZK ,F_GET_FULLDEPT(SSJG) ,CLXH,HJHM,CZT,CZTZH from t_sys_car";
		    var header = "号牌种类,号牌号码,车辆种类,车辆品牌,车架号,发动机号,核定载客,购置日期,司机姓名";
		        header +=",联系电话,是否置有GPS,车辆状态,设备状况,人员状况,所属机构,车辆型号,呼机号码,车载台,车载台组号";
			saveFieldsAsExcel("车辆信息列表", "car", header,dataSql);
		}
		
		if (id == "0_info") {
		    var CLBM = getPId();
			if(CLBM.length >= 2 || CLBM.length < 1){
		      alert("请选择一条数据!");
		      return null;
		   }
			else {
				window.showModalDialog("CarInfo.jsp?insrtOrUpdt=1&CLBM=" + CLBM , "", "dialogWidth:410px;dialogHeight:655px");
			}
		}
	}

