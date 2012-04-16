<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="com.ehl.dispatch.cdispatch.action.MaterialInfoAction"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%@page import="com.ehl.sm.common.Constants"%>
<%@page import="com.ehl.sm.base.Constant"%>
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
	System.out.println("机构查询条件: " + jgbh);
	System.out.println("机构层次编码: " + ccbm);

	String sql = "SELECT person FROM T_OA_DUTY WHERE deptid = '"
			+ jgid
			+ "' and to_date(dtime, 'yyyy-mm-dd hh24:mi:ss') between to_date(to_char(sysdate,'yyyy-mm-dd')||' 08:30:00', 'yyyy-mm-dd hh24:mi:ss') and to_date(to_char(sysdate + 1,'yyyy-mm-dd')||' 08:30:00', 'yyyy-mm-dd hh24:mi:ss') order by dtime desc";
	String personName = StringHelper.obj2str(DBHandler
			.getSingleResult(sql));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>事故查阅</title>
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
<link href="../../../util/jquery/jquery-ui/themes/start/jquery-ui-1.8.17.custom.css"
	rel="stylesheet" media="screen" type="text/css" />
<link href="../../../util/jquery/jqgrid/ui.jqgrid.css"
	rel="stylesheet" media="screen" type="text/css" />
<link href="../../css/common/group.css" rel="stylesheet" type="text/css" />
    <link href="../../css/allMaterialInfo.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="../../../util/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	jQuery = $; //rename $ function
</script>
<script type="text/javascript" src="../../../base/js/prototype.js"></script>
<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
<script type="text/javascript"
	src="../../../base/js/calendar/CalendarDate.js"></script>
<script type="text/javascript"
	src="../../../base/js/calendar/CalendarDateTime.js"></script>
<script type="text/javascript"
	src="../../../base/js/list/FillListBox.js"></script>
<script type='text/javascript' src='${contextPath}base/js/new/base.js'></script>
<script type="text/javascript" src="../../js/ccommon/Flow.js"></script>
<script type="text/javascript" src="../../../base/js/tree/tree.js"></script>
<script type="text/javascript"
	src="../../js/ceditpolice/DepartmentSelect.js"></script>
<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
<script type="text/javascript" src="../../js/ccommon/commonUtil.js"></script>
<script type="text/javascript" src="../../js/ccommon/group.js"></script>
<script type="text/javascript" src="../../js/ccommon/privilege.js"></script>

<script type="text/javascript"
	src="../../../util/jquery/jqgrid/i18n/grid.locale-cn.js"></script>
<script type="text/javascript"
	src="../../../util/jquery/jqgrid/grid.loader.js"></script>
<script type="text/javascript"
	src="../../../util/jquery/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="../../js/ceditpolice/allMaterialInfo.js"></script>
<script type="text/javascript" src="../../js/ceditpolice/materialInfo.js"></script>
<script type="text/javascript" src="../../js/ceditpolice/editTrfficeCrowd.js"></script>	
<script type="text/javascript" src="../../js/ceditpolice/RoadBuild.js"></script>
<script type="text/javascript" src="../../js/cpolicequery/xcbkSearch.js"></script>
<script type="text/javascript" src="../../js/ceditpolice/notice.js"></script>
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
} /*宽高行高背景不显示超过对象尺寸的内容*/
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
	filter: glow(color =                     #ffffff, strength =                    
		1);
}

.ui-widget-header {
	border: 0px solid #4297d7;
	background: #a6c9e2;
	color: #000000;
	font-weight: bold;
	font-size: 13px;
}

.ui-widget-content {
	border: 0px solid #a6c9e2;
	background: #fcfdfd 
		url(../../../util/image/jqgrid/ui-bg_inset-hard_100_fcfdfd_1x100.png) 50%
		bottom repeat-x;
	color: #222222;
}

/* no records*/
.norecords {  
    border-width: 2px !important;  
    display:none;  
    font-weight: bold;
    font-size:24px;  
    left: 30%;  
    margin: 50px;  
    padding: 50px;  
    position: absolute;  
    text-align: center;  
    top: 50%;  
    width: auto;  
    z-index: 102;  
}
</style>

