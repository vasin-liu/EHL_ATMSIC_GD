<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@page import="com.ehl.dispatch.common.FlowUtil"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("930203");	
		String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
	    String jgid=strObj[0];//单位编码
	    String jgmc=strObj[1];//机构名称
	    String jglx=strObj[3];//机构类型
	    //String jgid="441905000000";//12位机构编码
	    String jgbh = "";//总队(2位),支队(4位),大队(6位)
	    if("0000".equals(jgid.substring(2,6))){
	    	jgbh = jgid.substring(0,2);
	    }else if("00".equals(jgid.substring(4,6))){
	    	jgbh = jgid.substring(0,4);
	    }else{
	    	jgbh = jgid.substring(0,6);
	    }
%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%=title %></title>
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
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/TrafficNewsFeeds.js"></script>	
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
	<body style="background: none; padding-top: 0px;">
	<div id="showDivId"></div>
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
					<input id="jgbh" type="hidden" value="<%=jgbh%>"></input>
					<input id="jgid" type="hidden" value="<%=jgid%>"></input>
					<input id="jgmc" type="hidden" value="<%=jgmc%>"></input>
					<input id="jglx" type="hidden" value="<%=jglx%>"></input>
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
														<span class="currentLocationBold">您当前的位置</span>：<%=title %>
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
										<div align="left" style="width:100%; border: 1px solid #BFBFBF;">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr height="10px">
													<td colspan="7"></td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align:right">
														<label>
															报料时间：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<input type="text" name="sj1" style="width:105" id="sj1" maxLength="50" readonly onclick="SelectDateTime(this,0);" />
														-
														<input type="text" name="sj2" style="width:105" id="sj2" maxLength="50" readonly  onclick="SelectDateTime(this,0);" />
													</td>
													<td class="currentLocation" style="text-align:right;">
														<label>
															道路名称：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<input type="text" id="DLMC" style="width:130"/>
													</td>
													<td class="currentLocation" style="text-align:right">
														<label>
															处理状态：
														</label>
													</td>
													<td id="columnTd" align="left">
														<select id="CLZT" style="width:85">
															<%
																if("0".equals(jglx)){
															%>
															<option value="" selected>全部</option>
															<option value="0" >待总队处理</option>
															<option value="1" >待大队核实</option>
															<option value="2" >待总队确认</option>
															<option value="3" >处理完成</option>
															<option value="4" >已忽略</option>
															<%
																}else if("1".equals(jglx)||"2".equals(jglx)){
															%>
															<option value="" selected>全部</option>
															<option value="1" >待大队核实</option>
															<option value="2" >待总队确认</option>
															<option value="3" >处理完成</option>
															<%
																}
															%>
														</select>
													</td>
													<td  align="left">
														<div class="lsearch">
															<a href="#" onclick="doSearch();" class="currentLocation">
																<span class="lbl">查询</span>
															</a>
														</div>
													</td>
												</tr>
												<tr height="10px">
													<td colspan="5"></td>
												</tr>
												<tr style="display:none;">
													<td class="currentLocation" style="text-align:right;">
														<label>
															交通路况：
														</label>
													</td>
													<td id="columnTd" align="left" style="width:133">
														<select id="LK" style="width:133">
															<option value="" >未选择</option>
															<option value="缓慢" >缓慢</option>
															<option value="拥堵" >拥堵</option>
															<option value="堵塞" >堵塞</option>
															<option value="道路封闭" >道路封闭</option>
														</select>
													</td>
													<td align="left">
														<div style="width:11;float:right"></div>
														<div class="lsearch" style="display:none;">
															<a href="#"
																onclick="showExcel('<%=jgbh%>')"
																class="currentLocation"><span class="lbl">导出Excel</span>
															</a>
														</div>
													</td>
												</tr>
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
		<script type="text/javascript">doSearch();</script>
		<input type="hidden" name="hdncount" value="1" id="hdcount"> 
	</body>
</html>
