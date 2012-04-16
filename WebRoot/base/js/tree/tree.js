var str = "";
var Tree = {
	jgid : "",
	jgmc : ""
}
var chkShowSuperior = "";
var chkStatus = null;
var chkStatusTemp = null;
var globalDeptId = "440000000000";
//backup  deptDiv deptContents cover usedivs
var strTemp = "";
var scrollTop;
function openDivWindow(obj, top, left, width, height, okmethod, canclemethod,
		title) {
	// 创建容器div
	var useDiv1 = document.createElement("div");
	useDiv1.id = "deptDiv";
	useDiv1.style.zIndex = "10000";
	useDiv1.style.position = "absolute";
	useDiv1.style.display = "inline";
	if (width == null || width == "") {
		width = "250";
	}
	useDiv1.style.width = width;
	if (height == null || height == "") {
		height = "340";
	}
	useDiv1.style.height = height;
	// useDiv1.className="deptDivClass";
	// 给弹出窗口左右边距默认值0
	if (top == null || top == "") {
		top = 0;
	}
	if (left == null || left == "") {
		left = 0;
	}
	useDiv1.style.top = top;
	useDiv1.style.left = left;

	document.body.appendChild(useDiv1);
	// 创建弹出iframe
	var coverFrame = document.getElementById("cover");

	if (coverFrame != null) {
		coverFrame.parentNode.removeChild(coverFrame);
	}

	coverFrame = document.createElement("iframe");
	coverFrame.id = "cover";
	coverFrame.position = "absolute";
	coverFrame.zIndex = -1;
	coverFrame.src = "#";
	coverFrame.frameborder = 3;
	coverFrame.style.width = "250";
	coverFrame.style.height = "320";
	coverFrame.style.top = 0;
	coverFrame.style.left = 0;
	coverFrame.style.scrolling = "no";
	coverFrame.style.display = "inline";
	var useDiv = document.createElement("div");
	useDiv.id = "usedivs";
	// useDiv.style.backgroundColor="#DFEAF7";
	useDiv.style.width = "250";
	useDiv.style.height = "320";
	useDiv.style.zIndex = 10000;
	useDiv.style.position = "absolute";
	if (top == null || top == "") {
		top = 0;
	}
	if (left == null || left == "") {
		left = 0;
	}
	useDiv.style.top = 0;
	useDiv.style.left = 0;

	useDiv1.appendChild(useDiv);
	useDiv1.appendChild(coverFrame);
	useDiv.innerHTML = '\
           <div style="zIndex:10000">\
           <table id="poptable" width="100%" height="100%" style="margin:0px;" class="popup" cellpadding="0" cellspacing="0">\
            <tbody>\
              <tr id="deptTitle" height="5%" class="scrollColThead">\
                <td class="corner" id="topleft"></td>\
                <td class="top">'
			+ title
			+ '</td>\
                <td class="top" align="right" valign="middle">\
                  <img src="../../../sm/image/popup/ok.gif" border="0" style="cursor:pointer" onclick="'
			+ okmethod
			+ '">\
                  <img src="../../../sm/image/popup/cancel.gif" border="0" style="cursor:pointer" onclick="'
			+ canclemethod
			+ '">\
                </td>\
                <td class="corner" id="topright"></td>\
              </tr>\
              <tr height="92%" valign="top">\
                <td class="left"></td>\
                <td id="popup-contents" bgcolor="#DFEAF7" colspan="2">\
                   <div style="weight:180;height:298;overflow-y:scroll;" id="tdcontain"></div>\
                </td>\
                <td class="right"></td>\
              </tr>\
              <tr height="3%">\
                <td id="bottomleft" class="corner"></td>\
                <td class="bottom" colspan="2"></td>\
                <td id="bottomright" class="corner"></td>\
              </tr>\
            </tbody>\
            </table>\
          </div>';
	$("tdcontain").style.scrollbarBaseColor = "#DFEAF7";

	$("tdcontain").appendChild(obj);
	
//	new Move('deptTitle','deptDiv').move();
}

