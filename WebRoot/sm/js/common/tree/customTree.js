/**
	 * @---------------------------------------------------------------------------------------------------------------------------------- 
	 * @作者：louqiang
	 * @版本号：
	 * @类型说明：用于对页面的菜单功能树进行加载、生成表格显示菜单项的数据.对选中记录的进行增加、编辑、过滤、删除、刷新操作.
	 * @创建日期：2007-9-17
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	 	 
/** 函数功能 ：页面加载树* 时间：2007-09-17*/
function loadData() {
	var url = "common.treectrl.load.d";
	var params = "";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseXml, onSuccess:doFiniXml});
}
function showResponseXml(xmlHttp) {
	//alert(xmlHttp.responseText);
}
function doFiniXml() {
   //alert();
}

/** 函数功能 ： 增加* 时间：2007-09-17*/

function setData() {
	var url = "common.treectrl.insertItem.d";
	url = encodeURI(url); //url只请求的服务器，比如：url="http://www.google.com.cn"
	var params = "id=" + document.getElementById("id").value + "&&parent=" + document.getElementById("parent").value + "&&text=" + document.getElementById("text").value + "&&im0=" + document.getElementById("im0").value + "&&im1=" + document.getElementById("im1").value + "&&im2=" + document.getElementById("im2").value + "&&n_call=" + document.getElementById("n_call").value + "&&n_select=" + document.getElementById("n_select").value + "&&remark=" + document.getElementById("remark").value;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponse, onSuccess:doFini});
} 