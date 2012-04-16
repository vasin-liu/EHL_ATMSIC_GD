/*
1.本js引用prototype.js。
2.如果是省显示市的列表框，如果是市显示区县的列表框，如果是区县，到了最末位，则显示自己。
通过onchange事件来显示其子项，如果没有子项，则不显示或者隐藏显示。
把行政区划代码值放入隐藏域内保存，由父项和子项（如果存在）共同确定。
用户名不满足，处理情况
机构权限不满足，处理情况
*/

//由于异步请求后的回调函数中的this对象改变，所以在此建立该 对象，需要设置dmlb。
var division = new Division();
/**
 * 行政区划
 *  
 */
function Division(dmlb,localProvince,loginPageUrl,divisionList,citySelWidth,zoneSelWidth) {
	
	this.cityTdId = "cityTdId";
	this.zoneTdId = "zoneTdId";
	this.citySelId = "citySelId";
	this.zoneSelId = "zoneSelId";
	this.divisionId = "divisionId";
	this.citySelWidth = citySelWidth==undefined?"80":citySelWidth;
	this.zoneSelWidth = zoneSelWidth==undefined?"100":citySelWidth;
	
	this.isEndNode = false;
	this.cityCode = "";
	this.cityDesc = "";
	
	this.dmlb = dmlb;
	this.divisionList = divisionList instanceof Array ? divisionList : ["00","00","00","00"];
	this.localProvince = localProvince==undefined?"440000":localProvince;
	
	this.loginPageUrl = loginPageUrl==undefined?"http://localhost:8080/EHL_TIRA_1.4/tira/login.jsp":loginPageUrl;
	this.nullResponse = "null";
	
};

Division.prototype.createTable = function(id) {
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
	$(id).innerHTML = table;	
}

/**
 * 通过用户名，得到机构表中的机构id
 * @param {String} uname 用户名
 * @return {String} jgid 机构id
 */
