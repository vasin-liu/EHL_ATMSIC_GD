<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@page import="com.ehl.dispatch.notice.dao.NoticeDao"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("570605");	
	//当前用户信息
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String personname = ""; //姓名
	String uname = ""; //帐号
	String pid = ""; //人员ID
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	String jgbh = "";//总队(2位),支队(4位),大队(6位)
	if (prop != null) {
		uname = (String) prop.get("USERNAME");
		personname = (String) prop.get("NAME");
		deptcode = (String) prop.get("BRANCHID");
		deptname = (String) prop.get("BRANCHNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		pname = StringHelper.obj2str(DispatchUtil.getDutyPersonNameByDepId(deptcode),"");
	}
	
	NoticeDao dao = new NoticeDao();
	String ptype = StringHelper.obj2str(request.getParameter("ptype"),"1");
	String id = StringHelper.obj2str(request.getParameter("id"),dao.getId());
	String stype = StringHelper.obj2str(request.getParameter("stype"),"");
	pageContext.setAttribute("ctime", Constant.getCurrentTime(false).substring(0,16));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%=jgbh.length()==2?"值班日志":title %></title>
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/tab.css" />
		<link type="text/css" rel="Stylesheet"
			href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet"
			href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet"
			href="../../../webgis/css/contents.css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
		<link rel="STYLESHEET" type="text/css"
			href="../../../sm/css/popup/Popup.css">
		<link rel="stylesheet" type="text/css"
			href="../../../sm/css/Global.css">
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript"
			src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript"
			src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript"
			src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../js/ccommon/calendar/DateAndSchar.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/notice.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Flow.js"></script>
		<script type="text/javascript" src="../../js/ccommon/FileUpDownload.js"></script>
		<script type="text/javascript" src="../../js/ccommon/content.js"></script>
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
</style>
	</head>
	<body bgcolor="#ffffff" onload="Notice.add.init()">
		<div style="text-align: center; width: 100%; height: 100%;">
			<fieldset style="width: 99%; border: 1px solid #a5d3ef" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="wTableTopCenter">
						<td width="5%" >
							<div align="center">
								<img src="../../../base/image/cssimg/table/tb.gif"
									width="16" height="16" alt="img" />
							</div>
						</td>
						<td width="70%" class="currentLocation">
							<span class="currentLocationBold"><b>您当前的位置</b></span>：<%=jgbh.length()==2?"值班日志":title %>
						</td>
					</tr>
				</table>
				<table id="main" class="table3" width="100%" border="1" id="block" borderColor='#a5d1ec'>
					<tr style="display: none">
						<td>
							<input type="hidden" id="jgid" value="<%=deptcode%>" />
							<input type="hidden" id="jgbh" value="<%=jgbh%>" />
							<input type="hidden" id="dname" value="<%=deptname.replace(Constant.GAJJTJC,Constant.JJ)%>" />
							<input type="hidden" id="name" value="<%=pname%>" />
							<input type="hidden" id="page" value="<%=ptype%>" />
							<input type="hidden" id="id" value="<%=id%>" />
							<input type="hidden" id="stype" value="<%=stype%>" />
						</td>
					</tr>
					<tr>
						<td align="center" width="10%" bgcolor="#F0FFFF" >
							发送单位<input id="spdcode" type="hidden"  value="<%=deptcode%>" />
						</td>
						<td id="spdnameTd" colspan="1">
							<input id="spdname" type="text" value="<%=deptname.replace("公安局交通警察","交警")%>"  disabled>
						</td>
						<td id="stimeDescTd" align="center" width="15%" bgcolor="#F0FFFF">
							发送时间
						</td>
						<td id="stimeTd" algin="right">
							<input  type="text" id="stime" disabled value="${ctime}" />
						</td>
					</tr>
					<tr id="spnameTr" >
						<td align="center" width="10%" bgcolor="#F0FFFF">
							发送人
						</td>
						<td id="spnameTd">
							<input id="spname" type="type" value="<%=pname%>" />&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
						<td align="center" bgcolor="#F0FFFF">
							接收单位<input id="adcode" type="hidden" />
						</td>
						<td id="rpdnameTd" algin="right" colspan="1">
							<input type="hidden" id="jgmcId" />
                           	<input id="jgmc" type="text" value="" style="width: 153px;" readonly />
                            <img id="acceptDeptImg" alt="选择机构" src="../../images/popup/btnselect.gif" onclick="setTree('<%=deptcode %>',86,520,null,'11')" style="cursor:hand;"/>&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
					
					<tr>	
					   <td align="center" width="15%" bgcolor="#F0FFFF">
							标题
						</td>
						<td id="titleTd" colspan="3">
							<input id="title" type="text" style="width:554" />&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
					<tr id="contentTr" height="100">
						<td align="center" bgcolor="#F0FFFF">
							内容
						</td>
						<td id="contentTd" algin="right" colspan="3">
							<textarea id="content" style="height:150px;width:554" ></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
                    <tr id="attachTr" height="30">
                      	<td id="apathDescTd" align="center" bgcolor="#F0FFFF" >
                                                                                添加附件
                       </td>
                       <td id="fileRegion" colspan="3" >
                        <script type="text/javascript">$("fileRegion").innerHTML=UDload.getForm(547) </script>  
                    	</td>
                    </tr>
                    <tr id="addContentTr" height="100" style="display: none;">
						<td align="center" bgcolor="#F0FFFF">
							续报
						</td>
						<td id="addContent" algin="right" colspan="3">
							<textarea  style="height:100px;width:100%;" ></textarea>
						</td>
					</tr>
					<tr id="fcontentTr" height="100" style="display: none;">
						<td align="center" bgcolor="#F0FFFF">
							收文单位办理情况
						</td>
						<td id="fcontentTd" algin="right" colspan="3">
							<table border="0" cellspacing="0" cellpadding="0" style="width: 100%;height: 100%;">
								<tr id="blqkOldTr">
									<td id="blqkOldTd" style="width:545px;padding-top:5px;"></td>
									<td style="border-left:1px solid #a5d3ef;text-align: center;vertical-align: bottom;">
										<span class="lsearch" style="text-align: center;vertical-align: bottom">
											 <a href="#" name="addBlqk" id="addBlqk" onclick="showSave(1);">
											 	<span class="lbl"> 添 加 </span>
											 </a> 
										</span>
									</td>
								</tr>
								<tr id="blqkNewTr" name="blqkNewTr" style="display: none;border-top:1px solid #a5d3ef;">
									<td colspan="2" style="border-top:1px solid #a5d3ef;">
										<textarea name="fcontent" id="fcontent" style="height:150px;width:547"></textarea>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr id="adTr" style="display: none;">
						<td align="center" bgcolor="#F0FFFF">
							接收单位处理状态
						</td>
						<td id="adTd" algin="right" colspan="3"></td>
					</tr>
					<tr id="adcTr" style="display: none;">
						<td colspan="4" style="left">
							<span style="font-weight: bold;">◆接收单位处理状态中，单位名称显示为红色表示未签收，显示为绿色表示已签收，显示为黑色表示已处理</span>
						</td>
					</tr>
					</table>
			</fieldset>
			<div id="adcDiv" style="text-align: left;margin-left: 5px;display: none;">
				<span style="font-weight: bold;">◆说明：接收单位处理状态中未签收单位为红色，已签收单位为蓝色，已处理单位为黑色</span>
			</div>
			<div style="text-align: center; width: 2%; height: 2%;">
				<iframe id="dowmloadFrame" name="dowmloadFrame" style="display: none"></iframe>
			</div>
			<div
				style="text-align: center; height: 25px; border-bottom: 0 solid #a5d3ef;"
				id="buttonVegion">
				<span id="print" class="lsearch" style="margin-right: 30px;">
					 <a href="#" onclick="Notice.print();">
					 	<span class="lbl"> 打 印 </span>
					 </a> 
				</span>
				<span id="save1" class="lsearch" style="margin-right: 30px;display: none;"> 
					<a href="#" onclick="Notice.addAttachment(1)">
						<span class="lbl"> 保 存 </span> 
					</a> 
				</span>
				<span id="save2" class="lsearch" style="margin-right: 30px;display: none;"> 
					<a href="#" onclick="Notice.addAttachment(2)">
						<span class="lbl"> 发 送 </span> 
					</a> 
				</span>
				<span id="signin" class="lsearch" style="margin-right: 30px;display: none;">
					 <a href="#" onclick="Notice.finish('2')">
					 	<span class="lbl"> 签 收 </span>
					 </a> 
				</span>
				<span id="update" class="lsearch" style="margin-right: 30px;display: none;"> 
					<a href="#update" name="update" onclick="Notice.update()">
						<span class="lbl"> 保 存 </span> 
					</a> 
				</span>
				<span id="dispatch" class="lsearch" style="margin-right: 30px;display: none;"> 
					<a href="#dispatch" name="dispatch"  onclick="Notice.dispatch()">
						<span class="lbl"> 发 送 </span> 
					</a> 
				</span>
				<span id="send" class="lsearch" style="margin-right: 30px;display: none;"> 
					<a href="#" onclick="Notice.showSend()">
						<span class="lbl"> 发 送 </span> 
					</a> 
				</span>
				<span id="cancle" class="lsearch" style="margin-right: 30px;display: none;"> 
					<a href="#" onclick="Notice.cancel()">
						<span class="lbl"> 取 消 </span> 
					</a> 
				</span>
				<span id="finish" class="lsearch" style="margin-right: 30px;display: none;"> 
					<a href="#" onclick="Notice.finish('3')">
						<span class="lbl"> 办 结 </span> 
					</a> 
				</span>
				<span id="close" class="lsearch" style="margin-right: 30px;display: none;"> 
					<a href="#" onclick="window.close()">
						<span class="lbl"> 关 闭</span> 
					</a> 
				</span>
			</div>
		</div>
	</body>
</html>

