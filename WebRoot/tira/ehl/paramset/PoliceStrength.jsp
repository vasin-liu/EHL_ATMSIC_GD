<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>
<%@ include file="../util/Included.html"%>
<%
Object uname = session.getAttribute(com.ehl.base.Constants.PNAME_KEY);
String deptId = "";
String deptName = "";
int roadLength = 0;
int policeStrength = 0;
try{
	   String deptInfo =  DepartmentManage.getDeptInfo(request,"1");
	   deptId = deptInfo.split(",")[0];
	   deptName = deptInfo.split(",")[1];
}catch(Exception e){
    e.printStackTrace();
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<title>警力设置</title>
		<script type="text/javascript" src="../../js/paramset/PoliceStrength.js"></script>

	</head>
	<body style="background:none; padding-top:0px;" onload="getRoad()"><!--  -->
		<table width="100%" height="100%" border="0" cellspacing="0"
			cellpadding="0">
			<!-- 头部 -->
			<tr>
				<td height="30" class="wTableTopCenter">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="30" class="wTableTopLeft"></td>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="46%" valign="middle">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="5%">
														<div align="center">
															<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
														</div>
													</td>
													<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>：综合分析-参数设置-大队警力部署
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
							<td width="16" class="wTableTopRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 头部end -->
			<!-- 数据 -->
			<tr>
				<td>
					<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="wTableCenterLeft"></td>
							<td class="wTableCenterCenter" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td align="left" class="height"></td>
									</tr>
									<tr>
										<td align="left">
											<table width="141" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td class="sleek textB">设置条件</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd">
											<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
												<tr align="center" height="30">
													<!--
											        <td id="zdDescTdId" align="right" class="currentLocation">
														<p id="zdDescPId" align="right" >支队：</p>
													</td>
													<td id="zdCodeTdId" align="left">
														<script type="text/javascript">
															//dept.createZdSel("zdCodeTdId",true,"ddCodeTdId",false);
														</script>
													</td>
													
													<td id="ddDescTdId" align="right" class="currentLocation">
														<p id="ddDescPId" align="right" >大队：</p>
														<input id="roadLevelInputId" type="hidden" value="0">
													</td>
													<td id="ddCodeTdId" align="right" class="currentLocation">
														
													</td>
													 -->
														 <td id="jgDescTdId" align="right" class="currentLocation">
															<p id="jgDescPId" align="right">交警机关：</p>
														 </td>
														<td id="jgnameTdId" align="right" colspan="3" class="currentLocation" >
															<p id="jgnamePId" align="left"><%=deptName%></p>
															<input id="deptId" type="hidden" value="441909000000">
														</td>
														<td rowspan="4">
														
														</td>
													</tr>
													
													<tr  align="center" height="30">
														<td id="roadTypeDescTdId" align="right"  class="currentLocation">
															<p id="roadTypeDescPId" align="right">道路类型：</p>
														</td>
														<td id="roadTypeCodeTdId" align="left" id="year">
															<select id="roadTypeCodeSelId" onchange="getRoad()" style="width:100px;" >
																<option value="0" selected>高速公路</option>
<%--																<option value="1">国   &nbsp; &nbsp; &nbsp; 道</option>--%>
<%--																<option value="2">省   &nbsp; &nbsp; &nbsp; 道</option>--%>
															</select>
														</td>
														<td id="roadDescTdId" align="right" class="currentLocation">
															<p id="roadDescPId" align="right" >道路：</p>
														</td>
														<td id="roadCodeTdId" align="right" class="currentLocation">
															
														</td>
													</tr>
													
													<tr  align="center" height="30">
														<td id="roadLengthDescTdId" align="right" class="currentLocation">
															<p id="roadLengthDescPId" align="right" >道路长度：</p>
														</td>
														<td id="roadLengthCodeTdId" align="right" class="currentLocation">
															<input id="roadLengthCodeInputId" type="text" value="" style="width: 100px" />(千米)
														</td>
														
														<td id="policeStrengthDescTdId" align="right" class="currentLocation">
															<p id="policeStrengthDescPId" align="right" >部署警力：</p>
														</td>
														<td id="policeStrengthCodeTdId" align="right" class="currentLocation">
															<input id="policeStrengthCodeInputId" type="text" value="" style="width: 100px" />(人)
														</td>
													</tr>
													
													<tr  align="center" height="30">
												        <td id="analysisBtnTdId" align="right" colspan="4" >
															<div id="analysisDivId" class="search">
																<a id="analysisAId" href="javascript:setPoliceStrength('sysdate')">
																	<span id="analysisSpanId" class="lbl">设置当天值</span>
																</a>
															</div>
														</td>
														<td id="analysisBtnTdId" align="right"  class="currentLocation">
															<div id="analysisDivId" class="search">
																<a id="analysisAId" href="javascript:setPoliceStrength('to_date(\'1950-01-01\',\'yyyy-mm-dd\')')">
																	<span id="analysisSpanId" class="lbl">设置默认值</span>
																</a>
															</div>
														</td>
											   		</tr>
											   		<tr height="290">
											   			<td colspan="2"></td>
											   		</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<tr>
										<td class="height">
											
										</td>
									</tr>
								</table>
							</td>
							<td class="wTableCenterRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 数据end -->
			<!-- 尾部 -->
			<tr>
				<td height="35" class="wTableBottomCenter">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="35" class="wTableBottomLeft"></td>
							<td width="16" class="wTableBottomRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 尾部end -->
		</table>
	</body>
</html>
