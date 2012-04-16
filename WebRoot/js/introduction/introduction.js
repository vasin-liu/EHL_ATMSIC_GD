  //加载首页展示信息
  function loadIntroduce(){
    var params = "";
    url = 'introduce.introduction.getIntroduction.d';
	params = "";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showIntroduceResult});
 }
 
 //展示首页发布信息

 function showIntroduceResult(xmlHttp){
    var xmlDoc = xmlHttp.responseXML;
    var container = document.getElementById("txtContent");
    var rows = xmlDoc.getElementsByTagName("row");
    var roadStatus = xmlDoc.getElementsByTagName("roadStatus");
    var roadStatusArray = roadStatus[0].text.split(",");
    var title = "";
    var content = "";
    id = "";
    var titleEnd = "交通状况介绍";
    var contentEnd = "到目前为止，";
     //机动车较去年同期增长百分比
    if(roadStatusArray[1] == 0){
        roadStatusArray[1] = 1;
    }
    var vehPercent = (roadStatusArray[0]-roadStatusArray[1])/roadStatusArray[1];
    vehPercent = vehPercent.toString();
    if(vehPercent.indexOf(".") > -1){
        vehPercent = vehPercent.substring(0,vehPercent.indexOf(".")+3);
    }
    //
    if(roadStatusArray[2] == 0){
        roadStatusArray[2] = 1;
    }
    var driverPercent = (roadStatusArray[2]-roadStatusArray[3])/roadStatusArray[2];
    driverPercent = driverPercent.toString();
    if(driverPercent.indexOf(".") > -1){
        driverPercent = driverPercent.substring(0,driverPercent.indexOf(".")+3);
    }
    //
    if(roadStatusArray[8] == 0){
        roadStatusArray[8] = 1;
    }
    var vioPercent = (roadStatusArray[8]-roadStatusArray[9])/roadStatusArray[8];
    vioPercent = vioPercent.toString();
    if(vioPercent.indexOf(".") > -1){
        vioPercent = vioPercent.substring(0,vioPercent.indexOf(".")+3);
    }
    //
    if(roadStatusArray[10] == 0){
        roadStatusArray[10] = 1;
    }
    var accPercent = (roadStatusArray[10]-roadStatusArray[11])/roadStatusArray[10];
    accPercent = accPercent.toString();
    if(accPercent.indexOf(".") > -1){
        accPercent = accPercent.substring(0,accPercent.indexOf(".")+3);
    }
	contentEnd +=   "全市<b>机动车保有量"+roadStatusArray[0]+"</b>辆，较去年增长<b>"+vehPercent+"</b> ％。";
	contentEnd +=	"全市<b>驾驶人保有量"+roadStatusArray[2]+"</b>人，较去年增长<b>"+driverPercent+"</b>％。";
	contentEnd +=	"其中<b>驾龄1年以下</b>的有<b>"+roadStatusArray[4]+"</b>人，<b>1-2年</b>的有<b>"+roadStatusArray[5]+"</b>人，<b>2-3年</b>的有<b>"+roadStatusArray[6]+"</b>人，<b>3年以上</b>的有<b>"+roadStatusArray[7]+"</b>人。";
	contentEnd +=	"全市发生<b>交通违法"+roadStatusArray[8]+"</b>起，较去年同期增长<b>"+vioPercent+"</b>％。";
	contentEnd +=	"全市发生<b>交通事故"+roadStatusArray[10]+"</b>起，较去年同期增长<b>"+accPercent+"</b>％。其中<b>死亡事故"+roadStatusArray[12]+"</b>起，";
	contentEnd +=   "<b>伤人事故"+roadStatusArray[13]+"</b>起，<b>财产损失事故"+roadStatusArray[14]+"</b>起，<b>简易程序事故"+roadStatusArray[15]+"</b>起。";
	//contentEnd +=	"施工占道与交通管制：全市范围内发生施工占道路处，交通管制路段总数为处。";
     var str = ""
     for(var i = 0; i <= rows.length; i++){
       if(i == rows.length){
           id = "";
           title = titleEnd;
           content = contentEnd;
       }else{
           id = rows[i].getElementsByTagName("id")[0].text;
           title = rows[i].getElementsByTagName("title")[0].text;
           content = rows[i].getElementsByTagName("content")[0].text;
       }
        str+=  "<table width='100%' border='0' height='30%' cellpadding='0' cellspacing='0'>\
	         <tr>\
		         <td class='titleBack'>\
			        <table width='100%' border='0' cellspacing='0' cellpadding='0'>\
			            <tr>\
			              <td width='3%' align='center'><img src='../image/style/lm_dot.gif' width='11' height='11' /></td>\
			              <td width='93%' class='titleFont'>"+title+"</td>\
			              <td width='4%'><img src='../image/style/more.gif' style='cursor:hand' width='28' height='12' onclick=\"javascript:toEdit('"+id+"')\"/></td>\
			            </tr>\
			        </table>\
			    </td>\
		      </tr>\
		      <tr>\
		        <td class='tdFont' id='content'>&nbsp;&nbsp;&nbsp;&nbsp;"+content+"</td>\
			</tr>\
			</table>";
    }
    txtContent.innerHTML = str;
  }
  
  //进入发布信息编辑
  function toEdit(idStr){
    id = idStr;
    var params = "";
    url = 'common.login.hasThePrive.d';
	params = "funcCode=010908";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:openEditByResult});
  }
  
  function openEditByResult(xmlHttp){
      var result = xmlHttp.responseText;
      if(result == "yes"){
          if(id == ""){//最后一条发布信息
		       updateStateData();
		  }else{
		       openEdit(id);
		  }
          return;
      }else{
          alert("尚未登录或没有此修改权限，请登录！");
          return;
      }
  }