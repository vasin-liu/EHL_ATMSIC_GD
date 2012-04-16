<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ include file="../../../sm/Message.oni"%>
<%@page import="com.ehl.sm.pcs.DepartmentManage"%>
<% 
   String deptId = "";
   String deptName = "";
   try{
	   String deptInfo =  DepartmentManage.getDeptInfo(request,"1");
	   deptId = deptInfo.split(",")[0];
	   deptName = deptInfo.split(",")[1];
   }catch(Exception e){
       e.printStackTrace();
   }
%>
<html>
  <head>
    <title>设备信息维护</title>	
    <meta http-equiv="Content-Type" content="text/html charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/Equipment.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/page/PageCtrl.js"></script>
	<script type="text/javascript" src="../../js/tfm/TfmMain.js"></script>
	
  </head>

	<body  onload="filter();">
	    <table width="100%" height="100%">
	       <tr>
	          <td width="228">
	              <table width="100%" height="100%" border="1">
	                  <tr>
	                      <td>&nbsp;</td>
	                  
	                  </tr>
	                   <tr height="34%">
	                      <td> <iframe id="rightContent" name="rightContent" src="tfm.jsp" width="100%" height="100%" scrolling="no" scroll="no" frameborder="0" framespacing="0px"></iframe></td>
	                  
	                  </tr>
	                   <tr>
	                      <td>&nbsp;</td>
	                  
	                  </tr>
	              
	              </table>
	          </td>
	          <td>
				<div  class="table_tab" align="center">
					<ul id="tags" align="center">
						<li class='selectTag'>
							<a href='#' class="td_2">路况信息</a>
						</li>
					</ul>
					<!--查询区域2008-9-19-->
					<div id="tagContent" align="center">
						<div id="info" class='tagContent selectTag'>
							<table width="100%" border="0">
								<tr>
									<td class="td_2" width="25%" align="right">
										连线名称：
									</td>
									<td align="left" id='LineTdId' class="td_2">
										<script language="javascript">
							                 fillListBox("LineTdId","lineId","175","SELECT LINKDIRID,LINKDIRNAME FROM t_tfm_link_dir ","");
										</script>
									</td>
									<td width="25%" align="right" class="td_2">
										道路状况：
									</td>
									<td width="5%" align="left" id="SBZTTdId" class="td_2">
									    <select id="statusId" style="width:175">
									        <option value="">全部</option>
									        <option value="畅通">畅通</option>
									        <option value="拥挤">拥挤</option>
									        <option value="拥堵" selected>拥堵</option>
									    </select>
									</td>
									<td width="20%" align="center" valign="top">
										<input name="query" type="image"
											src="../../image/button/btnquery.gif" onClick="filter();" />
									</td>
									
								</tr>
								
							</table>
						</div>
					</div>
					<!--查询区域2008-9-19-->
		
					<!--数据表格2008-9-19 -->
					<div id="tagContent" align="center"
						style="height: 75%; width: 781; margin-top: 1">
						<table width="100%" border="0" height="100%" style="margin-left: 3;">
							<tr height="100%">
								<td width="45%" height="100%" valign="top">
									<div id="tdData" class="rollDiv">
										<table id="tabVeh" class="scrollTable" width=100% cellSpacing=0
											cellPadding=0>
											<tr class='scrollColThead' valign="top"
												style="text-decoration: none; background-color: #B4C1E2; line-height: 20px;">
												<td width='15%' align='center'>
													所属辖区
												</td>
												<td width='20%' align='center'>
													连线名称
												</td>
												<td width='5%' align='center'>
													方向
												</td>
												<td width='7%' align='center'>
													道路路况
												</td>
												<td width='40%' align='center'>
													拥堵或拥挤原因
												</td>
												
												<td width='5%' align='center'>
													修改
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<!--数据表格2008-9-19-->
				</div>
			 </td>
			</tr>
		 </table>
	</body>
</html>
