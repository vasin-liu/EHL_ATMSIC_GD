  var currentSysId ="";//当前登录系统编号
  var currentSysName ="";//当前登录系统名称
  var currentHomePage ="";//当前登录系统主页
  var G_loginAreaHtml = "";//登录区显示内容
  var G_isOpen = "";//是否在新窗口打开
  var G_atmsicTitle = "";//网站标题
  var G_systemsInfo = new Array();//导航中展示出来的子系统信息
  var G_personName = "";
  var G_deptName = "";
  /*根据配置加载九宫格子
  * xmlUrl-配置文件路径
  * colnum-列数
  */
  var itemImagePath = "image/login/gd/";
  function initPageItems(xmlUrl,colnum){
      var xmlDom = getDom(xmlUrl);
      var container = document.getElementById("itemTD");
      var titleTd = document.getElementById("titleTd");
      var atmsicTitle = xmlDom.getElementsByTagName("atmsictitle")[0].text;
      titleTd.innerHTML = atmsicTitle;
      G_atmsicTitle = atmsicTitle;
      var systems = xmlDom.getElementsByTagName("item");
      var htmlArray = new Array();
      var perTrImageArray = new Array();
      var perTrLinkArray = new Array();
      var sysid  = "";
      var sysname = "";
      var homepage = "";
      var isdisplay = "";
      var openNewPage = "";
      var image = "";
      var tdCount =1;
      htmlArray.push("<table width='80%' border='0' align='center' cellpadding='0' cellspacing='0'>");
      for(var j = 0; j < systems.length; j ++){
          sysid = systems[j].getAttribute("sysid");
          sysname = systems[j].text;
          homepage = systems[j].getAttribute("homepage");
          sysname = systems[j].text;
          isdisplay = systems[j].getAttribute("isdisplay");
          openNewPage =  systems[j].getAttribute("opennewpage"); 
          image = itemImagePath + systems[j].getAttribute("image"); 
          if(isdisplay != "block"){
              continue;
          }
          if(G_systemsInfo.length != 0){
              G_systemsInfo.push(";");
          }
          G_systemsInfo.push(sysid+","+sysname+","+homepage+","+openNewPage);
	      onclickStr = "intoSysFromGD('"+sysid+"','"+sysname+"','"+homepage+"','"+openNewPage+"')";
	      if(tdCount == colnum || j == (systems.length-1)){//结束一个tr
	          perTrImageArray.push("<td width='25%' align='center'><a href='#' onclick=\""+onclickStr+"\"><img src='"+image+"' width='115' height='113' /></a></td>")
              perTrLinkArray.push("<td class='whitefont'><a href='#'>"+sysname+"</a></td>");
	          perTrImageArray.push("</tr>");
	          perTrLinkArray.push("</tr><br>");
	          tdCount =0;
	          htmlArray.push(perTrImageArray.join(""));
	          htmlArray.push(perTrLinkArray.join(""));
	          perTrImageArray = new Array();
              perTrLinkArray = new Array();
	      }else if(tdCount == 1){//开始一个tr
	          perTrImageArray.push("<tr >");
	          perTrLinkArray.push("<tr height='40'>");
	          perTrImageArray.push("<td width='25%' align='center'><a href='#' onclick=\""+onclickStr+"\"><img src='"+image+"' width='115' height='113' /></a></td>")
              perTrLinkArray.push("<td class='whitefont'><a href='#'>"+sysname+"</a></td>");
	      }else{
	          perTrImageArray.push("<td width='25%' align='center'><a href='#' onclick=\""+onclickStr+"\"><img src='"+image+"' width='115' height='113' /></a></td>")
              perTrLinkArray.push("<td class='whitefont'><a href='#'>"+sysname+"</a></td>");
	      }
	      tdCount ++;
	  }
      htmlArray.push("</table>");
      container.innerHTML = htmlArray.join("");
  }
  /*根据登录情况初始登录区*/
  function initLoginArea(loginInfo){
       if(loginInfo != ""){
           var xmlArray = loginInfo.split(";");
	       var personName = xmlArray[2];
	       var deptName = xmlArray[1].split(",")[1];
	       /*赋值人员姓名机构*/
	       G_personName = personName;
	       G_deptName = deptName;
           var loginArea = document.getElementById("loginTd");
           var loginArray = new Array(); 
	       loginArray.push("<table width='80%' border='0' align='center' cellpadding='0' cellspacing='0' id='loginTab'>");
	       loginArray.push("<tr>");
		   loginArray.push("<td width='26%' class='whitefont'>人员姓名：");
		   loginArray.push(personName);
		   loginArray.push("</td>");
		   loginArray.push("<td width='25%' class='whitefont'>所属机构：");
		   loginArray.push(deptName);   
		   loginArray.push("</td>");     
		   loginArray.push("<td width='17%'><a href='#' onclick=\"javascript:logoutFromGD();\"><img src='image/login/gd/zx.png' width='94' height='26' border='0' /></a>");        
		   loginArray.push("</td>");
		   loginArray.push("<td width='17%'><a href='#' onclick=\"javascript:updatePwdFromGD();\"><img src='image/login/gd/xgmm.png' width='94' height='26' border='0' /></a>");                
		   loginArray.push("</td>");
		   loginArray.push("</tr>"); 
		   loginArray.push("</table>"); 
		   loginArea.innerHTML = loginArray.join("");
       }
  }
  /*验证进入子系统
  * sysId-系统编号
  * sysName-系统名称
  * homePage-系统首页
  */
  function intoSysFromGD(sysId,sysName,homePage,isOpen){
      currentSysId = sysId;
      currentSysName = sysName;
      currentHomePage = homePage;
      G_isOpen = isOpen;
      var params = "sysId="+sysId +"&sysName=" + sysName+"&isOpen=" + isOpen;
      var url = 'login.login.hasTheSysFun.d';
	  params = encodeURI(params);
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:intoIndexFromGD});
  }
  function intoIndexFromGD(xmlHttp){
      var xmlText = xmlHttp.responseText;
      if(xmlText == "noLogin"){
         alert("未登录或登录已过期,请登录!");
         window.top.location.href = "loginToGrid.jsp";
         return;
      }else if(xmlText == "false"){
         alert("登录用户没有该系统访问权限!");
         return;
      }else if(G_isOpen == "yes"){
         openNewPageFromGD(currentHomePage);
      }
      else{
         var url = "index.jsp?sysId=" +currentSysId +"&sysName=" + currentSysName +"&homePage=" +currentHomePage;
             url += "&loginModel=GD&title="+G_atmsicTitle+"&systems="+G_systemsInfo.join("");
             url += "&personName=" +G_personName +"&deptName=" + G_deptName;
             url = encodeURI(url);
         window.top.location.href = url;
      }
  }
  /*在新窗口打开页面 zhaoy 2010-09-15*/
  function openNewPageFromGD(pageURL){
      window.open("http://"+pageURL,"");
  }
  
  /*九宫格方式登录 zhaoy 2010-09-14*/
  function loginFromGD(){
      var userName = document.getElementById("pname").value;
      var pwd = document.getElementById("pwd").value;
      if(userName == ""){
          alert("请输入用户名!");
          return;
      }
      if(pwd == ""){
          alert("请输入密码!");
          return;
      }
      var params =  "pname=" + userName +"&pwd=" + pwd +"&loginModel=GD";
      var url = 'login.login.login.d';
	  params = encodeURI(params);
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:changeLoginArea});
  }
  function changeLoginArea(xmlHttp){
      var xmlStr = xmlHttp.responseText;
      if(xmlStr == "noConnection"){
          alert("数据库连接错误!");
          return;
      }
      if( xmlStr == "failure"){
          alert("用户或密码错误!");
          return;
      }
      var xmlArray = xmlStr.split(";");
      var personName = xmlArray[2];
      var deptName = xmlArray[1].split(",")[1];
       /*赋值人员姓名机构*/
       G_personName = personName;
       G_deptName = deptName;
      var loginArea = document.getElementById("loginTd");
      var loginArray = new Array(); 
      loginArray.push("<table width='70%' border='0' align='center' cellpadding='0' cellspacing='0' id='loginTab'>");
      loginArray.push("<tr>");
	  loginArray.push("<td width='26%' class='whitefont'>人员姓名：");
	  loginArray.push(personName);
	  loginArray.push("</td>");
	  loginArray.push("<td width='25%' class='whitefont'>所属机构：");
	  loginArray.push(deptName);   
	  loginArray.push("</td>");     
	  loginArray.push("<td width='17%'><a href='#' onclick=\"javascript:logoutFromGD('header');\"><img src='image/login/gd/zx.png' width='94' height='26' border='0' /></a>");        
	  loginArray.push("</td>");
	  loginArray.push("<td width='17%'><a href='#' onclick=\"javascript:updatePwdFromGD();\"><img src='image/login/gd/xgmm.png' width='94' height='26' border='0' /></a>");                
	  loginArray.push("</td>");
	  loginArray.push("</tr>"); 
	  loginArray.push("</table>"); 
	  loginArea.innerHTML =loginArray.join(""); 
	  G_loginAreaHtml =loginArray.join("");    
  }
  /*注销*/
  function logoutFromGD(header){
  	 if(header=="header" && header!=""){
		if(confirm("确认退出系统？")) {
			var strUrl = "login.login.logout.d"; 
			strUrl = encodeURI(strUrl);
			var params = "" ;
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseLogout});
		}
	 }else{
	 	var strUrl = "login.login.logout.d"; 
		strUrl = encodeURI(strUrl);
		var params = "" ;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseLogout});
	 }
  }
  function showResponseLogout(xmlHttp){
      var result = xmlHttp.responseText;
      if(result == "success"){
         window.top.location.href = "login_GD.jsp";
         alert("注销成功！");
      }
      if(result == "noLogin"){
         alert("您还没有登录！");
      }
  }
  /*修改密码*/
  
  function updatePwdFromGD(){
      var params = "isOpen=yes";
      var url = 'login.login.hasTheSysFun.d';
	  params = encodeURI(params);
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:openUpdatePwd});
  }
  function openUpdatePwd(xmlHttp){
      var xmlText = xmlHttp.responseText;
      if(xmlText == "noLogin"){
         alert("未登录或登录已过期,请登录!");
         window.top.location.href = "loginToGrid.jsp";
         return;
      }
      var returnValuestr = window.showModalDialog("sm/ehl/user/userModify.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2", "", "dialogWidth:300px;dialogHeight:250px");
	  if (returnValuestr == "insertOrUpdate") {
		 //fresh();
	  }
  }