Division.prototype.getJgidByUname = function(uname) {
	if(!Division.prototype.checkParam(uname)) {
		return null;
	}
	var url = "tira.Division.getJgidByUname.d";
	var params = "uname="+uname;
	new Ajax.Request(url, 
		{
				method: "post", 
				asyn: false,
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
	var xmlDoc = xmlHttp.responseText;
	if (!Division.prototype.checkParam(xmlDoc) || xmlDoc == division.nullResponse) {
		window.location.href = division.loginPageUrl;//用户权限不足，返回登录页面
		return;
	}
	var sql = division.createDivisionSqlByJgid(xmlDoc);
	sql = Division.prototype.filterSql("dmsm1",sql);
	fillListBox(division.cityTdId,division.citySelId,division.citySelWidth,sql,"全省","division.cityCallBack()","division.cityOnChange","");
}

/**
 * 通过机构id，创建获取行政区划列表的sql语句
 * @param {String} jgid 机构id
 * @return {String} 
 */
Division.prototype.createDivisionSqlByJgid = function(jgid) {
	//行政区划列表，由行政区划等级和对应的表示上一级代码组成
	var divisionList = this.divisionList;//由低到高排序的行政区划等级标识符数组
	var upperTop = "";//最高行政区划等级的上一级，为空串
	var lowerDown = "";//最低行政区划等级的下一级，为空串
	var index = jgid.length;//行政区划中当前行政区划等级所在的索引，初始为行政区划的末位的后一位
	var sql = "select dmz,dmsm1 from t_tira_code where dmlb='"+this.dmlb+"' ";
	//处理行政区划中的每一个行政区划等级
	for (var i = 0; i < divisionList.length; i++) {
		var def = divisionList[i];//当前行政区划等级的标识值
		var nextdef = i+1==divisionList.length?upperTop:divisionList[i+1];//下一个行政区划等级的标识值
		index -= def.length;//上调索引，将索引设为当前行政区划等级所在行政区划的索引
		var nextIndex = index - nextdef.length;//上调索引
		var rel = jgid.substr(index,def.length);//真实值
		var nextRel = jgid.substring(nextIndex,index);//下一个真实值
		var upperAll = jgid.substring(0,index);//所有上级的真实值
		var lowerAll = "";//所有下级的标识值
		for (var j = i - 1; j >= 0; j--) {
			lowerAll += divisionList[j];
		}
		if (rel == def) {//上级
			if (nextRel != nextdef) {//上一级
				
				if (i == 0) {
					this.isEndNode = true;
				}
				 sql += "and substr(dmz,1,"+index+")='"+upperAll+"' " + //确定上级域
					  	"and substr(dmz,"+(index+def.length+1)+","+(jgid.length-index-def.length)+")"+(lowerAll==""?" is null ":" = '"+lowerAll+"' ") + //排除下级域
					  	"and substr(dmz,"+(index+1)+","+def.length+")!='"+def+"'";//选取该行政区划等级值，排除标识值
				 break;
			}
			else {//上多级
				if(nextRel==nextdef && nextdef==upperTop) {//已到最高行政区划等级
					sql += "and substr(dmz,1,"+index+") is null " + //确定上级域
						   "and substr(dmz,"+(index+def.length+1)+","+(jgid.length-index-def.length)+")='"+lowerAll+"' " + //排除下级域
						   "and substr(dmz,"+(index+1)+","+def.length+")!='"+def+"'";//选取该行政区划等级值，排除标识值
				}
			}
		}else {//本级
			this.isEndNode = true;
			sql += "and dmz='"+upperAll+rel+lowerAll+"'";//substr(dmz,1,"+index+def.length+")||'"+lowerAll+"'='"+jgid+"'"; //选取该值
			break;
		}
	}
	return sql;
};



Division.prototype.cityCallBack = function() {
	Division.prototype.removeFirstItem(division.citySelId);
};

Division.prototype.zoneCallBack = function() {
	Division.prototype.removeFirstItem(division.zoneSelId);
};

Division.prototype.cityOnChange = function() {
	if (this.value != "") {
		if (!division.isEndNode ) {
			division.createSubItems(this.value);
		}
	}else {
		$(division.zoneTdId).innerHTML = "";
	}
	division.cityCode = this.value;
	division.cityDesc = this.options[this.selectedIndex].innerText;
	$(division.divisionId).value = this.value;
};

Division.prototype.zoneOnChange = function() {
	if (this.value == "") {
		$(division.divisionId).value = division.cityCode;
	}else {
		$(division.divisionId).value = this.value;
	}
};

Division.prototype.createSubItems = function(value) {
	var sql = "select dmz, dmsm1 from t_tira_code where dmlb='"+division.dmlb+"' and "+
 	 		  "substr(dmz,1,4)||'00'='"+value+"' and substr(dmz,5,2)!='00' order by dmz";
	sql = Division.prototype.filterSql("dmsm1", sql);
	fillListBox(division.zoneTdId,division.zoneSelId,division.zoneSelWidth,sql,"全市","division.zoneCallBack()","division.zoneOnChange","");
};

Division.prototype.removeFirstItem = function(selectId) {
	var options = $(selectId).childNodes;
	if(options.length == 2) {
		options[0].parentNode.removeChild(options[0]);
	}
};
/**
 * 移除市辖区选项<br>
 * MD:municipal district
 * @param {Object} selectId
 */
Division.prototype.removeMDItem = function(selectId) {
	var select = $(selectId);
	var options = select.childNodes;
	for (var i = 0; i < options.length; i++) {
		if(options[i].innerText == "市辖区") {
			select.removeChild(options[i]);
		}
	}
	
};

Division.prototype.filterSql = function(col, sql) {
	if (!Division.prototype.checkParam(col) || !Division.prototype.checkParam(sql)) {
		return sql;
	}
	return sql.replace(col,"rtrim("+col+",'（*）')");
};

Division.prototype.filterOption = function(selectId, movestr) {
	if (!movestr) {
		movestr = "（*）";//此处字符不是英文键盘的字符
	}
	
	var options = $(selectId).childNodes;
	for (var i = 0; i < options.length; i++) {
		var option = options[i].innerText;
		if (option.substr(option.length-movestr.length,movestr.length)==movestr) {
			options[i].innerText = option.replace(movestr,"");
		}
	}
};

Division.prototype.findOptionText = function(value) {
	if (value == "") return "全省";
	var id = value.substr(4,2)=="00"?this.citySelectId:this.zoneSelectId;
	var options = $(id).options;
	for (var i = 0; i < options.length; i++) {
		if (options[i].value == value) {
			return options[i].text;
		}
	}
};

/**
 * 验证参数
 * @param {Object} param
 * @return {boolean} 通过：true；通不过：false 
 */
Division.prototype.checkParam = function(param) {
	if(param == undefined || param == null || param.trim().length == 0) {
		return false;
	}
	return true;
};












//String.prototype.trim = function(){ return trim(this);}
//String.prototype.lTrim = function(){return lTrim(this);}
//String.prototype.rTrim = function(){return rTrim(this);}
//
////此处为独立函数
//function lTrim(str)
//{
//    var i;
//    for(i=0;i<str.length;i++)
//    {
//        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;
//    }
//    str=str.substring(i,str.length);
//    return str;
//}
//function rTrim(str)
//{
//    var i;
//    for(i=str.length-1;i>=0;i--)
//    {
//        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;
//    }
//    str=str.substring(0,i+1);
//    return str;
//}
//function trim(str)
//{
//    return lTrim(rTrim(str));
//}

