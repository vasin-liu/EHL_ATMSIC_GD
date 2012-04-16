<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>交警提示信息发布</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/dynamicinfo/css/policeremind.publish.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/prototype.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/new/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dynamicinfo/js/dynamicinfo/policeRemind/policeRemind.dynamic.update.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/new/api.date.js"></script>
  	<script type="text/javascript">
  		window.onload = function(){
  			$policeRemind.rollInfo.request({publishtype:"1"},function(xmlHttp,rollInfo){
  				rollInfo.setHtml();
  				$("msg").innerHTML = rollInfo.html||rollInfo.nodata;
  			});
  		};
  	</script>
  </head>
  
  <body>
    <marquee id="marquee" class="marquee" direction="up" scrolldelay="1" behavior="scroll" scrollamount="2" onmouseout="this.start();" onmouseover="this.stop();"  > 
   	 	<div id="msg" class="message">
<%-- 	   	 	<c:forEach var="policeWork" items="${requestScope.policeRemind}"  > --%>
<!-- 	  	 		<p id="msg" class="have"> -->
<%-- 	  	 			<c:out value="${policeWork[1]}消息：${policeWork[4]}　－【${policeWork[3]}】"></c:out> --%>
<!-- 	  	 		</p> -->
<%-- 	 	 	</c:forEach> --%>
<%-- 	 	 	<c:if test="${requestScope.policeRemind == null}"> --%>
<!-- 	 	 		<p id="noMsgPrompt" class="no"> -->
<!-- 	 	 			今日没有提示信息！ -->
<!-- 	 	 		</p> -->
<%-- 	 	 	</c:if> --%>
   	 	</div>
    </marquee>
  </body>
</html>
