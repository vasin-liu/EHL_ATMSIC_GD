<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String videoId = request.getParameter("VIDEOID");
	String videoURL = com.appframe.common.Setting.getString("serviceURL");
	String codebase = com.appframe.common.Setting.getString("cabPackageURL");
	
	String ip = request.getServerName(); 
	String hostPort = String.valueOf(request.getServerPort());
	String codebaseUrl = "http://" + ip + ":" + hostPort + codebase;
	//String sessionpode=(String)session.getAttribute(Constants.PCODE_KEY);
%>

<html>
	<head>
		<title>查看视频</title>
		<script LANGUAGE="javascript">
			var videoId = '<%=videoId%>';
			var videoURL = '<%=videoURL%>';
			
			var uname = '';
			function showVideo(){
		        var obj = document.getElementById("EhlSingleView");
		        obj.ShowView(videoURL,videoId,"admin");
		    }
		    //关闭页面注销视频用户
		    window.onunload = function (){   
				if(event.clientX<0&&event.clientY<0){
				  var obj = document.getElementById("EhlSingleView");
				  obj.exitvideo();
			   }
			}
		</script>
	</head>
	<body onload="showVideo();">
		<div>
		<OBJECT ID="EhlSingleView" WIDTH="900" CLASSID="CLSID:ABDD105A-E830-417F-A0AF-DE5D803E98C1" codebase="<%=codebaseUrl%>" >
		</OBJECT>
		</div>
	</body>
</html>