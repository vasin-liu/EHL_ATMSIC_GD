<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page  import="com.appframe.data.sql.DBHandler"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>录入值班电话</title>
	<script type="text/javascript" src="../../../base/js/jquery.js"></script>
	<script type="text/javascript" src="../../js/duty/duty.js"></script>
	<script type="text/javascript">
	
	</script>
  </head>
  <%
  	String sql = "select jgid,jgmc,zbdh1 from t_sys_department";
  	Object[][] result = DBHandler.getMultiResult(sql);
  %>
  <body align="center">
   <table>
   <tr align="center"><td colspan="2">录入值班电话</td></tr>
    <tr>
    <td>部门名称</td>
    <td><select id="sel" onchange="changeSel();">
    <option value="">--请选择部门名称--</option>
    <%for(int i=0;i<result.length;i++){
    	 
    %>
    <option value="<%=result[i][0] %>"><%=result[i][1] %></option>
    <%} %>
    </select></td>
    </tr>
     <tr>
    <td>值班电话</td>
    <td><input type="text" id="tel" size="25"></td>
    </tr>
    <tr align="center">
    <td colspan="2"><input type="button" id="btn" value="添加" onclick="btnTel();"></td>
    </tr>
    <tr align="center"><td colspan="2"><div id="msg"></div></td></tr>
   </table>
  </body>
</html>
