/**
 *author:wangxt
 *desc:编辑交通拥堵信息
 *date:2010-1-11
*/

function doOnLoad(){
    window.open("http://www.baidu.com");
    return;
	var jgbh = $("jgbh").value;
	//var sql = "select tod.BH,tod.DLBH,tod.DLMC,(select jgmc from t_sys_department tsd where tsd.jgid=tod.JGID) from T_OA_DICT_ROAD tod order by tod.JGID";
	var sql = "select taa.ALARMID,taa.REPORTPERSON,taa.REPORTUNIT,taa.REPORTTIME,";
	sql +=" taa.ROADNAME,tcc.DRIVERDIRECTION,tcc.TRAFFICSTATE,tcc.CROWDREASON,tcc.STARTADDRESS,";
	sql +=" tcc.ENDADDRESS,tcc.DEALSTATE"
	sql +=" from T_ATTEMPER_ALARM taa,T_ATTEMPER_CONGESTION tcc"
	sql +=" where taa.ALARMID=tcc.ALARMID";
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","10");
}
function showResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	
	var str = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
		str += "<tr class='titleTopBack'>";
		str += "<td width='8%' class='td_r_b td_font'>上报人</td>";
	    str += "<td width='10%' class='td_r_b td_font'>上报单位</td>";
		str += "<td width='10%' class='td_r_b td_font'>上报时间</td>";
		str += "<td width='12%' class='td_r_b td_font'>路段名</td>";
		str += "<td width='8%' class='td_r_b td_font'>方向</td>";
		str += "<td width='13%' class='td_r_b td_font'>交通状况</td>";
		str += "<td width='10%' class='td_r_b td_font'>拥堵原因</td>";
		str += "<td width='10%' class='td_r_b td_font'>起止里程</td>";
		str += "<td width='8%' class='td_r_b td_font'>维持状态</td>";
		
		str += "<td width='6%' class='td_r_b td_font'>明细</td>";
		str += "<td width='6%' class='td_r_b td_font'>修改</td>";	
		str += "<td width='6%' class='td_r_b td_font'>删除</td>";
	    str += "</tr>";
	    if(rows.length<=0){
		   str += "<tr class='titleTopBack'>";
		   str += "<td class='td_r_b td_font' colspan='10' align='center'>此条件下没有数据</td>";
		   str += "</tr>";
	    }else{
	      for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			str += "<tr align='center'>";
			str += "<td width='8%' class='td_r_b td_font' align=\'center\'>"+(results[1].text=="null"?"":results[1].text)+"</td>";
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'>"+(results[2].text=="null"?"":results[2].text)+"</td>";
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'>"+(results[3].text=="null"?"":results[3].text)+"</td>";
			str += "<td width='12%' class='td_r_b td_font' align=\'center\'>"+(results[4].text=="null"?"":results[4].text)+"</td>";
			str += "<td width='8%' class='td_r_b td_font' align=\'center\'>"+(results[5].text=="null"?"":results[5].text)+"</td>";
			str += "<td width='13%' class='td_r_b td_font' align=\'center\'>"+(results[6].text=="null"?"":results[6].text)+"</td>";
            str += "<td width='10%' class='td_r_b td_font' align=\'center\'>"+(results[7].text=="null"?"":results[7].text)+"</td>";
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'>"+(results[8].text=="null"?"":results[8].text)+(results[9].text=="null"?"":results[9].text)+"</td>";
		    str += "<td width='8%' class='td_r_b td_font' align=\'center\'>"+(results[10].text=="null"?"":results[10].text)+"</td>";
			
			str += "<td width='6%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"+results[0].text+"')\"></td>"
			str += "<td width='6%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"')\"></td>"
			str += "<td width='6%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"')\"></td>"
			str += "</tr>";			
	        }
	      }
		str +="</table>";
		var pagingObj = $('pageData').up('tr').next('tr').down('td');
		pagingObj.style.textAlign = "center";
		var tabOjb = document.getElementById("pageData");
		tabOjb.innerHTML = str;
		Popup.prototype.hideTips();
}


