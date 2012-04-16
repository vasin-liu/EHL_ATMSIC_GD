<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.ehl.dispatch.cdispatch.util.*" %>
<%

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>包头市公安交警支队指挥调度系统</title>
<script type="text/javascript" src="sm/js/common/prototype.js"></script>
<script type="text/javascript" src="dispatch/js/login/login.js"></script>
</head>

<script type="text/javascript">
		function privCtrls(){
			var url = "common.treectrl.load.d?showtree=57";
			url = encodeURI(url);
			var params ="";
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:createTools});		
		}
		function createTools(xmlHttp){
		  	var xmlDoc = xmlHttp.responseXML;		  	
			var root = xmlDoc.documentElement.childNodes;
			var href="";
			if(root!=null){
				href+="｜";
				for (var i = 0; i < root.length; i++) {		
					var rid=root[i].getAttribute("id");
					var rtxt=root[i].getAttribute("text");
					href+="<a href='"+rid+"' id='link_"+rid+"' onclick=initIndex('link_"+rid+"'); style='color: #ffffff;' target='ifMain' name='nagivate'>"+rtxt+"</a>｜";
									
				}	
			}
			document.getElementById("toolsSpan").innerHTML=href;
		}
		function initIndex(id){
		
			var hrefs=document.getElementsByName("nagivate");
			for(var i=0;i<hrefs.length;i++){			
				if(hrefs[i].id==id){
					hrefs[i].style.color="#D0AE1E";
				}else{
					hrefs[i].style.color="#ffffff";
				}
			}
		}		

</script>


<body style="margin: 0" onload="privCtrls();">
	<table style="border: 0; width: 100%; height: 100%" cellspacing="0"
		cellpadding="0">
		<tr>
			<td align=center>
				<table style="border: 0; width: 1001; height: 100%" cellspacing="0"
					cellpadding="0">
					<tr style="height: 76px; background-color: blue;">
						<td   valign="middle">
							<table	style="width: 100%; "cellspacing="0" cellpadding="0">
								<tr style="background: url(dispatch/images/top_head.gif) no-repeat; height: 57px; filter: progid :   DXImageTransform .   Microsoft .   AlphaImageLoader(src =   'dispatch/images/top_head.gif', sizingMethod =   'scale');">
									<td>
										&nbsp;<!-- <div style="text-align: right;padding-right:10px">aaaaaa</div><div style="text-align: right;padding-right:10px"><a href="#" style="color: #FFFFFF" onclick="doLogout('header')">注销</a></div> -->
									</td>
								</tr>
								<tr style="width: 100%; line-height: 23px;background: url(dispatch/images/top_head01.gif) no-repeat; filter: progid :   DXImageTransform .   Microsoft .   AlphaImageLoader(src =   'dispatch/images/top_head01.gif', sizingMethod =   'scale');">
									<td style="margin: 0; valign: bottom; line-height: 19px"
										valign="bottom">
										<div
											style="line-height: 19px; font-size: 12px;">
											<span style="list-style: none; valign: bottom;" id="toolsSpan">
												&nbsp;&nbsp;&nbsp;&nbsp;  
												<!-- ｜ <a
												href="dispatch/ehl/dictate/dictateDispose.jsp"
												style="color: #ffffff;" target="ifMain">接收指令指示</a> 
											    -->
											 </span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr height="100%">
						<td
							style="border-left: 1 #000000 solid; border-top: 1 #000000 solid; border-right: 1 #000000 solid;">
							<iframe id="ifMain" width="1001px" height="100%"
								style="margin-right: 0" name="ifMain" marginheight=0
								frameborder=0 src="dispatch/ehl/policeWatch/watching.jsp">
							</iframe>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>