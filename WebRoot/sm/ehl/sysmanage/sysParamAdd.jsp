<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<!--
    * author：zhaoyu
    * date:   2008-01-22
    * version:
-->
<% String type=request.getParameter("paramType")==null ? "" :request.getParameter("paramType"); 
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="../../css/nioa.css" rel="stylesheet" type="text/css">
	<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
	<script type="text/javascript" src="../../js/sysmanage/sysParamAdd.js">
	</script>
	<script type="text/javascript"> 
	function init(){
	document.all("paramDesc").focus();
	}
	</script>   
	<title><msg:Common_zh.sysparam.add.title></title>
</head>
<body onLoad="init();">
<form name="myform" action="" method="post">
	<fieldset style="width:230;height:270;border:1px solid #CCCCCC" align="center" ><br> 
		<center><caption><font size=3><strong><msg:Common_zh.sysparam.add.caption></strong></font></caption> </center>
			
			<table width="200" align="center">
				<tr>
					<td width="40%" align="center"><font  size=2><msg:Common_zh.sysparam.desc>:</font></td>
					<td width="60%" align="left"><input type="text" name="paramDesc" class="text" id="paramDesc" /></td>
				</tr><br>
				<tr align="right">
					<td width="40%" align="center"><font  size=2><msg:Common_zh.sysparam.value>:</font></td>
					<td width="60%" align="left"><input  type="text" name="paramValue" class="text" id="paramValue" /></td>
				</tr><br>
			</table>
			
			<table width=100% align="right">
				<tr> <input  style="visibility:hidden" type="text" name="paramType" id="paramId" value=<%=type%> />
					<td>      
					
					  <div align="center">
					    <input  type="image" src="../../image/button/btnsave.gif" name="button"  value='<msg:Common_zh.Global.save.desc>' onClick="modify();"/>
					    <input  type="image" src="../../image/button/btnreset.gif"  name="reset"  value='<msg:Common_zh.Global.reset.desc>'  /> 
					    <input  type="image" src="../../image/button/btnclose.gif" name="close"  value='<msg:Common_zh.Global.close.desc>' onClick="clse();"/>					
			          </div></td>
				</tr><br><br>
		  </table>
	
	</fieldset>
</form> 
</body>
</html>
