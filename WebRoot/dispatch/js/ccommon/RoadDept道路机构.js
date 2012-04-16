
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
			xqldmc : "rsegName_",
			qslc : "startKM_",
			jslc : "endKM_",
			xzqh : "deptCode_",
			xzqhmc : "deptName_",
			resultCtn : "resultCtn",
			result : "result_"
		},
		page : {
			gbbm : "roadCode",
			dlmc : "roadName",
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
	stype : {
		value : 0,
		values : ["road","dept"]
	},
	shtype : {
		value : 0,
		values : ["radio","checkbox"]
	},
	input : {
		sroad : {
			struct : ["gbbm","dlmc","xzqhmc","qslc","jslc"],
			data : []
		},
		sdept : {
			struct : ["xzqh","xzqhmc","dlmc","qslc","jslc"],
			data : []
		}
	},
	output : {
		sroad : {xzqhmc:""},
		sdept : {dlmc:"",xqldmc:"",qslc:"",jslc:""}
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
				width = "340px";
				zIndex = "20000";
				backgroundColor = "white";
				border = "1px solid green";
				textAlign = "center";
				overFlow = "visible";
			}
			document.body.appendChild(div);
		}
	},
	initLayout : function(roadCode,roadName, rsegName, startKM, endKM, deptCode, deptName, stype, shtype){
		
		var cmpn = RoadDept.id.cmpn;
		var page = RoadDept.id.page;
		
		if(roadCode)page.gbbm = roadCode;
		if(roadName)page.dlmc = roadName;
		if(rsegName)page.xqldmc = rsegName;
		if(startKM)page.qslc = startKM;
		if(endKM)page.jslc = endKM;
		if(deptCode)page.xzqh = deptCode;
		if(deptName)page.xzqhmc = deptName;
		if(stype && stype >= 0 && stype <= RoadDept.stype.values.length){
			RoadDept.stype.value = stype;
		}
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
		
		var dtdstyle = 'style="white-space:nowrap;text-align:right;padding-left:15px;"';
		var vtdstyle = 'style="white-space:nowrap;text-align:left;padding-left:0px;"';
		var vstyle = 'style="width:169px;"';
		var vkmstyle = 'style="border:0px;border-bottom:1px solid black;width:70px;text-align:center;"';
		var sdvmn = ["RoadDept.setRadio(RoadDept.input.sdept.data)","RoadDept.setCkb(RoadDept.input.sdept.data)"][RoadDept.shtype.value];
		var stdept = '\
			<table border="0" cellspacing="0" cellpadding="0">\
				<tr>\
					<td '+dtdstyle+'><label>道路名称：</label></td>\
					<td '+vtdstyle+'><input id="'+cmpn.dlmc+'" type="text" value="" '+vstyle+' /></td>\
					<td rowspan="3" align="left" valign="middle">\
						<input id="search" type="button" value="查询" style="margin:10" onclick="RoadDept.searchDept()" /><br>\
						<input id="close" type="button" value="确定" style="margin:10" onclick="'+sdvmn+';RoadDept.close();RoadDept.extend()" />\
					</td>\
				</tr>\
				<tr>\
					<td '+dtdstyle+'><label>路段名称：</label></td>\
					<td '+vtdstyle+'><input id="'+cmpn.xqldmc+'" type="text" value="" '+vstyle+' /></td>\
				</tr>\
				<tr>\
					<td '+dtdstyle+'><label>起止里程：</label></td>\
					<td '+vtdstyle+'>\
							K<input id="'+cmpn.qslc+'" type="text" value="" '+vkmstyle+' maxlength="6"/>\
							至K<input id="'+cmpn.jslc+'" type="text" value="" '+vkmstyle+' maxlength="6" />\
					</td>\
				</tr>\
			</table>\
		';
		
		var srvmn = ["RoadDept.setRadio(RoadDept.input.sroad.data)",
			"RoadDept.setCkb(RoadDept.input.sroad.data)"][RoadDept.shtype.value];
		var stroad ='\
			<table border="0" cellspacing="0" cellpadding="0">\
				<tr>\
					<td '+dtdstyle+'><label>机构名称：</label></td>\
					<td '+vtdstyle+'><input id="'+cmpn.xzqhmc+'" type="text" value="" '+vstyle+' /></td>\
					<td style="padding:0 6;">\
						<input id="search" type="button" value="查询"  onclick="RoadDept.searchRoad()" />\
						<input id="close" type="button" value="确定"  onclick="'+srvmn+';RoadDept.close();RoadDept.extend()" />\
					</td>\
				</tr>\
			</table>\
		';
		
		var rtitle = '\
			<span style="color:red">查询结果</span>\
		';
		
		var result = '\
			<div id="'+cmpn.resultCtn+'" \
			style="position:relative;height:80px;margin-bottom:10px;overflow:auto;text-align:center;vertical-align: middle;">\
			请输入查询条件后查询！</div>\
		';
		
		var content = title + [stdept,stroad][RoadDept.stype.value] + rtitle + result;
		$(cmpn.roadDept).innerHTML = content;
	},
	initValue : function(){
		var cmpn = RoadDept.id.cmpn;
		var page = RoadDept.id.page;
		var searchs = ["gbbm","dlmc","xqldmc","qslc","jslc","xzqh","xzqhmc"];
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
		var params = RoadDept.output.sdept;
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
		
		var url = "dispatch.roaddept.getDeptByRoad.d";
		new Ajax.Request(url,{
			method:"post",
			parameters : params,
			onComplete : RoadDept.showDeptResponse
		});
	},
	showDeptResponse : function(xmlHttp){
		var result;
		if(xmlHttp.responseText != "null"){
			var xmlDoc = xmlHttp.responseXML;
			RoadDept.saveData(xmlDoc,RoadDept.input.sdept);
			result = RoadDept.showDept();
		}else{
			result =  "未查询到任何数据！";
		}
		$(RoadDept.id.cmpn.resultCtn).innerHTML = result;
	},
	saveData : function(xmlDoc, data){
		data.data = [];
		var rows = xmlDoc.getElementsByTagName("row"),cols;
		for(var i=0;i<rows.length;i++){
			cols = rows[i].childNodes;
			var record = {};
			for(var j=0;j<data.struct.length;j++){
				record[data.struct[j]] = cols[j].text;
			}
			data.data[i] = record;
		}
	},
	showDept : function(){
		var results = RoadDept.input.sdept.data;
		var content = "未查询到任何数据！";
		if(results && results.length >= 1){
			content = "<table>";
			var result;
			for(var i=0;i<results.length;i++){
				result= results[i];
				content += "<tr>";
				content += "<td style=\"white-space:nowrap;text-align:right;\">";
				content += "<label>"+result.xzqhmc+"("+result.dlmc+","+result.qslc+"至"+result.jslc+")"+"</label>";
				content += "<input name=\""+RoadDept.id.cmpn.result+"\" type=\""+RoadDept.shtype.values[RoadDept.shtype.value]+"\" value=\""+i+"\" />";
				content += "</td>";
				content += "</tr>";
			}
			content += "</table>";
		}
		return content;
	},
	setRadio : function(datas){
		var dindex = Radio.getValue(RoadDept.id.cmpn.result);
		if(dindex){
			var data = datas[dindex];
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
			if($(page.qslc) && data.qslc){
				$(page.qslc).value = data.qslc;
				if(cvalue.qslc && data.qslc < cvalue.qslc){
					$(page.qslc).value = cvalue.qslc;
				}
			}
			if($(page.jslc) && data.jslc){
				$(page.jslc).value = data.jslc;
				if(cvalue.jslc && data.jslc > cvalue.jslc){
					$(page.jslc).value = cvalue.jslc;
				}
			}
			if($(page.xzqh) && data.xzqh){
				$(page.xzqh).value = data.xzqh;
			}
			if($(page.xzqhmc) && data.xzqhmc){
				$(page.xzqhmc).value = data.xzqhmc;
			}
		}
	},
	setCkb : function(datas){
		var dindexs = CheckBox.getValue(RoadDept.id.cmpn.result);
		if(dindexs && dindexs.length > 0){
			var dept = datas[0];
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
	},
	searchRoad : function(){
		
		var page = RoadDept.id.page;
		var cmpn = RoadDept.id.cmpn;
		
		var params = RoadDept.output.sroad;
		for(var attr in params){
			if($(cmpn[attr])){
				params[attr] = $(cmpn[attr]).value;
			}
		}
		
		RoadDept.value.cmpn = params;
		
		if(params.xzqhmc == ""){
			$(cmpn.resultCtn).innerHTML = "请输入机构名称！";
			$(cmpn.xzqhmc).focus();
			return;
		}
		
		var url = "dispatch.roaddept.getRoadByDept.d";
		new Ajax.Request(url,{
			method:"post",
			parameters : params,
			onComplete : RoadDept.showRoadResponse
		});
	},
	showRoadResponse : function(xmlHttp){
		var result;
		if(xmlHttp.responseText != "null"){
			var xmlDoc = xmlHttp.responseXML;
			RoadDept.saveRoad(xmlDoc);
			result = RoadDept.showRoad();
		}else{
			result =  "未查询到任何数据！";
		}
		$(RoadDept.id.cmpn.resultCtn).innerHTML = result;
	},
	saveRoad : function(xmlDoc){
		var road = RoadDept.input.sroad;
		road.data = [];
		var rows = xmlDoc.getElementsByTagName("row"),cols;
		for(var i=0;i<rows.length;i++){
			cols = rows[i].childNodes;
			var record = {};
			for(var j=0;j<road.struct.length;j++){
				record[road.struct[j]] = cols[j].text;
			}
			road.data[i] = record;
		}
	},
	showRoad : function(){
		var results = RoadDept.input.sroad.data;
		var content = "未查询到任何数据！";
		if(results && results.length >= 1){
			content = "<table>";
			var result;
			for(var i=0;i<results.length;i++){
				result= results[i];
				content += "<tr>";
				content += "<td style=\"white-space:nowrap;text-align:right;\">";
				content += "<label>"+result.dlmc+"("+result.xzqhmc+","+result.qslc+"至"+result.jslc+")"+"</label>";
				content += "<input name=\""+RoadDept.id.cmpn.result+"\" type=\""+RoadDept.shtype.values[RoadDept.shtype.value]+"\" value=\""+i+"\" />";
				content += "</td>";
				content += "</tr>";
			}
			content += "</table>";
		}
		return content;
	},
	show : function(id,rc,rn,rsn,skm,ekm,dc,dn,st,sht){
		var div = $(RoadDept.id.cmpn.roadDept);
		if(div){
			if(div.style.display == "none"){
				div.style.display = "block";
			}else{
				div.style.display = "none";
			}
			return;
		}
		RoadDept.createDiv(id);
		RoadDept.initLayout(rc,rn,rsn,skm,ekm,dc,dn,st,sht);
		RoadDept.layout();
		RoadDept.initValue();
		$("search").click();
	},
	hide : function(){
		$(RoadDept.id.cmpn.roadDept).style.display = "none";
	},
	close : function(){
		document.body.removeChild($(RoadDept.id.cmpn.roadDept));
	},
	extend : function(){
		
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
