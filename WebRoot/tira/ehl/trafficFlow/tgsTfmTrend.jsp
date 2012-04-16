<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%@ include file="../util/Included.html"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<title>省际卡口流量趋势</title>
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
														<span class="currentLocationBold">您当前的位置</span>：省际卡口流量趋势
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
														统计时间：
														</label>
													</td>
													<td class="currentLocation" id="columnTd" align="left" colspan="1">
														<input type="radio" name="check1" checked/>24小时趋势
														<input type="radio" name="check1"/>月度趋势
														<input type="radio" name="check1"/>年季趋势
														<input type="radio" name="check1"/>自定义
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															选择卡口：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<input type="text" name="sj2" style="width:200;border: 1px #7B9EBD solid" id="sj2" maxLength="50" readonly   />
													</td>
												</tr>
												<tr height="10px">
													<td ></td>
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
														<input type="text" name="sj2" style="width:200;border: 1px #7B9EBD solid" id="sj2" maxLength="50" readonly  onclick="SelectDate(this,0);" />
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															结束日期：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<input type="text" name="sj2" style="width:200;border: 1px #7B9EBD solid" id="sj2" maxLength="50" readonly  onclick="SelectDate(this,0);" />
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
									
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<script type="text/javascript">
									   var chart1 = new FusionCharts("../../../FusionChartsV3.1/swf/ScrollLine2D.swf", "showDivId", "810", "310", "0", "0");
									   //chart.setDataURL("../../xml/patrolMileage.xml");		   
									   var str1 = "<chart caption='省际卡口24小时流量趋势' numdivlines='9' lineThickness='2' showValues='0' anchorRadius='3' anchorBgAlpha='50' showAlternateVGridColor='1' numVisiblePlot='12' animation='0' baseFont='宋体' baseFontSize='15' formatNumberScale='0'> " + 
											" <categories > " + 
											" <category label='00:00' /> " + 
											" <category label='01:00' /> " + 
											" <category label='02:00' /> " + 
											" <category label='03:00' /> " + 
											" <category label='04:00' /> " + 
											" <category label='05:00' /> " + 
											" <category label='06:00' /> " + 
											" <category label='07:00' /> " + 
											" <category label='08:00' /> " + 
											" <category label='09:00' /> " + 
											" <category label='10:00' /> " + 
											" <category label='11:00' /> " + 
											" <category label='12:00' /> " + 
											" <category label='13:00' /> " + 
											" <category label='14:00' /> " + 
											" <category label='15:00' /> " + 
											" <category label='16:00' /> " + 
											" <category label='17:00' /> " + 
											" <category label='18:00' /> " + 
											" <category label='19:00' /> " + 
											" <category label='20:00' /> " + 
											" <category label='21:00' /> " + 
											" <category label='22:00' /> " + 
											" <category label='23:00' /> " + 
											" </categories> " + 
											" <dataset seriesName='G205蕉城北省际卡口' color='800080' anchorBorderColor='800080'> " + 
											" <set value='2673' /> " + 
											" <set value='1026' /> " + 
											" <set value='2470' /> " + 
											" <set value='2813' /> " + 
											" <set value='3961' /> " + 
											" <set value='3086' /> " + 
											" <set value='4415' /> " + 
											" <set value='4886' /> " + 
											" <set value='3252' /> " + 
											" <set value='5284' /> " + 
											" <set value='5391' /> " + 
											" <set value='5657' /> " + 
											" <set value='5847' /> " + 
											" <set value='2154' /> " + 
											" <set value='1165' /> " + 
											" <set value='1175' /> " + 
											" <set value='1190' /> " + 
											" <set value='1212' /> " + 
											" <set value='1241' /> " + 
											" <set value='308' /> " + 
											" <set value='401' /> " + 
											" <set value='481' /> " + 
											" <set value='851' /> " + 
											" <set value='250' /> " + 
											
											" </dataset> " + 
											" <dataset seriesName='G206平远省际卡口' color='FF8040' anchorBorderColor='FF8040'> " + 
											" <set value='2763' /> " + 
											" <set value='3149' /> " + 
											" <set value='3637' /> " + 
											" <set value='4015' /> " + 
											" <set value='4262' /> " + 
											" <set value='952' /> " + 
											" <set value='1448' /> " + 
											" <set value='1771' /> " + 
											" <set value='2316' /> " + 
											" <set value='4541' /> " + 
											" <set value='4837' /> " + 
											" <set value='5016' /> " + 
											" <set value='5133' /> " + 
											" <set value='5278' /> " + 
											" <set value='111' /> " + 
											" <set value='120' /> " + 
											" <set value='128' /> " + 
											" <set value='140' /> " + 
											" <set value='146' /> " + 
											" <set value='157' /> " + 
											" <set value='190' /> " + 
											" <set value='250' /> " + 
											" <set value='399' /> " + 
											" <set value='691' /> " + 
											" </dataset> " + 
											" <dataset seriesName='G324饶平省际卡口' color='FFFF00' anchorBorderColor='FFFF00' > " + 
											" <set value='3918' /> " + 
											" <set value='4140' /> " + 
											" <set value='4296' /> " + 
											" <set value='4519' /> " + 
											" <set value='4716' /> " + 
											" <set value='1334' /> " + 
											" <set value='1637' /> " + 
											" <set value='2056' /> " + 
											" <set value='2600' /> " + 
											" <set value='3070' /> " + 
											" <set value='3451' /> " + 
											" <set value='4881' /> " + 
											" <set value='5092' /> " + 
											" <set value='5249' /> " + 
											" <set value='115' /> " + 
											" <set value='141' /> " + 
											" <set value='175' /> " + 
											" <set value='189' /> " + 
											" <set value='208' /> " + 
											" <set value='229' /> " + 
											" <set value='252' /> " + 
											" <set value='440' /> " + 
											" <set value='608' /> " + 
											" <set value='889' /> " + 
											" </dataset> " + 
											" <dataset seriesName='潮州汕汾高速省际卡口' color='FF0080' anchorBorderColor='FF0080' > " + 
											" <set value='2365' /> " + 
											" <set value='2433' /> " + 
											" <set value='2559' /> " + 
											" <set value='2823' /> " + 
											" <set value='2867' /> " + 
											" <set value='1867' /> " + 
											" <set value='2198' /> " + 
											" <set value='1112' /> " + 
											" <set value='1192' /> " + 
											" <set value='1219' /> " + 
											" <set value='2264' /> " + 
											" <set value='2282' /> " + 
											" <set value='2867' /> " + 
											" <set value='2867' /> " + 
											" <set value='1298' /> " + 
											" <set value='1112' /> " + 
											" <set value='1192' /> " + 
											" <set value='1219' /> " + 
											" <set value='1264' /> " + 
											" <set value='1282' /> " + 
											" <set value='1365' /> " + 
											" <set value='1433' /> " + 
											" <set value='1559' /> " + 
											" <set value='1823' /> " + 
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
