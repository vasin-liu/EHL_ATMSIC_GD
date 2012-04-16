<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ehl.base.table.*"%>
<%@ page import="com.ehl.base.util.BaseUtil"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.ehl.base.table.PageUtil"%>
<%@ include file="../../../base/Message.oni"%>

<html>

	<%
		String ccbm="",jgid="",jgmc=""; 
	    try{
	    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
	    String jgidStr = BaseUtil.getSysOption("02","02001");
	    String jdmcStr = BaseUtil.getSysOption("02","02002");
	     jgid=jgidStr;//strObj[0];//单位编码
	     jgmc=jdmcStr;//strObj[1];//机构名称
	    Object ccbmObj = DBHandler.getSingleResult("SELECT JGCCBM FROM T_SYS_DEPARTMENT WHERE JGID ='"+jgid+"'");
	     ccbm=ccbmObj.toString();//strObj[2];//单位层次编码
	    
	    String[] str1Obj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
	   
	     String strjgid=str1Obj[0];//机构编码
	     String strjgmc=str1Obj[1];//机构名称
	    }catch(Exception e){
	   
	 %>
	 	 <script type="text/javascript">
	    	alert("登陆用户对应机构不存在\n请检查用户表和机构表数据！");
	    </script>
	 <%
	 	}
	  %>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>人员管理</title>
		<jsp:include page="../../../base/ShareInc.html" />
		<link rel="STYLESHEET" type="text/css" href="../../css/popup/Popup.css">
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<script type="text/javascript" src="../../js/pcs/PersonManage.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/output/SaveAsExcel.js"></script>
		<script type="text/javascript" src="../../js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
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
											<div id="toolbar_zone" style="width: 100%; border: 1px solid #BFBFBF;" />
											<div id="modify"></div>
										</td>
									</tr>
									<tr>
										<td align="center" valign="bottom"><span class="STYLE1">人员管理</span></td>
									</tr>
									<tr>
										<td height="2" valign="top"><hr></td>
									</tr>
									<tr>
										<td height="17" valign="top">
										<!--  -->
											<table width="100%" border="0">
		                                              <tr><td width="89%" rowspan="2">
											<div align="left" style="width: 100%; border: 1px solid #BFBFBF;">
											<!--  -->
													<table width="100%" border="0">
														<tr>
															<td width="18%" align="right">
																姓名:
															</td>
															<td  colspan="3" align="left" width="10%">
																<input  type="text" id="XM" class="text" />
															</td>
															<td width="18%" align="right">
																警号:
															</td>
															<td width="30%">
																<input  type="text" id="JH" class="text" />
															</td>
															<td width="3%"></td>
														</tr>
														<tr>
															<td align="right" width="10%">
																现任职务:
															</td>
															<td id="RYXRZW" width="15%">
																<script language="javascript">
																	fillListBox("RYXRZW","XRZW","100","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='013011'","","");
																</script>
															</td>
															<td align="right" width="10%">
																性别:
															</td>
															<td id="PersonXB" width="10%">
																<script language="javascript">
																	fillListBox("PersonXB","XB","100","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011005'","","");
																</script>
															</td>
															<td align="right" width="18%">
																所属单位:
															</td>
															<td id="SSJGMC" width="30%">
																<input type="text" class="text" id="SSJG" onClick="reset_dept('SSJG');" readonly></input>
															</td>
															<td align="left" width="3%">
																<img src="../../image/popup/btnselect.gif" alt="选择单位" style="cursor:hand;" style="cursor:hand" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','SSJG','151','487','yes')">
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
										    String sql = "SELECT RYID,XM,F_GET_NAME('013011',XRZW),ssjg,st,sj,bgdh,JH,F_GET_NAME('011005',XB),F_GET_FULLDEPT(SSJG),sthh,to_number(xh) xh FROM T_SYS_PERSON  WHERE 1=1 order by SSJG,XH";    
										    String tableScript = PageTableUtil.doDataWinGenerate("d_pers_s",sql);
										  %>
										  <%=tableScript%>
										  <script type="text/javascript">
						                     window.gridName = "t_pers_s";
								    			var grid = eval("mygrid" + window.gridName);
								  				grid.hdr.rows[1].childNodes[0].childNodes[0].innerHTML = ' \
											<span style="vertical-align:super;"><input type="checkbox" id="box" value="0" name="ckb_selectAll1" onclick="checkAllRow(mygridt_pers_s);">全选</span>';
						                </script>
						                <div align="right">
						                    <input type="image" src="../../image/button/setorder.gif" onclick="setOrder();" />
						                </div>
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
