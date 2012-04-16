
var optstat='0';
function onLoadTableGrid() {
	tablegrid = new dhtmlXGridObject("tProperts");
	tablegrid.setImagePath("../../image/table/");
	tablegrid.setHeader("选中,编号,命名,关联表,查询条件,宽度,高度,允许拖拽,主子关系描述,父表编号");
	tablegrid.setInitWidths("50,80,80,100,100,60,60,60,100,80");
	tablegrid.setColAlign("center,center,center,center,center,center,center,center,center,center");
	tablegrid.setColTypes("ch,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	tablegrid.setColSorting("int,str,str,str,str,str,str,str,str,str");
	tablegrid.init();
	tablegrid.loadXML("common.datatable.getData.d");
	tablegrid.setOnRowSelectHandler(changeOptToTab);
	tablegrid.setOnRowSelectHandler(onLoadFieldGridByTid);
	//alert("table表加载成功");
}

function onLoadFieldGrid()
{
	fieldgrid = new dhtmlXGridObject("fProperts");
	fieldgrid.setImagePath("../../image/table/");
	fieldgrid.setHeader("选中,编号,表格编号,关联字段,字段名,列宽,显示类型,对齐方式,数据类型,码名映射SQL");
	fieldgrid.setInitWidths("50,80,80,80,80,80,80,80,80,80");
	fieldgrid.setColAlign("center,center,center,center,center,center,center,center,center,center");
	fieldgrid.setColTypes("ch,ro,ro,ro,ro,ro,ro,ro,ro,ro");
	fieldgrid.setColSorting("int,str,str,str,str,str,str,str,str,str");
	fieldgrid.init();
	fieldgrid.loadXML("common.datafield.getData.d");
	fieldgrid.setOnRowSelectHandler(changeOptToFld);
	//alert("Field表加载成功");
}


//设置Table和Field关联
function onLoadFieldGridByTid(){
	var url= "common.datafield.getDataByTableId.d";
	url=encodeURI(url);
	var params="tid="+getId();
	fieldgrid.clearAll();
	fieldgrid.loadXML(url+"?"+params);
	fieldgrid.setOnRowSelectHandler(changeOptToFld);
}

function onCheck()
{
	var tTRowNum = tablegrid.getRowsNum();
	var tTColNum = tablegrid.getColumnCount();
	var fTRowNum = fieldgrid.getRowsNum();
	var fTColNum = fieldgrid.getColumnCount();
	var tChkedNum = 0;
	var fChkedNum = 0;
	var isOK = true;
	for(var i=0;i<tTRowNum;i++)
	{
		if(tablegrid.cells2(i,0).getValue() == 1)
		{
			tChkedNum = tChkedNum +1;
			for(var j=1;j<tTColNum;j++)
			{
				if(tablegrid.cells2(i,j).getValue() == '')
				{
					alert("请确认表格设置选中的行中没有空列");
					isOK = false;
					break;
				}
			}
		}
	}
	for(var i=0;i<fTRowNum;i++)
	{
		if(fieldgrid.cells2(i,0).getValue() == 1)
		{
			fChkedNum = fChkedNum +1;
			for(var j=1;j<fTColNum;j++)
			{
				if(fieldgrid.cells2(i,j).getValue() == '')
				{
					alert("请确认字段设置选中的行中没有空列");
					isOK = false;
					break;
				}
			}
		}
	}
	if((tChkedNum == 0) || (fChkedNum ==0))
	{	
		isOK = false;
		alert("不能提交空的表单！");
	}
	return isOK;
	
}

function doGetTableData()
{
	var iColumnCount = tablegrid.getColumnCount();
	var iRowsNum = tablegrid.getRowsNum();
	var finArray = new Array();
	for(var i=0; i < iRowsNum; i++)
	{
		if (tablegrid.cells2(i,0).getValue() == 1)
		{
			var dataArray = new Array();
			for (var j = 1; j < iColumnCount; j++)
			{	
				var str = tablegrid.cells2(i, j).getValue();
				dataArray.push(str);
			}
			finArray.push(dataArray);
		}
	}
	return finArray;
}

function doGetFieldData()
{
	var iColumnCount = fieldgrid.getColumnCount();
	var iRowsNum = fieldgrid.getRowsNum();
	var finArray = new Array();
	
	for(var i=0; i < iRowsNum; i++)
	{
		if (fieldgrid.cells2(i,0).getValue() == 1)
		{
			var dataArray = new Array();
			for (var j = 1; j < iColumnCount; j++)
			{	
				var str = fieldgrid.cells2(i, j).getValue();
				dataArray.push(str);
			}
			finArray.push(dataArray);
		}
	}
	return finArray;
}

function showResponse()
{
	alert("数据录入成功");
}

function doFini()
{
	//alert('do fini');
}


function genXML()
{
	if(onCheck())
	{
		var tableXML = createXMLbody(doGetTableData(),'Tables');
		var fieldXML = createXMLbody(doGetFieldData(),'Fields');
		var xmlBody = tableXML + fieldXML;
		var fullbody = createFullXMLBody(xmlBody)
		var reEq = /=/g;
		fullbody = fullbody.replace(reEq,"$");
		var url="common.pageCust.settingReport.d";
		url = encodeURI(url);
		var params = "xmlstr="+ fullbody;
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showResponse, onSuccess:doFini});
	}
}

