
/*
创建层
布局内容
赋初始值
搜索
显示搜索结果
设置值
*/

var RoadDept = {
	id : {
		cmpn : {
			roadDept : "roadDept_",
			gbbm : "roadCode_",
			dlmc : "roadName_",
			dldj : "roadLevel_",
			xqldmc : "rsegName_",
			qslc : "startKM_",
			jslc : "endKM_",
			xzqh : "deptCode_",
			xzqhmc : "deptName_",
			resultCtn : "resultCtn",
			result : "result_"
		},
		page : {
			dslre : "",//层显示位置相关元素 div show location refer element
			gbbm : "roadCode",
			dlmc : "roadName",
			dldj : "roadLevel",
			xqldmc : "rsegName",
			qslc : "startKM",
			jslc : "endKM",
			xzqh : "deptCode",
			xzqhmc : "deptName"
		}
	},
	value : {
		cmpn : {
			gbbm : "",
			dlmc : "",
			xzldmc : "",
			qslc : "",
			jslc : "",
			xzqh : "",
			xzqhmc : ""
		},
		page : {
			gbbm : "",
			dlmc : "",
			xzldmc : "",
			qslc : "",
			jslc : "",
			xzqh : "",
			xzqhmc : ""
		}
	},
	x : "170px",
	y : "166px",
	shtype : {
		value : 0,
		values : ["radio","checkbox"]
	},
	input : {
		struct : ["gbbm","dlmc","qslc","jslc","xzqh","xzqhmc","xqldmc"],
		data : []
	},
	output : {dlmc:"",xqldmc:"",qslc:"",jslc:"",xzqhmc:"",dldj:""},
	initLocation : function(id){
		if(id){
			RoadDept.id.page.dslre = id;
			var e = $(id);
			var l = Event.getXY(e, true);
			RoadDept.x = l.x;
			RoadDept.y = l.y + e.offsetHeight;
		}
	},
	createDiv : function(id){
		var cmpn = RoadDept.id.cmpn;
		if(id){
			cmpn.roadDept = id;
		}
		var div = $(cmpn.roadDept);
		if(!div){
			div = document.createElement("div");
	  		div.id = cmpn.roadDept;
			with(div.style){
				position = "absolute";
				left = RoadDept.x;
				top = RoadDept.y;
				width = "660";
				height = "0px";
				zIndex = "20000";
				backgroundColor = "white";
				border = "1px solid green";
				textAlign = "center";
				overFlow = "visible";
			}
			document.body.appendChild(div);
		}
	},
	initLayout : function(roadCode,roadName, roadLevel, startKM, endKM, rsegName,  deptCode, deptName, shtype){
		
		var cmpn = RoadDept.id.cmpn;
		var page = RoadDept.id.page;
		
		if(roadCode)page.gbbm = roadCode;
		if(roadName)page.dlmc = roadName;
		if(roadLevel)page.dldj = roadLevel;
		if(rsegName)page.xqldmc = rsegName;
		if(startKM)page.qslc = startKM;
		if(endKM)page.jslc = endKM;
		if(deptCode)page.xzqh = deptCode;
		if(deptName)page.xzqhmc = deptName;
		if(shtype && shtype >= 0 && shtype <= RoadDept.shtype.values.length){
			RoadDept.shtype.value = shtype;
		}
	},
	layout : function(){
		var cmpn = RoadDept.id.cmpn;
		var roadDept = $(cmpn.roadDept);
		if(!roadDept){
			return;
		}else if(roadDept.innerHTML){
			return;
		}
		
		var title = '\
			<div style="width:100%;height:16px;margin-bottom:10px;text-align:right;background:#F0FFFF url(../../images/dispatch/cbk.jpg) repeat-x;"\
				onmousemove="RoadDept.move(this,RoadDept.id.cmpn.roadDept)">\
				<img src="../../images/dispatch/close.jpg" style="text-align:right;margin-right:10px;cursor:hand;" onclick="RoadDept.close()" />\
			</div>\
		';
		
		var dtdstyle = 'style="white-space:nowrap;text-align:right;padding-left:10px;"';
		var vtdstyle = 'style="white-space:nowrap;text-align:left;padding-right:10px;"';
		var vstyle = 'style="width:140px;"';
		var vkmstyle = 'style="border:0px;border-bottom:1px solid black;width:55px;text-align:center;"';
		var sdvmn = ["RoadDept.setRadio()","RoadDept.setCkb()"][RoadDept.shtype.value];
		var stdept = '\
			<table border="0" cellspacing="0" cellpadding="0" style="width:0px;height:0px;">\
				<tr>\
					<td '+dtdstyle+'><label>道路名称：</label></td>\
					<td '+vtdstyle+'>\
						<input id="'+cmpn.dlmc+'" type="text" value="" '+vstyle+' />\
						<input id="'+cmpn.dldj+'" type="hidden" value="" '+vstyle+' />\
					</td>\
					<td '+dtdstyle+'><label>路段名称：</label></td>\
					<td '+vtdstyle+'><input id="'+cmpn.xqldmc+'" type="text" value="" '+vstyle+' /></td>\
					<td rowspan="2" align="left" valign="middle">\
						<input id="search" type="button" value="查询" style="margin:5 10 5 0" onclick="RoadDept.searchDept()" /><br>\
					</td>\
				</tr>\
				<tr>\
					<td '+dtdstyle+'><label>起止里程：</label></td>\
					<td '+vtdstyle+'>\
							K<input id="'+cmpn.qslc+'" type="text" value="" '+vkmstyle+' maxlength="6"/>\
							至K<input id="'+cmpn.jslc+'" type="text" value="" '+vkmstyle+' maxlength="6" />\
					</td>\
					<td '+dtdstyle+'><label>机构名称：</label></td>\
					<td '+vtdstyle+'><input id="'+cmpn.xzqhmc+'" type="text" value="" '+vstyle+' /></td>\
				</tr>\
			</table>\
		';
		
		var rtitle = '\
			<span style="margin:10;font-size:12px;color:red;white-space:nowrap;">查询结果</span>\
		';
		
		var result = '\
			<div id="'+cmpn.resultCtn+'" \
				style="width:638px;height:0px;text-align:center;vertical-align: middle;font-size:12px;padding:0 10;margin-bottom:10px;">\
				请输入查询条件后查询！\
			</div>\
		';
		
		var foot = '\
			<div style="width:100%;text-align:left;">\
				<input id="sure" type="button" value="确定" style="margin:5 10 10 20 ;visibility:hidden;" onclick="RoadDept.extend('+sdvmn+');RoadDept.close();" />\
				<input id="cancel" type="button" value="取消" style="margin:5 10 10 0;" onclick="RoadDept.close();" />\
			</div>\
		';
		var content = title + stdept + rtitle + result + foot;
		$(cmpn.roadDept).innerHTML = content;
	},
	getRoadName : function(){
		
	},
	initValue : function(){
		var cmpn = RoadDept.id.cmpn;
		var page = RoadDept.id.page;
		var searchs = ["gbbm","dlmc","dldj","xqldmc","qslc","jslc","xzqh","xzqhmc"];
		var ep,ec;
		for(var i=0;i<searchs.length;i++){
			ep = $(page[searchs[i]]);
			ec = $(cmpn[searchs[i]]);
			if(ep && ec){
				ec.value = ep.value;
			}
		}
		
	},
	searchDept : function(){
		
		var cmpn = RoadDept.id.cmpn;
		var page = RoadDept.id.page;
		var params = RoadDept.output;
		for(var attr in params){
			if($(cmpn[attr])){
				params[attr] = $(cmpn[attr]).value;
			}
		}
		RoadDept.value.cmpn = params;
		
		if(params.dlmc == ""){
			$(cmpn.resultCtn).innerHTML = "请输入道路名称！";
			$(cmpn.dlmc).focus();
			return;
		}
		
		var url = "dispatch.roaddept.getDeptRoad.d";
		new Ajax.Request(url,{
			method:"post",
			parameters : params,
			onComplete : RoadDept.showResponse
		});
	},
	showResponse : function(xmlHttp){
		var result;
		if(xmlHttp.responseText != "null"){
			var xmlDoc = xmlHttp.responseXML;
			RoadDept.saveData(xmlDoc);
			result = RoadDept.showResult();
		}else{
			result =  "未查询到任何数据！";
		}
		$(RoadDept.id.cmpn.resultCtn).innerHTML = result;
		RoadDept.adjustLocation();
	},
	saveData : function(xmlDoc){
		RoadDept.input.data = [];
		var rows = xmlDoc.getElementsByTagName("row"),cols;
		for(var i=0;i<rows.length;i++){
			cols = rows[i].childNodes;
			var record = {};
			for(var j=0;j<RoadDept.input.struct.length;j++){
				record[RoadDept.input.struct[j]] = cols[j].text;
			}
			RoadDept.input.data[i] = record;
		}
	},
	showResult : function(){
		var results = RoadDept.input.data;
		var content = "未查询到任何数据！";
		if(results && results.length >= 1){
			var rthstyle = 'style="width: ';//result table head style 结果表头样式
			var rthdesc = {dlmc:"道路名称",xzqhmc:"机构名称",qslc:"起始里程",jslc:"结束里程",xqldmc:"辖区路段"};
			var rthwidth = {dlmc:"110",xzqhmc:"120",qslc:"60",jslc:"60",xqldmc:"240"};
			
			var rthctn = "";
			rthctn += "<div style=\"font-weight:bold;text-align:center;width:620;\">";
			rthctn += "<span "+rthstyle+"20;\" ></span>";
			for(var attr in rthdesc){
				rthctn += "<span "+rthstyle+rthwidth[attr]+"\" >"+rthdesc[attr]+"</span>";
			}
			rthctn += "</div>";
			
			var rtdctn = "";
			for( var i = 0; i < results.length; i++){
				rtdctn += "<div style=\"width:620px;verticle-align:middle;background-color:"+["#cccccc","#ffffff"][i%2]+"\" >";
				rtdctn += "<input name=\""+RoadDept.id.cmpn.result+"\" type=\""+RoadDept.shtype.values[RoadDept.shtype.value]+"\" value=\""+i+"\" style=\"margin:1;\" />";
				for( var attr in rthdesc){
					rtdctn += "<span "+rthstyle+rthwidth[attr]+"\" >"+results[i][attr]+"</span>";
				}
				rtdctn += "</div>";
			}
			
			var rtctn = rthctn + '\
						<div style="width:0px;height:180px;overflow-x:visible;overflow-y:auto;text-align:center;vertical-align: middle;font-size:12px;">\
							'+rtdctn+'\
						</div>\
					';
			content = rtctn;
			$("sure").style.visibility = "visible";
		}
		return content;
	},
	adjustLocation : function(){
		if(!RoadDept.id.page.dslre && !RoadDept.fsearch){
			var w = document.body.offsetWidth;
			var h = document.body.offsetHeight;
			var rd = $(RoadDept.id.cmpn.roadDept);
			//alert("w:"+w+";h:"+h+";wr:"+rd.offsetWidth+";hr:"+rd.offsetHeight);
			rd.style.left = (w - rd.offsetWidth)/2;
			rd.style.top = (h - rd.offsetHeight)/2;
			RoadDept.fsearch = true;
		}
	},
	setRadio : function(){
		var dindex = Radio.getValue(RoadDept.id.cmpn.result);
		var data;
		if(dindex){
			data = RoadDept.input.data[dindex];
			var page = RoadDept.id.page;
			var cvalue = RoadDept.value.cmpn;
			if($(page.gbbm) && data.gbbm){
				$(page.gbbm).value = data.gbbm;
			}
			
			if($(page.dlmc) && data.dlmc){
				$(page.dlmc).value = data.dlmc;
			}
			if($(page.xqldmc) && cvalue.xqldmc){
				$(page.xqldmc).value = cvalue.xqldmc;
			}
//			if($(page.qslc) && data.qslc){
//				$(page.qslc).value = data.qslc;
//				if(cvalue.qslc && data.qslc < cvalue.qslc){
//					$(page.qslc).value = cvalue.qslc;
//				}
//			}
//			if($(page.jslc) && data.jslc){
//				$(page.jslc).value = data.jslc;
//				if(cvalue.jslc && data.jslc > cvalue.jslc){
//					$(page.jslc).value = cvalue.jslc;
//				}
//			}
			if($(page.xzqh) && data.xzqh){
				$(page.xzqh).value = data.xzqh;
			}
			if($(page.xzqhmc) && data.xzqhmc){
				$(page.xzqhmc).value = data.xzqhmc;
			}
		}
		return data;
	},
	setCkb : function(){
		var dindexs = CheckBox.getValue(RoadDept.id.cmpn.result);
		var dept;
		if(dindexs && dindexs.length > 0){
			dept = RoadDept.input.data[0];
			var depts = {};
			for(var attr in dept){
				depts[attr] = dept[attr];
			}
			for(var i=1;i<dindexs.length;i++){
				var dept = datas[i];
				for(var attr in dept){
					depts[attr] += ","+dept[attr];
				}
			}
			
			var page = RoadDept.id.page;
			if($(page.xzqh) && depts.xzqh){
				$(page.xzqh).value = depts.xzqh;
			}
			if($(page.xzqhmc) && depts.xzqhmc){
				$(page.xzqhmc).value = depts.xzqhmc;
			}
		}
		return dept;
	},
	show : function(sid,id,rc,rn,rl,skm,ekm,rsn,dc,dn,sht){
		var div = $(RoadDept.id.cmpn.roadDept);
		if(div){
			if(div.style.display == "none"){
				div.style.display = "block";
			}else{
				div.style.display = "none";
			}
			return;
		}
		RoadDept.fsearch = false;
		RoadDept.initLocation(sid);
		RoadDept.createDiv(id);
		RoadDept.initLayout(rc,rn,rl,skm,ekm,rsn,dc,dn,sht);
		RoadDept.layout();
		RoadDept.initValue();
		RoadDept.searchDept();
	},
	hide : function(){
		$(RoadDept.id.cmpn.roadDept).style.display = "none";
	},
	close : function(){
		document.body.removeChild($(RoadDept.id.cmpn.roadDept));
	},
	extend : function(data){
		return;
		var msg = $("deptarea");
		if(data && msg){
			msg.style.display = "block";
			msg.innerHTML = data.xzqhmc+"("+data.dlmc+","+data.qslc+"至"+data.jslc + ")";
		}else{
			msg.style.display = "none";
		}
	},
	eventAttr : {
		moveable : false,
		moveCursor : "move",
		dragable : false,
		dragCursor : "cell-resize",
		defCursor : "auto",
		ex : "",
		ey : ""
	},
	move : function(e,em){
		em = $(em);
		if(!em){ em = e};
		var h = e.clientHeight;
		var w = e.clientWidth;
		var x = event.offsetX;
		var y = event.offsetY;
		var xp = event.x;
		var yp = event.y;
		var es = event.srcElement;
		if(x >= 0 && x <= w && y >= 0 && y <= h){
			if(event.button == 1){
				if(RoadDept.eventAttr.moveable == false){
					RoadDept.eventAttr.moveable = true;
					e.style.cursor = RoadDept.eventAttr.moveCursor;
				}else{
					var dx = xp - RoadDept.eventAttr.ex;
					var dy = yp -RoadDept.eventAttr.ey;
					function px(v){var upx = "px"; return parseInt(v.substring(0,v.length-upx.length))};
					with(em.style){
						top = px(top) + dy;
						left = px(left) + dx;
					}
				}
			}else{
				e.style.cursor = RoadDept.eventAttr.defCursor;
				RoadDept.eventAttr.moveable = false;
			}
			RoadDept.eventAttr.ex = xp;
			RoadDept.eventAttr.ey = yp;
		}else{
			e.style.cursor = RoadDept.eventAttr.defCursor;
			RoadDept.eventAttr.moveable = false;
		}
	},
	hideByClk : function(){
		
	}
}

