<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.user.UserManage"%>	
<%@ page import="com.ehl.base.Constants"%>	
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>
<%
    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//单位名称
    String ccbm=strObj[2];//单位层次编码
 %>
<%
	String insrtOrUpdt =request.getParameter("insrtOrUpdt") == null? "" :request.getParameter("insrtOrUpdt");
	String did = request.getParameter("did")== null? "" :request.getParameter("did"); 
%>
<html>
	<head>
		<title>用户管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>	
		<script type="text/javascript" src="../../js/user/UserManage.js"></script>
		<script type="text/javascript" src="../../js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
        <script type="text/javascript" >
			function writePwd(){
				var REPWD_Desc = document.getElementById("REPWD_Desc");
				REPWD_Desc.innerHTML = "重复密码：";
				var pwdDesc = document.getElementById("pwdDesc");
				if(pwdDesc != null){
					pwdDesc.display = "none";
				}
				var REPWD_IN = document.getElementById("REPWD_IN");
				REPWD_IN.innerHTML = "<input id=\"REPWD\" type=\"password\" name=\"PWD\" maxlength=\"20\" style=\"width:280\"  class=\"text\">";
			}
        </script>
</head>
	<body  SCROLL="no">
		<fieldset style="width:430;height:360;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:0px;">
				<%=insrtOrUpdt.equals("0")?"用户信息新增":"用户信息编辑"%>
			</legend>
			<table width="400" align="center" class="tableInput" id="dataTable">
				<tr style="display:none">
					<td width="30%">用户编号：</td>
					<td width="66%" colspan="3" ><input id="USERCODE" type="text" maxLength="32" name="" class="text" value=<%=did %> style="width:280" readonly=true></input></td>
					<td width="4%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="30%"><msg:Common_zh.userManage.jsp.uname>：</td>
					<td width="66%" colspan="3" ><input id="USERNAME" type="text" name="USERNAME" maxlength="32" style="width:280" class="text"></input></td>
					<td width="4%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="30%"><msg:Common_zh.userManage.jsp.dname>：</td>
					<td width="66%" colspan="3">
						<table width="100%">
							<tr>
							    <td width="97%"><input id="DEPTCODE" type="text" name="DEPTCODE" onClick="reset_dept('DEPTCODE');" class="text" style="width:258" onpropertychange=doChange(<%=insrtOrUpdt %>) readonly=true></input></td>
								<td width="3%">
									<img src="../../image/popup/btnselect.gif" alt="选择单位"  style="cursor:hand;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','DEPTCODE','40','152')">
								</td>
							</tr>
						</table>
					</td>
					<td width="4%" class="RedFont">※</td>
				</tr>
				<tr>
					<td width="30%"><msg:Common_zh.user.jsp.pname>：</td>
					<td width="66%" colspan="3" id="tdPreson">					
						<script language="javascript">
	   						fillListBox("tdPreson","PERSONCODE","280","select ryid,xm from t_sys_person ","<msg:Common_zh.userManage.jsp.option_null>","doQuery(<%=did %>)");
					    </script>
					</td>
					<td width="4%"></td>
				</tr>
				<tr>
					<td width="30%"><msg:Common_zh.user.jsp.pwd>：</td>
					<td width="66%" colspan="3"><input id="PWD" type="password" name="PWD" maxlength="20" style="width:280" onkeydown="writePwd();" onMouseDown="writePwd();" onKeyUp=pwStrength(this.value) onBlur=pwStrength(this.value) class="text"></input></td>
					<td width="4%"></td>
				</tr >
				<tr heigth="5">
					<td width="30%" id="REPWD_Desc"></td>
					<td width="66%" colspan="3" align="left" id="REPWD_IN">
						<div align="left" id="pwdDesc">
							<span class="RedFont"><%=insrtOrUpdt.equals("0")?"默认密码为\"888888\"，点击输入框填写密码！":"点击输入框修改密码！"%></span>
						</div>
					</td>
					<td width="4%"></td>
			  	</tr>
				<tr>
					<td width="30%">密码强度：</td>
					<td width="22%" id="strength_L" style="text-align:center">弱</td>
					<td width="22%" id="strength_M" style="text-align:center">中</td>
					<td width="22%" id="strength_H" style="text-align:center">强</td>
					<td width="4%"></td>
				</tr>
				<tr>
					<td width="30%">应用场景选择：</td>
					<td width="66%"  colspan="3" id='apptd'>
					   <script language="javascript">
	   						fillListBox("apptd","APPID","280","select appid,appdesc from t_sys_application","未选择","doQuery(<%=did %>)");
					    </script>
                    </td>
                    <td width="4%"></td>
				</tr>
			  	<tr>
					<td width="30%">用户IP：</td>
					<td width="66%" colspan="3"><input id="userIP" type="text" name="userIP" maxlength="200" style="width:280" class="text"></input></td>
					<td width="4%" class="RedFont"></td>
				</tr>			
				<tr>
					<td width="30%"><msg:Common_zh.user.jsp.role>：</td>
					<td width="66%"  colspan="3" align="left">
						<div align="left" id="role_id" class="text" style="overflow:auto;width:280;height:200;">
							<%=UserManage.getRoles()%>
						</div>
					</td>
					<td width="4%" class="RedFont">※</td>
				</tr>
			  	<tr>
					<td width="30%"><msg:Common_zh.Global.remark.desc>：</td>
					<td width="66%" colspan="3"><input id="REMARK" type="text" name="REMARK" maxlength="200" style="width:280" class="text"></input></td>
					<td width="4%" class="RedFont"></td>
				</tr>				
				<tr>
					<td class="tdRight" colspan="5">
						<div align="center" >
							<input type="image" src="../../image/button/btnsave.gif" name="button"
								value="<msg:Common_zh.Global.save.desc>" onClick="modify(<%=insrtOrUpdt%>);" />
								<%if(insrtOrUpdt.equals("0")) {%>
							<input type="image" src="../../image/button/btnreset.gif" name="button"
								value="<msg:Common_zh.Global.reset.desc>" onClick="reset();" />
								<%} %>
							<input type="image" src="../../image/button/btnclose.gif" name="button"  value="<msg:Common_zh.Global.close.desc>" onClick="window.close();"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</body>
</html>
