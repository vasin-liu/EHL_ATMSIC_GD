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
		<title>交通拥堵情况信息</title>
	</head>
<body>
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #CCCCCC" align="center">
			<legend style="border:0px;font-size: 12pt;">交通拥堵填报表</legend>
			<table class="table2" width="100%">
			  
				<tr>
					<td class="tdtitle">填报单号：</td>
					<td class="tdvalue">
						<input type="text" id="alarmId_RoadBuild" class="textwidth" name="editinput" >					</td>
					<td class="tdtitle">填报时间：</td>
					<td class="tdvalue">
						<input type="text" id="alarmTime_RoadBuild" readonly onClick="SelectDateTime(this)" class="textwidth" name="editinput">					</td>
					<td class="tdtitle">填报人：</td>
					<td class="tdvalue">
						<input type="hidden" id="reportUnit_RoadBuild" class="textwidth" name="editinput" >
						<input type="text" id="reportPerson_RoadBuild" class="textwidth" name="editinput" >					</td>
				</tr>
				<tr>
					<td class="tdtitle">路段备注：</td>
					<td class="tdvalue" id="alarmRoad_RoadBuild_td">
						<script>
							fillListBox("alarmRoad_RoadBuild_td","alarmRoad_RoadBuild","111","select bh,dlmc from t_oa_dict_road t","请选择");
						</script>
					</td>
					<td class="tdtitle">方向：</td>
					<td class="tdvalue">
						<input type="text" id="fzr_RoadBuild" class="textwidth" name="editinput">					</td>
					<td class="tdtitle">起始里程：</td>
					<td class="tdvalue" name="editinput">
						<input type="text" id="phone_RoadBuild" class="textwidth" name="editinput">					</td>
				</tr>
				<tr>
					<td class="tdtitle">结束里程：</td>
					<td class="tdvalue" id="alarmRoad_RoadBuild_td">
						<input type="text" id="unit" class="textwidth" name="editinput">					
					</td>
					<td class="tdtitle" id="">交通状况：</td>
					<td class="tdvalue">
						<input type="text" id="alarmFiled_RoadBuild" class="textwidth" name="editinput">					</td>
					<td class="tdtitle">拥堵原因：</td>
					<td class="tdvalue">
						<input type="text" id="rotype_RoadBuild" class="textwidth" name="editinput" readonly">					</td>
				</tr>
				<tr>
					<td class="tdtitle">起始时间：</td>
					<td class="tdvalue" id ="alarmRoad_state_td1">
						<script>
							fillListBox("alarmRoad_state_td1","alarmRoad_state1","111","select id,name from t_attemper_code where id like '004%'","请选择");
						</script>
					</td>
					<td class="tdtitle">终止时间：</td>
					<td class="tdvalue" id ="alarmRoad_state_td1">
						<script>
							fillListBox("alarmRoad_state_td1","alarmRoad_state1","111","select id,name from t_attemper_code where id like '004%'","请选择");
						</script>
					</td>
				</tr>
				
			</table>
			<div style="text-align: right">
				<input type="button" value="定位案发地点" id="signSite_RoadBuild" style="display: none" onClick="showMap('1');">
				<input type="button" value="上报警情" id="saveData_TrfficeJam" style="" onClick="editTrfficeJamInfo();">
			</div>
		</fieldset>
	</div>
</body>
</html>
