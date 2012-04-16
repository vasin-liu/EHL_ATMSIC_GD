<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.dispatch.common.DispatchUtil"%>
<%@ include file="Message.oni"%>
<%
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	String path = request.getHeader("host");

	//当前用户信息
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String uname = ""; //帐号
	String pid = ""; //办公电话
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	if (prop != null) {
		deptcode = (String) prop.get("BRANCHID");
		deptname = (String) prop.get("BRANCHNAME");
		pname = (String) prop.get("NAME");
		uname = (String) prop.get("USERNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>警务工作系统</title>
		<link href="../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/header.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/main.css" rel="stylesheet" type="text/css" />
		<link href="../base/css/style1/sidebar.css" rel="stylesheet"　type="text/css" />
		<link href="../base/css/style1/navigation.css" rel="stylesheet"　type="text/css" />
		<link href="../base/css/style1/link.css" rel="stylesheet"　type="text/css" />
		<script type="text/javascript" src="../policeWork/js/login/prototype.js"></script>
		<script type="text/javascript" src="../policeWork/js/login/login.js"></script>
		<SCRIPT src="../base/js/style1/iframe.js" type=text/javascript></SCRIPT>
		<script type="text/javascript" src="../js/main.js"></script>
		<script type="text/javascript" src="../dispatch/js/login/prototype.js"></script>
		<script type="text/javascript" src="../base/js/dialog/jquery.js"></script>
		<link href="../base/js/dialog/dialog.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			#msg li {
				line-height: 18px;
				height: 18px;
				margin-top: 2px;
			}
			
			#msg a {
				padding: 1px 7px;
				line-height: 16px;
				margin: 0px 1px 0px 0px;
				text-decoration: underline;
				color: #07679c;
				float: none;
			}
			
			#msg a:hover {
				background: #ededed;
				color: #ff6633;
			}
			
			#msg a.selected {
				text-decoration: none;
				background: #3384ad;
				color: white;
			}
			
			#msg a.selected:hover {
				background: #3384ad;
			}
			
			#prompt_nav span {
				height: 16px;
			}
			
			table tr {
				line-height: 16px;
			}
			
			table tr td {
				font-size: 12px;
			}
		</style>

	</head>
	<script type="text/javascript">
	function privCtrls(){
		var url = "common.treectrl.load.d?showtree=88";
		url = encodeURI(url);
		var params ="";
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:createTools});		
	}
	
	function createTools(xmlHttp){
	  	var xmlDoc = xmlHttp.responseXML;		  	
		var root = xmlDoc.documentElement.childNodes;
		var href="";
		if(root!=null){
			href+="｜";
			for (var i = 0; i < root.length; i++) {		
				var rid=root[i].getAttribute("id");
				alert(id);
				var rtxt=root[i].getAttribute("text");
				href+="<a href='"+rid+"' id='link_"+rid+"' onclick=initIndex('link_"+rid+"'); style='color: #ffffff;' target='ifMain' name='nagivate'>"+rtxt+"</a>｜";
				
			}	
		}
		// href+='<font style="text-align: right;padding-right:10px"><a href="#" style="color: #FFFFFF" onclick="doLogout(\'header\')">注销</a></font> ';				
		document.getElementById("toolsSpan").innerHTML=href;
	}
	function initIndex(id){
		var hrefs=document.getElementsByName("nagivate");
		for(var i=0;i<hrefs.length;i++){			
			if(hrefs[i].id==id){
				hrefs[i].style.color="#D0AE1E";
			}else{
				hrefs[i].style.color="#ffffff";
			}
		}
	}	
</script>
	<body style="padding: 0; margin: 0" onload="setHomePage('88','<%=uname %>')">
		<div id="pagebody">
			<table width="100%">
				<tr>
					<td width="10">
						<div class="sidebar" id="frmTitle">
							<iframe frameborder="0" id="sidebar" name="sidebar"
								scrolling="auto" noresize="noresize" src="lefttree.jsp"
								style="HEIGHT: 521px; VISIBILITY: inherit; WIDTH: 168px; Z-INDEX: 2"
								allowtransparency="true"></iframe>
						</div>
					</td>
					<td width="8">
						<div class="switch">
							<div
								style="HEIGHT: 521px; float: left; background: url(../base/image/cssimg/switch.gif) no-repeat center;"
								onClick="switchSysBar()">
								<font style="FONT-SIZE: 12pt; width: 9px; CURSOR: default;">
									<span class="navPoint" id="switchPoint" title="切换"></span> </font>
							</div>
						</div>
					</td>
					<td>
						<div class="mainbody" style="width: 100%">
							<iframe frameborder="0" id="moduletarget" name="moduletarget"
								scrolling="auto" noresize="noresize"
								src="ehl/assess/AssessInfo.jsp"
								style="height: 540px; * height: 540px !important; * height: 522px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: -1; padding-top: 6px;"
								allowtransparency="true">
							</iframe>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
