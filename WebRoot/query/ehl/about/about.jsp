<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ include file="../../../sm/Message.oni"%>
<%String xmlURL = request.getParameter("versionXmlUrl");
%>

	<!--
	    * author：zhaoyu
	    * date:   2008-08-26
	    * version:
	-->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>关于...</title>
		<Link Rel="STYLESHEET" Href="../../../sm/css/pagetag/pagetag.css" Type="text/css">
		<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
		<script type="text/javascript" src="../../../sm/js/sysmanage/head.js"></script>
	    <style type="text/css">
        .tagContent { background:url(imgs/bg.gif) repeat-x; height:240; padding:10px; border:0px; color:#474747; width:100; display:none}
        .STYLE2 {font-family: "宋体"}
        </style>
</head>
<body align="center" onLoad ="createHistory('<%=xmlURL%>');">
    <div style="height:1px"></div>
	<ul id="tags" align = "center">
		<li class='selectTag'><a href='#' onClick="selectTag('info',this,'../../../sm/image/sysoption')"><font style = font-size:12px;>版本信息</font></a></li>
		<li><a href='#' onClick="selectTag('history',this,'../../../sm/image/sysoption')"><font style = font-size:12px;>版本历史</font></a></li>
    </ul>
   <div id="tagContent"  align = "center" >
		<div id="info" class='tagContent selectTag'>
		     <table width=390 height="25%" style=" border: 1px solid #7F9DB9;" align='center' cellpadding='0' cellspacing='0' >
				<tr>
				   <td width="4%" align='right'>&nbsp;</td>
				   <td width="16%" align="right"><img src="../../../sm/image/about/aboutProduct.jpg" width="60" height="45"/></td>
				   <td width="76%" align='right'>
				   <span class="STYLE2">综合查询系统</span>
				   <span class="STYLE2"><br><br>Version 1.02&nbsp;版权所有（C）2008-2010</span></td>
				   <td width="4%" align='right'>&nbsp;</td>
				</tr>		
		    </table>
		   	<table width=390 height="2%" align='center' cellpadding='0' cellspacing='0' >
				<tr>
				   <td align='center'></td>
				</tr>		
		   </table>
		   <table width=390 height="40%"  style=" border: 1px solid #7F9DB9;" align='center' cellpadding='0' cellspacing='0'>
	              <tr>
	                <td width="32%" align="center"><img src="../../../sm/image/about/aboutCorp.jpg" width="84" height="29"></td>
	                <td width="68%" align='left'>
					  <span class="STYLE2">北京易华录信息技术股份有限公司 </span><br>
					  <span class="STYLE2">电&nbsp;话：010-82526889</span>  <br>
				      <span class="STYLE2">主&nbsp;页：http://www.ehualu.com</span> </td>
	              </tr>
	           </table>
	        <table width=390 height="15%" align='center' cellpadding='0' cellspacing='0'>
	              <tr>
	                <td align='left'><br><br>
					  <span class="STYLE2">警告：本计算机程序受版权法保护，未经许可复制或分发本软件，或本软件的一部分，可能引起严重的民事或刑事责任。</span>			    </td>
	              </tr>
	       </table>
		 </div> 
		 <div id="history" class='tagContent'>
			<table style=" border: 1px solid #7F9DB9;" align='center' cellpadding='0' cellspacing='0' bordercolor='#BFBFBF'>
              <tr>
                <td align='center'id="ver"></td>
              </tr>
            </table>
		</div> 		
   </div>
</body>
</html>
