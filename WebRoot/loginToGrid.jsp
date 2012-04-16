<%@ page language="java"  pageEncoding="UTF-8"  buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="com.ehl.sm.common.Constants" %>
<%
    String isLogin = request.getParameter("login");
	String errMessUser = (String) session.getAttribute(Constants.ERRMESSUSER_KEY);
	//获取输入用户名或密码时，出现'用户名或密码错误的session值 
	if(errMessUser != null){
		session.removeAttribute(Constants.ERRMESSUSER_KEY);//移除session值errMessUser
	}else{
		errMessUser = "";
	}
	
%>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户登录</title>
		<link href="./base/css/style1/global.css" rel="stylesheet" type="text/css" />
		<link href="./base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="./base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<link href="./base/css/style1/login.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			if(window != top){
				top.location.href = window.location.href;
			}
		</script>
	</head>
	<script language="javascript">
	        /** 登录验证,在用户开始登录时进行的前端验证 */
	        function restart(){
		        var pname = document.getElementById("pname");
		        var pwd = document.getElementById("pwd");
		        pname.value = "";
		        pwd.value = "";
	       } 
	       function login(){
	          var pname=document.userform.pname.value;
	          var pwd=document.userform.pwd.value;
	　         if(pname==""&&pname!=null){
	             alert("用户名不能为空！");
	             document.userform.pname.focus();
	             return false;
	　          }
			 if(pwd==""&&pname!=null){
	             alert("密码不能为空！");
	             document.userform.pname.focus();
	             return false;
	　          }
	            document.userform.submit();
	      }
    </script>
<body onKeyDown="if(event.keyCode==13 || event.keyCode == 32) login();" >
<form id="userform" name="userform" action="login.loginGrid.login.d" method="post" onSubmit="return login();">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="607" align="center"><table width="974" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="331" class="loginTopBack logoText">广东道路交通信息集成与警务协作平台</td>
      </tr>
      <tr>
        <td height="116"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="393" height="116" class="loginLeftBack">&nbsp;</td>
            <td width="174"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="81" class="loginUserBack"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="24%" class="cbfdbeb" Style=""><font color="#ffffff" size="2">用户</font></td>
                    <td width="76%" height="25" ><input id="pname" name="pname" type="text" name="textfield" class="loginListText" value="admin"></td>
                  </tr>
                  <tr>
                    <td class="cbfdbeb" ><font color="#ffffff" size="2">密码</font></td>
                    <td height="25"><input type="password" id="pwd" name="pwd" class="loginListText" value="123456"></td>
                  </tr>
                  <font size='2' color = 'red'> <%= errMessUser %> </font>
                </table></td>
              </tr>
              <tr>
                <td height="35"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="50" height="35" class="loginbar">&nbsp;</td>
                    <td width="46">	
					     <div class="btnSearch">
                             <a href="#" onclick="javascript:login();"><span class="lbl">登录</span></a> 
                         </div>
                    </td>
                    <td width="45">	
					    <div class="btnSearch">
                             <a href="#" onClick="javascript:reset();"><span class="lbl">重置</span></a>
                         </div>         
                    </td>
                    <td width="33" class="loginBtnrBack">&nbsp;</td>
                  </tr>
                </table></td>
              </tr>
             </table></td>
            <td height="116" class="loginRightBack">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr >
        <td height="160" align="left" >
        <div  style="margin-left:220px">
        	<span style="font-size: 13;color:#F0F8FF;"  >
        	使用说明：<br>
			1、登陆用户名是交警队机构编码，初始密码为：123456；<a href="userAndDept.xls">查看各机构用户名列表</a></P>
			2、请各机构初次登陆后，尽快自行修改密码。操作方法：返回登录页，点击“修改密码”按钮；</P>
			3、值班室用户若需更新当前值班人员信息，可点击“交通警情”功能模块后按提示填写；</P>
                        4、在“用户帮助”功能模块可下载使用操作手册，查阅使用方法；</p>
			咨询和技术支持：省厅交管局指挥中心 020-36220800，周工 13827730885。</P>
			　　　　　　　</P>
			</span>
			</div>
		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td bgcolor="#02609c"></td>
  </tr>
</table>
</form>
<script language="javascript">
	document.userform.pname.focus();
	//var widthv = 570;
	//var heightv = 380;
	//var xposition = (screen.availWidth - widthv)/2;
	//var yposition = (screen.availHeight - heightv)/2;
	//var feature = 'height='+heightv+',width='+widthv+',top='+yposition+',left='+xposition+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no';	
	//window.open('tz.html',"",feature);
</script>
</body>
</html>
