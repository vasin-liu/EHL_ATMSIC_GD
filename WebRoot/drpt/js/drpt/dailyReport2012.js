
var _mark = false;

function doOnLoad(itemValue, mark){
	if(!itemValue) itemValue = $('jgbh').value;
	_mark = mark?true:false;
	doSearch(itemValue);
}

function doSearch(itemValue){
	var _jgbh = '';
	if($('xz')){
		_jgbh = itemValue.substring(0,6)
	}else{
		_jgbh = itemValue.substring(0,6).replace(/0+$/,'');
		if(_jgbh.length%2 !== 0){
			_jgbh = _jgbh + '0';
		}
	}
	
	var tbr = $('tbr').value;
	var sj1 = $('sj1').value;
	var sj2 = $('sj2').value;
	var zhiduiId = "";
	var daduiId = "";
	
	var zhiduiEle = $('zhiduiId');
	var daduiEle = $('daduiId');
	if(zhiduiEle)
		zhiduiId = zhiduiEle.value;
	if(daduiEle)
		daduiId = daduiEle.value;
	
	var sql = "select rzbh,(select jgmc from t_sys_department where jgid=tbdw) as tbdw,";
	sql += "to_char(tjrq,'yyyy-mm-dd') as tjrq,tbr,shr,lxdh,(case when sysdate-to_date(";
	sql += "substr(rzbh,7,14),'yyyyMMddHH24miSS')>1 then 0 else 1 end),to_char(to_date(";
	sql += "substr(rzbh,7,14),'yyyy-MM-dd HH24:mi:ss'),'yyyy-MM-dd HH24:mi:ss') from ";
	sql += "t_oa_dayreport where RZBH LIKE '" + _jgbh + "%'";
	
	if(tbr)
		sql += " and tbr like '%" + tbr + "%'";
	
	if(sj1&&sj2){
		sql += " AND TJRQ BETWEEN TO_DATE('" + sj1 + "','YYYY-MM-DD') AND TO_DATE('" + sj2
			+ "','YYYY-MM-DD')";
	}else if(sj1&&!sj2){
		sql += " AND TJRQ>=TO_DATE('" + sj1 + "','YYYY-MM-DD')";
	}else if(!sj1&&sj2){
		sql += " AND TJRQ<=TO_DATE('" + sj2 + "','YYYY-MM-DD')";
	}
	
	if(zhiduiId && daduiId)
		sql += " and  TBDW = '" + daduiId + "'";
	else if(zhiduiId)
		sql += " and  TBDW LIKE '" + zhiduiId.substring(0,4) + "%'";
	else if(daduiId)
		sql += " and  TBDW = '" + daduiId + "'";
	
	sql += " order by rzbh,tjrq desc";
	
//	prompt(sql,sql);
	
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","12");
}

function showResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	var str = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
	str += "<tr class='titleTopBack'>";
    str += "<th width='16%' class='td_r_b td_font'>填报单位</th>";
	str += "<th width='8%' class='td_r_b td_font'>统计日期</th>";
	str += "<th width='6%' class='td_r_b td_font'>填表人</th>";
	str += "<th width='6%' class='td_r_b td_font'>审核人</th>";
	str += "<th width='8%' class='td_r_b td_font'>联系电话</th>";
	str += "<th width='12%' class='td_r_b td_font'>填表时间</th>";
	str += "<th width='3%' class='td_r_b td_font'>明细</th>";
	str += "<th width='3%' class='td_r_b td_font'" + (_mark?" style='display:none;'":"") + ">修改</th>";	
	str += "<th width='3%' class='td_r_b td_font' style='display:none'>删除</th>";
    str += "</tr>";
    if(rows.length<=0){
	   str += "<tr class='titleTopBack'>";
	   str += "<td class='td_r_b td_font' colspan='9' align='center'>此条件下没有数据</td>";
	   str += "</tr>";
    }else{
	    for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			str += "<tr align='center'>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+results[1].text+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+results[2].text+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+(results[3].text!='null'?results[3].text:"")+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+(results[4].text!='null'?results[4].text:"")+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+(results[5].text!='null'?results[5].text:"")+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'>"+(results[7].text!='null'?results[7].text:"")+"</td>";
			str += "<td class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"+results[0].text+"')\"></td>"
		
			str += "<td class='td_r_b td_font' align=\'center\'" + (_mark?" style='display:none;'":"") + ">";
			if(results[6].text!='1'){
				str += "<input type=\"image\"  disabled  src=\"../../images/button/update_dis.gif\">";
			}else{
				str += "<input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"','"+results[2].text+"')\">";
			}
			str += "</td>";
			str += "<td class='td_r_b td_font' align=\'center\' style='display:none'><input type=\"image\"" + (results[6].text!='1'?" disabled":"") + " src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"')\"></td>"
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
 
function showModify(){
//	window.showModalDialog("dailyReport2012.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0", self, "dialogWidth:710px;dialogHeight:724px;");
    //update by Jason 20100204 应客户要求改为open窗口。
	window.open("dailyReport2012.jsp?tmp="+Math.random()+"&insrtOrUpdt=0","newwindow","height=580px,width=710px,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no,top=100,left=300")
	doOnLoad();
}

function editCallBack(){
	var jgid = $('tbdwid').value;
	var tjy = $('tjy').value;
	var tjr = $('tjr').value;
	var drpTime = tjy + '-' + tjr;
	var tempJgid = '';
	if(jgid.substring(2,6)=="0000"){        //总队用户
		tempJgid = " AND rzbh like '" + jgid.substring(0,2)+"__00%'";	    //支队，大队都选择的是“全部”

	}else if(jgid.substring(4,6)=="00"){	//支队用户
		tempJgid = " AND rzbh like '" + jgid.substring(0,4) + "__%'"; //选择的为支队下的全部大队
	}else{                                  //大队用户
		setFlowAdd();
		setPieceDd();
		setFlowDds();
		return;
	}
	
	var url = "drpt.dailyRptModify.infoCalBack.d";
	url = encodeURI(url);

	var params= "?startTime="+drpTime+"&endTime="+drpTime+"&departmentId="+tempJgid;
	params = encodeURI(params);
	new Ajax.Request(url, {method: 'post', parameters: params, onComplete: function(xmlHttp){
		var xmlDoc = xmlHttp.responseXML;
		var rows = xmlDoc.getElementsByTagName("row");
		
		var results = rows[0].childNodes;
		
		var TRJL = $('TRJL'); //投入警力（人次）
		TRJL.value = results[0].text!="　"?results[0].text:'0';
		var CDJC = $('CDJC'); //出动警车（辆次）
		CDJC.value = results[1].text!="　"?results[1].text:'0';
		var CSSB = $('CSSB');//投入测速设备（台）
		CSSB.value = results[2].text!="　"?results[2].text:'0';
		var CSD = $('CSD');
		CSD.value = results[47].text == "　"?"0":results[47].text;
		var GDCSD = $('GDCSD');//设置固定测速点（个）
		GDCSD.value = results[3].text!="　"?results[3].text:'0';
		var LDCSD = $('LDCSD');//设置流动测速点（个）

		LDCSD.value = results[4].text!="　"?results[4].text:'0';
		var ZQFWD = $('ZQFWD');//设置春运执勤服务点（个）
		ZQFWD.value = results[5].text!="　"?results[5].text:'0';
		var JTWFHJ = $('JTWFHJ');//查处交通违法行为合计（起）
		JTWFHJ.value = results[6].text!="　"?results[6].text:'0';
		var CSXS = $('CSXS');//查处超速行驶（起）
		CSXS.value = results[7].text!="　"?results[7].text:'0';
		var KCJY = $('KCJY');//查处客车超员（起）

		KCJY.value = results[8].text!="　"?results[8].text:'0';
		var PLJS = $('PLJS');//查处疲劳驾驶（起）

		PLJS.value = results[9].text!="　"?results[9].text:'0';
		var JHJS = $('JHJS');//查处酒后驾驶（起）

		JHJS.value = results[10].text!="　"?results[10].text:'0';
		var WZJS = $('WZJS');//查处无证驾驶（起）

		WZJS.value = results[11].text!="　"?results[11].text:'0';
		var NYCWFZK = $('NYCWFZK');//查处农用车违法载客（起）
		NYCWFZK.value = results[12].text!="　"?results[12].text:'0';
		var DXJDCJSZ = $('DXJDCJSZ');//吊销机动车驾驶证（本）
		DXJDCJSZ.value = results[13].text!="　"?results[13].text:'0';
		var ZKJDCJSZ = $('ZKJDCJSZ');
		ZKJDCJSZ.value = results[48].text == "　"?"0":results[48].text;
		var ZKJTWFCL = $('ZKJTWFCL');//暂扣交通违法车辆（辆次）

		ZKJTWFCL.value = results[14].text!="　"?results[14].text:'0';
		var JLJTWFJSR = $('JLJTWFJSR');//拘留交通违法驾驶人（人次）
		JLJTWFJSR.value = results[15].text!="　"?results[15].text:'0';
			
		var JCKYCL = $('JCKYCL');//检查客运车辆（辆）
		JCKYCL.value = results[16].text!="　"?results[16].text:'0';
		var TBKYBM = $('TBKYBM');//清理驾驶人记分（人）

		TBKYBM.value = results[17].text!="　"?results[17].text:'0';
		var SRYSQY = $('SRYSQY');//深入专业运输企业（个）

		SRYSQY.value = results[18].text!="　"?results[18].text:'0';
		var JYYSJSR = $('JYYSJSR');//教育运输企业驾驶人（人次）

		JYYSJSR.value = results[19].text!="　"?results[19].text:'0';
		var ELTQYA = $('ELTQYA');//启动恶劣天气应急预案（次）
		ELTQYA.value = results[20].text!="　"?results[20].text:'0';
		var YJSDFL = $('YJSDFL');//应急疏导、分流车辆（辆）
		YJSDFL.value = results[21].text!="　"?results[21].text:'0';
		var ZZWXLD = $('ZZWXLD');//整治危险路段（处）

		ZZWXLD.value = results[22].text!="　"?results[22].text:'0';
		
		var XZZYCK = $('XZZYCK');//卸客转运乘客（人次）
		XZZYCK.value = results[23].text!="　"?results[23].text:'0';
		var YDTB = $('YDTB');//通报异地超速50%以上、客车超员20%以上交通违法行为（起）
		YDTB.value = results[24].text!="　"?results[24].text:'0';
		var PCAQYHC = $('PCAQYHC');//排除安全隐患客车（辆）<span style="color:red;font-size:11px;">※</span>

		PCAQYHC.value = results[25].text!="　"?results[25].text:'0';
		var JYTZKCJSR = $('JYTZKCJSR');//建议调整客车驾驶人（人次）

		JYTZKCJSR.value = results[26].text!="　"?results[26].text:'0';
		var QZPLJSRXX = $('QZPLJSRXX');//强制疲劳驾驶人休息（人次）

		QZPLJSRXX.value = results[27].text!="　"?results[27].text:'0';
		var ZHCFLB = $('ZHCFLB');//抓获车匪路霸（人）

		ZHCFLB.value = results[28].text!="　"?results[28].text:'0';
		var JXXCHD = $('JXXCHD');//举行宣传活动（场次）
		JXXCHD.value = results[29].text!="　"?results[29].text:'0';
		var BFXCGP = $('BFXCGP');//播放宣传光盘（场次）
		BFXCGP.value = results[30].text!="　"?results[30].text:'0';
		var KDXCL = $('KDXCL');//设固定宣传栏（处）

		KDXCL.value = results[31].text!="　"?results[31].text:'0';
		var XCH = $('XCH');//张贴宣传画（张）
		XCH.value = results[32].text!="　"?results[32].text:'0';
		var XCZL = $('XCZL');//发放宣传资料（份）

		XCZL.value = results[33].text!="　"?results[33].text:'0';
		var SJY = $('SJY');//受教育（人次）

		SJY.value = results[34].text!="　"?results[34].text:'0';
		var DSXC = $('DSXC');//电视宣传（条）

		DSXC.value = results[35].text!="　"?results[35].text:'0';
		var DTXC = $('DTXC');//电台宣传（条）

		DTXC.value = results[36].text!="　"?results[36].text:'0';
		var BZXC = $('BZXC');//报纸宣传（条）

		BZXC.value = results[37].text!="　"?results[37].text:'0';
		var WLXC = $('WLXC');//网络宣传（条）

		WLXC.value = results[38].text!="　"?results[38].text:'0';
		var ZHS = $('ZHS');//为群众做好事（件）

		ZHS.value = results[39].text!="　"?results[39].text:'0';
		
		var SWSG = $('SWSG');//死亡事故宗数（起）

		SWSG.value = results[40].text!="　"?results[40].text:'0';
		var SWRS = $('SWRS');//死亡人数（人次）
		SWRS.value = results[41].text!="　"?results[41].text:'0';
		var SSRS = $('SSRS');//受伤人数（人次）
		SSRS.value = results[42].text!="　"?results[42].text:'0';
		var TDSG = $('TDSG');//特大事故宗数（起）

		TDSG.value = results[43].text!="　"?results[43].text:'0';
		var TDSWRS = $('TDSWRS');//死亡人数（人次）
		TDSWRS.value = results[44].text!="　"?results[44].text:'0';
		var TDSSRS = $('TDSSRS');//受伤人数（人次）
		TDSSRS.value = results[45].text!="　"?results[45].text:'0';

		var LSZQD = $('LSZQD');//新增设置临时执勤点（个）
		LSZQD.value = results[46].text!="　"?results[46].text:'0';
		
		setHtmlFlowZds();
		listFlowDd();
	}});
}

/**
* 函数功能:显示流量统计信息回调函数
*/
function showTfmStatResponse(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var rows = xmlDoc.getElementsByTagName("row");
	flowXmlToObject(rows);
	setZdInputShowPiece();
	setFlowInputs(getZdFlowInputs());
}


function fillSlct(ele,value,dlfx){
	$(ele).value = value;
	roadChange($(ele),dlfx);
	var id = "DLFX" + ele.substring(ele.length-1);
	var el = $(id);
	if($('insrtOrUpdt').value == '2'){
		$(ele).disabled = true;
		el.disabled = true;
	}
	
	iptSlct('TRJL');
}
function setStyle(ele){
	if($('insrtOrUpdt').value == '2'){
		$(ele).disabled = true;
		var id = "DLFX"+ele.substring(ele.length-1);
		$(id).disabled = true;
	}
	iptSlct('TRJL');
}

function iptSlct(ele){
	if($(ele)){
		$(ele).focus();
		$(ele).select();
	}
}

function doQuery(type,bh){
	setIsView();
	if(!bh){
		editCallBack();
		iptSlct('TRJL');
		return;
	}
	
	var url = "drpt.dailyRptModify.query.d";
	url = encodeURI(url);
	var params = "RZBH=" + bh;
	params = encodeURI(params);
	
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp){
		
		var xmlDoc = xmlHttp.responseXML;
		var rows = xmlDoc.getElementsByTagName("row");
		
		results = rows[0].childNodes;
		var bh = $('bh');
		bh.value = results[0].text;
		var tbdw = $('tbdw');
		tbdw.value = results[39].text;
		
		var tbdwid = $('tbdwid');
		tbdwid.value = results[34].text;
		
		var tbrq = results[35].text;
		var tjy = $('tjy');
		tjy.value = tbrq.substring(5,7);
		var tjr = $('tjr');
		tjr.value = tbrq.substring(8,10)
		
		var TRJL = $('TRJL'); //投入警力（人次）
		TRJL.value = results[1].text;
		var CDJC = $('CDJC'); //出动警车（辆次）
		CDJC.value = results[2].text;
		var CSSB = $('CSSB');//投入测速设备（台）
		CSSB.value = results[3].text;
		var CSD = $('CSD');//设置固定测速点（个）
		CSD.value = results[54].text;
		var GDCSD = $('GDCSD');//设置固定测速点（个）
		GDCSD.value = results[4].text;
		var LDCSD = $('LDCSD');//设置流动测速点（个）

		LDCSD.value = results[5].text;
		var ZQFWD = $('ZQFWD');//设置春运执勤服务点（个）
		ZQFWD.value = results[6].text;
		var JTWFHJ = $('JTWFHJ');//查处交通违法行为合计（起）
		JTWFHJ.value = results[7].text;
		var CSXS = $('CSXS');//查处超速行驶（起）
		CSXS.value = results[8].text;
		var KCJY = $('KCJY');//查处客车超员（起）

		KCJY.value = results[9].text;
		var PLJS = $('PLJS');//查处疲劳驾驶（起）

		PLJS.value = results[10].text;
		var JHJS = $('JHJS');//查处酒后驾驶（起）

		JHJS.value = results[11].text;
		var WZJS = $('WZJS');//查处无证驾驶（起）

		WZJS.value = results[12].text;
		var NYCWFZK = $('NYCWFZK');//查处农用车违法载客（起）
		NYCWFZK.value = results[13].text;
		var DXJDCJSZ = $('DXJDCJSZ');//吊销机动车驾驶证（本）
		var ZKJDCJSZ = $('ZKJDCJSZ');//设置固定测速点（个）
		ZKJDCJSZ.value = results[55].text;
		DXJDCJSZ.value = results[14].text;
		var ZKJTWFCL = $('ZKJTWFCL');//暂扣交通违法车辆（辆次）

		ZKJTWFCL.value = results[15].text;
		var JLJTWFJSR = $('JLJTWFJSR');//拘留交通违法驾驶人（人次）
		JLJTWFJSR.value = results[16].text;
		var XZZYCK = $('XZZYCK');//卸客转运乘客（人次）
		XZZYCK.value = results[17].text;
		var YDTB = $('YDTB');//通报异地超速50%以上、客车超员20%以上交通违法行为（起）
		YDTB.value = results[18].text;
		var PCAQYHC = $('PCAQYHC');//排除安全隐患客车（辆）<span style="color:red;font-size:11px;">※</span>

		PCAQYHC.value = results[19].text;
		var JYTZKCJSR = $('JYTZKCJSR');//建议调整客车驾驶人（人次）

		JYTZKCJSR.value = results[20].text;
		var QZPLJSRXX = $('QZPLJSRXX');//强制疲劳驾驶人休息（人次）

		QZPLJSRXX.value = results[21].text;
		var ZHCFLB = $('ZHCFLB');//抓获车匪路霸（人）

		ZHCFLB.value = results[22].text;
		var JXXCHD = $('JXXCHD');//举行宣传活动（场次）
		JXXCHD.value = results[23].text;
		var BFXCGP = $('BFXCGP');//播放宣传光盘（场次）
		BFXCGP.value = results[24].text;
		var KDXCL = $('KDXCL');//设固定宣传栏（处）

		KDXCL.value = results[25].text;
		var XCH = $('XCH');//张贴宣传画（张）
		XCH.value = results[26].text;
		var XCZL = $('XCZL');//发放宣传资料（份）

		XCZL.value = results[27].text;
		var SJY = $('SJY');//受教育（人次）

		SJY.value = results[28].text;
		var DSXC = $('DSXC');//电视宣传（条）

		DSXC.value = results[29].text;
		var DTXC = $('DTXC');//电台宣传（条）

		DTXC.value = results[30].text;
		var BZXC = $('BZXC');//报纸宣传（条）

		BZXC.value = results[31].text;
		var WLXC = $('WLXC');//网络宣传（条）

		WLXC.value = results[32].text;
		var ZHS = $('ZHS');//为群众做好事（件）

		ZHS.value = results[33].text;
		var TBR = $('TBR');
		TBR.value = results[36].text == "　"?"":results[36].text;
		var SHR = $('SHR');
		SHR.value = results[37].text == "　"?"":results[37].text;
		var LXDH = $('LXDH');
		LXDH.value = results[38].text == "　"?"":results[38].text;
		
		var JCKYCL = $('JCKYCL');//检查客运车辆（辆）
		JCKYCL.value = results[40].text;
		var TBKYBM = $('TBKYBM');//清理驾驶人记分（人）

		TBKYBM.value = results[41].text;
		var SRYSQY = $('SRYSQY');//深入专业运输企业（个）

		SRYSQY.value = results[42].text;
		var JYYSJSR = $('JYYSJSR');//教育运输企业驾驶人（人次）

		JYYSJSR.value = results[43].text;
		var ELTQYA = $('ELTQYA');//启动恶劣天气应急预案（次）
		ELTQYA.value = results[44].text;
		var YJSDFL = $('YJSDFL');//应急疏导、分流车辆（辆）
		YJSDFL.value = results[45].text;
		var ZZWXLD = $('ZZWXLD');//整治危险路段（处）

		ZZWXLD.value = results[46].text;
		
		var SWSG = $('SWSG');//死亡事故宗数（起）

		SWSG.value = results[47].text;
		var SWRS = $('SWRS');//死亡人数（人次）
		SWRS.value = results[48].text;
		var SSRS = $('SSRS');//受伤人数（人次）
		SSRS.value = results[49].text;
		var TDSG = $('TDSG');//特大事故宗数（起）

		TDSG.value = results[50].text;
		var TDSWRS = $('TDSWRS');//死亡人数（人次）
		TDSWRS.value = results[51].text;
		var TDSSRS = $('TDSSRS');//受伤人数（人次）
		TDSSRS.value = results[52].text;
		
		var LSZQD = $('LSZQD');//新增设置临时执勤点（个）
		LSZQD.value = results[53].text;
		
		var jgid = $('tbdwid').value;
		if(jgid.substring(4,6)!="00"){
			url = "drpt.dailyRptModify.queryRoad.d";
			url = encodeURI(url);
			
			var params = "RZBH=" + bh.value;
			params = encodeURI(params);
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp){
				var xmlDoc = xmlHttp.responseXML;
				var rows = xmlDoc.getElementsByTagName("row");
				setFlowAdd();
				var flows = flowXmlToObject(rows);
				setPieceDd(flows);
				setFlowDds(flows);
			}});
		}else{
			var jgid = $('tbdwid').value;
			var tjy = parseInt($('tjy').value)
			tjy = tjy > 9 ? tjy : ('0' + tjy);
			var tjr = parseInt($('tjr').value)
			tjr = tjr > 9 ? tjr : ('0' + tjr);
			var drpTime = '2010-' + tjy + '-' + tjr;
			var tempJgid = '';
			if(jgid.substring(2,6)=="0000"){        //总队用户
				tempJgid = " AND rzbh like '" + jgid.substring(0,2)+"__00%'";	    //支队，大队都选择的是“全部”

			}else if(jgid.substring(4,6)=="00"){	//支队用户
				tempJgid = " AND rzbh like '" + jgid.substring(0,4) + "__%'"; //选择的为支队下的全部大队
			}

			url = "drpt.dailyRptModify.queryRoad.d";
			url = encodeURI(url);
		
			var params = "RZBH=" + bh.value;
			params = encodeURI(params);
			
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp){
				var xmlDoc = xmlHttp.responseXML;
				var rows = xmlDoc.getElementsByTagName("row");
				var flows = flowXmlToObject(rows);
				setHtmlFlowZds(flowsToZd(flows));
				listFlowDd();
			}});
		}
	}});
	iptSlct('TRJL');
}


