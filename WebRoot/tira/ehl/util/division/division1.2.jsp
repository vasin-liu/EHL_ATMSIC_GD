<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String uname = request.getParameter("uname");
String dmlb = request.getParameter("dmlb");
//代码类别为null的处理情况，待定
if ((dmlb == null) || (dmlb.trim().length() == 0)) {
	
}
%>

<script type="text/javascript">
    var uname = "<%=uname%>";
 	var dmlb = "<%=dmlb%>";
 	
	division.createTable("selDivisionTdId");
	division.dmlb = dmlb;
	division.getJgidByUname(uname);
</script>