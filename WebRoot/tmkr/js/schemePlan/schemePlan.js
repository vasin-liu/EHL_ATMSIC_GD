/**基本信息*/
var baseInfo;
/**设置基本信息*/
function setBaseInfo() {
	baseInfo = {
		jgid : $("cjgid").value,
		jgmc : $("cjgmc").value,
		pname : $("cpname").value,
		appid : $("appid").value,
		page : $("page").value,
		time : $("ctime").value
	};
}
/**页面信息*/
var pageInfo;
/**设置新增时的测试数据*/
function setTestValue(){
	$("pname").value = "李继忠";
	$("title").value = "新建方案预案方案管理";
	$("content").value = "方案预案方案管理的建设要落实到位";
	$("ajgid").value = "440100000000；";
	$("ajgmc").value = "广州交警支队；";
}
/**设置添加页面值*/
function setAddValue(){
	UDload.maxnum = 5;
	$("fileRegion").innerHTML = UDload.getForm(560);
	Tree.jgmc="ajgmc";
	Tree.jgid="ajgid";
	$("add").show();
	//setTestValue();
}

/**添加验证*/
function addCheck(){
	pageInfo = {};
	var pname = $("pname");
	var pnvalue = pname.value;
	if(pnvalue == ""){
		alert("请输入录入人！");
		pname.focus();
		return false;
	}else{
		pageInfo.pname = pnvalue;
	}
	var title = $("title");
	var cvalue = title.value;
	if(cvalue == ""){
		alert("请输入预案标题！");
		title.focus();
		return false;
	}else{
		pageInfo.title = cvalue;
	}
	/*
	var content = $("content");
	var cvalue = content.value;
	if(cvalue == ""){
		alert("请输入预案内容！");
		content.focus();
		return false;
	}else{
		pageInfo.content = cvalue;
	}
	*/
	if(!UDload.isSelectFile()){
		alert("请选择附件！");
		return false;
	}
	
	var ajgid = $("ajgid");
	var ajvalue = ajgid.value;
	if(ajvalue){
		ajvalue = ajvalue.replace(/；/g,",");
		ajvalue = ajvalue.substring(0, ajvalue.length - 1);
		pageInfo.ajgid = ajvalue;
	}
	pageInfo.jgid = $("jgid").value;
	pageInfo.time = $("time").value;
	if(!validateInput()){
		return false;
	}
	filterOracle(pageInfo);
	return true;
}

/**添加*/
function add(isValid){
	if (isValid == true || addCheck()) {
		if(!isValid){
			UDload.cbfname = "add(true)";
			UDload.upload();
		}else{
			if(UDload.result){
				pageInfo.apath = UDload.result;
			}
			var url = "tmkr.schemePlan.add.d";
			new Ajax.Request(url, {
				method : "post",
				parameters : pageInfo,
				onComplete : function(xmlHttp) {
					var result = xmlHttp.responseText;
					if(result == "true"){
						alert("添加预案方案管理信息成功！");
						window.location.reload();
					}else if(result == "false"){
						alert("添加预案方案管理信息失败！");
					}else {
						alert("网络异常！");
					}
				}
			});
		}
	}
}

/**设置是否开放给相关单位查询*/
function setIsPublish(el){
	if(el){
		var v = el.value;
		var ajgTr = $("ajgTr");
		var ajgid = $("ajgid");
		var ajgmc = $("ajgmc");
		if(v == "0"){
			ajgTr.hide();
			ajgid.value = "";
			ajgmc.value = "";
		}else if(v == "1"){
			ajgTr.show();
			setTree(baseInfo.jgid,100,200,null,'12');
		}
	}
}

/**设置查询值*/
function setQueryValue(){
	window.G_jgID = baseInfo.jgid;
	var date = new Date();
	$("etime").value = date.format_(3);
	date.setMonth(0);
	date.setDate(1);
	$("stime").value = date.format_(3);
}

