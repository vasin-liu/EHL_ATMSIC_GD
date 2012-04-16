<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.cdispatch.action.AlarmPublishAction"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@page import="com.appframe.common.Setting"%>
<%
String ctime = Constant.getCurrentTime(true).substring(0,10);
String ptime = Setting.getString("publish_time");
String zdAllDdAlarmCount = (String)request.getAttribute(AlarmPublishAction.ZDALLDDALARMCOUNT_VAR);

String title = "路面交通动态信息发布情况表";
String statisDate = "（8月份至今）";
if(ctime.substring(0,4).equals(ptime.substring(0,4))){
	statisDate = "<br>" + statisDate;
}else{
	statisDate = "";
}
%>
<script type="text/javascript">
window.zdAllDdAlarmCount = <%=zdAllDdAlarmCount%>
window.ctime = "<%=ctime%>";
</script

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><%=title%></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="dispatch/js/ceditpolice/alarmPublish.js"></script>
	
  </head>
  
  <body style="text-align: center;overflow: auto;">
    <h4 id="title" style="text-align: center;"><%=title%><%=statisDate %></h4>
    <table id="contain" cellspacing="0" cellpadding="0" border="1" bordercolor="#b5d6e6" style="text-align: center;"> 
    </table>
  </body>
</html>
