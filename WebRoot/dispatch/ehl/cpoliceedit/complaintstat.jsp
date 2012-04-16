<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@page import="com.ehl.dispatch.common.FlowUtil"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("930302");	
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
		<title><%=title %></title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript"
			src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript"
			src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript"
			src="../../js/ceditpolice/complaintstat.js"></script>
		<script type="text/javascript"
			src="../../js/ceditpolice/DepartmentSelect.js"></script>
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
	<body style="background: none; padding-top: 0px;" onLoad="initPageInfo('<%=jgbh %>');">
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
														<span class="currentLocationBold">您当前的位置</span>：<%=title %>
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
											<table width="100%" border="0" cellspacing="3" cellpadding="0">
												<tr>
													<td class="currentLocation" style="text-align: right" width="80">
														<div id="hourAndDate">
															统计条件：
														</div>
													</td>
													<td width="74">
														<select style="width:74" id="alarmTotalRadio" name="alarmTotalRadio" onChange="setTimeSelect();">
														    <option value="1" selected>时间</option>
														    <option value="2">流转状态</option>
														    <option value="3">投诉类别</option>
													    </select>
													</td>
													<td class="currentLocation" width="90" style="text-align: right">时 间：</td>
													<td width="50"  >
														<select style="width:50" id="timeRadio" onChange="setTimeRadio();">
														    <option value="2" selected>日</option>
														    <option value="3">周</option>
														    <option value="4">月</option>
														    <option value="5">季度</option>
														    <option value="6">半年</option>
														    <option value="7">年</option>
													    </select>
                                                    </td>
													<td width="79" id="ResetTimeName" align="right">起止日期：</td>
													<td colspan="3" width="300"  id="ResetTime"><input name="text2" type="text" style="width:80"
															class="text" id="STARTTIME" onClick="SelectDate(this,0);" />
														-
														<input name="text" type="text" style="width:80"
															class="text" id="ENDTIME" onClick="SelectDate(this,0);" />
													</td>
													<td width="100">
														<table id="table1" width="100">
															<tr>
																<td style="text-align: right">
																	<div class="lsearch">
																		<a href="#"
																			onclick="searchTotalInfo('<%=jgbh %>');"
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
                                                	<td class="currentLocation" style="text-align: right" width="80">
														<div id="hourAndDate">
															统计单位：
														</div>
													</td>
													<td width="64">
														<select style="width:74" id="departType" name="departType" onChange="resetDepartType('<%=jgid %>')">
														    <option value="1" selected>全部</option>
														    <option value="2">支队统计</option>
														    <option value="3">大队统计</option>
													    </select>
													</td>
													<td class="currentLocation" style="text-align: right" width="90">
														<div id="hourAndDate">被投诉单位：</div>
													</td>
													
													<td colspan="5">
													<table>
													<tr>
													<td>
														<div id="showDwInfo" valign="middle">
															<input type="text" id="TSJGID" style="width:285;" readOnly/>
													      	<img src="../../images/popup/btnselect.gif" alt="选择单位" align="absmiddle"  style="cursor:hand;" onClick="setTree('<%=jgid %>','TSJGID','125','155','400','300')">
													    </div>
                                                    </td>
                                                    
                                                    <td class="currentLocation" style="text-align: right" width="60">
														<div id="hourAndDate">图形：</div>
													</td>
													<td>
														<select style="width:60" id="chartType">
														    <option value="line" selected>折线图</option>
														    <option value="bar">柱状图</option>
														    <%--<option value="pie">饼形图</option>--%>
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
												<span class='currentLocationBold'>各单位投诉量统计</span>
											</div>
										</td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td id="tdData">
											<!--数据表格2009-04-15 -->
											<div style="overflow:scroll;border:solid;border-width:1px;height:345px;width:784px;margin-left:5" id="overSpeedList"  align = "center" >
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
	</body>
</html>
