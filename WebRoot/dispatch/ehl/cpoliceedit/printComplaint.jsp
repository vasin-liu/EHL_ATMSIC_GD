<%--<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>

<%@ page contentType="application/msword; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%
			response.setHeader("Content-Disposition",
			"inline;filename=filename.doc");
			String bh = StringHelper.obj2str(request .getParameter("bh"), "");
			String jstime = StringHelper.obj2str(request .getParameter("jstime"), "");
			String bsdw = StringHelper.obj2str(request .getParameter("bsdw"), "");
			String bsr = StringHelper.obj2str(request .getParameter("bsr"), "");
			String jsdw = StringHelper.obj2str(request .getParameter("jsdw"), "");
			String jsr = StringHelper.obj2str(request .getParameter("jsr"), "");
			String TSRXM = StringHelper.obj2str(request .getParameter("TSRXM"), "");
			String TSRDH = StringHelper.obj2str(request .getParameter("TSRDH"), "");
			String TSRJZ = StringHelper.obj2str(request .getParameter("TSRJZ"), "");
			String TSRCP = StringHelper.obj2str(request .getParameter("TSRCP"), "");
			String TSJG = StringHelper.obj2str(request .getParameter("TSJG"), "");
			String TSJH = StringHelper.obj2str(request .getParameter("TSJH"), "");
			String TSYWLB = StringHelper.obj2str(request .getParameter("TSYWLB"), "");
			String TSNR = StringHelper.obj2str(request .getParameter("TSNR"), "");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns="http://www.w3.org/TR/REC-html40">
	<head>
		<!--[if gte mso 9]><xml>
			<w:WordDocument><w:View>Print</w:View></w:WordDocument>
		</xml><![endif]-->
		<style>
		<!--
		 p.MsoNormal
			{mso-style-parent:"";
			margin-bottom:.0001pt;
			text-align:justify;
			text-justify:inter-ideograph;
			font-size:10.5pt;
			font-family:Calibri;
			margin-left:0cm; margin-right:0cm; margin-top:0cm}
		 table.MsoNormalTable
			{mso-style-parent:"";
			font-size:10.0pt;
			font-family:"Times New Roman";
			}
		-->
		</style>
	</head>
	<body>
		<p class="MsoNormal" align="center" style="text-align: center">
		<span style="font-size: 18.0pt; font-family: 宋体">广东省公安厅交通管理局值班日志</span></p>
		<p class="MsoNormal" align="center" style="text-align: center">
		<span lang="EN-US" style="font-size: 18.0pt">&nbsp;</span></p>
		<table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" style="border-collapse: collapse" id="table1" width="100%">
			<tr style="height: 6.0pt">
				<td valign="top" style="width: 50%; height: 9.75pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">编号：</span><span lang="EN-US"><%=bh%></span></td>
				<td valign="top" style="width: 50%; height: 9.75pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">报送时间：</span><span lang="EN-US"><%=jstime%></span></td>
			</tr>
			<tr style="height: 6.0pt">
				<td valign="top" style="width: 50%; height: 6.0pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">报送单位：<%=bsdw%></span></td>
				<td valign="top" style="width: 50%; height: 6.0pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">报送人：<%=bsr%></span></td>
			</tr>
			<tr style="height: 6.0pt">
				<td valign="top" style="width: 50%; height: 5.25pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">接收单位：<%=jsdw%></span></td>
				<td valign="top" style="width: 50%; height: 5.25pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">接收人：<%=jsr%></span> </td>
			</tr>
		</table>
		<p class="MsoNormal" align="left" style="text-align: left">
		<span style="font-family: 宋体">&nbsp;领导批示：</span></p>
		<p class="MsoNormal" align="left" style="text-align: left"><span lang="EN-US">
		&nbsp;&nbsp;&nbsp;&nbsp; </span></p>
		<p class="MsoNormal" align="left" style="text-align: left"><span lang="EN-US">&nbsp;</span></p>
		<div style="mso-element: para-border-div; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding: 0cm">
			<p class="MsoNormal" align="left" style="text-align: left; border: medium none; padding: 0cm">
			<span lang="EN-US">&nbsp;</span></div>
		<p class="MsoNormal" align="left" style="text-align: left"><span lang="EN-US">&nbsp;</span></p>
		<table class="MsoNormalTable" border="0" cellspacing="0" cellpadding="0" style="border-collapse: collapse" id="table2" width="100%">
			<tr>
				<td colspan="2" valign="top" style="width: 100%; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm; text-align: center">
				<span style="font-size: 14.0pt; font-family: 宋体">省厅交管局群众投诉登记表</span></td>
			</tr>
			<tr>
				<td valign="top" style="width: 468px; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">投诉人：<%=TSRXM%></span><span lang="EN-US"></span></td>
				<td valign="top" style="width: 470px; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">联系电话：</span><span lang="EN-US"><%=TSRDH%></span></td>
			</tr>
			<tr>
				<td valign="top" style="width: 468px; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">驾驶证号：</span><span lang="EN-US"><%=TSRJZ%></span></td>
				<td valign="top" style="width: 470px; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">车牌号：<%=TSRCP%></span><span lang="EN-US"></span></td>
			</tr>
			<tr>
				<td valign="top" style="width: 468px; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">被投诉辖区：<%=TSJG%></span></td>
				<td valign="top" style="width: 470px; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">被投诉警员警号：</span><span lang="EN-US"><%=TSJH%></span></td>
			</tr>
			<tr>
				<td colspan="2" valign="top" style="width: 952px; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">投诉业务类别：<%=TSYWLB%></span></td>
			</tr>
			<tr style="height: 61.05pt">
				<td colspan="2" valign="top" style="width: 952px; height: 61.05pt; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">投诉内容：</span></p>
				<p class="MsoNormal" align="left" style="text-align: left; text-indent: 21.0pt">
				<span style="font-family: 宋体"><%=TSNR%></span></td>
			</tr>
			<%--<tr style="height: 20.65pt">
				<td colspan="2" valign="top" style="width: 952px; height: 20.65pt; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
				<p class="MsoNormal" align="left" style="text-align: left">
				<span style="font-family: 宋体">附件信息：</span></p>
				<p class="MsoNormal" align="left" style="text-align: left; text-indent: 21.0pt">
				<span lang="EN-US">1.exe</span></td>
			</tr>--%>
		</table>
		<p class="MsoNormal"><span lang="EN-US">&nbsp;</span></p>
		<p class="MsoNormal"><span lang="EN-US">&nbsp;</span></p>
	</body>
</html>
