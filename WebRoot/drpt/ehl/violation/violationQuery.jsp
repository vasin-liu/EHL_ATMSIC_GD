<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
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
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>春运客运车辆严重交通违法信息维护</title>
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
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/list/SelectFastPinYin.js"></script>
		<SCRIPT type="text/javascript" src="../../js/violation/violation.js"></SCRIPT>
		<style type="text/css">
			.td_font{ 
			     font-size:13px;
			}
		</style>
	</head>
	<body style="background:none; padding-top:0px;" onLoad="doOnLoad();">
		<table width="100%" height="100%" border="0" cellspacing="0"
			cellpadding="0">
			<!-- 头部 -->
			<tr style="display:none">
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
														<div align="center">
															<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
														</div>
													</td>
													<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>：客运车违法查询
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
				<td><div align="right"> 
					</div><table height="100%" width="100%" border="0" cellspacing="0"
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
													<td class="sleek textB">查询条件</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr height="10px"><td colspan="6"></td></tr>
												<tr>
													<td width="9%" class="currentLocation" style="text-align:right">
														<label>
															号牌号码：
														</label>
													</td>
													<td width="20%" id="columnTd" align="left">
														<%--<input type="text" name="HPHM" style="width:120;IME-MODE:inactive" id="HPHM" maxLength="15"/>--%>
														<span id="HPHM_TD">
									                    	<script language="javascript">
										                       fillListBox("HPHM_TD","HPHM_AERA","60","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' order by case when a = '粤' THEN 1 ELSE 2 END","请选择");
									                        </script>
									                    </span>
								                    	<input id="HPHM" class="input1" style="width:60px;" type="text" maxLength="6"/>
													</td>
													<td width="10%" class="currentLocation" style="text-align:right">
														<label>
															查处日期：
														</label>
													</td>
													<td width="20%" id="columnTd" align="left">
														<%--<input type="text" id="CCSJ" name="date" style="width:120" readonly onClick="SelectDate(this,0);" />--%>
														<input type="text" name="sj1" style="width:75" id="sj1" maxLength="50" readonly onclick="SelectDate(this,0);" />
														-
														<input type="text" name="sj2" style="width:75" id="sj2" maxLength="50" readonly onclick="SelectDate(this,0);" />
														<script language="javascript">
															var d = new Date(); 
															//d.setTime(d.getTime()-1000*60*60*24);
															d.setTime(d.getTime());
															var year = d.getYear(); 
															var month = d.getMonth()+1; 
															var date = d.getDate(); 
															if(month<10){
																month="0"+month;
															}
															if(date<10){
																date="0"+date;
															}
															$("sj1").value=year+"-"+month+"-"+date;
															$("sj2").value=year+"-"+month+"-"+date;
														</script>
													</td>
													<td width="10%" class="currentLocation" style="text-align:right">
														<label>
															路段名：
														</label>
													</td>
													<td width="31%" id="columnTd" align="left">
														<input type="text" name="CCLD" style="width:135;IME-MODE:inactive"
															id="CCLD" maxLength="50"/>
													</td>
												</tr>
												<tr height="10px"><td colspan="6"></td></tr>
												<tr>
													<td class="currentLocation" style="text-align:right">
														<label>
															驾驶人：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" name="JSR" style="width:120;IME-MODE:inactive"
															id="JSR" maxLength="10"/>
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															车属单位：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" name="CSDW" style="width:184;IME-MODE:inactive"
															id="CSDW" maxLength="50"/>
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															驾驶证号：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" name="JSZ" style="width:135;IME-MODE:inactive"
															id="JSZ" maxLength="30"/>
													</td>
												</tr>
												<tr height="10px"><td colspan="6"></td></tr>
												<tr>
													<td class="currentLocation" style="text-align:right">
														<label>
															支队：
														</label>
													</td>
													<td id="ZD_DIV" align="left">
														<script language="javascript">
									                    	fillListBox("ZD_DIV","ZD","150","select jgid,jgmc from t_sys_department where substr(jgid,3,2)<>'00' and substr(jgid,5,2)='00' order by jgid","全部","ZDCallBack()","ZDOnChange");
								                        </script>
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															大队：
														</label>
													</td>
													<td id="DD_DIV" align="left">
														<script language="javascript">
									                    	fillListBox("DD_DIV","DD","150","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00' order by jgid","全部","DDCallBack()");
								                        </script>
													</td>
													<%--<td class="currentLocation" style="text-align:right">
														<label></label>
													</td>--%>
													<td id="columnTd" align="left" colspan="2">
														<div class="search" style="float:left;margin-left:10px">
															<a href="#" onclick="onButtonClick('search','<%= jgbh %>')"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
														<div class="search" style="float:left;margin-left:10px">
															<a href="#" onclick="onButtonClick('excel','<%= jgbh %>')"
																class="currentLocation"><span class="lbl">导出EXCEL</span>
															</a>
														</div>
														<div class="search" style="float:left;margin-left:10px">
															<a href="#" onclick="printScreen('block','window.print();','<%= jgbh %>');"
																class="currentLocation"><span class="lbl">打印</span>
															</a>
														</div>
													</td>
												</tr>
												<tr height="10px"><td colspan="6"></td></tr>
												<tr style="display:none">
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
																		<a href="#" onclick="onButtonClick('search','<%= jgbh %>')"
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
											<div id="block">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="table" id="table1">
												<tr class="titleTopBack">
													<td width='7%' class='td_r_b td_font'>
														号牌号码
													</td>
													<td width='12%' class='td_r_b td_font'>
														车属单位（人）
													</td>
													<td width='11%' class='td_r_b td_font'>
														违法种类
													</td>
													<td width='11%' class='td_r_b td_font'>
														查处日期
													</td>
													<td width='7%' class='td_r_b td_font'>
														路段名
													</td>
													<td width='7%' class='td_r_b td_font'>
														驾驶人
													</td>
													<td width='8%' class='td_r_b td_font'>
														驾驶证号
													</td>
													<td width='14%' class='td_r_b td_font'>
														录入单位
													</td>
													<td width='8%' class='td_r_b td_font'>
														填表时间
													</td>
													<td width='5%' class='td_r_b td_font'>明细</td>
													<td width='5%' class='td_r_b td_font'>修改</td>
													<%--<td width='5%' class='td_r_b td_font'>删除</td>--%>
												</tr>
											</table>
											</div>
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
