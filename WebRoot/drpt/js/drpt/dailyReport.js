var ldsObjs = new Array();
var ldsOps = new Array();
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
	window.open("dailyReport2012.jsp?tmp="+Math.random()+"&insrtOrUpdt=0","newwindow","height=580px,width=870px,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no,top=100,left=300")
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
		var str = '<div><span id="DLBH1s"></span><input maxLength="8" id="LDMC1" class="input6" type="text" />路段共';
		str += '<input maxLength="8" id="CARNUM1" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
		str += '<input maxLength="8" id="KC1" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
		str += '<input maxLength="8" id="ZJC1" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。';
		str += '</div><div><span id="DLBH2s"></span><input maxLength="8" id="LDMC2" class="input6" type="text" />路段共';
		str += '<input maxLength="8" id="CARNUM2" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
		str += '<input maxLength="8" id="KC2" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
		str += '<input maxLength="8" id="ZJC2" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。'
		str += '</div><div><span id="DLBH3s"></span><input maxLength="8" id="LDMC3" class="input6" type="text" />路段共';
		str += '<input maxLength="8" id="CARNUM3" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
		str += '<input maxLength="8" id="KC3" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
		str += '<input maxLength="8" id="ZJC3" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。';
		str += '</div><div><span id="DLBH4s"></span><input maxLength="8" id="LDMC4" class="input6" type="text" />路段共';
		str += '<input maxLength="8" id="CARNUM4" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
		str += '<input maxLength="8" id="KC4" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
		str += '<input maxLength="8" id="ZJC4" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。</div>';
		
		$('ldxx').innerHTML = str;
		fillListBox("DLBH1s","DLBH1","110","select dlbh,dlmc from t_oa_dict_road where jgid like '" + jgid.substring(0,4) + "%'","请选择","iptSlct('TRJL')");
		fillListBox("DLBH2s","DLBH2","110","select dlbh,dlmc from t_oa_dict_road where jgid like '" + jgid.substring(0,4) + "%'","请选择","iptSlct('TRJL')");
		fillListBox("DLBH3s","DLBH3","110","select dlbh,dlmc from t_oa_dict_road where jgid like '" + jgid.substring(0,4) + "%'","请选择","iptSlct('TRJL')");
		fillListBox("DLBH4s","DLBH4","110","select dlbh,dlmc from t_oa_dict_road where jgid like '" + jgid.substring(0,4) + "%'","请选择","iptSlct('TRJL')");
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
		
		var url = "drpt.dailyRptModify.tfmCalBack.d";
		url = encodeURI(url);
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete: showTfmStatResponse});
	}});
}

/**
* 函数功能:显示流量统计信息回调函数
*/
function showTfmStatResponse(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var rows = xmlDoc.getElementsByTagName("row");
	ldsObjs = new Array();
	ldsOps = new Array();
	if(rows.length > 0){
		for (var i = 0; i < rows.length; i++){
			results = rows[i].childNodes;
			
			ldsObjs.push({
				llbh: results[6].text,
				dlbh: results[5].text,
				dlmc: results[0].text,
				ldmc: results[1].text,
				carnum: results[2].text,
				kc: results[3].text,
				zjc: results[4].text
			})
			
			var optText = '<option dlbh="' + ldsObjs[i].dlbh + '" dlmc="' + ldsObjs[i].dlmc;
			optText += '" ldmc="' + ldsObjs[i].ldmc + '" value="' + ldsObjs[i].llbh + '">' + ldsObjs[i].dlmc + '  ';
			optText += ldsObjs[i].ldmc + '</option>';
			
			ldsOps.push(optText);
		}
	}
	
	var str = '';
	var opt_str = '';
	for(var i = 0; i < ldsOps.length; i ++){
		opt_str += ldsOps[i];
	}
	

	for (var i = 0; i < ldsObjs.length; i ++){
		
		if(i >= 4) break;
		var index = i+1;
		str += '<div id="cllbox' + index + '"><input type="hidden" id="llbh' + index + '" value="';
		str += ldsObjs[i].llbh + '" /><select style="width:150px;margin:auto 20px;" onchange="changeLdxx(this);" id="dlld' + index + '">';
		str += '<option>请选择</option>'
		str += opt_str + '</select>路段共<input maxLength="8" id="CARNUM' + index + '" class="input5" type="text"';
		str += ' value="' + ldsObjs[i].carnum + '" maxLength="8" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车<input id="KC' + index;
		str += '" value="' + ldsObjs[i].kc + '" maxLength="8" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车<input id="ZJC' + index;
		str += '" value="' + ldsObjs[i].zjc + '" maxLength="8" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。</div>';
	}
	$('ldxx').innerHTML = str;
	
	initLdOpt(1,2,3,4,true);
	iptSlct('TRJL');
}