function doModify(flag){
	var bh = $('bh').value;
	var tbdwid = $('tbdwid').value;
	var tjy = $('tjy').value;
	var tjr = $('tjr').value;

	var TRJL = $('TRJL').value; //投入警力（人次）
	TRJL = /^\d+$/.test(TRJL)?TRJL:'0';
	var CDJC = $('CDJC').value; //出动警车（辆次）
	CDJC = /^\d+$/.test(CDJC)?CDJC:'0';
	var CSSB = $('CSSB').value;//投入测速设备（台）
	CSSB = /^\d+$/.test(CSSB)?CSSB:'0';
	var GDCSD = $('GDCSD').value;//设置固定测速点（个）

	GDCSD = /^\d+$/.test(GDCSD)?GDCSD:'0';
	var LDCSD = $('LDCSD').value;//设置流动测速点（个）

	LDCSD = /^\d+$/.test(LDCSD)?LDCSD:'0';
	var ZQFWD = $('ZQFWD').value;//设置春运执勤服务点（个）
	ZQFWD = /^\d+$/.test(ZQFWD)?ZQFWD:'0';
	var JTWFHJ = $('JTWFHJ').value;//查处交通违法行为合计（起）
	JTWFHJ = /^\d+$/.test(JTWFHJ)?JTWFHJ:'0';
	var CSXS = $('CSXS').value;//查处超速行驶（起）	
	CSXS = /^\d+$/.test(CSXS)?CSXS:'0';
	var KCJY = $('KCJY').value;//查处客车超员（起）

	KCJY = /^\d+$/.test(KCJY)?KCJY:'0';
	var PLJS = $('PLJS').value;//查处疲劳驾驶（起）

	PLJS = /^\d+$/.test(PLJS)?PLJS:'0';
	var JHJS = $('JHJS').value;//查处酒后驾驶（起）

	JHJS = /^\d+$/.test(JHJS)?JHJS:'0';
	var WZJS = $('WZJS').value;//查处无证驾驶（起）

	WZJS = /^\d+$/.test(WZJS)?WZJS:'0';
	var NYCWFZK = $('NYCWFZK').value;//查处农用车违法载客（起）
	NYCWFZK = /^\d+$/.test(NYCWFZK)?NYCWFZK:'0';
	var DXJDCJSZ = $('DXJDCJSZ').value;//吊销机动车驾驶证（本）

	DXJDCJSZ = /^\d+$/.test(DXJDCJSZ)?DXJDCJSZ:'0';
	var ZKJTWFCL = $('ZKJTWFCL').value;//暂扣交通违法车辆（辆次）

	ZKJTWFCL = /^\d+$/.test(ZKJTWFCL)?ZKJTWFCL:'0';
	var JLJTWFJSR = $('JLJTWFJSR').value;//拘留交通违法驾驶人（人次）
	JLJTWFJSR = /^\d+$/.test(JLJTWFJSR)?JLJTWFJSR:'0';
	var XZZYCK = $('XZZYCK').value;//卸客转运乘客（人次）
	XZZYCK = /^\d+$/.test(XZZYCK)?XZZYCK:'0';
	var YDTB = $('YDTB').value;//通报异地超速50%以上、客车超员20%以上交通违法行为（起）
	YDTB = /^\d+$/.test(YDTB)?YDTB:'0';
	var PCAQYHC = $('PCAQYHC').value;//排除安全隐患客车（辆）<span style="color:red;font-size:11px;">※</span>

	PCAQYHC = /^\d+$/.test(PCAQYHC)?PCAQYHC:'0';
	var JYTZKCJSR = $('JYTZKCJSR').value;//建议调整客车驾驶人（人次）

	JYTZKCJSR = /^\d+$/.test(JYTZKCJSR)?JYTZKCJSR:'0';
	var QZPLJSRXX = $('QZPLJSRXX').value;//强制疲劳驾驶人休息（人次）

	QZPLJSRXX = /^\d+$/.test(QZPLJSRXX)?QZPLJSRXX:'0';
	var ZHCFLB = $('ZHCFLB').value;//抓获车匪路霸（人）

	ZHCFLB = /^\d+$/.test(ZHCFLB)?ZHCFLB:'0';
	var JXXCHD = $('JXXCHD').value;//举行宣传活动（场次）
	JXXCHD = /^\d+$/.test(JXXCHD)?JXXCHD:'0';
	var BFXCGP = $('BFXCGP').value;//播放宣传光盘（场次）
	BFXCGP = /^\d+$/.test(BFXCGP)?BFXCGP:'0';
	var KDXCL = $('KDXCL').value;//设固定宣传栏（处）

	KDXCL = /^\d+$/.test(KDXCL)?KDXCL:'0';
	var XCH = $('XCH').value;//张贴宣传画（张）
	XCH = /^\d+$/.test(XCH)?XCH:'0';
	var XCZL = $('XCZL').value;//发放宣传资料（份）

	XCZL = /^\d+$/.test(XCZL)?XCZL:'0';
	var SJY = $('SJY').value;//受教育（人次）

	SJY = /^\d+$/.test(SJY)?SJY:'0';
	var DSXC = $('DSXC').value;//电视宣传（条）

	DSXC = /^\d+$/.test(DSXC)?DSXC:'0';
	var DTXC = $('DTXC').value;//电台宣传（条）

	DTXC = /^\d+$/.test(DTXC)?DTXC:'0';
	var BZXC = $('BZXC').value;//报纸宣传（条）

	BZXC = /^\d+$/.test(BZXC)?BZXC:'0';
	var WLXC = $('WLXC').value;//网络宣传（条）

	WLXC = /^\d+$/.test(WLXC)?WLXC:'0';
	var ZHS = $('ZHS').value;//为群众做好事（件）

	ZHS = /^\d+$/.test(ZHS)?ZHS:'0';
	
	var JCKYCL = $('JCKYCL').value;//检查客运车辆（辆）
	JCKYCL = /^\d+$/.test(JCKYCL)?JCKYCL:'0';
	var TBKYBM = $('TBKYBM').value;//清理驾驶人记分（人）

	TBKYBM = /^\d+$/.test(TBKYBM)?TBKYBM:'0';
	var SRYSQY = $('SRYSQY').value;//深入专业运输企业（个）

	SRYSQY = /^\d+$/.test(SRYSQY)?SRYSQY:'0';
	var JYYSJSR = $('JYYSJSR').value;//教育运输企业驾驶人（人次）

	JYYSJSR = /^\d+$/.test(JYYSJSR)?JYYSJSR:'0';
	var ELTQYA = $('ELTQYA').value;//启动恶劣天气应急预案（次）
	ELTQYA = /^\d+$/.test(ELTQYA)?ELTQYA:'0';
	var YJSDFL = $('YJSDFL').value;//应急疏导、分流车辆（辆）
	YJSDFL = /^\d+$/.test(YJSDFL)?YJSDFL:'0';
	var ZZWXLD = $('ZZWXLD').value;//整治危险路段（处）

	ZZWXLD = /^\d+$/.test(ZZWXLD)?ZZWXLD:'0';
	
	var SWSG = $('SWSG').value;//死亡事故宗数（起）

	SWSG = /^\d+$/.test(SWSG)?SWSG:'0';
	var SWRS = $('SWRS').value;//死亡人数（人次）
	SWRS = /^\d+$/.test(SWRS)?SWRS:'0';
	var SSRS = $('SSRS').value;//受伤人数（人次）
	SSRS = /^\d+$/.test(SSRS)?SSRS:'0';
	var TDSG = $('TDSG').value;//特大事故宗数（起）

	TDSG = /^\d+$/.test(TDSG)?TDSG:'0';
	var TDSWRS = $('TDSWRS').value;//死亡人数（人次）
	TDSWRS = /^\d+$/.test(TDSWRS)?TDSWRS:'0';
	var TDSSRS = $('TDSSRS').value;//受伤人数（人次）
	TDSSRS = /^\d+$/.test(TDSSRS)?TDSSRS:'0';
	
	var LSZQD = $('LSZQD').value;//新增设置临时执勤点（个）
	LSZQD = /^\d+$/.test(LSZQD)?LSZQD:'0';
	

	if(!flowWriteCheck()){
		return;
	}
	var TBR = $('TBR').value;
	if(!TBR){
		alert('请填写填表人！');
	    $('TBR').focus();
	    return;
	}

	var SHR = $('SHR').value;
	
	var LXDH = $('LXDH').value;
	var reg=/(^[0-9]{3,4}[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)|(^0{0,1}15[0-9]{9}$)/; 
    if (!reg.test(LXDH)){
	    alert("您输入的电话号码不正确！");
	    $('LXDH').focus();
	    return;
    }

	if(!validateInput()){
		return;
	}
	
	var dataArray = new Array();
	dataArray.push(bh);//0
	dataArray.push(tbdwid);
	dataArray.push(tjy);
	dataArray.push(tjr);
	dataArray.push(TRJL);
	dataArray.push(CDJC);
	dataArray.push(CSSB);
	dataArray.push(GDCSD);
	dataArray.push(LDCSD);
	dataArray.push(ZQFWD);
	dataArray.push(JTWFHJ);//10
	dataArray.push(CSXS);
	dataArray.push(KCJY);
	dataArray.push(PLJS);
	dataArray.push(JHJS);
	dataArray.push(WZJS);
	dataArray.push(NYCWFZK);
	dataArray.push(DXJDCJSZ);
	dataArray.push(ZKJTWFCL);
	dataArray.push(JLJTWFJSR);
	dataArray.push(XZZYCK);//20
	dataArray.push(YDTB);
	dataArray.push(PCAQYHC);
	dataArray.push(JYTZKCJSR);
	dataArray.push(QZPLJSRXX);
	dataArray.push(ZHCFLB);
	dataArray.push(JXXCHD);
	dataArray.push(BFXCGP);
	dataArray.push(KDXCL);
	dataArray.push(XCH);
	dataArray.push(XCZL);//30
	dataArray.push(SJY);
	dataArray.push(DSXC);
	dataArray.push(DTXC);
	dataArray.push(BZXC);
	dataArray.push(WLXC);
	dataArray.push(ZHS);
	dataArray.push(TBR);
	dataArray.push(SHR);
	dataArray.push(LXDH);
	dataArray.push($("CSD").value||'0');
	
	dataArray.push($("ZKJDCJSZ").value||'0');
	for(var i=42;i<60;i++){
		dataArray.push("");
	}
	
	dataArray.push(JCKYCL);//60
	dataArray.push(TBKYBM);//
	dataArray.push(SRYSQY);//
	dataArray.push(JYYSJSR);//
	dataArray.push(ELTQYA);//
	dataArray.push(YJSDFL);//
	dataArray.push(ZZWXLD);//66

	dataArray.push(SWSG);//67
	dataArray.push(SWRS);//
	dataArray.push(SSRS);//
	dataArray.push(TDSG);//
	dataArray.push(TDSWRS);//
	dataArray.push(TDSSRS);//72
	dataArray.push(LSZQD);//73
	
	var flow;
	for ( var no = 1; no <= $piece; no++) {
		flow = getObjectFlowDd(no);
		if(flow && flow.dlbh){
			dataArray.push(flow.dlbh);
			dataArray.push(flow.ldmc);
			dataArray.push(flow.carnum);
			dataArray.push(0);
			dataArray.push(0);
			dataArray.push(0);
			dataArray.push(0);
		}
	}
	
	
	var insertOrUpdate = 0;
	
	if(bh)	insertOrUpdate = 1;
 
	var url = "drpt.dailyRptModify.modify.d";
	
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");

	var params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insertOrUpdate;
	params = encodeURI(params);
	//Modified by Liuwx 2012年2月3日15:36:28
	//提交前添加遮罩效果，以防止重复提交。
	showOverlay();
	//Modification finished
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp){
		var result = xmlHttp.responseText;
		if(result.indexOf('失败') > -1){
			//Modified by Liuwx 2012年2月3日15:35:13
			showOverlay(result);
			window.setTimeout(function(){
				jQuery.unblockUI();
			}, 10000);
			//Modification finished
//			alert(result);
		}else{
			if(result.indexOf('success') > -1){
				//Modified by Liuwx 2012年2月3日15:35:09
				showOverlay("日报保存成功！");
				window.setTimeout(function(){
					jQuery.unblockUI();
					window.close();
				}, 2000);
				//Modification finished
//				alert('日报保存成功！');
			}else{
				//Modified by Liuwx 2012年2月3日15:35:05
				showOverlay("日报修改成功！");
				window.setTimeout(function(){
					jQuery.unblockUI();
					window.close();
				}, 2000);
				//Modification finished
//				alert('日报修改成功！');
			}
		}
//		window.close();
//		if(flag){
//			$('bh').value = xmlHttp.responseText;
//			return;
//		}
//		
//		if(result.indexOf('success') > -1){
//			
//		}
//		alert(result)
//		window.close();
	}});
}


