var aLinkObj = null;
var sysCodeStr = "";//全部权限内的系统编号
var user = "";//当前登录用户名
var defaultSysHomepage = "drpt/index_drpt.jsp";//默认子系统首页
var subsystemArray = new Array();
var G_loginModel = "";
var G_sysID = "";//当前登录系统编号
var xmlURL = "xml/subsystems.xml";
var G_loginPage = "";

/* 分类别初始化系统菜单 zhaoy 2010-08-10*/
function loadSysMenu(userName, loginModel, sysId, sysName, homePage,
		systemsForGD, personName, deptName) {
	G_loginModel = loginModel;
	G_sysID = sysId;
	if (sysId) {
		document.frames["_main"].location.href = defaultSysHomepage;
	}
	G_loginPage = "login.jsp";
	if (G_loginModel == "GD") {
		G_loginPage = "login_GD.jsp";
		loadNavigationByGD(sysName, homePage, userName, systemsForGD,
				personName, deptName);
		return;
	} else if (G_loginModel == "GD_NEW") {
		G_loginPage = "loginToGrid.jsp";
		loadNavigationByGD(sysName, homePage, userName, systemsForGD,
				personName, deptName);
		return;
	}
	var params = "userName=" + userName;
	user = userName
	var url = 'login.login.getLoginInfo.d';
	params = encodeURI(params);
	new Ajax.Request(url, {
		method : "post",
		parameters : params,
		onComplete : showSysMenu
	});
}
function showSysMenu(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var syscode = xmlDoc.getElementsByTagName("syscode");
	var deptName = xmlDoc.getElementsByTagName("dept")[0].text;
	var personName = xmlDoc.getElementsByTagName("person")[0].text;
	showLoginUser(deptName, personName);
	for ( var i = 0; i < syscode.length; i++) {
		sysCodeStr += " " + syscode[i].text;
	}
	showSystems(xmlURL, sysCodeStr, "");
}

/*初始控制显示子系统项
 *xmlUrl-xml路径
 *sysCodeStr-全部权限内的系统编号
 showType - 系统显示方式 ("group"-分组显示,"extend"-展开显示)
 */
function showSystems(xmlUrl, sysCodeStr, showType) {
	subsystemArray = new Array();
	var xmlDom = getDom(xmlUrl);
	var contaner = document.getElementById("alist");
	var htmlStr = "";
	if (showType == "group") {
		htmlStr = getSysHtmlStrOnGroup(xmlDom, sysCodeStr);
		contaner.innerHTML = htmlStr;
		menuFix();
	} else {
		htmlStr = getSysHtmlStrOnExtend(xmlDom, sysCodeStr);
		contaner.innerHTML = htmlStr;
		document.frames["_main"].location.href = defaultSysHomepage;
	}

}
/*分组显示子系统项,拼接菜单字符串
 * xmlDom-xml的Dom对象
 * sysCodeStr-全部权限内的系统编号
 */
