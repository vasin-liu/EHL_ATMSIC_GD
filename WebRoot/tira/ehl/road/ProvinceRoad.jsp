<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%@ include file="../util/Included.html"%>
<%
	String uname = session.getAttribute(com.ehl.base.Constants.PNAME_KEY).toString();
String dmlb = com.ehl.tira.common.Constants.ACC_DMLB_XZQH;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<title>省道和沿线大队分析</title>
		<script type="text/javascript" src="../../js/road/RoadManageQuality.js"></script>
		
	</head>
	<body style="background:none; padding-top:0px;"  onload = "setDateSE('dateStartInputId','dateEndInputId',1)"><!--  -->
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
														<span class="currentLocationBold">您当前的位置</span>：综合分析-道路分析-省道和沿线大队分析
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
														<input id="dateStartInputId" style="width:70px;" value="" onClick="SelectDate(this,0);" readOnly/>
														--
														<input id="dateEndInputId" style="width:70px;" value="" onClick="SelectDate(this,0);" readOnly/>
													</td>
													<td align="right" class="currentLocation" style="display: none;">
														<select id="statTypeSelectId">
															<option value="roadName" selected>高速公路</option>
															<option value="jgmc" >大队</option>
														</select>
													</td>
													<td id="roadDescTdId" align="right" class="currentLocation">
														<p id="roadDescPId" align="right" >省道：</p>
														<input id="roadLevelInputId" type="hidden" value="2">
													</td>
													<td id="roadCodeTdId" align="right" class="currentLocation">
														<script type="text/javascript">
															var sql = "select roadid,roadname from t_oa_road_info where roadlevel=2";
															fillListBox("roadCodeTdId","roadSelId","110",sql,"全部","doAnalysis(true)","","");
														</script>
													</td>
													<td id="analysisTargetDescTdId" align="right"  class="currentLocation">
														<p id="analysisTargetDescPId" align="right">分析指标：</p>
													</td>
													<td id="analysisTargetCodeTdId" align="left" id="year">
														<select id="analysisTargetCodeSelId" style="width:100px;" >
															<option value="0" selected>事故宗数</option>
															<option value="1">死亡人数</option>
															<option value="2">拥堵次数</option>
														</select>
													</td>
													
											        <td id="analysisBtnTdId" align="right"  class="currentLocation">
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
											<div id="showDivId">
											
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
