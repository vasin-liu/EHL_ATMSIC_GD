<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.jspsmart.upload.*"%>
<%@page import="com.ehl.dispatch.cdispatch.action.Jspsmart"%>
<%@include file="../../Message.oni"%>
<html>
	<head>
		<title>文件下载</title>
	</head>
	<body>
		<%
			request.setCharacterEncoding("UTF-8");
			String wz = request.getParameter("wz");
			String mc = request.getParameter("mc");
			System.out.println("文件：" + wz);
			System.out.println("下载后：" + mc);
			try {
				response.reset();
			    out.clear();
			    out=pageContext.pushBody();
				SmartUpload su = new SmartUpload();
				su.initialize(pageContext);
				su.setContentDisposition(null);
				Jspsmart js = new Jspsmart();
				su.downloadFile(js.getPath() + wz,null,mc);
				out.clear();
				out = pageContext.pushBody();
				System.out.println("执行完毕");
				response.getOutputStream().close();
			} catch (java.io.IOException e) {
				System.out.println("下载文件出错"+e);
				e.getMessage();
			} catch (Throwable ta) {
				System.out.println("下载文件出错"+ta);
			}%>
	</body>
</html>