function editTrfficeCrowdInfo() {
    
    var params={};
    params["alarmid_TrafficCrowd"] = $("alarmid_TrafficCrowd").value;
	params["reportPerson_TrafficCrowd"] = $('reportPerson_TrafficCrowd').value ;
	params["feedBackTime_TrafficCrowd"]  = $('feedBackTime_TrafficCrowd').value ;
	params["feedBackunit_TrafficCrowd"]  = $('feedBackunit_TrafficCrowd').value ;
	
	
	params["alarmRoad_TrafficCrowd"]  = $('alarmRoad_TrafficCrowd').value ;
	params["direction_TrafficCrowd"] = $('direction_TrafficCrowd').value;
	params["startaddress_TrafficCrowd"] = $('startaddress_TrafficCrowd').value; 
    params["endaddress_TrafficCrowd"] = $('endaddress_TrafficCrowd').value;
    params["trafficstate_TrafficCrowd"] = $('trafficstate_TrafficCrowd').value;
    params["trafficreason_TrafficCrowd"] = $('trafficreason_TrafficCrowd').value;
    params["starttime_TrafficCrowd"] = $('starttime_TrafficCrowd').value;
    params["endtime_TrafficCrowd"] = $('endtime_TrafficCrowd').value;
    params["desc_TrafficCrowd"] = $('desc_TrafficCrowd').value;
    
    params["closestarttime_TrafficCrowd"] = $('closestarttime_TrafficCrowd').value;
    params["closeendtime_TrafficCrowd"] = $('closeendtime_TrafficCrowd').value;
    //params["alarmDept"]=$("alarmDept").deptid;
    params["alarmDesc_RoadBuild"] = $('alarmDesc_RoadBuild').value;
    var url = "dispatch.trafficEdit.editTrafficRoadInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:editTrfficeCrowdInfoRes});
}


function editTrfficeCrowdInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc=="success"){
		alert("上报成功!");
		clearInfo();
		$("saveData").disabled=true;
		watching.readNoticedAffair();
	}else{
		alert("上报失败!");
	}
}

/**
    根据警情编号，事件类型查询详细信息
*/
function getTrafficCrowdInfo(alarmid) {
	if(alarmId!=""){	
		var url = "dispatch.trafficEdit.getgetTrafficInfo.d";	
		params="alarmid="+alarmId;
		url = encodeURI(url);		
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:getTrafficInfoRes});
	}
}
/**
   
*/
function getTrafficInfoRes() {
    
   
    
   
}


/** 
    * desc:新增或查询的跳转
    * param:
    * return:
    * author：wkz
    * date: 2010-1-12
    * version:
    */
function onButtonClick(itemId, itemValue) {
	var id = itemId;
	if (id == "new") {
		var returnValuestr = window.showModalDialog("TrafficCrowd.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0&bh=''", "", "dialogWidth:800px;dialogHeight:600px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "edit") {
		var returnValuestr = window.showModalDialog("TrafficCrowd.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&bh='" + itemValue + "'", "", "dialogWidth:800px;dialogHeight:600px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("TrafficCrowd.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&bh='" + itemValue + "'", "", "dialogWidth:800px;dialogHeight:600px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "search") {
		doSearch(itemValue);
	}
	if (id == "delete") {
		doDelete(itemValue);
	}
}


/** 
    * desc:通过参数显示新增或修改的页面
    * param:
    * return:
    * author：wkz
    * date: 2010-1-12
    * version:
    */
function doQuery(bh) {
	if (bh != "") {
		var url = "drpt.violationmanage.getDataById.d";
		url = encodeURI(url);
		var params = "bh=" + bh;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse});
	}
}
function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	var BH 		= document.getElementById("BH");
	var HPHM 	= document.getElementById("HPHM");
	var WFZL 	= document.getElementById("WFZL");
	var CSDW 	= document.getElementById("CSDW");
	var CCSJ 	= document.getElementById("CCSJ");
	var CCLD 	= document.getElementById("CCLD");
	var JSR 	= document.getElementById("JSR");
	var JSZ 	= document.getElementById("JSZ");
	var CLQK 	= document.getElementById("CLQK");
	var HZRS 	= document.getElementById("HZRS");
	var SZRS 	= document.getElementById("SZRS");
	BH.value  	= results[0].text;
	HPHM.value 	= results[1].text;
	fillListBox("WFZL_TD","WFZL","270","select ID,NAME from T_ATTEMPER_CODE where ID LIKE '024%'","请选择","onChange('WFZL','"+results[2].text+"')");
	//WFZL.value 	= results[2].text;
	CSDW.value 	= results[3].text;
	CCSJ.value 	= results[4].text;
	CCLD.value 	= results[5].text;
	JSR.value 	= results[6].text;
	JSZ.value 	= results[7].text;
	CLQK.value 	= results[8].text;
	HZRS.value 	= results[9].text;
	SZRS.value 	= results[10].text;
}









