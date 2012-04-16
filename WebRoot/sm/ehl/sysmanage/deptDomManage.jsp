<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.base.table.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>
<%
    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//单位名称
    String ccbm=strObj[2];//单位层次编码
 %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>警用辖区权限分配</title>
		<jsp:include page="../../../base/ShareInc.html" />
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/sysmanage/deptDomManage.js"></script>
		<script language="javascript">
			function doOnLoad(){
    			onLoadToolbar();  
    			adjustHeight('psTable,psTD');				
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

	<body onLoad="doOnLoad();" style="overflow:hidden">
		<table id="psTable" width="802"  border="0" align="center" cellpadding="0"
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
							<td id="psTD" width="3" background="../../image/back/back_midleft.gif" height="496"></td>
							
							<td>
								<table width="100%" height="88%" border="0" align="center" >
									<tr>
										<td height="25" valign="top">
											<div id="toolbar_zone"
												style="width: 100%; border: 1px solid #BFBFBF;" />
											<div id="modify"></div>
										</td>
									</tr>
									<tr><td><br></td></tr>
									<tr>
										<td align="center" valign="bottom">
											<span class="STYLE1">警用辖区权限分配</span>
										</td>
									</tr>
									<tr>
										<td height="2" valign="top">
											<hr>
										</td>
									</tr>
									<!--查询条件tr -->
									<tr>
										<td height="17" valign="top">
											<table width="100%" border="0">
			                                   <tr>
			                                      <td width="70%" rowspan="2">
												      <div align="left" style="width: 90%; border: 1px solid #BFBFBF;">
															<table width="100%" border="0" align="left">
																<tr>
																	<td align="right" width="40%">
																		所属单位:
																	</td>
																	<td width="18%" style="text-align:left">
																		<input type="text" style="width:200;" class="text" name="tnDepartmentName" id='tiDepartmentName' onClick="reset_dept('tiDepartmentName');" readonly></input>
																	</td>
																	<td>
																		<img src="../../image/popup/btnselect.gif" alt="选择单位" style="cursor:hand;" style="cursor:hand" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','tiDepartmentName','45','60')">
																	</td>
																	<td align="left" width="20%">																		
																	</td>
																	<td align="left" width="20%" style="text-align:left">                                                                       
																	</td>																	
																</tr>
															</table>
														<!--  -->	
													  </div>
												 </td>
		                                         <td width="30%"align="left" >
		                                            <input name="button" type="image" src="../../image/button/btnquery.gif" onClick="doQuery('<%=jgid %>');" value="查询" />                                           
		                                         </td>
		                                      </tr>
		                                   </table>
										</td>
									</tr>
		                            <!-- 查询条件tr -->
									<tr>
										<td valign="top">
											<%										
											String tableScript = PageTableUtil.doDataWinGenerate("d_deptdom_s");
											%>
											<%=tableScript%>
											<br>
											<div id="personbt"></div>
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
