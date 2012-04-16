<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.appframe.utils.StringHelper"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>blob to file</title>
	</head>
	<%
		ServletOutputStream sos = response.getOutputStream();
		InputStream is = null;
		File file = null;
		try {
			String filedisplay = StringHelper.obj2str(request.getParameter("fileName"),"");//下载文件时显示的文件保存名称
			//String filepath = com.appframe.common.Setting.getString("codebaseurl");	;
			//String filepath="http:////10.2.61.38:8081//EHL_CCTV//EhlCctvActiveX.rar";
			String filedownload = StringHelper.obj2str(request.getParameter("file"),"");//"cctv/activex/webrec.cab";
			//即将下载的文件的相对路径
			String filepath=getServletContext().getRealPath(filedownload);
			file = new File(filepath);
			if(file != null && file.length()>0){
				is = new FileInputStream(file);
			}else{
				return;
			}

			response.setContentType("application/x-download");//设置为下载application/x-download
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			response.setHeader("Content-Disposition","attachment;filename=" + filedisplay);

			byte[] data = new byte[2048];
			int len = 0;
			while ((len = is.read(data)) > 0) {
				sos.write(data, 0, len);
			}
			out.clear();  
			out = pageContext.pushBody();
		}catch(IOException ex) { 
			System.out.println("Error!");  
		}catch (Exception e) {
			System.out.println("Error!");  
		}finally{
			sos.flush();
			sos.close();
			is.close();
		}
	%>
	<body></body>
</html>