function closeDivWindow() {
	flag=0;
	var divPopup = document.getElementById("useDivs");
	if ((typeof(divPopup) != "undefined") && (divPopup != null)) {
		divPopup.parentNode.removeChild(divPopup);
	}
	var iframeObj = document.getElementById("cover");
	if ((typeof(iframeObj) != "undefined") && (iframeObj != null)) {
		iframeObj.parentNode.removeChild(iframeObj);
	}
	var deptDiv = document.getElementById("deptDiv")
	if ((typeof(deptDiv) != "undefined") && (deptDiv != null)) {
		deptDiv.parentNode.removeChild(deptDiv);
	}
}

var top = 130;
var left = 50;
// showID为要显示名称ID控件(必须还要有个控件ID为showID+ID)
var showTextID = "";
var flag=0;
function setAllTree(topDeptId, t, l, showID) {
	// var deptId="430100000000";
	showTextID = showID;
	if (t != "") {
		top = t;
	}
	if (l != "") {
		left = l;
	}
	if (str) {
		setTreeRes();
		return;
	}
	var deptId = "440000000000"
	if (topDeptId) {
		deptId = topDeptId;
	}
	var url = "dispatch.tree.setAllTree.d";
	url = encodeURI(url);
	var params = {};
	
	params["deptId"] = deptId;
	new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : setTreeRes
			});

}
function setTree(topDeptId, t, l, cbmname,dttype) {
	// var deptId="430100000000";
//	showTextID = showID;
	if (t != "") {
		top = t;
	}
	if (l != "") {
		left = l;
	}
	if (str) {
		setTreeRes(null,cbmname);
		return;
	}
	scrollTop = document.body.scrollTop;
	var deptId = "440000000000"
	if (topDeptId) {
		deptId = topDeptId;
		globalDeptId = topDeptId;
	}
	
	var url = "dispatch.tree.getDeptTree.d";
	url = encodeURI(url);
	var params = {};
//	if(deptId){
//		params["jgid"] = deptId;
//	}
	
	if(dttype){
		params["dttype"] = dttype;
	}
	new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : function(xmlHttp){
					setTreeRes(xmlHttp,cbmname);
				}
			});
}

