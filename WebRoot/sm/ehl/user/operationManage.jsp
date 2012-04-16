<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.base.table.*"%>
<%@ include file="../../../base/Message.oni"%>
<html>
<head>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="this is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><msg:BlkCtrl_zh.Private.title></title>
	<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
	<script type="text/javascript" src="../../js/user/operation.js"></script>
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
		<table id="funcTable" width="802"  border="0" align="center" cellpadding="0"
			cellspacing="0" style="position:absolute;top:2" height="503">
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
					<table border="0" align="left" cellpadding="0"
						cellspacing="0">
						<tr>
							<td id="funcTD" width="3" background="../../image/back/back_midleft.gif"></td>
							<td>
								<!--           -->
							<table width="100%" height="100%" border="0" align="center">
							<tr>
								<td height="25" valign="top">
								<div id="toolbar_zone"
									style="width: 100%; border: 1px solid #BFBFBF;" />

								<div id="modify"></div>
								</td>
							</tr>
							<tr>
								<td align="center" valign="bottom"><span class="STYLE1">操作类型管理</span>
								</td>
							</tr>
							<tr>
								<td height="2" valign="top">
								<hr>
								</td>
							</tr>
							
							<tr>
								<td height="17" valign="top" style="txt-align:center">
							  		<!--  -->
									<table width="100%" border="0" align="center">
                                   		<tr><td width="83%"  align="center">
											<div align="left" style="width: 100%; border: 1px solid #BFBFBF;">
											<!--  -->
											<table width="100%" border="0" >
												<tr height="10"></tr>
												<tr>
													<td width="10%">
														<div align="right">系统名称：</div>
													</td>
													<td  width="14%" id="tdsystem">
														<script language="javascript">
										  					fillListBox("tdsystem","selectSystemname","160","select id,text from t_sys_func where substr(id,3,4)='0000'","","");
													    </script>
													</td>
													
													<td width="10%">
														<div align="right">功能名称：</div>
													</td>
													<td width="14%" id="tdfuncname">
														<script language="javascript">
										  					fillListBox("tdfuncname","selectFuncname","160","select id,text from t_sys_func where substr(id,5,2)<>'00'","","");
													    </script>
													</td>
												</tr>
												<tr height="10"></tr>
											</table>
											<!--  -->	
											</div></td>
                                                <td width="20%" align="center">
                                                <input name="button" type="image" src="../../image/button/btnquery.gif" onClick="Filter();" value="查询" /></td>
                                                 </tr>
                                          </table>
                                             <!--  -->
										</td>
									</tr>
							
							<tr>
								<td valign="top">
								
								<%
									String tableScript = PageTableUtil.doDataWinGenerate("d_operation_s");
								%> <%=tableScript%> <br>
								<script type="text/javascript">
				                     window.gridName = "t_operation_s";
						    			var grid = eval("mygrid" + window.gridName);
						  				grid.hdr.rows[1].childNodes[0].childNodes[0].innerHTML = ' \
									<span style="vertical-align:super;"><input type="checkbox" id="box" value="0" name="ckb_selectAll1" onclick="checkAllRow(mygridt_operation_s);">全选</span>';
				                </script>
								<div id="personbt"></div>
								</td>
							</tr>
						</table>
								<!--           -->	
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
