/**
 *说明：警情查询类
 *创建日期：2010-01-17
 */
//var swsg=false;
//var yydksg=false;
//var whsg=false;
//var swsg=false;
//var jjsg=false;
//var isCheck=false;
//查询警情
function doQuery(param,dateType){
	var sqlStr = "SELECT T.ALARMID,T.EVENTTYPE,TO_CHAR(T.ALARMTIME,'YYYY-MM-DD HH24:MI'),T1.NAME AS TYPENAME,T.ALARMREGION,T.TITLE,T3.NAME AS STATENAME ";
	sqlStr += "FROM T_ATTEMPER_ALARM T, T_ATTEMPER_CODE T1, T_ATTEMPER_CODE T3 WHERE T.EVENTTYPE = T1.ID AND T.EVENTSTATE = T3.ID ";
	if(!param){
		var policeType = $("policeType")==null?'':$("policeType").value;
		var startTime = $("startTime")==null?'':$("startTime").value;
		var endsTime = $("endsTime")==null?'':$("endsTime").value;
		var policeState = $("policeState")==null?'':$("policeState").value;
		
		
		if(policeType){
			sqlStr += "AND T.EVENTTYPE='" + policeType + "' ";
		}
		if(startTime && endsTime){
			sqlStr += "AND TO_CHAR(T.ALARMTIME,'YYYY-MM-DD HH24:MI')>='" + startTime + "' AND TO_CHAR(T.ALARMTIME,'YYYY-MM-DD HH24:MI')<='" + endsTime + "'";
		}else if(startTime && !endsTime ){
			sqlStr += "AND TO_CHAR(T.ALARMTIME,'YYYY-MM-DD HH24:MI')>='" + startTime + "' ";
		}else if(!startTime && endsTime ){
			sqlStr += "AND TO_CHAR(T.ALARMTIME,'YYYY-MM-DD HH24:MI')<='" + endsTime + "' ";
		}
		if(policeState){
			sqlStr += "AND T.EVENTSTATE='" + policeState +"' ";
		}
		var daduiId = $("daduiId");
		if(daduiId && daduiId.value != ""){
			sqlStr += "AND T.ALARMREGIONID='" + daduiId.value +"' ";
		}
		
		var zhiduiId = $("zhiduiId");
		if(zhiduiId && zhiduiId.value != "" && daduiId.value == ""){
			sqlStr += "AND T.ALARMREGIONID LIKE '" + deptcode.substring(0,4) +"%'";
		}
	}else if(param=='init'){
//		yydksg=false;
//		whsg=false;
//		swsg=false;
//		jjsg=false;
//		isCheck=false;
		var startTime = getSysdate(24*60,false);
		$("startTime").value=startTime;
		var endsTime = getSysdate(0,false);
		$("endsTime").value=endsTime;
		sqlStr += "AND TO_CHAR(T.ALARMTIME,'YYYY-MM-DD HH24:MI')>='" + startTime + "' AND TO_CHAR(T.ALARMTIME,'YYYY-MM-DD HH24:MI')<='" + endsTime + "'";
		if(depttype == "1"){
			sqlStr += "AND T.ALARMREGIONID LIKE '" + deptcode.substring(0,4) +"%'";
		}else if(depttype == "2"){
			sqlStr += "AND T.ALARMREGIONID='" + deptcode +"'";
		}
	}else if(param=='check'){
		sqlStr += "AND T.EVENTLEVEL='006001'";
		if(dateType=="DD"){
			sqlStr += "AND TRUNC(SYSDATE,'DD')=TRUNC(T.ALARMTIME, 'DD') ";
		}else if(dateType=="MM"){
			sqlStr += "AND TRUNC(SYSDATE,'MM')=TRUNC(T.ALARMTIME, 'MM') ";
		}
	}
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sqlStr),"showResultsPage","12");
}
//查询警情列表反馈
function showResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	
	var str = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
	str += "<tr class='titleTopBack'>";
	str += "<th width='8%' class='td_r_b td_font'>警情类型</th>";
	str += "<th width='14%' class='td_r_b td_font'>上报时间</th>";
	str += "<th width='15%' class='td_r_b td_font'>上报机构</th>";
	str += "<th width='28%' class='td_r_b td_font'>警情摘要</th>";
	str += "<th width='10%' class='td_r_b td_font'>警情状态</th>";
	str += "<th width='5%' class='td_r_b td_font'>明细</th>";
	str += "<th width='5%' class='td_r_b td_font'>修改</th>";
	str += "<th width='5%' class='td_r_b td_font'>删除</th>";
    str += "</tr>";
    if(!rows || rows.length <= 0){
	   str += "<tr class='titleTopBack'>";
	   str += "<td class='td_r_b td_font' colspan='8' align='center'>没有数据, 请选择其他条件重新查询！</td>";
	   str += "</tr>";
    }else if(rows && rows.length>0){
    	var results = null;
    	var policeId=null,policeTypeNum=null,alarmTime=null,policeType=null,reportUnit=null,policeTitle=null,policeState=null;
		for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			policeId = results[0].text;
			policeTypeNum = results[1].text;
			alarmTime = results[2].text;
			policeType = results[3].text;
			reportUnit = results[4].text;
			policeTitle = results[5].text;
			policeState = results[6].text;
			
			str += "<tr align='center' height=20>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+policeType+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+alarmTime+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+reportUnit+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+policeTitle+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+policeState+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'><input type=\"image\" title='查看警情' src=\"../../images/button/para.gif\" onClick=\"viewPolice('"+policeId+"','"+policeTypeNum+"')\"></td>"
			str += "<td class='td_r_b td_font' align=\'center\'><input type=\"image\" title='编辑警情' src=\"../../images/button/update.gif\" onClick=\"editPolice('"+policeId+"','"+policeTypeNum+"')\"></td>"
			str += "<td class='td_r_b td_font' align=\'center\'><input type=\"image\" title='删除警情' src=\"../../images/button/btndelete1.gif\" onClick=\"delPolice('"+policeId+"','"+policeTypeNum+"')\"></td>"
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

/**
 * 函数说明：展示警情详细信息
 * 参数：policeId，警情编号；policeType 警情类型
 */
function viewPolice(policeId,policeType){
	if(!policeId || !policeType){
		alert("操作失败，请刷新后重试！");
		return false;
	}
	switch (policeType) {
		case '001001'://交通事故
			var urlStr = "../../ehl/policeEdit/accidentEdit.jsp?alarmId="+policeId +"&depttype="+ depttype+"&deptcode="+ deptcode+"&showtype=0";
			var iLeft = (screen.Width-660)/2;
			var iTop = (screen.Height-500)/2;
			window.open(urlStr,"","height=500,width=660,left="+iLeft+",top="+iTop+",scrollbars=yes");
		   	break;
		case '001002':// 交通拥堵
			
		   	break;
		case '001022': //交通管制
		 	
			break;
		case '001023'://施工占道
		    
		    break; 
	}
}

/**
 * 函数说明：编辑警情详细信息
 * 参数：policeId，警情编号；policeType 警情类型
 */
function editPolice(policeId,policeType){
	if(!policeId || !policeType){
		alert("操作失败，请刷新后重试！");
		return false;
	}
	
	switch (policeType) {
		case '001001'://交通事故
			var urlStr = "../../ehl/policeEdit/accidentEdit.jsp?alarmId="+policeId +"&depttype="+ depttype+"&deptcode="+ deptcode+"&showtype=1";
			var iLeft = (screen.Width-660)/2;
			var iTop = (screen.Height-500)/2;
			window.open(urlStr,"","height=500,width=670,left="+iLeft+",top="+iTop+",scrollbars=yes");
		   	break;
		case '001002':// 交通拥堵
			
		   	break;
		case '001007': //交通管制
		 	var urlStr = "../../ehl/policeEdit/TrafficContrl.jsp?alarmId="+policeId +"&depttype="+ depttype+"&deptcode="+ deptcode;
			window.open(urlStr,"","height=400,width=650,left="+eval(screen.Width-800)/2+",top="+eval(screen.Height-560)/2+"");
			break;
		case '001008'://施工占道
		    var urlStr = "../../ehl/policeEdit/RoadBuild.jsp?alarmId="+policeId +"&depttype="+ depttype+"&deptcode="+ deptcode;
			window.open(urlStr,"","height=500,width=650,left="+eval(screen.Width-800)/2+",top="+eval(screen.Height-560)/2+"");
		    break; 
	}
}

/**
 * 函数说明：删除警情信息
 * 参数：alarmId，警情编号；policeType 警情类型
 */
function delPolice(alarmId,policeType){
	if(!alarmId){
		alert("操作失败，请刷新后重试！");
		return false;
	}
	var param = "alarmId="+alarmId+"&policeType="+policeType;
	var url = "dispatch.accident.delPoliceInfo.d";	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:param, onComplete:showDelRes});
}
//根据后台得到的数据，初始化页面数据项
function showDelRes(xmlHttp){	
	var xmlDoc = xmlHttp.responsetext;		
	if(xmlDoc=="1"){
		alert('删除成功！');
		doQuery();
	}else{
		alert('删除失败，请刷新后重试！');
	} 
}
/**
 * 函数说明：判断是否具有相关操作权限
 * 参数：type 操作类型
 */
function checkPriv(type){
}

//大队用户
function daduiCallback(jgid){
	$("daduiId").value=jgid;
	$("daduiId").disabled=true;
}

//支队用户
function zhiduiCallback(jgid){
	$("zhiduiId").value=jgid;
	$("zhiduiId").disabled=true;
	var zhidui=jgid.substring(0,4);
	fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,1,4)='"+zhidui+"'","","");
}
function zongduiOnChange(){
	var zhidui=$("zhiduiId").value;
	if(zhidui==""){
		fillListBox("daduiTdId","daduiId","170","select jgid,jgmc from t_sys_department where substr(jgid,5,2)<>'00'","","");
	}else{
		zhidui=zhidui.substring(0,4);
		var sql="select jgid,jgmc from t_sys_department where substr(jgid,0,4)='"+zhidui+"'"
		fillListBox("daduiTdId","daduiId","170",sql,"","");
	}
}