/**查询页面对象字段名*/
var fnames = [ "id", "jgid", "jgmc", "pname", "time", "title", "content","apath","ajgid","ajgmc" ];
/**查询页面对象字段描述*/
var fdescs = ["编号","机构编号","机构名称","录入人","录入时间","预案标题","预案内容","附件路径","相关单位编号","相关单位名称"];
/**查询列表数据*/
var queryDatas;
/**查询单条数据*/
var queryData;

function queryCheck(){
	if(!validateInput()){
		return false;
	}
	pageInfo = {
		stime : $("stime").value,
		etime : $("etime").value,
		jgid : window.G_jgID,
		title : $("title").value
	}
	filterOracle(pageInfo);
	return true;
}

function query(){
	if(!queryCheck()){
		return;
	}
	function siftTime(value,cname,refer){
		if(value && cname && refer){
			var format = "yyyy-MM-dd hh24:mi:ss";
			var time = " and to_date('" + value + "','"+format+"') ";
			time += refer;
			time += cname;
			return time;
		}
		return "";
	}
	var sql = "select spm.id,spm.jgid," +
	"(select jgmc from t_sys_department where jgid=spm.jgid)," +
	"spm.pname,to_char(spm.time,'yyyy-mm-dd hh24:mi'),spm.title,spm.content,spm.apath," +
	"spm.ajgid";
	sql += " from t_tmkr_schemePlan spm";
	sql += " where 1=1";
	//自己的预案或者其他单位让自己查看的预案
	sql += " and (spm.jgid='"+baseInfo.jgid+"' or (instr(spm.ajgid,'"+baseInfo.jgid+"')!=0))";
	//筛选预案
	sql += "and "+Dept.siftChilds(pageInfo.jgid,"spm.jgid");
	//起始时间
	var start = pageInfo.stime;
	sql += siftTime(start+" 00:00:00","spm.time","<=");
	//结束时间
	var end = pageInfo.etime;
	sql += siftTime(end+" 23:59:59","spm.time",">=");
	//标题
	var title = pageInfo.title;
	sql += " and spm.title like '%"+title+"%'";	
	sql += " order by spm.jgid,spm.time desc";
	window.toPageNum = window.currPage;
	PageCtrl.initPage("tdData", "pageData", "pageNav", convertSql(sql),
			"showQueryResult", "10");
}

/**设置查询数据*/
function setQueryDatas(xmldoc) {
	if (fnames && xmldoc) {
		queryDatas = [];
		var rows = xmldoc.getElementsByTagName("row");
		var cols;
		for ( var i = 0; i < rows.length; i++) {
			cols = rows[i].childNodes;
			var queryData = {};
			for ( var j = 0; j < fnames.length && j < cols.length; j++) {
				queryData[fnames[j]] = cols[j].text == "null" ? ""
						: cols[j].text;
			}
			queryDatas.push(queryData);
		}
	} else {
		if (!fnames) {
			alert("未获取到查询列名！");
		}
		if (!xmldoc) {
			alert("xmldoc无效！");
		}
	}
}

