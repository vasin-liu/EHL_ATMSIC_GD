<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.common.*" %>
<%
 	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	
	String path = request.getHeader("host");

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
%>
<!-- 
	 * 
	 * 版 权：北京易华录信息技术股份有限公司 2009
	 * 文件名称：watching.jsp
	 * 摘 要：指挥调度监控界面。显示未处理警情列表，需关注警情列表等信息。	 * 当前版本：1.0
	 * 作 者：LChQ  2009-4-8
	 * 修改人：
	 * 修改日期：	 *
 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
		<title>指挥调度监控界面</title> 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	 	<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
	 	<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
	 	<script src="../../../base/js/prototype.js" type="text/javascript"></script>
	 	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
		
		<script type="text/javascript" src="../../js/bpoliceWatch/dispatchWatching.js"></script>
		<script type="text/javascript" src="../../js/bcommon/utility/DhtmUtilTable.js"></script>
		<script type="text/javascript" src="../../js/bSupervise/supervise.js"></script>
		<script type="text/javascript" src="../../js/beditPolice/editPolice.js"></script>
		<script type="text/javascript" src="../../js/bcomplement/complementEdit.js"></script>
		<script type="text/javascript" src="../../js/bcommon/utility/ListChose.js"></script>
		<script type="text/javascript" src="../../js/bcommon/utility/comLogic.js"></script>
		<script type="text/javascript" src="../../js/bcommon/utility/DivHolder.js"></script>
		<script type="text/javascript" src="../../js/bcommon/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/bcommon/CommonClear.js"></script>
		<script type="text/javascript" src="../../js/bpoliceAdd/addPolice.js"></script>
		<script type="text/javascript" src="../../js/bpoliceDispose/disposePolice.js"></script>
		
	</head>
	<script type="text/javascript">
		
		window.attachEvent('onload',pageLoadInitHandler);
	 	var deptcode = '<%=deptcode%>';
		var userCountyText = '<%= deptname %>';
		var pname = '<%=pname%>';
		var pid = '<%=pid%>';
		var userNameText = '<%= uname %>';
		var mobilephone='<%= mobilephone%>'
		var path='<%=path%>';
		//显示警情时间范围（小时）
		var msgWatchingTimeRange = '<%= com.appframe.common.Setting.getString("msgWatchingTimeRange") %>';
		//刷新频率（单位：秒）
		var msgListRefreshInterval = '<%= com.appframe.common.Setting.getString("msgListRefreshInterval") %>';
		// alert(pname)
		function pageLoadInitHandler(){
			initWatching();		//初始化监控警情列表			
			// initPages();		
			// initSupervise();
			// initPages_FeedBack(userCountyText,pname);			
			initDispose();
			
			// $("mapTd").style.display="none";
			// $('btnAffairProcess').attachEvent('onclick',initFeedBackPage);
			$('btnMonitor').attachEvent('onclick',querySupervise);
			$("addPolice").attachEvent('onclick',addNewInfo);	
			$("btnAppraise").attachEvent('onclick',showAppraiseDetail);
			$("btnComplement").attachEvent('onclick',openComple);
			$('spanShowHourSetting').innerHTML = msgWatchingTimeRange;
		}
		
		
	</script>
 
	<body style="margin:0;">
		<table border="0" cellspacing="0" cellpadding="0" style="text-align: center; width: 100%; height: 100%;">
			<tr>
				<td width=2px>&nbsp;
				</td>
				<td valign=top  class="td_r_b">
					<table border="0" cellspacing="0" cellpadding="0" style="text-align: center; width: 100%; height: 100%;">
						<tr>
							<td align="center" valign="middle"  height=20px width=8%>
								<font style="text-align: left;width: 80%">
									<input type="button" value="新增警情" id="addPolice"  associateDiv="divEditAlarm" mapDiv="mapTd"> &nbsp; 
									<input type="button" value="警情处理" id="btnAffairProcess"  associateDiv="divFeedBack" > &nbsp; 
									<input type="button" value="查看督办" id="btnMonitor" style="display: none" associateDiv="divSupervise" > &nbsp; 
									<input type="button" value="查看评价" id="btnAppraise" style="display: none"  associateDiv="divAppraise" > &nbsp; 	
								</font>	
								<font style="text-align: right;width: 20%">				
									<input type="button" value="补录警情" id="btnComplement" style="display: none"  associateDiv="divComplement" >&nbsp;
								</font>
							</td>
							<td valign=top class="td_r_b">
								<fieldset style="border: 1px solid #ccc; valign: top; align: center">
									<legend style="border: 0px; font-weight: 700; font-size: 8pt;">
										警情信息(最近<span id="spanShowHourSetting"></span>小时)
									</legend>
									<table style="border: 0; height: 106px; cellpadding: 0; cellspacing: 0;width:98%">
										<tr  style="display: none">
											<td style="line-height:12px">&nbsp;
											<a href="#" id="ahrefAllAffair">
												<input type='radio' name="radioSortLink" checked style="width:12px;height:12px" id="radioAllAffair">
												需关注警情(共<span id="spanAllAffair"></span>起)
											</a>&nbsp;
											 </td>
											<td style="line-height:12px">&nbsp;<a href="#" id="ahrefComplete">
												<input type='radio' name="radioSortLink" style="width:12px;height:12px" id="radioCompleteAffair"/>
												处理完毕警情(共<span id="spanCompleteAffair"></span>起)
											</a>&nbsp;
											</td>
											
										</tr>
										<tr style="display: none">
											<td style="line-height:12px">&nbsp;
												<a href="#" id="ahrefProcessing">
												<input type='radio' name="radioSortLink" style="width:12px;height:12px" id="radioProcessingAffair"/>
													正在处理警情(共<span id="spanProcessingAffair"></span>起)
												</a>&nbsp;</td>
										 	<td style="line-height:12px">&nbsp;
												<a href="#" id="ahrefUnhandle">
												<input type='radio' style="width:12px;height:12px" name="radioSortLink" id="radioUnhandleAffair" />
													未处理警情(共<span id="spanUnhandleAffair"></span>起)
												</a> &nbsp;
											</td>	
										</tr>
											
										<tr>
											
											<!-- 未处理警情列表 -->
											<td width="100%" align=center  valign="top">
												<div id="divUnhandleList"  
													style="width:98%;vertical-align:text-top;height:90px;text-align:left;overflow:auto;border:1 solid #000;" > 
												</div>
											</td>
										</tr>
										<tr style="display: none">
											<td align=right colspan=2><input type="button" id="btnMerge" value="合并警情"/>&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
										<td colspan='2'>
											<div id="soundDiv">
											
											</div>
										</td>
										</tr>
									</table>
								</fieldset>
							 </td>
						 </tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width=2px>&nbsp;
				</td>
				<td >
					<table border="0" cellspacing="0" cellpadding=0 style="width:100%; height: 100%;">
						<!-- 操作按钮 -->
						<tr style="">
							
							
						</tr>
						<tr>
							<td valign="top" style="text-align:center">
								<div id="divAlarmInfo" style="width:100%;display:none">
									<jsp:include flush="true" page="../policeEdit/policeInfo.jsp"></jsp:include>
								</div>
								<div id="divEditAlarm" style="width:100%;display:none">
									<jsp:include flush="true" page="../bpoliceAdd/policeEdit.jsp"></jsp:include>
								</div>
								<div id="divFeedBack" style="width:100%;" >
									<jsp:include flush="true" page="../bpoliceDispose/disposePolice.jsp"></jsp:include>
								</div>
								<div id="divSupervise" style="width:100%;display:none">
									<jsp:include flush="true" page="../supervise/showSupervise.jsp"></jsp:include>
								</div>
								<div id="divAppraise" style="width:100%;display:none">
									<jsp:include flush="true" page="../appraise/appraiseDetail.jsp"></jsp:include>
								</div>
								<div id="divComplement" style="width:100%;display:none">
									<jsp:include flush="true" page="../complement/complementEdit.jsp"></jsp:include>
								</div>
							</td>
						</tr>
						
					</table>
				</td>
			</tr>
		</table>
		
		
	</body>
</html>
