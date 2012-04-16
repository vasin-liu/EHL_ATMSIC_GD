<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@page import="com.ehl.dispatch.common.*" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>

<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? "" : request.getParameter("insrtOrUpdt");
	String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),""); 
	String depttype = StringHelper.obj2str(request.getParameter("depttype"),""); 
	String deptcodeStr = StringHelper.obj2str(request.getParameter("deptcode"),""); 
	
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String deptname = ""; 	  //部门名称
	String pname = ""; 		  //姓名
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
		<title>施工占道信息</title>
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
	 	<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css"> 
	 	
	 	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
	 	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/ccommon/CommonClear.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/DhtmUtilTable.js"></script>
		
		<script type="text/javascript" src="../../js/ceditpolice/RoadBuild.js"></script>
		<script type="text/javascript">
		   var alarmId = '<%=alarmId %>';
		   function showPlanInfo(){
				var ckb = document.getElementsByName('ISHAVEPLAN');
				for(i=0;i<ckb.length;i++){
					if(ckb[i].checked){
						$('PLAN_DIV').show();
						var width = $('fieldset').offsetWidth;
						var height = $('fieldset').offsetHeight
						width = eval(width + 25);    
						height = eval(height + 65);
						window.dialogHeight = height+"px";
						window.dialogWidth = width+"px";
						return;
					}
				}
				$('PLAN_DIV').hide();
				$('PLAN').value="";
				var width = $('fieldset').offsetWidth;
				var height = $('fieldset').offsetHeight-100
				width = eval(width + 25);    
				height = eval(height + 65);
				
				window.dialogHeight = height+"px";
				window.dialogWidth = width+"px";
			}
			function readOnlyRolanenum(){
				var ckb = document.getElementsByName('ISALLCLOSE');
				for(i=0;i<ckb.length;i++){
					if(ckb[i].checked && ckb[i].value=='0'){
						$('ROLANENUM').readOnly=false;
						return;
					}
				}
				$('ROLANENUM').readOnly=true;
				$('ROLANENUM').value="";
			}
		</script>
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
		</style>
	</head>
