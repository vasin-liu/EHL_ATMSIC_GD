<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.base.table.*" %>
<%@ include file="../../../base/Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");//获取../../js/base/dataDict.js文件中函数onLoadToolbar()传来的insrtOrUpdt值	String did = request.getParameter("did"); //获取../../js/base/dataDict.js文件中函数onLoadToolbar()传来的did值    String idtype = request.getParameter("idtype");//获取../../js/base/dataDict.js文件中函数onLoadToolbar()传来的代码类别 
%>
<html>
	<head>
		<title>数据字典管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>	  
        <script type="text/javascript" src="../../js/sysmanage/codeManage.js"></script>
        <script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
     </head>
<body onLoad=doQuery('<%=did%>','<%=idtype%>')>
	<div style="padding-left: 15px;text-align: center">
	   <fieldset style="width:300;height:240;border:1px solid #CCCCCC" align="center" >
	   <br> 
	   <legend style="border:0px;">字典信息编辑</legend><br>
		   <table width="300" align="center" class="tableInput" id="dataTable">
		    <tr>
		    	<td width="31%" align="right">系统类别：</td>
				<td width="66%" id="tdXtlb">
					<input type="text" class="text" id="XTLB" maxlength="20"  readonly/>
				</td>
				<td width="3%" class="RedFont">※</td>
			</tr>
		    <tr>
		        <td><msg:Common_zh.code.jsp.Category>：</td>
		        <td id="tdDmlb">
		              <input type="hidden"  id="DMLB" class="text" readonly/>
		               <input type="text"  id="codetype" class="text" readonly/>
			  </td>
			  <td width="3%" class="RedFont">※</td>
		    </tr>
		    <tr>
		        <td><msg:Common_zh.code.jsp.code>：</td>
		      	<td><input type="text" class="text" id="DM" maxlength="20" value='<%=did %>' readonly/></td>
		      	<td width="3%" class="RedFont">※</td>
		    </tr>
		    <tr>
				<td><msg:Common_zh.code.jsp.desc>：</td>
			  	<td><input type="text" name="DMSM" class="text" id="DMSM" maxlength="60" /></td>
			</tr>
			 <tr>
				<td><msg:Common_zh.Global.remark.desc>：</td>
			   	<td><input type="text" name="BZ" class="text" id="BZ" maxlength="500"/></td>
			</tr>
			<tr>
			    
		        <td class="tdRight" colspan="2"><br>
			        <div align="center">
				      	<input type="image" src="../../image/button/btnsave.gif"  name="button"  value="<msg:Common_zh.Global.save.desc>"  onClick="modify(<%=insrtOrUpdt %>);"/>
				        <input type="image" src="../../image/button/btnreset.gif" name="button"  value="<msg:Common_zh.Global.reset.desc>" onClick="reset();"/>
				        <input type="image" src="../../image/button/btnclose.gif" name="button"  value="<msg:Common_zh.Global.close.desc>" onClick="window.close();"/>
		            </div>
		        </td>
		     </tr>
			</table> 
		</fieldset>
	</div>
</body>
</html>
