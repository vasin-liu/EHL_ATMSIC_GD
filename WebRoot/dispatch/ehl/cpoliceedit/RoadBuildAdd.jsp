<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.data.sql.DBHandler"%>
<%@include file="../../Message.oni"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@page import="com.ehl.dispatch.common.*" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>

<%
	String title = FlowUtil.getFuncText("570603");	
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? "" : request.getParameter("insrtOrUpdt");
	String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),"");
	String bulidState = StringHelper.obj2str(request.getParameter("buildState"), "");
	String depttype = StringHelper.obj2str(request.getParameter("depttype"),""); 
	String deptcodeStr = StringHelper.obj2str(request.getParameter("deptcode"),""); 
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String deptname = ""; 	  //部门名称
	String personName = ""; 		  //姓名
	String uname = ""; 		  //帐号
	String pid = ""; 		  //办公电话
	String phone = ""; 		  //办公电话
	String mobilephone = "";  //手机
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		personName = (String)prop.get("NAME");
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

    //Modified by Liuwx 2012-3-29 10:39:39
    //是否显示计分栏，默认不显示:
    // 0-表示不显示；1-表示查看；2-表示修改；
    String isScoring = request.getParameter("isScoring") == null ? "0" : request.getParameter("isScoring");
    //Modification finished

    //Modified by Liuwx 2011-08-02
    Object isOutTime = DBHandler.getSingleResult("SELECT COUNT(TAA.ALARMID) FROM T_ATTEMPER_ALARM TAA WHERE TAA.ALARMREGIONID='"+jgid
    		+"' AND TAA.ALARMID='"+alarmId
    		+"' AND TAA.EVENTSTATE='570005' AND (SELECT SYSDATE FROM DUAL)>=TAA.CASEENDTIME ORDER BY 1");
    int resNum = StringHelper.obj2int(isOutTime,0);
    //Modification finished
    
    String systime = DBHandler.getSingleResult("select to_char(sysdate,'yyyy-mm-dd hh:MM:ss') from dual").toString();
    systime = systime.substring(0,systime.length()-2);
   	String id = jgid.substring(0,6) + systime.replace("-","").replace(":","").replace(" ","");
   	systime = systime.substring(0,systime.lastIndexOf(":")).replace("-","/");
   	if(alarmId != ""){
   		id = alarmId.substring(0,alarmId.length()-3);
   	}
   	
	personName = StringHelper.obj2str(DispatchUtil.getDutyPersonNameByDepId(jgid),"");
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
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css"> 
	 	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/dynamicinfo/css/text.css"> 
	 	<script type="text/javascript" src="${pageContext.request.contextPath}/util/jquery/jquery-1.7.1.min.js"></script>
	 	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
	 	<script type="text/javascript" src="../../../base/js/new/base.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
	 	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/ccommon/CommonClear.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/DhtmUtilTable.js"></script>
		<script type="text/javascript" src="../../../base/js/tree/tree.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/util/widget/widget.list.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/util/widget/widget.list.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/RoadBuild.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/roadbuild.jquery.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Flow.js"></script>
		<script type="text/javascript">
		   var p_alarmId = "<%=id%>";
		   var p_time = "<%=systime%>";
		   var alarmId = "<%=alarmId %>";
		   var p_bulidState = "<%=bulidState%>";
           var p_isScoring = "<%=isScoring%>";
		   var jgbh = "<%=jgbh%>";
		   function showPlanInfo(obj,id){
			   var plan = $(id);
			   if(obj.checked == true){
				   plan.style.display = "inline";
				   $("plan").innerText = "";
			   }else if(obj.checked == false) {
				   plan.style.display = "none";
			   }else {
				   alert("e_p(obj.checked):"+obj.checked);
			   }
			}
		   
			function readOnlyRolanenum(id){
				var ckb = $(id);
				if(ckb.checked == true){
					$('ROLANENUM').disabled=true;
					$('ROLANENUM').style.display = "none";
					$('ROLANENUM').value="";
				}else if (ckb.checked == false){
					$('ROLANENUM').disabled=false;
					$('ROLANENUM').style.display = "inline";
					$('ROLANENUM').value="";
				}else{
					alert("e_p(ckb.checked):"+ckb.checked);
				}
			}
			
			function showWarnning(state){
				if(state != 0 ){
					alert("该施工占道信息已到预计的结束时间，请解除施工或者更新预计结束时间。");
				}
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
				font-size:12px;
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
				width:15%;
				height:35;
				
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
				width:20%;
				height:35;
			}
			.textwidth{
				width: 160px;
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
<body bgcolor="#FFFFFF" onload="getRoadBuildInfo(alarmId,jgbh);showScoring(p_isScoring);showWarnning(<%=resNum%>);">
	
	<div style="text-align: center;width: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #a5d1ec" align="center" id="fieldset">
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
			<div id="showLength" style="color:red;display: none;"> </div>
			<table border="1" id="mainTableId" class="table2" width="100%" cellpadding="0" cellspacing="0">
				<tr style="display: none">
					<td colspan="4">
						<input id="jgid" type="text" value="<%=deptcode%>" readonly></input>
						<input id="jgbh" type="text" value="<%=jgbh%>" readonly></input>
						<input id="ALARMID" type="text" value="<%=alarmId%>" readonly></input><%--ALARMID--%>
						<input id="EVENTSOURCE" type="text" value="002022" readonly></input><%--警情上报系统--%>
						<input id="EVENTTYPE" type="text" value="001023" readonly></input><%--施工占道--%>
						<%--EVENTSTATE  事件状态--%>
						<input id="ALARMUNIT" type="text" value=<%=deptcode%> readonly></input><%--报警机构--%>
						<input id="TITLE" type="text" value=" 发生 施工占道" readonly></input><%--标题--%>
						<input id="ALARMREGIONID" type="text" value=<%=deptcode%> readonly></input><%--报警辖区编号--%>
						<input id="ALARMREGION" type="text" value=<%=deptname%> readonly></input><%--报警辖区--%>
						<input id="REPORTUNIT" type="text" value=<%=deptcode%> readonly></input><%--填报单位--%>
						<input id="REPORTPERSON" type="text" value=<%=uname%> readonly></input><%--填报人--%>
						<input id="page" type="hidden" value="<%=insrtOrUpdt%>" readonly></input>
					</td>
				</tr>
				<!-- 
				<tr>
					<td colspan="4" style="padding: 0px;">
						<table cellspacing="0" cellpadding="0" style="border-collapse: collapse;">
							<tr>
								<td class="tdtitle" bgcolor="#F0FFFF" style="width:21%">
									<label id="roadLevelLabelId">道路等级</label>
								</td>
								<td class="tdvalue" style="padding-right: 18px;">
									<select id="roadLevelValueId"  class="textwidth" onchange="Road.roadLevel.onchange(this)"></select>
								</td>
								<td class="tdtitle" bgcolor="#F0FFFF" style="width:15%">
									道路名称
								</td>
								<td class="tdvalue" id="alarmRoad_TrafficCrowd_td" style="padding-right: 10px;">
								</td>
								<td class="tdtitle" bgcolor="#F0FFFF" style="width:15%">路段备注</td>
								<td class="tdvalue">
									<input type="text" id="ROADNAME" class="textwidth" style="border: 1px #7B9EBD solid; width:120px;margin-right: 10px;" name="editinput" maxlength=40>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				 -->
				 <tr>
					<td class="tdtitle" bgcolor="#F0FFFF"><label id="roadLevelLabelId">道路等级</label></td>
					<%
					if (insrtOrUpdt.equals("1") || insrtOrUpdt.equals("2")) {
					%>
					<td class="tdvalue" colspan="1">
					<%
					}else {
					%>
					<td class="tdvalue" colspan="3">
					<%
					}
					%>
						<select id="roadLevelValueId"  class="textwidth" onchange="Road.roadLevel.onchange(this)"></select>&nbsp;&nbsp;<font size='1' color='red'>※</font>
				    </td>
				    	<% 
						if (insrtOrUpdt.equals("1") || insrtOrUpdt.equals("2")) {
						%>
						<td class="tdtitle" bgcolor="#F0FFFF">
							施工状态
						</td>
						<td class="tdvalue" colspan="1">
							<% if (bulidState.equals("570005")) {
							%>
							<span style="color: red;">道路施工中</span>
							<% 
							}else if (bulidState.equals("570006")) {
							%>
							<span style="color: green;">道路施工完毕</span>
							<%
							} 
							%>
						</td>
						<%
						}
						%>
				</tr>
				<tr>
					<td class="tdtitle" bgcolor="#F0FFFF">道路名称</td>
					<td  id="alarmRoad_TrafficCrowd_td"  class="tdvalue"> </td>
					<td class="tdtitle" bgcolor="#F0FFFF">
						<label id="roadLevelLabelId">路段备注</label>
						<br>（请填至镇、乡或村路段）
					</td>
					<td class="tdvalue" >
						<input type="text" id="ROADNAME" class="textwidth"  name="editinput" maxlength=40>&nbsp;&nbsp;<font size="1" color="red">※</font>
				    </td>
				</tr>
                <tr>
                    <td class="tdtitle"  bgcolor="#F0FFFF">方向</td>
                    <td class="tdvalue"  colspan="3">
                        <input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_1" checked><span id="rdForward">下行</span>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" value="0" name="RADIOTYPE" id="RADIOTYPE_2"><span id="rdBack">上行</span>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="radio" value="2" name="RADIOTYPE" id="RADIOTYPE_3"><span id="double">双向占道</span>
                    </td>
                </tr>
				<tr>
					<td class="tdtitle" bgcolor="#F0FFFF">占道开始里程</td>
					<td class="tdvalue">
						K<input type="text" id="KMVALUE" class="text" name="editinput" maxlength="6">
						+<input type="text" id="MVALUE" class="text" name="editinput"  maxlength="3" value="0" >米 &nbsp;&nbsp;<font size="1" color="red">※</font>
<%--						<input type="text" id="KMVALUE" class="textwidth" name="editinput" style="border: 1px #7B9EBD solid"  maxlength=6>(千米)--%>
				    </td>
					<td class="tdtitle" bgcolor="#F0FFFF">占道结束里程</td>
					<td class="tdvalue">
						K<input type="text" id="EndKMVALUE" class="text" name="editinput" maxlength="6">
						+<input type="text" id="EndMVALUE" class="text" name="editinput"  maxlength="3" value="0">米 &nbsp;&nbsp;<font size="1" color="red">※</font>
<%--					<input type="text" id="EndKMVALUE" class="textwidth" name="editinput" style="border: 1px #7B9EBD solid" maxlength=6>(千米)				--%>
					</td>
				</tr>
				<tr align="center">
					<td class="tdtitle"  bgcolor="#F0FFFF">全封闭</td>
					<td class="tdvalue">
						<input type="radio" value="0" name="ISALLCLOSE" id="ISALLCLOSE_0" style="margin-left: 20px;" onchange="onRadioSelectChange()" checked><span>否</span><!-- onclick="readOnlyRolanenum('ISALLCLOSE_1')" -->
						<input type="radio" value="1" name="ISALLCLOSE" id="ISALLCLOSE_1" style="margin-left: 20px;" onchange="onRadioSelectChange()"><span>是</span><!-- onclick="readOnlyRolanenum('ISALLCLOSE_1')" -->
					</td>
					<td class="tdtitle"  bgcolor="#F0FFFF">占用车道数</td>
					<td class="tdvalue" >
						<input type="text" id="ROLANENUM"  name="editinput"  class="textwidth" maxlength="2">
					</td>
				</tr>
				<tr>
					<td class="tdtitle" bgcolor="#F0FFFF">施工开始时间</td>
					<td class="tdvalue" >
						<input type="text" id="CaseHappenTime" style="border: 1px #7B9EBD solid" class="textwidth" name="editinput" value="" readonly onclick="SelectDateTime(this)">&nbsp;&nbsp;<font size="1" color="red">※</font>
					</td>
					<td id="caseETDesc" class="tdtitle" bgcolor="#F0FFFF">施工预计结束时间</td>
					<td class="tdvalue" >
						<input type="text" id="CaseEndTime" class="textwidth" name="editinput" style="border: 1px #7B9EBD solid" value="" readonly onclick="SelectDateTime(this)">&nbsp;&nbsp;<font size="1" color="red">※</font>
					</td>
				</tr>
				<tr style="display: none">
					<td class="tdtitle" bgcolor="#F0FFFF">
						分流管制措施
					</td>
					<td class="tdvalue"  colspan="3">
						<input id="ISHAVEPLAN" type="checkbox"  onclick="showPlanInfo(this,'planDescTrId')" bgcolor="#F0FFFF">
					</td>
				</tr>
				<tr id="planDescTrId" >
					<td class="tdtitle" bgcolor="#F0FFFF">
						分流管制措施
					</td>
					<td class="tdvalue" colspan="3" >
						<div id="PLAN_DIV"  bgcolor="#F0FFFF"><textarea id="PLAN" name="editinput" rows="4" style="border: 1px #7B9EBD solid" style="width:<%=insrtOrUpdt==""?"84.8%":"85.4%" %>"
						 onkeyup="this.value==''?$('ISHAVEPLAN').checked=false:$('ISHAVEPLAN').checked=true" ></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font></div><!--557 -->
					</td>	
				</tr>
				<!--Modify by xiayx 2012-3-7-->
				<!--添加近期路况提示-页面布局              -->
				<tr>
					<td class="tdtitle" bgcolor="#F0FFFF">
						近期路况提示
					</td>
					<td id="tdRoadState" class="tdvalue"  colspan="3">
						<div id="recentRoadStateHistory" style="display: none;width: 85.4%;padding: 5px 5px 0px 5px;margin-bottom: 10px;"></div>
						<textarea id="recentRoadState" rows="4" style="border: 1px #7B9EBD solid" style="width:<%=insrtOrUpdt==""?"84.8%":"85.4%" %>" 
						title="如果施工已有一段时间，根据前段时间施工以来的路面状况（如车辆行驶正常、缓慢、拥堵等），估计出今后几天内的大致路况；如果是上报施工占道信息，则根据您的经验，进行填写。"></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font></div>
					</td>
				</tr>
				<!--Modification finished-->
				<tr >
				    <td class="tdtitle" bgcolor="#F0FFFF">
				    	填报单位<input type="hidden" id="rjgid" value="" />
				    </td>
					<td class="tdvalue">
						<input type="text" id="REPORTUNITVALUE" class="textwidth" name="editinput" style="border: 1px #7B9EBD solid" value="<%=deptname %>">
					</td>
					<td class="tdtitle" bgcolor="#F0FFFF">填报人</td>
					<td class="tdvalue">
						<input type="text" id="REPORTPERSONVALUE" style="border: 1px #7B9EBD solid" class="textwidth" name="editinput" value="<%=personName %>">&nbsp;&nbsp;<font size="1" color="red">※</font>
					</td>
				</tr>
				
				<tr>
					<td class="tdtitle" bgcolor="#F0FFFF">填报时间</td>
					<td class="tdvalue" colspan="3">
						<input type="text" id="REPORTTIME" class="textwidth" name="editinput" value="<%=daytime %>">
					</td>
					
				</tr>
				
				<tr id="dispatchTr" height="35" style="display:none">
                     <td class="tdtitle" align="center" bgcolor="#F0FFFF">
                    	        转发单位
                    	  <input type="hidden" id="jgmcId" value="" />
                     </td>
                     <td id="dispatchTd" class="tdvalue" colspan="3" >
                     	<input id="jgmc" type="text" value="" style="width: 600px;" readonly="readonly"/>
                     </td>
                </tr>
                <tr id="zodbzTr" style="display: none;">
					<td class="tdtitle" bgcolor="#F0FFFF">总队备注</td>
					<td id="zodbzTd" class="tdvalue" colspan="3">
						<textarea id="zodbz" style="width:540;height:80px;" ></textarea>
					</td>
				</tr>
                <tr id="scoringTR" style="display: none;">
                    <td id="scoringDescTD" class="tdtitle" align="center" bgcolor="#F0FFFF">
                        采信计分
                    </td>
                    <td id="scoringValueTD" class="tdvalue" colspan="3">
                        <input type="radio" name="scoring" id="scoringRbt">计分
                        <input type="radio" name="scoring" id="noScoringRbt" style="margin-left: 10px;">不计分
                    </td>
                </tr>
			</table>
		</fieldset>
	</div>
	<div style="text-align: center;width: 2%;height: 2%;"></div>
	<div style="width:95%;text-align: center;">
		<input class="btn" type="button" value=" 打 印 " id="close" style="margin-right:30px; display : none" onclick="printWord();">&nbsp;&nbsp;&nbsp;
		<%
		if(insrtOrUpdt.equals("")){
		%>
		<input class="btn" type="button" value=" 报 送 " id="saveData_Accident" style="margin-right:30px;" 
			onclick="modify(<%=insrtOrUpdt%>);">
		<input class="btn" type="button" value=" 取 消 " id="saveData_Accident" 
			onclick="resetAllValue();">
		<%
		}else if(insrtOrUpdt.equals("2")) {%>
            <input class="btn" type="button" style="margin-right:30px;display: none;"
               value=" 确 认 " id="scoringBtn" onclick="scoring(p_isScoring);">
			<input class="btn" type="button" value=" 关 闭 " id="close" onclick="window.close();">&nbsp;&nbsp;&nbsp;
		<%}else if(insrtOrUpdt.equals("1")){%>
		<%if(jgbh.length()==2){ %>
			<input id="update"  class="btn" type="button" value=" 保 存 " style="margin-right:30px;" 
			onclick="updateZodbz()">&nbsp;&nbsp;&nbsp;
		<%  }else{ %>
		<input id="update"  class="btn" type="button" value=" 更 新 " style="margin-right:30px;" 
		onclick="modify(<%=insrtOrUpdt%>);">&nbsp;&nbsp;&nbsp;
		<% } %>
			<input id="relieve" class="btn" type="button" value=" 解 除 "  style="margin-right:30px;display: none;" 
			onclick="doUpdate($('ALARMID').value)">&nbsp;&nbsp;&nbsp;
			<input class="btn" type="button" style="margin-right:30px;display: none;"
				value=" 转 发 " id="dispatch"
				onclick="dispatch()">&nbsp;&nbsp;&nbsp;
			<input class="btn" type="button" value=" 关 闭 " id="close" onclick="window.close();">&nbsp;&nbsp;&nbsp;
		<%}%>
	</div>
</body>
</html>
<!--2011-11-2更新，雷适兴-->