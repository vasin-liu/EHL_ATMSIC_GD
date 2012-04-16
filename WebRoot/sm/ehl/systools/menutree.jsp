<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="../../../base/Message.oni"%>
	<!--
    * author：wangyali
    * date:   2007-11-26
    * version:
-->
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");//获取../../js/grid/customGrid.js文件中函数onLoadToolbar()传来的insrtOrUpdt值
	String did = request.getParameter("did");//获取../../js/grid/customGrid.js文件中函数onLoadToolbar()传来的did值
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../../base/css/style2/Global.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script language="JavaScript" src="../../../base/js/dhtmlx/xmlCreator.js"></script>
	</head>
	<body onload="doQuery(<%=did%>);">
		<fieldset style="width:300;height:300;border:1px solid #CCCCCC"
			align="center">
			<legend style="border:0px;">
				菜单定义数据录入
			</legend><br>
			<table width="90%" align="center" id="dataTable">
				<tr>
					<td width="43%">
						<div align="right">
							ID:
						</div>
					</td>
					<td width="57%">
						<input type="text" id="did" value=<%=did%>size="18"/>
					</td>
				</tr>
				<tr>
					<td>
						<div align="right">
							根节点:
						</div>
					</td>
					<td>
						<input name="parent" type="text" size="18" />
					</td>
				</tr>
				<tr>
					<td>
						<div align="right">
							名称:
						</div>
					</td>
					<td>
						<input name="text" type="text" id="text" size="18" />
					</td>
				</tr>
				<tr>
					<td>
						<div align="right">
							未展图像:
						</div>
					</td>
					<td>
						<input name="im0" type="text" id="im0" size="18" />
					</td>
				</tr>
				<tr>
					<td>
						<div align="right">
							展开图像:
						</div>
					</td>
					<td>
						<input name="im1" type="text" id="im1" size="18" />
					</td>
				</tr>
				<tr>
					<td>
						<div align="right">
							图像:
						</div>
					</td>
					<td>
						<input name="im2" type="text" id="im2" size="18" />
					</td>
				</tr>
				<tr>
					<td>
						<div align="right">
							调用:
						</div>
					</td>
					<td>
						<input name="n_call" type="text" id="n_call" size="18" />
					</td>
				</tr>
				<tr>
					<td>
						<div align="right">
							默认选中:
						</div>
					</td>
					<td>
						<input name="n_select" type="text" id="n_select" size="18" />
					</td>
				</tr>
				<tr>
					<td>
						<div align="right">
							备注:
						</div>
					</td>
					<td>
						<input name="remark" type="text id=" remark"  size="18" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="center">
						<!--modify():调用的是../../js/grid/customGrid.js文件中的函数modify()  -->
						<!--reset();调用的是../../js/grid/customGrid.js文件中的函数reset()  -->
							<input type="image" src="../../image/button/btnsave.gif" name="button" value="保存"onClick="modify(<%=insrtOrUpdt%>);">
							  <input type="image" src="../../image/button/btnreset.gif" name="button"  value="重置" onClick="reset();"/>
							<input type="image" src="../../image/button/btnclose.gif" name="button" value="关闭"onClick="window.close();" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</body>
</html>
