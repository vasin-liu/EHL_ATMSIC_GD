<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.*"%>
<%
	String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),""); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>警情编辑信息</title>
	</head>
<body onload="getAlarmInfoById('<%=alarmId %>')">
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #CCCCCC" align="center">
			<legend style="border:0px;">警情信息</legend>
			<table class="table2" width="100%">
				<tr>
					<td class="tdtitle">报警单号：</td>
					<td class="tdvalue">
						<input type="text" id="newalarmId" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle">报警时间：</td>
					<td class="tdvalue">
						<input type="text" id="newalarmTime" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">案发地点：</td>
					<td class="tdvalue">
						<input type="text" id="newalarmSite" class="textwidth" name="editinput" >
					</td>
				</tr>
				<tr>
					<td class="tdtitle">报警机构：</td>
					<td class="tdvalue">
						<input type="text" id="newalarmDept" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle">事件类型：</td>
					<td class="tdvalue" id="newalarmtypeTd" name="editinput">
						<script>
							fillListBox("newalarmtypeTd","newalarmType","111","select id,name from t_attemper_code where id like '001%'","请选择");
						</script>
					</td>
					<td class="tdtitle">分中心事件来源：</td>
					  <td class="tdvalue" id="newsubEventSourceTd">
						<script>
							fillListBox("newsubEventSourceTd","newsubEventSource","111","select id,name from t_attemper_code where id like '021%'","请选择");
						</script>
					</td> 
				</tr>
				<tr>
					<td class="tdtitle">案件辖区：</td>
					<td class="tdvalue">
						<input type="text" id="newRegion" class="textwidth" name="editinput" readonly>
					</td>					
				<!-- 
					<td class="tdtitle">事件程度：</td>
					<td class="tdvalue" id="alarmLevelTd">
						<script>
							fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
						</script>
					</td>
				 -->	
					<td class="tdtitle">事件状态：</td>
					<td class="tdvalue" id="newalarmStateTd">
						<script>
							fillListBox("newalarmStateTd","newalarmState","111","select id,name from t_attemper_code where id like '004%'","请选择");
						</script>
					</td>
					<td class="tdtitle" id="">事件细类：</td>
					<td class="tdvalue" id="newalarmThinTypeTd">
						<input type="text" id="newalarmThinType" class="textwidth" name="editinput" disabled="disabled">
					</td>
				</tr>		
				<tr>
					<td class="tdtitle">报警人：</td>
					<td class="tdvalue">
						<input type="text" id="newalarmperson" class="textwidth" name="editinput"  >
					</td>
					<td class="tdtitle">报警电话：</td>
					<td class="tdvalue">
						<input type="text" id="newalarmphone" class="textwidth" name="editinput" >
					</td>
					<td class="tdtitle">报警人地址：</td>
					<td class="tdvalue">
						<input type="text" id="newalarmaddres" class="textwidth" name="editinput" >
					</td>
				</tr>			
				<tr>
					<td class="tdtitle">上报时间：</td>
					<td class="tdvalue">
						<input type="text" id="newreportTime" class="textwidth" name="editinput" readonly >
					</td>
					<td class="tdtitle">上报人：</td>
					<td class="tdvalue">
						<input type="text" id="newreportPerson" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle">上报单位：</td>
					<td class="tdvalue">
						<input type="text" id="newreportUnit" class="textwidth" name="editinput" readonly>
					</td>
				</tr>					
				
				<tr>
					<td class="tdtitle">报警内容：</td>
					<td class="tdvalue" colspan="5">
						<textarea  rows="5" style="width:100%" id="newalarmDesc" name="neweditinput"></textarea>
					</td>
				</tr>
				
				
				<tr>
					<td class="td2" colspan="6"></td>
				</tr>
			</table>
			<div style="text-align: right">
				
				<input type="button" value="查看子单信息" id="showsonList" style="display: none" onclick="">
				<input type="button" value="上报警情" id="saveData" style="" onclick="saveNewPolice();">
			</div>
		</fieldset>
	</div>
</body>
</html>
