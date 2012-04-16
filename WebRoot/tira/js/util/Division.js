
var division = new Division();
function Division() {
	
	//页面元素id
	this.containId = "divisionContainId";
	this.cityTdId = "cityTdId";
	this.zoneTdId = "zoneTdId";
	this.citySelId = "citySelId";
	this.zoneSelId = "zoneSelId";
	this.divisionId = "divisionId";
	
	
	this.isEndNode = false;
	this.isOnLoad = true;
	this.ccback = null;
	
	//行政区划
	this.cityCode = "";
	this.cityDesc = "";
	this.zoneCode = "";
	this.zoneDesc = "";
	this.divisionCode = "";
	this.divisionDesc = "";
	this.citySelWidth = "80";
	this.zoneSelWidth = "100";
	
	this.dmlb = "";
	this.localProvince = "";
	
	this.loginPageUrl = "";
	this.nullResponse = "null";
}

Division.prototype.createTable = function(pId) {
	this.containId = pId;
	var table = "<table>\
			    	<tr>\
			    		<td class=\"td_2\" align=\"right\" width=\"0\">\
			    			<input id=\""+this.divisionId+"\" type=\"hidden\" value=\"\" />\
			    		</td>\
			    		<td id=\""+this.cityTdId+"\" align=\"right\"  class=\"td_2\" width=\""+this.citySelWidth+"\">\
			    		</td>\
			    		<td id=\""+this.zoneTdId+"\" align=\"left\"  class=\"td_2\" width=\""+this.zoneSelWidth+"\">\
			    		</td>\
			    	</tr>\
			     </table>";
	
	$(this.containId).innerHTML = table;
}

/**
 * 通过用户名，得到机构表中的机构id
 * @param {String} uname 用户名
 * @return {String} jgid 机构id
 */
Division.prototype.getJgidByUname = function(uname,method) {
	if(!Division.prototype.checkParam(uname)) {
		return null;
	}
	var url = "sa.DivisionAction.getJgidByUname.d";
	var params = "uname="+uname;
	new Ajax.Request(url, 
		{
				method: "post", 
				parameters: params, 
				onComplete: Division.prototype.gjbuCallback
		});
	
};

/**
 * 通过用户名得到机构id的回调函数
 * @param {Object} xmlHttp
 * @return {TypeName} 
 */
Division.prototype.gjbuCallback = function(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var jgid = xmlDoc.getElementsByTagName("jgid")[0].text;
	var sql = 
	"select \
		dmz,\
		rtrim(dmsm1,'（*）')\
	from \
		t_tira_code \
	where \
		dmlb='"+division.dmlb+"' and ";
	if (jgid.substr(4,2)!="00") {//区县
		sql += "dmz='"+jgid+"' order by dmz";
		fillListBox(division.zoneTdId,division.zoneSelId,division.zoneSelWidth,sql,"","division.zoneCallBack()","division.zoneOnChange","");
	}else if (jgid.substr(2,2)!="00") {//市
		sql += "substr(dmz,1,4)||'00'='"+jgid+"' order by dmz";
		fillListBox(division.zoneTdId,division.zoneSelId,division.zoneSelWidth,sql,"","division.zoneCallBack()","division.zoneOnChange","");
	}else {//省
		sql += "substr(dmz,5,2) = '00' and substr(dmz,1,2)||'0000'='"+jgid+"' order by dmz";
		fillListBox(division.cityTdId,division.citySelId,division.citySelWidth,sql,"","division.cityCallBack()","division.cityOnChange","");
	}
}

Division.prototype.cityCallBack = function() {
	Division.prototype.removeFirstItem(division.citySelId);
	var sel = $(division.citySelId);
	division.divisionCode = sel.value;
	division.divisionDesc = sel.options[sel.selectedIndex].innerText;
	if (division.isOnLoad && (division.ccback != undefined || division.ccback != null )) {
		division.isOnLoad = false;
		eval(division.ccback);
	}
};

Division.prototype.zoneCallBack = function() {
	Division.prototype.removeFirstItem(division.zoneSelId);
	var sel = $(division.zoneSelId);
	if (sel.value == division.d)
	division.divisionCode = sel.value;
	division.divisionDesc = sel.options[sel.selectedIndex].innerText;
	if (division.isOnLoad && (division.ccback != undefined || division.ccback != null )) {
		division.isOnLoad = false;
		eval(division.ccback);
	}
};

Division.prototype.cityOnChange = function() {
	if (this.value.substr(2) == "0000") {
		$(division.zoneTdId).innerHTML = "";
	}else {
		division.createSubItems(this.value);
	}
	division.cityCode = this.value;
	division.cityDesc = this.options[this.selectedIndex].innerText;
	division.divisionCode = division.cityCode;
	division.divisionDesc = division.cityDesc;
	$(division.divisionId).value = this.value;
};

Division.prototype.zoneOnChange = function() {
	
	division.zoneCode = this.value;
	division.zoneDesc = this.options[this.selectedIndex].innerText;
	division.divisionCode = division.zoneCode;
	if (this.value.substr(4)=="00") {
		division.divisionDesc = division.cityDesc;
	}else {
		division.divisionDesc = division.zoneDesc;
	}
	$(division.divisionId).value = this.value;
};

Division.prototype.createSubItems = function(value) {
	var sql = "select dmz, rtrim(decode(substr(dmz,5,2),'00','全市',dmsm1),'（*）') "+
			  " from t_tira_code where dmlb='"+division.dmlb+"' and "+
 	 		  "substr(dmz,1,4)||'00'='"+value+"' order by dmz";
	fillListBox(division.zoneTdId,division.zoneSelId,division.zoneSelWidth,sql,"","division.zoneCallBack()","division.zoneOnChange","");
};

Division.prototype.removeFirstItem = function(selectId) {
	var options = $(selectId).childNodes;
	options[0].parentNode.removeChild(options[0]);
};

/**
 * 验证参数
 * @param {Object} param
 * @return {boolean} 通过：true；通不过：false 
 */
Division.prototype.checkParam = function(param) {
	if(param == undefined || param == null) {
		return false;
	}
	return true;
};