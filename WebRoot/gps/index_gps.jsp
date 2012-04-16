<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统管理主界面</title>
<link href="../base/css/style1/global.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/font.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/header.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/main.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/sidebar.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/navigation.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/link.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../sm/js/sysmanage/head.js" type=text/javascript></SCRIPT>
<SCRIPT src="../base/js/style1/iframe.js" type=text/javascript></SCRIPT>
<SCRIPT src="../js/main.js" type=text/javascript></SCRIPT>
</head>
<body style="padding:0">
<!--内容-->
<!--页面主体部分 START-->
<div id="pagebody">
<!--页面主体左侧 START-->
  <table width="100%">
      <tr>
          <td width="10">
               <div class="sidebar" id="frmTitle">
			  <iframe frameborder="0" id="sidebar" name="sidebar" scrolling="auto" noresize="noresize"  src="lefttree.jsp" style="HEIGHT:521px; VISIBILITY: inherit; WIDTH: 168px; Z-INDEX: 2"  allowtransparency="true" ></iframe>
			  </div>   
          </td>
          <td width="8">
               <div class="switch">
		 	   <div style="HEIGHT: 521px; float:left; background:url(../base/image/cssimg/switch.gif) no-repeat center;" onClick="switchSysBar()">
		        <font style="FONT-SIZE: 12pt; width:9px; CURSOR: default;">
		        <span class="navPoint" id="switchPoint" title="切换">3</span>
		        </font></div>
				</div>
          </td>
          <td>
              <div class="mainbody" style="width:100%">
			    <iframe frameborder="0" id="moduletarget" name="moduletarget"   scrolling="auto" noresize="noresize"  src="../gps/ehl/instant/monitor.jsp" style="height:540px; *height:540px !important; *height:522px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1;padding-top:6px;"  allowtransparency="true"> </iframe>
			  </div>
          </td>
      </tr>
  </table>
  
   
  
</div>
</body>
</html>
<script type="text/javascript">
if(window.screen.width<'1024'){switchSysBar()}
</script>
