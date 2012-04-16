<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.cdispatch.action.AlarmPublishAction"%>
<%
String allZdAllAlarmCount = (String)request.getAttribute(AlarmPublishAction.ALLZDAllALARMCOUNT_VAR);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>交警提示信息发布</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  	<script>
		window.allZdAllAlarmCount = <%=allZdAllAlarmCount%>;
	</script>
	<script type="text/javascript" src="dispatch/js/ceditpolice/alarmPublish.js"></script>
	<style type="text/css">
		body{
			margin: 0px;
			padding: 0px;
		}
		.paddingDiv{
			width: 100%;
			height: 100%;
			background-color: white;
			font-size: 12px;
		}
		a{
			text-decoration: none;
		}
	</style>
  </head>
  
  <body>
  	<TABLE class="paddingDiv" id="allZdAllAlarmCount" BORDER-COLLAPSE: collapse" cellSpacing=0 border=0>
		<td style="width: 200px;" align="center">
			暂时未取到排行榜信息
		</td>
	</TABLE>
  </body>
</html>
