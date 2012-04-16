<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>综合研判系统_用户登录</title>
<link href="../base/css/style1/global.css" rel="stylesheet" type="text/css">
<link href="../base/css/style1/font.css" rel="stylesheet" type="text/css">
<link href="../base/css/style1/link.css" rel="stylesheet" type="text/css">
<link href="../base/css/style1/login.css" rel="stylesheet" type="text/css">
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
<body onKeyDown="if(event.keyCode==13 || event.keyCode == 32) login();">
<form id="userform" name="userform" action="common.userlogin.login.d" method="post" onSubmit="return login();">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="607" align="center"><table width="974" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="331" class="loginTopBack logoText">广东省交通管理综合信息研判平台</td>
      </tr>
      <tr>
        <td height="116"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="393" height="116" class="loginLeftBack">&nbsp;</td>
            <td width="174"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="81" class="loginUserBack"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="24%" class="cbfdbeb">用户</td>
                    <td width="76%" height="25" ><input id="pname" name="pname" type="text" name="textfield" class="loginListText"></td>
                  </tr>
                  <tr>
                    <td class="cbfdbeb" >密码</td>
                    <td height="25"><input type="password" id="pwd" name="pwd" class="loginListText"></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td height="35"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="50" height="35" class="loginbar">&nbsp;</td>
                    <td width="46">	
					<div class="btnSearch">
    <a href="#" onclick="javascript:login();"><span class="lbl">登录</span></a>     </div>                    </td>
                    <td width="45">	
					 <div class="btnSearch">
    <a href="#" onClick="javascript:reset();"><span class="lbl">重置</span></a>     </div>                    </td>
                    <td width="33" class="loginBtnrBack">&nbsp;</td>
                  </tr>
                </table></td>
              </tr>
             </table></td>
            <td height="116" class="loginRightBack">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="160" class="loginBottomBack">&nbsp;</td>
      </tr>
    </table></td>
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
