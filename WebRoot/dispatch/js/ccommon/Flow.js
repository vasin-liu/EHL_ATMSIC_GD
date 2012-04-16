var Flow = {
	//签收/办结{id:"",rpname:"",rtime:"",state:""}
//	{id:"",state:"",stype:"",rpname:"",rtime:""}
//	id=警情id，
//	id=接收单位id，stype=2
	updtState : function(params, msg, iscw){
		var url = "dispatch.accdept.updateState.d";
		new Ajax.Request(url,{
			method:"post",
			parameters : params,
			onComplete : function(xmlHttp){
				if(msg){
					var result = Flow.showResponse(xmlHttp,msg);
					if(iscw){
						window.close();
					}
				}
			}
		});
	},
	//分发
	showDispatch : function(aid,jgid,jgidId,x,y,dmname,dtype){
		//treeId,setJgidId,setJgmcId,cbmname
		if(jgidId){
			Tree.jgid = jgidId;
		}
		if(x && y){
			top = x;
			left = y;
		}
		if(dmname){
			dmname = ",'"+dmname+"'";
		}else{
			dmname="";
		}
		var pos = Flow.getPosition("250","340");
		if( pos != undefined && pos != null && pos.top != 0 && pos.left != 0 ){
			top = pos.top;
			left = pos.left;
		}
		setTree(jgid,top,left,"Flow.dispatchCheck('"+aid+"','"+jgid+"','"+jgidId+"','"+top+"','"+left+"'"+dmname+")",dtype);
	},
	dispatchCheck : function(aid,jgid,jgidId,x,y,dmname,dtype){
		var adcode = $(Tree.jgid).value;
		if(adcode == ""){
			if(dmname){
				dmname = dmname.replace(/'/g,"\\\'");
			}else{
				dmname = null;
			}
			Flow.showDispatch(aid,jgid,jgidId,x,y,dmname,dtype);
			alert("请选择发送机构！");
			return;
		}
		adcode = adcode.replace(/；/g,",");
		adcode = adcode.substring(0,adcode.length-1);
		if(dmname){
			eval(dmname);
		}else{
			Flow.dispatch(aid,adcode);
		}
	},
	dispatch: function(aid,adcode){
		var url = "dispatch.accdept.addAccDept.d";
		var params = {
			aid : aid,
			adcode : adcode
		}
		new Ajax.Request(url, {
			method : "post",
			parameters : params,
			onComplete : function(xmlHttp){
				var result = Flow.showResponse(xmlHttp,"发送");
				if(result == "true"){
					window.close();
				}
			}
		});
	},
	showResponse : function(xmlHttp,msg){
		var result = xmlHttp.responseText;
		var rdesc;
		if(result == "true"){
			rdesc = "成功";
		}else if(result == "false"){
			rdesc = "失败";
		}
		alert(msg+rdesc+"！");
		return result;
	},
	showAdstate : function(adstate,cindexs,id){
//		for(var i = 0; i < adstate.length; i++) {
//			alert(adstate[i])
//		}
		var adscolors = ["red","green","blue"];
		var adstateStr = "";
		var sep = "，";
		if(adstate && adstate.length == 1){
			adstate = adstate[0];
			adstate = adstate.childNodes;
			for(var i=0;i<cindexs.length && i<adstate.length;i++){
				if(adstate[i+1].text != ""){
					adstateStr += sep + "<span id="+i+" style='color:"+adscolors[cindexs[i]]+";'>"+adstate[i+1].text+"</span>";
				}
			}
		}
		if(adstateStr != ""){
			adstateStr = adstateStr.substring(sep.length);
		}
		if(id){
			$(id).innerHTML = adstateStr||"";
		}
		return adstateStr;
	},
	//Modified by Liuwx 2011-07-31
	getPosition : function (objWidth,objHight) {
		var ev = window.event || arguments[0]; //获取事件	
		var src = ev.srcElement || ev.target;//获取事件源对象
		var srcWidth = parseInt(src.offsetWidth);//对象自身的宽度
		var srcHeight = parseInt(src.offsetHeight);//对象自身的高度
		var eleTop = parseInt(src.offsetTop);//事件源绝对坐标Top
		var eleLeft = parseInt(src.offsetLeft);//事件源绝对坐标left
		var docWidth = parseInt(document.body.offsetWidth);//文档宽度
		var docHeight = parseInt(document.body.offsetHeight);  //文档高度

		//事件源对象绝对坐标
	    while(src=src.offsetParent) 
	    { 
	    	eleTop += parseInt(src.offsetTop);  
	    	eleLeft += parseInt(src.offsetLeft); 
	    };
	    
	    var left = eleLeft;
	    var top = eleTop;
	    
	    if(objWidth && objHight){
	    	if((eleTop+ parseInt(objHight)) > docHeight && parseInt(objHight) < docHeight){
	    		top = (eleTop -  parseInt(objHight) + srcHeight)>0?(eleTop -  parseInt(objHight) + srcHeight):(docHeight-parseInt(objHight))/2;//超出文档高度时向上弹出
	    	}else{
	    		top = (eleTop + srcHeight);
	    	}
	    	
	    	if((eleLeft+parseInt(objWidth)) > docWidth && parseInt(objWidth) < docWidth){
	    		left = (eleLeft - parseInt(objWidth) + srcWidth)>0?(eleLeft - parseInt(objWidth) + srcWidth):(docWidth-parseInt(objWidth))/2;//超出文档宽度时向左弹出
	    	}else{
	    		left = eleLeft;
	    	}
	    }
	    return {left:left,top:top};
//		return {eleTop:eleTop,eleLeft:eleLeft,docWidth:docWidth,docHeight:docHeight};
	}
	//Modification finished
}

var Submit = {
	urlParse : function(url){
		var urlJ = {};
		var uriIndex = url.indexOf("?");
		var uri = url.substring(0,uriIndex);
		urlJ["uri"] = uri;
		var paramStr = url.substring(uriIndex+1);
		var params = paramStr.split("&");
		var param;
		for(var i=0;i<params.length;i++){
			param = params[i].split("=");
			urlJ[param[0]] = param[1];
		}
		return urlJ;
	},
	urlToForm : function(url,paramj,target){
		if(!paramj){
			paramj = Submit.urlParse(url);
			url = paramj.uri;
			delete paramj.uri;
		}
		var form = document.createElement("form");
		form.action = url;
		form.method = "post";
		form.style.display = "none";
		form.target = target || "";
		document.body.appendChild(form);
		for(var attr in paramj){
			var input = document.createElement("input");
			input.type = "hidden";
			input.name = attr;
			input.value = paramj[attr];
			form.appendChild(input);
		}
		form.submit();
	}
}






















