<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@include file="../../Message.oni"%>
<%
	String[] strObj = DepartmentManage.getDeptInfo(request, "1").split(
	",");//获取单位信息串
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
	
	System.out.println("机构查询条件: " + jgmc);
	System.out.println("机构层次编码: " + ccbm);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>协查布控信息查询</title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet"
			type="text/css" />
		
		<style type="text/css">
.td_font {
	font-size: 13px;
}

.deptDivClass {
	z-index: 10000;
	position: absolute;
	display: inline;
	width: 250;
	height: 340;
}

table {
	font-size: 9pt;
	font-weight: normal;
	margin-left: 0px;
}

.lsearch {
	width: 82px;
	height: 22px;
	line-height: 22px;
	background: url(../../images/dispatch/btn.png) no-repeat;
	overflow: hidden;
}

/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
.lsearch a {
	display: block;
	height: 22px;
	background: url(../../images/dispatch/btn.png) center;
	text-decoration: none;
	line-height: 22px;
	overflow: hidden;
}

/*高度固定背景行高*/
.lsearch a:hover {
	height: 22px;
	background: url(../../images/dispatch/btn.png) center center;
	line-height: 22px;
}

/*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
.lsearch .lbl {
	display: block;
	width: 79px;
	height: 15px;
	padding-left: 3px;
	padding-top: 0px;
	margin: 0 auto;
	text-align: center;
	color: #ffffff;
	cursor: pointer;
}

/*颜色滤镜效果*/
.lsearch a:hover .lbl {
	color: #dae76b;
	filter: glow(color =           #ffffff, strength =           1);
}
</style>


		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../js/bcommon/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/ccommon/commonUtil.js"></script>
		<script type="text/javascript" src="../../js/cpolicequery/xcbkSearch.js"></script>
		<link href="../../css/common/group.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../js/ccommon/group.js"></script>
		<script type="text/javascript" src="../../js/ccommon/privilege.js"></script>
	</head>
	<body style="background: none; padding-top: 0px;" id="dt_example"
		onload="initSearchPage()">
		<!-- 应用场景编号 -->
		<input id="appid" type="hidden" value="<%=session.getAttribute("appid") %>" />
		<input id="operate" type="hidden" value="${operate}" />
		<table width="100%" height="100%" border="0" cellspacing="0"
			cellpadding="0">
			<!-- 头部 -->
			<tr style="display: none">
				<td>
					<input id="jgbh" type="text" value=<%=jgbh%> readonly></input>
					<input id="jgid" type="text" value=<%=jgid%> readonly></input>
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
														<!--  
														<div background-color="#106ead" width="100%">
															<table  border="1" borderColor='#a5d1ec' style="font-size: 12px;font-weight: bold;"> <tr>
															<td>&nbsp;&nbsp;&nbsp;选择待查阅事件类别&nbsp;&nbsp;&nbsp;</td>
															<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn1" onclick="getSearchPage()"> 重特大交通事故&nbsp;</td>
															<td >&nbsp;<input type="radio" name="radioBtn" id="radioBtn2" onclick="getSearchPage()">交通拥堵&nbsp;</td>
															<td bgcolor="#F0FFFF">&nbsp;<input type="radio" name="radioBtn" id="radioBtn3" onclick="getSearchPage()">协查通报&nbsp;</td>
															<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn4" onclick="getSearchPage()">施工占道信息&nbsp;</td>
															<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn5" onclick="getSearchPage()"><%=jgbh.length()==2?"其他重大情况/值班日志":"其他重大情况" %>&nbsp;</td>
															</tr>
															</table>
														</div>
														-->
														<table id="group"></table>
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
									<!-- 
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
									 -->
									<tr>
										<td class="sleektd">
											<%-- <div id="defaultCondition">
											<table table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr height="10px">
													<td colspan="8" align="left" style="text-algin:center;">
														<label style="font-size:12px;font-weight:bold;padding:12px;">
															查询条件：按时间查询
														</label>
													</td>
												</tr>
												<tr height="10px">
													<td></td>
												</tr>
												<tr height='30px;'>
													<td class="currentLocation" style="text-align: right;" width="75px">
														<label>
															填报时间：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<input type="text" name="dateS" style="width:105;border: 1px #7B9EBD solid" id="dateS" maxLength="50" readonly onclick="SelectDate(this,0);" />
														-
														<input type="text" name="dateE" style="width:105;border: 1px #7B9EBD solid" id="dateE" maxLength="50" readonly onclick="SelectDate(this,0);" />
													</td>
													<td colspan="4"></td>
													<td style="text-align: right" >
														<div class="lsearch">
															<a href="#"
																onclick="currPage=1;doSearch('<%=jgbh %>')"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
													</td>
													<td style="text-align: right" width="100px" >
														<div class="lsearch">
															<a href="#"
																onclick="showMoreCondition();"
																class="currentLocation"><span class="lbl">更多条件</span>
															</a>
														</div>
													</td>
												</tr>
												<tr height="10px">
													<td ></td>
												</tr>
											</table>
											</div> --%>
										<div id="moreCondition">
											<table width="100%" border="0px" cellspacing="0" cellpadding="0">
												<tr height="10px">
													<td colspan="1" width="73" ></td>
												</tr>
												<tr height="10px" id="defalutTitleTr">
													<td colspan="8" align="left" style="text-algin:center;">
														<label style="font-size:12px;font-weight:bold;padding:12px;">
															查询条件：按时间查询
														</label>
													</td>
												</tr>
												<tr height="30px">
													<td style="text-align: right" width="9%">
														<span style="text-align: right;width: 75px;">填报时间：</span>
														<input type="hidden" id="createTime" value=","/>
													</td>
													<td align="left" id="columnTd" colspan="3" width="40%">
																	<input id="dateS" type="text" style="width: 120px;"  readonly onClick="SelectDate(this,0);"  
																	onpropertychange="$('createTime').value=this.value + $('createTime').value.substring($('createTime').value.indexOf(','));"/>
																	-
																	<input id="dateE" type="text" style="width: 120px;"   readonly onClick="SelectDate(this,0);" 
																	 onpropertychange="$('createTime').value= $('createTime').value.substring(0,$('createTime').value.indexOf(',')+1)+this.value;"/>
													</td>
													<td class="currentLocation" style="text-align: right;" width="9%">
															<span id="tbdw_lable" style="text-align: right;width: 75px;display:none;">填报单位：</span>
															<input type="hidden" id="unit" maxLength="15" value=""/>
													</td>
													<td width="265px">
														<input type="text" id="unitDesc" maxLength="15" style="display:none;width: 260px;" onpropertychange="$('unit').value=G_jgID;"
                                                              ondblclick="showDepartTree('<%=ccbm%>','<%=jgid%>','<%=jgmc%>','unitDesc','70','525')" readonly/>
													</td>
													<td align="left">
														<img id="tbdw_img" src="../../images/popup/btnselect.gif" alt="选择机构" style="display:none;cursor: hand;" onclick="showDepartTree('<%=ccbm%>','<%=jgid%>','<%=jgmc%>','unitDesc','70','525')">
													</td>
													<td id="simpleButtomTd" style="width: 22%;">
                                                        <div class="lsearch" style="display:inline;float:right;margin-left:10px;">
                                                            <a href="#"
                                                               onclick="showMoreCondition();"
                                                               class="currentLocation"><span class="lbl" id="advancedLable">高级查询</span>
                                                            </a>
                                                        </div>
                                                        <div class="lsearch" style="dispaly:inline;float:right;">
                                                            <a href="#" onClick="searchXcbk()"
                                                               class="currentLocation"><span class="lbl">查询</span>
                                                        </div>
													</td>
													<%-- <td style="text-align: left;">
														<table border="1" cellpadding="0" cellspacing="0">
															<tr>
																<td>
																	<input type="text" id="unitDesc" maxLength="15" style="width: 160;" onpropertychange="$('unit').value=G_jgID;" readonly/>
																</td>
																<td>
																	<img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor: hand;" onclick="showDepartTree('<%=ccbm%>','<%=jgid%>','<%=jgmc%>','unitDesc','139','90')">
																</td>
															</tr>
														</table>
													</td> --%>
													<!-- <td style="text-align: left;">
														<table border="0" cellpadding="0" cellspacing="0">
															<tr>
																<td style="text-align: right">
																		<span style="text-align: right;width: 80px;">填报时间：</span>
																		<input type="hidden" id="createTime" value=","/>
																</td>
																<td align="left" id="columnTd">
																	<input id="dateS" type="text" style="width: 141;"  readonly onClick="SelectDate(this,0);"  
																	onpropertychange="$('createTime').value=this.value + $('createTime').value.substring($('createTime').value.indexOf(','));"/>
																</td>
																<td>
																	<span>—</span>
																</td>
																<td>
																	<input id="dateE" type="text" style="width: 141"   readonly onClick="SelectDate(this,0);" 
																	 onpropertychange="$('createTime').value= $('createTime').value.substring(0,$('createTime').value.indexOf(',')+1)+this.value;"/>
																</td>
															</tr>
														</table>
													</td> -->

													<!-- <td align="center" >
														<div class="lsearch">
															<a href="#" onClick="showExcelInfo()"
																class="currentLocation"><span class="lbl">导出Excel</span>
															</a>
														</div>
													</td> -->
													
												</tr>
												<tr height="30px" id="moreConditionTr" style="display:none;">
													<td style="text-align: right;width: 75px">
														<span  style="text-align: right;width: 75px;">号牌号码：</span>
													</td>
													<td  style="text-align: left;width: 160px;">
														<input type="text" id="carNumber" maxLength="15" style="width: 160px;" />
													</td>
													<td style="text-align: right;" width="75px">
														<span style="text-align: right;width: 75px;">号牌类型：</span>
													</td>
													<td id="cartypeTd" style="text-align: left; " width="100px">
													</td>
													<td style="text-align: right" width="75px">
														<span style="text-align: right;width: 75px;">车辆颜色：</span>
													</td>
													<td align="left" id="carcolorTd" style="text-align: left; " width="42%">
													</td>
													<!-- <td style="text-align: left; width: 50%">
														<table border="0" cellspacing="0" cellpadding="0">
															<tr height="35px;">
																<td style="text-align: right;">
																	<span style="text-align: right;width: 80px;">号牌类型：</span>
																</td>
																<td id="cartypeTd" style="text-align: left; ">
																</td>
																
																<td style="text-align: right">
																	<span style="text-align: right;width: 80px;">车辆颜色：</span>
																</td>
																<td id="carcolorTd" style="text-align: left; " >
																</td>
															</tr>
														</table>
													</td> -->
													
													<!-- <td style="text-align: center;width: 15%;">
														<div class="lsearch">
															<a href="#" onClick="searchXcbk()"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
													</td> -->
													
												</tr>
												<tr id="moreButtomTr" height='30px;' style="display: none;">
													<td colspan="5"></td>
													<td style="text-align: right;" colspan="2" align="right">
														<div class="lsearch" style="display:inline;float:right;margin-left:10px;">
															<a href="#"
																onclick="showMoreCondition();"
																class="currentLocation"><span class="lbl" id="simpleLable">简单查询</span>
															</a>
														</div>
														<div class="lsearch" style="dispaly:inline;float:right;">
															<a href="#" onClick="searchXcbk()"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
													</td>
													<!-- <td style="text-align: right"  width="100px">
														<div class="lsearch">
															<a href="#"
																onclick="showMoreCondition();"
																class="currentLocation"><span class="lbl">默认条件</span>
															</a>
														</div>
													</td> -->
												</tr>
												<tr height="10px">
													<td></td>
												</tr>
											</table>
										</div>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<!-- 占行高部分 -->
									<tr>
										<td class="height"></td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td id="tdData">
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