<body bgcolor="#FFFFFF" onload="getRoadBuildInfo(alarmId);">
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #a5d1ec" align="center" id="fieldset">
			<b><legend style="width:15%;border:0px;font-size: 12pt;">施工占道信息表</legend></b>
			<table class="table2" width="95%">
				<tr style="display: none">
					<td>
						<input id="jgbh" type="text" value=<%=jgbh%> readonly></input>
						<input id="ALARMID" type="text" value=<%=alarmId%> readonly></input><%--ALARMID--%>
						<input id="EVENTSOURCE" type="text" value="002022" readonly></input><%--警情上报系统--%>
						<input id="EVENTTYPE" type="text" value="001023" readonly></input><%--施工占道--%>
						<%--EVENTSTATE  事件状态--%>
						<input id="ALARMUNIT" type="text" value=<%=deptcode%> readonly></input><%--报警机构--%>
						<input id="TITLE" type="text" value=" 发生 施工占道" readonly></input><%--标题--%>
						<input id="ALARMREGIONID" type="text" value=<%=deptcode%> readonly></input><%--报警辖区编号--%>
						<input id="ALARMREGION" type="text" value=<%=deptname%> readonly></input><%--报警辖区--%>
						<input id="REPORTUNIT" type="text" value=<%=deptcode%> readonly></input><%--填报单位--%>
						<input id="REPORTPERSON" type="text" value=<%=uname%> readonly></input><%--填报人--%>
					</td>
				</tr>
				<tr>
					<td class="tdtitle" style="width:15%;" bgcolor="#F0FFFF">道路名称：</td>
					<td class="tdvalue" id="alarmRoad_TrafficCrowd_td">
						<script>
							fillListBox("alarmRoad_TrafficCrowd_td","alarmRoad_TrafficCrowd","111","select tori.LAYERROADID,tori.ROADNAME from t_oa_road_info tori,t_oa_roaddepartment tord where tori.roadid= tord.roadid and tori.LAYERROADID is not null and tord.jgid like '"+<%=jgbh%>+"%'  group by tori.LAYERROADID,tori.ROADNAME order by tori.LAYERROADID,tori.ROADNAME ","请选择","","showRoadPath");
						</script>
					</td>
					<td class="tdtitle" rowspan=4 valign="top" style="width:18%;" bgcolor="#F0FFFF">方向：</td>
					<td class="tdvalue" rowspan=4 valign="top">
						 <input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_1" checked>上行<br>
						 <input type="radio" value="2" name="RADIOTYPE" id="RADIOTYPE_2">下行<br>
						<%--<div style="float:left;" id="ROADDIRECTION"></div>
					--%></td>
					<td class="tdvalue" rowspan=4 valign="top" >
						<input type="radio" value="0" name="ISALLCLOSE" id="ISALLCLOSE_0" checked onclick="readOnlyRolanenum()">占用车道数<input type="text" id="ROLANENUM" name="editinput" size="8" readonly"><br>
						<input type="radio" value="1" name="ISALLCLOSE" id="ISALLCLOSE_1" onclick="readOnlyRolanenum()">全封闭<br>
					</td>
				</tr>
				<tr>
					<td class="tdtitle" bgcolor="#F0FFFF">路段备注：</td>
					<td class="tdvalue"><input type="text" id="ROADNAME" class="textwidth" name="editinput" maxlength=40></td>
				</tr>
				<tr>
				</tr>
				<tr>
				</tr>
				<tr>
					<td class="tdtitle" bgcolor="#F0FFFF">占道起点(千米)：</td>
					<td class="tdvalue">
						<input type="text" id="KMVALUE" class="textwidth" name="editinput" maxlength=6>
				    </td>
					<td class="tdtitle" bgcolor="#F0FFFF">占道终点(千米)：</td>
					<td class="tdvalue">
						<input type="text" id="EndKMVALUE" class="textwidth" name="editinput" maxlength=6>					
					</td>
					<td class="tdvalue" rowspan=2 valign="top" style="width:32%;"></td>
				</tr>
				<tr>
					<td class="tdtitle" bgcolor="#F0FFFF">施工开始时间：</td>
					<td class="tdvalue">
						<input type="text" id="CaseHappenTime" class="textwidth" name="editinput" value="<%=daytime%>" onclick="SelectDateTime(this)">
					</td>
					<td class="tdtitle" bgcolor="#F0FFFF">施工预计结束时间：</td>
					<td class="tdvalue">
						<input type="text" id="CaseEndTime" onKeyDown="if(event.keyCode==8){return false;};" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
				</tr>
				<%--<tr><td colspan="5" class="tdvalue" height="10" style="border-top: 2 solid #106ead;border-bottom: 2 solid #106ead;"></td>--%></tr>
				
				<tr>
					<td class="tdtitle" bgcolor="#F0FFFF">
						采取分流管制措施：
					</td>
					<td class="tdvalue" colspan="5" >
						<input type="checkbox" id="ISHAVEPLAN" name="ISHAVEPLAN" onclick="showPlanInfo()" bgcolor="#F0FFFF">
					</td>
						
				</tr>
				
				<tr>
					<td class="tdtitle" bgcolor="#F0FFFF">
						分流路径简述：
					</td>
					<td class="tdvalue" colspan="5" >
						<div id="PLAN_DIV" style="display:none;" bgcolor="#F0FFFF"><textarea  rows="5" style="width:85%" id="PLAN" name="editinput"></textarea></div>
					</td>
					
						
				</tr>
				
				
				
				<%--<tr><td colspan="5" class="tdvalue" height="10" style="border-top: 2 solid #106ead;border-bottom: 2 solid #106ead;"></td>--%></tr>
				<%--<tr>
					<td class="tdtitle">播放录音：</td>
					<td class="tdvalue" colspan="4">
						<object id="Player" width="240" height="45" classid="CLSID:6BF52A52-394A-11D3-B153-00C04F79FAA6">
					        <param name="URL" value="wangwei.wmv">
					        <param name="autoStart" value="false">
					        <param name="balance" value="0">
					        <param name="baseURL" value>
					        <param name="captioningID" value>
					        <param name="currentPosition" value="0">
					        <param name="currentMarker" value="0">
					        <param name="defaultFrame" value>
					        <param name="enabled" value="1">
					        <param name="enableErrorDialogs" value="0">
					        <param name="enableContextMenu" value="1">
					        <param name="fullScreen" value="0">
					        <param name="invokeURLs" value="1">
					        <param name="mute" value="0">
					        <param name="playCount" value="1">
					        <param name="rate" value="1">
					        <param name="SAMIStyle" value>
					        <param name="SAMILang" value>
					        <param name="SAMIFilename" value>
					        <param name="stretchToFit" value="0">
					        <param name="uiMode" value="Mini">
					        <param name="volume" value="100">
					        <param name="windowlessVideo" value="0">
					        <param name="BufferingTime" value="15"><!--设置视频缓存时间-->
					    </object>
					</td>
				</tr>
				--%><%--<tr><td colspan="5" class="tdvalue" height="10" style="border-top: 2 solid #106ead;border-bottom: 2 solid #106ead;"></td>--%></tr>
				<tr>
					<td colspan="5" style="border-right: 1 solid #CCCCCC;">
					<table width=100% cellspacing="0"><tr>
				    <td style="border-right: 1 solid #CCCCCC;text-align: right;" bgcolor="#F0FFFF">&nbsp;&nbsp;&nbsp;填报单位：</td>
					<td style="border-right: 1 solid #CCCCCC;">
						<input type="text" id="REPORTUNITVALUE" class="textwidth" name="editinput" value="<%=deptname %>">
					</td>
					<td style="border-right: 1 solid #CCCCCC;text-align: right;" bgcolor="#F0FFFF">填报人：</td>
					<td style="border-right: 1 solid #CCCCCC;">
						<input type="text" id="REPORTPERSONVALUE" class="textwidth" name="editinput" value="<%=pname %>">
					</td>
					<td style="border-right: 1 solid #CCCCCC;text-align: right;" bgcolor="#F0FFFF">填报时间：</td>
					<td style="border-right: 1 solid #CCCCCC;">
						<input type="text" id="REPORTTIME" class="textwidth" name="editinput" value="<%=daytime %>">
					</td>
					</tr></table>
					</td>
				</tr>
			</table>
			<div style="width:95%;text-align: right">
				<input class="btn" type="button" value=" 打 印 " id="close" onclick="window.close();">&nbsp;&nbsp;&nbsp;
				<%if(insrtOrUpdt.equals("2")) {%>
					<input class="btn" type="button" value=" 关 闭 " id="close" onclick="window.close();">&nbsp;&nbsp;&nbsp;
				<%}else{%>
					<input class="btn" type="button" value=" 报 送 " id="saveData_Accident" onclick="modify(<%=insrtOrUpdt%>);">&nbsp;&nbsp;&nbsp;
				<%}%>
			</div>
		</fieldset>
	</div>
</body>
</html>