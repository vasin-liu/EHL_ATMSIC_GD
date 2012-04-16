<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html>
  <head>
    <title>违法信息统计示意图</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
  </head>
  <%
  	String timetype = request.getParameter("timetype");
	String stattype = request.getParameter("stattype");
	String starttime = request.getParameter("starttime");
	String endtime = request.getParameter("endtime");
	String monthYear = request.getParameter("monthYear");
	String monthMonth = request.getParameter("monthMonth");
	String year = request.getParameter("year");
  %>
<body class="body" >
	  <div   align = "center" >
	     	<img src="tira.VioLineChart.draw.d?timetype=<%=timetype%>&stattype=<%=stattype%>&starttime=<%=starttime%>&endtime=<%=endtime%>&monthYear=<%=monthYear%>&monthMonth=<%=monthMonth%>&year=<%=year%>"/>
      </div>
</body>
</html>
	   