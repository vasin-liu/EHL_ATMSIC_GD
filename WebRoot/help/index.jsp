<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.common.*" %>
<%
 	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	
	String path = request.getHeader("host");
	System.out.println(path);
	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String deptname = ""; 	  //部门名称
	String pname = ""; 		  //姓名
	String uname = ""; 		  //帐号
	String pid = ""; 		  //办公电话
	String phone = ""; 		  //办公电话
	String mobilephone = "";  //手机
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		pname = (String)prop.get("NAME");
		uname = (String)prop.get("USERNAME");
		pid=(String)prop.get("PERSONID");
		phone = (String)prop.get("PHONE");
		mobilephone = (String)prop.get("MOBILEPHONE");
		
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>包头市公安交警支队指挥调度系统</title>

<link href="../base/css/style1/global.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/font.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/header.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/main.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/sidebar.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/navigation.css" rel="stylesheet" type="text/css" />
<link href="../base/css/style1/link.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../base/js/prototype.js"></script>
<script type="text/javascript" src="../dispatch/js/login/login.js"></script>
<SCRIPT src="../base/js/style1/iframe.js" type=text/javascript></SCRIPT>

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
			// href+='<font style="text-align: right;padding-right:10px"><a href="#" style="color: #FFFFFF" onclick="doLogout(\'header\')">注销</a></font> ';				
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


<body style="margin: 0" onload="">
	<table style="border: 0; width: 100%; height: 100%" cellspacing="0"
		cellpadding="0">
		<tr>
			<td align=center>
				<table style="border: 0; width: 1001; height: 100%" cellspacing="0"
					cellpadding="0">
					<tr style="height: 76px; background-color: blue;">
						<td  valign="middle">
							<table	style="width: 100%; "cellspacing="0" cellpadding="0">
								<tr style="background: url(../dispatch/images/top_head.gif) no-repeat; height: 57px; filter: progid :   DXImageTransform .   Microsoft .   AlphaImageLoader(src =   '../dispatch/images/top_head.gif', sizingMethod = 'scale');">
									<td>
										&nbsp;<span style="text-align: left;padding-left:860px;font: 12;color: #FFFFFF;font-weight:bolder;">当前用户：<%=pname %></span><b>
										&nbsp;<span style="text-align: left;padding-left:860px;font: 12;color: #FFFFFF;font-weight:bolder;">当前单位：<%=deptname %></span><b>
										&nbsp;<span style="text-align: left;padding-left:860px;font: 12;color: #FFFFFF;font-weight:bolder;"><a href="#" style="color: #FFFFFF" onclick="showPage();">修改密码</a>｜<a href="#" style="color: #FFFFFF" onclick="doLogout('header')">注销</a></span> 
									</td>
								</tr>
								<tr style="width: 100%; line-height: 23px;background: url(../dispatch/images/top_head01.gif) no-repeat; filter: progid :   DXImageTransform .   Microsoft .   AlphaImageLoader(src = '../dispatch/images/top_head01.gif', sizingMethod = 'scale');">
									<td style="margin: 0; valign: bottom; line-height: 19px"
										valign="bottom">
										<div style="line-height: 19px; font-size: 12px;">
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
						<td style="border-left: 1 #000000 solid; border-top: 1 #000000 solid; border-right: 1 #000000 solid;">
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
										    <iframe frameborder="0" id="moduletarget" name="moduletarget"   scrolling="auto" noresize="noresize"  src="rightLogo.jsp" style="height:540px; *height:540px !important; *height:522px; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1;padding-top:6px;"  allowtransparency="true"> </iframe>
										  </div>
							          </td>
							      </tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>