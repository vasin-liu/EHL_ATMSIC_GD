<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@include file="../../Message.oni"%>
<jsp:directive.page import="com.appframe.data.sql.DBHandler"/>
<jsp:directive.page import="com.appframe.utils.StringHelper"/>
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
    System.out.println("机构层次编码: "+ccbm);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>值班重大信息维护</title>
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
		<script type="text/javascript"
			src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript"
			src="../../../base/js/list/FillListBox.js"></script>
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
	<body style="background: none; padding-top: 0px;" onload="waitReceipt('<%=jgbh %>')" >
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
														<span class="currentLocationBold">您当前的位置</span>：待处理事故
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
										<td align="left" class="height" colspan=2></td>
									</tr>
									
									<tr>
										<td align="left" style="display: none">
											<table width="141" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td class="sleek textB">
														查询条件
													</td>
												</tr>
											</table>
										</td>
										<%
										System.out.println(jgbh);
											if (jgbh.length() == 4) {
											String rSql ="select count(*) ";
											rSql = rSql + " from T_ATTEMPER_ALARM_zd where EVENTSTATE in('004033','004042') ";
											rSql = rSql + " and EVENTTYPE = '001024' ";
											rSql = rSql + " and subStr(REPORTUNIT,0,4) = '" + jgbh + "'";
											Object rCount = DBHandler.getSingleResult(rSql); 
											int cnt = 0;
											cnt = StringHelper.obj2int(rCount,0);
										%>
										<td align="right"><a href="#" onclick="waitReceipt('<%=jgbh %>')"><font color="red" id="wr">待处理事故(<%=String.valueOf(cnt)%>)</font></a></td>
										<% }%>
										<%
											if (jgbh.length() == 2) {
											String rSql ="select count(*) ";
											rSql = rSql + " from T_ATTEMPER_ALARM_zd where EVENTSTATE in('004034','004043') ";
											rSql = rSql + " and EVENTTYPE = '001024' ";
											Object rCount = DBHandler.getSingleResult(rSql); 
											int cnt = 0;
											cnt = StringHelper.obj2int(rCount,0);
										%>
										<td align="right"><a href="#" onclick="waitReceipt('<%=jgbh %>')"><font color="red" id="wr">待处理事故(<%=String.valueOf(cnt)%>)</font></a></td>
										<% }%>
									</tr>
									<!-- 查询条件部分 -->
									<tr style="display: none">
										<td class="sleektd" colspan=2>
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr  height='35px;'>
													<td class="currentLocation" style="text-align: right">
														<label>
															&nbsp;&nbsp;&nbsp;填报单位：
														</label>
													</td>
													<td id="jgmcs" align="left">
														<input type="hidden" id="jgid" value="<%=jgid %>" />
														<input type="hidden" id="jgbh" value="<%=jgbh %>" />
														<input type="text" style="width:140" style="border: 1px #7B9EBD solid" id="jgmc" readonly ondblclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','jgmc','100','90')" />
														<input type="hidden" id="depUnitId"  />
													</td>
													<td>
														<img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor:hand;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','jgmc','100','90')">
														</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															&nbsp;&nbsp;填报人：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" style="width:140" style="border: 1px #7B9EBD solid" id="tbr" align="middle" />
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															&nbsp;&nbsp;&nbsp;填报时间：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" style="width:140" style="border: 1px #7B9EBD solid" id="tbsj" readonly onclick="SelectDate(this,0)" />&nbsp;&nbsp;&nbsp;
													</td>
													<td colspan="5" style="text-align: right">
															<div class="lsearch">
																<a href="#" onclick="onButtonClick('search','<%=jgbh %>')"
																	class="currentLocation"><span class="lbl">查询</span>
																</a>
															</div>
														</td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align: right">
														<label>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;事故标题：
														</label>
													</td>
													<td id="columnTd" align="left">
														<input type="text" style="width:140" id="dlmc" align="middle" style="border: 1px #7B9EBD solid" />
													</td>
													<td>
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															事故状态：
														</label>
													</td>
													<td id="columnTd" align="left">
														<select style="width:140" id="ldmc">
															<option value="" selected>全部</option>
															<option value="004032">大队警情保存</option>
															<option value="004033">大队警情上报支队未签收</option>
															<option value="004042">大队警情上报支队已签收</option>
															<option value="004034">支队警情上报总队未签收</option>
															<option value="004043">支队警情上报总队已签收</option>
															<option value="004036">总队签收完成</option>
															<option value="004037">总队警情下发支队</option>
															<option value="004035">支队签收完成</option>
														</select>
													</td>
													<td class="currentLocation" style="text-align: right">
														<label>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;事件类别：
														</label>
													</td>
													<td id="columnTd" align="left">
														<select style="width:140" id="sjlb">
															<option value="" selected>全部</option>
															<option value="99">一次死亡3人以上交通事故</option>
															<option value="ISBUS">营运大客车事故</option>
															<option value="ISSCHOOLBUS">校车事故</option>
															<option value="ISDANAGERCAR">危化品运输车交通事故</option>
															<option value="ISFOREIGNAFFAIRS">涉港澳台及涉外事故</option>
															<option value="ISPOLICE">涉警涉军交通事故</option>
															<option value="ISMASSESCASE">涉及群体性事件</option>
															<option value="ISCONGESTION">高速公路国省道交通中断</option>
															<option value="ISOTHERSITEM">其他</option>
														</select>
													</td>
													<td >
													    <div class="lsearch">
															<a href="#" onclick="showExcelInfo('search','<%=jgbh %>')"
																class="currentLocation"><span class="lbl">导出Excel</span>
															</a>
														</div>
													</td>
												</tr>
												<tr height="10px">
													<td colspan=2></td>
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
										<td id="tdData" colspan=2>
											<%--<table width="100%" border="0" cellpadding="0"
												cellspacing="0" onmouseover="changeto()"
												onmouseout="changeback()" class="table">
											--%>
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
