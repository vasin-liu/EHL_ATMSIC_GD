<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.ehl.dispatch.common.*" %>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("880903");	
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? "" : request.getParameter("insrtOrUpdt");
	String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),""); 
	
	String depttype = StringHelper.obj2str(request.getParameter("depttype"),""); 
	String deptcodeStr = StringHelper.obj2str(request.getParameter("deptcode"),""); 
	
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String deptname = ""; 	  //部门名称
	String pname1 = ""; 		  //姓名
	String uname = ""; 		  //帐号
	String pid = ""; 		  //办公电话
	String phone = ""; 		  //办公电话
	String mobilephone = "";  //手机
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		pname = (String)prop.get("NAME");
		uname = (String)prop.get("USERNAME");
		pid=(String)prop.get("PERSONID");
		phone = (String)prop.get("PHONE");
		mobilephone = (String)prop.get("MOBILEPHONE");
	}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");   
    Date date = new Date();   
    String daytime = sdf.format(date);
    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//机构名称
    String ccbm=strObj[2];//机构层次编码
    //String jgid="441905000000";//12位机构编码
    String jgbh;//总队(2位),支队(4位),大队(6位)
    if("0000".equals(jgid.substring(2,6))){
    	jgbh = jgid.substring(0,2);
    }else if("00".equals(jgid.substring(4,6))){
    	jgbh = jgid.substring(0,4);
    }else{
    	jgbh = jgid.substring(0,6);
    }
    System.out.println("机构查询条件: "+jgbh);
    System.out.println("机构层次编码: "+ccbm);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%=title %></title>
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/tab.css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
	 	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../sm/js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/assess/ScoreRecordEdit.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../dispatch/js/ccommon/calendar/DateAndSchar.js"></script>
		
		<style  type="text/css">
			.btn{
				BORDER-RIGHT: #000000 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #000000 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; 
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#0c9ad3, EndColorStr=#085887); 
				BORDER-LEFT: #000000 1px solid; CURSOR: hand; COLOR: #ffffff; PADDING-TOP: 2px; BORDER-BOTTOM: #000000 1px solid
			}
			.deptDivClass {
				z-index:10000;
				position:absolute;
				display:inline;
				width:250;
				height:340;
			}
			
			#calendarTableDT tr{
				height:12px;
				line-height:100%;
				margin :0px;
			}
			#calendarTableDT td{
				height:12px;
				line-height:100%;
				padding : 0px;
				margin :0px;
			}
			#calendarTableDT th{
				height:12px;
				line-height:100%;
				padding: 5 0 2;
				margin :0px;
			}
		</style>
	</head>
<body bgcolor="#FFFFFF">
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;border:1px solid #a5d3ef" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="wTableTopCenter">
						<td width="5%" >
							<div align="center">
								<img src="../../../base/image/cssimg/table/tb.gif"
									width="16" height="16" alt="img" />
							</div>
						</td>
						<td width="70%" class="currentLocation">
							<span class="currentLocationBold"><b>您当前的位置</b></span>：<%=title %>
						</td>
					</tr>
		</table>
			<div id="showLength" style="color:red"> </div>
			<form action="">
			<table class="table3" width="100%" border="1" cellspacing="0" cellpadding="0" id="block" borderColor='#a5d1ec'>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" width='15%'>交警机构</td>
					<input id="jgid" type="hidden" value="<%=jgid %>" />
					<td >
						<input type="text" style="width:170" style="border: 1px #7B9EBD solid" id="jgmc" readonly value="<%=deptname %>"
						ondblclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','jgmc','62','130')" 
						onpropertychange="$('jgid').value= G_jgID" />
						<img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor:hand;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','jgmc','62','130')">
					</td>
					<td bgcolor="#F0FFFF"  align="center" width='15%'>信息发生时间</td>
					<td >
						<input type="text" style="width:200" style="border: 1px #7B9EBD solid" id="date" readonly  onclick="SelectDateTime(this)"/>
					</td>
				</tr>
				<tr height="35" style="display: none">
					<td bgcolor="#F0FFFF"  align="center" style="display: none">警情加减分类型</td>
					<td style="padding-left: 10px;display: none;">
						<label id="addLabel" for="add" >加分类型</label>
						<input id="add" name="addMinus" type="radio" value="1" style="margin-right: 30px;" onclick="showAddMinus(this)"/>
						<label id="minusLabel" for="minus" >减分类型</label>
						<input id="minus" name="addMinus" type="radio" value="2" onclick="showAddMinus(this)"/>
					</td>
					<td bgcolor="#F0FFFF"  align="center" >
						<span id="addMinusSpan"></span>
						<input id="type" type="hidden" value="" />
					</td>
					<td colspan="3">
						<select id="addMinusSelect" style="width: 200px;" onchange="$('type').value = this.value;showDefReason(this,Radio.getValue('addMinus'))">
							
						</select>
					</td>
				</tr>
				<tr height="35" >
					<td bgcolor="#F0FFFF"  align="center" >扣分项目</td>
					<td>
						<select id="items" style="width: 190px;">
							
						</select>
					</td>
					<td bgcolor="#F0FFFF"  align="center" >
						扣分原因
					</td>
					<td colspan="3">
						<select id="reasons" style="width: 200px;" >
							
						</select>
					</td>
				</tr>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" >备注</td>
					<td colspan="3" style="text-align: left;">
						<textarea  rows="10" cols="70" id="reason" name="editinput" style="border: 1px #7B9EBD solid;margin-left: 0px;padding-left:10px;" ></textarea>
					</td>
				</tr>
			</table>
			</form>
		</fieldset>
		<div style="text-align: left;width: 100%;height: 2%;">
			<span  id="alarmTypeDesc" style="color: red;text-align: left;font-size: 12px;margin-left: 10px;"> 
				◆<!-- 加分类型包括：报送信息被部交管局采用； -->扣分类型包括：错报、漏报重特大交通事故、交通拥堵、施工占道信息，未准确回应报料信息
			</span>
		</div>
		<div>
			<input class="btn" type="button" value=" 扣分 " style="margin-right:70px;" id="submit" onclick="submit();">
<%--			<input class="btn" type="button" value=" 取消 " style="margin-right:70px;"onclick="document.forms[0].reset();">--%>
		</div>
	</div>
</body>
</html>