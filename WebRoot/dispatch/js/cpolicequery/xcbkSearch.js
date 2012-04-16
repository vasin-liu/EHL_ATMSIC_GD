
var jgid,jgbh;
function initSearchPage(){
	jgid = $("jgid").value;
	jgbh = $("jgbh").value;
	CarType.init();
	CarColor.init();
}

var SynchFlag = {
//	carNumber : false,
	carType : false,
	carColor : false
}
function setSynchFlag(attr){
	SynchFlag[attr] = true;
}

var CarNumber = {
	init : function(){
		var sql = "select substr(dm,1,1),substr(dm,1,1) from t_sys_code where dmlb = '011006' group by substr(dm,1,1)";
		var carNumber = "carnumberPre";
		fillListBox("carnumberPreDiv", carNumber, "50", sql,"", 
			"CarNumber.callBack('"+carNumber+"')","CarNumber.onchange");
	},
	callBack:function(id){
		setSynchFlag("carNumber");
		search();
	},
	onchange : function(){
		setCarNumber();
	}
}

var CarType = {
	init : function(){
			var sql = "select dm ,dmsm　from t_sys_code where DMlb ='011001'";
			var carType = "cartype";
			fillListBox("cartypeTd", carType, "135", sql, "请选择","CarType.callBack('"+carType+"')","");
	},
	callBack:function(id){
		setSynchFlag("carType");
		searchXcbk();
	},
	onchange : function(){
		
	}
}

var CarColor = {
	init : function(){
			var sql = "select dm ,dmsm　from t_sys_code where DMlb ='011007'";
			var carColor = "carcolor";
			fillListBox("carcolorTd", carColor, "75", sql, "", "CarColor.callBack('"+carColor+"')","CarColor.onchange");	
	},
	callBack:function(id){
		setSynchFlag("carColor");
		searchXcbk();
	},
	onchange : function(){
		
	}
}

function getSql(){
	var carNumber = $("carNumber").value;
	var carType = $("cartype").value;
	var carColor = $("carcolor").value;
	var unit = $("unit").value;
	var sfrtime = $("dateS").value;
	var efrtime = $("dateE").value;
	var whereStr = "";
	if(carNumber != ""){
		whereStr += " and xcbk.carnumber like '%"+carNumber+"%'";
	}
	if(carType != ""){
		whereStr += " and xcbk.cartype = '" + carType + "'";
	}
	if(carColor != ""){
		whereStr += " and xcbk.carcolor = '" + carColor + "'";
	}
	if(unit != ""){
		var length;
		if (unit.substring(2, 4) == "00") {
			length = 2;
		} else if (unit.substring(4, 6) == "00") {
			length = 4;
		} else {
			length = 6;
		}
		whereStr += " and substr(xcbk.frpdcode,1,"+length+") = '" + unit.substring(0,length) + "'";
	}
	if(sfrtime != ""){
		whereStr += " and xcbk.frtime >= to_date('"+sfrtime+"','yyyy-mm-dd')";
	}
	if(efrtime != ""){
		whereStr += " and xcbk.frtime <= to_date('"+efrtime+"','yyyy-mm-dd')";
	}
	//Modified by Liuwx 2011-07-19
//	whereStr += " order by xcbk.id desc";
//	whereStr += " order by 1 desc";
	//Modification finished
	var startColstr = "xcbk.id xid,xcbk.carnumber,f_get_dept(xcbk.frpdcode),to_char(xcbk.frtime,'yyyy-mm-dd hh24:mi:ss'),",
		endColstr = ",decode(xcbk.state,'1','通报中','2','已撤销'),xcbk.state xstate ",
		sendColstr,receiveColstr,dispatchColstr;
	var sendSql,receiveSql,dispatchSql;
	sendColstr = startColstr + "f_get_accdept(xcbk.id,1,2),'无'"+endColstr+",'1',1,'无' adid";
	sendSql = "select " + sendColstr + " from t_oa_xcbk xcbk where xcbk.frpdcode='"+jgid+"'";
	//Modified by Liuwx 2011-07-19
	sendSql += whereStr;
	//Modification finished
	receiveColstr = startColstr + "f_get_dept("+jgid+"),F_GET_DISPATCHDEPT(xcbk.id,'"+jgid+"','无')" +endColstr+",ad.state,2,ad.id adid";
	receiveSql = "select " + receiveColstr + " from t_oa_xcbk xcbk,t_oa_acceptdept ad where xcbk.id=ad.aid and ad.rpdcode='"+jgid+"' and ad.adid is null";
	//Modified by Liuwx 2011-07-19
	receiveSql += whereStr;
	//Modification finished
	dispatchColstr = startColstr + "f_get_dept((select rpdcode from t_oa_acceptdept where id=ad.adid)),f_get_dept('"+jgid+"')"+endColstr+",ad.state,3,ad.id adid";
	dispatchSql = "select " + dispatchColstr + " from t_oa_xcbk xcbk,t_oa_acceptdept ad where xcbk.id=ad.aid and ad.rpdcode='"+jgid+"'" + "and ad.adid is not null" ;
	//Modified by Liuwx 2011-07-19
	dispatchSql += whereStr;
	//Modification finished
	var sql = ""+sendSql+" union " + receiveSql + " union " + dispatchSql + "";
	sql += " order by 1 desc";
	return sql;
}

function searchXcbk() {
	for(var attr in SynchFlag){
		if(!SynchFlag[attr]){
			return;
		}
	}
	var sql = getSql();
	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
			"showResultsPage", "10");
}

