<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String fileName = request.getParameter("fileName");
	String graphURL = request.getParameter("graphURL");
%>

<html>
  <head>
    <title><msg:Common_zh.Global.chart.title></title>
	<link rel="stylesheet" type="text/css" href="css/Global.css">    
  </head> 
  <body class="FootText">
	<table width="100%" border="0" cellspacing="0" class="ContentText">
	  <tr>
		<td height="2%">&nbsp;
		</td>
	  </tr>
	  <tr>
		<td width="5%">
		</td>
		<td align="center">
		   <P ALIGN="CENTER">
		   <img src="<%=graphURL%>" width=750 height=410 border=0 usemap="#<%=fileName%>">
		   </P>
		</td>
	  </tr>
	  <tr>
		<td width="5%">
		</td>
		<td align="right">
			<a href="#" onclick="javascript:window.print();">打印</a>
		</td>		
	  </tr>
	</table>
  </body>
</html>
