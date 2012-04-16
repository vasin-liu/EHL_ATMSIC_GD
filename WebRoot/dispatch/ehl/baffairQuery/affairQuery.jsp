<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
<!-- 
	 * 
	 * 版 权：北京易华录信息技术股份有限公司 2009
	 * 文件名称：affairQuery.jsp
	 * 摘 要：警情查询
	 * 当前版本：1.0
	 * 作 者：LChQ  2009-4-14
	 * 修改人：
	 * 修改日期： -->
 
 <%@page import="com.ehl.dispatch.common.*" %>
<%
 	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	
	
	String flag=request.getParameter("flag");

	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String deptname = ""; 	  //部门名称
	String pname = ""; 		  //姓名
	String uname = ""; 		  //帐号
	String pid = ""; 		  //办公电话
	String phone = ""; 		  //办公电话
	String mobilephone = "";  //手机
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		pname = (String)prop.get("NAME");
		uname = (String)prop.get("USERNAME");
		pid=(String)prop.get("PERSONID");
		phone = (String)prop.get("PHONE");
		mobilephone = (String)prop.get("MOBILEPHONE");		
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>警情信息查询</title>
		<script type="text/javascript" src="../../js/bcommon/calendar/CalendarDateTime.js">	</script>
		<script type="text/javascript" src="../../js/bcommon/utility/DhtmlPaging.js"	></script>
		<script type="text/javascript" src="../../js/bcommon/utility/comLogic.js"	></script>
		<script src="../../js/bcommon/utility/DhtmlBranchTree.js"	type="text/javascript"></script>
		<script src="../../js/bcommon/list/FillListBox.js" type="text/javascript"></script>
		<!--  -->
		<jsp:include flush="true" page="../../../sm/ShareInc.html"></jsp:include>
		<style type="text/css">
			.queryTD1 {
				line-height: 16px;
				font-size: 9pt;
				empty-cells: show;
				text-align: right;
				width: 15%
			}
			
			.queryTD2 {
				line-height: 16px;
				font-size: 9pt;
				empty-cells: show;
				text-align: left;
				
			}
		</style>
		<script type="text/javascript">
		
	 	var deptcode = '<%=deptcode%>';
		var userCountyText = '<%= deptname %>';
		var pname = '<%=pname%>';
		var pid = '<%=pid%>';
		var userNameText = '<%= uname %>';
		var mobilephone='<%= mobilephone%>';		
		
	</script>
	</head>
	<body style="margin: 0;">
		<table border="0" cellspacing="0" cellpadding="0"
			style="text-align: center; width: 95%; height: 100%;valign:top"  align="center">
			<tr>
				<td align="center" width="95%">
					<fieldset style="border: 1px solid #ccc; valign: top; align: center">
									<legend style="border: 0px; font-weight: 700; font-size: 8pt;">
										查询条件
									</legend>
					<table border="0" cellspacing="2" cellpadding="0"
						style="width: 95%;">
						<tr>
							<td class="queryTD1" nowrap>
								事件类型：

							</td>
							<td class="queryTD2" id="cellAffairType">
								<script type="text/javascript">
									 fillListBox("cellAffairType","txtAffairType","230","SELECT t.id,t.name FROM t_attemper_code t WHERE t.id like '001%'","","");
								</script>
							</td>
							<td class="queryTD1" nowrap>
								事件来源：
							</td>
							<td class="queryTD2" id="cellAlarmSource">
								<script type="text/javascript">
									 fillListBox("cellAlarmSource","txtAlarmSource","240","SELECT t.id,t.name FROM t_attemper_code t WHERE t.id like '002%'","","");
								</script>
							</td>
							
						</tr>
						<tr>
							<td class="queryTD1" nowrap align="left">
								事件状态：
							</td>
							<td class="queryTD2" id="cellAlarmState">
								<<script type="text/javascript">
									 fillListBox("cellAlarmState","txtAlarmState","230","SELECT t.id,t.name FROM t_attemper_code t WHERE t.id in ('004012','004021','004006','004017','004018','004019','004020')","","");
								</script>
							</td>
							<td class="queryTD1">
								报警时间：
							</td>
							<td class="queryTD2" colspan=3>								
								<input type="text" id="txtBgAlarmTake" readonly style="width:110"
									onclick="SelectDateTime(this)" />
								至
								<input type="text" id="txtTmnAlarmTake" readonly style="width:110"
									onclick="SelectDateTime(this)" />
							</td>
						</tr>
						
					</table>
					</fieldset>
				</td>
			</tr>
			<tr style="lint-height:15px">
				<td colspan=6 align=right>
					<input type="button" value=" 查 询 " id="btnConfirm" />
					&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; 
				</td>
			</tr>
			<tr style="lint-height:5px"><td><hr style="height:1px"></td></tr>
			<tr>
				<td align=center>
				 
					<div id='t_affair_query' style="width:100%; height:336px; background-color: white;"></div>
					<div id="divPagingButton"  style="offsetTop:0px;line-height:19" class='PageText' ></div>
				</td>
			</tr>
		</table>
	</body>
	<script src="../../js/baffairQuery/affairQuery.js"	type="text/javascript"></script>
	
</html>
