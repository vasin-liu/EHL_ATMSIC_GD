<%@page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/jsp/base.jsp"%>
<%@page import="com.appframe.data.sql.DBHandler"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%
	String title = FlowUtil.getFuncText("570602");	
	//添加0,查看2,修改1
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? ""
			: request.getParameter("insrtOrUpdt");
	String enterState = request.getParameter("enterState") == null ? ""
			: request.getParameter("enterState");
	String alarmId = StringHelper.obj2str(request
			.getParameter("alarmId"), "");
	alarmId = alarmId.replace("'", "");
	System.out.println("alarmId:" + alarmId);
	String depttype = StringHelper.obj2str(request
			.getParameter("depttype"), "");
	String deptcodeStr = StringHelper.obj2str(request
			.getParameter("deptcode"), "");
	//Modified by Liuwx 2011-06-23
	String relieveTime = StringHelper.obj2str(request
			.getParameter("CaseEndTime"), "");
	String trfficeCrowState = request.getParameter("trfficeCrowState") == null ? "" 
			: StringHelper.obj2str(request.getParameter("trfficeCrowState"), "");
	//Modification finished
    //Modified by Liuwx 2012-3-29 10:39:39
    //是否显示计分栏，默认不显示:
    // 0-表示不显示；1-表示查看；2-表示修改；
    String isScoring = request.getParameter("isScoring") == null ? "0" : request.getParameter("isScoring");
    //Modification finished

	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String personName = ""; //姓名
	String uname = ""; //帐号
	String pid = ""; //办公电话
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	if (prop != null) {
		deptcode = (String) prop.get("BRANCHID");
		deptname = (String) prop.get("BRANCHNAME");
		personName = (String) prop.get("NAME");
		uname = (String) prop.get("USERNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
	}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	Date date = new Date();
	String daytime = sdf.format(date);

	String[] strObj = DepartmentManage.getDeptInfo(request, "1").split(
			",");//获取单位信息串
	String jgid = strObj[0];//单位编码
	String jgmc = strObj[1];//机构名称
	String ccbm = strObj[2];//机构层次编码
	//String jgid="441905000000";//12位机构编码
	String jgbh;//总队(2位),支队(4位),大队(6位)
	if ("0000".equals(jgid.substring(2, 6))) {
		jgbh = jgid.substring(0, 2);
	} else if ("00".equals(jgid.substring(4, 6))) {
		jgbh = jgid.substring(0, 4);
	} else {
		jgbh = jgid.substring(0, 6);
	}

	String systime = DBHandler.getSingleResult(
			"select to_char(sysdate,'yyyy-mm-dd hh24:mi') from dual")
			.toString();
	systime = systime.substring(0, systime.length() - 2);
	String id = jgid.substring(0, 6)
			+ systime.replace("-", "").replace(":", "")
			.replace(" ", "");
	systime = systime.substring(0, systime.lastIndexOf(":")).replace(
			"-", "/");
	if (alarmId != "") {
		id = alarmId.substring(0, alarmId.length() - 3);
	}
	//personName = DispatchUtil.getDutyPersonNameByDepId(jgid);
	personName = StringHelper.obj2str(DispatchUtil.getDutyPersonNameByDepId(jgid),"");
	Object apname = session.getAttribute(Constant.ZBLDXM_VAR);
	apname = Constant.nvl(apname);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%=title %></title>
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/tab.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
		<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
		<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
		<link rel="stylesheet" type="text/css" href="${contextPath}dynamicinfo/css/text.css">
		<link rel="stylesheet" type="text/css" href="../../css/alarm/crowd.css">
		<script type="text/javascript" src="${contextPath}util/jquery/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="${contextPath}util/jquery/jquery.json-2.3.min.js"></script>
		<script type="text/javascript" src="${contextPath}base/js/prototype.js"></script>
		<link rel="stylesheet" type="text/css" href="${contextPath}util/jquery/jquery-ui/themes/start/jquery-ui-1.8.17.custom.css" />
		<script type="text/javascript" src="${contextPath}util/jquery/jquery-ui/jquery-ui-1.8.17.custom.min.js"></script>
		<script type="text/javascript" src="${contextPath}base/js/new/base.js"></script>
		<script type="text/javascript" src="${contextPath}base/js/new/api.date.js"></script>
		<script type="text/javascript" src="${contextPath}base/js/new/api.string.js"></script>
		<link rel="stylesheet" type="text/css" href="${contextPath}util/widget/widget.list.css" />
		<script type="text/javascript" src="${contextPath}util/widget/widget.list.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../../base/js/tree/tree.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/editTrfficeCrowd.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/crowd.jquery.js"></script>
		<script type="text/javascript" src="../../js/ccommon/RoadDept.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Flow.js"></script>
		<script type="text/javascript" src="../../js/ccommon/content.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Department.js"></script>
		<script type="text/javascript" src="../../js/ccommon/FileUpDownload.js"></script>
		<style type="text/css">
			.btn{
				BORDER-RIGHT: #000000 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #000000 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; 
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#0c9ad3, EndColorStr=#085887); 
				BORDER-LEFT: #000000 1px solid; CURSOR: hand; COLOR: #ffffff; PADDING-TOP: 2px; BORDER-BOTTOM: #000000 1px solid
			}
			.table2{
				background:#ffffff;
				border-top: 1 solid #106ead;
				border-right: 1 solid #106ead;
				border-bottom: 1 solid #106ead;
				border-left: 1 solid #106ead;
				border-collapse:collapse;
				font-size:11px;
				text-align: center;
			}
			.tdtitle{
				border-top: 0 solid #000000;
				border-right: 1 solid #a5d1ec;
				border-bottom: 1 solid #a5d1ec;
				border-left: 1 solid #a5d1ec;
				line-height: 16px;
				color: #000000;
				border-collapse : separate;
				empty-cells:show;
				text-align: center;
				width:12%;
				height: 32px;
				
			}
			.tdvalue{
				border-top: 0 solid #000000;
				border-right: 1 solid #a5d1ec;
				border-bottom: 1 solid #a5d1ec;
				border-left: 1 solid #a5d1ec;
				line-height: 16px;
				color: #000000;
				border-collapse : separate;
				empty-cells:show;
				text-align: left;
				width:16%;
				height: 32px;
			}
			
			.textwidth{
				width: 160;
			}
			/*文本框变下划线*/
			.text {
			    font: 14px Tahoma, Verdana;
				border: 0;
				border-bottom: 1 solid black;
				background: ;
				text-align:center;
				/*padding-left:10px;*/
				width:62px;
			}
			
		</style>
	</head>
<script type="text/javascript">
	var p_alarmId = "<%=alarmId%>";
	var p_id = "<%=id%>";
	var p_time = "<%=systime%>";
	var p_jghb = "<%=jgbh%>";
    var p_isScoring = "<%=isScoring%>";
</script>
	<body bgcolor="#FFFFFF" onload="getCrowdInfo(p_alarmId,p_jghb);showScoring(p_isScoring);">
		<input id="enterState" type="hidden" value="<%=enterState %>"/>
		<input id="jgbh" type="hidden" value="<%=jgbh%>" readonly></input>
		<input id="ALARMID" type="hidden" value="<%=alarmId%>" readonly></input>
		<%--ALARMID--%>
		<input id="EVENTSOURCE" type="hidden" value="002022" readonly></input>
		<%--警情上报系统--%>
		<input id="EVENTTYPE" type="hidden" value="001002" readonly></input>
		<%--交通拥堵--%>
		<%--EVENTSTATE  事件状态--%>
		<input id="ALARMUNIT" type="hidden" value="<%=deptcode%>" readonly></input>
		<%--报警机构--%>
		<input id="TITLE" type="hidden" value=" 发生 交通拥堵" readonly></input>
		<%--标题--%>
		<input id="ALARMREGIONID" type="hidden" value="<%=deptcode%>"
			readonly>
		<%--报警辖区编号--%>
		<input id="ALARMREGION" type="hidden" value="<%=deptname%>" readonly>
		<%--报警辖区--%>
		<input id="REPORTUNIT" type="hidden" value="<%=deptcode%>" readonly>
		<%--填报单位--%>
		<input id="REPORTPERSON" type="hidden" value="<%=uname%>" readonly>
		<%--填报人--%>
		<input id="page" type="hidden" value="<%=insrtOrUpdt%>" readonly>
		<input id="jgid" type="hidden" value="<%=jgid%>" >
		<input id="pname" type="hidden" value="<%=personName%>" >
		<input id="tbdw" type="hidden"  >
		<div style="width: 100%;">
			<fieldset style="width:99%;height:95%;border:1px solid #a5d1ec"
				align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="wTableTopCenter">
						<td width="5%">
							<div align="center">
								<img src="../../../base/image/cssimg/table/tb.gif" width="16"
									height="16" alt="img" />
							</div>
						</td>
						<td width="70%" class="currentLocation">
							<span class="currentLocationBold"><b>您当前的位置</b>
							</span>：<%=title %>
						</td>
					</tr>
				</table>
	            <div id="crowdAddPrompt" style="font-size:12px;padding-left:1px;">&nbsp;&nbsp;◆ 该信息主要用于实时路况展示，拥堵已经结束的信息请不要填报！</div>
				<div id="showLength"  style="color:red;text-align: center;display: none;"></div>
				<table id="tblCrowd" class="table2" width="100%">
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							<label id="roadLevelLabelId">道路等级</label>
						</td>
						<td class="tdvalue" >
							<select id="roadLevelValueId" class="textwidth" onchange="Road.roadLevel.onchange(this);">
							</select>&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							道路名称
						</td>
						<td class="tdvalue" id="alarmRoad_TrafficCrowd_td"></td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							路段备注
							<br>（请填至镇、乡或村路段）
						</td>
						<td class="tdvalue" style="padding-right: 15px;">
							<input type="text" id="ROADNAME" name="rsname" class="textwidth"
								style="border: 1px #7B9EBD solid" >&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵起始时间
						</td>
						<td class="tdvalue">
							<input type="text" id="CaseHappenTime" class="textwidth"
								style="border: 1px #7B9EBD solid" name="happenTime"
								value="<%=daytime%>" readonly onclick="SelectDateTime(this)">&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
					<tr id = "trLocation">
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵开始里程
						</td>
						<td class="tdvalue">
							K
							<input type="text" id="KMVALUE" name="km" class="text"
								maxlength="6">
							+
							<input type="text" id="MVALUE" name="m" class="text"
								maxlength="3" value="0">
							米
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵结束里程
						</td>
						<td class="tdvalue">
							K
							<input type="text" id="EndKMVALUE" name="ekm" class="text"
								maxlength="6">
							+
							<input type="text" id="EndMVALUE" name="em" class="text" value="0"
								maxlength="3">
							米
						</td>
					</tr>
					<tr id="direction_standard_div">
						<td class="tdtitle" bgcolor="#F0FFFF">
							方向
						</td>
						<td class="tdvalue" id="tdDirection" colspan="3">
							<input type="radio" value="0" name="direction" id="RADIOTYPE_1" checked><span id="rdForward" name="direction_desc">下行</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="1" name="direction" id="RADIOTYPE_2"><span id="rdBack" name="direction_desc">上行</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="2" name="direction" id="RADIOTYPE_3"><span id="double" name="direction_desc">双向拥堵</span>
						</td>
					</tr>
					<tr id="direction_nostandard_div" style="display: none;">
						<td class="tdtitle" bgcolor="#F0FFFF">
							方向描述
						</td>
						<td class="tdvalue" id="showFX" colspan="3">
							<input type="text" id="direction_nostandard"  name="direction" style="width: 557px;"> 
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵原因简述
						</td>
						<td id="tdReason" class="tdvalue" colspan="3">
							<!--Modify by xiayx 2012-3-21-->
							<!-- 将事故描述改为交通事故，但保留其值，以防程序中直接使用“事故”进行判断出错             -->
							<!-- 
							<input type="checkbox" value="事故" name="reason" id="reason_1">
							事故 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							-->
							<input type="checkbox" value="事故" name="reason" id="reason_1">
							交通事故 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<!--Modification finished-->
							<input type="checkbox" value="车流量大" name="reason" id="reason_5">
							车流量大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="修路" name="reason" id="reason_2">
							修路&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="故障车" name="reason" id="reason_3">
							故障车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="恶劣天气" name="reason" id="reason_4">
							恶劣天气&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="群体性事件" name="reason" id="reason_7">
							群体性事件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="其他" name="reason" id="reason_6">
							其他
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵级别<br/>
							（交通中断时需填本栏）
						</td>
						<%
						if (insrtOrUpdt.equals("1") || insrtOrUpdt.equals("2")) {
						%>
						<td class="tdvalue" colspan="1">
						<%
						}else {
						%>
						<td class="tdvalue" colspan="3">
						<%
						}
						%>
							<input type="radio" disabled >
							<span style="color: gray;">Ⅰ级</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" disabled >
							<span style="color: gray;">Ⅱ级</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" disabled >
							<span style="color: gray;">Ⅲ级</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" id="crowdTypeFlg" name="crowdTypeFlg" onclick="resetRadio()" >
							<span >交通中断</span>
						</td>
						<% 
						if (insrtOrUpdt.equals("1") || insrtOrUpdt.equals("2")) {
						%>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵状态
						</td>
						<td class="tdvalue" colspan="1">
							<% if (trfficeCrowState.equals("570001")) {
							%>
							<span style="color: red;">拥堵中</span>
							<% 
							}else if (trfficeCrowState.equals("570002")) {
							%>
							<span style="color: green;">已解除</span>
							<%
							} 
							%>
						</td>
						<%
						}
						%>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报单位
							<input id="reportUnit_" type="hidden" value="" />
						</td>
						<td class="tdvalue">
							<input type="text" id="REPORTUNITVALUE" class="textwidth"
								style="border: 1px #7B9EBD solid" value="<%=deptname%>">
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报人
						</td>
						<td class="tdvalue">
							<input type="text" id="REPORTPERSONVALUE" class="textwidth"
								style="border: 1px #7B9EBD solid" value="<%=personName%>">&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
					<!-- Modified by Liuwx 2011-08-05 -->
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报时间
						</td>
						<td id="reportTimeTd" class="tdvalue"  >
							<input type="text" id="REPORTTIME" class="textwidth" name="rtime"
								style="border: 1px #7B9EBD solid" value="<%=daytime%>" readonly>
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							审核人
						</td>
						<td class="tdvalue">
							<input type="text" id="apname" class="textwidth"
								style="border: 1px #7B9EBD solid" >
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF" id="tnReciveTime">
							接警时间
						</td>
						<td class="tdvalue" id="tvReciveTime"  >
							<input type="text" id="ReceiveTime" class="textwidth"
								style="border: 1px #7B9EBD solid" readonly onclick="SelectDateTime(this,0)">
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF" id="tnRelieveTime" >
							拥堵预计结束时间
						</td>
						<td class="tdvalue" id="tvRelieveTime" >
							<input type="text" id="CaseEndTime" class="textwidth" name="overTime" value=""
								style="border: 1px #7B9EBD solid" readonly onclick="SelectDateTime(this,0)">&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
					<tr id="attachTr">
                      	<td id="apathDescTd" class="tdtitle" align="center" bgcolor="#F0FFFF" >
                                                                                添加附件
                       </td>
                       <td id="fileRegion" class="tdvalue" colspan="3" >
                        	<script type="text/javascript">$("fileRegion").innerHTML=UDload.getForm(547) </script>  
                    	</td>
                    </tr>
					<tr style="display:none">
						<td class="tdtitle" bgcolor="#F0FFFF" style="text-align:center;" id="innerStr" >管辖机构</td>
						<td class="tdvalue" width="20%" class="currentLocation" id="daduiTdId"></td>
					</tr>
					<!--Modify by xiayx 2012-3-1-->
					<!--添加管制措施-页面元素           -->
					<tr id="trGzcs" height="30" >
                        <td class="tdtitle" align="center" bgcolor="#F0FFFF" >
                       	    管制措施
                       	 <br> （包括：是否有交警在现场疏导交通、是否在某出入口实施交通分流等措施）
                        </td>
                        <td class="tdvalue" colspan="3" >
                             <textarea id="gzcs" name="gzcs"  style="border: 1px #7B9EBD solid;height:50px;"  cols="67"></textarea><font size="1" color="red" style="margin-left: 1em;">※</font>
                        </td>
	                </tr>
					<!--Modification finished-->
					
					<!-- Modification finished -->
				    <tr id="trRemind" height="30" >
                        <td class="tdtitle" align="center" bgcolor="#F0FFFF">
<!--                        	      交警提示 -->
							交通安全提示
							<br>（内容包括：提示驾驶员注意控制车速、不要随意变更车道，听从交警指挥等）
                        </td>
                        <td class="tdvalue" colspan="3" id="remindInfo">
                             <textarea id="remindInfoValue" name="remind"  style="border: 1px #7B9EBD solid;height:50px;"  cols="67"></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font>
                        </td>
	                </tr>
	                <tr height="35" id="remindInfoAdd" style="display: none;">
                        <td class="tdtitle" align="center" bgcolor="#F0FFFF">
                       	      添加交警提示
                        </td>
                        <td class="tdvalue" colspan="3">
                             <textarea rows="3" id="remindInfoValue" name="remind" style="border: 1px #7B9EBD solid"  cols="72"></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font>
                        </td>
	                </tr>
	                <%--
	                Modified by Liuwx 2011-06-23
	                                                    采用另外一种方式实现：点击转发按钮弹出机构选择框，然后选择机构进行转发
	                --%>
	                
	                <tr id="dispatchTr" height="35" style="display:none">
                        <td class="tdtitle" align="center" bgcolor="#F0FFFF">
                       	        转发给相关单位
                       	  <input type="hidden" id="jgmcId" value="" />
                        </td>
                        <td id="dispatchTd" class="tdvalue" colspan="3" >
                        </td>
                    </tr>
                    <tr id="trPublish" >
                    	<td id="tdPublishDesc" class="tdtitle" align="center" bgcolor="#F0FFFF">
                       	       交通信息发布内容
                       	   <br>（此栏目即将发布的交通信息如不完整或错漏，请补充修改）
<!--                        	   <br/>（<a id="a_publishtemplate" href="#publishtemplate" name="publishtemplate">模板实例</a>） -->
                        </td>
                        <td id="tdPublishValue" class="tdvalue" colspan="3" >
                        	<textarea id="areaPublish" style="border: 1px #7B9EBD solid;"  rows="4"  cols="67" ></textarea><font id="fontPublish" size="1" color="red" style="margin-left: 1em;">※</font>
                        </td>
                    </tr>
                    <tr id="scoringTR" style="display: none;">
                        <td id="scoringDescTD" class="tdtitle" align="center" bgcolor="#F0FFFF">
                            采信计分
                        </td>
                        <td id="scoringValueTD" class="tdvalue" colspan="3">
                            <input type="radio" name="scoring" id="scoringRbt">计分
                            <input type="radio" name="scoring" id="noScoringRbt" style="margin-left: 10px;">不计分
                        </td>
                    </tr>
				</table>
			</fieldset>
		</div>
		<!--  
		<div style="text-align: center;width: 2%;height: 2%;"></div>
		-->
		<div style="width:95%;text-align: center;margin-top:7px;">
			<input class="btn" type="button" style="margin-right:30px; display : none"
				value=" 打 印 " id="close" onclick="printWord();">
			&nbsp;&nbsp;&nbsp;
			<%
			if (insrtOrUpdt.equals("2")) {
			%>
            <input class="btn" type="button" style="margin-right:30px;display: none;"
                   value=" 确 认 " id="scoringBtn" onclick="scoring(p_isScoring);">
			<input class="btn" type="button" value=" 关 闭 " id="close"
				onclick="window.close();">
			&nbsp;&nbsp;&nbsp;
			<%
			} else if (insrtOrUpdt.equals("")) {
			%>
			<input class="btn" type="button" value=" 报 送 " id="saveData_Accident"
				onclick="send()" style="margin-right:30px;">
			<input class="btn" type="button" value=" 取 消 " id="close" 
				onclick="resetPageValue();">
			<%
			} else {
			%>
			<input class="btn" type="button" style="margin-right:30px;"
				value=" 更 新 " id="saveData_Accident"
				onclick="if(checkParam())modify(<%=insrtOrUpdt%>);">
			<input class="btn" type="button" style="margin-right:30px;display: none;"
				value=" 解 除 " id="relieve"
				onclick="relieve()">
			<input class="btn" type="button" style="margin-right:30px;display: none;"
				value=" 转 发 " id="dispatch"
				onclick="dispatch()">
			&nbsp;&nbsp;&nbsp;
			<input class="btn" type="button" value=" 关 闭 " id="close"
				onclick="window.close();">
			&nbsp;&nbsp;&nbsp;

			<%
			}
			%>
		</div>
	</body>

</html>