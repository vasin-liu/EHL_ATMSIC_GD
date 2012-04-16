<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
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
		<title>路段信息维护</title>
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
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/RoadSection.js"></script>	
		<style type="text/css">
			.td_font {
				font-size: 13px;
			}
		</style>
	</head>
	<body style="background: none; padding-top: 0px;" onLoad="doOnLoad();">
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
														<div align="center">
															<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
														</div>
													</td>
													<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>：路段信息维护
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
														查询条件
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align: right">
														<label>
															道路名称：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" style="width: 120"
															id="roadname" maxLength="15" />
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															路段备注：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" style="width:120"
															id="sectionname" />
													</td>
												</tr>
												<tr height="10px">
													<td></td>
												</tr>
												<tr>
													<td colspan="6" align="center">
														<table width="60%">
															<tr align="center">
																<td>
																	<div class="search">
																		<a href="#" onclick="onButtonClick('new')"
																			class="currentLocation"><span class="lbl">新增</span>
																		</a>
																	</div>
																</td>
																<td>
																	<div class="search">
																		<a href="#"
																			onclick="onButtonClick('search')"
																			class="currentLocation"><span class="lbl">查询</span>
																		</a>
																	</div>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
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
												    <td width='35%' class='td_r_b td_font'>
														路段备注
													</td>
													<td width='35%' class='td_r_b td_font'>
														道路名称
													</td>
													<td width='10%' class='td_r_b td_font'>
														明细
													</td>
													<td width='10%' class='td_r_b td_font'>
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
	</body>
</html>