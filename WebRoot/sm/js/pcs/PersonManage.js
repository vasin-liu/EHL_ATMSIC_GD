
var staticdeptcode;
/**
    * 说明：实现对人员表进行 新增、编辑、过滤、删除、刷新操作    * 作者：  郭田雨    * 日期:  2008-04-16
    */
 function onPersonload() {	
	var strUrl = "pcs.personManage.onload.d";	
	strUrl = encodeURI(strUrl);	
	mygridt_pers_s.clearAll();                                       //把表格的内容全部清空
	mygridt_pers_s.loadXML(strUrl,pageLoaded);                      //重新加载参数
}    
 //删除 .通过ajax将获取到的数据的RYID值（人员编码）传入后台
function doDelete() {
	if (getDelId() != "") { 
		if (confirm("确认删除人员?")) { 
			var strUrl = "pcs.personManage.delete.d";
			strUrl = encodeURI(strUrl);
			var params = "getparam=" + getDelId();
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});
		} else {  
			return;
		}
	} else { 
		alert("\u8bf7\u81f3\u5c11\u9009\u62e9\u4e00\u6761\u8bb0\u5f55");
	}
}
/** 
    * 作者：  郭田雨    * 日期:  2008-04-16
    * 说明：    */
function getDelId() {
    //获取表格总的行数
	var iRowsNum = mygridt_pers_s.getRowsNum();                    
	var delArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {
	    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_pers_s.cells2(i, 0).getValue() == 1) { 
		    //获取单元格数据        
			var strId = mygridt_pers_s.cells2(i, 1).getValue();    
			delArray.push(strId);
		}
	}
	return delArray;
}
function showResponseDelete(xmlHttp) {
	var xmlText = xmlHttp.responseText;
    if(xmlText=="err"){
       alert("您不能删除其他单位人员！");
	   return;
    }else{
       alert(xmlText);
       Filter();
    }
}
/** 
    * desc:  过滤数据.
    * author：郭田雨
    * date:   2008-04-16
    */
function Filter() {
	var XM = document.getElementById("XM").value;
	var XB = document.getElementById("XB").value;
	var JH = document.getElementById("JH").value;
	var XRZW = document.getElementById("XRZW").value;
	var SSJG = document.getElementById("SSJG").value;
	if(!checkTextDataForNORMAL(XM) || !checkTextDataForNORMAL(JH)){
	    alert("查询条件含有特殊字符，请检查！");
	    return;
	}
	var strUrl = "pcs.personManage.filter.d";
	if(SSJG==null||SSJG==""){
		G_jgID="";
	
	}

	JH = JH.toUpperCase();//转成大写
	strUrl = encodeURI(strUrl);
	var params = "XM=" + XM + "&&JH=" + JH + "&&XRZW=" + XRZW + "&&SSJG=" + G_jgID+ "&&XB=" + XB;
	params = encodeURI(params);  //进行编码转换
	mygridt_pers_s.clearAll(); //把表格的内容全部清空
	mygridt_pers_s.loadXML(strUrl + "?" + params, pageLoaded);//重新加载参数
}
/** 函数功能 ：刷新页面*/
function fresh() {
	var xm = document.getElementById("XM");
	xm.value = "";
	var xb = document.getElementById("XB");
	xb.value = "";
	var JH = document.getElementById("JH");
	JH.value = "";
	var XRZW = document.getElementById("XRZW");
	XRZW.value = "";
	var SSJG = document.getElementById("SSJG");
	SSJG.value = "";
	G_jgID = "";
	var strUrl = "pcs.personManage.filter.d";
	strUrl = encodeURI(strUrl);
	//传入空参数  
	var params = "";
	params = encodeURI(params);//进行编码转换
	mygridt_pers_s.clearAll();//把表格的内容全部清空
	mygridt_pers_s.loadXML(strUrl + "?" + params, pageLoaded);//重新加载参数
}
/** 
	* 功能 ：编辑 --将数据放入编辑框 
	* 时间：2008-04-16
	*/
function doQuery(RYID) {
	if (RYID != "") {
		var url = "pcs.personManage.getDataById.d";
		url = encodeURI(url);
		var params = "RYID=" + RYID;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showPerson});
	}
}
/** 
	* 功能 ：编辑页面带值
	* 时间：2009-04-09
	*/
