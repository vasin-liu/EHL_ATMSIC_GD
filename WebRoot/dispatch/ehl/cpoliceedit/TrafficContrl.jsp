<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//机构名称
    String ccbm=strObj[2];//机构层次编码
    //String jgid="441905000000";//12位机构编码
    String jgbh;//总队(2位),支队(4位),大队(6位)
    if("0000".equals(jgid.substring(2,6))){
    	jgbh = jgid.substring(0,2);
    }else if("00".equals(jgid.substring(4,6))){
    	jgbh = jgid.substring(0,4);
    }else{
    	jgbh = jgid.substring(0,6);
    }
    System.out.println("机构查询条件: "+jgbh);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>交通管制信息维护</title>
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
		<script type="text/javascript" src="../../js/ceditpolice/TrfficeContrl.js"></script>	
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
	<body style="background: none; padding-top: 0px;" onLoad="doOnLoad();">
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
			<tr style="display: none">
				<td>
					<input id="jgbh" type="text" value=<%=jgbh%> readonly></input>
					<input id="jgid" type="text" value=<%=jgid%> readonly></input>
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
														<span class="currentLocationBold">您当前的位置</span>：交通管制管理
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
										<td align="left" class="height" colspan="2"></td>
									</tr>
									<tr>
										<td align="left" colspan="2">
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
									<tr style="display: none">
										<td id="columnTd" align="left" colspan="2">
											<input type="text" name="sj1" style="width:105" id="sj1" maxLength="50" readonly onclick="SelectDateTime(this,0);" />
											-
											<input type="text" name="sj2" style="width:105" id="sj2" maxLength="50" readonly onclick="SelectDateTime(this,0);" />
										</td>
									</tr>
									<tr>
										<td class="sleektd" align="center" style="color:red;font-size:14px;width:700">
											<span id="title"></span>
											<script language="javascript">
												var d = new Date();
												d.setTime(d.getTime());
												var od = new Date();
												od.setTime(d.getTime()-1000*60*60*24);
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
												if(hour<10){
													hour="0"+hour;
												}
												if(min<10){
													min="0"+min;
												}
												var oyear = od.getYear(); 
												var omonth = od.getMonth()+1; 
												var odate = od.getDate();
												var ohour = od.getHours();
												var omin = od.getMinutes();
												if(omonth<10){
													omonth="0"+omonth;
												}
												if(odate<10){
													odate="0"+odate;
												}
												if(ohour<10){
													ohour="0"+ohour;
												}
												if(omin<10){
													omin="0"+omin;
												}
												//$("sj1").value=oyear+"-"+omonth+"-"+odate+" "+ohour+":"+omin;
												$("sj1").value=year+"-"+month+"-"+date+" 00:00";
												$("sj2").value=year+"-"+month+"-"+date+" "+hour+":"+min;
    											//$("title").innerHTML = '<span id="title">'+$("sj1").value+' 至 '+$("sj2").value+' 之内的管制信息</span>';
    											$("title").innerHTML = '<span id="title">默认显示当日新增信息</span>';
											</script>
										</td>
										<td class="sleektd" align="center">
											<%--<div style="width:30;float:right"></div>--%>
											<div class="lsearch">
												<a href="#" onclick="onButtonClick('new')"
													class="currentLocation"><span class="lbl">新增</span>
												</a>
											</div>
										</td>
									</tr>
									<!-- 查询条件部分end -->
									<!-- 占行高部分 -->
									<tr>
										<td class="height" colspan="2"></td>
									</tr>
									<!-- 占行高部分end -->
									<!-- 部数据列表分 -->
									<tr>
										<td id="tdData" colspan="2">
											<%--<table width="100%" border="0" cellpadding="0"
												cellspacing="0" onmouseover="changeto()"
												onmouseout="changeback()" class="table">
											--%>
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0" class="table">
												<tr class="titleTopBack">
												    <td width='15%' class='td_r_b td_font'>
														录入单位
													</td>
													<td width='15%' class='td_r_b td_font'>
														路段备注
													</td>
													<td width='15%' class='td_r_b td_font'>
														方向
													</td>
													<td width='20%' class='td_r_b td_font'>
														管制开始时间
													</td>
													<td width='20%' class='td_r_b td_font'>
														管制结束时间
													</td>
													<td width='5%' class='td_r_b td_font'>
														明细
													</td>
													<td width='5%' class='td_r_b td_font'>
														修改
													</td>
													<td width='5%' class='td_r_b td_font'>
														删除
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
