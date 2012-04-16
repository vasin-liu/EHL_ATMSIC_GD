<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.ehl.sm.base.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%@ page import="com.ehl.sm.common.util.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%
	pageContext.setAttribute("time",Constant.getCurrentTime(false).substring(0,4));
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String istr = "readonly";
%>
<html>
	<head>
		<title>春运日报表统计</title>
		<style type="text/css">
			<!--
			table{
				font-size:11px;
			}
			.tableInput{
				border:1px solid #b5d6e6;
			}
			.td_r_b1{ 
			    border-bottom:1px solid #b5d6e6; 
				border-right:1px solid #b5d6e6; 
				text-align:left;
			}
			.td_r_b{ 
			     border-bottom:1px solid #b5d6e6; 
				 border-right:1px solid #b5d6e6; 
				 text-align:center;
			}
			.td_r_tfm{ 
			     border-bottom:1px solid #b5d6e6; 
				 border-right:0px solid #b5d6e6; 
				 text-align:center;
			}
			.td_r_zh{ 
			     border-bottom:1px solid #b5d6e6; 
				 border-right:0px solid #b5d6e6; 
				 text-align:center;
			}
			.td_r_lastline{ 
			     border-bottom:0px solid #b5d6e6; 
				 border-right:1px solid #b5d6e6; 
				 text-align:center;
			}
			.td_b{ 
			     border-bottom:0px solid #b5d6e6; 
				 text-align:center;
			}
			.STYLE2 {
				color: #FF0000;
			}
			.input1{
				border:0;
				width:40px;
				text-align:center;
			}
			.input2{
				border:0;
				width:40px;
				border-bottom:1px solid #b5d6e6; 
			}
			.input3{
				border:0;
				width:100px;
			}
			*{
				margin:0;
				pading:0;
			}
			-->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../../base/css/style1/tab.css" rel="stylesheet"  type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet"	type="text/css" />
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../js/dailyRpt/dailyReportStat.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/output/SaveAsExcel.js"></script>
		<script type="text/javascript" src="../../../base/js/style1/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
	</head>
	<body>
	<input type="hidden" id="_jgid" value="<%=jgid%>">
	<input type="hidden" id="time" value="${time}">
		<table width="100%" height="100%" id="queryTable" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
			<tr>
				<td height="30" class="wTableTopCenter">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="12" height="30" class="wTableTopLeft"></td>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="46%" valign="middle">
										   <div id="printno">
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
														<span class="currentLocationBold">您当前的位置</span>：春运日报表统计
													</td>
												</tr>
											</table>
											</div>  <!-- div printno end-->
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
					<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="wTableCenterLeft"></td>
							<td class="wTableCenterCenter" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<!-- 查询条件部分 -->
									<tr>
										<td>
										    <div id="printno2">
											<table border="0" cellspacing="0"
												cellpadding="0" width=99% align=center>
												<tr>
													<td align="left">
														<table width="141" border="0" cellspacing="0" cellpadding="0">
															<tr><td class="sleek textB">统计条件</td></tr>
														</table>
													</td>
												</tr>
												<tr>
												  <td class="sleektd">
												        
														<table width="100%" border="0" cellspacing="0" cellpadding="0">
															<tr align="center" height="35">
																<td width="12%" align="right" class="currentLocation">
																	<p align="right">开始时间：</p></td>
																<td align="left" width="20%">
																	<input name="startTime" type='text' id='startTime' style="width:170px;" onClick="SelectDate(this,0);" readOnly>																</td>
																<td align="right" width="12%" class="currentLocation">
																	<p align="right">结束时间：</p></td>
																<td align="left" width="20%">
																	<input name="endTime" type='text' id='endTime' style="width:170px;" onClick="SelectDate(this,0);" readOnly>																</td>
																<td colspan="1" class="currentLocation" style="text-align:center;">&nbsp;</td>
															</tr>
															<tr align="center" height="35">
																<%
																	//总队用户
																	if("0000".equals(jgid.substring(2,6))){
																%>
																
																<script language="javascript"> 
																	zd();
																</script>
																<td align="center" width="12%" class="currentLocation" style="text-align:right;">支队：</td>
																<td align="center" width="20%" class="currentLocation" id="zhiduiTdId"></td>
																<td align="center" width="12%" class="currentLocation" style="text-align:right;">大队：</td>
																<td align="center" width="20%" class="currentLocation" id="daduiTdId">
																	<script language="javascript">
																		fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00' order by jgid","","initTime('<%=jgid%>')");
																	</script>																</td>
																
																<%
																	//支队用户
																	}else if("00".equals(jgid.substring(4,6))){
																%>
																
																<td align="center" width="16%" class="currentLocation" style="text-align:right;">支队：																</td>
																<td align="center" width="20%" class="currentLocation" id="zhiduiTdId">
																	<script language="javascript">
																	   fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where jgid='<%=jgid%>'  order by jgid","未选择","zhiduiCallback('<%=jgid%>')");
																	</script>																</td>
																<td align="center" width="12%" class="currentLocation" style="text-align:right;">大队：																</td>
																<td align="center" width="20%"  class="currentLocation" id="daduiTdId">																</td>
																<%
																	//大队用户
																	}else {
																%>
																<td align="center" width="15%" class="currentLocation" style="text-align:right;">支队：
																<td align="center" width="20%" class="currentLocation" id="zhiduiTdId">
																	<script language="javascript">
																	   fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where jgid='<%=jgid.substring(0,4)+"00"+jgid.substring(6,jgid.length())%>'  order by jgid","未选择","daduiCallbackzhidui('<%=jgid.substring(0,4)+"00"+jgid.substring(6,jgid.length())%>')");
																	</script></td>	
																<td align="center" width="12%" class="currentLocation" style="text-align:right;">大队：</td>
																<td align="center" width="20%" class="currentLocation" id="daduiTdId">
																	<script language="javascript">
																	   fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where jgid='<%=jgid%>'  order by jgid","未选择","daduiCallback('<%=jgid%>')");
																	</script>															  </td>
																
																<% } %>

																<td class="currentLocation" style="text-align:right;" width="12%">
																	<input id="statisticsCaseInfo" type="image" src="../../images/button/btnStat.gif"  onClick="stat('<%=jgid%>')"/>																</td>
																
																<td  class="currentLocation" style="text-align:center;">&nbsp;
																	
																	<input id="OutExcelCaseInfo" type="image" src="../../images/button/btnOutExcel.gif" 
																		alt="统计有数据时可用导出" onClick="AllAreaExcel()"/>
																</td>
																<td  class="currentLocation" style="text-align:left;">&nbsp;		
																	<input id="OutExcelCaseInfo" type="image" src="../../images/button/print.gif" 
																		alt="打印" onClick="removeCaption();printFor();createCaption()"/>	
																		
																	<!-- 
																	<input type="button" onClick="javascript:AllAreaExcel();" value="导出Excel">	
																	-->															
																 </td>
															</tr>
														</table>
														<div style="color:red;font-size:12px;padding-left:40px;">◆提示： 表中标记 ※ 部分为公安部要求的周报表统计项.</div>
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
										<td>
											<div id="print">
			 <table  style="display: none"> 
            	<tr>
                   <td colspan="3" class="td_r_b1">通报异地超速50%以上、客车超员20%以上交通违法行为（起）</td>
                   <td class="td_b"><input  maxLength="8" <%=istr%> id="YDTB" class="input1" type="text" onkeydown="changeBlur(event,'CSSB')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                   <td class="td_r_b1">设置固定测速点（个）</td>
                   <td class="td_r_b"><input  maxLength="8" <%=istr%> id="GDCSD" class="input1" type="text" onkeydown="changeBlur(event,'JYTZKCJSR')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                   <td  class="td_r_b1">建议调整客车驾驶人（人次）</td>
                   <td class="td_b"><input  maxLength="8" <%=istr%> id="JYTZKCJSR" class="input1" type="text" onkeydown="changeBlur(event,'LDCSD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                   <td class="td_r_b1">设置流动测速点（个）</td>
                   <td class="td_r_b"><input  maxLength="8" <%=istr%> id="LDCSD" class="input1" type="text" onkeydown="changeBlur(event,'QZPLJSRXX')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                   <td colspan="3" class="td_r_b1">强制疲劳驾驶人休息（人次）</td>
                   <td class="td_b"><input  maxLength="8" <%=istr%> id="QZPLJSRXX" class="input1" type="text" onkeydown="changeBlur(event,'ZQFWD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                   <td colspan="2" class="td_r_b1">受教育（人次）</td>
                   <td class="td_b"><input  maxLength="8" <%=istr%> id="SJY" class="input1" type="text" onkeydown="changeBlur(event,'NYCWFZK')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
               	<td colspan="3" class="td_r_b1">为群众做好事（件）</td>
               	<td class="td_b"><input  maxLength="8" <%=istr%> id="ZHS" class="input1" type="text" onkeydown="changeBlur(event,'TBKYBM')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                   <td class="td_r_b1">教育运输企业驾驶人（人次）</td>
                   <td class="td_r_b"><input name="text" type="text" class="input11" id="JYYSJSR" onFocus="this.select();" onKeyPress="checkNum(this,event)" onKeyDown="changeBlur(event,'SSRS')"  maxlength="8" <%=istr%> /></td>
               	<td rowspan="6" class="td_r_b">34</td>
               	<td rowspan="3" class="td_r_b1" align="center">交通事故情况</td>
               	<td class="td_r_b1" colspan="2">交通事故宗数（起）</td>
               	<td class="td_b" ><input  maxLength="8" <%=istr%> id="SWSG" class="input1" type="text" onkeydown="changeBlur(event,'SRYSQY')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
              		<td class="td_r_b">20</td>
               	<td class="td_r_b1" colspan="2">死亡人数（人次）</td>
               	<td class="td_b"><input  maxLength="8" <%=istr%> id="SWRS" class="input1" type="text" onkeydown="changeBlur(event,'JYYSJSR')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
               	<td class="td_r_b1"  colspan="2">受伤人数（人次）</td>
               	<td class="td_b"><input  maxLength="8" <%=istr%> id="SSRS" class="input1" type="text" onkeydown="changeBlur(event,'ELTQYA')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
               	<td rowspan="3" class="td_r_b1" align="center">其中</td>
               	<td class="td_r_b1"  colspan="2">特大事故宗数（起）</td>
               	<td class="td_b"><input  maxLength="8" <%=istr%> id="TDSG" class="input1" type="text" onkeydown="changeBlur(event,'YJSDFL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
              		<td class="td_r_b">23</td>
               	<td class="td_r_b1"  colspan="2">死亡人数（人次）</td>
               	<td class="td_b"><input  maxLength="8" <%=istr%> id="TDSWRS" class="input1" type="text" onkeydown="changeBlur(event,'ZZWXLD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
              		<td class="td_r_b">24</td>
               	<td class="td_r_b1"  colspan="2">受伤人数（人次）</td>
               	<td class="td_b"><input  maxLength="8" <%=istr%> id="TDSSRS" class="input1" type="text" onkeydown="changeBlur(event,'LSZQD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
         		</tr>
         	</table>
			<table width="99%" align="center" class="tableInput" id="dataTable">
                <tr>
                    <th width="6%" class="td_r_b">序号</th>
                    <th width="30%" class="td_r_b">项目</th>
                    <th width="10%" class="td_r_b">数量</th>
                    <th width="6%"  class="td_r_b">序号</th>
                    <th width="36%" class="td_r_b" colspan="3">项目</th>
                    <th width="10%" class="td_b">数量</th>
                </tr>
                <tr>
                    <td class="td_r_b">1</td>
                    <td class="td_r_b1">投入警力（人次）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" <%=istr%> id="TRJL" class="input1" type="text" onkeydown="changeBlur(event,'XZZYCK')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b">17</td>
                    <td colspan="3" class="td_r_b1">卸客转运乘客（人次）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input maxLength="8" <%=istr%> id="XZZYCK" class="input1" type="text" onkeydown="changeBlur(event,'CDJC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">2</td>
                    <td class="td_r_b1">出动警车（辆次）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="CDJC" class="input1" type="text" onkeydown="changeBlur(event,'YDTB')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b">18</td>
                    <td class="td_r_b1"  colspan="3">深入专业运输企业（个）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="SRYSQY" class="input1" type="text" onkeydown="changeBlur(event,'SWRS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">3</td>
                    <td class="td_r_b1">投入测速设备（台）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="CSSB" class="input1" type="text" onkeydown="changeBlur(event,'PCAQYHC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b">19</td>
                    <td class="td_r_b1" colspan="3" >排除安全隐患客车（辆）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="PCAQYHC" class="input1" type="text" onkeydown="changeBlur(event,'GDCSD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
				 <tr>
                    <td class="td_r_b">4</td>
                    <td class="td_r_b1">设置测速点（个）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="CSD" class="input1" type="text" onkeydown="changeBlur(event,'JYTZKCJSR')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b">20</td>
                    <td class="td_r_b1" colspan="3" >清理驾驶人记分（人）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="TBKYBM" class="input1" type="text" onkeydown="changeBlur(event,'SWSG')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">5</td>
                    <td class="td_r_b1">设置春运执勤服务点（个）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="ZQFWD" class="input1" type="text" onkeydown="changeBlur(event,'ZHCFLB')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b">21</td>
                    <td class="td_r_b1" colspan="3">整治危险路段（处）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="ZZWXLD" class="input1" type="text" onkeydown="changeBlur(event,'TDSSRS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">6</td>
                    <td class="td_r_b1">设置临时执勤点（个）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" <%=istr%> id="LSZQD" class="input1" type="text" onkeydown="changeBlur(event,'TRJL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b">22</td>
                    <td colspan="3" class="td_r_b1">抓获车匪路霸（人）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="ZHCFLB" class="input1" type="text" onkeydown="changeBlur(event,'JTWFHJ')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">7</td>
                    <td class="td_r_b1">查处交通违法行为合计（起）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="JTWFHJ" class="input1" type="text" onkeydown="changeBlur(event,'JXXCHD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td rowspan="11" class="td_r_b">23</td>
                    <td rowspan="11" width="4%" class="td_r_b1" align="center">交通安全宣传情况</td>
                    <td colspan="2" class="td_r_b1">举行宣传活动（场次）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="JXXCHD" class="input1" type="text" onkeydown="changeBlur(event,'CSXS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">8</td>
                    <td class="td_r_b1">查处超速行驶（起）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="CSXS" class="input1" type="text" onkeydown="changeBlur(event,'BFXCGP')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b1">播放宣传光盘（场次）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="BFXCGP" class="input1" type="text" onkeydown="changeBlur(event,'KCJY')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b" rowspan="2">9</td>
                	<td class="td_r_b1" >检查客运车辆（辆）<span style="color:red;font-size:11px;">※</span></td>
                	<td class="td_r_b"><input  maxLength="8" <%=istr%> id="JCKYCL" class="input1" type="text" onkeydown="changeBlur(event,'ZHS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b1" colspan="2" rowspan="2">设固定宣传栏（处）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b" rowspan="2"><input  maxLength="8" <%=istr%> id="KDXCL" class="input1" type="text" onkeydown="changeBlur(event,'PLJS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                 <tr>
                    <td class="td_r_b1">查处客车超员（起）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="KCJY" class="input1" type="text" onkeydown="changeBlur(event,'KDXCL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">10</td>
                    <td class="td_r_b1">查处疲劳驾驶（起）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="PLJS" class="input1" type="text" onkeydown="changeBlur(event,'XCH')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b1">张贴宣传画（张）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="XCH" class="input1" type="text" onkeydown="changeBlur(event,'JHJS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">11</td>
                    <td class="td_r_b1">查处酒后驾驶（起）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="JHJS" class="input1" type="text" onkeydown="changeBlur(event,'XCZL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b1">发放宣传资料（份）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="XCZL" class="input1" type="text" onkeydown="changeBlur(event,'WZJS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">12</td>
                    <td class="td_r_b1">查处无证驾驶（起）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="WZJS" class="input1" type="text" onkeydown="changeBlur(event,'SJY')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b1" width="3%" rowspan="5" align="center">媒体宣传</td>
                    <td class="td_r_b1">电视宣传（条）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="DSXC" class="input1" type="text" onkeydown="changeBlur(event,'DXJDCJSZ')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">13</td>
                    <td class="td_r_b1">查处农用车违法载客（起）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="NYCWFZK" class="input1" type="text" onkeydown="changeBlur(event,'DSXC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b1">电台宣传（条）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="DTXC" class="input1" type="text" onkeydown="changeBlur(event,'ZKJTWFCL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>	
                <tr>
                    <td class="td_r_b" rowspan="2">14</td>
                    <td class="td_r_b1">吊销机动车驾驶证（本）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="DXJDCJSZ" class="input1" type="text" onkeydown="changeBlur(event,'DTXC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b1" rowspan="2">报纸宣传（条）</td>
                    <td class="td_b" rowspan="2"><input  maxLength="8" <%=istr%> id="BZXC" class="input1" type="text" onkeydown="changeBlur(event,'JLJTWFJSR')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b1">暂扣机动车驾驶证（本）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="ZKJDCJSZ" class="input1" type="text" onkeydown="changeBlur(event,'DTXC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b" rowspan="2">15</td>
                    <td class="td_r_b1" rowspan="2">暂扣交通违法车辆（辆次）</td>
                    <td class="td_r_b" rowspan="2"><input  maxLength="8" <%=istr%> id="ZKJTWFCL" class="input1" type="text" onkeydown="changeBlur(event,'BZXC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b1">网络宣传（条）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="WLXC" class="input1" type="text" onkeydown="changeBlur(event,'JCKYCL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>	
                <tr>
                    <td class="td_r_b">24</td>
                    <td class="td_r_b1" colspan="3">启动恶劣天气应急预案（次）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="ELTQYA" class="input1" type="text" onkeydown="changeBlur(event,'TDSG')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
               		<td class="td_r_b">16</td>
                    <td class="td_r_b1">拘留交通违法驾驶人（人次）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr%> id="JLJTWFJSR" class="input1" type="text" onkeydown="changeBlur(event,'WLXC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                	<td class="td_r_b">25</td>
                    <td class="td_r_b1" colspan="3">应急疏导、分流车辆（辆）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr%> id="YJSDFL" class="input1" type="text" onkeydown="changeBlur(event,'TDSWRS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
               <tr>
					<td colspan='8'  id='tfm'  style="border:0">
					
					<!-- 在js里面拼此表格，所以注释掉。
					<table width="100%" align="center"  id="tfmTable" cellpadding="0" cellspacing="0">
						  <tr><td colspan="8" class="td_r_b" align="center">辖区日车流量最大的路段：</td></tr>
					      <tr>
					        <td width="28%" class="td_r_b" colspan="3">道路名称</td>
							<td width="27%" class="td_r_b" colspan="2">路段备注</td>
							<td width="15%" class="td_r_b">交通流量（车次）</td>
							<td width="15%" class="td_r_b">客车（车次）</td>
							<td width="15%" class="td_r_b">自驾车（车次）</td>	
					      </tr>
					</table>
					-->
					</td>
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
		<%@ include file="printOperation.jsp"%>
	</body>
</html>