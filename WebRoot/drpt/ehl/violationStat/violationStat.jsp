<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<%@ page import="com.ehl.sm.common.util.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%
   
	String username = (String)request.getSession().getAttribute("pname");
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串


    String jgid=strObj[0];//单位编码
	String jgmc = strObj[1];//机构名称
    //jgid="440100000000";
  
%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>春运车辆违法统计</title>
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/output/SaveAsExcel.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/style1/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../js/violationStat/violationStat.js"></script>
		<script type="text/javascript" src="../../js/violationStat/statisticsCommon.js"></script>
		<script type="text/javascript" > 
		
		//alert('<%=jgid.substring(2,6) %>');
		
		</script>
	</head>
	<body >
		<input type="hidden" id="jgmcHidden" value="<%=jgmc %>">
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
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
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="5%">
														<div align="center">
															<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
														</div>
													</td>
													<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>：春运车辆违法统计
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
					<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0">
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
													<td class="sleek textB">统计条件</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr align="center" height="35">
													<td width="12%" align="right" class="currentLocation">
														<p align="right">开始时间：</p>													</td>
													<td align="left" width="20%"  >
														<input name="startTime" type='text' id='startTime' style="width:170px;" onClick="SelectDate(this,0);" readOnly>											        </td>
											        <td align="right" width="12%" class="currentLocation">
														<p align="right">结束时间：</p>													</td>
													<td align="left" width="20%">
														<input name="endTime" type='text' id='endTime' style="width:170px;" onClick="SelectDate(this,0);" readOnly>											        </td>
											        <td width="16%" style="text-align:right;" class="currentLocation">违法种类：</td>
											        <td width="20%" style="text-align:left;" class="currentLocation" id="wfzlTdId">
														<script language="javascript">
															fillListBox("wfzlTdId","wfzlId","140","select id,name from T_ATTEMPER_CODE where substr(id,1,3)='215'","","");
														</script>													</td>
											    </tr>
												<tr align="center" height="35">
											        <%
											        	//总队用户
											        	if("0000".equals(jgid.substring(2,6))){
											        %>
											        <script language="javascript">zongduiCallback();</script>
											        <td align="center" width="12%" class="currentLocation" style="text-align:right;">支队：													</td>
											        <td align="center" width="20%" class="currentLocation" id="zhiduiTdId">													</td>
											        <td align="center" width="12%" class="currentLocation" style="text-align:right;">大队：													</td>
													<td align="center" width="20%" class="currentLocation" id="daduiTdId">
														<script language="javascript">
															fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00'","","initTime('<%=jgid%>')");
														</script>													</td>
													
											        <%
											        	//支队用户
											        	}else if("00".equals(jgid.substring(4,6))){
											        %>
											        
											        <td align="center" width="12%" class="currentLocation" style="text-align:right;">支队：													</td>
											        <td align="center" width="20%" class="currentLocation" id="zhiduiTdId">
														<script language="javascript">
									                       fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where jgid='<%=jgid%>'","未选择","zhiduiCallback('<%=jgid%>')");
								                        </script>													</td>
											        <td align="center" width="12%" class="currentLocation" style="text-align:right;">大队：													</td>
													<td align="center" width="20%"  class="currentLocation" id="daduiTdId">													</td>
											        
													
											        <%
											        	//大队用户
											        	}else {
											        	String zhidui00=jgid.substring(0,4)+"00"+jgid.substring(6,jgid.length());
											        	System.out.println(zhidui00);
											        %>
											        
											        <td align="center" width="12%" class="currentLocation" style="text-align:right;">支队：													</td>
											        <td align="center" width="20%" class="currentLocation" id="zhiduiTdId">
														<script language="javascript">
									                       fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,1,4)='<%=jgid.substring(0,4)%>'","未选择","daduiCallbackzhidui(<%=zhidui00%>)");
								                        </script>													</td>
											        <td align="center" width="12%" class="currentLocation" style="text-align:right;">大队：													</td>
											        <td align="center" width="20%" class="currentLocation" id="daduiTdId">
														<script language="javascript">
									                       fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where jgid='<%=jgid%>'","未选择","daduiCallback(<%=jgid%>)");
								                        </script>													</td>
											        <% } %>
											        <td width="16%" style="text-align:right;" class="currentLocation">
														<input id="statisticsCaseInfo" type="image" src="../../images/button/btnStat.gif"  onClick="statistics('<%=jgid%>')"/>													</td>
										          <td width="10%" style="text-align:left;" class="currentLocation">&nbsp;
														  <input id="OutExcelCaseInfo" type="image" src="../../images/button/btnOutExcel.gif" alt="统计有数据时可用导出" onClick="outExcel()"/>  
														 
														  &nbsp;<input name="image" type="image" id="image" onClick="printMe()" src="../../images/button/print.gif" 
																		alt="打印"/>												  </td>	  
												  
												 		
											      <td width="10%" style="text-align:left;" class="currentLocation">&nbsp;</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<tr>
										<td class="height"></td>
									</tr>
									<!-- 占行高部分 -->
									<tr>
										<td class="height"></td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td id="tdData">
											<table class="table" border='2'>
												<tr  align='center'>
													<td  class="wTableTopLeft" width='0%' align='center'></td>
													<td  class="wTableTopCenter" width='100%' align='center'>统计结果标题</td>
													<td  class="wTableTopRight" width='0%' align='center'></td>
												</tr>
												<tr  align='center'> 
													<td  class="wTableCenterLeft" width='0%' align='center'></td>
													<td  class="wTableCenterCenter" >请选择统计条件进行统计！</td>
													<td  class="wTableCenterRight" width='0%' align='center'></td>
												</tr>
											</table>
										</td>
										<td id="tdDataPrint" style="display:none">
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
		<%@ include file="PrintMe.jsp"%>
	</body>
</html>
