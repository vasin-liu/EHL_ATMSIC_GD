<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%
	String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),""); 
	String depttype = StringHelper.obj2str(request.getParameter("depttype"),""); 
	String deptcodeStr = StringHelper.obj2str(request.getParameter("deptcode"),""); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>道路交通拥堵接报信息表</title>
		<%
			if(!alarmId.equals("")){
		%>
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
	 	<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css"> 
	 	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/editPolice/editTrfficeCrowd.js"></script>
		<script type="text/javascript" src="../../js/editPolice/PoliceTool.js"></script>
		<script type="text/javascript" src="../../js/editPolice/ModifyAccident.js"></script>
		<script type="text/javascript" src="../../js/editPolice/PoliceMap.js"></script>
		<script type="text/javascript">
			var depttype = '<%=depttype%>';
			var deptcode = '<%=deptcodeStr%>';
			window.onload = function (){getAccInfo('<%=alarmId%>');};
		</script>
		<%
			}
		%>
	</head>
<body>
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #CCCCCC" align="center">
			<legend style="border:0px;font-size: 12pt;">道路交通拥堵接报信息表</legend>
			<table class="table2" width="100%">
				<tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="6">
						<input type="hidden" id="insertOrUpdate_TrafficCrowd" class="textwidth" name="editinput">
						<legend style="border:0px;font-size:14px">接报信息</legend>
					</td>
				</tr>
				<tr>
				    <input type="hidden" id="alarmid_TrafficCrowd" class="textwidth" readonly name="editinput" >
					<td class="tdtitle">填报人：</td>
					<td class="tdvalue">
						<input type="text" onKeyDown="if(event.keyCode==8){return false;};" id="alarmId_Accident" class="textwidth" readonly name="editinput" >
						<input type="hidden" id="reportPerson_TrafficCrowd" class="textwidth" readonly name="editinput" >
					</td>
					<td class="tdtitle">填报时间：</td>
					<td class="tdvalue">
						<input type="text" id="feedBackTime_TrafficCrowd" onKeyDown="if(event.keyCode==8){return false;};" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">填报单位：</td>
					<td class="tdvalue">
						<input type="text" id="feedBackunit_TrafficCrowd" class="textwidth" name="editinput" >
					</td>
				</tr>
				
				<tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="6">
						<legend style="border:0px;font-size:14px">
							拥堵简要信息
						</legend>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">路段备注：</td>
					<td class="tdvalue" id="alarmRoad_TrafficCrowd_td">
						<script>
							fillListBox("alarmRoad_TrafficCrowd_td","alarmRoad_TrafficCrowd","111","select bh,dlmc from t_oa_dict_road t","请选择");
						</script>
					</td>
					<td class="tdtitle">方向：</td>
					<td class="tdvalue">
						<input type="text" id="direction_TrafficCrowd" class="textwidth" name="editinput"></td>
					<td class="tdtitle">起始里程：</td>
					<td class="tdvalue" name="editinput">
						<select id="startaddress_TrafficCrowd" style="width:111px;">
							<option value="0" selected>0公里</option>
							<option value="1">1公里</option>
							<option value="2">2公里</option>
							<option value="3">3公里</option>
							<option value="4">4公里</option>
							<option value="5">5公里</option>
							<option value="6">6公里</option>
							<option value="7">7公里</option>
							<option value="8">8公里</option>
							<option value="9">9公里</option>
							<option value="10">10公里</option>
						</select>
						
				   </td>
				</tr>
				<tr>
					<td class="tdtitle">结束里程：</td>
					<td class="tdvalue">
						<select id="endaddress_TrafficCrowd" style="width:111px;">
							<option value="0" selected>0公里</option>
							<option value="1">1公里</option>
							<option value="2">2公里</option>
							<option value="3">3公里</option>
							<option value="4">4公里</option>
							<option value="5">5公里</option>
							<option value="6">6公里</option>
							<option value="7">7公里</option>
							<option value="8">8公里</option>
							<option value="9">9公里</option>
							<option value="10">10公里</option>
						</select>					
					</td>
					<td class="tdtitle">交通状况：</td>
					<td class="tdvalue">
						<input type="text" id="trafficstate_TrafficCrowd" class="textwidth" name="editinput">					</td>
					<td class="tdtitle">拥堵原因：</td>
					<td class="tdvalue">
						<input type="text" id="trafficreason_TrafficCrowd" class="textwidth" name="editinput" readonly">					</td>
				</tr>
				<tr>
					<td class="tdtitle">起始时间：</td>
					<td class="tdvalue">
							<input type="text" id="starttime_TrafficCrowd" onKeyDown="if(event.keyCode==8){return false;};" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">终止时间：</td>
					<td class="tdvalue">
						<input type="text" id="endtime_TrafficCrowd" onKeyDown="if(event.keyCode==8){return false;};" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
				</tr>
				
				<tr>
					<td class="tdtitle">交通状况简要情况：</td>
					<td class="tdvalue" colspan="5">
						<textarea  rows="5" style="width:100%" id="desc_TrafficCrowd" name="editinput"></textarea>
					</td>
				</tr>
				
				<tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="6">
						<legend style="border:0px;font-size:14px">
							道路封闭信息
						</legend>
					</td>
				</tr>
				
				<tr>
					<td class="tdtitle">起始时间：</td>
					<td class="tdvalue">
							<input type="text" id="closestarttime_TrafficCrowd" onKeyDown="if(event.keyCode==8){return false;};" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">终止时间：</td>
					<td class="tdvalue">
						<input type="text" id="closeendtime_TrafficCrowd" onKeyDown="if(event.keyCode==8){return false;};" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
				</tr>
			</table>
			<div style="text-align: right">
				<input type="button" value="定位案发地点" style="display:none" id="signSite_Accident" onclick="showMap('Accident',0);">
				<input type="button" value="上报警情" id="saveData_Accident" onclick="modifyAccidnet('_Accident');">
			</div>
		</fieldset>
	</div>
</body>
</html>