function doDelete(bh) {                                
	if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) { //提示用户是否删除记录                                       
		var strUrl = "drpt.dailyRptModify.delete.d";  //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "RZBH=" + bh;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});
	}
}

function showResponseDelete(xmlHttp) {
	var text = xmlHttp.responseText;
	if(!"成功".indexOf(text))	alert(text);
	doOnLoad();
}

//edit by lidq 
//说明：根据统计时间判断

function onButtonClick(itemId,itemValue,tjrq) {
	var id = itemId;
	if (id == "edit") {
		window.showModalDialog("dailyReport2012.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&bh=" + itemValue+"&tjrq="+tjrq, "", "dialogWidth:850px;dialogHeight:724px;");
		doOnLoad();
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("dailyReport2012.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&bh=" + itemValue, "", "dialogWidth:850px;dialogHeight:724px;");
		doOnLoad();
	}
	if (id != "search") {
		doOnLoad();
	}
	if (id == "delete") {
		doDelete(itemValue);
	}
}

function checkNum(ele,event){
	if(event.keyCode < 48 || event.keyCode > 57){
		event.keyCode=0;
		return;	
	}
	$(ele).style.color = "ff0000";
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

function changeBlur(event,ele){
	if(event.keyCode==9){
		//event.keyCode = 0;
//		alert(ele)
		//$(ele).focus();
		return false;
	}
}

function validateInput() {
	var input;
	var select;
	var textarea;
	var reg =  /^[^~@#\$\^&\s]*$/;
	input = document.getElementsByTagName("input");
	textarea = document.getElementsByTagName("textarea");
	for (var i = 0; i < input.length; i++) {
		var item = input[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括: 空白字符、~、@、#、$、^、&")
				item.focus();
				return false;	
			}
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括: 空白字符、~、@、#、$、^、&")
				item.focus();
				return false;	
			}
		}
	}
	return true;
}

