
/**
 * 展示视频.
 * devID-设备编号[tgsId或者camId];
 * ctrlType-控制类型[(play-查看 默认) 或者 back-回放)]
 * deviceType-设备类型 [目前仅支持(cctv 默认)和tgs]
 */
function showCctv(devID,ctrlType,deviceType){
	if(!ctrlType){
  		ctrlType = 'play';
  	}
	if(!deviceType){
		deviceType = 'cctv';
	}
	if(deviceType=="cctv"){
		var url = "../../../cctv/ehl/cctv/CctvViewer.jsp?CamID=" + encodeURI(devID) + "&type="+ctrlType;
	 	window.showModalDialog(url,"","dialogWidth:750px;dialogHeight:590px");
	}else if(deviceType=="tgs"){
		if (devID != "") {
			var url = "vcas.tgsandcam.getDataById.d";
			url = encodeURI(url);
			var params = "KKBM="+devID;
					
			new Ajax.Request(url, 
				{
					method:"post", parameters:params, 
					onComplete:function(xmlHttp){
						var xmldoc   = xmlHttp.responseXML;
						var results = xmldoc.getElementsByTagName("col");
			
						var strIP = results[2].text;
						var strPort = results[3].text;
						var strUser = results[4].text;
						var strPassword = results[5].text;
						
				    	var url = "../../../cctv/ehl/cctv/VideoTGSPlayback.jsp?DVRIP='" + encodeURI(strIP)+ "'&DVRPort='" + encodeURI(strPort) 
				    			  + "'&DVRLoginUser='" + encodeURI(strUser) + "'&DVRLoginPWD='" + encodeURI(strPassword) + "'" ;
				    	url = encodeURI(url);
				    	var wLeft = (screen.availWidth - 680) / 2;
				    	var wTop = (screen.availHeight - 560) / 2;
				    	window.open(url,"","height=560,width=680,left=" + wLeft +",top=" + wTop + ",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=yes");
					}
				}
			);
		}else{
			alert("卡口视频初始化失败！");
		}
  }
}

function openDownload(){
	var url = "../../../cctv/ehl/cctv/help.jsp" ;
	url = encodeURI(url);
	var wLeft = (screen.availWidth - 680) / 2;
	var wTop = (screen.availHeight - 560) / 2;
	window.open(url,"","height=560,width=680,left=" + wLeft +",top=" + wTop + ",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=yes");
	
}