/**显示查询结果*/
function showQueryResult(xmldoc){
	setQueryDatas(xmldoc);
	var str = getQueryTitle();
	str += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
	str += "<tr class='titleTopBack'>";
	str += "<td width='5%' class='td_r_b td_font'>序号</td>";
	//要显示的字段名索引
	var tindexs = [ 2, 3, 4,  5 ];
	//操作名称
	var onames = ["查看","处理"];
	//要显示的操作名称索引
	var onindexs = [ 0 ];
	if(baseInfo.appid == "1001"){
		onindexs.push(1);
	}
	for ( var i = 0; i < tindexs.length; i++) {
		str += getQueryThead(fdescs[tindexs[i]]);
	}
	for ( var i = 0; i < onindexs.length; i++) {
		str += getQueryThead(onames[onindexs[i]]);
	}
	str += "</tr>";
	if (!queryDatas || queryDatas.length <= 0) {
		str += getQueryNull(tindexs.length + onindexs.length + 1);
	} else {
		var tds;
		var tdtext;
		var ibpath = "../../images/button/";
		//操作数据
		var odata;
		for ( var i = 0; i < queryDatas.length; i++) {
			queryData = queryDatas[i];
			tds = [];
			tds.push(getQueryTd(i+1));
			odata = [];
			odata.push(["para.gif","查看","openDetail("+i+",2)"]);
			if(queryData.jgid == baseInfo.jgid){
				odata.push(["update.gif","处理","openDetail("+i+",3)"]);
			}else{
				odata.push(["lock.png","处理",""]);
			}
			for ( var j = 0; j < tindexs.length; j++) {
				tdtext = queryData[fnames[tindexs[j]]];
				tds.push(getQueryTd(tdtext));
			}
			for ( var j = 0; j < onindexs.length; j++) {
				tdtext = "<img src='" + (ibpath + odata[onindexs[j]][0]) + "' alt='"+odata[onindexs[j]][1]+"' onclick='" + odata[onindexs[j]][2] + "' style='cursor:hand;' />";
				tds.push(getQueryTd(tdtext));
			}
			str += getQueryTr(tds.join(""));
		}
	}
	str += "</table>";
	var pagingObj = $('pageData').up('tr').next('tr').down('td');
	pagingObj.style.textAlign = "center";
	var tabOjb = document.getElementById("pageData");
	tabOjb.innerHTML = str;
	Popup.prototype.hideTips();
	if(window.toPageNum){
		onTurnToPage(toPageNum);
		toPageNum = null;
	}
}

function openDetail(index,page){
	if (index >= 0 && index < queryDatas.length) {
		var url = "schemePlan.jsp?page="+(page||2);
		var feature = "dialogWidth:800px;";
		var pwin = window.showModalDialog(url, queryDatas[index], feature);
		if(page == 3){
			query();
		}
	} else {
		alert("指定打开页面的索引无效！");
	}
}

/**预案方案管理信息*/
var data;
/**设置预案方案管理信息*/
function setData(){
	var url = "tmkr.schemePlan.get.d";
	new Ajax.Request(url,{
		method:"post",
		asynchronous : false,
		parameters:{id:queryData.id},
		onComplete:function(xmlHttp){
			var xmlDoc = xmlHttp.responseXML;
			var plans = xmlDoc.getElementsByTagName("plan");
			if (plans && plans.length == 1) {
				data = {};
				var oattrs = plans[0].attributes;
				for ( var i = 0; i < oattrs.length; i++) {
					data[oattrs[i].nodeName] = oattrs[i].nodeValue;
				}
				data.apath = data.apath.replace(/\\/g,"/");
			}
		}
	});
}

function setReadValue() {
	if(!data){return}
	//设置录入机构、录入人、录入时间
	$("jgmc").value = data.jgmc;
	$("pname").value = data.pname;
	$("pname").disabled = true;
	$("time").value = data.time;
	//设置标题
	$("titleTd").innerText = data.title;
	//设置内容
	$("contentTd").innerHTML = data.content;
	//设置附件值
	$("apathDescTd").innerText = "附件名称";
	$("fileRegion").innerHTML = UDload.getAttachHtmls(data.apath) || "暂无附件";
	//开放给相关单位查询
	$("isPublish_yes").disabled = true;
	$("isPublish_no").disabled = true;
	if(data.ajgid){
		$("isPublish_yes").checked = true;
		$("ajgTr").show();
		$("ajgmc").innerText = data.ajgmc;
	}
	//显示关闭按钮
	$("close").show();
}

