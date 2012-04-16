<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>警情回馈</title>
		<script type="text/javascript"
			src="../../js/policeFeedback/ModifyFeedBack.js"></script>
		<script type="text/javascript"
			src="../../js/policeFeedback/FeedbackExceptionCar.js"></script>
		<script type="text/javascript"
			src="../../js/policeFeedback/FeedBackWeather.js"></script>
		<script type="text/javascript"
			src="../../js/policeFeedback/FeedBackPoliceEvent.js"></script>
		<script type="text/javascript"
			src="../../js/policeFeedback/FeedBackFireAndBlast.js"></script>
		<script type="text/javascript"
			src="../../js/policeFeedback/FeedBackBlackList.js"></script>
		<script type="text/javascript"
			src="../../js/policeFeedback/FeedBackGeoLogicDisaster.js"></script>
		<script type="text/javascript"
			src="../../js/policeFeedback/FeedBackTownPlan.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
	</head>
	<body>
		<fieldset style="width:100%;height:90%;border:1px solid #CCCCCC"
			align="center">
			<div id="tableList" style="overflow-y: scroll; height=90;border: 1px solid #cccccc;"> </div>
			<div style="text-align: right">
				<input type="button" value="出 警" id="addComout">
				<input type="button" value="事件结束" id="addFinish">
			</div>
			<div id="feedbackInfo" style="display: none">
				<legend style="border:0px;font-size:14px">
					反馈单内容				</legend>
				<div style="height:350px; overflow: auto;">
				<table id="fedBackGrid" width="99%" class="table2">
					<tr>
						<td align="center" style="display:none">
							<input type="hidden" id="FEEDBACKID" value="">
							<input type="hidden" id="eventType_feed">
							<input type="hidden" id="sjly">
							<input type="hidden" id="fkzj" value="">
							<input type="hidden" id="deptcode" value=="">
						</td>
					</tr>
					<tr bgcolor="#99c4f2">
						<td class="tdtitle" colspan="6">
							<legend style="border:0px;font-size:12px">
								出警反馈信息：							</legend>
						</td>
					</tr>
					<tr>
						<td width="90" align="right" class="tdtitle">
							报警单号：						</td>
						<td width="70" align="left" class="tdvalue">
							<input type="text" style="width:120" id="ALARMID_FEED"
								name="feedBackForInit" value="" readonly>
						</td>
						<td width="90" align="right" class="tdtitle">
							事件标题：						</td>
						<td width="70" align="left" class="tdvalue" colspan="3">
							<input type="text" class="textwidth" id="eventtile"
								name="feedBackForInit" value="" readonly>
						</td>
						<td style="display:none" align="right" class="tdtitle">
							是否警单归并：						</td>
						<td style="display:none" align="left" colspan="3" class="tdvalue">
							<input type="checkbox" name="feedBackForInit_box" id="jdhb" DISABLED=true>
						</td>

					</tr>
					<tr>
						<td align="right" class="tdtitle" id="ajfssjTd1">
							事件发生时间：						</td>
						<td align="left" class="tdvalue" id="ajfssjTd2">
							<input type="text" style="width:120" id="ajfssj"
								name="feedBackForInit" onClick="SelectDateTime(this)" readonly>
						</td>
						<td align="right" class="tdtitle">
							案件类型：						</td>
						<td align="left" class="tdvalue" id="ajlx_td">
							<script language="javascript">
	   						fillListBox("ajlx_td","ajlx_feedBack","120","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE SUBSTR(ID,0,3)='001' ORDER BY ID","未选择","","","");
					    </script>
						</td>
						<td width="87" align="right" class="tdtitle">
							出警单位：						</td>
						<td width="75" align="left" class="tdvalue">
							<input type="text" style="width:120" id="cjdw"
								name="feedBackForInit" value="" readonly>
						</td>
					</tr>
					<tr id="outTr">
						<td align="right" class="tdtitle">
							出警人：
						</td>
						<td align="left" class="tdvalue">
							<input type="text" style="width:100" id="cjr"
								name="feedBackForInit">
								<img src="../../../sm/image/popup/btnselect.gif" id="" onclick="chkPerson('okAddPersonClick();',180,250)" alt="选择人员" style="cursor: hand"/>
						</td>
						<td align="right" class="tdtitle">
							出警人数：						</td>
						<td align="left" class="tdvalue">
							<input type="text" style="width:120" id="cjrs"
								name="feedBackForInit" onkeydown="keyDown();"
								onKeyPress="keyPress()" onblur="isValidate(this);">
						</td>
						<td align="right" class="tdtitle">
							出动车辆：						</td>
						<td align="left" class="tdvalue">
							<input type="text" style="width:100" id="cdcl"
								name="feedBackForInit" onkeydown="keyDown();"
								onKeyPress="keyPress()" onblur="isValidate(this);">
							<img src="../../../sm/image/popup/btnselect.gif" id="" onclick="chkCar()" alt="选择车辆" style="cursor: hand"/>
						</td>
					</tr>
					<tr>
						<td align="right" class="tdtitle" id="cjsjnametd">
							出警时间：						</td>
						<td align="left" class="tdvalue" id="cjsjvaluetd">
							<input type="text" style="width:120" id="cjsj"
								name="feedBackForInit" onClick="SelectDateTime(this)" readonly>
						</td>
						<td width="90" align="right" class="tdtitle">
							反馈人：
						</td>
						<td width="75" align="left" class="tdvalue" colspan="3"
							id="pname_td">
							<input type="text" style="width:120" id="fkr"
								name="feedBackForInit" value="" readonly>
						</td>

						<td width="90" align="right" class="tdtitle" style="display:none">
							反馈单位：						</td>
						<td width="70" align="left" class="tdvalue" style="display:none">
							<input type="text" style="width:120" id="fkdw"
								name="feedBackForInit" value="" readonly>
						</td>
					</tr>
					<tr style="display: inline" id="outCarTr">
						<td align="right" class="tdtitle" style="display:none">
							事件状态：
						</td>
						<td align="left" class="tdvalue" style="display:none" id="sjzt_td">
							<script>
								fillListBox("sjzt_td","sjzt","100","SELECT ID,NAME FROM T_ATTEMPER_CODE WHERE ID LIKE '004%'","请选择");
						</script>
						</td>
					</tr>
					<tr>
						<td align="right" valign="bottom" colspan="6" class="tdtitle">
							<input type="button" class="" value="出警反馈" id="cjbtn"
								name="feedBackForOut_btn" onclick="eventtype('cj');"
								style="width:70">
							&nbsp;

						</td>
					</tr>
					<tr bgcolor="#99c4f2">
						<td class="tdtitle" colspan="6">
							<legend style="border:0px;font-size:12px">
								现场反馈信息：							</legend>
						</td>
					</tr>
					<!--交通事故与交通堵塞-->
					<tbody id="001001,001002" value=""
						name="tbodyForPoliceEventFeedBack" style="display:none">
						<jsp:include flush="true" page="Feedbacktraffic.jsp"></jsp:include>
					</tbody>
					<!-- 嫌疑车辆	-->
					<tbody id="001005" value="_BlackList"
						name="tbodyForPoliceEventFeedBack" style="display:none">
						<jsp:include flush="true" page="FeedbackBlackList.jsp"></jsp:include>
					</tbody>
					<!-- 灾害天气	-->
					<tbody id="001006" value="_Weather"
						name="tbodyForPoliceEventFeedBack" style="display:none">
						<jsp:include flush="true" page="FeedbackWeather.jsp"></jsp:include>
					</tbody>
					<!-- 治安事件	-->
					<tbody id="001007" value="_PoliceEvent"
						name="tbodyForPoliceEventFeedBack" style="display:none">
						<jsp:include flush="true" page="FeedbackPoliceEvent.jsp"></jsp:include>
					</tbody>
					<!--大型故障车-->
					<tbody id="001008" value="_ExceptionCar"
						name="tbodyForPoliceEventFeedBack" style="display:none">
						<jsp:include flush="true" page="FeedbackExceptionCar.jsp"></jsp:include>
					</tbody>
					<!-- 地质灾害 -->
					<tbody id="001010" value="_GeoLogicDisaster"
						name="tbodyForPoliceEventFeedBack" style="display:none">
						<jsp:include flush="true" page="FeedbackGeoLogicDisaster.jsp"></jsp:include>
					</tbody>
					<!-- 市政事件 -->
					<tbody id="001011" value="_TownPlan"
						name="tbodyForPoliceEventFeedBack" style="display:none">
						<jsp:include flush="true" page="FeedbackTownPlan.jsp"></jsp:include>
					</tbody>
					<!-- 火灾爆炸 -->
					<tbody id="001012" value="_FireAndBlast"
						name="tbodyForPoliceEventFeedBack" style="display:none">
						<jsp:include flush="true" page="FeedbackFireAndBlast.jsp"></jsp:include>
					</tbody>

					<!-- 无事件类型 -->
					<tbody id="noEventType" value="_Other"
						name="tbodyForPoliceEventFeedBack" style="display:none">
						<jsp:include flush="true" page="FeedbackOther.jsp"></jsp:include>
					</tbody>

					<tr>
						<td align="right" class="tdtitle">
							处理结果：						</td>
						<td colspan="5" align="left" class="tdvalue">
							<textarea rows="2" style="width:100%" id="cljg"
								name="feedBackForInit"></textarea>
						</td>
					</tr>

					<tr id="trddxcbtn">
						<td align="right" valign="bottom" colspan="6" class="tdtitle">
							<input type="button" class="" value="现场反馈" id="ddxcbtn"
								name="feedBackForArray_btn" onclick="eventtype('ddxc');"
								style="width:70">
							&nbsp;

						</td>
					</tr>

					<tr id="trclwbbtn">
						<td align="right" valign="bottom" colspan="6" class="tdtitle">
							<input type="button" class="" value="事件结束反馈" id="clwbbtn"
								name="feedBackForFinish_btn" onclick="eventtype('clwb');"
								style="width:90">
							&nbsp;

						</td>
					</tr>
					<tr style="display:none">
						<td align="right" valign="bottom" colspan="6" class="tdtitle">

						</td>
					</tr>
				</table>
				<br>
				</div>
			</div>
		</fieldset>
	</body>
</html>
