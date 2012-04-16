<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%@ include file="../util/Included.html"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<title>各辖区综合分析</title>
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
														<span class="currentLocationBold">您当前的位置</span>：各辖区综合分析
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
															辖区范围：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<select style="width:200;border: 1px #7B9EBD solid" id="sjlb">
															<option value="" selected>全部</option>
															<option value="border">粤东</option>
															<option value="ISBUS">粤西</option>
															<option value="ISSCHOOLBUS">粤北</option>
															<option value="ISDANAGERCAR">珠三角</option>
														</select>
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															所属大队：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="2">
														<input type="text"  style="width:200;border: 1px #7B9EBD solid" name="sj1" id="sj1" />
														<img src="../../images/button/btnselect.gif" alt="选择机构" style="cursor:hand;" >
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
														<div style="width:11;float:right"></div>
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
									   var chart1 = new FusionCharts("../../../FusionChartsV3.1/swf/MSColumn3D.swf", "showDivId", "800", "320", "0", "0");
									   //chart.setDataURL("../../xml/patrolMileage.xml");		   
									   var str1 = " <chart caption='各辖区综合分析图' shownames='1' showvalues='0' decimals='0' baseFont='宋体' baseFontSize='13' formatNumberScale='0'> " + 
													" <categories> " + 
													" 	<category label='湛江吴川大队' /> " + 
													" 	<category label='茂名一大队' /> " + 
													" 	<category label='肇庆第一大队' /> " +
													" 	<category label='惠州江南大队' /> " + 
													" 	<category label='汕尾市区大队' /> " + 
													" </categories> " + 
													" 	<dataset seriesName='事故宗数'  showValues='0'> " + 
													" 		<set value='601' /> " + 
													" 		<set value='481' /> " + 
													" 		<set value='372' /> " + 
													" 		<set value='407' /> " + 
													" 		<set value='105' /> " + 
													" 	</dataset> " + 
													" 	<dataset seriesName='违法宗数'  showValues='0'> " + 
													" 		<set value='201' /> " + 
													" 		<set value='118' /> " + 
													" 		<set value='772' /> " + 
													" 		<set value='207' /> " + 
													" 		<set value='505' /> " + 
													" 	</dataset> " + 
													" 	<dataset seriesName='辖区警力'  showValues='0'> " + 
													" 		<set value='21' /> " + 
													" 		<set value='38' /> " + 
													" 		<set value='52' /> " + 
													" 		<set value='37' /> " + 
													" 		<set value='55' /> " + 
													" 	</dataset> " + 
													" 	<dataset seriesName='辖区里程'  showValues='0'>  " + 
													" 		<set value='5000.65' /> " + 
													" 		<set value='4835.76' /> " + 
													" 		<set value='8722.18' /> " + 
													" 		<set value='7557.31' /> " + 
													" 		<set value='2633.68' /> " + 
													" 	</dataset> " + 
													" 	<dataset seriesName='总巡逻里程'   showValues='0'> " + 
													" 		<set value='7401.85' /> " + 
													" 		<set value='1941.19' /> " + 
													" 		<set value='5263.37' /> " + 
													" 		<set value='7320.16' /> " + 
													" 		<set value='4845.27' /> " + 
													" 	</dataset> " + 
													" 	<dataset seriesName='日均流量'  showValues='0'> " + 
													" 		<set value='4401' /> " + 
													" 		<set value='5941' /> " + 
													" 		<set value='2263' /> " + 
													" 		<set value='3320' /> " + 
													" 		<set value='7845' /> " + 
													" 	</dataset> " + 
													" </chart> "
									   
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
