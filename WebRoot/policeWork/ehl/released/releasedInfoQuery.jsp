<%@ page import="com.ehl.sm.pcs.DepartmentManage" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../Message.oni"%>
<%
    String title = request.getParameter("nodeDesc");
    title = title == null ? "发布信息查询":title;
    String[] strObj= DepartmentManage.getDeptInfo(request, "1").split(",");//获取单位信息串
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
		<link href="${contextPath}base/css/new/util.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style2/Popup.css" rel="stylesheet" type="text/css" />
		<link href="../../../util/jquery/jquery-ui/themes/start/jquery-ui-1.8.17.custom.css" rel="stylesheet" media="screen" type="text/css" />
		<link href="../../../util/jquery/jqgrid/ui.jqgrid.css" rel="stylesheet" media="screen" type="text/css" />
        <link href="../../../util/jquery/jqgrid/ui.jqgrid.specialConfig.css" rel="stylesheet" type="text/css"/>
		<script type="text/javascript" src="../../../util/jquery/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../util/jquery/jqgrid/i18n/grid.locale-cn.js"></script>
		<script type="text/javascript" src="../../../util/jquery/jqgrid/grid.loader.js"></script>
		<script type="text/javascript" src="../../../util/jquery/jqgrid/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
        <script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
        <script type="text/javascript" src="../../../dispatch/js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/released/releasedInfoQuery.js"></script>
		<style type="text/css">
			table{
				font-size:9pt;
				font-weight:normal;
				margin-left:0;
			}
			.lsearch{
			     width:82px; 
				 height:22px; 
				 line-height:22px; 
				 background:url(../../../dispatch/images/dispatch/btn.png) no-repeat;
				 overflow:hidden;
			}
			/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
			.lsearch a{ 
			     display:block; 
				 height:22px; 
				 background:url(../../../dispatch/images/dispatch/btn.png) center;
				 text-decoration:none; 
				 line-height:22px;
				 overflow:hidden;
			}
			/*高度固定背景行高*/
			.lsearch a:hover{ 
			     height:22px; 
				 background:url(../../../dispatch/images/dispatch/btn.png) center center;
				 line-height:22px;
			}
			/*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
			.lsearch .lbl{ 
			     display:block;
				 width:79px; 
				 height:15px; 
				 padding-left:3px; 
				 padding-top:0;
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
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<!-- 头部 -->
            <tr style="display: none">
                <td>
                    <input id="jgbh" type="text" value=<%=jgbh%> readonly />
                    <input id="jgid" type="text" value=<%=jgid%> readonly />
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
														<span class="currentLocationBold">您当前的位置</span>：信息考评计分 -> <%=title %>
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
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr height="10px">
													<td></td>
												</tr>
												<tr>
													<td class="currentLocation" style="text-align:right" width="75px">
														<label>
															开始时间：
														</label>
													</td>
													<td id="startDateTd" align="left"  colspan="1" width="125px">
														<input type="text" name="startDate" id="startDate"  readonly onclick="SelectDay(this,0);" style="width: 125px" />
													</td>
                                                    <td class="currentLocation" style="text-align:right" width="75px">
                                                        <label>
                                                            结束时间：
                                                        </label>
                                                    </td>
                                                    <td id="endDateTd" align="left"  colspan="1" width="125px">
                                                        <input type="text" name="endDate" id="endDate"  readonly onclick="SelectDay(this,0);" style="width: 125px" />
                                                    </td>
                                                    <td class="currentLocation" style="text-align:right" width="75px">
                                                        <label>
                                                            处理状态：
                                                        </label>
                                                    </td>
                                                    <td width="70px">
                                                        <select style="width:70px;" id="flowState">
                                                            <option value="" selected>全部</option>
                                                            <option value="1">已计分</option>
                                                            <option value="2">未计分</option>
                                                        </select>
                                                    </td>
                                                    <td class="currentLocation" style="text-align:right" width="75px">
                                                        <label>
                                                            信息类型：
                                                        </label>
                                                    </td>
                                                    <td width="90px">
                                                        <select style="width:90px;" id="infoType">
                                                            <option value="" selected>全部</option>
                                                            <option value="001002">交通拥堵</option>
                                                            <option value="001023">施工占道</option>
                                                        </select>
                                                    </td>
                                                    <td width="50px"></td>
												</tr>
                                                <tr style="height: 10px;">
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td class="currentLocation" style="text-align:right" width="75px">
                                                        <label>
                                                            发布单位：
                                                        </label>
                                                    </td>
                                                    <td id="deptTd" align="left" width="200px" colspan="2">
                                                        <input type="text" id="jgmc" style="width:200px;" ondblclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','jgmc','125','80')" readonly="readonly"/>
                                                    </td>
                                                    <td align="left">
                                                        <input type="hidden" id="depUnitId"  />
                                                        <img src="../../images/popup/btnselect.gif" alt="选择机构" style="cursor: pointer;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','jgmc','125','80')">
                                                    </td>
                                                    <td align="right" colspan="5">
                                                        <div id="searchDivId" class="lsearch" >
															<span class="lbl" style="padding-right: 10px;" onclick="reloadGrid();" >
																查询
															</span>
                                                        </div>
                                                    </td>
                                                </tr>
												<tr style="height: 10px;">
													<td></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td width="100%">
											<table id="tb_tableId"></table>
											<div id="div_pageId"></div>
										</td>
									</tr>
									<!-- 占行高部分end -->
								</table>
							</td>
							<td class="wTableCenterRight"></td>
						</tr>
					</table>
				</td>
			</tr>
			<!-- 数据end -->
			<!-- 尾部 -->
			<!-- 
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
			 -->
			<!-- 尾部end -->
		</table>
	</body>
</html>