var Event = {
	getXY : function(id,rl){
  		var el = $(id) || id;
  		var l;
  		if(el){
  			l = {x:0,y:0};
  			while(el != document.body){
  				l.x += el.offsetLeft;
  				l.y += el.offsetTop;
  				el = el.offsetParent;
  			}
	  		if(rl){
	  			l.x = l.x + (document.body.clientLeft==0?2:document.body.clientLeft);
				l.y = l.y + (document.body.clientTop==0?2:document.body.clientTop);
	  		}
  		}
  		return l;
	}
}


var Radio = {
	get : function(name) {
		var radio = null;
		var radios = document.getElementsByName(name);
		if (radios != null) {
			for ( var i = 0; i < radios.length; i++) {
				if (radios[i].checked == true) {
					radio = radios[i];
					break;
				}
			}
		}
		return radio;
	},
	getValue : function(name) {
		var value = null;
		var radio = Radio.get(name);
		if (radio != null) {
			value = radio.value;
		}
		return value;
	}
}


var CheckBox = {
	get : function(name) {
		var ckbChked = null;
		var ckbAll = document.getElementsByName(name);
		if (ckbAll != null) {
			ckbChked = [];
			for ( var i = 0; i < ckbAll.length; i++) {
				var temp = ckbAll[i];
				if (temp.checked == true) {
					ckbChked.push(temp);
				}
			}
		}
		return ckbChked;
	},
	getValue : function(name) {
		var values = null;
		var ckbs = CheckBox.get(name);
		if (ckbs != null) {
			values = [];
			for(var i=0;i<ckbs.length;i++){
				values.push(ckbs[i].value);
			}
		}
		return values;
	}
}
