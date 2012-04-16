<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.base.table.*"%>
<%@ include file="../../../base/Message.oni"%>
<%
	String queryTerm = request.getParameter("queryTerm");
%>
<html>
	<head>
		<title>数据字典管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>	  
        <script type="text/javascript" src="../../js/sysmanage/codeManage.js"></script>
        <script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<style type="text/css">
	  		body {
				background-color: #FFFFFF;
				margin-left:0;
				margin-right:0;
			}
		</style>
</head>
<body onLoad="getCodeGrid('<%=queryTerm%>');" style="overflow:hidden">

		<table id="psTable" width="630"  height="480" border="0" align="center" cellpadding="0"
			cellspacing="0" style="position:absolute;top:2" >
			<tr>
				<td align="left" valign="top">
					<table border="0" align="left" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="88%" border="0" align="center" >
									
									<tr><td colspan="2"><br></td></tr>
									<tr>
										<td align="center" valign="bottom" colspan="3">
											<span class="STYLE1">显示顺序设置</span>										</td>
									</tr>
									<tr>
										<td height="2" valign="top" colspan="3">
											<hr>										</td>
									</tr>
									<tr>
										<td valign="top" align = "center" colspan="3">
											<div id="codeTab" width="99%" height="350"
														style="width: 100%; border: 1px solid #BFBFBF;background-color:white;"></div>										</td>
									</tr>
									<tr>
									   <td align = "left" valign="top"> &nbsp;提示：设置显示顺序时鼠标双击序号列手动输入顺序号，然后点击保存即可。																		</td>
										<td width="23%" align = "right" valign="top"><input name="image" type="image" onClick="saveCodeList()" src="../../image/button/btnsave.gif"/>
										<input type="image" src="../../image/button/btnclose.gif" name="quit" onClick="doClose();" /></td>
										<td align = "right" width="10%">										</td>
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
