<%@ page language="java" contentType="application/x-msdownload" pageEncoding="UTF-8"%>
<%@ include file="../../Message.oni"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.URLEncoder"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
     response.reset();
     String fileName = request.getParameter("fileName");
     response.setContentType("application/x-download");  
     String realPath = request.getSession().getServletContext().getRealPath(
				"/");
		realPath = realPath.substring(0, realPath.length() - 1);
		int pathIndex = realPath.lastIndexOf("\\");
		realPath = realPath.substring(0, pathIndex + 1);
		String uploadPath = realPath;
      String filedownload = uploadPath+fileName;  
      int index = fileName.lastIndexOf("/") ;
      fileName = fileName.substring(index+1,fileName.length()) ;
   String filedisplay = URLEncoder.encode(fileName,"UTF-8");  
   filedisplay = new String( fileName.getBytes("gb2312"), "ISO8859-1");
   response.addHeader("Content-Disposition","attachment;filename=" + filedisplay);  
   
   OutputStream outp = null;  
   FileInputStream in = null;  
    try  
   {  
   outp = response.getOutputStream();  
   in = new FileInputStream(filedownload);  
   
   byte[] b = new byte[1024];  
   int i = 0;  
   
   while((i = in.read(b)) > 0)  
   {  
   outp.write(b, 0, i);  
   }  
 //    
 outp.flush();  
 //要加以下两句话，否则会报错  
 //java.lang.IllegalStateException: getOutputStream() has already been called for //this response    
 out.clear();  
 out = pageContext.pushBody();  
 }  
   catch(Exception e)  
   {  
  System.out.println("Error!");  
   e.printStackTrace();  
   }  
   finally  
   {  
   if(in != null)  
   {  
   in.close();  
   in = null;  
   }  
 //这里不能关闭    
 //if(outp != null)  
   //{  
   //outp.close();  
  //outp = null;  
   //}  
   }  
 %>>
<html>
  <head>
    
    <title>文件下载</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <script type="text/javascript">
      window.close();
     </script>
  </body>
</html>
