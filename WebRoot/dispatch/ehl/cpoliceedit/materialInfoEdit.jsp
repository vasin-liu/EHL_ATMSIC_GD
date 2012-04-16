<%@page import="com.appframe.data.sql.DBHandler"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="com.ehl.dispatch.cdispatch.action.MaterialInfoAction"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%@page import="com.ehl.sm.common.Constants"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("570601");
	String insrtOrUpdt = StringHelper.obj2str(request
			.getParameter("insrtOrUpdt"), "");
	String message = StringHelper.obj2str(request
			.getParameter("message"), "");
	String alarmId = StringHelper.obj2str(request
			.getParameter("alarmId"), "");
	String hideBtn = StringHelper.obj2str(request
			.getParameter("hideBtn"), "");
	String readContol = StringHelper.obj2str(request
			.getParameter("readContol"), "");
	String xbflg = StringHelper.obj2str(request.getParameter("xbflg"),
			"");

	String depttype = StringHelper.obj2str(request
			.getParameter("depttype"), "");
	String deptcodeStr = StringHelper.obj2str(request
			.getParameter("deptcode"), "");
	
	String updateFlag = StringHelper.obj2str(request
			.getParameter("updateFlag"), "");

	Hashtable prop = DispatchUtil.getCurrentUserData(request);

	//当前用户信息
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String uname = ""; //帐号
	String pid = ""; //办公电话
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	if (prop != null) {
		deptcode = (String) prop.get("BRANCHID");
		deptname = (String) prop.get("BRANCHNAME");
		pname = (String) prop.get("NAME");
		uname = (String) prop.get("USERNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
	}
	// new MaterialInfoAction().doReceivedMaterialInfo(alarmId, deptcode);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	Date date = new Date();
	String daytime = sdf.format(date);
	String daytime0 = sdf0.format(date);

	//Modified by Liuwx 2011-07-11
	String[] printStrObj = DepartmentManage.getDeptInfo(request, "1")
			.split(",");//获取单位信息串
	String printjgid = printStrObj[0];//单位编码
	Object result = DBHandler
			.getSingleResult("select replace(f_get_dept(jgid),'公安局交通警察','交警') from t_sys_department where jgid="
					+ printjgid);
	String printjgmc = StringHelper.obj2str(result, "");
	System.out.println(">>>>>>>>>>>>>>>>>机构名称:" + printjgmc
			+ "<<<<<<<<<<<<<<<<<<<<<");
	//String jgid="441905000000";//12位机构编码
	//Modification finished

	String jgbh;//总队(2位),支队(4位),大队(6位)
	if ("0000".equals(deptcode.substring(2, 6))) {
		jgbh = deptcode.substring(0, 2);
	} else if ("00".equals(deptcode.substring(4, 6))) {
		jgbh = deptcode.substring(0, 4);
	} else {
		jgbh = deptcode.substring(0, 6);
	}

	String alarmIdValue = "".equals(alarmId) ? (deptcode
			.substring(0, 6) + daytime0) : alarmId;

	String readStatic = (!"0".equals(insrtOrUpdt))
			&& (!"1".equals(insrtOrUpdt)) ? "readonly" : "";
	String readStatic1 = (!"0".equals(insrtOrUpdt))
			&& (!"1".equals(insrtOrUpdt)) ? "disabled" : "";

	if ("99".equals(readContol)) {
		readStatic = "readonly";
		readStatic1 = "disabled";
	}
	String personName = StringHelper.obj2str(DispatchUtil
			.getDutyPersonNameByDepId(deptcode), "");
	pname = personName;
	System.out.println("**************");
	System.out.println(pname);
	System.out.println("**************");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%=title%></title>
		<link type="text/css" rel="Stylesheet"
			href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet"
			href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet"
			href="../../../webgis/css/contents.css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<link rel="stylesheet" type="text/css"
			href="../../../sm/css/Global.css">
		<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
		<link rel="stylesheet" type="text/css"
			href="../../../base/css/style1/tab.css" />
		<link rel="stylesheet" type="text/css"
			href="../../../base/css/style1/font.css" />
		<link rel="stylesheet" type="text/css"
			href="../../../base/css/style1/link.css" />
		<link rel="stylesheet" type="text/css"
			href="../../../base/css/style1/form.css" />
		<link rel="stylesheet" type="text/css"
			href="../../../base/css/style1/Popup.css" />
		<script type="text/javascript"
			src="../../../base/js/list/FillListBox.js">
</script>
		<script type="text/javascript"
			src="../../../webgis/script/map/Util.js">
</script>
		<script type="text/javascript"
			src="../../../webgis/script/map/LoadLibFile.js">
</script>
		<script type="text/javascript" src="../../../base/js/global.js">
</script>
		<script type="text/javascript"
			src="../../../base/js/calendar/CalendarDateTime.js">
</script>
		<script type="text/javascript" src="../../../base/js/tree/tree.js">
</script>
		<script type="text/javascript"
			src="../../js/ceditpolice/DepartmentSelect.js">
</script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js">
</script>
		<script type="text/javascript" src="../../../base/js/prototype.js">
</script>
		<script type="text/javascript"
			src="../../js/ceditpolice/materialInfo.js">
</script>
		<script type="text/javascript" src="../../js/ccommon/RoadDept.js">
</script>
		<script type="text/javascript" src="../../js/ccommon/Flow.js">
</script>
		<script type="text/javascript" src="../../js/ccommon/content.js">
</script>
<script type="text/javascript" src="../../js/ccommon/FileUpDownload.js"></script>

		<style type="text/css">
.cb_text {
	width: 160px;
}

.flow_text {
	border: none;
	border-bottom: 1 solid #a5d3ef;
}

.tdtop_a {
	border-top: 1 solid #000000;
}

.tdtitle_a {
	border-top: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	border-right: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: right;
}

.tdvalue_a {
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	border-right: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: left;
}

.table_a {
	border-top: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
}

.tdtitle_b {
	border-top: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: left;
}

.tdvalue_b {
	border-left: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: left;
}

.tdtitle {
	border-top: 0 solid #000000;
	border-right: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: right;
	width: 9%;
}

.tdtitle1 {
	border-top: 0 solid #000000;
	border-right: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	border-left: 2px solid #106ead;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: right;
	width: 9%;
}

.tdvalue {
	border-top: 0 solid #000000;
	border-right: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: left;
	width: 16%;
}

.tdvalue1 {
	border-top: 0 solid #000000;
	border-right: 2px solid #106ead;
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: left;
	width: 16%;
}

.table2 {
	background: #ffffff;
	border-top: 1 solid #a5d3ef;
	border-right: 0 solid #000000;
	border-bottom: 1 solid #a5d3ef;
	border-left: 0 solid #000000;
	border-collapse: collapse;
	font-size: 11px;
	text-align: center;
}

.ltitle {
	background-color: #106ead;
	font-size: 12px;
	font-weight: bold;
	color: #000;
	text-align: left;
	padding-left: 15px;
	text-decoration: none;
	display: block;
	top: 0px;
	left: 0px;
	position: relative;
}

.ltitle span {
	display: block;
	top: -1px;
	left: 14px;
	position: absolute;
	color: #fff;
	cursor: pointer;
}

td {
	line-height: 23px;
}

/*宽高行高背景不显示超过对象尺寸的内容*/
.lsearch {
	width: 82px;
	height: 22px;
	line-height: 22px;
	background: url(../../images/dispatch/btn.png) no-repeat;
	overflow: hidden;
}

/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
.lsearch a {
	display: block;
	height: 22px;
	background: url(../../images/dispatch/btn.png) center;
	text-decoration: none;
	line-height: 22px;
	overflow: hidden;
}

/*高度固定背景行高*/
.lsearch a:hover {
	height: 22px;
	background: url(../../images/dispatch/btn.png) center center;
	line-height: 22px;
}

/*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
.lsearch .lbl {
	display: block;
	width: 79px;
	height: 15px;
	padding-left: 3px;
	padding-top: 0px;
	margin: 0 auto;
	text-align: center;
	color: #ffffff;
	cursor: pointer;
}

/*颜色滤镜效果*/
.lsearch a:hover .lbl {
	color: #dae76b;
	filter: glow(color = #ffffff, strength = 1);
}

.tableinput33 {
	width: 160px;
	border: none;
	border-bottom: 1px solid #000;
	font-size: 12px;
	color: #FF0000;
	text-align: center;
}

.tableinput {
	border: none;
	border-bottom: 1px solid #000;
	font-size: 12px;
	color: #FF0000;
	text-align: center;
}

/*宽高行高背景不显示超过对象尺寸的内容*/
.lbackBt {
	width: 112px;
	height: 22px;
	line-height: 22px;
	background: url(../../images/dispatch/backbt.png) no-repeat;
	overflow: hidden;
}

/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
.lbackBt a {
	display: block;
	height: 22px;
	background: url(../../images/dispatch/backbt.png) center;
	text-decoration: none;
	line-height: 22px;
	overflow: hidden;
}

/*高度固定背景行高*/
.lbackBt a:hover {
	height: 22px;
	background: url(../../images/dispatch/backbt.png) center center;
	line-height: 22px;
}

/*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
.lbackBt .lbl {
	display: block;
	width: 112px;
	height: 15px;
	padding-top: 0px;
	margin: 0 auto;
	text-align: center;
	color: #ffffff;
	cursor: pointer;
}

/*颜色滤镜效果*/
.lbackBt a:hover .lbl {
	color: #dae76b;
	filter: glow(color = #ffffff, strength = 1);
}

.table3 {
	border-top: 0 solid #106ead;
	border-right: 0 solid #106ead;
	border-bottom: 0 solid #106ead;
	border-left: 0 solid #106ead;
	border-collapse: collapse;
	font-size: 11px;
}

.table4 {
	border-top: 1 solid green;
	border-right: 1 solid green;
	border-bottom: 1 solid green;
	border-left: 1 solid green;
	border-collapse: collapse;
	font-size: 11px;
}

/*文本框变下划线*/
.textline {
	font: 14px Tahoma, Verdana;
	border: 0;
	border-bottom: 0 solid black;
	background: ;
	text-align: center;
	text-color: red;
	/*padding-left:10px;*/
	width: 190px;
}

/*文本框变下划线*/
.text1 {
	font: 14px Tahoma, Verdana;
	border: 0;
	border-bottom: 1 solid black;
	background: ;
	text-align: center;
	/*padding-left:10px;*/
	width: 62px;
}
</style>
		<script type="text/javascript">
function hideButton() {
	var isHide = $('hideBtn').value;
	if (isHide == 'false') {
		$('dispatch').style.display = "inline";
	}
}
</script>
	</head>
	<body
		onload="getMaterialInfo('<%=alarmId%>','<%=xbflg%>');hideButton();">
		<div style="text-align: center; width: 100%; height: 100%;">
			<fieldset style="width: 99%; border: 1px solid #a5d3ef"
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
							<span class="currentLocationBold">您当前的位置</span>：<%=title%>
							<%--<font style="font-size: 10pt;"> （编号：<input type="text"  class="textline" id="lookBh" value="<%=alarmIdValue%>" />）</font>
						--%>
						</td>
					</tr>
				</table>
				<%--<legend style="border:0px;font-size: 12pt;">
					事故信息<font style="font-size: 10pt;"> （编号：<input type="text"  class="textline" id="lookBh" value="<%=alarmIdValue%>" />）</font>
				</legend>
				--%>
				<table class="table3" width="100%" border="1" id="block"
					borderColor='#a5d1ec'>
					<tbody id="flowbox">
						<tr style="display: none">
							<td>
								<input type="hidden" id="updateFlag" value="<%=updateFlag%>" />
								<input type="hidden" id="hideBtn" value="<%=hideBtn%>" />
								<input type="hidden" id="xbflg" value="<%=xbflg%>" />
								<input type="hidden" id="jgbh" value="<%=jgbh%>" />
								<input type="hidden" id="jgid" value="<%=deptcode%>" />
								<input type="hidden" id="dname" value="<%=deptname.replace(Constant.GAJJTJC,Constant.JJ)%>" />
								<input type="hidden" id="zbrName" value="<%=pname%>" />
								<input type="hidden" id="printjgmc" value="<%=printjgmc%>" />
								<input type="hidden" id="czzt1" value="" />
								<input type="hidden" id="czzt2" value="" />
								<input type="hidden" id="insrtOrUpdt" value="<%=insrtOrUpdt%>" />
								<input type="hidden" id="daytime" value="<%=daytime%>" />
								<input type="hidden" id="message" value="<%=message%>" />
								<input id="ALARMID" type="text" value="<%=alarmId%>" readonly></input>
								<%--ALARMID--%>
								<input id="EVENTSOURCE" type="text" value="002022" readonly></input>
								<%--警情上报系统--%>
								<input id="EVENTTYPE" type="text" value="001024" readonly></input>
								<%--重大警情--%>
								<%--EVENTSTATE  事件状态--%>
								<input id="ALARMUNIT" type="text" value=<%=deptcode%> readonly></input>
								<%--报警机构--%>
								<input id="ALARMREGIONID" type="text" value=<%=deptcode%>
									readonly></input>
								<%--报警辖区编号--%>
								<input id="ALARMREGION" type="text" value=<%=deptname%> readonly></input>
								<%--报警辖区--%>
								<input id="REPORTUNIT" type="text" value=<%=deptcode%> readonly></input>
								<%--填报单位--%>
								<input id="REPORTPERSONUN" type="text" value=<%=uname%> readonly></input>
								<%--填报人--%>
								<input id="REPORTPERSONXM" type="text" value='<%=pname%>'
									readonly></input>
								<%--填报人--%>
								<input id="REPORTPERSONID" type="text" value=<%=pid%> readonly></input>
								<div style="height: 100%" id="flow1"></div>
								<%--填报人--%>
							</td>
						</tr>
						<tr style="display: none">
							<td algin="center" width="15%">
								事件编号
							</td>
							<td>
								<input type="text" id="ALARMIDVALUE" readonly
									value="<%=alarmIdValue%>" size="25" />
							</td>
							<td>
								审批人
							</td>
							<td>
								<input class="text" type="text" <%=readStatic%>
									id="RESPONSIBLEPERSON" />
							</td>
						</tr>

						<%
							String rw = "";
							if (!(insrtOrUpdt.equals("1") && !deptcode.substring(4, 6).equals(
									"00"))) {//非大队处理界面一律不准修改
								rw = "readonly";
							}
						%>
						<tr height="35">
							<td align="center"  width="15%" bgcolor="#F0FFFF">
								事故标题
							</td>
							<td colspan="3">
								<input type="text" <%=rw%> maxlength=100
									style="border: 1px #7B9EBD solid;width=619px" id="TITLE">
							</td>
							   
							<!--<td align="center" width="15%" bgcolor="#F0FFFF">
								<span style="color: gray">事故编号</span>
							</td>
							<td algin="right" id="alarmDept_TrafficRestrain">
								<input type="text" id="glAccNum" name="glAccNum" <%=rw%>
									style="border: 1px #7B9EBD solid" height="30" size="25" />
								<font color='gray'> （指事故系统编号）</font>
							</td>
						--></tr>
						<tr height="35">
						<td align="center"  width="15%" bgcolor="#F0FFFF">
                             	 接警时间
                            </td>
                            <td>
                                <input type="text" maxlength=100 style="border: 1px #7B9EBD solid;" size="25" id="receivetime" readonly onClick="SelectDateTime(this)">
                            </td>
						
							<td align="center" bgcolor="#F0FFFF">
								填报时间
							</td>
							<td algin="right">
								<input type="text" id="REPORTTIME" readonly
									style="border: 1px #7B9EBD solid" value="<%=daytime%>"
									size="25" />
							</td>

						</tr>

						<tr height="35">
						<td align="center" width="15%" bgcolor="#F0FFFF">
								填报单位
							</td>
							<td algin="right" id="alarmDept_TrafficRestrain">
								<input type="hidden" id="REPORTUNITIDVALUE" value=<%=deptcode%> />
								<input type="hidden" id="REPORTUNITVALUE" value=<%=deptname%> />
								<input type="hidden" id="reportunitId" name="reportunitId" />
								<input type="text" id="reportunitName"
									style="border: 1px #7B9EBD solid" name="reportunitName"
									value=<%=deptname%> readonly height="30" size="25" />
							</td>
							<td align="center" width="15%" bgcolor="#F0FFFF">
								管辖大队
							</td>
							<td algin="right">
								<input type="text" id="sobject_single_div" name="sobject_single_div" style="border: 1px #7B9EBD solid;width:183px;" readonly height="30" />
							</td>
						</tr>
						<tr height="35">
							<td align="center" bgcolor="#F0FFFF">
								填 报 人
							</td>
							<td algin="right">
								<input type="hidden" id="REPORTPERSONVALUE" value=<%=pid%> />
								<input height="25" type="text" id="reportPersonName"
									style="border: 1px #7B9EBD solid" name="reportPersonName"
									<%=rw%> size="25" />
							</td>

							<td align="center" bgcolor="#F0FFFF">
								联系电话
							</td>
							<td algin="right">
								<input type="text" maxlength=20 id="TELENUM" name="TELENUM"
									<%=rw%> height="25" style="border: 1px #7B9EBD solid" size="25" />
							</td>
						</tr>

						<tr height="35">
							<td align="center" bgcolor="#F0FFFF">
								事故时间
							</td>
							<td algin="right">
								<input type="text" id="caseHappenTime" size="25"
									<%=rw.replace("readonly", "disabled")%>
									onClick="SelectDateTime(this)"
									style="border: 1px #7B9EBD solid" />
							</td>
							<td align="center" bgcolor="#F0FFFF">
								事故地点
							</td>
							<td algin="right">
								<input height="25" type="text" id="caseHappenPlace" <%=rw%>
									size="25" style="border: 1px #7B9EBD solid"  onmouseover="mouseover(this)" onmouseout="mouseout(this)"/>
								<img id="roadselectImg" alt="选择道路列表"
									src="../../images/button/search.bmp"
									style="display: <%=insrtOrUpdt.equals("2") ? "none" : "inline"%>;cursor:hand;width:16px;height:16px;"
									onclick="RoadSelect.show('roadselect','caseHappenPlace')">
								<div id="roadselect"
									style="position: absolute; top: 0px; left: 0px; width: 250px; height: 120px; border: 1px solid green; background-color: white; display: none;">
									<div id="rstitle"
										style="display: block; height: 20px; background-color: green; text-align: right; padding: 0; margin: 0;">
										<span
											style="margin: 0 8 0 0; padding: 0; font-size: 12px; font-weight: bolder; cursor: hand;"
											onclick="RoadSelect.close('roadselect');">X</span>
									</div>
									<div style="margin: 10 10 5 10">
										<label style="">
											道路等级：
										</label>
										<select id="roadLevelValueId" style="width: 80px;"
											onchange="Road.roadLevel.onchange(this)"></select>
									</div>
									<div style="margin: 5px 10px;">
										<label>
											道路名称：
										</label>
										<div id="alarmRoad_TrafficCrowd_td" style="display: inline;"></div>
									</div>
									<div style="margin: 5px 10px;">
										<label>
											事故位置：
										</label>
										K
										<input id="KMVALUE" type="text"
											style="border: 0; border-bottom: 1px solid black; width: 70px; text-align: center;"
											value="" maxlength="6">
										+
										<input id="MVALUE" type="text"
											style="border: 0; border-bottom: 1px solid black; width: 60px; text-align: center;"
											value="0" maxlength="3">
										米
									</div>
									<div style="margin: 5 10; text-align: center;">
										<input type="button" value="确定" style="margin: 0 10 0 0"
											onclick="setValue222();RoadSelect.close('roadselect');" />
										<input type="button" value="取消" style="margin: 0 0 0 10"
											onclick="RoadSelect.close('roadselect');" />
									</div>
								</div>
							</td>
						</tr>
						<!-- 
						<tr height="35" style="display: none;">
	                		<td colspan="4" style="border-width: 0px;padding: 0px; " >
	                	    	<input id="readStatic" type="hidden" value="<%=readStatic%>" />
	                			<input id="readStatic1" type="hidden" value="<%=readStatic1%>" />
								<table border="1" cellspacing="0" cellpadding="0"  style="border-width: 0px; margin: 0px;padding:0px;height: 100%;width:100%; border-collapse: separate">
									<tr>
										<td style="text-align: center; border: #a5d1ec solid; border-width: 0 1; background-color: #F0FFFF;width:13%">
											<label id="roadLevelLabelId">道路等级</label>
										</td>
										<td style="text-align: left; border-width: 0;width:18%">
											<select id="roadLevelValueId" style="width:100px;" <%=rw.replace("readonly", "disabled")%>  onchange="Road.roadLevel.onchange(this)" ></select>
										</td>
										<td style="text-align: center; border: #a5d1ec solid; border-width: 0 1; background-color: #F0FFFF;width:10%">
											道路名称
										</td>
										<td  id="alarmRoad_TrafficCrowd_td" style="text-align: left; border-width: 0;width:18%" ></td>
										<td style="text-align: center; border: #a5d1ec solid; border-width: 0 1; background-color: #F0FFFF;width:10%" >事故位置</td>
										<td  style="text-align: left; border-width: 0;width:20%;white-space: nowrap">
											K<input type="text" id="KMVALUE" class="text1" name="editinput" style="width: 70px;" onBlur="setValue222()" <%=rw%> maxlength="6">
											+<input type="text" id="MVALUE" class="text1" name="editinput"  style="width: 70px;" onBlur="setValue222()" <%=rw%> maxlength="3" >米
									    </td>
									</tr>
								</table>
							</td>
						</tr>
						 -->
						<%
							String rwZd = rw;
							if (deptcode.substring(4, 6).equals("00")
									&& !deptcode.substring(2, 4).equals("00")
									&& insrtOrUpdt.equals("1")) {//支队处置界面
								rwZd = "";
							}
						%>
						<tr height="35">
							<td align="center" bgcolor="#F0FFFF">
								死亡人数
							</td>
							<td algin="right" id="alarmDept_TrafficRestrain">
								<input height="25" type="text" id="DEATHPERSONCOUNT" <%=rwZd%>
									style="border: 1px #7B9EBD solid" size="10" />
								<font color='black'> （数字）</font>
							</td>
							<td align="center" bgcolor="#F0FFFF">
								受伤人数
							</td>
							<td algin="right">
								<input height="25" type="text" id="BRUISEPERSONCOUNT" <%=rwZd%>
									style="border: 1px #7B9EBD solid" size="10" />
								<font color='black'> （数字）</font>
							</td>
						</tr>
						<%
							String rwZdDis = rwZd.replace("readonly", "disabled");
						%>
						<div id="fileRegion">
							<!-- 杜（追加） -->
							<form id="alarmFileUploadForm" name="alarmFileUploadForm"
								action="dispatch.alarmInfo.uploadFile.d"
								enctype="multipart/form-data" method="post" target="temp">
								<tr height="35">
									<%--
							    <td align="center" bgcolor="#F0FFFF">附件描述 </td>
								<td align="left" id="fileInfoList" >
									<input type="text" id="fileInfo0" name="fileInfo0" size="25" />
								</td>
									--%>
									<input type="hidden" id="insertYwid" name="insertYwid"
										value="<%=alarmIdValue%>" />
									<td align="center" id="resetFileId" bgcolor="#F0FFFF">
										附件名称
									</td>
									<td align='left' id="fileList" colspan=3>
										<input id="mfile0" type="file" <%=rwZdDis%> name="mfile0"
											size="80" style="border: 1px #7B9EBD solid" />
									</td>
								</tr>
							</form>
						</div>

						<tr height="35">
							<td align="center" bgcolor="#F0FFFF">
								情况描述
							</td>
							<td algin="right" colspan="3" id="trafficInfo">

							</td>
						</tr>


						<tr>
							<td colspan="1" align="center" bgcolor="#F0FFFF">
								事故性质
								<br />
								（必选项，可多选）
							</td>
							<td colspan="3">
								<input type="checkbox" <%=rwZdDis%> id="ISTHREEUP" name="eType" />
								<span class="cb_text">一次死亡3人以上交通事故</span>
								<input type="checkbox" <%=rwZdDis%> id="ISBUS" name="eType" />
								<span class="cb_text">营运大客车事故</span>
								<input type="checkbox" <%=rwZdDis%> id="ISSCHOOLBUS"
									name="eType" />
								<span>校车事故</span>
								<br />
								<input type="checkbox" <%=rwZdDis%> id="ISDANAGERCAR"
									name="eType" />
								<span class="cb_text">危化品运输车交通事故</span>
								<input type="checkbox" <%=rwZdDis%> id="ISFOREIGNAFFAIRS"
									name="eType" />
								<span class="cb_text">涉港澳台及涉外事故</span>
								<input type="checkbox" <%=rwZdDis%> id="ISPOLICE" name="eType" />
								<span class="cb_text">涉警交通事故</span>
								<br />
								<input type="checkbox" <%=rwZdDis%> id="ISARMYACC" name="eType" />
								<span class="cb_text">涉军交通事故</span>
								<input id="ISCONGESTION" disabled="disabled" <%=rwZdDis%>
									name="eType" type="checkbox" />
								<span>造成严重拥堵</span>
								<input id="ISOthersItem" <%=rwZdDis%> name="eType"
									type="checkbox" style="margin-left: 88px;" />
								<span>其他</span>
								<input id="ISMASSESCASE" <%=rwZdDis%> name="eType"
									type="checkbox" style="margin-left: 88px; display: none;" />
								<br />
							</td>
						</tr>
						
						<tr id="ddshr" style="display : inline;">
						    <td align="center" bgcolor="#F0FFFF">
						                              大队审核人
                            </td>
                            <td colspan="3" id="ddapprover">
                                <!--<input type="text" maxlength=100 style="border: 1px #7B9EBD solid;width:605px;"  id="ddapprover">
                            --></td>
                         </tr>

						<tr style="display: none">
							<td>
								情况描述

							</td>
							

							<td align="left" colspan="6">
								<span style="width: 150px">接警时间<input class="text"
										type="text" style="width: 100px;" id="ALARMTIME" readonly
										<%=(("0".equals(insrtOrUpdt) || "1".equals(insrtOrUpdt)) && (!"99"
							.equals(readContol))) ? "onClick=\"SelectDateTime(this)\""
							: ""%> />
								</span>
								<span style="width: 180px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;接警人<input
										class="text" type="text" <%=readStatic%> maxlength=20
										style="width: 100px;" id="RECEIVEPERSON"> </span>
								<span style="width: 100px">&nbsp;&nbsp;死亡人数<input
										class="text" type="text" <%=readStatic%> style="width: 40px;"
										maxlength=10 id="DEATHPERSONCOUNT"
										onkeypress="checkNum(this,event)"> </span>
								<span style="width: 100px">&nbsp;&nbsp;&nbsp;&nbsp;受伤人数<input
										class="text" type="text" <%=readStatic%> style="width: 40px;"
										maxlength=10 id="BRUISEPERSONCOUNT"
										onkeypress="checkNum(this,event)"> </span>
								<span style="display: none; width: 100px">&nbsp;&nbsp;&nbsp;&nbsp;失踪人数<input
										class="text" type="text" <%=readStatic%> style="width: 40px;"
										maxlength=10 id="MISSINGPERSONCOUNT"
										onkeypress="checkNum(this,event)"> </span>
								<div style="display: none">

									<span
										style="width: 100%; font-weight: 900; text-align: center; margin-right: 150px;">详细描述</span>
									<div>
										<span class="lsearch" style="margin-right: 20px;"> <a
											href="#" onclick="showDetailInfo();"><span class="lbl">查看范文</span>
										</a> </span>
										<span id="bt10" class="lsearch" style="margin-right: 20px;">
											<a href="#" onclick="writeAble();"><span class="lbl">编辑补充</span>
										</a> </span>
									</div>
								</div>

							</td>
						</tr>
						
						<tr id='flow2_box' style="display: none">
							<td align="center" colspan=1 bgcolor="#F0FFFF">
								支队处警情况
							</td>
							<td colspan=3 id="flow2">
							</td>
						</tr>
						
						<tr id="zdshr" style="display : none;">
						    <td align="center" bgcolor="#F0FFFF">
						         	    支队审核人
                            </td>
                            <td colspan="3" id="zdapprover"></td>
                         </tr>
						
						<tr id='showCheckZd' style="display: none">
							<td colspan=4 width="100%" height="100%">
								<table id="zdMessage" class="table3" width="100%" border="0">
									<tr>
										<td bgcolor="#AFEEEE" colspan=4 align="center"
											style="border-bottom: 1 solid #a5d3ef;">
											<font size="+1" color="">总队填写信息</font>
										</td>
									</tr>
									<tr id='flow7_box' style="display: none;">
										<td align="center" colspan=1 bgcolor="#F0FFFF"
											style="width: 10%; border-bottom: 1 solid #a5d3ef; border-right: 1 solid #a5d3ef;">
											领导批示
										</td>
										<td colspan=3 id="flow7"
											style="border-bottom: 1 solid #a5d3ef;">
										</td>
									</tr>
									<tr id='flow8_box' style="display: none">
										<td align="center" colspan=1 bgcolor="#F0FFFF"
											style="width: 15.5%; border-bottom: 1 solid #a5d3ef; border-right: 1 solid #a5d3ef;">
											办理情况
										</td>
										<td colspan=3 id="flow8"
											style="border-bottom: 1 solid #a5d3ef;">
										</td>
									</tr>

									<tr height="35" id='chooseCheck' style="display: none">
										<td align="center" colspan=1 bgcolor="#F0FFFF"
											style="width: 15.5%; border-bottom: 1 solid #a5d3ef; border-right: 1 solid #a5d3ef;">
											是否下发支队
										</td>
										<td colspan=3 id="chooseCheckBox"
											style="border-bottom: 1 solid #a5d3ef;">
											<input type="radio" value="1" name="RADIOTYPE" id="doBox"
												onclick="showFlow6Box();">
											是
											<input type="radio" value="2" name="RADIOTYPE"
												id="RADIOTYPE_2" onclick="showFlow6Box();" checked>
											否
											<%--<input type="checkbox" id="doBox" name="doBox" onclick="showFlow6Box();" />--%>
										</td>
									</tr>


									<tr id='flow6_box' style="display: none">
										<td align="center" colspan=1 bgcolor="#F0FFFF"
											style="width: 15%; border-right: 1 solid #a5d3ef;">
											省厅批示意见
										</td>
										<td colspan=3 id="flow6"
											style="">
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
						<tr id="zodshr" style="display : none;">
						    <td align="center" bgcolor="#F0FFFF">
						                              总队审核人
                            </td>
                            <td colspan="3" id="zodapprover">
                                </td>
                         </tr>
						
						<tr id='adstateTr' style="display: none">
							<td align="center" colspan=1 bgcolor="#F0FFFF" 
								style="border-right: 1 solid #a5d3ef;">
								流转记录
							</td>
							<td id="adstateTd" colspan="3"></td>
						</tr>
						<tr id='xbTr' style="display: none">
							<td align="center" colspan=1 bgcolor="#E0FFFF">
								续报情况
							</td>
							<td colspan=3 id="InfoRegionXB">
							</td>
						</tr>
					</tbody>
				</table>

			</fieldset>
			<div style="text-align: center; width: 2%; height: 2%;">
			</div>
			<div
				style="text-align: center; height: 25px; border-bottom: 0 solid #a5d3ef;"
				id="buttonVegion">
				<iframe id="target_upload" name="target_upload" src=""
					style="display: none"></iframe>
				<!-- 
					<div id="bt4" class="search" style="float:right;margin-right:180px;">
						<a href="#" onclick="addFbox()"><span class="lbl">添加附件</span></a>
					</div>
					-->
				<span id="bt6" class="lsearch" style="margin-right: 70px;"> <a
					href="#打印" name="打印" onclick="printWord('<%=daytime%>');"><span
						class="lbl">打印</span> </a> </span>
				<span id="bt7" class="lsearch" style="margin-right: 70px;"> <a
					href="#保存" name="保存"
					onclick="setButtonPressed($('bt7').innerText);showDiv('<%=daytime%>','2')"><span
						class="lbl">保存</span> </a> </span>
				<span id="bt1" class="lsearch" style="margin-right: 70px;"> <a
					href="#保存草稿" name="保存草稿" onclick="showDiv('<%=daytime%>','0')"><span
						class="lbl">保存草稿</span> </a> </span>
				
				<%
					if(jgbh.length() == 4) {
				%>
				<span id="bt2" class="lsearch" style="margin-right: 70px;"> <a
					href="#报送" name="报送" onclick="materialInfoTree()"><span
						class="lbl">报送</span> </a> </span>
				<%
					} else {
				%>
				<span id="bt2" class="lsearch" style="margin-right: 70px;"> <a
					href="#报送" name="报送" onclick="showDiv('<%=daytime%>','1')"><span
						class="lbl">报送</span> </a> </span>
				<%} %>
				
				
				<!-- 保存转发机构 -->
				<input id="adcode" type="hidden" value="" />
				<span id="dispatch" class="lsearch"
					style="margin-right: 70px; display: none;"> <a href="#转发"
					name="转发"
					onclick="setButtonPressed($('dispatch').innerText);dispatch();"><span
						class="lbl">转发</span> </a> </span>

				<span id="bt3" class="lsearch" style="margin-right: 50px;"> <a
					href="#" onclick="window.close();"><span class="lbl">关闭</span>
				</a> </span>
				<span id="bt4" class="lsearch" style="display: none"
					style="margin-right:70px;"> <a href="#查看续报" name="查看续报"
					onclick="showXbInfo();"><span class="lbl">查看续报</span> </a> </span>
				<!-- <span id="bt5" class="lsearch" style="margin-right:70px;"> <a
						href="#" onclick="PopupEdit('<%=alarmId%>');"><span class="lbl">编辑续报</span>
					</a> </span>-->

				<span id="bt8" class="lsearch" style="display: none"
					style="margin-right:70px;"> <a href="#支队签收" name="支队签收"
					onclick="showDiv('<%=daytime%>','3');"><span class="lbl">支队签收</span>
				</a> </span>
				<%--<span id="bt9" class="lsearch"  style="display:none" style="margin-right:70px;"> <a
						href="#" onclick="showDiv('<%=daytime%>','4');"><span class="lbl">总队签收</span>
					</a> </span>
					--%>
			</div>
		</div>
	</body>
</html>

