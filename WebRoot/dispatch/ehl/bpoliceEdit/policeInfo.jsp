<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.*"%>
<%@page import="com.ehl.dispatch.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String alarmId = StringHelper.obj2str(request.getParameter("alarmId"), "");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>警情详细信息</title>
	</head>
	<script type="text/javascript" src="../../js/common/popup/Popup.js"></script>
	<script type="text/javascript" src="../../../sm/js/pcs/DepartmentSelect.js"></script>
<body onload="">
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #CCCCCC" align="center">
			<legend style="border:0px;">警情详细信息</legend>
			<table class="table2" width="100%">
				<tr>
					<td class="tdtitle">报警单号：</td>
					<td class="tdvalue"><font id="alarmIdInfo"></font></td>
					<td class="tdtitle">报警时间：</td>
					<td class="tdvalue"><font id="alarmTimeInfo"></font></td>
					<td class="tdtitle">报警地点：</td>
					<td class="tdvalue"><font id="alarmSiteInfo"></font></td>
				</tr>
				<tr>
					<td class="tdtitle">报警机构：</td>
					<td class="tdvalue"><font id="alarmDeptInfo"></font></td>
					<td class="tdtitle">事件类型：</td>
					<td class="tdvalue"><font id="alarmTypeInfo"></font></td>
					<td class="tdtitle">事件来源：</td>
					<td class="tdvalue" id=""><font id="alarmSouceInfo"></font></td>
				</tr>
				<tr>
					<td class="tdtitle">事件程度：</td>
					<td class="tdvalue" id=""><font id="alarmLevelInfo"></font></td>
					<td class="tdtitle">事件状态：</td>
					<td class="tdvalue" id=""><font id="alarmStateInfo"></font></td>
					<td class="tdtitle" id="">事件细类：</td>
					<td class="tdvalue" id=""><font id="alarmThinTypeInfo"></font></td>
				</tr>
				<tr>
					<td class="tdtitle">路段名称：</td>
					<td class="tdvalue"><font id="roadNameInfo"></font></td>
					<td class="tdtitle">路段编号：</td>
					<td class="tdvalue"><font id="roadIdInfo"></font></td>
					<td class="tdvalue"></td>
					<td class="tdvalue"></td>
				</tr>
				<tr>
					<td class="tdtitle">报警内容：</td>
					<td class="tdvalue" colspan="5"><font id="alarmDescInfo"></font></td>
				</tr>
				<tr>
					<td class="tdtitle">处警时间：</td>
					<td class="tdvalue"><font id="disposeTimeInfo"></font></td>
					<td class="tdtitle">处警人：</td>
					<td class="tdvalue"><font id="disposePersonInfo"></font></td>
					<td class="tdtitle">处警单位：</td>
					<td class="tdvalue"><font id="disposeUnitInfo"></font></td>
				</tr>
				<tr>
					<td class="tdtitle">处警意见：</td>
					<td class="tdvalue" colspan="5"><font id="disposeIdeaInfo"></font></td>
				</tr>
				<tr style="display: none">
					<td class="tdtitle">X：</td>
					<td class="tdvalue"><input type="text" id="XInfo" class="textwidth" name="editinput"></td>
					<td class="tdtitle">Y：</td>
					<td class="tdvalue"><input type="text" id="YInfo" class="textwidth" name="editinput"></td>
					<td class="tdtitle"></td>
					<td class="tdvalue"></td>
				</tr>
				<tbody id="accidentInfoTbody" style="display: none">
					<jsp:include flush="true" page="accidentInfo.jsp"></jsp:include>
				</tbody>
				<tbody id="congestionInfoTbody" style="display:none">
					<jsp:include flush="true" page="congestionInfo.jsp"></jsp:include>
				</tbody>
				<tbody id="exceptionCarInfoTbody" style="display:none">
					<jsp:include flush="true" page="exceptionCarInfo.jsp"></jsp:include>
				</tbody>
				<tbody id="policeEventInfoTbody" style="display: none">
					<jsp:include flush="true" page="policeEventInfo.jsp"></jsp:include>
				</tbody>
				<tbody id="blackListInfoTbody" style="display: none">
					<jsp:include flush="true" page="blackListInfo.jsp"></jsp:include>
				</tbody>
				<tbody id="badWeatherInfoTbody" style="display: none">
					<jsp:include flush="true" page="badWeatherInfo.jsp"></jsp:include>
				</tbody>
				<tbody id="townPlanInfoTbody" style="display: none">
					<jsp:include flush="true" page="townPlanInfo.jsp"></jsp:include>
				</tbody>
				<tbody id="fireAndBlastInfoTbody" style="display: none">
					<jsp:include flush="true" page="fireAndBlastInfo.jsp"></jsp:include>
				</tbody>
				<tbody id="GeoLogicDisasterInfoTbody" style="display: none">
					<jsp:include flush="true" page="GeoLogicDisasterInfo.jsp"></jsp:include>
				</tbody>
				<tr id="markRoadinfo" style="display: 'none'">
					<td class="tdtitle">道路名称：</td>
					<td class="tdvalue" id ="dlmc_tdinfo">
						<script>
							fillListBox("dlmc_tdinfo","dlmcinfo","111","SELECT DISTINCT DLBH,DLMC  FROM LCB_PT WHERE DLBH IS NOT NULL AND DLBH != ' '","请选择","","","sde");
						</script>
					</td>
					<td class="tdtitle">千米值：</td>
					<td class="tdvalue">
						<input type="text" id="kmvalueinfo" title="请输入大于0且小于1000的整数" onblur="isValidate(this);" class="textwidth" name="editinput" value=0>
					</td>
					<td class="tdtitle">百米值：</td>
					<td class="tdvalue">
						<select id="mvalueinfo" style="width:111px;">
							<option value="0" >0</option>
							<option value="100" selected>100</option>
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
				<tr>
					<td class="td2" colspan="6"></td>
				</tr>
			</table>
			<div style="text-align: right">
				<input type="button" value="快速定位" id="markPointinfo" style="display: none" onclick="markOnRoad('info');">
				<input type="button" value="查看子单信息" id="showsonListInfo" style="display: none" onclick="">
				<input type="button" value="查看案发地点" id="signSiteInfo" style="display: none" onclick="showMap('2');">
			</div>
		</fieldset>
	</div>
</body>
</html>
