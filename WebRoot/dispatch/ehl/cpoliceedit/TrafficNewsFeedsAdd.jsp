<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.data.sql.DBHandler"%>
<%@include file="../../Message.oni"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%
	
	String title = FlowUtil.getFuncText("930201");//电台接报拥堵信息	
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
	personName = StringHelper.obj2str(DispatchUtil.getDutyPersonNameByDepId(jgid),"");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%=title%></title>
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
		<script type="text/javascript" src="../../js/ceditpolice/TrafficNewsFeeds.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ccommon/RoadDept.js"></script>
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
	<body bgcolor="#FFFFFF" onload="pageInit()" onclick="">

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
							</span>：<%=title%>
						</td>
					</tr>
				</table>
				<span id="deptarea" style="text-align: center; color:red;display: none;"></span>
				<form>
				<table class="table2" width="100%">
					<input type="hidden" id="page" value = ''>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							<label id="roadLevelDesc">拥堵道路等级</label>
						</td>
						<td class="tdvalue" colspan="3">
							<select id="roadLevel" class="textwidth" onchange="Road.roadLevel.onchange(this)"></select>&nbsp;&nbsp;<font size='1' color='red'>※</font>
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵道路名称
						</td>
						<td class="tdvalue" id="alarmRoad_TrafficCrowd_td"></td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵路段
						</td>
						<td class="tdvalue">
							<input type="text" id="ldmc" class="textwidth" 
								style="border: 1px #7B9EBD solid" name="editinput">&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵起始里程
						</td>
						<td class="tdvalue">
							K
							<input type="text" id="qslc" class="text" name="editinput"
								maxlength="6">
							+
							<input type="text" id="qslcm" class="text" name="editinput"
								maxlength="3"  >
							米
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵终止里程
						</td>
						<td class="tdvalue">
							K
							<input type="text" id="zzlc" class="text" name="editinput"
								maxlength="6">
							+
							<input type="text" id="zzlcm" class="text" name="editinput"
								maxlength="3" >
							米
						</td>
					</tr>
					<tr >
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵方向
							<input id="ldfx" type="hidden" value="0">
						</td>
						<td class="tdvalue" id="showFX" colspan="3">
							<input type="radio" value="0" name="RADIOTYPE" id="RADIOTYPE_1" checked onclick="$('ldfx').value = this.value"><span id="rdForward">下行</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_2" onclick="$('ldfx').value = this.value"><span id="rdBack">上行</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="双向拥堵" name="RADIOTYPE" id="RADIOTYPE_3" onclick="$('ldfx').value = this.value"><span id="double">双向拥堵</span>
						</td>
					</tr>
					
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							管辖机构
						</td>
						<td class="tdvalue" id="daduiTdId">
							<input type="text" style="width:140;" style="border: 1px #7B9EBD solid" id="gxdd" readonly ondblclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','gxdd','170','170')" />
							<input id="gxddCode" type="hidden" />
							<img src="../../images/button/search.bmp" alt="查找管辖大队" style="cursor:hand;width:16px;height:16px;" onclick="RoadDept.show('','','','dlmc','roadLevel','qslc','zzlc','ssld','gxddCode','gxdd',0)"/>
							<img src="../../images/popup/btnselect.gif" alt="全部机构列表" style="cursor:hand;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','gxdd','202','170')">&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							起始时间
						</td>
						<td class="tdvalue">
							<input type="text" id="qssj" class="textwidth"
								style="border: 1px #7B9EBD solid" name="editinput"
								value="" readonly onclick="SelectDateTime(this)">
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							路况
						</td>
						<td class="tdvalue" >
							<input type="text" id="lk" class="textwidth" 
								style="border: 1px #7B9EBD solid" name="editinput" value="拥堵" >
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">拥堵原因简述</td>
						<td class="tdvalue" colspan="1">
							<select id="lkyy" class="textwidth" >
								<option value="其他" selected>其他
								<option value="事故">事故
								<option value="车流量大">车流量大
								<option value="修路">修路
								<option value="故障车">故障车
								<option value="天气">天气
								<option value="群众堵路">群众堵路
							</select>&nbsp;&nbsp;<font size='1' color='red'>※</font>
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							报料人
						</td>
						<td class="tdvalue">
							<input type="text" id="blr" name="editinput" class="textwidth"
								style="border: 1px #7B9EBD solid" >
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							报料人联系方式
						</td>
						<td class="tdvalue">
							<input type="text" id="lxfs" class="textwidth" name="editinput"
								style="border: 1px #7B9EBD solid" value="">
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报人
						</td>
						<td class="tdvalue">
							<input type="text" id="lrr" class="textwidth" name="editinput"
								style="border: 1px #7B9EBD solid" value="<%=personName%>">&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
						<input type="hidden" id="lrbm"  value="<%=deptname%>"><!-- 录入部门 -->
						<td class="tdtitle" bgcolor="#F0FFFF">
							报料时间
						</td>
						<td class="tdvalue">
							<input type="text" id="lrsj" name="editinput" class="textwidth"
								style="border: 1px #7B9EBD solid" value="<%=daytime%>" readonly="readonly">
						</td>
					</tr>
					<tr height="35" >
                        <td align="center" bgcolor="#F0FFFF">
                       	      备注情况
                        </td>
                        <td class="tdvalue" colspan="3" >
                             <textarea rows="8" id="bz" name="remindInfoValue" style="border: 1px #7B9EBD solid"  cols="66"></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font>
                        </td>
	                </tr>
				</table>
				</form>
			</fieldset>
		</div>
		<div style="text-align: center;width: 2%;height: 2%;"></div>
		<div style="width:95%;text-align: center;">
			<input class="btn" type="button" value=" 报 送 " id="saveData_Accident"
				onclick="submitTNFAdd()" style="margin-right:70px;">
			<input class="btn" type="button" value=" 取 消 " id="close" 
				onclick="document.forms[0].reset()">
		</div>
	</body>

</html>
