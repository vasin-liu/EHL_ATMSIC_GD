<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.base.table.*"%>
<%@ include file="../../../base/Message.oni"%>
<%
	String CLBM = request.getParameter("CLBM");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>车辆日志</title>
		<jsp:include page="../../ShareInc.html" />
		<script type="text/javascript" src="../../js/pcs/DepartmentSelect.js"></script>		
		<script type="text/javascript" src="../../js/pcs/PersonManage.js"></script>		
		<script type="text/javascript" src="../../js/pcs/CarManage.js"></script>

		<script language="javascript">
			function doOnLoad(CLBM){
				loadLog(CLBM);
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

	<body onLoad="doOnLoad('<%= CLBM%>');" style="overflow:hidden">

		<table id="psTable" width="620"  height="445" border="0" align="center" cellpadding="0"
			cellspacing="0" style="position:absolute;top:2" >
			<tr>
				<td align="left" valign="top">
					<table border="0" align="left" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="88%" border="0" align="center" >
									
									<tr><td><br></td></tr>
									<tr>
										<td align="center" valign="bottom" colspan="2">
											<span class="STYLE1">车辆日志查看</span>
										</td>
									</tr>
									<tr>
										<td height="2" valign="top" colspan="2">
											<hr>
										</td>
									</tr>
									<tr>
										<td valign="top" colspan="2">
											<%
											String tableScript = PageTableUtil.doDataWinGenerate("d_carlog_s");
											%>
											<%=tableScript%>
											<br>
											<div id="personbt"></div>
										</td>
									</tr>
									<tr>
									   <td align = "right" width="90%">
										   <input type="image" src="../../image/button/btnclose.gif" name="quit" value="关闭" onClick="window.close();" />
										</td>
										<td align = "right" width="10%">
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
