<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.ehl.dispatch.common.*" %>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@include file="../../Message.oni"%>
<%
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
		<title>交通管制信息表</title>
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/tab.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
	 	<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
	 	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/TrfficeContrl.js"></script>
		<style  type="text/css">
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
				text-align: right;
				width:9%;
				
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
			}
			.table3{
				border-top: 0 solid #a5d1ec;
				border-right: 0 solid #106ead;
				border-bottom: 0 solid #106ead;
				border-left: 0 solid #106ead;
				border-collapse:collapse;
				font-size:11px;
			}
			/*文本框变下划线*/
			.textline {
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
<body bgcolor="#FFFFFF" onLoad="getContrlInfo(<%=alarmId%>,<%=insrtOrUpdt%>);">
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
					<span class="currentLocationBold"><b>您当前的位置</b></span>：交通管制信息
				</td>
			</tr>
		 </table>
			<table class="table3" width="100%" border="1" id="block" borderColor='#a5d1ec'>
				<tr style="display: none">
					<td>
						<input id="jgbh" type="text" value=<%=jgbh%> readonly></input>
						<input id="ALARMID" type="text" value=<%=alarmId%> readonly></input><%--ALARMID--%>
						<input id="EVENTSOURCE" type="text" value="002022" readonly></input><%--警情上报系统--%>
						<input id="EVENTTYPE" type="text" value="001022" readonly></input><%--交通管制--%>
						<%--EVENTSTATE  事件状态--%>
						<input id="ALARMUNIT" type="text" value=<%=deptcode%> readonly></input><%--报警机构--%>
						<input id="TITLE" type="text" value=" 发生 交通管制" readonly></input><%--标题--%>
						<input id="ALARMREGIONID" type="text" value=<%=deptcode%> readonly></input><%--报警辖区编号--%>
						<input id="ALARMREGION" type="text" value=<%=deptname%> readonly></input><%--报警辖区--%>
						<input id="REPORTUNIT" type="text" value=<%=deptcode%> readonly></input><%--填报单位--%>
						<input id="REPORTPERSON" type="text" value=<%=uname%> readonly></input><%--填报人--%>
					</td>
				</tr>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" width='15%'>填报单位</td>
					<td ><input type="text" size="25" id="REPORTUNITVALUE" readonly style="border: 1px #7B9EBD solid" name="editinput"></td>
					<td bgcolor="#F0FFFF"  align="center" width='15%' >填报人</td>
					<td >
						<input type="text" id="REPORTPERSONVALUE" size="25" readonly name="editinput" style="border: 1px #7B9EBD solid" >			
					</td>
				</tr>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" >填报时间</td>
					<td ><input type="text" id="REPORTTIME" size="25" style="border: 1px #7B9EBD solid" name="editinput" readonly value="<%=daytime %>"></td>
					<td bgcolor="#F0FFFF"  align="center" >道路名称</td>
					<td id="alarmRoad_TrafficCrowd_td">
						<script>
							
						</script>
					</td>
				</tr>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center"  >路段备注</td>
					<td ><input type="text" id="ROADNAME"  size="25" name="editinput" readonly style="border: 1px #7B9EBD solid" maxlength=40></td>
					<td valign="top" bgcolor="#F0FFFF"  align="center">方向</td>
					<td valign="top" id="setWay">
<%--						 <input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_1" disabled checked>上行--%>
<%--						  <input type="radio" value="2" disabled name="RADIOTYPE" id="RADIOTYPE_2">下行--%>
						 <input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_1" checked><span id="rdForward">下行</span></br>
						 <input type="radio" value="0" name="RADIOTYPE" id="RADIOTYPE_2"><span id="rdBack">上行</span>
					</td>
				</tr>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" >管制起点(千米)</td>
					<td >
						K<input type="text" id="KMVALUE" readonly class="textline" name="editinput" maxlength="6">
						+<input type="text" id="MVALUE" readonly class="textline" name="editinput"  maxlength="3" >米
				    </td>
					<td bgcolor="#F0FFFF"  align="center" >管制终点(千米)</td>
					<td >
						K<input type="text" id="ENDKMVALUE" readonly class="textline" name="editinput" maxlength="6">
						+<input type="text" id="ENDMVALUE"  readonly class="textline" name="editinput"  maxlength="3" >米
					</td>
				</tr>
				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" >管制开始时间</td>
					<td >
						<input type="text" id="CaseHappenTime" readonly size="25" name="editinput" value="<%=daytime%>" style="border: 1px #7B9EBD solid" >
					</td>
					<td bgcolor="#F0FFFF"  align="center" >管制结束时间</td>
					<td >
						<input type="text" id="CaseEndTime" onKeyDown="if(event.keyCode==8){return false;};" readonly size="25" name="editinput" style="border: 1px #7B9EBD solid" >
					</td>
				</tr>

				<tr height="35">
					<td bgcolor="#F0FFFF"  align="center" >管制方案简述</td>
					<td colspan="3" >
						<textarea  rows="5" cols="69" id="PLAN" disabled name="editinput" style="border: 1px #7B9EBD solid" ></textarea>
					</td>
				</tr>
				
			</table>
			
		</fieldset>
		<div style="text-align: center;width: 2%;height: 2%;">
			</div>
			<div>
			<input class="btn" style="margin-right:70px;" type="button" value=" 打 印 " id="close" onclick="window.print();"> 
				<%if(insrtOrUpdt.equals("2")) {%>
					<input class="btn" type="button" style="margin-right:70px;" value=" 关 闭 " id="close" onclick="window.close();">
				<%}else{%>
					<input class="btn" type="button" value=" 报 送 " style="margin-right:70px;" id="saveData_Accident" onclick="modify(<%=insrtOrUpdt%>);">
					<input class="btn" type="button" style="margin-right:70px;" value=" 关 闭 " id="close" onclick="window.close();">
				<%}%>
			</div>
	</div>

</body>
</html>