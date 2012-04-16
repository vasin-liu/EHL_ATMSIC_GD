<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.base.table.*" %>
<%@ include file="../../../base/Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
    String idtype = request.getParameter("idtype");
%>
<html>
	<head>
		<title>代码类别管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>	  
        <script type="text/javascript" src="../../js/sysmanage/codeTypeManage.js"></script>
        <script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
     </head>
<body onLoad="doQuery('<%=idtype%>');">
 <div style="padding-left: 15px;text-align: center">
   <fieldset style="width:300;height:220;border:1px solid #CCCCCC" align="center" >
   <br> 
   <legend style="border:0px;">代码类别编辑</legend><br>
   <table width="300" align="center" class="tableInput" id="dataTable">
    <tr>
      	<td width="31%" align="right">系统类别：</td>
		<td width="66%" id="tdXtlb">
			<input type="text" name="XTLB" class="text" id="XTLB" readonly/>
		</td>
		<td width="3%" class="RedFont">※</td>
	</tr>
	 <tr style="display:none">
      	<td width="31%">代码类别：</td>
        <td width="66%" id="tdDmlb_e">
        	<input type="text" name="DMLB" class="text" id="DMLB" value='<%=idtype %>'readonly/>
	  	</td>
	  	<td width="3%" class="RedFont">※</td>
    </tr>
    <tr>
	  	<td width="31%">代码类别说明：</td>
		<td width="66%"><input type="text" name="DMLBSM" class="text" id="DMLBSM" maxlength="60" /></td>
		<td width="3%" class="RedFont">※</td>
	</tr>
	 <tr>
	   	<td width="31%">备注：</td>
		<td width="66%"><input type="text" name="BZ" class="text" id="BZ" maxlength="500"/></td>
	</tr>
	<tr>  
        <td class="tdRight" colspan="2"><br>
	        <div align="center">
		      	<input type="image" src="../../image/button/btnsave.gif" 	name="button"  value="<msg:Common_zh.Global.save.desc>"  onClick="modify(<%=insrtOrUpdt %>);"/>
		        <input type="image" src="../../image/button/btnreset.gif"  	name="button"  value="<msg:Common_zh.Global.reset.desc>" onClick="reset();"/>
		        <input type="image" src="../../image/button/btnclose.gif" 	name="button"  value="<msg:Common_zh.Global.close.desc>" onClick="window.close();"/>
            </div>
        </td>
     </tr>
</table> 
</fieldset>
</div>
</body>
</html>
