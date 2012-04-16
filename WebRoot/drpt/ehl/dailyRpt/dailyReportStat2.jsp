<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="../../../base/Message.oni"%>

<%@ page import="com.ehl.sm.common.util.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%
   
	//String username = (String)request.getSession().getAttribute("pname");
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    //jgid="440100000000";
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
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
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
	    <!-- 当前位置table开始 -->
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
		</table> <!-- 当前位置结束 -->
	
	    
		<!-- 查询条件 table 开始 -->
		<table border="0" cellspacing="0" cellpadding="0" width=99% align=center>
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
									fillListBox("daduiTdId","daduiId","170",
									     "select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00' order by jgid",
										  "","initTime('<%=jgid%>')");
								</script>
							</td>
							
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
									alt="打印" onClick="printFor()"/>	
									
								<!-- 
								<input type="button" onClick="javascript:AllAreaExcel();" value="导出Excel">	
								-->															
							 </td>
						</tr>
					</table>
			  </td>
			</tr>
		</table><!--查询条件table 结束 -->
	
	    
		
		
		
		
		<!-- 显示前33项内容开始  -->
		
		<div id="print">
		<table width="99%" align="center" class="tableInput" id="dataTable">
			<tr>
				<th width="10%" class="td_r_b">序号</th>
				<th width="28%" class="td_r_b">项目</th>
				<th width="8%" class="td_r_b">数量</th>
				<th width="10%" colspan="2" class="td_r_b">序号</th>
				<th width="36%" colspan="2" class="td_r_b">项目</th>
				<th width="8%" class="td_b">数量</th>
			</tr>
			<tr>
				<td class="td_r_b">1</td>
				<td class="td_r_b1">投入警力（人次）1</td>
				<td class="td_r_b"><input class="input1" type="text" id="text1" readonly/></td>
				<td colspan="2" class="td_r_b">25</td>
				<td colspan="2" class="td_r_b1">卸客转运乘客（人次）</td>
				<td class="td_b"><input type="text" class="input1" id="text24" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">2</td>
				<td class="td_r_b1">出动警车（辆次）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text2" readonly/></td>
				<td colspan="2" class="td_r_b">26</td>
				<td colspan="2" class="td_r_b1"><p>通报异地超速50%以上、</p>
				<p>客车超员20%以上交通违法行为（起）</p></td>
				<td class="td_b"><input    type="text" class="input1" id="text25" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">3</td>
				<td class="td_r_b1">投入测速设备（台）</td>
			  <td class="td_r_b"><input class="input1" type="text" id="text3" readonly/></td>
				<td colspan="2" class="td_r_b">27</td>
				<td colspan="2" class="td_r_b1">排除安全隐患客车（辆）<span style="color:red;font-size:11px;">※</span></td>
				<td class="td_b"><input   type="text" class="input1" id="text26" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">4</td>
				<td class="td_r_b1">设置固定测速点（个）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text4" readonly/></td>
				<td colspan="2" class="td_r_b">28</td>
				<td colspan="2" class="td_r_b1">建议调整客车驾驶人（人次）</td>
				<td class="td_b"><input type="text" class="input1" id="text27" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">5</td>
				<td class="td_r_b1">设置流动测速点（个）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text5" readonly/></td>
				<td colspan="2" class="td_r_b">29</td>
				<td colspan="2" class="td_r_b1">强制疲劳驾驶人休息（人次）</td>
				<td class="td_b"><input   type="text" class="input1" id="text28" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">6</td>
				<td class="td_r_b1">设置春运执勤服务点（个）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text6" readonly/></td>
				<td colspan="2" class="td_r_b">30</td>
				<td colspan="2" class="td_r_b1">抓获车匪路霸（人）</td>
				<td class="td_b"><input  type="text" class="input1" id="text29" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">7</td>
				<td class="td_r_b1">查处交通违法行为合计（起）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text7" readonly/></td>
				<td rowspan="10" class="td_r_b">31</td>
				<td rowspan="10" width="4%" class="td_r_b1">交通安全宣传情况</td>
				<td colspan="2" class="td_r_b1">举行宣传活动（场次）</td>
				<td class="td_b"><input   type="text" class="input1" id="text30" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">8</td>
				<td class="td_r_b1">查处超速行驶（起）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text8" readonly/></td>
				<td colspan="2" class="td_r_b1">播放宣传光盘（场次）</td>
				<td class="td_b"><input   type="text" class="input1" id="text31" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">9</td>
				<td class="td_r_b1">查处客车超员（起）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text9" readonly/></td>
				<td colspan="2" class="td_r_b1">设固定宣传栏（处）</td>
				<td class="td_b"><input name="text32"  type="text" class="input1" id="text32" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">10</td>
				<td class="td_r_b1">查处疲劳驾驶（起）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text10" readonly/></td>
				<td colspan="2" class="td_r_b1">张贴宣传画（张）</td>
				<td class="td_b"><input name="text33"  type="text" class="input1" id="text33" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">11</td>
				<td class="td_r_b1">查处酒后驾驶（起）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text11" readonly/></td>
				<td colspan="2" class="td_r_b1">发放宣传资料（份）</td>
				<td class="td_b"><input name="text34"  type="text" class="input1" id="text34" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">12</td>
				<td class="td_r_b1">查处无证驾驶（起）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text12" readonly/></td>
				<td colspan="2" class="td_r_b1">受教育（人次）</td>
				<td class="td_b"><input class="input1" type="text"  id="text35" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">13</td>
				<td class="td_r_b1">查处农用车违法载客（起）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text13" readonly/></td>
				<td class="td_r_b1" width="4%" rowspan="4">媒体宣传</td>
				<td class="td_r_b1">电视宣传（条）</td>
				<td class="td_b"><input class="input1"   type="text" id="text36" readonly/></td>
			</tr>	
			<tr>
				<td class="td_r_b">14</td>
				<td class="td_r_b1">吊销机动车驾驶证（本）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text14" readonly/></td>
				<td class="td_r_b1">电台宣传（条）</td>
				<td class="td_b"><input class="input1"  type="text" id="text37" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">15</td>
				<td class="td_r_b1">暂扣交通违法车辆（辆次）</td>
				<td class="td_r_b"><input class="input1" type="text" id="text15" readonly/></td>
				<td class="td_r_b1">报纸宣传（条）</td>
				<td class="td_b"><input   type="text" class="input1" id="text38" readonly/></td>
			</tr>	
			<tr>
				<td rowspan="1" class="td_r_b">16</td>
				<td rowspan="1" class="td_r_b1">拘留交通违法驾驶人（人次）</td>
				<td rowspan="1" class="td_r_b"><input type="text" class="input1" id="text16" readonly/></td>
				<td class="td_r_b1">网络宣传（条）</td>
				<td class="td_b"><input  type="text" class="input1" id="text39" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">17</td>
				<td class="td_r_b1">检查客运车辆（辆）<font color="red">※</font></td>
				<td class="td_r_b"><input type="text" class="input1" id="text17" readonly/></td>
				<td colspan="2" class="td_r_b">32</td>
				<td colspan="2" class="td_r_b1">为群众做好事（件）</td>
				<td class="td_b"><input  type="text" class="input1" id="text40" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">18</td>
				<td class="td_r_b1">清理驾驶人记分（人）<font color="red">※</font></td>
				<td class="td_r_b"><input type="text" class="input1" id="text18" readonly/></td>
				<td rowspan="7" class="td_r_b">33</td>
				<td colspan="2" rowspan="3" class="td_r_b1" align="center">交通事故情况</td>
				<td class="td_r_b1">交通事故宗数（起）</td>
				<td class="td_b"><input  type="text" class="input1" id="text41" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">19</td>
				<td class="td_r_b1">深入专业运输企业（个）<font color="red">※</font></td>
				<td class="td_r_b"><input type="text" class="input1" id="text19" readonly/></td>
				<td class="td_r_b1">死亡人数（人次）</td>
				<td class="td_b"><input   type="text" class="input1" id="text42" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">20</td>
				<td class="td_r_b1">教育运输企业驾驶人（人次）<font color="red">※</font></td>
				<td class="td_r_b"><input  type="text" class="input1" id="text20" readonly/></td>
				<td class="td_r_b1">受伤人数（人次）</td>
				<td class="td_b"><input   type="text" class="input1" id="text43" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">21</td>
				<td class="td_r_b1">启动恶劣天气应急预案（次）<font color="red">※</font></td>
				<td class="td_r_b"><input  type="text" class="input1" id="text21" readonly/></td>
				<td colspan="2" rowspan="4" class="td_r_b1" align="center">其中</td>
				<td class="td_r_b1">特大事故宗数（起）</td>
				<td class="td_b"><input  type="text" class="input1" id="text44" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">22</td>
				<td class="td_r_b1">应急疏导、分流车辆（辆）<font color="red">※</font></td>
				<td class="td_r_b"><input  type="text" class="input1" id="text22" readonly/></td>
				<td class="td_r_b1">死亡人数（人次）</td>
				<td class="td_b"><input  type="text" class="input1" id="text45" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">23</td>
				<td class="td_r_b1">整治危险路段（处）<font color="red">※</font></td>
				<td class="td_r_b"><input  type="text" class="input1" id="text23" readonly/></td>
				<td class="td_r_b1" rowspan="2" >受伤人数（人次）</td>
				<td class="td_b" rowspan="2" ><input  type="text" class="input1" id="text46" readonly/></td>
			</tr>
			<tr>
				<td class="td_r_b">24</td>
				<td class="td_r_b1">设置临时执勤点（个）<font color="red">※</font></td>
				<td class="td_r_b"><input  type="text" class="input1" id="text47" readonly/></td>
			</tr>
			<!-- 用poi导出流量的数据到Excel，暂时保留，功能已经具备。
			<tr>
				<td colspan='8'  align="right"><input id="OutExcelCaseInfo" type="image" src="../../images/button/btnOutExcel.gif" 
									alt="统计有数据时可用导出" onClick="outExcel()"/>
			</tr>
			-->		   
			<tr>
				<td colspan='8'  id='tfm' >
				
				<!-- 在js里面拼此表格，所以注释掉。
				<table width="100%" align="center"  id="tfmTable">
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
		<!-- 显示前33项内容结束 -->
	
	    <!-- 显示流量数据开始 -->
		<table width="99%" align="center" class="tableInput" id="tfmTable">
		<tr>
			<td colspan='8'  id='tfm' >
		</table>
	    <!-- 显示流量数据结束 -->
		
		
		
		<!-- 底部table 开始 -->
		<table width="99%" align="center" class="tableInput" id="footTable">
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
		</table>
		<!-- 底部talbe 结束 -->
		<%@ include file="printOperation.jsp"%>
	</body>
</html>