/*
 * 获取机构人员树,并以气泡形式弹出 deptId-顶层机构编号,
 * divId-机构或人员容器的id,该容器value属性为人员或机构名称,personId属性为人员或机构编号;均以";"相隔 top-气泡距浏览器顶距离
 * left-距左侧距离 width-气泡宽度 height-气泡高度 ignorePerson-是否忽略人员 "true"-忽略人员
 */
var vmlArray = null;
var index = -1; // 渐变绘线当前路线下标
deptDiv = null;// vml容器
var str = new Array();
function setTree(xmlDoc) {
	var tree = xmlDoc.getElementsByTagName("tree");
	setHtml(tree[0]);
	deptDiv = document.getElementById("role_id");
	vmlArray = str.join("").split("deptsplit");
	writeDeptByStep();

}
var count = 0; // 统计在线人数
// 生成树html
function setHtml(dept, bm) {
	var space = "";
	if (bm) {
		for (var i = 0; i < bm.length; i++) {
			space += "&nbsp;&nbsp;"
		}
	}
	var deptson = dept.childNodes;

	for (var j = 0; j < deptson.length; j++) {
		var ccbm = "";
		var aid = "";
		if (deptson[j].tagName == "dept") {
			ccbm = deptson[j].getAttribute("jgccbm");
			aid = deptson[j].getAttribute("id");
			var deptName = deptson[j].getAttribute("text");
			if (j == 0) {
				str.push("deptsplit");
				count = 0;
			}
			str.push(space);
			str.push("<img id='img_");
			str.push(aid);
			str
					.push("' src='../sm/image/tree/tree_open.bmp' onclick=\"Expand('");
			str.push(aid);
			str.push("');\" />");
			str.push("<img id='i_");
			str.push(aid);
			str
					.push("' src='../sm/image/tree/folderOpen.gif' /><font onclick=Expand('");
			str.push(aid);
			str.push("');>");
			str.push(deptName);
			str.push("<br></font>");
		} else if (deptson[j].tagName == "person") {
			count += 1;
		}

		if (aid != "") {
			str.push("<div id='d_");
			str.push(aid);
			str.push("'>");
			setHtml(deptson[j], ccbm);
			str.push("</div>");
		}
	}
}

// 机构树展开关闭
function Expand(sid) {
	if (document.getElementById("d_" + sid).style.display == "") {
		document.getElementById("img_" + sid).src = "../sm/image/tree/tree_close.bmp";
		document.getElementById("i_" + sid).src = "../sm/image/tree/folderClosed.gif";
		document.getElementById("d_" + sid).style.display = "none";
	} else {
		document.getElementById("img_" + sid).src = "../sm/image/tree/tree_open.bmp";
		document.getElementById("i_" + sid).src = "../sm/image/tree/folderOpen.gif";
		document.getElementById("d_" + sid).style.display = "";
	}
}

function writeDeptByStep() {
	var temArray = new Array();
	for (var i = index + 1; i - index < 50; i++) {
		if (i < vmlArray.length) {
			temArray.push(vmlArray[i]);
		}
	}
	var divobj = document.createElement('div');
	deptDiv.appendChild(divobj);
	divobj.innerHTML = '<table>' + temArray.join('') + '</table>';
	index = i--;
	if (index < vmlArray.length) {
		setTimeout(writeDeptByStep, 0)
	}
}
/*
 * 取得所有在线部门的在线人数
 */
function showOnlineUsers() {
	var url = "dispatch.onlineUsers.showOnlineUsers.d";
	url = encodeURI(url);
	var params = "";
	new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : showOnlineUsersResult
			});
}

function showOnlineUsersResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var rows = xmlDoc.getElementsByTagName("row");
	var showHtml = "";
	for (var i = 0; i < rows.length; i++) {
		var results = rows[i].getElementsByTagName("col");;
		showHtml = showHtml + results[0].text + "<br/>"
	}
	$("role_id").innerHTML = "<font>" + showHtml + "</font>";
}