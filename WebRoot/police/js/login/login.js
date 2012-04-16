  /** 登录验证,在用户开始登录时进行的前端验证 */
	       function login(){
	          var pname=document.userform.pname.value;
	          var pwd=document.userform.pwd.value;

	　         if(pname==""&&pname!=null){
	             alert("用户名不能为空");
	             document.userform.pname.focus();
	             return false;
	　          }
			 if(pwd==""&&pname!=null){
	             alert("请输入用户密码");
	             document.userform.pwd.focus();
	             return false;
	　          }
	         document.userform.submmit;
	      }
/**
 * @功能：重置

 * @作者：gll
 * @创建日期：

 */	      
function reset(){
	document.userform.pname.value="";
	document.userform.pwd.value="";
	 document.userform.pname.focus();

}


function restart(){
    var pname = document.getElementById("pname");
    var pwd = document.getElementById("pwd");
    pname.value = "";
    pwd.value = "";
    document.userform.pname.focus();
} 