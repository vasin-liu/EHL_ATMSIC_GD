<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@page import="com.ehl.loginstate.StateListener,com.appframe.utils.StringHelper"%>
 <%@ page import="com.ehl.base.util.BaseUtil" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
  	<%
	String title ="在线用户统计";
	String deptid=StringHelper.obj2str(request.getParameter("deptid"),"");
	String sysid=StringHelper.obj2str(request.getParameter("sysid"),"");
	String sysname=StringHelper.obj2str(request.getParameter("sysname"),"");
	deptid =deptid.trim();
	sysid  =sysid.trim();
	StateListener st = new StateListener();
	String jgid = BaseUtil.getSysOption("02","02001");
	String res=st.getStatisticUser(jgid,deptid,sysid);
	String allUserCnt=st.getAllUserCount();
	if(sysname.length()>0){
		title=sysname+title;
	} 
   
 %>
 
    <title>在线用户统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include flush="true" page="../base/ShareInc.html"></jsp:include>	
	<Link Rel="STYLESHEET" Href="../css/pagetag/pagetag.css" Type="text/css">   
	<script type="text/javascript" src="../base/js/prototype.js"></script> 
	<script type="text/javascript" src="js/loginState.js"></script>
	<script type="text/javascript">
		function addTree(){ 
			var xmlTest =document.getElementById("xmlText").value; 
			var xmlObj = createXmls(xmlTest); 
			setTree(xmlObj);
		}
		function createXmls(str){ 
		　　if(document.all){ 
			　　var xmlDom=new ActiveXObject("Microsoft.XMLDOM");
			　　xmlDom.loadXML(str);
			　　return xmlDom ;
		　　} 
		　　else{ 
		　　		return new DOMParser().parseFromString(str, "text/xml");
			} 
		　}
		
	</script>
	 
	 <style type="text/css">
 		 
	#role_id{
		overFlow-x: hidden; overFlow-y: scroll; width:380px; height: 450px; 
		border: 1 solid; text-align: left; padding-left: 10px;padding-right: 10px;
		padding-top: 0px;
	}
	#msg a {
		padding: 1px 7px;
		line-height: 16px;
		margin: 0px 1px 0px 0px;
		text-decoration: underline;
		color: #07679c;
		float: none;
	}
 	</style>

  </head>
  
  <body onload="addTree()">
  <div style="padding-left: 10px;text-align: center">
  	<input id="xmlText" value="<%=res %>" type="text" style="display: none;">
  	<fieldset >
	    <legend style="border:0px;"><%=title %><font color="red"> (在线总人数：<%=allUserCnt %>人)</font></legend>
	    <font color="red">
	    	<input type="button" value="查看全部部门" onclick="window.location.reload();">&nbsp;&nbsp;&nbsp;&nbsp;
	    	<input type="button" value="查看在线部门" onclick="showOnlineUsers();">
		</font> 
		<div id="role_id"  class="text" style="background-color:#DFEAF7;">   
		 	 
		 </div> 
		<table>
	        <tr><td><a href="#" onclick="window.close()"><image border="0" src="../image/button/btnclose.gif"/></a></td></tr>
	    </table>
	</fieldset>
	
	</div>
  </body>
</html>
