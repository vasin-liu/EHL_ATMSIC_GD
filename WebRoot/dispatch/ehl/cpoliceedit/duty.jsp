<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.dispatch.bdispatch.util.DispatchUtil"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.ehl.dispatch.duty.dao.DutyDao"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@page import="com.ehl.sm.base.Constant"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<title>值班信息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/jquery.js"></script>
		<script type="text/javascript" src="../../js/duty/duty.js"></script>

		<style type="text/css">
table {
	font-size: 12pt;
	font-weight: normal;
}

td {
	padding-top: 2px;
	padding-bottom: 2px;
}

.text {
	font: 14px Tahoma, Verdana;
	border: 0;
	border-bottom: 1 solid black;
	background: ;
	text-align: center;
	width: 143px;
}
.text_num {
	font: 14px Tahoma, Verdana;
	border: 0;
	border-bottom: 1 solid black;
	background: ;
	text-align: center;
	width: 90px;
}
.text_tel {
	font: 14px Tahoma, Verdana;
	border: 0;
	border-bottom: 1 solid black;
	background: ;
	text-align: center;
	width: 45px;
}

.button {
	font: 12px Tahoma, Verdana;
	padding: 0 5px;
	color: #D3E0E7;
	background-image: url("../../images/button/btn.gif");
	background-repeat: repeat-x;
	background-position: 0 50%;
	outline: 1px solid #D3E0E7;
	border: 1px solid #FFF !important;
	height: 19px !important;
	border: 1px solid #D3E0E7;
	height: 21px;
	line-height: 17px;
}
</style>
		<script type="text/javascript">
	 function resetInppt(type){
	 if(type=="add"){
	 	document.getElementById("leaderAdd").value="";
	 	document.getElementById("personAdd").value="";
	 	}else if(type=="update"){
	 	if($("#leader").attr("disabled")==false){
	 		$("#leader").val("");
	 		$("#person").val("");
	 		}else{
	 		 $("#msg").html("<font color='red' size='-1'>请先点修改按钮</font>");
	 		}
	 	}
	 } 
	</script>
		<script type="text/javascript">
	function closeDiv(){
      var f = window.parent;
      f.cloDiv();
 }
	</script>
	</head>
	
	<%
		String deptId = StringHelper.obj2str(
		request.getParameter("deptId"), "");
		DutyDao dao = new DutyDao();
		Object[] result = dao.getByDeptId(deptId);
		boolean isTodayInfo = false;
		//Modified by Liuwx 2011-08-10
		if(result != null){
			for(int i=0;i< result.length;i++){
				result[i] = StringHelper.obj2str(result[i],"");
			}
			String dtime = (String)result[4];
			isTodayInfo = dao.isTodayInfo(dtime);
			session.setAttribute(Constant.ZBRXM_VAR, result[3]);
			session.setAttribute(Constant.ZBLDXM_VAR, result[2]);
		}else{
			result = new String[]{"","","","","",""};
		}
		//Modification finished
		Object tel = dao.geTelByJgid(deptId);
		String dutyTel = StringHelper.obj2str(tel, "");
		String[] strTel = dutyTel.split("-");
		if(strTel == null) {
			strTel = new String [2];
			strTel[0] = "";
			strTel[1] = ""; 
		} else {
			 if (strTel.length==1) {
			 	strTel = new String [2];
				strTel[0] = "";
				strTel[1] = dutyTel; 
			 }
		}
	%>
	<body>
		<table align="center">
			<tr>
				<td colspan="2" align="center">
					<input type="text" id="deptId" value="<%=deptId%>"
						style="display: none">

				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" id="msg"></td>
			</tr>
			<%
			if (!isTodayInfo) {
			%>
			<tr>
				<td>
					<font>值班领导:</font>
				</td>
				<td>

					<input type="text" id="leaderAdd" class="text" value="<%=Constant.nvl(result[2])%>">
				</td>
			</tr>
			<tr>
				<td>
					<font>值班人员:</font>
				</td>
				<td>
					<input type="text" id="personAdd" class="text" value="<%=Constant.nvl(result[3])%>">
				</td>
			</tr>
			<tr>
				<td>
					<font>值班电话:</font>
				</td>
				<%
				if (dutyTel.equals("")) {
				%>
				<td>
					<input type="text" id="tel1" class="text_tel">-
					<input type="text" id="tel2" class="text_num">

					<%
					} else {
					%>
				
				<td>
					<input type="text" id="tel1" class="text_tel" value="<%=strTel[0]%>">-
					<input type="text" id="tel2" class="text_num" value="<%=strTel[1]%>">
				</td>
				<%
				}
				%>
			</tr>
			<!-- Modified by Liuwx 2011-08-10 -->
			<tr>
				<td>
					<font>值班手机:</font>
				</td>
				<td>
					<input type="text" id="mobilePhoneAdd" class="text" value="<%=Constant.nvl(result[5])%>">(选填)
				</td>
			</tr>
			<!-- Modification finished -->
			
			<input id="appid" type="hidden" value="<%=session.getAttribute("appid") %>" />
			<%
			if(session.getAttribute("appid").equals("1001")){
			%>
			<tr align="center">
				<td style="align: right" colspan="2">
					<input type="button" value="保存" onclick="addInfo();" class="button">
				</td>
			</tr>
			<%
			}else{
			 %>
			 <tr align="center">
				<td>
					<span><input type="button" value="关闭" id="closessBtn"
							class="button" onclick="closeDiv();">
					</span>
				</td>
			</tr>
			<%
			}
			 %>
			


			<%
			} else {
			%>
			</tr>
			<tr>
				<td>
					<font>值班领导:</font>
					<input type="text" id="id" value="<%=result[0]%>"
						style="display: none">
				</td>
				<td>
					<input id="leader" type="text" value="<%=result[2]%>"
						disabled="true" class="text">
				</td>
			</tr>
			<tr>
				<td>
					<font>值班人员:</font>
				</td>
				<td>
					<input id="person" type="text" value="<%=result[3]%>"
						disabled="true" class="text">
				</td>
			</tr>
			<tr>
				<td>
					<font>值班电话:</font>
				</td>
				<td>
					<input id="tel1" type="text" value="<%=strTel[0]%>" disabled="true" class="text_tel">-
					<input id="tel2" type="text" value="<%=strTel[1]%>" disabled="true" class="text_num">
				</td>
			</tr>
			
			<!-- Modified by Liuwx 2011-08-10 -->
			<tr>
				<td>
					<font>值班手机:</font>
				</td>
				<td>
					<input type="text" id="mobilePhone" value="<%=result[5]%>" disabled="true"  class="text"> &nbsp;&nbsp;(选填)
				</td>
			</tr>
			<!-- Modification finished -->
			
			<%
			if(session.getAttribute("appid").equals("1001")){
			%>	
			<tr align="center">
				<td>
					<input type="button" value="修改" id="updateBtn"
						onclick="checkBtn('leader');" class="button">
				</td>

				<td>
					<span><input type="button" value="确认" id="closeBtn"
							class="button" onclick="closeDiv();">
					</span>
				</td>
			</tr>
			<% 
			}else{	
			%>
			<tr align="center">
				<td>
					<span><input type="button" value="关闭" id="closesBtn"
							class="button" onclick="closeDiv();">
					</span>
				</td>
			</tr>
			<% 
			}
			%>
			
			<%
			}
			%>

		</table>
</html>