function setDealValue(){
	if(!data){return}
	//设置机构赋值元素
	Tree.jgmc="ajgmc";
	Tree.jgid="ajgid";
	//设置录入机构、录入人、录入时间
	$("jgmc").value = data.jgmc;
	$("pname").value = data.pname;
	$("pname").disabled = true;
	$("time").value = data.time;
	//分机构设置
	if(baseInfo.jgid == data.jgid){
		//设置标题
		$("title").value = data.title;
		//设置内容
		$("content").value = data.content;
		//设置附件值
		$("apathDescTd").innerText = "附件名称";
		UDload.setDownloadModel("clear", 470, 5);
		$("fileRegion").innerHTML = UDload.getAttachHtmls(data.apath);
		//开放给相关单位查询
		if(data.ajgid){
			$("isPublish_yes").checked = true;
			$("ajgTr").show();
			$("ajgid").value = data.ajgid.replace(/,/g,"；");
			$("ajgmc").value = data.ajgmc.replace(/,/g,"；");
		}
		//设置按钮
		$("modify").show();
	}else{
		
	}
	$("close").show();
}

function modifyCheck(){
	pageInfo = pageInfo || {};
	var title = $("title");
	var tvalue = title.value;
	if(tvalue == ""){
		alert("请输入预案标题！");
		title.focus();
		return false;
	}else{
		if(tvalue != data.title){
			pageInfo.title = tvalue;
		}
	}
	/*
	var content = $("content");
	var cvalue = content.value;
	if(cvalue == ""){
		alert("请输入预案内容！");
		content.focus();
		return false;
	}else{
		if(cvalue != data.content){
			pageInfo.content = cvalue;
		}
	}
	*/
	//重新选择了附件
	if(UDload.isSelectFile()){
		pageInfo.apath = data.apath;
	}else{
		//没有选择附件又删除了所有的附件，提示选择附件
		if(UDload.apaths.length == UDload.rapaths.length){
			alert("请选择附件！");
			return false;
		}
	}
	//如果删除了附件
	if(UDload.rapaths.length != 0){
		var apath = ","+data.apath;
		for ( var i = 0; i < UDload.rapaths.length; i++) {
			apath = apath.replace(","+UDload.rapaths[i],"");
		}
		pageInfo.apath = apath==""?"":apath.substring(1);
	}
	//相关单位
	var ajgid = $("ajgid").value.replace(/；/g,",");
	if(ajgid != data.ajgid){
		pageInfo.ajgid = ajgid;
	}
	
	var i = 0;
	for(var attr in pageInfo){
		i++;
		break;
	}
	if(i == 0){
		alert("您尚未修改任何信息！");
		title.focus();
		return false;
	}
	filterOracle(pageInfo);
	return true;
}

function modify(isValid){
	if(isValid == true || modifyCheck()){
		if(!isValid){
			UDload.cbfname = "modify(true)";
			UDload.upload();
		}else{
			pageInfo.id = data.id;
			if(pageInfo.apath != undefined && UDload.result){
				if(pageInfo.apath){
					pageInfo.apath += ",";
				}
				pageInfo.apath += UDload.result.join(",");
			}
			var url = "tmkr.schemePlan.modify.d";
			new Ajax.Request(url,{
				method:"post",
				parameters : pageInfo,
				onComplete:function(xmlHttp){
					var result = xmlHttp.responseText;
					if(result == "true"){
						alert("修改方案预案成功！");
						window.close();
					}else if(result == "false"){
						alert("修改方案预案失败！");
					}else {
						alert("网络异常！");
					}
				}
			});
		}
	}
}

window.onload = function(){
	setBaseInfo();
	if(!baseInfo.page){
		baseInfo.page = "0";
	}
	switch (baseInfo.page) {
	case "0":
		setAddValue();
		break;
	case "1":
		setQueryValue();
		query();
		break;
	case "2":
		queryData = window.dialogArguments;
		setData();
		setReadValue();
		break;
	case "3":
		queryData = window.dialogArguments;
		setData();
		setDealValue();
		break;
	}
}