<script type="text/javascript">
	
</script>
</head>
<body style="background: none; padding-top: 0px;"
	Onkeydown="">
	<!-- 应用场景编号 -->
	<input id="appid" type="hidden"
		value="<%=session.getAttribute("appid")%>" />
	<input id="operate" type="hidden" value="${operate}" />
	<input id="zbrName" type="hidden" value="<%=personName%>" />
	<input type="hidden" id="searchAlarmId" />
	<input type="hidden" id="depUnitId" />
	<input type="hidden" id="jgid" value="<%=jgid%>" />
	<input type="hidden" id="jgbh" value="<%=jgbh%>" />
	<table width="100%" height="100%" border="0" cellspacing="0"
		cellpadding="0">
		<!-- 头部 -->
		<tr style="display: none">
			<td></td>
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
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="5%">
													<table id="group"></table> <!-- 	<div align="center">
															<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
														</div>
												</td>
												<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>：交通警情 -> 警情信息查询  -->
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
				<div align="right"></div>
				<table height="100%" width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<td class="wTableCenterLeft"></td>
						<td class="wTableCenterCenter" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="head">
								<tr>
									<td align="left" class="height" colspan=2></td>
								</tr>
								<!-- 查询条件部分 -->
								<tr>
									<td class="sleektd" colspan=2>
										<!-- <div id="defaultCondition">
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
																onclick="currPage=1;doSearchAll();"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
													</td>
													<td style="text-align: right" width="100px">
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
											</div> -->
										<div id="moreCondition_all">
										<table width="100%" border="0px" cellspacing="0" cellpadding="0">
											<tr height="10px">
												<td></td>
											</tr>
											<tr height="10px" id="defalutTitleTr_all">
													<td colspan="8" align="left" style="text-algin:center;">
														<label style="font-size:12px;font-weight:bold;padding:12px;">
															查询条件：按时间查询
														</label>
													</td>
											</tr>
											<tr height='30px;'>
												<!-- <td class="currentLocation" style="text-align: left">
													<label> 查询条件： </label>
												</td> -->
												<td class="currentLocation" style="text-align: right" width="75px">
													<label> 填报时间： </label>
												</td>
												<td id="columnTd" align="left" colspan="2" width="230px">
													<input type="text" name="tbsjStart"
														style="width: 105px; border: 1px #7B9EBD solid"
														id="tbsjStart" maxLength="50" readonly
														onclick="SelectDate(this,0);" /> 
													- 
													<input type="text" name="tbsjEnd"
														style="width: 105px; border: 1px #7B9EBD solid" id="tbsjEnd"
														maxLength="50" readonly onclick="SelectDate(this,0);" />
												</td>
											<!-- </tr>
											<tr height='30px;' id="allLine"> -->
												<td class="currentLocation" style="text-align: right" width="75px">
													<label id="jqbt_lable_all" style="display:none;"> 警情标题： </label>
												</td>
												<td id="columnTd" align="left" colspan="1" width="140px">
													<input
														type="text" id="info_title_all" align="middle"
														style="display:none;width: 140; border: 1px #7B9EBD solid" />
												</td>
												<td class="currentLocation" style="text-align: right" width="75px">
													<label id="xxlx_lable_all" style="display:none;"> 信息类型： </label>
												</td>
												<td id="columnTd" align="left">
														<select id="info_type_all" style="width:120;display:none;">
															<option value="">全部</option>
															<option value="1">重特大事故</option>
															<option value="2">交通拥堵</option>
															<option value="3">施工占道</option>
															<option value="4">协查通报</option>
															<% if (jgbh.length() == 2) {%>
															<option value="5">值班日志</option>
															<% }else { %>
															<option value="5">其他重大情况</option>
															<% }%>
														</select>
												</td>
                                                <td id="simpleButtomTd_all" style="width: 22%;">
                                                    <div class="lsearch" style="display:inline;float:right;margin-left:10px;">
                                                        <a href="#"
                                                           onclick="showMoreConditionAll();"
                                                           class="currentLocation"><span class="lbl" id="advancedLable_all">高级查询</span>
                                                        </a>
                                                    </div>
                                                    <div class="lsearch" style="display:inline;float:right;">
                                                        <a href="#"
                                                           onclick="doSearchAll();"
                                                           class="currentLocation"><span class="lbl">查询</span>
                                                        </a>
                                                    </div>
                                                </td>
											</tr>
											<tr height="30px" id="moreConditionTr_all" style="display:none;">
												<td class="currentLocation" style="text-align: right" width="75px">
													<label> 填报单位： </label>
												</td>
												<td id="jgmcs" align="left" style="width: 230px;">
													<input
														type="text" style="width: 230px;"
														style="border: 1px #7B9EBD solid" id="jgmc_all" readonly
														ondblclick="showDepartTree('<%=ccbm%>','<%=jgid%>','<%=jgmc%>','jgmc_all','100','70')" />
												</td>
												<td>
													<img  src="../../images/popup/btnselect.gif"
														alt="选择机构" style="cursor: hand;"
														onclick="showDepartTree('<%=ccbm%>','<%=jgid%>','<%=jgmc%>','jgmc_all','100','70')">
												</td>
											</tr>
											<tr id="moreButtomTr_all" height='30px' style="text-align:right;display: none;">
													<td colspan="5"></td>
													<td colspan="3" align="right" >
                                                        <div class="lsearch" style="text-align: right;float: right;display: inline;margin-left: 10px;">
                                                            <a href="#"
                                                               onclick="showMoreConditionAll();"
                                                               class="currentLocation"><span class="lbl" id="simpleLable_all">简单查询</span>
                                                            </a>
                                                        </div>
														<div class="lsearch" style="float: right;display: inline;">
															<a href="#" onClick="doSearchAll();"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
													</td>
												</tr>
											<tr height='10px'>
											</tr>
											<%-- <tr height='30px;'>
												<td style="text-align: center" colspan="7">
													<div class="lsearch">
														<a href="#" onclick="currPage=1;doSearch('<%=jgbh%>')"
															class="currentLocation"><span class="lbl">查询</span> </a>
													</div>
												</td>
											</tr> --%>
											<!--
											<tr>
												<td class="currentLocation" style="text-align: right">
													<label> 信息类型： </label>
												</td>
												<td colspan="7"><input type="radio" name="eventType"
													value="全部" checked="checked">全部&nbsp;&nbsp; 
													
													<input type="radio" name="eventType" value="重特大事故">重特大事故&nbsp;&nbsp;
													<input type="radio" name="eventType" value="交通拥堵">交通拥堵&nbsp;&nbsp;
													<input type="radio" name="eventType" value="施工占道">施工占道&nbsp;&nbsp;
													<input type="radio" name="eventType" value="协查通报">协查通报&nbsp;&nbsp;
													<input type="radio" name="eventType" value="其他重大情况/值班日志">其他重大情况/值班日志
												</td>
											</tr>
											-->
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
								<tr height="10px">
									<td></td>
								</tr>
								<tr>
									<td colspan=2 id="td1">
										<table id="tdData"></table>
										<div id="div_pageId"></div>
									</td>
									<td colspan=2 id="td2">
										<table id="jqData"></table>
										<div id="jqpage"></div>
									</td>
									<td colspan=2 id="td3">
										<table id="ydData"></table>
										<div id="ydpage"></div>
									</td>
									<td colspan=2 id="td4">
										<table id="sgData"></table>
										<div id="sgpage"></div>
									</td>
									<td colspan=2 id="td5">
										<table id="xcData"></table>
										<div id="xcpage"></div>
									</td>
									<td colspan=2 id="td6">
										<table id="qtData"></table>
										<div id="qtpage"></div>
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
		<!-- <tr>
			<td height="35" class="wTableBottomCenter">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="12" height="35" class="wTableBottomLeft"></td>
						<td width="16" class="wTableBottomRight"></td>
					</tr>
				</table>
			</td>
		</tr> -->
		<!-- 尾部end -->
	</table>
</body>
</html>