var isView = false;
function setIsView(){
	isView = $('insrtOrUpdt').value == '2';
}





/** 获取路段文本框 */
function htmlRoadSeg(id,value){
	var readOnly = isView ? "readOnly" : "";
	value = value || "";
	return '<input maxLength="8" '+readOnly+' id="'+id+'" value="'+value+'" class="input6" type="text" onfocus="this.select();" />';
}
/** 获取车流量文本框 */
function htmlCar(id,value){
	var readOnly = isView ? "readOnly" : "";
	value = value || "";
	return '<input maxLength="8" '+readOnly+' id="'+id+'" value="'+value+'" class="input5" type="text" onfocus="this.select();" onkeypress="checkNum(this,event)" />';
}

/** 获取环比增减 */
function htmlLRRatioInDe(no, value) {
	var attrs = {id:"LRRATIOINDE_INDE" + no,disabled:isView};
	var inde = null;
	if(!isNaN(value)){
		inde = value >= 0?"+":"-";
		value = Math.abs(value);
	}
	var div = htmlInDe(attrs, inde);
	div += htmlLRRatioInDeValue("LRRATIOINDE_VALUE" + no, value);
	var attrs = "id='LRRATIOINDE" + no + "' style='display:inline;'";
	div = "<div " + attrs + ">" + div + "</div>";
	return div;
}

