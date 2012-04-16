<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.*"%>
<%
	String alarmId=StringHelper.obj2str(request.getParameter("ALARMID"),"");
	String alarmType=StringHelper.obj2str(request.getParameter("ALARMTYPE"),"");	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
<script src="../../js/common/prototype.js" type="text/javascript"></script>
<script src="../../js/affairQuery/alarmInfoQuery.js" type="text/javascript"></script>
		<style type="text/css">
.queryTD1 {
	line-height: 16px;
	font-size: 9pt;
	empty-cells: show;
	text-align: right;
	border-right: 1 #caa solid;
	border-bottom: 1 #caa solid;
}

.queryTD2 {
	line-height: 16px;
	font-size: 9pt;
	empty-cells: show;
	text-align: left;
	border-right: 1 #caa solid;
	border-bottom: 1 #caa solid;
}

.detailTR1 {
	line-height: 16px;
	font-size: 9pt;
	empty-cells: show;
}
</style>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>警情详细信息</title>
	</head>
	<body onload="getAlarmInfo('<%=alarmId %>','<%=alarmType %>');">
		<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0
			align="center"
			style="border-left: 1 #caa solid; border-top: 1 #caa solid;">
			<tr style="background-color: blue; color: white; line-height: 16px; font-size: 9pt;">
				<td align=center style="color: white" colspan="6">
					警情详细信息
				</td>
			</tr>
			<tr class="detailTR1">
				<td class="queryTD1">
				接警单号：
				</td>
				<td class="queryTD2" id="alarmid">				
				</td>
				<td class="queryTD1">
				事件标题：
				</td>
				<td class="queryTD2" id="title" colspan="3">
				</td>				
			</tr>
			<tr class="detailTR1">				
				<td class="queryTD1" style="width:12%">
				报警辖区：
				</td>
				<td class="queryTD2" id="alarmregion" style="width:21%">
				</td>
				<td class="queryTD1" style="width:12%">
				报警时间：
				</td>
				<td class="queryTD2" id="alarmtime" style="width:21%">				
				</td>
				<td class="queryTD1" style="width:12%">
				报警地点：
				</td>
				<td class="queryTD2" id="alarmaddress" style="width:21%">
				</td>
			</tr>
			<tr class="detailTR1">	
				<td class="queryTD1">
				报警人：
				</td>
				<td class="queryTD2" id="alarmperson">
				</td>		
				<td class="queryTD1">
				报警电话：
				</td>
				<td class="queryTD2" id="alarmphone">
				</td>			
				<td class="queryTD1">
				接警员：
				</td>
				<td class="queryTD2" id="disposeperson">
				</td>				
			</tr>
			<tr class="detailTR1">
				<td class="queryTD1">
				事件来源：
				</td>
				<td class="queryTD2" id="eventsource">
				</td>
				<td class="queryTD1">
				事件类型：
				</td>
				<td class="queryTD2" id="eventtype">
				
				</td>				
				<td class="queryTD1">
				事件子类型：
				</td>
				<td class="queryTD2" id="eventthintype">
				</td>									
			</tr>
			<tr class="detailTR1">	
				<td class="queryTD1">
				事件程度：
				</td>
				<td class="queryTD2" id="eventlevel">
				</td>
				<td class="queryTD1">
				道路类型：
				</td>
				<td class="queryTD2" id="roadtype">				
				</td>
				<td class="queryTD1">
				天气状况：
				</td>
				<td class="queryTD2" id="weather">
				</td>				
			</tr>
			<tr class="detailTR1">	
				<td class="queryTD1">
				受伤人数：
				</td>
				<td class="queryTD2" id="FLESHWOUNDPERSONCOUNT">
				</td>	
				<td class="queryTD1">
				死亡人数：
				</td>
				<td class="queryTD2" id="DEATHPERSONCOUNT">				
				</td>
				<td class="queryTD1">
				处理结果：
				</td>
				<td class="queryTD2" id="disposeresult">
				</td>
			</tr>
			<tr class="detailTR1"> 	
				<td class="queryTD1">
				报警内容：
				</td>
				<td class="queryTD2" id="alarmdesc" colspan="5">
				</td>	
			</tr>
			<tr class="detailTR1">	
				<td class="queryTD1">
				指挥员：
				</td>
				<td class="queryTD2" id="attemper">
				</td>	
				<td class="queryTD1">
				出警人员：
				</td>
				<td class="queryTD2" id="comeoutperson">				
				</td>
				<td class="queryTD1">
				出警车辆：
				</td>
				<td class="queryTD2" id="comeoutcar">
				</td>
			</tr>
			<tr class="detailTR1">	
				<td class="queryTD1">
				派警时间：
				</td>
				<td class="queryTD2" id="assigntime">
				</td>	
				<td class="queryTD1">
				出警时间：
				</td>
				<td class="queryTD2" id="comeouttime">				
				</td>
				<td class="queryTD1">
				到达时间：
				</td>
				<td class="queryTD2" id="comeoutarrivetime">
				</td>
			</tr>
			<tr class="detailTR1">	
				<td class="queryTD1">
				反馈时间：
				</td>
				<td class="queryTD2" id="feedbacktime">
				</td>	
				<td class="queryTD1">
				撤离时间：
				</td>
				<td class="queryTD2" id="finishtime">				
				</td>
				<td class="queryTD1">
				恢复时间：
				</td>
				<td class="queryTD2" id="trafficreverttime">
				</td>
			</tr>
			<tr class="detailTR1">	
				<td class="queryTD1">
				反馈记录：
				</td>
				<td class="queryTD2" id="feedbackdesc" colspan="5">
				</td>	
			</tr>
		</table>

	</body>
</html>
