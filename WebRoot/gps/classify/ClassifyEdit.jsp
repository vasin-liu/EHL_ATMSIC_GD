<!DoCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String insrtOrUpdt = request.getParameter("insertorupdate");
	String cataid = request.getParameter("cataid");
	//System.out.println(insrtOrUpdt+"--"+CLASSID);
%>
<html>
	<head>
	   
		<title>车辆分类管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="../js/classify/GpsCarClassify.js"></script>
		<script type="text/javascript" src="../../gzzd/js/Prototype.js"></script>
		<script language="javascript">
      	</script>
	</head>
	<body onLoad="doQueryById('<%=cataid %>');" style="overflow:hidden">
	<div style="padding-left: 16px;text-align: center">
		<fieldset style="width:360px;height:420px;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:1px;">
			    分类编辑
			</legend>
			<table width="100%" height="420" align="center" border='0' >
				<tr>
					 <td align="left">分类编号：</td>
					 <td colspan="2" id="">
				     <input type="text" class="text" id="cataid" ></input></td>
				      <td>&nbsp;</td>
				</tr>
				<tr>
					 <td align="left">分类名称：</td>
				     <td colspan="2"><input type="text" class="text" id="cataname" ></input></td>
				      <td>&nbsp;</td>
				</tr>
				
			    <tr>
			       <td align="left">备注</td>
			      <td colspan="2"><input type="text" class="text" id="bz" ></input></td>
			    </tr>
		       
				<tr>
					<td class="tdRight" colspan="3"><br>
					
						<div align="right">
							<img src="../../sm/image/button/btnsave.gif" style="cursor:hand" onClick="save('<%=insrtOrUpdt %>');">
							<img src="../../sm/image/button/btnreset.gif" style="cursor:hand" onClick="reset();">
							<img src="../../sm/image/button/btnclose.gif" style="cursor:hand" onClick="doClose();"></div></td>
				</tr>
		</table>
	  </fieldset>
		</div>
	</body>
</html>
