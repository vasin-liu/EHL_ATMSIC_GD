<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>
<%
	String[] strObj = DepartmentManage.getDeptInfo(request, "1").split(",");//获取机构信息串
	String jgid = strObj[0];//机构编码
	String jgmc = strObj[1];//机构名称
	String ccbm = strObj[2];//机构层次编码
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>警情投诉管理</title>
		<jsp:include page="../../../base/ShareInc.html" />
		<link rel="stylesheet" type="text/css" href="../../css/pagetag/pagetag.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<%--<script type="text/javascript" src="../../js/ceditpolice/DepartmentTree.js"></script>--%>
		<script type="text/javascript" src="../../js/ceditpolice/ComplaintInfo.js"></script>
		<script language="javascript">
	//定义全局标量
	var G_jgID = <%=jgid%>;
	var G_divID  = "";
	function doOnLoad(){
	 	  //onLoadToolbar();
	 	  //isHaveRoot();
	 	  //doDepartInfo('<%=jgid%>');
	 	  //adjustHeight('dpTable,dpTD');
	 	}
	 	
	 </script>
	<style type="text/css"> 
		body {
			margin-left:0;
			margin-right:0;
		}
		.tdtitle_a{
			border-top: 0 solid #000000;
			border-right: 1 solid #CCCCCC;
			border-bottom: 1 solid #CCCCCC;
			border-left: 1 solid #CCCCCC;
			line-height: 16px;
			color: #000000;
			border-collapse : separate;
			empty-cells:show;
			text-align: right;
			
		}
		
		.tdvalue_a{
			border-top: 0 solid #000000;
			border-right: 1 solid #CCCCCC;
			border-bottom: 1 solid #CCCCCC;
			border-left: 1 solid #CCCCCC;
			line-height: 16px;
			color: #000000;
			border-collapse : separate;
			empty-cells:show;
			text-align: left;
		}

	</style>
	</head>
	<body onLoad="doOnLoad();" bgcolor="#FFFFFF" text="#000000"
		style="padding-top: 0px;height:100%;overflow:hidden;">
		<table id="dpTable" width="100%" border="0" cellpadding="0"
			cellspacing="0"
			style="txt-align:center;position:absolute;top:2;height:100%">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="3">
								<img src="../../images/back/back_topleft.gif" width="3"
									height="1" />
							</td>
							<td background="../../images/back/back_topmid.gif">
								<img src="" alt="22" name="" width="32" height="1" align="left" />
							</td>
							<td width="3">
								<img src="../../images/back/back_topright.gif" width="3"
									height="1" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="txt-align:center" valign="top">
					<table width="100%" border="0" style="txt-align:center"
						cellpadding="0" cellspacing="0">
						<tr>
							<td id="dpTD" width="3"
								background="../../images/back/back_midleft.gif"></td>
							<td>
								<table width="100%" height="88%" border="0" align="center">
									<tr>
										<td height="25" valign="top" colspan="3">
											<div id="toolbar_zone"
												style="width: 100%; border: 1px solid #BFBFBF;" />
												<div id="modify"></div>
										</td>
									</tr>
									<tr>
										<td colspan=3 align="center" valign="bottom">
											<span class="STYLE1">警情投诉管理</span>
										</td>
									</tr>
									<tr>
										<td colspan=3 valign="top">
											<hr>
										</td>
									</tr>
									<tr>
										<td width="4%">
										<td width="738">
											<ul id="tagsd" align="center">
												<li class='selectTag'>
													<a href='#' onclick="selectTag('a',this)" id="aa"><font
														style=font-size:12px;>群众投诉</font> </a>
												</li>
												<br>
												<li>
													<a href='#' onclick="selectTag('b',this)"  id="bb"><font
														style=font-size:12px;>指挥科审核</font> </a>
												</li>
												<br>
												<li>
													<a href='#' onclick="selectTag('c',this)" id="cc"><font
														style=font-size:12px;>指挥处审核</font> </a>
												</li>
												<br>
												<li>
													<a href='#' onclick="selectTag('d',this)" id="dd"><font
														style=font-size:12px;>局长审核</font> </a>
												</li>
												<br>
												<li>
													<a href='#' onclick="selectTag('e',this)" id="ee"><font
														style=font-size:12px;>业务处审核</font> </a>
												</li>
												<br>
												<li>
													<a href='#' onclick="selectTag('f',this)" id="ff"><font
														style=font-size:12px;>支队承办</font> </a>
												</li>
												<br>
												<li>
													<a href='#' onclick="selectTag('g',this)" id="gg"><font
														style=font-size:12px;>大队承办</font> </a>
												</li>
												<br>
												<li>
													<a href='#' onclick="selectTag('h',this)" id="hh"><font
														style=font-size:12px;>支队签收</font> </a>
												</li>
												<br>
												<li>
													<a href='#' onclick="selectTag('k',this)" id="kk"><font
														style=font-size:12px;>总队签收</font> </a>
												</li>
												<br>
											</ul>
											<table style="height:23">
												<tr>
													<td></td>
												</tr>
											</table>
											<div id="tagContentd" align="center" valign="top">
												<div id='a' class='tagContentd selectTag' valign="top">
													<div style="height:30px;width:680px;">
														<span style="width:150px">登记人：<input type="text" id="JGID" readOnly value="测试用户" style="width:80px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
														<span style="width:150px"><input type="text" id="TSDJSJ_Y" value="2010" readOnly style="width:40px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>年
														<input type="text" id="TSDJSJ_M" value="04" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>月
														<input type="text" id="TSDJSJ_D" value="27" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>日</span>
														<span style="width:240px">处理单号：<input type="text" value="44000020100427101103123" id="CPID" readOnly style="width:180px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
													</div>
													<div style="height:100%;width:680px;overflow:hidden;offsetTop:0px;border: 1px solid #99C4F2">
														<table class="table2" width="100%" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tbody class='scrollContent'>
																<tr>
																	<td width="20%" align="left" class="tdtitle_a">
																		投诉人：
																	</td>
																	<td width="20%" class="tdvalue_a">
																		<input type="text" id="JGID" class="textwidth"/>
																	</td>
																	<td width="20%" align="left" class="tdtitle_a">
																		联系电话：
																	</td>
																	<td width="40%" class="tdvalue_a">
																		<input type="text" id="JGMC"
																			 class="textwidth"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="checkbox" value="交通违法" name="reason"
																			id="reason_1">
																		交通违法
																		<input type="checkbox" value="事故处理" name="reason"
																			id="reason_2">
																		事故处理
																		<input type="checkbox" value="车管办证" name="reason"
																			id="reason_3">
																		车管办证
																		<input type="checkbox" value="其他" name="reason"
																			id="reason_4">
																		其他
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉内容：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="YWMS"  class="textwidth" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		交通指挥科意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth" style="background-color:#dddddd;" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		交通指挥处意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth" style="background-color:#dddddd;" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		业务处主管部门意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="BZRS" class="textwidth" style="background-color:#dddddd;" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		局领导意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="ZBZGS"  class="textwidth" style="background-color:#dddddd;" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="right" colspan="4">
																		<input type="button" value="发送" id="close"
																			onclick="selectTag('b',$('bb'));">
																		<input type="button" value="重置" id="close"
																			onclick="window.close();">
																		<input type="button" value="打印" id="close"
																			onclick="window.close();">
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
												<div id='b' class='tagContentd' valign="top">
													<div style="height:30px;width:680px;">
														<span style="width:150px">登记人：<input type="text" id="JGID" readOnly value="测试用户" style="width:80px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
														<span style="width:150px"><input type="text" id="TSDJSJ_Y" value="2010" readOnly style="width:40px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>年
														<input type="text" id="TSDJSJ_M" value="04" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>月
														<input type="text" id="TSDJSJ_D" value="27" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>日</span>
														<span style="width:240px">处理单号：<input type="text" value="44000020100427101103123" id="CPID" readOnly style="width:180px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
													</div>
													<div style="height:100%;width:680px;overflow:hidden;offsetTop:0px;border: 1px solid #99C4F2">
														<table class="table2" width="100%" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tbody class='scrollContent'>
																<tr>
																	<td width="20%" align="left" class="tdtitle_a">
																		投诉人：
																	</td>
																	<td width="20%" class="tdvalue_a">
																		<input type="text" id="JGID"
																			 class="textwidth"/>
																	</td>
																	<td width="20%" align="left" class="tdtitle_a">
																		联系电话：
																	</td>
																	<td width="40%" class="tdvalue_a">
																		<input type="text" id="JGMC"
																			 class="textwidth"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="checkbox" disabled value="交通违法" name="reason"
																			id="reason_1">
																		交通违法
																		<input type="checkbox" disabled value="事故处理" name="reason"
																			id="reason_2">
																		事故处理
																		<input type="checkbox" disabled value="车管办证" name="reason"
																			id="reason_3">
																		车管办证
																		<input type="checkbox" disabled value="其他" name="reason"
																			id="reason_4">
																		其他
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉内容：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="YWMS"  class="textwidth"/>
																	</td>
																</tr>
																<tr>
																	
																	<td align="left" class="tdtitle_a">
																		交通指挥科意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		交通指挥处意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		业务处主管部门意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="BZRS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		局领导意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="ZBZGS"  class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
															<tr>
																<td align="right" class="tdtitle_a" colspan="4">
																	<input type="button" value="发送" id="close"
																		onclick="selectTag('c',$('cc'));">
																	<input type="button" value="重置" id="close"
																		onclick="window.close();">
																	<input type="button" value="打印" id="close"
																		onclick="window.close();">
																</td>
															</tr>
														</table>
													</div>
												</div>
												<div id='c' class='tagContentd' valign="top">
													<div style="height:30px;width:680px;">
														<span style="width:150px">登记人：<input type="text" id="JGID" readOnly value="测试用户" style="width:80px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
														<span style="width:150px"><input type="text" id="TSDJSJ_Y" value="2010" readOnly style="width:40px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>年
														<input type="text" id="TSDJSJ_M" value="04" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>月
														<input type="text" id="TSDJSJ_D" value="27" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>日</span>
														<span style="width:240px">处理单号：<input type="text" value="44000020100427101103123" id="CPID" readOnly style="width:180px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
													</div>
													<div style="height:100%;width:680px;overflow:hidden;offsetTop:0px;border: 1px solid #99C4F2">
														<table class="table2" width="100%" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tbody class='scrollContent'>
																<tr>
																	<td width="20%" align="left" class="tdtitle_a">
																		投诉人：
																	</td>
																	<td width="20%" class="tdvalue_a">
																		<input type="text" id="JGID"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																	<td width="20%" align="left" class="tdtitle_a">
																		联系电话：
																	</td>
																	<td width="40%" class="tdvalue_a">
																		<input type="text" id="JGMC"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="checkbox" disabled value="交通违法" name="reason"
																			id="reason_1">
																		交通违法
																		<input type="checkbox" disabled value="事故处理" name="reason"
																			id="reason_2">
																		事故处理
																		<input type="checkbox" disabled value="车管办证" name="reason"
																			id="reason_3">
																		车管办证
																		<input type="checkbox" disabled value="其他" name="reason"
																			id="reason_4">
																		其他
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉内容：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="YWMS"  class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	
																	<td align="left" class="tdtitle_a">
																		交通指挥科意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		交通指挥处意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		业务处主管部门意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="BZRS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		局领导意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="ZBZGS"  class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
															<tr>
																<td align="right" class="tdtitle_a" colspan="4">
																	<input type="button" value="发送" id="close"
																		onclick="selectTag('d',$('dd'));">
																	<input type="button" value="重置" id="close"
																		onclick="window.close();">
																	<input type="button" value="打印" id="close"
																		onclick="window.close();">
																</td>
															</tr>
														</table>
													</div>
												</div>

												<div id='d' class='tagContentd' valign="top">
													<div style="height:30px;width:680px;">
														<span style="width:150px">登记人：<input type="text" id="JGID" readOnly value="测试用户" style="width:80px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
														<span style="width:150px"><input type="text" id="TSDJSJ_Y" value="2010" readOnly style="width:40px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>年
														<input type="text" id="TSDJSJ_M" value="04" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>月
														<input type="text" id="TSDJSJ_D" value="27" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>日</span>
														<span style="width:240px">处理单号：<input type="text" value="44000020100427101103123" id="CPID" readOnly style="width:180px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
													</div>
													<div style="height:100%;width:680px;overflow:hidden;offsetTop:0px;border: 1px solid #99C4F2">
														<table class="table2" width="100%" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tbody class='scrollContent'>
																<tr>
																	<td width="20%" align="left" class="tdtitle_a">
																		投诉人：
																	</td>
																	<td width="20%" class="tdvalue_a">
																		<input type="text" id="JGID"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																	<td width="20%" align="left" class="tdtitle_a">
																		联系电话：
																	</td>
																	<td width="40%" class="tdvalue_a">
																		<input type="text" id="JGMC"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="checkbox" disabled value="交通违法" name="reason"
																			id="reason_1">
																		交通违法
																		<input type="checkbox" disabled value="事故处理" name="reason"
																			id="reason_2">
																		事故处理
																		<input type="checkbox" disabled value="车管办证" name="reason"
																			id="reason_3">
																		车管办证
																		<input type="checkbox" disabled value="其他" name="reason"
																			id="reason_4">
																		其他
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉内容：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="YWMS"  class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	
																	<td align="left" class="tdtitle_a">
																		交通指挥科意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		交通指挥处意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		业务处主管部门意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="BZRS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		局领导意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="ZBZGS"  class="textwidth"/>
																	</td>
																</tr>
															<tr>
																<td align="right" class="tdtitle_a" colspan="4">
																	<input type="button" value="发送" id="close"
																		onclick="selectTag('e',$('ee'));">
																	<input type="button" value="重置" id="close"
																		onclick="window.close();">
																	<input type="button" value="打印" id="close"
																		onclick="window.close();">
																</td>
															</tr>
														</table>
													</div>
												</div>

												<div id='e' class='tagContentd' valign="top">
													<div style="height:30px;width:680px;">
														<span style="width:150px">登记人：<input type="text" id="JGID" readOnly value="测试用户" style="width:80px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
														<span style="width:150px"><input type="text" id="TSDJSJ_Y" value="2010" readOnly style="width:40px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>年
														<input type="text" id="TSDJSJ_M" value="04" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>月
														<input type="text" id="TSDJSJ_D" value="27" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>日</span>
														<span style="width:240px">处理单号：<input type="text" value="44000020100427101103123" id="CPID" readOnly style="width:180px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
													</div>
													<div style="height:100%;width:680px;overflow:hidden;offsetTop:0px;border: 1px solid #99C4F2">
														<table class="table2" width="100%" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tbody class='scrollContent'>
																<tr>
																	<td width="20%" align="left" class="tdtitle_a">
																		投诉人：
																	</td>
																	<td width="20%" class="tdvalue_a">
																		<input type="text" id="JGID"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																	<td width="20%" align="left" class="tdtitle_a">
																		联系电话：
																	</td>
																	<td width="40%" class="tdvalue_a">
																		<input type="text" id="JGMC"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="checkbox" disabled value="交通违法" name="reason"
																			id="reason_1">
																		交通违法
																		<input type="checkbox" disabled value="事故处理" name="reason"
																			id="reason_2">
																		事故处理
																		<input type="checkbox" disabled value="车管办证" name="reason"
																			id="reason_3">
																		车管办证
																		<input type="checkbox" disabled value="其他" name="reason"
																			id="reason_4">
																		其他
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉内容：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="YWMS"  class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	
																	<td align="left" class="tdtitle_a">
																		交通指挥科意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		交通指挥处意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		业务处主管部门意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="BZRS" class="textwidth"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		局领导意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="ZBZGS"  class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
															<tr>
																<td align="right" class="tdtitle_a" colspan="4">
																	<input type="button" value="发送" id="close"
																		onclick="selectTag('f',$('ff'));">
																	<input type="button" value="重置" id="close"
																		onclick="window.close();">
																	<input type="button" value="打印" id="close"
																		onclick="window.close();">
																</td>
															</tr>
														</table>
													</div>
												</div>

												<div id='f' class='tagContentd' valign="top">
													<div style="height:30px;width:680px;">
														<span style="width:150px">登记人：<input type="text" id="JGID" readOnly value="测试用户" style="width:80px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
														<span style="width:150px"><input type="text" id="TSDJSJ_Y" value="2010" readOnly style="width:40px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>年
														<input type="text" id="TSDJSJ_M" value="04" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>月
														<input type="text" id="TSDJSJ_D" value="27" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>日</span>
														<span style="width:240px">处理单号：<input type="text" value="44000020100427101103123" id="CPID" readOnly style="width:180px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
													</div>
													<div style="height:100%;width:680px;overflow:hidden;offsetTop:0px;border: 1px solid #99C4F2">
														<table class="table2" width="100%" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tbody class='scrollContent'>
																<tr>
																	<td width="20%" align="left" class="tdtitle_a">
																		投诉人：
																	</td>
																	<td width="20%" class="tdvalue_a">
																		<input type="text" id="JGID"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																	<td width="20%" align="left" class="tdtitle_a">
																		联系电话：
																	</td>
																	<td width="40%" class="tdvalue_a">
																		<input type="text" id="JGMC"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="checkbox" disabled value="交通违法" name="reason"
																			id="reason_1">
																		交通违法
																		<input type="checkbox" disabled value="事故处理" name="reason"
																			id="reason_2">
																		事故处理
																		<input type="checkbox" disabled value="车管办证" name="reason"
																			id="reason_3">
																		车管办证
																		<input type="checkbox" disabled value="其他" name="reason"
																			id="reason_4">
																		其他
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉内容：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="YWMS"  class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td class="tdtitle_a" rowspan=4>
																		省厅交管局意见：
																	</td>
																	<td align="left" class="tdtitle_a">
																		交通指挥科意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		交通指挥处意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		业务处主管部门意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="BZRS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		局领导意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="ZBZGS"  class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		支队意见：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="BZRS" class="textwidth"/>
																	</td>
																</tr>
															<tr id="HFSTJGJ">
																<td align="right" class="tdtitle_a" colspan="4">
																	<input type="button" value="回复省厅交管局" id="close"
																		onclick="HFSTJGJ();">
																	<input type="button" value="转大队核查" id="close"
																		onclick="selectTag('g',$('gg'));">
																	<input type="button" value="打印" id="close"
																		onclick="window.close();">
																</td>
															</tr>
															<script language="javascript">
																function HFSTJGJ(){
																	$('HFSTJGJ').hide();
																	$('HFSTJGJ1').show();
																	$('HFSTJGJ2').show();
																	$('HFSTJGJ3').show();
																	$('HFSTJGJ4').show();
																}
															</script>
															<tr id="HFSTJGJ1" style="display: none">
																<td align="left" class="tdtitle_a">
																	办理结果说明：
																</td>
																<td colspan="3" class="tdvalue_a">
																	<input type="text" name="BZRS" class="textwidth" />
																</td>
															</tr>
															<tr id="HFSTJGJ2" style="display: none">
																<td colspan="4" class="tdvalue_a">
																	<input type="checkbox" value="基本属实" name="reason"
																		id="reason_1">
																	基本属实
																	<input type="checkbox" value="已答复群众" name="reason"
																		id="reason_2">
																	已答复群众
																	<input type="checkbox" value="已解决" name="reason"
																		id="reason_3">
																	已解决
																</td>
															</tr>
															<tr id="HFSTJGJ3" style="display: none">
																<td colspan="4" class="tdvalue_a">
																	经办单位：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																	经办联系人：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																	电话：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																	审批人：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																	日期：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																</td>
															</tr>
															<tr id="HFSTJGJ4" style="display: none">
																<td align="right" class="tdtitle_a" colspan="4">
																	<input type="button" value="确认保存" id="close"
																		onclick="selectTag('k',$('kk'));">
																	<input type="button" value="重置" id="close"
																		onclick="window.close();">
																	<input type="button" value="取消" id="close"
																		onclick="window.close();">
																</td>
															</tr>
														</table>
													</div>
												</div>

												<div id='g' class='tagContentd' valign="top">
													<div style="height:30px;width:680px;">
														<span style="width:150px">登记人：<input type="text" id="JGID" readOnly value="测试用户" style="width:80px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
														<span style="width:150px"><input type="text" id="TSDJSJ_Y" value="2010" readOnly style="width:40px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>年
														<input type="text" id="TSDJSJ_M" value="04" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>月
														<input type="text" id="TSDJSJ_D" value="27" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>日</span>
														<span style="width:240px">处理单号：<input type="text" value="44000020100427101103123" id="CPID" readOnly style="width:180px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
													</div>
													<div style="height:100%;width:680px;overflow:hidden;offsetTop:0px;border: 1px solid #99C4F2">
														<table class="table2" width="100%" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tbody class='scrollContent'>
																<tr>
																	<td width="20%" align="left" class="tdtitle_a">
																		投诉人：
																	</td>
																	<td width="20%" class="tdvalue_a">
																		<input type="text" id="JGID"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																	<td width="20%" align="left" class="tdtitle_a">
																		联系电话：
																	</td>
																	<td width="40%" class="tdvalue_a">
																		<input type="text" id="JGMC"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="checkbox" disabled value="交通违法" name="reason"
																			id="reason_1">
																		交通违法
																		<input type="checkbox" disabled value="事故处理" name="reason"
																			id="reason_2">
																		事故处理
																		<input type="checkbox" disabled value="车管办证" name="reason"
																			id="reason_3">
																		车管办证
																		<input type="checkbox" disabled value="其他" name="reason"
																			id="reason_4">
																		其他
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉内容：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="YWMS"  class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td class="tdtitle_a" rowspan=4>
																		省厅交管局意见：
																	</td>
																	<td align="left" class="tdtitle_a">
																		交通指挥科意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		交通指挥处意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		业务处主管部门意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="BZRS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		局领导意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="ZBZGS"  class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		支队意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="ZBZGS"  class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
															<tr id="DDQS"><!-- 大队签收 -->
																<td align="right" class="tdtitle_a" colspan="4">
																	<input type="button" value="签收" id="close"
																		onclick="DDQS();">
																	<input type="button" value="打印" id="close"
																		onclick="window.close();">
																</td>
															</tr>
															<script language="javascript">
																function DDQS(){
																	$('DDQS').hide();
																	$('DDQS1').show();
																	$('DDQS2').show();
																	$('DDQS3').show();
																	$('DDQS4').show();
																}
															</script>
															<tr id="DDQS1" style="display: none">
																<td align="left" class="tdtitle_a">
																	办理结果说明：
																</td>
																<td colspan="3" class="tdvalue_a">
																	<input type="text" name="BZRS" class="textwidth" />
																</td>
															</tr>
															<tr id="DDQS2" style="display: none">
																<td colspan="4" class="tdvalue_a">
																	<input type="checkbox" value="基本属实" name="reason"
																		id="reason_1">
																	基本属实
																	<input type="checkbox" value="已答复群众" name="reason"
																		id="reason_2">
																	已答复群众
																	<input type="checkbox" value="已解决" name="reason"
																		id="reason_3">
																	已解决
																</td>
															</tr>
															<tr id="DDQS3" style="display: none">
																<td colspan="4" class="tdvalue_a">
																	经办单位：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																	经办联系人：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																	电话：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																	审批人：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																	日期：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																</td>
															</tr>
															<tr id="DDQS4" style="display: none">
																<td align="right" class="tdtitle_a" colspan="2">
																	<input type="button" value="回复支队" id="close"
																		onclick="selectTag('h',$('hh'));">
																</td>
																<td align="right" class="tdtitle_a" colspan="2">
																	<input type="button" value="打印" id="close"
																		onclick="window.close();">
																</td>
															</tr>
														</table>
													</div>
												</div>

												<div id='h' class='tagContentd' valign="top">
													<div style="height:30px;width:680px;">
														<span style="width:150px">登记人：<input type="text" id="JGID" readOnly value="测试用户" style="width:80px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
														<span style="width:150px"><input type="text" id="TSDJSJ_Y" value="2010" readOnly style="width:40px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>年
														<input type="text" id="TSDJSJ_M" value="04" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>月
														<input type="text" id="TSDJSJ_D" value="27" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>日</span>
														<span style="width:240px">处理单号：<input type="text" value="44000020100427101103123" id="CPID" readOnly style="width:180px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
													</div>
													<div style="height:100%;width:680px;overflow:hidden;offsetTop:0px;border: 1px solid #99C4F2">
														<table class="table2" width="100%" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tbody class='scrollContent'>
																<tr>
																	<td width="20%" align="left" class="tdtitle_a">
																		投诉人：
																	</td>
																	<td width="20%" class="tdvalue_a">
																		<input type="text" id="JGID"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																	<td width="20%" align="left" class="tdtitle_a">
																		联系电话：
																	</td>
																	<td width="40%" class="tdvalue_a">
																		<input type="text" id="JGMC"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="checkbox" disabled value="交通违法" name="reason"
																			id="reason_1">
																		交通违法
																		<input type="checkbox" disabled value="事故处理" name="reason"
																			id="reason_2">
																		事故处理
																		<input type="checkbox" disabled value="车管办证" name="reason"
																			id="reason_3">
																		车管办证
																		<input type="checkbox" disabled value="其他" name="reason"
																			id="reason_4">
																		其他
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉内容：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="YWMS"  class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a" rowspan=4>
																		省厅交管局意见：
																	</td>
																	<td align="left" class="tdtitle_a">
																		交通指挥科意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		交通指挥处意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		业务处主管部门意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="BZRS" class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		局领导意见：
																	</td>
																	<td class="tdvalue_a" colspan="2">
																		<input type="text" name="ZBZGS"  class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		支队意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="ZBZGS"  class="textwidth" readOnly style="background-color:#dddddd;" style="background-color:#dddddd;"/>
																	</td>
																</tr>
																<tr>
																<td align="left" class="tdtitle_a">
																	办理结果说明：
																</td>
																<td colspan="3" class="tdvalue_a">
																	<input type="text" id="test1" name="BZRS" class="textwidth" readOnly style="background-color:#dddddd;" />
																</td>
															</tr>
															<tr>
																<td colspan="4" class="tdvalue_a">
																	<input type="checkbox" disabled value="基本属实" name="reason"
																		id="test_1">
																	基本属实
																	<input type="checkbox" disabled value="已答复群众" name="reason"
																		id="test_2">
																	已答复群众
																	<input type="checkbox" disabled value="已解决" name="reason"
																		id="test_3">
																	已解决
																</td>
															</tr>
															<tr>
																<td colspan="4" class="tdvalue_a">
																	经办单位：
																	<input type="text" name="test2" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																	经办联系人：
																	<input type="text" name="test3" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																	电话：
																	<input type="text" name="test4" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																	审批人：
																	<input type="text" name="test5" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																	日期：
																	<input type="text" name="test6" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																</td>
															</tr>
															<tr id="QRBLJGHFZD">
																<td align="right" class="tdtitle_a" colspan="4">
																	<input type="button" value="确认办理结果回复总队" id="close"
																		onclick="selectTag('k',$('kk'));">
																	<input type="button" value="修正补充办理结果" id="close"
																		onclick="QRBLJGHFZD();">
																	<input type="button" value="打印" id="close"
																		onclick="window.close();">
																</td>
															</tr>
															<script language="javascript">
																function QRBLJGHFZD(){
																	$('QRBLJGHFZD').hide();
																	$('QRBLJGHFZD1').show();
																	$('QRBLJGHFZD2').show();
																	test1.readOnly = false;
																	test1.style.backgroundColor = '';
																	test2.readOnly = false;
																	test2.style.backgroundColor = '';
																	test3.readOnly = false;
																	test3.style.backgroundColor = '';
																	test4.readOnly = false;
																	test4.style.backgroundColor = '';
																	test5.readOnly = false;
																	test5.style.backgroundColor = '';
																	test6.readOnly = false;
																	test6.style.backgroundColor = '';
																	test_1.disabled = false;
																	test_2.disabled = false;
																	test_3.disabled = false;
																}
															</script>
															<tr id="QRBLJGHFZD1">
																<td colspan="4" class="tdvalue_a">
																	修改人：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																	修改意见：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" />
																</td>
															</tr>
															<tr id="QRBLJGHFZD2">
																<td align="right" class="tdtitle_a" colspan="4">
																	<input type="button" value="确认保存" id="close"
																		onclick="selectTag('k',$('kk'));">
																	<input type="button" value="重置" id="close"
																		onclick="window.close();">
																	<input type="button" value="打印" id="close"
																		onclick="window.close();">
																</td>
															</tr>
														</table>
													</div>
												</div>
												<div id='k' class='tagContentd' valign="top">
													<div style="height:30px;width:680px;">
														<span style="width:150px">登记人：<input type="text" id="JGID" readOnly value="测试用户" style="width:80px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
														<span style="width:150px"><input type="text" id="TSDJSJ_Y" value="2010" readOnly style="width:40px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>年
														<input type="text" id="TSDJSJ_M" value="04" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>月
														<input type="text" id="TSDJSJ_D" value="27" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>日</span>
														<span style="width:240px">处理单号：<input type="text" value="44000020100427101103123" id="CPID" readOnly style="width:180px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/></span>
													</div>
													<div style="height:100%;width:680px;overflow:hidden;offsetTop:0px;border: 1px solid #99C4F2">
														<table class="table2" width="100%" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tbody class='scrollContent'>
																<tr>
																	<td width="20%" align="left" class="tdtitle_a">
																		投诉人：
																	</td>
																	<td width="20%" class="tdvalue_a">
																		<input type="text" id="JGID"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																	<td width="20%" align="left" class="tdtitle_a">
																		联系电话：
																	</td>
																	<td width="40%" class="tdvalue_a">
																		<input type="text" id="JGMC"
																			 class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="checkbox" disabled value="交通违法" name="reason"
																			id="reason_1">
																		交通违法
																		<input type="checkbox" disabled value="事故处理" name="reason"
																			id="reason_2">
																		事故处理
																		<input type="checkbox" disabled value="车管办证" name="reason"
																			id="reason_3">
																		车管办证
																		<input type="checkbox" disabled value="其他" name="reason"
																			id="reason_4">
																		其他
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		投诉内容：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="YWMS"  class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	
																	<td align="left" class="tdtitle_a">
																		交通指挥科意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		交通指挥处意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="YWMS" class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		业务处主管部门意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="BZRS" class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		局领导意见：
																	</td>
																	<td class="tdvalue_a" colspan="3">
																		<input type="text" name="ZBZGS"  class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																	<td align="left" class="tdtitle_a">
																		支队意见：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="BZRS" class="textwidth" readOnly style="background-color:#dddddd;" />
																	</td>
																</tr>
																<tr>
																<td align="left" class="tdtitle_a">
																	办理结果说明：
																</td>
																<td colspan="3" class="tdvalue_a">
																	<input type="text" name="BZRS" class="textwidth" readOnly style="background-color:#dddddd;" />
																</td>
															</tr>
															<tr>
																<td colspan="4" class="tdvalue_a">
																	<input type="checkbox" disabled value="基本属实" name="reason"
																		id="reason_1">
																	基本属实
																	<input type="checkbox" disabled value="已答复群众" name="reason"
																		id="reason_2">
																	已答复群众
																	<input type="checkbox" disabled value="已解决" name="reason"
																		id="reason_3">
																	已解决
																</td>
															</tr>
															<tr>
																<td colspan="4" class="tdvalue_a">
																	经办单位：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																	经办联系人：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																	电话：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																	审批人：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																	日期：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																</td>
															</tr>
															<tr>
																<td colspan="4" class="tdvalue_a">
																	修改人：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																	修改意见：
																	<input type="text" name="BZRS" class="textwidth"
																		style="width:70px;border:0px" readOnly style="background-color:#dddddd;" />
																</td>
															</tr>
															<tr>
																<td align="right" class="tdtitle_a" colspan="4">
																	<input type="button" value="签收" id="close"
																		onclick="window.close();">
																	<input type="button" value="打印" id="close"
																		onclick="window.close();">
																</td>
															</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</td>
							<td width="3" background="../../images/back/back_midright.gif"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="6">
								<img src="../../images/back/back_bottleft.gif" width="6"
									height="7" />
							</td>
							<td background="../../images/back/back_bottmid.gif">
								<img src="" alt="" name="" width="32" height="7" align="left" />
							</td>
							<td width="6" valign="top">
								<img src="../../images/back/back_bottright.gif" width="6"
									height="7" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
