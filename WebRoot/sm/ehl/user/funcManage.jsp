<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.base.table.*"%>
<%@ include file="../../../base/Message.oni"%>
<html>
	<head>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
		<script type="text/javascript" src="../../js/user/FunctionManage.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" language="javascript">
		function doOnLoad(){
			onLoadToolbar();
			adjustHeight('funcTable,funcTD');
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
		<table id="funcTable" width="802" border="0" cellpadding="0" cellspacing="0"
			style="position: absolute; top: 2" height="503">
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
					<table  border="0" align="left" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td id="funcTD" width="3" background="../../image/back/back_midleft.gif"
											height="496"></td>
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
														<br><span class="STYLE1"><msg:Common_zh.functionManage.jsp.title>
														</span>
													</td>
												</tr>
												<tr>
													<td valign="top" height=2><hr></td>
												</tr>
												<tr>
													<td height="17" valign="top">
														<!--  -->
													<table width="90%" border="0">
                                        				<tr><td width="89%">
													<div align="left" style="width: 100%; border: 1px solid #BFBFBF;">
													<!--  -->
														<table width="95%" border="0" align="center">
															<tr>
																<td width="8%" align="right">系统类别：</td>
																<td width="10%" id="tdXtlb">
																	<script language="javascript">
																		fillListBox("tdXtlb","XTLB","100","SELECT substr(ID,0,2),TEXT FROM t_sys_func where id like '%0000'","","");
																  	</script>
																</td>
																<td width="8%">
																	<div align="right">功能编码：</div>
																</td>
																<td width="10%">
																	<input type="text" id="funcid" maxlength="32" class="text" width="100%"/>
																</td>
																<td width="8%">
																	<div align="right"><msg:Common_zh.function.jsp.fname>：</div>
																</td>
																<td width="10%%">
																	<input type="text" id="funcname" maxlength="32" class="text" width="100%"/>
																</td>
															</tr>
														</table>
														<!--  -->	
											</div></td>
	                                       <td width="8%" align="center">
	                                       <input name="button" type="image" src="../../image/button/btnquery.gif" onClick="Filter();" value="查询" />                                                  </td>
	                                        </tr>
	                                    </table>
	                                    <!--  -->
													</td>
												</tr>
												<tr>
													<td height="100%" valign="top">
														<%=PageTableUtil.doDataWinGenerate("d_gn_s")%>
														<br>
														<script type="text/javascript">
									                     window.gridName = "t_gn_s";
											    			var grid = eval("mygrid" + window.gridName);
											  				grid.hdr.rows[1].childNodes[0].childNodes[0].innerHTML = ' \
														<span style="vertical-align:super;"><input type="checkbox" id="box" value="0" name="ckb_selectAll1" onclick="checkAllRow(mygridt_gn_s);">全选</span>';
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
