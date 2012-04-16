<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.ehl.dispatch.common.*" %>
<%@include file="../../Message.oni"%>
<%
    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//机构名称
    String ccbm=strObj[2];//机构层次编码
    
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String deptname = ""; 	  //部门名称
	String uname = ""; 		  //帐号
	String pid = ""; 		  //办公电话
	String phone = ""; 		  //办公电话
	String mobilephone = "";  //手机
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		pname = (String)prop.get("NAME");
		uname = (String)prop.get("USERNAME");
		pid = (String)prop.get("PERSONID");
		phone = (String)prop.get("PHONE");
		mobilephone = (String)prop.get("MOBILEPHONE");
	}
    String jgbh;//总队(2位),支队(4位),大队(6位)
    if("0000".equals(jgid.substring(2,6))){
    	jgbh = jgid.substring(0,2);
    }else if("00".equals(jgid.substring(4,6))){
    	jgbh = jgid.substring(0,4);
    }else{
    	jgbh = jgid.substring(0,6);
    }
    
    System.out.println("jgbh:"+jgbh + ",ccbm:" + ccbm);
    
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>值班重大信息填报</title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/materialInfo.js"></script>	
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
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
	<body style="background: none; padding-top: 0px;" onload="doSearch('<%=jgbh %>')">
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
														<div align="center">
															<img src="../../../base/image/cssimg/table/tb.gif"
																width="16" height="16" alt="img" />
														</div>
													</td>
													<td width="70%" class="currentLocation">
														<span class="currentLocationBold">您当前的位置</span>：值班重大信息填报
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
														新增记录
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<!-- 查询条件部分 -->
									<tr style="display:none;">
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align: right">
														<label>
															填报单位：
														</label>
													</td>
													<td id="jgmcs" align="left">
														<input type="hidden" id="jgid" value="<%=jgid %>" />
														<input type="hidden" id="jgbh" value="<%=jgbh %>" />
														<input type="hidden" id="deptname" value="<%=deptname %>" />
														<input type="hidden" id="page" />
														<input type="text" style="width:140" id="jgmc" readonly/>
														<img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor:hand;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','jgmc','100','95')">
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															填报人：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" style="width:140" id="tbr" align="middle" />
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															填报时间：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" style="width:140"
															id="tbsj" readonly onclick="SelectDate(this,0)" />
														<script type="text/javascript">
															var d = new Date();
															d.setTime(d.getTime());
															var year = d.getYear(); 
															var month = d.getMonth()+1; 
															var date = d.getDate();
															var hour = d.getHours();
															var min = d.getMinutes();
															if(month<10){
																month="0"+month;
															}
															if(date<10){
																date="0"+date;
															}
															
															$("tbsj").value=year+"-"+month+"-"+date;
														</script>
													</td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align: right">
														<label>
															道路名称：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" style="width:140" id="dlmc" align="middle" />
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															路段名称：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" style="width:140" id="ldmc" align="middle" />
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															事件类别：
														</label>
													</td>
													<td id="columnTd" align="left">
														<select style="width:140" id="sjlb">
															<option value="" selected>全部</option>
															<option value="ISBUS">营运大客事故</option>
															<option value="ISDANAGERCAR">涉及危化事故</option>
															<option value="ISFOREIGNAFFAIRS">涉外事故</option>
															<option value="ISPOLICE">涉及军警事故</option>
															<option value="ISMASSESCASE">群众群体事件</option>
															<option value="ISCONGESTION">道路通行中断</option>
														</select>
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
																		<a href="#" onclick="onButtonClick('search')"
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
									<tr align="right">
										<td class="sleektd" style="color:red;font-size:14px;">
											<% if(jgbh.length()==6){ %><div style="width:82px;margin-right:20px;" class="lsearch">
												<a href="#" onclick="onButtonClick('new','<%=jgbh %>')"
													class="currentLocation"><span class="lbl">新增</span>
												</a>
												<%} %>
											</div>
										</td>
									</tr>
									<tr>
										<td class="height"></td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td>
											<div style="text-align:center;width:100%;line-height:22px; float:left;"><span class="currentLocationBold">当日重大信息记录</span></div>
										</td>
									</tr>
									<tr>
										<td id="tdData">
											<%--<table width="100%" border="0" cellpadding="0"
												cellspacing="0" onmouseover="changeto()"
												onmouseout="changeback()" class="table">
											--%>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="table">
												<tr class="titleTopBack">
													<td width='15%' class='td_r_b td_font'>
														填报单位
													</td>
													<td width='11%' class='td_r_b td_font'>
														填报人
													</td>
													<td width='12%' class='td_r_b td_font'>
														填报时间
													</td>
													<td width='12%' class='td_r_b td_font'>
														事故时间
													</td>
													<td width='10%' class='td_r_b td_font'>
														道路名称
													</td>
													<td width='15%' class='td_r_b td_font'>
														状态
													</td>
													<td width='5%' class='td_r_b td_font'>
														明细
													</td>
													<td width='5%' class='td_r_b td_font'>
														处理
													</td>
													<td width='5%' class='td_r_b td_font'>
														新增续报
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
