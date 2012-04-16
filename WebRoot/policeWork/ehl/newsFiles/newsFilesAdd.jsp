<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="java.text.DateFormat"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("880603");	
	String[] strObj = DepartmentManage.getDeptInfo(request, "1").split( ",");//获取单位信息串
	String jgid = strObj[0];//单位编码
	String jgmc = strObj[1];//机构名称
	String ccbm = strObj[2];//机构层次编码

	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; //部门编码
	String depttype = ""; //部门类型
	String deptname = ""; //部门名称
	String personname = ""; //姓名
	String uname = ""; //帐号
	String pid = ""; //人员ID
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	if (prop != null) {
		deptcode = (String) prop.get("BRANCHID");
		depttype = (String) prop.get("DEPTTYPE");
		deptname = (String) prop.get("BRANCHNAME");
		personname = (String) prop.get("NAME");
		uname = (String) prop.get("USERNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
	}

	//String jgid="441905000000";//12位机构编码
	String jgbh;//总队(2位),支队(4位),大队(6位)
	
	if (depttype.equals("9") || depttype.equals("0")
			|| "0000".equals(jgid.substring(2, 6))) {
		//总队
		jgbh = jgid.substring(0, 2);
	} else if ("00".equals(jgid.substring(4, 6))) {
		//支队
		jgbh = jgid.substring(0, 4);
	} else {
		//大队
		jgbh = jgid.substring(0, 6);
	}
	Date now = new Date();
	DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	String noticeId = jgid.toString() + format.format(now) + (int) (Math.random() * 9);
	
	String resultMsg = StringHelper.obj2str(session.getAttribute("resultMsg"),"");
	session.removeAttribute("resultMsg");
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
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../base/js/tree/tree.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/newsFiles/newsFilesAdd.js"></script>
		<script type="text/javascript" src="../../js/DepartmentSelect.js"></script>
		<style type="text/css">
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
			/*宽高行高背景不显示超过对象尺寸的内容*/
			.lbackBt {
				width: 112px;
				height: 22px;
				line-height: 22px;
				background: url(../../images/dispatch/backbt.png) no-repeat;
				overflow: hidden;
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
	<body bgcolor="#ffffff" onload="if('<%=resultMsg%>'){alert('<%=resultMsg%>')}">
	<form name="alarmFileUploadForm" id="alarmFileUploadForm" method="POST" action="policeWorks.newsFiles.insertNewsFiles.d" ENCTYPE="multipart/form-data">
		<div style="text-align: center; width: 100%; height: 100%;"> <fieldset style="width: 99%; border: 1px solid #a5d3ef" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr class="wTableTopCenter">
				<td width="5%" >
					<div align="center">
						<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
					</div>
				</td>
				<td width="70%" class="currentLocation">
					<span class="currentLocationBold"><b>您当前的位置</b></span>：<%=title %>
				</td>
			</tr>
		 </table>
				<table class="table3" width="100%" border="1" id="block"
					borderColor='#a5d1ec'>
					<tbody id="flowbox">
						<tr style="display: none">
							<td>
								<input type="hidden" id="jgbh" name="jgbh" value="<%=jgbh%>" />
								<input type="hidden" id="jgid" name="jgid" value="<%=deptcode%>" />
								<input type="hidden" id="sbtype" name="sbtype" value="1" />
							</td>
						</tr>
						<tr height="35">
							<td align="center" width="15%" bgcolor="#F0FFFF">
								标题
							</td>
							<td colspan="3">
								<input type="text" id="title" name="title" size="91" maxlength=100 style=" border: 1px #7B9EBD solid"/>&nbsp;&nbsp;<font size="1" color="red">※</font>
							</td>
						</tr>
						<tr height="35">
							<td align="center" bgcolor="#F0FFFF">
								报送时间
							</td>
							<td algin="right">
								<%
									Date date = new Date();
									SimpleDateFormat mat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
								%>
								<input size="25" type="text" id="sendTime" name="sendTime" readonly 
									value="<%=mat.format(date)%>" size="25"  style=" border: 1px #7B9EBD solid"/>
							</td>

							<td align="center" width="15%" bgcolor="#F0FFFF">
								报送单位
							</td>
							<td>
								<input type="text" id="sendUnit" name="sendUnit" value="<%=jgmc%>"  readonly size="25" style=" border: 1px #7B9EBD solid">
							</td>
						</tr>
						<tr height="35">
							<td align="center" width="15%" bgcolor="#F0FFFF">
								报送人
							</td>
							<td colspan='3'>
								<input size="25" type="type" id="sendPeople" name="sendPeople" maxlength=20  value="<%=personname%>" style=" border: 1px #7B9EBD solid"/>&nbsp;&nbsp;<font size="1" color="red">※</font>
							</td>
						</tr>
						<tr height="35">
							<td align="center" width="15%" bgcolor="#F0FFFF">
								撰稿人
							</td>
							<td colspan="3">
								<input type="text" id="writer" name="writer"  style=" border: 1px #7B9EBD solid;width:182px;"/>
								（多个撰稿人之间以顿号、分隔）
							</td>
						</tr>
							<tr  height="35" >
							    <td align="center" bgcolor="#F0FFFF">
								文档文件
								</td>
								<td align='left' id="fileList" colspan="3" >
									<input id="wordFile" type="file" name="wordFile" size="40" onkeydown="if(event.keyCode==8){this.select();}else{return false;}"/>
									（<font color="red" size="2">必填</font>，文件大小不能超过50M）
								</td>
							</tr>
							<tr  height="35" >
							    <td align="center" bgcolor="#F0FFFF">
									 多媒体文件
								</td>
								<td align='left' id="fileList" colspan="3" >
									<input id="streamFile" type="file" name="streamFile" size="40" onkeydown="if(event.keyCode==8){this.select();}else{return false;}"/>
									（文件大小不能超过50M）
								</td>
							</tr>

						<tr height="35">
							<td align="center" bgcolor="#F0FFFF">
								内容概要
							</td>
							<td algin="right" colspan="3">
								<textarea rows="5" name="modifyContent" id="modifyContent" cols="70" style=" border: 1px #7B9EBD solid"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</fieldset>
			<div style="text-align: center; width: 2%; height: 2%;"> </div>
			<div style="text-align: center; height: 25px; border-bottom: 0 solid #a5d3ef;" id="buttonVegion">
				<iframe id="target_upload" name="target_upload" src="" style="display: none"></iframe>
				<span id="bt6" class="lsearch" style="margin-right: 70px;"> 
					<a href="#" onclick="window.print();"><span class="lbl"> 打 印 </span> </a>
				</span>
				<span id="bt4" class="lsearch" style="margin-right: 70px;"> 
					<a href="#" onclick="doInsertNewsFiles();"><span class="lbl"> 报 送 </span> </a> 
				</span>
			</div>
		</div>
	</body>
</form>
</html>

