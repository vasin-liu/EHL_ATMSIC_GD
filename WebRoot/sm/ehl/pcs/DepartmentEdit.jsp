<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.*"%>
<%@ include file="../../../base/Message.oni"%>
<!--
    * author：郭田雨
    * date:   2008-04-10
    * version:
-->
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");//获取../../js/tgsinfo/BasicInfoManage.js文件中函数onLoadToolbar()传来的insrtOrUpdt值
	String JGID = request.getParameter("JGID"); //获取../../js/tgsinfo/BasicInfoManage.js文件中函数onLoadToolbar()传来的SBBH值
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../../../base/ShareInc.html" />
		<script type="text/javascript" src="../../js/pcs/DepartmentManage.js"></script>
		<script type="text/javascript" src="../../js/pcs/DepartmentTree.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<title>机构管理</title>
	</head>
	<body>
	<div style="padding-left: 16px;text-align: center">
		<fieldset style="width:600;height:370;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:0px;">
				<%=insrtOrUpdt.equals("0")?"组织机构信息新增":"组织机构信息编辑"%>
				<br>
			</legend>
			<table width="100%" align="center" class="tableInput" id="dataTable">
				<tr bgcolor="#FFFFFF">
					<td width="16%">机构ID：</td>
				  <td width="30%">
				      <input type="text" id="JGID" class="text" value="<% if(insrtOrUpdt.equals("1")){%><%=JGID %><% }%>" maxLength="12" />
				      <input type="hidden" id="parentJGID" class="text" value="<%=JGID %>" maxLength="12" />
				  </td>
				  <td width="2%" class="RedFont">※</td>
				  <td width="17%"> 机构名称： </td>
				  <td width="32%"><input type="text" id="JGMC" class="text" maxLength="15" /></td>
				    <td width="3%" class="RedFont">※</td>
			  </tr>
				<tr style="display:none">
					<td style="display:none">层次编码：</td>
					<td><input type="text" id="JGCCBM" class="text" style="display:none" maxLength="20" /></td>
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td>行政级别：</td>
					<td width="30%" id="JGXZJB">
				  <script language="javascript"> 
					    	fillListBox("JGXZJB","XZJB","100%","SELECT DM,DMSM FROM T_SYS_CODE WHERE DMLB='013013'","未选择");
					    </script>				    </td>
				  <td width="2%" id="JGXZJB">&nbsp;</td>
				  <td>机构职能：</td>
					<td><input type="text" id="JGZN" class="text" maxLength="60" /></td>
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td>业务描述：</td>
					<td colspan="4"><input type="textarea" id="YWMS" class="text" maxLength="30" /></td>
			        <td>&nbsp;</td>
				</tr>
				<tr>
					<td>机构类型：</td>
					<td width="30%" id="DEPARTMENTLX">
					  <script language="javascript">
					    	fillListBox("DEPARTMENTLX","JGLX","100%","SELECT DM,DMSM FROM T_SYS_CODE WHERE DMLB='013010'","未选择","<% if(insrtOrUpdt.equals("1")){%> doQuery('<%=JGID %>')<%} %><% if(insrtOrUpdt.equals("0")){%> doGetJGCCBM('<%=JGID %>')<%} %>");
					    </script>
					</td>
				  <td width="2%" id="DEPARTMENTLX"class="RedFont">※</td>
				  <td>所处地址：</td>
					<td><input type="text" id="SCDZ" class="text" maxLength="60" /></td>
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td>邮政编码：</td>
					<td><input type="text" id="YZBM" class="text" maxLength="6" /></td>
					<td>&nbsp;</td>
					<td>编制人数：</td>
					<td><input type="text" id="BZRS" class="text" maxLength="10" /></td>
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td>在编民警：</td>
					<td><input type="text" id="ZBMJS" class="text" maxLength="10" /></td>
					<td>&nbsp;</td>
					<td>在编职工：</td>
					<td><input type="text" id="ZBZGS" class="text" maxLength="10" /></td>
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td>其他人数：</td>
					<td><input type="text" id="QTRS" class="text" maxLength="10" /></td>
					<td>&nbsp;</td>
					<td>负责人：</td>
					<td><input type="text" id="FZR" class="text" maxLength="20" /></td>
				    <td width="2%" id="DEPARTMENTLX"class="RedFont"></td>
				</tr>
				<tr>
					<td>负责人电话：</td>
					<td><input type="text" id="FZRDH" class="text" maxLength="13" /></td>
					<td width="2%" id="DEPARTMENTLX"class="RedFont"></td>
					<td>负责人手机：</td>
					<td><input type="text" id="FZRSJ" class="text" maxLength="11" /></td>
				    <td width="2%" id="DEPARTMENTLX"class="RedFont"></td>
				</tr>
				<tr>
					<td>值班电话1：</td>
					<td><input type="text" id="ZBDH1" class="text" maxLength="13" /></td>
					<td width="2%" id="DEPARTMENTLX"class="RedFont"></td>
					<td>值班电话2：</td>
					<td><input type="text" id="ZBDH2" maxlength="13" class="text" /></td>
				    <td width="2%" id="DEPARTMENTLX"class="RedFont"></td>
				</tr>
				<tr>
					<td>值班电话3：</td>
					<td><input type="text" id="ZBDH3" class="text" maxLength="13" /></td>
					<td width="2%" id="DEPARTMENTLX"class="RedFont"></td>
					<td>传真：</td>
					<td><input type="text" id="CZDH" class="text" maxLength="13" /></td>
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td class="tdRight" colspan="6" align="center"><div align="center"><br>
					  <img src="../../image/button/btnsave.gif" style="cursor:hand" onClick="modify(<%=insrtOrUpdt %>);">
					  <%if(insrtOrUpdt.equals("0")) {%>
					  <input type="image" src="../../image/button/btnreset.gif" name="button" onClick="reset();" />
					  <%} %>
					  <img src="../../image/button/btnclose.gif" style="cursor:hand" onClick="window.close();">					  
				    </div></td>
				</tr>
			</table>
	  </fieldset>
		</div>
	</body>
</html>
