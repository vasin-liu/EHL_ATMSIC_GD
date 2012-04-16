<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@page import="com.ehl.base.table.*"%>
<%@ include file="../../../base/Message.oni"%>
<html>
	<head>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../js/user/privManage.js"></script>
		<script type="text/javascript" language="javascript">
        	function doOnLoad(){
				onLoadToolbar("0_info,0_excel,0_chart");
				adjustHeight('privTable,privTD');
       		}
		</script>
		<style type="text/css">
			body {
				background-color: #FFFFFF;
				margin-left:0;
				margin-right:0;
			}
		</style>
	</head>
<body onLoad="doOnLoad();" style="overflow: hidden">
	<table id="privTable" width="802" border="0" cellpadding="0" cellspacing="0"  style="position: absolute; top: 2" height="503">
		<tr>
		    <td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					    <td width="3"><img src="../../image/back/back_topleft.gif" width="3" height="1" /></td>
					    <td background="../../image/back/back_topmid.gif"><img src="" alt="" name="" width="32" height="1" align="left" /></td>
					    <td width="3"><img src="../../image/back/back_topright.gif" width="3" height="1" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr style="height:100%">
			<td align="left" valign="top">
				<table  border="0" align="left" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td id="privTD" width="3" background="../../image/back/back_midleft.gif" height="496"></td>
									<td>
										<table width="100%" height="88%" border="0" align="center">
											<tr>
												<td height="25" valign="top">
													<div id="toolbar_zone" style="width: 100%; border: 1px solid Silver;"></div>
													<div id="modify"></div>
												</td>
											</tr>
											<tr>
												<td align="center" valign="bottom">
													<br><span class="STYLE1">权限管理</span>
												</td>
											</tr>
											<tr>
												<td valign="top" height=2><hr></td>
											</tr>
											<tr>
												<td height="17" valign="top">
													<!--  -->
													<table width="90%" border="0">
                                       					<tr>
                                       						<td width="80%">
																<div align="left" style="width: 100%; border: 1px solid #BFBFBF;">
																<!--  -->
																	<table width="95%" border="0" align="center">
																		<tr>
																			<td width="20%">
																				<div align="right">系统类别：</div>
																		   </td>																		
																		  <td id="tdXtlb" width="20%">				
																				<script language="javascript">
																					fillListBox("tdXtlb","XTLB","150","SELECT sysid,sysname FROM t_sys_config where IsDisplay = '1'","");
																			  	</script>
																			</td>									
																			<td width="20%">
																				<div align="right">权限名称：</div>
																		   </td>
																			<td width="20%">
																				<input type="text" name="PrivName" id="PrivName" maxlength="32" class="text" width="100%"/>
																		  </td>																		  
																		</tr>
																  </table>
																	<!--  -->	
																</div>
															</td>
					                                       <td width="20%" align="left" style="padding-left: 30px;">
						                                       <input name="button" type="image" src="../../image/button/btnquery.gif" onClick="Filter();" value="查询" />                                                  </td>
				                                        </tr>
				                                    </table>
				                                    <!--  -->
												</td>
											</tr>
											<tr>
												<td height="100%" valign="top">
													<%=PageTableUtil.doDataWinGenerate("d_qx_s")%>
													<br>
													<script type="text/javascript">
									                     window.gridName = "t_qx_s";
											    			var grid = eval("mygrid" + window.gridName);
											  				grid.hdr.rows[1].childNodes[0].childNodes[0].innerHTML = ' \
														<span style="vertical-align:super;"><input type="checkbox" id="box" value="0" name="ckb_selectAll1" onclick="checkAllRow(mygridt_qx_s);">全选</span>';
									                </script>
												</td>
											</tr>

										</table>
									</td>
								</tr>
							</table>
						</td>
						<td width="3" background="../../image/back/back_midright.gif"></td>
					</tr>
				</table>
			</td>

		</tr>
		<tr>
         <td>
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                   <td width="6"><img src="../../image/back/back_bottleft.gif" width="6" height="7" /></td>
	               <td background="../../image/back/back_bottmid.gif"><img src="" alt="" name="" width="32" height="7" align="left" /></td>
	               <td width="6" valign="top"><img src="../../image/back/back_bottright.gif" width="6" height="7" /></td>
	            </tr>
            </table>
         </td>
    </tr>
	</table>
</body>
</html>
		