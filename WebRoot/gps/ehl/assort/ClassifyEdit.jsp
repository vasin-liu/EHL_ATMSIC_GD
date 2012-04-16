<!DoCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String insrtOrUpdt = request.getParameter("insertorupdate");
	String cataid = request.getParameter("cataid");
%>
<html>
	<head>
	   
		<title>车辆分类管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../css/font.css" rel="stylesheet" type="text/css">
		<link href="../../css/zt.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css"> 
		<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script> 
		<script type="text/javascript" src="../../js/classify/GpsCarClassify.js"></script>
		<script language="javascript">
      	</script>
	</head>
	<body onLoad="doQueryById('<%=cataid %>');" style="overflow:hidden">
	<div style="padding-left: 16px;text-align: center">
		<fieldset style="width:360px;height:180px;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:1px;">
			    分类编辑
			</legend>
			<table width="300" align="center" class="tableInput" id="dataTable">
				<tr style="display:none">
					 <td width="31%" align="right">分类编号：</td>
					 <td>
				     <input type="text" class="text" id="cataid"></input>
				     </td>
				</tr>
				<tr>
					 <td width="31%" align="right">分类名称：</td>
				     <td width="66%">
				     <input type="text" class="text" id="cataname" size="24" />   
				     </td>
				     <td width="3%" class="RedFont">
					  ※
					 </td>
				</tr>
				<tr></tr>
			    <tr>
			       <td width="31%" align="right">描述：</td>
			       <td width="66%">
			       <textarea id="bz" ></textarea>
			       </td>
			    </tr>
				<tr>
					<td class="tdRight" colspan="3"><br>
					
						<div align="right">
							<img src="../../images/button/btnsave.gif" style="cursor:hand" onClick="save('<%=insrtOrUpdt %>');">
							<img src="../../images/button/btnreset.gif" style="cursor:hand" onClick="reset();">
							<img src="../../images/button/btnclose.gif" style="cursor:hand" onClick="doClose();"></div></td>
				</tr>
		</table>
	  </fieldset>
		</div>
	</body>
</html>
