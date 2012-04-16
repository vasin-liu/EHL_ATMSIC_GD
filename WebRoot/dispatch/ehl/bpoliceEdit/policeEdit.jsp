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
			<legend style="border:0px;">警情详细信息</legend>
			<table class="table2" width="100%">
				<tr>
					<td class="tdtitle">报警单号：</td>
					<td class="tdvalue">
						<input type="text" id="alarmId" class="textwidth" name="editinput" >
					</td>
					<td class="tdtitle">报警时间：</td>
					<td class="tdvalue">
						<input type="text" id="alarmTime" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">报警地点：</td>
					<td class="tdvalue">
						<input type="text" id="alarmSite" class="textwidth" name="editinput" >
					</td>
				</tr>
				<tr>
					<td class="tdtitle">报警机构：</td>
					<td class="tdvalue">
						<input type="text" id="alarmDept" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle">事件类型：</td>
					<td class="tdvalue" id="alarmtypeTd" name="editinput">
						<script>
							fillListBox("alarmtypeTd","alarmType","111","select id,name from t_attemper_code where id like '001%'","请选择");
						</script>
					</td>
					<td class="tdtitle">分中心事件来源：</td>
					  <td class="tdvalue" id="subEventSourceTd">
						<script>
							fillListBox("subEventSourceTd","subEventSource","111","select id,name from t_attemper_code where id like '021%'","请选择");
						</script>
					</td> 
				</tr>
				<tr>
					<td class="tdtitle">路段备注：</td>
					<td class="tdvalue">
						<input type="text" id="roadName" class="textwidth" name="editinput">
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
					<td class="tdvalue" id="alarmStateTd">
						<script>
							fillListBox("alarmStateTd","alarmState","111","select id,name from t_attemper_code where id like '004%'","请选择");
						</script>
					</td>
					<td class="tdtitle" id="">事件细类：</td>
					<td class="tdvalue" id="alarmThinTypeTd">
						<input type="text" id="alarmThinType" class="textwidth" name="editinput">
					</td>
				</tr>		
				<tr>
					<td class="tdtitle">处警时间：</td>
					<td class="tdvalue">
						<input type="text" id="disposeTime" class="textwidth" name="editinput" readonly onclick="SelectDateTime(this)">
					</td>
					<td class="tdtitle">处警人：</td>
					<td class="tdvalue">
						<input type="text" id="disposePerson" class="textwidth" style="width:86%" name="editinput"><img src="../../../sm/image/popup/btnselect.gif" id="" onclick="chkPerson('okDisposePersonClick();',30,200)" alt="选择人员" style="cursor: hand"/>
					</td>
					<td class="tdtitle">处警单位：</td>
					<td class="tdvalue">
						<input type="text" id="disposeUnit" class="textwidth" name="editinput">
					</td>
				</tr>	
				<tr>
					<td class="tdtitle">上报时间：</td>
					<td class="tdvalue">
						<input type="text" id="reportTime" class="textwidth" name="editinput" readonly >
					</td>
					<td class="tdtitle">上报人：</td>
					<td class="tdvalue">
						<input type="text" id="reportPerson" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle">上报单位：</td>
					<td class="tdvalue">
						<input type="text" id="reportUnit" class="textwidth" name="editinput" readonly>
					</td>
				</tr>					
				<tr>
					<!-- 
					<td class="tdtitle">路段名称：</td>
					<td class="tdvalue">
						<input type="text" id="roadName" class="textwidth" name="editinput">
					</td>
					 -->
					<td class="tdtitle">经度：</td>
					<td class="tdvalue">
						<input type="text" id="X" class="textwidth" name="editinput" readonly >
					</td>
					<td class="tdtitle">纬度：</td>
					<td class="tdvalue">
						<input type="text" id="Y" class="textwidth" name="editinput" readonly> 
					</td>
					 <td class="tdtitle">自行处理：</td>
					<td class="tdvalue" >
						<input type="checkbox" id="disposeSelfChk" checked value="">
					</td> 	
				</tr>
				<tr style="display : none">
				  	<td class="tdtitle">事件程度：</td>
					<td class="tdvalue" id="alarmLevelTd">
						<script>
							fillListBox("alarmLevelTd","alarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
						</script>
					</td>
				</tr>
				
				<tr style="display : none">
				    <td class="tdtitle">事件来源：</td>
					<td class="tdvalue" id="alarmSouceTd">
						<script>
							fillListBox("alarmSouceTd","alarmSouce","111","select id,name from t_attemper_code where id like '002%'","请选择");
						</script>
					</td> 
				</tr>
				<tr>
					<td class="tdtitle">报警内容：</td>
					<td class="tdvalue" colspan="5">
						<textarea  rows="5" style="width:100%" id="alarmDesc" name="editinput"></textarea>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">处警意见：</td>
					<td class="tdvalue" colspan="5">
						<textarea rows="3" style="width:100%" id="disposeIdea" name="editinput"></textarea>
					</td>
				</tr>
				<tr id="markRoad" style="display: none">
					<td class="tdtitle">道路名称：</td>
					<td class="tdvalue" id ="dlmc_td">
						<script>
							
						</script>
					</td>
					<td class="tdtitle">千米值：</td>
					<td class="tdvalue">
						<input type="text" id="kmvalue" title="请输入大于0且小于1000的整数" onblur="isValidate(this);" class="textwidth" name="editinput" value=0>
					</td>
					<td class="tdtitle">百米值：</td>
					<td class="tdvalue">
						<select id="mvalue" style="width:111px;">
							<option value="0" selected>0</option>
							<option value="100">100</option>
							<option value="200">200</option>
							<option value="300">300</option>
							<option value="400">400</option>
							<option value="500">500</option>
							<option value="600">600</option>
							<option value="700">700</option>
							<option value="800">800</option>
							<option value="900">900</option>
						</select>
					</td>
				</tr>
				<tr style="display: none">
					<td class="tdtitle">路段编号：</td>
					<td class="tdvalue">
						<input type="text" id="roadId" class="textwidth" name="editinput">
					</td>
				</tr>
				<tbody id="accidentTbody" style="display: none">
					<jsp:include flush="true" page="accidentEdit.jsp"></jsp:include>
				</tbody>
				<tbody id="congestionTbody" style="display: none">
					<jsp:include flush="true" page="congestionEdit.jsp"></jsp:include>
				</tbody>
				<tbody id="exceptionCarTbody" style="display: none">
					<jsp:include flush="true" page="exceptionCarEdit.jsp"></jsp:include>
				</tbody>
				<tbody id="policeEventTbody" style="display: none">
					<jsp:include flush="true" page="policeEventEdit.jsp"></jsp:include>
				</tbody>
				<tbody id="blackListTbody" style="display: none">
					<jsp:include flush="true" page="blackListEdit.jsp"></jsp:include>
				</tbody>
				<tbody id="badWeatherTbody" style="display: none">
					<jsp:include flush="true" page="badWeatherEdit.jsp"></jsp:include>
				</tbody>
				<tbody id="townPlanTbody" style="display: none">
					<jsp:include flush="true" page="townPlanEdit.jsp"></jsp:include>
				</tbody>
				<tbody id="fireAndBlastTbody" style="display: none">
					<jsp:include flush="true" page="fireAndBlastEdit.jsp"></jsp:include>
				</tbody>
				<tbody id="geoLogicDisasterTbody" style="display: none">
					<jsp:include flush="true" page="GeoLogicDisasterEdit.jsp"></jsp:include>
				</tbody>
				<tr>
					<td class="td2" colspan="6"></td>
				</tr>
			</table>
			<div style="text-align: right">
				<input type="button" value="快速定位" id="markPoint" style="" onclick="markOnRoad();">
				<input type="button" value="查看子单信息" id="showsonList" style="display: none" onclick="">
				<input type="button" value="定位案发地点" id="signSite" style="display: none" onclick="showMap('1');">
				<input type="button" value="上报警情" id="saveData" style="display: none" onclick="editAlarmInfo();">
			</div>
		</fieldset>
	</div>
</body>
</html>
