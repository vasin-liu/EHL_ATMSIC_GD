<%@page import="com.ehl.sm.base.Constant"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.ehl.dispatch.common.*" %>
<%@ include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("880604");	
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//机构名称
    String ccbm=strObj[2];//机构层次编码
    String appid = Constant.nvl(session.getAttribute(Constant.APPID_VAR));
    Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String depttype = ""; 	  //部门类型
	String deptname = ""; 	  //部门名称
	String personname = "";	  //姓名
	String uname = ""; 		  //帐号
	String pid = ""; 		  //人员ID
	String phone = ""; 		  //办公电话
	String mobilephone = "";  //手机
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		depttype = (String)prop.get("DEPTTYPE");
		deptname = (String)prop.get("BRANCHNAME");
		personname = (String)prop.get("NAME");
		uname = (String)prop.get("USERNAME");
		pid=(String)prop.get("PERSONID");
		phone = (String)prop.get("PHONE");
		mobilephone = (String)prop.get("MOBILEPHONE");
	}
	
	//String jgid="441905000000";//12位机构编码
    String jgbh;//总队(2位),支队(4位),大队(6位)
    if(depttype.equals("9") || depttype.equals("0") || "0000".equals(jgid.substring(2,6))){
    	//总队
    	jgbh = jgid.substring(0,2);
    }else if("00".equals(jgid.substring(4,6))){
    	//支队
    	jgbh = jgid.substring(0,4);
    }else{
    	//大队
    	jgbh = jgid.substring(0,6);
    }
    System.out.println("部门类型: "+depttype);
    System.out.println("机构查询条件: "+jgbh);
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
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/newsFiles/newsFilesQuery.js"></script>
		<script type="text/javascript" src="../../../dispatch/js/ccommon/FileUpDownload.js"></script>
		<script type="text/javascript" src="../../../dispatch/js/ccommon/calendar/DateAndSchar.js"></script>
		
		<style type="text/css">
			.td_font {
				font-size: 13px;
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
	<body style="background: none; padding-top: 0px;" onLoad="" >
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
			<tr style="display: none">
				<td>
					<input id="jgbh" type="text" value=<%=jgbh%> readonly></input>
					<input id="jgid" type="text" value=<%=jgid%> readonly></input>
					<input id="appid" type="text" value=<%=appid%> readonly></input>
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
										<td class="sleektd" colspan="2">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr>
												<td style="text-align:right;">
														<label>
															报送时间：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" name="sj1" style="width:80" id="sj1" maxLength="50" readonly onclick="SelectDate(this,0);" />
														-
														<input type="text" name="sj2" style="width:80" id="sj2" maxLength="50" readonly onclick="SelectDate(this,0);" />
														<script language="javascript">
															var date = new Date();
															var end = date.format_(3);
															date.setDate(1);
															var start = date.format_(3);
															$("sj1").value = start;
															$("sj2").value = end;
														</script>
													</td>
														<td>
														<label>
															报送单位：
														</label>
													</td>
													<td id="zhiduiTdId" align="left">
													<script type="text/javascript">
														// 登录用户单位
														var jgid = $('jgid').value;
														// 判断用机构编号
														var jgbh = $('jgbh').value;
														if (jgbh.length == 4 || jgbh.length == 6) {
															fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)=\'00\' and jgid not in (\'440000000000\',\'446000000000\',\'446100000000\',\'446200000000\',\'446300000000\')  order by jgid","全省","resetListValue('"+ jgid +"');doSearch()");
														} else {
															fillListBox("zhiduiTdId","zhiduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)=\'00\' and jgid not in (\'440000000000\',\'446000000000\',\'446100000000\',\'446200000000\',\'446300000000\')  order by jgid","全省","doSearch();");
														}
													</script>
													</td>
													<td>
														<label>
															采用状态：
														</label>
													</td>
													<td id="zhiduiTdId" align="left">
														<select style="width:90;border: 1px #7B9EBD solid" id="state">
															<option value="" selected>全部</option>
															<option value="2">省厅采用</option>
														</select>
													</td>
													<td align="right">
														<div class="lsearch">
															<a href="#"
																onclick="doSearch()"
																class="currentLocation"> <span class="lbl">查询</span>
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
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td id="tdData" colspan="2">
											<div style='text-align:center;width:100%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span></div>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="table">
												<tr class="titleTopBack">
													<td width='4%' class='td_r_b td_font'>
														序号
													</td>
												    <td width='13%' class='td_r_b td_font'>
														报送时间
													</td>
													<td width='13%' class='td_r_b td_font'>
														报送单位
													</td>
													<td width='16%' class='td_r_b td_font'>
														填报人
													</td>
													<td width='16%' class='td_r_b td_font'>
														文档文件
													</td>
													<td width='10%' class='td_r_b td_font'>
														多媒体文件
													</td>
													<td width='10%' class='td_r_b td_font'>
														材料状态
													</td>
													<td width='10%' class='td_r_b td_font'>
														处理
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