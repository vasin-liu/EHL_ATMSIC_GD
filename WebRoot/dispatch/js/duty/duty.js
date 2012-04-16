
/**
值班信息js
*/

function checkBtn() {

	var btn = jQuery("#updateBtn");
	var leader = jQuery("#leader");
	var person = jQuery("#person");
	var tel1 = jQuery("#tel1");
	var tel2 = jQuery("#tel2");
	//Modified by Liuwx 2011-8-10
	var mobilePhone = jQuery("#mobilePhone");
	//Modification finished
	if (btn.val() == "\u4fee\u6539") {
		leader.attr("disabled", false);
		btn.val("\u4fdd\u5b58");
		person.attr("disabled", false);
		tel1.attr("disabled", false);
		tel2.attr("disabled", false);
		//Modified by Liuwx 2011-8-10
		mobilePhone.attr("disabled", false);
		var close = jQuery("#closeBtn");
		if(close){
			close.hide();
		}
		//Modification finished
	} else {
		if(checkInput("update")){
		btn.val("\u4fee\u6539");
		leader.attr("disabled", true);
		person.attr("disabled", true);
		tel1.attr("disabled", true);
		tel2.attr("disabled", true);
		//Modified by Liuwx 2011-8-10
		mobilePhone.attr("disabled", true);
		//Modification finished
		update();
	}
	}
}
function update() {
	
	var url = "dispatch.duty.update.d";
	var deptId = jQuery("#deptId").val();
	var id = jQuery("#id").val();
	var leader = jQuery("#leader");
	var person = jQuery("#person");
	//Modified by Liuwx 2011-8-10
	var mobilePhone = jQuery("#mobilePhone");
	//Modification finished
	var msg = jQuery("#msg");
	var params = "";
	params += "id=" + id;
	params += "&deptId=" + deptId;
	params += "&leader=" + convertSql(leader.val());
	params += "&person=" + convertSql(person.val());
	//Modified by Liuwx 2011-8-10
	params += "&mobilePhone=" + convertSql(mobilePhone.val());
	//Modification finished
	params = encodeURI(params);
	jQuery.ajax({url:url, type:"GET", data:params, dataType:"xml", timeout:5000, complete:function (xmlHttp) {
		var xmlDoc = xmlHttp.responseText;
		if (xmlDoc == "true") {
			modifyTel();
			msg.html("<font color='red' size='-1'>\u4fee\u6539\u6210\u529f</font>");
			//Modified by Liuwx 2011-8-11
			setTimeout(function(){closeDiv();},1000);
			//Modification finished
		} else {
			msg.html("<font color='red' size='-1'>\u66f4\u65b0\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u6280\u672f\u4eba\u5458\uff01\uff01</font>");
		}
	}});
}
/**检查用户输入  */
function checkInput(type) {
	var flag = true;
	var msg = jQuery("#msg");
	var leader;
	var person;
	var tel1 = jQuery("#tel1").val();
	var tel2 = jQuery("#tel2").val();
	//Modified by Liuwx 2011-8-10
//	var mobilePhone = jQuery("#mobilePhone").val();
	//Modification finished
	if (type == "add") {
		leader = jQuery("#leaderAdd").val();
		person = jQuery("#personAdd").val();
		//Modified by Liuwx 2011-8-10
		var mobilePhone = jQuery("#mobilePhoneAdd").val();
		//Modification finished
	} else {
		if (type == "update") {
			leader = jQuery("#leader").val();
			person = jQuery("#person").val();
			//Modified by Liuwx 2011-8-10
			var mobilePhone = jQuery("#mobilePhone").val();
			//Modification finished
		}
	}
	if (leader == "") {
		msg.html("<font color='red' size='-1'>\u8bf7\u8f93\u5165\u503c\u73ed\u9886\u5bfc</font>");
		flag = false;
	}
	if (person == "") {
		msg.html("<font color='red' size='-1'>\u8bf7\u8f93\u5165\u503c\u73ed\u4eba\u5458</font>");
		flag = false;
	}
	var regExp=/^\d+(\.\d+)?$/;
	if (tel1 == "") {
		msg.html("<font color='red' size='-1'>值班电话的区号不能为空</font>");
		flag = false;
	}
    if (!regExp.test(tel1)) {
    	msg.html("<font color='red' size='-1'>值班电话的区号必须是数字！</font>");
		flag = false;
	}
	if (tel2 == "") {
		msg.html("<font color='red' size='-1'>值班电话不能为空</font>");
		flag = false;
	}
	if (!regExp.test(tel2)) {
    	msg.html("<font color='red' size='-1'>值班电话必须是数字！</font>");
		flag = false;
	}
	//Modified by Liuwx 2011-8-10
    if (tel1.length > 4 || tel1.length < 3) {
    	msg.html("<font color='red' size='-1'>值班电话的区号长度为3-4位！</font>");
		flag = false;
	}
    if (tel2.length > 9) {
    	msg.html("<font color='red' size='-1'>值班电话的长度不能大于9位！</font>");
		flag = false;
	}
	//Modification finished
	//Modified by Liuwx 2011-8-10
    if(mobilePhone != null && mobilePhone != ""){
    	if (!regExp.test(mobilePhone)) {
        	msg.html("<font color='red' size='-1'>值班手机号码必须是数字！</font>");
    		flag = false;
    	}else if (mobilePhone.length < 11) {
        	msg.html("<font color='red' size='-1'>值班手机号码长度不能小于11位！</font>");
    		flag = false;
    	}else if (mobilePhone.length > 15) {
        	msg.html("<font color='red' size='-1'>值班手机号码长度不能超过15位！</font>");
    		flag = false;
    	}
    }
	//Modification finished
	if(!checkChineseName(leader)){
		msg.html("<font color='red' size='-1'>值班领导必须为中文</font>");
		flag = false;
	}
	if(!checkChineseName(person)){
		msg.html("<font color='red' size='-1'>值班人员必须为中文</font>");
		flag = false;
	}
	/*if(!checkImmobilityPhone(tel)){
		msg.html("<font color='red' size='-1'>格式错误，正确的格式为020-XXXXXXXX</font>");
		flag= false
	}*/
	return flag;
}
function addInfo() {
	if(checkInput("add")){
	var msg = jQuery("#msg");
	var url = "dispatch.duty.insert.d";
	var params = "";
	var leader = jQuery("#leaderAdd").val();
	var person = jQuery("#personAdd").val();
	var deptId = jQuery("#deptId").val();
	//Modified by Liuwx 2011-8-10
	var mobilePhone = jQuery("#mobilePhoneAdd").val()?jQuery("#mobilePhoneAdd").val():"";
	//Modification finished
	params += "deptId=" + deptId;
	params += "&leader=" + leader;
	params += "&person=" + person;
	//Modified by Liuwx 2011-8-10
	params += "&mobilePhone=" + convertSql(mobilePhone);
	//Modification finished
	params = encodeURI(params);
	jQuery.ajax({url:url, type:"GET", data:params, dataType:"xml", timeout:5000, complete:function (xmlHttp) {
		var xmlDoc = xmlHttp.responseText;
		if (xmlDoc == "true") {
			msg.html("<font color='red' size='-1'>\u6dfb\u52a0\u6210\u529f\uff01\uff01</font>");
			window.location.href = "duty.jsp?deptId=" + deptId;
		} else {
			msg.html("<font color='red' size='-1'>\u6dfb\u52a0\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u6280\u672f\u4eba\u5458\uff01\uff01</font>");
		}
	}});
	}
}
	
	function modifyTel() {
	var url = "dispatch.duty.modifyTel.d";
	var params = "";
	var tel1 = jQuery("#tel1").val();
	var tel2 = jQuery("#tel2").val();
	var jgid = jQuery("#deptId").val();
	params += "jgid=" + jgid;
	params += "&tel=" + tel1 + "-" + tel2;
	params = encodeURI(params);
	jQuery.ajax({url:url, type:"GET", data:params, dataType:"xml", timeout:5000, complete:function (xmlHttp) {
		var xmlDoc = xmlHttp.responseText;
		if (xmlDoc == "false") {
			alert("值班电话插入错误");
		}
	}});
}
//根据选择部门ID获得值班电话
	function changeSel(){
	    var url = "dispatch.duty.getTel.d";
	    var params = "";
		var jgid = jQuery("#sel").val();
		params += "jgid=" + jgid;
		params = convertSql(params);
		params = encodeURI(params);
		jQuery.ajax({url:url, type:"GET", data:params, dataType:"text", timeout:5000, complete:function (xmlHttp) {
		var xmlDoc = xmlHttp.responseText;
		jQuery("#msg").html("");
		jQuery("#tel").val(xmlDoc!="null"?xmlDoc:"");
	}});
}
//录入值班电话按钮事件
function btnTel(){
  var url = "dispatch.duty.modifyTel.d";
	    var params = "";
		var jgid = jQuery("#sel").val();
		var tel = jQuery("#tel").val();
		params += "jgid=" + jgid;
		params+="&tel="+tel;
		params = convertSql(params);
		params = encodeURI(params);
		jQuery.ajax({url:url, type:"GET", data:params, dataType:"xml", timeout:5000, complete:function (xmlHttp) {
		var xmlDoc = xmlHttp.responseText;
		if(xmlDoc=="true"){
			jQuery("#msg").html("<font color='red'>添加成功！！</font>");
			//Modified by Liuwx 2011-8-11
			setTimeout(function(){closeDiv();},1000);
			//Modification finished
		}else{
			jQuery("#msg").html("<font color='red'>添加失败！！</font>");
		}
	}});
}

