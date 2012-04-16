<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%@ include file="../util/Included.html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<title>高速大队高速公路里程和总巡逻里程和总巡逻车数量</title>
		<script type="text/javascript" src="../../js/speedway/PatrolMileage.js"></script>
		
	</head>
	<body style="background:none; padding-top:0px;"  onload = "initValue()"><!--  -->
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
														<span class="currentLocationBold">您当前的位置</span>：综合分析-道路分析-高速公路和沿线大队分析
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
									<form>
									<tr>
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr align="center" height="25">
													<td class="currentLocation" style="text-align:right">
														<label>
															辖区范围：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<select style="width:200;border: 1px #7B9EBD solid" id="area" onchange="showDept()">
														</select>
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															所属大队：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="2">
														<select style="width:200;border: 1px #7B9EBD solid" id="dept">
															<option value="" selected>全部</option>
															<option value="1">一大队</option>
															<option value="2">二大队</option>
															<option value="3">三大队</option>
															<option value="4">四大队</option>
														</select>
													</td>
											    </tr>
											    <tr height="25">
											    	<td class="currentLocation" style="text-align:right">
														<label>
															分析开始时间：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<input type="text" style="width:200;border: 1px #7B9EBD solid" id="dateS" maxLength="50" readonly  onclick="SelectDate(this,0);" />
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															分析结束时间：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<input type="text" id="dateE" style="width:200;border: 1px #7B9EBD solid" maxLength="50" readonly  onclick="SelectDate(this,0);" />
													</td>
													
													<td id="analysisTdId" align="right"  class="currentLocation">
														<div id="analysisDivId" class="search">
															<a id="analysisAId" href="javascript:submit('false')">
																<span id="analysisSpanId" class="lbl">分析</span>
															</a>
														</div>
													</td>
											    </tr>
											</table>
										</td>
									</tr>
									</form>
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
