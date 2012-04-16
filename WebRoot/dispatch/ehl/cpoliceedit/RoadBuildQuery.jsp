<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@include file="../../Message.oni"%>
<%
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//机构名称
    String ccbm=strObj[2];//机构层次编码
    //String jgid="441905000000";//12位机构编码
    String jgbh;//总队(2位),支队(4位),大队(6位)
    if("0000".equals(jgid.substring(2,6))){
    	jgbh = jgid.substring(0,2);
    }else if("00".equals(jgid.substring(4,6))){
    	jgbh = jgid.substring(0,4);
    }else{
    	jgbh = jgid.substring(0,6);
    }
    System.out.println("机构查询条件: "+jgbh);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>交通占道信息维护</title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/RoadBuild.js"></script>
		<script type="text/javascript" src="../../js/ccommon/commonUtil.js"></script>
		<link href="../../css/common/group.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../js/ccommon/group.js"></script>
		<script type="text/javascript" src="../../js/ccommon/privilege.js"></script>
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
			}
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
		</style>
	</head>
	<body style="background: none; padding-top: 0px;" onLoad="doSearch('<%=jgbh%>')"
			Onkeydown="if (window.event.keyCode==13) doSearch('<%=jgbh %>')" 
	>
		<!-- 应用场景编号 -->
		<input id="appid" type="hidden" value="<%=session.getAttribute("appid") %>" />
		<input id="operate" type="hidden" value="${operate}" />
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
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
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="5%">
														<!-- 
														<div background-color="#106ead" width="100%"><table  border="1" borderColor='#a5d1ec' style="font-size: 12px;font-weight: bold;"> <tr>
															<td>&nbsp;&nbsp;&nbsp;选择待查阅事件类别&nbsp;&nbsp;&nbsp;</td>
															<td >&nbsp;<input type="radio" name="radioBtn" id="radioBtn1" onclick="getSearchPage()"> 重特大交通事故&nbsp;</td>
															<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn2" onclick="getSearchPage()">交通拥堵&nbsp;</td>
															<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn3" onclick="getSearchPage()">协查通报&nbsp;</td>
															<td bgcolor="#F0FFFF" >&nbsp;<input type="radio" name="radioBtn" id="radioBtn4" onclick="getSearchPage()">施工占道信息&nbsp;</td>
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
									<!-- 查询条件部分 -->
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
													<td style="text-align: right"  width="100px">
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
										<div id="moreCondition" style="width:100%;">
											<table width="100%" border="0px" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr height="10px" id="defalutTitleTr">
													<td colspan="8" align="left" style="text-algin:center;">
														<label style="font-size:12px;font-weight:bold;padding:12px;">
															查询条件：按时间查询
														</label>
													</td>
												</tr>
												<tr height="30px">
													<td class="currentLocation" style="text-align:right" width="75px">
														<label>
															时间范围：
														</label>
													</td>
													<td id="columnTd" align="left"  colspan="2" width="280px">
														<input type="text" name="sj1" id="sj1"  readonly onclick="SelectDate(this,0);" style="width: 130px" />
														-
														<input type="text" name="sj2"  id="sj2"  readonly onclick="SelectDate(this,0);" style="width: 130px" />
													</td>
													<td class="currentLocation" align="right" style="text-align:right;" width="75px">
														<label id="dlmc_lable" style="display:none;">
															道路名称：
														</label>
													</td>
													<td id="alarmRoad_TrafficCrowd_td" align="left" width="200px">
														<input type="text" id="ROADNAME" style="display:none;width: 200px;"/>
													</td>
                                                    <td id="simpleButtomTd" align="right" style="width: 22%;">
                                                        <div class="lsearch" style="text-align: right;float: right;display: inline;margin-left: 10px;">
                                                            <a href="#"
                                                               onclick="showMoreCondition();"
                                                               class="currentLocation"><span class="lbl" id="advancedLable">高级查询</span>
                                                            </a>
                                                        </div>
                                                        <div class="lsearch" style="text-align: right;float: right;display: inline;">
															<span class="lbl" style="padding-right: 10px;" onclick="onRoadClick('search','<%=jgbh%>')"  >
																查询
															</span>
                                                        </div>
                                                    </td>
													<%-- <td align="center">
														<div class="lsearch" >
															<span class="lbl" style="padding-right: 10px;" onclick="onRoadClick('search','<%=jgbh%>')"  >
																查询
															</span>
														</div>
													</td> --%>
												</tr>
												<tr height="30px" id="moreConditionTr" style="display:none">
													<td class="currentLocation" style="text-align:right" width="75px">
														<label>
															填报单位：
														</label>
													</td>
													<td id="columnTd" align="left" width="160">
														<input type="text" id="AlarmUnit" style="width:220" ondblclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','AlarmUnit','100','89')" readonly="readonly"/>
													</td>
													<td align="left" style="width:90">
														<img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor:hand;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','AlarmUnit','100','89')">
													</td>
													<td class="currentLocation" style="text-align:right;" width="75px">
														<label>
															占道类型：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<select id="ISALLCLOSE" style="width:133;">
															<option value="全部">全部</option>
															<option value="1">全封闭</option>
															<option value="0">占用部分车道</option>
														</select>
													</td>
													<%-- <td  align="center" >
														<div class="lsearch" >
															<span class="lbl" style="padding-right: 5px;" onclick="showExcel('<%=jgbh%>')" >
																导出Excel
															</span>
														</div>
													</td> --%>
													<td></td>
												</tr>
												<tr id="moreButtomTr" height='30px;' style="display: none;">
													<td colspan="4"></td>
													<td colspan="2" align="right">
                                                        <div class="lsearch" style="text-align: right;float: right;display: inline;margin-left: 10px;">
                                                            <a href="#"
                                                               onclick="showMoreCondition();"
                                                               class="currentLocation"><span class="lbl" id="simpleLable">简单查询</span>
                                                            </a>
                                                        </div>
														<div class="lsearch" style="text-align: right;float: right;display: inline;">
															<span class="lbl" style="padding-right: 10px;" onclick="onRoadClick('search','<%=jgbh%>')"  >
																查询
															</span>
														</div>
													</td>
												</tr>
												<script type="text/javascript">
													//var sql = "  select dlmc A, dlbh||' '||'：'||dlmc B,dlbh from LCB_PT_MIS group by dlmc, dlbh||':'||dlmc,dlbh order by dlbh";
													//fillListBox("alarmRoad_TrafficCrowd_td","ROADNAME","133",sql,"请选择","DateUser.setDateSE('sj1','sj2',1),doSearch('<%=jgbh%>')");
												</script>
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
											<%--<table width="100%" border="0" cellpadding="0"
												cellspacing="0" onmouseover="changeto()"
												onmouseout="changeback()" class="table">
											--%>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="table">
												<tr class="titleTopBack">
												    <td width='15%' class='td_r_b td_font'>
														填报单位
													</td>
													<td width='15%' class='td_r_b td_font'>
														路段备注
													</td>
													<td width='15%' class='td_r_b td_font'>
														方向
													</td>
													<td width='15%' class='td_r_b td_font'>
														施工开始时间
													</td>
													<td width='15%' class='td_r_b td_font'>
														施工结束时间
													</td>
													<td width='5%' class='td_r_b td_font'>
														明细
													</td>
													<td width='5%' class='td_r_b td_font'>
														修改
													</td>
													<td width='10%' class='td_r_b td_font'>
														删除
													</td>
												</tr>
											</table>
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
		<div id="showDivId"></div>
	</body>
</html>