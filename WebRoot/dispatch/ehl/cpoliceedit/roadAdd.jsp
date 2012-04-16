<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? "" : request.getParameter("insrtOrUpdt");
	String roadid = request.getParameter("roadid")== null ? "" : request.getParameter("roadid");
	String readStatic = (!"0".equals(insrtOrUpdt))
		&& (!"1".equals(insrtOrUpdt)) ? "readonly" : "";
	String readStatic1 = (!"0".equals(insrtOrUpdt))
	&& (!"1".equals(insrtOrUpdt)) ? "disabled" : "";
%>
<html>
	<head>
		<title>道路管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../../base/css/style1/tab.css" rel="stylesheet"  type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet"	type="text/css" />
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/style1/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/output/SaveAsExcel.js"></script>
		<SCRIPT type="text/javascript" src="../../js/ceditpolice/road.js"></SCRIPT>
	    <script type="text/javascript">
	        var username = '<%=pname%>';
	    </script>
	    <style type="text/css">
			<!--
			table{
				font-size:11px;
			}
			.tableInput{
				background:#ffffff;
				border-top: 1 solid #CCCCCC;
				border-right: 0 solid #000000;
				border-bottom: 1 solid #CCCCCC;
				border-left: 1 solid #CCCCCC;
				border-collapse:collapse;
				font-size:11px;
				text-align: center;
			}
			.td_r_b1{ 
			    border-bottom:1px solid #CCCCCC; 
				border-right:1px solid #CCCCCC; 
			}
			.STYLE2 {
				color: #FF0000;
			}
			.input1{
				width:200px;
			}
			.input2{
				border:0;
				width:40px;
				border-bottom:1px solid #b5d6e6; 
			}
			.input3{
				border:0;
				width:100px;
			}
			.btn{
				BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; 
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); 
				BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid
			}
			-->
        </style>
	</head>
	<body bgcolor="#FFFFFF" onLoad="doQuery(<%=roadid %>);">
		<fieldset style="width:400px;height:130px;border:1px solid #a5d1ec" align="center">
			<br>
			<b><legend style="border:0px;font-size:11px">
				<%="".equals(roadid)?"道路记录新增":"道路记录编辑"%>
			</legend></b>
            <table width="95%" align="center" class="tableInput" id="dataTable">
            	<tr style="display:none">
                    <td>原始道路编号</td>
                    <td>
                        <input id="ID" type="text" maxLength="32" name="lkid" class="text"
                            value=<%=roadid%> readonly="true"></input>
                    </td>
                </tr>
	            <tr>
                    <td class="td_r_b1" style="text-align:right" width="9%">道路编号</td>
                    <td class="td_r_b1" style="text-align:left" width="16%">
                       <input id="ROADID" class="input1"  type="text" maxLength="19" <%=readStatic%>/>
                       <input id="OLDROADID" type="hidden" value="" />
                    </td>
                </tr>
                <tr>
                    <td class="td_r_b1" style="text-align:right">道路名称</td>
                    <td class="td_r_b1" style="text-align:left">
                       <input id="ROADNAME" class="input1"  type="text" maxLength="20" <%=readStatic%>/>
                    </td>
                </tr>
                <tr>
                    <td class="td_r_b1" style="text-align:right">道路行政等级</td>
                    <td class="td_r_b1" style="text-align:left">
	                    <select  id="ROADLEVEL"  style="width:100;border: 1px #7B9EBD solid" <%=readStatic1%>>
							<option value="1" selected>高速公路</option>
							<option value="2">国道</option>
							<option value="3">省道</option>
						</select>
                    </td>
                </tr>
                 <tr>
                    <td class="td_r_b1" style="text-align:right">道路起点</td>
                    <td class="td_r_b1" style="text-align:left">
                       <input id="START" class="input1"  type="text" maxLength="20" <%=readStatic%>/>
                    </td>
                </tr>
                <tr>
                    <td class="td_r_b1" style="text-align:right">道路终点</td>
                    <td class="td_r_b1" style="text-align:left">
                       <input id="END" class="input1"  type="text" maxLength="20" <%=readStatic%>/>
                    </td>
                </tr>
                <!-- 
                <tr>
                    <td class="td_r_b1" style="text-align:right">道路入口路段备注</td>
                    <td class="td_r_b1" style="text-align:left">
                       <input id="INROADNAME" class="input1"  type="text" maxLength="20" <%=readStatic%>/>
                    </td>
                </tr>
                <tr>
                    <td class="td_r_b1" style="text-align:right">道路出口路段备注</td>
                    <td class="td_r_b1" style="text-align:left">
                       <input id="OUTROADNAME" class="input1"  type="text" maxLength="20" <%=readStatic%>/>
                    </td>
                </tr>
               
                <tr style="display: none">
                    <td class="td_r_b1" style="text-align:right">图层道路编号</td>
                    <td class="td_r_b1" style="text-align:left">
                       <input id="graficRoadId" class="input1"  type="text" maxLength="19" <%=readStatic%>/>
                    </td>
                </tr>
                 -->
                <tr>
                	<td class="td_r_b1" colspan="2" align="center">
                		<table width="100%">
                			<tr>
                				<td>
                					<div style="width: 120px;"></div>
                				</td>
                				<%
								if (insrtOrUpdt.equals("0")) {
								%>	
			                	<td width="33%" align="center">
			                		<input class="btn" type="button" value="重置" onclick="reset();">
			                	</td>
			                	<%
								}
								if (insrtOrUpdt.equals("1")) {
								%>	
			                	<td width="33%" align="center">
			                		<input class="btn" type="button" value="重置" onclick="doQuery(<%=roadid %>);">
			                	</td>
                				<%
								}
								if (!insrtOrUpdt.equals("2")) {
								%>
                				<td width="33%" align="center">
                					<input class="btn" type="button" value="保存" onclick="modify(<%=insrtOrUpdt%>);">
			                	</td>							
			                	
			                	<%
								}
								%>
			                	<td width="34%" align="center">
			                		<input class="btn" type="button" value="关闭" onclick="window.close();">
                				</td>
                				<td>
                					<div style="width: 50px;"></div>
                				</td>
                			</tr>
                		</table>
                	</td>
                </tr>
            </table>
		</fieldset>
	</body>
</html>