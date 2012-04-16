<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="com.appframe.utils.*"%>
<%@page import="com.ehl.dispatch.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%String alarmId=StringHelper.obj2str(request.getParameter("alarmId"),""); %>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>警情补录信息</title>
	</head>

	
	<body onload="">
		<div style="text-align: center;width: 100%;height: 100%;">
			<fieldset style="width:99%;height:95%;border:1px solid #CCCCCC"
				align="center">
				<legend style="border:0px;">
					警情详细信息
				</legend>


				<table class="table2" width="100%">
					<tr>
						<td class="tdtitle">
							报警单号：


						</td>
						<td class="tdvalue">
							<input type="text" id="complealarmId" class="textwidth" name="compleeditinput">
						</td>
						<td class="tdtitle">
							报警时间：


						</td>
						<td class="tdvalue">
							<input type="text" id="complealarmTime" readonly
								onclick="SelectDateTime(this)" class="textwidth" name="compleeditinput">
						</td>
						<td class="tdtitle">
							报警地点：


						</td>
						<td class="tdvalue">
							<input type="text" id="complealarmSite" class="textwidth" name="compleeditinput">
						</td>
					</tr>
					<tr>
						<td class="tdtitle">
							报警机构：


						</td>
						<td class="tdvalue">
							<input type="text" id="complealarmDept" class="textwidth" name="compleeditinput" readonly>
							
						</td>

						<td class="tdtitle">
							事件类型：


						</td>
						<td class="tdvalue" id="complealarmtypeTd" name="compleeditinput">
							<script>
								fillListBox("complealarmtypeTd","complealarmType","111","select id,name from t_attemper_code where id like '001%'","请选择","","typeChage");
							</script>
							
						</td>
						<td class="tdtitle">
							分中心事件来源：

						</td>
						<td class="tdvalue" id="complesubalarmSouceTd">
							<!-- <input type="text" id="complealarmSouce" class="textwidth"> -->
							<script>
								fillListBox("complesubalarmSouceTd","complesubEventSource","111","select id,name from t_attemper_code where id like '021%'","请选择");
							</script>
						</td>

					</tr>
					<tr>
						<td class="tdtitle">
							事件程度：


						</td>
						<td class="tdvalue" id="complealarmLevelTd">
							<!--<input type="text" id="complealarmLevel" class="textwidth">-->
							<script>
								fillListBox("complealarmLevelTd","complealarmLevel","111","select id,name from t_attemper_code where id like '011%'","请选择");
							</script>
						</td>
						<td class="tdtitle">
							事件状态：
						</td>
						<td class="tdvalue" id="complealarmStateTd">
							<!--<input type="text" id="complealarmState" class="textwidth">-->
							<script>
								fillListBox("complealarmStateTd","complealarmState","111","select id,name from t_attemper_code where id like '004%'","请选择");
							</script>
						</td>
						<td class="tdtitle" id="">
							事件细类：


						</td>
						<td class="tdvalue" id="complealarmThinTypeTd">
							<input type="text" id="complealarmThinType" class="textwidth" name="compleeditinput">
						</td>

					</tr>		
					<tr>
						<td class="tdtitle">
							处警时间：


						</td>
						<td class="tdvalue">
							<input type="text" id="compledisposeTime" class="textwidth" name="compleeditinput" readonly onclick="SelectDateTime(this)">
						</td>
						<td class="tdtitle">
							处警人：
						</td>
						<td class="tdvalue">
							<input type="text" id="compledisposePerson" class="textwidth" name="compleeditinput">
						</td>
						<td class="tdtitle">
							处警单位：


						</td>
						<td class="tdvalue">
							<input type="text" id="compledisposeUnit" class="textwidth" name="compleeditinput">
						</td>
					</tr>	
					
					<tr>
						<td class="tdtitle">
							 上报时间：


						</td>
						<td class="tdvalue">
							<input type="text" id="complereportTime" class="textwidth" name="compleeditinput" readonly >
						</td>
						<td class="tdtitle">
							上报人：
						</td>
						<td class="tdvalue">
							<input type="text" id="complereportPerson" class="textwidth" name="compleeditinput" readonly>
						</td>
						<td class="tdtitle">
							上报单位：


						</td>
						<td class="tdvalue">
							<input type="text" id="complereportUnit" class="textwidth" name="compleeditinput" readonly>
						</td>
					</tr>					
					<tr>
						<td class="tdtitle">
							路段备注：


						</td>
						<td class="tdvalue">
							<input type="text" id="compleroadName" class="textwidth" name="compleeditinput">
						</td>
						
						
						<td class="tdtitle">
							经度：


						</td>
						<td class="tdvalue">
							<input type="text" id="compleX" class="textwidth" name="compleeditinput" readonly>
						</td>
						<td class="tdtitle">
							纬度：


						</td>
						<td class="tdvalue">
							<input type="text" id="compleY" class="textwidth" name="compleeditinput" readonly> 
						</td>

					</tr>
					<tr>
						<td class="tdtitle">
							报警内容：


						</td>
						<td class="tdvalue" colspan="5">
							<textarea  rows="3" style="width:100%" id="complealarmDesc" name="compleeditinput"></textarea>
						</td>
					</tr>
					
					<tr>
						<td class="tdtitle">
							处警意见：


						</td>
						<td class="tdvalue" colspan="5">
							<textarea rows="2" style="width:100%" id="compledisposeIdea" name="compleeditinput"></textarea>
						</td>
					</tr>
					
					<tr style="display: none">
						
						<td class="tdtitle">
							路段编号：


						</td>
						<td class="tdvalue">
							<input type="text" id="compleroadId" class="textwidth" name="compleeditinput">
						</td>
						<td class="tdtitle">
							事件来源：


						</td>
						<td class="tdvalue" id="complealarmSouceTd">
							<!-- <input type="text" id="complealarmSouce" class="textwidth"> -->
							<script>
								fillListBox("complealarmSouceTd","complealarmSouce","111","select id,name from t_attemper_code where id like '002%'","请选择");
							</script>
						</td>
						
					</tr>
					
					
					<tr>
						<td  align="right" class="tdtitle">反馈单位：</td>
					  	<td  align="left" class="tdvalue">
					  		<input type="text" class="textwidth" id="complefkdw" name="compleeditinput" value="" readonly>
					  	</td>
						<td  align="right" class="tdtitle">反馈人：</td>
					  	<td  align="left" class="tdvalue" id="">
					  		<input type="text" class="textwidth" id="complefkr" name="compleeditinput" value="" readonly>
					  	</td>
					  	<td  align="right" class="tdtitle">反馈时间：</td>
					  	<td  align="left" class="tdvalue" id="">
					  		<input type="text" class="textwidth" id="complefksj" name="compleeditinput" value=""  readonly>
					  	</td>
						
					</tr>
					<tr>
						<td  align="right" class="tdtitle">出警单位：</td>
					  	<td  align="left" class="tdvalue">
					  		<input type="text" class="textwidth" id="complecjdw" name="compleeditinput" readonly>
					  	</td>
						<td align="right" class="tdtitle">出警时间：</td>
						<td align="left" class="tdvalue">
							<input type="text" class="textwidth" id="complecjsj" name="compleeditinput" onClick="SelectDateTime(this)" readonly>
						</td>
						<td align="right" class="tdtitle">到警时间：</td>
						<td align="left" class="tdvalue">
							<input type="text" class="textwidth" id="compledjsj" name="compleeditinput" onClick="SelectDateTime(this)" readonly>
						</td>
						
					</tr>
					<tr>
						<td align="right" class="tdtitle">
							出警人：
						</td>
						<td align="left" class="tdvalue">
							<input type="text" style="width:98" id="complecjr"
								 name="compleeditinput">
								<img src="../../../sm/image/popup/btnselect.gif" id="" onclick="chkPerson('okAddPersonClick(\'complete\');',180,250)" alt="选择人员" style="cursor: hand"/>
						</td>
						
						<td align="right" class="tdtitle" id="ajfssjTd1">事件发生时间：</td>
						<td align="left" class="tdvalue" id="ajfssjTd2">
							<input type="text" class="textwidth" id="compleajfssj" name="compleeditinput" onClick="SelectDateTime(this)" readonly>
						</td>
						<td align="right" class="tdtitle">事件结束时间：</td>
						<td align="left" class="tdvalue">
							<input type="text" class="textwidth" id="compleajjssj" name="compleeditinput" onClick="SelectDateTime(this)" readonly>
						</td>
						
						
					  
					 </tr>
					 <tr style="display: inline">
					 	<td align="right" class="tdtitle">
							出警人数：
						</td>
						<td align="left" class="tdvalue">
							<input type="text" style="width:120" id="complecjrs"
								name="compleeditinput" onkeydown="keyDown();"
								onKeyPress="keyPress()" onblur="isValidate(this);">
						</td>
						
						<td align="right" class="tdtitle">出动车辆：</td>
						<td align="left" class="tdvalue">
							<input type="text" style="width:91" id="complecdcl"
								name="compleeditinput" onkeydown="keyDown();"
								onKeyPress="keyPress()" onblur="isValidate(this);">
							<img src="../../../sm/image/popup/btnselect.gif" id="" onclick="chkCar('comple')" alt="选择车辆" style="cursor: hand"/>
						</td>
						
						
					</tr>
					<tr> 
						<td align="right" class="tdtitle">处理结果：</td>
						<td colspan="5" align="left" class="tdvalue">
							<textarea rows="2" style="width:100%" id="complecljg" name="compleeditinput" >
						</textarea></td>
					</tr>				

					<tbody id="compleaccidentTbody" style="display: none">

						<jsp:include flush="true" page="compleAccidentEdit.jsp"></jsp:include>

					</tbody>
				
					<tbody id="compleexceptionCarTbody" style="display: none">

						<jsp:include flush="true" page="compleexceptionCarEdit.jsp"></jsp:include>

					</tbody>
					<tbody id="complepoliceEventTbody" style="display: none">

						<jsp:include flush="true" page="complepoliceEventEdit.jsp"></jsp:include>

					</tbody>
					
					<tbody id="complebadWeatherTbody" style="display: none">

						<jsp:include flush="true" page="complebadWeatherEdit.jsp"></jsp:include>

					</tbody>
					
					<tbody id="completownPlanTbody" style="display: none">

						<jsp:include flush="true" page="completownPlanEdit.jsp"></jsp:include>

					</tbody>
					
					<tbody id="complefireAndBlastTbody" style="display: none">

						<jsp:include flush="true" page="complefireAndBlastEdit.jsp"></jsp:include>

					</tbody>
					
					<tbody id="complegeoLogicDisasterTbody" style="display: none">

						<jsp:include flush="true" page="compleGeoLogicDisasterEdit.jsp"></jsp:include>

					</tbody>
					<tr>
						<td class="td2" colspan="6"></td>
					</tr>

				</table>
				<div style="text-align: right">
					
					<input type="button" value="定位案发地点" id="complesignSite" style="display: none"
						onclick="showMap('3');">
					<input type="button" value="上报警情" id="complesaveData" style="display: none"
						onclick="editCompleAlarmInfo()">

				</div>
			</fieldset>
		</div>
	</body>
</html>
