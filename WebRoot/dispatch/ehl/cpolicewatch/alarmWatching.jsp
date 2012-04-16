<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.cdispatch.util.*" %>

<!-- 
	 * 
	 * 版 权：北京易华录信息技术股份有限公司 2009
	 * 文件名称：alarmWatching.jsp
	 * 摘 要：指挥调度监控界面。

	 *
	 * 当前版本：1.0
	 * 作 者：LChQ  2009-4-22
	 * 修改人：
	 * 修改日期：

 -->
<%
	// 获取用户信息
 	Hashtable userData = DispatchUtil.getCurrentUserData(request);
 	String deptcode = "";
 	if(userData != null)
 	{
		deptcode = (String)userData.get("BRANCHID");
	}
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>警情监控</title>
  
  	<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
	<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
	<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
	<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	
	<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
	<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
	
	<script type="text/javascript" src="../../js/policeWatch/dispatchWatching.js"></script>
	<script type="text/javascript" src="../../js/policeWatch/alarmWatchMap.js"></script>
	<script type="text/javascript" src="../../js/common/utility/DhtmUtilTable.js"></script>
	<script type="text/javascript" src="../../js/common/utility/comLogic.js"></script>
	<script type="text/javascript">
		window.attachEvent('onload',pageLoadInitHandler);
	 		
		//显示警情时间范围（小时）
		var msgWatchingTimeRange = '<%= com.appframe.common.Setting.getString("msgWatchingTimeRange") %>';
		
		//刷新频率（单位：秒）
		var msgListRefreshInterval = '<%= com.appframe.common.Setting.getString("msgListRefreshInterval") %>';
		
		//用户所属的机构标识
		var deptcode = '<%=deptcode%>';
		
		function pageLoadInitHandler()
		{
			initWatching();		//初始化监控警情列表

			watching.setRowClickHandler(rowClickCenterPoint );
			$('spanShowHourSetting').innerHTML = msgWatchingTimeRange;
			MapUtils.prototype.moveToAreaCenter(deptcode);
		}
	</script>
  </head>
  <body style="margin:0;">
		<table border="0" cellspacing="0" cellpadding="0" style="text-align: center; width: 101%; height: 100%;">
			<tr>
				<td width=34% valign=top>
					<table border="0" cellspacing="0" cellpadding="0" style="text-align: center; width: 100%; height: 100%;">
						<tr>
							<td valign=top>
								<fieldset style="border: 1px solid #ccc; valign: top; align: center">
									<legend style="border: 0px; font-weight: 700; font-size: 8pt;">
										警情信息(最近<span id="spanShowHourSetting"></span>小时)
									</legend>
									<table style="border: 0; height: 500px; cellpadding: 0; cellspacing: 0;width:98%">
										<tr>
											<td style="line-height:12px">&nbsp;
											<a href="#" id="ahrefAllAffair">
												<input type='radio' name="radioSortLink" checked style="width:12px;height:12px" id="radioAllAffair">
												需关注警情(共<span id="spanAllAffair"></span>起)
											</a>&nbsp;</td>
											<td style="line-height:12px">&nbsp;<a href="#" id="ahrefComplete">
												<input type='radio' name="radioSortLink" style="width:12px;height:12px" id="radioCompleteAffair"/>
												处理完毕警情(共<span id="spanCompleteAffair"></span>起)
											</a>&nbsp;</td>
										</tr>
										<tr  style="display:none">
											<td style="line-height:12px">&nbsp;
											<a href="#" id="ahrefProcessing">
											<input type='radio' name="radioSortLink" style="width:12px;height:12px" id="radioProcessingAffair"/>
												正在处理警情(共<span id="spanProcessingAffair"></span>起)
											</a>&nbsp;</td>
											<td style="line-height:12px">&nbsp;
											<a href="#" id="ahrefUnhandle">
											<input type='radio' style="width:12px;height:12px" name="radioSortLink" id="radioUnhandleAffair" />
												未处理警情(共<span id="spanUnhandleAffair"></span>起)
											</a> &nbsp;</td>
										</tr>
										<tr>
											<!-- 未处理警情列表 -->
											<td width="100%" align=center colspan=2>
											<div id="divUnhandleList"  
												style="width:98%;vertical-align:text-top;height:400px;text-align:left;overflow:auto;border:1 solid #000;" > 
												</div>
											</td>
										</tr>
									</table>
								</fieldset>
							 </td>
						 </tr>
					</table>
				</td>
				<td width=3px>&nbsp;
				</td>
				<td width=65%>
					<table border="0" cellspacing="0" cellpadding=0 style="width:100%; height: 100%;">
						<!-- 操作按钮 -->
						<tr>
							<td  valign=top style="text-align:center;width:100%" align=center><!-- 地图显示 -->		
								<div id="map"  style="position: relative; width: 100%; height: 540px; left: 0px; top: 0px;margin:0">
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<script type="text/javascript" src="../../../webgis/script/map/MapConfig.js"></script>
	</body>
</html>
