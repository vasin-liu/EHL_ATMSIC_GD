var modifyOrnot = 1;
var mygridt00=null;

  /** 
    * desc:  获得高亮显示id
    * param:
    * return:
    * author：郭田雨
    * date:   2008-07-19
    * version:
    */
function getRFId()
{		
	var iRowsNum = mygridt_dw_s.getRowsNum();                      //获取表格总的行数
		
		var array = new Array();                                //定义数组array
		for (var i = 0; i < iRowsNum; i+=1) {                       //循环遍历数据表的行

				if ( mygridt_dw_s.cells2(i, 0).getValue() == 1) {         //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态

				var strId = mygridt_dw_s.cells2(i, 1).getValue();    //获取i 行 1 列单元格数据
				array.push(strId);  
				}                           //将数据存入数组delArray
			}
		if(array.length >= 2||array.length<1){
			alert("请选择一条记录");
			return;
		}
		return array[0];
	}

function onCheck()
{
	var iColumnCount = mygridt_ck_s.getColumnCount();
	var iRowsNum = mygridt_dw_s.getRowsNum();
	var chkedNum = 0;
	var isOK = true;
	for(var i=0;i<iRowsNum;i++)
	{
		if(mygridt_ck_s.cells2(i,0).getValue() == 1)
		{
			chkedNum = chkedNum +1;
			if(mygridt_dw_s.cells2(i,1).getValue() == '' || mygridt_dw_s.cells2(i,2).getValue() == '')
			{
				alert("请先填入编号和报表名");
				isOK = false;
				return isOK;
			}
		}
		
	}
	if(chkedNum == '0')
	{
		alert("无法提交空表单！");
		isOK = false;
		return isOK;
	}
	return isOK;
		
}

function nextRowNum()
{
	var thisRowNum = mygridt_dw_s.getRowsNum();
	return thisRowNum;
}

function doGetData()
{
	var iColumnCount = mygridt_ck_s.getColumnCount();
	var iRowsNum = mygridt_dw_s.getRowsNum();
	var finArray = new Array();
	
	for(var i=0; i < iRowsNum; i++)
	{
		if (mygridt_dw_s.cells2(i,0).getValue() == 1)
		{
			var dataArray = new Array();
			for (var j = 1; j < iColumnCount; j++)
			{	
				var str = mygridt_dw_s.cells2(i, j).getValue();
				dataArray.push(str);
			}
			finArray.push(dataArray);
		}
	}
	return finArray;
}



function genXML()
{
	if(onCheck() == true)
	{
		var body = createXMLbody(doGetData(),'RFWin');
		var reEq = /=/g;
		var fullbody = createFullXMLBody(body);
		fullbody = fullbody.replace(reEq,"$");
		var url="common.pageCust.settingReport.d";
		url = encodeURI(url);
		var params = "xmlStr="+ fullbody;
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showResponse, onSuccess:doFini});
	}
}

function showResponse()
{
	
}

function doFini()
{
	
}
function doFilter(){
    
    var dname = document.getElementById("dname").value;
    var dvalue = document.getElementById("dvalue").value;
    var url = "common.datawin.filter.d";
    
    url = encodeURI(url);
    
    var params = "dname=" + dname+ "&&dvalue=" + dvalue;
    
    params = encodeURI(params);
    
    mygridt_dw_s.clearAll();//把表格的内容全部清空
    
	mygridt_dw_s.loadXML(url+"?"+params,pageLoaded);
	}
/**
 * 功能：删除记录
 * 时间：2007-09-17
 */
function doDelete() {
 	 if(getDelId()!=""){
	if (confirm("您确定删除此项记录吗?")) {
	   var getparam = "";
	   var url = 'common.datawin.delete.d?getparam='+getDelId();
	   url = encodeURI(url);
	   var params = "postparam="+getDelId();
	   new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseDelete, onSuccess:doFiniDelete});     
       }else { 
      return;
      }
   		}else{
 alert("请至少选择一项记录进行此操作");
 }   
}
function showResponseDelete(xmlHttp) {
	alert(xmlHttp.responseText);
	fresh();
}

function doFiniDelete() {
	//alert("\u6b64\u8bb0\u5f55\u88ab\u6210\u529f\u5220\u9664");
}	
  /**
*功能 ：获取每行记录的唯一标识,并放入数组
*/
function getDelId(){
 var iColumnCount=mygridt_dw_s.getColumnCount();
 var iRowsNum=mygridt_dw_s.getRowsNum();
 var rowid= mygridt_dw_s.getSelectedId();
 var delArray = new Array();
 for (var i = 0; i < iRowsNum; i++){
   if (mygridt_dw_s.cells2(i,0).getValue()== 1){
     var delid = mygridt_dw_s.cells2(i, 1).getValue();	
     delArray.push(delid);
   }
}
return delArray;
}
/**
*功能 ：刷新页面
*/
function fresh(){
	
	var dname = document.getElementById("dname");
    var dvalue = document.getElementById("dvalue");
    dname.value="";
    dvalue.value="";
    var url = "common.datawin.filter.d";
    
    url = encodeURI(url);
    
    var params = "dname=" +""+ "&&dvalue=" + "";
    
    params = encodeURI(params);
    
    mygridt_dw_s.clearAll();//把表格的内容全部清空
    
	mygridt_dw_s.loadXML(url+"?"+params,pageLoaded);
}  
function doBeforeDeleted(){
  var rowid= mygridt_dw_s.getSelectedId();
  if (mygridt_dw_s.cells2(rowid,0).getValue()== 0){
     alert("请至少选中一项记录进行此操作");
     return false;
   }
   return true;
} 
//dataWin.jsp中调用的toolbar

	function onButtonClick(itemId, itemValue) {
    var id = itemId;				
    if (id == "0_new") {
	 var returnvalue = window.showModalDialog("dataWinUtil.jsp?insrtOrUpdt=0&did=''","","dialogWidth:360px;dialogHeight:300px");			
     if(returnvalue == "fresh"){
       fresh();
     }
    } 
 if (id == "0_edit") {
 	if(getDelId()==undefined || getDelId().length != 1) {
 	   alert("请选择一条记录！");
	}else{
      var returnvalue = window.showModalDialog("dataWinUtil.jsp?insrtOrUpdt=1&did='"+getDelId()+"'","","dialogWidth:360px;dialogHeight:300px");
      if(returnvalue == "fresh"){
       fresh();
     }
    }
  } 
 if (id == "0_filter") {
      doFilter();
     } 
 if (id == "0_save") {
     } 
 if (id == "0_fresh") {
      fresh();	
     } 
 if (id == "0_delete") {
      doDelete(); 
     }
  }
