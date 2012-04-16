<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.util.*"%>
<%
	Hashtable propAlarm = DispatchUtil.getCurrentUserData(request);
%>
<!-- 
	 * 
	 * 版 权：北京易华录信息技术股份有限公司 2009
	 * 文件名称：dictateDispose.jsp
	 * 摘 要：上级指令指示处理界面。

	 * 当前版本：1.0
	 * 作 者：LChQ  2009-4-8
	 * 修改人：
	 * 修改日期：

 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>接收上级指令指示</title>
		<script type="text/javascript"	src="../../js/common/calendar/CalendarDateTime.js">	</script>
		<script type="text/javascript"	src="../../js/common/utility/DhtmlPaging.js"></script>
		<script type="text/javascript"	src="../../js/common/utility/comLogic.js"></script>
		<script type="text/javascript"  src="../../js/common/utility/DhtmlBranchTree.js" ></script>
		<script type="text/javascript"  src="../../js/common/list/FillListBox.js"	></script>
		<!--  -->
		<jsp:include flush="true" page="../../../sm/ShareInc.html"></jsp:include>
		<style type="text/css">
		.queryTD1 {
			line-height: 16px;
			font-size: 9pt;
			empty-cells: show;
			text-align: right;
			width: 11%;
			border-right: 1 #caa solid;
			border-bottom: 1 #caa solid;
		}
		
		.queryTD2 {
			line-height: 16px;
			font-size: 9pt;
			empty-cells: show;
			text-align: left;
			width: 22%;
			border-right: 1 #silver solid;
			border-bottom: 1 #caa solid;
		}
		</style>
		<script type="text/javascript">
			var userCountyCode = '<%=(String)propAlarm.get("BRANCHID")%>';
			var userCountyText = '<%=(String)propAlarm.get("BRANCHNAME")%>';
			var userSocialName = '<%=(String)propAlarm.get("NAME")%>';
		</script>
	</head>
	<body>
		<table border="0" cellspacing="0" cellpadding=0
			style="text-align: center; width: 95%; height: 96%; valign: top"
			align="center">
			<tr>
				<td align="center" width="95%">
					<table border="0" cellspacing="2" cellpadding="0"
						style="width: 95%;">
						<tr>
							<td align=center nowrap>
								接收时间：


							 
								&nbsp;
								<span style="font-size: 7pt">从</span>
								<input type="text" id="txtBgIncept" readonly
									onclick="SelectDateTime(this)" />
								&nbsp;
								<span style="font-size: 7pt">至</span>&nbsp;
								<input type="text" id="txtTmnIncept" readonly
									onclick="SelectDateTime(this)" />
							 	&nbsp;
								<input type="button" value=" 查 询 " id="btnQuery" />
								 
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align=center valign="top">
					<div id='t_dictate_query'
						style="width: 100%; height: 210px; background-color: white;"></div>
					<div id="divPagingButton"
						style="offsetTop: 0px; line-height: 19; text-align: right"
						class='PageText'></div>
				</td>
			</tr>
			<tr>
				<td align=center valign="top" width="100%">
					<fieldset
						style="border: 1px solid #ccc; valign: top; align: center">
						<legend style="border: 0px; font-weight: 700; font-size: 8pt;">
							<a href=# id="hrefIncept"
								style="border: 1 #0000FF solid; height: 15px; width: 120px; text-align: center;font-weight:300">接收领导指令指示</a>&nbsp;&nbsp;
							<a href=# id="hrefFullfill"
								style="border: 1 #0000FF solid; height: 15px; width: 120px; text-align: center;font-weight:300">落实领导指令指示</a>
						</legend>

						<table cellspacing="0" cellpadding="0" width="100%" height="217">
							<tr>
								<td style="line-height: 5px">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td valign="top">
									<table id="tableDictateContent" style="border-top: 1 #caa solid;border-bottom: 1 #caa solid;line-height:25px" cellspacing="0" cellpadding="0"
										  width="100%">
										<tr >
											<td class="queryTD1" style="line-height:25px" nowrap>
												指示时间：


											</td>
											<td class="queryTD2">
												<span id="txtDictateTime"></span>
											</td>
											<td class="queryTD1" nowrap>
												指示来源：


											</td>
											<td class="queryTD2">
												<span id="txtDictateSource"></span>
											</td>
										</tr>
										<tr>
											<td class="queryTD1" style="line-height:25px" nowrap>
												领导姓名：


											</td>
											<td class="queryTD2">
												<span id="txtLeaderName"></span>
											</td>
											<td class="queryTD1" nowrap>
												指示类型：


											</td>
											<td class="queryTD2">
												<span id="txtDictateType"></span>
											</td>
										</tr>
										<tr>
											<td class="queryTD1" style="line-height:25px" nowrap>
												落实状态：
											</td>
											<td class="queryTD2">
												<span id="txtFullfillState"></span>
											</td>
											<td class="queryTD1" nowrap>
												指示记录人姓名：
											</td>
											<td class="queryTD2">
												<span id="txtDictateRecoder"></span>
											</td>
										</tr>
										<tr>
											<td class="queryTD1" style="line-height:25px" style="border-bottom:0;" nowrap>
												指示内容：


											</td>
											<td class="queryTD2" colspan=3 style="border-bottom:0;line-height:25px" id="tdDictateContent">
											 
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr id="trFullfillContent" style="display:none">
								<td valign="top" >
									<table  style="border-top: 1 #caa solid;border-bottom: 1 #caa solid;" cellspacing="0" cellpadding="0"
										width="100%" >
										<tr>
											<td class="queryTD1"   nowrap>
												落实记录人：
											</td>
											<td class="queryTD2">
												<input type="text" id="txtFullfillRecorder" value='<%=(String)propAlarm.get("NAME")%>' maxlength="8"/>
											</td>
											<td class="queryTD1" nowrap>
												接收指示时间：


											</td>
											<td class="queryTD2">
												<input type="text" id="txtInceptTime" readonly
													onclick="SelectDateTime(this)" />
											</td>
											<td class="queryTD1" nowrap>
												落实指示时间：


											</td>
											<td class="queryTD2">
												<input type="text" id="txtFullfilltime" readonly
													onclick="SelectDateTime(this)" />
											</td>
										</tr>
										<tr>
											<td class="queryTD1"   nowrap>
												落实单位：


											</td>
											<td class="queryTD2" colspan=3>
												<input type="text" id="txtFullfillCounty" size="40" />
											</td>
											<td class="queryTD2" colspan=2 nowrap>
												<input type="checkbox" id="ckFullfillComplete" />
												<a href=# onclick="ckFullfillComplete.checked= !ckFullfillComplete.checked;" >
													落实完毕
												</a>
											</td>
										</tr>
										<tr>
											<td class="queryTD1" style="border-bottom:0;" nowrap>
												落实情况：


											</td>
											<td class="queryTD2" style="border-bottom:0;" colspan=5>
												 <textarea rows="4" style="width:100%" id="txtFullfillContent"></textarea>
											</td>
										</tr>
									</table>
									<div style="width:94%;text-align:right">
										<br>
											 <input type="button" id="btnFullfillSave" value="保存信息"/> 
									</div>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
	</body>
	<script src="../../js/dictate/dictateDispose.js" type="text/javascript"></script>

</html>
