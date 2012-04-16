<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="java.io.File" %>
<%@page import="com.jspsmart.upload.*"%>
<%@page import="com.ehl.dispatch.cdispatch.action.Jspsmart"%> 
<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<jsp:useBean id="myBean" scope="page" class="com.ehl.dispatch.cdispatch.util.FileAction"/> 
<%@include file="../../Message.oni"%>
<html>
    <head>
        <title>上传文件</title>
    </head>
	<body>
	<%    
  	 	String ywid = request.getParameter("ywid");
  	 	String ywlx = request.getParameter("ywlx");
  	 	String[] fjmss = request.getParameter("fjmss").split("@");
  	 	
  	 	int count = 0;
		try{
			String fileExt = "jpg,gif,jpeg,dmp,GIF,JPG,JPEG,DMP,txt,doc,ppt,xls,pdf,rar,docx,xlsx,pptx,cad,CAD";
			mySmartUpload.initialize(pageContext);
			mySmartUpload.setMaxFileSize(10000000);
			mySmartUpload.setTotalMaxFileSize(100000000);
			mySmartUpload.setAllowedFilesList(fileExt);
			mySmartUpload.upload();
			String fjName,fjPath,fjId;
			String spath = Jspsmart.getPath();
			for (int i = 0; i < mySmartUpload.getFiles().getCount(); i++) {
				SmartFile myFile = mySmartUpload.getFiles().getFile(i);
				fjName = myFile.getFileName();
				fjPath = myFile.getFilePathName();
				if (fjPath != null && new File(fjPath).exists()) {
					if(myBean.doSaveFile(ywid,ywlx,fjmss[i],spath,myFile))
						count++;
				}else{
					System.out.println("文件不存在！");
				}
			}  
		} catch(SmartUploadException e) {
			System.out.println("上传文件出错");
			e.printStackTrace();	                                    
		} catch(Throwable ta) {
			System.out.println("未知错误");
			ta.printStackTrace();
		}
	%>
   </body>
</html>