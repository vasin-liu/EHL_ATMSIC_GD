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
    System.out.println("机构层次编码: "+ccbm);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>交通拥堵信息维护</title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style1/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Department.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/editTrfficeCrowd.js"></script>	
		<script type="text/javascript" src="../../js/ccommon/Flow.js"></script>
		<script type="text/javascript" src="../../js/ccommon/commonUtil.js"></script>
		<script type="text/javascript" src="../../js/ccommon/FileUpDownload.js"></script>
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
	<body style="background: none; padding-top: 0px;" onLoad="doSearch('<%=jgbh%>');" 
			Onkeydown="if (window.event.keyCode==13) doSearch('<%=jgbh %>')" 
	>
	<div id="showDivId"></div>
	
		 <!-- 应用场景编号 -->
		<input id="appid" type="hidden" value="<%=session.getAttribute("appid") %>" />
		<input id="operate" type="hidden" value="${operate}" />
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
			<tr style="display: none">
				<td>
					<input id="jgbh" type="text" value="<%=jgbh%>"readonly></input>
					<input id="jgid" type="text" value="<%=jgid%>" readonly></input>
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
														<div background-color="#106ead" width="100%"><table  border="1" borderColor='#a5d1ec' style="font-size: 12px;font-weight: bold;"> <tr>
															<td>&nbsp;&nbsp;&nbsp;选择待查阅事件类别&nbsp;&nbsp;&nbsp;</td>
															<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn1" onclick="getSearchPage()"> 重特大交通事故&nbsp;</td>
															<td bgcolor="#F0FFFF">&nbsp;<input type="radio" name="radioBtn" id="radioBtn2" onclick="getSearchPage()">交通拥堵&nbsp;</td>
															<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn3" onclick="getSearchPage()">协查通报&nbsp;</td>
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
					<%--<div align="right">
					</div>--%>
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
													<td colspan="8"></td>
												</tr>
												<tr height="10px">
													<td colspan="8" align="left" style="text-algin:center;">
														<label style="font-size:12px;font-weight:bold;padding:12px;">
															查询条件：按时间查询
														</label>
													</td>
												</tr>
												<tr height="10px">
													<td colspan="8"></td>
												</tr>
												<tr height='30px;'>
													<td class="currentLocation" style="text-align:right" width="95">
														<label>
															拥堵报告时间：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="2" width="230">
														<input type="text" name="sj1" style="width:105" id="sj1" maxLength="50" readonly onclick="SelectDate(this,0);" />
														-
														<input type="text" name="sj2" style="width:105" id="sj2" maxLength="50" readonly  onclick="SelectDate(this,0);" />
													</td>
													<td colspan="2"></td>
													<td style="text-align: right" >
														<div class="lsearch">
															<a href="#"
																 onclick="onTrafficClick('search','<%=jgbh%>');"
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
										</div> --%>
										<div id="moreCondition" align="left" style="width: 100%; border: 1px solid #BFBFBF;">
											<table width="100%" border="0px" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td colspan="8"></td>
												</tr>
												<tr height="10px" id="defalutTitleTr">
													<td colspan="8" align="left" style="text-algin:center;">
														<label style="font-size:12px;font-weight:bold;padding:12px;">
															查询条件：按时间查询
														</label>
													</td>
												</tr>
												<tr height="30px">
													<td class="currentLocation" style="text-align:right" width="95">
														<label>
															拥堵报告时间：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="2" width="230">
														<input type="text" name="sj1" style="width:105" id="sj1" maxLength="50" readonly onclick="SelectDate(this,0);" />
														-
														<input type="text" name="sj2" style="width:105" id="sj2" maxLength="50" readonly  onclick="SelectDate(this,0);" />
													</td>
													<td class="currentLocation" style="text-align:right;" width="65">
														<label id="dlmc_lable" style="display:none;">
															道路名称：
														</label>
													</td>
													<td id="alarmRoad_TrafficCrowd_td" align="left" >
														<input type="text" id="ROADNAME" style="display:none;"/>
													</td>
													<td class="currentLocation" style="text-align:right;width:75">
														<label id="tbdw_lable" style="display:none;">
															填报单位：
														</label>
													</td>
													<td id="columnTd" align="left" width="160">
														<input type="text" id="AlarmUnit" style="width:160;display:none;" ondblclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','AlarmUnit','70','525')" readonly/>
													</td>
													<td align="left" valign="bottom">
														<img id="tbdw_img" src="../../images/popup/btnselect.gif" alt="选择机构"  style="display:none;cursor:hand;padding-bottom: -10" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','AlarmUnit','70','525')">
													</td>
                                                    <td id="simpleButtomTd" style="width: 22%">
                                                        <div class="lsearch" style="diaplay:inline;float:right;margin-left:10px;">
                                                            <a href="#"
                                                               onclick="showMoreCondition();"
                                                               class="currentLocation"><span class="lbl" id="advancedLable">高级查询</span>
                                                            </a>
                                                        </div>
                                                        <div class="lsearch" style="float: right;display: inline;">
															<span class="lbl" onclick="onTrafficClick('search','<%=jgbh%>')"  >
																查询
															</span>
                                                        </div>
                                                    </td>
												</tr>
												<tr height="30px" id="moreConditionTr" style="display:none;">
													<td class="currentLocation" style="text-align:right;">
														<label>
															事件状态：
														</label>
													</td>
													<td id="columnTd" align="left" style="width:133">
														<select id="CONGESTIONTYPE" style="width:228">
															<option value="全部" >全部</option>
															<option value="570001" selected="selected">拥堵中</option>
															<option value="570002">拥堵已结束</option>
															<!-- xyx 04-10
															580001->570001
															580002->570002
															T_ATTEMPER_ALARM(roadid<道路名称>,roadname<路段备注>)
															
															 -->
														</select>
													</td>
													<td></td>
													<td class="currentLocation" style="text-align:right">
														<label>
															拥堵原因：
														</label>
													</td>
													<td id="columnTd" align="left" style="width:133">
														<select id="reason" style="width:133">
															<option value="全部">全部</option>
															<option value="事故">事故</option>
															<option value="修路">修路</option>
															<option value="故障车">故障车</option>
															<option value="天气">天气</option>
															<option value="车流量大">车流量大</option>
															<option value="其他">其他</option>
														</select>
													</td>
												</tr>
												<tr id="moreButtomTr" height='30px;' style="display: none;">
													<td colspan="5"></td>
													<td align="right" colspan="3">
														<div class="lsearch" style="diaplay:inline;float:right;margin-left:10px;">
															<a href="#"
																onclick="showMoreCondition();"
																class="currentLocation"><span class="lbl" id="simpleLable">简单查询</span>
															</a>
														</div>
														<div class="lsearch" style="float: right;">
															<span class="lbl" onclick="onTrafficClick('search','<%=jgbh%>')"  >
																查询
															</span>
														</div>
														<!-- <div class="lsearch" >
															<span class="lbl" style="padding-right: 5px;" onclick="exportExcel()" >
																导出Excel
															</span>
														</div> -->
													</td>
													<!-- <td style="text-align: right" >
														<div class="lsearch">
															<a href="#"
																onclick="showMoreCondition();"
																class="currentLocation"><span class="lbl">默认条件</span>
															</a>
														</div>
													</td> -->
												</tr>
												<script type="text/javascript">
													//var sql = "  select dlmc A, dlbh||' '||'：'||dlmc B,dlbh from LCB_PT_MIS group by dlmc, dlbh||':'||dlmc,dlbh order by dlbh";
													//fillListBox("alarmRoad_TrafficCrowd_td","ROADNAME","133",sql,"请选择","DateUser.setDateSE('sj1','sj2',0),doSearch('<%=jgbh%>')");
												</script>
												<tr height="10px">
													<td colspan="7"></td>
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
													<td width='10%' class='td_r_b td_font'>
														方向
													</td>
													<td width='10%' class='td_r_b td_font'>
														交通状况
													</td>
													<td width='10%' class='td_r_b td_font'>
														拥堵原因
													</td>
													<%--<td width='10%' class='td_r_b td_font'>
														起止里程
													</td>--%>
													<td width='15%' class='td_r_b td_font'>
														报告时间
													</td>
													<td width='7%' class='td_r_b td_font'>
														拥堵状态
													</td>
													<td width='5%' class='td_r_b td_font'>
														明细
													</td>
													<td width='5%' class='td_r_b td_font'>
														修改
													</td>
													<td width='5%' class='td_r_b td_font'>
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
		<div id="showDivId">
		
		</div>
	</body>
</html>
