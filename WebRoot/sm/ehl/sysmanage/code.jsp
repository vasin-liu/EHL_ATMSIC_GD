<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.base.table.*" %>
<%@ include file="../../../base/Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
	String XTLB = request.getParameter("XTLB") == null ? "": request.getParameter("XTLB");
    String DMLB = request.getParameter("DMLB") == null ? "": request.getParameter("DMLB");
%>
<html>
	<head>
		<title>数据字典管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>	  
        <script type="text/javascript" src="../../js/sysmanage/codeManage.js"></script>
        <script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
        <script language="javascript">
        	function setCode_Dict(){
        		var XTLB = document.getElementById("XTLB");
				var DMLB = document.getElementById("DMLB");
				if(XTLB != null && XTLB != undefined){
					XTLB.value = '<%=XTLB%>';
				}
				if(DMLB != null && DMLB != undefined){
					DMLB.value = '<%=DMLB%>';
				}
        	}
        </script>
     </head>
<body>
 <div style="padding-left: 15px;text-align: center">
   <fieldset style="width:300;height:260;border:1px solid #CCCCCC" align="center" >
   <br> 
   <legend style="border:0px;">字典信息新增</legend><br>
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
      	<td width="31%"><msg:Common_zh.code.jsp.Category>：</td>
        <td width="66%" id="tdDmlb">
		  <script language="javascript">
			 fillListBox("tdDmlb","DMLB","100%","SELECT dmlb,dmlbsm FROM t_sys_codetype","未选择","setCode_Dict()");
		  </script>
	  	</td>
	  <td width="3%" class="RedFont">※</td>
    </tr>
    <tr>
      	<td width="31%"><msg:Common_zh.code.jsp.code>：</td>
        <td width="66%"><input type="text" class="text" id="DM" maxlength="20"/></td>
        <td width="3%" class="RedFont">※</td>
    </tr>
    <tr>
	  	<td width="31%"><msg:Common_zh.code.jsp.desc>：</td>
		<td width="66%"><input type="text" name="DMSM" class="text" id="DMSM" maxlength="60" /></td>
	</tr>
	 <tr>
	   	<td width="31%"><msg:Common_zh.Global.remark.desc>：</td>
		<td width="66%"><input type="text" name="BZ" class="text" id="BZ" maxlength="500"/></td>
	</tr>
	<tr>  
        <td class="tdRight" colspan="2"><br>
	        <div align="center">
		      	<input type="image" src="../../image/button/btnsave.gif" name="button"  value="<msg:Common_zh.Global.save.desc>"  onClick="modify(<%=insrtOrUpdt %>);"/>
		        <input type="image" src="../../image/button/btnreset.gif"  name="button"  value="<msg:Common_zh.Global.reset.desc>" onClick="reset();"/>
		        <input type="image" src="../../image/button/btnclose.gif" name="button"  value="<msg:Common_zh.Global.close.desc>" onClick="window.close();"/>
            </div>
        </td>
     </tr>
</table> 
</fieldset>
</div>
</body>
</html>
