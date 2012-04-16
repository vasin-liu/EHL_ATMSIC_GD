/*全局变量*/
var PrivCode = "";
var strPara = "";//是否是 编辑状态，0-新增；1-编辑
/*菜单数组*/
var imageArray = new Array();
var idArray = new Array();
var imagePath = "../../image/i_banner.gif";//图片路径
var handMethod = "";
/*设置菜单生成完毕调用的方法*/
function setMethod(method) {
	handMethod = method;
}
/*从后台获取xml生成菜单---获取权限项*/
function getPrivMenu(did,insrtOrUpdt) {

	var url = "common.RoleManage.loadChildSys.d";
	url = encodeURI(url);
	var params ="insrtOrUpdt ="+ insrtOrUpdt+"&roleCode=" + did; 
	params = encodeURI(params);	
	var myAjax = new Ajax.Request(url, {method:"POST", parameters:params, onComplete:handlerGetMenu});
}

function handlerGetMenu(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var root = xmlDoc.documentElement.childNodes;
	var str = "";
	for (var i = 0; i < root.length; i++) {
		var name = root[i].getAttribute("name");
		var id = root[i].getAttribute("id");
		var textColor = root[i].getAttribute("textColor");
		//alert(textColor);
//		idArray[i] = root[i].getAttribute("id");
		str = str + createMenu(name,id,textColor);
		//openMenu(id,strPara);
	}
	document.getElementById("role_id").innerHTML = str;
	//eval(handMethod + "()");
}

/*创建菜单*/
function createMenu(text,id,textColor) {
	var str="";
	str += "<div style=\"text-align:left;width:310px;valign:top;background-repeat: no-repeat;cursor: hand;" ;
	str += "\"id=\"imgmenu" + id + "\" onclick=\"showsubmenu('" + id + "')\" ><span id='number' style=\"color:"+textColor+";\"><img id= \"pic"+id+"\" style=\"cursor:hand;\" src=\"../../image/tree/plus5.gif\">" + text + "</span></div>";
	str += "<div style=\"overflow:hidden;padding-left:13px;\"><div id=\"" + id + "\"  style=\"display:none;overflow:hidden;\"></div></div>";
	return str;
}

/*控制菜单显示*/
function showsubmenu(sid) {
	var img = document.getElementById("pic"+sid);
	var RoleCode = document.getElementById("did").value;
	
	var menuSID = document.getElementById(sid);
	if (menuSID.style.display == "none") {
		menuSID.style.display = "inline";
		img.setAttribute("src", "../../image/tree/minus5.gif");
		/*加载功能树*/
		loadPrivTree(RoleCode,sid);
		//alert("sid:"+sid.innetHTML);
	} else {
		menuSID.style.display = "none";
		img.setAttribute("src", "../../image/tree/plus5.gif");
	}
}
function openMenu(sid,strPara) {
	var img = document.getElementById("pic"+sid);
	var RoleCode = document.getElementById("did").value;
	
	var menuSID = document.getElementById(sid);
	if (menuSID.style.display == "none") {
		menuSID.style.display = "inline";
		img.setAttribute("src", "../../image/tree/minus5.gif");
		/*加载功能树*/
		loadPrivTree(RoleCode,sid);
		//alert("sid:"+sid.innetHTML);
	} 
	//else {
	//	menuSID.style.display = "none";
		//img.setAttribute("src", "../../image/tree/plus5.gif");
	//}
}