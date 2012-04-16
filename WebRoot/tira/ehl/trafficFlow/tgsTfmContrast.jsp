<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%@ include file="../util/Included.html"%>

<html>
	<head>
<!--		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">-->
<!--		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />-->
		<title>省际卡口流量对比</title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../FusionChartsV3.1/JSClass/FusionCharts.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style1/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
	
		<style type="text/css">
			.lsearch{
			     width:82px; 
				 height:22px; 
				 line-height:22px; 
				 background:url(../../images/button/btn.png) no-repeat; 
				 overflow:hidden;
			}
			/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
			.lsearch a{ 
			     display:block; 
				 height:22px; 
				 background:url(../../images/button/btn.png) center; 
				 text-decoration:none; 
				 line-height:22px;
				 overflow:hidden;
			}
			/*高度固定背景行高*/
			.lsearch a:hover{ 
			     height:22px; 
				 background:url(../../images/button/btn.png) center center; 
				 line-height:22px;
			}
			/*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
			.lsearch .lbl{ 
			     display:block;
				 width:79px; 
				 height:15px; 
				 padding-left:3px; 
				 padding-top:0px; 
				 margin:0 auto; 
				 text-align:center; 
				 color:#ffffff; 
				 cursor:pointer;
			}
			/*颜色滤镜效果*/
			.lsearch a:hover .lbl{ 
			     color:#dae76b; 
				 filter:glow(color=#ffffff,strength=1);
			}
			
		</style>
	</head>
	<body style="background: none; padding-top: 0px;"  >
	<div id="showDivId"></div>
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
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
														<span class="currentLocationBold">您当前的位置</span>：省际卡口流量对比
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
														查询条件
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd">
										<div align="left" style="width: 100%; border: 1px solid #BFBFBF;">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												
												<tr height="10px">
													<td ></td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align:right">
														<label>
															选择卡口：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<input type="text" name="sj2" style="width:200;border: 1px #7B9EBD solid" id="sj2" maxLength="50" readonly   />
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															快捷时间单位：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
													<select style="width:200;border: 1px #7B9EBD solid">
														<option value="1"/>默认
														<option value="2"/>五一黄金周
														<option value="3"/>十一黄金周
														<option value="4"/>春运前阶段
														<option value="5"/>春运后阶段
													</select>
													</td>
												</tr>
												<tr height="10px">
													<td ></td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align:right">
														<label>
															开始日期：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<input type="text" name="sj2" style="width:200;border: 1px #7B9EBD solid" id="sj2" maxLength="50" readonly  onclick="SelectDateTime(this,0);" />
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															结束日期：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<input type="text" name="sj2" style="width:200;border: 1px #7B9EBD solid" id="sj2" maxLength="50" readonly  onclick="SelectDateTime(this,0);" />
													</td>
													
													<td  align="right">
														<div class="lsearch">
															<a href="#" class="currentLocation"><span class="lbl">分析</span>
															</a>
														</div>
													</td>
												</tr>
												<tr height="10px">
													<td ></td>
												</tr>
											</table>
											</div>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<!-- 占行高部分 -->
									<tr>
										<td class="height" id="chartTdId1"></td>
									</tr>
									<tr>
										<td class="height" id="chartTdId2"></td>
									</tr>
									<tr>
										<td class="height" id="chartTdId3"></td>
									</tr>
									<tr>
										<td class="height" id="chartTdId4"></td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<script type="text/javascript">
									   var chart1 = new FusionCharts("../../../FusionChartsV3.1/swf/StackedColumn3D.swf", "showDivId", "800", "310", "0", "0");
									   //chart.setDataURL("../../xml/patrolMileage.xml");		   
									   var str1 = " <chart palette='1' caption='省际卡口流量对比图' shownames='1' showvalues='0' showSum='1' decimals='0' overlapColumns='0' baseFont='宋体' baseFontSize='15' formatNumberScale='0'> " + 
											" <categories> " + 
											" <category label='潮州汕汾高速省际卡口' /> " + 
											" <category label='S334饶平三饶东门桥省际卡口' /> " + 
											" <category label='G324饶平省际卡口' /> " + 
											" <category label='梅州天汕高速省际卡口' /> " + 
											" <category label='G206平远省际卡口' /> " + 
											" </categories> " + 
											" <dataset seriesName='出省流量 单位(/辆)'  showValues='0'> " + 
											" <set value='25601' /> " + 
											" <set value='20148' /> " + 
											" <set value='17372' /> " + 
											" <set value='35407' /> " + 
											" <set value='38105' /> " + 
											" </dataset> " + 
											" <dataset seriesName='入省流量 单位(/辆)' showValues='0'> " + 
											" <set value='57401' /> " + 
											" <set value='41941' /> " + 
											" <set value='45263' /> " + 
											" <set value='117320' /> " + 
											" <set value='114845' /> " + 
											" </dataset> " + 
											" </chart> ";
									   
									   chart1.setDataXML(str1);
									   chart1.render("chartTdId1");
									</script>
									
									
									<tr>
									</tr>
									<!-- 部数据列表分end -->
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
		<div id="showDivId">
		
		</div>
	</body>
</html>
