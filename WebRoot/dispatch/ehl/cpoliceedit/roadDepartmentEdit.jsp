<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper;"%>
<%@include file="../../Message.oni"%>
<%
	String insrtOrUpdt = StringHelper.obj2str(request
			.getParameter("insrtOrUpdt"), "");
	String rdId = request.getParameter("rdId");
	insrtOrUpdt = null == insrtOrUpdt?"":insrtOrUpdt;
	rdId = null == rdId?"":rdId;
	String readStatic = (!"0".equals(insrtOrUpdt))
			&& (!"1".equals(insrtOrUpdt)) ? "readonly" : "";
	String readStatic1 = (!"0".equals(insrtOrUpdt))
			&& (!"1".equals(insrtOrUpdt)) ? "disabled" : "";
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
	 	<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css"> 
	 	<script type="text/javascript" src="../../../base/js/dhtmlx/xmlCreator.js"></script>
	 	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
	 	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/ccommon/CommonClear.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/ccommon/utility/DhtmUtilTable.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/roadDepartment.js"></script>
		<style  type="text/css">
			.cb_text{
				width:120px;
			}
			.btn{
				BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; 
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); 
				BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid
			}
		</style>
		<title>道路辖区维护</title>
	</head>
<body bgcolor="#FFFFFF" onload="getRoadDepartmentInfo('<%=rdId %>')">
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:360px;height:130px;border:1px solid #a5d1ec" align="center">
			<b><legend style="border:0px;font-size: 11pt;">道路辖区维护</legend></b>
				<table class="table2" width="95%">
					<%--<tr bgcolor="#99c4f2">
						<td class="tdtitle" colspan="2">
							<legend style="border:0px;font-size:14px">道路辖区<%="0".equals(insrtOrUpdt)?"新增":("1".equals(insrtOrUpdt)?"编辑":"明细") %></legend>
							<input type="hidden" id="insrtOrUpdt" value="<%=insrtOrUpdt %>" />							
							<input type="hidden" id="rdId" class="textwidth" value="<%=rdId %>" />
						</td>
					</tr>--%>
					<tr>
					<input type="hidden" id="id" class="textwidth" value="<%=rdId %>" />
					<input type="hidden" id="insrtOrUpdt" value="<%=insrtOrUpdt %>" />
						<td class="tdtitle">道路名称</td>
						<td class="tdvalue" id="roadIds">
						</td>
					</tr>
					<tr>
						<td class="tdtitle">所属辖区</td>
						<td class="tdvalue" id="jgIds">
						</td>
					</tr>
					<tr>
						<td class="tdtitle">起始里程</td>
						<td class="tdvalue" id="showLC">
							<input type="text" id="qslc"  style="width: 170"  <%=readStatic%>  maxLength="15" />(千米)
						</td>
					</tr>
					<tr>
						<td class="tdtitle">结束里程</td>
						<td class="tdvalue" id="showLC">
							<input type="text" id="jslc" name="roadLength" style="width: 170"  <%=readStatic%>  maxLength="15" />(千米)
						</td>
					</tr>
					<tr>
						<td class="tdtitle">管辖里程</td>
						<td class="tdvalue" id="showLC">
							<input type="text" id="xqlc" name="roadLength" style="width: 170"  <%=readStatic%>  maxLength="15" />(千米)
						</td>
					</tr>
					<tr>
						<td class="tdtitle">辖区内路段备注</td>
						<td class="tdvalue" id="showLh">
							<textarea id="xqldmc" style="width: 200;height:60"  <%=readStatic%>  ></textarea>
						</td>
					</tr>
					<tr>
						<td class="tdtitle">路面配置警力</td>
						<td class="tdvalue" id="showJL">
							<input type="text" id="lmjl"  style="width:170"  <%=readStatic%>  maxLength="15" />(人数)
						</td>
					</tr>
					<tr>
						<td colspan="2" style="BORDER-RIGHT: #CCCCCC 1px solid;">
							<script type="text/javascript">
								fillListBox("roadIds","gbbm","200","select gbdm,dlmc from t_oa_dictdlfx order by roadlevel,dlmc","请选择","reSetInfo()");
								fillListBox("jgIds","xzqh","200","select jgid,jgmc from t_sys_department order by jgid","请选择","reSetInfo()");
							</script>
							<div style="text-align: middle">
								<input class="btn" type="button" <%="2".equals(insrtOrUpdt)?"style='display:none;'":"" %> value="保存" onclick="modify(<%=insrtOrUpdt %>);" />
								<span style="width:50px"></span>
								<input class="btn" type="button" value="关闭" onclick="window.close();">
							</div>
						</td>
					</tr>
				</table>
		</fieldset>
	</div>
</body>
</html>

