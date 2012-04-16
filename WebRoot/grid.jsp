<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@page import="com.ehl.sm.common.Constants"%>
<%@page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@page import="com.appframe.data.sql.DBHandler"%>
<%@page import="com.ehl.login.Login"%>
<%@include file="base/Message.oni"%>
<%
	Object loginName = session.getAttribute(Constants.PNAME_KEY);
	String deptInfoStr = DepartmentManage.getDeptInfo(request, "1");
	String personName = "";
	if(loginName!=null){
		personName = Login.getPersonNameByUserName(String.valueOf(loginName));
	}
	String loginInfo = loginName + ";" + deptInfoStr + ";" + personName;
	String login = StringHelper.obj2str(request.getParameter("login"),"");
%>
<%
String appid = "";//应用场景编号
if(loginName != null){
	String getAppidSql = "select appid from t_sys_user where username='"+loginName+"'";
	try{
		appid = StringHelper.obj2str(DBHandler.getSingleResult(getAppidSql),"");
	}catch(Exception e){
		appid = null;
		System.out.println("获取应用场景编号出错！异常信息："+e);
	}
	if(appid != null){
		session.setAttribute("appid",appid);
	}
}

%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>登录界面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link href="css/gd.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/grid.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="base/js/prototype.js"></script>
		<script type="text/javascript" src="loginstate/js/SessionKeep.js"></script>
		<script type="text/javascript" src="js/AutoCome.js"></script>
		<script type="text/javascript">
		    //监视浏览器的关闭动作，然后调用doLogout()向服务器发送一个请求---by ldq
			window.onunload=function (){   
				if(event.clientX<0&&event.clientY<0){ 
			   		doLogout();  
			   	}
			}
			var login = '<%=login%>';
			function correctPNG(){ // correctly handle PNG transparency in Win IE 5.5 & 6.
				var arVersion = navigator.appVersion.split("MSIE")
				var version = parseFloat(arVersion[1])
				if ((version >= 5.5) && (document.body.filters)){
					var images = document.getElementsByTagName("img");
					for ( var j = 0; j < images.length; j++) {
						var img = images[j]
						var imgName = img.src.toUpperCase()
						if (imgName.substring(imgName.length - 3, imgName.length) == "PNG"
								|| imgName.substring(imgName.length - 3, imgName.length) == "png") {
							var imgID = (img.id) ? "id='" + img.id + "' " : "";
							var imgClass = (img.className) ? "class='" + img.className + "' " : "";
							var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' ";
							var imgStyle = "display:inline-block;" + img.style.cssText;
							if (img.align == "left")
								imgStyle = "float:left;" + imgStyle;
							if (img.align == "right")
								imgStyle = "float:right;" + imgStyle;
							if (img.parentElement.href)
								imgStyle = "cursor:hand;" + imgStyle;
							var strNewHTML = "<span " + imgID + imgClass + imgTitle + " style=\""
									+ "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";"
									+ "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
									+ "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>";
							img.outerHTML = strNewHTML;
							j = j - 1;
						}
					}
				}
			}
			
		</script>
	</head>
		<body onload="checkAndLoadGrids('<%=loginName%>','<%= appid %>');initUserInfo('<%=loginInfo%>');autoCome();" >
		<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr height="10px;">
				<td >
				</td>
			</tr>
			<tr>
				<td id="titleTd" align="center" style="font-size: 30; height: 30; color: white;" >
				</td>
			</tr>
			<tr>
				<td id="itemTD">
				</td>
			</tr>
		</table>
		<div id="autoComeIndex" style="position:absolute;bottom:65;right:50;width:0px;height:0px;">
			<span style="white-space: nowrap;">系统提示：如果没有操作，<span id="waitSec" style="cursor: hand;" onclick="timerControl()"></span>秒后系统将默认进入<span id="subSystem"></span>模块</span>
		</div>
	</body>
</html>