function getId()
{	
	var tmptable = null;
	if(optstat==='1'){
		tmptable = tablegrid;
	}else if(optstat==='2'){
		tmptable = fieldgrid;
	}
	var rowChosen=tmptable.getSelectedId();
	if(rowChosen !== null)
	{
		var Id;
		Id= tmptable.cells2(rowChosen, 1).getValue();			
		return Id;
	}
}	
/** 函数功能 ：删除 * 时间：2007-09-17*/
function doFieldDelete() {
  if(getFieldId()!=""){
	if (confirm("您确定删除此项记录吗?")) {
		var getparam = "";
		var url = "common.datafield.delete.d?getparam=" + getFieldId();
		url = encodeURI(url);
		var params = "postparam=" + getFieldId();
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseDelete, onSuccess:doFiniDelete});
	} else {
		return;
	}
		}else{
 alert("请至少选择一项记录进行此操作");
 }
}
function showResponseDelete(xmlHttp) {
	alert(xmlHttp.responseText);
	freshByFiled();
}
function doFiniDelete() {
	//alert("\u6b64\u8bb0\u5f55\u88ab\u6210\u529f\u5220\u9664");
}

/** 函数功能 ：获取每行记录的唯一标识,并放入数组，用于删除记录2007-10-17*/
function getFieldId() {
	var iRowsNum = fieldgrid.getRowsNum();
	var delArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {
		if (fieldgrid.cells2(i, 0).getValue() == 1) {
			var delid = fieldgrid.cells2(i, 1).getValue();
			delArray.push(delid);
		}
	}
	return delArray;
}
/** 函数功能 ：删除 * 时间：2007-09-17*/
function doTableDelete() {
  if(getTableId()!=""){
	if (confirm("您确定删除此项记录吗?")) {
		var url = "common.datatable.delete.d?getparam=" + getTableId();
		url = encodeURI(url);
		var params = "postparam=" + getTableId();
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseTableDelete, onSuccess:doFiniTableDelete});
	} else {
		return;
	}
		}else{
 alert("请至少选择一项记录进行此操作");
 }
}
function showResponseTableDelete(xmlHttp) {
	alert(xmlHttp.responseText);
	freshByTable();

}
function doFiniTableDelete() {
	//alert("\u6b64\u8bb0\u5f55\u88ab\u6210\u529f\u5220\u9664");
}

/** 函数功能 ：获取每行记录的唯一标识,并放入数组，用于删除记录2007-10-17*/
function getTableId() {
	var iRowsNum = tablegrid.getRowsNum();
	var delArray = new Array();
	for (var i = 0; i < iRowsNum; i++) {
		if (tablegrid.cells2(i, 0).getValue() == 1) {
			var delid = tablegrid.cells2(i, 1).getValue();
			delArray.push(delid);
		}
	}
	return delArray;
}

