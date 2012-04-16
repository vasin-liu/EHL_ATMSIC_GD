<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@include file="../../Message.oni"%>
<%
	String[] strObj = DepartmentManage.getDeptInfo(request, "1").split(",");//获取单位信息串
	String jgid = strObj[0];//单位编码
	String jgmc = strObj[1];//机构名称
	String ccbm = strObj[2];//机构层次编码
	//String jgid="441905000000";//12位机构编码
	String jgbh;//总队(2位),支队(4位),大队(6位)
	if ("0000".equals(jgid.substring(2, 6))) {
		jgbh = jgid.substring(0, 2);
	} else if ("00".equals(jgid.substring(4, 6))) {
		jgbh = jgid.substring(0, 4);
	} else {
		jgbh = jgid.substring(0, 6);
	}
	System.out.println("机构查询条件: " + jgbh);
	System.out.println("机构层次编码: " + ccbm);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>值班重大信息维护</title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/materialTotalInfo.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../sm/js/pcs/PersonTree.js"></script>
		<style type="text/css">
		.td_font {
			font-size: 13px;
		}
		.deptDivClass {
					z-index:10000;
					position:absolute;
					display:inline;
					width:250;
					height:340;
				}
				table{
					font-size:9pt;
					font-weight:normal;
					margin-left:0px;
				}/*宽高行高背景不显示超过对象尺寸的内容*/
			.lsearch{
			     width:82px; 
				 height:22px; 
				 line-height:22px; 
				 background:url(../../images/dispatch/btn.png) no-repeat; 
				 overflow:hidden;
			}
			/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
			.lsearch a{ 
			     display:block; 
				 height:22px; 
				 background:url(../../images/dispatch/btn.png) center; 
				 text-decoration:none; 
				 line-height:22px;
				 overflow:hidden;
			}
			/*高度固定背景行高*/
			.lsearch a:hover{ 
			     height:22px; 
				 background:url(../../images/dispatch/btn.png) center center; 
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
			.scrolltable{
				border-bottom:1px solid #CCCCCC; 
				border-right:1px solid #CCCCCC; 
			}
			.scrolltable td,.scrollTable th{
			     border-left: 1px solid #CCCCCC; 
			     border-top: 1px solid #CCCCCC; 
			     padding: 1px; 
			}
			.scrollColThead {
			    position: relative; 
			    top: expression
			    (this.parentElement.parentElement.parentElement.scrollTop);
			    z-index:1;
			    overflow: hidden; 
			    height:25px;
			    width:100%
			    text-overflow:ellipsis
			}
			.rowstyle{
				height: 25px;
				cursor: hand; 
				align:center;
			}
			.scrollRowThead{
			    position: relative; 
			    left: expression(this.parentElement.parentElement.parentElement.parentElement.scrollLeft);
			    z-index:0;
			    text-align:left;
			    background-color: #B4C1E2;
			}
		</style>
	</head>
	<body style="background: none; padding-top: 0px;" onLoad="initPageInfo('<%=jgid%>','<%=jgbh %>','<%=jgmc %>');DateUser.setDateSE('STARTTIME','ENDTIME',1)">
		<table width="100%" height="100%" border="0" cellspacing="0"
			cellpadding="0">
			<!-- 头部 -->
			<tr style="display: none">
				<td>
				</td>
			</tr>
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
														<span class="currentLocationBold">您当前的位置</span>：事故统计
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
					<div align="right">
					</div>
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
														统计条件
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd" align="left">
										  <p>&nbsp;
												
											</p>
											<table width="96%" border="0" cellspacing="3" cellpadding="0">
												<tr>
													<td class="currentLocation" style="text-align: right" width="110">
														<div id="hourAndDate">
															统计类别：
														</div>
													</td>
													<td width="74">
														<input type="hidden" id="jgid" value="<%=jgid %>" />
														<input type="hidden" id="jgbh" value="<%=jgbh %>" />
														<select style="width:74" id="alarmTotalRadio" name="alarmTotalRadio" onChange="reSetTimeShow()" >
														    <option value="1" selected>警情趋势</option>
														    <option value="2">事故分类</option>
													    </select>
													</td>
													<td class="currentLocation"  width="110" style="text-align: right">统计单位：</td>
													<td width="30"  >
														<select style="width:50" id="timeType" name="timeType" onChange="setTimeRadio();">
														    <option value="1" selected>日</option>
														    <option value="3">月</option>
														    <option value="6">年</option>
													    </select>
													<td width="90" id="ResetTimeName" align="right">起止日期：</td>
													<td width="450"  id="ResetTime"><input name="text2" type="text" style="width:80"
															class="text" id="STARTTIME" onClick="SelectDate(this,0);" />
														-
														<input name="text" type="text" style="width:80"
															class="text" id="ENDTIME" onClick="SelectDate(this,0);" /></td>
													<td width="100">
														<table id="table1" width="100">
															<tr>
																<td style="text-align: right">
																	<div class="lsearch">
																		<a href="#"
																			onclick="searchTotalInfo();"
																			class="currentLocation"><span class="lbl">
																				统计</span> </a>
																	</div>
																	
																</td>
																<td style="text-align: right">&nbsp;
																	
															  </td>
															</tr>
														</table>
													</td>
												</tr>
                                               
                                                <tr>
													<td class="currentLocation" style="text-align: right" width="110">
														<div>统计机构：</div>
													</td>
													<td id="showDepartType" width="74">
														<select style="width:74" name="departType"  id="departType"  onChange="resetDepartType('<%=jgmc %>');">
														    <option value="1" selected>全省统计</option>
														    <option value="2">支队统计</option>
														    <option value="3">大队统计</option>
													    </select>
                                                    </td>
													<td class="currentLocation"  width="110" style="text-align: right">
														<div id="hourAndDate">报警单位：</div>
													</td>
													<td id="showDwInfo" colspan="3">
														<table>
															<tr>
																<td><input type="text" id="TSJGID" style="width:270;"  onclick="alert()" readOnly/></td>
																<td><img id="showDepInfo" src="../../images/popup/btnselect.gif" alt="选择单位" style="cursor:hand;" 
																onClick="setTree('<%=jgid %>','TSJGID','135','95','400','300');"></td>
																<td>
																	<span style="display: none;">图形：</span> 
																	<select style="width:60;display: none;" id="chartType">
																	   <option value="line" selected>折线图</option>
																	   <option value="bar">柱状图</option>
																	</select>
																</td>
															</tr>
														</table>
                                                    </td>
													<td>
														<table id="table1" width="100">
															<tr>
																<td style="text-align: right">
																	
																	<div class="lsearch">
																		<a href="#"
																			onclick="periodFlowChart();"
																			class="currentLocation"><span class="lbl">
																				统计图</span> </a>
																	</div>
																</td>
																<td style="text-align: right">&nbsp;
																	
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
											<p>&nbsp;
												
											</p>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<!-- 占行高部分 -->
									<tr>
										<td class="height">
											<div style='text-align:center;width:100%;line-height:22px; float:left;' id='msg'>
												<span class='currentLocationBold'>各单位警情量统计</span>
											</div>
										</td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td id="tdData">
											<!--数据表格2009-04-15 -->
											<div style="overflow:scroll;border:solid;border-width:1px;height:310px;width:784px;margin-left:5" id="overSpeedList"  align = "center" >
											</div>
											<!--数据表格2009-04-15--> 
										</td>
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
		<div id="showDiv2Id" style="display:none"></div>
	</body>
</html>
