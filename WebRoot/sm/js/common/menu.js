/*全局变量*/
var PrivCode = "";
/*菜单数组*/
var imageArray = new Array();
var idArray = new Array();
var imagePath = "../../image/i_banner.gif";//图片路径

var handMethod = "";
/*设置菜单生成完毕调用的方法*/
function setMethod(method) {
	handMethod = method;
}

/*从后台获取xml生成菜单*/
function getMenu() {
	
	url = "common.PrivManage.loadChildSys.d";
	//setMethod(afterLoadMenu);
	url = encodeURI(url);
	var params = "";
	var myAjax = new Ajax.Request(url, {method:"POST", parameters:params, onComplete:handlerGetMenu});
}
function handlerGetMenu(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var root = xmlDoc.documentElement.childNodes;
	var str = "";
	for (var i = 0; i < root.length; i++) {
		var name = root[i].getAttribute("name");
		var id = root[i].getAttribute("id");
//		idArray[i] = root[i].getAttribute("id");
		str = str + createMenu(name, id);
	}
	document.getElementById("menu").innerHTML = str;
	//eval(handMethod + "()");
}

/*创建菜单*/
function createMenu(text, id) {
	var str="";
	str += "<div style=\"text-align:left;width:270px;valign:top;height:22px;background-repeat: no-repeat;cursor: hand;" ;
	str = str + "\"  id=\"imgmenu" + id + "\" onclick=\"showsubmenu('" + id + "')\" ><span id='number'><img id= \"pic"+id+"\" style=\"cursor:hand;\" src=\"../../image/tree/plus5.gif\">" + text + "</span></div>";
	str = str + "<div  id=\"" + id + "\" style=\"display:none;\"></div>";
	return str;
}

/*控制菜单显示*/
function showsubmenu(sid) {
	var img = document.getElementById("pic"+sid);
	var PrivCode = document.getElementById("PrivCode").value;
	var menuSID = document.getElementById(sid);
	if (menuSID.style.display == "none") {
		menuSID.style.display = "inline";
		img.setAttribute("src", "../../image/tree/minus5.gif");
		/*加载功能树*/
		loadFuncTree(PrivCode,sid);
		//alert("sid:"+sid.innetHTML);
	} else {
		menuSID.style.display = "none";
		img.setAttribute("src", "../../image/tree/plus5.gif");
	}
}