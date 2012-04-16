<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.ehl.sm.common.Constants" %>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@page import="com.appframe.data.sql.DBHandler"%>
<%@page import="com.ehl.dispatch.common.DispatchUtil"%>
<%@page import="com.appframe.common.Setting"%>
<%@page import="com.ehl.sm.base.Constant"%>
<% 
	String userName = (String)session.getAttribute(Constants.PNAME_KEY);
   	String loginModel = request.getParameter("loginModel");//登录模式:"GD"-九宫格模式 ""-默认模式
   	String sysId = request.getParameter("sysId");//登录系统编号
   	String sysName = request.getParameter("sysName");//登录系统编号
   	String homePage = request.getParameter("homePage");//登录系统编号
   	String title = request.getParameter("title");//网站标题
   	String systems = request.getParameter("systems");//系统权限
   	String personName = request.getParameter("personName");//登录人员
   	String deptName = request.getParameter("deptName");//人员所属机构
   	String sCode = (String)session.getAttribute("sCode");
   	if(title == null){
       	title = "";
   	}
   	//Modify by Xiayx 2011-11-5
   	//设置常用信息
	String jgid = Constant.nvl(session.getAttribute(Constant.JGID_VAR));//单位编码
	String jgmc = Constant.nvl(session.getAttribute(Constant.JGMC_VAR));//机构名称
	String jgcc = Constant.nvl(session.getAttribute(Constant.JGCC_VAR));
	String jglx = Constant.nvl(session.getAttribute(Constant.JGLX_VAR));
	String zbrxm = Constant.nvl(session.getAttribute(Constant.ZBRXM_VAR));
	String appid = Constant.nvl(session.getAttribute(Constant.APPID_VAR));
	String svurl = Constant.nvl(session.getAttribute(Constant.SERVER_URL_VAR));
	String appurl = Constant.nvl(session.getAttribute(Constant.APP_URL_VAR));
	String octime = Constant.getCurrentTime(false).substring(0,16);			
	//Modification finished
	
	//值班日期
	String dutyBTime = Setting.getString("POLICE_DUTY_BEGIN_TIME");
%>
<html>
	<head>
		<title><%=title %></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="css/atmsic.css" rel="stylesheet" type="text/css" />
	    <link href="general/css/jqgrid/jquery-ui-1.8.2.custom.css" rel="stylesheet" type="text/css" />
		<link href="notices/css/notices_style.css" rel="stylesheet" type="text/css" />
		<link href="util/jquery/colorbox/colorbox.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="util/jquery/jquery-1.7.1.min.js"></script>
	    <script type="text/javascript" src="sm/js/common/prototype.js"></script>
	    <script type="text/javascript" src="sm/js/common/global.js"></script>
	    <script type="text/javascript" src="sm/js/user/UserManage.js"></script>
		<script type="text/javascript" src="sm/js/sysmanage/head.js"></script>
		<script type="text/javascript" src="js/main.js"></script>
		<script type="text/javascript" src="${contextPath}dispatch/js/ccommon/privilege.js"></script>
		<script type="text/javascript" src="loginstate/js/SessionKeep.js"></script>
	    <script type="text/javascript" src="util/jquery/colorbox/jquery.colorbox-min.js"></script>
	    <script type="text/javascript" src="util/jquery/blockui/jquery.blockUI.js"></script>
	    <script type="text/javascript" src="notices/js/notices.js"></script>
   	    <script language="javascript"> 
   	    	var jglx = "<%=jglx%>";
   	    	//监视浏览器的关闭动作，然后调用doLogout()向服务器发送一个请求---by ldq
			window.onunload=function (){   
				if(event.clientX<0&&event.clientY<0){ 
			   		doLogout();  
			   	}
			}
		  	//定义超连接的样式  2009-1-14（wyl）
		 	function tabit(id,cid){
		    	if(subsystemArray){
		        	for(var i = 0; i < subsystemArray.length; i++){
		            	subsystemArray[i].className="taboff";   
		        	}
		    	}
		    	id.className="tabon";  
			}
  		</script> 
  		<style type="text/css">
  			.table {border:1px solid #A5D1EC;}
  			.titleTopBack{ 
			     height:22px; 
				 background:url(base/image/cssimg/table/bg.gif) repeat-x; 
				 line-height:22px; 
				 text-align:center;
			}
			.td_font {font-size: 12px;}
			.td_r_b{ 
			     border-bottom:1px solid #b5d6e6; 
				 border-right:1px solid #b5d6e6; 
				 text-align:center;
			}
		</style>
	</head>
<body style="overflow-y : visible;" onload="adjustHeight('_main',220);loadSysMenu('<%=userName%>','<%=loginModel%>','<%=sysId%>','<%=sysName%>','<%=homePage%>','<%=systems %>','<%=personName %>','<%= deptName%>');" >
<div align="center">
	 <div class="head" id="head" width="100%" >
	     <table width="100%" cellSpacing=0 cellPadding=0 heigth="95%" >
	         <tr>
	            <td id="currentLogin2" align="left" style="font-size:25;color:white">
	               <B></B>
				</td>
				<td id="currentLogin" align="right"></td>
			</tr>
		</table>
	</div>
    <div class="nav" id="alist" width="100%" > 
			<ul id="navg" >
				<li>
					<a href="#" style="width: 180px"></a>
					<ul >
						<li><a href="#" style=" width: 180px;"></a></li>
					</ul>
				</li>
		 </ul>
	</div>         
	<div class="body" id="bodyid" border="0" >
		<iframe width="100%" height='530'  scrolling="no" align="middle" id="_main" name="_main" src="" ></iframe>
	</div>           
</div>
<input type="hidden" id="sCode" value="<%=sCode %>">
<input type="hidden" id="sysid" value="<%=sysId %>" />
<input id="jgid" type="hidden" value="<%=jgid %>" />
<input id="appid" type="hidden" value="<%=appid %>" />
<input id="dutyBTime" type="hidden" value="<%=dutyBTime %>" />
<input id="zbrxm" type="hidden" value="<%=zbrxm%>" />
<!-- 基本信息 -->
<input id="cjgid" type="hidden" value="<%=jgid%>"/>
<input id="cjgmc" type="hidden" value="<%=jgmc%>"/>
<input id="cjglx" type="hidden" value="<%=jglx%>"/>
<input id="cjgcc" type="hidden" value="<%=jgcc%>"/>
<input id="cpname" type="hidden" value="<%=zbrxm%>"/>
<input id="appid" type="hidden" value="<%=appid%>"/>
<input id="ctime" type="hidden" value="<%=octime%>"/>
<input id="svurl" type="hidden" value="<%=svurl%>"/>
<input id="appurl" type="hidden" value="<%=appurl%>"/>

<% 
	Object loginName = session.getAttribute(Constants.PNAME_KEY); 
   	if(loginName != null){
%>
<input id=loginName type="hidden" value="<%=loginName%>"/>
<div class="float_layer" id="toolbar_float_layer">
</div>
<script type="text/javascript" src="dispatch/js/login/prompt.js"></script>
<script type="text/javascript" src="dispatch/js/ccommon/Flow.js"></script>
<script type="text/javascript">
	var appid = $("appid").value;
	if(appid == "1001"){//值班室用户应用场景
		toolbarDiv();
		setTimeout('ctrlDiv()',1000);
		recordTime(60);
	}
</script>
<%
   }
%>
</body>
</html>