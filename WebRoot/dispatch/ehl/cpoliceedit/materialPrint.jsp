<%@ page language="java" import="java.util.*"
	contentType="application/msword; charset=gbk" pageEncoding="UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.sm.base.Constant"%>
<%@page import="com.ehl.util.Print"%>
<%@page import="java.net.URLEncoder"%>
<%
	
	String cujgmc = null, bh = null, time = null, fsdw = null, fszby = null, jsdw = null, jszby = null, title = null, content = null, xb = null, zdcjqk = null, ldps = null, blqk = null, stpsyj = null
	//Modified by Liuwx 2011-08-10
	, lzjl = null;//流转记录
	//Modification finished
	request.setCharacterEncoding("utf-8");
	String displayName = URLEncoder.encode("值班日志","utf-8");
	response.addHeader("Content-Disposition","attachment;filename="+displayName+".doc");
	String id =  request.getParameter("id");
	String rtime = Print.getAlarmTimeFromBase(id);
	String ctime = request.getParameter("ctime");
	String jgid = Constant.nvl(session.getAttribute(Constant.JGID_VAR));
	String snstr = Print.getSerialNumber(application, id, jgid);
	if (snstr != null) {
		cujgmc = Constant.nvl(session.getAttribute(Constant.JGMC_VAR));
		//编号=填报日期+序列号
		bh = Print.getBh(rtime,snstr);
		//日期=接收时间||当前时间，针对总队
		time = Print.toPrintTime(time);
		if (cujgmc == null) {
			cujgmc = StringHelper.obj2str(request.getParameter("cujgmc"), "");
		}
		fsdw = StringHelper.obj2str(request.getParameter("fsdw"), "");
		fszby = StringHelper.obj2str(request.getParameter("fszby"), "");
		jsdw = StringHelper.obj2str(request.getParameter("jsdw"), "");
		jszby = Print.getAlarmPersonFromBase(id,jgid);
		
		title = request.getParameter("title");
		content = request.getParameter("content");
		xb = request.getParameter("xb");
		zdcjqk = request.getParameter("zdcjqk");
		ldps = request.getParameter("ldps");
		blqk = request.getParameter("blqk");
		stpsyj = request.getParameter("stpsyj");
		//Modified by Liuwx 2011-08-10
		lzjl = request.getParameter("lzjl");//流转记录
		//Modification finished
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
			<%
				if (ldps != null) {
			%>
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
			<tr>
			</tr>
			<%
				}
			%>
			<%
				if (ldps != null && blqk != null) {
			%>
			<%
				}
			%>
			<%
				if (blqk != null) {
			%>
			<tr style="height: 5.25pt">
				<td colspan="2" width="338" valign="top"
					style="width: 253.5pt; height: 5.25pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: medium none; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
					<p class="MsoNormal" align="left"
						style="font-size: 15.0pt; text-align: left">
						<span style="font-family: 宋体"><strong>办理情况：</strong></span>
						<!-- Modified by Liuwx 2011-08-11 -->
						<pre style="font-size: 14.0pt;"><%=blqk%></pre>
						<!-- Modification finished -->
					</p>
				</td>
			</tr>
			<%
				}
			%>
			<%
				if (stpsyj != null && !("440000000000".equals(jgid.trim())) ) {
			%>
			<tr style="height: 5.25pt">
				<td colspan="2" width="338" valign="top"
					style="width: 253.5pt; height: 5.25pt; border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: medium none; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
					<p class="MsoNormal" align="left"
						style="font-size: 15.0pt; text-align: left">
						<span style="font-family: 宋体"><strong>省厅批示意见：</strong></span>
						<!-- Modified by Liuwx 2011-08-11 -->
						<pre style="font-size: 14.0pt;"><%=stpsyj%></pre>
						<!-- Modification finished -->
					</p>
				</td>
			</tr>
			<%
				}
			%>
			<%
				if (blqk != null || ldps != null || stpsyj != null) {
			%>
			<tr style="height: 5.25pt">
				<td colspan="2" valign="top"
					style="border-left: medium none; border-right: medium none; border-top: medium none; border-bottom: 1.5pt solid windowtext;">
				</td>
			</tr>
			<%
				}
			%>
		</table>
		<p class="MsoTitle">
			<span id="title" style="font-size: 18.0pt;font-family: 宋体"><%=title%></span>
		</p>
		<p class="MsoNormal">
			<!-- Modified by Liuwx 2011-08-11 -->
			<pre style="font-size: 16.0pt"><%=content%></pre>
			<!-- Modification finished -->
		</p>
		<%
			if (xb != null) {
		%>
		<p class="MsoTitle">
			<span id="title" style="font-size: 15.0pt;font-family: 宋体"><strong>续报内容</strong></span>
		</p>
		<p class="MsoNormal">
			<!-- Modified by Liuwx 2011-08-11 -->
			<pre style="font-size: 14.0pt;"><%=xb%></pre>
			<!-- Modification finished -->
		</p>
		<%
			}
		%>
		<%
			if (zdcjqk != null) {
		%>
		<%--		<hr width="5px" color="black">--%>
		<table>
			<tr>
				<hr
					style="text-align: left; margin-left =-10px; border: 1px dashed; height: 1px; width: 100px;">
				<p class="MsoNormal" style="margin-top: 10px;">
					<span style="font-size: 15.0pt;font-family: 宋体"><strong>支队处置情况：</strong></span>
					<!-- Modified by Liuwx 2011-08-11 -->
					<pre style="font-size: 14.0pt;"><%=zdcjqk%></pre>
					<!-- Modification finished -->
				</p>
			</tr>
		</table>
		<%
			}
		%>
		<%
			if(lzjl != null){
		%>
		<hr
			style="text-align: left; margin-left =-10px; margin-top: 150px; border: 1px dashed; height: 1px; width: 100px;">
		<p class="MsoNormal" style="margin-top: 10px;">
			<span style="font-size: 15.0pt;font-family: 宋体"><strong>流转记录：</strong></span>
		</p>
		<p class="MsoNormal" style="text-indent: 2em;">
			<pre style="font-size: 14.0pt;"><%=lzjl%></pre>
		</p>
		<%
			}
		%>
	</body>

</html>

