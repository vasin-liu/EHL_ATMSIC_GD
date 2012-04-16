<%@page language="java" import="java.util.*" pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%
	String DVRIP = StringHelper.obj2str(request.getParameter("DVRIP"),"");
	String DVRPort = StringHelper.obj2str(request.getParameter("DVRPort"),"");
	String DVRLoginUser = StringHelper.obj2str(request.getParameter("DVRLoginUser"),"");	
	String DVRLoginPWD = StringHelper.obj2str(request.getParameter("DVRLoginPWD"),"");	
	String channel = StringHelper.obj2str(request.getParameter("channel"),"");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
  	<head>
		<title>视频播放窗口</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	    <script language="javascript" type="text/javascript">
	    	function init(){
	    		var clist = '<%=channel%>';
	    		var strs = new Array();
	    		if(clist){
	    			strs = clist.split(",");
					if(strs){
						var inner = "";
						for(var i=0;i<strs.length;i++){
							inner += "<input type='radio' value='"+strs[i].substring(strs[i].length-1)+"' name='cmode'/>"+strs[i];
						}
						var obj = document.getElementById("list");
						if(obj){
							obj.innerHTML = inner;
						}
						
					}
	    		}
	    	}
	        function showView() {
	        	var radios = document.all.cmode;
	        	var channel = "";
	        	for(var i = 0; i<radios.length ; i++){
					if(radios[i].checked == true){
						channel = radios[i].value;
						break;
					}
				}
				if(channel == ""){
					alert("请选择视频通道！");
					return;
				}
	        	//IP,PORT,USER,PWD,频道号
	            CctvViewer.showView(<%=DVRIP%>,<%=DVRPort%>,<%=DVRLoginUser%>,<%=DVRLoginPWD%>, channel);
	        }
	    </script>
	    <style type="text/css">
	        body{
	            font-family: Calibri;
	        }
	        #CSActiveX{
	            height: 96px;
	            width: 491px;
	        }
	    </style>
	</head>
<body onload="init();">
    <div id='list' align="center" style ="border-style :solid ;border-width :1px;border-color:#BFBFBF;font-size:10pt;"></div>
    <input type="button" name="showView" value="浏  览" onclick="showView()" />
    <p></p>
    <object id="CctvViewer" classid="CLSID:012566B8-3920-4995-AAD8-A4E738D263D0"></object>
</body>
</html>