function initLdOpt(arg0,arg1,arg2,arg3,type){
	var dlld1 = $('dlld1');
	var dlld2 = $('dlld2');
	var dlld3 = $('dlld3');
	var dlld4 = $('dlld4');
	

	if(!isNaN(arg0)){
		if(dlld1){
			if(!type && ((dlld2 && dlld2.options[arg0].selected) ||
				(dlld3 && dlld3.options[arg0].selected) ||
				(dlld4 && dlld4.options[arg0].selected))){
				alert('已存在！')
				return false;
			}
			dlld1.options[arg0].selected = true;
		}
	}
	if(!isNaN(arg1)){
		if(dlld2){
			if(!type && ((dlld1 && dlld1.options[arg1].selected) ||
				(dlld3 && dlld3.options[arg1].selected) ||
				(dlld4 && dlld4.options[arg1].selected))){
				alert('已存在！')
				return false;
			}
			dlld2.options[arg1].selected = true;
		}
	}
	if(!isNaN(arg2)){
		if(dlld3){
			if(!type && ((dlld1 && dlld1.options[arg2].selected) ||
				(dlld2 && dlld2.options[arg2].selected) ||
				(dlld4 && dlld4.options[arg2].selected))){
				alert('已存在！')
				return false;
			}
			dlld3.options[arg2].selected = true;
		}
	}
	if(!isNaN(arg3)){
		if(dlld4){
			if(!type && ((dlld1 && dlld1.options[arg3].selected) ||
				(dlld2 && dlld2.options[arg3].selected) ||
				(dlld3 && dlld3.options[arg3].selected))){
				alert('已存在！')
				return false;
			}
			dlld4.options[arg3].selected = true;
		}
	}
	return true;
}

function changeLdxx(ele){
	
	var slcIndex = ele.id.charAt(4);
	var flag = false;
	
	if(slcIndex == 1){
		flag = initLdOpt(ele.selectedIndex,'null','null','null');
		if(flag)
			for(var i = 0; i < ldsObjs.length; i ++){
				if(ldsObjs[i].llbh == ele.value){
					$('llbh1').value = ldsObjs[i].llbh;
					$('CARNUM1').value = ldsObjs[i].carnum;
					$('KC1').value = ldsObjs[i].kc;
					$('ZJC1').value = ldsObjs[i].zjc;
					return;
				}
			}
	}
	if(slcIndex == 2){
		flag = initLdOpt('null',ele.selectedIndex,'null','null');
		if(flag)
			for(var i = 0; i < ldsObjs.length; i ++){
				if(ldsObjs[i].llbh == ele.value){
					$('llbh2').value = ldsObjs[i].llbh;
					$('CARNUM2').value = ldsObjs[i].carnum;
					$('KC2').value = ldsObjs[i].kc;
					$('ZJC2').value = ldsObjs[i].zjc;
					return;
				}
			}
	}
	if(slcIndex == 3){
		flag = initLdOpt('null','null',ele.selectedIndex,'null');
		if(flag)
			for(var i = 0; i < ldsObjs.length; i ++){
				if(ldsObjs[i].llbh == ele.value){
					$('llbh3').value = ldsObjs[i].llbh;
					$('CARNUM3').value = ldsObjs[i].carnum;
					$('KC3').value = ldsObjs[i].kc;
					$('ZJC3').value = ldsObjs[i].zjc;
					return;
				}
			}
	}
	if(slcIndex == 4){
		flag = initLdOpt('null','null','null',ele.selectedIndex);
		if(flag)
			for(var i = 0; i < ldsObjs.length; i ++){
				if(ldsObjs[i].llbh == ele.value){
					$('llbh4').value = ldsObjs[i].llbh;
					$('CARNUM4').value = ldsObjs[i].carnum;
					$('KC4').value = ldsObjs[i].kc;
					$('ZJC4').value = ldsObjs[i].zjc;
					return;
				}
			}
	}
	ele.options[0].selected = true;
	$('llbh' + slcIndex).value = '';
	$('CARNUM' + slcIndex).value = '';
	$('KC' + slcIndex).value = '';
	$('ZJC' + slcIndex).value = '';
}

