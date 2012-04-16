<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%@ page import="com.ehl.dispatch.common.*" %>
<%@ include file="../../../base/Message.oni"%>
<%
	String title = FlowUtil.getFuncText("930301");	
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//机构名称
    String ccbm=strObj[2];//机构层次编码
    
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
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/ComplaintInfo.js"></script>	
		<script type="text/javascript" src="../../js/ceditpolice/ComplaintAwake.js"></script>
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
	<body style="background: none; padding-top: 0px;" onLoad="doOnLoad('<%=pid%>');">
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
			<tr style="display: none">
				<td>
					<input id="jgbh" type="text" value=<%=jgbh%> readonly></input>
					<input id="jgid" type="text" value=<%=jgid%> readonly></input>
					<input id="pid" type="text" value=<%=pid%> readonly></input>
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
										<td class="sleektd">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr>
													<td style="text-align:right;">
														<label>
															投诉人：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<input type="text" id="TSRXM" style="width:160;"/>
													</td>
													<td style="text-align:right;">
														<label>
															投诉人电话：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<input type="text" id="TSRDH"  style="width:158;"/>
													</td>
													<td style="text-align:right;">
														<label>
															投诉登记日期：
														</label>
													</td>
													<td id="columnTd" align="left" >
														<input type="text" id="TSDJSJ" style="width:160;" readOnly onClick="SelectDate(this,0);"/>
													</td>
													<%--<td colspan="2" align="right">
														<div style="width:11;float:right"></div>
														<div class="search">
															<a href="#" onclick="onButtonClick('new')"
																class="currentLocation"><span class="lbl">新增</span>
															</a>
														</div>
														<div class="search">
															<a href="#"
																onclick="onButtonClick('search','<%=jgbh%>')"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
													</td>--%>
												</tr>
												<tr height="10px">
													<td></td>
												</tr>
												<tr>
													<td style="text-align:right;">
														<label>
															投诉内容：
														</label>
													</td>
													<td id="columnTd" align="left" colspan="3">
														<input type="text" id="TSNR" style="width:430;"/>
													</td>
													<td style="text-align:right;">
														<label>
															流转状态：
														</label>
													</td>
													<td id="SearchLZZT_TD" align="left" >
														<script language="javascript">
									                       fillListBox("SearchLZZT_TD","SearchLZZT","160","select distinct t.NAME,t.NAME from t_oa_code t where 1=1 and t.ID like '00%'","请选择");
								                        </script>
													</td>
												</tr>
												<tr height="10px">
													<td></td>
												</tr>
												<tr>
													<td colspan="4"></td>
													<td align="right" colspan="2">
														<div style="width:85;float:left"></div>
														<div class="lsearch" style="float:left">
															<a href="#"
																onclick="onButtonClick('search','<%=jgbh%>')"
																class="currentLocation"><span class="lbl">查询</span>
															</a>
														</div>
														<div class="lsearch" style="float:left" id="newbutton">
															<a href="#" onclick="onButtonClick('new')"
																class="currentLocation"><span class="lbl">新增</span>
															</a>
														</div>
													</td>
													<script type="text/javascript">
														if($('jgbh').value.length!=2){
															$('newbutton').hide();
														}
													</script>
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
										<td id="tdData">
											<%--<table width="100%" border="0" cellpadding="0"
												cellspacing="0" onmouseover="changeto()"
												onmouseout="changeback()" class="table">
											--%>
											<div style='text-align:center;width:100%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span></div>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="table">
												<tr class="titleTopBack">
													<td width='5%' class='td_r_b td_font'>
														序号
													</td>
												    <td width='10%' class='td_r_b td_font'>
														投诉人
													</td>
													<td width='15%' class='td_r_b td_font'>
														投诉人电话
													</td>
													<td width='15%' class='td_r_b td_font'>
														投诉登记时间
													</td>
													<td width='25%' class='td_r_b td_font'>
														投诉内容
													</td>
													<td width='20%' class='td_r_b td_font'>
														流转状态
													</td>
													<td width='5%' class='td_r_b td_font'>
														明细
													</td>
													<td width='5%' class='td_r_b td_font'>
														处理
													</td>
													<%--<td width='5%' class='td_r_b td_font'>
														删除
													</td>--%>
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