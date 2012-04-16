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
    
    String sql = "SELECT person FROM T_OA_DUTY WHERE deptid = '"+ jgid +"' and to_date(dtime, 'yyyy-mm-dd hh24:mi:ss') between to_date(to_char(sysdate,'yyyy-mm-dd')||' 08:30:00', 'yyyy-mm-dd hh24:mi:ss') and to_date(to_char(sysdate + 1,'yyyy-mm-dd')||' 08:30:00', 'yyyy-mm-dd hh24:mi:ss') order by dtime desc";
    String personName = StringHelper.obj2str(DBHandler.getSingleResult(sql));
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
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript"
			src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/materialInfo.js"></script>
		<script type='text/javascript' src='${contextPath}base/js/new/base.js'></script>
		<script type="text/javascript" src="../../js/ccommon/Flow.js"></script>
		<script type="text/javascript" src="../../../base/js/tree/tree.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
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
		</style>
	</head>
	<body style="background: none; padding-top: 0px;" onload="doSearch('<%=jgbh %>');" Onkeydown="if (window.event.keyCode==13) doSearch('<%=jgbh %>')" >
		<!-- 应用场景编号 -->
		<input id="appid" type="hidden" value="<%=session.getAttribute("appid") %>" />
		<input id="operate" type="hidden" value="${operate}" />
		<input id="zbrName" type="hidden" value="<%=personName%>" />
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
														<!-- 
														<div background-color="#106ead" width="100%">
															<table  border="1" borderColor='#a5d1ec' style="font-size: 12px;font-weight: bold;"> 
																<tr height="25">
																<td>&nbsp;&nbsp;&nbsp;选择待查阅事件类别&nbsp;&nbsp;&nbsp;</td>
																<td bgcolor="#F0FFFF" >&nbsp;<input type="radio" name="radioBtn" id="radioBtn1" onclick="getSearchPage()"> 重特大交通事故&nbsp;</td>
																<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn2" onclick="getSearchPage()">交通拥堵&nbsp;</td>
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
					<div align="right">
					</div>
					<table height="100%" width="100%" border="0" cellspacing="0"
						cellpadding="0">
						<tr>
							<td class="wTableCenterLeft"></td>
							<td class="wTableCenterCenter" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td align="left" class="height" colspan=2></td>
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
											<table table width="100%" border="0px" cellspacing="0"
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
													<td align="right" class="currentLocation" style="text-align: right;" width="75px">
														<label>
															填报时间：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="2">
														<input type="text" name="tbsjStart" style="width:115px;border: 1px #7B9EBD solid" id="tbsjStart" maxLength="50" readonly onclick="SelectDate(this,0);" />
														-
														<input type="text" name="tbsjEnd" style="width:115px;border: 1px #7B9EBD solid" id="tbsjEnd" maxLength="50" readonly onclick="SelectDate(this,0);" />
													</td>
													<td colspan="3"></td>
													<td style="text-align: right;" >
														<div class="lsearch">
															<a href="#"
																onclick="currPage=1;doSearch('<%=jgbh %>')"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
													</td>
													<td style="text-align: right;"  width="100px">
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
											<div id="moreCondition" style="width: 100%;">
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
												<tr height='30px;'>
														<input type="hidden"  id="searchAlarmId"  />
													<%--<td class="currentLocation" style="text-align: right">
														<label>
															&nbsp;&nbsp;&nbsp;事故编号：
														</label>
													</td>
													<td id="jgmcs" align="left">
													</td>
													--%>
													<td class="currentLocation" style="text-align: right" width="9%">
														<label id="tbsj_lable">
															填报时间：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="2">
														<input type="text" name="tbsjStart" style="width:105;border: 1px #7B9EBD solid" id="tbsjStart" maxLength="50" readonly onclick="SelectDate(this,0);" />
														-
														<input type="text" name="tbsjEnd" style="width:105;border: 1px #7B9EBD solid" id="tbsjEnd" maxLength="50" readonly onclick="SelectDate(this,0);" />
													</td>
													<td class="currentLocation" style="text-align: right">
														<label id="sgbt_lable" style="display:none;">
															事故标题：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<input type="text" id="dlmc" align="middle" style="display:none;width:225px;border: 1px #7B9EBD solid" />
													</td>
													<td class="currentLocation" style="text-align: right">
														<label id="tbr_lable" style="display:none;">
															填报人：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" style="width:140" style="display:none;border: 1px #7B9EBD solid" id="tbr" align="middle" />
													</td>
                                                    <td id="simpleButtomTd" style="width: 22%;">
                                                        <div class="lsearch" style="display:inline;float:right;margin-left:10px;">
                                                            <a href="#"
                                                               onclick="showMoreCondition();"
                                                               class="currentLocation"><span class="lbl" id="advancedLable">高级查询</span>
                                                            </a>
                                                        </div>
                                                        <div class="lsearch" style="display:inline;float:right;">
                                                            <a href="#"
                                                               onclick="currPage=1;doSearch('<%=jgbh %>')"
                                                               class="currentLocation"><span class="lbl">查询</span>
                                                            </a>
                                                        </div>
                                                    </td>
												</tr>
												<tr id="moreConditionTr" height='30px;' style="display:none;">
													<td class="currentLocation" style="text-align: right">
														<label>
															填报单位：
														</label>
													</td>
													<td id="jgmcs" align="left" style="width:215px;">
														<input type="hidden" id="jgid" value="<%=jgid %>" />
														<input type="hidden" id="jgbh" value="<%=jgbh %>" />
														<input type="text" style="width:215px;" style="border: 1px #7B9EBD solid" id="jgmc" readonly ondblclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','jgmc','100','70')" />
													</td>
													<td >
														<input type="hidden" id="depUnitId"  />
														<img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor:hand;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','jgmc','100','70')">
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															事件类别：
														</label>
													</td>
													<td id="columnTd" align="left">
														<select style="width:229;border: 1px #7B9EBD solid" id="sjlb">
															<option value="" selected>全部</option>
															<option value="99">一次死亡3人以上交通事故</option>
															<option value="ISBUS">营运大客车事故</option>
															<option value="ISSCHOOLBUS">校车事故</option>
															<option value="ISDANAGERCAR">危化品运输车交通事故</option>
															<option value="ISFOREIGNAFFAIRS">涉港澳台及涉外事故</option>
															<option value="ISPOLICE">涉警交通事故</option>
															<option value="ISARMYACC">涉军交通事故</option>
															<option value="ISMASSESCASE">涉及群体性事件</option>
															<option value="ISCONGESTION">高速公路国省道交通中断</option>
															<option value="ISOTHERSITEM">其他</option>
														</select>
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															信息状态：
														</label>
													</td>
													<td id="columnTd" align="left">
														<select style="width:140;border: 1px #7B9EBD solid" id="ldmc">
															<option value="" selected>全部</option>
															<option value="004032">大队保存草稿，未上报</option>
															<option value="004033">大队报支队，未签收</option>
															<option value="004042">大队报支队，已签收</option>
															<option value="004034">支队报总队，未签收</option>
															<option value="004043">支队报总队，已签收</option>
															<option value="004036">总队处理完成</option>
															<option value="004037">总队下发支队，未签收</option>
															<option value="004035">总队下发支队，已签收</option>
														</select>
													</td>
												</tr>
												<tr id="moreButtomTr" height='30px;' style="display: none;">
												<td colspan="5"></td>
												<td style="text-align: right" colspan="2">
													<div class="lsearch" style="display:inline;float:right;margin-left:10px;">
															<a href="#"
																onclick="showMoreCondition();"
																class="currentLocation"><span class="lbl" id="simpleLable">简单查询</span>
															</a>
													</div>
													<div class="lsearch" style="display:inline;float:right;">
														<a href="#"
															onclick="currPage=1;doSearch('<%=jgbh %>')"
															class="currentLocation"><span class="lbl">查询</span>
														</a>
													</div>
												</td>
												<%-- <td style="text-align: center">
														 <div class="lsearch">
															<a href="#" onclick="showExcelInfo('search','<%=jgbh %>')"
																class="currentLocation"><span class="lbl">导出Excel</span>
															</a>
														</div>
													</td> --%>
													<!-- <td style="text-align: right" >
														<div class="lsearch">
															<a href="#"
																onclick="showMoreCondition();"
																class="currentLocation"><span class="lbl">默认条件</span>
															</a>
														</div>
													</td> -->
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
										<td class="height"></td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td id="tdData" colspan=2>
											<%--<table width="100%" border="0" cellpadding="0"
												cellspacing="0" onmouseover="changeto()"
												onmouseout="changeback()" class="table">
											--%>
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
