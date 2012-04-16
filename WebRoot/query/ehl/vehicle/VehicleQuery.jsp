<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html>
	<head>
		<title>车辆信息查询</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
		<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css"
			Type="text/css">
		<link href="../../css/tira.css" rel="stylesheet" type="text/css">
		<script type="text/javascript"
			src="../../../sm/js/common/prototype.js"></script>
		<script type="text/javascript"
			src="../../../sm/js/common/popup/Popup.js"></script>
		<script type="text/javascript"
			src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript"
			src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript"
			src="../../../sm/js/common/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../js/vehicle/VehicleQuery.js"></script>

	</head>

	<body class="body">
		<div id="dContainer" class="table_tab" align="center">
			<ul id="tags">
				<li class='selectTag'>
					<a href='#' class="td_2">车辆信息查询</a>
				</li>
			</ul>
			<!--查询区域2009-4-23-->
			<div id="tagContent" align="center">
				<div id="info" class='tagContent selectTag'>
					<table width="100%">
						<tr>
							<td width="10%" align="right" class="td_2">
								号牌种类：
							</td>
							<td width="17%" align="left" id="HPZLTD" class="td_2">
								<script type="text/javascript">
                          fillListBox("HPZLTD","HPZL","130","select DMZ,DMSM1 from T_TIRA_CODE where dmlb='300107'","","","");
								</script>
							</td>
							<td class="td_2" width="10%" align="right">
								号牌号码：
							</td>
							<td width="20%" align="left" class="td_2">
								<input name="text" type="text" class="text" style="width:150"
									id="HPHM" />
							</td>
							<td class="td_2" width="13%" align="right">
								机动车所有人：
							</td>
							<td width="20%" align="left" class="td_2">
								<input name="text" type="text" class="text" style="width:150"
									id="JDCSYR" />
							</td>
							<td width="10%" align="center" valign="top">
								<input name="query"  type="image" src="../../image/button/btnquery.gif" 
									onClick="vehicleInfoQuery();" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!--查询区域2008-9-19-->

			<!--数据表格2008-9-19 -->
			<div id="tagContent" align="center"
				style="height: 82%; width: 781; margin-top: 3">
				<table width="100%" border="0" height="100%" style="margin-left: 3;">
					<tr height="100%">
						<td width="45%" height="100%" valign="top">
							<div id="tdData" class="rollDiv">
								<table id="tabVeh" class="scrollTable" width=100% cellSpacing=0
									cellPadding=0>
									<tr class='scrollColThead' valign="top"
										style="text-decoration: none; background-color: #B4C1E2; line-height: 20px;">
										<td width='15%' align='center'>
											机动车序号
										</td>
										<td width='10%' align='center'>
											号牌种类
										</td>
										<td width='10%' align='center'>
											号牌号码
										</td>
										<td width='10%' align='center'>
											中文品牌
										</td>
										<td width='15%' align='center'>
											车辆类型
										</td>
										<td width='10%' align='center'>
											车身颜色
										</td>
										<td width='30%' align='center'>
											机动车所有人
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<!--数据表格2008-9-19-->
		</div>
	</body>
</html>
