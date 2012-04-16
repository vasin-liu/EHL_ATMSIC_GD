<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.base.table.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>

<!--
    * author：ldq
    * date:   2008-03-17
    * version:
-->
<html>
<head>
	
	 <%
	    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
	     String jgid="",jgmc="",ccbm="";
		try{
		     jgid=strObj[0];//单位编码
		     jgmc=strObj[1];//单位名称
		     ccbm=strObj[2];//单位层次编码
	    }catch(Exception e){
	%>
	    <script type="text/javascript">
	    	alert("登陆用户对应机构不存在\n请检查用户表和机构表数据！");
	    </script>
	<%
	    }
	 %>
	
	<title><msg:Common_zh.userManage.jsp.title></title>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="this is my page">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
	<script type="text/javascript" src="../../js/pcs/DepartmentSelect.js"></script>
	<script type="text/javascript" src="../../js/user/UserManage.js"></script>
	<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
	<script language="javascript">
	function doOnLoad(){
		loadToolbar();
		adjustHeight('userTable,userTD');
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
<table id="userTable" width="802" border="0" cellpadding="0" cellspacing="0"
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
	<tr >
		<td align="left" valign="top">
		<table  border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td id="userTD" width="3" background="../../image/back/back_midleft.gif" height="496"></td>
						<td>
						<table width="100%" height="80%" border="0" align="center">
							<tr>
								<td height="25" valign="top">
									<div id="toolbar_zone" style="width: 100%; border: 1px solid Silver;"></div>
								</td>
							</tr>
							<tr>
								<td align="center" valign="bottom"><br><span class="STYLE1"><msg:Common_zh.userManage.jsp.title></span></td>
							</tr>
							<tr><td valign="top" height=2><hr></td></tr>
							<tr>
								<td height="17" valign="top" style="txt-align:center">
							  		<!--  -->
									<table width="100%" border="0" align="center">
                                   		<tr><td width="89%" rowspan="2" align="center">
											<div align="left" style="width: 100%; border: 1px solid #BFBFBF;">
											<!--  -->
											<table width="100%" border="0" >
												<tr height="10"></tr>
												<tr>
													<td width="10%">
														<div align="right">用户名：</div>
													</td>
													<td  width="14%">
														<input type="text" name="USERNAME" class="text" />
													</td>
													
													<td width="10%">
														<div align="right">所属单位：</div>
													</td>
													<td width="14%"><input type="text" class="text" id="DEPTCODE_F" onClick="reset_dept('DEPTCODE_F');"readonly=true/></td>
													<td width="5%"><img src="../../image/popup/btnselect.gif" style="cursor:hand;" alt="选择单位"  onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','DEPTCODE_F','125','460')"></td>
												</tr>
												<tr height="10"></tr>
											</table>
											<!--  -->	
											</div></td>
                                                <td width="11%">
                                                <input name="button" type="image" src="../../image/button/btnquery.gif" onClick="Filter();" value="查询" /></td>
                                                 </tr>
                                          </table>
                                             <!--  -->
										</td>
									</tr>
							<tr>
								<td width="100%" valign="top"  height="80%">
								<%
								    String sql = "";
								    if(DepartmentManage.isManager(jgid)){//true为具有查看所有机构下用户信息的权限
										sql = "SELECT USERCODE,USERNAME,(select xm from t_sys_person where ryid = PERSONCODE)as PERSONCODE,F_GET_FULLDEPT(DEPTCODE),f_get_role(usercode) FROM T_SYS_USER  WHERE 1=1";
									
									}else{//false为不具有查看所有机构信息的权限，只能查看当前登录用户所属机构下用户信息的权限
										sql = "SELECT USERCODE,USERNAME,(select xm from t_sys_person where ryid = PERSONCODE)as PERSONCODE,F_GET_FULLDEPT(DEPTCODE),f_get_role(usercode) FROM T_SYS_USER  WHERE 1=1";
										sql+=" and deptcode in ("+DepartmentManage.getDeptIdStr(jgid)+")";
									}
									sql+=" order by DEPTCODE";
								    String tableScript = PageTableUtil.doDataWinGenerate("d_yh_s",sql);
							   %>
							   <%=tableScript%>
							   <script type="text/javascript">
				                     window.gridName = "t_yh_s";
						    			var grid = eval("mygrid" + window.gridName);
						  				grid.hdr.rows[1].childNodes[0].childNodes[0].innerHTML = ' \
									<span style="vertical-align:super;"><input type="checkbox" id="box" value="0" name="ckb_selectAll1" onclick="checkAllRow(mygridt_yh_s);">全选</span>';
				                </script>
							   <br>
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
