<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>
<%@ include file="../util/Included.html"%>

<%
Object uname = session.getAttribute(com.ehl.base.Constants.PNAME_KEY);
String dmlb = com.ehl.tira.common.Constants.ACC_DMLB_XZQH;
String deptId = "";
try{
    
	   String deptInfo =  DepartmentManage.getDeptInfo(request,"1");
	   deptId = deptInfo.split(",")[0];
	   deptId = deptId.substring(0,4) + "00";
}catch(Exception e){
    e.printStackTrace();
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<title>事故宗数、死亡人数分析</title>
		<script type="text/javascript" src="../../js/util/Department.js"></script>
		<script type="text/javascript" src="../../js/accident/Account.js"></script>
		<script type="text/javascript">
		/*
		
		*/
		</script>
	</head>
	<body style="background:none; padding-top:0px;"  onload = ""><!--  -->
		
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
														<span class="currentLocationBold">您当前的位置</span>：综合分析-事故分析-交警机关和事故宗数、死亡人数分析
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
													<td class="sleek textB">分析条件</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr align="center" height="30">
											        <td id="dateSEDescTdId" align="right" class="currentLocation">
														<p id="dateSEDescPId" align="right" >起止时间：</p>
													</td>
													<td id="dateSECodeTdId" align="left">
														<select id="dateSECodeSelId" style="width:100px;" >
															<option value="month" >本月至今天</option>
															<option value="year" selected="selected">今年截至目前</option>
														</select>
													</td>
													<td id="deptDescTdId" align="right" class="currentLocation">
														<p id="deptDescPId" align="right" >交警机关：</p>
													</td>
													<td id="deptCodeTdId" align="right" class="currentLocation">
															<script type="text/javascript">
																dept.defZdCode = "<%=deptId%>";
																dept.zdCB = "doAnalysis(true)";
																dept.createZdSel("deptCodeTdId",true);
															</script>
													</td>
													<!--  
													<td id="deptDescTdId" align="right" class="currentLocation">
														<p id="deptDescPId" align="right" >行政区划：</p>
													</td>
													<td id="deptCodeTdId" align="right" class="currentLocation">
															<script type="text/javascript">
																var divisionClone = division;
<%--																division.dmlb = "<%=dmlb%>";--%>
																division.ccback = "doAnalysis(true)";
																division.createTable("deptCodeTdId");
<%--																division.getJgidByUname("<%=uname%>");--%>
															</script>
													</td>
													-->
													<td id="diedPersonNumDescTdId" align="right"  class="currentLocation">
														<p id="diedPersonNumDescPId" align="right">死亡人数：</p>
													</td>
													<td id="diedPersonNumCodeTdId" align="left" id="year">
														<select id="diedPersonNumCodeSelId" style="width:100px;" >
															<option value="0" selected>全部</option>
															<option value="3">三人及以上</option>
															<option value="5">五人及以上</option>
														</select>
													</td>
													
											        <td id="analysisTdId" align="right"  class="currentLocation">
														<div id="analysisDivId" class="search">
															<a id="analysisAId" href="javascript:doAnalysis()">
																<span id="analysisSpanId" class="lbl">分析</span>
															</a>
														</div>
													</td>
											    </tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<tr>
										<td class="height">
										</td>
									</tr>
									<tr> 
										<td id="showTdId" class="sleektd" align="center" >
											<div id="showDivId" width="700" height="350" style="overflow: scroll;">
												
											</div>
										</td>
									</tr>
									<tr>
										<td class="height"></td>
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
