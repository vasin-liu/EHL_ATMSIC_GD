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
<body>
 <div style="padding-left: 15px;text-align: center">
   <fieldset style="width:300;height:260;border:1px solid #CCCCCC" align="center" >
   <br> 
   <legend style="border:0px;">代码类别新增</legend><br>
   <table width="300" align="center" class="tableInput" id="dataTable">
    <tr>
      	<td width="31%" align="right">系统类别：</td>
		<td width="66%" id="tdXtlb">
			<script language="javascript">
				fillListBox("tdXtlb","XTLB","100%","SELECT substr(ID,0,2),TEXT FROM t_sys_func where id like '%0000'","未选择");
		  	</script>
		</td>
		<td width="3%" class="RedFont">※</td>
	</tr>
	 <tr>
      	<td width="31%">代码类别标准：</td>
        <td width="66%" id="tdDmlb">
        	<select name="DMLB" id="DMLB">
        		<option value="">未选择</option>
        		<option value="1">国标</option>
        		<option value="2">行业标准</option>
        		<option value="3">自定义编码</option>
        		<option value="4">系统管理编码</option>
        	</select>
	  	</td>
	  	<td width="3%" class="RedFont">※</td>
    </tr>
    <tr>
	  	<td width="31%">代码类别说明：</td>
		<td width="66%"><input type="text" name="DMLBSM" class="text" id="DMLBSM" maxlength="60" /></td>
		<td width="3%" class="RedFont">※</td>
	</tr>
	 <tr>
	   <td width="31%"><msg:Common_zh.Global.remark.desc>：</td>
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
