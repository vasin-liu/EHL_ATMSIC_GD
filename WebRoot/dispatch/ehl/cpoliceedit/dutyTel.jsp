<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.duty.dao.DutyDao"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>值班电话查询</title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/js/DataTable/css/demo_page.css"
			rel="stylesheet" type="text/css" />
		<link href="../../../base/js/DataTable/css/demo_table.css"
			rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="../../../base/js/prototype.js"></script>
			<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" language="javascript"
			src="../../../base/js/jquery.js"></script>
		<script type="text/javascript" language="javascript"
			src="../../../base/js/DataTable/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="../../js/duty/dutyTel.js"></script>
		<script type="text/javascript" src="../../../base/js/tree/tree.js"></script>
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
}

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
	filter: glow(color =           #ffffff, strength =           1);
}
</style>
	<script type="text/javascript">
			jQuery.noConflict();
</script>
<script type="text/javascript">

  jQuery(document).ready(function(){
 	doOnload();
 });
</script>



	</head>
	<body style="background: none; padding-top: 0px;" id="dt_example">
		<table width="100%" height="100%" border="0" cellspacing="0"
			cellpadding="0">
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
															<img src="../../../base/image/cssimg/table/tb.gif"
																width="16" height="16" alt="img" />
														</div>
													</td>
													<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>值班电话查询
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
										<td width="66%" align="left" class="height"></td>
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
								
									<tr>
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td width="73"></td>
												</tr>
												
												<tr height="10px">
													<td></td>
												</tr>
												<tr>
													<td width="73" align="left" id="columnTd"
														style="text-align: right">&nbsp;
														
													</td>
													
												
													<td width="160" style="width: 160; text-align: right">
														<label>按部门名称搜索：
														</label>
													</td>
													<%
													
														String excelId = StringHelper.obj2str(request.getParameter("jgid"),"");
													%>
													<td   id="columnTd">
														<input type="hidden" id="jgmcId" value="" />
														<input type="hidden" id="excelId" value="<%=excelId %>" />
														<input name="jgmc" type="text"
															id="jgmc" style="border: 1px #7B9EBD solid"
															onClick="setTree('440000000000','84','440')" size="60" readonly /><img src="../../images/popup/btnselect.gif" alt="选择机构" align="absbottom" style="cursor:hand;" onClick="setAllTree('440000000000','84','440')">
								          </td>
													<td width="119" align="center" id="columnTd"><div class="lsearch">
													
															<a href="#" onClick="searchResult();"
																class="currentLocation"><span class="lbl">查询</span>
															</a>

														</div></td>

													<td width="194">
												
														<div class="lsearch">
															<a href="#" onClick="showExcelInfo();"
																class="currentLocation"><span class="lbl">导出Excel</span>
															</a>

														</div>
														
													</td>
												</tr>

												<tr height="10px">
													<td></td>
												</tr>
											
											</table>
										</td>
									</tr>
									
									<!-- 查询条件部分end -->
									<!-- 占行高部分 -->
									<tr>
										<td class="height"></td>
									</tr>
									<!-- 数据列表-->

									<%
									String jgid = StringHelper.obj2str(request.getParameter("jgid"),
									"");
									String jgidStr="";
									if(!jgid.equals("")){
										jgid = jgid.replace("；",",");
										jgidStr = jgid.substring(0,jgid.lastIndexOf(","));
									}
									DutyDao dao = new DutyDao();
									Object[][] result = dao.getTelAll(jgidStr);
									%>
									<tr>
										<td id="tdData" colspan="3">
											<div
												style='text-align: center; width: 100%; line-height: 22px; float: left;'>
												<span class='currentLocationBold'>查询结果</span>
											</div>
											<table cellpadding="0" cellspacing="0" border="0"
												class="display" id="data">
												<thead>
													<tr align="center">
													<th>
															序号
														</th>
														<th>
															部门名称
														</th>
														<th>
															值班电话
														</th>
													</tr>
												</thead>
												<tbody>
													<%
														if(result!=null){
															for(int i=0;i<result.length;i++){
													%>
													<tr align="center">		
														<td>
														<%=i+1 %>
														</td>
														<%
															for(int j=0;j<result[i].length;j++){
														%>
														<td>
														<%=StringHelper.obj2str(result[i][j],"") %>
														</td>
														<%}%>
													</tr>
													<%	}	
													}else{
													%>
													<tr>
														<td colspan="3" align="center">
															<font color="red"><strong>当前搜索出0条记录！</strong> </font>
														</td>
													</tr>
													<%} %>
													<tr>
														<td colspan="8" align="center">
															<font color="red">&nbsp;</font>
														</td>
													</tr>
												</tbody>
												<!--  
											<tfoot>
												<tr>
													<tr>
													<th>
														序号
													</th>
													<th>
														车牌号码
													</th>
													<th>
														车辆类型
													</th>
													<th>
														填报单位
													</th>
													<th>
														填报人员
													</th>
													<th>
														填报时间
													</th>
													<th>
													 明細
													</th>
													<th>
													 修改
													</th>
													<th>
													 刪除
													</th>

												</tr>
											</tfoot>
											-->
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
		</table>

	</body>
</html>
