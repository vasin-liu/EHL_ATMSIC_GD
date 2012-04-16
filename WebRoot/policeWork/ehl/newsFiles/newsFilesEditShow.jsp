<%@ page language="java"  contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@include file="../../Message.oni"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.ehl.dispatch.common.*" %>
<%
	String newsFileid = StringHelper.obj2str(request.getParameter("newsFileid"), "");
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//机构名称
    String ccbm=strObj[2];//机构层次编码
    
    Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String depttype = ""; 	  //部门类型
	String deptname = ""; 	  //部门名称
	String personname = "";	  //姓名
	String uname = ""; 		  //帐号
	String pid = ""; 		  //人员ID
	String phone = ""; 		  //办公电话
	String mobilephone = "";  //手机
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		depttype = (String)prop.get("DEPTTYPE");
		deptname = (String)prop.get("BRANCHNAME");
		personname = (String)prop.get("NAME");
		uname = (String)prop.get("USERNAME");
		pid=(String)prop.get("PERSONID");
		phone = (String)prop.get("PHONE");
		mobilephone = (String)prop.get("MOBILEPHONE");
	}
	
	//String jgid="441905000000";//12位机构编码
    String jgbh;//总队(2位),支队(4位),大队(6位)
    if(depttype.equals("9") || depttype.equals("0") || "0000".equals(jgid.substring(2,6))){
    	//总队
    	jgbh = jgid.substring(0,2);
    }else if("00".equals(jgid.substring(4,6))){
    	//支队
    	jgbh = jgid.substring(0,4);
    }else{
    	//大队
    	jgbh = jgid.substring(0,6);
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>报送部局信息</title>
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
		<script type="text/javascript" src="../../js/newsFiles/newsFilesAddZd.js"></script>
		<script type="text/javascript" src="../../js/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../dispatch/js/ccommon/FileUpDownload.js"></script>
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
	<body bgcolor="#ffffff" onload="setAllPageValue('<%=newsFileid%>');">
		<div style="text-align: center; width: 100%; height: 100%;"> <fieldset style="width: 99%; border: 0px solid #a5d3ef" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr class="wTableTopCenter">
				<td width="5%" >
					<div align="center">
						<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
					</div>
				</td>
				<td width="70%" class="currentLocation">
					<span class="currentLocationBold"><b>您当前的位置</b></span>：报送部局信息
				</td>
			</tr>
		 </table>
 			<iframe id="uploadFrame" name="uploadFrame" style="display:none"></iframe>
			<form name="alarmFileUploadForm" id="alarmFileUploadForm" method="POST" action="policeWorks.newsFiles.modify.d" ENCTYPE="multipart/form-data" target="uploadFrame">
				<input type="hidden" id="jgbh" name="jgbh" value="<%=jgbh%>" />
				<input type="hidden" id="id" name="id" value="" />
				<input type="hidden" id="sbtype" name="sbtype" value="2" />
		 
				<table class="table3" width="100%" border="1" id="block"
					borderColor='#a5d1ec'>
					<tbody id="flowbox">
						<tr height="35">
							<td align="center" width="15%" bgcolor="#F0FFFF">
								标题
							</td>
							<td colspan="3">
								<input type="text" id="title" name="title" size="89" maxlength=100 style=" border: 1px #7B9EBD solid"/>
							</td>
						</tr>
						<tr height="35">
							<td align="center" bgcolor="#F0FFFF">
								报送时间
							</td>
							<td algin="right">
								<input size="25" type="text" id="sendTime" name="sendTime" readonly style=" border: 1px #7B9EBD solid"/>
							</td>

							<td align="center" width="15%" bgcolor="#F0FFFF">
								报送单位
							</td>
							<td>
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input type="text" id="sendUnit" name="sendUnit" onpropertychange="$('jgid').value=(window.G_jgID||'<%=jgid%>');" maxLength="15" style="width: 160;"  readonly />
											<input type="hidden" id="jgid"  name="jgid"/>
										</td>
										<td>
											<img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor: hand;" onclick="showDepartTree('2','<%=jgid%>','<%=jgmc%>','sendUnit','95','510')">
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr height="35">
							<td align="center" width="15%" bgcolor="#F0FFFF">
								报送人
							</td>
							<td>
								<input size="25" type="type" id="sendPeople" name="sendPeople" maxlength=20 style=" border: 1px #7B9EBD solid"/>
							</td>
							<!-- Modify by Xiayx 2011-08-14 -->
							<td align="center" width="15%" bgcolor="#F0FFFF">
								信息文件类型
							</td>
							<td>
								工作信息<input type="radio" name="type" value="1" style="margin-right: 20px;"/>
								调研信息<input type="radio" name="type" value="2"  />
							</td>
							<!-- Modify finished -->
						</tr>
						<tr height="35">
							<td align="center" width="15%" bgcolor="#F0FFFF">
								撰稿人
							</td>
							<td colspan="3">
								<input type="text" id="writer" name="writer"  style=" border: 1px #7B9EBD solid;width:182px;"/>
							</td>
						</tr>
							<tr  height="35" >
							    <td align="center" bgcolor="#F0FFFF">
								文档文件
								</td>
								<td align='left' id="wordFileList" colspan="3" >
									<input id="wordFile" type="file" name="wordFile" size="79" onkeydown="if(event.keyCode==8){this.select();}else{return false;}"/>
								</td>
							</tr>
							<tr  height="35" >
							    <td align="center" bgcolor="#F0FFFF">
									 多媒体文件
								</td>
								<td align='left' id="streamFileList" colspan="3" >
									<input id="streamFile" type="file" name="streamFile" size="79" onkeydown="if(event.keyCode==8){this.select();}else{return false;}"/>
								</td>
							</tr>

						<tr height="35">
							<td align="center" bgcolor="#F0FFFF">
								内容概要
							</td>
							<td algin="right" colspan="3" >
								<textarea  rows="5" name="modifyContent" id="modifyContent" cols="70" style=" border: 1px #7B9EBD solid"></textarea>
							</td>
						</tr>
						<tr>
						<td bgcolor="#AFEEEE" colspan=4 align="center" style="border-bottom: 1 solid #a5d3ef;">
							<font size="+1" color="red">是否被部局采用信息</font>
						</td>
						</tr>
						<tr>
						<td colspan='4'>
						<table class="table3" width="91.5%" border="0">
						<tr style="border-bottom: 1 solid #a5d3ef;" >
							<td align="center" colspan=1 bgcolor="#F0FFFF" style="border-right: 1 solid #a5d3ef;border-bottom: 1 solid #a5d3ef;">
								是否被部局采用
							</td>
							<td algin="right" colspan=3 style="width:15.5%;">
								<input type="radio"  id="radio2" name="state" value="4">是
								<input type="radio" id="radio3" name="state" value="3"> 否
							</td>
						</tr> 
						<tr >
							<td align="center" colspan=1 bgcolor="#F0FFFF" style="border-right: 1 solid #a5d3ef;">
								备注信息
							</td>
							<td  algin="right" colspan=3 >
								<textarea rows="5" name="otherInfo" id="otherInfo" cols="70" style=" border: 1px #7B9EBD solid"></textarea>
							</td>
						</tr> 
						</table>
						</td>
						</tr>
					</tbody>
				</table>
				</form>
			</fieldset>
			<div style="text-align: center; width: 2%; height: 2%;"> </div>
			<div style="text-align: center; height: 25px; border-bottom: 0 solid #a5d3ef;" id="buttonVegion">
				<iframe id="target_upload" name="target_upload" src="" style="display: none"></iframe>
				<span id="bt1" class="lsearch" style="margin-right: 70px;"> 
					<a href="#" onclick="window.print();"><span class="lbl"> 打 印 </span> </a>
				</span>
				<span id="bt2" class="lsearch" style="margin-right: 70px;"> 
					<a href="#" onclick="doUpdateNewsFilesInfo('<%=newsFileid%>');"><span class="lbl"> 保 存 </span> </a> 
				</span>
				<span id="bt3" class="lsearch" style="margin-right: 70px;"> 
					<a href="#" onclick="window.close();"><span class="lbl"> 关 闭 </span> </a> 
				</span>
			</div>
		</div>
	</body>
</html>

