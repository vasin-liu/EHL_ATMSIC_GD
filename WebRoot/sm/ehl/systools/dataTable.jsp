<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../../../base/Message.oni"%>
<html>
	<head>
		<title>表格设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
		<script language="JavaScript" src="../../../base/js/table/tableConfig.js"></script>
		<script>
		function onLoadGroupware(){
			onLoadToolbar("0_info,0_excel,0_chart,0_fresh");
			onLoadTableGrid();
		    onLoadFieldGrid();
		    adjustHeight('blktable,blkid');
    		
		}
		</script>
		<style type="text/css">
			body {		
				margin-left:0;
				margin-right:0;
				}
		</style>
	</head>
	<body onLoad="onLoadGroupware()" style="overflow:hidden">
<table id="blktable" width="802" border="0" align="center" cellpadding="0"
	cellspacing="0" style="position: absolute; top: 2" height="503">
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
	<tr>
		<td align="left" valign="top">
		<table  border="0" align="left" cellpadding="0"
			cellspacing="0">

			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					    <td id="blkid" width="3" background="../../image/back/back_midleft.gif" height="496"></td>
						<td>
						<table width="100%" height="86%" border="0" align="center"
							bgcolor="#FFFFFF">
							<tr>
								<td valign="top" bgcolor="#FFFFFF">
								<div id="toolbar_zone"
									style="width: 100%; border: 1px solid Silver;"></div>
									<div id="modify"></div>
								</td>
							</tr>
											<tr>
												<td align="center" valign="bottom">
													<br><span class="STYLE1">数据表格定义</span>
													<hr>
												</td>
											</tr>
											<tr>
												<td width="100%" height="20" valign="top" bgcolor="#FFFFFF">
									<!--  -->
									<table width="100%" border="0">
                                    <tr><td width="89%" rowspan="2">
									<div align="left" style="width: 100%; border: 1px solid #BFBFBF;">
									<!--  -->
													<table width="100%" border="0" align="center">
														<tr>
															<td width="10%">
																<div align="center">
																	表格--
																</div>
															</td>

															<td width="10%">
																<div align="right">
																	编号：



																</div>
															</td>
															<td width="15%">
																<input name="treename" type="text" size="10%" />
															</td>
															<td width="10%">
																<div align="right">
																	命名：



																</div>
															</td>
															<td width="15%">
																<input name="treepoint" type="text" size="10%" />
															</td>
															<td width="10%">
																<div align="right">
																	关联表：
																</div>
															</td>
															<td width="15%">
																<input name="treeid" type="text" size="10%" />
															</td>

														</tr>
														<tr>
															<td>
																<div align="center">
																	字段--
																</div>
															</td>
															<td  >
																<div align="right">
																	编号：



																</div>
															</td>
															<td  >
																<input name="fid" type="text" size="10%" />
															</td>
															<td  >
																<div align="right">
																	关联字段：



																</div>
															</td>
															<td  >
																<input name="fldname" type="text" size="10%" />
															</td>
															<td  >
																<div align="right">
																	字段名：
																</div>
															</td>
															<td  >
																<input name="displayname" type="text" size="10%" />
															</td>
													</table>
								<!--  -->	
								</div></td>
                                <td width="11%">
                                 <input name="button" type="image" src="../../image/button/btnquery.gif" onClick="doFilter();" value="查询" />                                                  </td>
                                  </tr>
                                 <tr>
                                 <td>&nbsp;</td>
                                 </tr>
                               </table>
                            <!--  -->									
												</td>
											</tr>
											<tr>
												<td width="100%" valign="top">
													表格:
													<div id="tProperts" width="100%" height="150"
														style="width: 100%; border: 1px solid #BFBFBF;background-color:white;"></div>
												</td>
											</tr>
											<tr>
												<td width="100%" valign="top">
													字段： <div id="fProperts" width="100%" height="180" style="width: 100%; border: 1px solid #BFBFBF;background-color:white;"></div>
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
