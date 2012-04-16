<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%@ include file="../util/Included.html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<title>高速公路和沿线大队分析</title>
		<script type="text/javascript" src="../../js/road/RoadManageQuality.js"></script>
		<style type="text/css">
			td{
				font-size: 12px;
			}
			.tdesc{
				text-align: left;
				
			}
			.desc{
				text-align: right;
			}
			.kv{
				text-align: left;
			}
		</style>
<script type="text/javascript">
/*
统计项：
	统计项描述：
	统计项键列表：
	统计项值列表：
	统计项键：
	统计项值：
	统计项细分项列表：
	统计项细分项键：
	统计项细分项值：

高速公路名称，管辖大队名称，管辖里程数，部署警力数
日流量，日拥堵次数，日事故次数，日死亡人数	


 */

</script>
	</head>
	<body style="background: none; padding-top: 0px;"
		onload="setDateSE('dateStartInputId','dateEndInputId',1)">
		
		<!--  -->
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
															<img src="../../../base/image/cssimg/table/tb.gif"
																width="16" height="16" alt="img" />
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
					<table height="100%" width="100%" border="0" cellspacing="0"
						cellpadding="0">
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
													<td class="sleek textB">
														分析条件
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd">
											<table width="100%" border="1" cellspacing="0"
												cellpadding="0">
											<!-- 统计项和浮动项START -->
											<tr>
												<td>
													<table width="100%">
														<tr>
															<td>
																<!-- 统计项START -->
																<table border="1">
																	<tr>
																		<td rowspan="2"   valign="top" class="tdesc">统计项</td>
																		<td colspan="4" >&nbsp;</td>
																	</tr>
																	<tr>
																		<td class="desc">统计类别：</td>
																		<td class="kv">
																			<select>
																				<option value="" label="高速公路" selected="selected"/>
																				<option value="" label="大队" />
																			</select>
																		</td>
																		<td class="desc">统计细分类别：</td>
																		<td class="kv">
																			<select>
																				<option value="" label="全部" />
																				<option value="" label="父" />
																				<option value="" label="子" />
																			</select>
																		</td>
																	</tr>
																</table>
																<!-- 统计项END -->
															</td>
															<td>
																<!--浮动项START -->
																<table border="1" width="100%"> 	
																	<tr>
																		<td rowspan="2" valign="top" class="tdesc">浮动项</td>
																		<td colspan="4" >&nbsp;</td>
																	</tr>
																	<tr>
																		<td class="desc">浮动类别：</td>
																		<td class="kv">
																			<select>
																				<option value="" label="无" selected="selected"/>
																			</select>
																		</td>
																		<td class="desc">浮动细分类别：</td>
																		<td class="kv">
																			<select>
																				<option value="" label="无" />
																			</select>
																		</td>
																	</tr>
																</table>
																<!-- 浮动项END -->																	
															</td>
														</tr>
													</table>
												</td>
												<td rowspan="3">
													<input id="" type="button" value="分析" />
												</td>
											</tr>
											<!-- 统计项和浮动项END -->
											<!-- 数据项START -->
											<tr>
												<td >
													<table border="1" width="100%">
														<tr>
															<td rowspan="2" valign="top" class="tdesc">数据项</td>
															<td colspan="2">&nbsp;</td>
														</tr>
														<tr>
															<td class="desc">数据类别：</td>
															<td class="kv">
																管辖里程<input id="" type="checkbox" value="" />
																部署警力<input id="" type="checkbox" value="" />
																日流量<input id="" type="checkbox" value="" />
																日拥堵次数<input id="" name="optionalDataItem" type="radio" value="" />
																日事故宗数<input id="" name="optionalDataItem" type="radio" value="" />
																日死亡人数<input id="" name="optionalDataItem" type="radio" value="" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- 数据项START -->
											<!-- 筛选项START -->
											<tr>
												<td align="left">
													<table border="1" width="100%">
														<tr>
															<td rowspan="2" valign="top" class="tdesc">筛选项</td>
															<td>&nbsp;</td>
														</tr>
														<tr>
															<td>
																<table border="1" width="100%">
																	<tr>
																		<td class="desc">
																			高速公路：
																		</td>
																		<td class="kv">
																			<select>
																				<option value="" label="广深高速" selected="selected"/>
																				<option value="" label="京珠高速" />
																			</select>
																		</td>
																		<td class="desc">
																			起止日期：
																		</td>
																		<td class="kv">
																			<input id="" type="text" value="" onclick=""/> —
																			<input id="" type="text" value="" onclick=""/>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- 筛选项END -->
											</table>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<tr>
										<td class="height">
										</td>
									</tr>
									<tr>
										<td id="showTdId" class="sleektd" align="center">
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
