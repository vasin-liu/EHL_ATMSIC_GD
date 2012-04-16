<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@page import="com.ehl.sm.dao.DepartmentDao"%>
<%@include file="../../Message.oni"%>
<%
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
	String jgid=strObj[0];//单位编码
	String jgmc=strObj[1];//机构名称
	String ccbm=strObj[2];//机构层次编码
	//String jgid="441905000000";//12位机构编码
	//当前用户信息
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String personname = ""; //姓名
	String uname = ""; //帐号
	String pid = ""; //人员ID
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	String jgbh = "";//总队(2位),支队(4位),大队(6位)
	if (prop != null) {
		uname = (String) prop.get("USERNAME");
		personname = (String) prop.get("NAME");
		deptcode = (String) prop.get("BRANCHID");
		deptname = (String) prop.get("BRANCHNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
		if ("0000".equals(deptcode.substring(2, 6))) {
			jgbh = deptcode.substring(0, 2);
		} else if ("00".equals(deptcode.substring(4, 6))) {
			jgbh = deptcode.substring(0, 4);
		} else {
			jgbh = deptcode.substring(0, 6);
		}
		pname = StringHelper.obj2str(DispatchUtil.getDutyPersonNameByDepId(deptcode),"");
	}

	String ptype = StringHelper.obj2str(request.getParameter("ptype"),"0");
	String id = StringHelper.obj2str(request.getParameter("id"),"");
	
	String jglx = Constant.nvl(session.getAttribute(Constant.JGLX_VAR));
	String pjgid = jgid,pjgmc = jgmc,pjgcc = ccbm;
	if(!jglx.equals("0")){
		pjgid = Constant.getRootParent(jgid);
		Object[] dept = DepartmentDao.getByJgid(pjgid);
		pjgmc = (String)dept[1];
		pjgcc = (String)dept[2];
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>通知信息维护</title>
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/ccommon/calendar/DateAndSchar.js"></script>
		<script type='text/javascript' src='${contextPath}base/js/new/base.js'></script>
		<script type="text/javascript" src="../../js/ceditpolice/notice.js"></script>
		<script type="text/javascript" src="../../js/ccommon/commonUtil.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Flow.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Department.js"></script>
		<link href="../../css/common/group.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../js/ccommon/group.js"></script>
		<script type="text/javascript" src="../../js/ccommon/privilege.js"></script>
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
	<body style="background: none; padding-top: 0px;" onLoad="Notice.search.init()"
			Onkeydown="if (window.event.keyCode==13) onButtonClick('search','<%=deptname%>')"  >
		<!-- 应用场景编号 -->
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
			<tr style="display: none">
				<td>
					<input id="appid" type="hidden" value="<%=session.getAttribute("appid") %>" />
					<input type="hidden" id="jgid" value="<%=deptcode%>" />
					<input type="hidden" id="dname" value="<%=deptname%>" />
					<input type="hidden" id="jgbh" value="<%=jgbh%>" />
					<input type="hidden" id="name" value="<%=pname%>" />
					<input type="hidden" id="ptype" value="<%=ptype%>" />
					<input type="hidden" id="id" value="<%=id%>" />
					<input id="operate" type="hidden" value="${operate}" />
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
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="5%">
														<!--
														<div background-color="#106ead" width="100%"><table  border="1" borderColor='#a5d1ec' style="font-size: 12px;font-weight: bold;"> <tr>
															<td>&nbsp;&nbsp;&nbsp;选择待查阅事件类别&nbsp;&nbsp;&nbsp;</td>
															<td >&nbsp;<input type="radio" name="radioBtn" id="radioBtn1" onclick="getSearchPage()"> 重特大交通事故&nbsp;</td>
															<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn2" onclick="getSearchPage()">交通拥堵&nbsp;</td>
															<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn3" onclick="getSearchPage()">协查通报&nbsp;</td>
															<td>&nbsp;<input type="radio" name="radioBtn" id="radioBtn4" onclick="getSearchPage()">施工占道信息&nbsp;</td>
															<td bgcolor="#F0FFFF" >&nbsp;<input type="radio" name="radioBtn" id="radioBtn5" onclick="getSearchPage()"><%=jgbh.length()==2?"其他重大情况/值班日志":"其他重大情况" %>&nbsp;</td>
															</tr>
															</table>
														</div>
														-->
														<table id="group"></table>
														
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
									<!-- 
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
									 -->
									<!-- 查询条件部分 -->
									<tr>
										<td class="sleektd" colspan="2">
										<%-- <div id="defaultCondition">
											<table table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr height="10px">
													<td colspan="8" align="left" style="text-algin:center;">
														<label style="font-size:12px;font-weight:bold;padding:12px;">
															查询条件：按时间查询
														</label>
													</td>
												</tr>
												<tr height="10px">
													<td></td>
												</tr>
												<tr height='30px;'>
													<td class="currentLocation" style="text-align: right;" width="75px">
														<label>
															报送时间：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<input type="text" name="dateS" style="width:105;border: 1px #7B9EBD solid" id="dateS" maxLength="50" readonly onclick="SelectDate(this,0);" />
														-
														<input type="text" name="dateE" style="width:105;border: 1px #7B9EBD solid" id="dateE" maxLength="50" readonly onclick="SelectDate(this,0);" />
													</td>
													<td colspan="4"></td>
													<td style="text-align: right" >
														<div class="lsearch">
															<a href="#"
																onclick="currPage=1;doSearch('<%=jgbh %>')"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
													</td>
													<td style="text-align: right" width="100px" >
														<div class="lsearch">
															<a href="#"
																onclick="showMoreCondition();"
																class="currentLocation"><span class="lbl">更多条件</span>
															</a>
														</div>
													</td>
												</tr>
												<tr height="10px">
													<td ></td>
												</tr>
											</table>
											</div> --%>
										<div id="moreCondition" style="width: 100%;">
											<table width="100%" border="0px" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr height="10px" id="defalutTitleTr">
													<td colspan="8" align="left" style="text-algin:center;">
														<label style="font-size:12px;font-weight:bold;padding:12px;">
															查询条件：按时间查询
														</label>
													</td>
												</tr>
												<tr height='30px;'>
													<td style="text-align:right;" width="9%">
														<label>
															报送时间：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" name="dateS" style="width:80" id="stime" maxLength="50" readonly onclick="SelectDate(this,0);" />
														-
														<input type="text" name="dateE" style="width:80" id="etime" maxLength="50" readonly onclick="SelectDate(this,0);" />
													</td>
													<td align="right">
														<label id="clzt_lable" style="display:none;">
															处理状态：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<select id="state" style="width:120;display:none;">
															<option value="">全部</option>
															<option value="1">未签收</option>
															<option value="2">已签收</option>
															<option value="3">已处理</option>
														</select>
													</td>
													<td>
														<label id="xxlx_lable" style="display:none;">
															信息类型：
														</label>
													</td>
													<td id="columnTd" align="left">
														<select id="stype" style="width:120;display:none;">
															<option value="0" selected="selected">全部</option>
															<option value="1">已发送</option>
															<option value="2">已接收</option>
														</select>
													</td>
                                                    <td id="simpleButtomTd" style="width: 22%;">
                                                        <div class="lsearch" style="float: right;display: inline;margin-left: 10px;">
                                                            <a href="#"
                                                               onclick="showMoreCondition();"
                                                               class="currentLocation"><span class="lbl" id="advancedLable">高级查询</span>
                                                            </a>
                                                        </div>
                                                        <div class="lsearch" style="float: right;display: inline;">
                                                            <a href="#"
                                                               onclick="Notice.search.submit()"
                                                               class="currentLocation"> <span class="lbl">查询</span>
                                                            </a>
                                                        </div>
                                                    </td>
													<!-- <td align="right">
														<div class="lsearch">
															<a href="#"
																onclick="Notice.search.showExcelInfo()"
																class="currentLocation"> <span class="lbl">导出Excel</span>
															</a>
														</div>
													</td> -->
												</tr>
												<tr height='30px;' id="moreConditionTr" style="display:none;">
												<td style="text-align:right;">
														<label>
															标题：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="1">
														<input type="text" id="title" style="width:180px;"/>
													</td>
													<td class="currentLocation" style="text-align:right" width="80">
														<label>
															填报单位：
														</label>
													</td>
													<td id="columnTd" align="left" width="160" >
														<input type="text" id="AlarmUnit" style="width:160" ondblclick="showDepartTree('<%=pjgcc %>','<%=pjgid %>','<%=pjgmc %>','AlarmUnit','100','350')" readonly  />
													</td>
													<td colspan="1">
														<img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor:hand;" onclick="showDepartTree('<%=pjgcc %>','<%=pjgid %>','<%=pjgmc %>','AlarmUnit','100','350')">
													</td>
													<!-- <td align="right">
														<div class="lsearch">
															<a href="#"
																onclick="Notice.search.submit()"
																class="currentLocation"> <span class="lbl">查询</span>
															</a>
														</div>
													</td> -->
												</tr>
												<tr id="moreButtomTr" height='30px;' style="display: none;">
													<td colspan="4"></td>
													<td align="right" colspan="4">
                                                        <div class="lsearch" style="float: right;display: inline;margin-left: 10px;">
                                                            <a href="#"
                                                               onclick="showMoreCondition();"
                                                               class="currentLocation"><span class="lbl" id="simpleLable">简单查询</span>
                                                            </a>
                                                        </div>
														<div class="lsearch" style="float: right;display: inline;">
															<a href="#"
																onclick="Notice.search.submit()"
																class="currentLocation"> <span class="lbl">查询</span>
															</a>
														</div>
													</td>
												</tr>
												<tr height="10px">
													<td></td>
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
	</body>
</html>