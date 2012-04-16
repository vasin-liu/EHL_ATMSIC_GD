<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="com.ehl.prmplan.util.BlobUtil"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.util.Hashtable"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>blob to file</title>
	</head>
	<%
		response.setContentType("application/x-octetstream;charset=gb2312");     
		OutputStream outStream = response.getOutputStream();
		Hashtable<String, Object> blobInfo = new Hashtable<String, Object>();
		try {
			String id = StringHelper.obj2str(request.getParameter("fID"),"");
			
			blobInfo = new BlobUtil().getBLOB(id);
			String fileName = StringHelper.obj2str(blobInfo.get("fName"),"");
			fileName = new String(fileName.getBytes("GBK"),"iso-8859-1");   
			response.setContentType("application/x-octetstream;charset=gb2312");   
			
			byte[] bBLOB = BlobUtil.ConvertBLOBtoStream(blobInfo.get("fObj"));
			   
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
			outStream.write(bBLOB);

			outStream.close();
			out.clear();
			out = pageContext.pushBody();
		} catch (Exception e) {
			outStream.close();
		}
	%>
	<body></body>
</html>