function getSysHtmlStrOnGroup(xmlDom, sysCodeStr) {
	var subsystemRoot = xmlDom.documentElement.childNodes;
	if (subsystemRoot.length == 0) {
		return;
	}
	var htmlStr = "";
	var subsystemGroup = subsystemRoot[0].getElementsByTagName("items");
	var id = "";//子系统英文缩写
	var sysid = "";//子系统编号
	var homepage = "";//子系统首页
	var sysname = "";//子系统名称
	var isdisplay = "";//是否显示
	var openNewPage = "";//是否新打开窗口
	var onclickStr = "";
	var systems = "";//一组子系统集合
	var sysGroupName = "";//系统分组名称
	var perSysGroupStr = "";//一组子系统字符串
	var perSysStr = "";//每个子系统字符串
	for ( var j = 0; j < subsystemGroup.length; j += 1) {
		perSysGroupStr = "";//清空子系统分组字符串
		sysGroupName = subsystemGroup[j].getAttribute("name");
		var systems = subsystemGroup[j].childNodes;
		for ( var i = 0; i < systems.length; i++) {
			perSysStr = "";//清空系统项
			id = systems[i].getAttribute("id");
			//subsystemArray.push(id);
			sysid = systems[i].getAttribute("sysid");
			homepage = systems[i].getAttribute("homepage");
			sysname = systems[i].text;
			isdisplay = systems[i].getAttribute("isdisplay");
			openNewPage = systems[i].getAttribute("opennewpage");
			if (sysCodeStr.indexOf(sysid) == -1 && user != "admin") {//判断系统是否在权限内 
				continue;
			}
			if (isdisplay == "block") {

			} else {
				continue;
			}
			if (defaultSysHomepage == "" && htmlStr.length == 0
					&& perSysStr.length == 0 && perSysGroupStr.length == 0) {//设置系统默认首页
				defaultSysHomepage = homepage;
			}
			if (openNewPage == "yes") {
				perSysStr += "<li><a href='#' style=' width: 100px;' id = '"
						+ id + "' name='" + homepage
						+ "' onclick=\"openTheUrl('" + homepage + "');\">   "
						+ sysname + "</a></li>";
			} else {
				onclickStr = "isLogin(this,'no')";
				perSysStr += "<li><a href='#' style=' width: 100px;' id = '"
						+ id + "' name='" + homepage + "' onclick=\""
						+ onclickStr + ";\">   " + sysname + "</a></li>";
			}
			perSysGroupStr += perSysStr;
		}
		if (perSysGroupStr.length != 0) {//增加一组系统列表
			htmlStr += "<li ><a href='#' style='width: 100px'>" + sysGroupName
					+ "</a><ul style='border: 1px solid #4b9fd3'>   "
					+ perSysGroupStr + "</ul></li>";
		}
	}
	htmlStr += "<li ><a href='#' style='width: 100px' onclick=\"toExtend()\">展开显示>></a></li>";
	htmlStr = "<ul id='navg' style='text-align: center;'>" + htmlStr + "</ul>";
	return htmlStr;
}

/*展开显示子系统项,拼接菜单字符串
 * xmlDom-xml的Dom对象
 * sysCodeStr-全部权限内的系统编号
 */
function getSysHtmlStrOnExtend(xmlDom, sysCodeStr) {
	var systems = xmlDom.getElementsByTagName("item");
	if (systems.length == 0) {
		return;
	}
	var htmlStr = "";
	var id = "";//子系统英文缩写
	var sysid = "";//子系统编号
	var homepage = "";//子系统首页
	var sysname = "";//子系统名称
	var isdisplay = "";//是否显示
	var openNewPage = "";//是否新打开窗口
	var onclickStr = "";
	var perSysStr = "";//每个子系统字符串
	for ( var i = 0; i < systems.length; i++) {
		perSysStr = "";//清空系统项
		id = systems[i].getAttribute("id");
		sysid = systems[i].getAttribute("sysid");
		homepage = systems[i].getAttribute("homepage");
		sysname = systems[i].text;
		isdisplay = systems[i].getAttribute("isdisplay");
		openNewPage = systems[i].getAttribute("opennewpage");
		if (sysCodeStr.indexOf(sysid) == -1 && user != "admin") {//判断系统是否在权限内 
			continue;
		}
		if (isdisplay == "block") {

		} else {
			continue;
		}
		if (systems[i].getAttribute("default") == "t") {//设置系统默认首页
			defaultSysHomepage = homepage;
		}
		if (openNewPage == "yes") {
			perSysStr += "<li><a href='#' class='taboff' style=' width: 100px;  text-align:center;' id = '"
					+ id
					+ "' name='"
					+ homepage
					+ "' onclick=\"openTheUrl('"
					+ homepage + "');\">   " + sysname + "</a></li>";
		} else {
			onclickStr = "isLogin(this,'no')";
			perSysStr += "<li><a href='#' class='taboff' style=' width: 100px;  text-align:center;' id = '"
					+ id
					+ "' name='"
					+ homepage
					+ "' onclick=\""
					+ onclickStr
					+ ";\">  " + sysname + "</a></li>";
		}
		if (i != systems.length - 1) {
			perSysStr += "<font color='#ffffff'>｜</font>";
		}
		htmlStr += perSysStr
	}
	if (htmlStr.length != 0) {
		htmlStr = "<ul id='nav_back'>"
				+ htmlStr
				+ "<li><a href='#' class='taboff' style=' width: 100px; text-align:center;' onclick=\"toGroup('"
				+ user + "');\">分组显示>></a></li>" + "</ul>";
	}
	return htmlStr;
}

