<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ehl.base.table.*"%>
<%@page import="com.ehl.base.Constants"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<% 
    String jgid = "";//单位编码
    String jgmc = "";//单位名称
    String ccbm = "";//单位层次编码
    String[] strObj = null;
    String deptId = request.getParameter("jgid");//过滤条件，查询该机构下的人员
    try{
        strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    }catch(Exception ex){
        ex.printStackTrace();
    }
    if(strObj.length >=3){
        jgid=strObj[0];//单位编码
        jgmc=strObj[1];//单位名称
        ccbm=strObj[2];//单位层次编码
    }
 %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>人员显示顺序设置</title>
		<jsp:include page="../../../base/ShareInc.html" />
				<script type="text/javascript" src="../../js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../js/pcs/PersonManage.js"></script>
		<style type="text/css">
	  		body {
				background-color: #FFFFFF;
				margin-left:0;
				margin-right:0;
			}
		</style>
	</head>

	<body  style="overflow:hidden">

		<table id="psTable" width="620"  height="445" border="0" align="center" cellpadding="0"
			cellspacing="0" style="position:absolute;top:2" >
			<tr>
				<td align="center" valign="top">
					<table border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="88%" border="0" align="center" >
									
									<tr><td><br></td></tr>
									<tr>
										<td align="center" valign="bottom" colspan="2">
											<span class="STYLE1">人员显示顺序设置</span>
										</td>
									</tr>
									<tr>
										<td style="padding-left: 20px;text-align: center" height="2" valign="top" colspan="2">
											<hr>
										</td>
									</tr>
									
									<tr>
										<td  width="100%" valign="top" style="padding-left: 20px;text-align: center" align="center">
											
											<div id="t_personorder_s"   height="400" style="padding-left: 1px;text-align: center"
														style="width: 586px; border: 1px solid #BFBFBF;background-color:white;">
											<script language="javascript">
											   loadPerson('<%= deptId%>');
											</script>			
											</div>
											<br>
									   </td>
									</tr>
									<tr>
									   <td>
										   <table width="100%">
										      <tr>
										         <td width="40%" style="padding-left: 20px;">
										              提示：双击显示序号进行编辑
										         </td>
										          <td align = "right" width="50%">
												     <input type="image" src="../../image/button/saveset.gif" onclick="saveOrder();" />
												  
													 <input type="image" src="../../image/button/btnclose.gif" name="quit" value="关闭" onClick="window.close();" />
												 </td>
											     <td>&nbsp;
										         </td>
										      </tr>
										   </table>
									   </td>
                                    </tr>
								</table>
							</td>
						</tr>

					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
