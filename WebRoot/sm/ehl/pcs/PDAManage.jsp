<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ehl.base.table.*"%>
<%@page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>

<%
    String jgid = "";
   String jgmc = "";
   String ccbm = "";
   try{
	   String deptInfo =  DepartmentManage.getDeptInfo(request,"1");
	   jgid = deptInfo.split(",")[0];
	   jgmc = deptInfo.split(",")[1];
	   ccbm  = deptInfo.split(",")[1];
   }catch(Exception e){
       e.printStackTrace();
   }
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>PDA管理</title>
		<jsp:include page="../../../base/ShareInc.html" />
		<script type="text/javascript" src="../../js/pcs/PDAManage.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/output/SaveAsExcel.js"></script>
		<script type="text/javascript" src="../../js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script language="javascript">
			function doOnLoad(){
    			onLoadToolbar();  
    			adjustHeight('pdaTable,pdaTD');
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
		<table id="pdaTable" width="802"  border="0" align="center" cellpadding="0"
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
							<td id="pdaTD" width="3" background="../../image/back/back_midleft.gif" height="496"></td>
							<td>
								<table width="100%" height="88%" border="0" align="center" >
									<tr>
										<td height="25" valign="top">
											<div id="toolbar_zone" style="width: 100%; border: 1px solid #BFBFBF;" />
											<div id="modify"></div>
										</td>
									</tr>
									<tr><td><br></td></tr>
									<tr>
										<td align="center" valign="bottom"><span class="STYLE1">PDA 管理</span></td>
									</tr>
									<tr>
										<td height="2" valign="top"><hr></td>
									</tr>
									<tr>
										<td height="17" valign="top">
										<!--  -->
											<table width="100%" border="0">
		                                              <tr><td width="89%" rowspan="1">
											<div align="left" style="width: 100%; border: 1px solid #BFBFBF;">
											<!--  -->
													<table width="100%" border="0">
														<tr>
															<td width="7%" align="right">
																设备号码:
															</td>
															<td  align="left" width="15%">
																<input  type="text" id="deviceid" class="text" />
															</td>
															<td width="7%" align="right">
																警员姓名:
															</td>
															<td width="15%">
																<input  type="text" id="policename" class="text" />
															</td>
															<td width="7%" align="right">
																是否在线:
															</td>
															<td width="15%" id="devicestatetd">
																<script>
																	fillListBox("devicestatetd","devicestate","100","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='013016'","","");
																</script>
															</td>
															<td align="right" width="7%">
																所属单位:
															</td>
															<td id="SSJGMC" width="17%">
																<input type="text" class="text"  id="ssjgtxt" onClick="reset_dept('ssjgtxt');"readonly></input>
															</td>
															<td align="left" width="3%">
																<img src="../../image/popup/btnselect.gif" alt="选择单位" style="cursor:hand;" style="cursor:hand" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','ssjgtxt','151','487')">
															</td>
														</tr>
														
													</table>
													<!--  -->	
										</div></td>
		                                             <td width="11%">
                                             	<input name="button" type="image" src="../../image/button/btnquery.gif" onClick="Filter();" value="查询" />                                                  
                                             </td>
                                              </tr>
                                               <tr>
                                                <td>&nbsp;</td>
                                             </tr>
                                          </table>
                                          <!--  -->
										</td>
									</tr>
									<tr>
										<td valign="top">
											<%
											String tableScript = PageTableUtil.doDataWinGenerate("d_pda_s");
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