/** 获取增减选择列表（是增或者减） */
function htmlInDe(attrs,value){
	var objects = [{
		innerHTML:"增",
		value:"+"
	},{
		innerHTML:"减",
		value:"-"
	}];
	var select = DOM.Select.elementSpeedy(attrs,objects,value);
	return select.outerHTML;
}

/** 获取环比增减值 */
function htmlLRRatioInDeValue(id,value){
	var readOnly = isView ? "readOnly" : "";
	value = value || "";
	return '<input maxLength="8" '+readOnly+' id="'+id+'" value="'+value+'" class="input5" type="text" onfocus="this.select();" onkeypress="checkNum(this,event)" />%';
}


/** 获得流量数值输入串 */
function htmlFlowNum(no,object){
	object = object || {};
	var flow = [];
	flow.push(htmlCar("CARNUM" + no,object.carnum)+"辆次");
	var kc = '，其中：客车' + htmlCar("KC" + no,object.kc)+"辆次";
	var zjc = '、自驾车' + htmlCar("ZJC" + no,object.zjc)+"辆次";
	var kczjc = "<div id='KCZJC"+no+"' style='display:none;'>"+kc+zjc+"</div>";
	flow.push(kczjc);
	flow.push('，3日平均流量'+htmlCar("PJCARNUM"+no,object.avg)+"辆次");
	flow.push('，环比增减'+htmlLRRatioInDe(no,object.lrratio));
	flow.push('。');
	return flow.join("");
}



/** 获取流量输入串 */
function getFlowInputs(input){
	var roads = [];
	for(var no=1;no<=$piece;no++){
		roads.push('<div style="text-align:left;">'+input(no)+'</div>');
	}
	return roads.join("");//'<hr color="#106ead">'
}

/** 设置流量输入串 */
function setFlowInputs(html){
	$("ldxx").innerHTML = html;
	for(var i=1;i<=$piece;i++){
		$("KCZJC"+i).style.display = "none";
	}
}

/** 获取流量索引 */
function getFlowNo(value) {
	if(value.nodeName){
		value = value.id;
	}
	if(typeof value == "string"){
		value = value.substring(value.length - 1);
	}
	if(/^\d+$/.test(value)){
		value = parseInt(value);
		return value;
	}
	return -1;
}

/** 设置流量数值 */
function setFlowNum(no,flow){
	flow = flow || {};
	$("CARNUM"+no).value = flow.carnum||"";
	$("KC"+no).value = flow.kc||"";
	$("ZJC"+no).value = flow.zjc||"";
	$("PJCARNUM"+no).value = flow.avg||"";
	$("LRRATIO"+no).value = flow.lrratio||"";
}

function getFlowNum(no,object) {
	object = object || {};
	var fields = {
		carnum : "CARNUM"
//		kc : "KC",
//		zjc : "ZJC",
//		avg : "PJCARNUM",
//		lrratio : "LRRATIOINDE_VALUE" 
	};
	for(var attr in fields){
		object[attr] = $(fields[attr]+no).value||0;
	}
//	object.lrratio = parseInt($("LRRATIOINDE_INDE"+no).value + object.lrratio); 
	return object;
}

/** 获取道路查询Sql语句 */
function getRoadSql(jgid,level){
//	var sql = "select distinct r.gbdm,r.dlmc";
//	sql+=" from t_oa_dictdlfx r,t_oa_dict_road rj";
//	sql+=" where r.gbdm=rj.ndlbh and instr(r.gbdm,'z_old')=0";
//	sql+=" and substr(rj.jgid,1,"+jgid.length+")='"+jgid+"'";
//	sql+=" and r.roadlevel='"+level+"'";
//	sql+=" order by r.dlmc";
	var sql = "select distinct r.gbdm,r.dlmc";
	sql += " from t_oa_dictdlfx r";
	sql += " where r.roadlevel='"+level+"'";
	sql += " and instr(r.gbdm,'z_old')=0";
	sql+=" order by r.dlmc"; 
	return sql;
}

/**流量总条数，国道条数，高速条数（界面展示）*/
var $piece = 4,$gd = 2,$gs=$piece - $gd;
/** 将最大流量xml格式数据转换成json格式 */
var flowFileds = [ "rzbh","tbdw","dwmc","tjrq","bh", "dlbh", "dlmc", "level", "ldmc",  "carnum",
		"kc", "zjc", "avg", "lrratio" ];