function showResultsPage(xmldoc) {
	var stype = "1";
	var coldescs;
	var colwidths = ["8","12","20","15","15","10"];
	var coldescfull = ["协查通报编号","车辆颜色","发送单位","接收单位","转发单位",
		"通报状态","通报状态代码","签收状态代码","单位类型","接收单位编号"];//10
	coldescs = ["号牌号码","发送单位","发送时间","接收单位","转发单位","状态"];
	
	var rows = xmldoc.getElementsByTagName("row");
	
	var results = null;
	var appid = $("appid").value;
	var jgbh = $("jgbh").value;
//	var str = "<div style='text-align:center;width:100%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span></div>";
	var str = "<div style='display:inline'>" + 
					"<div style='text-align:center;width:80%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span>" +
					"</div>" +
					"<div class='lsearch' style='float:right;'><a href='#' onclick=\"showExcelInfo('search','"+jgbh+"')\" class='currentLocation'>" +
					"<span class='lbl'>导出Excel</span></a></div>" +
			  "</div>";
	str += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
	str += "<tr class='titleTopBack'>";
	str += "<td width='5%' class='td_r_b td_font'>序号</td>";
	for(var i=0;i<coldescs.length;i++){
		str += "<td width='"+colwidths[i]+"%' class='td_r_b td_font'>"+coldescs[i]+"</td>";
	}
	str += "<td width='5%' class='td_r_b td_font'>查看</td>";
	if(appid == "1001"){
		str += "<td width='5%' class='td_r_b td_font'>处理</td>";
	}
	str += "</tr>";
	if (rows.length <= 0) {
		str += "<tr class='titleTopBack'>";
		str += "<td class='td_r_b td_font' colspan='10' align='center'>此条件下没有数据</td>";
		str += "</tr>";
	} else {
		var text,length=16;
		var id,stype,state;
		for ( var i = 0; i < rows.length; i++) {
			results = rows[i].childNodes;
			str += "<tr align='center' height='25'>";
			str += "<td width='5%'  class='td_r_b td_font' align=\'center\'>" + (i + 1) + "</td>";
			for(var j=0;j<coldescs.length;j++){
				text = results[j+1].text;
				if(text.length > length){
					text = "<label title='"+text+"'>"+text.substring(0,length)+"</label>";
				}
				str += "<td class='td_r_b td_font'>"+text+"</td>";
			}
			stype = results[9].text;
		
			if(stype == "1"){
				id = results[0].text;
			}else if(stype == "2" || stype == "3"){
				id = results[10].text;
			}

			str +=  "<td width='5%' class='td_r_b td_font' align=\'center\'>" +
						"<img src=\"../../images/button/para.gif\" onClick=\"showMD('2','"+id+"','"+stype+"','900','620')\"  style=\"cursor:hand\"  />" +
					"</td>";
			var dealImgPath = "", methodName="";
			state = results[7].text;
			if(state == "1"){//未解除
				if(jgbh.length != 2 && stype != "1" && results[8].text != "1"){//
					dealImgPath = "update_hover.gif";
					methodName = "alert('已签收！')";
				}else{
					dealImgPath = "update.gif";
					methodName = "showMD('3','"+id+"','"+stype+"','900','620')";
				}
			}else{
				dealImgPath = "update_hover.gif";
				methodName = "alert('协查通报已解除！')";
			}
			if(appid == "1001"){
				str += "<td width='5%' class='td_r_b td_font' align=\'center\'>" +
						"<img src=\"../../images/button/"+ dealImgPath + "\" onClick=\""+methodName+"\"  style=\"cursor:hand\"/>" +
					  "</td>"
			}
			str += "</tr>";
		}
	}
	str += "</table>";
	var pagingObj = $('pageData').up('tr').next('tr').down('td');
	pagingObj.style.textAlign = "center";
	var tabOjb = document.getElementById("pageData");
	tabOjb.innerHTML = str;
	Popup.prototype.hideTips();
}

function showMD(page,id,stype,width,height,flag){
	var params = "&ptype="+page+"&id="+id+"&stype="+stype;
	window.showModalDialog("xcbkAdd.jsp?tmp=" + Math.random()+ params,
		"","dialogWidth:"+width+"px;dialogHeight:"+height+"px");
	if(flag == 'all') {
		doSearchAll();
	} else if(flag == 'xcbk') {
		doSearchXCBKInfo();
	} else {
		if(page == "3"){
			searchXcbk();
		}
	}
}


function showExcelInfo(){
	var sql = getSql();
	var url = "dispatch.xcbk.showExcel.d?" + "searchSql=" + sql;
	url = encodeURI(url);
	window.open(url, "", "height=500,width=800,top=" + 100 + ",left=" + 200
		+ ",toolbar=yes,menubar=yes,"
		+ "scrollbars=yes,resizable=yes,location=no,status=no");
}

function showMoreCondition(){
	if($('moreConditionTr').visible()){
		$('defalutTitleTr').show();
		$('moreConditionTr').hide();
        $('simpleButtomTd').show();
        $('moreButtomTr').hide();
		$('tbdw_lable').hide();
		$('unitDesc').hide();
		$('tbdw_img').hide();
//		$('moreLable').innerHTML = "高级查询";
		clearElementValue();
	}else{
		$('defalutTitleTr').hide();
		$('moreConditionTr').show();
        $('simpleButtomTd').hide();
        $('moreButtomTr').show();
		$('tbdw_lable').show();
		$('unitDesc').show();
		$('tbdw_img').show();
//		$('moreLable').innerHTML = "简单查询";
	}
}

function clearElementValue(){
    G_jgID = "";
    $('unit').value = "";
	$('unitDesc').value = "";
	$('carNumber').value = "";
	$('carColor').options[0].selected = true;
	$('cartype').options[0].selected = true;
}