<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
	<%@ page import="com.ehl.query.common.Constants" %>
<html>
  <head>
    <title>驾驶员查询</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/page/PageCtrl.js"></script>
	<script type="text/javascript" src="../../js/driver/DriverQuery.js"></script>
	
  </head>

	<body class="body">
		<div id="dContainer" class="table_tab" align="center">
			<ul id="tags" align="center">
				<li class='selectTag'>
					<a href='#' class="td_2">驾驶员查询</a>
				</li>
			</ul>
			<!--查询区域2008-9-19-->
			<div id="tagContent" align="center">
				<div id="info" class='tagContent selectTag'>
					<table width="100%" border="0">
						<tr>
							<td class="td_2" width="5%" align="right">
								身份证明名称：
							</td>
							<td width="5%" align="left" id='cardNameTdId' class="td_2">
								<script language="javascript">
					         fillListBox("cardNameTdId","cardNameId","200","SELECT DMZ,DMSM1 FROM T_TIRA_CODE  WHERE DMLB='300121'","","","");
					    </script>
								<input name="text" type="text" class="text" id="cardName" />

							</td>
							<td width="5%" align="right" class="td_2">
								姓名：
							</td>
							<td width="8%" align="left" class="td_2">
								<input name="text2" type="text" class="text" id="pname" />
							</td>

						</tr>
						<tr>
							<td class="td_2" width="5%" align="right">
								身份证明号码：
							</td>
							<td width="8%" align="left" class="td_2">
								<input name="text" type="text" class="text" id="cardNo" />
							</td>
							<td width="5%" align="right" class="td_2">
								性别：
							</td>
							<td width="5%" align="left" id="sexTdId" class="td_2">
								<script language="javascript">
					         fillListBox("sexTdId","sexSelectId","180","SELECT DMZ,DMSM1 FROM T_TIRA_CODE  WHERE DMLB='300435'","","","");
								</script>
							</td>
							<td width="10%" align="center" valign="top">
								<input name="query" type="image"
									src="../../image/button/btnquery.gif" onClick="queryDriver();" />
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
										<td width='18%' align='center'>
											姓名
										</td>
										<td width='8%' align='center'>
											性别
										</td>
										<td width='20%' align='center'>
											身份证明名称
										</td>
										<td width='20%' align='center'>
											身份证明号码
										</td>
										<td width='16%' align='center'>
											住所行政区划
										</td>
										<td width='18%' align='center'>
											联系电话
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