function showPerson(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var inputArray = document.getElementsByTagName("input");
	
	var ele_XM = document.getElementById("XM");
    var ele_XB = document.getElementById("XB");
	var ele_CSRQ = document.getElementById("CSRQ");
	var ele_SFZH = document.getElementById("SFZH");
	var ele_JH = document.getElementById("JH");
	var ele_JX = document.getElementById("JX");
	var ele_BZLX = document.getElementById("BZLX");
	var ele_XRZW = document.getElementById("XRZW");
	var ele_SJ = document.getElementById("SJ");
	var ele_ST = document.getElementById("ST");
	var ele_BGDH = document.getElementById("BGDH");
	var ele_JTDH = document.getElementById("JTDH");
	var ele_SSJG = document.getElementById("SSJG");
	
	var ele_STZH = document.getElementById("STZH");
	var fileObj = document.getElementById("fileObjId");
	var ele_STHH = document.getElementById("STHH");
	var ele_TQJB = document.getElementById("TQJB");
	if(ele_XB == null || ele_JX == null || ele_BZLX == null ||ele_XRZW == null){
	     setTimeout("doQuery('"+results[15].text+"')",100);
	     return;
	}
	
    document.getElementById("pic3").src = results[14].text;
	ele_XM.value = results[0].text;
	
	
	//性别下拉列表框中对应值选中选择
	for (var i = 0; i < ele_XB.length; i++) {	 
        //下拉列表中的值与查询的值相同则选中
	   if (ele_XB.options[i].text == results[1].text) {
		  ele_XB.options[i].selected = true;
	   }
	}
	ele_CSRQ.value = results[2].text;
	ele_SFZH.value = results[3].text;
	ele_JH.value = results[4].text;
	
	for (var i = 0; i < ele_JX.length; i++) {
           //下拉列表中的值与查询的值相同则选中
		if (ele_JX.options[i].text == results[5].text) {			
			ele_JX.options[i].selected = true;
		}
	}		
	//编制类型下拉列表框中对应值选中选择
	for (var i = 0; i < ele_BZLX.length; i++) {	 
           //下拉列表中的值与查询的值相同则选中
		if (ele_BZLX.options[i].text == results[6].text) {
			ele_BZLX.options[i].selected = true;
		}
	}
	//现任职务下拉列表框中对应值选中选择
	for (var i = 0; i < ele_XRZW.length; i++) {	 
           //下拉列表中的值与查询的值相同则选中
		if (ele_XRZW.options[i].text == results[7].text) {
			ele_XRZW.options[i].selected = true;
		}
	}
	//特勤级别下拉列表框中对应值选中选择
	for (var i = 0; i < ele_TQJB.length; i++) {	 
           //下拉列表中的值与查询的值相同则选中
		if (ele_TQJB.options[i].text == results[17].text) {
			ele_TQJB.options[i].selected = true;
		}
	}	
	ele_SJ.value = results[8].text;
	ele_ST.value = results[9].text;
	ele_BGDH.value = results[10].text;
	ele_JTDH.value = results[11].text;
	ele_SSJG.value = results[12].text;
	ele_STZH.value = results[13].text;
	ele_STHH.value = results[16].text;
	G_jgID = results[18].text;
}

/** ---功能 ：增加、编辑 --绑定前端数据到后端进行业务处理 * 
    * 作者：郭田雨
    * 时间：2008-03-17
    *修改:  2008-05-19 js验证数据格式
    */
