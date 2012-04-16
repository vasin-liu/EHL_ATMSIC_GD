<%@ page language="java"   pageEncoding="UTF-8"%>
<%@page import="com.ehl.sm.common.Constants" %>
<%
	String errMessUser = (String) session.getAttribute(Constants.ERRMESSUSER_KEY);
	//获取输入用户名或密码时，出现'用户名或密码错误'的session值
	if (errMessUser == null) {
		errMessUser = "";
	}
	session.removeAttribute("errMessUser");//移除session值errMessUser
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>春运信息上报系统用户登录</title>
<link href="./base/css/style1/global.css" rel="stylesheet" type="text/css">
<link href="./base/css/style1/font.css" rel="stylesheet" type="text/css">
<link href="./base/css/style1/link.css" rel="stylesheet" type="text/css">
<link href="./base/css/style1/login.css" rel="stylesheet" type="text/css">
<script src="js/login.js" type=text/javascript></script>
<script language="javascript">
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
	      	
	      	function doLogin()
	      	{
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
    </script>
</head>
<body>
	<form id="userform" name="userform" action="gzzd.userlogin.login.d" method="post" onSubmit="return login();">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
	    	<td height="607" align="center">
	    		<table width="974" border="0" cellspacing="0" cellpadding="0">
					<tr>
					  <td height="331" class="loginTopBack logoText">广东省交通警察春运信息上报系统</td>
					</tr>
					<tr>
				        <td height="116">
				        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				          		<tr>
						            <td width="393" height="116" class="loginLeftBack">&nbsp;</td>
						            <td width="174">
						            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				              				<tr>
				                				<td height="81" class="loginUserBack">
				                					<table width="100%" border="0" cellspacing="0" cellpadding="0">
				                  						<tr>
										                	<td width="24%" class="cbfdbeb" style="font-size:14px;">用户：</td>
										                  	<td width="76%" height="25" >
										                  		<input id="pname" name="pname" type="text" name="textfield" class="loginListText">
										                  	</td>
									                  	</tr>
										                <tr>
										                  	<td class="cbfdbeb" style="font-size:14px;">密码：</td>
										                    <td height="25"><input type="password" id="pwd" name="pwd" class="loginListText"></td>
										                </tr>
										                <tr>
												        	<td colspan=2>
												        		<span style="align:center;color:red;font-size:9pt" >
												        			&nbsp;&nbsp;&nbsp;&nbsp;<%=errMessUser%>
												        		</span>
															</td>
														</tr>
									                </table>
								                </td>
							              	</tr>
							            	<tr>
	                							<td height="35">
		                							<table width="100%" border="0" cellspacing="0" cellpadding="0">
									                	<tr>
										                    <td width="50" height="35" class="loginbar">&nbsp;</td>
										                    <td width="46">	
																<div class="btnSearch">
																    <a href="#" onclick="javascript:doLogin();">
																    	<span class="lbl">登录</span>
																    </a>     
																</div>                    
															</td>
										                    <td width="45">	
																<div class="btnSearch">
										    						<a href="#" onClick="javascript:reset();">
										    							<span class="lbl">重置</span>
										    						</a>     
										    					</div>                    
										    				</td>
										                	<td width="33" class="loginBtnrBack">&nbsp;</td>
									                	</tr>
									            	</table>
									            </td>
						              		</tr>
						             	</table>
					             	</td>
						            <td height="116" class="loginRightBack">&nbsp;</td>
					          	</tr>
					        </table>
				        </td>
			      	</tr>
				    <tr>
			        	<td height="160" class="loginBottomBack">&nbsp;</td>
			      	</tr>
	    		</table>
	   		</td>
	  	</tr>
	  	<tr>
	    	<td bgcolor="#02609c">&nbsp;</td>
	  	</tr>
		</table>
	</form>
	<script language="javascript">
		document.userform.pname.focus();
	</script>
	</body>
</html>

