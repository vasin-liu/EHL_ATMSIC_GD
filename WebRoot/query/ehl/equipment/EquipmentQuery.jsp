<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html>
  <head>
    <title>设备信息查询</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/page/PageCtrl.js"></script>
	<script type="text/javascript" src="../../js/equipment/EquipmentQuery.js"></script>
  </head>

	<body class="body">
		<div id="dContainer" class="table_tab" align="center">
			<ul id="tags">
				<li class='selectTag'>
					<a href='#' class="td_2">设备信息查询</a>
				</li>
			</ul>
			<!--查询区域2009-4-23-->
			<div id="tagContent" align="center">
				<div id="info" class='tagContent selectTag'>
					<table width="100%" border="0">
						<tr>
							<td width="10%" align="right" class="td_2">
								设备编码：
							</td>
							<td width="20%" align="left" class="td_2">
								<input name="text" type="text" class="text" style="width: 150" id="SBBM" />
							</td>
							<td class="td_2" width="10%" align="right">
								设备类型：
							</td>
							<td width="20%" align="left" id="SBLXTD" class="td_2">
								<script type="text/javascript">
		                        	fillListBox("SBLXTD","SBLX","150","select id,value from atms_equipment_dict where type='00_SBLX'","");
							   	</script>
							</td>
							<td class="td_2" width="10%" align="right">
								设备状态：
							</td>
							<td width="20%" align="left" id="SBZTTD" class="td_2">
								<script type="text/javascript">
		                            fillListBox("SBZTTD","SBZT","150","select id,value from atms_equipment_dict where type='设备状态'","");
							   	</script>
							</td>
							<td width="10%" align="center" valign="top">
								<input name="query" type="image" src="../../image/button/btnquery.gif" onClick="equipmentInfoQuery();" />
							</td>

						</tr>
					</table>
				</div>
			</div>
			<!--查询区域2008-9-19-->

			<!--数据表格2008-9-19 -->
			<div id="tagContent" align="center"
				style="height: 78%; width: 781; margin-top: 3">
				<table width="100%" border="0" height="100%" style="margin-left: 3;">
					<tr height="100%">
						<td width="45%" height="100%" valign="top">
							<div id="tdData" class="rollDiv">
								<table id="tabVeh" class="scrollTable" width=100% cellSpacing=0
									cellPadding=0>
									<tr class='scrollColThead' valign="top"
										style="text-decoration: none; background-color: #B4C1E2; line-height: 20px;">
										<td width='10%' align='center'>
											主键
										</td>
										<td width='15%' align='center'>
											设备类型
										</td>
										<td width='10%' align='center'>
											设备编码
										</td>
										<td width='30%' align='center'>
											产品名称
										</td>
										<td width='35%' align='center'>
											所属机构
										</td>
									</tr>
								</table>
							</div>
						</td>
						<!--2009-3-20 修改-->
					</tr>
				</table>
			</div>
			<!--数据表格2008-9-19-->
		</div>
	</body>
</html>
	   