//将Table设置为可操作状态
function changeOptToTab(){
	optstat='1';
	var divopt=document.getElementById('optstat');
	//修改:郭亮亮
	//divopt.innerHTML="<a href=\"javascript:void(0);\" onclick=\"alert('你正在操作表格．．．');\"><font color=\"#FF0033\">表格．．．正处于可操作状态．．．</font></a>";
}
//将Field设置为可操作状态
function changeOptToFld(){
	optstat='2';
	var divopt=document.getElementById('optstat');
	//修改:郭亮亮
	//divopt.innerHTML="<a href=\"javascript:void(0);\" onclick=\"alert('你正在操作字段．．．');\"><font color=\"#FF0033\">字段．．．正处于可操作状态．．．</font></a>";	
}
function freshByTable() {
	FilterByTable(); 
}
function freshByFiled(){
     onLoadFieldGridByTid();
     FilterByField();
     }
     
 //datatable.jsp 中加载的toolbar

	function onButtonClick(itemId, itemValue) {
    var id = itemId;
    // alert(id);					
    if (id == "0_new") {
    	if(optstat=='0'){
    		alert('请选择操作对象．．．');
    	}else if(optstat=='1'){
      		if (confirm("此操作将改变系统配置，操作错误将影响系统正常工作，慎重选择，确认继续？")){
      		window.showModalDialog("../../ehl/systools/dataTableUtil.jsp?insrtOrUpdt=0&tid=''", "", "dialogWidth:430px;dialogHeight:430px");
      	    }
      	}else{
      		if (confirm("此操作将改变系统配置，操作错误将影响系统正常工作，慎重选择，确认继续？")){
      		window.showModalDialog("../../ehl/systools/dataFieldUtil.jsp?insrtOrUpdt=0&fid=''", "", "dialogWidth:430px;dialogHeight:430px");
      	    }
      	}
			
     } 
 if (id == "0_edit") {
     	if(optstat=='0'){
    	alert('请选择操作对象．．．');
    	 }else if(optstat=='1'){
     	 	if (confirm("此操作将改变系统配置，操作错误将影响系统正常工作，慎重选择，确认继续？")){
     	 	window.showModalDialog("../../ehl/systools/dataTableUtil.jsp?insrtOrUpdt=1&tid='"+ getId()+"'", "", "dialogWidth:430px;dialogHeight:430px");
     	    }
     	 }else{
     	    if (confirm("此操作将改变系统配置，操作错误将影响系统正常工作，慎重选择，确认继续？")){
     	 	window.showModalDialog("../../ehl/systools/dataFieldUtil.jsp?insrtOrUpdt=1&fid='"+ getId()+"'", "", "dialogWidth:430px;dialogHeight:430px");
     	 }
     	}
     } 
 if (id == "0_filter") {
		if(optstat=='0'){
		 alert('请选择操作对象....');
		 }else if(optstat=='1'){
		 FilterByTable();
		 }else{
		  
      	 FilterByField();}
     } 
 if (id == "0_save") {

     } 
 if (id == "0_fresh") {
		if(optstat=='0'){
		alert('请选择操作对象....');
		}
		else if(optstat=='1'){
		freshByTable();
		}else{
		freshByFiled();	}
     } 
 if (id == "0_delete") {
     if(optstat=='0'){
			alert('请选择操作对象．．．');
	}else if(optstat=='1'){
    			doTableDelete();		
	}else{
	          doFieldDelete();		
	}
 } 
 if (id == "0_print") {
     }
     };

 var tablegrid = null;
function FilterByTable() {
	var treename=document.getElementById("treename").value;
	var treepoint = document.getElementById("treepoint").value;
	var treeid = document.getElementById("treeid").value;
    var url = "common.datatable.filter.d";
	url = encodeURI(url);
	var params = "tid=" + treename + "&&tname=" + treepoint + "&&tablename=" + treeid;
	params=encodeURI(params);
	tablegrid.clearAll();//������������������������������
	tablegrid.loadXML(url + "?" + params);
}
var fieldgrid=null;
function FilterByField(){
	var fid=document.getElementById("fid").value;
	var fldname=document.getElementById("fldname").value;
	var displayname=document.getElementById("displayname").value;
	var url="common.datafield.filter.d";
	url=encodeURI(url);
	var params="fid="+fid+"&&fldname="+fldname+"&&displayname="+displayname;
	params=encodeURI(params);
	fieldgrid.clearAll();
	fieldgrid.loadXML(url+"?"+params);
	}
	
	// 查询
	function doFilter(){
	  if(optstat=='0'){
		 alert('请选择操作对象....');
		 }else if(optstat=='1'){
		 FilterByTable();
		 }else{
		  
      	 FilterByField();}
	}
	
	