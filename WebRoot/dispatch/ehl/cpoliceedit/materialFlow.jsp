<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="com.ehl.dispatch.cdispatch.action.MaterialInfoAction"%>
<%@include file="../../Message.oni"%>
<%
	String insrtOrUpdt = StringHelper.obj2str(request
			.getParameter("insrtOrUpdt"), "");
	String alarmId = StringHelper.obj2str(request
			.getParameter("alarmId"), "");
	String depttype = StringHelper.obj2str(request
			.getParameter("depttype"), "");
	String deptcodeStr = StringHelper.obj2str(request
			.getParameter("deptcode"), "");
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

	new MaterialInfoAction().doReceivedMaterialInfo(alarmId, deptcode);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	Date date = new Date();
	String daytime = sdf.format(date);
	String daytime0 = sdf0.format(date);

	String jgbh;//总队(2位),支队(4位),大队(6位)
	if ("0000".equals(deptcode.substring(2, 6))) {
		jgbh = deptcode.substring(0, 2);
	} else if ("00".equals(deptcode.substring(4, 6))) {
		jgbh = deptcode.substring(0, 4);
	} else {
		jgbh = deptcode.substring(0, 6);
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>重特大交通事故续报</title>
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
			href="../../../base/css/style2/Popup.css" />
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
	border-right: 0 solid #a5d3ef;
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
	color: #345678;
	filter: glow(color =   #ffffff, strength =   1);
}

.table3 {
	background: #F5F5F5;
	border-top: 0 solid #106ead;
	border-right: 0 solid #106ead;
	border-bottom: 0 solid #106ead;
	border-left: 0 solid #106ead;
	border-collapse: collapse;
	font-size: 11px;
	background-color: #E5F4FD;
}
</style>
	</head>
	<body bgcolor="#E5F4FD" onload="getMaterialInfoFlow('<%=alarmId%>', '<%=jgbh%>')" onunload="if($('resFlag').value != 'true') {deleteAttachmentFile($('fjid').value, $('insertYwid').value)}">
		<div>
			<fieldset align="center"
				style="width: 99%; border: 1px solid #a5d3ef">

				<legend style="border: 0px; font-size: 12pt;">
					重特大交通事故续报
				</legend>

				<table id="block" border="1" class="table3">
					<tr height='35px'>
						<td align="center" width="20%" bgcolor="#F0FFFF">
							事故标题
						</td>
						<td colspan="3">
							<label id="title"></label>
						</td>
					</tr>
					<tr height='35px'>
						<td align="center" width="20%">
							续&nbsp;&nbsp;报&nbsp;&nbsp;人
						</td>
						<td>
							<input ID="PERSONWRITE" name="PERSONWRITE" type="text"
								value="<%=pname%>" maxlength=20 size="25" />&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
						<td align="center" width="20%">
							续报时间
						</td>
						<td algin="right">
							<input type="text" id="REPORTTIME" readonly value="<%=daytime%>"
								size="25" />&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
					<tr height='35px'>
						<td align="center" width="20%">
							死亡人数
						</td>
						<td>
							<input height="25" type="text" id="DEATHPERSONCOUNT"
								 maxlength=10 size="10" />
							<font color='black'>&nbsp;&nbsp;<font size="1" color="red">※</font>（数字）</font>
						</td>
						<td align="center" width="20%">
							受伤人数
						</td>
						<td>
							<input height="25" type="text" id="BRUISEPERSONCOUNT"
								maxlength=10 size="10" />
							<font color='black'>&nbsp;&nbsp;<font size="1" color="red">※</font>（数字）</font>
						</td>
					</tr>
					<tr height='35px' id="roadInfo">
						<td align="center" width="20%">
							道路名称
						</td>
						<td colspan="3">
							<label id="roadname"></label>
						</td>
					</tr>
					<tr>
					<td align="center" width="20%">
							事故位置
						</td>
						<td colspan="3">
							K
							<input id="KMVALUE" type="text"
								style="width: 70px; display: inline;" maxlength="6">
							+
							<input id="MVALUE" type="text" style="width: 60px;" value="0"
								maxlength="3">
							米
						</td>
					</tr>
					<tr height='35px'>
						<td align="center" width="20%" bgcolor="#F0FFFF" id="rd">
							路&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;段
						</td>
						<td colspan="3">
							<input type="text" maxlength=100 style="width: 521px;"
								id="roadNote">
						</td>
					</tr>
					
					<div id="fileRegion">
					<!-- modified by leisx 2011-8-24 -->
						<tr height="35">
							<input type="hidden" id="insertYwid" name="insertYwid"
									value="<%=alarmId%>" />
							<input type="hidden" id="fjid" name="fjid" value="">
							<input type="hidden" id="fjwz" name="fjwz" value="">
							<td align="center" id="resetFileId" bgcolor="#F0FFFF">
								续报附件
							</td>
							<td align='left' id="fileList" colspan=3>
								<input id="fjlocal" type="text" size="60" readonly="readonly">
								<input id="filebutton" type="button" onclick="uploadAttachment('<%=alarmId%>')" value=" 新 增 " style="border: 1px #7B9EBD solid;width: 60px">
								<input id="deletebutton" type="button" onclick="clearData()" value=" 删 除 " style="border: 1px #7B9EBD solid;width: 60px">
							</td>
						</tr>
					</div>
					<tr>
						<td align="center" width="20%">
							续报内容
						</td>
						<td colspan="3">
							<textarea rows="7" ID="XBNR" name="XBNR" name="S1" cols="63"
								onkeypress="if   (this.value.length   >=  300)   return   false;"></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
				</table>
			</fieldset>
			<div
				style="text-align: center; height: 25px; border-bottom: 0 solid #a5d3ef;">
				<iframe id="target_upload" name="target_upload" src=""
					style="display: none"></iframe>
				<span id="bt2" class="lsearch" style="margin-right: 20px;"> <a
					href="#"
					onclick="if(confirm('是否确认报送?')){insertNewXb('<%=alarmId%>','<%=deptname%>','<%=jgbh%>');}"><span
						class="lbl">报 送</span> </a> </span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="lsearch" style="margin-right: 20px;"> <a
					href="#" onclick="deleteAttachmentFile($('fjid').value, $('insertYwid').value);window.close();"><span class="lbl">关 闭</span>
				</a> </span>
			</div>
		</div>
		<input type="hidden" id="roaddirection">
		<input type="hidden" id="resFlag" value="">
	</body>
</html>