function setZhiTree(topDeptId, t, l, showID) {
	// var deptId="430100000000";
	showTextID = showID;
	if (t != "") {
		top = t;
	}
	if (l != "") {
		left = l;
	}
	if (str) {
		setTreeRes();
		return;
	}
	var deptId = "440000000000"
	if (topDeptId) {
		deptId = topDeptId;
	}
	var url = "dispatch.tree.setZhiTree.d";
	url = encodeURI(url);
	var params = {};
	
	params["deptId"] = deptId;
	new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : setTreeRes
			});

}
function setDaTree(topDeptId, t, l, showID) {
	// var deptId="430100000000";

	showTextID = showID;
	if (t != "") {
		top = t;
	}
	if (l != "") {
		left = l;
	}
	if (str) {
		setTreeRes();
		return;
	}
	var deptId = "440000000000"
	if (topDeptId) {
		deptId = topDeptId;
	}
	var url = "dispatch.tree.setDaTree.d";
	url = encodeURI(url);
	var params = {};
	
	params["deptId"] = deptId;
	new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : setTreeRes
			});

}
function setTreeRes(xmlHttp,cbmname) {
	if (flag == 0) {
		flag = 1;
		if (xmlHttp) {
			var xmlDoc = xmlHttp.responseXML;
			var tree = xmlDoc.getElementsByTagName("tree");
			setHtml(tree[0]);
		}
		strTemp = str;
		var div = document.createElement("div");
//		//Modified by Liuwx 2011-06-20
//		var chkShowSuperior = "<div id='divShowSuperior'>"
//							+"<input type='checkbox' id='isShowSuperior' name='showSuperior' value ='isShow' "
//							+"onClick='flushTree();'><font><b>显示上级部门<br></b></font></div>";
		div.innerHTML = "<div id='deptContents'>" + str + "</div>";
//		//Modification finished

		openDivWindow(div, top, left, 206, 300,
				'getChkPerson();closeDivWindow();'+(cbmname||''), 'cancelClear();closeDivWindow();',
				'请选择机构');
		var deptIdStr="";
		if($(Tree.jgid)){
			deptIdStr = $(Tree.jgid).value;
		}else if($("jgmcId")){
			deptIdStr = $("jgmcId").value;
		}
		
//		//Modified by Liuwx 2011-06-20
//		//for checkbox id:isShowSuperior
//		var chkBox = document.getElementById("isShowSuperior");
		chkStatusTemp = chkStatus;
//		chkBox.checked = chkStatus;
		selectBefore(deptIdStr);
		if(scrollTop != 0){
			setTimeout(function(){window.scrollTo(0,scrollTop)},500);
		}
		// $("bodydiv").appendChild(div);
		//Modification finished
	}
}
// 生成树html
function setHtml(dept, bm) {
	var space = "";
	if (bm) {
		for (var i = 0; i < bm.length; i++) {
			space += "&nbsp;&nbsp;"
		}
	}
	var deptson = dept.childNodes;
	//Modify by xiayx 2011-07-28
	//设置指定机构不可选中
//	var dstyle="";
//	for (var j = 0; j < deptson.length; j++) {
//		var ccbm = "";
//		var aid = "";
//		if (deptson[j].tagName == "dept") {
//			ccbm = deptson[j].getAttribute("jgccbm");
//			aid = deptson[j].getAttribute("id");
//			if(globalDeptId.indexOf(aid)!=-1){
//				dstyle = " style='background-color:gray;' onclick='return false' isChecked='false' ";
//			}
	var dstyle="";
	for (var j = 0; j < deptson.length; j++) {
		var ccbm = "";
		var aid = "";
		if (deptson[j].tagName == "dept") {
			ccbm = deptson[j].getAttribute("jgccbm");
			aid = deptson[j].getAttribute("id");
			if(globalDeptId.indexOf(aid)!=-1){
				dstyle = " style='' onclick='return false' isChecked='false' ";
			} else {
				dstyle = "";
				
			}
 	//Modify finished
			var deptName = deptson[j].getAttribute("text")
			str += space
					+ "<img id='img_"
					+ aid
					+ "' src='../../../sm/image/tree/tree_open.bmp' onclick=\"Expand('"
					+ aid
					+ "');\" /><input "+dstyle+" type='checkbox' id='"
					+ aid
					+ "' ccbm='"
					+ ccbm
					+ "' name='depts'"
					+ "' deptName='"
					+ deptName
					+ "' onDblclick=\"clickr('"
					+ aid
					+ "','"
					+ ccbm
					+ "' );\" ><img id='i_"
					+ aid
					+ "' src='../../../sm/image/tree/folderOpen.gif' /><font  onclick=Expand('"
					+ aid + "');>" + deptName + "<br></font>";
		}

		if (deptson[j].tagName == "person") {
			ccbm = deptson[j].getAttribute("ccbm");
			var personid = deptson[j].getAttribute("id");
			var personname = deptson[j].getAttribute("name");
			var perdept = deptson[j].getAttribute("deptId");
			str += space
					+ "<font style='width:13px'></font><input type=checkbox id='"
					+ personid
					+ "' xm='"
					+ personname
					+ "' ccbm='"
					+ ccbm
					+ "' name='personnames'><img  src='../../../sm/image/tree/book_titel.gif' />"
					+ personname + "<br>";
		}

		if (aid != "") {
			str += "<div id='d_" + aid + "'>"
			setHtml(deptson[j], ccbm);
			str += "</div>";
		}
	}
}

// 点击选择部门下所有人员

