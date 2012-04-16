<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@page import="java.util.Hashtable"%>
<%
	String title = FlowUtil.getFuncText("880904");	
	String[] strObj = DepartmentManage.getDeptInfo(request, "1").split(
			",");//获取单位信息串
	String jgid = strObj[0];//单位编码
	String jgmc = strObj[1];//机构名称
	String ccbm = strObj[2];//机构层次编码
	//String jgid="441905000000";//12位机构编码
	String jgbh;//总队(2位),支队(4位),大队(6位)
	if ("0000".equals(jgid.substring(2, 6))) {
		jgbh = jgid.substring(0, 2);
	} else if ("00".equals(jgid.substring(4, 6))) {
		jgbh = jgid.substring(0, 4);
	} else {
		jgbh = jgid.substring(0, 6);
	}
	System.out.println("机构查询条件: " + jgid);
	
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; //部门编码
	String depttype = ""; //部门类型
	String deptname = ""; //部门名称
	String personname = ""; //姓名
	String uname = ""; //帐号
	String pid = ""; //人员ID
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	if (prop != null) {
	deptcode = (String) prop.get("BRANCHID");
	depttype = (String) prop.get("DEPTTYPE");
	deptname = (String) prop.get("BRANCHNAME");
	personname = (String) prop.get("NAME");
	uname = (String) prop.get("USERNAME");
	pid = (String) prop.get("PERSONID");
	phone = (String) prop.get("PHONE");
	mobilephone = (String) prop.get("MOBILEPHONE");
	}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%=title %></title>
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
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript"
			src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../sm/js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/assess/ScoreRecordQuery.js"></script>	
		
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
	<body style="background: none; padding-top: 0px;"
		onLoad="DateUser.setDateSE('dateS','dateE',1);submit()">
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
											<table width="100%" border="0" cellspacing="5"
												cellpadding="0" style="">
												<tr>
													<td class="currentLocation" width="70px" align="right" style="text-align: right;">
														交警机构：
														<input id="jgid" type="hidden" value="<%=jgid%>" />
													</td>
													<td id="columnTd" align="left" width="160">
														<input type="text" id="jgmc" style="width: 160" value="<%=deptname%>" readonly />
													</td>
													<td align="left" style="width: 30">
														<img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor: hand;"
															onclick="showDepartTree('<%=ccbm%>','<%=jgid%>','<%=jgmc%>','jgmc','90','90')"
															onpropertychange="$('jgid').value= G_jgID">
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															计分开始时间：
														</label>
													</td>
													<td align="left" colspan="1">
														<input type="text" name="startDate"
															style="width: 100; border: 1px #7B9EBD solid" id="dateS"
															maxLength="50" readonly onclick="SelectDate(this,0);" />
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															计分结束时间：
														</label>
													</td>
													<td align="left" colspan="1">
														<input type="text" name="endDate"
															style="width: 100; border: 1px #7B9EBD solid" id="dateE"
															maxLength="50" readonly onclick="SelectDate(this,0);" />
													</td>
													<td align="center">
														<div class="lsearch">
															<a href="#" onclick="submit()"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
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
		<div id="showDivId" style="position: absolute;width: 400px;height: 400px;left: 10px;top: 10px;"></div>
	</body>
</html>