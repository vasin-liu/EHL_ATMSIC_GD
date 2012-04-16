<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="com.ehl.sm.common.Constants" %>
<%@page import="com.ehl.sm.pcs.DepartmentManage" %>
<%@page import="com.ehl.login.Login" %>

<% Object loginName = session.getAttribute(Constants.PNAME_KEY); 
   String loginInfo = "";
   if(loginName == null){
       loginName = "";
       loginInfo = "";
   }else{
       String deptInfoStr = DepartmentManage.getDeptInfo(request, "1");
	   String personName = Login.getPersonNameByUserName(loginName.toString());
	   loginInfo = loginName +";"+ deptInfoStr+";" + personName;
   }
   
   
%>
<head>
<script type="text/javascript" src="js/login_GD.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="base/js/prototype.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>登录界面</title>
<link href="css/gd.css" rel="stylesheet" type="text/css" />

<script language="JavaScript">
function correctPNG() // correctly handle PNG transparency in Win IE 5.5 & 6.
{
     var arVersion = navigator.appVersion.split("MSIE")
     var version = parseFloat(arVersion[1])
     if ((version >= 5.5) && (document.body.filters)) 
     {
       for(var j=0; j<document.images.length; j++)
       {
           var img = document.images[j]
           var imgName = img.src.toUpperCase()
           if (imgName.substring(imgName.length-3, imgName.length) == "PNG")
           {
             var imgID = (img.id) ? "id='" + img.id + "' " : ""
             var imgClass = (img.className) ? "class='" + img.className + "' " : ""
             var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' "
             var imgStyle = "display:inline-block;" + img.style.cssText 
             if (img.align == "left") imgStyle = "float:left;" + imgStyle
             if (img.align == "right") imgStyle = "float:right;" + imgStyle
             if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle
             var strNewHTML = "<span " + imgID + imgClass + imgTitle
             + " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";"
             + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
             + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>" 
             img.outerHTML = strNewHTML
             j = j-1
           }
       }
     }    
}
//监视浏览器的关闭动作，然后调用doLogout()向服务器发送一个请求---by ldq
	 window.onunload=function (){   
		if(event.clientX<0&&event.clientY<0){ 
		   logoutFromGD();  
	   }
	 }

window.attachEvent("onload", correctPNG);
</script>

</head>

<body onload="initPageItems('xml/subsystems.xml','4');initLoginArea('<%= loginInfo%>')">
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr >
    <td  align="center"  style="font-size:30;height:90;color:white" id="titleTd">广东总队综合信息平台</td>
  </tr>
  <tr>
    <td id="itemTD">

    </td>
  </tr>
  <tr>
    <td height="20">&nbsp;</td>
  </tr>
  <tr>
    <td id="loginTd">
       <table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" >
	       <tr>
		        <td width="26%" class="whitefont">用户名：
		            <input id="pname" name="textfield" type="text" size="10" onKeyDown="if(event.keyCode==13 || event.keyCode == 32) loginFromGD();"/>
		        </td>
		        <td width="25%" class="whitefont">密 码：
		          <input id="pwd" name="textfield2" type="password" size="10" onKeyDown="if(event.keyCode==13 || event.keyCode == 32) loginFromGD();" />        
		        <td width="17%"><a href="#" onclick="loginFromGD();"><img src="image/login/gd/dl.png" width="94" height="26" border="0"  /></a>        
		        <td width="17%"><a href="#" onclick="logoutFromGD('header')"><img src="image/login/gd/zx.png" width="94" height="26" border="0" /></a>        
		        <td width="17%"><a href="#" onclick="updatePwdFromGD()"><img src="image/login/gd/xgmm.png" width="94" height="26" border="0" /></a>
	       </tr>
        </table>   
     </td>
  </tr>
</table>
</body>
</html>