function fillSlct(ele,value){
	$(ele).value = value;
	
	if($('insrtOrUpdt').value == '2'){
		$(ele).disabled = true;
	}
	
	iptSlct('TRJL');
}
function setStyle(ele){
	if($('insrtOrUpdt').value == '2'){
		$(ele).disabled = true;
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
			var str = '';
			if($('insrtOrUpdt').value == '2'){
				str = '<div><span id="DLBH1s"></span><input maxLength="8" readOnly id="LDMC1" class="input6" type="text" onfocus="this.select();" />路段共';
				str += '<input maxLength="8" readOnly id="CARNUM1" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
				str += '<input maxLength="8" readOnly id="KC1" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
				str += '<input maxLength="8" readOnly id="ZJC1" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。';
				str += '</div><div><span id="DLBH2s"></span><input maxLength="8" readOnly id="LDMC2" class="input6" type="text" onfocus="this.select();" />路段共';
				str += '<input maxLength="8" readOnly id="CARNUM2" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
				str += '<input maxLength="8" readOnly id="KC2" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
				str += '<input maxLength="8" readOnly id="ZJC2" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。'
				str += '</div><div><span id="DLBH3s"></span><input maxLength="8" readOnly id="LDMC3" class="input6" type="text" onfocus="this.select();" />路段共';
				str += '<input maxLength="8" readOnly id="CARNUM3" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
				str += '<input maxLength="8" readOnly id="KC3" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
				str += '<input maxLength="8" readOnly id="ZJC3" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。';
				str += '</div><div><span id="DLBH4s"></span><input maxLength="8" readOnly id="LDMC4" class="input6" type="text" onfocus="this.select();" />路段共';
				str += '<input maxLength="8" readOnly id="CARNUM4" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
				str += '<input maxLength="8" readOnly id="KC4" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
				str += '<input maxLength="8" readOnly id="ZJC4" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。</div>';
			}else{
				str = '<div><span id="DLBH1s"></span><input maxLength="8" id="LDMC1" class="input6" type="text" onfocus="this.select();" />路段共';
				str += '<input maxLength="8" id="CARNUM1" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
				str += '<input maxLength="8" id="KC1" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
				str += '<input maxLength="8" id="ZJC1" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。';
				str += '</div><div><span id="DLBH2s"></span><input maxLength="8" id="LDMC2" class="input6" type="text" onfocus="this.select();" />路段共';
				str += '<input maxLength="8" id="CARNUM2" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
				str += '<input maxLength="8" id="KC2" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
				str += '<input maxLength="8" id="ZJC2" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。'
				str += '</div><div><span id="DLBH3s"></span><input maxLength="8" id="LDMC3" class="input6" type="text" onfocus="this.select();" />路段共';
				str += '<input maxLength="8" id="CARNUM3" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
				str += '<input maxLength="8" id="KC3" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
				str += '<input maxLength="8" id="ZJC3" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。';
				str += '</div><div><span id="DLBH4s"></span><input maxLength="8" id="LDMC4" class="input6" type="text" onfocus="this.select();" />路段共';
				str += '<input maxLength="8" id="CARNUM4" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车';
				str += '<input maxLength="8" id="KC4" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车';
				str += '<input maxLength="8" id="ZJC4" class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。</div>';
			}
			$('ldxx').innerHTML = str;
		
			url = "drpt.dailyRptModify.queryRoad.d";
			url = encodeURI(url);
		
			var params = "RZBH=" + bh.value;
			params = encodeURI(params);
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp){
				var xmlDoc = xmlHttp.responseXML;
				var rows = xmlDoc.getElementsByTagName("row");
				var i = 0;
				for(; i < rows.length; i ++){
					results = rows[i].childNodes;
					$('LDMC' + (i + 1)).value = results[1].text;
					$('CARNUM' + (i + 1)).value = results[2].text;
					$('KC' + (i + 1)).value = results[3].text;
					$('ZJC' + (i + 1)).value = results[4].text;
					fillListBox("DLBH" + (i + 1) + "s","DLBH" + (i + 1),"110","select dlbh,dlmc from t_oa_dict_road where jgid like '" + jgid.substring(0,4) + "%'","请选择","fillSlct('DLBH" + (i + 1) + "','" + results[5].text + "')");
				}
				if(i<4){
					for(; i<4; i ++)
					fillListBox("DLBH" + (i + 1) + "s","DLBH" + (i + 1),"110","select dlbh,dlmc from t_oa_dict_road where jgid like '" + jgid.substring(0,4) + "%'","请选择","setStyle('DLBH" + (i + 1) + "')");
				}
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
				
				ldsObjs = new Array();
				ldsOps = new Array();
				if(rows.length > 0){
					for (var i = 0; i < rows.length; i++){
						results = rows[i].childNodes;
						
						ldsObjs.push({
							llbh: results[6].text,
							dlbh: results[5].text,
							dlmc: results[0].text,
							ldmc: results[1].text,
							carnum: results[2].text,
							kc: results[3].text,
							zjc: results[4].text
						})
						
						var optText = '<option dlbh="' + ldsObjs[i].dlbh + '" dlmc="' + ldsObjs[i].dlmc;
						optText += '" ldmc="' + ldsObjs[i].ldmc + '" value="' + ldsObjs[i].llbh + '">' + ldsObjs[i].dlmc + '  ';
						optText += ldsObjs[i].ldmc + '</option>';
						
						ldsOps.push(optText);
					}
				}
				
				var str = '';
				var opt_str = '';
				for(var i = 0; i < ldsOps.length; i ++){
					opt_str += ldsOps[i];
				}
				
				var textStyle = '';
				var selectStyle = '';
				if($('insrtOrUpdt').value == '2'){
					textStyle = ' readOnly';
					selectStyle = ' disabled';
				}
				
				for (var i = 0; i < ldsObjs.length; i ++){
					var index = i+1;
					str += '<div id="cllbox' + index + '"><input type="hidden" id="llbh' + index + '" value="';
					str += ldsObjs[i].llbh + '" /><select style="width:180px;margin:auto 20px;"' + selectStyle + ' onchange="changeLdxx(this);" id="dlld' + index + '">';
					str += '<option>请选择</option>'
					str += opt_str + '</select>路段共<input' + textStyle + ' id="CARNUM' + index + '" class="input5" type="text"';
					str += ' value="' + ldsObjs[i].carnum + '"' + textStyle + ' onkeypress="checkNum(this,event)" onfocus="this.select();" />车次，其中：客车<input id="KC' + index;
					str += '" value="' + ldsObjs[i].kc + '"' + textStyle + ' class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次、自驾车<input id="ZJC' + index;
					str += '" value="' + ldsObjs[i].zjc + '"' + textStyle + ' class="input5" type="text" onkeypress="checkNum(this,event)" onfocus="this.select();" />车次。</div>';
				}
				$('ldxx').innerHTML = str;
				
				initLdOpt(1,2,3,4,true);
			}});
		}
	}});
	iptSlct('TRJL');
}

