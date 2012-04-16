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
		<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/global.js"></script>	
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
	    		//默认时间
			    var startDate = getSysdate(60,false);//获取当前月份第一天日期
			
				var endDate = getSysdate(0,false);
				var STARTTIME = document.getElementById("startTime");
				var ENDTIME = document.getElementById("endTime");
				STARTTIME.value = startDate;
				ENDTIME.value = endDate;
	    	}
	         function showReplayView(){
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
				var startTime = document.getElementById("startTime").value;
				var entTime = document.getElementById("endTime").value;
				var sYear,sMou,sDay,sHour,sMin,sSon,eYear,eMou,eDay,eHour,eMin,eSon;
				if(startTime>entTime){
					alert("开始时间大于结束时间！");
					return;
				}else{
					sYear=startTime.substring(0,4);
					sMou=startTime.substring(5,7);
					sDay=startTime.substring(8,10);
					sHour=startTime.substring(11,13);
					sMin=startTime.substring(14,16);
					sSon=00;
					
					eYear=entTime.substring(0,4);
					eMou=entTime.substring(5,7);
					eDay=entTime.substring(8,10);
					eHour=entTime.substring(11,13);
					eMin=entTime.substring(14,16);
					eSon=00;
				}
	        	//IP,PORT,USER,PWD,频道号
	            CctvViewer.showReplayView(<%=DVRIP%>,<%=DVRPort%>,<%=DVRLoginUser%>,<%=DVRLoginPWD%>, channel,sYear,sMou,sDay,sHour,sMin,sSon,eYear,eMou,eDay,eHour,eMin,eSon);
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
    <div id ="hourAndDate" align="center" style ="border-style :solid ;border-width :1px;border-color:#BFBFBF;font-size:10pt;">
		起止日期：    
            <input name="text" style="width :170px"  type="text" class="text" id="startTime" onClick="SelectDateTime(this,0);" readonly/>	              
         -	              
            <input name="text2" style="width :170px" type="text" class="text" id="endTime" onClick="SelectDateTime(this,0);" readonly/>	
	</div>
    <div id='list' align="center" style ="border-style :solid ;border-width :1px;border-color:#BFBFBF;font-size:10pt;"></div>
    <input type="button" name="showView" value="按时间回放" onclick="showReplayView()" />
    <p></p>
    <object id="CctvViewer" classid="CLSID:748D03C7-95C8-4c72-AC3D-A589D22041DF"></object>
</body>
</html>