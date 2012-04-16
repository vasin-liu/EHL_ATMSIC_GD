<%@ page language="java" import="java.util.*"
	contentType="application/msword; charset=gbk" pageEncoding="UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.sm.base.Constant"%>
<%@page import="com.ehl.util.Print"%>
<%@page import="java.net.URLEncoder"%>
<%
	request.setCharacterEncoding("utf-8");
	String displayName = new String("值班日志".getBytes("gb2312"), "ISO8859-1");
	response.addHeader("Content-Disposition","attachment;filename="+displayName+".doc");
	String cujgmc = "", bh = "", time = "", fsdw = "", fszby = "", jsdw = "", jszby = "", title = "", ncontent = "", xb = "", acontent = "";
	String id = request.getParameter("id");
	String rtime =  request.getParameter("rtime");
	String ctime = request.getParameter("ctime");
	String jgid = String.valueOf(session.getAttribute(Constant.JGID_VAR));
	String snstr = Print.getSerialNumber(application, id, jgid);
	if (snstr != null) {
		cujgmc = (String)session.getAttribute(Constant.JGMC_VAR);
		if(cujgmc != null){
			cujgmc = Constant.nvl(request.getParameter("cujgmc"));
		}
		//编号=填报日期+序列号
		bh = Print.getBh(rtime,snstr);
		if (bh == null) {
			bh = "";
		}
		//日期=接收时间||当前时间，针对总队
		time = Print.getNoticeTimeFromBase(id);
		fsdw = StringHelper.obj2str(request.getParameter("fsdw"), "");
		fszby = StringHelper.obj2str(request.getParameter("fszby"), "");
		jsdw = StringHelper.obj2str(request.getParameter("jsdw"), "");
		jszby = Print.getNoticePerSonFromBase(id, jgid);
		title = StringHelper.obj2str(request.getParameter("title"), "");
		ncontent = StringHelper.obj2str(request.getParameter("ncontent"), "").trim();
		xb = StringHelper.obj2str(request.getParameter("xb"), "")
				.trim();
		acontent = StringHelper.obj2str(
				request.getParameter("acontent"), "").trim();
	} else {
		session.setAttribute(Constant.ERRMESSUSER_KEY, "登录超时，请重新登录!");
		String loginPagePath = Constant.getLoginPage(request);
		response.sendRedirect(loginPagePath);
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:o="urn:schemas-microsoft-com:office:office"
	xmlns:w="urn:schemas-microsoft-com:office:word"
	xmlns=" http://www.w3.org/TR/REC-html40">
	<head>
		<title>值班日志</title>
		<style>
.MsoNormal {
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

.MsoNormalTable {
	mso-style-parent: "";
	font-size: 10.0pt;
	font-family: "Times New Roman";
}

.MsoTitle {
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
		<!--[if gte mso 9]>
		<xml>
			<w:WordDocument><w:View>Print</w:View></w:WordDocument>
		</xml>
		<![endif]-->
	</head>
	<body>
		<p class="MsoNormal" align="center" style="text-align: center">
		<p class="MsoTitle">
			<span style="font-size: 26.0pt; font-family: 宋体"><%=cujgmc%>值班日志</span>
		</p>
		</p>
		<p class="MsoNormal" align="center" style="text-align: center">
			<span lang="EN-US" style="font-size: 18.0pt">&nbsp;</span>
		</p>
		<table class="MsoNormalTable" border="0" cellspacing="0"
			cellpadding="0" style="border-collapse: collapse">
			<tr style="height: 100pt">
				<td width="300" valign="top"
					style="width: 253.5pt; border: none; border-bottom: solid windowtext 1pt; padding: 10px 5.4pt 10px 5.4pt; height: 9.75pt">
					<p class="MsoNormal" align="left"
						style="font-size: 14.0pt; text-align: left">
						<span style="font-family: 宋体"><strong>编号：</strong></span><a name="bh" style="font-family: 宋体"><%=bh%></a>
					</p>
				</td>
				<td width="400" valign="top"
					style="width: 172.6pt; border: none; border-bottom: solid windowtext 1pt; padding: 10px 5.4pt 10px 5.4pt; height: 9.75pt">
					<p class="MsoNormal" align="left"
						style="font-size: 14.0pt; text-align: left">
						<span style="font-family: 宋体"><strong>日期：</strong></span><a name="time" style="font-family: 宋体"><%=time%></a>
					</p>
				</td>
			</tr>
			<tr style="height: 100pt">
				<td width="400" valign="top"
					style="width: 400pt; height: 6.0pt; border-left: medium none; border-right: medium none; border-top: medium none;padding: 10px 5.4pt 0px 5.4pt">
					<p class="MsoNormal" align="left"
						style="font-size: 14.0pt; text-align: left">
						<span style="font-family: 宋体"><strong>发送单位：</strong></span><a name="SENDUNIT"><%=fsdw%></a>
					</p>
				</td>
				<td width="230" valign="top"
					style="width: 172.6pt; height: 6.0pt; border-left: medium none; border-right: medium none; border-top: medium none; padding: 10px 5.4pt 0px 5.4pt">
					<p class="MsoNormal" align="left"
						style="font-size: 14.0pt; text-align: left">
						<span style="font-family: 宋体"><strong>值班员：</strong><%=fszby%></span>
					</p>
				</td>
			</tr>
			<tr style="height: 100pt">
				<td width="400" valign="top"
					style="width: 400pt; height: 5.25pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1pt solid windowtext;padding: 10px 5.4pt 0px 5.4pt">
					<p class="MsoNormal" align="left"
						style="font-size: 14.0pt; text-align: left">
						<span style="font-family: 宋体"><strong>接收单位：</strong></span><a name="SENDUNIT"><%=jsdw%></a>
					</p>
				</td>
				<td width="230" valign="top"
					style="width: 253.5pt; height: 5.25pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1pt solid windowtext;padding: 1px 5.4pt 10px 5.4pt">
					<p class="MsoNormal" align="left"
						style="font-size: 14.0pt; text-align: left">
						<span style="font-family: 宋体"><strong>接收人：</strong></span><a name="SENDUNIT"><%=jszby%></a>
					</p>
				</td>
			</tr>
			<!--
		<tr style="height: 100pt">
			<td width="400" valign="top" colspan="2"
					style="width: 400pt; height: 5.25pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1pt solid windowtext;padding: 15px 5.4pt 15px 5.4pt">
					<p class="MsoNormal" align="left"
						style="font-size: 15.0pt; text-align: left">
						<span style="font-family: 宋体"><strong>拟办意见：</strong><b style="font-size : 14.0pt">呈处领导阅示，报厅领导</b></span><a name="SENDUNIT"></a>
					</p>
					<br>
				</td>
			</tr>
		-->
		<tr style="height: 100pt">
			<td width="400" valign="top" colspan="2"
					style="width: 400pt; height: 5.25pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1pt solid windowtext;padding: 10px 5.4pt 10px 5.4pt">
					<p class="MsoNormal" align="left"
						style="font-size: 15.0pt; text-align: left">
						<span style="font-family: 宋体"><strong>	领导批示：</strong></span><a name="SENDUNIT"></a>
						<br>
						<br>
						<br>
					</p>
					<br>
				</td>
			</tr>
		
		</table>
		<p class="MsoTitle">
			<span style="font-family: 宋体;font-size: 18.pt"> <%=title%></span>
		</p>
		<div class="MsoNormal">
			<!-- Modified by Liuwx 2011-08-11 -->
				<pre style="font-size: 16.0pt;font:bold;line-height: 150%;font-family: 宋体"><%=ncontent%></pre>
			<!-- Modification finished -->
		</div>
		<%
			if (xb != null && xb != "") {
		%>
		<table border="0">
			<tr>
				<br>
				<br>
				<p class="MsoTitle" align="left"
					style="font-size: 14.0pt; text-align: left; margin: 0px; margin-top: 0px;">
				<hr
					style="text-align: left; margin-left =-10px; border: 1px dashed; height: 1px; width: 130;">
				<span id="title" style="font-family: 宋体;font-size: 15.0pt"><strong>续报内容:</strong></span>
				</p>
			</tr>
			<tr>
				<p class="MsoNormal">
				<!-- Modified by Liuwx 2011-08-11 -->
					<pre style="font-size: 14.0pt;font-family: 宋体"><%=xb%></pre>
				<!-- Modification finished -->
				</p>
			</tr>
		</table>
		<%
			}
		%>
		<%
			if (acontent != null && acontent != "") {
		%>
		<table border="0">
			<tr>
				<br>
				<br>
				<p class="MsoNormal" align="left"
					style="font-size: 14.0pt; text-align: left; margin: 0px; margin-top: 0px;">
				<hr
					style="text-align: left; margin-left =-10px; border: 1px dashed; height: 1px; width: 130;">
				<span style="font-family: 宋体; font-size: 15.0pt"><strong>收文单位办理情况：</strong></span>
				</p>
			<tr>
				<p class="MsoNormal" style="text-indent: 2em;">
				<!-- Modified by Liuwx 2011-08-11 -->
					<pre style="font-size: 16.0pt;font: bold;font-family:宋体 "><%=acontent%></pre>
				<!-- Modification finished -->					
				</p>
			</tr>
		</table>
		<%
			}
		%>

	</body>

</html>