var flows = [];
/**将流量的数据格式由xml转换为对象*/
function flowXmlToObject(rows) {
	var length = rows.length,row;
	var flows=[],flow;
	for ( var i = 0; i < length; i++) {
		row = rows[i].childNodes;
		flow = {};
		for ( var j = 0; j < flowFileds.length; j++) {
			flow[flowFileds[j]] = filterText(row[j].text);
		}
		flows.push(flow);
	}
	return flows;
}
/**流量对象数据转换为支队使用格式*/
function flowsToZd(objects){
	var flows=[],level;
	var gd = 0,gs=2;
	for(var i=0;i<objects.length;i++){
		level = objects[i].level;
		if(level == "1"){
			flows[gs++] = objects[i];
		}else if(level == "2"){
			flows[gd++] = objects[i];
		}
	}
	return flows;
}

function filterText(text){
	return text == "　"?"":text;
}
function getFlowObject(no){
	var flows = gdflows;
	var index = no - 1;
	if(index >= $gd){
		flows = gsflows;
		index -= $gd;
	}
	return flows[index];
}


//----------------大队------------------------
/** 获取大队流量输入串 */
function htmlFlowDd(no,object){
	var html = [];
	no = no || "";
	object = object || {};
	var attrs = {id:"ROADLEVEL"+no,onchange:"roadLevelOnchange(this)",disabled:isView};
	html.push(RoadLevel.selectReport(attrs,object.level).outerHTML);
	var roads = DOM.Select.empty({style:"width:110"},"道路").outerHTML;
	html.push('<span id="DLBH' + no + 's">' + roads + '</span>');
	html.push(htmlRoadSeg("LDMC" + no, object.ldmc)+"路段");
	html.push(htmlFlowNum(no, object));
	return html.join("");
}

/**道路等级改变事件*/
function roadLevelOnchange(el){
	el = el || this;
	var no = getFlowNo(el);
	fillRoad(no,el.value);
}

/** 填充道路列表  */
function fillRoad(no,level,value){
	if(level){
		var jgid = $("tbdwid").value;
		jgid = jgid.substring(0,4);
		var sql = getRoadSql(jgid,level);
		value = value ? ",'"+value+"'":"";
		fillListBox("DLBH"+no+"s","DLBH"+no,"110",sql,"请选择","roadCallback("+no+value+")");
	}else{
		var attrs = {id:"DLBH"+no,disabled:isView,style:"width:110px;"};
		$("DLBH"+no+"s").innerHTML = DOM.Select.empty(attrs).outerHTML;
//		setDefault(no);
	}
}

/** 大队流量道路回调函数  */
function roadCallback(no,value){
	var dlbh = $("DLBH"+no);
	if(value){
		dlbh.value = value;
	}
	dlbh.disabled = isView;
	appendFlowUniqCheck(no);
}

/** 添加唯一验证 */
function appendFlowUniqCheck(no) {
	$("DLBH"+no).onchange = function(){
		flowUniqCheck(this);
	};
	$("LDMC" + no).onblur = function() {
		flowUniqCheck(this);
	};
}

/** 流量唯一性检查 */
function flowUniqCheck(el){
	var no = getFlowNo(el.id);
	var current = getObjectFlowDd(no);
	var exist;
	if(current.dlbh && current.ldmc){
		for(var i=1;i<=$piece;i++){
			if(no == i){
				continue;
			}
			exist = getObjectFlowDd(i);
			if(checkDlldfx(current,exist)){
				showCheckDlldfx(el);
				return false;
			}
		}
	}
	return true;
}

/** 获取流量对象 */
function getObjectFlowDd(no) {
	var object = {
		dlbh : $("DLBH" + no).value,
		ldmc : $("LDMC" + no).value
	};
	object = getFlowNum(no, object);
	return object;
}

/** 检查道路路段方向是否相同 */
function checkDlldfx(search, target) {
	return search.dlbh == target.dlbh && search.ldmc == target.ldmc;
}

/** 显示道路路段方向的验证提示 */
function showCheckDlldfx(el){
	alert("相同路名和路段的流量已存在，请重新选择！");
	reset(el);
}

/** 重新设置元素 */
function reset(el){
	el.value = "";
	el.focus();
}

/** 设置大队流量输入串 */
function setHtmlFlowDd(no,object){
	no = no || "";
	object = object || {};
	var html = htmlFlowDd(no,object);
	var div = document.createElement("div");
	div.id = "flow"+no;
	div.style.textAlign="left";
	div.style.display="block";
	div.innerHTML = html;
	$("ldxx").appendChild(div);
	fillRoad(no,object.level,object.dlbh);
}

/** 设置大队多条流量输入串 */
function setFlowDds(objects) {
	objects = objects || [];
	for ( var i = 1; i <= $piece; i++) {
//		setHtmlFlowDd(i,objects[i-1]);
		appendFlow(i,objects[i-1]);
	}
}

/** 
 * 设置大队显示条数
 * 1.至少显示一条，至多显示两条
 * 2.固定2条
  */
function setPieceDd(flows){
//	flows = flows || [];
//	var length = flows.length;
//	if(length >2 ){
//		$piece = 1;
//	}else if(length < 1){
//		$piece = 1;
//	}else{
//		$piece = length;
//	}
	$piece = 2;
}

/** 设置大队手动添加新流量 */
function setFlowAdd(){
	if(!isView){
		var util = $("flowUtil");
		util.style.display = "inline";
		util.innerText = "添加新流量";
		util.onclick = function(){
			var max = 2;
			if($piece >= max){
				alert("最多只能录入"+max+"条流量信息！");
			}else{
				$piece++;
				setHtmlFlowDd($piece);
			}
		};
	}
}
var validescs = {
	LDMC : "路段名称",
	CARNUM: "日流量"
//	PJCARNUM:"3日平均车辆次",
//	LRRATIOINDE_VALUE : "环比增减"
};
function flowWriteCheck(){
	var is = false;
	var isgsdd = $("isgsdd").value;
	if(isgsdd){
		//高速大队必须至少录入一条道路
		for ( var i = 1; i <= $piece; i++) {
			if($("DLBH"+i).value){
				is = true;
				break;
			}
		}
		if(!is){
			alert("高速大队至少录入一条流量信息！");
			$("ROADLEVEL1").focus();
			return false;
		}
	}else{
		is = true;
		var tbdw = $("tbdwid").value;
		if(tbdw.substring(4,6)=="00"){
			var isgs = false,isgd=false;
			for(var i=1;i<=2;i++){
				if($("DLBH"+i).value){
					isgd = true;
					break;
				}
			}
			for(var i=3;i<=4;i++){
				if($("DLBH"+i).value){
					isgs = true;
					break;
				}
			}
			if(!isgd || !isgs){
				alert("支队至少录入一条国道流量和一条高速流量！");
				if(isgd){
					$("DLBH3").focus();
				}else{
					$("DLBH1").focus();
				}
				return false;
			}
		}
	}
	if(is){
		//如果需要录入流量，流量数据必须完整
		var dlbh;
		for ( var i = 1; i <= $piece; i++) {
			dlbh = $("DLBH"+i).value;
			if(dlbh){
				for (var attr in validescs) {
					if(!validates.check($(attr+i),validescs[attr])){
						return false;
					}
				}
			}
		}
	}
	return true;
}


