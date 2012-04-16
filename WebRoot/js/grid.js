  var currentSysId ="";//当前登录系统编号
  var currentSysName ="";//当前登录系统名称
  var currentHomePage ="";//当前登录系统主页
  var G_loginAreaHtml = "";//登录区显示内容
  var sysCodeStr = "";//当前用户权限内系统编码
  var G_isOpen = "";//是否在新窗口打开
  var G_atmsicTitle = "";//网站标题
  var G_systemsInfo = new Array();//导航中展示出来的子系统信息
  var user = "";
  var G_personName = "";
  var G_deptName = "";
  var appId = ""; // 应用场景编号
  
 /**
  * 根据配置加载九宫格子
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
      htmlArray.push("<table width='80%' border='0' style='vertical-align:bottom;' align='center' cellpadding='0' cellspacing='0'>");
      for(var j = 0; j < systems.length; j ++){
          sysid = systems[j].getAttribute("sysid");
          syspid =  systems[j].getAttribute("id");
          homepage = systems[j].getAttribute("homepage");
          isdisplay = systems[j].getAttribute("isdisplay");
          openNewPage =  systems[j].getAttribute("opennewpage"); 
          sysname = systems[j].text;
          //Modify by Xiayx 2011-9-21
          //具有系统管理权限，显示系统管理链接
//          if(sysid == "01"){
//	          if(sysCodeStr.indexOf("01") != -1 || user == "admin"){
//	          		$("system").style.display = "block";
//	          }
//	          continue;
//          }
          //Modification finished
          if(isdisplay != "block"){
              continue;
          }
          if(sysCodeStr.indexOf(sysid) == -1 && user !="admin"){//判断系统是否在权限内 // && sysid!="" 
        	  image = itemImagePath + "d_" + systems[j].getAttribute("image"); 
	      	  onclickStr = "";
	      }else{
	          image = itemImagePath + systems[j].getAttribute("image"); 
		      onclickStr = "intoSysFromGD('"+sysid+"','"+sysname+"','"+homepage+"','"+openNewPage+"')";
		      var defaultPage = systems[j].getAttribute("default");
		      if(defaultPage=="t" && login=='yes'){
		    	  login=='no';
		    	  var tempsysid = sysid;
		    	  var tempsysname = sysname;
		    	  var temphomepage = homepage;
		    	  var tempopenNewPage = openNewPage;
		    	   if (appId == "1002") {
		    	  	intoSysFromGD(tempsysid,tempsysname,temphomepage,tempopenNewPage);
		    	  }
//		    	  window.setTimeout(function(){
//		    		  	if($(container.innerHTML!=""))
//		    	  			intoSysFromGD(tempsysid,tempsysname,temphomepage,tempopenNewPage);
//		    			},100);
		      }
	      }
          
      	  if(G_systemsInfo.length != 0){
              G_systemsInfo.push(";");
          }
          G_systemsInfo.push(sysid+","+sysname+","+homepage+","+openNewPage);

	      if(tdCount == colnum || j == (systems.length-1)){//结束一个tr
	          perTrImageArray.push("<td width='25%' align='center'><a id=\""+syspid+"\" href='#' onclick=\""+onclickStr+"\"><img src='"+image+"' width='115' height='113' /></a></td>")
              perTrLinkArray.push("<td class='whitefont'><a href='#' onclick=\""+onclickStr+"\">"+sysname+"</a></td>");
	          perTrImageArray.push("</tr>");
	          perTrLinkArray.push("</tr><br>");
	          tdCount =0;
	          htmlArray.push(perTrImageArray.join(""));
	          htmlArray.push(perTrLinkArray.join(""));
	          htmlArray.push("<tr style='height:100px'><td align='4'>&nbsp;</td></tr>");
	          perTrImageArray = new Array();
              perTrLinkArray = new Array();
	      }else if(tdCount == 1){//开始一个tr
	          perTrImageArray.push("<tr style='height:100px;'>");
	          perTrLinkArray.push("<tr style='vertical-align:bottom;height:40px'>");
	          perTrImageArray.push("<td width='25%' align='center'><a id=\""+syspid+"\" href='#' onclick=\""+onclickStr+"\"><img src='"+image+"' width='115' height='113' /></a></td>")
              perTrLinkArray.push("<td class='whitefont'><a href='#' onclick=\""+onclickStr+"\">"+sysname+"</a></td>");
	      }else{
	          perTrImageArray.push("<td width='25%' align='center'><a id=\""+syspid+"\" href='#' onclick=\""+onclickStr+"\"><img src='"+image+"' width='115' height='113' /></a></td>")
              perTrLinkArray.push("<td class='whitefont'><a href='#' onclick=\""+onclickStr+"\">"+sysname+"</a></td>");
	      }
	      tdCount ++;
	  }
	  
      htmlArray.push("</table>");
      
      //增加修改密码和注销按钮  2010-12-30 by wanghy
      var  onclickKey= "changeKeyNum()";
      var  onclickLog= "doLogout('header')";
      htmlArray.push("<table width='80%' border='0' align='center' cellpadding='0' cellspacing='0'>");
      htmlArray.push("<tr style='align:center;'>");
      htmlArray.push("<td width='50%' align=\"right\"><span style=\"position:relative;color:white;\"><a href='#' class='whitefont' onclick=\""+onclickKey+"\">修改密码</a>｜</span></td>");
      var logout = ["<span style=\"position:relative;color:white;\"><a href='#' class='whitefont' onclick=\""+onclickLog+"\">注销</a></span>"];
      if(sysCodeStr.indexOf("01") != -1 || user == "admin"){
      	   logout.push("<span style=\"position:relative;color:white;\">｜<a href='sm/index.jsp' class='whitefont' >系统管理</a></span>");
      }
      htmlArray.push("<td width='50%' align=\"left\">"+logout.join("")+"</td>");
      htmlArray.push("</tr>");
      htmlArray.push("</table>");
      container.innerHTML = htmlArray.join("");
      correctPNG();
  }
  
  //修改密码 2010-12-30 by wanghy
  function changeKeyNum(){
   var returnValuestr = window.showModalDialog("sm/ehl/user/userModify.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2", "", "dialogWidth:300px;dialogHeight:250px");
  }
  
  //注销登录 2010-12-30 by wanghy
  function doLogout(header){
	 if(header == "header" && header!=""){
		if(confirm("确认退出系统？")) {
			var strUrl = "common.userlogin.logout.d?update=1"; 
			strUrl = encodeURI(strUrl);
			var params = "" ;
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseLogout});
		}
	}else{
		var strUrl = "common.userlogin.logout.d?update=1"; 
		strUrl = encodeURI(strUrl);
		var params = "" ;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseLogout});
	}
  }
  
  function showResponseLogout() {
	//跳转
	parent.document.location.href="./loginToGrid.jsp";
}
  function checkAndLoadGrids(username,apid){
  	  appId = apid;
      var params = "userName=" + username;
      user = username;
      var url = 'login.login.getLoginInfo.d';
	  params = encodeURI(params);
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:initSys});
  }
  function initSys(xmlHttp){
      var xmlDoc = xmlHttp.responseXML;
      var syscode = xmlDoc.getElementsByTagName("syscode");
      for(var i = 0; i < syscode.length; i ++){
          sysCodeStr += " "+syscode[i].text;
      }
      initPageItems('xml/subsystems.xml','4');
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
      }else{
         var url = "index.jsp?sysId=" +currentSysId +"&sysName=" + currentSysName +"&homePage=" +currentHomePage;
             url += "&loginModel=GD_NEW&title="+G_atmsicTitle+"&systems="+G_systemsInfo.join("");
             url += "&personName=" +G_personName +"&deptName=" + G_deptName;
             url = encodeURI(url);
         window.top.location.href = url;
      }
  }
  /*在新窗口打开页面 zhaoy 2010-09-15*/
  function openNewPageFromGD(pageURL){
      window.open("http://"+pageURL,"");
  }
  
  function initUserInfo(loginInfo){
      var xmlArray = loginInfo.split(";");
	       var personName = xmlArray[2];
	       var deptName = xmlArray[1].split(",")[1];
	       /*赋值人员姓名机构*/
	       G_personName = personName;
	       G_deptName = deptName;
  }
  