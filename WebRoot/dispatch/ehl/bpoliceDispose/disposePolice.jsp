
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
	
	<body>

		<div style="text-align: center;width: 100%;height: 100%;">
			<fieldset style="width:99%;border:1px solid #CCCCCC" align="center">
				<legend style="border:0px;">
					警情详细信息
				</legend>
				<table class="table2" width="100%">
					<tr>
						<td class="tdtitle">
							报警时间：
						</td>
						<td class="tdvalue">
							<input type="text" id="disposealarmTime" readonly class="textwidth" name="disposeinput">
						</td>
						<td class="tdtitle">
							报警地点：
						</td>
						<td class="tdvalue">
							<input type="text" id="disposealarmSite" readonly class="textwidth" name="disposeinput">
						</td>
						<td class="tdtitle">
							报警人：
						</td>
						<td class="tdvalue">
							<input type="text" id="alarmperson" readonly class="textwidth" name="disposeinput">
						</td>
						<td class="tdtitle">
							事件类型：
						</td>
						<td class="tdvalue" id="disposealarmtypeTd" name="disposeinput">
							<script>
								fillListBox("disposealarmtypeTd","disposealarmType","131","select id,name from t_attemper_code where id like '001%'","请选择");
							</script>
						</td>
					</tr>
					<tr>
						<td class="tdtitle">
							事件细类：
						</td>
						<td class="tdvalue" id="disposeThinTypeTd">
							<input type="text" id="disposeThinType" readonly class="textwidth" name="disposeinput">
						</td>
						<td class="tdtitle">
							事件程度：
						</td>
						<td class="tdvalue" id="disposealarmLevelTd">
							<script>
								fillListBox("disposealarmLevelTd","disposealarmLevel","131","select id,name from t_attemper_code where id like '011%'","请选择");
							</script>
						</td>
						<td class="tdtitle">
							道路类型：
						</td>
						<td class="tdvalue" id="disposeRoadTypeTd" name="disposeinput">
							<script>
								fillListBox("disposeRoadTypeTd","disposeRoadType","131","select id,name from t_attemper_code where id like '212%'","请选择");
							</script>
						</td>
						<td class="tdtitle">
							处理结果：
						</td>
						<td class="tdvalue" id="disposeResultTd" name="disposeinput">
							<script>
								fillListBox("disposeResultTd","disposeResult","131","select id,name from t_attemper_code where id like '213%'","请选择");
							</script>
						</td>
					</tr>
					<tr>
						<td class="tdtitle">
							天气情况：
						</td>
						<td class="tdvalue" id="weatherinfoTd">
							<script>
								fillListBox("weatherinfoTd","weatherinfo","131","select id,name from t_attemper_code where id like '211%'","请选择");
							</script>
						</td>
						<td class="tdtitle">
							报警内容：
						</td>
						<td class="tdvalue" colspan="5">
							<input type="text" id="disposealarmDesc" class="textwidth" name="disposeinput">
						</td>
					</tr>
					
				</table>
			</fieldset>
			<br>
			<div style="width: 99%;text-align: center">
				<table class="table2" width="100%">
					<tr>
						<td width="15%" class="td2" valign="top">
							<div id="">
								<fieldset style="width:99%;border:1px solid #CCCCCC" align="center">
									<legend style="border:0px;">
										警情时间控制
									</legend>
									<table width=100%>
										
										<tr>
											<td width="20%">
												<input type="button" value="派 警" id="pjbutton" onclick="" name="optbtn">
											</td>										
											<td>
												<input readonly type="text" id="pjtime" class="textwidth" name="disposeinput">
											</td>
										</tr>
										<tr>
											<td>
												<input  type="button" value="出 警" id="cjbutton" onclick="" name="optbtn">
											</td>									
											<td>
												<input readonly type="text" id="cjtime" class="textwidth" onclick="" name="disposeinput">
											</td>
										</tr>
										<tr>
											<td>
												<input type="button" value="到 达" id="ddbutton" onclick="" name="optbtn">
											</td>										
											<td>
												<input readonly type="text" id="ddtime" class="textwidth" onclick="" name="disposeinput">
											</td>
										</tr>
										<tr>
											<td>
												<input type="button" value="反 馈" id="fkbutton" onclick="" name="optbtn">
											</td>										
											<td>
												<input readonly type="text" id="fktime" class="textwidth" onclick="" name="disposeinput">
											</td>
										</tr>
										<tr>
											<td>
												<input type="button" value="撤 离" id="clbutton" onclick="" name="optbtn">
											</td>										
											<td>
												<input readonly type="text" id="cltime" class="textwidth" onclick="" name="disposeinput">
											</td>
										</tr>
										<tr>
											<td>
												<input type="button" value="恢 复" id="hfbutton" onclick="" name="optbtn">
											</td>										
											<td>
												<input readonly type="text" id="hftime" class="textwidth" onclick="" name="disposeinput">
											</td>
										</tr>
										<tr>
											<td colspan='2' align="center">
												&nbsp;
											</td>																
										</tr>
										<tr >
											<td colspan='2' align="left">
												<input type="button" value="退 出 调 度" id="tcddbutton" onclick="" name="optbtn">
											</td>												
										</tr>
										
									</table>
								</fieldset>
							</div>
						</td>
						<td class="td2" valign="top">
							<div id="" style="width:100%;">
								<fieldset style="width:99%;border:1px solid #CCCCCC" align="center">
									<legend style="border:0px;">
										警情处理
									</legend>
									<table width=100% >
										<tr>
											<td width=25% valign="top">
												<div style="text-align: center;">													
													<table width=100%>
														<tr>
															<td>&nbsp;</td>
															<td colspan="1" align="center"> 
																<input  type="button" value="事故中队" name="personChk" id="sg" onclick="">&nbsp;
																<input type="button" value="警力查找" name="personChk" id="gq" onclick="">
															</td>	
														</tr>
														<tr>
															<td width="20%" valign="top">
																人员列表
															</td>
															<td id="personlisttd">
																<div id="personlist" style="border: 1px solid #CCCCCC;overflow-Y: scroll;width:100%;height:90;">
																	
																</div>
															</td>
														</tr>
														<tr>
															<td>&nbsp;</td>
															<td  align="left"> 
																<input  type="button" value="车辆查找" name="carChk" id="cl" onclick="">&nbsp;
															</td>	
														</tr>
														<tr>
															<td valign="top"> 
																车辆列表
															</td>
															<td id="carlisttd">
																<div id="carlist" style="border: 1px solid #CCCCCC;overflow-Y: scroll;width:100%;height:60;">
																	
																</div>
															</td>
														</tr>
													</table>
												</div>
											</td>
											<td valign="top">
												<fieldset style="width:99%;border:1px solid #CCCCCC" align="center">
												<legend style="border:0px;">
													警情反馈
												</legend>
												<table width="100%">
													<tr>
														<td width="30%" align="right">
															受伤人数：
														</td>
														<td width="20%">
															<input type="text" id="disposessrs" class="textwidth" name="disposeinput">
														</td>													
														<td width="30%" align="right">
															死亡人数：
														</td>
														<td width="20%">
															<input type="text" id="disposeswrs" class="textwidth" name="disposeinput">
														</td>
													</tr>
													<tr>
														<td width="30%" align="right">
															专用短语：
														</td>
														<td id="zydytd" >
															<script>
																fillListBox("zydytd","zydy","140","select id,name from t_attemper_code where id like '210%'","请选择");
															</script>
														</td>													
														
													
														<td width="30%" align="right">
															通用短语：
														</td>
														<td id="tydytd" >
															<script>
																fillListBox("tydytd","tydy","140","select id,name from t_attemper_code where id like '200%'","请选择");
															</script>
														</td>													
														
													</tr>
													<tr>																						
														<td width="30%" align="right" valign="top">
															处理反馈：
														</td>
														<td colspan="3">
															<textarea  rows="2" style="width:100%" id="disposeDesc" ></textarea>
														</td>
													</tr>
													<tr>																						
														<td width="30%" align="right" valign="top">
															反馈记录：
														</td>
														<td colspan="3" id="fklist">
															<!-- <div id="fklist" style="border:1px solid #CCCCCC;width:100%;height:123px;overflow:scroll; ">
																
															</div>
															 -->
														</td>
													</tr>
												</table>
												
												</fieldset>
											</td>
										</tr>	
									</table>
								</fieldset>
							</div>
						</td>

					</tr>
				</table>

			</div>
		</div>



	</body>
</html>