function modify(insrtOrUpdt,RYID) {
	//验证非法字符
	window.returnValue = "fresh";
	var ele_Array = document.getElementsByTagName("input");
	for(var i=0;i<ele_Array.length-3;i++){
	    if(!checkNormalStr(ele_Array[i].value)){
	      alert("不允许输入非法字符");
	      ele_Array[i].focus();
	      return;
	    }
     }
	var dataArray = new Array();
	var ele_XM = document.getElementById("XM");
    var ele_XB = document.getElementById("XB");
	var ele_CSRQ = document.getElementById("CSRQ");
	var ele_SFZH = document.getElementById("SFZH");
	var ele_JH = document.getElementById("JH");
	var ele_JX = document.getElementById("JX");
	var ele_BZLX = document.getElementById("BZLX");
	var ele_XRZW = document.getElementById("XRZW");
	var ele_SJ = document.getElementById("SJ");
	var ele_ST = document.getElementById("ST");
	var ele_STHH = document.getElementById("STHH");
	var ele_STZH = document.getElementById("STZH");
	var ele_BGDH = document.getElementById("BGDH");
	var ele_JTDH = document.getElementById("JTDH");
	var SSJG = document.getElementById("SSJG");
	var RYIDObj = document.getElementById("RYIDForAdd");
	var ele_TQJB = document.getElementById("TQJB");
	if(ele_XM.value.Trim() == "") {
		alert("请输入人员姓名!");
		ele_XM.focus();
		return;
	}
	if(isChinese(ele_SFZH.value))
	{   
	    alert("身份证不能包含汉字！");
		ele_SFZH.focus();
		return;
	}
	if(!checkMath(ele_SJ.value))
    {
        alert("请正确输入负责人手机号码!");
        ele_SJ.focus();
		return;
    }
    if(ele_SJ.value.Trim()!="")
    {
      if(ele_SJ.value.length!=11)
      {
         alert("手机号码输入有误,请重新输入!");
         ele_SJ.focus();
         return;
      }
    }else{
         alert("请输入手机号码!");
         ele_SJ.focus();
         return;
    }
    
    if(SSJG.value.Trim() == "") {
		alert("请选择所属单位!");
		return;
	}
	if(SSJG.value.Trim() == "undefined") {
		alert("请选择所属单位!");
		return;
	}
	if(RYID==null||(RYID).Trim()=="")
	{
	  dataArray.push("0");
	
	}else{
	  dataArray.push(RYID);
	}
	var strJH=ele_JH.value;	
	var strST=ele_ST.value;	
	var strSTZH=ele_STZH.value;	
	var strSTHH=ele_STHH.value;	
	strJH = strJH.toUpperCase();//转成大写
	strST = strST.toUpperCase();//转成大写
	strSTZH = strSTZH.toUpperCase();//转成大写
	strSTHH = strSTHH.toUpperCase();//转成大写
	dataArray.push(ele_XM.value);
	dataArray.push(ele_XB.value);
	dataArray.push(ele_CSRQ.value);
	dataArray.push(ele_SFZH.value);
	dataArray.push(strJH); 
	dataArray.push(ele_JX.value); 
	dataArray.push(ele_BZLX.value); 
	dataArray.push(ele_XRZW.value); 
	dataArray.push(ele_SJ.value);
	dataArray.push(strST); 
	dataArray.push(ele_BGDH.value);
	dataArray.push(ele_JTDH.value);
	dataArray.push(G_jgID);
	dataArray.push(strSTZH);
	dataArray.push(RYIDObj.value);
	dataArray.push(strSTHH);
	dataArray.push(ele_TQJB.value);
	//提交照片并以人员编号为名称保存
	var formObj = document.getElementById("uploadform");
	var fileObj = document.getElementById("fileObjId");
	var fileName = fileObj.value;
	if(fileName != ""){
	   if( "GIF,JPG,JPEG,BMP".indexOf(fileName.split(".")[1].toUpperCase()) == -1){
          fileObj.value = "";
          alert("照片必须是图片文件！");
          return;
       }
       formObj.submit();
	}
    //保存新增人员信息
	var url = "pcs.personManage.updateData.d";
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	var params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseWin});
}
function showResponseWin(xmlHttp) {
    if(xmlHttp.responseText.split(",")[1] == "0" && xmlHttp.responseText.split(",")[0] =="successed"){
      alert("人员新增成功！");
      closeThis();
    }else if(xmlHttp.responseText.split(",")[1] == "1" && xmlHttp.responseText.split(",")[0] =="successed"){
	  alert("人员修改成功！");
	  closeThis();
    }else if(xmlHttp.responseText == "err"){
       alert("您不能编辑其他单位人员！");
        return;  
    }else{
       alert(xmlHttp.responseText);
        return;  
    }   
}

function showAddResult(xmlHttp){

}
  /** 
    * desc:  获得id
    * author：郭田雨
    * date:   2008-07-19
    * version:
    */
