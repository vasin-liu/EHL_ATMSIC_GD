<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.util.*" %>
<%
 	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String depttype = ""; //机构类型   
	String pname = ""; //姓名
	String uname = ""; //帐号
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		depttype = (String)prop.get("DEPTTYPE");
		pname = (String)prop.get("DEPTTYPE");
		uname = (String)prop.get("USERNAME");
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
		<title>警情监控界面</title> 
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
		<script type="text/javascript" src="../../js/common/utility/ListChose.js"></script>
		<script type="text/javascript" src="../../js/common/utility/comLogic.js"></script>
		<script type="text/javascript" src="../../js/common/utility/DivHolder.js"></script>
		<script type="text/javascript" src="../../js/common/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/common/CommonClear.js"></script>
		<script type="text/javascript" src="../../js/editPolice/PoliceTool.js"></script>
		<script type="text/javascript" src="../../js/editPolice/ModifyAccident.js"></script>
	</head>
	<script type="text/javascript">
		window.attachEvent('onload',pageLoadInitHandler);
	 	var deptcode = '<%=deptcode%>';
		var userCountyText = '<%= deptname %>';
		var depttype = '<%= depttype %>';
		var pname = '<%=pname%>';
		var userNameText = '<%= uname %>';
		
		//显示警情时间范围（小时）
		var msgWatchingTimeRange = '<%= com.appframe.common.Setting.getString("msgWatchingTimeRange") %>';
		//刷新频率（单位：秒）
		var msgListRefreshInterval = '<%= com.appframe.common.Setting.getString("msgListRefreshInterval") %>';
		
		function pageLoadInitHandler(){
			initWatching();		//初始化监控警情列表
			
			initPages();		
			$("mapTd").style.display="none";
			//绑定功能按钮操作
			$("addAccident").attachEvent('onclick',function(event){openAddPolice('Accident');});
			$("addCongestion").attachEvent('onclick',function(event){openAddPolice('Congestion');});
			$("addRoadBuild").attachEvent('onclick',function(event){openAddPolice('RoadBuild');});
			$("addTrafficRestrain").attachEvent('onclick',function(event){openAddPolice('TrafficRestrain');});
			$('spanShowHourSetting').innerHTML = msgWatchingTimeRange; //显示关注警情时间值
		}
	</script>
	<body style="margin:0;">
		<table border="0" cellspacing="0" cellpadding="0" style="text-align: center; width: 100%; height: 100%;">
			<tr>
				<td width=33% valign=top  class="td_r_b">
					<table border="0" cellspacing="0" cellpadding="0" style="text-align: center; width: 100%; height: 100%;">
						<tr>
							<td valign=top class="td_r_b">
								<fieldset style="border: 1px solid #ccc; valign: top; align: center">
									<legend style="border: 0px; font-weight: 700; font-size: 8pt;">
										警情信息(最近<span id="spanShowHourSetting"></span>小时)
									</legend>
									<table style="border: 0; height: 490px; cellpadding: 0; cellspacing: 0;width:98%">
										<tr>
											<td style="line-height:8px">&nbsp;
											<a href="#" id="ahrefAllAffair">
												<input type='radio' name="radioSortLink" checked style="width:12px;height:12px" id="radioAllAffair">
												需关注警情(共<span id="spanAllAffair"></span>起)
											</a>&nbsp;
											 </td>
											<td style="line-height:8px">&nbsp;<a href="#" id="ahrefComplete">
												<input type='radio' name="radioSortLink" style="width:12px;height:12px" id="radioCompleteAffair"/>
												处理完毕警情(共<span id="spanCompleteAffair"></span>起)
											</a>&nbsp;
											</td>
											
										</tr>
										<tr style="display: none">
											<td style="line-height:12px">&nbsp;
												<a href="#" id="ahrefProcessing">
												<input type='radio' name="radioSortLink" style="width:12px;height:12px" id="radioProcessingAffair"/>
													正在处理警情(共<span id="spanProcessingAffair"></span>起)
												</a>&nbsp;</td>
										 	<td style="line-height:12px">&nbsp;
												<a href="#" id="ahrefUnhandle">
												<input type='radio' style="width:12px;height:12px" name="radioSortLink" id="radioUnhandleAffair" />
													未处理警情(共<span id="spanUnhandleAffair"></span>起)
												</a> &nbsp;
											</td>	
										</tr>
										<tr>
											<!-- 未处理警情列表 -->
											<td width="100%" align=center colspan=2>
											<div id="divUnhandleList"  
												style="width:98%;vertical-align:text-top;height:420px;text-align:left;overflow:auto;border:1 solid #000;" > 
												</div>
											</td>
										</tr>
									</table>
								</fieldset>
							 </td>
						 </tr>
					</table>
				</td>
				<td width=2px>&nbsp;
				</td>
				<td width=66%>
					<table border="0" cellspacing="0" cellpadding=0 style="width:100%; height: 100%;">
						<!-- 操作按钮 -->
						<tr>
							<td align="left" valign="bottom" height=30px width=100%>
								<font style="text-align: left;width: 90%" id="ctrlList">
									<input type="button" value="上报交通事故" id="addAccident" associateDiv="divAccident" mapDiv="mapTd">
									<input type="button" value="上报交通拥堵" id="addCongestion" associateDiv="divCongestion" mapDiv="mapTd">
									<input type="button" value="上报施工占道" id="addRoadBuild" associateDiv="divRoadBuild" mapDiv="mapTd">
									<input type="button" value="上报交通管制" id="addTrafficRestrain" associateDiv="divTrafficRestrain" mapDiv="mapTd">
								</font>	
							</td>
							
						</tr>
						<tr>
							<td valign="top" style="text-align:center" id="divContainer">
								<div id="divAccident" style="width:100%;">
									<jsp:include flush="true" page="../policeEdit/accidentEdit.jsp"></jsp:include>
								</div>
								<div id="divCongestion" style="width:100%;display:none">
									<jsp:include flush="true" page="../policeEdit/policeEdit.jsp"></jsp:include>
								</div>
								<div id="divRoadBuild" style="width:100%;display:none">
									<jsp:include flush="true" page="../policeEdit/RoadBuild.jsp"></jsp:include>
								</div>
								<div id="divTrafficRestrain" style="width:100%;display:none">
									<jsp:include flush="true" page="../policeEdit/TrafficContrl.jsp"></jsp:include>
								</div>
							</td>
						</tr>
						<tr id="mapTd">
							<td  valign=top style="text-align:center;width:100%" align=center >								
								<div id="map"  style="position: relative; width: 100%; height: 470px; left: 0px; top: 0px;margin:0"></div> 
								<div style="text-align: right;width:100%">
									<a href=# style="font-color:blue;cursor:hand;font-weight:500" id="backInfobtn" >[ 返  回 ]</a>&nbsp;
								</div>							
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<script type="text/javascript" src="../../../webgis/script/map/MapConfig.js"></script>
		<script type="text/javascript" src="../../js/editPolice/policeConfig.js"></script>
	</body>
</html>
