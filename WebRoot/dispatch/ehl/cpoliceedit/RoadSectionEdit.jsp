<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? "" : request.getParameter("insrtOrUpdt");
	String sectionid = request.getParameter("sectionid")== null ? "" : request.getParameter("sectionid");
	System.out.println(insrtOrUpdt+":"+sectionid);
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
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css"> 
	 	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
	 	<script type="text/javascript" src="../../../base/js/dhtmlx/xmlCreator.js"></script>
	 	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/ccommon/CommonClear.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/DhtmUtilTable.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/RoadSection.js"></script>
		<script type="text/javascript">
		   var sectionid = '<%=sectionid %>';
		</script>
		<title>路段信息</title>
		
	</head>

<body onload="getRoadSectionInfo(sectionid);">
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #CCCCCC" align="center">
			<legend style="width:15%;border:0px;font-size: 12pt;">路段信息</legend>
			<table class="table2" width="100%">
				<tr style="display:none">
					<td>路段编号</td>
					<td colspan="3">
						<input id="SECTIONID" type="text" name="SECTIONID" class="text" value="<%=sectionid%>" readonly></input>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">路段备注：</td>
					<td class="tdvalue"><input type="text" id="SECTIONNAME" class="textwidth" name="SECTIONNAME"></td>
					<td class="tdtitle" style="width:12%;">道路名称：</td>
					<td class="tdvalue" id="ROAD_td">
						<script>
							fillListBox("ROAD_td","ROADID","111","select roadid,roadname from T_OA_ROAD_INFO order by roadid","请选择");
						</script>
					</td>
				</tr>
				<tr>
					
				</tr>
			</table>
			<div style="width:100%;text-align: right">
               		<table width="100%">
               			<tr>
               				<%
							if (!insrtOrUpdt.equals("2")) {
							%>
               				<td width="33%" align="center">
		                		<span class="search">
		                			<input type="button" value="保存" id="saveData_Accident" onclick="modify(<%=insrtOrUpdt%>);">
		                		</span>
		                	</td>							
		                	<%
		                	}
							if (insrtOrUpdt.equals("0")) {
							%>	
		                	<td width="33%" align="center">
		                		<span class="search">
									<input type="button" value="重置" id="saveData_Accident" onclick="reset();">
		                		</span>
		                	</td>
		                	<%
							}
							if (insrtOrUpdt.equals("1")) {
							%>	
		                	<td width="33%" align="center">
		                		<span class="search">
		                			<input type="button" value="重置" id="saveData_Accident" onclick="getRoadSectionInfo(sectionid);">
		                		</span>
		                	</td>
		                	<%
							}
							%>
		                	<td width="34%" align="center">
		                		<span class="search">
		                			<input type="button" value="关闭" id="saveData_Accident" onclick="window.close();">
		                		</span>
               				</td>
               			</tr>
               		</table>
			</div>
		</fieldset>
	</div>
</body>
</html>
<%--
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #CCCCCC" align="center">
			<legend style="border:0px;font-size: 12pt;">施工占道信息表</legend>
			<table class="table2" width="100%">
			    <tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="5">
						<input type="hidden" id="insertOrUpdate_TrafficCrowd" class="textwidth" >
						<legend style="border:0px;font-size:14px">接报信息</legend>
					</td>
					<td><input type="button" value="接收上报" id="ReceiveEvent_RoadBuild" onclick="receiveReport('_RoadBuild',$('flowId_RoadBuild').value,$('alarmId_RoadBuild').value);"></td>
				</tr>
				<tr>
				    <input type="hidden" id="flowId_RoadBuild" class="textwidth" name="editinput">
					<input type="hidden" id="alarmId_RoadBuild" class="textwidth" name="editinput">
					<td class="tdtitle">填报时间：</td>
					<td class="tdvalue">
						<input type="text" id="alarmTime_RoadBuild" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">填报人：</td>
					<td class="tdvalue">
						<input type="hidden" id="reportUnit_RoadBuild" class="textwidth" name="editinput" >
						<input type="text" id="reportPerson_RoadBuild" class="textwidth" name="editinput" >
					</td>
				</tr>
				<tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="6">
						<input type="hidden" id="insertOrUpdate_TrafficCrowd" class="textwidth" name="editinput">
						<legend style="border:0px;font-size:14px">施工占道信息</legend>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">占道单位：</td>
					<td class="tdvalue">
						<input type="text" id="alarmDept_RoadBuild" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">负责人：</td>
					<td class="tdvalue">
						<input type="text" id="fzr_RoadBuild" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">联系电话：</td>
					<td class="tdvalue" name="editinput">
						<input type="text" id="phone_RoadBuild" class="textwidth" name="editinput">
					</td>
				</tr>
				<tr>
					<td class="tdtitle">占用道路：</td>
					<td class="tdvalue" id="alarmRoad_RoadBuild_td">
						<script>
							fillListBox("alarmRoad_RoadBuild_td","alarmRoad_RoadBuild","111","select bh,dlmc from t_oa_dict_road t","请选择");
						</script>
					</td>
					<td class="tdtitle" id="">相关路段：</td>
					<td class="tdvalue">
						<input type="text" id="alarmFiled_RoadBuild" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">占用情况：</td>
					<td class="tdvalue">
						<input type="text" id="rotype_RoadBuild" class="textwidth" name="editinput" readonly">
					</td>
				</tr>		
				<tr>
					<td class="tdtitle">占道开始时间：</td>
					<td class="tdvalue">
						<input type="text" id="starttime_RoadBuild" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">占道终止时间：</td>
					<td class="tdvalue" >
						<input type="text" id="endtime_RoadBuild" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td> 	
					<td class="tdtitle">准行情况：</td>
					<td class="tdvalue">
						<input type="text" id="permittype_RoadBuild" class="textwidth" name="editinput">
					</td>
				</tr>	
				<tr style="display : none">
					<td class="tdtitle">经度：</td>
					<td class="tdvalue">
						<input type="text" id="X_RoadBuild" class="textwidth" name="editinput" readonly >
					</td>
					<td class="tdtitle">纬度：</td>
					<td class="tdvalue">
						<input type="text" id="Y_RoadBuild" class="textwidth" name="editinput" readonly> 
					</td>
					<td class="tdtitle"></td>
					<td class="tdvalue" >
						
					</td> 	
				</tr>
				<tr style="display : none">
				<td class="tdtitle">警情状态：</td>
					<td class="tdvalue">
						<input type="hidden" id="alarmState_RoadBuild" class="textwidth">
						<input type="hidden" id="eventType_RoadBuild" class="textwidth">
						<input type="hidden" id="eventSource_RoadBuild" class="textwidth">
						<input type="hidden" id="superUnit_RoadBuild" class="textwidth">
					</td> 
				</tr>	
				<tr>
					<td class="tdtitle">事件内容：</td>
					<td class="tdvalue" colspan="5">
						<textarea  rows="5" style="width:100%" id="alarmDesc_RoadBuild" name="editinput"></textarea>
					</td>
				</tr>
			</table>
			<div style="text-align: right">
				<input type="button" value="定位案发地点" id="signSite_RoadBuild" style="display: none" onclick="showMap('1');">
				<input type="button" value="上报警情" id="saveData_RoadBuild" style="" onclick="editTrfficeRoadBuildInfo();">
			</div>
		</fieldset>
	</div>
--%>