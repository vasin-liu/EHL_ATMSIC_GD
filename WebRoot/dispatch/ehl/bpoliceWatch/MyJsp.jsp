<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ehl.sm.common.Constants"%>
<%
	String userCode = (String)session.getAttribute(Constants.PCODE_KEY);
%>
<!-- 
	 * 
	 * 版 权：北京易华录信息技术股份有限公司 2009
	 * 文件名称：watching.jsp
	 * 摘 要：指挥调度监控界面。显示未处理警情列表，需关注警情列表等信息。



	 * 当前版本：1.0
	 * 作 者：LChQ  2009-4-8
	 * 修改人：
	 * 修改日期：



 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
		<title>指挥调度监控界面</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link type="text/css" rel="Stylesheet" href="css/map.css" />
		<link type="text/css" rel="Stylesheet" href="css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="css/contents.css" />
	 
		<script type="text/javascript" src="script/map/Util.js"></script>
		<script type="text/javascript" src="script/map/LoadLibFile.js"></script>
 		<script type="text/javascript">
 		var btnIDList = ["addPolice","btnAffairProcess","btnTakeOrder"];
		window.onload = function()
		{
		 	 for(var i=0; i<btnIDList.length; i+=1)
			 {
			 	$(btnIDList[i]).attachEvent('onclick',btnFunClick);
			 	//$(btnIDList[i]).style.borderColor = "";
			 	$(btnIDList[i]).style.border ="1 black";
			  	$(btnIDList[i]).style.borderStyle = "solid";
			 	//$(btnIDList[i]).style.color = "white";
			 }
		};
		
	
		function btnFunClick()
		{	
			event.srcElement.style.backgroundColor = '#87F';
			for( var i=0;i<btnIDList.length; i+=1 )
			{
				if($(btnIDList[i]) != event.srcElement)
				{
					if(i==2)
					{
						$(btnIDList[2]).disabled = true;
					}
					else
					{
						$(btnIDList[i]).style.backgroundColor = "transparent";
					}
				}
				 
			}
		}
	</script>
	</head>
   
	<body style="margin: 0">
		<table border="0" cellspacing="2" cellpadding="0" style="text-align: center; width: 100%; height: 100%;">
			<tr>
				<td width=35%>
					<table border="0" cellspacing="0" cellpadding="0" style="text-align: center; width: 100%; height: 100%;">
						<tr>
							<td>
								<fieldset style="border: 1px solid #ccc; valign: top; align: center">
									<legend style="border: 0px; font-weight: 700; font-size: 8pt;">
										未处理警情


									</legend>
									<table style="border: 0; height: 190px; cellpadding: 0; cellspacing: 0;width:98%">
										<tr>
											<!-- 未处理警情列表 -->
											<td width="100%" align=center>
											<div id="divUnhandleList"  
												style="width:98%;vertical-align:text-top;height:156px;text-align:left;overflow:auto;border:1 solid #000;" > 
												</div>
											</td>
										</tr>
										<tr><td align=right><input type="button" id="btnMerge" value="合并警情"/>&nbsp;&nbsp;
										</td></tr>
									</table>
								</fieldset>
							 </td>
						 </tr>
						 <tr>
							<td>
							
								<fieldset style="border: 1px solid #ccc; valign: top; align: center">
									<legend style="border: 0px; font-weight: 700; font-size: 8pt">
										警情类型汇总

									</legend>
									<table border="0" cellpadding="0" cellspacing="0" style="height: 400px" align="center">
										<tr>
											<!-- 警情类型汇总： 以机构、警情类型进行汇总的树结构信息 -->
											<td>
												<div id="treeDiv" class="divTreeStyle">
													<table width="100%"  border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td height="5">&nbsp;</td>
															<td height="10"></td>
													  	</tr>
													  	<tr>
															<td></td>
															<td valign="top" id="treeTD"></td>
													  	</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</fieldset>
							
							</td>
						</tr>
					</table>
				</td>
				<td width=65% height="100%">
					<table border="0" cellspacing="0" cellpadding=0 style="text-align: center; height: 100%;">
						<!-- 操作按钮 -->
						<tr>
							<td align="right">
								<input type="button" value="新增警情" id="addPolice" onclick=""> &nbsp; 
								<input type="button" value="警情处理" id="btnAffairProcess"> &nbsp; 
								<input type="button" value="接收上级指令" id="btnTakeOrder">  
							</td>
						</tr>
						<tr>
							<td>
							<div id="tab1">
								<fieldset style="border: 1px solid #ccc; valign: top; align: center">
									<legend style="border: 0px; font-size: 8pt; font-weight: 700;">
										需关注警情
									</legend>
									<table style="border: 0; cellpadding: 0; cellspacing: 0; height: 160px; align: center;width:98%">
										<tr>
											<!--  需关注警情列表 -->
											<td align=center>
											<div id="divWatchingList"  
												style="width:98%;vertical-align:text-top;height:146px;text-align:left;overflow:auto;border:1 solid #000;" > 
											</div>
											</td>
										</tr>
									</table>
								</fieldset>
								</div>
							</td>
						</tr>
						
						<tr>
						
							<td><!-- 地图显示 -->
							<div id="tab2">
								<div id="map" style="position: relative; width: 700px; height: 400px; left: 0px; top: 0px;">
								</div>
							</div>
								<script type="text/javascript" src="script/map/MapConfig.js"></script>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
