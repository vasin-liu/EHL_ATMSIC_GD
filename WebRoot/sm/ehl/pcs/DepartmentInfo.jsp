<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String jgid = request.getParameter("jgid"); 

%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../../../base/ShareInc.html" />
		<script type="text/javascript" src="../../js/pcs/PersonManage.js"></script>
		<title>机构管理</title>
	</head>
	<body onload="loadDeptInfo('<%= jgid%>');">
	<div style="padding-left: 16px;text-align: center">
		<fieldset style="width:600;height:370;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:0px;">
				组织机构信息
				<br>
			</legend>
			<table width="100%" align="center" class="tableInput" id="dataTable">
				 <tr bgcolor="#FFFFFF">
				    <td>机构类型：</td>
					<td width="30%" id="DEPARTMENTLX">
					   <input type="text" id="JGLX" class="text" maxLength="60" /></td>
					  <td>&nbsp;</td> 
				    <td width="17%"> 机构名称： </td>
				    <td width="32%"><input type="text" id="JGMC" class="text" maxLength="15" /></td>
				     <td>&nbsp;</td>
			    </tr>
				<tr>
					<td>行政级别：</td>
					<td width="30%" id="JGXZJB">
					    <input type="text" id="XZJB" class="text" maxLength="60" />
					</td>
					
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
				  <td >所处地址：</td>
					<td colspan="4"><input type="text" id="SCDZ" class="text" maxLength="60" /></td>
					<td>&nbsp;</td>
				    
				</tr>
				<tr>
					<td>邮政编码：</td>
					<td width="32%"><input type="text" id="YZBM" class="text" maxLength="6" /></td>
					<td>&nbsp;</td>
					<td>编制人数：</td>
					<td width="32%"><input type="text" id="BZRS" class="text" maxLength="10" /></td>
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
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td>负责人电话：</td>
					<td><input type="text" id="FZRDH" class="text" maxLength="13" /></td>
					<td>&nbsp;</td>
					<td>负责人手机：</td>
					<td><input type="text" id="FZRSJ" class="text" maxLength="11" /></td>
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td>值班电话1：</td>
					<td><input type="text" id="ZBDH1" class="text" maxLength="13" /></td>
					<td>&nbsp;</td>
					<td>值班电话2：</td>
					<td><input type="text" id="ZBDH2" maxlength="13" class="text" /></td>
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td>值班电话3：</td>
					<td><input type="text" id="ZBDH3" class="text" maxLength="13" /></td>
					<td>&nbsp;</td>
					<td>传真：</td>
					<td><input type="text" id="CZDH" class="text" maxLength="13" /></td>
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td class="tdRight" colspan="6" align="center"><div align="center"><br>
					  
					  <img src="../../image/button/btnclose.gif" style="cursor:hand" onClick="window.close();">					  
				    </div></td>
				</tr>
			</table>
	  </fieldset>
		</div>
	</body>
</html>