function getPId() {
    //获取表格总的行数
	var iRowsNum = mygridt_pers_s.getRowsNum();                      
	var array = new Array();
	//循环遍历数据表的行 
	for (var i = 0; i < iRowsNum; i += 1) {                       
        //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
		if (mygridt_pers_s.cells2(i, 0).getValue() == 1) {        
			var strId = mygridt_pers_s.cells2(i, 1).getValue(); 
			array.push(strId);
		}   
	}
	if (array.length >= 2 || array.length < 1) {
		alert("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		return;
	}
	return array[0];
}

/** 
    * desc:  明细数据
    * author：郭田雨
    * date:   2008-07-14
    * version:
    */
function doList(RYID) {
	if (RYID !== "") {
		var url = "pcs.personManage.getDataListById.d";
		url = encodeURI(url);
		var params = "RYID=" + RYID;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showListGetXMLResponse});
	}
}

/** 
    * desc:  明细页面写如数据.顺序是姓名，性别，出生日期，身份证号，警号，警衔，机构名称，
    *          编制类型，现任职务，手机，手台，办公电话，家庭电话，手台组号
    * author：郭田雨
    * date:   2008-07-16
    * version:
    */
function showListGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var ele_XM = document.getElementById("XM");
    var ele_XB = document.getElementById("XB");
	var ele_CSRQ = document.getElementById("CSRQ");
	var ele_SFZH = document.getElementById("SFZH");
	var ele_JH = document.getElementById("JH");
	var ele_JX = document.getElementById("JX");
	var ele_BZLX = document.getElementById("BZLX");
	var ele_XRZW = document.getElementById("XRZW");
	var ele_SJ = document.getElementById("SJ");
	var ele_ST = document.getElementById("ST");
	var ele_BGDH = document.getElementById("BGDH");
	var ele_JTDH = document.getElementById("JTDH");
	var ele_SSJG = document.getElementById("SSJG");
	var ele_STZH = document.getElementById("STZH");
	var ele_STHH = document.getElementById("STHH");
	var ele_TQJB = document.getElementById("TQJB");
	var fileObj = document.getElementById("fileObjId");
		
	ele_XM.value = results[0].text;
	ele_XB.value = results[1].text;
	ele_CSRQ.value = results[2].text;
	ele_SFZH.value = results[3].text;
	ele_JH.value = results[4].text;
	ele_JX.value = results[5].text;
	ele_SSJG.value = results[6].text;
	ele_BZLX.value = results[7].text;
	ele_XRZW.value = results[8].text;
	ele_SJ.value = results[9].text;
	ele_ST.value = results[10].text;
	ele_BGDH.value = results[11].text;
	ele_JTDH.value = results[12].text;
	ele_STZH.value = results[13].text;
	ele_STHH.value = results[15].text;
	ele_TQJB.value = results[16].text;
	document.getElementById("pic3").src = results[14].text;
}
/** 
    * desc:  新增与修改的重置函数
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
}
//打开机构树页，并将所选的机构写入文本框
function deptTree(){
	var deptcode = window.showModalDialog("DepartmentSelect.jsp", "", "dialogWidth:380px;dialogHeight:500px");
	if (deptcode == undefined) {
	} else {
		var SSJG = document.getElementById("SSJG");
		if(SSJG!=""){
			var dept= deptcode.split(",");
			SSJG.value = dept[1];
			staticdeptcode = dept[0];
		}
	}
	
}
function checkDept(){
	if(tree.getSelectedItemId()==''){
		alert("请选择所属机构");
		window.returnValue = tree.getSelectedItemText();
    } else {
		alert("您选的部门是："+ tree.getSelectedItemText());
		window.returnValue = tree.getSelectedItemId()+","+tree.getSelectedItemText();
		window.close();
	}
}

	/**
	* 弹出页面以便对显示顺序进行编辑
	*/
	function setOrder(){
	   var top=(screen.availHeight-460)/2;		
	   var left=(screen.availWidth-735)/2;
	   if(typeof staticdeptcode == "undefined"){
	       staticdeptcode = "";
	   }
	   window.showModalDialog("PersonOrder.jsp?jgid="+G_jgID, "", "dialogWidth:640px;dialogHeight:565px");
  }
  
  //展示人员显示序号信息 
   function loadPerson(ssjg){

    tablegrid = new dhtmlXGridObject("t_personorder_s");
	tablegrid.setImagePath("../../../sm/image/table/");
	tablegrid.setHeader("1,1,显示序号,姓名,性别,所属机构,现任职务");
	tablegrid.setInitWidths("0,0,50,120,40,180,175");
	tablegrid.setColAlign("center,center,center,center,center,center,center");
	tablegrid.setColTypes("ro,ro,ed,ro,ro,ro,ro");
	tablegrid.setColOrder("int,str,int,str,str,str,str");
	tablegrid.init();
	tablegrid.clearAll();
	tablegrid.loadXML("pcs.personManage.personForOrder.d?ssjg=" + ssjg);
 }
 
  function saveOrder(){
     if(!confirm("确定保存当前设置？")){
        return;
     }
     //获取表格总的行数
     var iRowsNum = tablegrid.getRowsNum();                      
	 var array = new Array(); 
	 for (var i = 0; i < iRowsNum; i += 1) { //循环遍历数据表的行
			var strId = tablegrid.cells2(i, 1).getValue();    //获取人员编号
			var strXh = tablegrid.cells2(i, 2).getValue();    //获取显示序号
			array.push(strId);
			array.push(strXh);
		                    
	 }
	 
	 
     var xmlbody = createLineXMLBody(array, "RFWin");
	 var fullbody = createFullXMLBody(xmlbody);
	 var reEq = /=/g;
	 fullbody = fullbody.replace(reEq, "$");
	 var url = "pcs.personManage.saveOrder.d";
	 var params = "xmlStr=" + fullbody ;
	 new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseWin});
     var strUrl = "pcs.personManage.saveOrder.d";
  }

  //获取选记录的人员ID 展示其明细
   function f_pers_02() {
	var rId = mygridt_pers_s.getSelectedId();
	var str = mygridt_pers_s.cells2(rId, 1).getValue();
	window.showModalDialog("PersonList.jsp?RYID=" + str + "", "", "dialogWidth:460px;dialogHeight:620px");
	
  }
  //获取选记录机构 展示其明细
  function f_pers_10(){
    var rId = mygridt_pers_s.getSelectedId();
	var str = mygridt_pers_s.cells2(rId, 4).getValue();
	str = encodeURI(str);
	window.showModalDialog("DepartmentInfo.jsp?jgid=" + str + "", "", "dialogWidth:650px;dialogHeight:450px");
  }
  
  function loadDeptInfo(jgid){
     var url = "pcs.personManage.getDeptByJGID.d?jgid=" + jgid ;
	 var params = "";
	 url = encodeURI(url);
	 new Ajax.Request(url, {method:"post", parameters:params, onComplete:showDeptInfo});
  }
  /**展示机构明细
  *  后台数据顺序是机构名称 行政级别 机构职能 业务描述 机构类型 所处地址 邮政编码 
  *  编制人数 在编民警数 在编职工数 其他人数 负责人 负责人电话 负责人手机 值班电话1 值班电话2 值班电话3 传真电话
  */
  function showDeptInfo(xmlHttp){
     var xmlDoc =  xmlHttp.responseXML;
     var rows = xmlDoc.getElementsByTagName("row");
     for(var i = 0; i < rows.length; i++){
        var res = rows[i].getElementsByTagName("col");
        document.getElementById("JGMC").value = res[0].text;
		document.getElementById("XZJB").value  = res[1].text;
		document.getElementById("JGZN").value  = res[2].text;
		document.getElementById("YWMS").value = res[3].text;
		document.getElementById("JGLX").value = res[4].text;
		document.getElementById("SCDZ").value = res[5].text;
		document.getElementById("YZBM").value = res[6].text;
		document.getElementById("BZRS").value = res[7].text;
	    document.getElementById("ZBMJS").value = res[8].text;
	    document.getElementById("ZBZGS").value = res[9].text;
		document.getElementById("QTRS").value = res[10].text;
		document.getElementById("FZR").value = res[11].text;
	    document.getElementById("FZRDH").value = res[12].text;
	   document.getElementById("FZRSJ").value  = res[13].text;
	   document.getElementById("ZBDH1").value  = res[14].text;
	   document.getElementById("ZBDH2").value  = res[15].text;
	   document.getElementById("ZBDH3").value = res[16].text;  
	   document.getElementById("CZDH").value  = res[17].text; 
     }
		
  }
  
  
  //双击以原路径打开图片
