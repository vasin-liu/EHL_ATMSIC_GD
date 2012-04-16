<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%
	String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),""); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
	 	<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
	 	<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css"> 
	 	<script src="../../js/common/Prototype.js" type="text/javascript"></script>
	 	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
		<script type="text/javascript" src="../../js/policeWatch/dispatchWatching.js"></script>
		<script type="text/javascript" src="../../js/common/utility/DhtmUtilTable.js"></script>
		<script type="text/javascript" src="../../js/editPolice/editPolice.js"></script>
		<script type="text/javascript" src="../../js/editPolice/editTrffice.js"></script>
		<script type="text/javascript" src="../../js/editPolice/PoliceMap.js"></script>
		<script type="text/javascript" src="../../js/common/utility/CalendarDateTime.js"></script>
		<script type="text/javascript">
		   var alarmId = '<%=alarmId %>';
		   
		</script>
		<title>交通管制信息</title>
	</head>
<body onload="getTrafficInfo(alarmId);">
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #CCCCCC" align="center">
			<legend style="border:0px;font-size: 12pt;">交通管制填报表</legend>
			<table class="table2" width="100%">
			     <tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="5">
						<input type="hidden" id="insertOrUpdate_TrafficRestrain" class="textwidth" name="editinput">
						<legend style="border:0px;font-size:14px">接报信息</legend>
					</td>
					
					<td><input type="button" value="接收上报" id="ReceiveEvent_TrafficRestrain" onclick="receiveReport('_TrafficRestrain',$('flowId_TrafficRestrain').value,$('alarmId_TrafficRestrain').value);"></td>
				</tr>
				<tr>
				     <input type="hidden" id="flowId_TrafficRestrain" class="textwidth" name="editinput">
					<input type="hidden" id="alarmId_TrafficRestrain" class="textwidth" name="editinput" >				
					<td class="tdtitle">填报时间：</td>
					<td class="tdvalue">
						<input type="text" id="alarmTime_TrafficRestrain" readonly onClick="SelectDateTime(this)" class="textwidth" name="editinput">					</td>
					<td class="tdtitle">上报单位：</td>
					<td class="tdvalue">
						<input type="text" id="alarmDept_TrafficRestrain" class="textwidth" name="editinput">					
					</td>
					<td class="tdtitle">填报人：</td>
					<td class="tdvalue">
						<input type="hidden" id="reportUnit_TrafficRestrain" class="textwidth" name="editinput" >
						<input type="text" id="reportPerson_TrafficRestrain" class="textwidth" name="editinput" >					</td>
				</tr>
				<tr>
				
					<td class="tdtitle" id="">填报单位：</td>
					<td class="tdvalue">
						<input type="text" id="reportDept_TrafficRestrain" class="textwidth" name="editinput">					</td>
					<td class="tdtitle">联系电话：</td>
					<td class="tdvalue">
						<input type="text" id="telpone_TrafficRestrain" class="textwidth" name="editinput" readonly">					</td>
				</tr>
				
				
				 <tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="6">
						<input type="hidden" id="insertOrUpdate_TrafficCrowd" class="textwidth" name="editinput">
						<legend style="border:0px;font-size:14px">交通管制信息</legend>
					</td>
				</tr>
				<tr>
				   <td class="tdtitle">管制原因：</td>
					<td class="tdvalue" id="reson_TrafficRestrain_td">
						<script>
							fillListBox("reson_TrafficRestrain_td","reson_TrafficRestrain","111","select id,name from t_attemper_code where id like '3001%'","请选择");
						</script>					
					</td>
					
					<td class="tdtitle">事发路段：</td>
					<td class="tdvalue" id="accsection_TrafficRestrain_td">
						<script>
							fillListBox("accsection_TrafficRestrain_td","accsection_TrafficRestrain","111","select bh,dlmc from t_oa_dict_road t","请选择");
						</script>
					</td>
					
					<td class="tdtitle">公里：</td>
					<td class="tdvalue" id="" length="111">
						<select id="mvalue_TrafficRestrain" style="width:111px;">
							<option value="0" selected>0米</option>
							<option value="100">100米</option>
							<option value="200">200米</option>
							<option value="300">300米</option>
							<option value="400">400米</option>
							<option value="500">500米</option>
							<option value="600">600米</option>
							<option value="700">700米</option>
							<option value="800">800米</option>
							<option value="900">900米</option>
						</select>
					</td>
					
				</tr>
				<tr>
				   <td class="tdtitle">管制地点：</td>
					<td class="tdvalue">
						<input type="text" id="address_TrafficRestrain" class="textwidth" name="editinput">					
					</td>
					<td class="tdtitle">管制方向：</td>
					<td class="tdvalue" id="direction_TrafficRestrain_td">
						<script>
							fillListBox("direction_TrafficRestrain_td","direction_TrafficRestrain","111","select id,name from t_attemper_code where id like '3003%'","请选择");
						</script>							
					</td>
					<td class="tdtitle">管制类型：</td>
					<td class="tdvalue" id="type_TrafficRestrain_td">
						<script>
							fillListBox("type_TrafficRestrain_td","type_TrafficRestrain","111","select id,name from t_attemper_code where id like '3002%'","请选择");
						</script>				
					</td>
				
				</tr>
				<tr>
					<td class="tdtitle">管制开始时间：</td>
					<td class="tdvalue">
						<input type="text" id="starttime_TrafficRestrain" readonly onClick="SelectDateTime(this)" class="textwidth" name="editinput">					
					</td>
					<td class="tdtitle">管制结束时间：</td>
					<td class="tdvalue">
						<input type="text" id="endtime_TrafficRestrain" readonly onClick="SelectDateTime(this)" class="textwidth" name="editinput">					
					</td>
					<td class="tdtitle"></td>
					<td class="tdvalue" name="editinput">
					</td>
				</tr>
				<tr>
					<td class="tdtitle">备注：</td>
					<td class="tdvalue" colspan="5">
						<textarea  rows="5" style="width:100%" id="desc_TrafficRestrain" name="editinput"></textarea>					
					</td>
				</tr>
				<tr style="display : none">
				<td class="tdtitle">警情状态：</td>
					<td class="tdvalue">
						<input type="hidden" id="alarmState_TrafficRestrain" class="textwidth">
						<input type="hidden" id="eventType_TrafficRestrain" class="textwidth">
						<input type="hidden" id="eventSource_TrafficRestrain" class="textwidth">
						<input type="hidden" id="superUnit_TrafficRestrain" class="textwidth">
					</td> 
				</tr>	
			</table>
			<div style="text-align: right">
				<input type="button" value="定位案发地点" id="signSite_TrafficRestrain" style="display: none" onClick="showMap('1');">
				<input type="button" value="上报事件" id="saveData_TrafficRestrain" style="" onClick="editTrfficeContrl();">
			</div>
		</fieldset>
	</div>
</body>
</html>
