<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 
	 * 
	 * 版 权：北京易华录信息技术股份有限公司 2009
	 * 文件名称：mergeAffairs.jsp
	 * 摘 要：合并警情信息。	 * 当前版本：1.0
	 * 作 者：LChQ  2009-4-10
	 * 修改人：
	 * 修改日期： -->
<%
	String affairIDs = request.getParameter("AFFAIRIDS");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>警情合并</title>
		<script src="../../js/common/prototype.js" type="text/javascript"></script>
		<script src="../../js/common/utility/DhtmUtilTable.js" type="text/javascript"></script>
		<script src="../../js/affairProcess/mergeAffairs.js" type="text/javascript"></script>
		<script type="text/javascript">
			window.onload = function(){
		 		var mergeAffair = new MergeAffairs('<%=affairIDs %>');
		 		document.getElementById('btnCancle').onclick = function(){window.close();};
		 		document.getElementById('btnConfirm').onclick = function(){ mergeAffair.mergeHandler();};
		 		mergeAffair.readAffairBrief();	//读取警情信息
			};
		</script>
	</head>
	<body style="margin: 0">
		<table border="0" cellspacing="2" cellpadding="0"
			style="text-align: center; width: 100%; height: 100%;">
			<tr><td>&nbsp;
			</td></tr>
			<tr><td  style="font-size:11pt;font-weight:500">请选择主报警单
			</td></tr>
			<tr>
				<td>
					<div id="divMergeAffairList"
						style="width: 90%; vertical-align: text-top; height: 155px; text-align: left; overflow: auto; border: 1 solid #000;">
					</div>
				</td>
			</tr>
			<tr>
				<td align=right>
					<input type="button" value=" 合 并 " id="btnConfirm"/> &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value=" 取 消 " id="btnCancle"/>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
				</td>
			</tr>
			<tr><td>&nbsp;
			</td></tr>
		</table>
	</body>
</html>
