<%--<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>

<%@ page contentType="application/msword; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%
	response.setHeader("Content-Disposition",
			"inline;filename=filename.doc");
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String alarmId = StringHelper.obj2str(request
			.getParameter("alarmId"), "");
	String time = StringHelper.obj2str(
			request.getParameter("time"), "");
	String fromUnit = StringHelper
			.obj2str(request.getParameter("fromUnit"), "");
	String fromPerson = StringHelper.obj2str(request.getParameter("fromPerson"), "");
	String alarmType = StringHelper.obj2str(request.getParameter("alarmType"), "")+"详情";
	String detailContent = StringHelper.obj2str(request.getParameter("detailContent"), "");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:o="urn:schemas-microsoft-com:office:office"
	xmlns:w="urn:schemas-microsoft-com:office:word"
	xmlns=" http://www.w3.org/TR/REC-html40">
	<head>
		<style>
p.MsoNormal {
	mso-style-parent: "";
	margin-bottom: .0001pt;
	text-align: justify;
	text-justify: inter-ideograph;
	font-size: 10.5pt;
	font-family: Calibri;
	margin-left: 0cm;
	margin-right: 0cm;
	margin-top: 0cm
}

table.MsoNormalTable {
	mso-style-parent: "";
	font-size: 10.0pt;
	font-family: "Times New Roman";
}

p.MsoTitle {
	margin-top: 12.0pt;
	margin-right: 0cm;
	margin-bottom: 3.0pt;
	margin-left: 0cm;
	text-align: center;
	font-size: 16.0pt;
	font-family: Cambria;
	font-weight: bold
}
</style>
		<!--[if gte mso 9]><xml>
			<w:WordDocument><w:View>Print</w:View></w:WordDocument>
		</xml><![endif]-->
	</head>
	<body>
		<p class="MsoNormal" align="center" style="text-align: center">
			<p class="MsoTitle">
				<span style="font-size: 22.0pt; font-family: 宋体"><%=fromUnit%>值班日志</span>
			</p>
		</p>
		<p class="MsoNormal" align="center" style="text-align: center">
			<span lang="EN-US" style="font-size: 18.0pt">&nbsp;</span>
		</p>
		<table class="MsoNormalTable" border="0" cellspacing="0"
			cellpadding="0" style="border-collapse: collapse" id="table1">
			<tr style="height: 9.75pt">
				<td width="338" valign="top"
					style="width: 253.5pt; border: none; border-bottom: solid windowtext 1.5pt; padding: 0cm 5.4pt 0cm 5.4pt; height: 9.75pt">
					<p class="MsoNormal" align="left"
						style="font-size: 12.0pt; text-align: left">
						<span style="font-family: 宋体">编号：</span><a name="ALARMID"><%=alarmId%></a>
					</p>
				</td>
				<td width="230" valign="top"
					style="width: 172.6pt; border: none; border-bottom: solid windowtext 1.5pt; padding: 0cm 5.4pt 0cm 5.4pt; height: 9.75pt">
					<p class="MsoNormal" align="left"
						style="font-size: 12.0pt; text-align: left">
						<span style="font-family: 宋体">报送时间：<%=time%></span>
					</p>
				</td>
			</tr>
			<tr style="height: 6.0pt">
				<td width="338" valign="top"
					style="width: 253.5pt; height: 6.0pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
					<p class="MsoNormal" align="left"
						style="font-size: 12.0pt; text-align: left">
						<span style="font-family: 宋体">来文单位：</span><a name="SENDUNIT"><%=fromUnit%></a>
					</p>
				</td>
				<td width="230" valign="top"
					style="width: 172.6pt; height: 6.0pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
					<p class="MsoNormal" align="left"
						style="font-size: 12.0pt; text-align: left">
						<span style="font-family: 宋体">值班员：<%=fromPerson%></span>
					</p>
				</td>
			</tr>
			<tr style="height: 5.25pt">
				<td width="338" valign="top"
					style="width: 253.5pt; height: 5.25pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
					<p class="MsoNormal" align="left"
						style="font-size: 12.0pt; text-align: left">
						<span style="font-family: 宋体">接收单位：</span>
					</p>
				</td>
				<td width="230" valign="top"
					style="width: 172.6pt; height: 5.25pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
					<p class="MsoNormal" align="left"
						style="font-size: 12.0pt; text-align: left">
						<span style="font-family: 宋体">接收人：</span>
					</p>
				</td>
			</tr>
		</table>
		
		<p class="MsoTitle">
			<span style="font-family: 宋体"><%=alarmType%></span>
		</p>
		<p class="MsoNormal">
			<span lang="EN-US">&nbsp;&nbsp; &nbsp;</span>
		</p>
		<p class="MsoNormal">
			<span lang="EN-US" style="font-size: 15.0pt">&nbsp;&nbsp; </span>
			<span style="font-size: 12.0pt; font-family: 宋体"><%=detailContent%></span>
		</p>
	</body>

</html>