function openTheUrl(url) {
	window.open("http://" + url);

}
function toGroup(loginUser) {
	//loginUser//登录用户预留
	showSystems("xml/subsystems.xml", sysCodeStr, "group");
}

function toExtend() {
	showSystems("xml/subsystems.xml", sysCodeStr, "");
}
/* 
 加载菜单事件
 */
function menuFix() {
	var sfEls = document.getElementById("navg").getElementsByTagName("li");
	for ( var i = 0; i < sfEls.length; i++) {
		sfEls[i].onmouseover = function() {
			this.className += (this.className.length > 0 ? " " : "")
					+ "sfhover";
		}
		sfEls[i].onMouseDown = function() {
			this.className += (this.className.length > 0 ? " " : "")
					+ "sfhover";
		}
		sfEls[i].onMouseUp = function() {
			this.className += (this.className.length > 0 ? " " : "")
					+ "sfhover";
		}
		sfEls[i].onmouseout = function() {

			this.className = this.className.replace(new RegExp(
					"( ?|^)sfhover\\b"), "");
		}
	}
}

//如果登录成功，使所有超链有效,重载首页右侧区域
function showLoginUser(jgmc, userName) {
	var pname = userName;
	var deptName = jgmc;
	var deptAndUserTd = window.top.window.document
			.getElementById("currentLogin");
	var deptAndUserInner
= "<table >\
	                    <tr>\
	                        <td>\
	                            <ul>\
						           <li><span ><a href=\"#\"><span style=\"color:#ffffff;font-size:7pt;font-weight:lighter;\">当前用户：</span></a></span></li>\
					            </ul>\
	                        </td>\
	                        <td> \
	                            <ul>\
						           <li><span ><a href=\"#\"><span style=\"color:#ffffff;font-size:7pt;font-weight:lighter;\">"+pname+"</span></a></span></li>\
					            </ul>\
	                        </td>\
	                    </tr>\
	                    <tr>\
	                        <td>\
	                            <ul>\
						           <li><span ><a href=\"#\"><span style=\"color:#ffffff;font-size:7pt;font-weight:lighter;\">当前机构：</span></a></span></li>\
					            </ul>\
	                        </td>\
	                        <td>\
	                            <ul>\
						           <li><span ><a href=\"#\"><span style=\"color:#ffffff;font-size:7pt;font-weight:lighter;\">"+deptName+"</span></a></span></li>\
					            </ul>\
	                        </td>\
	                    </tr>\
	                </table>";
	deptAndUserTd.innerHTML = deptAndUserInner;
 }
 /**
 * 检查登录状态,并根据结果进行跳转或不
 * param noCheck-是否进行登录检查("yes"-不进行登录检查 "no"-进行登录检查)
 * param aObj-超链对象（aLinkObj.name为超链目标URL）
 */

 function isLogin(aObj,noCheck){
    var liObj = aObj.parentNode.parentNode.parentNode;
    liObj.className=liObj.className.replace(new RegExp("( ?|^)sfhover\\b"),"");
    var obj = document.getElementsByTagName("iframe")[0];
    obj.focus();
   
    if(noCheck && noCheck == "yes"){
       document.frames["_main"].location.href = aObj.name;
       return;
    }
    aLinkObj = aObj;
    url = 'common.login.isLogin.d';
	params = encodeURI("");
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showIsLogin});
  }


 function showIsLogin(xmlHttp){
   var pname = xmlHttp.responseText;
    if(pname == "null" || pname == null){
       alert("您尚未登录或登录已过期，请登录！");
    }else{
       if(aLinkObj != null && typeof aLinkObj != "undefined"){
          if(aLinkObj.name.indexOf("http")>-1){
              window.open(aLinkObj.name);
          }else{
              document.frames["_main"].location.href = aLinkObj.name;
          }
       }
    }
}

 //打开友情链接
 function friendLink(aObj){
    if(aObj){
       window.open(aObj.name); 
    }
 }
  
  //实时获取当前系统时间
   function systemTime()
{
	var bgclock = document.getElementById("bgclock");
    var now = new Date();
    var year = now.getYear();
    var month = now.getMonth();
    var date = now.getDate();
    var day = now.getDay();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
     month = month+1;
    if(month<10)month="0"+month;
    if(date<10)date="0"+date;
    if(hour<10)hour="0"+hour;
    if(minu<10)minu="0"+minu;
    if(sec<10)sec="0"+sec;
    var arr_week = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
    var week = arr_week[day];
    var time = "";
     time = "<b>"+year+"年"+month+"月"+date+"日"+week+"</b>";
    if(document.all)
    {
         bgclock.innerHTML=time;
    }
    //id="bgclock"          
}
 //注销
 function logout(){
    
	if(confirm("确认退出系统？")) {
		var strUrl = "common.login.logout.d"; 
		strUrl = encodeURI(strUrl);
		var params = "" ;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseLogout});
	}
 }
 
  function showResponseLogout(xmlHttp){
      var result = xmlHttp.responseText;
      if(result == "success"){
         window.top.location.href = G_loginPage;
         alert("注销成功！");
      }
      if(result == "noLogin"){
         alert("您还没有登录！");
      }
  }
  
  //修改密码
 function  updatePwd(){
		var strUrl = "common.login.isLogin.d"; 
		strUrl = encodeURI(strUrl);
		var params = "" ;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showUpdateResult});
 }
 
  function showUpdateResult(xmlHttp){
      var result = xmlHttp.responseText;
      if(result != "null" && result != null){
         showPwdPage();
      }else{
         alert("您尚未登录或登录已过期！");
      }
      
  }
  //修改密码
  function showPwdPage(){
	var returnValuestr = window.showModalDialog("sm/ehl/user/userModify.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2", "", "dialogWidth:300px;dialogHeight:250px");
	if (returnValuestr == "insertOrUpdate") {
		fresh();
	}
	//window.open("ehl/user/userModify.jsp?insrtOrUpdt=2", "width:380px;height:500px");
}

   function timeAndLogin(){
     systemTime();
     initLoginState();
   }

  
  //打开管理员登陆窗口
  function openAdminLoginPage(){
      window.open("sm/index.jsp");
  }
  
  var isRight = "false"
  /*
  * 按九宫格方式加载导航
  */
  function loadNavigationByGD(sysName,homePage,userName,systemsForGD,personName,deptName){
      /*展示用户机构*/
      showLoginUser(deptName,personName);
      var contaner = document.getElementById("alist");
      document.frames["_main"].location.href = homePage;
      var systems = systemsForGD.split(";");
      var sysid = "";//子系统编号
	  var homepage = "";//子系统首页
	  var sysname = "";//子系统名称
	  var openNewPage = "";//是否新打开窗口
	  var perSystem = "";
	  var onclickStr = "";
	  var perSysStr = "";
	  var htmlStr = "";
	  var sCode = $('sCode').value;
	  var sCodes= new Array(); 
	  sCodes = sCode.split(" ");

	  var defaultBackStyle = "url(image/head/nav_hover.gif);";
	  var backStyle = "background:url();"
      for(var i = 0 ; i < systems.length; i ++){
          perSysStr = "";
          perSystem = systems[i].split(",");
          sysid = perSystem[0];
          sysname = perSystem[1];
          if(sysname == sysName){
              defaultBackStyle = "background:url(image/head/nav_hover.gif);";
          }else{
              defaultBackStyle = "";
          }
          homepage = perSystem[2];
          openNewPage = perSystem[3];
	      if(openNewPage == "yes"){  
	    	  //color='white' 修改样式增大字体、加粗 2010-12-30 by wanghy
		       perSysStr+= "<li onclick=\"changeLiStatus(this)\"><a  href='#'   style='width: 100px;"+defaultBackStyle+"  text-align:center;' id = '"+sysid+"' name='"+homepage+"' onclick=\"openTheUrl('"+homepage+"');\"> <font style=\"color:white;fontsize:2pt;font-weight:bold;\"> "+sysname+"</font> </a></li>";
	       }else{
	           onclickStr = "isLogin(this,'no')";
	           for (var k=0;k<sCodes.length ;k++ ) {
	        	   var code = sCodes[k];
	        	   if(code == sysid || sysid == "") {
	        		   isRight = "true"
	        			   break;
	        	   } else {
	        		   isRight = "false"
	        	   }
	           }
	       }
//	      alert(i + "---" + sysid)
	      if(isRight == "true") {
		      perSysStr+= "<li onclick=\"changeLiStatus(this)\"><a href='#'   style='width: 100px;  "+defaultBackStyle+" text-align:center;' id = '"+sysid+"' name='"+homepage+"' onclick=\""+onclickStr+";\"><font style=\"color:white;fontsize:2pt;font-weight:bold;\"> "+sysname+"</font> </a></li>";
	      } else {
		      perSysStr+= "<li onclick=\"changeLiStatus(this)\"><a style='width: 100px; "+backStyle+" text-align:center;' id = '"+sysid+"' name='"+homepage+"'><font style=\"color:gray;fontsize:2pt;font-weight:bold;\"> "+sysname+"</font> </a></li>";
	      }
	      htmlStr += perSysStr;
       }
      var onclickStr = "isLogin(this,'yes')";                                                                                                               
      htmlStr+= "<li><a href='#' class='taboff' style=' width: 100px;  text-align:center;' onclick=\"openLoginState();\">  <font style=\"color:white;fontsize:2pt;font-weight:bold;\"> 在线人数</font></a></li>";
      htmlStr+= "<li><a href='#' class='taboff' style=' width: 100px;  text-align:center;' onclick=\"goBackLoginPage('"+userName+"');\">  <font style=\"color:white;fontsize:2pt;font-weight:bold;\"> 返回登录页</font></a></li>";
      contaner.innerHTML = "<ul id='navg' style='text-align: center;'>" + htmlStr +"</ul>";
  }
  /*返回登录页面*/
  function goBackLoginPage(userName){
      window.top.location.href = "grid.jsp";
  }
  /*查看在线人数
  * deptId-要查看在线人数的机构编码(机构无上下级所属关系)
  * sysId-要查看在线人数的系统编号
  * sysName-要查看在线人数的系统名称
  */
  function openLoginState(deptId,sysId,sysName){
		//查询在线人数
		var url = "loginstate/loginState.jsp";
		//查询指定机构在线人数
		//var url = "loginstate/loginState.jsp?deptid=370102010400";
		//查询指定系统在线人数 
		//var url = "loginstate/loginState.jsp?sysid=010000&sysname=系统管理中心";
		//查询指定机构和系统在线人数
		//var url = "loginstate/loginState.jsp?deptid=370102010400&sysid=010000&sysname=系统管理中心";
		url = encodeURI(url);
		window.open (url,'','height=550,width=500,top=150,left=400');
  } 
  
  function changeLiStatus(liobj){
       var lis = liobj.parentNode.childNodes;
	   
       for(var i = 0;  i < lis.length; i ++){
    	   if(lis[i].getElementsByTagName("a")[0].style.backgroundImage == "url(image/head/nav_hover.gif)") {
    		   lis[i].getElementsByTagName("a")[0].style.backgroundImage = "";
    	   }
       }
       if(liobj.getElementsByTagName("a")[0].style.background == "") {
		   liobj.getElementsByTagName("a")[0].style.background="url(image/head/nav_hover.gif)";
       }
  }
  
  /*
 * Modified by Leisx 2011-11-24 设置主页方法，根据sID，userName获取该模块的主页
 * sID：交管知识库=58、警民互动=93、信息编报管理=88、交通警情=57、分析研判=30 春运管理=90、系统管理=01 userName：用户登录名
 */
function setHomePage(sID, userName) {
	if (userName != "admin") {
		var params = "sID=" + sID + "&userName=" + userName;
		var url = 'common.homePageUtil.getHomePage.d';
		params = encodeURI(params);
		new Ajax.Request(url, {
			method : "post",
			asynchronous : false,
			parameters : params,
			onComplete : showHomePage
		});
	}
}

/*
 * Modified by Leisx 2011-11-24 获得主页链接，并展示
 */
function showHomePage(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var homePage = xmlDoc.getElementsByTagName("hp")[0]
			.getElementsByTagName("col")[0].text;
	var funcId = xmlDoc.getElementsByTagName("hp")[0]
			.getElementsByTagName("col")[1].text;
	if (homePage.substring(homePage.length - 4) == ".cpt") {
		homePage = "../../WebReport/" + homePage;
		homePage += "&op=view&funcId="+ funcId;
	} else {
		funcId = ShowNodePrivi.changeToParent(funcId);
		homePage += ".jsp?funcId="+ funcId;
	}
	document.getElementById("moduletarget").src = homePage;

}
/*
 * finished by Leisx 2011-11-24
 */