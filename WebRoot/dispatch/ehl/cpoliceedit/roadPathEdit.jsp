<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../../Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
	String rpId = request.getParameter("rpId");
	insrtOrUpdt = null == insrtOrUpdt?"0":insrtOrUpdt;
	rpId = null == rpId?"":rpId;
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
		<script type="text/javascript" src="../../js/ceditpolice/roadPath.js"></script>
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
		<title>道路方向维护</title>
	</head>
<body bgcolor="#FFFFFF" onload="getRoadPathInfo('<%=rpId %>')">
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #a5d1ec" align="center">
			<b><legend style="border:0px;font-size: 11pt;">道路方向维护</legend></b>
				<table class="table2" width="95%">
					<tr>
						<td colspan="2">
							<input type="hidden" id="insrtOrUpdt" value="<%=insrtOrUpdt %>" />
							<input type="hidden" id="rpId" class="textwidth" value="<%=rpId %>" />
						</td>
					</tr>
					<tr>
						<td class="tdtitle">关联道路</td>
						<td class="tdvalue" id="roadIds">
						</td>
					</tr>
					<tr>
						<td class="tdtitle">所属辖区</td>
						<td class="tdvalue" id="jgIds">
							<select id="jgId" disabled style="width:180px;">
								<option selected>请选择道路</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdtitle">上下行：</td>
						<td class="tdvalue" id="">
							<input type="radio" value="0" id="sxxx0" name="sxxx" checked>上行
							<input type="radio" value="1" id="sxxx1" name="sxxx">下行				
						</td>
					</tr>
					<tr>
						<td class="tdtitle">方向名称：</td>
						<td class="tdvalue" id="">
							<input type="text" id="fxmc" class="textwidth" name="fxmc" style="width:180px;">					
						</td>
					</tr>
					<tr>
						<td colspan="2" style="BORDER-RIGHT: #CCCCCC 1px solid;">
							<script type="text/javascript">
								fillListBox("roadIds","roadId","180","select roadId,roadname from t_oa_road_info order by roadid","请选择","","onRoadIdChange");
							</script>
							
							<div style="text-align: middle">
								<input class="btn" type="button" <%="2".equals(insrtOrUpdt)?"style='display:none;'":"" %> value="保存" onclick="modify(<%=insrtOrUpdt %>)" />
								<span style="width:50px"></span>
								<input class="btn" type="button" value="关闭" id="" onclick="window.close();">
							</div>
						</td>
					</tr>
				</table>
		</fieldset>
	</div>
</body>
</html>

