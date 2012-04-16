<%@page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.data.sql.DBHandler"%>
<%@include file="../../Message.oni"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%
	//jsp,js,java,db,
	//alarmId,eventT,eventS,eventState
	//crowRoadId,crowRoadName,crowRoadDicId,crowRoadDicName,
	//kmS,kmE,timeS,timeE,
	//添加0,查看2,修改1
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? ""
			: request.getParameter("insrtOrUpdt");
	String alarmId = StringHelper.obj2str(request
			.getParameter("alarmId"), "");
	alarmId = alarmId.replace("'", "");
	System.out.println("alarmId:" + alarmId);
	String depttype = StringHelper.obj2str(request
			.getParameter("depttype"), "");
	String deptcodeStr = StringHelper.obj2str(request
			.getParameter("deptcode"), "");

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
	System.out.println("机构查询条件: " + jgbh);
	System.out.println("机构层次编码: " + ccbm);

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
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>交通拥堵信息</title>
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/tab.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
		<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
		<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/editTrfficeCrowd.js"></script>
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
				height: 35px;
				
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
				height: 35px;
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
</script>
	<body bgcolor="#FFFFFF" onload="getCrowdInfo(p_alarmId,p_jghb)">

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
							</span>：交通拥堵
						</td>
					</tr>
				</table>
	            <div style="font-size:12px;padding-left:1px;">&nbsp;&nbsp;◆ 用户可追加交警提示信息，点击修改按钮保存该信息！</div>
				<div id="showLength" style="color:red;text-align: center;display: none;">
				</div>
				<table class="table2" width="100%">
					<tr style="display: none">
						<td colspan="6">
							<input id="jgbh" type="text" value="<%=jgbh%>" readonly></input>
							<input id="ALARMID" type="text" value="<%=alarmId%>" readonly></input>
							<%--ALARMID--%>
							<input id="EVENTSOURCE" type="text" value="002022" readonly></input>
							<%--警情上报系统--%>
							<input id="EVENTTYPE" type="text" value="001002" readonly></input>
							<%--交通拥堵--%>
							<%--EVENTSTATE  事件状态--%>
							<input id="ALARMUNIT" type="text" value="<%=deptcode%>" readonly></input>
							<%--报警机构--%>
							<input id="TITLE" type="text" value=" 发生 交通拥堵" readonly></input>
							<%--标题--%>
							<input id="ALARMREGIONID" type="text" value="<%=deptcode%>"
								readonly></input>
							<%--报警辖区编号--%>
							<input id="ALARMREGION" type="text" value="<%=deptname%>"
								readonly></input>
							<%--报警辖区--%>
							<input id="REPORTUNIT" type="text" value="<%=deptcode%>" readonly></input>
							<%--填报单位--%>
							<input id="REPORTPERSON" type="text" value="<%=uname%>" readonly></input>
							<%--填报人--%>
							<input id="page" type="hidden" value="<%=insrtOrUpdt%>" readonly></input>
						</td>
					</tr>
					<tr >
						<td colspan="4" style="padding: 0px;">
							<table cellspacing="0" cellpadding="0" style="border-collapse: collapse;">
								<tr>
									<td class="tdtitle" bgcolor="#F0FFFF">
										<label id="roadLevelLabelId">道路等级</label>
									</td>
									<td class="tdvalue" >
										<select id="roadLevelValueId" class="textwidth" onchange="Road.roadLevel.onchange(this)">
											
										</select>
									</td>
									<td class="tdtitle" bgcolor="#F0FFFF">
										道路名称
									</td>
									<td class="tdvalue" id="alarmRoad_TrafficCrowd_td">
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							路段名称
						</td>
						<td class="tdvalue" style="padding-right: 15px;">
							<input type="text" id="ROADNAME" class="textwidth"
								style="border: 1px #7B9EBD solid" name="editinput">
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵起始时间
						</td>
						<td class="tdvalue">
							<input type="text" id="CaseHappenTime" class="textwidth"
								style="border: 1px #7B9EBD solid" name="editinput"
								value="<%=daytime%>" readonly onclick="SelectDateTime(this)">
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							起始里程
						</td>
						<td class="tdvalue">
							K
							<input type="text" id="KMVALUE" class="text" name="editinput"
								maxlength="6">
							+
							<input type="text" id="MVALUE" class="text" name="editinput"
								maxlength="3">
							米
							<%--						<input type="text" id="KMVALUE" class="textwidth" name="editinput" style="border: 1px #7B9EBD solid"  maxlength=6>(千米)--%>
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							终止里程
						</td>
						<td class="tdvalue">
							K
							<input type="text" id="EndKMVALUE" class="text" name="editinput"
								maxlength="6">
							+
							<input type="text" id="EndMVALUE" class="text" name="editinput"
								maxlength="3">
							米
							<%--					<input type="text" id="EndKMVALUE" class="textwidth" name="editinput" style="border: 1px #7B9EBD solid" maxlength=6>(千米)				--%>
						</td>
					</tr>
					<tr >
						<td class="tdtitle" bgcolor="#F0FFFF">
							方向
						</td>
						<td class="tdvalue" id="showFX" colspan="3">
							<input type="radio" value="0" name="RADIOTYPE" id="RADIOTYPE_1" checked><span id="rdForward">下行</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_2"><span id="rdBack">上行</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="2" name="RADIOTYPE" id="RADIOTYPE_3"><span id="double">双向拥堵</span>
						</td>
					</tr>
					
					<tr style="display:'none'">
						<td id="crowETDesc" class="tdtitle" bgcolor="#F0FFFF"
							style="white-space:nowrap">
							拥堵预计终止时间
						</td>
						<td class="tdvalue">
							<input type="text" id="CaseEndTime" value="<%=daytime%>"
								onKeyDown="if(event.keyCode==8){return false;};"
								style="border: 1px #7B9EBD solid" readonly
								onclick="SelectDateTime(this)" class="textwidth"
								name="editinput" readonly>
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵原因简述
						</td>
						<td class="tdvalue" colspan="3">
							<input type="checkbox" value="事故" name="reason" id="reason_1">
							事故 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="车流量大" name="reason" id="reason_5">
							车流量大&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="修路" name="reason" id="reason_2">
							修路&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="故障车" name="reason" id="reason_3">
							故障车&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="天气" name="reason" id="reason_4">
							天气&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="群众堵路" name="reason" id="reason_7">
							群众堵路&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" value="其他" name="reason" id="reason_6">
							其他
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵级别
						</td>
						<td class="tdvalue" colspan="3">
							<input type="radio" disabled >
							<span >Ⅰ级</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" disabled >
							<span >Ⅱ级</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" disabled >
							<span >Ⅲ级</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" id="crowdTypeFlg" name="crowdTypeFlg" onclick="resetRadio()" >
							<span >中断</span>&nbsp;&nbsp;&nbsp;&nbsp;
							（拥堵级别国家标准尚未确定，导致道路中断的可选择中断。）
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报单位
						</td>
						<td class="tdvalue">
							<input type="text" id="REPORTUNITVALUE" name="editinput"
								style="border: 1px #7B9EBD solid" value="<%=deptname%>">
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报人
						</td>
						<td class="tdvalue">
							<input type="text" id="REPORTPERSONVALUE" name="editinput"
								style="border: 1px #7B9EBD solid" value="<%=personName%>">
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报时间
						</td>
						<td class="tdvalue">
							<input type="text" id="REPORTTIME" name="editinput"
								style="border: 1px #7B9EBD solid" value="<%=daytime%>">
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF" style="text-align:center;" id="innerStr" >管辖机构</td>
						<td class="tdvalue" width="20%" class="currentLocation" id="daduiTdId">
							<script language="javascript">
							
							</script>																
						</td>

					</tr>
					
				    <tr height="35" >
                        <td align="center" bgcolor="#F0FFFF">
                       	      交警提示
                        </td>
                        <td class="tdvalue" colspan="3" id="remindInfo">
                             <textarea rows="3" id="remindInfoValue" name="remindInfoValue" style="border: 1px #7B9EBD solid"  cols="69"></textarea>
                        </td>
	                </tr>
                        
				</table>

			</fieldset>
		</div>
	<div style="text-align: center;width: 2%;height: 2%;"></div>
	<div style="width:95%;text-align: center;">
		<input class="btn" type="button" style="margin-right:70px;" value=" 打 印 " id="close" onclick="window.print();">&nbsp;&nbsp;&nbsp;
		<input class="btn" type="button" style="margin-right:70px;" value=" 修 改 " id="saveData_Accident" onclick="modify(<%=insrtOrUpdt%>);"><%--&nbsp;&nbsp;&nbsp;
		<input class="btn" type="button" style="margin-right:70px;" value=" 解 除 " id="saveData_Accident" onclick="doDeleteCrowd('<%=alarmId%>');">&nbsp;&nbsp;&nbsp;
		--%><input class="btn" type="button" value=" 关 闭 " id="close" onclick="window.close();">&nbsp;&nbsp;&nbsp;
	</div>
	
</body>

</html>