function showNormallyZP(picNo){
    
 	var imageObj =  document.getElementById(picNo);
 	var imageUrl = imageObj.getAttribute("src");
 	if(imageUrl.indexOf("http") < 0){
      
    }else{
       window.open(imageUrl,"","width=800,height=520,left="+eval(screen.Width-800)/2+",top="+eval(screen.Height-560)/2+"");
    }
 	
}
 /**打开上传照片窗口
 * param:ryid-新增人员编号，用于命名上传照片；appUrl-setting.xml中redirecturl对应值，用于获得应用服务名
 * 如："/EHL_Manager/sm/index.jsp"
 */
 function upLoadPhoto(ryid){
    var fileObj = document.getElementById("fileObjId");
    fileName = fileObj.value;
    document.getElementById("pic3").src = fileObj.value;
    if("GIF,JPG,JPEG,BMP".indexOf(fileName.split(".")[1].toUpperCase()) == -1){
        fileObj.value = "";
        alert("必须是图片文件！");
        
        return;
    }
 }
 
 
 
 /**
  *
  *功能 ： 刷新父页面数据，关闭子窗口

  */
 function closeThis(){
    window.opener.fresh();
     window.close();
     
 }
 //初始人员性别
 function setValue(xb){
	var xbObj = document.getElementById("XB");
	if(xb==""){
	}else{
		//下拉列表中的值与查询的值相同则选中
		for (var i = 0; i < xbObj.length; i++) {	        
		    if (xbObj.options[i].value == xb) {				
			   xbObj.options[i].selected = true;
			}
	     }
     }
}
/**
  *名称：工具栏组件
  *功能 ： 可对当前文件进行增加、修改、删除、明细、转存Excel、查询操作
  */

	function onButtonClick(itemId, itemValue) {
		var id = itemId;
		if (id == "0_new") {//触发新增事件，打开新增页面	
		    var top=(screen.availHeight-540)/2;		
	        var left=(screen.availWidth-380)/2;
			var  rev = window.open("PersonEdit.jsp?insrtOrUpdt=0", "", "Width=460px,Height=600px,top="+top+",left="+left);
			if(rev == "fresh"){
			  Filter();
			}
			
 		}
		if (id == "0_edit") {//触发编辑事件，打开编辑页面	
			if (getPId() == undefined) {
				//alert("请选择一条记录！");
			} else {
			    var top=(screen.availHeight-540)/2;		
	            var left=(screen.availWidth-380)/2;
				var  rev = window.open("PersonEdit.jsp?insrtOrUpdt=1&RYID=" + getPId() + "", "", "Width=460px,Height=600px,top="+top+",left="+left);
				if(rev == "fresh"){
				  Filter();
				}
			}
		}
		if (id == "0_delete") {
			doDelete();//触发删除事件	
		}
		if (id == "0_filter") {
			Filter();//触发过滤事件	
		}

		if (id == "0_fresh") {
			fresh();//触发刷新事件
		}
		
		if (id == "0_excel") {//触发转存事件	
		    var dataSql = "SELECT XM,F_GET_NAME('011005',XB),to_char(CSRQ,'yyyy-mm-dd'),SFZH,JH";
		       dataSql+= ",F_GET_NAME('013009',JX),F_GET_FULLDEPT(SSJG),F_GET_NAME('013012',BZLX)";
		       dataSql+= ",F_GET_NAME('013011',XRZW),SJ,ST,BGDH,JTDH,STZH FROM T_SYS_PERSON";
		    var header = "姓名,性别,出生日期,身份证号,警号,警衔,所属机构,编制类型,现任职务,手机";
			    header +=",手台,办公电话,家庭电话,手台组号";
			saveFieldsAsExcel("\u4eba\u5458\u57fa\u672c\u4fe1\u606f", "Person", header,dataSql);
		}
		
		if (id == "0_info") {//触发明细事件，打开明细页面	
			if (getPId() == undefined) {
				//alert("请选择一条记录！");
			} 
			else {
				window.showModalDialog("PersonList.jsp?RYID=" + getPId() + "", "", "dialogWidth:460px;dialogHeight:620px");
			}
		}
	}