function appendFlow(no,object){
	no = no || "";
	object = object || {};
	var table = $("flow");
	var row = table.insertRow(table.rows.length);
	var cell = row.insertCell(row.cells.length);
	var attrs = {id:"ROADLEVEL"+no,disabled:isView};
	var roadLevel = RoadLevel.selectReport(attrs,object.level);
	roadLevel.onchange = roadLevelOnchange;
	cell.appendChild(roadLevel);
	cell = row.insertCell(row.cells.length);
	cell.id = "DLBH" + no + "s";
	cell.appendChild(DOM.Select.empty({style:"width:110"}));
	cell = row.insertCell(row.cells.length);
	cell.innerHTML = htmlRoadSeg("LDMC" + no, object.ldmc);
	cell = row.insertCell(row.cells.length);
	cell.innerHTML=htmlCar("CARNUM" + no,object.carnum);
//	cell = row.insertCell(row.cells.length);
//	cell.innerHTML=htmlCar("PJCARNUM" + no,object.carnum);
//	cell = row.insertCell(row.cells.length);
//	cell.innerHTML=htmlLRRatioInDe(no,object.lrratio);
	
	fillRoad(no,object.level,object.dlbh);
}


//-----------支队------------------

/**加载大队流量数据*/
function loadData(el){
	var jgid = $("tbdwid").value;
	var tjy = $('tjy').value;
	var tjr = $('tjr').value;
	var drpTime = tjy + '-' + tjr;
	var url = "drpt.dailyRptModify.tfmCalBack.d";
	url = encodeURI(url);
	params= "?startTime="+drpTime+"&endTime="+drpTime+"&departmentId="+jgid;
	var request = {
		method : 'post',
		parameters : params,
		onComplete : function(xmlHttp) {
			var xmlDoc = xmlHttp.responseXML;
			var rows = xmlDoc.getElementsByTagName("row");
			var flows = flowXmlToObject(rows);
			var table = generateTable(flows);
			el.content(table);
		}
	};
	new Ajax.Request(url,request);
}
/**根据大队流量数据生成表格*/
function generateTable(flows){
	var table = document.createElement("table");
	table.border = "0";
	table.cellPadding="5";
	table.cellSpacing="0";
	table.style.cssText = "border-bottom: 1px solid #b5d6e6;border-right: 1px solid #b5d6e6;width:100%;text-align:center;";
	
	var row = table.insertRow(table.rows.length);
	var style = "border-top: 1px solid #b5d6e6;border-left: 1px solid #b5d6e6;";
//	var columns = ["填报单位","填报日期","道路名称","路段名称","车辆次","3日平均流量","环比增减"];
	var columns = ["填报单位","填报日期","道路名称","路段名称","日流量"];
	for(var i=0;i<columns.length;i++){
		var th = document.createElement("th");
		th.style.cssText = style;
		th.innerText = columns[i];
		row.appendChild(th);
	}
	var bgs = ["","background-color: #b5d6e6"];
	for(var i=0;i<flows.length;i++){
		var row = table.insertRow(table.rows.length);
		for(var attr in flows[i]){
			if("rzbh,tbdw,bh,dlbh,level,kc,zjc,avg,lrratio".indexOf(attr)==-1){
				var cell = row.insertCell(row.cells.length);
				cell.style.cssText = style+bgs[i%2];
				cell.innerText = flows[i][attr]||"0";
			}
		}
	}
	if(flows.length <= 0){
		var row = table.insertRow(table.rows.length);
		var cell = row.insertCell(row.cells.length);
		cell.style.cssText = style+";text-align:center";
		cell.colSpan = table.rows[0].cells.length;
		cell.innerText = "没有可以展示的数据！";
	}
	return table;
}

/** 查看大队流量列表 */
function listFlowDd(){
	if(!isView){
//		var table = $("flow");
//		var row = table.insertRow(table.rows.length);
//		var cell = row.insertCell(row.cells.length);
//		cell.colSpan = table.rows[0].cells.length;
//		cell.style.cssText = "text-align:left";
//		var link = document.createElement("span");
//		$("trFlowList").style.display = "inline";
		var link = $("btnFlowList");
		link.style.display = "block";
		link.innerText = "查看大队填报流量";
		link.onclick = function() {
			art.dialog({
				id : "flowDdList",
				title : "辖区大队上报流量列表",
				ok : true,
				init : function() {
					loadData(this);
				},
				width : 550
			});
		};
		cell.appendChild(link);
	}
}

/** 获得支队流量输入串 */
function htmlFlowZd(no,object){
	no = no || "";
	object = object || {};
	var html = [];
	var desc = ["高速","国道","省道"][object.level-1];
	html.push('<span id="DLBH' + no + 's"></span>'+desc);
	html.push(htmlRoadSeg("LDMC" + no, object.ldmc)+"路段");
	html.push(htmlFlowNum(no,object));
	return html.join("");
}

/** 设置支队流量 */
function setHtmlFlowZd(no,object){
	var html = htmlFlowZd(no,object);
	var div = document.createElement("div");
	div.id = "flow"+no;
	div.style.textAlign="left";
	div.style.display="block";
	div.innerHTML = html;
	$("ldxx").appendChild(div);
	fillRoad(no,object.level,object.dlbh);
}

/** 设置支队流量，两条国道和两条高速 */
function setHtmlFlowZds(objects){
	objects = objects || [];
	var gd = 2,gs = 2;
	var s = 1,e = gd;
	for(var i=s;i<=e;i++){
		appendFlow(i,objects[i-1]||{level:2});
		$("ROADLEVEL"+i).disabled=true;
	}
	s = e+1,e+=gs;
	for(var i=s;i<=e;i++){
		appendFlow(i,objects[i-1]||{level:1});
		$("ROADLEVEL"+i).disabled=true;
	}
	$piece = e;
}

function getObjectFlowZd(no){
	var flowPK = $("flowPK"+no);
	flowPK = flowPK.options[flowPK.selectedIndex];
	var object = {
		dlbh : flowPK.dlbh,
		ldmc : flowPK.ldmc,
		dlfx : flowPK.dlfx
	};
	object = getFlowNum(no,object);
	return object;
}




function setDefault(no){
	$("ROADLEVEL"+no).value = "1";
	fillRoad(no,"1","S21");
	$("LDMC"+no).value = "S21";
	$("CARNUM"+no).value = "20";
	$("PJCARNUM"+no).value = "10";
	$("LRRATIOINDE_VALUE"+no).value = "10";
	$("TBR").value="张三";
	$("LXDH").value="1111111";
}

/**
 * 
	* 方法名称：showOverlay<br>
	* 方法描述： 防重复提交遮罩层 <br><br> 
	* 版本信息：Ver 1.1 <br>
	***********************************<br>
	* 创建人：Vasin   <br>
	* 创建时间：2012-2-3 下午2:34:33  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-3 下午2:34:33   <br>
	* 修改备注：    <br>
 */
function showOverlay(msg) {
	var message = "提交中，请稍候……";
	if(msg){
		message = msg;
	}
	jQuery.blockUI({
		message : message,
		css : {
			border : 'none',
			padding : '15px',
			backgroundColor : '#000',
			'-webkit-border-radius' : '10px',
			'-moz-border-radius' : '10px',
			opacity : .5,
			color : '#fff'
		}
	});
}