function clickr(oid, dname) {
	//Modify by xiayx 2011-07-28
	//设置指定机构双击事件不选中
//	var chk = document.getElementById(oid);
//	var inputs = document.getElementsByTagName("input");
//	for (var i = 0; i < inputs.length; i++) {
//		if (inputs[i].type == "checkbox") {
//			if (inputs[i].ccbm && inputs[i].ccbm.length >= dname.length) {
//				if (inputs[i].ccbm.substring(0, dname.length) == dname) {
//					if (chk.checked == true) {
//						inputs[i].checked = true;
//					} else {
//						inputs[i].checked = false;
//					}
//				}
//			}
//		}
//	}
	
	var chk = document.getElementById(oid);
	var inputs = document.getElementsByTagName("input")
	if(chk.isChecked){
		if(chk.isChecked == "false"){
			chk.isChecked = "true";
			chk.checked = true;
		}else if(chk.isChecked == "true"){
			chk.isChecked = "false";
			chk.checked = false;
		}
	}
	var inputs = document.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type == "checkbox") {
			if (inputs[i].ccbm && inputs[i].ccbm.length >= dname.length) {
				if (inputs[i].ccbm.substring(0, dname.length) == dname) {
					if (chk.checked == true) {
						if(inputs[i].isChecked) {
							if (inputs[i].isChecked == false) {
								inputs[i].checked = true;
							}
						} else {
							inputs[i].checked = true;
						}
					} else {
						inputs[i].checked = false;
					}
				}
			}
		}
	}
	if(chk.isChecked){
		chk.checked = false;
	}
	//Modify finished
}

// 机构树展开关闭
function Expand(sid) {

	if (document.getElementById("d_" + sid).style.display == "") {
		document.getElementById("img_" + sid).src = "../../../sm/image/tree/tree_close.bmp";
		document.getElementById("i_" + sid).src = "../../../sm/image/tree/folderClosed.gif";
		document.getElementById("d_" + sid).style.display = "none";
	} else {
		document.getElementById("img_" + sid).src = "../../../sm/image/tree/tree_open.bmp";
		document.getElementById("i_" + sid).src = "../../../sm/image/tree/folderOpen.gif";
		document.getElementById("d_" + sid).style.display = "";
	}
}
//判断前一次被选择的部门
function selectBefore(deptIdStr){
	if(deptIdStr&&deptIdStr.length>0){
	var str= new Array();    
	str = deptIdStr.split("；");
	var inputs = document.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type == "checkbox") {
			for(var j=0;j<str.length;j++){
				if (inputs[i].id==str[j]) {
					inputs[i].checked = true;
				}
			}
		}
	}		
	}
}
// 获取人员选中信息
function getChkPerson() {
   flag=0;
	var depts = document.getElementsByName("depts");
	var deptId = "";
	var deptName = "";
	if (depts != null) {
		for (var i = 0; i < depts.length; i++) {
			if (depts[i].checked == true) {
			if(depts[i].id!=globalDeptId){
				deptName += depts[i].deptName + "；";
				deptId += depts[i].id + "；";
				}
			}
		}
		if(Tree.jgid && $(Tree.jgid)){
			$(Tree.jgid).value = deptId;
		}else {
			if($("jgmcId")){
				$("jgmcId").value = deptId;
			}
		}
		if(Tree.jgmc && $(Tree.jgmc)){
			$(Tree.jgmc).value = deptName;
		}else {
			if($("jgmc")){
				$("jgmc").value = deptName;
			}
		}
	}
	//Modified by Liuwx 2011-06-20
	//for checkbox id:isShowSuperior
//	var chkBox = document.getElementById("isShowSuperior");
//	chkStatus = chkBox.checked;
	//Modification finished
}

//Modified by Liuwx 2011-06-20
//是否显示上级部门
function flushTree() {
	var chkBox = document.getElementById("isShowSuperior");
	chkStatus = chkBox.checked;
	
	var url = "dispatch.tree.setTree.d";
	url = encodeURI(url);
	var params = {};
	
	var isShowSuperior = 0;
	if(chkBox.checked){
		isShowSuperior = 1;
	}else{
		isShowSuperior = 0;
	}
	params["deptId"] = globalDeptId;
	params["isShowSuperior"] = isShowSuperior;
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : flushContents
	});
}

//刷新部门树内容
function flushContents(xmlHttp){
	if (flag == 1) {
		if (xmlHttp) {
			strTemp = str;
			str = ""
			var xmlDoc = xmlHttp.responseXML;
			var tree = xmlDoc.getElementsByTagName("tree");
			setHtml(tree[0]);
			var div = document.getElementById("deptContents");
			div.innerHTML = str;
		}
		
		var deptIdStr = $("jgmcId").value;
		selectBefore(deptIdStr);
		// $("bodydiv").appendChild(div);
	}
}

//清除还原
function cancelClear(){
	str = strTemp;
	chkStatus = chkStatusTemp;
}
//Modification finished