function savePm(flag){
	var countV = 0;
	var inputs = new Array();
	var bh = $('bh').value;
	var tbdwid = $('tbdwid').value;
	var tjy = $('tjy').value;
	var tjr = $('tjr').value;
	
	var TRJL = $('TRJL').value; //投入警力（人次）
	if(TRJL)	countV ++;
	TRJL = /^\d+$/.test(TRJL)?TRJL:'0';
	var CDJC = $('CDJC').value; //出动警车（辆次）
	if(TRJL)	countV ++;
	CDJC = /^\d+$/.test(CDJC)?CDJC:'0';
	var CSSB = $('CSSB').value;//投入测速设备（台）
	if(TRJL)	countV ++;
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
	
	inputs.push($('TRJL'));
	inputs.push($('CDJC'));
	inputs.push($('CSSB'));
	inputs.push($('GDCSD'));
	inputs.push($('LDCSD'));
	inputs.push($('ZQFWD'));
	inputs.push($('JTWFHJ'));//10
	inputs.push($('CSXS'));
	inputs.push($('KCJY'));
	inputs.push($('PLJS'));
	inputs.push($('JHJS'));
	inputs.push($('WZJS'));
	inputs.push($('NYCWFZK'));
	inputs.push($('DXJDCJSZ'));
	inputs.push($('ZKJTWFCL'));
	inputs.push($('JLJTWFJSR'));
	inputs.push($('XZZYCK'));//20
	inputs.push($('YDTB'));
	inputs.push($('PCAQYHC'));
	inputs.push($('JYTZKCJSR'));
	inputs.push($('QZPLJSRXX'));
	inputs.push($('ZHCFLB'));
	inputs.push($('JXXCHD'));
	inputs.push($('BFXCGP'));
	inputs.push($('KDXCL'));
	inputs.push($('XCH'));
	inputs.push($('XCZL'));
	inputs.push($('SJY'));
	inputs.push($('DSXC'));
	inputs.push($('DTXC'));
	inputs.push($('BZXC'));
	inputs.push($('WLXC'));
	inputs.push($('ZHS'));
	inputs.push($('TBKYBM'));//
	inputs.push($('SRYSQY'));//
	inputs.push($('JYYSJSR'));//
	inputs.push($('ELTQYA'));//
	inputs.push($('YJSDFL'));//
	inputs.push($('ZZWXLD'));
	inputs.push($('SWSG'));
	inputs.push($('SWRS'));//
	inputs.push($('SSRS'));//
	inputs.push($('TDSG'));//
	inputs.push($('TDSWRS'));//
	inputs.push($('TDSSRS'));
	inputs.push($('LSZQD'));
	inputs.push($('CARNUM1'));
	inputs.push($('KC1'));
	inputs.push($('ZJC1'));
	inputs.push($('CARNUM2'));
	inputs.push($('KC2'));
	inputs.push($('ZJC2'));
	inputs.push($('CARNUM3'));
	inputs.push($('KC3'));
	inputs.push($('ZJC3'));
	inputs.push($('CARNUM4'));
	inputs.push($('KC4'));
	inputs.push($('ZJC4'));
	inputs.push($('JCKYCL'));
	
	
	var TBR = $('TBR').value;
	var SHR = $('SHR').value;
	
	var DLBH1 = '';
	var LDMC1 = '';
	var CARNUM1 = '';
	var KC1 = '';
	var ZJC1 = '';
	if($('DLBH1')){
		DLBH1 = $('DLBH1').value;
		LDMC1 = $('LDMC1').value;
		CARNUM1 = $('CARNUM1').value;
		CARNUM1 = /^\d+$/.test(CARNUM1)?CARNUM1:'0';
		KC1 = $('KC1').value;
		KC1 = /^\d+$/.test(KC1)?KC1:'0';
		ZJC1 = $('ZJC1').value;
		ZJC1 = /^\d+$/.test(ZJC1)?ZJC1:'0';
		
		if(!(DLBH1&&LDMC1)){
			DLBH1 = '';
		}
	}else{
		var dlld1 = $('dlld1');
		if($('dlld1')){
			DLBH1 = $('dlld1').options[$('dlld1').selectedIndex].dlbh;
			LDMC1 = $('dlld1').options[$('dlld1').selectedIndex].ldmc;
			CARNUM1 = $('CARNUM1').value;
			CARNUM1 = /^\d+$/.test(CARNUM1)?CARNUM1:'0';
			KC1 = $('KC1').value;
			KC1 = /^\d+$/.test(KC1)?KC1:'0';
			ZJC1 = $('ZJC1').value;
			ZJC1 = /^\d+$/.test(ZJC1)?ZJC1:'0';
			
			if(!(DLBH1&&LDMC1)){
				DLBH1 = '';
			}
		}
	}
	
	var DLBH2 = '';
	var LDMC2 = '';
	var CARNUM2 = '';
	var KC2 = '';
	var ZJC2 = '';
	
	if($('DLBH2')){
		DLBH2 = $('DLBH2').value;
		LDMC2 = $('LDMC2').value;
		CARNUM2 = $('CARNUM2').value;
		CARNUM2 = /^\d+$/.test(CARNUM2)?CARNUM2:'0';
		KC2 = $('KC2').value;
		KC2 = /^\d+$/.test(KC2)?KC2:'0';
		ZJC2 = $('ZJC2').value;
		ZJC2 = /^\d+$/.test(ZJC2)?ZJC2:'0';
		
		if(!(DLBH2&&LDMC2)){
			DLBH2 = '';
		}
	}else{
		var dlld2 = $('dlld2');
		if($('dlld2')){
			DLBH2 = $('dlld2').options[$('dlld2').selectedIndex].dlbh;
			LDMC2 = $('dlld2').options[$('dlld2').selectedIndex].ldmc;
			CARNUM2 = $('CARNUM2').value;
			CARNUM2 = /^\d+$/.test(CARNUM2)?CARNUM2:'0';
			KC2 = $('KC2').value;
			KC2 = /^\d+$/.test(KC2)?KC2:'0';
			ZJC2 = $('ZJC2').value;
			ZJC2 = /^\d+$/.test(ZJC2)?ZJC2:'0';
			if(!(DLBH2&&LDMC2)){
				DLBH2 = '';
			}
		}
	}
	
	var DLBH3 = '';
	var LDMC3 = '';
	var CARNUM3 = '';
	var KC3 = '';
	var ZJC3 = '';
	
	if($('DLBH3')){
		DLBH3 = $('DLBH3').value;
		LDMC3 = $('LDMC3').value;
		CARNUM3 = $('CARNUM3').value;
		CARNUM3 = /^\d+$/.test(CARNUM3)?CARNUM3:'0';
		KC3 = $('KC3').value;
		KC3 = /^\d+$/.test(KC3)?KC3:'0';
		ZJC3 = $('ZJC3').value;
		ZJC3 = /^\d+$/.test(ZJC3)?ZJC3:'0';
		
		if(!(DLBH3&&LDMC3)){
				DLBH3 = '';
		}
	}else{
		var dlld3 = $('dlld3');
		if($('dlld3')){
			DLBH3 = $('dlld3').options[$('dlld3').selectedIndex].dlbh;
			LDMC3 = $('dlld3').options[$('dlld3').selectedIndex].ldmc;
			CARNUM3 = $('CARNUM3').value;
			CARNUM3 = /^\d+$/.test(CARNUM3)?CARNUM3:'0';
			KC3 = $('KC3').value;
			KC3 = /^\d+$/.test(KC3)?KC3:'0';
			ZJC3 = $('ZJC3').value;
			ZJC3 = /^\d+$/.test(ZJC3)?ZJC3:'0';
			
			if(!(DLBH3&&LDMC3)){
				DLBH3 = '';
			}
		}
	}
	
	var DLBH4 = '';
	var LDMC4 = '';
	var CARNUM4 = '';
	var KC4 = '';
	var ZJC4 = '';
	
	if($('DLBH4')){
		DLBH4 = $('DLBH4').value;
		LDMC4 = $('LDMC4').value;
		CARNUM4 = $('CARNUM4').value;
		CARNUM4 = /^\d+$/.test(CARNUM4)?CARNUM4:'0';
		KC4 = $('KC4').value;
		KC4 = /^\d+$/.test(KC4)?KC4:'0';
		ZJC4 = $('ZJC4').value;
		ZJC4 = /^\d+$/.test(ZJC4)?ZJC4:'0';
		
		if(!(DLBH4&&LDMC4)){
				DLBH4 = '';
		}
	}else{
		var dlld4 = $('dlld4');
		if($('dlld4')){
			DLBH4 = $('dlld4').options[$('dlld4').selectedIndex].dlbh;
			LDMC4 = $('dlld4').options[$('dlld4').selectedIndex].ldmc;
			CARNUM4 = $('CARNUM4').value;
			CARNUM4 = /^\d+$/.test(CARNUM4)?CARNUM4:'0';
			KC4 = $('KC4').value;
			KC4 = /^\d+$/.test(KC4)?KC4:'0';
			ZJC4 = $('ZJC4').value;
			ZJC4 = /^\d+$/.test(ZJC4)?ZJC4:'0';
			
			if(!(DLBH4&&LDMC4)){
				DLBH4 = '';
			}
		}
	}
	
	var LXDH = $('LXDH').value;
	
	var reg=/(^[0-9]{3,4}[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)|(^0{0,1}15[0-9]{9}$)/; 
    if (LXDH && !reg.test(LXDH)){
	    LXDH = '';
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
	
	dataArray.push(DLBH1);//40
	dataArray.push(LDMC1);
	dataArray.push(CARNUM1);
	dataArray.push(KC1);
	dataArray.push(ZJC1);
	dataArray.push(DLBH2);
	dataArray.push(LDMC2);
	dataArray.push(CARNUM2);
	dataArray.push(KC2);
	dataArray.push(ZJC2);
	dataArray.push(DLBH3);//50
	dataArray.push(LDMC3);
	dataArray.push(CARNUM3);
	dataArray.push(KC3);
	dataArray.push(ZJC3);
	dataArray.push(DLBH4);
	dataArray.push(LDMC4);
	dataArray.push(CARNUM4);
	dataArray.push(KC4);
	dataArray.push(ZJC4);//59
	
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
	
	dataArray.push(LSZQD);
	
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
	
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp){
		var result = xmlHttp.responseText;
		if(result.indexOf('失败') > -1){
			alert(result);
			return;
		}
		if(!bh){
			$('bh').value = xmlHttp.responseText;
		}
	}});
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
	
	
	
	var TBR = $('TBR').value;
	if(!TBR){
		alert('请填写填表人！');
	    $('TBR').focus();
	    return;
	}
	
	var SHR = $('SHR').value;
	
	
	
	var DLBH1 = '';
	var LDMC1 = '';
	var CARNUM1 = '';
	var KC1 = '';
	var ZJC1 = '';
	if($('DLBH1')){
		DLBH1 = $('DLBH1').value;
		LDMC1 = $('LDMC1').value;
		CARNUM1 = $('CARNUM1').value;
		CARNUM1 = /^\d+$/.test(CARNUM1)?CARNUM1:'0';
		KC1 = $('KC1').value;
		KC1 = /^\d+$/.test(KC1)?KC1:'0';
		ZJC1 = $('ZJC1').value;
		ZJC1 = /^\d+$/.test(ZJC1)?ZJC1:'0';
		
		if(DLBH1 || LDMC1){
			if(!DLBH1){
				alert('请选择道路！');
				return;
			}
			if(!LDMC1){
				alert('请填写路段名称！');
				$('LDMC1').focus();
				return;
			}
		}else{
			if(CARNUM1 != '0'){
				alert('请选择道路！');
				return;
			}
			if(KC1 != '0'){
				alert('请选择道路！');
				return;
			}
			if(ZJC1 != '0'){
				alert('请选择道路！');
				return;
			}
		}
		if(CARNUM1 - KC1 < ZJC1){
			alert("数量输入有误！");
			$('CARNUM1').focus();
			return;
		}
	}else{
		var dlld1 = $('dlld1');
		if($('dlld1')){
			DLBH1 = $('dlld1').options[$('dlld1').selectedIndex].dlbh;
			LDMC1 = $('dlld1').options[$('dlld1').selectedIndex].ldmc;
			CARNUM1 = $('CARNUM1').value;
			CARNUM1 = /^\d+$/.test(CARNUM1)?CARNUM1:'0';
			KC1 = $('KC1').value;
			KC1 = /^\d+$/.test(KC1)?KC1:'0';
			ZJC1 = $('ZJC1').value;
			ZJC1 = /^\d+$/.test(ZJC1)?ZJC1:'0';
			
			if(DLBH1 || LDMC1){
				if(!DLBH1){
					alert('请选择道路名称！');
					return;
				}
				if(!LDMC1){
					alert('请填写路段名称！');
					$('LDMC1').focus();
					return;
				}
			}else{
				if(CARNUM1 != '0'){
					alert('请选择道路！');
					return;
				}
				if(KC1 != '0'){
					alert('请选择道路！');
					return;
				}
				if(ZJC1 != '0'){
					alert('请选择道路！');
					return;
				}
			}
			if(CARNUM1 - KC1 < ZJC1){
				alert("数量输入有误！");
				$('CARNUM1').focus();
				return;
			}
		}
	}
	
	var DLBH2 = '';
	var LDMC2 = '';
	var CARNUM2 = '';
	var KC2 = '';
	var ZJC2 = '';
	
	if($('DLBH2')){
		DLBH2 = $('DLBH2').value;
		LDMC2 = $('LDMC2').value;
		CARNUM2 = $('CARNUM2').value;
		CARNUM2 = /^\d+$/.test(CARNUM2)?CARNUM2:'0';
		KC2 = $('KC2').value;
		KC2 = /^\d+$/.test(KC2)?KC2:'0';
		ZJC2 = $('ZJC2').value;
		ZJC2 = /^\d+$/.test(ZJC2)?ZJC2:'0';
		
		if(DLBH2 || LDMC2){
			if(!DLBH2){
				alert('请选择道路名称！');
				return;
			}
			if(!LDMC2){
				alert('请填写路段名称！');
				$('LDMC2').focus();
				return;
			}
		}else{
			if(CARNUM2 != '0'){
				alert('请选择道路！');
				return;
			}
			if(KC2 != '0'){
				alert('请选择道路！');
				return;
			}
			if(ZJC2 != '0'){
				alert('请选择道路！');
				return;
			}
		}
		if(CARNUM2 - KC2 < ZJC2){
			alert("数量输入有误！");
			$('CARNUM2').focus();
			return;
		}
	}else{
		var dlld2 = $('dlld2');
		if($('dlld2')){
			DLBH2 = $('dlld2').options[$('dlld2').selectedIndex].dlbh;
			LDMC2 = $('dlld2').options[$('dlld2').selectedIndex].ldmc;
			CARNUM2 = $('CARNUM2').value;
			CARNUM2 = /^\d+$/.test(CARNUM2)?CARNUM2:'0';
			KC2 = $('KC2').value;
			KC2 = /^\d+$/.test(KC2)?KC2:'0';
			ZJC2 = $('ZJC2').value;
			ZJC2 = /^\d+$/.test(ZJC2)?ZJC2:'0';
			if(DLBH2 || LDMC2){
				if(!DLBH2){
					alert('请选择道路名称！');
					return;
				}
				if(!LDMC2){
					alert('请填写路段名称！');
					$('LDMC2').focus();
					return;
				}
			}else{
				if(CARNUM2 != '0'){
					alert('请选择道路！');
					return;
				}
				if(KC2 != '0'){
					alert('请选择道路！');
					return;
				}
				if(ZJC2 != '0'){
					alert('请选择道路！');
					return;
				}
			}
			if(CARNUM2 - KC2 < ZJC2){
				alert("数量输入有误！");
				$('CARNUM2').focus();
				return;
			}
		}
	}
	
	var DLBH3 = '';
	var LDMC3 = '';
	var CARNUM3 = '';
	var KC3 = '';
	var ZJC3 = '';
	
	if($('DLBH3')){
		DLBH3 = $('DLBH3').value;
		LDMC3 = $('LDMC3').value;
		CARNUM3 = $('CARNUM3').value;
		CARNUM3 = /^\d+$/.test(CARNUM3)?CARNUM3:'0';
		KC3 = $('KC3').value;
		KC3 = /^\d+$/.test(KC3)?KC3:'0';
		ZJC3 = $('ZJC3').value;
		ZJC3 = /^\d+$/.test(ZJC3)?ZJC3:'0';
		
		if(DLBH3 || LDMC3){
			if(!DLBH3){
				alert('请选择道路名称！');
				return;
			}
			if(!LDMC3){
				alert('请填写路段名称！');
				$('LDMC3').focus();
				return;
			}
		}else{
			if(CARNUM3 != '0'){
				alert('请选择道路！');
				return;
			}
			if(KC3 != '0'){
				alert('请选择道路！');
				return;
			}
			if(ZJC3 != '0'){
				alert('请选择道路！');
				return;
			}
		}
		if(CARNUM3 - KC3 < ZJC3){
			alert("数量输入有误！");
			$('CARNUM3').focus();
			return;
		}
	}else{
		var dlld3 = $('dlld3');
		if($('dlld3')){
			DLBH3 = $('dlld3').options[$('dlld3').selectedIndex].dlbh;
			LDMC3 = $('dlld3').options[$('dlld3').selectedIndex].ldmc;
			CARNUM3 = $('CARNUM3').value;
			CARNUM3 = /^\d+$/.test(CARNUM3)?CARNUM3:'0';
			KC3 = $('KC3').value;
			KC3 = /^\d+$/.test(KC3)?KC3:'0';
			ZJC3 = $('ZJC3').value;
			ZJC3 = /^\d+$/.test(ZJC3)?ZJC3:'0';
			
			if(DLBH3 || LDMC3){
				if(!DLBH3){
					alert('请选择道路名称！');
					return;
				}
				if(!LDMC3){
					alert('请填写路段名称！');
					$('LDMC3').focus();
					return;
				}
			}else{
				if(CARNUM3 != '0'){
					alert('请选择道路！');
					return;
				}
				if(KC3 != '0'){
					alert('请选择道路！');
					return;
				}
				if(ZJC3 != '0'){
					alert('请选择道路！');
					return;
				}
			}
			if(CARNUM3 - KC3 < ZJC3){
				alert("数量输入有误！");
				$('CARNUM3').focus();
				return;
			}
		}
	}
	
	var DLBH4 = '';
	var LDMC4 = '';
	var CARNUM4 = '';
	var KC4 = '';
	var ZJC4 = '';
	
	if($('DLBH4')){
		DLBH4 = $('DLBH4').value;
		LDMC4 = $('LDMC4').value;
		CARNUM4 = $('CARNUM4').value;
		CARNUM4 = /^\d+$/.test(CARNUM4)?CARNUM4:'0';
		KC4 = $('KC4').value;
		KC4 = /^\d+$/.test(KC4)?KC4:'0';
		ZJC4 = $('ZJC4').value;
		ZJC4 = /^\d+$/.test(ZJC4)?ZJC4:'0';
		
		if(DLBH4 || LDMC4){
			if(!DLBH4){
				alert('请选择道路名称！');
				return;
			}
			if(!LDMC4){
				alert('请填写路段名称！');
				$('LDMC4').focus();
				return;
			}
		}else{
			if(CARNUM4 != '0'){
				alert('请选择道路！');
				return;
			}
			if(KC4 != '0'){
				alert('请选择道路！');
				return;
			}
			if(ZJC4 != '0'){
				alert('请选择道路！');
				return;
			}
		}
		if(CARNUM4 - KC4 < ZJC4){
			alert("数量输入有误！");
			$('CARNUM4').focus();
			return;
		}
	}else{
		var dlld4 = $('dlld4');
		if($('dlld4')){
			DLBH4 = $('dlld4').options[$('dlld4').selectedIndex].dlbh;
			LDMC4 = $('dlld4').options[$('dlld4').selectedIndex].ldmc;
			CARNUM4 = $('CARNUM4').value;
			CARNUM4 = /^\d+$/.test(CARNUM4)?CARNUM4:'0';
			KC4 = $('KC4').value;
			KC4 = /^\d+$/.test(KC4)?KC4:'0';
			ZJC4 = $('ZJC4').value;
			ZJC4 = /^\d+$/.test(ZJC4)?ZJC4:'0';
			
			if(DLBH4 || LDMC4){
				if(!DLBH4){
					alert('请选择道路名称！');
					return;
				}
				if(!LDMC4){
					alert('请填写路段名称！');
					$('LDMC4').focus();
					return;
				}
			}else{
				if(CARNUM4 != '0'){
					alert('请选择道路！');
					return;
				}
				if(KC4 != '0'){
					alert('请选择道路！');
					return;
				}
				if(ZJC4 != '0'){
					alert('请选择道路！');
					return;
				}
			}
			if(CARNUM4 - KC4 < ZJC4){
				alert("数量输入有误！");
				$('CARNUM4').focus();
				return;
			}
		}
	}
	
	
	var LSZQD = $('LSZQD').value;//新增设置临时执勤点（个）
	LSZQD = /^\d+$/.test(LSZQD)?LSZQD:'0';
	
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
	
	dataArray.push(DLBH1);//40
	dataArray.push(LDMC1);
	dataArray.push(CARNUM1);
	dataArray.push(KC1);
	dataArray.push(ZJC1);
	dataArray.push(DLBH2);
	dataArray.push(LDMC2);
	dataArray.push(CARNUM2);
	dataArray.push(KC2);
	dataArray.push(ZJC2);
	dataArray.push(DLBH3);//50
	dataArray.push(LDMC3);
	dataArray.push(CARNUM3);
	dataArray.push(KC3);
	dataArray.push(ZJC3);
	dataArray.push(DLBH4);
	dataArray.push(LDMC4);
	dataArray.push(CARNUM4);
	dataArray.push(KC4);
	dataArray.push(ZJC4);//59
	
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
	
	dataArray.push(LSZQD);
	
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
	
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function(xmlHttp){
		var result = xmlHttp.responseText;
		if(result.indexOf('失败') > -1){
			alert(result);
			window.close();
		}
		if(flag){
			$('bh').value = xmlHttp.responseText;
			return;
		}
		
		if(result.indexOf('success') > -1){
			alert('日报保存成功！');
			
		}
		
		window.close();
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
//		window.open("dailyReport2012.jsp?tmp="+Math.random()+"&insrtOrUpdt=1&bh=" + itemValue+"&tjrq="+tjrq,"newwindow","height=580px,width=870px,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no,top=100,left=300")
		window.showModalDialog("dailyReport2012.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&bh=" + itemValue+"&tjrq="+tjrq, "", "dialogWidth:870px;dialogHeight:724px;");
		doOnLoad();
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("dailyReport2012.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&bh=" + itemValue, "", "dialogWidth:870px;dialogHeight:724px;");
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
		$(ele).focus();
		return;
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
