<%@ page language="java" pageEncoding="UTF-8" session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@page import="com.ehl.sm.common.Constants" %>
<%
	String errMessUser = (String) session.getAttribute(Constants.ERRMESSUSER_KEY);
	//获取输入用户名或密码时，出现'用户名或密码错误'的session值
	if (errMessUser == null) {
		errMessUser = "";
	}
	session.removeAttribute("errMessUser");//移除session值errMessUser
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>交通管理综合信息中心</title>
		<style>
			*{ padding:0; margin:0; list-style:none;}
			body{ background:url(image/login/bg.jpg) top center no-repeat;}
			.login_bg{ width:688px; height:398px; position:absolute; left:50%; top:50%; margin-left:-330px; margin-top:-180px;z-index:1}
			.login{ width:688px; height:398px; position:absolute; left:50%; top:50%; margin-left:-330px; margin-top:-180px; z-index:2; font-size:12px;}
			.copy{ font-size:14px; color:#124c8c; width:280px; height:16px; right:38px; bottom:55px; position:absolute;}
			.login dl{ width:240px; height:200px;  position:absolute; left:350px; top:90px; }
			.input_k{ border:#cbcbcb solid 1px; width:176px; height:20px; line-height:20px;}
			
			.button1{ background:url(image/login/button.png) no-repeat; width:64px; height:24px; line-height:24px; color:#fff; text-align:center; border:none; margin-right:32px;}
			.button2{ background:url(image/login/button.png) no-repeat; width:64px; height:24px; line-height:24px; color:#fff; text-align:center; border:none;}
			.login dl dd{ padding-left:63px;}
			.login span{ width:50px; float:left; line-height:24px;}
			.login h1{ position:absolute; top:-53px; left:75px;}
		</style>
		<script language="JavaScript">
			/**
			 * @说明:字符串去掉前后空格
			 */
			String.prototype.trim = function(){
				var matches = this.match(/^[ \t\n\r]+/);
				var prefixLength = (matches == null) ? 0:matches[0].length;
				matches = this.match(/[ \t\r\n]+$/);
				var suffixLength =  (matches == null) ? 0:matches[0].length;
				return this.slice( prefixLength,this.length - suffixLength);
			}
	        //重置
	        function restart(){
		        var pname = document.getElementById("pname");
		        var pwd = document.getElementById("pwd");
		        pname.value = "";
		        pwd.value = "";
		        pname.focus();
	       	} 
	       	
	       	function login(){
	       		document.getElementById('pname').value = document.getElementById('pname').value.trim();
	         	var pname= document.getElementById('pname').value ;
	         	
	         	document.getElementById('pwd').value = document.getElementById('pwd').value.trim();
	         	var pwd= document.getElementById('pwd').value ;
	         	 
	　        	if( "" == pname ){
					alert("请输入用户名！");
					document.getElementById('pname').focus();
					return false;
	　          	}
	 	 		if( "" == pwd ){
             		alert("请输入密码！");
             		document.getElementById('pwd').focus();
             		return false;
　         		}
	            return true;
	      	}
	      	
	      	function doLogin(){
	      		if(login()){
		      				userform.submit();
		      	}
	      	}
	      	
	      	window.onload = function(){
	      		document.userform.pname.focus();
	      		document.userform.onkeydown = function(){
	      			if(event.keyCode==13 || event.keyCode == 32){
		      			if(login()){
		      				userform.submit();
		      			}
	      			}
	      		};
	      	};
			// correctly handle PNG transparency in Win IE 5.5 & 6.
			function correctPNG(){
				var arVersion = navigator.appVersion.split("MSIE")
			    var version = parseFloat(arVersion[1])
			    if ((version >= 5.5) && (document.body.filters)){
			       for(var j=0; j<document.images.length; j++){
			           var img = document.images[j]
			           var imgName = img.src.toUpperCase()
			           if (imgName.substring(imgName.length-3, imgName.length) == "PNG"){
			             var imgID = (img.id) ? "id='" + img.id + "' " : ""
			             var imgClass = (img.className) ? "class='" + img.className + "' " : ""
			             var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' "
			             var imgStyle = "display:inline-block;" + img.style.cssText 
			             if (img.align == "left") imgStyle = "float:left;" + imgStyle
			             if (img.align == "right") imgStyle = "float:right;" + imgStyle
			             if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle
			             var strNewHTML = "<span " + imgID + imgClass + imgTitle
			             + " style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";"
			             + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
			             + "(src=\'" + img.src + "\', sizingMethod='scale');\"></span>" 
			             img.outerHTML = strNewHTML
			             j = j-1
			           }
			       }
			     }    
			}
			window.attachEvent("onload", correctPNG);
		</script>
	</head>

	<body>
		<div class="login_bg">
			<img src="image/login/login_bg.png" />
		</div>
		<div class="login">
			<h1>
				<font  width="586" height="40" ><b>交通管理综合信息中心</b> </font>
			</h1>
			<form id="userform" name="userform" action="login.login.login.d" method="post" onSubmit="return login();">
				<dl>
					<dt>
						<span>用户名：</span>
						<input name="pname" type="text" class="input_k" />
					</dt>
					<dt style=" padding-top:46px; margin-bottom:57px;">
						<span>密 &nbsp;码：</span>
						<input name="pwd" type="password" class="input_k" />
					</dt>
					<font color="red"><%=errMessUser%></font>
					<dt>
						<input name="" type="button" class="button1" value="登 录" onclick="javascript:doLogin();"/>
						<input name="" type="button"  class="button1" value="重 置" onClick="javascript:reset();"/>
					</dt>
				</dl>
			</form>
			<div class="copy">技术支持：北京易华录信息技术股份有限公司</div>
		</div>
		<script language="javascript">
			document.userform.pname.focus();
		</script>
	</body>
</html>

